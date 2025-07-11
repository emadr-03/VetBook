package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AdminController;

import javax.swing.*;
import java.awt.*;

/**
 * Rappresenta la finestra principale per l'attore Amministratore.
 * Da qui, l'amministratore può accedere alle diverse funzionalità del sistema
 * a lui dedicate, come la gestione delle disponibilità, la visualizzazione
 * degli incassi e il report degli animali non vaccinati.
 */
public class AmministratoreBoundary extends JFrame {

    private static final String RES = "src/main/resources/img/";

    private JLabel head;
    private JPanel box;
    private JComponent cardDisponibilita, cardIncassi, cardAnimali;
    private final AdminController adminController;

    public AmministratoreBoundary(AdminController adminController) {
        super("Area Amministratore");
        this.adminController = adminController;
        VetcareStyle.initLookAndFeel();

        initFrame();
        initComponents();
        layoutComponents();
    }

    private void initFrame() {
        setSize(750, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        // Inizializzazione header con logo
        ImageIcon logo = new ImageIcon(
                new ImageIcon(RES + "logo_clinica.png")
                        .getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH));
        head = new JLabel(logo, SwingConstants.CENTER);
        head.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Inizializzazione pannello centrale
        box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(10, 200, 30, 200));

        // Inizializzazione delle card-menu
        cardDisponibilita = VetcareStyle.makeCard(
                "Inserisci Disponibilità",
                createIcon("calendar_icon.png"),
                () -> new DisponibilitaForm(this).setVisible(true)
        );

        cardIncassi = VetcareStyle.makeCard(
                "Incassi Giornalieri",
                createIcon("health-report.png"),
                () -> new IncassiGiornalieriForm(this, adminController).setVisible(true)
        );

        cardAnimali = VetcareStyle.makeCard(
                "Animali Non Vaccinati",
                createIcon("medicine.png"),
                () -> new AnimaliNonVaccinatiForm(this, adminController).setVisible(true)
        );
    }

    private void layoutComponents() {
        add(head, BorderLayout.NORTH);

        box.add(cardDisponibilita);
        box.add(Box.createVerticalStrut(26));
        box.add(cardIncassi);
        box.add(Box.createVerticalStrut(26));
        box.add(cardAnimali);

        add(box, BorderLayout.CENTER);
    }

    private ImageIcon createIcon(String file) {
        return new ImageIcon(
                new ImageIcon(RES + file).getImage()
                        .getScaledInstance(32, 32, Image.SCALE_SMOOTH));
    }
}