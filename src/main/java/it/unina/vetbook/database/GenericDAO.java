package it.unina.vetbook.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, K> {
    default void create(T entity) throws SQLException{
        //throw new UnsupportedOperationException("Not supported yet");
    }
    default Optional<T> read(K[] key) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet");
    }
    default void update(T entity) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet");
    }
    default void delete(K id) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet");
    }

    default List<T> executeQuery(String sql, Object... params) throws SQLException{
        throw new UnsupportedOperationException("Not supported yet");
    }
}

