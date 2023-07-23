package DAO;

import Model.Dipendente;
import Model.Progetto;

import java.util.List;

public interface ProgettoDAO {
    List<Progetto> getProgetti() throws Exception;

    void inserisci(Progetto progetto) throws Exception;

    void rimuovi(int cup) throws Exception;

    void setReferente(Dipendente dipendente, int cup) throws Exception;

    void setResponsabile(Dipendente dipendente, int cup) throws Exception;

}
