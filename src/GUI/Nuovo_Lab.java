package GUI;

import javax.swing.*;

public class Nuovo_Lab extends JPanel{
    private JTextField nomeLaboratorioTextField;
    public JTextField Nomelabor;
    private JTextField topicTextField;
    private JTextField creaLaboratorioTextField;
    public JComboBox topiccombo;
    private JPanel panel1;
    private String[] topic = {"chimica" , "fisica" , "matematica" , "informatica" , "biologia"};

    /**
     * Creazione della finestra di inserimento di un nuovo laboratorio
     */
    public Nuovo_Lab(){
        add(panel1);
        setSize(500,300);
/**
 * Aggiunge alla combo box tutti i topic nel sistema
 */
        for(int i = 0 ; i < topic.length ; i++){
            topiccombo.addItem(topic[i]);
        }


    }
}
