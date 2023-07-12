package DAO;

import Model.Progetto;

import java.util.ArrayList;
import java.util.List;

import Controller.*;

import java.sql.*;

public class ProgettoDAOImpl implements ProgettoDAO {
    private ConnectionController connectionController;
    public ProgettoDAOImpl(ConnectionController connectionController){
        this.connectionController = connectionController;
    }

    /**
     * Funzione per ottenere tutti i progetti
     *
     * @return
     */
    @Override
    public List<Progetto> getProgetti() throws Exception {
        List<Progetto> progetti = new ArrayList<>();
        String sql = ("Select * from Progetto");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Progetto progetto = new Progetto(resultSet.getInt("CUP"), resultSet.getString("nome_p"), resultSet.getString("referente"), resultSet.getString("responsabile"));
                progetti.add(progetto);
            }
            return progetti;
        } catch (SQLException e) {
            throw new Exception(e);
        }


    }

    /**
     * Funzione per trovare il cup piu grande di un progetto
     *
     * @return
     */
    private int generaCup() throws Exception {
        int cup = 1;
        String sql = ("SELECT max(cup) as massimo from progetto");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                cup = resultSet.getInt("massimo") + 1;
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
    public void inserisci(Progetto p) throws Exception {
        p.setCup(generaCup());
        String sql = ("Insert into Progetto(CUP,Nome_p) values ('" + p.getCup() + "','" + p.getNome() +  "')");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
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
    public void rimuovi(int cup) throws Exception {
        String sql = ("Delete from progetto where progetto.cup = " + cup);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
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
    public int getNumeroTotale() throws Exception {
        int i = 0;
        String sql = ("Select COUNT(*) as conto from progetto");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                i = resultSet.getInt("conto");
            }
            return i;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    /**
     * Funzione per impostare il nuovo referente di un progetto
     *
     * @param idDip
     * @param cup
     * @return
     */
    @Override
    public void setReferente(int idDip, int cup) throws Exception {

        String sql = ("Update progetto set referente = " + idDip + "where cup = " + cup);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
           statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per settare un nuovo dirigente di un progetto
     *
     * @param idDip
     * @param cup
     * @return
     */
    @Override
    public void setResponsabile(int idDip, int cup) throws Exception {

        String sql = ("Update progetto set responsabile = " + idDip + "where cup = " + cup);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
}
