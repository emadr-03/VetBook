package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AdminController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class IncassiGiornalieriForm extends JDialog {

    private final DefaultTableModel model =
            new DefaultTableModel(new String[]{"Data","Visite","Totale"}, 0);
    private final AdminController ctrl = AdminController.getInstance();

    public IncassiGiornalieriForm(Frame owner) {
        super(owner, "Incassi Giornalieri", true);
        setSize(600, 420);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12,12));

        JTable table = VetcareStyle.makeTable(new Object[0][0],
                new String[]{"Data","Visite","Totale"});
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        /* pulsanti ------------------------------------------------------ */
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
        Object[][] rows = ctrl.visualizzaIncassiGiornalieri();
        for (Object[] r : rows) model.addRow(r);
    }
}
