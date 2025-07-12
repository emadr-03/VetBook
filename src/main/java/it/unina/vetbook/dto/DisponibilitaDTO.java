package it.unina.vetbook.dto;


import it.unina.vetbook.entity.Stato;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilitaDTO(
        int id, //L'id è presente nel DTO solo perchè alcune funzionalità del software sono mockate
        LocalDate data,
        LocalTime ora,
        Stato stato
){
    public DisponibilitaDTO(LocalDate data, LocalTime ora, Stato stato){
        this(0, data, ora, stato);
    }
}

