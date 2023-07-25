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
     * funzione che ottiene tutti i dipendenti dal database
     * @return
     * @throws Exception
     */


    /**
     * rimuove un dipendente dal database
     *
     * @param dipendente
     * @throws Exception
     */

    @Override
    public void removeDipendente(Dipendente dipendente) throws Exception {
        String sql = "delete from Dipendente where Dipendente.id_dip = " + dipendente.getId();
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * funzione che inserisce un dipendente nel database
     *
     * @param dipendente
     * @throws Exception
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
     * funzione che genera un nuovo id per un dipendente
     *
     * @return
     * @throws Exception
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
     * funzione che setta un laboratorio dal database
     *
     * @param laboratorio
     * @param dipendente
     * @throws Exception
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

    /**
     * funzione che promuove un dipendente nel database
     *
     * @param dipendente
     * @throws Exception
     */

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
     * funzione che degrada un dipendente nel database
     *
     * @param dipendente
     * @throws Exception
     */

    @Override
    public void degrada(Dipendente dipendente) throws Exception {
        String sql = ("update Dipendente set Dirigente = 'no' where Dipendente.id_dip = " + dipendente.getId());
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * funzione che ottiene tutti i dipendenti nel database
     *
     * @return
     * @throws Exception
     */

    public List<Dipendente> getDipendenti() throws Exception {
        String sql = ("select * from dipendente");
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<Dipendente> dipendenti = new ArrayList<>();
            while (resultSet.next()) {
                Laboratorio laboratorio = new Laboratorio(resultSet.getString("nome_lab"));
                Dipendente dipendente = new Dipendente(resultSet.getString("Nome"), resultSet.getString("Cognome"), resultSet.getInt("id_dip"), resultSet.getBoolean("Dirigente"), resultSet.getDate("data_assunzione"), resultSet.getDate("data_n"), laboratorio);
                dipendenti.add(dipendente);
            }
            return dipendenti;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
