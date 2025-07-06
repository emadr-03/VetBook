package it.unina.vetbook.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visita {

    private TipoVisita tipo;
    private String descrizione;
    private double costo;
    private LocalDate data;
    private LocalTime ora;
    private Farmaco farmaco;
    private AnimaleDomestico animale;

    public Visita(TipoVisita tipo, String descrizione, double costo) {}

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public TipoVisita getTipo() {
        return tipo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getCosto() {
        return costo;
    }

    public AnimaleDomestico getAnimale() {
        return animale;
    }

    public void prescrivi(Farmaco f) {
        this.farmaco = f;
    }
}

