package GUI;


import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import Model.Dipendente;
import Model.Laboratorio;

public class ModelloDipendenti extends AbstractTableModel {

    private List<Dipendente> dipendenti;

    public ModelloDipendenti() {
        this(Collections.emptyList());
    }

    public ModelloDipendenti(List<Dipendente> dipendenti) {
        setDipendenti(dipendenti);
    }

    public void setLaboratorio(Laboratorio nomeLaboratorio, Dipendente dipendente) {
        dipendenti.remove(dipendente);
        dipendente.setLaboratorio(nomeLaboratorio);
        dipendenti.add(dipendente);
    }

    public void rimuoviDipendentiAssegnati(){
        int i = 0;
        while (i < dipendenti.size()){
            if(!dipendenti.get(i).haLaboratorioAssegnato()){
                rimuoviDipendente(dipendenti.get(i));
            }else{
                i++;
            }
        }
    }

    public void setDipendenti(List<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
        fireTableDataChanged();
    }

    public void aggiungiDipendente(Dipendente dipendente) {
        this.dipendenti.add(dipendente);
        fireTableDataChanged();
    }

    public void rimuoviDipendente(Dipendente dipendente) {
        this.dipendenti.remove(dipendente);
        fireTableDataChanged();
    }

    public Dipendente getDipendente(int rowIndex) {
        return dipendenti.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return dipendenti.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

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
