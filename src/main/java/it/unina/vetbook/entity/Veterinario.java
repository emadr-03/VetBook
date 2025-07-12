package it.unina.vetbook.entity;


import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.database.VisitaDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Veterinario extends Utente {

    private final Agenda agenda = Agenda.getInstance();
    private List<Visita> visite;

    public Veterinario(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.VETERINARIO;
        this.visite = Visita.getVisiteVeterinario(this.id);
    }

    public void registraVisita(Visita v) {
        try(Connection conn = DBManager.getInstance().getConnection()){
            VisitaDAO visitaDAO = new VisitaDAO(conn);
            visitaDAO.create(v);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        agenda.getVisite().add(v);
    }

    public List<Visita> getVisite() {
        return visite;
    }

    public static Veterinario mockVet(){
        Veterinario mock = new Veterinario("dr_roberto", "email@outlook.it", "6e8feec77c620c73adf7c7f78e9bc9c1c4372195140a6d79225594b8961ab4f6");
        mock.setId(3);
        return mock;
    }
}
