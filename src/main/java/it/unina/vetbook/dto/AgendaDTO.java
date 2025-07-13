package it.unina.vetbook.dto;

import java.time.LocalDate;
import java.time.LocalTime;


public record AgendaDTO(String tipoEvento, LocalDate data, LocalTime ora, String descrizione) {

     public AgendaDTO(String tipoEvento, LocalDate data, LocalTime ora){
        this(tipoEvento, data, ora, "");
    }
}