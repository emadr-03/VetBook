package it.unina.vetbook.control;

import it.unina.vetbook.entity.UserRole;
import it.unina.vetbook.entity.Utente;
import it.unina.vetbook.entity.UtenteFactory;

public class AuthController {

    private static AuthController instance = null;

    private AuthController() {}

    public static AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }

    public UserRole login(String username, String password) {
        Utente utente = Utente.login(username, password);
        if (utente == null)
            throw new IllegalArgumentException("Credenziali non valide");
        return utente.getRuolo();
    }

    public UserRole registrati(String username, String email, String nome, String cognome, String password) {
        Utente nuovoUtente = UtenteFactory.creaProprietario(username, email, nome, cognome, password);
        nuovoUtente.registrati();
        return nuovoUtente.getRuolo();
    }
}
