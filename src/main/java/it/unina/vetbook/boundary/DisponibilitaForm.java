package it.unina.vetbook.boundary;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import it.unina.vetbook.control.AgendaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DisponibilitaForm extends JDialog {

    private final DefaultTableModel model =
            new DefaultTableModel(new String[]{"Data","Ora"}, 0);
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final AgendaController ctrl = AgendaController.getInstance();

    public DisponibilitaForm(Frame owner) {
        super(owner, "Inserisci disponibilità", true);
        setSize(620, 460);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12,12));

        /* ---------------- pickers -------------------------------------- */
        JPanel pick = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pick.setOpaque(false);

        DatePicker dp = VetcareStyle.makeDatePicker();
        TimePicker tp = VetcareStyle.makeHourPicker();
        JButton add  = new JButton("Inserisci");

        pick.add(new JLabel("Data:")); pick.add(dp);
        pick.add(new JLabel("Ora:"));  pick.add(tp);
        pick.add(add);
        add(pick, BorderLayout.NORTH);

        /* ---------------- tabella -------------------------------------- */
        JTable table = VetcareStyle.makeTable(new Object[0][0], new String[]{"Data","Ora"});
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        /* ---------------- chiudi --------------------------------------- */
        JButton close = new JButton("Chiudi");
        close.addActionListener(e -> dispose());
        JPanel south = new JPanel(); south.setOpaque(false); south.add(close);
        add(south, BorderLayout.SOUTH);

        /* ---------------- dati iniziali -------------------------------- */
        caricaDisponibilita();

        /* ---------------- inserimento ---------------------------------- */
        add.addActionListener(e -> {
            LocalDate data = dp.getDate();
            LocalTime ora  = tp.getTime();
            if (data == null || ora == null) {
                JOptionPane.showMessageDialog(this,"Seleziona data e ora");
                return;
            }

            try {
                boolean ok = ctrl.inserisciDisponibilita(data, ora);
                if (ok) {
                    caricaDisponibilita();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Esiste già una disponibilità per "
                                    + df.format(data) + " alle " + ora,
                            "Duplicato", JOptionPane.WARNING_MESSAGE);
                }
            } catch (UnsupportedOperationException unsupportedOperationException){
                JOptionPane.showMessageDialog(this,"Operazione non ancora supportata");
            }
        });
    }

    private void caricaDisponibilita() {
        model.setRowCount(0);
        Object[][] rows = ctrl.visualizzaAgenda();
        for (Object[] r : rows) model.addRow(r);
    }
}
