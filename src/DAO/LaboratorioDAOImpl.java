package DAO;

import Controller.*;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia DAO per database PostgreSQL
 */

public class LaboratorioDAOImpl implements LaboratorioDAO {
    private final ConnectionController connectionController;

    public LaboratorioDAOImpl(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    @Override
    public List<Laboratorio> getLaboratori() throws Exception {
        List<Laboratorio> laboratori = new ArrayList<>();
        String sql = ("select * from laboratorio ");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Progetto progetto = new Progetto(resultSet.getInt("progetto"));
                Dipendente dipendente = new Dipendente(resultSet.getInt("referente"));
                Laboratorio laboratorio = new Laboratorio(resultSet.getString("nome_lab"), Laboratorio.Topic.valueOf(resultSet.getString("topic")), progetto, dipendente);
                laboratori.add(laboratorio);
            }
            return laboratori;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    @Override
    public void inserisci(Laboratorio laboratorio) throws Exception {
        String sql = "Insert into laboratorio(nome_lab , topic) values('" + laboratorio.getNome() + "','" + laboratorio.getTopic() + "')";
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void rimuovi(Laboratorio laboratorio) throws Exception {
        String sql = ("delete from laboratorio where laboratorio.nome_lab = '" + laboratorio.getNome() + "'");
        try {
            Connection connection = connectionController.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    @Override
    public void assegnaReferente(Laboratorio laboratorio, Dipendente dipendente) throws Exception {
        String sql = ("update Laboratorio set referente = " + dipendente.getId() + "where laboratorio.nome_lab = '" + laboratorio.getNome() + "'");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    @Override
    public void assegnaProgetto(Laboratorio laboratorio, Progetto progetto) throws Exception {

        String sql = ("update Laboratorio set progetto = " + progetto.getCup() + " where nome_lab = '" + laboratorio.getNome() + "'");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

}
