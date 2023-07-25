package DAO;

import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.util.*;

public interface LaboratorioDAO {

    List<Laboratorio> getLaboratori() throws Exception;

    void inserisci(Laboratorio laboratorio) throws Exception;

    void rimuovi(Laboratorio laboratorio) throws Exception;

    void assegnaDipendente(Laboratorio laboratorio, Dipendente dipendente) throws Exception;

    void assegnaProgetto(Laboratorio laboratorio, Progetto progetto) throws Exception;

    int getIdReferente(Laboratorio laboratorio) throws Exception;
}
