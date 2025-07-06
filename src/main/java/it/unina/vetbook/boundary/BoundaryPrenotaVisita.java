package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BoundaryPrenotaVisita extends JDialog {

    private final DefaultTableModel model;
    private final JTable table;
    private AnimaleDTO animaleSelezionato = null;
    private boolean selezioneConfermata = false;

    public BoundaryPrenotaVisita(Window owner) {
        super(owner, "Seleziona un Animale", ModalityType.APPLICATION_MODAL);
        setSize(700, 400);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(VetcareStyle.GRAD_TOP);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Codice Chip", "Nome", "Tipo", "Razza"};
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
        JButton selezionaBtn = new JButton("Seleziona");
        JButton annullaBtn = new JButton("Annulla");

        selezionaBtn.setEnabled(false);
        southPanel.add(selezionaBtn);
        southPanel.add(annullaBtn);
        add(southPanel, BorderLayout.SOUTH);

        caricaDatiAnimali();

        table.getSelectionModel().addListSelectionListener(e -> {
            selezionaBtn.setEnabled(table.getSelectedRow() != -1);
        });

        annullaBtn.addActionListener(e -> dispose());

        selezionaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) return;

            long chip = (long) model.getValueAt(selectedRow, 0);
            String nome = (String) model.getValueAt(selectedRow, 1);
            String tipo = (String) model.getValueAt(selectedRow, 2);
            String razza = (String) model.getValueAt(selectedRow, 3);

            this.animaleSelezionato = new AnimaleDTO(chip, nome, tipo, razza, "", "");
            this.selezioneConfermata = true;
            dispose();
        });
    }

    private void caricaDatiAnimali() {
        model.setRowCount(0);
        Object[][] datiAnimali = ProprietarioController.getInstance().visualizzaAnimali();
        for (Object[] row : datiAnimali) {
            model.addRow(new Object[]{row[0], row[1], row[2], row[3]});
        }
    }

    public AnimaleDTO getAnimaleSelezionato() {
        return animaleSelezionato;
    }

    public boolean isSelezioneConfermata() {
        return selezioneConfermata;
    }
}