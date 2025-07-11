package it.unina.vetbook.database;

import it.unina.vetbook.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UtenteDAO implements GenericDAO<Utente, String> {

    @Override
    public Optional<Utente> read(String[] key) throws SQLException {
        Utente mockUtente = new Veterinario(key[0], "email", key[1]);
        return Optional.of(mockUtente);
    }

    public Optional<Utente> readByUsername(String username) throws SQLException {
        List<Utente> utenti = executeQuery("SELECT * FROM Utente WHERE username = '" + username + "'");
        return utenti.isEmpty() ? Optional.empty() : Optional.of(utenti.get(0));
    }

    @Override
    public List<Utente> executeQuery(String sql, Object... params) throws SQLException {
        return new ArrayList<>();
    }
}