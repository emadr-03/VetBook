package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.ProprietarioDTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class ProfiloProprietarioBoundary extends JDialog {

    private static final int PROFILE_IMG_SIZE = 80;

    private final JTextField txtNome = VetcareStyle.textField("Nome");
    private final JTextField txtCognome = VetcareStyle.textField("Cognome");
    private final JTextField txtUsername = VetcareStyle.textField("Username");
    private final JTextField txtEmail = VetcareStyle.textField("E-mail");
    private final JPasswordField txtPassword = VetcareStyle.passwordField("Nuova password (opzionale)");
    private final JLabel lblProfileIcon = new JLabel();

    public ProfiloProprietarioBoundary(Window owner) {
        super(owner, "Gestione Profilo", ModalityType.APPLICATION_MODAL);
        setSize(500, 600);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());

        JPanel card = VetcareStyle.makeDialogCard();
        card.setLayout(new GridBagLayout());
        add(card);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 20, 10, 20);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        card.add(lblProfileIcon, c);

        c.gridy++;
        c.insets = new Insets(0, 20, 20, 20);
        JButton cambiaFotoBtn = new JButton("Cambia Foto...");
        card.add(cambiaFotoBtn, c);
        c.gridwidth = 1;

        c.insets = new Insets(8, 20, 8, 20);
        c.anchor = GridBagConstraints.WEST;
        c.gridy++; c.gridx = 0; card.add(new JLabel("Nome:"), c);
        c.gridx = 1; card.add(txtNome, c);
        c.gridy++; c.gridx = 0; card.add(new JLabel("Cognome:"), c);
        c.gridx = 1; card.add(txtCognome, c);
        c.gridy++; c.gridx = 0; card.add(new JLabel("Username:"), c);
        c.gridx = 1; card.add(txtUsername, c);
        c.gridy++; c.gridx = 0; card.add(new JLabel("Email:"), c);
        c.gridx = 1; card.add(txtEmail, c);
        c.gridy++; c.gridx = 0; card.add(new JLabel("Password:"), c);
        c.gridx = 1; card.add(txtPassword, c);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 20, 20, 20);
        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton salvaBtn = new JButton("Salva Modifiche");
        JButton annullaBtn = new JButton("Annulla");
        btnBox.setOpaque(false);
        btnBox.add(salvaBtn);
        btnBox.add(annullaBtn);
        card.add(btnBox, c);

        caricaDatiDalController();

        annullaBtn.addActionListener(e -> dispose());

        salvaBtn.addActionListener(e -> {
            try {
                InputStream imgStream = null;
                String imagePath = (String) lblProfileIcon.getClientProperty("imagePath");

                if (imagePath != null && new File(imagePath).exists()) {
                    imgStream = new FileInputStream(imagePath);
                }

                ProprietarioController.getInstance().gestioneProfilo(
                        txtNome.getText(),
                        txtCognome.getText(),
                        txtUsername.getText(),
                        String.valueOf(txtPassword.getPassword()),
                        txtEmail.getText(),
                        imgStream
                );
                JOptionPane.showMessageDialog(this, "Profilo aggiornato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il salvataggio: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        cambiaFotoBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Immagini (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
            chooser.setFileFilter(filter);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                aggiornaIconaProfilo(selectedFile.getAbsolutePath());
            }
        });
    }

    private void caricaDatiDalController() {
        ProprietarioDTO proprietario = ProprietarioController.getInstance().getDatiProprietarioDTO();
        txtNome.setText(proprietario.nome);
        txtCognome.setText(proprietario.cognome);
        txtUsername.setText(proprietario.username);
        txtEmail.setText(proprietario.email);

        Object imageSource;
        File f = new File(proprietario.imagePath);
        if (f.exists() && !f.isDirectory()) {
            imageSource = proprietario.imagePath;
        } else {
            imageSource = getClass().getResource(proprietario.imagePath);
            if (imageSource == null) {
                imageSource = getClass().getResource("/img/user_profile_icon.png");
            }
        }
        aggiornaIconaProfilo(imageSource);
    }

    private void aggiornaIconaProfilo(Object pathOrUrl) {
        ImageIcon imageIcon = null;
        String pathForStorage = "";

        if (pathOrUrl instanceof String) {
            imageIcon = new ImageIcon((String) pathOrUrl);
            pathForStorage = (String) pathOrUrl;
        } else if (pathOrUrl instanceof URL) {
            imageIcon = new ImageIcon((URL) pathOrUrl);
            pathForStorage = ((URL) pathOrUrl).getPath();
        } else {
            System.err.println("Percorso immagine nullo o non valido.");
            URL fallbackUrl = getClass().getResource("/img/user_profile_icon.png");
            if (fallbackUrl != null) imageIcon = new ImageIcon(fallbackUrl);
        }

        if (imageIcon != null) {
            Image scaledImage = imageIcon.getImage().getScaledInstance(PROFILE_IMG_SIZE, PROFILE_IMG_SIZE, Image.SCALE_SMOOTH);
            lblProfileIcon.setIcon(new ImageIcon(scaledImage));
            lblProfileIcon.putClientProperty("imagePath", pathForStorage);
        }
    }
}