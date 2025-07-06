package it.unina.vetbook.entity;

import it.unina.vetbook.database.PrenotazioneDAO;
import it.unina.vetbook.database.VisitaDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {

    private static Agenda instance;
    List<Disponibilita> disponibilita;
    List<Prenotazione> prenotazioni;
    List<Visita> visite;

    private Agenda() {
        this.disponibilita = new ArrayList<>();
        this.prenotazioni = new ArrayList<>();
        this.visite = new ArrayList<>();
    }

    public static Agenda getInstance() {
        if (instance == null) {
            instance = new Agenda();
        }
        return instance;
    }

    public List<Disponibilita> getDisponibilita() {
        return disponibilita;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public List<Visita> getVisite() {
        return visite;
    }

    public void registraVisita(Visita v) {
        disponibilita.removeIf(d ->
                d.getData().equals(v.getData()) &&
                        d.getOra().equals(v.getOra())
        );

        prenotazioni.removeIf(p ->
                p.getData().equals(v.getData()) &&
                        p.getOra().equals(v.getOra())
        );

        visite.add(v);

        try{
            VisitaDAO dao = new VisitaDAO();
            dao.create(v);
        } catch (SQLException e){
            throw new RuntimeException("Errore nella registrazione della visita", e);
        }
    }

    public List<Visita> visualizzaVisiteGiornaliere() {
        return this.getVisite().stream()
                .filter(visita -> visita.getData().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public double ottieniIncasso(List<Visita> visite) {
        return visite.stream()
                .mapToDouble(Visita::getCosto)
                .sum();
    }

    public void prenotaVisita(Prenotazione p) {
        disponibilita.removeIf(d ->
                d.getData().equals(p.getData()) &&
                        d.getOra().equals(p.getOra())
        );
        this.prenotazioni.add(p);

        try {
            new PrenotazioneDAO().create(p);
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella creazione della prenotazione", e);
        }
    }

    public void aggiungiDisponibilità(Disponibilita d) {
        throw new UnsupportedOperationException("Not supported yet");
    }

}

