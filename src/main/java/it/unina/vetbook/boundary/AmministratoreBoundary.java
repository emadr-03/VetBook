package it.unina.vetbook.boundary;

import javax.swing.*;
import java.awt.*;

public class AmministratoreBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";

    public AmministratoreBoundary() {
        super("Area Amministratore");
        VetcareStyle.initLookAndFeel();

        setSize(750, 520);
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

        /* card-menu --------------------------------------------------------- */
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(10, 200, 30, 200));

        box.add(VetcareStyle.makeCard(
                "Inserisci Disponibilità",
                icon("calendar_icon.png"),
                () -> new DisponibilitaForm(this).setVisible(true)
        ));
        box.add(Box.createVerticalStrut(26));

        box.add(VetcareStyle.makeCard(
                "Incassi Giornalieri",
                icon("health-report.png"),
                () -> new IncassiGiornalieriForm(this).setVisible(true)
        ));
        box.add(Box.createVerticalStrut(26));

        box.add(VetcareStyle.makeCard(
                "Animali Non Vaccinati",
                icon("medicine.png"),
                () -> new AnimaliNonVaccinatiForm(this).setVisible(true)
        ));
        add(box, BorderLayout.CENTER);
    }

    private ImageIcon icon(String file) {
        return new ImageIcon(
                new ImageIcon(RES + file).getImage()
                        .getScaledInstance(32,32,Image.SCALE_SMOOTH));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AmministratoreBoundary().setVisible(true));
    }
}
