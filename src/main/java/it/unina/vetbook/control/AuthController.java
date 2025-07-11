package it.unina.vetbook.control;

import it.unina.vetbook.dto.LoginResultDTO;
import it.unina.vetbook.dto.RegistrationResultDTO;
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

    public LoginResultDTO login(String username, String password) {
        // 1. Controllo campi vuoti
        if (isNullOrEmpty(username) || isNullOrEmpty(password)) {
            return LoginResultDTO.failure("Tutti i campi sono obbligatori.");
        }
        // 2. Controllo lunghezza password
        if (password.length() < 6) {
            return LoginResultDTO.failure("La password deve contenere almeno 6 caratteri.");
        }
        // 3. Verifica credenziali
        Utente utente = Utente.login(username, password);
        if (utente == null) {
            return LoginResultDTO.failure("Credenziali non valide.");
        }

        return switch (utente.getRuolo()) {
            case VETERINARIO -> LoginResultDTO.success(new VeterinarioController((Veterinario) utente));
            case PROPRIETARIO -> LoginResultDTO.success(new ProprietarioController((Proprietario) utente));
            case AMMINISTRATORE_DELL_AMBULATORIO -> LoginResultDTO.success(AdminController.getInstance());
        };
    }


    public RegistrationResultDTO registrati(String username, String email, String nome, String cognome, String password) {
        // 1. Controllo campi vuoti
        if (isNullOrEmpty(username) || isNullOrEmpty(email) || isNullOrEmpty(nome)
                || isNullOrEmpty(cognome) || isNullOrEmpty(password)) {
            return RegistrationResultDTO.failure("Tutti i campi sono obbligatori.");
        }

        // 2. Controllo formato email semplice
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return RegistrationResultDTO.failure("Email non valida.");
        }

        // 3. Controllo lunghezza password
        if (password.length() < 6) {
            return RegistrationResultDTO.failure("La password deve contenere almeno 6 caratteri.");
        }

        // 4. Controllo esistenza username
        if (Utente.exists(username)) {
            return RegistrationResultDTO.failure("Username già presente.");
        }

        Utente nuovoUtente = UtenteFactory.creaProprietario(username, email, nome, cognome, password);
        try {
            nuovoUtente.registrati();
            ProprietarioController controller = new ProprietarioController((Proprietario) nuovoUtente);
            return RegistrationResultDTO.success(controller);
        } catch (Exception e) {
            return RegistrationResultDTO.failure("Errore durante la registrazione: " + e.getMessage());
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }


}
