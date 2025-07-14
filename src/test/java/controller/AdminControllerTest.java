package controller;

import it.unina.vetbook.control.AdminController;
import it.unina.vetbook.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AdminControllerTest {

    Amministratore admin;
    AdminController ctrl;
    List<Visita> visite = new ArrayList<>();

    @Test
    void incassoGiornaliero_ok() {
        Agenda mockAgenda = mock(Agenda.class);

        when(mockAgenda.getVisite()).thenReturn(visite);
        when(mockAgenda.visualizzaVisiteGiornaliere()).thenReturn(visite);

        try (MockedStatic<Agenda> ignored = Mockito.mockStatic(Agenda.class)) {
            Mockito.when(Agenda.getInstance()).thenReturn(mockAgenda);

            visite.add(new Visita(TipoVisita.CONTROLLO,    "Visita1", 30,
                    LocalDate.now(), LocalTime.NOON,           1));
            visite.add(new Visita(TipoVisita.VACCINAZIONE, "Vaccino",  40,
                    LocalDate.now(), LocalTime.NOON.plusHours(1), 1));

            admin = new Amministratore("admin","a@x.it","pw");
            ctrl  = AdminController.getInstance(admin);

            assertEquals(70.0, ctrl.getTotaleIncassoGiornaliero(), 0.001);
        }

    }
}


