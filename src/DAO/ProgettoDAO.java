package DAO;

import Model.Progetto;

import java.util.List;

public interface ProgettoDAO {
    List<Progetto> ottieniprogetti() throws Exception;
    int InserisciProgetto(Progetto p);
    int GeneraCup();
    int EliminaProgetto(int cup);
    int conta_progetti();
    int setReferente(int id_dip , int cup);
    int setResponsabile(int id_dip , int cup);
    int getReferente(int id_dip);
    int getResponsabile(int id_dip);


}
