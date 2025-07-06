package it.unina.vetbook.database;

import it.unina.vetbook.entity.AnimaleDomestico;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AnimaleDAO implements GenericDAO<AnimaleDomestico, String> {

    private static final String INSERT =
            "INSERT INTO animale (codice_chip, nome, tipo, razza, colore, data_nascita, id_proprietario) " +
                    "VALUES (?,?,?,?,?,?,?)";

    private static final String SELECT =
            "SELECT * FROM animale WHERE codice_chip = ?";

    private static final String UPDATE =
            "UPDATE animale SET nome = ?, tipo = ?, razza = ?, colore = ?, data_nascita = ?, id_proprietario = ? " +
                    "WHERE codice_chip = ?";

    private static final String DELETE =
            "DELETE FROM animale WHERE codice_chip = ?";

    private final DBManager db = DBManager.getInstance();

    @Override
    public void create(AnimaleDomestico a) throws SQLException {
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT)) {

            // ps.setString(1, a.getCodiceChip());
            ps.setString(2, a.getNome());
            ps.setString(3, a.getTipo());
            ps.setString(4, a.getRazza());
            ps.setString(5, a.getColore());
            ps.setDate  (6, Date.valueOf(a.getDataDiNascita()));
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<AnimaleDomestico> read(String chip) throws SQLException {
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT)) {

            ps.setString(1, chip);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public void update(AnimaleDomestico a) throws SQLException {
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE)) {

            ps.setString(1, a.getNome());
            ps.setString(2, a.getTipo());
            ps.setString(3, a.getRazza());
            ps.setString(4, a.getColore());
            ps.setDate  (5, Date.valueOf(a.getDataDiNascita()));
            //ps.setString(6, a.getCodiceChip());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String chip) throws SQLException {
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE)) {

            ps.setString(1, chip);
            ps.executeUpdate();
        }
    }

    @Override
    public List<AnimaleDomestico> executeQuery(String sql, Object... params) throws SQLException {
        List<AnimaleDomestico> out = new ArrayList<>();
        try (Connection c = db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) { ps.setObject(i + 1, params[i]); }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { out.add(mapRow(rs)); }
            }
        }
        return out;
    }

    /* ====== helper =================================================== */
    private AnimaleDomestico mapRow(ResultSet rs) throws SQLException {
        AnimaleDomestico a = new AnimaleDomestico();
        //a.setCodiceChip (rs.getString("codice_chip"));
        a.setNome       (rs.getString("nome"));
        a.setTipo       (rs.getString("tipo"));
        a.setRazza      (rs.getString("razza"));
        a.setColore     (rs.getString("colore"));
        a.setDataDiNascita(rs.getDate  ("data_nascita").toLocalDate());
        // proprietario da caricare a parte (lazy) con ProprietarioDAO
        return a;
    }
}

