package it.unina.vetbook.database;

import it.unina.vetbook.entity.*;

import java.sql.*;
import java.util.Optional;

public class UtenteDAO extends GenericDAO<Utente, Integer> {

    public UtenteDAO(Connection conn) {
        super(conn);
    }

    @Override
    public void create(Utente u) throws SQLException {
        String sql = "INSERT INTO utenti (username, email, password, ruolo, nome, cognome) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRuolo().name());

            if (u instanceof Proprietario p) {
                stmt.setString(5, p.getNome());
                stmt.setString(6, p.getCognome());
            } else {
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.VARCHAR);
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    u.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Optional<Utente> read(Integer[] key) throws SQLException {
        // Implementazione generica per read con chiave ID
        String sql = "SELECT * FROM utenti WHERE id = ?";
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

    public Optional<Utente> readLogin(String[] usernamePassword) throws SQLException {
        String sql = "SELECT * FROM utenti WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usernamePassword[0]);
            stmt.setString(2, usernamePassword[1]);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Utente> readByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM utenti WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    protected Utente mapRow(ResultSet rs) throws SQLException {
        String ruolo = rs.getString("ruolo");
        Utente u;

        switch (UserRole.valueOf(ruolo)) {
            case VETERINARIO:
                u = new Veterinario(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                break;
            case PROPRIETARIO:
                Proprietario p = new Proprietario(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                p.setNome(rs.getString("nome"));
                p.setCognome(rs.getString("cognome"));
                p.setImmagineProfilo(rs.getBytes("immagine_profilo"));
                u = p;
                break;
            case AMMINISTRATORE:
                u = new Amministratore(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                break;
            default:
                throw new IllegalStateException("Ruolo non gestito: " + ruolo);
        }

        u.setId(rs.getInt("id"));
        return u;
    }

    @Override
    public void update(Utente u) throws SQLException {
        String sql = "UPDATE utenti SET username = ?, email = ?, password = ?, ruolo = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRuolo().name());
            stmt.setInt(5, u.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM utenti WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void deleteProprietario(int idProprietario) {
        try (Connection conn = DBManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            AnimaleDomesticoDAO animaleDAO = new AnimaleDomesticoDAO(conn);
            String deleteAnimaliSQL = "DELETE FROM animali WHERE idproprietario = ?";
            try (PreparedStatement stmtAnimali = conn.prepareStatement(deleteAnimaliSQL)) {
                stmtAnimali.setInt(1, idProprietario);
                stmtAnimali.executeUpdate();
            }

            String deleteProprietarioSQL = "DELETE FROM utenti WHERE id = ?";
            try (PreparedStatement stmtProprietario = conn.prepareStatement(deleteProprietarioSQL)) {
                stmtProprietario.setInt(1, idProprietario);
                stmtProprietario.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la cancellazione del proprietario e dei suoi animali", e);
        }
    }
}
