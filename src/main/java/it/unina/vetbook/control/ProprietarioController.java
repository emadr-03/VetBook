package it.unina.vetbook.control;

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
    private Proprietario proprietarioCorrente;
    private List<AnimaleDomestico> animaliMock;

    private ProprietarioController() {
        // Dati mockati nel controller
        animaliMock = new ArrayList<>();
        animaliMock.add(new AnimaleDomestico(1234567890, "Fido", "Cane", "Golden Retriever", "Biondo", LocalDate.of(2020, 5, 10)));
        animaliMock.add(new AnimaleDomestico(987654321, "Micia", "Gatto", "Siamese", "Crema", LocalDate.of(2021, 8, 15)));
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
        AnimaleDomestico nuovoAnimale = new AnimaleDomestico(codiceChip, nome, tipo, razza, colore, dataDiNascita);
        animaliMock.add(nuovoAnimale);
    }

    public void modificaAnimale(int codiceChip, String nome, String tipo, String razza, String colore, LocalDate dataDiNascita) {
        animaliMock.removeIf(a -> a.getCodiceChip() == codiceChip);
        animaliMock.add(new AnimaleDomestico(codiceChip, nome, tipo, razza, colore, dataDiNascita));
    }

    public void eliminaAnimale(int codiceChip) {
        animaliMock.removeIf(a -> a.getCodiceChip() == codiceChip);
    }

    public void effettuaPrenotazione(AnimaleDomestico a, LocalDate data, LocalTime ora) {
        Prenotazione p = new Prenotazione(data, ora, a);
        Agenda.getInstance().prenotaVisita(p);
    }

    public List<AnimaleDomestico> getAnimaliProprietario() {
        return new ArrayList<>(animaliMock);
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
            proprietarioCorrente = new Proprietario();
            proprietarioCorrente.setNome("Mario");
            proprietarioCorrente.setCognome("Rossi");
        }
        return proprietarioCorrente;
    }
}