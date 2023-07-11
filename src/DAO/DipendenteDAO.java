package DAO;
import Model.Dipendente;

import java.util.Date;
import java.util.List;

public interface DipendenteDAO {
    List<Dipendente> getDipendente() throws Exception;
    int removeDipendente(int id_dip);
    int insertDipendente(Dipendente nuovodip);
    int count();
    int Id_dip();
    int setLaboratorio(String nome_lab , int id);
    int promuovi(int id_dip);
    int degrada(int id_dip);
    List<Dipendente> getDir();
    Date DataAssunzione(int id_dip);
    List<Dipendente> getSenior(String Nome_lab);
    int countSenior(String Nome_lab);
    List<Dipendente> getDirigentiLaboratorio(String Nome_lab);
}
