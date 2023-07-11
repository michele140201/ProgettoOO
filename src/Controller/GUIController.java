package Controller;

import GUI.GUImain;
import DAO.*;

public class GUIController {
    private GUImain guimain;
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();
    private final DipendenteDAO dipendenteDAO = new DipendenteDAOimpl();
    private final LaboratorioDAO laboratorioDAO = new LaboratorioDAOImpl();
    private final CambioRuoloDAO cambioRuoloDAO = new CambioRuoloDAOImpl();

    /**
     * Controller che serve ad avviare l'interfaccia grafica
     */
    public void GUIController() {
        guimain = new GUImain(progettoDAO, dipendenteDAO, laboratorioDAO, cambioRuoloDAO);
        guimain.setVisible(true);
    }
}
