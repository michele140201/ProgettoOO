package GUI;

import Model.Progetto;
import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.List;

/**
 * Classe che implementa la tabella dei progetti usata nel GUImain
 */

public class ModelloProgetti extends AbstractTableModel {
    private List<Progetto> progetti;

    /**
     * Crea una tabella vuota
     */

    public ModelloProgetti() {
        this(Collections.emptyList());
    }

    /**
     * Crea una tabella con gli elementi che gli vengono passati
     * @param progetti progetti da settare
     */

    public ModelloProgetti(List<Progetto> progetti) {
        setProgetti(progetti);
    }

    /**
     * Metodo che ritorna tutti i progetti
     * presenti nella Tabella
     * @return tutti i progetti
     */

    public List<Progetto> getProgetti() {
        return progetti;
    }

    /**
     * Metodo che setta i progetti nella Tabella
     * con quelli che gli vengono passati
     * @param progetti progetti da settare
     */

    public void setProgetti(List<Progetto> progetti) {
        this.progetti = progetti;
        fireTableDataChanged();
    }

    /**
     * Metodo che aggiunge un progetto
     * alla Tabella
     * @param progetto progetto da aggiungere
     */

    public void aggiungiProgetto(Progetto progetto) {
        progetti.add(progetto);
        fireTableDataChanged();
    }

    /**
     * Metodo che rimuove il progetto selezionato
     * dalla Tabella
     * @param progetto progetto da rimuovere
     */

    public void rimuoviProgetto(Progetto progetto) {
        progetti.remove(progetto);
        fireTableDataChanged();
    }

    /**
     * Metodo che ritorna il Progetto
     * della riga selezionata
     * @param rowIndex
     * @return progetto selezionato
     */

    public Progetto getProgetto(int rowIndex) {
        return progetti.get(rowIndex);
    }

    /**
     *Metodo che ritorna quanti progetti
     * ci sono nella Tabella
     * @return numero progetti
     */

    @Override
    public int getRowCount() {
        return progetti.size();
    }

    /**
     * Metodo che ritorna quante colonne
     * ci sono nella Tabella
     * @return numero colonne
     */

    @Override
    public int getColumnCount() {
        return 4;
    }

    /**
     * Metodo che ritorna il valore
     * nel punto selezionato
     * @param rowIndex riga selezionata
     * @param columnIndex colonna selezionata
     * @return attributo del progetto selezionato
     */

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount())
            return null;

        Progetto progetto = progetti.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return progetto.getNome();
            case 1:
                return progetto.getCup();
            case 2:
                return progetto.getResponsabile();
            case 3:
                return progetto.getReferente();
            default:
                return null;
        }
    }

    /**
     * Metodo che ritorna il nome
     * della colonna selezionata
     * @param column  colonna selezionata
     * @return nome della colonna selezionata
     */

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nome";
            case 1:
                return "CUP";
            case 2:
                return "Responsabile";
            case 3:
                return "Referente";
            default:
                return null;
        }
    }
}
