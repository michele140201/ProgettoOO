package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Classe che implementa la finestra di dialogo per inserire un nuovo dipendente
 */

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
    private JComboBox meseNascita;
    private JSpinner giornoNascita;
    private JSpinner annoNascita;
    private JTextField nomeInseritoTextField;

    private SpinnerNumberModel YearModel;
    private SpinnerNumberModel giorniModel28;
    private SpinnerNumberModel giorniModel30;
    private SpinnerNumberModel giorniModel31;
    private final int currentYear = LocalDate.now().getYear();

    public DialogoNuovoDipendente(){
        inizializzaInserimentoDipendente();
        meseNascita.addActionListener(event -> sceltaMese());
        giornoNascita.addChangeListener(event ->{
            if( (int) giornoNascita.getValue() > 28) sceltaMese();
        });
    }

    /**
     * Metodo che permette in base al mese
     * scelto dall'utente di settare qual è
     * l'ultimo giorno selezionabile:
     * -Se il mese è Febbraio, allora l'ultimo giorno sarà 28
     * -Se il mese ha 30 giorni, allora l'ultimo giorno sarà 30
     * -Se il mese ha 31 giorni, allora avrà 31 giorni
      */

    private void sceltaMese() {
        Mese MeseScelto =(Mese) meseNascita.getSelectedItem();
        if (MeseScelto.toString().equals("Febbraio")) {
            int i = (int) giornoNascita.getValue();
            giornoNascita.setModel(giorniModel28);
            if(i > 28) giornoNascita.setValue(28);
            else giornoNascita.setValue(i);
        } else if (MeseScelto.toString().equals("Novembre") || MeseScelto.toString().equals("Giugno") || MeseScelto.toString().equals("Aprile") || MeseScelto.toString().equals("Settembre")) {
            int i = (int) giornoNascita.getValue();
            if (i > 30) i = 30;
            giornoNascita.setModel(giorniModel30);
            giorniModel30.setValue(i);
        } else {
            int i = (int) giornoNascita.getValue();
            giornoNascita.setModel(giorniModel31);
            giornoNascita.setValue(i);
        }
    }

    /**
     * Metodo che inizializza il
     * form di assunzione di un nuovo
     * dipendente
     */

    private void inizializzaInserimentoDipendente() {
        add(Assumi);
        for (Mese mese : Mese.values()) {
            meseNascita.addItem(mese);

        }
        dirigenteBox.setEditable(true);
        dirigenteBox.addItem("NO");
        dirigenteBox.addItem("SI");
        YearModel = new SpinnerNumberModel(currentYear - 30, currentYear - 80, currentYear - 18, 1);
        annoNascita.setModel(YearModel);
        giorniModel28 = new SpinnerNumberModel(1, 1, 28, 1);
        giorniModel30 = new SpinnerNumberModel(1, 1, 30, 1);
        giorniModel31 = new SpinnerNumberModel(1, 1, 31, 1);
        giornoNascita.setModel(giorniModel31);
    }

    /**
     * Metodo che ritorna la data relativa
     * al giorno, al mese e all'anno che
     * gli vengono passati
     * @param giorno giorno della data
     * @param mese mese della data
     * @param anno anno della data
     * @return data
     */

    private java.sql.Date converti(int giorno, Mese mese, int anno) {
        return new java.sql.Date(anno, mese.ordinal() , giorno);
    }

    /**
     * Metodo che ritorna il nome
     * inserito dall'utente
     * @return nome dipendente inserito
     */

    public String getNome() {
        return nomeInseritoTextField.getText();
    }

    /**
     * Metodo che ritorna il cognome
     * inserito dall' utente
     * @return cognome dipendente   inserito
     */

    public String getCognome(){
        return cognomeInseritoTextField.getText();
    }

    /**
     * Metodo che ritorna se il
     * nuovo dipendente deve essere assunto
     * come dirigente o meno
     * @return se il dipendente è dirigente
     */

    public boolean getDirigente(){
        return dirigenteBox.getSelectedItem().equals("SI");

    }

    /**
     * Metodo che converte i dati inseriti dall'utente
     * relativi alla data di nascita del dipendente
     * in una data
     * @return data di nascita del dipendente
     */

    public Date getDataNascita(){
        return converti((int) giornoNascita.getValue(), (Mese) meseNascita.getSelectedItem(),(int) annoNascita.getValue() - 1900);
    }


    /**
     * Metodo che pulisce i campi della schermata di inserimento
     * in modo da creare un'interfaccia di inserimento nuova
     */

    public void clear(){
        nomeInseritoTextField.setText(null);
        cognomeInseritoTextField.setText(null);
        giorniModel28.setValue(1);
        giorniModel30.setValue(1);
        giorniModel31.setValue(1);
        giornoNascita.setValue(giorniModel31);
        meseNascita.setSelectedIndex(1);
        annoNascita.setValue(currentYear - 30);
    }

    private enum Mese{
        Gennaio,
        Febbraio,
        Marzo,
        Aprile,
        Maggio,
        Giugno,
        Luglio,
        Agosto,
        Settembre,
        Ottobre,
        Novembre,
        Dicembre
    }

}
