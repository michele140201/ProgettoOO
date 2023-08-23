package Model;

import java.util.Date;

/**
 * Classe che rappresenta un dipendente dell'azienda
 */
//todo commenti ai model, alla gui e ai DAO. Finire anche i commenti dei costruttori e delle classi
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
     * @param nome
     * @param cognome
     * @param id
     * @param dirigente
     * @param dataAssunzione
     * @param dataNascita
     * @param laboratorio
     * @param dataPromozione
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
     * Costruttore del dipendente nullo, che quindi ha come id 0
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



    public Dipendente(int id) {
        setId(id);
    }

    public Dipendente(){
        setId(0);
    }

    public Dipendente(String nome, String cognome, Boolean dir, Date date, java.sql.Date datadiN, Laboratorio laboratorio) {
        setNome(nome);
        setCognome(cognome);
        setDirigente(dirigente);
        setDataAssunzione(dataAssunzione);
        setDataNascita(dataNascita);
        setLaboratorio(laboratorio);
    }

    @Override
    public String toString() {
        if (getId() > 0)
            return getNome() + " " + getCognome() + " " + getId();
        else {
            return "Non Assegnato";
        }
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Date getDataAssunzione() {
        return dataAssunzione;
    }

    public void setDataAssunzione(Date dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }

    public boolean isDirigente() {
        return dirigente;
    }

    public void setDirigente(boolean dirigente) {
        this.dirigente = dirigente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataPromozione() {
        return dataPromozione;
    }

    public void setDataPromozione(Date dataPromozione) {
        this.dataPromozione = dataPromozione;
    }
}
