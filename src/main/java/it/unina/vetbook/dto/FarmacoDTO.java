package it.unina.vetbook.dto;

public record FarmacoDTO(int id, String nome, String produttore) {

    @Override
    public String toString() {
        return nome + " (" + produttore + ")";
    }
}