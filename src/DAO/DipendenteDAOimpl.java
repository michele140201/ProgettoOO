package DAO;

import Controller.*;
import Model.CambioRuolo;
import Model.Dipendente;
import com.sun.source.tree.StatementTree;

import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.ArrayList;
import java.util.List;


public class DipendenteDAOimpl implements DipendenteDAO{
    private Dipendente dip;
    ConnectionController controller = new ConnectionController();
    Connection con;
    private List<Dipendente> dipendenti = new ArrayList<>();

    /**
     * Funzione per avere tutti i dipendenti
     * @return
     */
    @Override
    public List<Dipendente> getDipendente() throws Exception {
        String sql = ("SELECT * FROM DIPENDENTE");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                dip = new Dipendente(rs.getString("Nome"), rs.getString("Cognome"),rs.getInt("id_dip"), rs.getBoolean("dirigente"), rs.getDate("Data_assunzione"), rs.getDate("Data_N"), rs.getString("nome_lab") );
                dipendenti.add(dip);

            }
            return dipendenti;
        }catch(SQLException e){
            throw new Exception(e);
        }

    }

    /**
     * Funzione per rimuovere un dipendente
     * @param id_dip
     * @return
     */
    @Override
    public int removeDipendente(int id_dip) {
        String sql = ("delete from Dipendente where Dipendente.id_dip = "+ id_dip);
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

    /**
     * Funzione per aggiungere un dipendente
     * @param nuovodip
     * @return
     */
    @Override
    public int insertDipendente(Dipendente nuovodip) {
        String sql = "insert into Dipendente(nome,cognome,data_n, id_dip,dirigente, data_assunzione)  values('"+nuovodip.getNome()+"','" + nuovodip.getCognome()+"','"+nuovodip.getData_nascita()+"','"+nuovodip.getId_dip()+"','"+nuovodip.isDirigente()+"','"+nuovodip.getAssunzione()+"')";
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            int i = stmt.executeUpdate(sql);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Funzione per mod
     * @return
     */


    public int count(){
        String sql = ("select count(*) as count from dipendente");
        int i = 0;
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                i = rs.getInt("count");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Funzione per trovare l'id dipendente maggiore
     * @return
     */
    public int Id_dip(){
        String sql = ("select MAX(id_dip) as Max from dipendente ");
        int i = 0;
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())  i = rs.getInt("Max");
            i++;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Funzione per impostare il laboratorio di un dipendente
     * @param nome_lab
     * @param id
     * @return
     */
    @Override
    public int setLaboratorio(String nome_lab , int id) {
        String sql = ("update Dipendente set nome_lab = '" + nome_lab + "' where Dipendente.id_dip = " + id);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Funzione per la promozione di un dipendente
     * @param id_dip
     * @return
     */
    @Override
    public int promuovi(int id_dip) {
        String sql = ("update Dipendente set Dirigente = 'yes' where Dipendente.id_dip = " + id_dip);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Funzione per degradare un dipendente
     * @param id_dip
     * @return
     */
    @Override
    public int degrada(int id_dip) {
        String sql = ("update Dipendente set Dirigente = 'no' where Dipendente.id_dip = " + id_dip);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Funzione per avere tutti i dirigenti
     * @return
     */
    public List<Dipendente> getDir(){
        String sql = ("select * from Dipendente where Dipendente.nome_lab is not null");
        List<Dipendente> Dirigenti = new ArrayList<>();
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;
            while(rs.next()) {
                dip = new Dipendente(rs.getString("Nome"), rs.getString("Cognome"),rs.getInt("id_dip"), rs.getBoolean("dirigente"), rs.getDate("Data_assunzione"), rs.getDate("Data_N"), rs.getString("nome_lab") );
                Dirigenti.add(dip);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return Dirigenti;
    }

    /**
     * Funzione per avere la data in cui è stato assunto un dipendente
     * @param id_dip
     * @return
     */
    @Override
    public Date DataAssunzione(int id_dip) {
        Date data = null;
        String sql = ("select data_assunzione as assunzione from dipendente where dipendente.id_dip = "+ id_dip);
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                data = rs.getDate("assunzione ");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Funzione per avere tutti i dipendenti senior
     * @param Nome_lab
     * @return
     */
    @Override
    public List<Dipendente> getSenior(String Nome_lab) {
        List<Dipendente> Dipendenti = new ArrayList<>();
        String sql = ("Select * from dipendente where (current_date-dipendente.data_assunzione)/365>=7 and dipendente.nome_lab = '" + Nome_lab + "'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Dipendente dip = new Dipendente(rs.getString("Nome"), rs.getString("Cognome"), rs.getInt("id_dip") , rs.getBoolean("Dirigente"),rs.getDate("data_assunzione") , rs.getDate("data_n") );
                Dipendenti.add(dip);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return Dipendenti;
    }

    /**
     * Funzione per contare tutti i dipendenti senior
     * @param Nome_lab
     * @return
     */
    @Override
    public int countSenior(String Nome_lab) {
        int i = 0;
        String sql = ("select count(*) as conto from dipendente where (current_date-dipendente.data_assunzione)/365>=7 and dipendente.nome_lab = '" + Nome_lab + "'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs =stmt.executeQuery(sql);
            while(rs.next()) i = rs.getInt("conto");

        }catch(SQLException e){
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Funzione per avere tutti dirigenti di un laboratorio
     * @param Nome_lab
     * @return
     */
    @Override
    public List<Dipendente> getDirigentiLaboratorio(String Nome_lab) {
        List<Dipendente> DirigentiLabiratorio = new ArrayList<>();
        String sql = ("Select * from dipendente where dipendente.nome_lab = '" + Nome_lab + "' and dirigente = 'yes'");
        try{
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Dipendente dip = new Dipendente(rs.getString("Nome"), rs.getString("Cognome"), rs.getInt("id_dip") , rs.getBoolean("Dirigente"),rs.getDate("data_assunzione") , rs.getDate("data_n") );
                DirigentiLabiratorio.add(dip);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return DirigentiLabiratorio;
    }
}
