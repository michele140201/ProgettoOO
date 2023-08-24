package Model;

import java.util.Date;

/**
 * Classe che rappresenta un dipendente dell'azienda
 */
public class Dipendente {

    private String nome;
    private String cognome;
    private int id;
    private boolean dirigente;
    private Date dataAssunzione;
    private Date dataNascita;
    private Laboratorio laboratorio;
    private Date dataPromozione;

    /**
     * Costruttore generico di un nuovo dipendente
     * che distingue il caso in cui il dipendente
     * sia un dirigente da quello in cui non lo è
     *
     * @param nome nome del dipendente
     * @param cognome cognome del dipendente
     * @param id id del dipendente
     * @param dirigente dato che indica se il dipendente è dirigente
     * @param dataAssunzione data di assunzione del dipendente
     * @param dataNascita data di nascita del dipendente
     * @param laboratorio laboratorio a cui è assegnato il dipendente
     * @param dataPromozione data in cui è stato promosso il dipendente
     */

    public Dipendente(String nome, String cognome, int id, boolean dirigente, Date dataAssunzione, Date dataNascita, Laboratorio laboratorio , Date dataPromozione) {
        if(dirigente == true){
            setNome(nome);
            setCognome(cognome);
            setId(id);
            setDirigente(true);
            setDataAssunzione(dataAssunzione);
            setDataNascita(dataNascita);
            setLaboratorio(laboratorio);
            setDataPromozione(dataPromozione);
        }else{
            setNome(nome);
            setCognome(cognome);
            setId(id);
            setDirigente(false);
            setDataAssunzione(dataAssunzione);
            setDataNascita(dataNascita);
            setLaboratorio(laboratorio);
            setDataPromozione(null);
        }
    }

    /**
     * Crea un nuovo dipendente con i dati che gli vengono forniti
     * @param nome nome del dipendente
     * @param cognome cognome del dipendente
     * @param id id del dipendente
     * @param dirigente dato che indica se il dipendente è dirigente
     * @param dataAssunzione quando è stato assunto il dipendente
     * @param dataNascita data di nascita del dipendente
     * @param dataPromozione quando è stato promosso il dipendente
     */

    public Dipendente(String nome, String cognome, int id, boolean dirigente, Date dataAssunzione, Date dataNascita , Date dataPromozione) {
        this(nome, cognome, id, dirigente, dataAssunzione, dataNascita, null , dataPromozione);
    }

    /**
     * Costruttore di un dipendente a cui non è stato
     * ancora generato l'id, che quindi ha come id 0
     * @param nome nome del dipendente
     * @param cognome cognome del dipendente
     * @param dirigente dato che indica se il dipendente è dirigente
     * @param dataAssunzione quando è stato assunto il dipendente
     * @param dataNascita data di nascita del dipendente
     * @param dataPromozione quando è stato promosso il dipendente
     */

    public Dipendente(String nome, String cognome, boolean dirigente, Date dataAssunzione, Date dataNascita , Date dataPromozione) {
        this(nome, cognome, 0, dirigente, dataAssunzione, dataNascita, null , dataPromozione);
    }

    /**
     * Crea un nuovo dipendente con i dati che gli vengono forniti
     * @param nome nome del dipendente
     * @param cognome cognome del dipendente
     * @param dirigente dato che indica se il dipendente è dirigente
     * @param dataAssunzione quando è stato assunto il dipendente
     * @param dataNascita data di nascita del dipendente
     * @param laboratorio laboratorio a cui è assegnato il dipendente
     */

    public Dipendente(String nome, String cognome, Boolean dirigente, Date dataAssunzione, java.sql.Date dataNascita, Laboratorio laboratorio) {
        setNome(nome);
        setCognome(cognome);
        setDirigente(dirigente);
        setDataAssunzione(dataAssunzione);
        setDataNascita(dataNascita);
        setLaboratorio(laboratorio);
    }

    /**
     * crea un dipendente che ha come unico campo l'id.
     * Serve per il recupero dei dati dal database
     * @param id id del dipendente
     */

    public Dipendente(int id) {
        setId(id);
    }

    /**
     * Costruttore del dipendente vuoto
     */

    public Dipendente(){
        setId(0);
    }


    /**
     * Metodo che si occupa di come viene visualizzato
     * un dipendente:
     * -se il dipendente ha un id>0, viene mostrato nome, cognome e id
     * -altrimenti viene mostrato "Non Assegnato"
     * @return La stringa da stampare
     */

    @Override
    public String toString() {
        if (getId() > 0)
            return getNome() + " " + getCognome() + " " + getId();
        else {
            return "Non Assegnato";
        }
    }

    /**
     * Metodo che ritorna il laboratorio del dipendente
     * @return laboratorio del dipendente
     */

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    /**
     * Metodo che setta il laboratorio del dipendente
     * @param laboratorio laboratorio del dipendente
     */

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * Metodo che ritorna la data di nascita del dipendente
     * @return data di nascita del dipendente
     */

    public Date getDataNascita() {
        return dataNascita;
    }

    /**
     * Metodo che setta la data di nascita del dipendente
     * @param dataNascita data di nascita del dipendente
     */

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * Metodo che ritorna la data in cui è stato assunto il dipendente
     * @return quando è stato assunto il dipendente
     */

    public Date getDataAssunzione() {
        return dataAssunzione;
    }

    /**
     * Metodo che setta la data di assunzione del dipendente
     * @param dataAssunzione quando è stato assunto il dipendente
     */

    public void setDataAssunzione(Date dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }

    /**
     * Metodo che ritorna se un dipendente è un dirigente
     * @return se un dipendente è dirigente
     */

    public boolean isDirigente() {
        return dirigente;
    }

    /**
     * Metodo che setta se un dipendente è dirigente
     * @param dirigente se un dipendente è dirigente
     */

    public void setDirigente(boolean dirigente) {
        this.dirigente = dirigente;
    }

    /**
     * Metodo che ritorna l'id di un dipendente
     * @return id del dipendente
     */

    public int getId() {
        return id;
    }

    /**
     * Metodo che setta l'id di un dipendente
     * @param id id del dipendente
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo che ritorna il nome di un dipendente
     * @return nome del dipendente
     */

    public String getNome() {
        return nome;
    }

    /**
     * Metodo che setta il nome di un dipendente
     * @param nome nome del dipendente
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che ritorna il cognome di un dipendente
     * @return cognome del dipendente
     */

    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che setta il cognome di un dipendente
     * @param cognome cognome del dipendente
     */

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Metodo che ritorna la data di promozione di un dipendente
     * @return data di promozione del dipendente
     */

    public Date getDataPromozione() {
        return dataPromozione;
    }

    /**
     * Metodo che setta la data di promozione del dipendente
     * @param dataPromozione data di promozione del dipendente
     */

    public void setDataPromozione(Date dataPromozione) {
        this.dataPromozione = dataPromozione;
    }
}
