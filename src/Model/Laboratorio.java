package Model;
public class Laboratorio {
    private String nome;
    private Topic topic;
    private Progetto progetto;
    private Dipendente referente;

    public Laboratorio(String nome, Topic topic, Progetto progetto, Dipendente referente) {
        this(nome, topic);
        setProgetto(progetto);
        setReferente(referente);
    }

    public Laboratorio(){
        setNome(null);
    }

    public Laboratorio(String nome){
        setNome(nome);
    }

    public Laboratorio(String nome, Topic topic) {
        setNome(nome);
        setTopic(topic);
    }

    @Override
    public String toString() {
        if(getNome() != null)
            return getNome();
        else
            return "Non Assegnato";
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

    public Dipendente getReferente() {
        return referente;
    }

    public void setReferente(Dipendente referente) {
        this.referente = referente;
    }

    public enum Topic {
        chimica,
        fisica,
        matematica,
        informatica,
        biologia
    }
}
