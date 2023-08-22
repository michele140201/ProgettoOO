package Controller;

import DAO.*;
import GUI.GUImain;
import Model.Dipendente;
import Model.Progetto;

import java.util.List;

public class ControllerAvvio {
    ConnectionController connectionController = new ConnectionController();
    ProgettoDAO progettoDAO = new ProgettoDAOImpl(connectionController);
    DipendenteDAO dipendenteDAO = new DipendenteDAOimpl(connectionController);
    LaboratorioDAO laboratorioDAO = new LaboratorioDAOImpl(connectionController);
    GUImain guimain = new GUImain();
    ControllerMainPage controllerMainPage = new ControllerMainPage(dipendenteDAO, progettoDAO, laboratorioDAO , guimain);

    public ControllerAvvio() throws Exception {
        guimain.setController(controllerMainPage);
        guimain.setVisible(true);
        inizializzaGui();
    }

    /**
     * Metodo che permette l'inizializzazione dell'interfaccia grafica
     * inizializzando tutte le tabelle con i dati che gli vengono forniti
     * dal database tramite i DAO
     * @throws Exception
     */

    private void inizializzaGui() throws Exception {
        guimain.setDipendenti(inizializzaDipendenti());
        guimain.setLaboratori(laboratorioDAO.getLaboratori());
        guimain.setProgetti(inzializzaProgetti());
        guimain.setLaboratoriDipendenti();
        guimain.setProgettiLaboratorio();
        guimain.setReferenteLaboratorio();
        guimain.setProgettiLaboratorio();
        guimain.setReferenteProgetto();
        guimain.setLaboratoriProgetto();
        guimain.setResponsabiliProgetto();
    }

    private List<Progetto> inzializzaProgetti() throws Exception {
        List<Progetto> progetti = progettoDAO.getProgetti();
        return progetti;
    }

    private List<Dipendente> inizializzaDipendenti() throws Exception {
        List<Dipendente> dipendenti = dipendenteDAO.getDipendenti();
        return dipendenti;
    }

}
