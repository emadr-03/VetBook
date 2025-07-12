package it.unina.vetbook.database;

import it.unina.vetbook.entity.Farmaco;
import it.unina.vetbook.entity.Visita;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class VisitaDAO extends GenericDAO<Visita, Integer> {

    public VisitaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public void create(Visita visita) throws SQLException {
        String insertVisitaSQL = "INSERT INTO visite (tipo_visita, descrizione, costo, id_veterinario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertVisitaSQL, Statement.RETURN_GENERATED_KEYS)) {
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

        String insertFarmaciSQL = "INSERT INTO visite_farmaci (id_visita, id_farmaco) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertFarmaciSQL)) {
            for (Farmaco f : visita.getFarmaciPrescritti()) {
                ps.setInt(1, visita.getIdVisita());
                ps.setInt(2, f.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public List<Visita> readAllByVet(int idVet) throws SQLException {
        String sql = "SELECT * FROM visite WHERE id_veterinario = ?";
        return executeQuery(sql, idVet);
    }


    @Override
    public Optional<Visita> read(Integer[] key) throws SQLException {
        throw new UnsupportedOperationException("read() non implementato per VisitaDAO.");
    }

    @Override
    public void update(Visita entity) throws SQLException {
        throw new UnsupportedOperationException("update() non implementato per VisitaDAO.");
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("delete() non implementato per VisitaDAO.");
    }

    @Override
    protected Visita mapRow(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("mapRow() non implementato per VisitaDAO.");
    }
}
