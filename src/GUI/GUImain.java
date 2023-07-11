package GUI;

import DAO.*;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.sql.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class GUImain extends JFrame {
    DefaultTableModel modelloDipendenti;
    private final ProgettoDAO progettoDAO;
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
    private JButton dipendentiDaAssegnareButton;
    private JButton tuttiIDipendentiButton;
    private JTable TabellaDipendenti;
    private JScrollPane PannelloDipendenti;
    private JScrollPane PannelloProgetti;
    private JButton licenziaButton;
    private JButton assegnaButton;
    private JButton promuoviButton;
    private JButton degradaButton;
    private JButton visualizzaCarrieraButton;
    private JTable TabellaProgetti;
    private JButton nuovoProgettoButton;
    private JButton eliminaProgettoButton;
    private final DipendenteDAO dipendenteDAO;
    private Dipendente dip;
    String[] ValoriDirigenteBox = {"NO", "SI"};
    String[] Mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
    private boolean Dir;
    private final int currentYear = LocalDate.now().getYear();
    private final SpinnerNumberModel YearModel;
    private final SpinnerNumberModel GiorniModel28;
    private final SpinnerNumberModel GiorniModel30;
    private final SpinnerNumberModel GiorniModel31;
    private final JComboBox Lab;
    private String[] NomiLab;
    private List<Dipendente> DIR = new ArrayList<>();
    private final LaboratorioDAO laboratorioDAO;
    private final CambioRuoloDAO cambioRuoloDAO;
    private final Nuovo_Prog InterfacciaProgetto = new Nuovo_Prog();
    private DefaultTableModel modelloProgetti = new DefaultTableModel();
    private DefaultTableModel modelloLaboratori = new DefaultTableModel();
    private final JButton AssegnaReferenteProgettiButton = new JButton("Inserisci");
    private final JButton InserimentoProgettoButton = new JButton("Inserisci");
    private final JButton CreaNuovoLaboratorioButton = new JButton("Inserisci");
    private JTable table3;
    private JScrollPane Tabella3;
    private JButton nuovoLaboratorioButton;
    private JButton eliminaSedeButton;
    private JButton assegnaProgettoButton;
    private JButton assegnaReferenteButton;
    private JButton assegnaResponsabileButton;
    private JButton DialogoReferenteProgettiButton;
    private final Nuovo_Lab nuovoLab = new Nuovo_Lab();
    private final JComboBox ComboBox;
    private final JButton AssegnazioneProgettoButton = new JButton("Conferma");
    private final JButton ConfermaResponsabileButton = new JButton("Conferma");

    public GUImain(ProgettoDAO progettoDAO, DipendenteDAO dipendenteDAO, LaboratorioDAO laboratorioDAO, CambioRuoloDAO cambioRuoloDAO) {
        this.progettoDAO = progettoDAO;
        this.dipendenteDAO = dipendenteDAO;
        this.laboratorioDAO = laboratorioDAO;
        this.cambioRuoloDAO = cambioRuoloDAO;
/**
 *
 * Aggiunta dei mesi alla combobox di inserimento
 */
        for (int i = 0; i < 12; i++) {
            MesiNascita.addItem(Mesi[i]);

        }
        setContentPane(PannelloPrincipale);
        /**
         * Realizzazione della schermata di aggiunta di un nuovo dipendente
         */
        setSize(1000, 300);
        setLocationRelativeTo(null);
        setBackground(Color.CYAN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DirigenteBox.setEditable(true);
        DirigenteBox.addItem(ValoriDirigenteBox[0]);
        DirigenteBox.addItem(ValoriDirigenteBox[1]);
        YearModel = new SpinnerNumberModel(currentYear - 30, currentYear - 80, currentYear - 18, 1);
        AnnoNascita.setModel(YearModel);
        GiorniModel28 = new SpinnerNumberModel(1, 1, 28, 1);
        GiorniModel30 = new SpinnerNumberModel(1, 1, 30, 1);
        GiorniModel31 = new SpinnerNumberModel(1, 1, 31, 1);
        GiornoNascita.setModel(GiorniModel31);
/**
 * FINE DEFINIZIONE AGGIUNGI DIPENDENTE
 *
 * Definizione del modello dipendenti
 */
        ArrayList<Dipendente> data = new ArrayList<>();
        modelloDipendenti = CreaModelloDipendenti();
        TabellaDipendenti = new JTable(modelloDipendenti) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        CreaTabella(TabellaDipendenti, PannelloDipendenti, modelloDipendenti, "Dipendenti");
        fetch_dipendenti(modelloDipendenti);

        /**
         * SCHERMATA DI DIALOGO DI ASSEGNAZIONE AD UN NUOVO LABORATORIO
         */
        JDialog dialogoLaboratorio = new JDialog();
        JButton ConfermaAssegnazioneLaboratorio = new JButton("Conferma");
        JTextField DomandaLaboratorio = new JTextField("A quale laboratorio vuoi assegnarlo?");
        DomandaLaboratorio.setEnabled(false);
        DomandaLaboratorio.setDisabledTextColor(Color.black);
        CreaDialogo(dialogoLaboratorio, ConfermaAssegnazioneLaboratorio, DomandaLaboratorio);
        Lab = new JComboBox<>();

        /**
         * INSERIMENTO PROGETTO
         */


        JDialog dialogoInserimentoProgetto = new JDialog();
        dialogoInserimentoProgetto.setLayout(new BorderLayout());
        dialogoInserimentoProgetto.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogoInserimentoProgetto.add(InterfacciaProgetto);
        dialogoInserimentoProgetto.add(InserimentoProgettoButton, BorderLayout.PAGE_END);
        dialogoInserimentoProgetto.pack();
        dialogoInserimentoProgetto.setLocationRelativeTo(null);

        /**
         * INSERIMENTO LABORATORIO
         */
        JDialog DialogoNuovoLaboratorio = new JDialog();
        DialogoNuovoLaboratorio.setLayout(new BorderLayout());
        DialogoNuovoLaboratorio.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        DialogoNuovoLaboratorio.add(nuovoLab);
        DialogoNuovoLaboratorio.add(CreaNuovoLaboratorioButton, BorderLayout.PAGE_END);
        DialogoNuovoLaboratorio.pack();
        DialogoNuovoLaboratorio.setLocationRelativeTo(null);
        /**
         * Definizione modello della tabella progetti
         */
        modelloProgetti = CreazioneModelloProgetti();
        TabellaProgetti = new JTable(modelloProgetti) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        CreaTabella(TabellaProgetti, PannelloProgetti, modelloProgetti, "Laboratori");
        fetch_progetti(modelloProgetti);
/**
 * Creazione finestra di dialogo assegna Referente
 */
        JButton ConfermaReferenteButton = new JButton("Conferma");
        JTextField DomandaReferente = new JTextField("Quale referente vuoi assegnare?");
        JDialog dialogoAssegnazioneReferente = new JDialog();
        CreaDialogo(dialogoAssegnazioneReferente, ConfermaReferenteButton, DomandaReferente);

        ComboBox = new JComboBox<>();
        modelloLaboratori = CreaModelloLaboratori();
        table3 = new JTable(modelloLaboratori) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        CreaTabella(table3, Tabella3, modelloLaboratori, "Progetti");
        fetch_laboratorio(modelloLaboratori);
/**
 * Creazione di finestra di dialogo assegna Progetto
 */
        JDialog DialogoAssegnazioneProgetto = new JDialog();
        JTextField DOMANDA3 = new JTextField("Quale progetto vuoi assegnare?");
        CreaDialogo(DialogoAssegnazioneProgetto, AssegnazioneProgettoButton, DOMANDA3);
/**
 * Creazione Finestra di dialogo assegna Referente
 */

        JDialog dialogoProgetti = new JDialog();

        JTextField AssegnaReferente = new JTextField("Assegna Referente");
        CreaDialogo(dialogoProgetti, AssegnaReferenteProgettiButton, AssegnaReferente);
/**
 * Creazione Finestra di Dialogo assegna Responsabile
 */
        JDialog dialogoAssegnazioneResponabile = new JDialog();
        JTextField AssegnaResponsabile = new JTextField("Assegna Responsabile");
        CreaDialogo(dialogoAssegnazioneResponabile, ConfermaResponsabileButton, AssegnaResponsabile);

        /**
         * Creazione di tutti gli Action Listener
         */
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
        assumiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonConferma(modelloDipendenti);
            }
        });
        //Action Listener del Pannello Visual
        licenziaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonLicenzia(modelloDipendenti);
            }
        });
        assegnaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegna(dialogoLaboratorio);
            }
        });
        promuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonPromuovi(modelloDipendenti);
            }
        });
        ConfermaAssegnazioneLaboratorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssegnazioneLaboratorio(dialogoLaboratorio, modelloDipendenti);
            }

        });
        degradaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonDegrada(modelloDipendenti);
            }
        });
        tuttiIDipendentiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonTuttiIDipendenti(modelloDipendenti);
            }
        });
        dipendentiDaAssegnareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonDipendentiDaAssegnare(modelloDipendenti);
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
        eliminaProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonEliminaProgetto();
            }
        });
        InserimentoProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonInserimentoProgetto(dialogoInserimentoProgetto);
            }
        });
        //ACTION LISTENER DI LABORATORIO
        nuovoLaboratorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogoNuovoLaboratorio.setVisible(true);
            }
        });
        CreaNuovoLaboratorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonCreaNuovoLaboratorio(DialogoNuovoLaboratorio);
            }
        });
        eliminaSedeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonEliminaSede();
            }
        });
        assegnaReferenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnaReferente(dialogoAssegnazioneReferente);
            }
        });
        ConfermaReferenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonConfermaReferente(dialogoAssegnazioneReferente);
            }
        });
        AssegnazioneProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnazioneProgetto(DialogoAssegnazioneProgetto);
            }
        });
        DialogoReferenteProgettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonDialogoReferenteProgetti(dialogoProgetti);

            }
        });
        assegnaProgettoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnaProgettoLaboratorio(DialogoAssegnazioneProgetto);
            }
        });
        AssegnaReferenteProgettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonAssegnaReferenteProgetti(dialogoProgetti);
            }
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
                ButtonConfermaResponsabile(dialogoAssegnazioneResponabile);
            }
        });
    }


    /**
     * Funzione fetch_progetti
     *
     * @param model Funzione che prende in ingresso un modello(nel nostro caso il modello della Tabella Progetti)
     *              lo svuota completamente e restituisce la tabella completamente riempita
     *              questo per evitare che nel momento della fetch vengano inseriti dati gia presenti
     *              nella tabella
     */
    public void fetch_progetti(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        try {
            List<Progetto> Progetti = progettoDAO.ottieniprogetti();
            for (int i = 0; i < Progetti.size(); i++) {
                Progetto p = Progetti.get(i);
                String nome_p, referente, dirigente, topic;
                nome_p = p.getNome_Prog();
                referente = p.getReferente();
                dirigente = p.getDirigente();
                topic = p.getTopic();
                String cup = String.valueOf(p.getCUP());
                String[] row = {nome_p, cup, topic, referente, dirigente};
                model.addRow(row);
            }
            PannelloProgetti.setViewportView(TabellaProgetti);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
        }

    }

    /**
     * Stessa cosa della fetch progetti ma per la tabella laboratorio
     *
     * @param model
     */
    public void fetch_laboratorio(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        try {
            List<Laboratorio> Laboratori = laboratorioDAO.getLabs();
            for (int i = 0; i < Laboratori.size(); i++) {
                Laboratorio l = Laboratori.get(i);
                String nome_lab, topic;
                String progetto = String.valueOf(l.getProgetto());
                if (progetto.equals("0")) progetto = "Non Assegnato";
                String responsabile = String.valueOf(l.getResponsabile());
                nome_lab = l.getNome_Lab();
                topic = l.getTopic();
                if (responsabile.equals("0")) responsabile = "Non Assegnato";
                String[] row = {nome_lab, topic, progetto, responsabile};
                model.addRow(row);
            }
            Tabella3.setViewportView(table3);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore di Darabase");
        }

    }

    /**
     * Stessa cosa della altre due fetch
     *
     * @param model
     */
    public void fetch_dipendenti(DefaultTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        try {
            List<Dipendente> Lista = dipendenteDAO.getDipendente();
            for (int i = 0; i < Lista.size(); i++) {
                Dipendente d = Lista.get(i);
                String nome, cognome, Data_N, Laboratorio, DataA;
                nome = d.getNome();
                cognome = d.getCognome();
                Data_N = d.getData_nascita().toString();
                String id_dip = Integer.toString(d.getId_dip());
                Laboratorio = d.getLaboratorio();
                String Dirigente;
                DataA = d.getAssunzione().toString();
                if (d.isDirigente()) Dirigente = "SI";
                else Dirigente = "NO";
                String[] row = {nome, cognome, id_dip, Data_N, Laboratorio, Dirigente, DataA};
                model.addRow(row);
            }
            PannelloDipendenti.setViewportView(TabellaDipendenti);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Errore nel DataBase");
        }
    }

    /**
     * Questa è la funzione invocata da LicenziaButton
     * prende il valore selezionato nella tabella
     * e lo rimuove dalla tabella e dal database
     * con il vincolo che se è responsabile di laboratorio non viene
     * eseguita l'azione
     */
    public void ButtonLicenzia(DefaultTableModel model) {
        int row = TabellaDipendenti.getSelectedRow();
        int column = 2; //numero della colonna id_dip che è chiave esterna nel database
        String value = TabellaDipendenti.getModel().getValueAt(row, column).toString();
        String NomeLab = TabellaDipendenti.getModel().getValueAt(row, 4).toString();

        int id_dip = Integer.valueOf(value);
        int i;
        try{
            if (id_dip != laboratorioDAO.getReferenteLab(NomeLab)) {
                i = dipendenteDAO.removeDipendente(id_dip);
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Dipendente Licenziato! Poverino :(");
                    fetch_progetti(modelloProgetti);
                } else JOptionPane.showMessageDialog(null, "Qualcosa è andato storto!");
                model.removeRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "Il Dipendente è Responsabile di Laboratorio");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Questa funzione prende in ingresso una finestra di dialogo e un modello e effettua l'assegnazione
     * ad un nuovo laboratorio, ma se il dipendente è gia responsabile del laboratorio in cui si trova
     * non effettua l'aggiornamento
     *
     * @param dialogo
     * @param modello
     */
    public void AssegnazioneLaboratorio(JDialog dialogo, DefaultTableModel modello) {
        String Nuovo_Lab = (String) Lab.getSelectedItem();
        int row = TabellaDipendenti.getSelectedRow();
        String value = TabellaDipendenti.getModel().getValueAt(row, 2).toString();
        int id_dip = Integer.valueOf(value);
        String NomeLab = TabellaDipendenti.getModel().getValueAt(row, 4).toString();
        try{
            if (id_dip != laboratorioDAO.getReferenteLab(NomeLab)) {
                dipendenteDAO.setLaboratorio(Nuovo_Lab, id_dip);
                JOptionPane.showMessageDialog(null, "Dipendente Assegnato");
                dialogo.setVisible(false);
                modello.setValueAt(Nuovo_Lab, row, 4);
            } else {
                JOptionPane.showMessageDialog(null, "Il Dipendente è Responsabile di Laboratorio");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

        PannelloDipendenti.setViewportView(TabellaDipendenti);
    }

    /**
     * Elimina un laboratorio
     */
    public void ButtonEliminaSede() {
        int row = table3.getSelectedRow();
        String nome = String.valueOf(table3.getValueAt(row, 0));//ottengo il nome del laboratorio
        try{
            int i = laboratorioDAO.remove(nome);
            if (i > 0) {
                JOptionPane.showMessageDialog(null, "Sede Eliminata");
                modelloLaboratori.removeRow(row);
                fetch_dipendenti(modelloDipendenti);
            } else {
                JOptionPane.showMessageDialog(null, "OPS, Qualcosa è andato storto!");

            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Funzione che effettua l'inserimento nella tabella Dipendenti e nel database
     * dopo aver inserito le credenziali nella prima schermata di Pannello Principale
     * Effettua un controllo sui dati inseriti e non accetta ne spazi ne numeri
     *
     * @param model
     */
    public void ButtonConferma(DefaultTableModel model) {
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
        try {
            if (!Nome.matches("[A-Za-z]+") || !Cognome.matches("[A-Za-z]+")) throw new Exception();
            int id_dip = dipendenteDAO.Id_dip();
            dip = new Dipendente(Nome, Cognome, id_dip, Dir, data, DatadiN);
            dipendenteDAO.insertDipendente(dip);
            String nome, cognome, Data_N, Laboratorio;
            nome = dip.getNome();
            cognome = dip.getCognome();
            Data_N = dip.getData_nascita().toString();
            String ID = Integer.toString(dip.getId_dip());
            Laboratorio = dip.getLaboratorio();
            String Dirigente;
            String DataA = dip.getAssunzione().toString();
            if (dip.isDirigente()) Dirigente = "SI";
            else Dirigente = "NO";
            String[] row = {nome, cognome, ID, Data_N, Laboratorio, Dirigente, DataA};
            model.addRow(row);
            JOptionPane.showMessageDialog(null, "Dipendente Assunto!");
            NomeInserito.setText("");
            CognomeInserito.setText("");
        } catch (Exception e1) {

            JOptionPane.showMessageDialog(null, "Inserisci le Credenziali Corrette!");
        }
    }

    /**
     * Funzione che crea una tabella dati in input il nome della tabella, il modello e la tabella
     *
     * @param tabella
     * @param Tab
     * @param modello
     * @param Nome
     */
    public void CreaTabella(JTable tabella, JScrollPane Tab, DefaultTableModel modello, String Nome) {
        tabella = new JTable(modello) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tabella.setName(Nome);
        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabella.setFocusable(false);
        tabella.setModel(modello);
        tabella.setFillsViewportHeight(true);
        tabella.setPreferredScrollableViewportSize(tabella.getPreferredSize());
        Tab.add(tabella.getTableHeader());
        Tab.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Tab.setBounds(446, 50, 450, 490);
        Tab.setViewportView(tabella);
        Tab.setVisible(true);
    }

    /**
     * funzione che crea una finestra di dialogo con input la domanda, il bottone di conferma relativo e la finestra di dialogo
     *
     * @param dialogo
     * @param conferma
     * @param domanda
     */
    public void CreaDialogo(JDialog dialogo, JButton conferma, JTextField domanda) {

        dialogo.setLocationRelativeTo(null);
        dialogo.setModal(true);
        dialogo.add(new JLabel());
        dialogo.pack();
        dialogo.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialogo.setLayout(new BorderLayout());
        dialogo.setSize(300, 150);

        domanda.setEnabled(false);
        domanda.setDisabledTextColor(Color.black);
        dialogo.setResizable(false);
        dialogo.add(domanda, BorderLayout.PAGE_START);
        dialogo.add(conferma, BorderLayout.PAGE_END);
    }

    /**
     * Bottone di conferma della finestra di dialogo laboratorio
     *
     * @param dialogo
     */
    public void ButtonCreaNuovoLaboratorio(JDialog dialogo) {
        String Topic = nuovoLab.getTopicElement();
        try{
            Laboratorio l = new Laboratorio(nuovoLab.getNomeLab(), Topic);
            int c = laboratorioDAO.Inserisci(l.getNome_Lab(), l.getTopic());
            if (c > 0) {
                dialogo.setVisible(false);
                JOptionPane.showMessageDialog(null, "Inserimento riuscito!");
                String[] row = {l.getNome_Lab(), l.getTopic(), "Non Assegnato", "Non Assegnato"};
                modelloLaboratori.addRow(row);
                nuovoLab.setText();
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Funzione del bottone per eliminare un progetto dal db e dalla tabella e aggiornare
     * la tabella laboratorio
     */
    public void ButtonEliminaProgetto() {
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf(TabellaProgetti.getValueAt(row, 1).toString());//prendo il cup dalla tabella
        try{
            int ritorno = progettoDAO.EliminaProgetto(cup);
            if (ritorno == 1) {
                modelloProgetti.removeRow(row);
                JOptionPane.showMessageDialog(null, "Progetto Eliminato!");
                fetch_laboratorio(modelloLaboratori);
            } else {
                JOptionPane.showMessageDialog(null, "Ops, Qualcosa è andato storto!");
         }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }


    }

    /**
     * funzione per degradare un responsabile
     *
     * @param modello
     */
    public void ButtonDegrada(DefaultTableModel modello) {
        int row = TabellaDipendenti.getSelectedRow();
        String value = (String) TabellaDipendenti.getModel().getValueAt(row, 5);
        int id_dip = Integer.valueOf(TabellaDipendenti.getValueAt(row, 2).toString());//prendendo id_dip dalla tabella
        try{
            if (value.equals("SI")) {
                dipendenteDAO.degrada(row);
                modello.setValueAt("NO", row, 5);
                try{
                    cambioRuoloDAO.removePromozione(id_dip);
                }catch(Exception e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null , "Errore nel Database");
                }
            } else {
                JOptionPane.showMessageDialog(null, "IMPOSSIBILE DEGRADARE!");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }
    }

    /**
     * filtro per visualizzare solo i dipendenti da assegnare
     *
     * @param modello
     */
    public void ButtonDipendentiDaAssegnare(DefaultTableModel modello) {
        int i = 0;
        while (i < modello.getRowCount()) {
            String cmp = (String) modello.getValueAt(i, 4);
            if ((!cmp.equals("Non Assegnato"))) {
                modello.removeRow(i);
            } else {
                i++;
            }
        }
    }

    /**
     * bottone per inserire nella tabella il responsabile di progetto selezionato
     *
     * @param dialogo
     */

    public void ButtonConfermaResponsabile(JDialog dialogo) {
        String values = (String) ComboBox.getSelectedItem();
        String[] id = values.split(" ");
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        try{
            progettoDAO.setResponsabile(Integer.valueOf(id[0]), cup);
            modelloProgetti.setValueAt(id[0], row, 4);
            dialogo.setVisible(false);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }
    }

    /**
     * bottone per confermare il referente dei progetti selezionato
     *
     * @param dialogo
     */
    public void ButtonAssegnaReferenteProgetti(JDialog dialogo) {
        String values = (String) ComboBox.getSelectedItem();
        String[] id = values.split(" ");
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        try{
            progettoDAO.setReferente(Integer.valueOf(id[0]), cup);
            modelloProgetti.setValueAt(id[0], row, 3);
            dialogo.setVisible(false);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Funzione per l'apertura della finestra di dialogo per l'assegnazione di un progetto ad un laboratorio
     * che abbia lo stesso topic del progetto
     *
     * @param dialogo
     */
    public void ButtonAssegnaProgettoLaboratorio(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = table3.getSelectedRow();
        String Nome_lab = (String) table3.getValueAt(row, 0);
        String Topic = (String) table3.getValueAt(row, 1);
        List<Progetto> progettos = new ArrayList<>();
        try {
            int lung = progettoDAO.conta_progetti();
            progettos = progettoDAO.ottieniprogetti();
            ArrayList<Progetto> progettos1 = new ArrayList<>();
            int i = 0;
            while (i < lung) {
                Progetto p = progettos.get(i);
                if (laboratorioDAO.countProgetti(p.getCUP()) < 3) {
                    {
                        if (p.getTopic().equals(Topic)) {
                            progettos1.add(p);
                        }
                    }
                }
                i++;
            }
            i = 0;
            lung = progettos1.size();
            while (i < lung) {
                Progetto p = progettos1.get(i);
                ComboBox.addItem(p.getCUP() + " " + p.getNome_Prog());
                i++;
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore di DataBase");
        }

    }

    /**
     * bottone per l'apertura della finestra di dialogo per assegnare un referente
     *
     * @param dialogo
     */
    public void ButtonAssegnaReferente(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = table3.getSelectedRow();
        System.out.println(row);
        String Nome_lab = (String) table3.getValueAt(row, 0);

        try{
            DIR = dipendenteDAO.getSenior(Nome_lab);
            int lung = dipendenteDAO.countSenior(Nome_lab);
            for (int i = 0; i < lung; i++) {

                ComboBox.addItem(DIR.get(i).getId_dip() + " " + DIR.get(i).getCognome() + " " + DIR.get(i).getNome());
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * bottone per visualizzare gli scatti di carriera di un dipendente
     */
    public void ButtonVisualizzaCarriera() {
        int row = TabellaDipendenti.getSelectedRow();
        String DataA = (String) TabellaDipendenti.getValueAt(row, 6);
        String Nome = (String) TabellaDipendenti.getValueAt(row, 0);
        String Cognome = (String) TabellaDipendenti.getValueAt(row, 1);
        Date A = Date.valueOf(DataA);
        Date ora = Date.valueOf(LocalDate.now());
        long diff = ora.getTime() - A.getTime();
        long ORE = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        long Anni = ORE / 365;
        LocalDate prova = A.toLocalDate();
        prova = prova.plusYears(3);
        LocalDate senior = A.toLocalDate();
        senior = senior.plusYears(7);
        try{
            Date data_cambio = cambioRuoloDAO.getDataCambio(Integer.valueOf(TabellaDipendenti.getValueAt(row, 2).toString()));
            if (data_cambio != null) {
                if (Anni < 3) {
                    JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDirigente : " + data_cambio);
                } else if (Anni >= 3 && Anni < 7) {
                    JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDipendente Middle : " + prova + "\nDirigente : " + data_cambio);

                } else {
                    JOptionPane.showMessageDialog(null, " " + Nome + " " + Cognome + "\nDipendente junior : " + DataA + "\nDipendente Middle : " + prova + "\nDipendente Senior : " + senior + "\nDirigente : " + data_cambio);

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
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * bottone per promuovere un dipendente
     * ovviamente se si prova a promuovere un dipendente gia dirigente si
     * ha un errore
     *
     * @param modello
     */
    public void ButtonPromuovi(DefaultTableModel modello) {
        int row = TabellaDipendenti.getSelectedRow();
        String value = (String) TabellaDipendenti.getModel().getValueAt(row, 5);
        int id = Integer.valueOf(TabellaDipendenti.getValueAt(row, 2).toString());//prendendo id dip dalla tabella
        try{
            if (value.equals("NO")) {
                dipendenteDAO.promuovi(id);
                modello.setValueAt("SI", row, 5);
                cambioRuoloDAO.setDataPromozione(id);
            } else {
                JOptionPane.showMessageDialog(null, "IMPOSSIBILE PROMUOVERE!");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Bottone per la creazione della finestra di dialogo per assegnare
     * il referente del progetto
     *
     * @param dialogo
     */
    public void ButtonDialogoReferenteProgetti(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        List<Laboratorio> labs = new ArrayList<>();
        try{
            labs = laboratorioDAO.getLaboratori(cup);
            int i = 0;
            List<Dipendente> dips = new ArrayList<>();
            while (i < labs.size()) {
                try{
                    dips.addAll(dipendenteDAO.getSenior(labs.get(i).getNome_Lab()));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null , "Errore nel Database");
                }
                i++;
            }
            i = 0;
            while (i < dips.size()) {
                ComboBox.addItem(dips.get(i).getId_dip() + " " + dips.get(i).getNome() + " " + dips.get(i).getCognome());
                i++;
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Filtro per visualizzare tutti i dipendenti
     */
    public void ButtonTuttiIDipendenti(DefaultTableModel modello) {
        try{
            if (!(modello.getRowCount() == dipendenteDAO.count())) {
                List<Dipendente> Lista = dipendenteDAO.getDir();
                for (int i = 0; i < Lista.size(); i++) {

                    Dipendente d = Lista.get(i);
                    String nome, cognome, Data_N, Laboratorio, DataA;
                    nome = d.getNome();
                    cognome = d.getCognome();
                    Data_N = d.getData_nascita().toString();
                    String id_dip = Integer.toString(d.getId_dip());
                    Laboratorio = d.getLaboratorio();
                    String Dirigente;
                    DataA = d.getAssunzione().toString();
                    if (d.isDirigente()) Dirigente = "SI";
                    else Dirigente = "NO";
                    String[] row = {nome, cognome, id_dip, Data_N, Laboratorio, Dirigente, DataA};
                    modello.addRow(row);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }
    }

    /**
     * Bottone per confermare l'assegnazione del referente di laboratorio
     *
     * @param dialogo
     */
    public void ButtonConfermaReferente(JDialog dialogo) {
        String dirig = (String) ComboBox.getSelectedItem();
        String[] split = dirig.split(" ");
        int i = 0;
        int row = table3.getSelectedRow();
        String Nome_Lab = (String) table3.getValueAt(row, 0);
        try{
            laboratorioDAO.riassegnaDipendente(Nome_Lab, Integer.valueOf(split[0]));
            modelloLaboratori.setValueAt(split[0], row, 3);
            dialogo.setVisible(false);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Bottone per la creazione di un nuovo progetto
     *
     * @param dialogo
     */
    public void ButtonInserimentoProgetto(JDialog dialogo) {
        Progetto p = new Progetto();
        p.setNome_Prog(InterfacciaProgetto.getNomeProgetto());
        p.setTopic(InterfacciaProgetto.getTopicElement());
        try{
            p.setCUP(progettoDAO.GeneraCup());
            int c = progettoDAO.InserisciProgetto(p);
            if (c > 0) {
                dialogo.setVisible(false);
                JOptionPane.showMessageDialog(null, "Inserimento riuscito!");
                String Nome_Prog = p.getNome_Prog();
                String Topic = p.getTopic();
                String cup = String.valueOf(p.getCUP());
                String[] row = {Nome_Prog, cup, Topic};
                modelloProgetti.addRow(row);
                InterfacciaProgetto.setText();
            } else {
                JOptionPane.showMessageDialog(null, "Qualcosa è andato storto!");
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Bottone per l'assegnazione di un progetto ad un laboratorio
     *
     * @param dialogo
     */
    public void ButtonAssegnazioneProgetto(JDialog dialogo) {
        String progs = (String) ComboBox.getSelectedItem();
        String[] prog = progs.split(" ");
        int row = table3.getSelectedRow();
        String Nome_Lab = (String) table3.getValueAt(row, 0);
        try{
            laboratorioDAO.riassegnaProgetto(Nome_Lab, Integer.valueOf(prog[0]));
            modelloLaboratori.setValueAt(prog[0], row, 2);
            dialogo.setVisible(false);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Bottone per aprire la finestra di assegnazione del responsabile di progetto
     *
     * @param dialogo
     */
    public void ButtonAssegnaResponsabile(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        List<Laboratorio> labs = new ArrayList<>();
        try {
            labs = laboratorioDAO.getLaboratori(cup);
            int i = 0;
            List<Dipendente> dips = new ArrayList<>();
            while (i < labs.size()) {
                dips.addAll(dipendenteDAO.getDirigentiLaboratorio(labs.get(i).getNome_Lab()));
                i++;
            }
            i = 0;
            while (i < dips.size()) {
                ComboBox.addItem(dips.get(i).getId_dip() + " " + dips.get(i).getNome() + " " + dips.get(i).getCognome());
                i++;
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

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
    public void ButtonAssegna(JDialog dialog) {
        Lab.removeAllItems();
        try {
            NomiLab = laboratorioDAO.getNomeLab();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore di Database");
        }
        try{
            for (int i = 0; i < laboratorioDAO.countLab(); i++) {
                Lab.addItem(NomiLab[i]);
            }

            int row = TabellaDipendenti.getSelectedRow();
            int column = 2;//numero della colonna id_dip che è chiave esterna nel database
            int columnlab = 4;//numero della colonna del laboratorio
            String value = TabellaDipendenti.getModel().getValueAt(row, column).toString();
            String nomeLaboratorio = TabellaDipendenti.getModel().getValueAt(row, columnlab).toString();
            int index = Integer.valueOf(value);
            dialog.add(Lab, BorderLayout.CENTER);
            dialog.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null , "Errore nel Database");
        }

    }

    /**
     * Funzione per convertire valori in una data
     *
     * @param Giorno
     * @param Mese
     * @param Anno
     * @return
     */
    public java.sql.Date Converti(int Giorno, String Mese, int Anno) {
        int month = ConvertiInNumero(Mese);

        java.sql.Date gdn = new java.sql.Date(Anno, month, Giorno);
        return gdn;
    }

    /**
     * Funzione per convertire i mesi in numero
     *
     * @param mese
     * @return
     */
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

    /**
     * Funzione per aggiornare i giorni in base al mese scelto
     */
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

    /**
     * funzione per la creazione del modello dipendenti
     *
     * @return
     */
    public DefaultTableModel CreaModelloDipendenti() {
        modelloDipendenti = new DefaultTableModel();
        modelloDipendenti.addColumn("Nome");
        modelloDipendenti.addColumn("Cognome");
        modelloDipendenti.addColumn("id_Dip");
        modelloDipendenti.addColumn("Data di Nascita");
        modelloDipendenti.addColumn("Laboratorio");
        modelloDipendenti.addColumn("Dirigente");
        modelloDipendenti.addColumn("Data di Assunzione");
        modelloDipendenti.fireTableDataChanged();
        return modelloDipendenti;
    }

    /**
     * funzione per la creazione del modello progetti
     */

    public DefaultTableModel CreazioneModelloProgetti() {
        modelloProgetti.addColumn("Nome Progetto");
        modelloProgetti.addColumn("CUP");
        modelloProgetti.addColumn("Topic");
        modelloProgetti.addColumn("Referente");
        modelloProgetti.addColumn("Responsabile");
        modelloProgetti.fireTableDataChanged();
        return modelloProgetti;
    }

    /**
     * funzione per la creazione del modello laboratorio
     *
     * @return
     */
    public DefaultTableModel CreaModelloLaboratori() {
        modelloLaboratori.addColumn("Nome Laboratorio");
        modelloLaboratori.addColumn("Topic");
        modelloLaboratori.addColumn("Nome Progetto");
        modelloLaboratori.addColumn("Referente");
        return modelloLaboratori;
    }

}


