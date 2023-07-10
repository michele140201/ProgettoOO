package DAO;
import Model.Dipendente;

import java.util.Date;
import java.util.List;

public interface DipendenteDAO {
    List<Dipendente> getDipendente();
    int remove_dip(int id_dip);
    int add_dip(Dipendente nuovodip);
    int count();
    int Id_dip();
    int set_lab(String nome_lab , int id);
    int promuovi(int id_dip);
    int degrada(int id_dip);
    List<Dipendente> getDir();
    Date DataAssunzione(int id_dip);
    List<Dipendente> dipendente_senior(String Nome_lab);
    int conta_senior(String Nome_lab);
    List<Dipendente> Dirigenti_Laboratorio(String Nome_lab);
}
