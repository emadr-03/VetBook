package it.unina.vetbook.control;

import it.unina.vetbook.dto.AgendaEntryDTO;
import it.unina.vetbook.dto.DisponibilitaDTO;
import it.unina.vetbook.dto.PrenotazioneDTO;
import it.unina.vetbook.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        List<PrenotazioneDTO> lista = agenda.getPrenotazioni();
        lista.sort(Comparator.comparing(PrenotazioneDTO::getData)
                .thenComparing(PrenotazioneDTO::getOra));
        return lista;
    }

    public void registraVisita(TipoVisita tipo, String descrizione, double costo, String prodFarmaco, String nomeFarmaco) {
        Visita v = new Visita(tipo, descrizione, costo);
        if((prodFarmaco!=null && !prodFarmaco.isEmpty()) && (nomeFarmaco!=null && !nomeFarmaco.isEmpty())){
            v.prescrivi(new Farmaco(nomeFarmaco, prodFarmaco));
        }
        agenda.registraVisita(v);
    }

    public List<AgendaEntryDTO> visualizzaAgenda() {
        List<AgendaEntryDTO> righe = new ArrayList<>();

        for (DisponibilitaDTO d : agenda.getDisponibilita()) {
            righe.add(new AgendaEntryDTO(
                    "Disponibilità",
                    d.getData(),
                    d.getOra(),
                    ""
            ));
        }

        for (PrenotazioneDTO p : agenda.getPrenotazioni()) {
            String info = p.getAnimale().getNome() + " - " + p.getAnimale().getTipo();
            righe.add(new AgendaEntryDTO(
                    "Prenotazione",
                    p.getData(),
                    p.getOra(),
                    info
            ));
        }

        for (Visita v : agenda.getVisite()) {
            righe.add(new AgendaEntryDTO(
                    "Visita",
                    v.getData(),
                    v.getOra(),
                    v.getTipo().toString()
            ));
        }

        righe.sort(Comparator.comparing(AgendaEntryDTO::getData)
                .thenComparing(AgendaEntryDTO::getOra));

        return righe;
    }
}