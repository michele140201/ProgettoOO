package DAO;
import Model.Dipendente;

import java.util.List;

public interface DipendenteDAO {
    List<Dipendente> getDipendenti() throws Exception;
    void removeDipendente(int id) throws Exception ;
    void insertDipendente(Dipendente dipendente)throws Exception;
    void setLaboratorio(String nome_lab , int id)throws Exception;
    void promuovi(int id_dip)throws Exception;
    void degrada(int id_dip)throws Exception;
    List<Dipendente> getDirigenti()throws Exception;
    List<Dipendente> getSenior(String nomeLaboratorio)throws Exception;
    List<Dipendente> getDirigenti(String nomeLaboratorio)throws Exception;
}
