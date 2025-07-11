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

    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel northPanel, southPanel;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private JButton addButton, closeButton;

    private final AgendaController ctrl = AgendaController.getInstance();

    public DisponibilitaForm(Frame owner) {
        super(owner, "Gestisci Agenda", true);

        initDialog();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initDialog() {
        setSize(620, 460);
        setLocationRelativeTo(getOwner());
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12, 12));
    }

    private void initComponents() {
        initNorthPanel();
        initTable();
        initSouthPanel();
    }

    private void initNorthPanel() {
        northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        northPanel.setOpaque(false);

        datePicker = VetcareStyle.makeDatePicker();
        timePicker = VetcareStyle.makeHourPicker();
        addButton = new JButton("Inserisci Disponibilità");

        northPanel.add(new JLabel("Data:"));
        northPanel.add(datePicker);
        northPanel.add(new JLabel("Ora:"));
        northPanel.add(timePicker);
        northPanel.add(addButton);
    }

    private void initTable() {
        String[] cols = {"Tipo Evento", "Data", "Ora", "Descrizione"};
        model = new DefaultTableModel(cols, 0);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        scrollPane = new JScrollPane(table);
        caricaAgenda();
    }

    private void initSouthPanel() {
        southPanel = new JPanel();
        southPanel.setOpaque(false);
        closeButton = new JButton("Chiudi");
        southPanel.add(closeButton);
    }

    private void layoutComponents() {
        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        addButton.addActionListener(e -> {
            LocalDate data = datePicker.getDate();
            LocalTime ora = timePicker.getTime();
            if (data == null || ora == null) {
                JOptionPane.showMessageDialog(this, "Seleziona data e ora");
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

        closeButton.addActionListener(e -> dispose());
    }

    private void caricaAgenda() {
        model.setRowCount(0);
        List<AgendaDTO> righe = ctrl.visualizzaAgenda();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

        for (AgendaDTO r : righe) {
            model.addRow(new Object[]{
                    r.tipoEvento(),
                    r.data().format(formatterData),
                    r.ora().format(formatterOra),
                    r.descrizione()
            });
        }
    }
}