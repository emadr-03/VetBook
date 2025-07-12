package it.unina.vetbook.entity;

import java.time.LocalDate;

public class AnimaleDomestico {

    private int codiceChip;
    private int idProprietario;
    private String nome;
    private String tipo;
    private String razza;
    private String colore;
    private LocalDate dataDiNascita;
    private LocalDate dataUltimaVaccinazione;


    public AnimaleDomestico(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        this.codiceChip = codiceChip;
        this.nome = nome;
        this.tipo = tipo;
        this.razza = razza;
        this.colore = colore;
        this.dataDiNascita = dataDiNascita;
    }

    public AnimaleDomestico(int codiceChip, int idProprietario, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        this.codiceChip = codiceChip;
        this.idProprietario = idProprietario;
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


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setRazza(String razza) {
        this.razza = razza;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }

    public LocalDate getDataUltimaVaccinazione() {
        return dataUltimaVaccinazione;
    }

    public void setDataUltimaVaccinazione(LocalDate dataUltimaVaccinazione) {
        this.dataUltimaVaccinazione = dataUltimaVaccinazione;
    }
}


