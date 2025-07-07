package it.unina.vetbook.dto;

import it.unina.vetbook.entity.Disponibilita;

import java.time.LocalDate;
import java.time.LocalTime;

public class DisponibilitaDTO {

    enum Stato{
        OCCUPATO,
        LIBERO;
    }

    private LocalDate data;
    private LocalTime ora;
    private DisponibilitaDTO.Stato stato;

    public DisponibilitaDTO(LocalDate data, LocalTime ora) {
        this.data = data;
        this.ora = ora;
        this.stato = DisponibilitaDTO.Stato.LIBERO;
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

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }
}
