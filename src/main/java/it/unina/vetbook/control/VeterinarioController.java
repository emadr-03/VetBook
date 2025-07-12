package it.unina.vetbook.control;

import it.unina.vetbook.entity.*;
import it.unina.vetbook.dto.FarmacoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class VeterinarioController {

    private final Veterinario veterinario;
    private final Agenda agenda = Agenda.getInstance();

    public VeterinarioController(Veterinario veterinario){
        this.veterinario = veterinario;
    }

    public List<FarmacoDTO> getFarmaciDisponibili() {
        List<Farmaco> farmaciEntity = Farmaco.readFarmaci();
        return farmaciEntity.stream()
                .map(f -> new FarmacoDTO(f.getId(), f.getNome(), f.getProduttore()))
                .collect(Collectors.toList());
    }

    public void registraVisita(TipoVisita tipo, String descrizione, double costo, List<FarmacoDTO> farmaci) {
        // 1. Verifica lunghezza descrizione
        if (descrizione.length() > 150) {
            throw new IllegalArgumentException("La descrizione non può superare i 150 caratteri.");
        }
        // 2. Verifica costo valido
        if (costo < 0) {
            throw new IllegalArgumentException("Il costo non può essere minore di 0.");
        }

        Visita v = new Visita(tipo, descrizione, costo, veterinario.getId());

        for (FarmacoDTO farmaco : farmaci) {
            v.prescrivi(new Farmaco(farmaco.id(), farmaco.nome(), farmaco.produttore()));
        }

        veterinario.registraVisita(v);
    }

}

