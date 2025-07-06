package it.unina.vetbook.boundary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AnimaliProprietarioBoundary extends JFrame {

    private final DefaultTableModel model;
    private final JTable table;

    public AnimaliProprietarioBoundary() {
        super("I Tuoi Animali");
        VetcareStyle.initLookAndFeel();

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        String[] cols = {"Codice Chip", "Nome", "Tipo", "Razza", "Colore", "Data Nascita"};
        Object[][] data = {
                {"1234567890", "Fido", "Cane", "Golden Retriever", "Biondo", "10/05/2020"},
                {"0987654321", "Micia", "Gatto", "Siamese", "Crema", "15/08/2021"}
        };
        model = new DefaultTableModel(data, cols);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton modificaBtn = new JButton("Modifica Selezionato");
        JButton eliminaBtn = new JButton("Elimina Selezionato");
        JButton indietroBtn = new JButton("Indietro");

        buttonPanel.add(modificaBtn);
        buttonPanel.add(eliminaBtn);
        buttonPanel.add(indietroBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        modificaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un animale da modificare.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String codiceChip = model.getValueAt(selectedRow, 0).toString();
            String nome = model.getValueAt(selectedRow, 1).toString();
            String tipo = model.getValueAt(selectedRow, 2).toString();
            String razza = model.getValueAt(selectedRow, 3).toString();
            String colore = model.getValueAt(selectedRow, 4).toString();
            LocalDate dataNascita = LocalDate.parse(model.getValueAt(selectedRow, 5).toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            new FormAnimale(codiceChip, nome, tipo, razza, colore, dataNascita).setVisible(true);
            dispose();
        });

        eliminaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un animale da eliminare.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare l'animale selezionato?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Animale eliminato con successo! (MOCK)");
            }
        });

        indietroBtn.addActionListener(e -> {
            new ProprietarioBoundary().setVisible(true);
            dispose();
        });
    }
}