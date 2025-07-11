package it.unina.vetbook.control;

import it.unina.vetbook.entity.TipoVisita;
import it.unina.vetbook.dto.FarmacoDTO;
import it.unina.vetbook.entity.Farmaco;
import it.unina.vetbook.entity.Veterinario;
import it.unina.vetbook.entity.Visita;

import java.util.List;

public class VeterinarioController {

    private final Veterinario veterinario;

    public VeterinarioController(Veterinario veterinario){
        this.veterinario = veterinario;
    }


    public void registraVisita(TipoVisita tipo, String descrizione, double costo, List<FarmacoDTO> farmaci) {
        Visita v = new Visita(tipo, descrizione, costo);
        for(FarmacoDTO farmaco : farmaci){
            v.prescrivi(new Farmaco(farmaco.nome(), farmaco.produttore()));
        }
        veterinario.registraVisita(v);
    }
}

