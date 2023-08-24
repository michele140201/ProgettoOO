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
     * @param nome nome del progetto
     * @param cup cup del progetto
     * @param referente referente del progetto
     * @param responsabile responsabile del progetto
     */

    public Progetto(String nome, int cup, Dipendente referente, Dipendente responsabile) {
        setNome(nome);
        setCup(cup);
        setReferente(referente);
        setResponsabile(responsabile);
    }

    /**
     * Crea un nuovo progetto con nome e cup che gli vengono forniti
     * @param nome nome del progetto
     * @param cup cup del progetto
     */

    public Progetto(String nome, int cup) {
        this(nome , cup , null , null);
    }

    /**
     * Crea un nuovo progetto con il nome fornitogli
     * @param nome nome del progetto
     */

    public Progetto(String nome) {
        this(nome , 0 , null , null);
    }

    /**
     * Crea un progetto con il cup fornitogli
     * @param cup cup del progetto
     */

    public Progetto(int cup) {
        this(null , cup , null , null);
    }

    /**
     * Costruttore del progetto nullo
     */

    public Progetto(){
        this(0);
    }



    /**
     * Metodo che si occupa di come viene visualizzato
     * un progetto:
     * -se il progetto ha un cup > 0 , viene mostrato il nome
     * -altrimenti viene mostrato "Non Assegnato"
     * @return La stringa da visualizzare
     */

    @Override
    public String toString() {
        if(getCup() > 0)
            return getNome();
        else
            return "Non Assegnato";
    }

    /**
     * Metodo che returna il cup del progetto
     * @return cup del progetto
     */

    public int getCup() {
        return cup;
    }

    /**
     * Metodo che setta il cup del progetto
     * @param cup cup del progetto
     */

    public void setCup(int cup) {
        this.cup = cup;
    }

    /**
     * Metodo che returna il nome del progetto
     * @return nome del progetto
     */

    public String getNome() {
        return nome;
    }

    /**
     * Metodo che setta il nome del progetto
     * @param nome nome del progetto
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che ritorna il referente del progetto
     * @return referente del progetto
     */

    public Dipendente getReferente() {
        return referente;
    }

    /**
     * Metodo che setta il referente del progetto
     * @param referente referente del progetto
     */

    public void setReferente(Dipendente referente) {
        this.referente = referente;
    }

    /**
     * Metodo che ritorna il responsabile del progetto
     * @return responsabile del progetto
     */

    public Dipendente getResponsabile() {
        return responsabile;
    }

    /**
     * Metodo che setta il responsabile del progetto
     * @param responsabile responsabile del progetto
     */

    public void setResponsabile(Dipendente responsabile) {
        this.responsabile = responsabile;
    }

    /**
     * Metodo che ritorna i laboratori a cui è assegnato il progetto
     * @return laboratori a cui è assegnato il progetto
     */

    public List<Laboratorio> getLaboratori() {
        return laboratori;
    }

    /**
     * Metodo che setta la lista di laboratori a cui è assegnato il progetto
     * @param laboratori laboratori a cui è assegnato il progetto
     */

    public void setLaboratori(List<Laboratorio> laboratori) {
        this.laboratori = laboratori;
    }

    /**
     * Metodo che aggiunge un laboratorio dopo che il progetto gli viene assegnato
     * @param laboratorio laboratorio a cui è stato assegnato il progetto
     */

    public void addLaboratorio(Laboratorio laboratorio) {
        laboratori.add(laboratorio);
    }

}
