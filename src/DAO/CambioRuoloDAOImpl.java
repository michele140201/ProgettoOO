package DAO;

import Controller.*;

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
     * @param idDip
     * @return
     */
    @Override
    public Date getDataPromozione(int idDip) throws Exception {
        Date data = null;
        String sql = ("Select * from Ruolo where Ruolo.id_dip = " + idDip);
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
     * funzione per inserire la data in cui un dipendente
     * è stato promosso a dirigente
     *
     * @param idDip
     * @return
     */
    @Override
    public void setDataPromozione(int idDip) throws Exception {
        String data = LocalDate.now().toString();
        String sql = ("Insert into Ruolo(id_dip,data_cambio) values ('" + idDip + "','" + data + "')");

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
     * @param idDip
     * @return
     */
    @Override
    public void removePromozione(int idDip) throws Exception {
        String sql = ("Delete from Ruolo where ruolo.id_dip = " + idDip);
        try {
            Connection connection = connectionController.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
