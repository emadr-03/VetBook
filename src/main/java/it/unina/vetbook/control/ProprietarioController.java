package it.unina.vetbook.control;

import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.ProprietarioDTO;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Prenotazione;
import it.unina.vetbook.entity.Proprietario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioController {

    //Istanza mockata del proprietario
    private Proprietario proprietarioCorrente;
    private final List<AnimaleDomestico> animaliMock;

    private final Agenda agenda;

    public ProprietarioController(Proprietario proprietarioCorrente) {
        animaliMock = new ArrayList<>();
        animaliMock.add(new AnimaleDomestico(1234567890, "Fido", "Cane", "Golden Retriever", "Biondo", LocalDate.of(2020, 5, 10)));
        animaliMock.add(new AnimaleDomestico(1234567891, "Micia", "Gatto", "Siamese", "Crema", LocalDate.of(2021, 8, 15)));
        //this.proprietarioCorrente = proprietarioCorrente;
        //Usiamo un proprietario mockato
        this.proprietarioCorrente = getProprietarioMock();
        agenda = Agenda.getInstance();
    }

    public void gestioneProfilo(String username, String nome, String cognome, String email, String password) {
        proprietarioCorrente.setUsername(username);
        proprietarioCorrente.setNome(nome);
        proprietarioCorrente.setCognome(cognome);
        proprietarioCorrente.setEmail(email);

        System.out.println("Profilo aggiornato. Nuovo username: " + proprietarioCorrente.getUsername());
    }

    public void inserisciAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        boolean chipEsistente = animaliMock.stream().anyMatch(a -> a.getCodiceChip() == codiceChip);
        if (chipEsistente) {
            throw new IllegalStateException("Codice chip già esistente.");
        }
        AnimaleDomestico nuovoAnimale = new AnimaleDomestico(codiceChip, nome, tipo, razza, colore, dataDiNascita);
        animaliMock.add(nuovoAnimale);
    }

    public void modificaAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        for (AnimaleDomestico a : animaliMock) {
            if (a.getCodiceChip() == codiceChip) {
                a.setNome(nome);
                a.setTipo(tipo);
                a.setRazza(razza);
                a.setColore(colore);
                a.setDataDiNascita(dataDiNascita);
                return;
            }
        }
        throw new IllegalArgumentException("Animale con codice chip " + codiceChip + " non trovato.");
    }


    public void eliminaAnimale(int codiceChip) {
        animaliMock.removeIf(a -> a.getCodiceChip() == codiceChip);
    }

    public void effettuaPrenotazione(AnimaleDomesticoDTO animaleDto, LocalDate data, LocalTime ora) {
        //TODO: Ricerca animale nel DB, se non presente ritorna errore

        Prenotazione p = new Prenotazione(data, ora, null);
        agenda.prenotaVisita(p);
    }

    public List<AnimaleDomesticoDTO> getAnimaliProprietario() {
        return animaliMock.stream()
                .map(a -> new AnimaleDomesticoDTO(
                        a.getCodiceChip(),
                        a.getNome(),
                        a.getTipo(),
                        a.getRazza(),
                        a.getColore(),
                        a.getDataDiNascita(),
                        a.getProprietario()
                ))
                .toList();
    }


    public Proprietario getProprietarioMock() {
        if (proprietarioCorrente == null) {
            proprietarioCorrente = new Proprietario("mrossi", "mario.rossi@email.com", "prova");
            proprietarioCorrente.setNome("Mario");
            proprietarioCorrente.setCognome("Rossi");
            proprietarioCorrente.setAnimali(animaliMock);
            proprietarioCorrente.setImmagineProfilo(new byte[0]);
        }
        return proprietarioCorrente;
    }

    public ProprietarioDTO getProprietarioDTO() {
        return new ProprietarioDTO(
                proprietarioCorrente.getUsername(),
                proprietarioCorrente.getEmail(),
                proprietarioCorrente.getNome(),
                proprietarioCorrente.getCognome(),
                proprietarioCorrente.getImmagineProfilo(),
                proprietarioCorrente.getAnimali()
        );
    }
}