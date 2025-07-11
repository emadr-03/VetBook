package it.unina.vetbook.entity;

public class UtenteFactory {

    public static Utente creaProprietario(String username, String email, String nome, String cognome, String password) {
        Proprietario p = new Proprietario(username, email, password);
        p.setNome(nome);
        p.setCognome(cognome);
        return p;
    }

    public static Utente creaVeterinario(String username, String email, String password) {
        return new Veterinario(username, email, password);
    }

    public static Utente creaAmministratore(String username, String email, String password) {
        return new Amministratore(username, email, password);
    }
}
