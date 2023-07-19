package DAO;

import Controller.*;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DipendenteDAOimpl implements DipendenteDAO {
    private final ConnectionController connectionController;

    public DipendenteDAOimpl(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    /**
     * Funzione per avere tutti i dipendenti
     *
     * @return
     */
    @Override
    public List<Dipendente> getDipendenti() throws Exception {
        String sql = ("select * from dipendente");
        return getDipendenti(sql);
    }

    /**
     * Funzione per rimuovere un dipendente
     *
     * @param id
     * @return
     */
    @Override
    public void removeDipendente(int id) throws Exception {
        String sql = "delete from Dipendente where Dipendente.id_dip = " + id;
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per aggiungere un dipendente
     *
     * @param dipendente
     * @return
     */
    @Override
    public void insertDipendente(Dipendente dipendente) throws Exception {
        dipendente.setId(generaId());
        String sql = "insert into Dipendente(nome,cognome,data_n, id_dip,dirigente, data_assunzione)  values('" + dipendente.getNome() + "','" + dipendente.getCognome() + "','" + dipendente.getDataNascita() + "','" + dipendente.getId() + "','" + dipendente.isDirigente() + "','" + dipendente.getDataAssunzione() + "')";
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per trovare l'id dipendente maggiore
     *
     * @return
     */
    private int generaId() throws Exception {
        String sql = ("select MAX(id_dip) as Max from dipendente ");
        int id = 0;
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
                id = resultSet.getInt("Max") + 1;
            return id;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per impostare il laboratorio di un dipendente
     *
     * @param laboratorio
     * @param dipendente
     * @return
     */
    @Override
    public void setLaboratorio(Laboratorio laboratorio, Dipendente dipendente) throws Exception {
        String sql = ("update Dipendente set nome_lab = '" + laboratorio.getNome() + "' where Dipendente.id_dip = " + dipendente.getId());
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }


    @Override
    public void promuovi(Dipendente dipendente) throws Exception {
        String sql = ("update Dipendente set Dirigente = 'yes' where Dipendente.id_dip = " + dipendente.getId());
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    /**
     * Funzione per degradare un dipendente
     *
     * @param id
     * @return
     */
    @Override
    public void degrada(int id) throws Exception {
        String sql = ("update Dipendente set Dirigente = 'no' where Dipendente.id_dip = " + id);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per avere tutti i dirigenti
     *
     * @return
     */
    public List<Dipendente> getDirigenti() throws Exception {
        String sql = ("select * from Dipendente where Dipendente.nome_lab is not null");
        List<Dipendente> dirigenti = new ArrayList<>();
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Dipendente dipendente = new Dipendente(resultSet.getString("Nome"), resultSet.getString("Cognome"), resultSet.getInt("id_dip"), resultSet.getBoolean("dirigente"), resultSet.getDate("Data_assunzione"), resultSet.getDate("Data_N"));
                dirigenti.add(dipendente);
            }
            return dirigenti;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per avere tutti i dipendenti senior
     *
     * @param nomeLaboratorio
     * @return
     */
    @Override
    public List<Dipendente> getSenior(String nomeLaboratorio) throws Exception {
        String sql = ("Select * from dipendente where (current_date-dipendente.data_assunzione)/365>=7 and dipendente.nome_lab = '" + nomeLaboratorio + "'");
        return getDipendenti(sql);

    }

    /**
     * Funzione per avere tutti dirigenti di un laboratorio
     *
     * @param nomeLaboratorio
     * @return
     */
    @Override
    public List<Dipendente> getDirigenti(String nomeLaboratorio) throws Exception {
        String sql = ("Select * from dipendente where dipendente.nome_lab = '" + nomeLaboratorio + "' and dirigente = 'yes'");
        return getDipendenti(sql);

    }

    private List<Dipendente> getDipendenti(String sql) throws Exception {
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Dipendente> dipendenti = new ArrayList<>();
            while (resultSet.next()) {
                Laboratorio laboratorio = new Laboratorio(resultSet.getString("nome_lab"));
                Dipendente dipendente = new Dipendente(resultSet.getString("Nome"), resultSet.getString("Cognome"), resultSet.getInt("id_dip"), resultSet.getBoolean("Dirigente"), resultSet.getDate("data_assunzione"), resultSet.getDate("data_n") , laboratorio);
                dipendenti.add(dipendente);
            }
            return dipendenti;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    @Override
    public Dipendente getDipendente(int id) throws Exception {
        String sql = "select * from dipendente where dipendente.id_dip = " + id;
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Dipendente dipendente = null;
            while (resultSet.next()) {
                dipendente = new Dipendente(resultSet.getString("Nome"), resultSet.getString("Cognome"), resultSet.getInt("id_dip"), resultSet.getBoolean("Dirigente"), resultSet.getDate("data_assunzione"), resultSet.getDate("data_n"));
            }
            return dipendente;
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }
}
