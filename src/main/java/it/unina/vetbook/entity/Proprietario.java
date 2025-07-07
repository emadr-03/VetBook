package it.unina.vetbook.entity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Proprietario extends Utente {

    private String nome;
    private String cognome;
    private InputStream immagineProfilo;
    private List<AnimaleDomestico> animali;

    public Proprietario() {
        this.animali = new ArrayList<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<AnimaleDomestico> getAnimali() {
        return animali;
    }

    public AnimaleDomestico getAnimaleByCodiceChip(int codiceChip){
        return this.getAnimali().stream()
                .filter(a -> a.getCodiceChip() == codiceChip)
                .findFirst()
                .orElse(null);
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

    @Override
    public boolean checkPassword(String password) {
        return false;
    }
}