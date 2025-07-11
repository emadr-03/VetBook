package it.unina.vetbook.control;

import it.unina.vetbook.dto.AnimaleDomesticoDTO;
import it.unina.vetbook.dto.VisitaDTO;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.Visita;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    private static AdminController instance = null;
    private final Agenda agenda = Agenda.getInstance();

    private AdminController() {
    }

    //A: uso di synchronized per futuro scaling del lato server-side
    public static synchronized AdminController getInstance() {
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

    public List<AnimaleDomesticoDTO> visualizzaAnimaliNonVaccinati() {
        // MOCK DATA: Method must not be implemented
        return List.of(
                new AnimaleDomesticoDTO(101, "Luna", "Gatto", null, null, LocalDate.of(2025, 4, 27), null),
                new AnimaleDomesticoDTO(207, "Milo", "Cane", null, null, LocalDate.of(2023, 4, 27), null),
                new AnimaleDomesticoDTO(315, "Kiwi", "Coniglio", null, null, LocalDate.of(2022, 4, 25), null)
        );
    }
}