package GUI;

import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Model.Laboratorio;

/**
 * Classe che implementa la tabella dei laboratori usata nel GUImain
 */

public class ModelloLaboratori extends AbstractTableModel {

    private List<Laboratorio> laboratori;

    /**
     * Crea una nuova tabella vuota
     */

    public ModelloLaboratori() {
        this(Collections.emptyList());
    }

    /**
     * Crea una nuova tabella con gli elementi che gli vengono passati
     * @param laboratori laboratori da settare
     */

    public ModelloLaboratori(List<Laboratorio> laboratori) {
        setLaboratori(laboratori);
    }

    /**
     * Metodo che ritorna tutti i laboratori
     * presenti nella Tabella
     * @return tutti i laboratori
     */

    public List<Laboratorio> getLaboratori() {
        return laboratori;
    }

    /**
     * Metodo che setta nella Tabella
     * i laboratori che gli vengono passati
     * @param laboratori laboratori da settare
     */

    public void setLaboratori(List<Laboratorio> laboratori) {
        this.laboratori = laboratori;
        fireTableDataChanged();
    }

    /**
     * Metodo che aggiunge un laboratorio
     * alla Tabella
     * @param laboratorio laboratorio da aggiungere
     */

    public void aggiungiLaboratorio(Laboratorio laboratorio) {
        laboratori.add(laboratorio);
        fireTableDataChanged();
    }

    /**
     * Metodo che rimuove il laboratorio selezionato
     * dalla Tabella
     * @param laboratorio laboratorio da rimuovere
     */

    public void rimuoviLaboratorio(Laboratorio laboratorio) {
        laboratori.remove(laboratorio);
        fireTableDataChanged();
    }

    /**
     * Metodo che ritorna il Laboratorio
     * della riga selezionata
     * @param rowIndex riga selezionata
     * @return laboratorio selezionato
     */

    public Laboratorio getLaboratorio(int rowIndex) {
        return laboratori.get(rowIndex);
    }

    /**
     * Metodo che ritorna quanti laboratori
     * ci sono nella Tabella
     * @return numero laboratori
     */

    @Override
    public int getRowCount() {
        return laboratori.size();
    }

    /**
     * Metodo che ritorna quante colonne
     * ci sono nella Tabella
     * @return colonne della tabella
     */

    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Metodo che restituisce il valore nella Tabella
     * nel punto selezionato
     * @param rowIndex  riga selezionata
     * @param columnIndex  colonna selezionata
     * @return attributo del dipendente selezionato
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
     * @param column  colonna selezionata
     * @return nome della colonna selezionata
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

