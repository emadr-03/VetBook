package it.unina.vetbook.entity;

import java.time.LocalDate;

public class AnimaleDomestico {

    private int codiceChip;
    private String nome;
    private String tipo;
    private String razza;
    private String colore;
    private LocalDate dataDiNascita;
    private Proprietario proprietario;

    public AnimaleDomestico(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        this.codiceChip = codiceChip;
        this.nome = nome;
        this.tipo = tipo;
        this.razza = razza;
        this.colore = colore;
        this.dataDiNascita = dataDiNascita;
    }

    public boolean isVaccinato(){
        throw new UnsupportedOperationException("Not supported yet");
    }

    public int getCodiceChip() {
        return codiceChip;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
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


