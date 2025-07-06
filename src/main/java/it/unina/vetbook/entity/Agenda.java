package it.unina.vetbook.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {

    private static Agenda instance;
    List<Disponibilita> disponibilita;
    List<Prenotazione> prenotazioni;
    List<Visita> visite;

    private Agenda() {}

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

        // VisitaDAO dao = VisitaDAO.create(v)
        // dao.create()
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
        //PrenotazioniDAO dao = new PrenotazioniDAO(p);
        //dao.create(p);
    }

    public void aggiungiDisponibilità(Disponibilita d) {
        throw new UnsupportedOperationException("Not supported yet");
    }

}

