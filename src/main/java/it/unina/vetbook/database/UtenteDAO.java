package it.unina.vetbook.database;

import it.unina.vetbook.entity.*;

import java.sql.*;
import java.util.Optional;

public class UtenteDAO implements GenericDAO<Utente, String> {

    public void create(Utente u) throws SQLException {
        String sql = "INSERT INTO Utente (username, email, password, ruolo, nome, cognome) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        }
    }

    @Override
    public void update(Utente u) throws SQLException {
        String sql = "UPDATE Utente SET email = ?, password = ?, nome = ?, cognome = ? WHERE username = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getPassword());

            if (u instanceof Proprietario p) {
                stmt.setString(3, p.getNome());
                stmt.setString(4, p.getCognome());
            } else {
                stmt.setNull(3, Types.VARCHAR);
                stmt.setNull(4, Types.VARCHAR);
            }
            stmt.setString(5, u.getUsername());
            stmt.executeUpdate();
        }
    }

    public Optional<Utente> read(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Utente WHERE username = ? AND password = ?";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String ruoloStr = rs.getString("ruolo");
                    UserRole ruolo = UserRole.valueOf(ruoloStr);

                    Utente u;

                    if (ruolo == UserRole.PROPRIETARIO) {
                        Proprietario p = new Proprietario();
                        p.setNome(rs.getString("nome"));
                        p.setCognome(rs.getString("cognome"));
                        u = p;
                    } else if (ruolo == UserRole.VETERINARIO) {
                        u = new Veterinario();
                    } else {
                        u = new Amministratore();
                    }

                    u.setUsername(username);
                    u.setPassword(password);
                    u.setEmail(rs.getString("email"));
                    u.setRuolo(ruolo);

                    return Optional.of(u);
                }
            }
        }
        return Optional.empty();
    }
}