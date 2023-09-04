package GUI;

import Controller.ControllerMainPage;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;
import java.sql.Date;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe che implementa l'interfaccia grafica principale
 */

public class GUIMain extends JFrame {
    private final DialogoNuovoDipendente interfacciaDipendente = new DialogoNuovoDipendente();
    private final DialogoNuovoProgetto interfacciaProgetto = new DialogoNuovoProgetto();
    private final DialogoNuovoLaboratorio interfacciaLaboratorio = new DialogoNuovoLaboratorio();
    private final JButton creaNuovoDipendenteButton = new JButton("Inserisci");
    private final JButton creaNuovoProgettoButton = new JButton("Inserisci");
    private final JButton creaNuovoLaboratorioButton = new JButton("Inserisci");
    private final JComboBox<Laboratorio> laboratoriComboBox = new JComboBox<>();
    private final JComboBox<Dipendente> referenteProgettoComboBox = new JComboBox<>();
    private final JComboBox<Dipendente> responsabileProgettoComboBox = new JComboBox<>();
    private final JComboBox<Progetto> progettoLaboratorioComboBox = new JComboBox<>();
    private final JComboBox<Dipendente> referenteLaboratorioComboBox = new JComboBox<>();
    private JTabbedPane PannelloPrincipale;
    private JPanel Dipendenti;
    private JPanel Progetti;
    private JPanel Laboratori;
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
    private JTable tabellaLaboratori;
    private JScrollPane PannelloLaboratori;
    private JButton dialogoNuovoLaboratorioButton;
    private JButton eliminaSedeButton;
    private JButton assegnaProgettoButton;
    private JButton apriFinestraAssegnazioneReferenteLaboratorioButton;
    private JButton assegnaResponsabileButton;
    private JButton dialogoReferenteProgettiButton;
    private JButton dialogoNuovoDipendenteButton;
    private ControllerMainPage controllerMainPage;
    private JDialog dialogoAssegnazioneDipendenteLaboratorio;
    private JDialog dialogoInserimentoDipendente = new JDialog();
    private JDialog dialogoInserimentoProgetto = new JDialog();
    private JDialog dialogoInserimentoLaboratorio = new JDialog();

    public GUIMain() {
        inizializzaGui();
        inizializzaListener();
    }

    private void inizializzaGui(){
        setContentPane(PannelloPrincipale);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inizializzaTabellaDipendenti();
        inizializzaTabellaLaboratori();
        inizializzaTabellaProgetti();
        inizializzaPulsantiGrado();
        inizializzaDialoghi();

    }

    /**
     * Metodo che aggiunge tutti i Listener ai bottoni
     */

    private void inizializzaListener(){
        dialogoNuovoDipendenteButton.addActionListener(event ->{
            dialogoInserimentoDipendente.setVisible(true);
        });

        licenziaButton.addActionListener(event -> controllerMainPage.licenziaDipendente(getDipendenteSelezionato()));

        mostraTuttiDipendentiButton.addActionListener(event -> {
            try {
                controllerMainPage.mostraTuttiDipendenti();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        mostraDipendentiNonAssegnatiButton.addActionListener(event -> rimuoviDipendentiAssegnati());

        visualizzaCarrieraButton.addActionListener(event -> buttonVisualizzaCarriera());

        //ACTION LISTENER DI PROGETTO

        nuovoProgettoButton.addActionListener(event -> dialogoInserimentoProgetto.setVisible(true));

        eliminaProgettoButton.addActionListener(event -> controllerMainPage.eliminaProgetto(getProgettoSelezionato()));

        creaNuovoDipendenteButton.addActionListener(event->{
            String nome = interfacciaDipendente.getNome();
            String cognome = interfacciaDipendente.getCognome();
            boolean dirigente = interfacciaDipendente.getDirigente();
            Date dataNascita = interfacciaDipendente.getDataNascita();
            controllerMainPage.nuovoDipendente(nome, cognome, dirigente, Date.valueOf(LocalDate.now()), dataNascita, Date.valueOf(LocalDate.now()));

        });

        creaNuovoProgettoButton.addActionListener(event -> {
            String nome = interfacciaProgetto.getProgetto();
            Progetto progetto = controllerMainPage.nuovoProgetto(nome);
            getModelloProgetti().aggiungiProgetto(progetto);
            dialogoInserimentoProgetto.setVisible(false);
            interfacciaProgetto.clear();
        });

        //ACTION LISTENER DI LABORATORIO

        dialogoNuovoLaboratorioButton.addActionListener(event -> dialogoInserimentoLaboratorio.setVisible(true));

        creaNuovoLaboratorioButton.addActionListener(event -> {
            dialogoInserimentoLaboratorio.setVisible(false);
            String nome = interfacciaLaboratorio.getNome();
            Laboratorio.Topic topic = interfacciaLaboratorio.getTopic();
            controllerMainPage.nuovoLaboratorio(nome, topic);
            interfacciaLaboratorio.clear();
        });

        eliminaSedeButton.addActionListener(event -> {
            controllerMainPage.eliminaLaboratorio(getLaboratorioSelezionato());
        });

        apriFinestraAssegnazioneReferenteLaboratorioButton.addActionListener(event -> {
            JDialog dialogoAssegnazioneReferenteLaboratorio = schermataDialogoAssegnazioneReferenteLaboratorio();
            setComboBoxReferenteLaboratorio(referenteLaboratorioComboBox, getLaboratorioSelezionato());
            if (referenteLaboratorioComboBox.getItemCount() > 0) {
                dialogoAssegnazioneReferenteLaboratorio.setVisible(true);
            } else {
                showErrorMessage("Nessun dipendente da assegnare come referente");
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
                if(referenteProgettoComboBox.getItemCount() > 0)
                    dialodoAssegnazioneReferente.setVisible(true);
                else showErrorMessage("Nessun referente da poter assegnare");
            } else {
                showErrorMessage("Nessun laboratorio assegnato");
            }
        });

        assegnaProgettoButton.addActionListener(event -> {
            JDialog dialogoAssegnazioneProgettoLaboratorio = schermataAssegnazioneProgettiLaboratorio();
            setProgettiLaboratorioComboBox(progettoLaboratorioComboBox);
            dialogoAssegnazioneProgettoLaboratorio.setVisible(true);
        });

        assegnaResponsabileButton.addActionListener(event -> {
            JDialog dialogoAssegnazioneResponsabile = schermataDialogoAssegnazioneResponsabileProgetto();
            Progetto progetto = getProgettoSelezionato();
            if (progetto.getLaboratori().size() > 0) {
                setResponsabiliProgettoComboBox(responsabileProgettoComboBox, progetto);
                if(responsabileProgettoComboBox.getItemCount() > 0)
                    dialogoAssegnazioneResponsabile.setVisible(true);
            } else {
                showErrorMessage("Nessun laboratorio assegnato");
            }
        });
    }

    /**
     * Metodo che tra una tabella con schermata, modello e nome della tabella
     * @param tab schermata dove inserire la tabella
     * @param modello modello della tabella
     * @param nome nome della tabella
     * @return tabella
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
     * @return schermata di dialogo
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
     * Metodo per visualizzare gli scatti di carriera
     */

    public void buttonVisualizzaCarriera() {
        Dipendente dipendente = getDipendenteSelezionato();
        controllerMainPage.getVisualizzaCarriera(dipendente);
    }

    /**
     * Metodo per selezionare quali progetti
     * sono idonei per essere assegnati ad un laboratorio.
     * Un progetto per essere idoneo deve avere
     * massimo 2 laboratori assegnati
     *
     * @param comboBox combobox dei progetti da assegnare al laboratorio
     */

    private void setProgettiLaboratorioComboBox(JComboBox comboBox) {
        comboBox.removeAllItems();
        for (Progetto progetto : getModelloProgetti().getProgetti()) {
            if (progetto.getLaboratori().size() < 3) {
                comboBox.addItem(progetto);
            }
        }
    }


    /**
     * Metodo per inizializzare la tabella dipendenti
     */

    private void inizializzaTabellaDipendenti() {
        ModelloDipendenti modello = new ModelloDipendenti();
        tabellaDipendenti = creaTabella(PannelloDipendenti, modello, "Dipendenti");
    }

    /**
     * Metodo per inizializzare la tabella laboratori
     */

    private void inizializzaTabellaLaboratori() {
        ModelloLaboratori modello = new ModelloLaboratori();
        tabellaLaboratori = creaTabella(PannelloLaboratori, modello, "Laboratori");
    }

    /**
     * Metodo per inizializzare la tabella progetti
     */

    private void inizializzaTabellaProgetti() {
        ModelloProgetti modello = new ModelloProgetti();
        tabellaProgetti = creaTabella(PannelloProgetti, modello, "Progetti");
    }


    /**
     * Metodo per settare i dipendenti nella tabella
     *
     * @param dipendenti dipendenti da inserire
     */

    public void setDipendenti(List<Dipendente> dipendenti) {

        getModelloDipendenti().setDipendenti(dipendenti);
    }

    /**
     * Metodo per settare i laboratori nella tabella
     *
     * @param laboratori laboratori da inserire
     */

    public void setLaboratori(List<Laboratorio> laboratori) {
        getModelloLaboratori().setLaboratori(laboratori);
    }

    /**
     * Metodo per settare i progetti nella tabella
     *
     * @param progetti progetti da settare
     */

    public void setProgetti(List<Progetto> progetti) {
        getModelloProgetti().setProgetti(progetti);
    }

    /**
     * Metodo per aggiungere un dipendente nella tabella
     *
     * @param dipendente dipendente da aggiungere
     */

    public void aggiungiDipendente(Dipendente dipendente) {
        getModelloDipendenti().aggiungiDipendente(dipendente);
        dialogoInserimentoDipendente.setVisible(false);
        interfacciaDipendente.clear();
    }

    /**
     * Metodo per rimuovere un dipendente dalla tabella
     *
     * @param dipendente dipendente da rimuovere
     */

    public void rimuoviDipendente(Dipendente dipendente) {
        getModelloDipendenti().rimuoviDipendente(dipendente);
        getModelloLaboratori().fireTableDataChanged();
        getModelloProgetti().fireTableDataChanged();
    }

    /**
     * Metodo per aggiungere un laboratorio nella tabella
     *
     * @param laboratorio laboratorio da aggiungere
     */

    public void aggiungiLaboratorio(Laboratorio laboratorio) {
        getModelloLaboratori().aggiungiLaboratorio(laboratorio);
    }

    /**
     * Metodo per rimuovere un laboratorio dalla tabella
     *
     * @param laboratorio laboratorio da rimuovere
     */

    public void rimuoviLaboratorio(Laboratorio laboratorio) {
        getModelloLaboratori().rimuoviLaboratorio(laboratorio);
    }

    /**
     * Metodo per rimuovere un progetto dalla tabella
     *
     * @param progetto progetto da rimuovere
     */

    public void rimuoviProgetto(Progetto progetto) {
        getModelloProgetti().rimuoviProgetto(progetto);
    }

    /**
     * funzione per ottenere il modello della tabella dipendenti
     *
     * @return modello della tabella dipendenti
     */

    private ModelloDipendenti getModelloDipendenti() {
        return (ModelloDipendenti) tabellaDipendenti.getModel();
    }

    /**
     * funzione per ottenere il modello della tabella laboratori
     *
     * @return modello della tabella laboratori
     */

    private ModelloLaboratori getModelloLaboratori() {
        return (ModelloLaboratori) tabellaLaboratori.getModel();
    }

    /**
     * funzione per ottenere il modello della tabella progetti
     *
     * @return modello della tabella progetti
     */

    private ModelloProgetti getModelloProgetti() {
        return (ModelloProgetti) tabellaProgetti.getModel();
    }

    /**
     * funzione per ottenere il dipendente selezionato
     *
     * @return dipendente selezionato
     */

    private Dipendente getDipendenteSelezionato() {
        ModelloDipendenti modello = getModelloDipendenti();
        return modello.getDipendente(tabellaDipendenti.getSelectedRow());
    }

    /**
     * funzione per ottenere il laboratorio selezionato
     *
     * @return laboratorio selezionato
     */
    private Laboratorio getLaboratorioSelezionato() {
        ModelloLaboratori modello = getModelloLaboratori();
        return modello.getLaboratorio(tabellaLaboratori.getSelectedRow());
    }

    /**
     * funzione per ottenere il progetto selezionato
     *
     * @return progetto selezionato
     */

    private Progetto getProgettoSelezionato() {
        ModelloProgetti modello = getModelloProgetti();
        return modello.getProgetto(tabellaProgetti.getSelectedRow());
    }

    /**
     * funzione per mostrare un messaggio di errore
     *
     * @param messaggio messaggio da mostrare
     */

    public void showErrorMessage(String messaggio) {
        JOptionPane.showMessageDialog(this, messaggio);
    }

    /**
     * funzione per mostrare un messaggio info
     *
     * @param messaggio messaggio da mostrare
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
     * funzione che setta il controller della gui
     *
     * @param controller controller da settare
     */


    public void setController(ControllerMainPage controller) {
        this.controllerMainPage = controller;
    }

    /**
     * funzione che setta la combo box di assegnazione di un laboratorio ad un dipendente
     *
     * @param comboBox combobox di laboratori
     * @param laboratori laboratori del dipendente
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
     * @param comboBox combobox di referenti
     * @param progetto progetto selezionato
     */

    private void setComboBoxReferenteProgetto(JComboBox comboBox, Progetto progetto) {
        comboBox.removeAllItems();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        List<Dipendente> dipendentiScelti =  controllerMainPage.listaReferentiProgettoPossibili( dipendenti , progetto);
        for (Dipendente dipendente : dipendentiScelti) {
            comboBox.addItem(dipendente);
        }
    }

    /**
     * funzione che setta la combo box di assegnazione di un referete ad un laboratorio
     *
     * @param comboBox combobox di referenti
     * @param laboratorio laboratorio selezionato
     */

    private void setComboBoxReferenteLaboratorio(JComboBox comboBox, Laboratorio laboratorio) {
        comboBox.removeAllItems();
        List<Dipendente> dipendenti = controllerMainPage.listaReferentiLaboratorioPossibili(getModelloDipendenti().getDipendenti() , laboratorio);
        for (Dipendente dipendente : dipendenti) {
            comboBox.addItem(dipendente);
        }
    }

    /**
     * funzione per creare la schermata di assegnazione di un laboratorio ad un dipendente
     *
     * @return schermata di assegnazione di un laboratorio ad un dipendente
     */

    private JDialog schermataDialogoAssegnazioneLaboratorioDipendente() {
        JDialog dialogo = creaDialogo();
        dialogo.add(new JLabel("Quale Laboratorio vuoi assegnargli"), BorderLayout.PAGE_START);
        List<Laboratorio> laboratory = getModelloLaboratori().getLaboratori();
        setComboBoxLaboratorioDipendente(laboratoriComboBox, laboratory);
        dialogo.add(laboratoriComboBox, BorderLayout.CENTER);
        JButton conferma = new JButton("Conferma");
        dialogo.add(conferma, BorderLayout.PAGE_END);
        dialogo.pack();
        conferma.addActionListener(Event -> {
            Laboratorio laboratorio = (Laboratorio) laboratoriComboBox.getSelectedItem();
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
     * @return schermata di assegnazione di un responsabile ad un progetto
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
     * @return schermata di assegnazione di un referente ad un progetto
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
     * @return schermata di assegnazione di un referente ad un laboratorio
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
     * @param laboratorio laboratorio da settare
     * @param dipendente dipendente selezionato
     */

    public void aggiornaLaboratorioDipendente(Laboratorio laboratorio, Dipendente dipendente) {
        dipendente.setLaboratorio(laboratorio);
        getModelloDipendenti().fireTableDataChanged();

    }

    /**
     * funzione per aggiornare il responsabile di un progetto
     *
     * @param progetto progetto selezionato
     * @param dipendente responsabile da settare
     */

    public void aggiornaResponsabileProgetto(Progetto progetto, Dipendente dipendente) {
        progetto.setResponsabile(dipendente);
        getModelloProgetti().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare il referente di un progetto
     *
     * @param progetto progetto selezionato
     * @param dipendente referente da settare
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
        controllerMainPage.rimuoviDipendentiAssegnati(dipendenti);
    }

    /**
     * funzione per settare i laboratori assegnati ai dipendenti
     */

    public void setLaboratoriDipendenti() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setLaboratoriDipendente(dipendenti, laboratori);
    }

    /**
     * funzione per settare i progetti assegnati ai laboratori
     */

    public void setProgettiLaboratorio() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        controllerMainPage.setProgettiLaboratorio(laboratori , progetti);
    }

    /**
     * funzione per settare i referenti assegnati ai laboratori
     */

    public void setReferenteLaboratorio() {
        List<Laboratorio> laboratori = getModelloLaboratori().getLaboratori();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setReferentiLaboratorio(laboratori , dipendenti);
    }

    /**
     * funzione per settare i referenti assegnati ai progetti
     */

    public void setReferenteProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setReferentiProgetto(progetti, dipendenti);
    }

    /**
     * funzione per settare i responsabili assegnati ai progetti
     */

    public void setResponsabiliProgetto() {
        List<Progetto> progetti = getModelloProgetti().getProgetti();
        List<Dipendente> dipendenti = getModelloDipendenti().getDipendenti();
        controllerMainPage.setResponsabiliProgetto(progetti, dipendenti);
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
     * @return schermata di assegnazione di un progetto ad un laboratorio
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
     * @param comboBox combobox responsabili
     * @param progetto progetto scelto
     */

    public void setResponsabiliProgettoComboBox(JComboBox comboBox, Progetto progetto) {
        comboBox.removeAllItems();
        List<Dipendente> dipendenti = controllerMainPage.listaResponsabiliProgettoPossibili(getModelloDipendenti().getDipendenti() , progetto);
        if(dipendenti.size() > 0){
            for (Dipendente dipendente : dipendenti) {
                comboBox.addItem(dipendente);
            }
        }else{
            showErrorMessage("Nessun responsabile da assegnare");
        }

    }

    /**
     * funzione per aggiornare il progetto di un laboratorio
     *
     * @param laboratorio laboratorio scelto
     * @param progetto progetto da settare
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
     * @param laboratorio laboratorio scelto
     * @param dipendente referente da settare
     */

    public void aggiornaReferenteLaboratorio(Laboratorio laboratorio, Dipendente dipendente) {
        laboratorio.setReferente(dipendente);
        getModelloLaboratori().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare le tabelle dopo un licenziamento
     *
     * @param dipendente dipendente licenziato
     */

    public void aggiornaTabelleDopoLicenziamento(Dipendente dipendente) {
        for (Progetto progetto : getModelloProgetti().getProgetti()) {
            if (progetto.getResponsabile().equals(dipendente)) {
                controllerMainPage.rimuoviResponsabile(progetto);
            }
        }

        for (Progetto progetto : getModelloProgetti().getProgetti()) {
            if (progetto.getReferente().equals(dipendente)) {
                controllerMainPage.rimuoviReferente(progetto);
            }
        }
        getModelloProgetti().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare le tabelle dopo l'elimizazione di un progetto
     *
     * @param progetto progetto eliminato
     */

    public void aggiornaTabelleDopoEliminazioneProgetto(Progetto progetto) {
        for (Laboratorio laboratorio : getModelloLaboratori().getLaboratori()) {
            if (laboratorio.getProgetto().equals(progetto)) {
                controllerMainPage.rimuoviProgetto(laboratorio);
            }
        }
        getModelloLaboratori().fireTableDataChanged();
    }

    /**
     * funzione per aggiornare le tabelle dopo l'eliminazione di un laboratorio
     *
     * @param laboratorio laboratorio eliminato
     */

    public void aggiornaTabelleDopoEliminazioneLaboratorio(Laboratorio laboratorio) {
        for (Dipendente dipendente : getModelloDipendenti().getDipendenti()) {
            if (dipendente.getLaboratorio().equals(laboratorio)) {
                controllerMainPage.rimuoviLaboratorio(dipendente);
            }
        }
        getModelloDipendenti().fireTableDataChanged();
    }

    /**
     * degrada il dipendente selezionato
     * @param dipendente dipendente selezionato
     */

        public void degradaDipendente(Dipendente dipendente) {
        dipendente.setDataPromozione(null);
        dipendente.setDirigente(false);
    }

    /**
     * promuove il dipendente selezionato
     * @param dipendente dipendente da promuovere
     */

    public void promuoviDipendente(Dipendente dipendente) {
        dipendente.setDataPromozione(Date.valueOf(LocalDate.now()));
        dipendente.setDirigente(true);
    }

    /**
     * Metodo che inizializza i dialoghi
     */

        private void inizializzaDialoghi(){
        dialogoInserimentoDipendente.setLayout(new BorderLayout());
        dialogoInserimentoDipendente.setModal(true);
        dialogoInserimentoDipendente.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogoInserimentoDipendente.add(interfacciaDipendente);
        dialogoInserimentoDipendente.add(creaNuovoDipendenteButton, BorderLayout.PAGE_END);
        dialogoInserimentoDipendente.pack();
        dialogoInserimentoDipendente.setLocationRelativeTo(null);

        dialogoInserimentoProgetto.setLayout(new BorderLayout());
        dialogoInserimentoProgetto.setModal(true);
        dialogoInserimentoProgetto.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogoInserimentoProgetto.add(interfacciaProgetto);
        dialogoInserimentoProgetto.add(creaNuovoProgettoButton, BorderLayout.PAGE_END);
        dialogoInserimentoProgetto.pack();
        dialogoInserimentoProgetto.setLocationRelativeTo(null);

        dialogoInserimentoLaboratorio.setLayout(new BorderLayout());
        dialogoInserimentoLaboratorio.setModal(true);
        dialogoInserimentoLaboratorio.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogoInserimentoLaboratorio.add(interfacciaLaboratorio);
        dialogoInserimentoLaboratorio.add(creaNuovoLaboratorioButton, BorderLayout.PAGE_END);
        dialogoInserimentoLaboratorio.pack();
        dialogoInserimentoLaboratorio.setLocationRelativeTo(null);
    }

}