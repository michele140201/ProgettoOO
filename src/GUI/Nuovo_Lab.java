package GUI;

import javax.swing.*;

public class Nuovo_Lab extends JPanel {
    private JTextField nomeLaboratorioTextField;
    private JTextField Nomelabor;
    private JTextField topicTextField;
    private JTextField creaLaboratorioTextField;
    private JComboBox topiccombo;
    private JPanel panel1;
    private final String[] topic = {"chimica", "fisica", "matematica", "informatica", "biologia"};

    /**
     * Creazione della finestra di inserimento di un nuovo laboratorio
     */
    public Nuovo_Lab() {
        add(panel1);
        setSize(500, 300);
/**
 * Aggiunge alla combo box tutti i topic nel sistema
 */
        for (int i = 0; i < topic.length; i++) {
            topiccombo.addItem(topic[i]);
        }

    }

    public String getTopicElement() {
        String Topic = (String) topiccombo.getSelectedItem();
        return Topic;
    }

    public String getNomeLab() {
        String NomeLab = Nomelabor.getText();
        return NomeLab;
    }

    public void setText() {
        Nomelabor.setText(" ");
    }
}
