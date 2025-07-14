package it.unina.vetbook.boundary;

import com.github.lgooddatepicker.components.DatePicker;
import it.unina.vetbook.control.ProprietarioController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FormAnimale extends JFrame {

    private final boolean isModifica;

    private JTextField txtCodiceChip, txtNome, txtTipo, txtRazza, txtColore;
    private DatePicker datePicker;
    private JButton salvaBtn, indietroBtn;
    private JPanel mainContentPanel;

    private ProprietarioController proprietarioController;

    public FormAnimale(ProprietarioController proprietarioController) {
        this(proprietarioController, null, "", "", "", "", null);
    }

    public FormAnimale(ProprietarioController proprietarioController, String codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataNascita) {
        this.isModifica = (codiceChip != null);
        this.proprietarioController = proprietarioController;

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
        try {
            String codiceChipStr = txtCodiceChip.getText().trim();
            if (!codiceChipStr.matches("\\d+")) {
                mostraErrore("Il codice chip deve essere un numero valido.");
                return;
            }

            if (codiceChipStr.length() != 10) {
                mostraErrore("Il codice chip deve essere esattamente di 10 cifre.");
                return;
            }

            int codiceChipInt = Integer.parseInt(txtCodiceChip.getText().trim());
            String nomeInput = txtNome.getText().trim();
            String tipoInput = txtTipo.getText().trim();
            String razzaInput = txtRazza.getText().trim();
            String coloreInput = txtColore.getText().trim();
            LocalDate dataNascitaInput = datePicker.getDate();

            if (isModifica) {
                proprietarioController.modificaAnimale(codiceChipInt, nomeInput, tipoInput, razzaInput, coloreInput, dataNascitaInput);
                JOptionPane.showMessageDialog(this, "Animale modificato con successo! (MOCK)");
            } else {
                proprietarioController.inserisciAnimale(codiceChipInt, nomeInput, tipoInput, razzaInput, coloreInput, dataNascitaInput);
                JOptionPane.showMessageDialog(this, "Animale inserito con successo! (MOCK)");
            }

            new AnimaliProprietarioBoundary(proprietarioController).setVisible(true);
            dispose();
        } catch (Exception ex) {
            mostraErrore("Errore: " + ex.getMessage());
        }
    }


    private void handleIndietro() {
        if (isModifica) {
            new AnimaliProprietarioBoundary(proprietarioController).setVisible(true);
        } else {
            new ProprietarioBoundary(proprietarioController).setVisible(true);
        }
        dispose();
    }


    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
    }
}