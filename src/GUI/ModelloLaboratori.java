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

    public void setLaboratori(List<Laboratorio> laboratori) {
        this.laboratori = laboratori;
        fireTableDataChanged();
    }

    public List<Laboratorio> getLaboratori(){
        return laboratori;
    }

    public void aggiungiLaboratorio(Laboratorio laboratorio){
        laboratori.add(laboratorio);
        fireTableDataChanged();
    }

    public Laboratorio getLaboratorio(int rowIndex) {
        return laboratori.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return laboratori.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

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
                return laboratorio.getResponsabile();
            case 3:
                return laboratorio.getProgetto();
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
                return "Topic";
            case 2:
                return "Referente";
            case 3:
                return "Progetto";
            default:
                return null;
        }
    }

}

