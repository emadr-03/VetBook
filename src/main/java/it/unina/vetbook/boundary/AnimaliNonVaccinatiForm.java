package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AdminController;

import javax.swing.*;
import java.awt.*;

public class AnimaliNonVaccinatiForm extends JDialog {

    private final String[] COLS = { "ID", "Nome", "Specie", "Ult. vaccino" };

    private JTable table;
    private JScrollPane scrollPane;
    private JButton chiudiButton;
    private JPanel southPanel;

    public AnimaliNonVaccinatiForm(Frame owner) {
        super(owner, "Animali Non Vaccinati", true);

        initDialog();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initDialog() {
        setSize(600, 400);
        setLocationRelativeTo(getOwner());
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout(12, 12));
    }

    private void initComponents() {
        initTable();
        initSouthPanel();
    }

    private void initTable() {
        Object[][] rows;
        try {
            AdminController ctrl = AdminController.getInstance();
            rows = ctrl.visualizzaAnimaliNonVaccinati();
        } catch (UnsupportedOperationException ex) {
            JOptionPane.showMessageDialog(this,
                    "Funzionalità non ancora implementata,\n"
                            + "verranno mostrati dati di esempio.",
                    "Non disponibile", JOptionPane.INFORMATION_MESSAGE);

            rows = new Object[][]{
                    {101, "Luna", "Gatto", "27/04/2025"},
                    {207, "Milo", "Cane", "27/04/2023"},
                    {315, "Kiwi", "Coniglio", "25/04/2022"},
            };
        }
        table = VetcareStyle.makeTable(rows, COLS);
        scrollPane = new JScrollPane(table);
    }

    private void initSouthPanel() {
        chiudiButton = new JButton("Chiudi");
        southPanel = new JPanel();
        southPanel.setOpaque(false);
        southPanel.add(chiudiButton);
    }

    private void layoutComponents() {
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        chiudiButton.addActionListener(e -> dispose());
    }
}