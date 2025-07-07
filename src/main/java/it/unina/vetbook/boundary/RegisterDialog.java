package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AuthController;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    public RegisterDialog(Frame owner) {
        super(owner, "Registrazione proprietario", true);
        setSize(480, 420);
        setLocationRelativeTo(owner);

        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());

        JPanel card = VetcareStyle.makeDialogCard();
        add(card);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12,20,12,20);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.CENTER;

        JTextField email = VetcareStyle.textField("E-mail");
        JTextField user  = VetcareStyle.textField("Username");
        JTextField nome = VetcareStyle.textField("Nome");
        JTextField cognome = VetcareStyle.textField("Cognome");
        JPasswordField p1= VetcareStyle.passwordField("Password");
        JPasswordField p2= VetcareStyle.passwordField("Ripeti password");

        card.add(email, c);   c.gridy++;
        card.add(user , c);   c.gridy++;
        card.add(nome, c);    c.gridy++;
        card.add(cognome, c); c.gridy++;
        card.add(p1   , c);   c.gridy++;
        card.add(p2   , c);   c.gridy++;

        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        JButton ok = new JButton("Registrati");
        JButton cancel = new JButton("Annulla");
        btnBox.setOpaque(false);
        btnBox.add(ok); btnBox.add(cancel);
        card.add(btnBox, c);

        ok.addActionListener(e -> {
            if (!String.valueOf(p1.getPassword()).equals(String.valueOf(p2.getPassword()))) {
                JOptionPane.showMessageDialog(this,"Le password non coincidono");
                return;
            }

            try {
                // Chiama il Controller per eseguire la registrazione
                AuthController.getInstance().registrati(
                        user.getText(),
                        email.getText(),
                        nome.getText(),
                        cognome.getText(),
                        String.valueOf(p1.getPassword())
                );
                JOptionPane.showMessageDialog(this, "Registrazione avvenuta con successo!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore di registrazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancel.addActionListener(e -> dispose());
    }
}