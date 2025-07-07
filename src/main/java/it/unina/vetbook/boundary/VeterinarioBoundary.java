package it.unina.vetbook.boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VeterinarioBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";

    public VeterinarioBoundary() {
        super("Area Veterinario");
        VetcareStyle.initLookAndFeel();

        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon(
                new ImageIcon(RES + "logo_clinica.png")
                        .getImage().getScaledInstance(140,140,Image.SCALE_SMOOTH));
        JLabel head = new JLabel(logo, SwingConstants.CENTER);
        head.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        add(head, BorderLayout.NORTH);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(10, 200, 30, 200));

        box.add(VetcareStyle.makeCard(
                "Visualizza Prenotazioni",
                icon("calendar_icon.png"),
                () -> {
                    new PrenotazioniBoundary().setVisible(true);
                    dispose();
                }
        ));
        box.add(Box.createVerticalStrut(26));

        box.add(VetcareStyle.makeCard(
                "Registra Visita",
                icon("health-report.png"),
                () -> new FormDialogVisita(this).setVisible(true)
        ));
        add(box, BorderLayout.CENTER);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            dispose();
            new VisitorBoundary().setVisible(true);
        });
        JPanel south = new JPanel();
        south.setOpaque(false);
        south.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));
        south.add(logout);
        add(south, BorderLayout.SOUTH);
    }

    private ImageIcon icon(String file) {
        try {
            return new ImageIcon(new ImageIcon(RES + file).getImage()
                    .getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            return new ImageIcon(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB));
        }
    }
}