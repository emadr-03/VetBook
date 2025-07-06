package it.unina.vetbook.boundary;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProfiloProprietarioBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";
    private JLabel imageLabel;
    private final int IMG_SIZE = 128;

    public ProfiloProprietarioBoundary() {
        super("Gestione Profilo");
        VetcareStyle.initLookAndFeel();

        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Layout principale per centrare il contenuto
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());

        // Pannello che contiene tutta la logica (immagine + form)
        JPanel mainContentPanel = new JPanel(new BorderLayout(20, 20));
        mainContentPanel.setOpaque(false);
        add(mainContentPanel, new GridBagConstraints());

        // --- Sezione Immagine (in alto) ---
        JPanel imageSectionPanel = new JPanel();
        imageSectionPanel.setLayout(new BoxLayout(imageSectionPanel, BoxLayout.Y_AXIS));
        imageSectionPanel.setOpaque(false);

        imageLabel = new JLabel(icon("user_profile_icon.png", IMG_SIZE));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel imageButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imageButtonsPanel.setOpaque(false);
        JButton caricaBtn = new JButton("Carica Immagine");
        JButton modificaBtn = new JButton("Modifica Immagine");
        imageButtonsPanel.add(caricaBtn);
        imageButtonsPanel.add(modificaBtn);

        imageSectionPanel.add(imageLabel);
        imageSectionPanel.add(imageButtonsPanel);

        mainContentPanel.add(imageSectionPanel, BorderLayout.NORTH);

        // --- Sezione Form Dati (al centro) ---
        JPanel formSectionPanel = new JPanel(new GridBagLayout());
        formSectionPanel.setOpaque(false);
        formSectionPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(8, 8, 8, 8);

        JTextField txtNome = VetcareStyle.textField("Nome");
        txtNome.setText("Mario");
        JTextField txtCognome = VetcareStyle.textField("Cognome");
        txtCognome.setText("Rossi");
        JTextField txtUsername = VetcareStyle.textField("Username");
        txtUsername.setText("mrossi");
        txtUsername.setEditable(false);
        JTextField txtEmail = VetcareStyle.textField("Email");
        txtEmail.setText("mario.rossi@email.com");
        JPasswordField password = VetcareStyle.passwordField("Nuova password");

        c.gridx = 0; c.gridy = 0; formSectionPanel.add(new JLabel("Nome:"), c);
        c.gridx = 1; c.gridy = 0; formSectionPanel.add(txtNome, c);
        c.gridx = 0; c.gridy = 1; formSectionPanel.add(new JLabel("Cognome:"), c);
        c.gridx = 1; c.gridy = 1; formSectionPanel.add(txtCognome, c);
        c.gridx = 0; c.gridy = 2; formSectionPanel.add(new JLabel("Username:"), c);
        c.gridx = 1; c.gridy = 2; formSectionPanel.add(txtUsername, c);
        c.gridx = 0; c.gridy = 3; formSectionPanel.add(new JLabel("Email:"), c);
        c.gridx = 1; c.gridy = 3; formSectionPanel.add(txtEmail, c);
        c.gridx = 0; c.gridy = 4; formSectionPanel.add(new JLabel("Password:"), c);
        c.gridx = 1; c.gridy = 4; formSectionPanel.add(password, c);

        mainContentPanel.add(formSectionPanel, BorderLayout.CENTER);

        // --- Pulsanti di controllo (in basso) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setOpaque(false);
        JButton salvaBtn = new JButton("Salva");
        JButton esciBtn = new JButton("Esci");
        bottomPanel.add(salvaBtn);
        bottomPanel.add(esciBtn);
        mainContentPanel.add(bottomPanel, BorderLayout.SOUTH);

        // --- Azioni Pulsanti ---
        caricaBtn.addActionListener(e -> handleImageUpload());
        modificaBtn.addActionListener(e -> handleImageUpload());

        salvaBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Profilo salvato con successo! (MOCK)");
        });

        esciBtn.addActionListener(e -> {
            new ProprietarioBoundary().setVisible(true);
            dispose();
        });
    }

    private void handleImageUpload() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Immagini (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            ImageIcon newIcon = new ImageIcon(selectedFile.getAbsolutePath());
            imageLabel.setIcon(scaleImageIcon(newIcon, IMG_SIZE));
        }
    }

    private ImageIcon scaleImageIcon(ImageIcon sourceIcon, int size) {
        Image image = sourceIcon.getImage();
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private ImageIcon icon(String file, int size) {
        try {
            return scaleImageIcon(new ImageIcon(RES + file), size);
        } catch (Exception e) {
            return new ImageIcon(new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB));
        }
    }
}