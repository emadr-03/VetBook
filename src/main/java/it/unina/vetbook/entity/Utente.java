package it.unina.vetbook.entity;

import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.database.UtenteDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public abstract class Utente {

    protected int id;
    protected String username;
    protected String email;
    protected String password;
    protected UserRole ruolo;

    public Utente(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static Utente login(String username, String password) {
        try(Connection conn = DBManager.getInstance().getConnection()) {
            Optional<Utente> utente = new UtenteDAO(conn).read(new String[]{username, password});
            return utente.orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il login", e);
        }
    }

    public void registrati() {
        try(Connection conn = DBManager.getInstance().getConnection()) {
            new UtenteDAO(conn).create(this);
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella registrazione dell'utente", e);
        }
    }

    public static boolean exists(String username) {
        try(Connection conn = DBManager.getInstance().getConnection()) {
            Optional<Utente> u = new UtenteDAO(conn).readByUsername(username);
            return u.isPresent();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella verifica dell'esistenza dell'utente", e);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRuolo() {
        return ruolo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRuolo(UserRole ruolo) {
        this.ruolo = ruolo;
    }
}