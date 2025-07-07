package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.entity.TipoVisita;

import javax.swing.*;
import java.awt.*;

public class FormDialogVisita extends JDialog {

    public FormDialogVisita(Frame owner) {
        super(owner, "Registra Visita", true);
        setSize(500, 550);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());

        JPanel card = VetcareStyle.makeDialogCard();
        add(card);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 10, 20);
        c.gridx = 0; c.gridy = 0;

        JComboBox<TipoVisita> tipoVisitaCombo = new JComboBox<>(TipoVisita.values());
        JTextField descrizioneText = VetcareStyle.textField("Descrizione della visita...");
        JTextField costoText = VetcareStyle.textField("Costo (es. 50.00)");
        JTextField nomeFarmacoText = VetcareStyle.textField("Nome farmaco (opzionale)");
        JTextField prodFarmacoText = VetcareStyle.textField("Produttore farmaco (opzionale)");

        card.add(new JLabel("Tipo Visita:"), c); c.gridy++;
        card.add(tipoVisitaCombo, c); c.gridy++;
        card.add(new JLabel("Descrizione:"), c); c.gridy++;
        card.add(descrizioneText, c); c.gridy++;
        card.add(new JLabel("Costo:"), c); c.gridy++;
        card.add(costoText, c); c.gridy++;
        card.add(new JLabel("Nome Farmaco:"), c); c.gridy++;
        card.add(nomeFarmacoText, c); c.gridy++;
        card.add(new JLabel("Produttore Farmaco:"), c); c.gridy++;
        card.add(prodFarmacoText, c); c.gridy++;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton registraBtn = new JButton("Registra");
        JButton chiudiBtn = new JButton("Chiudi");
        buttonPanel.setOpaque(false);
        buttonPanel.add(registraBtn);
        buttonPanel.add(chiudiBtn);
        c.insets = new Insets(20, 20, 10, 20);
        card.add(buttonPanel, c);

        registraBtn.addActionListener(e -> {
            // --- INIZIO BLOCCO DI VALIDAZIONE ---
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
            // --- FINE BLOCCO DI VALIDAZIONE ---

            try {
                TipoVisita tipo = (TipoVisita) tipoVisitaCombo.getSelectedItem();

                AgendaController.getInstance().registraVisita(tipo, descrizione, costo, prodFarmaco, nomeFarmaco);

                JOptionPane.showMessageDialog(this, "Visita registrata con successo!");
                dispose();
            } catch (Exception ex) {
                mostraErrore("Errore durante la registrazione: " + ex.getMessage());
            }
        });

        chiudiBtn.addActionListener(e -> dispose());
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
    }
}