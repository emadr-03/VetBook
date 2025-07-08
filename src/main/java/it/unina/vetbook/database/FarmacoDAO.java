package it.unina.vetbook.database;

import it.unina.vetbook.entity.Farmaco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmacoDAO implements GenericDAO<Farmaco, Integer> {

    @Override
    public void create(Farmaco entity) throws SQLException {
        String sql = "INSERT INTO Farmaco (nome, produttore) VALUES (?, ?)";
        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getNome());
            stmt.setString(2, entity.getProduttore());
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Farmaco> read(Integer id) throws SQLException {
        String sql = "SELECT * FROM Farmaco WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToFarmaco(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void update(Farmaco entity) throws SQLException {
        String sql = "UPDATE Farmaco SET nome = ?, produttore = ? WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getNome());
            stmt.setString(2, entity.getProduttore());
            stmt.setInt(3, entity.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM Farmaco WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Farmaco> readAll() throws SQLException {
        String sql = "SELECT * FROM Farmaco ORDER BY nome";
        List<Farmaco> farmaci = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                farmaci.add(mapResultSetToFarmaco(rs));
            }
        }
        return farmaci;
    }

    private Farmaco mapResultSetToFarmaco(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String produttore = rs.getString("produttore");
        Farmaco farmaco = new Farmaco(nome, produttore);
        farmaco.setId(id);
        return farmaco;
    }
}