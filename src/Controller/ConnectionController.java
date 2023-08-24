package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che si occupa di gestire la connessione del programma con il database
 */

public class ConnectionController {
    private final String dbname = "jdbc:postgresql://localhost:5432/Progetto";
    private final String username = "postgres";
    private final String password = "Girafarig20";

    /**
     * Costruttore del controller che inizializza i driver necessari per connettere il programma al database
     * @throws ClassNotFoundException
     */

    public ConnectionController() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    /**
     * Metodo che permette di connettersi ad un database generico
     * Modificando le variabili è possibile accedere ad un qualsiasi database ed utilizzare quei dati
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbname, username, password);
    }

}
