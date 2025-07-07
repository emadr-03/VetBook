package it.unina.vetbook.control;

import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.PrenotazioneDTO;
import it.unina.vetbook.dto.ProprietarioDTO;
import it.unina.vetbook.entity.Agenda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioController {

    private static ProprietarioController instance;
    private ProprietarioDTO proprietarioCorrente;
    private List<AnimaleDomesticoDTO> animaliMock;

    private ProprietarioController() {
        animaliMock = new ArrayList<>();
        animaliMock.add(new AnimaleDomesticoDTO(1234567890, "Fido", "Cane", "Golden Retriever", "Biondo", LocalDate.of(2020, 5, 10)));
        animaliMock.add(new AnimaleDomesticoDTO(987654321, "Micia", "Gatto", "Siamese", "Crema", LocalDate.of(2021, 8, 15)));
    }

    public static synchronized ProprietarioController getInstance() {
        if (instance == null) {
            instance = new ProprietarioController();
        }
        return instance;
    }

    public void gestioneProfilo(String username, String nome, String cognome, String email, String password) {
        ProprietarioDTO utente = getProprietario();
        utente.setUsername(username);
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);

        System.out.println("Profilo aggiornato. Nuovo username: " + utente.getUsername());
    }

    public void inserisciAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        boolean chipEsistente = animaliMock.stream().anyMatch(a -> a.getCodiceChip() == codiceChip);
        if (chipEsistente) {
            throw new IllegalStateException("Codice chip già esistente.");
        }
        AnimaleDomesticoDTO nuovoAnimale = new AnimaleDomesticoDTO(codiceChip, nome, tipo, razza, colore, dataDiNascita);
        animaliMock.add(nuovoAnimale);
    }

    public void modificaAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        animaliMock.removeIf(a -> a.getCodiceChip() == codiceChip);
        animaliMock.add(new AnimaleDomesticoDTO(codiceChip, nome, tipo, razza, colore, dataDiNascita));
    }

    public void eliminaAnimale(int codiceChip) {
        animaliMock.removeIf(a -> a.getCodiceChip() == codiceChip);
    }

    public void effettuaPrenotazione(AnimaleDomesticoDTO animaleDto, LocalDate data, LocalTime ora) {
        PrenotazioneDTO p = new PrenotazioneDTO(data, ora, animaleDto);
        Agenda.getInstance().prenotaVisita(p);
    }

    public List<AnimaleDomesticoDTO> getAnimaliProprietario() {
        return new ArrayList<>(animaliMock);
    }

    public ProprietarioDTO getProprietario() {
        if (proprietarioCorrente == null) {
            proprietarioCorrente = new ProprietarioDTO();
            proprietarioCorrente.setUsername("mrossi");
            proprietarioCorrente.setEmail("mario.rossi@email.com");
            proprietarioCorrente.setNome("Mario");
            proprietarioCorrente.setCognome("Rossi");
        }
        return proprietarioCorrente;
    }
}