package DAO;
import java.sql.*;
public interface CambioRuoloDAO {
    Date getDataPromozione(int id_dip) throws Exception;
    void setDataPromozione(int id_dip) throws Exception;
    void removePromozione(int id_dip) throws Exception;
}
