package DAO;

import Model.Progetto;

import java.util.ArrayList;
import java.util.List;

import Controller.*;

import java.sql.*;

public class ProgettoDAOImpl implements ProgettoDAO {
    ConnectionController controller = new ConnectionController();
    Connection con;

    /**
     * Funzione per ottenere tutti i progetti
     *
     * @return
     */
    @Override
    public List<Progetto> ottieniprogetti() throws Exception {
        List<Progetto> Lista;
        Lista = new ArrayList<>();
        Progetto p;
        String sql = ("Select * from Progetto");
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                p = new Progetto(rs.getInt("CUP"), rs.getString("nome_p"), rs.getString("referente"), rs.getString("responsabile"), rs.getString("topic"));
                Lista.add(p);
            }
            return Lista;
        } catch (SQLException e) {
            throw new Exception(e);
        }


    }

    /**
     * Funzione per trovare il cup piu grande di un progetto
     *
     * @return
     */
    @Override
    public int GeneraCup() throws Exception {
        int cup = 1;
        String sql = ("SELECT max(cup) as massimo from progetto");
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cup = rs.getInt("massimo") + 1;
            }
            return cup;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per inserire un proggetto
     *
     * @param p
     * @return
     */
    @Override
    public int InserisciProgetto(Progetto p) throws Exception {
        String sql = ("Insert into Progetto(CUP,Nome_p,topic) values ('" + p.getCUP() + "','" + p.getNome_Prog() + "','" + p.getTopic() + "')");
        int i = 0;
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            i = stmt.executeUpdate(sql);
            return i;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per eliminare un progetto
     *
     * @param cup
     * @return
     */
    @Override
    public int EliminaProgetto(int cup) throws Exception {
        String sql = ("Delete from progetto where progetto.cup = " + cup);
        int i = 0;
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            i = stmt.executeUpdate(sql);
            return i;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per contare tutti i progetti
     *
     * @return
     */
    @Override
    public int conta_progetti() throws Exception {
        int i = 0;
        String sql = ("Select COUNT(*) as conto from progetto");
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                i = rs.getInt("conto");
            }
            return i;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    /**
     * Funzione per impostare il nuovo referente di un progetto
     *
     * @param id_dip
     * @param cup
     * @return
     */
    @Override
    public int setReferente(int id_dip, int cup) throws Exception {
        int c = 0;
        String sql = ("Update progetto set referente = " + id_dip + "where cup = " + cup);
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            c = stmt.executeUpdate(sql);
            return c;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per settare un nuovo dirigente di un progetto
     *
     * @param id_dip
     * @param cup
     * @return
     */
    @Override
    public int setResponsabile(int id_dip, int cup) throws Exception {
        int c = 0;
        String sql = ("Update progetto set responsabile = " + id_dip + "where cup = " + cup);
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            c = stmt.executeUpdate(sql);
            return c;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    /**
     * Funzione per ottenere il referente di un progetto
     *
     * @param cup
     * @return
     */
    @Override
    public int getReferente(int cup) throws Exception {
        int id_dip = 0;
        String sql = ("Select referente from progetto where progetto.cup = " + cup);
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id_dip = rs.getInt("referente");
            }
            return id_dip;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per ottenere il responsabile di un progetto
     *
     * @param cup
     * @return
     */
    @Override
    public int getResponsabile(int cup) throws Exception {
        int id_dip = 0;
        String sql = ("Select responsabile from progetto where progetto.cup = " + cup);
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id_dip = rs.getInt("responsabile");
            }
            return id_dip;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }
}
