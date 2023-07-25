package DAO;

import Controller.*;
import Model.Dipendente;

import java.sql.*;
import java.time.LocalDate;


public class CambioRuoloDAOImpl implements CambioRuoloDAO {
    private final ConnectionController connectionController;

    public CambioRuoloDAOImpl(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    /**
     * Funzione per vedere quando un dipendente è
     * stato promosso a dirigente
     *
     * @param dipendente
     * @return
     */
    @Override
    public Date getDataPromozione(Dipendente dipendente) throws Exception {
        Date data = null;
        String sql = ("Select * from Ruolo where Ruolo.id_dip = " + dipendente.getId());
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                data = resultSet.getDate("data_cambio");
            }
            return data;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    /**
     * funzione per memorizzare nel database quando un dipendente diventa dirigente
     *
     * @param dipendente
     * @throws Exception
     */

    @Override
    public void setDataPromozione(Dipendente dipendente) throws Exception {
        String data = LocalDate.now().toString();
        String sql = ("Insert into Ruolo(id_dip,data_cambio) values ('" + dipendente.getId() + "','" + data + "')");

        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }

    }

    /**
     * Funzione per rimuovere la promozione di un dipendente, quando viene degradato
     *
     * @param dipendente
     * @return
     */
    @Override
    public void removePromozione(Dipendente dipendente) throws Exception {
        String sql = ("Delete from Ruolo where ruolo.id_dip = " + dipendente.getId());
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
