package Controller;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionController {
    Connection con = null;
    String dbname = "Progetto";
    String username = "postgres";
    String password = "Girafarig20";

    public Connection ConnectionController() {
        Connection conn = null;

        /**
         * Funzione per connettersi al database
         * @param db_name
         * @param user
         * @param pass
         * @return
         */
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
