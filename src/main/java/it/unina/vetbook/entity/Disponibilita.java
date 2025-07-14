package it.unina.vetbook.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilita {

    private int id;
    private LocalDate data;
    private LocalTime ora;
    private Stato stato;

    public Disponibilita(LocalDate data, LocalTime ora) {
        this.data = data;
        this.ora = ora;
        this.stato = Stato.LIBERA;
    }

    public Disponibilita(int id, LocalDate data, LocalTime ora) {
        this.id = id;
        this.data = data;
        this.ora = ora;
        this.stato = Stato.PRENOTATA;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public Stato getStato() {
        return stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}