package DAO;

import Model.Progetto;

import java.util.List;

public interface ProgettoDAO {
    List<Progetto> ottieniprogetti();
    int InserisciProgetto(Progetto p);
    int GeneraCup();
    Progetto NuovoInserito();
    int EliminaProgetto(int cup);
    int conta_progetti();
    int set_referente(int id_dip , int cup);
    int set_dirigente(int id_dip , int cup);
    int get_referente(int id_dip);
    int get_responsabile(int id_dip);


}
