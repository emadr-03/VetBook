package it.unina.vetbook.dto;

import it.unina.vetbook.entity.Proprietario;

import java.time.LocalDate;

public record AnimaleDomesticoDTO(
        int codiceChip,
        String nome,
        String tipo,
        String razza,
        String colore,
        LocalDate dataDiNascita,
        int idProprietario
) {
    public AnimaleDomesticoDTO(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        this(codiceChip, nome, tipo, razza, colore, dataDiNascita, 1);
    }
}
