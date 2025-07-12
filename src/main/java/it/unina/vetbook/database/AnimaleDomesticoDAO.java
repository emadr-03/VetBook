package it.unina.vetbook.database;

import it.unina.vetbook.entity.AnimaleDomestico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimaleDomesticoDAO extends GenericDAO<AnimaleDomestico, Integer> {

    public AnimaleDomesticoDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(AnimaleDomestico animale) throws SQLException {
        String sql = "INSERT INTO animali (codicechip, nome, tipo, razza, colore, data_nascita, idproprietario, vaccinazione) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, animale.getCodiceChip());
            stmt.setString(2, animale.getNome());
            stmt.setString(3, animale.getTipo());
            stmt.setString(4, animale.getRazza());
            stmt.setString(5, animale.getColore());
            stmt.setDate(6, Date.valueOf(animale.getDataDiNascita()));
            stmt.setInt(7, animale.getProprietario().getId());
            stmt.setDate(8, animale.getDataUltimaVaccinazione() != null ? Date.valueOf(animale.getDataUltimaVaccinazione()) : null);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<AnimaleDomestico> read(Integer[] codiceChip) throws SQLException {
        String sql = "SELECT * FROM animali WHERE codicechip = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, codiceChip[0]);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
        }
        return Optional.empty();
    }

    public List<AnimaleDomestico> readAll() throws SQLException {
        String sql = "SELECT * FROM animali";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<AnimaleDomestico> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapRow(rs));
            return lista;
        }
    }

    @Override
    public void update(AnimaleDomestico animale) throws SQLException {
        String sql = "UPDATE animali SET nome = ?, tipo = ?, razza = ?, colore = ?, data_nascita = ?, idproprietario = ?, vaccinazione = ? " +
                "WHERE codicechip = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animale.getNome());
            stmt.setString(2, animale.getTipo());
            stmt.setString(3, animale.getRazza());
            stmt.setString(4, animale.getColore());
            stmt.setDate(5, Date.valueOf(animale.getDataDiNascita()));
            stmt.setInt(6, animale.getProprietario().getId());
            stmt.setDate(7, animale.getDataUltimaVaccinazione() != null ? Date.valueOf(animale.getDataUltimaVaccinazione()) : null);
            stmt.setLong(8, animale.getCodiceChip());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer codiceChip) throws SQLException {
        String sql = "DELETE FROM animali WHERE codicechip = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, codiceChip);
            stmt.executeUpdate();
        }
    }

    public List<AnimaleDomestico> readByIdProprietario(int idProprietario) throws SQLException {
        String sql = "SELECT * FROM animali WHERE idproprietario = ?";
        return executeQuery(sql, idProprietario);
    }


    @Override
    protected AnimaleDomestico mapRow(ResultSet rs) throws SQLException {
        AnimaleDomestico a = new AnimaleDomestico(
                rs.getInt("codicechip"),
                rs.getString("nome"),
                rs.getString("tipo"),
                rs.getString("razza"),
                rs.getString("colore"),
                rs.getDate("data_nascita").toLocalDate()
        );
        Date vacc = rs.getDate("vaccinazione");
        if (vacc != null) a.setDataUltimaVaccinazione(vacc.toLocalDate());
        return a;
    }
}
