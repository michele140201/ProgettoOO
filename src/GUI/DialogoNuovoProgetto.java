package GUI;

import javax.swing.*;

/**
 * Classe che implementa la finestra di dialogo per inserire un nuovo progetto
 */

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
     * @return nome progetto inserito
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
