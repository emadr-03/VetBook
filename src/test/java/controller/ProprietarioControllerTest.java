package controller;

import it.unina.vetbook.control.ProprietarioController;
import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.DisponibilitaDTO;
import it.unina.vetbook.entity.*;
import it.unina.vetbook.exception.BusinessRuleViolationException;
import it.unina.vetbook.exception.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProprietarioControllerTest {

    Proprietario proprietario;
    ProprietarioController controller;
    Agenda mockAgenda;
    MockedStatic<Agenda> agendaStatic;
    List<AnimaleDomestico> animali;

    @BeforeEach
    void setUp() {
        proprietario = mock(Proprietario.class);
        when(proprietario.getId()).thenReturn(1);
        animali = new ArrayList<>();
        when(proprietario.getAnimali()).thenReturn(animali);

        doAnswer(invocation -> {
            AnimaleDomestico a = invocation.getArgument(0);
            animali.add(a);
            return null;
        }).when(proprietario).addAnimale(any());

        mockAgenda = mock(Agenda.class);
        agendaStatic = Mockito.mockStatic(Agenda.class);
        agendaStatic.when(Agenda::getInstance).thenReturn(mockAgenda);

        controller = new ProprietarioController(proprietario);
    }


    @AfterEach
    void tearDown() {
        agendaStatic.close();
    }

    @Test
    void gestioneProfilo_aggiornaCampi_success() {
        controller.gestioneProfilo("newUser", "Mario", "Rossi", "mail@test.com", "password");
        verify(proprietario).setUsername("newUser");
        verify(proprietario).setNome("Mario");
        verify(proprietario).setCognome("Rossi");
        verify(proprietario).setEmail("mail@test.com");
        verify(proprietario).setPassword("password");
    }

    @Test
    void aggiornaImmagineProfilo_fileNull_exception() {
        assertThrows(ValidationException.class, () -> controller.aggiornaImmagineProfilo(null));
    }

    @Test
    void inserisciAnimale_success() {
        controller.inserisciAnimale(1234567890, "Fido", "Cane", "Labrador", "Nero", LocalDate.now().minusYears(1));
        assertEquals(1, animali.size());
        assertEquals("Fido", animali.get(0).getNome());
    }

    @Test
    void inserisciAnimale_codiceChipDuplicato_exception() {
        animali.add(new AnimaleDomestico(1234567890, 1, "Fido", "Cane", "Labrador", "Nero", LocalDate.now().minusYears(1)));
        assertThrows(BusinessRuleViolationException.class, () ->
                controller.inserisciAnimale(1234567890, "Altro", "Gatto", "Siamese", "Bianco", LocalDate.now().minusYears(2)));
    }

    @Test
    void modificaAnimale_success() {
        animali.add(new AnimaleDomestico(1234567890, 1, "Fido", "Cane", "Labrador", "Nero", LocalDate.now().minusYears(1)));
        controller.modificaAnimale(1234567890, "NuovoNome", "NuovoTipo", "NuovaRazza", "Bianco", LocalDate.now().minusYears(2));
        assertEquals("NuovoNome", animali.get(0).getNome());
    }

    @Test
    void eliminaAnimale_success() {
        animali.add(new AnimaleDomestico(1234567890, 1, "Fido", "Cane", "Labrador", "Nero", LocalDate.now().minusYears(1)));
        controller.eliminaAnimale(1234567890);
        assertEquals(0, animali.size());
    }

    @Test
    void effettuaPrenotazione_success() {
        AnimaleDomestico animale = new AnimaleDomestico(1234567890, 1, "Fido", "Cane", "Labrador", "Nero", LocalDate.now().minusYears(1));
        animali.add(animale);
        DisponibilitaDTO disponibilita = new DisponibilitaDTO(LocalDate.now().plusDays(1), LocalTime.NOON, Stato.LIBERA);
        controller.effettuaPrenotazione(new AnimaleDomesticoDTO(1234567890, "Fido", "Cane", "Labrador", "Nero", LocalDate.now().minusYears(1), 1), disponibilita);
        verify(mockAgenda).prenotaVisita(any(Prenotazione.class));
    }

    @Test
    void getAnimaliProprietario_returnsCorrectDTOs() {
        animali.add(new AnimaleDomestico(1234567890, 1, "Fido", "Cane", "Labrador", "Nero", LocalDate.now().minusYears(1)));
        List<AnimaleDomesticoDTO> dto = controller.getAnimaliProprietario();
        assertEquals(1, dto.size());
        assertEquals("Fido", dto.get(0).nome());
    }
}
