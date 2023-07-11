package Model;

import java.util.*;
/**
 * Creazione del model Progetto con getter e setter
 */
public class Progetto {
    private int CUP;
    private String NomeProg;
    private String Referente;
    private String Dirigente;
    private String Topic;


    public Progetto(int CUP, String nome_Prog, String referente, String dirigente, String topic) {
        this.CUP = CUP;
        NomeProg = nome_Prog;
        Referente = referente;
        Dirigente = dirigente;
        Topic = topic;
    }

    public Progetto() {

    }

    public int getCUP() {
        return CUP;
    }

    public void setCUP(int CUP) {
        this.CUP = CUP;
    }

    public String getNomeProg() {
        return NomeProg;
    }

    public void setNomeProg(String nome_Prog) {
        NomeProg = nome_Prog;
    }

    public String getReferente() {
        return Referente;
    }

    public void setReferente(String referente) {
        Referente = referente;
    }

    public String getDirigente() {
        return Dirigente;
    }

    public void setDirigente(String dirigente) {
        Dirigente = dirigente;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }
}
