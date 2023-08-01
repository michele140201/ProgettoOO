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
    private final JButton inserimentoProgettoButton = new JButton("Inserisci");
    private final JButton creaNuovoLaboratorioButton = new JButton("Inserisci");
    private final DialogoNuovoLaboratorio interfacciaLaboratorio = new DialogoNuovoLaboratorio();
    private final JComboBox comboBox;
    private final JButton AssegnazioneProgettoButton = new JButton("Conferma");
    private final JButton confermaResponsabileButton = new JButton("Conferma");
    private final int currentYear = LocalDate.now().getYear();
    private final JComboBox<Laboratorio> LaboratorioComboBox = new JComboBox<>();
    private final JComboBox<Dipendente> referenteProgettoComboBox = new JComboBox<>();
    private final JComboBox<Dipendente> responsabileProgettoComboBox = new JComboBox<>();
    private final JComboBox<Progetto> progettoLaboratorioComboBox = new JComboBox<>();
    private final JComboBox<Dipendente> referenteLaboratorioComboBox = new JComboBox<>();
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
    private JTextField cognomeInseritoTextField;
    private JComboBox mesiNascita;
    private JComboBox dirigenteBox;
    private JButton nuovoDipendenteButton;
    private JSpinner GiornoNascita;
    private JTextField giornoDiNascitaTextField;
    private JTextField meseDiNascitaTextField;
    private JTextField annoDiNascitaTextField;
    private JSpinner AnnoNascita;
    private JTextField nomeInseritoTextField;
    private JButton mostraDipendentiNonAssegnatiButton;
    private JButton mostraTuttiDipendentiButton;
    private JTable tabellaDipendenti;
    private JScrollPane PannelloDipendenti;
    private JScrollPane PannelloProgetti;
    private JButton licenziaButton;
    private JButton assegnaLaboratorioDipendenteButton;
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
    private JButton apriFinestraAssegnazioneReferenteLaboratorioButton;
    private JButton assegnaResponsabileButton;
    private JButton dialogoReferenteProgettiButton;
    private ControllerMainPage controllerMainPage;
    private JDialog dialogoAssegnazioneDipendenteLaboratorio;

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
        comboBox = new JComboBox<>();
        Lab = new JComboBox<>();

        JDialog dialogoInserimentoProgetto = new JDialog();
        dialogoInserimentoProgetto.setLayout(new BorderLayout());
        dialogoInserimentoProgetto.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogoInserimentoProgetto.add(InterfacciaProgetto);
        dialogoInserimentoProgetto.add(inserimentoProgettoButton, BorderLayout.PAGE_END);
        dialogoInserimentoProgetto.pack();
        dialogoInserimentoProgetto.setLocationRelativeTo(null);

        JDialog dialogoNuovoLaboratorio = new JDialog();
        dialogoNuovoLaboratorio.setLayout(new BorderLayout());
        dialogoNuovoLaboratorio.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogoNuovoLaboratorio.add(interfacciaLaboratorio);
        dialogoNuovoLaboratorio.add(creaNuovoLaboratorioButton, BorderLayout.PAGE_END);
        dialogoNuovoLaboratorio.pack();
        dialogoNuovoLaboratorio.setLocationRelativeTo(null);

        mesiNascita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sceltaMese();
            }
        });

        dirigenteBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDirigente();
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
                buttonVisualizzaCarriera();
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
            controllerMainPage.eliminaProgetto(progetto);
        });
        inserimentoProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = InterfacciaProgetto.getProgetto();
                controllerMainPage.nuovoProgetto(nome);
                dialogoInserimentoProgetto.setVisible(false);
                InterfacciaProgetto.clear();
            }
        });
        //ACTION LISTENER DI LABORATORIO
        nuovoLaboratorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogoNuovoLaboratorio.setVisible(true);
            }
        });
        creaNuovoLaboratorioButton.addActionListener(event -> {
            dialogoNuovoLaboratorio.setVisible(false);
            String nome = interfacciaLaboratorio.getNome();
            Laboratorio.Topic topic = interfacciaLaboratorio.getTopic();
            controllerMainPage.nuovoLaboratorio(nome, topic);
            interfacciaLaboratorio.clear();
        });
        eliminaSedeButton.addActionListener(event -> {
            Laboratorio laboratorio = getLaboratorioSelezionato();
            controllerMainPage.eliminaLaboratorio(laboratorio);
        });
        apriFinestraAssegnazioneReferenteLaboratorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogoAssegnazioneReferenteLaboratorio = schermataDialogoAssegnazioneReferenteLaboratorio();
                setComboBoxReferenteLaboratorio(referenteLaboratorioComboBox, getLaboratorioSelezionato());
                if (referenteLaboratorioComboBox.getItemCount() > 0) {
                    dialogoAssegnazioneReferenteLaboratorio.setVisible(true);
                } else {
                    showErrorMessage("Nessun dipendente da assegnare come referente");
                }

            }
        });
        assegnaLaboratorioDipendenteButton.addActionListener(event -> {
            dialogoAssegnazioneDipendenteLaboratorio = schermataDialogoAssegnazioneLaboratorioDipendente();
            dialogoAssegnazioneDipendenteLaboratorio.setVisible(true);
        });

        dialogoReferenteProgettiButton.addActionListener(event -> {
            JDialog dialodoAssegnazioneReferente = schermataDialogoAssegnazioneReferenteProgetto();
            Progetto progetto = getProgettoSelezionato();
            if (progetto.getLaboratori().size() > 0) {
                setComboBoxReferenteProgetto(referenteProgettoComboBox, progetto);
                dialodoAssegnazioneReferente.setVisible(true);
            } else {
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
        assegnaResponsabileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogoAssegnazioneResponsabile = schermataDialogoAssegnazioneResponsabileProgetto();
                List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
                Progetto progetto = getProgettoSelezionato();
                List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
                if (progetto.getLaboratori().size() > 0) {
                    setResponsabiliProgettoComboBox(responsabileProgettoComboBox, progetto);
                    dialogoAssegnazioneResponsabile.setVisible(true);
                } else {
                    showErrorMessage("Nessun laboratorio assegnato");
                }
            }
        });
        confermaResponsabileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dipendente dipendente = (Dipendente) comboBox.getSelectedItem();
                controllerMainPage.setResponsabile(dipendente, getProgettoSelezionato());
            }
        });
    }

    /**
     * Funzione per creare una tabella
     *
     * @param tab
     * @param modello
     * @param nome
     * @return
     */

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

    /**
     * funzione per creare una schermata di dialogo
     *
     * @return
     */

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

    /**
     * funzione per visualizzare gli scatti di carriera
     */

    public void buttonVisualizzaCarriera() {
        Dipendente dipendente = getDipendenteSelezionato();
        controllerMainPage.getVisualizzaCarriera(dipendente);
    }

    /**
     * funzione per inserire i progetti nella combobox
     *
     * @param comboBox
     */

    private void setProgettoLaboratorioComboBox(JComboBox comboBox) {
        comboBox.removeAllItems();
        for (Progetto progetto : getModelloProgetti().getProgetti()) {
            if (progetto.getLaboratori().size() < 3) {
                comboBox.addItem(progetto);
            }
        }
    }

    /**
     * funzione per inizializzare la combobox dirigente
     */

    public void setDirigente() {
        String bool = (String) dirigenteBox.getSelectedItem();
        Dir = bool.equals("SI");
    }

    /**
     * funzione per convertire i dati in una data
     *
     * @param Giorno
     * @param Mese
     * @param Anno
     * @return
     */

    public java.sql.Date converti(int Giorno, String Mese, int Anno) {
        int month = convertiInNumero(Mese);
        java.sql.Date gdn = new java.sql.Date(Anno, month, Giorno);
        return gdn;
    }

    /**
     * funzione per convertire i mesi in numeri
     *
     * @param mese
     * @return
     */

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

    /**
     * funzione per aggiornare i giorni dello spinner in base al mese inserito
     */

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

    /**
     * funzione per inizializzare la tabella dipendenti
     */

    private void inizializzaTabellaDipendenti() {
        ModelloDipendenti modello = new ModelloDipendenti();
        tabellaDipendenti = creaTabella(PannelloDipendenti, modello, "Dipendenti");
    }

    /**
     * funzione per inizializzare la tabella laboratori
     */

    private void inizializzaTabellaLaboratori() {
        ModelloLaboratori modello = new ModelloLaboratori();
        tabellaLaboratori = creaTabella(PannelloLaboratori, modello, "Laboratori");
    }

    /**
     * funzione per inizializzare la tabella progetti
     */

    private void inizializzaTabellaProgetti() {
        ModelloProgetti modello = new ModelloProgetti();
        tabellaProgetti = creaTabella(PannelloProgetti, modello, "Progetti");
    }

    /**
     * funzione per inizializzare la schermata di inserimento nuovo dipendente
     */

    private void inizializzaInserimentoDipendente() {
        for (int i = 0; i < 12; i++) {
            mesiNascita.addItem(Mesi[i]);

        }
        dirigenteBox.setEditable(true);
        dirigenteBox.addItem(ValoriDirigenteBox[0]);
        dirigenteBox.addItem(ValoriDirigenteBox[1]);
        YearModel = new SpinnerNumberModel(currentYear - 30, currentYear - 80, currentYear - 18, 1);
        AnnoNascita.setModel(YearModel);
        GiorniModel28 = new SpinnerNumberModel(1, 1, 28, 1);
        GiorniModel30 = new SpinnerNumberModel(1, 1, 30, 1);
        GiorniModel31 = new SpinnerNumberModel(1, 1, 31, 1);
        GiornoNascita.setModel(GiorniModel31);
    }

    /**
     * funzione per inserire i dipendenti nella tabella
     *
     * @param dipendenti
     */

    public void setDipendenti(List<Dipendente> dipendenti) {

        getModelloDipendenti().setDipendenti(dipendenti);
    }

    /**
     * funzione per inserire i laboratori nella tabella
     *
     * @param laboratori
     */

    public void setLaboratori(List<Laboratorio> laboratori) {
        getModelloLaboratori().setLaboratori(laboratori);
    }

    /**
     * funzione per inserire i progetti nella tabella
     *
     * @param progetti
     */

    public void setProgetti(List<Progetto> progetti) {
        getModelloProgetti().setProgetti(progetti);
    }

    /**
     * funzione per aggiungere un dipendente nella tabella
     *
     * @param dipendente
     */

    public void aggiungiDipendente(Dipendente dipendente) {
        getModelloDipendenti().aggiungiDipendente(dipendente);
    }

    /**
     * funzione per rimuovere un dipendente dalla tabella
     *
     * @param dipendente
     */

    public void rimuoviDipendente(Dipendente dipendente) {
        getModelloDipendenti().rimuoviDipendente(dipendente);
        getModelloLaboratori().fireTableDataChanged();
        getModelloProgetti().fireTableDataChanged();
    }

    /**
     * funzione per aggiungere un laboratorio nella tabella
     *
     * @param laboratorio
     */

    public void aggiungiLaboratorio(Laboratorio laboratorio) {
        getModelloLaboratori().aggiungiLaboratorio(laboratorio);
    }

    /**
     * funzione per rimuovere un laboratorio dalla tabella
     *
     * @param laboratorio
     */

    public void rimuoviLaboratorio(Laboratorio laboratorio) {
        getModelloLaboratori().rimuoviLaboratorio(laboratorio);
    }

    /**
     * funzione per aggiungere un progetto alla tabella
     *
     * @param progetto
     */

    public void aggiungiProgetto(Progetto progetto) {
        Dipendente responsabile = new Dipendente();
        progetto.setResponsabile(responsabile);
        Dipendente referente = new Dipendente();
        progetto.setReferente(referente);
        getModelloProgetti().aggiungiProgetto(progetto);
    }

    /**
     * funzione per rimuovere un progetto dalla tabella
     *
     * @param progetto
     */

    public void rimuoviProgetto(Progetto progetto) {
        getModelloProgetti().rimuoviProgetto(progetto);
    }

    /**
     * funzione per ottenere il modello della tabella dipendenti
     *
     * @return
     */

    private ModelloDipendenti getModelloDipendenti() {
        return (ModelloDipendenti) tabellaDipendenti.getModel();
    }

    /**
     * funzione per ottenere il modello della tabella laboratori
     *
     * @return
     */

    private ModelloLaboratori getModelloLaboratori() {
        return (ModelloLaboratori) tabellaLaboratori.getModel();
    }

    /**
     * funzione per ottenere il modello della tabella progetti
     *
     * @return
     */

    private ModelloProgetti getModelloProgetti() {
        return (ModelloProgetti) tabellaProgetti.getModel();
    }

    /**
     * funzione per ottenere il dipendente selezionato
     *
     * @return
     */

    private Dipendente getDipendenteSelezionato() {
        ModelloDipendenti modello = getModelloDipendenti();
        return modello.getDipendente(tabellaDipendenti.getSelectedRow());
    }

    /**
     * funzione per ottenere il laboratorio selezionato
     *
     * @return
     */
    private Laboratorio getLaboratorioSelezionato() {
        ModelloLaboratori modello = getModelloLaboratori();
        return modello.getLaboratorio(tabellaLaboratori.getSelectedRow());
    }

    /**
     * funzione per ottenere il progetto selezionato
     *
     * @return
     */

    private Progetto getProgettoSelezionato() {
        ModelloProgetti modello = getModelloProgetti();
        return modello.getProgetto(tabellaProgetti.getSelectedRow());
    }

    /**
     * funzione per mostrare un messaggio di errore
     *
     * @param messaggio
     */

    public void showErrorMessage(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio);
    }

    /**
     * funzione per mostrare un messaggio info
     *
     * @param messaggio
     */

    public void showInfoMessage(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio);
    }

    /**
     * funzione per inizializzare i pulsanti promuovi e licenzia
     */

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

    /**
     * funzione che inizializza il form assunzione
     */

    private void inizializzaFormAssunzione() {
        nuovoDipendenteButton.addActionListener(event -> {
            String nome = nomeInseritoTextField.getText();
            String cognome = cognomeInseritoTextField.getText();
            String value = (String) dirigenteBox.getSelectedItem();
            Boolean dir;
            dir = value.equals("SI");
            int giorno = (int) GiornoNascita.getValue();
            String mese = (String) mesiNascita.getSelectedItem();
            int anno = (int) AnnoNascita.getValue();
            anno = anno - 1900;
            Date datadiN = converti(giorno, mese, anno);
            controllerMainPage.aggiungiDipendente(nome, cognome, dir, Date.valueOf(LocalDate.now()), datadiN, Date.valueOf(LocalDate.now()));
            nomeInseritoTextField.setText("");
            cognomeInseritoTextField.setText("");
        });
    }

    /**
     * funzione che setta il controller della gui
     *
     * @param controller
     */


    public void setController(ControllerMainPage controller) {
        this.controllerMainPage = controller;
    }

    /**
     * funzione che setta la combo box di assegnazione di un laboratorio ad un dipendente
     *
     * @param comboBox
     * @param laboratori
     */

    private void setComboBoxLaboratorioDipendente(JComboBox comboBox, List<Laboratorio> laboratori) {
        comboBox.removeAllItems();
        for (Laboratorio laboratorio : laboratori) {
            comboBox.addItem(laboratorio);
        }
    }

    /**
     * funzione che setta la combo box di assegnazione di un referente ad un progetto
     *
     * @param comboBox
     * @param progetto
     */

    private void setComboBoxReferenteProgetto(JComboBox comboBox, Progetto progetto) {
        comboBox.removeAllItems();
        List<Dipendente> dipendenti =  controllerMainPage.listaDipendenti(getModelloDipendenti().getDipendenti() , progetto);
        for (Dipendente dipendente : dipendenti) {
            comboBox.addItem(dipendente);
        }
    }

    /**
     * funzione che setta la combo box di assegnazione di un referete ad un laboratorio
     *
     * @param comboBox
     * @param laboratorio
     */

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

    /**
     * funzione per creare la schermata di assegnazione di un laboratorio ad un dipendente
     *
     * @return
     */

    private JDialog schermataDialogoAssegnazioneLaboratorioDipendente() {
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
            controllerMainPage.assegnaLaboratorio(dipendente, laboratorio);
            getModelloDipendenti().fireTableDataChanged();
            dialogoAssegnazioneDipendenteLaboratorio.setVisible(false);
        });
        return dialogo;
    }

    /**
     * funzione per creare la schermata di assegnazione di un responsabile ad un progetto
     *
     * @return
     */

    private JDialog schermataDialogoAssegnazioneResponsabileProgetto() {
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

    /**
     * funzione per creare la schermata di assegnazione di un referente ad un progetto
     *
     * @return
     */

    private JDialog schermataDialogoAssegnazioneReferenteProgetto() {
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

    /**
     * funzione per creare la schermata di assegnazione di un referente ad un laboratorio
     *
     * @return
     */

    private JDialog schermataDialogoAssegnazioneReferenteLaboratorio() {
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Dipendente vuoi rendere referente"), BorderLayout.PAGE_START);
        dialogo.add(referenteLaboratorioComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Dipendente dipendente = (Dipendente) referenteLaboratorioComboBox.getSelectedItem();
            controllerMainPage.setReferenteLaboratorio(dipendente, getLaboratorioSelezionato());
            getModelloProgetti().fireTableDataChanged();
            dialogo.setVisible(false);
        });
        return dialogo;
    }

    /**
     * funzione per aggiornare il laboratorio di un dipendente
     *
     * @param laboratorio
     * @param dipendente
     */

    public void aggiornaLaboratorioDipendente(Laboratorio laboratorio, Dipendente dipendente) {
        dipendente.setLaboratorio(laboratorio);
        getModelloDipendenti().fireTableDataChanged();

    }

    /**
     * funzione per aggiornare il responsabile di un progetto
     *
     * @param progetto
     * @param dipendente
     */

    public void aggiornaResponsabileProgetto(Progetto progetto, Dipendente dipendente) {
        progetto.setResponsabile(dipendente);
        getModelloProgetti().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare il referente di un progetto
     *
     * @param progetto
     * @param dipendente
     */

    public void aggiornaReferenteProgetto(Progetto progetto, Dipendente dipendente) {

        progetto.setReferente(dipendente);
        getModelloProgetti().fireTableDataChanged();
    }

    /**
     * funzione per rimuovere i dipendenti assegnati dalla tabella
     */

    public void rimuoviDipendentiAssegnati() {
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.rimuoviDipendenti(dipendenti);
    }

    /**
     * funzione per settare i laboratori assegnati ai dipendenti
     */

    public void setLaboratoriDipendenti() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setLaboratorioDipendenti(dipendenti, laboratori);
    }

    /**
     * funzione per settare i progetti assegnati ai laboratori
     */

    public void setProgettiLaboratorio() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        controllerMainPage.setProgettoLaboratori(laboratori , progetti);
    }

    /**
     * funzione per settare i referenti assegnati ai laboratori
     */

    public void setReferenteLaboratorio() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setReferenteLaboratori(laboratori , dipendenti);
    }

    /**
     * funzione per settare i referenti assegnati ai progetti
     */

    public void setReferenteProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setReferenteProgetti(progetti, dipendenti);
    }

    /**
     * funzione per settare i responsabili assegnati ai progetti
     */

    public void setResponsabiliProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setResponsabileProgetti(progetti, dipendenti);
    }

    /**
     * funzione per settare i laboratori assegnati ai progetti
     */

    public void setLaboratoriProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        controllerMainPage.setLaboratoriProgetto(progetti, laboratori);
    }

    /**
     * funzione per inizializzare la schermata di assegnazione dei progetti ai laboratori
     *
     * @return
     */

    public JDialog schermataAssegnazioneProgettiLaboratorio() {
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Progetto vuoi assegnare"), BorderLayout.PAGE_START);
        dialogo.add(progettoLaboratorioComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Progetto progetto = (Progetto) progettoLaboratorioComboBox.getSelectedItem();
            Laboratorio laboratorio = getLaboratorioSelezionato();
            controllerMainPage.aggiornaProgettoLaboratorio(progetto, laboratorio);
            getModelloProgetti().fireTableDataChanged();
            dialogo.setVisible(false);
        });
        return dialogo;

    }

    /**
     * funzione per inizializzare la combobox di assegnazione dei responsabili idonei per il progetto scelto
     *
     * @param comboBox
     * @param progetto
     */

    public void setResponsabiliProgettoComboBox(JComboBox comboBox, Progetto progetto) {
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

    /**
     * funzione per aggiornare il progetto di un laboratorio
     *
     * @param laboratorio
     * @param progetto
     */

    public void aggiornaProgettoLaboratorio(Laboratorio laboratorio, Progetto progetto) {
        laboratorio.setProgetto(progetto);
        progetto.addLaboratorio(laboratorio);
        getModelloProgetti().fireTableDataChanged();
        getModelloLaboratori().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare il referente di un laboratorio
     *
     * @param laboratorio
     * @param dipendente
     */

    public void aggiornaReferenteLaboratorio(Laboratorio laboratorio, Dipendente dipendente) {
        laboratorio.setReferente(dipendente);
        getModelloLaboratori().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare le tabelle dopo un licenziamento
     *
     * @param dipendente
     */

    public void aggiornaTabelleDopoLicenziamento(Dipendente dipendente) {
        for (Progetto progetto : getModelloProgetti().getProgetti()) {
            if (progetto.getResponsabile().getId() == dipendente.getId()) {
                controllerMainPage.rimuoviResponsabile(progetto);
            }
        }

        for (Progetto progetto : getModelloProgetti().getProgetti()) {
            if (progetto.getReferente().getId() == dipendente.getId()) {
                controllerMainPage.rimuoviReferente(progetto);
            }
        }
        getModelloProgetti().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare le tabelle dopo l'elimizazione di un progetto
     *
     * @param progetto
     */

    public void aggiornaTabelleDopoEliminazioneProgetto(Progetto progetto) {
        for (Laboratorio laboratorio : getModelloLaboratori().getLaboratori()) {
            if (laboratorio.getProgetto().getCup() == progetto.getCup()) {
                controllerMainPage.rimuoviProgetto(laboratorio);
            }
        }
        getModelloLaboratori().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare le tabelle dopo l'eliminazione di un laboratorio
     *
     * @param laboratorio
     */

    public void aggiornaTabelleDopoEliminazioneLaboratorio(Laboratorio laboratorio) {
        for (Dipendente dipendente : getModelloDipendenti().getDipendenti()) {
            if (dipendente.getLaboratorio().getNome() != null) {
                if (dipendente.getLaboratorio().getNome().equals(laboratorio.getNome()))
                    controllerMainPage.rimuoviLaboratorio(dipendente);
            }
        }
        getModelloDipendenti().fireTableDataChanged();
    }

    public void degradaDipendente(Dipendente dipendente) {
        dipendente.setDataPromozione(null);
        dipendente.setDirigente(false);
    }

    public void promuoviDipendente(Dipendente dipendente) {
        dipendente.setDataPromozione(Date.valueOf(LocalDate.now()));
        dipendente.setDirigente(true);
    }

}