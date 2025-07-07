package it.unina.vetbook.boundary;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.dto.AgendaDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DisponibilitaForm extends JDialog {

    private final DefaultTableModel model;
    private final AgendaController ctrl = AgendaController.getInstance();

    public DisponibilitaForm(Frame owner) {
        super(owner, "Gestisci Agenda", true);
        setSize(620, 460);
        setLocationRelativeTo(owner);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12,12));

        JPanel pick = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        pick.setOpaque(false);

        DatePicker dp = VetcareStyle.makeDatePicker();
        TimePicker tp = VetcareStyle.makeHourPicker();
        JButton add  = new JButton("Inserisci Disponibilità");

        pick.add(new JLabel("Data:")); pick.add(dp);
        pick.add(new JLabel("Ora:"));  pick.add(tp);
        pick.add(add);
        add(pick, BorderLayout.NORTH);

        String[] cols = {"Tipo Evento", "Data", "Ora", "Descrizione"};
        model = new DefaultTableModel(cols, 0);
        JTable table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton close = new JButton("Chiudi");
        close.addActionListener(e -> dispose());
        JPanel south = new JPanel(); south.setOpaque(false); south.add(close);
        add(south, BorderLayout.SOUTH);

        caricaAgenda();

        add.addActionListener(e -> {
            LocalDate data = dp.getDate();
            LocalTime ora  = tp.getTime();
            if (data == null || ora == null) {
                JOptionPane.showMessageDialog(this,"Seleziona data e ora");
                return;
            }

            boolean ok = ctrl.inserisciDisponibilita(data, ora);
            if (ok) {
                caricaAgenda();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Esiste già una disponibilità per questa data e ora",
                        "Duplicato", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void caricaAgenda() {
        model.setRowCount(0);
        List<AgendaDTO> righe = ctrl.visualizzaAgenda();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

        for (AgendaDTO r : righe) {
            model.addRow(new Object[]{
                    r.getTipoEvento(),
                    r.getData().format(formatterData),
                    r.getOra().format(formatterOra),
                    r.getDescrizione()
            });
        }
    }
}