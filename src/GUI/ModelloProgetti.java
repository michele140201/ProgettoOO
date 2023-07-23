package GUI;

import Model.Dipendente;
import Model.Progetto;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.List;

public class ModelloProgetti extends AbstractTableModel {
    private List<Progetto> progetti;

    public ModelloProgetti() {
        this(Collections.emptyList());
    }

    public ModelloProgetti(List<Progetto> progetti) {
        setProgetti(progetti);
    }

    public List<Progetto> getProgetti() {
        return progetti;
    }

    public void setProgetti(List<Progetto> progetti) {
        this.progetti = progetti;
        fireTableDataChanged();
    }

    public void aggiungiProgetto(Progetto progetto) {
        progetti.add(progetto);
        fireTableDataChanged();
    }

    public void rimuoviProgetto(Progetto progetto) {
        progetti.remove(progetto);
        fireTableDataChanged();
    }


    public Progetto getProgetto(int rowIndex) {
        return progetti.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return progetti.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

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
                return progetto.getReferente();
            case 3:
                return progetto.getResponsabile();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nome";
            case 1:
                return "CUP";
            case 2:
                return "Referente";
            case 3:
                return "Responsabile";
            default:
                return null;
        }
    }
}
