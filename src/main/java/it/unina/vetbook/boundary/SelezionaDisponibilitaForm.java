package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Disponibilita;
import it.unina.vetbook.entity.Proprietario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SelezionaDisponibilitaForm extends JFrame {

    private final AnimaleDomesticoDTO animaleSelezionato;
    private final JTable table;
    private final DefaultTableModel model;
    private final List<Disponibilita> disponibilita;

    public SelezionaDisponibilitaForm(AnimaleDomesticoDTO a) {
        super("Prenota Visita - Seleziona Disponibilità");
        this.animaleSelezionato = a;
        VetcareStyle.initLookAndFeel();

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        String infoAnimale = String.format("Stai prenotando per: %s (%s)", a.getNome(), a.getTipo());
        add(new JLabel(infoAnimale, SwingConstants.CENTER), BorderLayout.NORTH);

        String[] cols = {"Data", "Ora"};

        // La chiamata ora è corretta e non dà errore
        this.disponibilita = AgendaController.getInstance().visualizzaDisponibilita();

        Object[][] data = new Object[disponibilita.size()][2];
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < disponibilita.size(); i++) {
            Disponibilita d = disponibilita.get(i);
            data[i][0] = d.getData().format(formatterData);
            data[i][1] = d.getOra().format(formatterOra);
        }

        model = new DefaultTableModel(data, cols);
        table = VetcareStyle.makeTable(new Object[0][0], cols);
        table.setModel(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton selezionaDispBtn = new JButton("Conferma Prenotazione");
        JButton annullaBtn = new JButton("Annulla");
        buttonPanel.add(selezionaDispBtn);
        buttonPanel.add(annullaBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        selezionaDispBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona una data e un'ora disponibili.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Disponibilita slotSelezionato = disponibilita.get(selectedRow);

            ProprietarioController.getInstance().effettuaPrenotazione(animaleSelezionato, slotSelezionato.getData(), slotSelezionato.getOra());

            JOptionPane.showMessageDialog(this, "Prenotazione confermata!", "Successo", JOptionPane.INFORMATION_MESSAGE);

            new ProprietarioBoundary().setVisible(true);
            dispose();
        });

        annullaBtn.addActionListener(e -> {
            new BoundaryPrenotaVisita().setVisible(true);
            dispose();
        });
    }
}