package it.unina.vetbook.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Visita {

    private int idVisita;
    private int idVeterinario;
    private TipoVisita tipo;
    private String descrizione;
    private double costo;
    private LocalDate data;
    private LocalTime ora;
    private List<Farmaco> farmaciPrescritti;

    public Visita(TipoVisita tipo, String descrizione, double costo, int idVeterinario) {
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.costo = costo;
        this.idVeterinario = idVeterinario;
        this.farmaciPrescritti = new ArrayList<>();
    }

    public void prescrivi(Farmaco f) {
        farmaciPrescritti.add(f);
    }

    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public TipoVisita getTipo() { return tipo; }
    public String getDescrizione() { return descrizione; }
    public double getCosto() { return costo; }
    public void setData(LocalDate data) { this.data = data; }
    public void setOra(LocalTime ora) { this.ora = ora; }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public void setTipo(TipoVisita tipo) {
        this.tipo = tipo;
    }


    public List<Farmaco> getFarmaciPrescritti() {
        return farmaciPrescritti;
    }


    public int getIdVisita() {
        return idVisita;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }


}