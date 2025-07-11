package it.unina.vetbook.entity;

public class Amministratore extends Utente {


    public Amministratore(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.AMMINISTRATORE_DELL_AMBULATORIO;
    }
}
