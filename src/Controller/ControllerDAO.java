package Controller;
import DAO.*;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import javax.swing.*;
import java.util.List;

public class ControllerDAO {
    private ProgettoDAO progettoDAO = new ProgettoDAOImpl();
    private DipendenteDAO dipendenteDAO = new DipendenteDAOimpl();
    private LaboratorioDAO laboratorioDAO = new LaboratorioDAOImpl();
    private CambioRuoloDAO cambioRuoloDAO = new CambioRuoloDAOImpl();

    public List<Progetto> getProgetti(){
        try{
            return progettoDAO.ottieniprogetti();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
            return null;
        }
    }
    public List<Laboratorio> getLaboratori(){
        try{
            return laboratorioDAO.getLabs();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
            return null;
        }
    }
    public List<Dipendente> getDipendenti(){
        try{
            return dipendenteDAO.getDipendente();
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
            return null;
        }
    }
    public int getReferenteLab(String NomeLab){
        return laboratorioDAO.getReferenteLab(NomeLab);
    }
    public int RimuoviDipendente(int id){
        return dipendenteDAO.removeDipendente(id);
    }
}
