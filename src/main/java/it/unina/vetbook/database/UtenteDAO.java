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
        String sql = "INSERT INTO utenti (username, email, password, ruolo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRuolo().name());
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
}
