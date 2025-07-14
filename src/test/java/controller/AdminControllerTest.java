package controller;

import it.unina.vetbook.control.AdminController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.VisitaDTO;
import it.unina.vetbook.entity.*;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    AdminController controller;
    Amministratore mockAdmin;
    Agenda mockAgenda;
    MockedStatic<Agenda> agendaStatic;

    List<Visita> visite;

    @BeforeEach
    void setUp() {
        visite = List.of(
                new Visita(TipoVisita.VACCINAZIONE, "Visita A", 50.0, LocalDate.now(), null, 1),
                new Visita(TipoVisita.CONTROLLO, "Visita B", 100.0, LocalDate.now(), null, 1)
        );

        mockAdmin = mock(Amministratore.class);
        mockAgenda = mock(Agenda.class);

        agendaStatic = Mockito.mockStatic(Agenda.class);
        agendaStatic.when(Agenda::getInstance).thenReturn(mockAgenda);

        when(mockAgenda.visualizzaVisiteGiornaliere()).thenReturn(visite);
        when(mockAdmin.ottieniIncasso(visite)).thenReturn(150.0);

        controller = AdminController.getInstance(mockAdmin);
    }

    @AfterEach
    void tearDown() {
        agendaStatic.close();
        resetSingleton();
    }

    private void resetSingleton() {
        try {
            Field f = AdminController.class.getDeclaredField("instance");
            f.setAccessible(true);
            f.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getVisiteGiornaliere_returnsCorrectDTOs() {
        List<VisitaDTO> result = controller.getVisiteGiornaliere();
        assertEquals(2, result.size());
        assertEquals("Visita A", result.get(0).descrizione());
        assertEquals("Visita B", result.get(1).descrizione());
    }

    @Test
    void getTotaleIncassoGiornaliero_returnsCorrectAmount() {
        double incasso = controller.getTotaleIncassoGiornaliero();
        assertEquals(150.0, incasso);
    }

    @Test
    void visualizzaMockAnimaliNonVaccinati_returnsExpectedMockList() {
        List<AnimaleDomesticoDTO> animali = controller.visualizzaMockAnimaliNonVaccinati();
        assertEquals(3, animali.size());
        assertEquals("Luna", animali.get(0).nome());
        assertEquals("Milo", animali.get(1).nome());
        assertEquals("Kiwi", animali.get(2).nome());
    }
}
