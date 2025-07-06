package it.unina.vetbook.database;

import it.unina.vetbook.entity.Prenotazione;

import java.sql.*;

public class PrenotazioneDAO implements GenericDAO<Prenotazione, Integer> {

    @Override
    public void create(Prenotazione p) throws SQLException {
        String sql = "INSERT INTO Prenotazione (codice_chip, data, ora) VALUES (?, ?, ?)";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getAnimale().getCodiceChip());
            stmt.setDate(2, Date.valueOf(p.getData()));
            stmt.setTime(3, Time.valueOf(p.getOra()));

            stmt.executeUpdate();
        }
    }
}
