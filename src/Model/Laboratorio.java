package Model;

/**
 * Classe che rappresenta un laboratorio dell'azienda
 */

public class Laboratorio {
    private String nome;
    private Topic topic;
    private Progetto progetto;
    private Dipendente referente;

    /**
     * Crea un laboratorio con i dati che gli vengono forniti
     * @param nome nome del laboratorio
     * @param topic topic del laboratorio
     * @param progetto progetto assegnato al laboratorio
     * @param referente referente assegnato al laboratorio
     */

    public Laboratorio(String nome, Topic topic, Progetto progetto, Dipendente referente) {
        setNome(nome);
        setTopic(topic);
        setProgetto(progetto);
        setReferente(referente);
    }

    /**
     * Costruttore del laboratorio nullo
     */

    public Laboratorio(){
        this(null , null , null , null);
    }

    /**
     * Crea un laboratorio con il nome fornitogli
     * @param nome nome del laboratorio
     */

    public Laboratorio(String nome){
        this(nome , null , null , null);
    }

    /**
     * Crea un nuovo laboratorio con il nome e il topic che vengono scelti
     * @param nome nome del laboratorio
     * @param topic topic del laboratorio
     */

    public Laboratorio(String nome, Topic topic) {
        this(nome , topic , null , null);
    }

    public boolean equals(Laboratorio laboratorio){
        if(this.getNome().equals(laboratorio.getNome())) return true;
        else return false;
    }

    /**
     * Metodo che si occupa di come viene visualizzato
     * un laboratorio:
     * -se il laboratorio ha un nome, viene mostrato il nome
     * -altrimenti viene mostrato "Non Assegnato"
     * @return La stringa da visualizzare
     */

    @Override
    public String toString() {
        if(getNome() != null)
            return getNome();
        else
            return "Non Assegnato";
    }

    /**
     * Metodo che ritorna il nome del laboratorio
     * @return nome del laboratorio
     */

    public String getNome() {
        return nome;
    }

    /**
     * Metodo che ritorna il nome del laboratorio
     * @param nome nome del laboratorio
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che ritorna il topic del laboratorio
     * @return topic del laboratorio
     */

    public Topic getTopic() {
        return topic;
    }

    /**
     * Metodo che setta il topic del laboratorio
     * @param topic topic del laboratorio
     */

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    /**
     * Metodo che ritorna il progetto assegnato al laboratorio
     * @return progetto assegnato al laboratorio
     */

    public Progetto getProgetto() {
        return progetto;
    }

    /**
     * Metodo che setta il progetto assegnato al laboratorio
     * @param progetto progetto assegnato al laboratorio
     */

    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
    }

    /**
     * Metodo che ritorna il referente assegnato al laboratorio
     * @return referente assegnato al laboratorio
     */

    public Dipendente getReferente() {
        return referente;
    }

    /**
     * Metodo che setta il referente assegnato al laboratorio
     * @param referente referente assegnato al laboratorio
     */

    public void setReferente(Dipendente referente) {
        this.referente = referente;
    }

    /**
     * Tipo di variabile che rappresenta quali sono
     * i possibili topic che un laboratorio può avere
     */

    public enum Topic {
        chimica,
        fisica,
        matematica,
        informatica,
        biologia
    }
}
