package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AdminController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AnimaliNonVaccinatiForm extends JDialog {

    private final String[] COLS = { "ID", "Nome", "Specie", "Ult. vaccino" };

    private JTable table;
    private JScrollPane scrollPane;
    private JButton chiudiButton;
    private JPanel southPanel;

    private AdminController adminController;

    public AnimaliNonVaccinatiForm(Frame owner, AdminController adminController) {
        super(owner, "Animali Non Vaccinati", true);
        this.adminController = adminController;

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
        List<AnimaleDomesticoDTO> rows;
        try {
            rows = adminController.visualizzaAnimaliNonVaccinati();
        } catch (UnsupportedOperationException ex) {
            JOptionPane.showMessageDialog(this,
                    "Funzionalità non ancora implementata,\n"
                            + "verranno mostrati dati di esempio.",
                    "Non disponibile", JOptionPane.INFORMATION_MESSAGE);

            // Mock: crea lista di DTO fittizi
            rows = List.of(
                    new AnimaleDomesticoDTO(101, "Luna", "Gatto", null, null, LocalDate.of(2025, 4, 27), null),
                    new AnimaleDomesticoDTO(207, "Milo", "Cane", null, null, LocalDate.of(2023, 4, 27), null),
                    new AnimaleDomesticoDTO(315, "Kiwi", "Coniglio", null, null, LocalDate.of(2022, 4, 25), null)
            );
        }


        Object[][] data = new Object[rows.size()][COLS.length];
        for (int i = 0; i < rows.size(); i++) {
            AnimaleDomesticoDTO dto = rows.get(i);
            data[i][0] = dto.codiceChip();
            data[i][1] = dto.nome();
            data[i][2] = dto.tipo();
            data[i][3] = dto.dataDiNascita().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        table = VetcareStyle.makeTable(data, COLS);
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