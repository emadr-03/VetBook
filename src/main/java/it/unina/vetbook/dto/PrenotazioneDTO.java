package it.unina.vetbook.dto;

import it.unina.vetbook.entity.Proprietario;

import java.time.LocalDate;
import java.time.LocalTime;

public record PrenotazioneDTO(
        LocalDate data,
        LocalTime ora,
        AnimaleDomesticoDTO animale,
        int idProprietario
) {

    public PrenotazioneDTO(LocalDate data, LocalTime ora, AnimaleDomesticoDTO animale) {
        this(data, ora, animale, animale != null ? animale.idProprietario() : null);
    }
}
