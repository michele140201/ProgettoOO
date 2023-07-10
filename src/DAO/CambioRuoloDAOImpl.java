package DAO;

import Controller.*;

import java.sql.*;
import java.time.LocalDate;


public class CambioRuoloDAOImpl implements CambioRuoloDAO{
    ConnectionController controller = new ConnectionController();
    Connection con;
    @Override
    public Date data_cambio(int id_dip) {
        Date data = null;
        String sql = ("Select * from Ruolo where Ruolo.id_dip = " + id_dip);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                data = rs.getDate("data_cambio");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return data;
    }
    @Override
    public int inserisci_data_promozione(int id_dip) {
        String data = LocalDate.now().toString();
        Date ora = Date.valueOf(data);
        String sql = ("Insert into Ruolo(id_dip,data_cambio) values ('" + id_dip + "','" + data + "')");

        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    @Override
    public int delete_promozione(int id_dip) {
        String sql = ("Delete from Ruolo where ruolo.id_dip = "+id_dip);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

}
