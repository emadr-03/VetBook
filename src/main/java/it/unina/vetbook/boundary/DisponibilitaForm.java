package it.unina.vetbook.boundary;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class DisponibilitaForm extends JDialog {

    private final DefaultTableModel model =
            new DefaultTableModel(new String[]{"Data","Ora"}, 0);
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public DisponibilitaForm(Frame owner) {
        super(owner, "Inserisci disponibilità", true);
        setSize(620, 460);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12,12));

        /* --- picker panel ------------------------------------------------ */
        JPanel pick = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pick.setOpaque(false);

        DatePicker dp = VetcareStyle.makeDatePicker();
        TimePicker tp = VetcareStyle.makeHourPicker();

        JButton add = new JButton("Inserisci");

        pick.add(new JLabel("Data:")); pick.add(dp);
        pick.add(new JLabel("Ora:"));  pick.add(tp);
        pick.add(add);
        add(pick, BorderLayout.NORTH);

        /* --- tabella disponibilità -------------------------------------- */
        JTable table = VetcareStyle.makeTable(new Object[0][0], new String[]{"Data","Ora"});
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        /* --- pulsante chiudi -------------------------------------------- */
        JButton close = new JButton("Chiudi");
        close.addActionListener(e -> dispose());
        JPanel south = new JPanel(); south.setOpaque(false); south.add(close);
        add(south, BorderLayout.SOUTH);

        /* --- azione inserimento ----------------------------------------- */
        add.addActionListener(e -> {
            if (dp.getDate() == null || tp.getTime() == null) {
                JOptionPane.showMessageDialog(this,"Seleziona data e ora");
                return;
            }
            String data = df.format(dp.getDate());
            String ora  = tp.getTime().toString();

            /* ---------- CONTROL (BCED) --------------
               // DisponibilitaController ctrl = new …();
               // boolean ok = ctrl.insert(dp.getDate(), tp.getTime());
               ---------------------------------------- */
            boolean ok = true;           // ← MOCK

            if (ok)  model.addRow(new Object[]{ data, ora });
            else     JOptionPane.showMessageDialog(this,"Errore salvataggio");
        });
    }
}
