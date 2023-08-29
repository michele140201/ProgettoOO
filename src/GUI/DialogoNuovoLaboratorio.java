package GUI;

import Model.Laboratorio;
import javax.swing.*;

/**
 * Classe che implementa la finestra di dialogo per inserire un nuovo laboratorio
 */

public class DialogoNuovoLaboratorio extends JPanel {
    private JTextField nomeLaboratorioTextField;
    private JTextField nomeJTextField;
    private JTextField topicTextField;
    private JTextField creaLaboratorioTextField;
    private JComboBox<Laboratorio.Topic> topicJComboBox;
    private JPanel pannello;

    public DialogoNuovoLaboratorio() {
        inizializzaFormLaboratorio();

    }

    /**
     * Metodo che inizializza il form di
     * creazione nuovo laboratorio
     */

    private void inizializzaFormLaboratorio(){
        add(pannello);
        setSize(500, 300);
        for (Laboratorio.Topic topic : Laboratorio.Topic.values()) {
            topicJComboBox.addItem(topic);
        }
    }

    /**
     * Metodo che ritorna il nome
     * inserito dall'utente
     * @return nome laboratorio inserito
     */

    public String getNome(){
        return nomeJTextField.getText();
    }

    /**
     * Metodo che ritorna il topic
     * selezionato dall'utente
     * @return topic laboratorio inserito
     */

    public Laboratorio.Topic getTopic(){
        return (Laboratorio.Topic) topicJComboBox.getSelectedItem();
    }

    /**
     * Metodo che pulisce il campo
     * di inserimento nome
     */

    public void clear() {
        nomeJTextField.setText(" ");
    }
}
