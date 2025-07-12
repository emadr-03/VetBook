package it.unina.vetbook.dto;

import it.unina.vetbook.entity.AnimaleDomestico;

import java.io.InputStream;
import java.util.List;

public record ProprietarioDTO(
        String username,
        String email,
        String nome,
        String cognome,
        byte[] immagineProfilo,
        List<AnimaleDomesticoDTO> animali
) {
    public ProprietarioDTO(String username, String email, String nome, String cognome, byte[] immagineProfilo) {
        this(username, email, nome, cognome, immagineProfilo, List.of());
    }
}
