package it.unina.vetbook.control;

import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.DisponibilitaDTO;
import it.unina.vetbook.dto.ProprietarioDTO;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Prenotazione;
import it.unina.vetbook.entity.Proprietario;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
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
        if (username == null || username.trim().isEmpty() || username.length() > 20) {
            throw new IllegalArgumentException("Username non valido.");
        }
        if (!nome.matches("[a-zA-Z\\s]+") || nome.length() > 30) {
            throw new IllegalArgumentException("Nome non valido.");
        }
        if (!cognome.matches("[a-zA-Z\\s]+") || cognome.length() > 30) {
            throw new IllegalArgumentException("Cognome non valido.");
        }
        if (!email.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email non valida.");
        }
        if (password.length() < 6 && (!password.isEmpty())) {
            throw new IllegalArgumentException("La password deve essere maggiore di 6 caratteri");
        }

        proprietarioCorrente.setUsername(username.trim());
        proprietarioCorrente.setNome(nome.trim());
        proprietarioCorrente.setCognome(cognome.trim());
        proprietarioCorrente.setEmail(email.trim());
        proprietarioCorrente.setPassword(password);

        System.out.println("Profilo aggiornato. Nuovo username: " + proprietarioCorrente.getUsername());
    }

    public void aggiornaImmagineProfilo(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File immagine non valido.");
        }

        String nomeFile = file.getName().toLowerCase();
        if (!(nomeFile.endsWith(".jpg") || nomeFile.endsWith(".jpeg") ||
                nomeFile.endsWith(".png") || nomeFile.endsWith(".gif"))) {
            throw new IllegalArgumentException("Formato immagine non supportato.");
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = fis.readAllBytes();
            proprietarioCorrente.setImmagineProfilo(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura del file immagine.", e);
        }
    }


    public void inserisciAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        if (String.valueOf(codiceChip).length() != 10)
            throw new IllegalArgumentException("Il codice chip deve essere di 10 cifre.");

        if (animaliMock.stream().anyMatch(a -> a.getCodiceChip() == codiceChip))
            throw new IllegalStateException("Codice chip già esistente.");

        validaCampoTesto(nome, "Nome", 40);
        validaCampoTesto(tipo, "Tipo", 20);
        validaCampoTesto(razza, "Razza", 20);
        validaCampoTesto(colore, "Colore", 20);

        if (dataDiNascita == null || dataDiNascita.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data di nascita non valida.");

        animaliMock.add(new AnimaleDomestico(codiceChip, nome, tipo, razza, colore, dataDiNascita));
    }


    public void modificaAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        validaCampoTesto(nome, "Nome", 40);
        validaCampoTesto(tipo, "Tipo", 20);
        validaCampoTesto(razza, "Razza", 20);
        validaCampoTesto(colore, "Colore", 20);

        if (dataDiNascita == null || dataDiNascita.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Data di nascita non valida.");

        AnimaleDomestico animale = animaliMock.stream()
                .filter(a -> a.getCodiceChip() == codiceChip)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Animale con codice chip " + codiceChip + " non trovato."));

        animale.setNome(nome);
        animale.setTipo(tipo);
        animale.setRazza(razza);
        animale.setColore(colore);
        animale.setDataDiNascita(dataDiNascita);
    }

    public void eliminaAnimale(int codiceChip) {
        animaliMock.removeIf(a -> a.getCodiceChip() == codiceChip);
    }

    public void effettuaPrenotazione(AnimaleDomesticoDTO animaleDomesticoDTO, DisponibilitaDTO disponibilita) {
        AnimaleDomestico animale = proprietarioCorrente.getAnimali().stream()
                .filter(a -> a.getCodiceChip() == animaleDomesticoDTO.codiceChip())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Animale non trovato per il codice chip specificato."));

        Prenotazione prenotazione = new Prenotazione(
                disponibilita.data(),
                disponibilita.ora(),
                animale
        );

        agenda.prenotaVisita(prenotazione);
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


    //A: La seguente classe permette di tener conto della sessione corrente del proprietario
    //      Attualmente la classe è mockata
    private Proprietario getProprietarioMock() {
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

    private void validaCampoTesto(String testo, String nomeCampo, int lunghezzaMax) {
        if (testo == null || testo.trim().isEmpty())
            throw new IllegalArgumentException("Il campo '" + nomeCampo + "' è obbligatorio.");
        if (testo.length() > lunghezzaMax)
            throw new IllegalArgumentException("Il campo '" + nomeCampo + "' è troppo lungo (max " + lunghezzaMax + ").");
        if (testo.matches(".*\\d.*"))
            throw new IllegalArgumentException("Il campo '" + nomeCampo + "' non può contenere numeri.");
        if (!testo.matches("[a-zA-Z\\s]+"))
            throw new IllegalArgumentException("Il campo '" + nomeCampo + "' contiene simboli non validi.");
    }

}