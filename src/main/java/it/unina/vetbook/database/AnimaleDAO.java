package it.unina.vetbook.database;

import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Proprietario;

import java.sql.*;
import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimaleDAO implements GenericDAO<AnimaleDomestico, Integer> {

    @Override
    public void create(AnimaleDomestico entity) throws SQLException {
        String sql = "INSERT INTO Animale (codiceChip, nome, tipo, razza, colore, dataDiNascita) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entity.getCodiceChip());
            stmt.setString(2, entity.getNome());
            stmt.setString(3, entity.getTipo());
            stmt.setString(4, entity.getRazza());
            stmt.setString(5, entity.getColore());
            stmt.setDate(6, Date.valueOf(entity.getDataDiNascita()));

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<AnimaleDomestico> read(Integer codiceChip) throws SQLException {
        String sql = "SELECT * FROM Animale WHERE codiceChip = ?";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codiceChip);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    AnimaleDomestico animale = mapResultSetToAnimale(rs);
                    return Optional.of(animale);
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public void update(AnimaleDomestico entity) throws SQLException {
        String sql = "UPDATE Animale SET nome = ?, tipo = ?, razza = ?, colore = ?, dataDiNascita = ? WHERE codiceChip = ?";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, entity.getNome());
            stmt.setString(2, entity.getTipo());
            stmt.setString(3, entity.getRazza());
            stmt.setString(4, entity.getColore());
            stmt.setDate(5, Date.valueOf(entity.getDataDiNascita()));
            stmt.setInt(6, entity.getCodiceChip());

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codiceChip) throws SQLException {
        String sql = "DELETE FROM Animale WHERE codiceChip = ?";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codiceChip);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<AnimaleDomestico> executeQuery(String sql, Object... params) throws SQLException {
        List<AnimaleDomestico> result = new ArrayList<>();

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapResultSetToAnimale(rs));
                }
            }
        }

        return result;
    }

    private AnimaleDomestico mapResultSetToAnimale(ResultSet rs) throws SQLException {
        int codiceChip = rs.getInt("codiceChip");
        String nome = rs.getString("nome");
        String tipo = rs.getString("tipo");
        String razza = rs.getString("razza");
        String colore = rs.getString("colore");
        LocalDate dataDiNascita = rs.getDate("dataDiNascita").toLocalDate();
        String usernameProprietario = rs.getString("usernameProprietario");

        // 1. Crea animale
        AnimaleDomestico animale = new AnimaleDomestico(codiceChip, nome, tipo, razza, colore, dataDiNascita);

        // 2. Setta proprietario (puoi creare un oggetto minimo o usare un DAO)
        Proprietario proprietario = new Proprietario();
        proprietario.setUsername(usernameProprietario);
        animale.setProprietario(proprietario);

        return animale;
    }
}
