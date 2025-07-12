package it.unina.vetbook.database;

import it.unina.vetbook.entity.Disponibilita;
import it.unina.vetbook.entity.Stato;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DisponibilitaDAO implements GenericDAO<Disponibilita, Integer> {

    private final Connection connection;

    public DisponibilitaDAO(Connection connection) {
        this.connection = connection;
    }

    public void create(Disponibilita d) throws SQLException {
        String sql = "INSERT INTO disponibilita (data_visita, ora_visita, stato) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(d.getData()));
            stmt.setTime(2, Time.valueOf(d.getOra()));
            stmt.setString(3, d.getStato().name().toLowerCase());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    d.setId(rs.getInt(1));
                }
            }
        }
    }


    @Override
    public Optional<Disponibilita> read(Integer[] key) throws SQLException {
        String sql = "SELECT * FROM disponibilita WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, key[0]);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Disponibilita d) throws SQLException {
        String sql = "UPDATE disponibilita SET data_visita=?, ora_visita=?, stato=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(d.getData()));
            stmt.setTime(2, Time.valueOf(d.getOra()));
            stmt.setString(3, d.getStato().name().toLowerCase());
            stmt.setInt(4, d.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM disponibilita WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Disponibilita> executeQuery(String sql, Object... params) throws SQLException {
        List<Disponibilita> results = new ArrayList<>();
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

    private Disponibilita mapRow(ResultSet rs) throws SQLException {
        Disponibilita d = new Disponibilita(
                rs.getDate("data_visita").toLocalDate(),
                rs.getTime("ora_visita").toLocalTime()
        );
        d.setId(rs.getInt("id"));
        d.setStato(Stato.valueOf(rs.getString("stato").toUpperCase()));
        return d;
    }
}
