package it.unina.vetbook.control;

import it.unina.vetbook.dto.RegistrationResult;
import it.unina.vetbook.dto.UtenteDTO;
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

    public Object login(String username, String password) {
        Utente utente = Utente.login(username, password);
        if (utente == null) {
            throw new IllegalArgumentException("Credenziali non valide");
        }
        return switch (utente.getRuolo()) {
            case VETERINARIO -> new VeterinarioController((Veterinario) utente);
            case PROPRIETARIO -> new ProprietarioController((Proprietario) utente);
            case AMMINISTRATORE_DELL_AMBULATORIO -> AdminController.getInstance();
        };
    }

    public RegistrationResult registrati(String username, String email, String nome, String cognome, String password) {
        if (Utente.exists(username)) {
            return RegistrationResult.failure("Username già presente");
        }

        Utente nuovoUtente = UtenteFactory.creaProprietario(username, email, nome, cognome, password);
        try {
            nuovoUtente.registrati();
            ProprietarioController controller = new ProprietarioController((Proprietario) nuovoUtente);
            return RegistrationResult.success(controller);
        } catch (Exception e) {
            return RegistrationResult.failure("Errore durante la registrazione: " + e.getMessage());
        }
    }

}
