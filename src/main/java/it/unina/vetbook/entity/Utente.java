package it.unina.vetbook.entity;

public abstract class Utente {

    protected String username;
    protected String email;
    protected String password;
    protected UserRole ruolo;

    public abstract boolean checkPassword(String password);

    // I METODI login() E registrati() SONO STATI RIMOSSI DA QUI

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRuolo() {
        return ruolo;
    }

    public void setRuolo(UserRole ruolo) {
        this.ruolo = ruolo;
    }

}