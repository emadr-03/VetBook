package controller;

import it.unina.vetbook.control.VeterinarioController;
import it.unina.vetbook.dto.FarmacoDTO;
import it.unina.vetbook.entity.*;
import it.unina.vetbook.exception.ValidationException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VeterinarioControllerTest {

    Veterinario veterinario;
    VeterinarioController controller;
    Agenda mockAgenda;
    MockedStatic<Agenda> agendaStatic;
    MockedStatic<Farmaco> farmacoStatic;

    @BeforeEach
    void setUp() {
        veterinario = mock(Veterinario.class);
        when(veterinario.getId()).thenReturn(1);

        mockAgenda = mock(Agenda.class);
        agendaStatic = Mockito.mockStatic(Agenda.class);
        agendaStatic.when(Agenda::getInstance).thenReturn(mockAgenda);

        farmacoStatic = Mockito.mockStatic(Farmaco.class);
        controller = new VeterinarioController(veterinario);
    }

    @AfterEach
    void tearDown() {
        agendaStatic.close();
        farmacoStatic.close();
    }

    @Test
    void getFarmaciDisponibili_success() {
        Farmaco farmaco = new Farmaco(1, "NomeFarmaco", "AziendaX");
        farmacoStatic.when(Farmaco::readFarmaci).thenReturn(List.of(farmaco));

        List<FarmacoDTO> result = controller.getFarmaciDisponibili();

        assertEquals(1, result.size());
        assertEquals("NomeFarmaco", result.get(0).nome());
        assertEquals("AziendaX", result.get(0).produttore());
    }

    @Test
    void registraVisita_descrizioneTroppoLunga_fails() {
        String lungaDescrizione = "a".repeat(151);
        assertThrows(ValidationException.class, () -> controller.registraVisita(TipoVisita.CONTROLLO, lungaDescrizione, 50, List.of()));
    }

    @Test
    void registraVisita_costoNegativo_fails() {
        assertThrows(ValidationException.class, () -> controller.registraVisita(TipoVisita.CONTROLLO, "Descrizione", -1, List.of()));
    }

    @Test
    void registraVisita_success() {
        FarmacoDTO farmacoDTO = new FarmacoDTO(1, "FarmacoTest", "AziendaTest");

        controller.registraVisita(TipoVisita.CONTROLLO, "Descrizione", 100, List.of(farmacoDTO));

        verify(veterinario).registraVisita(any(Visita.class));
    }
}
