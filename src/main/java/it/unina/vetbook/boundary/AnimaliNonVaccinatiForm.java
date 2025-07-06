package it.unina.vetbook.boundary;

import javax.swing.*;
import java.awt.*;

public class AnimaliNonVaccinatiForm extends JDialog {

    public AnimaliNonVaccinatiForm(Frame owner) {
        super(owner, "Animali Non Vaccinati", true);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());

        /* -------------- CONTROL (BCED) -----------------
           // CTRL. AnimaleController ctrl = new …();
           // CTRL. Object[][] rows = ctrl.nonVaccinati();
           ---------------------------------------------- */
        Object[][] rows = {                      // ← MOCK dati
                { 101, "Luna", "Gatto",   "-" },
                { 207, "Milo", "Cane",    "-" },
                { 315, "Kiwi", "Coniglio","-" }
        };
        String[] cols = { "ID", "Nome", "Specie", "Ult. vaccino" };

        JTable table = VetcareStyle.makeTable(rows, cols);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton chiudi = new JButton("Chiudi");
        chiudi.addActionListener(e -> dispose());
        JPanel south = new JPanel(); south.setOpaque(false); south.add(chiudi);
        add(south, BorderLayout.SOUTH);
    }
}
