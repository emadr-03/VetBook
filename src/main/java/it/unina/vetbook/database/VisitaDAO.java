package it.unina.vetbook.database;

import it.unina.vetbook.entity.Visita;

import java.sql.*;

public class VisitaDAO implements GenericDAO<Visita, Integer> {

    @Override
    public void create(Visita v) throws SQLException {
        String sql = "INSERT INTO Visita (data, ora, tipo, descrizione, costo, codiceChipAnimale) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(v.getData()));
            stmt.setTime(2, Time.valueOf(v.getOra()));
            stmt.setString(3, v.getTipo().name());
            stmt.setString(4, v.getDescrizione());
            stmt.setDouble(5, v.getCosto());
            stmt.setInt(6, v.getAnimale().getCodiceChip());

            stmt.executeUpdate();
        }
    }

}
