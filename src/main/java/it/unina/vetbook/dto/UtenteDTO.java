package it.unina.vetbook.dto;

import it.unina.vetbook.entity.UserRole;

public record UtenteDTO(
        String username,
        String email,
        String password,
        UserRole ruolo
) {
    public UtenteDTO(String username, String email, UserRole ruolo){
        this(username, email, null, ruolo);
    }
}
