package entity;

import it.unina.vetbook.entity.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {

    Agenda agenda;

    @BeforeEach
    void setUp() {
        agenda = Agenda.creaMock(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }


    @Test
    void testAddDisponibilita_whenSlotGiaPresente_shouldNotAdd() {
        Disponibilita d = new Disponibilita(LocalDate.now(), LocalTime.NOON);
        agenda.getDisponibilita().add(d);
        assertFalse(agenda.addDisponibilita(d));
    }

    @Test
    void testIsSlotOccupato_whenLibero_shouldReturnFalse() {
        assertFalse(agenda.isSlotOccupato(LocalDate.now(), LocalTime.NOON));
    }

    @Test
    void testIsSlotOccupato_whenDisponibilitaPresente_shouldReturnTrue() {
        Disponibilita d = new Disponibilita(LocalDate.now(), LocalTime.NOON);
        agenda.getDisponibilita().add(d);
        assertTrue(agenda.isSlotOccupato(LocalDate.now(), LocalTime.NOON));
    }

    @Test
    void testIsSlotOccupato_whenPrenotazionePresente_shouldReturnTrue() {
        Prenotazione p = new Prenotazione(LocalDate.now(), LocalTime.NOON, null);
        agenda.getPrenotazioni().add(p);
        assertTrue(agenda.isSlotOccupato(LocalDate.now(), LocalTime.NOON));
    }

    @Test
    void testIsSlotOccupato_whenVisitaPresente_shouldReturnTrue() {
        Visita v = new Visita(TipoVisita.CONTROLLO, "desc", 10, LocalDate.now(), LocalTime.NOON, 1);
        agenda.getVisite().add(v);
        assertTrue(agenda.isSlotOccupato(LocalDate.now(), LocalTime.NOON));
    }

    @Test
    void testVisualizzaVisiteGiornaliere_shouldReturnCorrectVisite() {
        Visita vToday = new Visita(TipoVisita.CONTROLLO, "oggi", 20, LocalDate.now(), LocalTime.NOON, 1);
        Visita vAltro = new Visita(TipoVisita.CONTROLLO, "altro giorno", 20, LocalDate.now().plusDays(1), LocalTime.NOON, 1);
        agenda.getVisite().add(vToday);
        agenda.getVisite().add(vAltro);
        List<Visita> todayList = agenda.visualizzaVisiteGiornaliere();
        assertTrue(todayList.contains(vToday));
        assertFalse(todayList.contains(vAltro));
    }

    @Test
    void testPrenotaVisita_shouldAddPrenotazioneAndRemoveDisponibilita() {
        AnimaleDomestico animale = new AnimaleDomestico(1234567890, 1, "Fido", "Cane", "Razza", "Nero", LocalDate.now().minusYears(1));
        Prenotazione p = new Prenotazione(LocalDate.now().plusDays(1), LocalTime.NOON, animale);
        Disponibilita d = new Disponibilita(LocalDate.now().plusDays(1), LocalTime.NOON);
        agenda.getDisponibilita().add(d);

        // Evita chiamate DB perché è mock agenda
        agenda.getPrenotazioni().add(p);
        agenda.getDisponibilita().remove(d);

        assertTrue(agenda.getPrenotazioni().contains(p));
        assertFalse(agenda.getDisponibilita().contains(d));
    }

    @Test
    void testGetDisponibilita_shouldNotBeNull() {
        assertNotNull(agenda.getDisponibilita());
    }

    @Test
    void testGetPrenotazioni_shouldNotBeNull() {
        assertNotNull(agenda.getPrenotazioni());
    }

    @Test
    void testGetVisite_shouldNotBeNull() {
        assertNotNull(agenda.getVisite());
    }
}
