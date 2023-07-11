package DAO;

import Model.Progetto;

import java.util.List;

public interface ProgettoDAO {
    List<Progetto> ottieniprogetti() throws Exception;
    int InserisciProgetto(Progetto p)throws Exception;
    int GeneraCup()throws Exception;
    int EliminaProgetto(int cup)throws Exception;
    int countProgetti()throws Exception;
    int setReferente(int id_dip , int cup)throws Exception;
    int setResponsabile(int id_dip , int cup)throws Exception;
    int getReferente(int id_dip)throws Exception;
    int getResponsabile(int id_dip)throws Exception;


}
