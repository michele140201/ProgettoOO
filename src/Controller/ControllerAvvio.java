package Controller;

import DAO.*;
import GUI.GUImain;
import Model.Dipendente;
import Model.Laboratorio;
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
        guimain.setDipendenti(getDipendenti());
        guimain.setLaboratori(getLaboratori());
        guimain.setProgetti(getProgetti());
        guimain.setLaboratoriDipendenti();
        guimain.setProgettiLaboratorio();
        guimain.setReferenteLaboratorio();
        guimain.setProgettiLaboratorio();
        guimain.setReferenteProgetto();
        guimain.setLaboratoriProgetto();
        guimain.setResponsabiliProgetto();
    }

    /**
     * Metodo che chiede ai DAO tutti i progetti del
     * Database
     * @return
     * @throws Exception
     */

    private List<Progetto> getProgetti() throws Exception {
        return progettoDAO.getProgetti();
    }

    /**
     * Metodo che chiede ai DAO tutti i dipendenti del
     * Database
     * @return
     * @throws Exception
     */

    private List<Dipendente> getDipendenti() throws Exception {
        return dipendenteDAO.getDipendenti();
    }

    /**
     * Metodo che chiede ai DAO tutti i laboratori del
     * Database
     * @return
     * @throws Exception
     */

    private List<Laboratorio> getLaboratori() throws Exception{
        return laboratorioDAO.getLaboratori();
    }

}
