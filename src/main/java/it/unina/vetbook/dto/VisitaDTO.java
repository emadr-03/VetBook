package it.unina.vetbook.dto;

import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.boundary.TipoVisita;

import java.time.LocalDate;
import java.time.LocalTime;
public class VisitaDTO {

    private TipoVisita tipo;
    private String descrizione;
    private double costo;
    private LocalDate data;
    private LocalTime ora;
    private AnimaleDomestico animale;

    public VisitaDTO(TipoVisita tipo, String descrizione, double costo) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.costo = costo;
    }

    public TipoVisita getTipo() {
        return tipo;
    }

    public void setTipo(TipoVisita tipo) {
        this.tipo = tipo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getCosto() {
        return costo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public AnimaleDomestico getAnimale() {
        return animale;
    }

    public void setAnimale(AnimaleDomestico animale) {
        this.animale = animale;
    }
}
