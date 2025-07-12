package it.unina.vetbook.database;

import it.unina.vetbook.entity.Farmaco;

import java.sql.*;
import java.util.*;

public class FarmacoDAO implements GenericDAO<Farmaco, Integer> {

    private final Connection connection;

    public FarmacoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Farmaco f) throws SQLException {
        String sql = "INSERT INTO farmaci (nome, produttore) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getProduttore());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) f.setId(rs.getInt(1));
            }
        }
    }

    @Override
    public Optional<Farmaco> read(Integer[] key) throws SQLException {
        String sql = "SELECT * FROM farmaci WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, key[0]);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Farmaco f) throws SQLException {
        String sql = "UPDATE farmaci SET nome = ?, produttore = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getProduttore());
            stmt.setInt(3, f.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM farmaci WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Farmaco> executeQuery(String sql, Object... params) throws SQLException {
        List<Farmaco> results = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) stmt.setObject(i + 1, params[i]);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) results.add(mapRow(rs));
            }
        }
        return results;
    }

    private Farmaco mapRow(ResultSet rs) throws SQLException {
        Farmaco f = new Farmaco(rs.getString("nome"), rs.getString("produttore"));
        f.setId(rs.getInt("id"));
        return f;
    }
}
