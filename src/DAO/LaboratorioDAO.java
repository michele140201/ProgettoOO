package DAO;
import Model.Laboratorio;

import java.util.*;

public interface LaboratorioDAO {
    //mettere commenti
    List<Laboratorio> getLaboratoriAssegnati()throws Exception;
    void inserisci(Laboratorio laboratorio)throws Exception;
    void rimuovi(String nome)throws Exception;
    void riassegnaDipendente(String nome , int id_dip)throws Exception;
    void riassegnaProgetto(String nome , int Progetto)throws Exception;
    int getNumeroLaboratoriAssegnati(int progetto) throws Exception;
    List<Laboratorio> getLaboratoriAssegnati(int cup)throws Exception;
    int getIdReferente(String nome)throws Exception;
}
