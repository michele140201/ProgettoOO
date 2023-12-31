import Controller.*;
import DAO.*;
import GUI.GUImain;

public class Applicazione {
    public static void main(String[] args) throws Exception {
        ConnectionController connectionController = new ConnectionController();
        ProgettoDAO progettoDAO = new ProgettoDAOImpl(connectionController);
        DipendenteDAO dipendenteDAO = new DipendenteDAOimpl(connectionController);
        LaboratorioDAO laboratorioDAO = new LaboratorioDAOImpl(connectionController);
        CambioRuoloDAO cambioRuoloDAO = new CambioRuoloDAOImpl(connectionController);
        GUImain guimain = new GUImain();
        ControllerMainPage controllerMainPage  = new ControllerMainPage(dipendenteDAO, progettoDAO, laboratorioDAO, cambioRuoloDAO , guimain);
        guimain.setController(controllerMainPage);
        guimain.setVisible(true);
    }
}