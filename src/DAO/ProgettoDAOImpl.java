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
                Progetto progetto = new Progetto(resultSet.getString("nome_p"), resultSet.getInt("CUP"), dipendente, responsabile);
                progetti.add(progetto);
            }
            return progetti;
        } catch (SQLException e) {
            throw new Exception(e);
        }


    }

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

    @Override
    public void rimuovi(Progetto progetto) throws Exception {
        String sql = ("Delete from progetto where progetto.cup = " + progetto.getCup());
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    @Override
    public void setReferente(Dipendente dipendente, Progetto progetto) throws Exception {
        int id;
        String sql;
        if (dipendente != null) {
            sql = ("Update progetto set referente = " + dipendente.getId() + " where cup = " + progetto.getCup());
        } else {
            sql = ("Update progetto set referente = " + null + " where cup = " + progetto.getCup());
        }

        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    @Override
    public void setResponsabile(Dipendente dipendente, Progetto progetto) throws Exception {

        String sql = ("Update progetto set responsabile = " + dipendente.getId() + "where cup = " + progetto.getCup());
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
}
