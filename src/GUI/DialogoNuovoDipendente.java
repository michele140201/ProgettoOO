package GUI;

import Model.Dipendente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

public class DialogoNuovoDipendente extends JPanel {
    private JPanel Assumi;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField dirigenteTextField;
    private JComboBox dirigenteBox;
    private JTextField cognomeInseritoTextField;
    private JTextField giornoDiNascitaTextField;
    private JTextField meseDiNascitaTextField;
    private JTextField annoDiNascitaTextField;
    private JComboBox mesiNascita;
    private JSpinner GiornoNascita;
    private JSpinner AnnoNascita;
    private JTextField nomeInseritoTextField;
    String[] Mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
    private SpinnerNumberModel YearModel;
    private SpinnerNumberModel GiorniModel28;
    private SpinnerNumberModel GiorniModel30;
    private SpinnerNumberModel GiorniModel31;
    private final int currentYear = LocalDate.now().getYear();

    public DialogoNuovoDipendente(){
        add(Assumi);
        inizializzaInserimentoDipendente();
        mesiNascita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceltaMese();
            }
        });
    }
    public void sceltaMese() {
        int i = 0;
        String MeseScelto = (String) mesiNascita.getSelectedItem();
        if (MeseScelto.equals("Febbraio")) {
            i = (int) GiornoNascita.getValue();
            if (i > 28) i = 28;
            GiornoNascita.setModel(GiorniModel28);
            GiorniModel28.setValue(i);
        } else if (MeseScelto.equals("Novembre") || MeseScelto.equals("Giugno") || MeseScelto.equals("Aprile") || MeseScelto.equals("Settembre")) {
            i = (int) GiornoNascita.getValue();
            if (i > 30) i = 30;
            GiornoNascita.setModel(GiorniModel30);
            GiorniModel30.setValue(i);
        } else {
            GiornoNascita.setModel(GiorniModel31);
        }
    }

    private void inizializzaInserimentoDipendente() {
        for (int i = 0; i < 12; i++) {
            mesiNascita.addItem(Mesi[i]);

        }
        dirigenteBox.setEditable(true);
        dirigenteBox.addItem("NO");
        dirigenteBox.addItem("SI");
        YearModel = new SpinnerNumberModel(currentYear - 30, currentYear - 80, currentYear - 18, 1);
        AnnoNascita.setModel(YearModel);
        GiorniModel28 = new SpinnerNumberModel(1, 1, 28, 1);
        GiorniModel30 = new SpinnerNumberModel(1, 1, 30, 1);
        GiorniModel31 = new SpinnerNumberModel(1, 1, 31, 1);
        GiornoNascita.setModel(GiorniModel31);
    }

    public String getNome() {
        return nomeInseritoTextField.getText();
    }

    public String getCognome(){
        return cognomeInseritoTextField.getText();
    }

    public boolean getDirigente(){
        return dirigenteBox.getSelectedItem().equals("SI");

    }

    public Date getDataNascita(){
        Date datadiN = converti((int)GiornoNascita.getValue(), (String) mesiNascita.getSelectedItem(),(int) AnnoNascita.getValue() - 1900);
        return datadiN;
    }


    public int convertiInNumero(String mese) {
        int i = 0;
        switch (mese) {
            case "Gennaio":
                i = 0;
                break;
            case "Febbraio":
                i = 1;
                break;
            case "Marzo":
                i = 2;
                break;
            case "Aprile":
                i = 3;
                break;
            case "Maggio":
                i = 4;
                break;
            case "Giugno":
                i = 5;
                break;
            case "Luglio":
                i = 6;
                break;
            case "Agosto":
                i = 7;
                break;
            case "Settembre":
                i = 8;
                break;
            case "Ottobre":
                i = 9;
                break;
            case "Novembre":
                i = 10;
                break;
            case "Dicembre":
                i = 11;
                break;
        }
        return i;
    }

    public java.sql.Date converti(int Giorno, String Mese, int Anno) {
        int month = convertiInNumero(Mese);
        java.sql.Date gdn = new java.sql.Date(Anno, month, Giorno);
        return gdn;
    }

    public void clear(){
        nomeInseritoTextField.setText(null);
        cognomeInseritoTextField.setText(null);
    }

}
