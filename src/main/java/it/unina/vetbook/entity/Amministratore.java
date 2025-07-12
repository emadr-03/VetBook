package it.unina.vetbook.entity;

public class Amministratore extends Utente {

    private final Agenda agenda = Agenda.getInstance();

    public Amministratore(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.AMMINISTRATORE;
    }

}
