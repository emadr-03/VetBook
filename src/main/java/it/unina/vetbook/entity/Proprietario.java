package it.unina.vetbook.entity;

import java.io.InputStream;
import java.util.List;

public class Proprietario extends Utente {

    private String nome;
    private String cognome;
    private InputStream immagineProfilo;
    private List<AnimaleDomestico> animali;

    protected Proprietario() {}

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

    @Override
    public boolean checkPassword(String password) {
        return false;
    }
}

