package DAO;

import Model.Dipendente;
import Model.Progetto;

import java.util.List;

public interface ProgettoDAO {
    List<Progetto> getProgetti() throws Exception;

    void inserisci(Progetto progetto) throws Exception;

    void rimuovi(Progetto progetto) throws Exception;

    void setReferente(Dipendente dipendente, Progetto progetto) throws Exception;

    void setResponsabile(Dipendente dipendente, Progetto progetto) throws Exception;

}
