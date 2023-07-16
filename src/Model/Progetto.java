package Model;

/**
 * Creazione del model Progetto con getter e setter
 */
public class Progetto {
    private int cup;
    private String nome;
    private Dipendente referente;
    private Dipendente responsabile;

    public Progetto(int cup, String nome, Dipendente referente, Dipendente responsabile) {
        this(nome);
        setCup(cup);
        setReferente(referente);
        setResponsabile(responsabile);
    }

    public Progetto(String nome, int cup) {
        this(nome);
        setCup(cup);
    }

    public Progetto(String nome) {
        setNome(nome);
    }

    @Override
    public String toString() {
        return getNome();
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

    public Dipendente getReferente() {
        return referente;
    }

    public void setReferente(Dipendente referente) {
        this.referente = referente;
    }

    public Dipendente getResponsabile() {
        return responsabile;
    }

    public void setResponsabile(Dipendente responsabile) {
        this.responsabile = responsabile;
    }
}
