package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AdminController;

import javax.swing.*;
import java.awt.*;

public class AnimaliNonVaccinatiForm extends JDialog {

    private final String[] COLS = { "ID", "Nome", "Specie", "Ult. vaccino" };

    public AnimaliNonVaccinatiForm(Frame owner) {
        super(owner, "Animali Non Vaccinati", true);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12,12));

        Object[][] rows;
        try {
            AdminController ctrl = AdminController.getInstance();
            rows = ctrl.visualizzaAnimaliNonVaccinati();
        } catch (UnsupportedOperationException ex) {
            JOptionPane.showMessageDialog(this,
                    "Funzionalità non ancora implementata,\n"
                            + "verranno mostrati dati di esempio.",
                    "Non disponibile", JOptionPane.INFORMATION_MESSAGE);

            // Mock
            rows = new Object[][]{
                    { 101, "Luna", "Gatto",   "27/04/2025" },
                    { 207, "Milo", "Cane",    "27/04/2023" },
                    { 315, "Kiwi", "Coniglio","25/04/2022" },
            };
        }

        JTable table = VetcareStyle.makeTable(rows, COLS);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton chiudi = new JButton("Chiudi");
        chiudi.addActionListener(e -> dispose());
        JPanel south = new JPanel(); south.setOpaque(false); south.add(chiudi);
        add(south, BorderLayout.SOUTH);
    }
}
