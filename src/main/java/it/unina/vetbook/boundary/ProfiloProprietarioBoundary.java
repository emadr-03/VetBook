package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.ProprietarioDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;

public class ProfiloProprietarioBoundary extends JFrame {

    private static final String RES = "/img/";
    private final ProprietarioDTO proprietarioCorrente;

    private JLabel imageLabel;
    private JTextField txtNome, txtCognome, txtUsername, txtEmail;
    private JPasswordField password;
    private JButton caricaBtn, modificaBtn, salvaBtn, esciBtn;

    private final ProprietarioController proprietarioController;

    public ProfiloProprietarioBoundary(ProprietarioController proprietarioController) {
        super("Gestione Profilo");
        this.proprietarioController = proprietarioController;
        this.proprietarioCorrente = proprietarioController.getProprietarioDTO();

        initFrame();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        VetcareStyle.initLookAndFeel();
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        imageLabel = new JLabel(icon());
        caricaBtn = new JButton("Carica Immagine");
        modificaBtn = new JButton("Modifica Immagine");

        txtNome = VetcareStyle.textField("Nome");
        txtNome.setText(proprietarioCorrente.nome());

        txtCognome = VetcareStyle.textField("Cognome");
        txtCognome.setText(proprietarioCorrente.cognome());

        txtUsername = VetcareStyle.textField("Username");
        txtUsername.setText(proprietarioCorrente.username());
        txtUsername.setEditable(false);

        txtEmail = VetcareStyle.textField("Email");
        txtEmail.setText(proprietarioCorrente.email());

        password = VetcareStyle.passwordField("Nuova password (lasciare vuoto per non modificare)");

        salvaBtn = new JButton("Salva");
        esciBtn = new JButton("Esci");
    }

    private void layoutComponents() {
        JPanel mainContentPanel = new JPanel(new BorderLayout(20, 20));
        mainContentPanel.setOpaque(false);
        add(mainContentPanel, new GridBagConstraints());

        JPanel imageSectionPanel = new JPanel();
        imageSectionPanel.setLayout(new BoxLayout(imageSectionPanel, BoxLayout.Y_AXIS));
        imageSectionPanel.setOpaque(false);

        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel imageButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imageButtonsPanel.setOpaque(false);
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
        bottomPanel.add(salvaBtn);
        bottomPanel.add(esciBtn);
        mainContentPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        caricaBtn.addActionListener(e -> handleImageUpload());
        modificaBtn.addActionListener(e -> handleImageUpload());
        salvaBtn.addActionListener(e -> handleSalva());
        esciBtn.addActionListener(e -> handleEsci());
    }

    private void handleSalva() {
        try {
            proprietarioController.gestioneProfilo(
                    txtUsername.getText(),
                    txtNome.getText(),
                    txtCognome.getText(),
                    txtEmail.getText(),
                    String.valueOf(password.getPassword())
            );
            JOptionPane.showMessageDialog(this, "Profilo aggiornato con successo!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEsci() {
        new ProprietarioBoundary(proprietarioController).setVisible(true);
        dispose();
    }

    private void handleImageUpload() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Immagini (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                proprietarioController.aggiornaImmagineProfilo(selectedFile);

                byte[] imgBytes = proprietarioController.getProprietarioDTO().immagineProfilo();
                if (imgBytes != null) {
                    ImageIcon icon = new ImageIcon(imgBytes);
                    imageLabel.setIcon(scaleImageIcon(icon));
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore durante il caricamento dell'immagine:\n" + ex.getMessage(),
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private ImageIcon scaleImageIcon(ImageIcon sourceIcon) {
        Image image = sourceIcon.getImage();
        Image scaledImage = image.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private ImageIcon icon() {
        try {
            return scaleImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(RES + "user_profile_icon.png"))));
        } catch (Exception e) {
            return createPlaceholderIcon();
        }
    }

    private ImageIcon createPlaceholderIcon() {
        BufferedImage placeholder = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = placeholder.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(220, 220, 220));
        g2d.fillOval(0, 0, 128, 128);

        g2d.setColor(new Color(180, 180, 180));
        int headSize = 128 / 2;
        g2d.fillOval((128 - headSize) / 2, 128 / 6, headSize, headSize);

        int bodyHeight = 128 / 2;
        g2d.fillArc(-128 / 4, 128 / 2, 128 + 128 /2, bodyHeight, 0, -180);

        g2d.dispose();
        return new ImageIcon(placeholder);
    }
}