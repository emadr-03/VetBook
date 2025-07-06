package it.unina.vetbook.entity;

public class Veterinario extends Utente {

    public Veterinario() {

    }

    @Override
    public boolean checkPassword(String password) {
        return false;
    }
}
