package it.unina.vetbook.database;

import it.unina.vetbook.entity.Farmaco;
import it.unina.vetbook.entity.Visita;

import java.sql.*;

public class VisitaDAO implements GenericDAO<Visita, Integer> {

    private final Connection conn;

    public VisitaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void create(Visita visita) throws SQLException {
        String insertVisitaSQL = "INSERT INTO visite (tipo_visita, descrizione, costo, id_veterinario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertVisitaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, visita.getTipo().toString());
            ps.setString(2, visita.getDescrizione());
            ps.setDouble(3, visita.getCosto());
            ps.setInt(4, visita.getIdVeterinario());

            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    visita.setIdVisita(generatedKeys.getInt(1));
                }
            }
        }

        // Inserisci farmaci nella tabella associativa
        String insertFarmaciSQL = "INSERT INTO visite_farmaci (id_visita, id_farmaco) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertFarmaciSQL)) {
            for (Farmaco f : visita.getFarmaciPrescritti()) {
                ps.setInt(1, visita.getIdVisita());
                ps.setInt(2, f.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}
