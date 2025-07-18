package it.unina.vetbook.entity;

import it.unina.vetbook.database.AnimaleDomesticoDAO;
import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.database.UtenteDAO;

import java.sql.Connection;
import java.sql.SQLException;
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
        List<AnimaleDomestico> animali = new ArrayList<>();
        try(Connection conn = DBManager.getInstance().getConnection()) {
            AnimaleDomesticoDAO dao = new AnimaleDomesticoDAO(conn);
            animali = dao.readAll(this.id);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return animali;
    }

    //A: Metodo unicamente inserito per esplicitare la composizione
    public void deleteProprietario() {
        try(Connection conn = DBManager.getInstance().getConnection()) {
            UtenteDAO dao = new UtenteDAO(conn);
            dao.deleteProprietario(this.id);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
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