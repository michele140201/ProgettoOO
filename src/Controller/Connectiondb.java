package Controller;

import com.sun.jdi.event.ExceptionEvent;

import java.sql.Connection;
import java.sql.DriverManager;


public class Connectiondb {
    Connection conn = null;

    /**
     * Funzione per connettersi al database
     * @param db_name
     * @param user
     * @param pass
     * @return
     */
    public Connection connect_to_db(String db_name,String user, String pass) {

    try {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+ db_name,user,pass);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return conn;

    }
}