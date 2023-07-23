package DAO;

import Model.Dipendente;

import java.sql.*;

public interface CambioRuoloDAO {
    Date getDataPromozione(Dipendente dipendente) throws Exception;

    void setDataPromozione(Dipendente dipendente) throws Exception;

    void removePromozione(Dipendente dipendente) throws Exception;
}
