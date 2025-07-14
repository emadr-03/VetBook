package it.unina.vetbook.database;

import it.unina.vetbook.entity.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class VisitaDAO extends GenericDAO<Visita, Integer> {

    public VisitaDAO(Connection conn) {
        super(conn);
    }

    @Override
    public void create(Visita visita) throws SQLException {
        String insertVisitaSQL = "INSERT INTO visite (tipo_visita, descrizione, costo, data_visita, ora_visita, id_veterinario) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertVisitaSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, visita.getTipo().toString());
            ps.setString(2, visita.getDescrizione());
            ps.setDouble(3, visita.getCosto());
            ps.setDate(4, Date.valueOf(visita.getData()));
            ps.setTime(5, Time.valueOf(visita.getOra()));
            ps.setInt(6, visita.getIdVeterinario());

            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    visita.setIdVisita(generatedKeys.getInt(1));
                }
            }
        }

        String insertFarmaciSQL = "INSERT INTO farmaci_has_visite (visite_id, farmaci_id) VALUES (?, ?)";
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

    public List<Visita> readAll() throws SQLException {
        return executeQuery("SELECT * FROM VISITE");
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
        int idVisita = rs.getInt("id");
        String tipoVisitaStr = rs.getString("tipo_visita");
        String descrizione = rs.getString("descrizione");
        double costo = rs.getDouble("costo");
        Date dataSql = rs.getDate("data_visita");
        Time oraSql = rs.getTime("ora_visita");
        int idVeterinario = rs.getInt("id_veterinario");

        TipoVisita tipo = TipoVisita.valueOf(tipoVisitaStr);
        LocalDate data = dataSql != null ? dataSql.toLocalDate() : null;
        LocalTime ora = oraSql != null ? oraSql.toLocalTime() : null;

        Visita visita = new Visita(tipo, descrizione, costo, data, ora, idVeterinario);
        visita.setIdVisita(idVisita);

        return visita;
    }


}
