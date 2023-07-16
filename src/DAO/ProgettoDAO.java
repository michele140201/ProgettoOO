package DAO;

import Model.Progetto;

import java.util.List;

public interface ProgettoDAO {
    List<Progetto> getProgetti() throws Exception;

    void inserisci(Progetto progetto) throws Exception;

    void rimuovi(int cup) throws Exception;

    int getNumeroTotale() throws Exception;

    void setReferente(int id, int cup) throws Exception;

    void setResponsabile(int id, int cup) throws Exception;

    int getReferente(Progetto progetto, String ruoloDipendente) throws Exception;
}
