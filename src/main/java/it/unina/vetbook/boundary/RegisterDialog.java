package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AuthController;
import it.unina.vetbook.utils.RegistrationResult;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    private JTextField emailField, userField, nomeField, cognomeField;
    private JPasswordField passwordField1, passwordField2;
    private JButton okButton, cancelButton;
    private final Frame parentFrame;

    private final AuthController authController;

    public RegisterDialog(Frame owner, AuthController authController) {
        super(owner, "Registrazione proprietario", true);
        this.authController = authController;
        this.parentFrame = owner;

        initDialog();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initDialog() {
        setSize(480, 450);
        setLocationRelativeTo(getOwner());
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        emailField = VetcareStyle.textField("E-mail");
        userField = VetcareStyle.textField("Username");
        nomeField = VetcareStyle.textField("Nome");
        cognomeField = VetcareStyle.textField("Cognome");
        passwordField1 = VetcareStyle.passwordField("Password");
        passwordField2 = VetcareStyle.passwordField("Ripeti password");
        okButton = new JButton("Registrati");
        cancelButton = new JButton("Annulla");
    }

    private void layoutComponents() {
        JPanel card = VetcareStyle.makeDialogCard();
        add(card);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 20, 12, 20);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;

        card.add(emailField, c);   c.gridy++;
        card.add(userField, c);    c.gridy++;
        card.add(nomeField, c);     c.gridy++;
        card.add(cognomeField, c);  c.gridy++;
        card.add(passwordField1, c); c.gridy++;
        card.add(passwordField2, c); c.gridy++;

        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnBox.setOpaque(false);
        btnBox.add(okButton);
        btnBox.add(cancelButton);
        card.add(btnBox, c);
    }

    private void addListeners() {
        okButton.addActionListener(e -> handleRegistrazione());
        cancelButton.addActionListener(e -> dispose());
    }

    private void handleRegistrazione() {
        if (!String.valueOf(passwordField1.getPassword()).equals(String.valueOf(passwordField2.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Le password non coincidono");
            return;
        }

        RegistrationResult result = authController.registrati(
                userField.getText(),
                emailField.getText(),
                nomeField.getText(),
                cognomeField.getText(),
                String.valueOf(passwordField1.getPassword())
        );

        if (result.isSuccess()) {
            JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!");
            new ProprietarioBoundary(result.getController()).setVisible(true);
            dispose();
            parentFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(this, result.getErrorMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }

    }
}