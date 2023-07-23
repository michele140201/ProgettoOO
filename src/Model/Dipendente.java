package Model;

import java.util.Date;

/**
 * Creazione del model Dipendente con getter e setter
 */
public class Dipendente {

    private String nome;
    private String cognome;
    private int id;
    private boolean dirigente;
    private Date dataAssunzione;
    private Date dataNascita;
    private Laboratorio laboratorio;

    public Dipendente(String nome, String cognome, int id, boolean dirigente, Date dataAssunzione, Date dataNascita) {
        this(nome, cognome, id, dirigente, dataAssunzione, dataNascita, null);
    }

    public Dipendente(String nome, String cognome, boolean dirigente, Date dataAssunzione, Date dataNascita) {
        this(nome, cognome, 0, dirigente, dataAssunzione, dataNascita, null);
    }

    public Dipendente(String nome, String cognome, int id, boolean dirigente, Date dataAssunzione, Date dataNascita, Laboratorio laboratorio) {
        setNome(nome);
        setCognome(cognome);
        setId(id);
        setDirigente(dirigente);
        setDataAssunzione(dataAssunzione);
        setDataNascita(dataNascita);
        setLaboratorio(laboratorio);
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
        if (getNome() != null)
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
}
