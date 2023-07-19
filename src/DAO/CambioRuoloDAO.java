package DAO;

import Model.Dipendente;

import java.sql.*;

public interface CambioRuoloDAO {
    Date getDataPromozione(int id_dip) throws Exception;

    void setDataPromozione(Dipendente dipendente) throws Exception;

    void removePromozione(int id_dip) throws Exception;
}
