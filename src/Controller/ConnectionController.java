package Controller;

import java.sql.Connection;


public class ConnectionController {
    Connectiondb db = new Connectiondb();
    Connection con = null;
    String dbname = "Progetto";
    String username = "postgres";
    String password = "Girafarig20";



    public Connection ConnectionController(){
        con = db.connect_to_db( dbname , username , password );
        return con;
    }


}
