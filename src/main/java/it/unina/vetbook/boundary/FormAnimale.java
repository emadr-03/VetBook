package it.unina.vetbook.boundary;

import com.github.lgooddatepicker.components.DatePicker;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FormAnimale extends JFrame {

    // Costruttore per INSERIMENTO
    public FormAnimale() {
        this(null, "", "", "", "", null);
        setTitle("Inserisci Nuovo Animale");
    }

    // Costruttore per MODIFICA
    public FormAnimale(String codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataNascita) {
        super("Modifica Animale");
        boolean isModifica = (codiceChip != null);

        VetcareStyle.initLookAndFeel();

        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());

        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainContentPanel, new GridBagConstraints());

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(8, 8, 8, 8);

        JTextField txtCodiceChip = VetcareStyle.textField("Codice Chip (10 cifre)");
        JTextField txtNome = VetcareStyle.textField("Nome dell'animale");
        JTextField txtTipo = VetcareStyle.textField("Es. Cane, Gatto");
        JTextField txtRazza = VetcareStyle.textField("Razza");
        JTextField txtColore = VetcareStyle.textField("Colore del manto");
        DatePicker datePicker = VetcareStyle.makeDatePicker();

        if (isModifica) {
            txtCodiceChip.setText(codiceChip);
            txtCodiceChip.setEditable(false);
            txtNome.setText(nome);
            txtTipo.setText(tipo);
            txtRazza.setText(razza);
            txtColore.setText(colore);
            datePicker.setDate(dataNascita);
        }

        c.gridx = 0; c.gridy = 0; mainContentPanel.add(new JLabel("Codice Chip:"), c);
        c.gridx = 1; c.gridy = 0; mainContentPanel.add(txtCodiceChip, c);
        c.gridx = 0; c.gridy = 1; mainContentPanel.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = 1; mainContentPanel.add(txtNome, c);
        c.gridx = 0; c.gridy = 2; mainContentPanel.add(new JLabel("Tipo:"), c);
        c.gridx = 1; c.gridy = 2; mainContentPanel.add(txtTipo, c);
        c.gridx = 0; c.gridy = 3; mainContentPanel.add(new JLabel("Razza:"), c);
        c.gridx = 1; c.gridy = 3; mainContentPanel.add(txtRazza, c);
        c.gridx = 0; c.gridy = 4; mainContentPanel.add(new JLabel("Colore:"), c);
        c.gridx = 1; c.gridy = 4; mainContentPanel.add(txtColore, c);
        c.gridx = 0; c.gridy = 5; mainContentPanel.add(new JLabel("Data di Nascita:"), c);
        c.gridx = 1; c.gridy = 5; mainContentPanel.add(datePicker, c);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setOpaque(false);
        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; c.insets = new Insets(20, 8, 8, 8);
        mainContentPanel.add(bottomPanel, c);

        JButton salvaBtn = new JButton(isModifica ? "Salva Modifiche" : "Inserisci Animale");
        JButton indietroBtn = new JButton("Indietro");
        bottomPanel.add(salvaBtn);
        bottomPanel.add(indietroBtn);

        salvaBtn.addActionListener(e -> {
            String nomeInput = txtNome.getText().trim();
            if (nomeInput.isEmpty()) {
                mostraErrore("Il campo Nome è obbligatorio.");
                return;
            }
            if (nomeInput.length() > 40) {
                mostraErrore("Nome troppo lungo!");
                return;
            }
            if (nomeInput.matches(".*\\d.*")) {
                mostraErrore("Il nome non può contenere numeri!");
                return;
            }
            if (!nomeInput.matches("[a-zA-Z\\s]+")) {
                mostraErrore("Il nome non può contenere simboli speciali!");
                return;
            }

            String tipoInput = txtTipo.getText().trim();
            String erroreTipo = validaCampoTesto(tipoInput, "Tipo", 20);
            if (erroreTipo != null) {
                mostraErrore(erroreTipo);
                return;
            }

            String razzaInput = txtRazza.getText().trim();
            String erroreRazza = validaCampoTesto(razzaInput, "Razza", 20);
            if(erroreRazza != null) {
                mostraErrore(erroreRazza);
                return;
            }

            String coloreInput = txtColore.getText().trim();
            String erroreColore = validaCampoTesto(coloreInput, "Colore", 20);
            if(erroreColore != null) {
                mostraErrore(erroreColore);
                return;
            }

            LocalDate dataNascitaInput = datePicker.getDate();
            if (dataNascitaInput == null) {
                mostraErrore("Formato data errato!");
                return;
            }
            if (dataNascitaInput.isAfter(LocalDate.now())) {
                mostraErrore("Non è possibile inserire una data futura!");
                return;
            }

            if (isModifica) {
                JOptionPane.showMessageDialog(this, "Animale modificato con successo! (MOCK)");
            } else {
                String codiceChipInput = txtCodiceChip.getText().trim();
                if (codiceChipInput.isEmpty() || !codiceChipInput.matches("\\d+")) {
                    mostraErrore("Formato codice chip errato!");
                    return;
                }
                if (codiceChipInput.length() != 10) {
                    mostraErrore("Il Codice Chip deve essere di esattamente 10 cifre.");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Animale inserito con successo! (MOCK)");
            }
        });

        indietroBtn.addActionListener(e -> {
            if (isModifica) {
                new AnimaliProprietarioBoundary().setVisible(true);
            } else {
                new ProprietarioBoundary().setVisible(true);
            }
            dispose();
        });
    }

    private String validaCampoTesto(String testo, String nomeCampo, int lunghezzaMax) {
        if (testo.isEmpty()) {
            return "Il campo '" + nomeCampo + "' è obbligatorio.";
        }
        if (testo.length() > lunghezzaMax) {
            return "Il campo '" + nomeCampo + "' è troppo lungo!";
        }
        if (testo.matches(".*\\d.*")) {
            return "Il campo '" + nomeCampo + "' non può contenere un numero!";
        }
        if (!testo.matches("[a-zA-Z\\s]+")) {
            return "Il campo '" + nomeCampo + "' non può contenere simboli speciali!";
        }
        return null;
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
    }
}