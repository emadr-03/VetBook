package it.unina.vetbook.dto;

import java.time.LocalDate;
import java.time.LocalTime;


public class AgendaDTO {

    private final String tipoEvento;
    private final LocalDate data;
    private final LocalTime ora;
    private final String descrizione;

    public AgendaDTO(String tipoEvento, LocalDate data, LocalTime ora, String descrizione) {
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