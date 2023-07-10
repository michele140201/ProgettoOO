package Controller;

import GUI.GUImain;

public class GUIController {
    private GUImain guimain;
    public void GUIController(){
        guimain = new GUImain();
        guimain.setVisible(true);
    }
}
