package DAO;

import Controller.*;

import java.sql.*;
import java.time.LocalDate;


public class CambioRuoloDAOImpl implements CambioRuoloDAO {
    ConnectionController controller = new ConnectionController();
    Connection con;

    /**
     * Funzione per vedere quando un dipendente è
     * stato promosso a dirigente
     *
     * @param idDip
     * @return
     */
    @Override
    public Date getDataCambio(int idDip) throws Exception {
        Date data = null;
        String sql = ("Select * from Ruolo where Ruolo.id_dip = " + idDip);
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                data = rs.getDate("data_cambio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * funzione per inserire la data in cui un dipendente
     * è stato promosso a dirigente
     *
     * @param idDip
     * @return
     */
    @Override
    public int setDataPromozione(int idDip) throws Exception {
        String data = LocalDate.now().toString();
        Date ora = Date.valueOf(data);
        String sql = ("Insert into Ruolo(id_dip,data_cambio) values ('" + idDip + "','" + data + "')");

        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            return 0;
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
    public int removePromozione(int idDip) throws Exception {
        String sql = ("Delete from Ruolo where ruolo.id_dip = " + idDip);
        try {
            con = controller.ConnectionController();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            return 0;
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

}
