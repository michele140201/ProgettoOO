package DAO;

import Controller.*;
import Model.Laboratorio;
import Model.Progetto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LaboratorioDAOImpl implements LaboratorioDAO{
    ConnectionController controller = new ConnectionController();
    Connection con;

    /**
     * Funzione per avere tutti nomi dei Laboratori
     * @return
     */
    @Override
    public String[] getNomeLab() throws Exception {
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
            return Labs;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Funzione per contare tutti i Laboratori
     * @return
     */
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

    /**
     * Funzione per ottenere tutti i laboratori
     * @return
     */
    @Override
    public List<Laboratorio> getLabs() throws Exception{
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
                Laboratorio lab = new Laboratorio(rs.getString("nome_lab") , rs.getString("topic") ,progetto , rs.getInt("referente"));
                Laboratori.add(lab);
            }
            return Laboratori;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Inserimento di un nuovo laboratorio
     * @param Nome_Lab
     * @param Topic
     * @return
     */
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

    /**
     * Rimozione di un laboratorio
     * @param Nome
     * @return
     */
    @Override
    public int remove(String Nome) throws Exception{
        String sql = ("delete from laboratorio where laboratorio.nome_lab = '" + Nome + "'");
        int i = 0;
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            i = stmt.executeUpdate(sql);
            return i;
        }catch (SQLException e){
           throw new Exception(e);
        }

    }

    /**
     * Assegnazione di un nuovo Referente
     * @param Nome_Lab
     * @param id_dip
     * @return
     */
    @Override
    public int riassegnaDipendente(String Nome_Lab, int id_dip) throws Exception{
        int c = 0;
        String sql = ("update Laboratorio set referente = " + id_dip + "where laboratorio.nome_lab = '" + Nome_Lab + "'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            c = stmt.executeUpdate(sql);
            return c;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Riassegnazione di un nuovo progetto
     * @param Nome_Lab
     * @param Progetto
     * @return
     */
    @Override
    public int riassegnaProgetto(String Nome_Lab, int Progetto) throws Exception{
        int c = 0;
        String sql = ("update Laboratorio set progetto = " + Progetto + " where nome_lab = '" + Nome_Lab + "'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            c = stmt.executeUpdate(sql);
            return c;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Funzione per contare tutti i progetti
     * @param progetto
     * @return
     */
    @Override
    public int countProgetti(int progetto)throws Exception{
        int c = 0;
        String sql = ("Select count(*) as conto from laboratorio where laboratorio.progetto = " + progetto);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                c = rs.getInt("conto");
            }
            return c;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Funzione per ottenere tutti i laboratori che stanno lavorando ad un progetto
     * @param cup
     * @return
     */
    @Override
    public List<Laboratorio> getLaboratori(int cup) throws Exception{
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
            return labs;
        }catch (SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Funzione per ottenere il referente del laboratorio
     * @param nome_lab
     * @return
     */
    @Override
    public int getReferenteLab(String nome_lab)throws Exception{
        int id_dip = 0;
        String sql = ("Select referente from laboratorio where laboratorio.nome_lab = '" + nome_lab + "'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                id_dip = rs.getInt("referente");
            }
            return id_dip;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Funzione per ottenere a quale progetto sta lavorando il laboratorio
     * @param NomeLab
     * @return
     */
    @Override
    public int getProgetto(String NomeLab) throws Exception{
        int cup = 0;
        String sql = ("Select progetto from laboratorio where laboratorio.nome_lab = '" + NomeLab + "'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                cup = rs.getInt(cup);
            }
            return cup;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

}
