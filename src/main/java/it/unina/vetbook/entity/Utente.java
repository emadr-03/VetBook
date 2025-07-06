package it.unina.vetbook.entity;

public abstract class Utente {
    protected String username;
    protected String email;
    protected String password;
    protected UserRole ruolo;


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

    public abstract boolean checkPassword(String password);

    public static Utente login(String username, String password) {
        //UtenteDAO u = UtenteDAO(username, password);
        //return u.read()
        return null;
    }

    public void registrati(){
        //UtenteDAO u = UtenteDAO(this);
        //u.create();
    }
}
