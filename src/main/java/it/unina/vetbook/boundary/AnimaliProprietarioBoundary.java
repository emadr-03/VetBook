package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnimaliProprietarioBoundary extends JFrame {

    private final DefaultTableModel model;
    private final JTable table;
    private final ProprietarioController ctrl = ProprietarioController.getInstance();
    private final List<AnimaleDomesticoDTO> animali;

    public AnimaliProprietarioBoundary() {
        super("I Tuoi Animali");
        VetcareStyle.initLookAndFeel();

        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        String[] cols = {"Codice Chip", "Nome", "Tipo", "Razza", "Colore", "Data Nascita"};
        model = new DefaultTableModel(cols, 0);

        this.animali = ctrl.getAnimaliProprietario();
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
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton modificaBtn = new JButton("Modifica Selezionato");
        JButton eliminaBtn = new JButton("Elimina Selezionato");
        JButton indietroBtn = new JButton("Indietro");

        buttonPanel.add(modificaBtn);
        buttonPanel.add(eliminaBtn);
        buttonPanel.add(indietroBtn);
        add(buttonPanel, BorderLayout.SOUTH);

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