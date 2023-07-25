package GUI;

import Model.Progetto;

import javax.swing.*;

public class DialogoNuovoProgetto extends JPanel {
    private JTextField inserisciNomeProgettoMAXTextField;
    private JPanel pannello;
    private JTextField nomeTextField;
    private JTextField textField4;
    private JTextField inserimentoNuovoProgettoTextField;

    public DialogoNuovoProgetto() {
        add(pannello);
        setSize(500, 300);
    }

    public Progetto getProgetto() {
        return new Progetto(nomeTextField.getText());
    }

    public void clear() {
        nomeTextField.setText(" ");
    }
}
