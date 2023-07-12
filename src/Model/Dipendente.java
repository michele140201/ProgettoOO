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
    private String laboratorio;

    public Dipendente(String nome, String cognome, int id, boolean dirigente, Date dataAssunzione, Date dataNascita) {
        this(nome, cognome, id, dirigente, dataAssunzione, dataNascita, null);
    }

    public Dipendente(String nome, String cognome , boolean dirigente, Date dataAssunzione, Date dataNascita) {
        this(nome, cognome, 0 , dirigente, dataAssunzione, dataNascita, null);
    }


    public Dipendente(String nome, String cognome, int id, boolean dirigente, Date dataAssunzione, Date dataNascita, String laboratorio) {
        setNome(nome);
        setCognome(cognome);
        setId(id);
        setDirigente(dirigente);
        setDataAssunzione(dataAssunzione);
        setDataNascita(dataNascita);
        setLaboratorio(laboratorio);
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        if (laboratorio != null) this.laboratorio = laboratorio;
        else this.laboratorio = "Non Assegnato";
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
