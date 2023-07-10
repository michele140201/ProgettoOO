package DAO;
import Model.Laboratorio;
import Model.Progetto;

import java.util.*;

public interface LaboratorioDAO {
    public String[] NomeLab();
    public int countLab();
    List<Laboratorio> ottieniLabs();
    int Inserisci(String Nome_Lab , String Topic);
    int remove(String Nome);
    int riassegna(String Nome_Lab , int id_dip);
    int riassegna_progetto(String Nome_Lab , int Progetto);
    int conta_prog(int progetto);
    List<Laboratorio> lab_prog(int cup);
    int getReferente_Lab(String nome_lab);
    int getProgetto(String NomeLab);
}
