package DAO;

import Controller.*;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDAOImpl implements LaboratorioDAO {
    private final ConnectionController connectionController;

    public LaboratorioDAOImpl(ConnectionController connectionController) {
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
        String sql = ("Select * from laboratorio , progetto where laboratorio.progetto = " + cup + "and laboratorio.progetto = progetto.cup");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Progetto progetto = new Progetto(resultSet.getString("nome_p"), resultSet.getInt("cup"));
                Dipendente dipendente = new Dipendente(resultSet.getInt("referente"));
                Laboratorio lab = new Laboratorio(resultSet.getString("nome_lab"), Laboratorio.Topic.valueOf(resultSet.getString("topic")), progetto , dipendente);
                laboratori.add(lab);
            }
            return laboratori;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    @Override
    public int getIdReferente(Laboratorio laboratorio) throws Exception {
        int idDip = 0;
        String sql = ("Select referente from laboratorio where laboratorio.nome_lab = '" + laboratorio.getNome() + "'");
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

    @Override
    public Laboratorio getLaboratorioDipendente(Dipendente dipendente) throws Exception {
        String sql = "Select * from laboratorio,progetto,dipendente where dipendente.nome_lab = laboratorio.nome_lab and dipendente.id_dip =  " + dipendente.getId() + " and laboratorio.progetto = progetto.cup";
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Laboratorio laboratorio = null;
            while (resultSet.next()) {
                Progetto progetto = new Progetto(resultSet.getString("nome_p"), resultSet.getInt("cup"));
                laboratorio = new Laboratorio(resultSet.getString("nome_lab"), Laboratorio.Topic.valueOf(resultSet.getString("topic")), progetto, dipendente);
            }
            return laboratorio;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
}
