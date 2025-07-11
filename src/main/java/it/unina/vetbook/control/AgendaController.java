package it.unina.vetbook.control;

import it.unina.vetbook.dto.*;
import it.unina.vetbook.entity.*;

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

    public static synchronized AgendaController getInstance() {
        if (instance == null) {
            instance = new AgendaController();
        }
        return instance;
    }

    public boolean inserisciDisponibilita(LocalDate data, LocalTime ora) {
        return agenda.addDisponibilita(new Disponibilita(data, ora));
    }

    public List<DisponibilitaDTO> visualizzaDisponibilita() {
        List<Disponibilita> lista = agenda.getDisponibilita();
        lista.sort(Comparator.comparing(Disponibilita::getData)
                .thenComparing(Disponibilita::getOra));
        return lista.stream()
                .map(d -> new DisponibilitaDTO(d.getData(), d.getOra(), d.getStato()))
                .toList();
    }


    public List<PrenotazioneDTO> visualizzaPrenotazioni() {
        return agenda.getPrenotazioni().stream()
                .map(p -> new PrenotazioneDTO(
                        p.getData(),
                        p.getOra(),
                        new AnimaleDomesticoDTO(
                                p.getAnimale().getCodiceChip(),
                                p.getAnimale().getNome(),
                                p.getAnimale().getTipo(),
                                p.getAnimale().getRazza(),
                                p.getAnimale().getColore(),
                                p.getAnimale().getDataDiNascita(),
                                p.getAnimale().getProprietario()
                        )
                ))
                .toList();
    }


    public List<AgendaDTO> visualizzaAgenda() {
        List<AgendaDTO> righe = new ArrayList<>();

        for (Disponibilita d : agenda.getDisponibilita()) {
            righe.add(new AgendaDTO("Disponibilità", d.getData(), d.getOra(), ""));
        }

        for (Prenotazione p : agenda.getPrenotazioni()) {
            AnimaleDomestico a = p.getAnimale();
            String info = a.getNome() + " - " + a.getTipo();
            righe.add(new AgendaDTO("Prenotazione", p.getData(), p.getOra(), info));
        }

        for (Visita v : agenda.getVisite()) {
            righe.add(new AgendaDTO("Visita", v.getData(), v.getOra(), v.getTipo().toString()));
        }

        righe.sort(Comparator.comparing(AgendaDTO::data)
                .thenComparing(AgendaDTO::ora));
        return righe;
    }

}