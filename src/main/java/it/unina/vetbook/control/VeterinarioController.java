package it.unina.vetbook.control;

import it.unina.vetbook.entity.TipoVisita;
import it.unina.vetbook.dto.FarmacoDTO;
import it.unina.vetbook.entity.Farmaco;
import it.unina.vetbook.entity.Veterinario;
import it.unina.vetbook.entity.Visita;

import java.util.List;
import java.util.stream.Collectors;

public class VeterinarioController {

    private final Veterinario veterinario;

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

        Visita v = new Visita(tipo, descrizione, costo);

        for (FarmacoDTO farmaco : farmaci) {
            v.prescrivi(new Farmaco(farmaco.nome(), farmaco.produttore()));
        }

        veterinario.registraVisita(v);
    }

}

