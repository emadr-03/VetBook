package it.unina.vetbook.entity;

import it.unina.vetbook.database.UtenteDAO;

import java.sql.SQLException;
import java.util.Optional;

public abstract class Utente {

    protected String username;
    protected String email;
    protected String password;
    protected UserRole ruolo;

    public abstract boolean checkPassword(String password);

    public static Utente login(String username, String password) {
        try {
            Optional<Utente> utente = new UtenteDAO().read(username, password);
            return utente.orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il login", e);
        }
    }


    public void registrati() {
        try {
            new UtenteDAO().create(this);
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella registrazione dell'utente", e);
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

    public void setRuolo(UserRole ruolo) {
        this.ruolo = ruolo;
    }

}
