package it.unina.vetbook.dto;

import it.unina.vetbook.entity.AnimaleDomestico;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDTO {

    private String username;
    private String email;
    private String nome;
    private String cognome;
    private InputStream immagineProfilo;
    private List<AnimaleDomestico> animali;

    public ProprietarioDTO() {
        this.animali = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public List<AnimaleDomestico> getAnimali() {
        return animali;
    }
}