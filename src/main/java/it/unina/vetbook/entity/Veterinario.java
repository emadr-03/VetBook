package it.unina.vetbook.entity;


import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.database.VisitaDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Veterinario extends Utente {

    private final Agenda agenda = Agenda.getInstance();
    private List<Visita> visite;

    public Veterinario(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.VETERINARIO;
        this.visite = new ArrayList<>();
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
        return Visita.getVisiteVeterinario(this.id);
    }

    public static Veterinario mockVet(){
        Veterinario mock = new Veterinario("dr_roberto", "email@outlook.it", "veterinario");
        mock.setId(3);
        return mock;
    }
}
