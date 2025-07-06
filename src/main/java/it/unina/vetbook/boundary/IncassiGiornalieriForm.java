package it.unina.vetbook.boundary;

import javax.swing.*;
import java.awt.*;

public class IncassiGiornalieriForm extends JDialog {

    public IncassiGiornalieriForm(Frame owner) {
        super(owner, "Incassi Giornalieri", true);
        setSize(600, 400);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());

        /* ---------------- CONTROL (BCED) ------------------
           // CTRL. IncassoController ctrl = new …();
           // CTRL. Object[][] rows = ctrl.elencoIncassi();
           ------------------------------------------------- */
        Object[][] rows = {                        // ← MOCK dati
                { "05-07-2025", 12, "480,00 €" },
                { "04-07-2025",  9, "355,00 €" },
                { "03-07-2025", 15, "610,00 €" }
        };
        String[] cols = { "Data", "Visite", "Totale" };

        JTable table = VetcareStyle.makeTable(rows, cols);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton chiudi = new JButton("Chiudi");
        chiudi.addActionListener(e -> dispose());
        JPanel south = new JPanel(); south.setOpaque(false); south.add(chiudi);
        add(south, BorderLayout.SOUTH);
    }
}
