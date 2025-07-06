package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SelezioneDisponibilitaForm extends JDialog {

    private final DefaultTableModel model;
    private final JTable table;
    private LocalDate dataSelezionata = null;
    private LocalTime oraSelezionata = null;
    private boolean selezioneConfermata = false;

    public SelezioneDisponibilitaForm(Window owner) {
        super(owner, "Seleziona Disponibilità", ModalityType.APPLICATION_MODAL);
        setSize(500, 400);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(VetcareStyle.GRAD_TOP);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"Data", "Ora"};
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

        caricaDisponibilita();

        table.getSelectionModel().addListSelectionListener(e -> {
            selezionaBtn.setEnabled(table.getSelectedRow() != -1);
        });

        annullaBtn.addActionListener(e -> dispose());

        selezionaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) return;

            this.dataSelezionata = (LocalDate) model.getValueAt(selectedRow, 0);
            this.oraSelezionata = (LocalTime) model.getValueAt(selectedRow, 1);
            this.selezioneConfermata = true;
            dispose();
        });
    }

    private void caricaDisponibilita() {
        model.setRowCount(0);
        Object[][] slots = AgendaController.getInstance().visualizzaDisponibilita();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Object[] slot : slots) {
            LocalDate data = (LocalDate) slot[0];
            LocalTime ora = (LocalTime) slot[1];
            model.addRow(new Object[]{data, ora, data.format(dateFormatter), ora.format(timeFormatter)});
        }

        table.removeColumn(table.getColumnModel().getColumn(0));
        table.removeColumn(table.getColumnModel().getColumn(0));
    }

    public LocalDate getDataSelezionata() {
        return dataSelezionata;
    }

    public LocalTime getOraSelezionata() {
        return oraSelezionata;
    }

    public boolean isSelezioneConfermata() {
        return selezioneConfermata;
    }
}