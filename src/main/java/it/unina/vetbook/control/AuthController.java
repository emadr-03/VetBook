package it.unina.vetbook.control;

import it.unina.vetbook.entity.Proprietario;
import it.unina.vetbook.entity.UserRole;
import it.unina.vetbook.entity.Utente;
import it.unina.vetbook.entity.UtenteFactory;

import java.util.HashMap;
import java.util.Map;

public class AuthController {

    private static AuthController instance = null;
    private final Map<String, Utente> utentiMock;

    private AuthController() {
        // Creiamo un database di utenti mock in memoria
        utentiMock = new HashMap<>();

        // Aggiungiamo un utente proprietario di esempio per il login
        Utente p = UtenteFactory.creaProprietario("mario", "mario.rossi@email.com", "Mario", "Rossi", "password");
        utentiMock.put(p.getUsername(), p);
    }

    public static AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }

    public Utente login(String username, String password) {
        Utente utente = utentiMock.get(username);

        if (utente != null && utente.getPassword().equals(password)) {
            return utente;
        }
        throw new IllegalArgumentException("Credenziali non valide");
    }

    public Utente registrati(String username, String email, String nome, String cognome, String password) {
        if (utentiMock.containsKey(username)) {
            throw new IllegalStateException("Username già in uso.");
        }
        Utente nuovoUtente = UtenteFactory.creaProprietario(username, email, nome, cognome, password);
        utentiMock.put(username, nuovoUtente); // Aggiungiamo il nuovo utente alla nostra lista mock
        return nuovoUtente;
    }
}