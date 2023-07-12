package Model;

/**
 * Creazione del model Progetto con getter e setter
 */
public class Progetto {
    private int cup;
    private String nome;
    private String referente;
    private String responsabile;

    @Override
    public String toString() {
        return getNome();
    }

    public Progetto(int cup, String nome, String referente, String responsabile) {
        this(nome);
        setCup(cup);
        setReferente(referente);
        setResponsabile(responsabile);
    }

    public Progetto(String nome) {
        setNome(nome);
    }

    public int getCup() {
        return cup;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }

    public String getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(String responsabile) {
        this.responsabile = responsabile;
    }
}
