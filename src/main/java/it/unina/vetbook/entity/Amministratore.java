package it.unina.vetbook.entity;

import java.util.List;

public class Amministratore extends Utente {

    private final Agenda agenda = Agenda.getInstance();

    public Amministratore(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.AMMINISTRATORE;
    }

    public double ottieniIncasso(List<Visita> visite) {
        return agenda.visualizzaVisiteGiornaliere().stream().mapToDouble(Visita::getCosto).sum();
    }
}
