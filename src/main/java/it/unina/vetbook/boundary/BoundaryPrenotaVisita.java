package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.entity.AnimaleDomestico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BoundaryPrenotaVisita extends JFrame {

    private final JTable table;
    private final DefaultTableModel model;
    private final List<AnimaleDomestico> animali;

    public BoundaryPrenotaVisita() {
        super("Prenota Visita - Seleziona Animale");
        VetcareStyle.initLookAndFeel();
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        add(new JLabel("Seleziona l'animale per cui prenotare la visita:", SwingConstants.CENTER), BorderLayout.NORTH);

        String[] cols = {"Codice Chip", "Nome", "Tipo", "Razza"};

        this.animali = ProprietarioController.getInstance().getAnimaliProprietario();
        Object[][] data = new Object[animali.size()][4];
        for (int i = 0; i < animali.size(); i++) {
            AnimaleDomestico a = animali.get(i);
            data[i][0] = a.getCodiceChip();
            data[i][1] = a.getNome();
            data[i][2] = a.getTipo();
            data[i][3] = a.getRazza();
        }

        model = new DefaultTableModel(data, cols);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton selezionaBtn = new JButton("Seleziona Animale");
        JButton annullaBtn = new JButton("Annulla");
        buttonPanel.add(selezionaBtn);
        buttonPanel.add(annullaBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        selezionaBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un animale dalla tabella.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            AnimaleDomestico animaleSelezionato = animali.get(selectedRow);
            new SelezionaDisponibilitaForm(animaleSelezionato).setVisible(true);
            dispose();
        });

        annullaBtn.addActionListener(e -> {
            new ProprietarioBoundary().setVisible(true);
            dispose();
        });
    }
}