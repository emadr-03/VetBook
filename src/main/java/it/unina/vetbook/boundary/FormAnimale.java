package it.unina.vetbook.boundary;

import com.github.lgooddatepicker.components.DatePicker;
import it.unina.vetbook.control.ProprietarioController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FormAnimale extends JFrame {

    private final ProprietarioController ctrl = ProprietarioController.getInstance();
    private final boolean isModifica;

    private JTextField txtCodiceChip, txtNome, txtTipo, txtRazza, txtColore;
    private DatePicker datePicker;
    private JButton salvaBtn, indietroBtn;
    private JPanel mainContentPanel;

    public FormAnimale() {
        this(null, "", "", "", "", null);
    }

    public FormAnimale(String codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataNascita) {
        this.isModifica = (codiceChip != null);

        initFrame();
        initComponents(codiceChip, nome, tipo, razza, colore, dataNascita);
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        String title = isModifica ? "Modifica Animale" : "Inserisci Nuovo Animale";
        setTitle(title);
        VetcareStyle.initLookAndFeel();
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());
    }

    private void initComponents(String codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataNascita) {
        mainContentPanel = new JPanel(new GridBagLayout());
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtCodiceChip = VetcareStyle.textField("Codice Chip (10 cifre)");
        txtNome = VetcareStyle.textField("Nome dell'animale");
        txtTipo = VetcareStyle.textField("Es. Cane, Gatto");
        txtRazza = VetcareStyle.textField("Razza");
        txtColore = VetcareStyle.textField("Colore del manto");
        datePicker = VetcareStyle.makeDatePicker();
        salvaBtn = new JButton(isModifica ? "Salva Modifiche" : "Inserisci Animale");
        indietroBtn = new JButton("Indietro");

        if (isModifica) {
            txtCodiceChip.setText(codiceChip);
            txtCodiceChip.setEditable(false);
            txtNome.setText(nome);
            txtTipo.setText(tipo);
            txtRazza.setText(razza);
            txtColore.setText(colore);
            datePicker.setDate(dataNascita);
        }
    }

    private void layoutComponents() {
        add(mainContentPanel, new GridBagConstraints());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(8, 8, 8, 8);

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
        bottomPanel.add(salvaBtn);
        bottomPanel.add(indietroBtn);

        c.gridx = 0; c.gridy = 6; c.gridwidth = 2; c.insets = new Insets(20, 8, 8, 8);
        mainContentPanel.add(bottomPanel, c);
    }

    private void addListeners() {
        salvaBtn.addActionListener(e -> handleSalva());
        indietroBtn.addActionListener(e -> handleIndietro());
    }

    private void handleSalva() {
        String nomeInput = txtNome.getText().trim();
        if (nomeInput.isEmpty() || nomeInput.length() > 40 || nomeInput.matches(".*\\d.*") || !nomeInput.matches("[a-zA-Z\\s]+")) {
            mostraErrore("Formato nome non valido.");
            return;
        }

        String tipoInput = txtTipo.getText().trim();
        if (validaCampoTesto(tipoInput, "Tipo", 20) != null) {
            mostraErrore(validaCampoTesto(tipoInput, "Tipo", 20));
            return;
        }

        String razzaInput = txtRazza.getText().trim();
        if (validaCampoTesto(razzaInput, "Razza", 20) != null) {
            mostraErrore(validaCampoTesto(razzaInput, "Razza", 20));
            return;
        }

        String coloreInput = txtColore.getText().trim();
        if (validaCampoTesto(coloreInput, "Colore", 20) != null) {
            mostraErrore(validaCampoTesto(coloreInput, "Colore", 20));
            return;
        }

        LocalDate dataNascitaInput = datePicker.getDate();
        if (dataNascitaInput == null || dataNascitaInput.isAfter(LocalDate.now())) {
            mostraErrore("Data di nascita non valida.");
            return;
        }

        try {
            int codiceChipInt = Integer.parseInt(txtCodiceChip.getText().trim());

            if (isModifica) {
                ctrl.modificaAnimale(codiceChipInt, nomeInput, tipoInput, razzaInput, coloreInput, dataNascitaInput);
                JOptionPane.showMessageDialog(this, "Animale modificato con successo!");
            } else {
                if (txtCodiceChip.getText().trim().length() != 10) {
                    mostraErrore("Il Codice Chip deve essere di esattamente 10 cifre.");
                    return;
                }
                ctrl.inserisciAnimale(codiceChipInt, nomeInput, tipoInput, razzaInput, coloreInput, dataNascitaInput);
                JOptionPane.showMessageDialog(this, "Animale inserito con successo!");
            }
            new AnimaliProprietarioBoundary().setVisible(true);
            dispose();
        } catch (NumberFormatException ex) {
            mostraErrore("Il codice chip deve essere un numero valido.");
        } catch (Exception ex) {
            mostraErrore("Errore: " + ex.getMessage());
        }
    }

    private void handleIndietro() {
        if (isModifica) {
            new AnimaliProprietarioBoundary().setVisible(true);
        } else {
            new ProprietarioBoundary().setVisible(true);
        }
        dispose();
    }

    private String validaCampoTesto(String testo, String nomeCampo, int lunghezzaMax) {
        if (testo.isEmpty()) return "Il campo '" + nomeCampo + "' è obbligatorio.";
        if (testo.length() > lunghezzaMax) return "Il campo '" + nomeCampo + "' è troppo lungo!";
        if (testo.matches(".*\\d.*")) return "Il campo '" + nomeCampo + "' non può contenere un numero!";
        if (!testo.matches("[a-zA-Z\\s]+")) return "Il campo '" + nomeCampo + "' non può contenere simboli speciali!";
        return null;
    }

    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
    }
}