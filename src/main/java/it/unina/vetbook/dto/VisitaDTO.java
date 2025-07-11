package it.unina.vetbook.dto;

import it.unina.vetbook.entity.TipoVisita;
import it.unina.vetbook.entity.AnimaleDomestico;

import java.time.LocalDate;
import java.time.LocalTime;

public record VisitaDTO(
        TipoVisita tipo,
        String descrizione,
        double costo,
        LocalDate data,
        LocalTime ora,
        AnimaleDomestico animale
) {
    public VisitaDTO(TipoVisita tipo, String descrizione, double costo) {
        this(tipo, descrizione, costo, null, null, null);
    }
}
