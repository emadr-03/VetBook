package it.unina.vetbook.dto;

import java.time.LocalDate;
import java.time.LocalTime;


/*Lo scopo di questa classe è quello di rappresentare una singola e generica "entry" della tabella di visualizzazione.
Grazie a questo DTO, il Controller può creare una lista omogenea (List<AgendaEntryDTO>) mescolando al suo interno disponibilità, prenotazioni e visite.
Passando questa lista alla Boundary, rispettiamo pienamente la separazione dei layer: il Controller fornisce i dati strutturati, e la Boundary si occupa solo di come disegnarli in una tabella, senza dover sapere cosa sia una PrenotazioneDTO o una Visita.
 */
public class AgendaEntryDTO {

    private final String tipoEvento;
    private final LocalDate data;
    private final LocalTime ora;
    private final String descrizione;

    public AgendaEntryDTO(String tipoEvento, LocalDate data, LocalTime ora, String descrizione) {
        this.tipoEvento = tipoEvento;
        this.data = data;
        this.ora = ora;
        this.descrizione = descrizione;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public String getDescrizione() {
        return descrizione;
    }
}