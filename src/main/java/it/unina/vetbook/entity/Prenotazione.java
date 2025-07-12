package it.unina.vetbook.entity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Prenotazione {

    private int id;
    private Proprietario proprietario;
    private LocalDate data;
    private LocalTime ora;
    private AnimaleDomestico animale;


    public Prenotazione(LocalDate data, LocalTime ora, AnimaleDomestico animale) {
        this.data = data;
        this.ora = ora;
        this.animale = animale;
        if (animale != null) {
            this.proprietario = animale.getProprietario();
        }
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

    public Proprietario getProprietario() {
        return proprietario;
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

}