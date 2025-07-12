package it.unina.vetbook.entity;

import it.unina.vetbook.database.DBManager;
import it.unina.vetbook.database.VisitaDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Visita {

    private int idVisita;
    private Veterinario veterinario;
    private TipoVisita tipo;
    private String descrizione;
    private double costo;
    private LocalDate data;
    private LocalTime ora;
    private List<Farmaco> farmaciPrescritti;

    public Visita(TipoVisita tipo, String descrizione, double costo, Veterinario veterinario) {
        this.veterinario = veterinario;
        this.tipo = tipo;
        this.descrizione = descrizione;
        this.costo = costo;
        this.farmaciPrescritti = new ArrayList<>();
    }

    public void prescrivi(Farmaco f) {
        farmaciPrescritti.add(f);
    }

    public LocalDate getData() { return data; }
    public LocalTime getOra() { return ora; }
    public TipoVisita getTipo() { return tipo; }
    public String getDescrizione() { return descrizione; }
    public double getCosto() { return costo; }
    public void setData(LocalDate data) { this.data = data; }
    public void setOra(LocalTime ora) { this.ora = ora; }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }

    public void setTipo(TipoVisita tipo) {
        this.tipo = tipo;
    }


    public List<Farmaco> getFarmaciPrescritti() {
        return farmaciPrescritti;
    }


    public int getIdVisita() {
        return idVisita;
    }

    public int getIdVeterinario() {
        return this.veterinario.id;
    }

    public static List<Visita> getVisiteVeterinario(int idVeterinario) {
        try (Connection conn = DBManager.getInstance().getConnection()) {
            return new VisitaDAO(conn).readAllByVet(idVeterinario);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero delle visite", e);
        }
    }


}