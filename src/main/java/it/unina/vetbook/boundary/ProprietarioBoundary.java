package it.unina.vetbook.boundary;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDTO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ProprietarioBoundary extends JFrame {

    public ProprietarioBoundary() {
        super("Area Proprietario");
        setSize(750, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setContentPane(VetcareStyle.createSpotlightBackground());
        setLayout(new BorderLayout());

        URL logoUrl = getClass().getResource("/img/logo_clinica.png");
        if (logoUrl != null) {
            ImageIcon logo = new ImageIcon(new ImageIcon(logoUrl).getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH));
            JLabel head = new JLabel(logo, SwingConstants.CENTER);
            head.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            add(head, BorderLayout.NORTH);
        }

        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setOpaque(false);
        box.setBorder(BorderFactory.createEmptyBorder(10, 200, 30, 200));

        box.add(VetcareStyle.makeCard("Gestisci Profilo", icon("user_profile_icon.png"), this::gestioneProfiloOnClickHandler));
        box.add(Box.createVerticalStrut(26));
        box.add(VetcareStyle.makeCard("I Miei Animali", icon("pets_icon.png"), this::visualizzaAnimaliOnClickHandler));
        box.add(Box.createVerticalStrut(26));
        box.add(VetcareStyle.makeCard("Inserisci Nuovo Animale", icon("add_pet_icon.png"), this::inserisciAnimaleOnClickHandler));
        box.add(Box.createVerticalStrut(26));
        box.add(VetcareStyle.makeCard("Prenota Visita", icon("book_visit.png"), this::prenotaVisitaOnClickHandler));
        add(box, BorderLayout.CENTER);
    }

    private ImageIcon icon(String file) {
        URL imageUrl = getClass().getResource("/img/" + file);
        if (imageUrl != null) {
            return new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        } else {
            System.err.println("Immagine non trovata: /img/" + file);
            return null;
        }
    }

    private void gestioneProfiloOnClickHandler() {
        new ProfiloProprietarioBoundary(this).setVisible(true);
    }

    private void visualizzaAnimaliOnClickHandler() {
        new AnimaliProprietarioBoundary(this).setVisible(true);
    }

    private void inserisciAnimaleOnClickHandler() {
        new FormAnimale(this, null).setVisible(true);
    }

    private void prenotaVisitaOnClickHandler() {
        BoundaryPrenotaVisita selezioneAnimaleDialog = new BoundaryPrenotaVisita(this);
        selezioneAnimaleDialog.setVisible(true);

        if (selezioneAnimaleDialog.isSelezioneConfermata()) {
            AnimaleDTO animaleScelto = selezioneAnimaleDialog.getAnimaleSelezionato();

            SelezioneDisponibilitaForm selezioneDisponibilita = new SelezioneDisponibilitaForm(this);
            selezioneDisponibilita.setVisible(true);

            if (selezioneDisponibilita.isSelezioneConfermata()) {
                LocalDate dataScelta = selezioneDisponibilita.getDataSelezionata();
                LocalTime oraScelta = selezioneDisponibilita.getOraSelezionata();

                try {
                    ProprietarioController.getInstance().effettuaPrenotazione(animaleScelto, dataScelta, oraScelta);

                    String dataFormatted = dataScelta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String oraFormatted = oraScelta.format(DateTimeFormatter.ofPattern("HH:mm"));

                    JOptionPane.showMessageDialog(this, "Prenotazione per '" + animaleScelto.nome + "' effettuata con successo per il " + dataFormatted + " alle " + oraFormatted);
                } catch (UnsupportedOperationException e) {
                    JOptionPane.showMessageDialog(this, "Funzionalità 'effettuaPrenotazione' non ancora supportata dal controller.", "Informazione", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}