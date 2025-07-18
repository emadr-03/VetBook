package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.DisponibilitaDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SelezionaDisponibilitaForm extends JFrame {

    private final AnimaleDomesticoDTO animaleSelezionato;
    private final List<DisponibilitaDTO> disponibilita;

    private JTable table;
    private DefaultTableModel model;
    private JButton selezionaDispBtn, annullaBtn;

    private final ProprietarioController proprietarioController;
    private final AgendaController agendaController;

    public SelezionaDisponibilitaForm(AnimaleDomesticoDTO a, ProprietarioController proprietarioController) {
        super("Prenota Visita - Seleziona Disponibilità");
        this.animaleSelezionato = a;
        this.proprietarioController = proprietarioController;
        this.agendaController = AgendaController.getInstance();
        this.disponibilita = agendaController.visualizzaDisponibilita();

        initFrame();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        VetcareStyle.initLookAndFeel();
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    }

    private void initComponents() {
        String[] cols = {"Data", "Ora"};
        Object[][] data = formatDataForTable();
        model = new DefaultTableModel(data, cols);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);

        selezionaDispBtn = new JButton("Conferma Prenotazione");
        annullaBtn = new JButton("Annulla");
    }

    private void layoutComponents() {
        String infoAnimale = String.format("Stai prenotando per: %s (%s)", animaleSelezionato.nome(), animaleSelezionato.tipo());
        add(new JLabel(infoAnimale, SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(selezionaDispBtn);
        buttonPanel.add(annullaBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        selezionaDispBtn.addActionListener(e -> handleConferma());
        annullaBtn.addActionListener(e -> handleAnnulla());
    }

    private void handleConferma() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona una data e un'ora disponibili.", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DisponibilitaDTO slotSelezionato = disponibilita.get(selectedRow);

        try {
            proprietarioController.effettuaPrenotazione(animaleSelezionato, slotSelezionato);
            JOptionPane.showMessageDialog(this, "Prenotazione confermata!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            new ProprietarioBoundary(proprietarioController).setVisible(true);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleAnnulla() {
        new BoundaryPrenotaVisita(proprietarioController).setVisible(true);
        dispose();
    }

    private Object[][] formatDataForTable() {
        Object[][] data = new Object[disponibilita.size()][2];
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < disponibilita.size(); i++) {
            DisponibilitaDTO d = disponibilita.get(i);
            data[i][0] = d.data().format(formatterData);
            data[i][1] = d.ora().format(formatterOra);
        }
        return data;
    }
}