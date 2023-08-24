package Model;

import java.util.List;

/**
 * Classe che rappresenta un progetto dell'azienda
 */

public class Progetto {
    private int cup;
    private String nome;
    private Dipendente referente;
    private Dipendente responsabile;
    private List<Laboratorio> laboratori;

    /**
     * Crea un nuovo progetto con i dati che gli vengono passati
     * @param cup cup del progetto
     * @param nome nome del progetto
     * @param referente
     * @param responsabile
     */

    public Progetto(int cup, String nome, Dipendente referente, Dipendente responsabile) {
        this(nome);
        setCup(cup);
        setReferente(referente);
        setResponsabile(responsabile);
    }

    public Progetto(String nome, int cup, Dipendente referente, Dipendente responsabile) {
        this(nome, cup, referente);
        setResponsabile(responsabile);
    }

    public Progetto(String nome, int cup) {
        this(nome);
        setCup(cup);
    }

    public Progetto(String nome) {
        setNome(nome);
    }

    public Progetto(int cup) {
        setCup(cup);
    }

    public Progetto(String nome, int cup, Dipendente dipendente) {
        setNome(nome);
        setCup(cup);
        setReferente(dipendente);
    }

    public List<Laboratorio> getLaboratori() {
        return laboratori;
    }

    public void setLaboratori(List<Laboratorio> laboratori) {
        this.laboratori = laboratori;
    }

    public void addLaboratorio(Laboratorio laboratorio) {
        laboratori.add(laboratorio);
    }

    public Progetto(){
        setCup(0);
    }

    @Override
    public String toString() {
        if(getCup() > 0)
            return getNome();
        else
            return "Non Assegnato";
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
