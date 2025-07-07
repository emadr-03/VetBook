package it.unina.vetbook.boundary;

import it.unina.vetbook.entity.Proprietario;
import it.unina.vetbook.entity.UserRole;

import javax.swing.*;
import java.awt.*;

public class VisitorBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";

    public VisitorBoundary() {
        super("Benvenuto");
        VetcareStyle.initLookAndFeel();

        /* finestra -------------------------------------------------------- */
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());

        /* logo ------------------------------------------------------------ */
        ImageIcon logo = new ImageIcon(
                new ImageIcon(RES + "logo_clinica.png")
                        .getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH));
        JLabel head = new JLabel(logo, SwingConstants.CENTER);
        head.setBorder(BorderFactory.createEmptyBorder(30,0,10,0));
        add(head, BorderLayout.NORTH);

        /* pannello login -------------------------------------------------- */
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.anchor = GridBagConstraints.EAST;

        JTextField user = new JTextField(18);
        JPasswordField pass = new JPasswordField(18);
        Dimension size = new Dimension(180,28);
        user.setPreferredSize(size);  pass.setPreferredSize(size);

        c.gridx = 0; c.gridy = 0; center.add(new JLabel("Username:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.WEST; center.add(user, c);
        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.EAST;
        center.add(new JLabel("Password:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.WEST; center.add(pass, c);

        add(center, BorderLayout.CENTER);

        /* pulsanti -------------------------------------------------------- */
        JButton login = new JButton("Login");
        JButton reg   = new JButton("Registrati");

        login.addActionListener(e -> {
            String u = user.getText();
            String p = String.valueOf(pass.getPassword());

            /* --------- CONTROL (BCED) -------------------------------
               // AuthController ctrl = new AuthController();
               // UserRole role = ctrl.login(u, p);   // se fallisce, lancia eccezione
               ------------------------------------------------------- */

            UserRole role = UserRole.PROPRIETARIO;          // ← MOCK risposta

            JOptionPane.showMessageDialog(this, "Login OK come " + role);
            // switch di instradamento
            switch (role) {
                case PROPRIETARIO: {
                    new ProprietarioBoundary().setVisible(true);
                    break;
                }
                case VETERINARIO: {
                    new VeterinarioBoundary().setVisible(true);
                    System.out.println("Apri VeterinarioBoundary");
                    break;
                }
                case AMMINISTRATORE_DELL_AMBULATORIO: {
                    new AmministratoreBoundary().setVisible(true);
                    break;
                }
            }
            dispose();
        });

        reg.addActionListener(e -> new RegisterDialog(this).setVisible(true));

        JPanel south = new JPanel(); south.setOpaque(false);
        south.add(login); south.add(reg);
        add(south, BorderLayout.SOUTH);
    }
}
