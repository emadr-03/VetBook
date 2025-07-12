package it.unina.vetbook.entity;

import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.database.FarmacoDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Farmaco {

    private int id;
    private String nome;
    private String produttore;

    public Farmaco(String nome, String produttore) {
        this.nome = nome;
        this.produttore = produttore;
    }

    public Farmaco(int id, String nome, String produttore) {
        this.id = id;
        this.nome = nome;
        this.produttore = produttore;
    }

    public static List<Farmaco> readFarmaci(){
        try(Connection conn = DBManager.getInstance().getConnection()) {
            FarmacoDAO farmacoDAO = new FarmacoDAO(conn);
            return farmacoDAO.readAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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