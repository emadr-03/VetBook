package it.unina.vetbook.control;

import it.unina.vetbook.dto.*;
import it.unina.vetbook.entity.*;
import it.unina.vetbook.exception.BusinessRuleViolationException;
import it.unina.vetbook.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public void inserisciDisponibilita(LocalDate data, LocalTime ora) {
        //1. Verifica che data e ora non siano nulli
        if (data == null || ora == null) {
            throw new ValidationException("Data e ora sono obbligatorie.");
        }
        // 2. Verifica che la data/ora non sia nel passato
        LocalDate oggi = LocalDate.now();
        LocalTime oraAttuale = LocalTime.now();
        if (data.isBefore(oggi) || (data.equals(oggi) && ora.isBefore(oraAttuale))) {
            throw new ValidationException("Non è possibile inserire disponibilità in una data o ora passata.");
        }
        // 3. Verifica che lo slot orario sia disponibile
        if (agenda.isSlotOccupato(data, ora)) {
            throw new BusinessRuleViolationException("Lo slot è già occupato da una disponibilità, prenotazione o visita.");
        }
        agenda.addDisponibilita(new Disponibilita(data, ora));
    }

    public List<DisponibilitaDTO> visualizzaDisponibilita() {
        List<Disponibilita> lista = agenda.getDisponibilita();
        lista.sort(Comparator.comparing(Disponibilita::getData)
                .thenComparing(Disponibilita::getOra));
        return lista.stream()
                .map(d -> new DisponibilitaDTO(d.getData(), d.getOra(), d.getStato()))
                .toList();
    }


    public List<PrenotazioneDTO> visualizzaPrenotazioniGiornaliere() {
        LocalDate oggi = LocalDate.now();
        return agenda.getPrenotazioni().stream()
                .filter(p -> p.getData().equals(oggi))
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
                                p.getAnimale().getIdProprietario()
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
            righe.add(new AgendaDTO("Prenotazione", p.getData(), p.getOra()));
        }

        for (Visita v : agenda.getVisite()) {
            righe.add(new AgendaDTO("Visita", v.getData(), v.getOra(), v.getTipo().toString()));
        }

        righe.sort(Comparator.comparing(AgendaDTO::data)
                .thenComparing(AgendaDTO::ora));
        return righe;
    }

}