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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GUImain extends JFrame {
    private final JComboBox Lab;
    private final DialogoNuovoProgetto InterfacciaProgetto = new DialogoNuovoProgetto();
    private final JButton AssegnaReferenteProgettiButton = new JButton("Inserisci");
    private final JButton InserimentoProgettoButton = new JButton("Inserisci");
    private final JButton CreaNuovoLaboratorioButton = new JButton("Inserisci");
    private final DialogoNuovoLaboratorio nuovoLab = new DialogoNuovoLaboratorio();
    private final JComboBox ComboBox;
    private final JButton AssegnazioneProgettoButton = new JButton("Conferma");
    private final JButton ConfermaResponsabileButton = new JButton("Conferma");
    private final int currentYear = LocalDate.now().getYear();
    private final JComboBox<Laboratorio> LaboratorioComboBox = new JComboBox<>();
    private final JComboBox<Dipendente> referenteProgettoComboBox = new JComboBox<>();
    String[] ValoriDirigenteBox = {"NO", "SI"};
    String[] Mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
    private SpinnerNumberModel YearModel;
    private SpinnerNumberModel GiorniModel28;
    private SpinnerNumberModel GiorniModel30;
    private SpinnerNumberModel GiorniModel31;
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
    private JComboBox<Dipendente> responsabileProgettoComboBox = new JComboBox<>();
    private JComboBox<Progetto> progettoLaboratorioComboBox = new JComboBox<>();
    private JComboBox<Dipendente> referenteLaboratorioComboBox = new JComboBox<>();

    public GUImain() {
        setContentPane(PannelloPrincipale);
        setSize(1000, 300);
        setLocationRelativeTo(null);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inizializzaInserimentoDipendente();
        inizializzaTabellaDipendenti();
        inizializzaTabellaLaboratori();
        inizializzaTabellaProgetti();
        inizializzaPulsantiGrado();
        inizializzaFormAssunzione();
        ComboBox = new JComboBox<>();
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

        licenziaButton.addActionListener(event -> controllerMainPage.licenziaDipendente(getDipendenteSelezionato()));


        mostraTuttiDipendentiButton.addActionListener(event -> {
            try {
                controllerMainPage.mostraTuttiDipendenti();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        mostraDipendentiNonAssegnatiButton.addActionListener(event -> {
            rimuoviDipendentiAssegnati();
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
        eliminaProgettoButton.addActionListener(event -> {
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
        CreaNuovoLaboratorioButton.addActionListener(event -> {
            Laboratorio laboratorio = nuovoLab.getLaboratorio();
            controllerMainPage.creaLaboratorio(laboratorio);
        });
        eliminaSedeButton.addActionListener(event -> {
            Laboratorio laboratorio = getLaboratorioSelezionato();
            controllerMainPage.rimuovoLaboratorio(laboratorio);
        });
        assegnaReferenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnaReferente();
            }
        });
        assegnaButton.addActionListener(event -> {
            dialogoAssegnazioneDipendenteLaboratorio = SchermataDialogoAssegnazioneLaboratorioDipendente();
            dialogoAssegnazioneDipendenteLaboratorio.setVisible(true);
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
                JDialog dialogoAssegnazioneProgettoLaboratorio = schermataAssegnazioneProgettiLaboratorio();


            }
        });
        DialogoReferenteProgettiButton.addActionListener(event -> {
            JDialog dialodoAssegnazioneReferente = SchermataDialogoAssegnazioneReferenteProgetto();
            List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
            Progetto progetto = getProgettoSelezionato();
            List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
            if(progetto.getLaboratori().size()>0){
                setComboBoxReferenteProgetto(referenteProgettoComboBox, progetto);
                dialodoAssegnazioneReferente.setVisible(true);
            }else{
                showErrorMessage("Nessun laboratorio assegnato");
            }
        });
        assegnaProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JDialog dialogoAssegnazioneProgettoLaboratorio = schermataAssegnazioneProgettiLaboratorio();
                    setProgettoLaboratorioComboBox(progettoLaboratorioComboBox);
                    dialogoAssegnazioneProgettoLaboratorio.setVisible(true);
            }
        });
        AssegnaReferenteProgettiButton.addActionListener(event -> {

        });
        assegnaResponsabileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogoAssegnazioneResponsabile = SchermataDialogoAssegnazioneResponsabileProgetto();
                List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
                Progetto progetto = getProgettoSelezionato();
                List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
                if(progetto.getLaboratori().size()>0){
                    setResponsabiliProgettoComboBox(responsabileProgettoComboBox , progetto);
                    dialogoAssegnazioneResponsabile.setVisible(true);
                }else{
                    showErrorMessage("Nessun laboratorio assegnato");
                }
            }
        });
        ConfermaResponsabileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente dipendente = (Dipendente) ComboBox.getSelectedItem();
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

    private JDialog creaDialogo() {
        JDialog dialogo = new JDialog();
        dialogo.setLocationRelativeTo(null);
        dialogo.setModal(true);
        dialogo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(300, 150);
        dialogo.setResizable(false);
        dialogo.pack();
        return dialogo;
    }

    public void ButtonAssegnaProgettoLaboratorio(JDialog dialogo) throws Exception {
        ComboBox.removeAllItems();
        controllerMainPage.setProgettiLaboratorioComboBox();
        dialogo.add(ComboBox, BorderLayout.CENTER);
        dialogo.setVisible(true);
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
        Dipendente dipendente = getDipendenteSelezionato();
        Date ora = Date.valueOf(LocalDate.now());
        long differenzaDiDate = ora.getTime() - dipendente.getDataAssunzione().getTime();
        long Anni = (TimeUnit.DAYS.convert(differenzaDiDate, TimeUnit.MILLISECONDS)) / 365;
        Date dataAssunzione = (Date) dipendente.getDataAssunzione();
        LocalDate dataDipendenteMiddle = dataAssunzione.toLocalDate();
        dataDipendenteMiddle = dataDipendenteMiddle.plusYears(3);
        LocalDate senior = dataAssunzione.toLocalDate();
        senior = senior.plusYears(7);
        Date dataCambio = controllerMainPage.getDataPromozione(dipendente);
        if (dataCambio != null) {
            if (Anni < 3) {
                JOptionPane.showMessageDialog(null, " " + dipendente.getNome() + " " + dipendente.getCognome() + "\nDipendente junior : " + dipendente.getDataAssunzione() + "\nDirigente : " + dataCambio);
            } else if (Anni >= 3 && Anni < 7) {
                JOptionPane.showMessageDialog(null, " " + dipendente.getNome() + " " + dipendente.getCognome() + "\nDipendente junior : " + dipendente.getDataAssunzione() + "\nDipendente Middle : " + dataDipendenteMiddle + "\nDirigente : " + dataCambio);

            } else {
                JOptionPane.showMessageDialog(null, " " + dipendente.getNome() + " " + dipendente.getCognome() + "\nDipendente junior : " + dipendente.getDataAssunzione() + "\nDipendente Middle : " + dataDipendenteMiddle + "\nDipendente Senior : " + senior + "\nDirigente : " + dataCambio);

            }
        } else {
            if (Anni < 3) {
                JOptionPane.showMessageDialog(null, " " + dipendente.getNome() + " " + dipendente.getCognome() + "\nDipendente junior : " + dipendente.getDataAssunzione());
            } else if (Anni >= 3 && Anni < 7) {
                JOptionPane.showMessageDialog(null, " " + dipendente.getNome() + " " + dipendente.getCognome() + "\nDipendente junior : " + dipendente.getDataAssunzione() + "\nDipendente Middle : " + dataDipendenteMiddle);

            } else {
                JOptionPane.showMessageDialog(null, " " + dipendente.getNome() + " " + dipendente.getCognome() + "\nDipendente junior : " + dipendente.getDataAssunzione() + "\nDipendente Middle : " + dataDipendenteMiddle + "\nDipendente Senior : " + senior);

            }
        }


    }

    private void setProgettoLaboratorioComboBox(JComboBox comboBox){
        comboBox.removeAllItems();
        for (Progetto progetto : getModelloProgetti().getProgetti()) {
            if(progetto.getLaboratori().size() < 3){
                comboBox.addItem(progetto);
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
     * Funzione per vedere se un dipendente lo si vuole assumere come dirigente oppure no
     */
    public void DirigFunction() {
        String bool = (String) DirigenteBox.getSelectedItem();
        Dir = bool.equals("SI");
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

    private void inizializzaTabellaLaboratori() {
        ModelloLaboratori modello = new ModelloLaboratori();
        tabellaLaboratori = creaTabella(PannelloLaboratori, modello, "Laboratori");
    }

    private void inizializzaTabellaProgetti() {
        ModelloProgetti modello = new ModelloProgetti();
        tabellaProgetti = creaTabella(PannelloProgetti, modello, "Progetti");
    }

    private void inizializzaInserimentoDipendente() {
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

    public void setDipendenti(List<Dipendente> dipendenti) {

        getModelloDipendenti().setDipendenti(dipendenti);
    }

    public void setLaboratori(List<Laboratorio> laboratori) {
        getModelloLaboratori().setLaboratori(laboratori);
    }

    public void setProgetti(List<Progetto> progetti) {
        getModelloProgetti().setProgetti(progetti);
    }

    public void aggiungiDipendente(Dipendente dipendente) {
        getModelloDipendenti().aggiungiDipendente(dipendente);
    }

    public void rimuoviDipendente(Dipendente dipendente) {
        getModelloDipendenti().rimuoviDipendente(dipendente);
    }

    public void aggiungiLaboratorio(Laboratorio laboratorio) {
        getModelloLaboratori().aggiungiLaboratorio(laboratorio);
    }

    public void rimuoviLaboratorio(Laboratorio laboratorio) {
        getModelloLaboratori().rimuoviLaboratorio(laboratorio);
    }

    public void aggiungiProgetto(Progetto progetto) {
        getModelloProgetti().aggiungiProgetto(progetto);
    }

    public void rimuoviProgetto(Progetto progetto) {
        getModelloProgetti().rimuoviProgetto(progetto);
    }

    private ModelloDipendenti getModelloDipendenti() {
        return (ModelloDipendenti) tabellaDipendenti.getModel();
    }

    private ModelloLaboratori getModelloLaboratori() {
        return (ModelloLaboratori) tabellaLaboratori.getModel();
    }

    private ModelloProgetti getModelloProgetti() {
        return (ModelloProgetti) tabellaProgetti.getModel();
    }

    private Dipendente getDipendenteSelezionato() {
        ModelloDipendenti modello = getModelloDipendenti();
        return modello.getDipendente(tabellaDipendenti.getSelectedRow());
    }

    private Laboratorio getLaboratorioSelezionato() {
        ModelloLaboratori modello = getModelloLaboratori();
        return modello.getLaboratorio(tabellaLaboratori.getSelectedRow());
    }

    private Progetto getProgettoSelezionato() {
        ModelloProgetti modello = getModelloProgetti();
        return modello.getProgetto(tabellaProgetti.getSelectedRow());
    }


    public void showErrorMessage(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio);
    }

    public void showInfoMessage(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio);
    }

    private void inizializzaPulsantiGrado() {
        promuoviButton.addActionListener(event -> {
            Dipendente dipendente = getDipendenteSelezionato();
            controllerMainPage.promuovi(dipendente);
            getModelloDipendenti().fireTableDataChanged();
            getModelloProgetti().fireTableDataChanged();
            getModelloLaboratori().fireTableDataChanged();
        });

        degradaButton.addActionListener(event -> {
            Dipendente dipendente = getDipendenteSelezionato();
            controllerMainPage.degrada(dipendente);
            getModelloDipendenti().fireTableDataChanged();
        });
    }

    private void inizializzaFormAssunzione() {
        assumiButton.addActionListener(event -> {
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


    public void setController(ControllerMainPage controller) {
        this.controllerMainPage = controller;
    }

    public void setComboBoxLaboratorioDipendente(List<Progetto> progetti) {
        for (Progetto progetto : progetti) {
            ComboBox.addItem(progetto);
        }
    }

    private void setComboBoxLaboratorioDipendente(JComboBox comboBox, List<Laboratorio> laboratori) {
        comboBox.removeAllItems();
        for (Laboratorio laboratorio : laboratori) {
            comboBox.addItem(laboratorio);
        }
    }

    private void setComboBoxReferenteProgetto(JComboBox comboBox, Progetto progetto) {
        comboBox.removeAllItems();
        TimeUnit time = TimeUnit.DAYS;
        for (Dipendente dipendente : getModelloDipendenti().getDipendenti()) {
            for (Laboratorio laboratorio : progetto.getLaboratori()) {
                if (dipendente.getLaboratorio().getNome() != null) {
                    if (dipendente.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                        if (time.convert(Date.valueOf(LocalDate.now()).getTime() - dipendente.getDataAssunzione().getTime(), TimeUnit.MILLISECONDS) / 365 >= 7)
                            comboBox.addItem(dipendente);
                    }
                }
            }

        }
}

    private void setComboBoxReferenteLaboratorio(JComboBox comboBox, Laboratorio laboratorio) {
        comboBox.removeAllItems();
        TimeUnit time = TimeUnit.DAYS;
        for (Dipendente dipendente : getModelloDipendenti().getDipendenti()) {
                if (dipendente.getLaboratorio().getNome() != null) {
                    if (dipendente.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                        if (time.convert(Date.valueOf(LocalDate.now()).getTime() - dipendente.getDataAssunzione().getTime(), TimeUnit.MILLISECONDS) / 365 >= 7)
                            comboBox.addItem(dipendente);
                    }
                }
        }
    }

    private JDialog SchermataDialogoAssegnazioneLaboratorioDipendente() {
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Laboratorio vuoi assegnargli"), BorderLayout.PAGE_START);
        List<Laboratorio> laboratory = getModelloLaboratori().getLaboratori();
        setComboBoxLaboratorioDipendente(LaboratorioComboBox, laboratory);
        dialogo.add(LaboratorioComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Laboratorio laboratorio = (Laboratorio) LaboratorioComboBox.getSelectedItem();
            Dipendente dipendente = getDipendenteSelezionato();
            controllerMainPage.AssegnaLaboratorio(dipendente, laboratorio);
            getModelloDipendenti().fireTableDataChanged();
            dialogoAssegnazioneDipendenteLaboratorio.setVisible(false);
        });
        return dialogo;
    }

    private JDialog SchermataDialogoAssegnazioneResponsabileProgetto() {
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Dipendente vuoi rendere responsabile"), BorderLayout.PAGE_START);
        dialogo.add(responsabileProgettoComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Dipendente dipendente = (Dipendente) responsabileProgettoComboBox.getSelectedItem();
            Progetto progetto = getProgettoSelezionato();
            controllerMainPage.setResponsabile(dipendente, progetto);
            getModelloProgetti().fireTableDataChanged();
            dialogo.setVisible(false);
        });
        return dialogo;
    }

    private JDialog SchermataDialogoAssegnazioneReferenteProgetto() {
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Dipendente vuoi rendere referente"), BorderLayout.PAGE_START);
        dialogo.add(referenteProgettoComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Dipendente dipendente = (Dipendente) referenteProgettoComboBox.getSelectedItem();
            Progetto progetto = getProgettoSelezionato();
            controllerMainPage.setReferenteProgetto(dipendente, progetto);
            getModelloProgetti().fireTableDataChanged();
            dialogo.setVisible(false);
        });
        return dialogo;
    }

    private JDialog SchermataDialogoAssegnazioneReferenteLaboratorio() {
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Dipendente vuoi rendere referente"), BorderLayout.PAGE_START);
        dialogo.add(referenteProgettoComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Dipendente dipendente = (Dipendente) referenteLaboratorioComboBox.getSelectedItem();
            Progetto progetto = getProgettoSelezionato();
            controllerMainPage.set(dipendente, progetto);
            getModelloProgetti().fireTableDataChanged();
            dialogo.setVisible(false);
        });
        return dialogo;
    }

    public void aggiornaLaboratorioDipendente(Laboratorio laboratorio, Dipendente dipendente) {
        dipendente.setLaboratorio(laboratorio);
        getModelloDipendenti().fireTableDataChanged();

    }

    public void aggiornaResponsabileProgetto(Progetto progetto , Dipendente dipendente){
        progetto.setResponsabile(dipendente);
        getModelloProgetti().fireTableDataChanged();
    }

    public void aggiornaReferenteProgetto(Progetto progetto, Dipendente dipendente) {

        progetto.setReferente(dipendente);
        getModelloProgetti().fireTableDataChanged();
    }

    public void rimuoviDipendentiAssegnati() {
        getModelloDipendenti().rimuoviDipendentiAssegnati();
    }

    public void setLaboratoriDipendenti() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        for (Dipendente dipendente : dipendenti) {
            for (Laboratorio laboratorio : laboratori) {
                if (laboratorio.getNome() == dipendente.getLaboratorio().getNome()) {
                    dipendente.setLaboratorio(laboratorio);
                }
            }
        }
    }

    public void setProgettiLaboratorio() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        for (Laboratorio laboratorio : laboratori) {
            for (Progetto progetto : progetti) {
                if (progetto.getCup() == laboratorio.getProgetto().getCup()) {
                    laboratorio.setProgetto(progetto);
                }
            }
        }
    }

    public void setReferenteLaboratorio() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        for (Laboratorio laboratorio : laboratori) {
            for (Dipendente dipendente : dipendenti) {
                if (laboratorio.getResponsabile().getId() == dipendente.getId()) {
                    laboratorio.setResponsabile(dipendente);
                }
            }
        }
    }

    public void setReferenteProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        for (Progetto progetto : progetti) {
            for (Dipendente dipendente : dipendenti) {
                if (progetto.getReferente().getId() == dipendente.getId()) {
                    progetto.setReferente(dipendente);
                }
            }
        }
    }

    public void setResponsabiliProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        for (Progetto progetto : progetti) {
            for (Dipendente dipendente : dipendenti) {
                if (progetto.getResponsabile().getId() == dipendente.getId()) {
                    progetto.setResponsabile(dipendente);
                }
            }
        }
    }

    public void setLaboratoriProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        for (Progetto progetto : progetti) {
            List<Laboratorio> laboratoriProgetto = new ArrayList<>();
            for (Laboratorio laboratorio : laboratori) {
                if (progetto.getNome() == laboratorio.getProgetto().getNome()) {
                    laboratoriProgetto.add(laboratorio);
                }
            }
            progetto.setLaboratori(laboratoriProgetto);
        }
    }

    public JDialog schermataAssegnazioneProgettiLaboratorio(){
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Progetto vuoi assegnare"), BorderLayout.PAGE_START);
        dialogo.add(progettoLaboratorioComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Progetto progetto = (Progetto) progettoLaboratorioComboBox.getSelectedItem();
            Laboratorio laboratorio = getLaboratorioSelezionato();
            controllerMainPage.aggiornaProgettoLaboratorio(progetto , laboratorio);
            getModelloProgetti().fireTableDataChanged();
            dialogo.setVisible(false);
        });
        return dialogo;

    }

    public void setResponsabiliProgettoComboBox(JComboBox comboBox , Progetto progetto) {
        comboBox.removeAllItems();
        for (Dipendente dipendente : getModelloDipendenti().getDipendenti()) {
            for (Laboratorio laboratorio : progetto.getLaboratori()) {
                if (dipendente.getLaboratorio().getNome() != null) {
                    if (dipendente.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                        if (dipendente.isDirigente())
                            comboBox.addItem(dipendente);
                    }
                }
            }

        }
    }

    public void aggiornaProgettoLaboratorio(Laboratorio laboratorio , Progetto progetto){
        laboratorio.getProgetto().getLaboratori().remove(laboratorio);
        laboratorio.setProgetto(progetto);
        progetto.addLaboratorio(laboratorio);
        System.out.println(progetto.getLaboratori().size());
        getModelloLaboratori().fireTableDataChanged();
    }
}


