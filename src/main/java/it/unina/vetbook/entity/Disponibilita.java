package it.unina.vetbook.entity;

import it.unina.vetbook.dto.DisponibilitaDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilita {

    private LocalDate data;
    private LocalTime ora;
    private Stato stato;

    public Disponibilita(LocalDate data, LocalTime ora) {
        this.data = data;
        this.ora = ora;
        this.stato = Stato.LIBERO;
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
}