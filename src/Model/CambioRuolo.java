package Model;

import java.util.Date;

/**
 * Creazione del Model Cambio Ruolo, che serve a memorizzare la data in cui
 * un dipendente è stato promosso a dirigente, unico scatto di carriera
 * non calcolabile
 */
public class CambioRuolo {
    private int idDipendente;
    private Date data;

    public CambioRuolo(int idDipendente, Date data) {
        setIdDipendente(idDipendente);
        setData(data);
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdDipendente() {
        return idDipendente;
    }

    public void setIdDipendente(int idDipendente) {
        this.idDipendente = idDipendente;
    }
}
