package DAO;
import Model.Dipendente;

import java.util.Date;
import java.util.List;

public interface DipendenteDAO {
    List<Dipendente> getDipendente() throws Exception;
    int removeDipendente(int id_dip) throws Exception ;
    int insertDipendente(Dipendente nuovodip)throws Exception;
    int count()throws Exception;
    int Id_dip()throws Exception;
    int setLaboratorio(String nome_lab , int id)throws Exception;
    int promuovi(int id_dip)throws Exception;
    int degrada(int id_dip)throws Exception;
    List<Dipendente> getDir()throws Exception;
    Date DataAssunzione(int id_dip)throws Exception;
    List<Dipendente> getSenior(String Nome_lab)throws Exception;
    int countSenior(String Nome_lab)throws Exception;
    List<Dipendente> getDirigentiLaboratorio(String Nome_lab)throws Exception;
}
