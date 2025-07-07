package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.entity.Proprietario;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ProfiloProprietarioBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";
    private JLabel imageLabel;
    private final int IMG_SIZE = 128;
    private final Proprietario proprietarioCorrente;

    public ProfiloProprietarioBoundary() {
        super("Gestione Profilo");
        this.proprietarioCorrente = ProprietarioController.getInstance().getProprietario();
        VetcareStyle.initLookAndFeel();

        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());

        JPanel mainContentPanel = new JPanel(new BorderLayout(20, 20));
        mainContentPanel.setOpaque(false);
        add(mainContentPanel, new GridBagConstraints());

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

        JPanel formSectionPanel = new JPanel(new GridBagLayout());
        formSectionPanel.setOpaque(false);
        formSectionPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(8, 8, 8, 8);

        JTextField txtNome = VetcareStyle.textField("Nome");
        txtNome.setText(proprietarioCorrente.getNome());
        JTextField txtCognome = VetcareStyle.textField("Cognome");
        txtCognome.setText(proprietarioCorrente.getCognome());
        JTextField txtUsername = VetcareStyle.textField("Username");
        // txtUsername.setText(proprietarioCorrente.getUsername());
        txtUsername.setEditable(false);
        JTextField txtEmail = VetcareStyle.textField("Email");
        // txtEmail.setText(proprietarioCorrente.getEmail());
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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setOpaque(false);
        JButton salvaBtn = new JButton("Salva");
        JButton esciBtn = new JButton("Esci");
        bottomPanel.add(salvaBtn);
        bottomPanel.add(esciBtn);
        mainContentPanel.add(bottomPanel, BorderLayout.SOUTH);

        caricaBtn.addActionListener(e -> handleImageUpload());
        modificaBtn.addActionListener(e -> handleImageUpload());

        salvaBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funzionalità non richiesta in questo contesto.");
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
            return createPlaceholderIcon(size);
        }
    }

    private ImageIcon createPlaceholderIcon(int size) {
        BufferedImage placeholder = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholder.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(220, 220, 220));
        g2d.fillOval(0, 0, size, size);

        g2d.setColor(new Color(180, 180, 180));
        int headSize = size / 2;
        g2d.fillOval((size - headSize) / 2, size / 6, headSize, headSize);

        int bodyWidth = size;
        int bodyHeight = size / 2;
        g2d.fillArc(-bodyWidth / 4, size / 2, bodyWidth + bodyWidth/2, bodyHeight, 0, -180);

        g2d.dispose();
        return new ImageIcon(placeholder);
    }
}