package it.unina.vetbook.control;

import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.PrenotazioneDTO;
import it.unina.vetbook.dto.ProprietarioDTO;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.AnimaleDomestico;
import it.unina.vetbook.entity.Prenotazione;
import it.unina.vetbook.entity.Proprietario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProprietarioController {

    private static ProprietarioController instance;
    private ProprietarioDTO proprietarioCorrente;
    private List<AnimaleDomesticoDTO> animaliMock;

    private ProprietarioController() {
        // Dati mockati nel controller
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

    public void effettuaPrenotazione(AnimaleDomesticoDTO a, LocalDate data, LocalTime ora) {
        PrenotazioneDTO p = new PrenotazioneDTO(data, ora, a);
        Agenda.getInstance().prenotaVisita(p);
    }

    public List<AnimaleDomesticoDTO> getAnimaliProprietario() {
        return new ArrayList<>(animaliMock);
    }

    public List<AnimaleDomesticoDTO> visualizzaAnimaliInTabella() {
        List<AnimaleDomesticoDTO> animali = getAnimaliProprietario(); // Prende la lista degli Entity
        List<AnimaleDomesticoDTO> animaliDto = new ArrayList<>();

        for (AnimaleDomesticoDTO a : animali) {
            // Converte ogni Entity in un DTO
            animaliDto.add(new AnimaleDomesticoDTO(
                    a.getCodiceChip(),
                    a.getNome(),
                    a.getTipo(),
                    a.getRazza(),
                    a.getColore(),
                    a.getDataDiNascita()
            ));
        }
        return animaliDto;
    }

    public ProprietarioDTO getProprietario() {
        if (proprietarioCorrente == null) {
            proprietarioCorrente = new ProprietarioDTO();
            proprietarioCorrente.setNome("Mario");
            proprietarioCorrente.setCognome("Rossi");
        }
        return proprietarioCorrente;
    }
}