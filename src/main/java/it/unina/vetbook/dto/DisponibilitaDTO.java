package it.unina.vetbook.dto;


import it.unina.vetbook.entity.Stato;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilitaDTO(
        LocalDate data,
        LocalTime ora,
        Stato stato
){}

