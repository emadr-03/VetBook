package it.unina.vetbook.entity;

import it.unina.vetbook.database.DisponibilitaDAO;
import it.unina.vetbook.database.PrenotazioneDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Agenda {

    private static Agenda instance;
    private final List<Disponibilita> disponibilita;
    private final List<Prenotazione> prenotazioni;
    private List<Visita> visite;

    private Agenda() {
        this.disponibilita = new ArrayList<>();
        this.prenotazioni = new ArrayList<>();
        this.visite = new ArrayList<>();

        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(1), LocalTime.of(10, 0)));
        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(2), LocalTime.of(11, 0)));
        disponibilita.add(new Disponibilita(LocalDate.now().plusDays(2), LocalTime.of(12, 0)));


        //dati MOCKATI per far visualizzare una prenotazione al Veterinario
        AnimaleDomestico animaleTest = new AnimaleDomestico(111222333, "Rex", "Cane", "Pastore Tedesco", "Nero", LocalDate.now().minusYears(3));
        this.prenotazioni.add(new Prenotazione(LocalDate.now().plusDays(1), LocalTime.of(9, 0), animaleTest));
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
        PrenotazioneDAO dao = new PrenotazioneDAO();
        try {
            dao.create(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        DisponibilitaDAO dao = new DisponibilitaDAO();
        try {
            dao.create(nuovaDisp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.disponibilita.add(nuovaDisp);
    }

    public List<Visita> getVisite() {
        return this.visite;
    }

    public List<Visita> visualizzaVisiteGiornaliere() {
        return this.visite.stream()
                .filter(visita -> visita.getData().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public boolean isSlotOccupato(LocalDate data, LocalTime ora) {
        return disponibilita.stream().anyMatch(d -> d.getData().equals(data) && d.getOra().equals(ora)) ||
                prenotazioni.stream().anyMatch(p -> p.getData().equals(data) && p.getOra().equals(ora)) ||
                visite.stream().anyMatch(v -> v.getData().equals(data) && v.getOra().equals(ora));
    }

    public double ottieniIncasso(List<Visita> visite) {
        return visite.stream().mapToDouble(Visita::getCosto).sum();
    }
}