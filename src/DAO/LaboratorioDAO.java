package DAO;
import Model.Laboratorio;
import Model.Progetto;

import java.util.*;

public interface LaboratorioDAO {
    public String[] getNomeLab() throws Exception;
    public int countLab();
    List<Laboratorio> getLabs()throws Exception;
    int Inserisci(String Nome_Lab , String Topic);
    int remove(String Nome);
    int riassegnaDipendente(String Nome_Lab , int id_dip);
    int riassegnaProgetto(String Nome_Lab , int Progetto);
    int countProgetti(int progetto);
    List<Laboratorio> getLaboratori(int cup);
    int getReferenteLab(String nome_lab);
    int getProgetto(String NomeLab);
}
