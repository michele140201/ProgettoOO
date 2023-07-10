package GUI;

import Model.Progetto;

import javax.swing.*;

public class Nuovo_Prog extends JPanel {
    private JTextField inserisciNomeProgettoMAXTextField;
    private JPanel panel1;
    public JTextField textField2;
    private JTextField inserisciIlTopicMAXTextField;
    private JTextField textField4;
    private JTextField inserimentoNuovoProgettoTextField;
    public JComboBox TopicBox;
    private String[] topic = {"chimica" , "fisica" , "matematica" , "informatica" , "biologia"};
    public int c = 0;
    private Progetto p = new Progetto();

    /**
     * Creazione della finestra di inserimento di un nuovo Progetto
     */
    public Nuovo_Prog(){
        add(panel1);
        setSize(500,300);

        for(int i = 0 ; i < topic.length ; i++){
            TopicBox.addItem(topic[i]);
        }


    }
}
