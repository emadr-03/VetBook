package entity;

import it.unina.vetbook.entity.*;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmministratoreTest {

    Amministratore admin;
    Agenda mockAgenda;
    MockedStatic<Agenda> agendaStatic;

    @BeforeEach
    void setUp() {
        mockAgenda = mock(Agenda.class);
        agendaStatic = Mockito.mockStatic(Agenda.class);
        agendaStatic.when(Agenda::getInstance).thenReturn(mockAgenda);
        admin = new Amministratore("admin", "admin@mail.com", "pass");
    }

    @AfterEach
    void tearDown() {
        agendaStatic.close();
    }

    @Test
    void testOttieniIncasso_correctSumReturned() {
        List<Visita> todayVisits = List.of(
                new Visita(TipoVisita.CHIRURGIA, "desc", 50, LocalDate.now(), LocalTime.NOON, 1),
                new Visita(TipoVisita.VACCINAZIONE, "desc", 70, LocalDate.now(), LocalTime.NOON.plusHours(1), 1)
        );
        when(mockAgenda.visualizzaVisiteGiornaliere()).thenReturn(todayVisits);

        double incasso = admin.ottieniIncasso(todayVisits);

        assertEquals(120.0, incasso);
    }
}
