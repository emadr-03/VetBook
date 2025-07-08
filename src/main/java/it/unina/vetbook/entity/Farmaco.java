package it.unina.vetbook.entity;

public class Farmaco {

    private int id;
    private String nome;
    private String produttore;

    public Farmaco(String nome, String produttore) {
        this.nome = nome;
        this.produttore = produttore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProduttore() {
        return produttore;
    }

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }
}