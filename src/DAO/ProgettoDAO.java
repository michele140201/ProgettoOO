package DAO;

import Model.Progetto;

import java.util.List;

public interface ProgettoDAO {
    List<Progetto> getProgetti() throws Exception;
    void inserisci(Progetto p)throws Exception;
    void rimuovi(int cup)throws Exception;
    int getNumeroTotale()throws Exception;
    void setReferente(int id_dip , int cup)throws Exception;
    void setResponsabile(int id_dip , int cup)throws Exception;
}
