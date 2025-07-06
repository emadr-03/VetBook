package it.unina.vetbook.control;

import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Prenotazione;
import it.unina.vetbook.entity.Proprietario;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ProprietarioController {

    private static ProprietarioController instance = null;
    private Proprietario proprietario;
    private Agenda agenda = Agenda.getInstance();

    private ProprietarioController() {}

    public static ProprietarioController getInstance() {
        if (instance == null) {
            instance = new ProprietarioController();
        }
        return instance;
    }

    public Object[][] getProprietario() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void gestioneProfilo(String nome, String cognome, String username, String password, String email, InputStream img) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void inserisciAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDNascita) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public String effettuaPrenotazione(int codiceChip, LocalDate data, LocalTime ora) {
        AnimaleDomestico animale = proprietario.getAnimaleByCodiceChip(codiceChip);
        if(animale==null) {
            return "codice chip non valido";
        }
        Prenotazione p = new Prenotazione(data, ora, animale);
        agenda.prenotaVisita(p);
        return "Animale salvato con successo!";
    }

    public Object[][] visualizzaAnimali() {
        List<AnimaleDomestico> lista = proprietario.getAnimali();
        return convertiInTabella(lista);
    }

    private Object[][] convertiInTabella(List<AnimaleDomestico> animali) {
        Object[][] tabella = new Object[animali.size()][];
        for (int i = 0; i < animali.size(); i++) {
            AnimaleDomestico a = animali.get(i);
            tabella[i] = new Object[]{a.getCodiceChip(), a.getNome(), a.getTipo(), a.getRazza(), a.getColore(), a.getDataDiNascita()};
        }
        return tabella;
    }
}

