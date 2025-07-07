package it.unina.vetbook.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Visita {

    private TipoVisita tipo;
    private String descrizione;
    private double costo;
    private LocalDate data;
    private LocalTime ora;
    private List<Farmaco> farmaciPrescritti;
    private AnimaleDomestico animale;

    public Visita(TipoVisita tipo, String descrizione, double costo) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.costo = costo;
        this.farmaciPrescritti = new ArrayList<>();
    }

    public void prescrivi(Farmaco f) {
        if (this.farmaciPrescritti == null) {
            this.farmaciPrescritti = new ArrayList<>();
        }
        this.farmaciPrescritti.add(f);
    }

    // Getters e Setters...
    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public TipoVisita getTipo() { return tipo; }
    public String getDescrizione() { return descrizione; }
    public double getCosto() { return costo; }
    public AnimaleDomestico getAnimale() { return animale; }
    public List<Farmaco> getFarmaciPrescritti() { return farmaciPrescritti; }
    public void setAnimale(AnimaleDomestico animale) { this.animale = animale; }
    public void setData(LocalDate data) { this.data = data; }
    public void setOra(LocalTime ora) { this.ora = ora; }
}