package GUI;



import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Dipendente;

public class ModelloDipendenti extends AbstractTableModel {

    private List<Dipendente> dipendenti;

    public ModelloDipendenti() {
        this(Collections.emptyList());
    }

    public ModelloDipendenti(List<Dipendente> dipendenti) {
        setDipendenti(dipendenti);
    }

    /**
     * Metodo che ritorna tutti i dipendenti
     * presenti nella Tabella
     * @return
     */

    public List<Dipendente> getDipendenti() {
        return dipendenti;
    }

    /**
     * Metodo che permette di settare i dipendenti della Tabella
     * con i dipendenti che gli vengono passati
     * @param dipendenti
     */

    public void setDipendenti(List<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
        fireTableDataChanged();
    }

    /**
     * Metodo che aggiunge un dipendente
     * alla Tabella
     * @param dipendente
     */

    public void aggiungiDipendente(Dipendente dipendente) {
        this.dipendenti.add(dipendente);
        fireTableDataChanged();
    }

    /**
     * Metodo che rimuove il dipendente selezionato
     * dalla Tabella
     * @param dipendente
     */

    public void rimuoviDipendente(Dipendente dipendente) {
        this.dipendenti.remove(dipendente);
        fireTableDataChanged();
    }

    /**
     * Metodo che ritorna il dipendente
     * della riga selezionata
     * @param rowIndex
     * @return
     */

    public Dipendente getDipendente(int rowIndex) {
        return dipendenti.get(rowIndex);
    }

    /**
     * Metodo che ritorna quanti dipendenti sono presenti nella Tabella
     * @return
     */

    @Override
    public int getRowCount() {
        return dipendenti.size();
    }

    /**
     * Metodo che ritorna quante colonne presenta la Tabella
     * @return
     */

    @Override
    public int getColumnCount() {
        return 7;
    }

    /**
     * Metodo che ritorna il valore della Tabella nel punto selezionato
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return
     */

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount())
            return null;

        Dipendente dipendente = dipendenti.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return dipendente.getId();
            case 1:
                return dipendente.getNome();
            case 2:
                return dipendente.getCognome();
            case 3:
                return dipendente.getDataNascita();
            case 4:
                return dipendente.getLaboratorio();
            case 5:
                return dipendente.isDirigente();
            case 6:
                return dipendente.getDataAssunzione();
            default:
                return null;
        }
    }

    /**
     * Metodo che ritorna il nome della colonna selezionata
     * @param column  the column being queried
     * @return
     */

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Id";
            case 1:
                return "Nome";
            case 2:
                return "Cognome";
            case 3:
                return "Data di nascita";
            case 4:
                return "Laboratorio";
            case 5:
                return "Dirigente";
            case 6:
                return "Data di assunzione";
            default:
                return null;
        }
    }

}
