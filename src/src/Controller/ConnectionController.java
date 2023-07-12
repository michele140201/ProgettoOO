package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionController {
    private final String dbname = "jdbc:postgresql://localhost:5432/Progetto";
    private final String username = "postgres";
    private final String password = "Girafarig20";

    public ConnectionController() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbname, username, password);
    }

}
