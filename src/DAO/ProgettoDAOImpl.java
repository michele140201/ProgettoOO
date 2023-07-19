package DAO;

import Model.Dipendente;
import Model.Progetto;

import java.util.ArrayList;
import java.util.List;

import Controller.*;

import java.sql.*;

public class ProgettoDAOImpl implements ProgettoDAO {
    private final ConnectionController connectionController;

    public ProgettoDAOImpl(ConnectionController connectionController) {
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
                Dipendente dipendente = new Dipendente(resultSet.getInt("referente"));
                Dipendente responsabile = new Dipendente(resultSet.getInt("responsabile"));
                Progetto progetto = new Progetto(resultSet.getString("nome_p"), resultSet.getInt("CUP") , dipendente , responsabile);
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
     * @param progetto
     * @return
     */
    @Override
    public void inserisci(Progetto progetto) throws Exception {
        progetto.setCup(generaCup());
        String sql = ("Insert into Progetto(CUP,Nome_p) values ('" + progetto.getCup() + "','" + progetto.getNome() + "')");
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
     * @param id
     * @param cup
     * @return
     */
    @Override
    public void setReferente(int id, int cup) throws Exception {

        String sql = ("Update progetto set referente = " + id + "where cup = " + cup);
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
     * @param id
     * @param cup
     * @return
     */
    @Override
    public void setResponsabile(int id, int cup) throws Exception {

        String sql = ("Update progetto set responsabile = " + id + "where cup = " + cup);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    @Override
    public int getReferente(Progetto progetto, String ruoloDipendente) throws Exception {
        String sql = "Select " + ruoloDipendente + " from progetto where progetto.cup = " + progetto.getCup();
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt(ruoloDipendente);
            }
            return id;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }
}
