package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.control.VeterinarioController;
import it.unina.vetbook.dto.PrenotazioneDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrenotazioniBoundary extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JButton indietroBtn;

    private final VeterinarioController veterinarioController;

    public PrenotazioniBoundary(VeterinarioController veterinarioController) {
        super("Elenco Prenotazioni");
        this.veterinarioController = veterinarioController;

        initFrame();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        VetcareStyle.initLookAndFeel();
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
    }

    private void initComponents() {
        String[] cols = {"Data", "Ora", "Dettaglio Animale"};
        model = new DefaultTableModel(cols, 0);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);

        indietroBtn = new JButton("Indietro");

        caricaDati();
    }

    private void layoutComponents() {
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(indietroBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        indietroBtn.addActionListener(e -> {
            new VeterinarioBoundary(veterinarioController).setVisible(true);
            dispose();
        });
    }

    private void caricaDati() {
        model.setRowCount(0);

        List<PrenotazioneDTO> prenotazioni = AgendaController.getInstance().visualizzaPrenotazioniGiornaliere();
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

        for (PrenotazioneDTO p : prenotazioni) {
            String dettaglio = p.animale().nome() + " - " + p.animale().tipo();
            model.addRow(new Object[]{
                    p.data().format(formatterData),
                    p.ora().format(formatterOra),
                    dettaglio
            });
        }
    }
}