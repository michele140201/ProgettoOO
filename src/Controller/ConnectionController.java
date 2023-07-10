package Controller;

import java.sql.Connection;


public class ConnectionController {
    Connectiondb db = new Connectiondb();
    Connection con = null;
    String dbname = "Progetto";
    String username = "postgres";
    String password = "Girafarig20";


    /**
     * Funzione per la creazione di una connessione
     * Quando bisogna cambiare database, basta modificare i dati qui
     * @return
     */
    public Connection ConnectionController(){
        con = db.connect_to_db( dbname , username , password );
        return con;
    }


}
