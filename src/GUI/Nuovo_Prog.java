package GUI;

import Model.Progetto;

import javax.swing.*;

public class Nuovo_Prog extends JPanel {
    public int c = 0;
    private JTextField inserisciNomeProgettoMAXTextField;
    private JPanel panel1;
    private JTextField InserisciProgetto;
    private JTextField inserisciIlTopicMAXTextField;
    private JTextField textField4;
    private JTextField inserimentoNuovoProgettoTextField;
    private JComboBox TopicBox;
    private final String[] topic = {"chimica", "fisica", "matematica", "informatica", "biologia"};
    private final Progetto p = new Progetto();

    /**
     * Creazione della finestra di inserimento di un nuovo Progetto
     */
    public Nuovo_Prog() {
        add(panel1);
        setSize(500, 300);

        for (int i = 0; i < topic.length; i++) {
            TopicBox.addItem(topic[i]);
        }
    }

    public String getTopicElement() {
        String Topic = (String) TopicBox.getSelectedItem();
        return Topic;
    }

    public String getNomeProgetto() {
        String NomeProgetto = InserisciProgetto.getText();
        return NomeProgetto;
    }

    public void setText() {
        InserisciProgetto.setText(" ");
    }
}
