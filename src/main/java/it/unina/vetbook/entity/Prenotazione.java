package it.unina.vetbook.entity;


import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {

    private int id;
    private int idProprietario;
    private LocalDate data;
    private LocalTime ora;
    private int idAnimale;
    private AnimaleDomestico animale;


    public Prenotazione(LocalDate data, LocalTime ora, AnimaleDomestico animale) {
        this.data = data;
        this.ora = ora;
        this.animale = animale;
        if (animale != null) {
            this.idProprietario = animale.getIdProprietario();
        }
    }

    public Prenotazione(LocalDate data, LocalTime ora, int idAnimale, int idProprietario) {
        this.data = data;
        this.ora = ora;
        this.idAnimale = idAnimale;
        this.idProprietario = idProprietario;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public AnimaleDomestico getAnimale() {
        return animale;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnimale() {
        return idAnimale;
    }

    public void setAnimale(AnimaleDomestico animale) {
        this.animale = animale;
    }
}