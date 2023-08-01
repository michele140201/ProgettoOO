package GUI;

import Model.Laboratorio;

import javax.swing.*;

public class DialogoNuovoLaboratorio extends JPanel {
    private JTextField nomeLaboratorioTextField;
    private JTextField nomeJTextField;
    private JTextField topicTextField;
    private JTextField creaLaboratorioTextField;
    private JComboBox<Laboratorio.Topic> topicJComboBox;
    private JPanel pannello;

    public DialogoNuovoLaboratorio() {
        add(pannello);
        setSize(500, 300);
        for (Laboratorio.Topic topic : Laboratorio.Topic.values()) {
            topicJComboBox.addItem(topic);
        }

    }

    public String getNome(){
        return nomeJTextField.getText();
    }

    public Laboratorio.Topic getTopic(){
        return (Laboratorio.Topic) topicJComboBox.getSelectedItem();
    }

    public void clear() {
        nomeJTextField.setText(" ");
    }
}
