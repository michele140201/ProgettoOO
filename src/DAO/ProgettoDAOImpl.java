package DAO;

import Model.Progetto;

import java.util.ArrayList;
import java.util.List;
import Controller.*;
import java.sql.*;

public class ProgettoDAOImpl implements ProgettoDAO{
    ConnectionController controller = new ConnectionController();
    Connection con;
    @Override
    public List<Progetto> ottieniprogetti() {
        List<Progetto> Lista;
        Lista = new ArrayList<>();
        Progetto p;
        String sql = ("Select * from Progetto");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                p = new Progetto(rs.getInt("CUP"), rs.getString("nome_p"),rs.getString("referente"),rs.getString("responsabile"), rs.getString("topic"));
                Lista.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return Lista;
    }

    @Override
    public int GeneraCup() {
        int cup = 1;
        String sql = ("SELECT max(cup) as massimo from progetto");
        try{
            con = controller.ConnectionController();
           Statement stmt = con.createStatement();
           ResultSet rs = stmt.executeQuery(sql);
           while(rs.next()){
               cup = rs.getInt("massimo") + 1;
           }
        }catch(SQLException e){
            e.printStackTrace();        }
        return cup;
    }

    @Override
    public int InserisciProgetto(Progetto p) {
        String sql = ("Insert into Progetto(CUP,Nome_p,topic) values ('"  + p.getCUP() + "','" + p.getNome_Prog() + "','" + p.getTopic() + "')");
        int i = 0;
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            i = stmt.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public Progetto NuovoInserito() {
        Progetto p = new Progetto();
        String sql = ("Select * from Progetto where Progetto.cup = (select max(cup) from progetto)");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String topic = rs.getString("topic");
               int cup = rs.getInt("cup");
                p.setNome_Prog(rs.getString("nome_p"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public int EliminaProgetto(int cup) {
        String sql = ("Delete from progetto where progetto.cup = " + cup);
        int i = 0;
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            i = stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int conta_progetti() {
        int i = 0;
        String sql = ("Select COUNT(*) as conto from progetto");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                i = rs.getInt("conto");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int set_referente(int id_dip, int cup) {
        int c = 0;
        String sql = ("Update progetto set referente = " + id_dip + "where cup = " + cup);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            c = stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public int set_dirigente(int id_dip, int cup) {
        int c = 0;
        String sql = ("Update progetto set responsabile = " + id_dip + "where cup = " + cup);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            c = stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public int get_referente(int cup) {
        int id_dip = 0;
        String sql = ("Select referente from progetto where progetto.cup = " + cup);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                id_dip = rs.getInt("referente");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return id_dip;
    }

    @Override
    public int get_responsabile(int cup) {
        int id_dip = 0;
        String sql = ("Select responsabile from progetto where progetto.cup = " + cup);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                id_dip = rs.getInt("responsabile");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return id_dip;
    }
}
