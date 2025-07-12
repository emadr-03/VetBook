package it.unina.vetbook.entity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Proprietario extends Utente {

    private String nome;
    private String cognome;
    byte[] immagineProfilo;
    private List<AnimaleDomestico> animali;
    private List<Prenotazione> prenotazioni;

    public Proprietario(String username, String email, String password) {
        super(username, email, password);
        this.ruolo = UserRole.PROPRIETARIO;
        this.animali = new ArrayList<>();
        this.prenotazioni = new ArrayList<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public byte[] getImmagineProfilo() {
        return immagineProfilo;
    }

    public void setImmagineProfilo(byte[] immagineProfilo) {
        this.immagineProfilo = immagineProfilo;
    }

    public List<AnimaleDomestico> getAnimali() {
        return animali;
    }

    public void setAnimali(List<AnimaleDomestico> animali) {
        this.animali = animali;
    }

    public void addAnimale(AnimaleDomestico animaleDomestico){
        animali.add(animaleDomestico);
    }

    public static Proprietario mockProprietario() {
        Proprietario mock = new Proprietario("Mario", "mariorossi@libero.it", "mario123");
        mock.setNome("Mario");
        mock.setCognome("Di Lela");
        mock.setImmagineProfilo(new byte[0]);
        mock.setId(1);
        return mock;
    }

}