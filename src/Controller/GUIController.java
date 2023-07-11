package Controller;

import GUI.GUImain;
import DAO.*;

public class GUIController {
    private GUImain guimain;

    /**
     * Controller che serve ad avviare l'interfaccia grafica
     */
    public void GUIController() {
        guimain = new GUImain();
        guimain.setVisible(true);
    }
}
