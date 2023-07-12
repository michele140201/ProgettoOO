package DAO;

import Controller.*;
import Model.Laboratorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDAOImpl implements LaboratorioDAO {
    private ConnectionController connectionController;
    public LaboratorioDAOImpl(ConnectionController connectionController){
        this.connectionController = connectionController;
    }
    /**
     * Funzione per ottenere tutti i laboratori
     *
     * @return
     */
    @Override
    public List<Laboratorio> getLaboratoriAssegnati() throws Exception {
        List<Laboratorio> laboratori = new ArrayList<>();
        String sql = ("Select * from Laboratorio");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int progetto = resultSet.getString("progetto") == null ? 0 : resultSet.getInt("progetto");
                Laboratorio laboratorio = new Laboratorio(resultSet.getString("nome_lab"), Laboratorio.Topic.valueOf(resultSet.getString("topic")), progetto, resultSet.getInt("referente"));
                laboratori.add(laboratorio);
            }
            return laboratori;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Inserimento di un nuovo laboratorio
     *
     * @param nome
     * @param Topic
     * @return
     */
    @Override
    public void inserisci(Laboratorio laboratorio) throws Exception{
        String sql = "Insert into laboratorio(nome_lab , topic) values('" + laboratorio.getNome() + "','" + laboratorio.getTopic() + "')";
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    /**
     * Rimozione di un laboratorio
     *
     * @param nome
     * @return
     */
    @Override
    public void rimuovi(String nome) throws Exception {
        String sql = ("delete from laboratorio where laboratorio.nome_lab = '" + nome + "'");
        try {
            Connection connection = connectionController.getConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Assegnazione di un nuovo Referente
     *
     * @param nome
     * @param idDip
     * @return
     */
    @Override
    public void riassegnaDipendente(String nome, int idDip) throws Exception {
        String sql = ("update Laboratorio set referente = " + idDip + "where laboratorio.nome_lab = '" + nome + "'");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Riassegnazione di un nuovo progetto
     *
     * @param nome
     * @param cup
     * @return
     */
    @Override
    public void riassegnaProgetto(String nome, int cup) throws Exception {

        String sql = ("update Laboratorio set progetto = " + cup + " where nome_lab = '" + nome + "'");
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
     * @param progetto
     * @return
     */
    @Override
    public int getNumeroLaboratoriAssegnati(int progetto) throws Exception {
        int c = 0;
        String sql = ("Select count(*) as conto from laboratorio where laboratorio.progetto = " + progetto);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                c = resultSet.getInt("conto");
            }
            return c;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per ottenere tutti i laboratori che stanno lavorando ad un progetto
     *
     * @param cup
     * @return
     */
    @Override
    public List<Laboratorio> getLaboratoriAssegnati(int cup) throws Exception {
        List<Laboratorio> laboratori = new ArrayList<>();
        String sql = ("Select * from laboratorio where laboratorio.progetto = " + cup);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Laboratorio lab = new Laboratorio(resultSet.getString("nome_lab"), Laboratorio.Topic.valueOf(resultSet.getString("topic")), resultSet.getInt("progetto"), resultSet.getInt("Referente"));
                laboratori.add(lab);
            }
            return laboratori;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per ottenere il referente del laboratorio
     *
     * @param nome
     * @return
     */
    @Override
    public int getIdReferente(String nome) throws Exception {
        int idDip = 0;
        String sql = ("Select referente from laboratorio where laboratorio.nome_lab = '" + nome + "'");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                idDip = resultSet.getInt("referente");
            }
            return idDip;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }
}
