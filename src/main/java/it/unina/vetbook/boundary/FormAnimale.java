package it.unina.vetbook.boundary;

import com.github.lgooddatepicker.components.DatePicker;
import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDTO;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class FormAnimale extends JDialog {

    private final boolean isEditMode;
    private boolean salvataggioRiuscito = false;

    private final JTextField txtChip = VetcareStyle.textField("Codice numerico di 10 cifre");
    private final JTextField txtNome = VetcareStyle.textField("Nome dell'animale");
    private final JTextField txtTipo = VetcareStyle.textField("Tipo (es. Gatto)");
    private final JTextField txtRazza = VetcareStyle.textField("Razza (es. Persiano)");
    private final JTextField txtColore = VetcareStyle.textField("Colore del manto");
    private final DatePicker datePicker = VetcareStyle.makeDatePicker();

    public FormAnimale(Window owner, AnimaleDTO animaleDTO) {
        super(owner, ModalityType.APPLICATION_MODAL);
        this.isEditMode = animaleDTO != null;

        setTitle(isEditMode ? "Modifica Animale" : "Nuovo Animale");
        setSize(520, 500);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new GridBagLayout());

        JPanel card = VetcareStyle.makeDialogCard();
        card.setLayout(new GridBagLayout());
        add(card);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 20, 8, 20);
        c.anchor = GridBagConstraints.WEST;
        int y = 0;

        c.gridy = y++; c.gridx = 0; card.add(new JLabel("Codice Chip:"), c);
        c.gridx = 1; card.add(txtChip, c);
        c.gridy = y++; c.gridx = 0; card.add(new JLabel("Nome:"), c);
        c.gridx = 1; card.add(txtNome, c);
        c.gridy = y++; c.gridx = 0; card.add(new JLabel("Tipo:"), c);
        c.gridx = 1; card.add(txtTipo, c);
        c.gridy = y++; c.gridx = 0; card.add(new JLabel("Razza:"), c);
        c.gridx = 1; card.add(txtRazza, c);
        c.gridy = y++; c.gridx = 0; card.add(new JLabel("Colore:"), c);
        c.gridx = 1; card.add(txtColore, c);
        c.gridy = y++; c.gridx = 0; card.add(new JLabel("Data di Nascita:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL; card.add(datePicker, c);

        c.gridy = y;
        c.gridx = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(25, 20, 15, 20);
        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton salvaBtn = new JButton("Salva");
        JButton annullaBtn = new JButton("Annulla");
        btnBox.setOpaque(false);
        btnBox.add(salvaBtn);
        btnBox.add(annullaBtn);
        card.add(btnBox, c);

        if (isEditMode) {
            impostaModalitaModifica(animaleDTO);
        }

        annullaBtn.addActionListener(e -> dispose());
        salvaBtn.addActionListener(e -> salvaDati());
    }

    private void impostaModalitaModifica(AnimaleDTO animale) {
        txtChip.setText(String.valueOf(animale.codiceChip));
        txtChip.setEnabled(false);
        txtNome.setText(animale.nome);
        txtTipo.setText(animale.tipo);
        txtRazza.setText(animale.razza);
        txtColore.setText(animale.colore);
        if (animale.dataNascita != null && !animale.dataNascita.isEmpty()) {
            datePicker.setDate(LocalDate.parse(animale.dataNascita, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    private void salvaDati() {
        if (!validaInput()) {
            return;
        }

        try {
            if (isEditMode) {
                long codiceChip = Long.parseLong(txtChip.getText());
                AnimaleDTO animaleAggiornato = new AnimaleDTO(
                        codiceChip,
                        txtNome.getText(),
                        txtTipo.getText(),
                        txtRazza.getText(),
                        txtColore.getText(),
                        datePicker.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                );
                ProprietarioController.getInstance().updateAnimale(animaleAggiornato);
                JOptionPane.showMessageDialog(this, "Dati dell'animale aggiornati.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                long codiceChip = Long.parseLong(txtChip.getText());
                ProprietarioController.getInstance().inserisciAnimale(
                        codiceChip,
                        txtNome.getText(),
                        txtTipo.getText(),
                        txtRazza.getText(),
                        txtColore.getText(),
                        datePicker.getDate()
                );
                JOptionPane.showMessageDialog(this, "Nuovo animale inserito con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            }

            this.salvataggioRiuscito = true;
            dispose();

        } catch (UnsupportedOperationException e) {
            JOptionPane.showMessageDialog(this, "Funzionalità non ancora supportata dal controller.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Il codice chip deve essere un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validaInput() {
        StringBuilder errors = new StringBuilder();
        if (!isEditMode) {
            String chipStr = txtChip.getText().trim();
            if (chipStr.isEmpty()) {
                errors.append("- Il campo 'Codice Chip' è obbligatorio.\n");
            } else {
                try {
                    Long.parseLong(chipStr);
                    if (chipStr.length() != 10) {
                        errors.append("- Il codice chip deve essere di 10 cifre.\n");
                    }
                } catch (NumberFormatException e) {
                    errors.append("- Formato codice chip errato! (solo cifre ammesse)\n");
                }
            }
        }
        validaCampoTesto(txtNome.getText(), "Nome", 40, errors);
        validaCampoTesto(txtTipo.getText(), "Tipo", 20, errors);
        validaCampoTesto(txtRazza.getText(), "Razza", 20, errors);
        validaCampoTesto(txtColore.getText(), "Colore", 20, errors);
        if (datePicker.getDate() == null) {
            errors.append("- Selezionare una data di nascita.\n");
        } else if (datePicker.getDate().isAfter(LocalDate.now())) {
            errors.append("- Non è possibile inserire una data futura!\n");
        }
        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, "Correggere i seguenti errori:\n\n" + errors.toString(), "Errore di Validazione", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void validaCampoTesto(String testo, String nomeCampo, int maxLength, StringBuilder errors) {
        String testoTrim = testo.trim();
        if (testoTrim.isEmpty()) {
            errors.append("- Il campo '").append(nomeCampo).append("' è obbligatorio.\n");
            return;
        }
        if (testoTrim.length() > maxLength) {
            errors.append("- Il campo '").append(nomeCampo).append("' è troppo lungo (max ").append(maxLength).append(" caratteri).\n");
        }
        if (!Pattern.matches("^[\\p{L} .'-]+$", testoTrim)) {
            errors.append("- Il campo '").append(nomeCampo).append("' non può contenere numeri o simboli speciali.\n");
        }
    }

    public boolean isSalvataggioRiuscito() {
        return salvataggioRiuscito;
    }
}