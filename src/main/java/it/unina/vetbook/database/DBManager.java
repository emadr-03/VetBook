package it.unina.vetbook.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBManager {

    private static final String URL      = "jdbc:mysql://localhost:3306/vetbook";
    private static final String USER     = "root";
    private static final String PASSWORD = "password";
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
