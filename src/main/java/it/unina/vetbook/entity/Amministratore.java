package it.unina.vetbook.entity;

public class Amministratore extends Utente {

    protected Amministratore() {

    }

    @Override
    public boolean checkPassword(String password) {
        return false;
    }
}
