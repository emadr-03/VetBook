package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrenotazioniBoundary extends JFrame {

    public PrenotazioniBoundary() {
        super("Elenco Prenotazioni");
        VetcareStyle.initLookAndFeel();

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        String[] cols = {"Data", "Ora", "Dettaglio Animale"};
        Object[][] data = AgendaController.getInstance().visualizzaPrenotazioni();

        DefaultTableModel model = new DefaultTableModel(data, cols);
        JTable table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton indietroBtn = new JButton("Indietro");
        buttonPanel.add(indietroBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        indietroBtn.addActionListener(e -> {
            new VeterinarioBoundary().setVisible(true);
            dispose();
        });
    }
}