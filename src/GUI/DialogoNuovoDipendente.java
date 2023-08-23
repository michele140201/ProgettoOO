package GUI;

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

    private SpinnerNumberModel YearModel;
    private SpinnerNumberModel GiorniModel28;
    private SpinnerNumberModel GiorniModel30;
    private SpinnerNumberModel GiorniModel31;
    private final int currentYear = LocalDate.now().getYear();

    public DialogoNuovoDipendente(){
        inizializzaInserimentoDipendente();

        mesiNascita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceltaMese();
            }
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

    /**
     * Metodo che inizializza il
     * form di assunzione di un nuovo
     * dipendente
     */

    private void inizializzaInserimentoDipendente() {
        add(Assumi);
        for (Mese mese : Mese.values()) {
            mesiNascita.addItem(mese);

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

    /**
     * Metodo che ritorna la data relativa
     * al giorno, al mese e all'anno che
     * gli vengono passati
     * @param Giorno
     * @param mese
     * @param Anno
     * @return
     */

    private java.sql.Date converti(int Giorno, Mese mese, int Anno) {
        return new java.sql.Date(Anno, mese.ordinal() , Giorno);
    }

    /**
     * Metodo che ritorna il nome
     * inserito dall'utente
     * @return
     */

    public String getNome() {
        return nomeInseritoTextField.getText();
    }

    /**
     * Metodo che ritorna il cognome
     * inserito dall' utente
     * @return
     */

    public String getCognome(){
        return cognomeInseritoTextField.getText();
    }

    /**
     * Metodo che ritorna se il
     * nuovo dipendente deve essere assunto
     * come dirigente o meno
     * @return
     */

    public boolean getDirigente(){
        return dirigenteBox.getSelectedItem().equals("SI");

    }

    /**
     * Metodo che converte i dati inseriti dall'utente
     * relativi alla data di nascita del dipendente
     * in una data
     * @return
     */

    public Date getDataNascita(){
        return converti((int)GiornoNascita.getValue(), (Mese) mesiNascita.getSelectedItem(),(int) AnnoNascita.getValue() - 1900);
    }


    /**
     * Metodo che rimuove tutti i dati
     * all'interno del campo nome e cognome
     */

    public void clear(){
        nomeInseritoTextField.setText(null);
        cognomeInseritoTextField.setText(null);
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
