package it.unina.vetbook.boundary;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.control.VeterinarioController;
import it.unina.vetbook.dto.FarmacoDTO;
import it.unina.vetbook.entity.TipoVisita;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormDialogVisita extends JDialog {

    private JComboBox<TipoVisita> tipoVisitaCombo;
    private JTextField descrizioneText;
    private JTextField costoText;
    private JButton registraBtn, chiudiBtn, addFarmacoBtn, removeFarmacoBtn;
    private DefaultListModel<FarmacoDTO> disponibiliModel;
    private DefaultListModel<FarmacoDTO> prescrittiModel;
    private JList<FarmacoDTO> disponibiliList, prescrittiList;

    private final VeterinarioController veterinarioController;

    public FormDialogVisita(Frame owner, VeterinarioController veterinarioController) {
        super(owner, "Registra Visita", true);
        this.veterinarioController = veterinarioController;

        initDialog();
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initDialog() {
        setSize(750, 600);
        setLocationRelativeTo(getOwner());
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        tipoVisitaCombo = new JComboBox<>(TipoVisita.values());
        descrizioneText = VetcareStyle.textField("Descrizione della visita...");
        costoText = VetcareStyle.textField("Costo (es. 50.00)");

        disponibiliModel = new DefaultListModel<>();
        prescrittiModel = new DefaultListModel<>();

        List<FarmacoDTO> farmaciDisponibili = veterinarioController.getFarmaciDisponibili();
        farmaciDisponibili.forEach(disponibiliModel::addElement);

        disponibiliList = new JList<>(disponibiliModel);
        prescrittiList = new JList<>(prescrittiModel);

        disponibiliList.setCellRenderer(new FarmacoRenderer());
        prescrittiList.setCellRenderer(new FarmacoRenderer());

        addFarmacoBtn = new JButton(">>");
        removeFarmacoBtn = new JButton("<<");
        registraBtn = new JButton("Registra");
        chiudiBtn = new JButton("Chiudi");
    }

    private void layoutComponents() {
        JPanel card = VetcareStyle.makeDialogCard();
        card.setLayout(new GridBagLayout());
        add(card);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 15, 8, 15);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;

        card.add(new JLabel("Tipo Visita:"), c);
        c.gridy++;
        card.add(tipoVisitaCombo, c);
        c.gridy++;
        card.add(new JLabel("Descrizione:"), c);
        c.gridy++;
        card.add(descrizioneText, c);
        c.gridy++;
        card.add(new JLabel("Costo:"), c);
        c.gridy++;
        card.add(costoText, c);
        c.gridy++;
        card.add(new JLabel("Prescrivi Farmaci (Disponibili a sx, Prescritti a dx):"), c);

        c.gridy++;
        c.gridwidth = 1;
        c.weightx = 0.45;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        card.add(new JScrollPane(disponibiliList), c);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addFarmacoBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(removeFarmacoBtn);
        c.gridx = 1;
        c.weightx = 0.1;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        card.add(buttonPanel, c);

        c.gridx = 2;
        c.weightx = 0.45;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        card.add(new JScrollPane(prescrittiList), c);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(registraBtn);
        bottomPanel.add(chiudiBtn);
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 3;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 15, 8, 15);
        card.add(bottomPanel, c);
    }

    private void addListeners() {
        addFarmacoBtn.addActionListener(e -> spostaFarmaci(disponibiliList, disponibiliModel, prescrittiModel));
        removeFarmacoBtn.addActionListener(e -> spostaFarmaci(prescrittiList, prescrittiModel, disponibiliModel));
        registraBtn.addActionListener(e -> handleRegistra());
        chiudiBtn.addActionListener(e -> dispose());
    }

    private void spostaFarmaci(JList<FarmacoDTO> sourceList, DefaultListModel<FarmacoDTO> sourceModel, DefaultListModel<FarmacoDTO> destModel) {
        List<FarmacoDTO> selected = sourceList.getSelectedValuesList();
        for (FarmacoDTO f : selected) {
            destModel.addElement(f);
            sourceModel.removeElement(f);
        }
    }

    private void handleRegistra() {
        String descrizione = descrizioneText.getText().trim();
        String costoStr = costoText.getText().trim();

        double costo;
        try {
            costo = Double.parseDouble(costoStr);
        } catch (NumberFormatException ex) {
            mostraErrore("Costo non valido! Inserire un numero.");
            return;
        }

        TipoVisita tipo = (TipoVisita) tipoVisitaCombo.getSelectedItem();
        List<FarmacoDTO> farmaciPrescritti = new ArrayList<>();
        for (int i = 0; i < prescrittiModel.getSize(); i++) {
            farmaciPrescritti.add(prescrittiModel.getElementAt(i));
        }

        try {
            veterinarioController.registraVisita(tipo, descrizione, costo, farmaciPrescritti);
            JOptionPane.showMessageDialog(this, "Visita registrata con successo!");
            dispose();
        } catch (Exception ex) {
            mostraErrore("Errore: " + ex.getMessage());
        }
    }


    private void mostraErrore(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio, "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
    }

    static class FarmacoRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof FarmacoDTO dto) {
                setText(dto.nome() + " (" + dto.produttore() + ")");
            }
            return this;
        }
    }
}