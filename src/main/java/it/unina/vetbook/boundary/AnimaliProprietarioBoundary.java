package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnimaliProprietarioBoundary extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JButton modificaBtn, eliminaBtn, indietroBtn;

    private final ProprietarioController ctrl = ProprietarioController.getInstance();
    private List<AnimaleDomesticoDTO> animali;

    public AnimaliProprietarioBoundary() {
        super("I Tuoi Animali");
        VetcareStyle.initLookAndFeel();

        initFrame();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initFrame() {
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void initComponents() {
        initTableAndModel();
        initButtonPanel();
    }

    private void initTableAndModel() {
        String[] cols = {"Codice Chip", "Nome", "Tipo", "Razza", "Colore", "Data Nascita"};
        model = new DefaultTableModel(cols, 0);

        animali = ctrl.getAnimaliProprietario();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (AnimaleDomesticoDTO a : animali) {
            model.addRow(new Object[]{
                    a.getCodiceChip(),
                    a.getNome(),
                    a.getTipo(),
                    a.getRazza(),
                    a.getColore(),
                    a.getDataDiNascita().format(formatter)
            });
        }

        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        scrollPane = new JScrollPane(table);
    }

    private void initButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        modificaBtn = new JButton("Modifica Selezionato");
        eliminaBtn = new JButton("Elimina Selezionato");
        indietroBtn = new JButton("Indietro");

        buttonPanel.add(modificaBtn);
        buttonPanel.add(eliminaBtn);
        buttonPanel.add(indietroBtn);
    }

    private void layoutComponents() {
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        modificaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un animale da modificare.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AnimaleDomesticoDTO animaleSelezionato = animali.get(selectedRow);
            new FormAnimale(
                    String.valueOf(animaleSelezionato.getCodiceChip()),
                    animaleSelezionato.getNome(),
                    animaleSelezionato.getTipo(),
                    animaleSelezionato.getRazza(),
                    animaleSelezionato.getColore(),
                    animaleSelezionato.getDataDiNascita()
            ).setVisible(true);
            dispose();
        });

        eliminaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un animale da eliminare.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare l'animale selezionato?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int codiceChip = (int) model.getValueAt(selectedRow, 0);
                ctrl.eliminaAnimale(codiceChip);
                model.removeRow(selectedRow);
                animali.remove(selectedRow);
                JOptionPane.showMessageDialog(this, "Animale eliminato con successo!");
            }
        });

        indietroBtn.addActionListener(e -> {
            new ProprietarioBoundary().setVisible(true);
            dispose();
        });
    }
}