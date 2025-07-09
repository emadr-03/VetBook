package it.unina.vetbook.control;

import it.unina.vetbook.boundary.TipoVisita;
import it.unina.vetbook.database.FarmacoDAO;
import it.unina.vetbook.dto.AgendaDTO;
import it.unina.vetbook.dto.DisponibilitaDTO;
import it.unina.vetbook.dto.FarmacoDTO;
import it.unina.vetbook.dto.PrenotazioneDTO;
import it.unina.vetbook.entity.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AgendaController {

    private static AgendaController instance;
    private final Agenda agenda = Agenda.getInstance();

    private AgendaController() {}

    public static AgendaController getInstance() {
        if (instance == null) {
            instance = new AgendaController();
        }
        return instance;
    }

    public List<FarmacoDTO> getFarmaciDisponibili() {
        try {
            List<Farmaco> farmaciEntity = new FarmacoDAO().readAll();
            return farmaciEntity.stream()
                    .map(f -> new FarmacoDTO(f.getId(), f.getNome(), f.getProduttore()))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            System.err.println("Errore nel caricamento dei farmaci dal DB: " + e.getMessage());
            return new ArrayList<>();
        }

        //per testare il corretto comportamento della funzionalità di prescrizione dei farmaci
        //sostituire il corpo di questo metodo con questo:
        /*
        List<FarmacoDTO> farmaciMock = new ArrayList<>();
        farmaciMock.add(new FarmacoDTO(1, "Tachipirina", "Bayer"));
        farmaciMock.add(new FarmacoDTO(2, "Moment", "Angelini"));
        farmaciMock.add(new FarmacoDTO(3, "Aspirina", "Bayer"));
        farmaciMock.add(new FarmacoDTO(4, "Gaviscon", "Reckitt"));
        farmaciMock.add(new FarmacoDTO(5, "Frontline Combo", "Boehringer"));
        return farmaciMock;
        */
    }

    public boolean inserisciDisponibilita(LocalDate data, LocalTime ora) {
        return agenda.addDisponibilita(new DisponibilitaDTO(data, ora));
    }

    public List<DisponibilitaDTO> visualizzaDisponibilita() {
        List<DisponibilitaDTO> lista = agenda.getDisponibilita();
        lista.sort(Comparator.comparing(DisponibilitaDTO::getData)
                .thenComparing(DisponibilitaDTO::getOra));
        return lista;
    }

    public List<PrenotazioneDTO> visualizzaPrenotazioni() {
        return agenda.getPrenotazioni();
    }

    public List<AgendaDTO> visualizzaAgenda() {
        List<AgendaDTO> righe = new ArrayList<>();
        for (DisponibilitaDTO d : agenda.getDisponibilita()) {
            righe.add(new AgendaDTO("Disponibilità", d.getData(), d.getOra(), ""));
        }
        for (PrenotazioneDTO p : agenda.getPrenotazioni()) {
            String info = p.getAnimale().getNome() + " - " + p.getAnimale().getTipo();
            righe.add(new AgendaDTO("Prenotazione", p.getData(), p.getOra(), info));
        }
        for (Visita v : agenda.getVisite()) {
            righe.add(new AgendaDTO("Visita", v.getData(), v.getOra(), v.getTipo().toString()));
        }
        righe.sort(Comparator.comparing(AgendaDTO::getData)
                .thenComparing(AgendaDTO::getOra));
        return righe;
    }
}