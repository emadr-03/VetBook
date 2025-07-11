package it.unina.vetbook.entity;


import java.time.LocalDate;
import java.time.LocalTime;

public class Prenotazione {

    private LocalDate data;
    private LocalTime ora;
    private AnimaleDomestico animale;
    private Proprietario proprietario;

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
}