package it.unina.vetbook.control;

import it.unina.vetbook.utils.LoginResult;
import it.unina.vetbook.utils.RegistrationResult;
import it.unina.vetbook.entity.*;

public class AuthController {

    private static AuthController instance = null;

    private AuthController() {}

    public static synchronized AuthController getInstance() {
        if (instance == null) {
            instance = new AuthController();
        }
        return instance;
    }

    public LoginResult login(String username, String password) {
        // 1. Controllo campi vuoti
        if (isNullOrEmpty(username) || isNullOrEmpty(password)) {
            return LoginResult.failure("Tutti i campi sono obbligatori.");
        }
        // 2. Controllo lunghezza password
        if (password.length() < 6) {
            return LoginResult.failure("La password deve contenere almeno 6 caratteri.");
        }
        // 3. Verifica credenziali
        Utente utente = Utente.login(username, password);
        if (utente == null) {
            return LoginResult.failure("Credenziali non valide.");
        }

        return switch (utente.getRuolo()) {
            case VETERINARIO -> LoginResult.success(new VeterinarioController((Veterinario) utente));
            case PROPRIETARIO -> LoginResult.success(new ProprietarioController((Proprietario) utente));
            case AMMINISTRATORE -> LoginResult.success(AdminController.getInstance((Amministratore) utente));
        };
    }


    public RegistrationResult registrati(String username, String email, String nome, String cognome, String password) {
        // 1. Controllo campi vuoti
        if (isNullOrEmpty(username) || isNullOrEmpty(email) || isNullOrEmpty(nome)
                || isNullOrEmpty(cognome) || isNullOrEmpty(password)) {
            return RegistrationResult.failure("Tutti i campi sono obbligatori.");
        }

        // 2. Controllo formato email semplice
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return RegistrationResult.failure("Email non valida.");
        }

        // 3. Controllo lunghezza password
        if (password.length() < 6) {
            return RegistrationResult.failure("La password deve contenere almeno 6 caratteri.");
        }

        // 4. Controllo esistenza username
        if (Utente.exists(username)) {
            return RegistrationResult.failure("Username già presente.");
        }

        Utente nuovoUtente = UtenteFactory.creaProprietario(username, email, nome, cognome, password);

        nuovoUtente.registrati();
        ProprietarioController controller = new ProprietarioController((Proprietario) nuovoUtente);
        return RegistrationResult.success(controller);
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }


}
