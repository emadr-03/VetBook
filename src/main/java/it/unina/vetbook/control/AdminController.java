package it.unina.vetbook.control;

import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.Visita;

import java.util.List;

public class AdminController {

    private static AdminController instance = null;

    private Agenda agenda = Agenda.getInstance();

    private AdminController() {}

    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    public Object[][] visualizzaIncassiGiornalieri() {
        List<Visita> visiteGiornaliere = agenda.visualizzaVisiteGiornaliere();
        double incasso = agenda.ottieniIncasso(visiteGiornaliere);
        return convertiVisiteIncasso(visiteGiornaliere, incasso);
    }

    public Object[][] visualizzaAnimaliNonVaccinati() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    private Object[][] convertiVisiteIncasso(List<Visita> visite, double incasso) {
        Object[][] tabella = new Object[visite.size() + 1][];

        for (int i = 0; i < visite.size(); i++) {
            Visita v = visite.get(i);
            tabella[i] = new Object[]{v.getTipo(), v.getDescrizione(), v.getCosto()};
        }

        tabella[visite.size()] = new Object[]{"", "TOTALE", incasso};

        return tabella;
    }
}

