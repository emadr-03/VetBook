package it.unina.vetbook.entity;

import it.unina.vetbook.database.*;
import it.unina.vetbook.exception.PersistenceException;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Agenda {

    private static Agenda instance;
    private final List<Disponibilita> disponibilita;
    private final List<Prenotazione> prenotazioni;
    private final List<Visita> visite;

    private Agenda() {
        try(Connection conn = DBManager.getInstance().getConnection()) {
            this.disponibilita = new DisponibilitaDAO(conn).readAll();
            this.prenotazioni = new PrenotazioneDAO(conn).readAll();
            this.visite = new VisitaDAO(conn).readAll();
            List<AnimaleDomestico> animali = new AnimaleDomesticoDAO(conn).readAll();
            risolviCollegamenti(animali);
        } catch (SQLException e){
            throw new PersistenceException("Errore durante l'inizializzazione dell'agenda" + e.getMessage());
        }
    }


    //A: Metodo di supporto per test unitari
    public static Agenda creaMock(List<Disponibilita> disp, List<Prenotazione> pren, List<Visita> vis) {
        Agenda a = new Agenda(true);
        a.getDisponibilita().addAll(disp);
        a.getPrenotazioni().addAll(pren);
        a.getVisite().addAll(vis);
        return a;
    }

    private Agenda(boolean testingMode) {
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

    public void prenotaVisita(Prenotazione p) {
        Optional<Disponibilita> daEliminare = disponibilita.stream()
                .filter(d -> d.getData().equals(p.getData()) && d.getOra().equals(p.getOra()))
                .findFirst();
        disponibilita.removeIf(d -> d.getData().equals(p.getData()) && d.getOra().equals(p.getOra()));

        try (Connection conn = DBManager.getInstance().getConnection()) {
            if (daEliminare.isPresent()) {
                new DisponibilitaDAO(conn).delete(daEliminare.get().getId());
            }

            // Creazione prenotazione
            new PrenotazioneDAO(conn).create(p);

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
        try(Connection conn = DBManager.getInstance().getConnection()){
            DisponibilitaDAO dao = new DisponibilitaDAO(conn);
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

    private void risolviCollegamenti(List<AnimaleDomestico> animali) {
        for (Prenotazione p : prenotazioni) {
            animali.stream()
                    .filter(a -> a.getCodiceChip() == p.getIdAnimale())
                    .findFirst()
                    .ifPresent(p::setAnimale);
        }
    }

}