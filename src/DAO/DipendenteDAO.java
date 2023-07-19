package DAO;

import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.util.List;

public interface DipendenteDAO {
    Dipendente getDipendente(int id) throws Exception;

    List<Dipendente> getDipendenti() throws Exception;

    void removeDipendente(int id) throws Exception;

    void insertDipendente(Dipendente dipendente) throws Exception;

    void setLaboratorio(Laboratorio laboratorio, Dipendente dipendente) throws Exception;

    void promuovi(Dipendente dipendente) throws Exception;

    void degrada(int id_dip) throws Exception;

    List<Dipendente> getDirigenti() throws Exception;

    List<Dipendente> getSenior(String nomeLaboratorio) throws Exception;

    List<Dipendente> getDirigenti(String nomeLaboratorio) throws Exception;
}
