package it.unina.vetbook.database;

import it.unina.vetbook.entity.Prenotazione;
import it.unina.vetbook.entity.Visita;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class PrenotazioneDAO extends GenericDAO<Prenotazione, Integer> {

    public PrenotazioneDAO(Connection connection) {
        super(connection);
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
    protected Prenotazione mapRow(ResultSet rs) throws SQLException {
        Prenotazione p = new Prenotazione(
                rs.getDate("data_prenotazione").toLocalDate(),
                rs.getTime("ora_prenotazione").toLocalTime(),
                rs.getInt("id_animale"),
                rs.getInt("id_proprietario")
        );
        p.setId(rs.getInt("id"));
        return p;
    }

    @Override
    public Optional<Prenotazione> read(Integer[] key) throws SQLException {
        String sql = "SELECT * FROM prenotazioni WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, key[0]);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public List<Prenotazione> readAll() throws SQLException {
        return executeQuery("SELECT * FROM PRENOTAZIONI");
    }

    @Override
    public void update(Prenotazione entity) throws SQLException {
        throw new UnsupportedOperationException("update non supportato per PrenotazioneDAO");
    }
}
