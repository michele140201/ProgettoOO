package Model;

import javax.xml.crypto.Data;
import java.util.Date;

public class CambioRuolo {
    private int id_dip;
    private Date DataCambio;

    public CambioRuolo(int id_dip, Date DataCambio) {
        this.id_dip = id_dip;
        this.DataCambio = DataCambio;
    }

    public Date getDataCambio() {
        return DataCambio;
    }

    public void setDataCambio(Date dataCambio) {
        DataCambio = dataCambio;
    }

    public int getId_dip() {
        return id_dip;
    }

    public void setId_dip(int id_dip) {
        this.id_dip = id_dip;
    }
}
