package it.unina.vetbook.control;

import it.unina.vetbook.boundary.TipoVisita;
import it.unina.vetbook.entity.Agenda;
import it.unina.vetbook.entity.Farmaco;
import it.unina.vetbook.entity.Veterinario;
import it.unina.vetbook.entity.Visita;

import java.util.List;

public class VeterinarioController {

    private static VeterinarioController instance = null;
    private final Veterinario veterinario = Veterinario.getInstance();

    public static VeterinarioController getInstance() {
        if (instance == null) {
            instance = new VeterinarioController();
        }
        return instance;
    }

    public void registraVisita(TipoVisita tipo, String descrizione, double costo, List<Farmaco> farmaci) {
        Visita v = new Visita(tipo, descrizione, costo);
        if (farmaci != null) {
            farmaci.forEach(v::prescrivi);
        }
        veterinario.registraVisita(v);
    }
}
