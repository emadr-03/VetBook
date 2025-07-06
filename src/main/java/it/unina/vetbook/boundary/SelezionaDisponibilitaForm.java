package it.unina.vetbook.boundary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SelezionaDisponibilitaForm extends JFrame {

    private final JTable table;
    private final DefaultTableModel model;

    public SelezionaDisponibilitaForm(String nomeAnimale, String tipoAnimale) {
        super("Prenota Visita - Seleziona Disponibilità");
        VetcareStyle.initLookAndFeel();

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        String infoAnimale = String.format("Stai prenotando per: %s (%s)", nomeAnimale, tipoAnimale);
        add(new JLabel(infoAnimale, SwingConstants.CENTER), BorderLayout.NORTH);

        String[] cols = {"Data", "Ora"};
        Object[][] data = {
                {"20/07/2025", "09:00"},
                {"20/07/2025", "10:00"},
                {"21/07/2025", "11:00"}
        };
        model = new DefaultTableModel(data, cols);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton selezionaDispBtn = new JButton("Conferma Prenotazione");
        JButton annullaBtn = new JButton("Annulla");
        buttonPanel.add(selezionaDispBtn);
        buttonPanel.add(annullaBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        selezionaDispBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una data e un'ora disponibili.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String dataSelezionata = model.getValueAt(selectedRow, 0).toString();
            String oraSelezionata = model.getValueAt(selectedRow, 1).toString();

            JOptionPane.showMessageDialog(this, "Prenotazione confermata per " + nomeAnimale + " il " + dataSelezionata + " alle " + oraSelezionata, "Successo", JOptionPane.INFORMATION_MESSAGE);

            new ProprietarioBoundary().setVisible(true);
            dispose();
        });

        annullaBtn.addActionListener(e -> {
            new BoundaryPrenotaVisita().setVisible(true);
            dispose();
        });
    }
}