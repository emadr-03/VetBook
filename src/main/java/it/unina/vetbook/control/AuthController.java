package it.unina.vetbook.control;

import it.unina.vetbook.database.UtenteDAO;
import it.unina.vetbook.entity.Utente;
import it.unina.vetbook.entity.UtenteFactory;

import java.sql.SQLException;
import java.util.Optional;

public class AuthController {

    private static AuthController instance = null;
    private final UtenteDAO utenteDAO;

    private AuthController() {
        this.utenteDAO = new UtenteDAO();
    }

    public static AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }

    public Utente login(String username, String password) {
        try {
            // È il Controller che chiama il DAO
            Optional<Utente> utente = utenteDAO.read(username, password);
            return utente.orElseThrow(() -> new IllegalArgumentException("Credenziali non valide"));
        } catch (SQLException e) {
            throw new RuntimeException("Errore di sistema durante il login", e);
        }
    }

    public void registrati(String username, String email, String nome, String cognome, String password) {
        // 1. Il Controller crea l'entità tramite la Factory
        Utente nuovoUtente = UtenteFactory.creaProprietario(username, email, nome, cognome, password);

        // 2. Il Controller chiama il DAO per salvare l'entità
        try {
            utenteDAO.create(nuovoUtente);
        } catch (SQLException e) {
            // Gestisce errori specifici del DB, come username duplicato
            throw new RuntimeException("Errore nella registrazione: " + e.getMessage(), e);
        }
    }
}