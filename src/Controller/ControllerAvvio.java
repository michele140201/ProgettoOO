package Controller;

import DAO.*;
import GUI.GUImain;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;
import java.util.List;

/**
 * Classe che si occupa di avviare il programma
 */

public class ControllerAvvio {
    ConnectionController connectionController = new ConnectionController();
    ProgettoDAO progettoDAO = new ProgettoDAOImpl(connectionController);
    DipendenteDAO dipendenteDAO = new DipendenteDAOimpl(connectionController);
    LaboratorioDAO laboratorioDAO = new LaboratorioDAOImpl(connectionController);
    GUImain guimain = new GUImain();
    ControllerMainPage controllerMainPage = new ControllerMainPage(dipendenteDAO, progettoDAO, laboratorioDAO , guimain);

    /**
     * Costruttore che setta il controller dell'interfaccia grafica e la fa partire
     * @throws Exception
     */

    public ControllerAvvio() throws Exception {
        inizializzaGui();
    }

    /**
     * Metodo che permette l'inizializzazione dell'interfaccia grafica
     * inizializzando tutte le tabelle con i dati che gli vengono forniti
     * dal database tramite i DAO
     * @throws Exception
     */

    private void inizializzaGui() throws Exception {
        guimain.setController(controllerMainPage);
        guimain.setDipendenti(getDipendenti());
        guimain.setLaboratori(getLaboratori());
        guimain.setProgetti(getProgetti());
        guimain.setProgettiLaboratorio();
        guimain.setReferenteLaboratorio();
        guimain.setProgettiLaboratorio();
        guimain.setReferenteProgetto();
        guimain.setLaboratoriProgetto();
        guimain.setResponsabiliProgetto();
        guimain.setLaboratoriDipendenti();
        guimain.setVisible(true);
    }

    /**
     * Metodo che chiede ai DAO tutti i progetti del
     * Database
     * @return
     * @throws Exception
     */

    private List<Progetto> getProgetti() throws Exception {
        List<Progetto> progetti = progettoDAO.getProgetti();
        return progetti;
    }

    /**
     * Metodo che chiede ai DAO tutti i dipendenti del
     * Database
     * @return
     * @throws Exception
     */

    private List<Dipendente> getDipendenti() throws Exception {
        List<Dipendente> dipendenti = dipendenteDAO.getDipendenti();
        return dipendenti;
    }

    /**
     * Metodo che chiede ai DAO tutti i laboratori del
     * Database
     * @return
     * @throws Exception
     */

    private List<Laboratorio> getLaboratori() throws Exception{
        List<Laboratorio> laboratori = laboratorioDAO.getLaboratori();
        return laboratori;
    }

}
