package it.unina.vetbook.entity;

import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.database.DisponibilitaDAO;
import it.unina.vetbook.database.PrenotazioneDAO;
import it.unina.vetbook.database.VisitaDAO;

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
    private List<Visita> visite;

    private Agenda() {
        this.disponibilita = new ArrayList<>();
        this.prenotazioni = new ArrayList<>();
        this.visite = new ArrayList<>();

        // Disponibilità
        disponibilita.add(new Disponibilita(1, LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
        disponibilita.add(new Disponibilita(2, LocalDate.now().plusDays(1), LocalTime.of(10, 0)));
        disponibilita.add(new Disponibilita(3, LocalDate.now().plusDays(2), LocalTime.of(11, 0)));
        disponibilita.add(new Disponibilita(4, LocalDate.now().plusDays(2), LocalTime.of(12, 0)));

        // Animali di test
        AnimaleDomestico rex = new AnimaleDomestico(111222333, null,"Rex", "Cane", "Pastore Tedesco", "Nero", LocalDate.now().minusYears(3));
        AnimaleDomestico luna = new AnimaleDomestico(444555666, null, "Luna", "Gatto", "Europeo", "Bianco", LocalDate.now().minusYears(2));
        AnimaleDomestico zorro = new AnimaleDomestico(777888999, null, "Zorro", "Cane", "Labrador", "Miele", LocalDate.now().minusYears(4));

        // Prenotazioni MOCK
        this.prenotazioni.add(new Prenotazione(LocalDate.now().plusDays(1), LocalTime.of(9, 0), rex));
        this.prenotazioni.add(new Prenotazione(LocalDate.now().plusDays(2), LocalTime.of(12, 0), luna));
        this.prenotazioni.add(new Prenotazione(LocalDate.now().plusDays(3), LocalTime.of(15, 0), zorro));

        // Visite MOCK
        Visita v1 = new Visita(TipoVisita.CONTROLLO, "Controllo annuale", 35.00, Veterinario.mockVet());
        v1.setData(LocalDate.now());
        v1.setOra(LocalTime.of(10, 0));
        this.visite.add(v1);

        Visita v2 = new Visita(TipoVisita.VACCINAZIONE, "Vaccino rabbia", 60.00, Veterinario.mockVet());
        v2.setData(LocalDate.now());
        v2.setOra(LocalTime.of(11, 0));
        this.visite.add(v2);

        Visita v3 = new Visita(TipoVisita.INTERVENTO_CHIRURGICO, "Rimozione cisti", 120.00, Veterinario.mockVet());
        v3.setData(LocalDate.now().plusDays(1));
        v3.setOra(LocalTime.of(9, 0));
        this.visite.add(v3);
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

}