package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AnimaliProprietarioBoundary extends JDialog {

    private final DefaultTableModel model;
    private final JTable table;
    private final JButton modificaBtn = new JButton("Modifica Animale");
    private final JButton cancellaBtn = new JButton("Cancella Animale");

    public AnimaliProprietarioBoundary(Window owner) {
        super(owner, "I Miei Animali", ModalityType.APPLICATION_MODAL);
        setSize(800, 500);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(VetcareStyle.GRAD_TOP);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Codice Chip", "Nome", "Tipo", "Razza", "Colore", "Data di Nascita"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = VetcareStyle.makeTable(new Object[0][0], columnNames);
        table.setModel(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        southPanel.setOpaque(false);
        JButton chiudiBtn = new JButton("Chiudi");

        modificaBtn.setEnabled(false);
        cancellaBtn.setEnabled(false);

        southPanel.add(modificaBtn);
        southPanel.add(cancellaBtn);
        southPanel.add(Box.createHorizontalStrut(20));
        southPanel.add(chiudiBtn);
        add(southPanel, BorderLayout.SOUTH);

        caricaDatiDalController();

        table.getSelectionModel().addListSelectionListener(e -> {
            boolean rowIsSelected = table.getSelectedRow() != -1;
            modificaBtn.setEnabled(rowIsSelected);
            cancellaBtn.setEnabled(rowIsSelected);
        });

        chiudiBtn.addActionListener(e -> dispose());

        cancellaBtn.addActionListener(e -> cancellaAnimaleSelezionato());

        modificaBtn.addActionListener(e -> modificaAnimaleSelezionato());
    }

    private void caricaDatiDalController() {
        model.setRowCount(0);
        Object[][] datiAnimali = ProprietarioController.getInstance().visualizzaAnimali();
        for (Object[] row : datiAnimali) {
            model.addRow(row);
        }
    }

    private void cancellaAnimaleSelezionato() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        String nomeAnimale = (String) model.getValueAt(selectedRow, 1);
        long chip = (long) model.getValueAt(selectedRow, 0);

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Sei sicuro di voler cancellare l'animale '" + nomeAnimale + "'?",
                "Conferma Cancellazione",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            ProprietarioController.getInstance().deleteAnimale(chip);
            caricaDatiDalController();
            JOptionPane.showMessageDialog(this, "Animale cancellato con successo.");
        }
    }

    private void modificaAnimaleSelezionato() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        long chip = (long) model.getValueAt(selectedRow, 0);
        String nome = (String) model.getValueAt(selectedRow, 1);
        String tipo = (String) model.getValueAt(selectedRow, 2);
        String razza = (String) model.getValueAt(selectedRow, 3);
        String colore = (String) model.getValueAt(selectedRow, 4);
        String dataNascita = model.getValueAt(selectedRow, 5).toString();

        AnimaleDTO animaleDaModificare = new AnimaleDTO(chip, nome, tipo, razza, colore, dataNascita);

        FormAnimale form = new FormAnimale(this, animaleDaModificare);
        form.setVisible(true);

        if (form.isSalvataggioRiuscito()) {
            caricaDatiDalController();
        }
    }
}