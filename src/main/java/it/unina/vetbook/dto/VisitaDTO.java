package it.unina.vetbook.dto;

import it.unina.vetbook.entity.TipoVisita;
import it.unina.vetbook.entity.AnimaleDomestico;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record VisitaDTO(
        TipoVisita tipo,
        String descrizione,
        double costo
) {}
