package it.unina.vetbook.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBManager {

    private static final String URL      = "jdbc:postgresql://localhost:5432/vetclinic";
    private static final String USER     = "vet_user";
    private static final String PASSWORD = "vet_password";
    private static DBManager instance;

    private DBManager() { }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) { }
        }
    }
}
