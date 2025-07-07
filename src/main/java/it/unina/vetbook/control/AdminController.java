package it.unina.vetbook.control;

import it.unina.vetbook.dto.VisitaDTO;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.Visita;

import java.util.ArrayList;
import java.util.List;

public class AdminController {

    private static AdminController instance = null;
    private final Agenda agenda = Agenda.getInstance();

    private AdminController() {}

    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    public List<VisitaDTO> getVisiteGiornaliere() {
        List<Visita> visiteEntity = agenda.visualizzaVisiteGiornaliere();
        List<VisitaDTO> visiteDto = new ArrayList<>();
        for (Visita v : visiteEntity) {
            visiteDto.add(new VisitaDTO(v.getTipo(), v.getDescrizione(), v.getCosto()));
        }
        return visiteDto;
    }

    public double getTotaleIncassoGiornaliero() {
        List<Visita> visiteGiornaliere = agenda.visualizzaVisiteGiornaliere();
        return agenda.ottieniIncasso(visiteGiornaliere);
    }

    public Object[][] visualizzaAnimaliNonVaccinati() {
        throw new UnsupportedOperationException("Not supported yet");
    }
}