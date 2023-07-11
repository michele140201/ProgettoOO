package DAO;
import java.sql.*;
public interface CambioRuoloDAO {
    Date getDataCambio(int id_dip) throws Exception;
    int setDataPromozione(int id_dip) throws Exception;
    int removePromozione(int id_dip) throws Exception;
}
