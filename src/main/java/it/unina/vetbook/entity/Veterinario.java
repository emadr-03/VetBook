package it.unina.vetbook.entity;

public class Veterinario extends Utente {

    protected Veterinario() {

    }

    @Override
    public boolean checkPassword(String password) {
        return false;
    }
}
