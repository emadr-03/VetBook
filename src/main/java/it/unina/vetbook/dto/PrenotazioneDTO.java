package it.unina.vetbook.dto;

import it.unina.vetbook.entity.Proprietario;

import java.time.LocalDate;
import java.time.LocalTime;

public class PrenotazioneDTO {

    private LocalDate data;
    private LocalTime ora;
    private AnimaleDomesticoDTO animale;
    private Proprietario proprietario;

    public PrenotazioneDTO(LocalDate data, LocalTime ora, AnimaleDomesticoDTO animale) {
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

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public AnimaleDomesticoDTO getAnimale() {
        return animale;
    }

    public void setAnimale(AnimaleDomesticoDTO animale) {
        this.animale = animale;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
}
