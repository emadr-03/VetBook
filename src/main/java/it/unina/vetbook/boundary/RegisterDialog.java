package it.unina.vetbook.boundary;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    public RegisterDialog(Frame owner) {
        super(owner, "Registrazione proprietario", true);
        setSize(480, 420);
        setLocationRelativeTo(owner);

        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());                   // centriamo la card

        JPanel card = VetcareStyle.makeDialogCard();      // ← card bianca
        add(card);                                        // GridBag defaults OK

        /* layout campi dentro la card ------------------------------------------------ */
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12,20,12,20);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.CENTER;

        JTextField email = VetcareStyle.textField("E-mail");
        JTextField user  = VetcareStyle.textField("Username");
        JPasswordField p1= VetcareStyle.passwordField("Password");
        JPasswordField p2= VetcareStyle.passwordField("Ripeti password");

        card.add(email, c);   c.gridy++;
        card.add(user , c);   c.gridy++;
        card.add(p1   , c);   c.gridy++;
        card.add(p2   , c);   c.gridy++;

        /* pulsanti -------------------------------------------------------- */
        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        JButton ok = new JButton("Registrati");
        JButton cancel = new JButton("Annulla");
        btnBox.setOpaque(false);
        btnBox.add(ok); btnBox.add(cancel);
        card.add(btnBox, c);

        /* azioni ---------------------------------------------------------- */
        ok.addActionListener(e -> {
            if (!String.valueOf(p1.getPassword())
                    .equals(String.valueOf(p2.getPassword()))) {
                JOptionPane.showMessageDialog(this,"Le password non coincidono");
                return;
            }
            /* -------- CONTROL -------------------------------------------
               // AuthController ctrl = new AuthController();
               // boolean done = ctrl.register(email.getText(),
               //       user.getText(), String.valueOf(p1.getPassword()),
               //       UserRole.PROPRIETARIO);
               ------------------------------------------------------------ */
            boolean done = true; // MOCK
            JOptionPane.showMessageDialog(this,
                    done?"Registrazione ok":"Errore registrazione");
            if(done) dispose();
        });
        cancel.addActionListener(e -> dispose());
    }
}
