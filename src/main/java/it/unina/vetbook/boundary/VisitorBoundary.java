package it.unina.vetbook.boundary;

import it.unina.vetbook.entity.UserRole;

import javax.swing.*;
import java.awt.*;

public class VisitorBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton registerButton;

    public VisitorBoundary() {
        super("Benvenuto");

        initFrame();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        VetcareStyle.initLookAndFeel();
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        userField = new JTextField(18);
        passField = new JPasswordField(18);
        loginButton = new JButton("Login");
        registerButton = new JButton("Registrati");
    }

    private void layoutComponents() {
        ImageIcon logo = new ImageIcon(
                new ImageIcon(RES + "logo_clinica.png")
                        .getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH));
        JLabel head = new JLabel(logo, SwingConstants.CENTER);
        head.setBorder(BorderFactory.createEmptyBorder(30,0,10,0));
        add(head, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.anchor = GridBagConstraints.EAST;

        Dimension fieldSize = new Dimension(180,28);
        userField.setPreferredSize(fieldSize);
        passField.setPreferredSize(fieldSize);

        c.gridx = 0; c.gridy = 0; centerPanel.add(new JLabel("Username:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.WEST; centerPanel.add(userField, c);
        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.EAST; centerPanel.add(new JLabel("Password:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.WEST; centerPanel.add(passField, c);
        add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(loginButton);
        southPanel.add(registerButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> new RegisterDialog(this).setVisible(true));
    }

    private void handleLogin() {
        String u = userField.getText();
        String p = String.valueOf(passField.getPassword());

        UserRole role = UserRole.PROPRIETARIO;

        JOptionPane.showMessageDialog(this, "Login OK come " + role);

        switch (role) {
            case PROPRIETARIO: {
                new ProprietarioBoundary().setVisible(true);
                break;
            }
            case VETERINARIO: {
                new VeterinarioBoundary().setVisible(true);
                break;
            }
            case AMMINISTRATORE_DELL_AMBULATORIO: {
                new AmministratoreBoundary().setVisible(true);
                break;
            }
        }
        dispose();
    }
}