package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AdminController;
import it.unina.vetbook.dto.VisitaDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IncassiGiornalieriForm extends JDialog {

    private DefaultTableModel model;
    private JTable table;
    private JButton refreshButton;
    private JButton closeButton;

    private final AdminController ctrl = AdminController.getInstance();

    public IncassiGiornalieriForm(Frame owner) {
        super(owner, "Incassi Giornalieri", true);

        initDialog();
        initComponents();
        layoutComponents();
        addListeners();

        caricaDati();
    }

    private void initDialog() {
        setSize(600, 420);
        setLocationRelativeTo(getOwner());
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12, 12));
    }

    private void initComponents() {
        String[] cols = {"Tipo Visita", "Descrizione", "Costo"};
        model = new DefaultTableModel(cols, 0);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);

        refreshButton = new JButton("Aggiorna");
        closeButton = new JButton("Chiudi");
    }

    private void layoutComponents() {
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(refreshButton);
        southPanel.add(closeButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        refreshButton.addActionListener(e -> caricaDati());
        closeButton.addActionListener(e -> dispose());
    }

    private void caricaDati() {
        model.setRowCount(0);

        List<VisitaDTO> visite = ctrl.getVisiteGiornaliere();
        double totale = ctrl.getTotaleIncassoGiornaliero();

        for (VisitaDTO v : visite) {
            model.addRow(new Object[]{
                    v.getTipo().toString(),
                    v.getDescrizione(),
                    String.format("%.2f €", v.getCosto())
            });
        }

        model.addRow(new Object[]{"", "TOTALE", String.format("%.2f €", totale)});
    }
}