package it.unina.vetbook.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, K> {
    void create(T entity)                 throws SQLException;
    Optional<T> read(K id)                throws SQLException;
    void update(T entity)                 throws SQLException;
    void delete(K id)                     throws SQLException;


    List<T> executeQuery(String sql, Object... params) throws SQLException;
}

