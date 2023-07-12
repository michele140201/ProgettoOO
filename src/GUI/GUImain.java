package GUI;

import Controller.ControllerMainPage;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.sql.Date;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GUImain extends JFrame {
    private int currentYear = LocalDate.now().getYear();
    private SpinnerNumberModel YearModel;
    private SpinnerNumberModel GiorniModel28;
    private SpinnerNumberModel GiorniModel30;
    private SpinnerNumberModel GiorniModel31;
    private final JComboBox Lab;
    private final DialogoNuovoProgetto InterfacciaProgetto = new DialogoNuovoProgetto();
    private final JButton AssegnaReferenteProgettiButton = new JButton("Inserisci");
    private final JButton InserimentoProgettoButton = new JButton("Inserisci");
    private final JButton CreaNuovoLaboratorioButton = new JButton("Inserisci");
    private final DialogoNuovoLaboratorio nuovoLab = new DialogoNuovoLaboratorio();
    private final JComboBox ComboBox;
    private final JButton AssegnazioneProgettoButton = new JButton("Conferma");
    private final JButton ConfermaResponsabileButton = new JButton("Conferma");
    String[] ValoriDirigenteBox = {"NO", "SI"};
    String[] Mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
    private JTabbedPane PannelloPrincipale;
    private JPanel Assumi;
    private JPanel Visual;
    private JPanel Progetti;
    private JPanel Laboratori;
    private JTextField dirigenteTextField;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField CognomeInserito;
    private JComboBox MesiNascita;
    private JComboBox DirigenteBox;
    private JButton assumiButton;
    private JSpinner GiornoNascita;
    private JTextField giornoDiNascitaTextField;
    private JTextField meseDiNascitaTextField;
    private JTextField annoDiNascitaTextField;
    private JSpinner AnnoNascita;
    private JTextField NomeInserito;
    private JButton mostraDipendentiNonAssegnatiButton;
    private JButton mostraTuttiDipendentiButton;
    private JTable tabellaDipendenti;
    private JScrollPane PannelloDipendenti;
    private JScrollPane PannelloProgetti;
    private JButton licenziaButton;
    private JButton assegnaButton;
    private JButton promuoviButton;
    private JButton degradaButton;
    private JButton visualizzaCarrieraButton;
    private JTable tabellaProgetti;
    private JButton nuovoProgettoButton;
    private JButton eliminaProgettoButton;
    private boolean Dir;
    private JTable tabellaLaboratori;
    private JScrollPane PannelloLaboratori;
    private JButton nuovoLaboratorioButton;
    private JButton eliminaSedeButton;
    private JButton assegnaProgettoButton;
    private JButton assegnaReferenteButton;
    private JButton assegnaResponsabileButton;
    private JButton DialogoReferenteProgettiButton;
    private ControllerMainPage controllerMainPage;
    private JDialog dialogoAssegnazioneDipendenteLaboratorio;

    public GUImain(ControllerMainPage controllerMainPage) {
        this.controllerMainPage = controllerMainPage;

        setContentPane(PannelloPrincipale);
        setSize(1000, 300);
        setLocationRelativeTo(null);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inizializzaInserimentoDipendente();
        inizializzaTabellaDipendenti();
        inizializzaTabellaLaboratori();
        inizializzaTabellaProgetti();
        inizializzaDialogoAssegnazioneDipendenteLaboratorio();
        inizializzaPulsantiGrado();
        inizializzaFormAssunzione();

        Lab = new JComboBox<>();


        JDialog dialogoInserimentoProgetto = new JDialog();
        dialogoInserimentoProgetto.setLayout(new BorderLayout());
        dialogoInserimentoProgetto.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogoInserimentoProgetto.add(InterfacciaProgetto);
        dialogoInserimentoProgetto.add(InserimentoProgettoButton, BorderLayout.PAGE_END);
        dialogoInserimentoProgetto.pack();
        dialogoInserimentoProgetto.setLocationRelativeTo(null);

        JDialog DialogoNuovoLaboratorio = new JDialog();
        DialogoNuovoLaboratorio.setLayout(new BorderLayout());
        DialogoNuovoLaboratorio.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        DialogoNuovoLaboratorio.add(nuovoLab);
        DialogoNuovoLaboratorio.add(CreaNuovoLaboratorioButton, BorderLayout.PAGE_END);
        DialogoNuovoLaboratorio.pack();
        DialogoNuovoLaboratorio.setLocationRelativeTo(null);

        JButton ConfermaReferenteButton = new JButton("Conferma");
        JDialog dialogoAssegnazioneReferente = creaDialogo(ConfermaReferenteButton, "Quale referente vuoi assegnare?");

        ComboBox = new JComboBox<>();
        JDialog DialogoAssegnazioneProgetto = creaDialogo(AssegnazioneProgettoButton, "Quale progetto vuoi assegnare?");

        JDialog dialogoProgetti = creaDialogo(AssegnaReferenteProgettiButton, "Assegna Referente");

        JDialog dialogoAssegnazioneResponabile = creaDialogo(ConfermaResponsabileButton, "Assegna Responsabile");

        MesiNascita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SceltaMese();
            }
        });
        DirigenteBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DirigFunction();
            }
        });

        //Action Listener del Pannello Visual
        licenziaButton.addActionListener(event-> controllerMainPage.licenziaDipendente(getDipendenteSelezionato()));




        mostraTuttiDipendentiButton.addActionListener(event-> {
            List<Dipendente> dipendenti = controllerMainPage.getDipendentiAssegnare();
            getModelloDipendenti().setDipendenti(dipendenti);
        });

        mostraDipendentiNonAssegnatiButton.addActionListener(event-> {
            int i = 0;
            ModelloDipendenti modello = getModelloDipendenti();
            while (i < modello.getRowCount()) {
                Dipendente dipendente = modello.getDipendente(i);
                if (dipendente.haLaboratorioAssegnato()) {
                    modello.rimuoviDipendente(dipendente);
                } else {
                    i++;
                }
            }
        });

        visualizzaCarrieraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonVisualizzaCarriera();
            }
        });

        //ACTION LISTENER DI PROGETTO
        nuovoProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogoInserimentoProgetto.setVisible(true);
            }
        });
        eliminaProgettoButton.addActionListener(event-> {
                Progetto progetto = getProgettoSelezionato();
                controllerMainPage.EliminaProgetto(progetto);
        });
        InserimentoProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonInserimentoProgetto();
            }
        });
        //ACTION LISTENER DI LABORATORIO
        nuovoLaboratorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoNuovoLaboratorio.setVisible(true);
            }
        });
        CreaNuovoLaboratorioButton.addActionListener(event-> {
                Laboratorio laboratorio = nuovoLab.getLaboratorio();
                controllerMainPage.creaLaboratorio(laboratorio);
        });
        eliminaSedeButton.addActionListener(event-> {
                Laboratorio laboratorio = getLaboratorioSelezionato();
                controllerMainPage.rimuovoLaboratorio(laboratorio);;
        });
        assegnaReferenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnaReferente();
            }
        });
        ConfermaReferenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente dipendente = (Dipendente) ComboBox.getSelectedItem();
                controllerMainPage.riassegnaDipendente(getLaboratorioSelezionato(), dipendente);
            }
        });
        AssegnazioneProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnazioneProgetto(DialogoAssegnazioneProgetto);
            }
        });
        DialogoReferenteProgettiButton.addActionListener(event->{
                ComboBox.removeAllItems();
                Progetto progetto = getProgettoSelezionato();
                List<Dipendente> dipendenti = controllerMainPage.getReferenti(progetto);
                for (Dipendente dipendente : dipendenti) {
                    ComboBox.addItem(dipendente);
                }
        });
        assegnaProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnaProgettoLaboratorio(DialogoAssegnazioneProgetto);
            }
        });
        AssegnaReferenteProgettiButton.addActionListener(event-> {
                Dipendente dipendente = (Dipendente) ComboBox.getSelectedItem();
                controllerMainPage.setReferente(dipendente , getProgettoSelezionato());
        });
        assegnaResponsabileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnaResponsabile(dialogoAssegnazioneResponabile);
            }
        });
        ConfermaResponsabileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente dipendente =(Dipendente) ComboBox.getSelectedItem();
                controllerMainPage.setResponsabile(dipendente, getProgettoSelezionato());
            }
        });
    }


    private JTable creaTabella(JScrollPane tab, TableModel modello, String nome) {
        JTable tabella = new JTable(modello) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabella.setName(nome);
        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabella.setFocusable(false);
        tabella.setFillsViewportHeight(true);
        tabella.setPreferredScrollableViewportSize(tabella.getPreferredSize());
        tab.add(tabella.getTableHeader());
        tab.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tab.setBounds(446, 50, 450, 490);
        tab.setViewportView(tabella);
        tab.setVisible(true);
        return tabella;
    }

    private JDialog creaDialogo(JButton conferma, String domanda) {
        JDialog dialogo = new JDialog();
        dialogo.setLocationRelativeTo(null);
        dialogo.setModal(true);
        dialogo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(300, 150);
        dialogo.setResizable(false);
        dialogo.add(new JLabel(domanda), BorderLayout.PAGE_START);
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        return dialogo;
    }

    public void ButtonAssegnaProgettoLaboratorio(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = tabellaLaboratori.getSelectedRow();
        String NomeLab = (String) tabellaLaboratori.getValueAt(row, 0);
        String Topic = (String) tabellaLaboratori.getValueAt(row, 1);
        List<Progetto> Progetti = getProgettiIdonei();
        int i = 0;
        if (Progetti.size() > 0) {
            while (i < Progetti.size()) {
                Progetto p = Progetti.get(i);
                ComboBox.addItem(p.getCup() + " " + p.getNome());
                i++;
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nessun Progetto con lo stesso Topic");
        }
    }

    public void ButtonAssegnaReferente() {
        ComboBox.removeAllItems();
        int row = tabellaLaboratori.getSelectedRow();
        System.out.println(row);
        String NomeLab = (String) tabellaLaboratori.getValueAt(row, 0);
        List<Dipendente> dipendenti = controllerMainPage.getSenior(NomeLab);
            for (Dipendente dipendente : dipendenti) {
                ComboBox.addItem(dipendente);
            }
    }

    /**
     * Funzione del bottone per visualizzare gli scatti di carriera di un dipendente
     */
    public void ButtonVisualizzaCarriera() {
        int row = tabellaDipendenti.getSelectedRow();
        String DataA = (String) tabellaDipendenti.getValueAt(row, 6);
        String Nome = (String) tabellaDipendenti.getValueAt(row, 0);
        String Cognome = (String) tabellaDipendenti.getValueAt(row, 1);
        Date A = Date.valueOf(DataA);
        Date ora = Date.valueOf(LocalDate.now());
        long diff = ora.getTime() - A.getTime();
        long ORE = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        long Anni = ORE / 365;
        LocalDate prova = A.toLocalDate();
        prova = prova.plusYears(3);
        LocalDate senior = A.toLocalDate();
        senior = senior.plusYears(7);
        Date dataCambio = controllerMainPage.getDataPromozione(Integer.valueOf(tabellaDipendenti.getValueAt(row, 2).toString()));
        if (dataCambio != null) {
            if (Anni < 3) {
                JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDirigente : " + dataCambio);
            } else if (Anni >= 3 && Anni < 7) {
                JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDipendente Middle : " + prova + "\nDirigente : " + dataCambio);

            } else {
                JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDipendente Middle : " + prova + "\nDipendente Senior : " + senior + "\nDirigente : " + dataCambio);

            }
        } else {
            if (Anni < 3) {
                JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA);
            } else if (Anni >= 3 && Anni < 7) {
                JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDipendente Middle : " + prova);

            } else {
                JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDipendente Middle : " + prova + "\nDipendente Senior : " + senior);

            }
        }


    }


    public void ButtonInserimentoProgetto() {
        Progetto progetto = InterfacciaProgetto.getProgetto();
        controllerMainPage.inserisciProgetto(progetto);
        InterfacciaProgetto.clear();
    }

    /**
     * Funzione del bottone per l'assegnazione di un progetto ad un laboratorio
     *
     * @param dialogo
     */
    public void ButtonAssegnazioneProgetto(JDialog dialogo) {
       Progetto progetto = (Progetto) ComboBox.getSelectedItem();
        controllerMainPage.assegnaProgetto(getLaboratorioSelezionato(), progetto);
    }

    /**
     * Funzione del bottone per aprire la finestra di assegnazione del responsabile di progetto
     *
     * @param dialogo
     */
    public void ButtonAssegnaResponsabile(JDialog dialogo) {
        ComboBox.removeAllItems();
        List<Dipendente> dipendenti = controllerMainPage.getResponsabiliPossibili(getProgettoSelezionato());
            for (Dipendente dipendente : dipendenti) {
                ComboBox.addItem(dipendente);
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
    }

    /**
     * Funzione per vedere se un dipendente lo si vuole assumere come dirigente oppure no
     */
    public void DirigFunction() {
        String bool = (String) DirigenteBox.getSelectedItem();
        Dir = bool.equals("SI");
    }

    /**
     * bottone per l'apertura della schermata di dialogo
     * per assegnare un dipendente ad un laboratorio
     *
     * @param dialog
     */
    private void apriDialogoAssegnaDipendenteLaboratorio(JDialog dialog) {
        Lab.removeAllItems();
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        if (laboratori.size() > 0) {
            for (Laboratorio laboratorio: laboratori) {
                Lab.addItem(laboratorio);
            }
            dialog.add(Lab, BorderLayout.CENTER);
            dialog.setVisible(true);
        } else {
            showErrorMessage("Nessun laboratorio disponibile");
        }

    }

    public java.sql.Date Converti(int Giorno, String Mese, int Anno) {
        int month = ConvertiInNumero(Mese);
        java.sql.Date gdn = new java.sql.Date(Anno, month, Giorno);
        return gdn;
    }

    public int ConvertiInNumero(String mese) {
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

    public void SceltaMese() {
        int i = 0;
        String MeseScelto = (String) MesiNascita.getSelectedItem();
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

    private void inizializzaTabellaDipendenti() {
        ModelloDipendenti modello = new ModelloDipendenti();
        tabellaDipendenti = creaTabella(PannelloDipendenti, modello, "Dipendenti");
    }

    private void inizializzaTabellaLaboratori(){
        ModelloLaboratori modello = new ModelloLaboratori();
        tabellaLaboratori = creaTabella(PannelloLaboratori, modello, "Laboratori");
    }
    private void inizializzaTabellaProgetti(){
        ModelloProgetti modello = new ModelloProgetti();
        tabellaProgetti = creaTabella(PannelloProgetti, modello, "Progetti");
    }

    private void inizializzaInserimentoDipendente(){
        for (int i = 0; i < 12; i++) {
            MesiNascita.addItem(Mesi[i]);

        }
        DirigenteBox.setEditable(true);
        DirigenteBox.addItem(ValoriDirigenteBox[0]);
        DirigenteBox.addItem(ValoriDirigenteBox[1]);
        YearModel = new SpinnerNumberModel(currentYear - 30, currentYear - 80, currentYear - 18, 1);
        AnnoNascita.setModel(YearModel);
        GiorniModel28 = new SpinnerNumberModel(1, 1, 28, 1);
        GiorniModel30 = new SpinnerNumberModel(1, 1, 30, 1);
        GiorniModel31 = new SpinnerNumberModel(1, 1, 31, 1);
        GiornoNascita.setModel(GiorniModel31);
    }

    public void setDipendenti(List<Dipendente> dipendenti){

        getModelloDipendenti().setDipendenti(dipendenti);
    }
    public void aggiungiDipendente(Dipendente dipendente){
        getModelloDipendenti().aggiungiDipendente(dipendente);
    }
    public void rimuoviDipendente(Dipendente dipendente){
        getModelloDipendenti().rimuoviDipendente(dipendente);
    }

    public void aggiungiLaboratorio(Laboratorio laboratorio){
        getModelloLaboratori().aggiungiLaboratorio(laboratorio);
    }

    private void inizializzaDialogoAssegnazioneDipendenteLaboratorio(){
        JButton buttonConferma = new JButton("Conferma");
        buttonConferma.addActionListener(event->{
            String NuovoLab = (String) Lab.getSelectedItem();
            Dipendente dipendente = getDipendenteSelezionato();
            controllerMainPage.AssegnaLaboratorio(dipendente, NuovoLab);
            getModelloDipendenti().fireTableDataChanged();
            dialogoAssegnazioneDipendenteLaboratorio.setVisible(false);
                });
        dialogoAssegnazioneDipendenteLaboratorio = creaDialogo( buttonConferma, "A quale laboratorio vuoi assegnarlo?");
        assegnaButton.addActionListener(event-> apriDialogoAssegnaDipendenteLaboratorio(dialogoAssegnazioneDipendenteLaboratorio));
    }

    private ModelloDipendenti getModelloDipendenti(){
        return (ModelloDipendenti) tabellaDipendenti.getModel();
    }

    private ModelloLaboratori getModelloLaboratori(){
        return (ModelloLaboratori) tabellaLaboratori.getModel();
    }

    private ModelloProgetti getModelloProgetti(){
        return(ModelloProgetti) tabellaProgetti.getModel();
    }

    private Dipendente getDipendenteSelezionato(){
        ModelloDipendenti modello = getModelloDipendenti();
        return modello.getDipendente(tabellaDipendenti.getSelectedRow());
    }

    private Laboratorio getLaboratorioSelezionato(){
        ModelloLaboratori modello = getModelloLaboratori();
        return modello.getLaboratorio(tabellaLaboratori.getSelectedRow());
    }

    private Progetto getProgettoSelezionato(){
        ModelloProgetti modello = getModelloProgetti();
        return modello.getProgetto(tabellaProgetti.getSelectedRow());
    }


    public void showErrorMessage(String messaggio){
        JOptionPane.showMessageDialog(this , messaggio);
    }
    public void showInfoMessage(String messaggio){
        JOptionPane.showMessageDialog(this , messaggio);
    }

    private void inizializzaPulsantiGrado(){
        promuoviButton.addActionListener(event-> {
            Dipendente dipendente = getDipendenteSelezionato();
            controllerMainPage.promuovi(dipendente);
            getModelloDipendenti().fireTableDataChanged();
        });

        degradaButton.addActionListener(event-> {
            Dipendente dipendente = getDipendenteSelezionato();
            controllerMainPage.degrada(dipendente);
            getModelloDipendenti().fireTableDataChanged();
        });
    }

    private void inizializzaFormAssunzione(){
        assumiButton.addActionListener(event-> {
                String Nome = NomeInserito.getText();
                String Cognome = CognomeInserito.getText();
                String dir = (String) DirigenteBox.getSelectedItem();
                Boolean Dir;
                Dir = dir.equals("SI");
                Date data = Date.valueOf(LocalDate.now());
                int Giorno = (int) GiornoNascita.getValue();
                String mese = (String) MesiNascita.getSelectedItem();
                int Anno = (int) AnnoNascita.getValue();
                Anno = Anno - 1900;
                Date DatadiN = Converti(Giorno, mese, Anno);
                Dipendente dipendente = new Dipendente(Nome, Cognome, Dir, Date.valueOf(LocalDate.now()), DatadiN);
                controllerMainPage.aggiungiDipendente(dipendente);
                NomeInserito.setText("");
                CognomeInserito.setText("");
            });
    }

    private List<Progetto> getProgettiIdonei(){
        //todo
        return null;
        /*public List<Progetto> getProgettiIdonei(Laboratorio.Topic topic) {
            try {
                int lung = progettoDAO.getNumeroTotale();
                List<Progetto> Progetti = progettoDAO.getProgetti();
                ArrayList<Progetto> ProgettiScelti = new ArrayList<>();
                int i = 0;
                while (i < lung) {
                    Progetto p = Progetti.get(i);
                    if (laboratorioDAO.getNumeroLaboratoriAssegnati(p.getCup()) < 3) {
                        {
                            ProgettiScelti.add(p);
                        }
                    }
                    i++;
                }
                i = 0;
                lung = ProgettiScelti.size();
                return ProgettiScelti;
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Errore di DataBase");
                return null;
            }
        }*/
    }



}


