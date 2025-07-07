package it.unina.vetbook.entity;

import java.time.LocalDate;
import java.time.LocalTime;
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

        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(1), LocalTime.of(10, 0)));
        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(2), LocalTime.of(11, 0)));
        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(2), LocalTime.of(12, 0)));
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

    public void prenotaVisita(Prenotazione p) {
        disponibilita.removeIf(d -> d.getData().equals(p.getData()) && d.getOra().equals(p.getOra()));
        this.prenotazioni.add(p);
        System.out.println("Prenotazione aggiunta in memoria per: " + p.getAnimale().getNome());
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public boolean addDisponibilita(Disponibilita nuovaDisp) {
        boolean esisteGia = disponibilita.stream()
                .anyMatch(d -> d.getData().equals(nuovaDisp.getData()) && d.getOra().equals(nuovaDisp.getOra()));
        if (esisteGia) {
            return false;
        }
        return this.disponibilita.add(nuovaDisp);
    }

    public void registraVisita(Visita v) {
        this.visite.add(v);
    }

    public List<Visita> getVisite() {
        return this.visite;
    }

    public List<Visita> visualizzaVisiteGiornaliere() {
        return this.visite.stream()
                .filter(visita -> visita.getData().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public double ottieniIncasso(List<Visita> visite) {
        return visite.stream().mapToDouble(Visita::getCosto).sum();
    }
}