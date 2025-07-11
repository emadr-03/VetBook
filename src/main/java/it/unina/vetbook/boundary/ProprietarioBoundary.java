package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProprietarioBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";

    private JPanel northPanel;
    private JPanel centerBoxPanel;
    private JPanel southPanel;
    private JButton logoutButton;

    private final ProprietarioController pController;

    public ProprietarioBoundary(ProprietarioController pController) {
        super("Area Proprietario");
        this.pController = pController;

        initFrame();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        VetcareStyle.initLookAndFeel();
        setSize(750, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false);

        ImageIcon logo = new ImageIcon(
                new ImageIcon(RES + "logo_clinica.png")
                        .getImage().getScaledInstance(140,140,Image.SCALE_SMOOTH));
        JLabel headLabel = new JLabel(logo, SwingConstants.CENTER);
        headLabel.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));

        String nomeProprietario = pController.getProprietarioMock().getNome();
        JLabel welcomeLabel = new JLabel("Benvenuto, " + nomeProprietario, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        welcomeLabel.setForeground(VetcareStyle.TXT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        northPanel.add(headLabel, BorderLayout.NORTH);
        northPanel.add(welcomeLabel, BorderLayout.SOUTH);

        centerBoxPanel = new JPanel();
        centerBoxPanel.setLayout(new BoxLayout(centerBoxPanel, BoxLayout.Y_AXIS));
        centerBoxPanel.setOpaque(false);
        centerBoxPanel.setBorder(BorderFactory.createEmptyBorder(10, 200, 30, 200));

        southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));
        logoutButton = new JButton("Logout");
    }

    private void layoutComponents() {
        add(northPanel, BorderLayout.NORTH);
        add(centerBoxPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        centerBoxPanel.add(VetcareStyle.makeCard(
                "Gestisci Profilo",
                icon("user_profile_icon.png"),
                () -> {
                    new ProfiloProprietarioBoundary(pController).setVisible(true);
                    dispose();
                }
        ));
        centerBoxPanel.add(Box.createVerticalStrut(26));

        centerBoxPanel.add(VetcareStyle.makeCard(
                "Inserisci Nuovo Animale",
                icon("add_pet_icon.png"),
                () -> {
                    new FormAnimale(pController).setVisible(true);
                    dispose();
                }
        ));
        centerBoxPanel.add(Box.createVerticalStrut(26));

        centerBoxPanel.add(VetcareStyle.makeCard(
                "Visualizza i Tuoi Animali",
                icon("pets_icon.png"),
                () -> {
                    new AnimaliProprietarioBoundary(pController).setVisible(true);
                    dispose();
                }
        ));
        centerBoxPanel.add(Box.createVerticalStrut(26));

        centerBoxPanel.add(VetcareStyle.makeCard(
                "Prenota Visita",
                icon("book_visit.png"),
                () -> {
                    new BoundaryPrenotaVisita(pController).setVisible(true);
                    dispose();
                }
        ));

        southPanel.add(logoutButton);
    }

    private void addListeners() {
        logoutButton.addActionListener(e -> {
            dispose();
            new VisitorBoundary().setVisible(true);
        });
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