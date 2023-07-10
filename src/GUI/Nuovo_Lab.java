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
    public Nuovo_Lab(){
        add(panel1);
        setSize(500,300);

        for(int i = 0 ; i < topic.length ; i++){
            topiccombo.addItem(topic[i]);
        }


    }
}
