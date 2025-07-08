package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.entity.TipoVisita;

import javax.swing.*;
import java.awt.*;

public class FormDialogVisita extends JDialog {

    private JComboBox<TipoVisita> tipoVisitaCombo;
    private JTextField descrizioneText;
    private JTextField costoText;
    private JTextField nomeFarmacoText;
    private JTextField prodFarmacoText;
    private JButton registraBtn;
    private JButton chiudiBtn;

    public FormDialogVisita(Frame owner) {
        super(owner, "Registra Visita", true);

        initDialog();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initDialog() {
        setSize(500, 550);
        setLocationRelativeTo(getOwner());
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        tipoVisitaCombo = new JComboBox<>(TipoVisita.values());
        descrizioneText = VetcareStyle.textField("Descrizione della visita...");
        costoText = VetcareStyle.textField("Costo (es. 50.00)");
        nomeFarmacoText = VetcareStyle.textField("Nome farmaco/i (opzionale)");
        prodFarmacoText = VetcareStyle.textField("Produttore farmaco/i (opzionale)");
        registraBtn = new JButton("Registra");
        chiudiBtn = new JButton("Chiudi");
    }

    private void layoutComponents() {
        JPanel card = VetcareStyle.makeDialogCard();
        add(card);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 10, 20);
        c.gridx = 0;
        c.gridy = 0;

        card.add(new JLabel("Tipo Visita:"), c); c.gridy++;
        card.add(tipoVisitaCombo, c); c.gridy++;
        card.add(new JLabel("Descrizione:"), c); c.gridy++;
        card.add(descrizioneText, c); c.gridy++;
        card.add(new JLabel("Costo:"), c); c.gridy++;
        card.add(costoText, c); c.gridy++;
        card.add(new JLabel("Nome Farmaco/i:"), c); c.gridy++;
        card.add(nomeFarmacoText, c); c.gridy++;
        card.add(new JLabel("Produttore Farmaco/i:"), c); c.gridy++;
        card.add(prodFarmacoText, c); c.gridy++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registraBtn);
        buttonPanel.add(chiudiBtn);

        c.insets = new Insets(20, 20, 10, 20);
        card.add(buttonPanel, c);
    }

    private void addListeners() {
        registraBtn.addActionListener(e -> handleRegistra());
        chiudiBtn.addActionListener(e -> dispose());
    }

    private void handleRegistra() {
        String descrizione = descrizioneText.getText().trim();
        if (descrizione.length() > 150) {
            mostraErrore("La descrizione non può superare i 150 caratteri.");
            return;
        }

        String costoStr = costoText.getText().trim();
        if (costoStr.isEmpty()) {
            mostraErrore("Il campo Costo è obbligatorio.");
            return;
        }

        double costo;
        try {
            costo = Double.parseDouble(costoStr);
            if (costo < 0) {
                mostraErrore("Il costo non può essere un numero minore di 0!");
                return;
            }
        } catch (NumberFormatException ex) {
            mostraErrore("Costo non valido! Inserire un numero.");
            return;
        }

        String nomeFarmaco = nomeFarmacoText.getText().trim();
        if (nomeFarmaco.length() > 150) {
            mostraErrore("Il nome del farmaco non può superare i 150 caratteri.");
            return;
        }

        String prodFarmaco = prodFarmacoText.getText().trim();
        if (prodFarmaco.length() > 150) {
            mostraErrore("Il produttore del farmaco non può superare i 150 caratteri.");
            return;
        }

        try {
            TipoVisita tipo = (TipoVisita) tipoVisitaCombo.getSelectedItem();
            AgendaController.getInstance().registraVisita(tipo, descrizione, costo, prodFarmaco, nomeFarmaco);
            JOptionPane.showMessageDialog(this, "Visita registrata con successo!");
            dispose();
        } catch (Exception ex) {
            mostraErrore("Errore durante la registrazione: " + ex.getMessage());
        }
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
    }
}