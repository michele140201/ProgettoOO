package Model;

import java.util.ArrayList;
/**
 * Creazione del model Laboratorio con getter e setter
 */
public class Laboratorio {
    private String Nome_Lab;
    private String topic;
    private int Progetto;
    private int Responsabile;

    public Laboratorio(String nome_Lab, String topic, int progetto, int responsabile) {
        Nome_Lab = nome_Lab;
        this.topic = topic;
        Progetto = progetto;
        Responsabile = responsabile;
    }

    public Laboratorio() {

    }

    public Laboratorio(String nome_Lab, String topic) {
        Nome_Lab = nome_Lab;
        this.topic = topic;
    }

    public String getNome_Lab() {
        return Nome_Lab;
    }

    public void setNome_Lab(String nome_Lab) {
        Nome_Lab = nome_Lab;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getProgetto() {
        return Progetto;
    }

    public void setProgetto(int progetto) {
        Progetto = progetto;
    }

    public int getResponsabile() {
        return Responsabile;
    }

    public void setResponsabile(int responsabile) {
        Responsabile = responsabile;
    }

    public void CambiaRes(int NuovoRes){Responsabile = NuovoRes;}


}
