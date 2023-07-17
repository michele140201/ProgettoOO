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
    private final GUImain guImain;

    public ControllerMainPage(DipendenteDAO dipendenteDAO, ProgettoDAO progettoDAO, LaboratorioDAO laboratorioDAO, CambioRuoloDAO cambioRuoloDAO, GUImain guImain) throws Exception {
        this.dipendenteDAO = dipendenteDAO;
        this.cambioRuoloDAO = cambioRuoloDAO;
        this.laboratorioDAO = laboratorioDAO;
        this.progettoDAO = progettoDAO;
        this.guImain = guImain;
        guImain.setDipendenti(inizializzaDipendenti());
        guImain.setLaboratori(laboratorioDAO.getLaboratoriAssegnati());
        guImain.setProgetti(inzializzaProgetti());
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

    public void mostraTuttiDipendenti() throws Exception {
        guImain.setDipendenti(inizializzaDipendenti());
    }



    public void licenziaDipendente(Dipendente dipendente) {
        try {
            if (dipendente.getId() != laboratorioDAO.getIdReferente(dipendente.getLaboratorio())) {
                dipendenteDAO.removeDipendente(dipendente.getId());
                guImain.showInfoMessage("Dipendente Licenziato! Poverino :(");
                guImain.rimuoviDipendente(dipendente);
            } else {
                guImain.showErrorMessage("Il Dipendente è Responsabile di Laboratorio");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void AssegnaLaboratorio(Dipendente dipendente, Laboratorio laboratorio) {
        try {
            dipendenteDAO.setLaboratorio(laboratorio, dipendente);
            guImain.aggiornaLaboratorioDipendente(laboratorio, dipendente);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void rimuovoLaboratorio(Laboratorio laboratorio) {
        try {
            laboratorioDAO.rimuovi(laboratorio.getNome());
            guImain.rimuoviLaboratorio(laboratorio);
            guImain.showInfoMessage("Laboratorio Eliminato");
        } catch (Exception e) {
            guImain.showErrorMessage("Errore nel Database");
            e.printStackTrace();
        }
        //todo chiama la gui per aggiungere
    }

    public void creaLaboratorio(Laboratorio laboratorio) {
        try {
            laboratorioDAO.inserisci(laboratorio);
            guImain.aggiungiLaboratorio(laboratorio);
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
            progettoDAO.setResponsabile(dipendente.getId(), progetto.getCup());
            guImain.showInfoMessage("Modifica Riuscita!");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public void setReferenteProgetto(Dipendente dipendente, Progetto progetto) {
        try {
            progettoDAO.setReferente(dipendente.getId(), progetto.getCup());
            guImain.showInfoMessage("Aggiornamento Riuscito");
            guImain.aggiornaReferenteProgetto(progetto , dipendente);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    public Date getDataPromozione(Dipendente dipendente) {
        try {
            Date dataCambio = cambioRuoloDAO.getDataPromozione(dipendente.getId());
            return dataCambio;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    public void promuovi(Dipendente dipendente) {
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

    public void inserisciProgetto(Progetto progetto) {
        try {
            progettoDAO.inserisci(progetto);
            guImain.aggiungiProgetto(progetto);
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

    public void setReferente(Dipendente dipendente, Progetto progetto) {
        try {
            int iddip = progettoDAO.getReferente(progetto, "Referente");
            progetto.setReferente(dipendente);
        } catch (Exception e) {
            progetto.setReferente(null);
        }
    }

    private void setResponsabile(Progetto progetto) {
        try {
            int iddip = progettoDAO.getReferente(progetto, "Responsabile");
            Dipendente dipendente = dipendenteDAO.getDipendente(iddip);
            progetto.setReferente(dipendente);
        } catch (Exception e) {
            progetto.setReferente(null);
        }

    }

    private List<Progetto> inzializzaProgetti() throws Exception {
        List<Progetto> progetti = progettoDAO.getProgetti();
        for (Progetto progetto : progetti) {
            setResponsabile(progetto);
        }
        return progetti;
    }

    private List<Dipendente> inizializzaDipendenti() throws Exception {
        List<Dipendente> dipendenti = dipendenteDAO.getDipendenti();
        for (Dipendente dipendente : dipendenti) {
            dipendente.setLaboratorio(laboratorioDAO.getLaboratorioDipendente(dipendente));
        }
        return dipendenti;
    }

    public void setProgettiLaboratorioComboBox() throws Exception {
        List<Progetto> Progetti = progettoDAO.getProgetti();
        ArrayList<Progetto> ProgettiScelti = new ArrayList<>();
        int i = 0;
        while (i < Progetti.size()) {
            Progetto p = Progetti.get(i);
            if (laboratorioDAO.getNumeroLaboratoriAssegnati(p.getCup()) < 3) {
                {
                    ProgettiScelti.add(p);
                }
            }
            i++;
        }
        guImain.setComboBoxLaboratorioDipendente(ProgettiScelti);
    }
}
