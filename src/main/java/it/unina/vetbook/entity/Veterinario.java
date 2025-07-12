package it.unina.vetbook.entity;


public class Veterinario extends Utente {

    private final Agenda agenda = Agenda.getInstance();

    public Veterinario(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.VETERINARIO;
    }


}
