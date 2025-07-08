package it.unina.vetbook.dto;

public class FarmacoDTO {

    private final int id;
    private final String nome;
    private final String produttore;

    public FarmacoDTO(int id, String nome, String produttore) {
        this.id = id;
        this.nome = nome;
        this.produttore = produttore;
    }

    public String getNome() {
        return nome;
    }

    public String getProduttore() {
        return produttore;
    }

    @Override
    public String toString() {
        return nome + " (" + produttore + ")";
    }
}