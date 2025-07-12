package it.unina.vetbook.database;

import it.unina.vetbook.entity.Prenotazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDAO implements GenericDAO<Prenotazione, Integer> {

    private final Connection connection;

    public PrenotazioneDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Prenotazione p) throws SQLException {
        String sql = "INSERT INTO prenotazioni (id_proprietario, id_animale, data_prenotazione, ora_prenotazione) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, p.getIdProprietario());
            stmt.setLong(2, p.getAnimale().getCodiceChip());
            stmt.setDate(3, Date.valueOf(p.getData()));
            stmt.setTime(4, Time.valueOf(p.getOra()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        }
    }


    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM prenotazioni WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Prenotazione> executeQuery(String sql, Object... params) throws SQLException {
        List<Prenotazione> results = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapRow(rs));
                }
            }
        }
        return results;
    }

    private Prenotazione mapRow(ResultSet rs) throws SQLException {

        Prenotazione p = new Prenotazione(
                rs.getDate("data_prenotazione").toLocalDate(),
                rs.getTime("ora_prenotazione").toLocalTime(),
                null
        );
        p.setId(rs.getInt("id"));
        return p;
    }
}

