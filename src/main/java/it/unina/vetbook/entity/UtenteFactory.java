package it.unina.vetbook.entity;

public class UtenteFactory {

    public static Utente creaProprietario(String username, String email, String nome, String cognome, String password) {
        Proprietario p = new Proprietario();
        p.setUsername(username);
        p.setEmail(email);
        p.setNome(nome);
        p.setCognome(cognome);
        p.setPassword(password);
        p.setRuolo(UserRole.PROPRIETARIO);
        return p;
    }

    public static Utente creaVeterinario(String username, String email, String password) {
        Veterinario v = new Veterinario();
        v.setUsername(username);
        v.setEmail(email);
        v.setPassword(password);
        v.setRuolo(UserRole.VETERINARIO);
        return v;
    }

    public static Utente creaAmministratore(String username, String email, String password) {
        Amministratore a = new Amministratore();
        a.setUsername(username);
        a.setEmail(email);
        a.setPassword(password);
        a.setRuolo(UserRole.AMMINISTRATORE_DELL_AMBULATORIO);
        return a;
    }
}
