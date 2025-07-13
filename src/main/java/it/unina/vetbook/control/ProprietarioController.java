package it.unina.vetbook.control;

import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.DisponibilitaDTO;
import it.unina.vetbook.dto.ProprietarioDTO;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Prenotazione;
import it.unina.vetbook.entity.Proprietario;
import it.unina.vetbook.exception.BusinessRuleViolationException;
import it.unina.vetbook.exception.ValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class ProprietarioController {


    private final Proprietario proprietarioCorrente;

    //Il seguente attributo è necessario solo a fini di mock del comportamento dell'UI, dunque non è presente nel BCED
    // Dunque i metodi che utilizzano tali attributo sono delle versioni mockate del metodo finale
    private final List<AnimaleDomestico> animali;

    private final Agenda agenda;

    public ProprietarioController(Proprietario proprietarioCorrente) {
        this.proprietarioCorrente = proprietarioCorrente;
        this.animali = this.proprietarioCorrente.getAnimali();
        this.proprietarioCorrente.setAnimali(animali);
        agenda = Agenda.getInstance();
    }

    public void gestioneProfilo(String username, String nome, String cognome, String email, String password) {
        if (username == null || username.trim().isEmpty() || username.length() > 20) {
            throw new ValidationException("Username non valido.");
        }
        if (!nome.matches("[a-zA-Z\\s]+") || nome.length() > 30) {
            throw new ValidationException("Nome non valido.");
        }
        if (!cognome.matches("[a-zA-Z\\s]+") || cognome.length() > 30) {
            throw new ValidationException("Cognome non valido.");
        }
        if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new ValidationException("Email non valida.");
        }
        if (password.length() < 6 && (!password.isEmpty())) {
            throw new ValidationException("La password deve essere maggiore di 6 caratteri");
        }

        //A: I seguenti setters sono mockati per simulare il funzionamento dell'UI, non c'è persistenza
        proprietarioCorrente.setUsername(username.trim());
        proprietarioCorrente.setNome(nome.trim());
        proprietarioCorrente.setCognome(cognome.trim());
        proprietarioCorrente.setEmail(email.trim());
        proprietarioCorrente.setPassword(password);

        System.out.println("Profilo aggiornato. Nuovo username: " + proprietarioCorrente.getUsername());
    }

    public void aggiornaImmagineProfilo(File file) {
        if (file == null || !file.exists()) {
            throw new ValidationException("File immagine non valido.");
        }

        String nomeFile = file.getName().toLowerCase();
        if (!(nomeFile.endsWith(".jpg") || nomeFile.endsWith(".jpeg") ||
                nomeFile.endsWith(".png") || nomeFile.endsWith(".gif"))) {
            throw new ValidationException("Formato immagine non supportato.");
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = fis.readAllBytes();
            //A: Il seguente setter è mockato per simulare il funzionamento dell'UI, non c'è persistenza
            proprietarioCorrente.setImmagineProfilo(bytes);
        } catch (IOException e) {
            throw new BusinessRuleViolationException("Errore nella lettura del file immagine.", e);
        }
    }


    public void inserisciAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        if (String.valueOf(codiceChip).length() != 10)
            throw new ValidationException("Il codice chip deve essere di 10 cifre.");

        if (proprietarioCorrente.getAnimali().stream().anyMatch(a -> a.getCodiceChip() == codiceChip))
            throw new BusinessRuleViolationException("Codice chip già esistente.");

        validaCampoTesto(nome, "Nome", 40);
        validaCampoTesto(tipo, "Tipo", 20);
        validaCampoTesto(razza, "Razza", 20);
        validaCampoTesto(colore, "Colore", 20);

        if (dataDiNascita == null || dataDiNascita.isAfter(LocalDate.now()))
            throw new ValidationException("Data di nascita non valida.");

        //A: Il seguente add è un mock per simulare il funzionamento dell'UI. L'aggiunta non viene persistita-
        proprietarioCorrente.addAnimale(new AnimaleDomestico(codiceChip, proprietarioCorrente.getId(), nome, tipo, razza, colore, dataDiNascita));
    }


    public void modificaAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        validaCampoTesto(nome, "Nome", 40);
        validaCampoTesto(tipo, "Tipo", 20);
        validaCampoTesto(razza, "Razza", 20);
        validaCampoTesto(colore, "Colore", 20);

        if (dataDiNascita == null || dataDiNascita.isAfter(LocalDate.now()))
            throw new ValidationException("Data di nascita non valida.");


        AnimaleDomestico animale = animali.stream()
                .filter(a -> a.getCodiceChip() == codiceChip)
                .findFirst()
                .orElseThrow(() -> new ValidationException("Animale con codice chip " + codiceChip + " non trovato."));

        //A: I seguenti setters sono mockati per simulare il funzionamento dell'UI, non c'è persistenza
        animale.setNome(nome);
        animale.setTipo(tipo);
        animale.setRazza(razza);
        animale.setColore(colore);
        animale.setDataDiNascita(dataDiNascita);
    }

    public void eliminaAnimale(int codiceChip) {
        proprietarioCorrente.getAnimali().removeIf(a -> a.getCodiceChip() == codiceChip);
    }

    public void effettuaPrenotazione(AnimaleDomesticoDTO animaleDomesticoDTO, DisponibilitaDTO disponibilita) {
        AnimaleDomestico animale = proprietarioCorrente.getAnimali().stream()
                .filter(a -> a.getCodiceChip() == animaleDomesticoDTO.codiceChip())
                .findFirst()
                .orElseThrow(() -> new ValidationException("Animale non trovato per il codice chip specificato."));

        Prenotazione prenotazione = new Prenotazione(
                disponibilita.data(),
                disponibilita.ora(),
                animale
        );
        agenda.prenotaVisita(prenotazione);
    }

    public List<AnimaleDomesticoDTO> getAnimaliProprietario() {
        return proprietarioCorrente.getAnimali().stream()
                .map(a -> new AnimaleDomesticoDTO(
                        a.getCodiceChip(),
                        a.getNome(),
                        a.getTipo(),
                        a.getRazza(),
                        a.getColore(),
                        a.getDataDiNascita(),
                        proprietarioCorrente.getId()
                ))
                .toList();
    }


    public ProprietarioDTO getProprietarioDTO() {
        return new ProprietarioDTO(
                proprietarioCorrente.getUsername(),
                proprietarioCorrente.getEmail(),
                proprietarioCorrente.getNome(),
                proprietarioCorrente.getCognome(),
                proprietarioCorrente.getImmagineProfilo(),
                getAnimaliDTO()
        );
    }

    private List<AnimaleDomesticoDTO> getAnimaliDTO() {
        return proprietarioCorrente.getAnimali().stream()
                .map(a -> new AnimaleDomesticoDTO(
                        a.getCodiceChip(),
                        a.getNome(),
                        a.getTipo(),
                        a.getRazza(),
                        a.getColore(),
                        a.getDataDiNascita(),
                        proprietarioCorrente.getId()
                ))
                .toList();
    }

    private void validaCampoTesto(String testo, String nomeCampo, int lunghezzaMax) {
        if (testo == null || testo.trim().isEmpty())
            throw new ValidationException("Il campo '" + nomeCampo + "' è obbligatorio.");
        if (testo.length() > lunghezzaMax)
            throw new ValidationException("Il campo '" + nomeCampo + "' è troppo lungo (max " + lunghezzaMax + ").");
        if (testo.matches(".*\\d.*"))
            throw new ValidationException("Il campo '" + nomeCampo + "' non può contenere numeri.");
        if (!testo.matches("[a-zA-Z\\s]+"))
            throw new ValidationException("Il campo '" + nomeCampo + "' contiene simboli non validi.");
    }

}