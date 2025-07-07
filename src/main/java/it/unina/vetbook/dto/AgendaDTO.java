package it.unina.vetbook.dto;

import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.Visita;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



/*Questa classe rappresenta l'intero stato dell'agenda in un dato momento.
E' un container che raggruppa diverse liste: lista delle disponibilità, delle prenotazioni etc...
E' utile, ad esempio, ad inviare l'intera agenda in un'unica chiamata.
Ma non è adatta a rappresentare una singola riga in una tabella di visualizzazione (es. disponibilità, o prenotazione, o visita)
*/
public class AgendaDTO {

    private AnimaleDomesticoDTO animaleTest;
    private List<DisponibilitaDTO> disponibilita;
    private List<PrenotazioneDTO> prenotazioni;
    private List<Visita> visite;

    private AgendaDTO() {
        this.disponibilita = new ArrayList<>();
        this.prenotazioni = new ArrayList<>();
        this.visite = new ArrayList<>();

        disponibilita.add(new DisponibilitaDTO(LocalDate.now().plusDays(1), LocalTime.of(9, 0)));
        disponibilita.add(new DisponibilitaDTO(LocalDate.now().plusDays(1), LocalTime.of(10, 0)));
        disponibilita.add(new DisponibilitaDTO(LocalDate.now().plusDays(2), LocalTime.of(11, 0)));
        disponibilita.add(new DisponibilitaDTO(LocalDate.now().plusDays(2), LocalTime.of(12, 0)));


        //dati MOCKATI per far visualizzare una prenotazione al Veterinario
        animaleTest = new AnimaleDomesticoDTO(111222333, "Rex", "Cane", "Pastore Tedesco", "Nero", LocalDate.now().minusYears(3));
        this.prenotazioni.add(new PrenotazioneDTO(LocalDate.now().plusDays(1), LocalTime.of(9, 0), animaleTest));
    }

    public AnimaleDomesticoDTO getAnimaleTest() {
        return animaleTest;
    }

    public void setAnimaleTest(AnimaleDomesticoDTO animaleTest) {
        this.animaleTest = animaleTest;
    }

    public List<DisponibilitaDTO> getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(List<DisponibilitaDTO> disponibilita) {
        this.disponibilita = disponibilita;
    }

    public List<PrenotazioneDTO> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<PrenotazioneDTO> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public List<Visita> getVisite() {
        return visite;
    }

    public void setVisite(List<Visita> visite) {
        this.visite = visite;
    }
}
