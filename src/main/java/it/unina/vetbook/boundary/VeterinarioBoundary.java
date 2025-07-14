package it.unina.vetbook.boundary;

import it.unina.vetbook.control.VeterinarioController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class VeterinarioBoundary extends JFrame {

    private static final String RES = "/img/";
    private JButton logoutButton;
    private final VeterinarioController veterinarioController;

    public VeterinarioBoundary(VeterinarioController veterinarioController) {
        super("Area Veterinario");
        this.veterinarioController = veterinarioController;

        initFrame();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        VetcareStyle.initLookAndFeel();
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        logoutButton = new JButton("Logout");
    }

    private void layoutComponents() {
        ImageIcon logo = new ImageIcon(
                new ImageIcon(Objects.requireNonNull(getClass().getResource(RES + "logo_clinica.png")))
                        .getImage().getScaledInstance(140,140,Image.SCALE_SMOOTH));
        JLabel head = new JLabel(logo, SwingConstants.CENTER);
        head.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        add(head, BorderLayout.NORTH);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(10, 200, 30, 200));

        box.add(VetcareStyle.makeCard(
                "Visualizza Prenotazioni Giornaliere",
                icon("calendar_icon.png"),
                () -> {
                    new PrenotazioniBoundary(veterinarioController).setVisible(true);
                    dispose();
                }
        ));
        box.add(Box.createVerticalStrut(26));

        box.add(VetcareStyle.makeCard(
                "Registra Visita",
                icon("health-report.png"),
                () -> new FormDialogVisita(this, veterinarioController).setVisible(true)
        ));
        add(box, BorderLayout.CENTER);

        JPanel south = new JPanel();
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));
        south.add(logoutButton);
        add(south, BorderLayout.SOUTH);
    }

    private void addListeners() {
        logoutButton.addActionListener(e -> {
            dispose();
            new VisitorBoundary().setVisible(true);
        });
    }

    private ImageIcon icon(String file) {
        try {
            return new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(RES + file))).getImage()
                    .getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            return new ImageIcon(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
        }
    }
}