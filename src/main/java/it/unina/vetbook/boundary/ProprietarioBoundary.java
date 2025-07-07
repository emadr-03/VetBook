package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProprietarioBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";
    private final ProprietarioController ctrl = ProprietarioController.getInstance();

    public ProprietarioBoundary() {
        super("Area Proprietario");
        VetcareStyle.initLookAndFeel();

        setSize(750, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());

        /* logo -------------------------------------------------------------- */
        ImageIcon logo = new ImageIcon(
                new ImageIcon(RES + "logo_clinica.png")
                        .getImage().getScaledInstance(140,140,Image.SCALE_SMOOTH));
        JLabel head = new JLabel(logo, SwingConstants.CENTER);
        head.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        add(head, BorderLayout.NORTH);

        String nomeProprietario = "Mario Rossi";
        JLabel welcomeLabel = new JLabel("Benvenuto, " + nomeProprietario, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        welcomeLabel.setForeground(VetcareStyle.TXT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);

        north.add(head, BorderLayout.NORTH);
        north.add(welcomeLabel, BorderLayout.SOUTH);

        add(north, BorderLayout.NORTH);

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(10, 200, 30, 200));

        box.add(VetcareStyle.makeCard(
                "Gestisci Profilo",
                icon("user_profile_icon.png"),
                () -> {
                    new ProfiloProprietarioBoundary().setVisible(true);
                    dispose();
                }
        ));
        box.add(Box.createVerticalStrut(26));

        box.add(VetcareStyle.makeCard(
                "Inserisci Nuovo Animale",
                icon("add_pet_icon.png"),
                () -> {
                    new FormAnimale().setVisible(true);
                    dispose();
                }
        ));
        box.add(Box.createVerticalStrut(26));

        box.add(VetcareStyle.makeCard(
                "Visualizza i Tuoi Animali",
                icon("pets_icon.png"),
                () -> {
                    new AnimaliProprietarioBoundary().setVisible(true);
                    dispose();
                }
        ));
        box.add(Box.createVerticalStrut(26));

        box.add(VetcareStyle.makeCard(
                "Prenota Visita",
                icon("book_visit.png"),
                () -> {
                    new BoundaryPrenotaVisita().setVisible(true);
                    dispose();
                }
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