package GUI;

import Model.Progetto;

import javax.swing.*;

public class DialogoNuovoProgetto extends JPanel {
    private JTextField inserisciNomeProgettoMAXTextField;
    private JPanel panel1;
    private JTextField nomeTextField;
    private JTextField textField4;
    private JTextField inserimentoNuovoProgettoTextField;

    /**
     * Creazione della finestra di inserimento di un nuovo Progetto
     */
    public DialogoNuovoProgetto() {
        add(panel1);
        setSize(500, 300);
    }
    public Progetto getProgetto(){
        return new Progetto(nomeTextField.getText());
    }

    public void clear() {
        nomeTextField.setText(" ");
    }
}
