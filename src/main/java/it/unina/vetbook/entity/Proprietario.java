package it.unina.vetbook.entity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Proprietario extends Utente {

    private String nome;
    private String cognome;
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

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    @Override
    public boolean checkPassword(String password) {
        return false;
    }
}