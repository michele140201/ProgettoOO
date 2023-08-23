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

    /**
     * Metodo che ritorna il nome
     * inserito dall'utente
     * @return
     */

    public String getProgetto() {
        return nomeTextField.getText();
    }

    /**
     * Metodo che pulisce il campo nome
     */

    public void clear() {
        nomeTextField.setText(" ");
    }
}
