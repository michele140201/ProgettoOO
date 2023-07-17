package Model;

/**
 * Creazione del model Laboratorio con getter e setter
 */
public class Laboratorio {
    private String nome;
    private Topic topic;
    private Progetto progetto;
    private int responsabile;

    public Laboratorio(String nome, Topic topic, Progetto progetto, int responsabile) {
        this(nome, topic);
        setProgetto(progetto);
        setResponsabile(responsabile);
    }

    public Laboratorio(String nome, Topic topic) {
        setNome(nome);
        setTopic(topic);
    }

    @Override
    public String toString() {
        return getNome();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Progetto getProgetto() {
        return progetto;
    }

    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
    }

    public int getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(int responsabile) {
        this.responsabile = responsabile;
    }

    public enum Topic {
        chimica,
        fisica,
        matematica,
        informatica,
        biologia
    }
}
