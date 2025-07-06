package it.unina.vetbook.dto;

public class ProprietarioDTO {
    public final String nome;
    public final String cognome;
    public final String username;
    public final String email;
    public final String imagePath;

    public ProprietarioDTO(String nome, String cognome, String username, String email, String imagePath) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.imagePath = imagePath;
    }
}