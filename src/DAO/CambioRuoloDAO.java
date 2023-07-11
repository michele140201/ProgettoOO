package DAO;
import java.sql.*;
public interface CambioRuoloDAO {
    Date getDataCambio(int id_dip);
    int setDataPromozione(int id_dip);
    int removePromozione(int id_dip);
}
