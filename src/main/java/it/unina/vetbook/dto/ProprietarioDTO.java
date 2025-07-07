package it.unina.vetbook.dto;

import it.unina.vetbook.entity.AnimaleDomestico;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDTO {

    private String nome;
    private String cognome;
    private InputStream immagineProfilo;
    private List<AnimaleDomestico> animali;

    public ProprietarioDTO() {
        this.animali = new ArrayList<>();
    }


    public ProprietarioDTO(String cognome, InputStream immagineProfilo, List<AnimaleDomestico> animali, String nome) {
        this.cognome = cognome;
        this.immagineProfilo = immagineProfilo;
        this.animali = animali;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public InputStream getImmagineProfilo() {
        return immagineProfilo;
    }

    public List<AnimaleDomestico> getAnimali() {
        return animali;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setImmagineProfilo(InputStream immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public void setAnimali(List<AnimaleDomestico> animali) {
        this.animali = animali;
    }
}
