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

    private List<AnimaleDomesticoDTO> animali;
    private  ProprietarioController proprietarioController;


    public AnimaliProprietarioBoundary(ProprietarioController proprietarioController) {
        super("I Tuoi Animali");
        this.proprietarioController = proprietarioController;

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

        animali = proprietarioController.getAnimaliProprietario();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (AnimaleDomesticoDTO a : animali) {
            model.addRow(new Object[]{
                    a.codiceChip(),
                    a.nome(),
                    a.tipo(),
                    a.razza(),
                    a.colore(),
                    a.dataDiNascita().format(formatter)
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
                    proprietarioController,
                    String.valueOf(animaleSelezionato.codiceChip()),
                    animaleSelezionato.nome(),
                    animaleSelezionato.tipo(),
                    animaleSelezionato.razza(),
                    animaleSelezionato.colore(),
                    animaleSelezionato.dataDiNascita()
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
                proprietarioController.eliminaAnimale(codiceChip);
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Animale eliminato con successo! (MOCK)");
            }
        });

        indietroBtn.addActionListener(e -> {
            new ProprietarioBoundary(proprietarioController).setVisible(true);
            dispose();
        });
    }
}