package Model;

import java.util.*;
public class Progetto {
    private int CUP;
    private String Nome_Prog;
    private String Referente;
    private String Dirigente;
    private String Topic;


    public Progetto(int CUP, String nome_Prog, String referente, String dirigente, String topic) {
        this.CUP = CUP;
        Nome_Prog = nome_Prog;
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

    public String getNome_Prog() {
        return Nome_Prog;
    }

    public void setNome_Prog(String nome_Prog) {
        Nome_Prog = nome_Prog;
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
