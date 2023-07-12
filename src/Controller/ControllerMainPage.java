package Controller;

import GUI.GUImain;
import Model.Dipendente;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import DAO.*;
import Model.Laboratorio;
import Model.Progetto;

/**
 * Classe che si occupa dell'interazione tra
 * i DAO e l'interfaccia grafica
 */
public class ControllerMainPage {
    private final ProgettoDAO progettoDAO;
    private final DipendenteDAO dipendenteDAO;
    private final LaboratorioDAO laboratorioDAO;
    private final CambioRuoloDAO cambioRuoloDAO;
    private GUImain guImain;

    public ControllerMainPage(DipendenteDAO dipendenteDAO, ProgettoDAO progettoDAO, LaboratorioDAO laboratorioDAO, CambioRuoloDAO cambioRuoloDAO) throws Exception{
        this.dipendenteDAO = dipendenteDAO;
        this.cambioRuoloDAO = cambioRuoloDAO;
        this.laboratorioDAO = laboratorioDAO;
        this.progettoDAO = progettoDAO;

        guImain.setDipendenti(dipendenteDAO.getDipendenti());
        //todo setlaboratori e set progetti


    }

    public void aggiungiDipendente(Dipendente dipendente) {

        try {
            dipendenteDAO.insertDipendente(dipendente);
            guImain.aggiungiDipendente(dipendente);
            guImain.showInfoMessage("Dipendente Assunto!");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("OPS! Qualcosa è andato storto");
        }
    }

    public void licenziaDipendente(Dipendente dipendente) {
        try {
            if (dipendente.getId() != laboratorioDAO.getIdReferente(dipendente.getLaboratorio())) {
                dipendenteDAO.removeDipendente(dipendente.getId());
               guImain.showInfoMessage("Dipendente Licenziato! Poverino :(");
            } else {
                guImain.showErrorMessage("Il Dipendente è Responsabile di Laboratorio");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void AssegnaLaboratorio(Dipendente dipendente ,String NuovoLab) {
        try {
            if (dipendente.getId() != laboratorioDAO.getIdReferente(dipendente.getLaboratorio())){
                dipendenteDAO.setLaboratorio(NuovoLab, dipendente.getId());
            } else {
                guImain.showErrorMessage( "Il Dipendente è Responsabile di Laboratorio");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void rimuovoLaboratorio(Laboratorio laboratorio) {
        try {
            laboratorioDAO.rimuovi(laboratorio.getNome());
            guImain.showInfoMessage("Laboratorio Eliminato");
        } catch (Exception e) {
            guImain.showErrorMessage("Errore nel Database");
        }
        //todo chiama la gui per aggiungere
    }

    public void creaLaboratorio(Laboratorio laboratorio) {
        try {
            laboratorioDAO.inserisci(laboratorio);
            guImain.showInfoMessage("Inserimento Riuscito!");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
        //todo chiama la gui per aggiungere
    }

    /**
     * elimina un progetto
     */
    public void EliminaProgetto(Progetto progetto) {
        try {
            progettoDAO.rimuovi(progetto.getCup());
            guImain.showInfoMessage("Progetto Eliminato");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }


    public void degrada(Dipendente dipendente) {
        try {
            if (dipendente.isDirigente()) {
                dipendenteDAO.degrada(dipendente.getId());
                cambioRuoloDAO.removePromozione(dipendente.getId());
            } else {
                guImain.showErrorMessage("IMPOSSIBILE DEGRADARE!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void setResponsabile(Dipendente dipendente, Progetto progetto) {
        try {
            progettoDAO.setResponsabile(dipendente.getId() , progetto.getCup());
            guImain.showInfoMessage("Modifica Riuscita!");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void setReferente(Dipendente dipendente , Progetto progetto) {
        try {
            progettoDAO.setReferente(dipendente.getId() , progetto.getCup());
            guImain.showInfoMessage("Aggiornamento Riuscito");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public Date getDataPromozione(int idDip) {
        try {
            Date dataCambio = cambioRuoloDAO.getDataPromozione(idDip);
            return dataCambio;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    public void promuovi(Dipendente dipendente){
        try {
            if (!dipendente.isDirigente()) {
                dipendenteDAO.promuovi(dipendente.getId());
                cambioRuoloDAO.setDataPromozione(dipendente.getId());
                guImain.showInfoMessage("Promosso!");
            } else {
                guImain.showErrorMessage("IMPOSSIBILE PROMUOVERE!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public List<Dipendente> getReferenti(Progetto progetto) {
        try {
            List<Laboratorio> labs = laboratorioDAO.getLaboratoriAssegnati(progetto.getCup());
            int i = 0;
            List<Dipendente> dips = new ArrayList<>();
            while (i < labs.size()) {
                try {
                    dips.addAll(dipendenteDAO.getSenior(labs.get(i).getNome()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Errore nel Database");
                }
                i++;
            }
            return dips;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    public List<Laboratorio> getLaboratori() {
        try {
            List<Laboratorio> Laboratori = laboratorioDAO.getLaboratoriAssegnati();
            return Laboratori;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore di Darabase");
            return null;
        }
    }

    public List<Dipendente> getSenior(String NomeLab) {
        try {
            List<Dipendente> DIR = dipendenteDAO.getSenior(NomeLab);
            return DIR;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    public List<Dipendente> getDipendentiAssegnare() {
        try {
            List<Dipendente> Lista = dipendenteDAO.getDirigenti();
            return Lista;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    public void riassegnaDipendente(Laboratorio laboratorio, Dipendente dipendente) {
        try {
            laboratorioDAO.riassegnaDipendente(laboratorio.getNome(), dipendente.getId());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
        }
    }

    public void inserisciProgetto(Progetto p) {
        try {
            progettoDAO.inserisci(p);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void assegnaProgetto(Laboratorio laboratorio, Progetto progetto) {
        try {
            laboratorioDAO.riassegnaProgetto(laboratorio.getNome(), progetto.getCup());
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public List<Dipendente> getResponsabiliPossibili(Progetto progetto) {
        try {
            List<Laboratorio> labs = laboratorioDAO.getLaboratoriAssegnati(progetto.getCup());
            int i = 0;
            List<Dipendente> dips = new ArrayList<>();
            while (i < labs.size()) {
                dips.addAll(dipendenteDAO.getDirigenti(labs.get(i).getNome()));
                i++;
            }
            return dips;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }
}
