package it.unina.vetbook.dto;

import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Farmaco;
import it.unina.vetbook.entity.TipoVisita;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VisitaDTO {

    private TipoVisita tipo;
    private String descrizione;
    private double costo;
    private LocalDate data;
    private LocalTime ora;
    private List<Farmaco> farmaciPrescritti;
    private AnimaleDomestico animale;

    public VisitaDTO(TipoVisita tipo, String descrizione, double costo, LocalDate data, LocalTime ora, List<Farmaco> farmaciPrescritti, AnimaleDomestico animale) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.costo = costo;
        this.data = data;
        this.ora = ora;
        this.farmaciPrescritti = farmaciPrescritti;
        this.animale = animale;
    }

    public VisitaDTO(TipoVisita tipo, String descrizione, double costo) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.costo = costo;
        this.farmaciPrescritti = new ArrayList<>();
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

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
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

    public List<Farmaco> getFarmaciPrescritti() {
        return farmaciPrescritti;
    }

    public void setFarmaciPrescritti(List<Farmaco> farmaciPrescritti) {
        this.farmaciPrescritti = farmaciPrescritti;
    }

    public AnimaleDomestico getAnimale() {
        return animale;
    }

    public void setAnimale(AnimaleDomestico animale) {
        this.animale = animale;
    }
}
