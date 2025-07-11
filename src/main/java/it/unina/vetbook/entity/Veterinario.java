package it.unina.vetbook.entity;

import it.unina.vetbook.database.VisitaDAO;

import java.sql.SQLException;

public class Veterinario extends Utente {

    private final Agenda agenda = Agenda.getInstance();

    public Veterinario(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.VETERINARIO;
    }


    public void registraVisita(Visita v) {
        VisitaDAO visitaDAO = new VisitaDAO();
        try {
            visitaDAO.create(v);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        agenda.getVisite().add(v);
    }

}
