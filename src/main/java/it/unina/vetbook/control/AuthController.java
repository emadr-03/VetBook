package it.unina.vetbook.control;

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

    public Utente login(String username, String password) {
        Utente u = Utente.login(username, password);
        if (u == null) {
            throw new IllegalArgumentException("Credenziali non valide");
        }
        return u;
    }

    public void registrati(String username, String email, String nome, String cognome, String password) {
        // 1. Il Controller crea l'entità
        Utente nuovoUtente = UtenteFactory.creaProprietario(username, email, nome, cognome, password);

        // 2. Il Controller chiama il metodo dell'entità per la registrazione
        nuovoUtente.registrati();
    }
}