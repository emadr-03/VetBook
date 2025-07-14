package controller;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.dto.AgendaDTO;
import it.unina.vetbook.dto.DisponibilitaDTO;
import it.unina.vetbook.dto.PrenotazioneDTO;
import it.unina.vetbook.entity.*;
import it.unina.vetbook.exception.BusinessRuleViolationException;
import it.unina.vetbook.exception.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AgendaControllerTest {

    AgendaController ctrl;
    Agenda           mockAgenda;
    List<Disponibilita> disponibilita = new ArrayList<>();
    List<Prenotazione>  prenotazioni  = new ArrayList<>();
    List<Visita>        visite        = new ArrayList<>();
    MockedStatic<Agenda> agendaStatic;

    @BeforeEach
    void setUp() {
        mockAgenda = Mockito.mock(Agenda.class, Mockito.RETURNS_DEEP_STUBS);
        agendaStatic = Mockito.mockStatic(Agenda.class);
        agendaStatic.when(Agenda::getInstance).thenReturn(mockAgenda);

        Mockito.when(mockAgenda.getDisponibilita()).thenReturn(disponibilita);
        Mockito.when(mockAgenda.getPrenotazioni()).thenReturn(prenotazioni);
        Mockito.when(mockAgenda.getVisite()).thenReturn(visite);
        Mockito.when(mockAgenda.isSlotOccupato(Mockito.any(), Mockito.any())).thenAnswer(
                i -> disponibilita.stream().anyMatch(d ->
                        d.getData().equals(i.getArgument(0)) &&
                                d.getOra().equals(i.getArgument(1)))
        );
        Mockito.doAnswer(i -> {
            Disponibilita d = i.getArgument(0);
            disponibilita.add(d);
            return null;
        }).when(mockAgenda).addDisponibilita(Mockito.any());

        ctrl = AgendaController.getInstance();
    }


    @AfterEach
    void tearDown() {
        agendaStatic.close();
        resetSingleton();
    }

    private void resetSingleton() {
        try {
            Field f = AgendaController.class.getDeclaredField("instance");
            f.setAccessible(true);
            f.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void inserisciDisponibilita_ok(){
        DisponibilitaDTO mock = new DisponibilitaDTO(LocalDate.now().plusYears(5), LocalTime.NOON, Stato.LIBERA);

        ctrl.inserisciDisponibilita(mock.data(), mock.ora());
        List<DisponibilitaDTO> result = ctrl.visualizzaDisponibilita();

        assertEquals(1, result.size());
        assertEquals(mock.data(), result.getFirst().data());
        assertEquals(mock.ora(), result.getFirst().ora());
        assertEquals(mock.stato(), result.getFirst().stato());
    }

    @Test
    void inserisciDisponibilita_null_ko() {
        assertThrows(ValidationException.class,
                () -> ctrl.inserisciDisponibilita(null, LocalTime.NOON));
    }

    @Test
    void inserisciDisponibilita_past_ko() {
        LocalDate ieri = LocalDate.now().minusDays(1);
        assertThrows(ValidationException.class,
                () -> ctrl.inserisciDisponibilita(ieri, LocalTime.NOON));
    }

    @Test
    void inserisciDisponibilita_slotOccupato_ko() {
        LocalDate d = LocalDate.now().plusDays(1);
        LocalTime t = LocalTime.of(9, 0);
        disponibilita.add(new Disponibilita(d, t));

        assertThrows(BusinessRuleViolationException.class,
                () -> ctrl.inserisciDisponibilita(d, t));
    }


    @Test
    void visualizzaDisponibilita_sortedOK() {
        LocalDate base = LocalDate.now().plusDays(1);
        disponibilita.add(new Disponibilita(base, LocalTime.of(11,0)));
        disponibilita.add(new Disponibilita(base, LocalTime.of( 9,0)));

        List<DisponibilitaDTO> out = ctrl.visualizzaDisponibilita();

        assertEquals(LocalTime.of(9,0),  out.get(0).ora());
        assertEquals(LocalTime.of(11,0), out.get(1).ora());
    }


    @Test
    void visualizzaPrenotazioni_mappingOK() {
        AnimaleDomestico a = new AnimaleDomestico(1234567890, 1,"Fido","Cane","Lab","Nero",
                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()));
        prenotazioni.add(new Prenotazione(LocalDate.now(), LocalTime.NOON, a));

        List<PrenotazioneDTO> out = ctrl.visualizzaPrenotazioniGiornaliere();

        assertEquals(1, out.size());
        assertEquals("Fido", out.getFirst().animale().nome());
    }


    @Test
    void visualizzaAgenda_mergeAndSort() {
        LocalDate d = LocalDate.now().plusDays(1);
        disponibilita.add(new Disponibilita(d, LocalTime.of( 9,0)));
        prenotazioni.add(new Prenotazione   (d, LocalTime.of(10,0), Mockito.mock(AnimaleDomestico.class)));
        visite.add(new Visita(TipoVisita.CONTROLLO,"Desc",30,
                d, LocalTime.of(11,0), 1));

        List<AgendaDTO> righe = ctrl.visualizzaAgenda();

        assertEquals(3, righe.size());
        assertEquals("Disponibilità", righe.get(0).tipoEvento());
        assertEquals("Prenotazione", righe.get(1).tipoEvento());
        assertEquals("Visita", righe.get(2).tipoEvento());
    }
}
