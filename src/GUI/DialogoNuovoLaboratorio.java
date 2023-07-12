package GUI;

import Model.Laboratorio;

import javax.swing.*;

public class DialogoNuovoLaboratorio extends JPanel {
    private JTextField nomeLaboratorioTextField;
    private JTextField nomeJTextField;
    private JTextField topicTextField;
    private JTextField creaLaboratorioTextField;
    private JComboBox <Laboratorio.Topic> topicJComboBox;
    private JPanel panel1;

    /**
     * Creazione della finestra di inserimento di un nuovo laboratorio
     */
    public DialogoNuovoLaboratorio() {
        add(panel1);
        setSize(500, 300);
/**
 * Aggiunge alla combo box tutti i topic nel sistema
 */
        for (Laboratorio.Topic topic: Laboratorio.Topic.values()) {
            topicJComboBox.addItem(topic);
        }

    }
    public Laboratorio getLaboratorio(){
        return new Laboratorio(nomeJTextField.getText() , (Laboratorio.Topic)topicJComboBox.getSelectedItem());
    }

    public void clear() {
        nomeJTextField.setText(" ");
    }
}
