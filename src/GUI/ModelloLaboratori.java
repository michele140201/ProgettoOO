package GUI;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import Model.Laboratorio;

public class ModelloLaboratori extends AbstractTableModel {

    private List<Laboratorio> laboratori;

    public ModelloLaboratori() {
        this(Collections.emptyList());
    }

    public ModelloLaboratori(List<Laboratorio> laboratori) {
        setLaboratori(laboratori);
    }

    /**
     * Metodo che ritorna tutti i laboratori
     * presenti nella Tabella
     * @return
     */

    public List<Laboratorio> getLaboratori() {
        return laboratori;
    }

    /**
     * Metodo che setta nella Tabella
     * i laboratori che gli vengono passati
     * @param laboratori
     */

    public void setLaboratori(List<Laboratorio> laboratori) {
        this.laboratori = laboratori;
        fireTableDataChanged();
    }

    /**
     * Metodo che aggiunge un laboratorio
     * alla Tabella
     * @param laboratorio
     */

    public void aggiungiLaboratorio(Laboratorio laboratorio) {
        laboratori.add(laboratorio);
        fireTableDataChanged();
    }

    /**
     * Metodo che rimuove il laboratorio selezionato
     * dalla Tabella
     * @param laboratorio
     */

    public void rimuoviLaboratorio(Laboratorio laboratorio) {
        laboratori.remove(laboratorio);
        fireTableDataChanged();
    }

    /**
     * Metodo che ritorna il Laboratorio
     * della riga selezionata
     * @param rowIndex
     * @return
     */

    public Laboratorio getLaboratorio(int rowIndex) {
        return laboratori.get(rowIndex);
    }

    /**
     * Metodo che ritorna quanti laboratori
     * ci sono nella Tabella
     * @return
     */

    @Override
    public int getRowCount() {
        return laboratori.size();
    }

    /**
     * Metodo che ritorna quante colonne
     * ci sono nella Tabella
     * @return
     */

    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Metodo che restituisce il valore nella Tabella
     * nel punto selezionato
     * @param rowIndex        the row whose value is to be queried
     * @param columnIndex     the column whose value is to be queried
     * @return
     */

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount())
            return null;

        Laboratorio laboratorio = laboratori.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return laboratorio.getNome();
            case 1:
                return laboratorio.getTopic();
            case 2:
                return laboratorio.getProgetto();
            case 3:
                return laboratorio.getReferente();
            default:
                return null;
        }
    }

    /**
     * Metodo che restituisce il nome della
     * colonna selezionata
     * @param column  the column being queried
     * @return
     */

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nome";
            case 1:
                return "Topic";
            case 2:
                return "Progetto";
            case 3:
                return "Referente";
            default:
                return null;
        }
    }

}

