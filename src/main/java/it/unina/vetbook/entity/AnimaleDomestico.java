package it.unina.vetbook.entity;

import java.time.LocalDate;

public class AnimaleDomestico {

    private int codiceChip;
    private String nome;
    private String tipo;
    private String razza;
    private String colore;
    private LocalDate dataDiNascita;

    public AnimaleDomestico() {}

    public AnimaleDomestico(int codiceChip, String nome, String tipo, String razza,
                            String colore, LocalDate dataDiNascita) {
        this.codiceChip    = codiceChip;
        this.nome          = nome;
        this.tipo          = tipo;
        this.razza         = razza;
        this.colore        = colore;
        this.dataDiNascita = dataDiNascita;
    }

    // getters & setters
    public int getCodiceChip() { return codiceChip; }
    public void setCodiceChip(int codiceChip) { this.codiceChip = codiceChip; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getRazza() { return razza; }
    public void setRazza(String razza) { this.razza = razza; }

    public String getColore() { return colore; }
    public void setColore(String colore) { this.colore = colore; }

    public LocalDate getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(LocalDate dataDiNascita) { this.dataDiNascita = dataDiNascita; }
}
