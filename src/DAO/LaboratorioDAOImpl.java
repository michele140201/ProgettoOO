package DAO;

import Controller.*;
import Model.Laboratorio;
import Model.Progetto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDAOImpl implements LaboratorioDAO{
    ConnectionController controller = new ConnectionController();
    Connection con;
    @Override
    public String[] NomeLab() {
        String[] Labs;
        int i = 0;
        String sql = "Select * from Laboratorio";
        int size = countLab();
        Labs = new String[size];
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String Giorgio = new String(rs.getString("nome_lab"));
                Labs[i] = Giorgio;

                i++;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Labs;
    }

    @Override
    public int countLab() {
        int i = 0;
        String sql = "Select count(*) AS count from Laboratorio";

        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    i = rs.getInt("count");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public List<Laboratorio> ottieniLabs() {
        List<Laboratorio> Laboratori = new ArrayList<>();
        String sql = ("Select * from Laboratorio");
        int i = 0;
        int progetto;
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("progetto") == null) progetto = 0;
                else progetto = rs.getInt("progetto");
                Laboratorio lab = new Laboratorio(rs.getString("nome_lab") ,(String) rs.getString("topic") ,progetto , rs.getInt("referente"));
                Laboratori.add(lab);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Laboratori;
    }

    @Override
    public int Inserisci(String Nome_Lab, String Topic) {
        int i = 0;
        String sql = ("Insert into laboratorio(nome_lab , topic) values('" + Nome_Lab +"','" + Topic + "')");
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
    public int remove(String Nome) {
        String sql = ("delete from laboratorio where laboratorio.nome_lab = '" + Nome + "'");
        int i = 0;
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            i = stmt.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int riassegna(String Nome_Lab, int id_dip) {
        int c = 0;
        String sql = ("update Laboratorio set referente = " + id_dip + "where laboratorio.nome_lab = '" + Nome_Lab + "'");
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
    public int riassegna_id(String Nome_Lab, int Progetto) {
        int c = 0;
        String sql = ("update Laboratorio set progetto = " + Progetto + " where nome_lab = '" + Nome_Lab + "'");
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
    public int conta_prog(int progetto) {
        int c = 0;
        String sql = ("Select count(*) as conto from laboratorio where laboratorio.progetto = " + progetto);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                c = rs.getInt("conto");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public List<Laboratorio> lab_prog(int cup) {
        List<Laboratorio> labs = new ArrayList<>();
        String sql = ("Select * from laboratorio where laboratorio.progetto = " + cup);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Laboratorio lab = new Laboratorio(rs.getString("nome_lab"), rs.getString("topic") , rs.getInt("progetto"), rs.getInt("Referente") );
                labs.add(lab);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return labs;
    }

    @Override
    public int getReferente_Lab(String nome_lab){
        int id_dip = 0;
        String sql = ("Select referente from laboratorio where laboratorio.nome_lab = '" + nome_lab + "'");
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
    public int getProgetto(String NomeLab) {
        int cup = 0;
        String sql = ("Select progetto from laboratorio where laboratorio.nome_lab = '" + NomeLab + "'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                cup = rs.getInt(cup);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return cup;
    }
    @Override
    public String getNomeLab(int cup){
        String NomeLab = null;
        String sql = ("Select nome_lab from laboratorio where laboratorio.progetto = " + cup);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                NomeLab = rs.getString("nome_lab");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return NomeLab;
    }
}
