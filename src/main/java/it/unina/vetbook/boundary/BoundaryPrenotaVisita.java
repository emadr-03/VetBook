package it.unina.vetbook.boundary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BoundaryPrenotaVisita extends JFrame {

    private final DefaultTableModel model;
    private final JTable table;

    public BoundaryPrenotaVisita() {
        super("Prenota Visita - Seleziona Animale");
        VetcareStyle.initLookAndFeel();

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        add(new JLabel("Seleziona l'animale per cui prenotare la visita:", SwingConstants.CENTER), BorderLayout.NORTH);

        String[] cols = {"Codice Chip", "Nome", "Tipo", "Razza"};
        Object[][] data = {
                {"1234567890", "Fido", "Cane", "Golden Retriever"},
                {"0987654321", "Micia", "Gatto", "Siamese"}
        };
        model = new DefaultTableModel(data, cols);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton selezionaBtn = new JButton("Seleziona Animale");
        JButton annullaBtn = new JButton("Annulla");

        buttonPanel.add(selezionaBtn);
        buttonPanel.add(annullaBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        selezionaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un animale dalla tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nomeAnimale = model.getValueAt(selectedRow, 1).toString();
            String tipoAnimale = model.getValueAt(selectedRow, 2).toString();

            new SelezionaDisponibilitaForm(nomeAnimale, tipoAnimale).setVisible(true);
            dispose();
        });

        annullaBtn.addActionListener(e -> {
            new ProprietarioBoundary().setVisible(true);
            dispose();
        });
    }
}