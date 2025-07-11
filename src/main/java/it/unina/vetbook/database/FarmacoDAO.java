package it.unina.vetbook.database;

import it.unina.vetbook.entity.Farmaco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FarmacoDAO implements GenericDAO<Farmaco, Integer> {

    @Override
    public List<Farmaco> executeQuery(String sql, Object... params) throws SQLException {
        System.out.println("Executando SQL: " + sql);
        return new ArrayList<>();
    }
}