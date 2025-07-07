package it.unina.vetbook.dto;

import it.unina.vetbook.entity.Proprietario;

import java.time.LocalDate;

public class AnimaleDomesticoDTO {

    private int codiceChip;
    private String nome;
    private String tipo;
    private String razza;
    private String colore;
    private LocalDate dataDiNascita;
    private Proprietario proprietario;

    public AnimaleDomesticoDTO(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        this.codiceChip = codiceChip;
        this.nome = nome;
        this.tipo = tipo;
        this.razza = razza;
        this.colore = colore;
        this.dataDiNascita = dataDiNascita;
    }

    public int getCodiceChip() {
        return codiceChip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRazza() {
        return razza;
    }

    public String getColore() {
        return colore;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
}
