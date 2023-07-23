package DAO;

import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.util.*;

public interface LaboratorioDAO {

    List<Laboratorio> getLaboratoriAssegnati() throws Exception;

    void inserisci(Laboratorio laboratorio) throws Exception;

    void rimuovi(Laboratorio laboratorio) throws Exception;

    void riassegnaDipendente(Laboratorio laboratorio, Dipendente dipendente) throws Exception;

    void riassegnaProgetto(Laboratorio laboratorio, Progetto progetto) throws Exception;

    int getNumeroLaboratoriAssegnati(Progetto progetto) throws Exception;

    int getIdReferente(Laboratorio laboratorio) throws Exception;
}
