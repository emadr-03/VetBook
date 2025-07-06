package it.unina.vetbook.control;

import it.unina.vetbook.database.AnimaleDAO;
import it.unina.vetbook.database.UtenteDAO;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Prenotazione;
import it.unina.vetbook.entity.Proprietario;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProprietarioController {

    private static ProprietarioController instance;
    private Proprietario proprietarioCorrente;

    private ProprietarioController() {}

    public static synchronized ProprietarioController getInstance() {
        if (instance == null) {
            instance = new ProprietarioController();
        }
        return instance;
    }

    public void setProprietarioCorrente(Proprietario proprietario) {
        this.proprietarioCorrente = proprietario;
    }

    public void gestioneProfilo(String nome, String cognome, String email, String password) {
        if (proprietarioCorrente == null) {
            throw new IllegalStateException("Nessun proprietario loggato.");
        }
        proprietarioCorrente.setNome(nome);
        proprietarioCorrente.setCognome(cognome);
        proprietarioCorrente.setEmail(email);
        if (password != null && !password.isEmpty()) {
            proprietarioCorrente.setPassword(password);
        }

        try {
            new UtenteDAO().update(proprietarioCorrente);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'aggiornamento del profilo", e);
        }
    }

    public void inserisciAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        if (proprietarioCorrente == null) {
            throw new IllegalStateException("Nessun proprietario loggato.");
        }
        AnimaleDomestico nuovoAnimale = new AnimaleDomestico(codiceChip, nome, tipo, razza, colore, dataDiNascita);
        nuovoAnimale.setProprietario(proprietarioCorrente);
        try {
            new AnimaleDAO().create(nuovoAnimale);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'inserimento dell'animale", e);
        }
    }

    public void modificaAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        if (proprietarioCorrente == null) {
            throw new IllegalStateException("Nessun proprietario loggato.");
        }
        AnimaleDomestico animaleModificato = new AnimaleDomestico(codiceChip, nome, tipo, razza, colore, dataDiNascita);
        try {
            new AnimaleDAO().update(animaleModificato);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la modifica dell'animale", e);
        }
    }

    public void eliminaAnimale(int codiceChip) {
        try {
            new AnimaleDAO().delete(codiceChip);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'eliminazione dell'animale", e);
        }
    }

    public void gestioneAnimale() {
        throw new UnsupportedOperationException("Logica non specificata");
    }

    public void effettuaPrenotazione(AnimaleDomestico a, LocalDate data, LocalTime ora) {
        if (proprietarioCorrente == null) {
            throw new IllegalStateException("Nessun proprietario loggato.");
        }
        Prenotazione p = new Prenotazione(data, ora, a);
        Agenda.getInstance().prenotaVisita(p);
    }

    public List<AnimaleDomestico> getAnimaliProprietario() {
        if (proprietarioCorrente == null) {
            throw new IllegalStateException("Nessun proprietario loggato.");
        }
        try {
            return new AnimaleDAO().executeQuery("SELECT * FROM Animale WHERE usernameProprietario = ?", proprietarioCorrente.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException("Errore nel recupero degli animali", e);
        }
    }

    public Object[][] visualizzaAnimaliInTabella() {
        List<AnimaleDomestico> animali = getAnimaliProprietario();
        Object[][] tabella = new Object[animali.size()][6];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < animali.size(); i++) {
            AnimaleDomestico a = animali.get(i);
            tabella[i][0] = a.getCodiceChip();
            tabella[i][1] = a.getNome();
            tabella[i][2] = a.getTipo();
            tabella[i][3] = a.getRazza();
            tabella[i][4] = a.getColore();
            tabella[i][5] = a.getDataDiNascita().format(formatter);
        }
        return tabella;
    }

    public Proprietario getProprietario() {
        if (proprietarioCorrente == null) {
            throw new IllegalStateException("Nessun proprietario loggato.");
        }
        return proprietarioCorrente;
    }
}