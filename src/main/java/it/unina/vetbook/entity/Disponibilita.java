package it.unina.vetbook.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilita {

    enum Stato{
        OCCUPATO,
        LIBERO;
    }

    private LocalDate data;
    private LocalTime ora;
    private Stato stato;

    public Disponibilita(LocalDate data, LocalTime ora) {}

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public String getStato() {
        return stato.toString();
    }
}

