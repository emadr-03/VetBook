package it.unina.vetbook.dto;

public class AnimaleDTO {
    public final int codiceChip;
    public final String nome;
    public final String tipo;
    public final String razza;
    public final String colore;
    public final String dataNascita;

    public AnimaleDTO(int codiceChip, String nome, String tipo, String razza, String colore, String dataNascita) {
        this.codiceChip = codiceChip;
        this.nome = nome;
        this.tipo = tipo;
        this.razza = razza;
        this.colore = colore;
        this.dataNascita = dataNascita;
    }
}