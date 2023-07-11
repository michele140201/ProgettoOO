package DAO;
import Model.Laboratorio;
import Model.Progetto;

import java.util.*;

public interface LaboratorioDAO {
    public String[] getNomeLab() throws Exception;
    public int countLab()throws Exception;
    List<Laboratorio> getLabs()throws Exception;
    int Inserisci(String Nome_Lab , String Topic)throws Exception;
    int remove(String Nome)throws Exception;
    int riassegnaDipendente(String Nome_Lab , int id_dip)throws Exception;
    int riassegnaProgetto(String Nome_Lab , int Progetto)throws Exception;
    int countProgetti(int progetto) throws Exception;
    List<Laboratorio> getLaboratori(int cup)throws Exception;
    int getReferenteLab(String nome_lab)throws Exception;
    int getProgetto(String NomeLab)throws Exception;
}
