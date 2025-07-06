package it.unina.vetbook.control;

import it.unina.vetbook.dto.AnimaleDTO;
import it.unina.vetbook.dto.ProprietarioDTO;
import it.unina.vetbook.entity.Proprietario;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioController {

    private static ProprietarioController instance;
    private final List<AnimaleDTO> animaliInMemory;
    private ProprietarioDTO proprietarioCorrente;

    private ProprietarioController() {
        animaliInMemory = new ArrayList<>();
        animaliInMemory.add(new AnimaleDTO(9810200387L, "Fuffi", "Gatto", "Persiano", "Bianco", "01-05-2020"));
        animaliInMemory.add(new AnimaleDTO(9410000186L, "Bobby", "Cane", "Labrador", "Miele", "15-08-2022"));
        animaliInMemory.add(new AnimaleDTO(9810987654L, "Nemo", "Pesce", "Pagliaccio", "Arancione", "10-01-2024"));

        this.proprietarioCorrente = new ProprietarioDTO(
                "Mario", "Rossi", "mariorossi", "mario.rossi@example.com",
                getClass().getResource("/img/user_profile_icon.png").getPath()
        );
    }

    public static synchronized ProprietarioController getInstance() {
        if (instance == null) {
            instance = new ProprietarioController();
        }
        return instance;
    }

    public void gestioneProfilo(String nome, String cognome, String username, String password, String email, InputStream img) {
        String newImagePath = this.proprietarioCorrente.imagePath;
        if (img != null) {
            try {
                File tempFile = File.createTempFile("profile_img_", ".jpg");
                Files.copy(img, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                newImagePath = tempFile.getAbsolutePath();
                tempFile.deleteOnExit();
            } catch (IOException e) {
                System.err.println("Impossibile salvare l'immagine del profilo: " + e.getMessage());
            }
        }
        this.proprietarioCorrente = new ProprietarioDTO(nome, cognome, username, email, newImagePath);
    }

    public void inserisciAnimale(long codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        String dataFormatted = dataDiNascita.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        AnimaleDTO nuovoAnimale = new AnimaleDTO(codiceChip, nome, tipo, razza, colore, dataFormatted);
        animaliInMemory.add(nuovoAnimale);
    }

    public void gestioneAnimale() {
        System.out.println("Chiamato metodo generico 'gestioneAnimale'");
    }

    public void effettuaPrenotazione(AnimaleDTO a, LocalDate data, LocalTime ora) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public Object[][] visualizzaAnimali() {
        Object[][] tabella = new Object[animaliInMemory.size()][6];
        for (int i = 0; i < animaliInMemory.size(); i++) {
            AnimaleDTO a = animaliInMemory.get(i);
            tabella[i][0] = a.codiceChip;
            tabella[i][1] = a.nome;
            tabella[i][2] = a.tipo;
            tabella[i][3] = a.razza;
            tabella[i][4] = a.colore;
            tabella[i][5] = a.dataNascita;
        }
        return tabella;
    }

    public Proprietario getProprietario() {
        return null;
    }

    public ProprietarioDTO getDatiProprietarioDTO() {
        return this.proprietarioCorrente;
    }

    public void updateAnimale(AnimaleDTO animaleAggiornato) {
        for (int i = 0; i < animaliInMemory.size(); i++) {
            if (animaliInMemory.get(i).codiceChip == animaleAggiornato.codiceChip) {
                animaliInMemory.set(i, animaleAggiornato);
                return;
            }
        }
    }

    public void deleteAnimale(long chip) {
        animaliInMemory.removeIf(animale -> animale.codiceChip == chip);
    }
}