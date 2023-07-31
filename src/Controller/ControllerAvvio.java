package Controller;

import DAO.*;
import GUI.GUImain;

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
    }

}
