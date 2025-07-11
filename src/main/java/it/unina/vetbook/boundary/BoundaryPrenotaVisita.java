package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BoundaryPrenotaVisita extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JButton selezionaBtn, annullaBtn;
    private JLabel titleLabel;

    private List<AnimaleDomesticoDTO> animali;

    private ProprietarioController proprietarioController;

    public BoundaryPrenotaVisita(ProprietarioController proprietarioController) {
        super("Prenota Visita - Seleziona Animale");
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
        titleLabel = new JLabel("Seleziona l'animale per cui prenotare la visita:", SwingConstants.CENTER);
        initTableAndModel();
        initButtonPanel();
    }

    private void initTableAndModel() {
        String[] cols = {"Codice Chip", "Nome", "Tipo", "Razza"};
        model = new DefaultTableModel(cols, 0);

        animali = proprietarioController.getAnimaliProprietario();
        for (AnimaleDomesticoDTO a : animali) {
            model.addRow(new Object[]{
                    a.codiceChip(),
                    a.nome(),
                    a.tipo(),
                    a.razza()
            });
        }

        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        scrollPane = new JScrollPane(table);
    }

    private void initButtonPanel() {
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        selezionaBtn = new JButton("Seleziona Animale");
        annullaBtn = new JButton("Annulla");
        buttonPanel.add(selezionaBtn);
        buttonPanel.add(annullaBtn);
    }

    private void layoutComponents() {
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        selezionaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un animale dalla tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            AnimaleDomesticoDTO animaleSelezionato = animali.get(selectedRow);
            new SelezionaDisponibilitaForm(animaleSelezionato, proprietarioController).setVisible(true);
            dispose();
        });

        annullaBtn.addActionListener(e -> {
            new ProprietarioBoundary(proprietarioController).setVisible(true);
            dispose();
        });
    }
}