package controller;

import it.unina.vetbook.control.AgendaController;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.Disponibilita;
import it.unina.vetbook.exception.BusinessRuleViolationException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgendaControllerTest {

    AgendaController ctrl;
    Agenda            mockAgenda;
    List<Disponibilita> disp = new ArrayList<>();
    MockedStatic<Agenda> agendaStatic;

    @BeforeEach
    void setUp() {
        mockAgenda   = Mockito.mock(Agenda.class, Mockito.RETURNS_DEEP_STUBS);
        agendaStatic = Mockito.mockStatic(Agenda.class);
        agendaStatic.when(Agenda::getInstance).thenReturn(mockAgenda);

        Mockito.when(mockAgenda.getDisponibilita()).thenReturn(disp);
        Mockito.when(mockAgenda.isSlotOccupato(Mockito.any(), Mockito.any())).thenAnswer(
                inv -> disp.stream().anyMatch(d ->
                        d.getData().equals(inv.getArgument(0)) &&
                                d.getOra() .equals(inv.getArgument(1)))
        );

        ctrl = AgendaController.getInstance();
    }

    @AfterEach
    void tearDown() { agendaStatic.close(); }

    @Test
    void inserisciDisponibilita_OK() {
        LocalDate d = LocalDate.now().plusDays(1);
        LocalTime t = LocalTime.of(10,0);

        ctrl.inserisciDisponibilita(d, t);

        // verifico che sul mock sia stato delegato l'inserimento
        Mockito.verify(mockAgenda).addDisponibilita(Mockito.any());
    }

    @Test
    void inserisciDisponibilita_slotOccupato_ko() {
        LocalDate d = LocalDate.now().plusDays(1);
        LocalTime t = LocalTime.of(10,0);
        disp.add(new Disponibilita(d, t));   // occupo lo slot

        assertThrows(BusinessRuleViolationException.class,
                () -> ctrl.inserisciDisponibilita(d, t));
    }
}

