package DAO;
import java.sql.*;
public interface CambioRuoloDAO {
    Date data_cambio(int id_dip);
    int inserisci_data_promozione(int id_dip);
    int delete_promozione(int id_dip);
}
