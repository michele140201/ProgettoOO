package DAO;

import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.util.List;

public interface DipendenteDAO {

    List<Dipendente> getDipendenti() throws Exception;

    void removeDipendente(Dipendente dipendente) throws Exception;

    void insertDipendente(Dipendente dipendente) throws Exception;

    void setLaboratorio(Laboratorio laboratorio, Dipendente dipendente) throws Exception;

    void promuovi(Dipendente dipendente) throws Exception;

    void degrada(Dipendente dipendente) throws Exception;
}
