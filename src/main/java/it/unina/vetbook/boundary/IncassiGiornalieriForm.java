package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AdminController;
import it.unina.vetbook.dto.VisitaDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class IncassiGiornalieriForm extends JDialog {

    private final DefaultTableModel model;
    private final AdminController ctrl = AdminController.getInstance();

    public IncassiGiornalieriForm(Frame owner) {
        super(owner, "Incassi Giornalieri", true);
        setSize(600, 420);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12,12));

        String[] cols = {"Tipo Visita", "Descrizione", "Costo"};
        model = new DefaultTableModel(cols, 0);
        JTable table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Aggiorna");
        refresh.addActionListener(e -> caricaDati());

        JButton chiudi  = new JButton("Chiudi");
        chiudi.addActionListener(e -> dispose());

        JPanel south = new JPanel(); south.setOpaque(false);
        south.add(refresh); south.add(chiudi);
        add(south, BorderLayout.SOUTH);

        caricaDati();
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