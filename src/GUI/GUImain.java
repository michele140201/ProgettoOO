package GUI;

import Controller.ControllerMainPage;
import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;

import java.sql.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        assumiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        //Action Listener del Pannello Visual
        licenziaButton.addActionListener(event-> controllerMainPage.licenziaDipendente(getDipendenteSelezionato()));


        promuoviButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ButtonPromuovi(modelloDipendenti);
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
     * funzione per assegnare un nuovo laboratorio
     *
     * @param dialogo
     * @param modello
     */


    /**
     * Elimina un laboratorio
     */
    public void ButtonEliminaSede() {
        int row = tabellaLaboratori.getSelectedRow();
        String nome = String.valueOf(tabellaLaboratori.getValueAt(row, 0));//ottengo il nome del laboratorio
        int i = controllerMainPage.rimuovoLaboratorio(nome);
        if (i > 0) modelloLaboratori.removeRow(row);
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
    }

    /**
     * Funzione del bottone di conferma della finestra di dialogo laboratorio
     *
     * @param dialogo
     */
    public void ButtonCreaNuovoLaboratorio(JDialog dialogo) {
        Laboratorio laboratorio = nuovoLab.getLaboratorio();
        int i = controllerMainPage.creaLaboratorio(dialogo, laboratorio);
        if (i > 0) {
            Object[] row = {laboratorio.getNome(), laboratorio.getTopic(), "Non Assegnato", "Non Assegnato"};
            modelloLaboratori.addRow(row);
            nuovoLab.clear();
        }

    }

    /**
     * Funzione del bottone per eliminare un progetto dal db e dalla tabella e aggiornare
     * la tabella laboratorio
     */
    public void ButtonEliminaProgetto() {
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf(TabellaProgetti.getValueAt(row, 1).toString());//prendo il cup dalla tabella
        int i = controllerMainPage.EliminaProgetto(cup);
        if (i > 0) {
            modelloProgetti.removeRow(row);
            fetchProgetti(modelloProgetti);
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
        int idDip = Integer.valueOf(TabellaDipendenti.getValueAt(row, 2).toString());//prendendo id_dip dalla tabella
        int i = controllerMainPage.Degrada(value, idDip);
        if (i > 0) {
            modello.setValueAt("NO", row, 5);
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
     * Funzione del bottone per inserire nella tabella il responsabile di progetto selezionato
     *
     * @param dialogo
     */

    public void ButtonConfermaResponsabile(JDialog dialogo) {
        String values = (String) ComboBox.getSelectedItem();
        String[] id = values.split(" ");
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        int idDip = Integer.valueOf(id[0]);
        int i = controllerMainPage.setResponsabile(idDip, cup);
        if (i > 0) {
            modelloProgetti.setValueAt(idDip, row, 4);
            dialogo.setVisible(false);
        }
    }

    /**
     * Funzione del bottone per confermare il referente dei progetti selezionato
     *
     * @param dialogo
     */
    public void ButtonAssegnaReferenteProgetti(JDialog dialogo) {
        String values = (String) ComboBox.getSelectedItem();
        String[] id = values.split(" ");
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        int i = controllerMainPage.setReferente(Integer.valueOf(id[0]), cup);
        if (i > 0) {
            modelloProgetti.setValueAt(id[0], row, 3);
            dialogo.setVisible(false);
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

    /**
     * Funzione del bottone per l'apertura della finestra di dialogo per assegnare un referente
     *
     * @param dialogo
     */
    public void ButtonAssegnaReferente(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = tabellaLaboratori.getSelectedRow();
        System.out.println(row);
        String NomeLab = (String) tabellaLaboratori.getValueAt(row, 0);
        List<Dipendente> Referenti = controllerMainPage.getSenior(NomeLab);
        if (Referenti.size() > 0) {
            for (int i = 0; i < Referenti.size(); i++) {

                ComboBox.addItem(Referenti.get(i).getId() + " " + Referenti.get(i).getCognome() + " " + Referenti.get(i).getNome());
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nessun referente da assegnare");
        }
    }

    /**
     * Funzione del bottone per visualizzare gli scatti di carriera di un dipendente
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
        Date dataCambio = controllerMainPage.getDataPromozione(Integer.valueOf(TabellaDipendenti.getValueAt(row, 2).toString()));
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

    /**
     * Funzione del bottone per promuovere un dipendente
     * ovviamente se si prova a promuovere un dipendente gia dirigente si
     * ha un errore
     *
     * @param modello
     */
    public void ButtonPromuovi(DefaultTableModel modello) {
        int row = TabellaDipendenti.getSelectedRow();
        String value = (String) TabellaDipendenti.getModel().getValueAt(row, 5);
        int id = Integer.valueOf(TabellaDipendenti.getValueAt(row, 2).toString());//prendendo id dip dalla tabella
        int i = controllerMainPage.promuovi(id, value);
        if (i > 0) modello.setValueAt("SI", row, 5);
    }

    /**
     * Funzione del bottone per la creazione della finestra di dialogo per assegnare
     * il referente del progetto
     *
     * @param dialogo
     */
    public void ButtonDialogoReferenteProgetti(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        List<Dipendente> dips = controllerMainPage.getReferenti(cup);
        int i = 0;
        if (dips.size() > 0) {
            while (i < dips.size()) {
                ComboBox.addItem(dips.get(i).getId() + " " + dips.get(i).getNome() + " " + dips.get(i).getCognome());
                i++;
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nessun Referente da Assegnare");
        }
    }

    /**
     * Filtro per visualizzare tutti i dipendenti
     */
    public void ButtonTuttiIDipendenti(DefaultTableModel modello) {
        List<Dipendente> Lista = controllerMainPage.getDipendentiAssegnare();
        if (!(modello.getRowCount() == Lista.size())) {
            for (int i = 0; i < Lista.size(); i++) {
                Dipendente d = Lista.get(i);
                String nome, cognome, DataN, Laboratorio, DataA;
                nome = d.getNome();
                cognome = d.getCognome();
                DataN = d.getDataNascita().toString();
                String idDip = Integer.toString(d.getId());
                Laboratorio = d.getLaboratorio();
                String Dirigente;
                DataA = d.getDataAssunzione().toString();
                if (d.isDirigente()) Dirigente = "SI";
                else Dirigente = "NO";
                String[] row = {nome, cognome, idDip, DataN, Laboratorio, Dirigente, DataA};
                modello.addRow(row);
            }
        }
    }

    /**
     * Funzione del bottone per confermare l'assegnazione del referente di laboratorio
     *
     * @param dialogo
     */
    public void ButtonConfermaReferente(JDialog dialogo) {
        String dirig = (String) ComboBox.getSelectedItem();
        String[] split = dirig.split(" ");
        int row = tabellaLaboratori.getSelectedRow();
        String NomeLab = (String) tabellaLaboratori.getValueAt(row, 0);
        int idDip = Integer.valueOf(split[0]);

        int i = controllerMainPage.riassegnaDipendente(NomeLab, idDip);
        if (i > 0) {
            modelloLaboratori.setValueAt(split[0], row, 3);
            dialogo.setVisible(false);
        }
    }

    /**
     * Funzione del bottone per la creazione di un nuovo progetto
     *
     * @param dialogo
     */
    public void ButtonInserimentoProgetto(JDialog dialogo) {
        Progetto progetto = InterfacciaProgetto.getProgetto();
        controllerMainPage.inserisciProgetto(progetto);
        dialogo.setVisible(false);
            JOptionPane.showMessageDialog(null, "Inserimento riuscito!");
            String NomeProg = progetto.getNome();
            String Topic = progetto.getTopic();
            String cup = String.valueOf(progetto.getCup());
            String[] row = {NomeProg, cup, Topic};
            modelloProgetti.addRow(row);
            InterfacciaProgetto.clear();
            //todo
    }

    /**
     * Funzione del bottone per l'assegnazione di un progetto ad un laboratorio
     *
     * @param dialogo
     */
    public void ButtonAssegnazioneProgetto(JDialog dialogo) {
        String progs = (String) ComboBox.getSelectedItem();
        String[] prog = progs.split(" ");
        int idDip = Integer.valueOf(prog[0]);
        int row = tabellaLaboratori.getSelectedRow();
        String NomeLab = (String) tabellaLaboratori.getValueAt(row, 0);
        int i = controllerMainPage.assegnaProgetto(NomeLab, idDip);
        if (i > 0) {
            modelloLaboratori.setValueAt(prog[0], row, 2);
            dialogo.setVisible(false);
        }

    }

    /**
     * Funzione del bottone per aprire la finestra di assegnazione del responsabile di progetto
     *
     * @param dialogo
     */
    public void ButtonAssegnaResponsabile(JDialog dialogo) {
        ComboBox.removeAllItems();
        int row = TabellaProgetti.getSelectedRow();
        int cup = Integer.valueOf((String) TabellaProgetti.getValueAt(row, 1));
        List<Dipendente> dips = controllerMainPage.getResponsabiliPossibili(cup);
        if (dips.size() > 0) {
            int i = 0;
            while (i < dips.size()) {
                ComboBox.addItem(dips.get(i).getId() + " " + dips.get(i).getNome() + " " + dips.get(i).getCognome());
                i++;
            }
            dialogo.add(ComboBox, BorderLayout.CENTER);
            dialogo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nessun Resposnabile da Assegnare");
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
        TabellaDipendenti = creaTabella(PannelloDipendenti, modello, "Dipendenti");
    }

    private void inizializzaTabellaLaboratori(){
        ModelloLaboratori modello = new ModelloLaboratori();
        tabellaLaboratori = creaTabella(PannelloLaboratori, modello, "Laboratori");
    }
    private void inizializzaTabellaProgetti(){
        ModelloProgetti modello = new ModelloProgetti();
        TabellaProgetti = creaTabella(PannelloProgetti, modello, "Progetti");
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
            Dipendente dipendente = getDipendenteSelezionato();
            controllerMainPage.AssegnaLaboratorio(dipendente)
                });
        dialogoAssegnazioneDipendenteLaboratorio = creaDialogo( buttonConferma, "A quale laboratorio vuoi assegnarlo?");
        assegnaButton.addActionListener(event-> apriDialogoAssegnaDipendenteLaboratorio(dialogoAssegnazioneDipendenteLaboratorio));
        ConfermaAssegnazioneLaboratorio.addActionListener(event->AssegnazioneLaboratorio(dialogoLaboratorio, modelloDipendenti));

    }


    private void AssegnazioneLaboratorio(JDialog dialogo, DefaultTableModel modello) {
        String NuovoLab = (String) Lab.getSelectedItem();
        int row = TabellaDipendenti.getSelectedRow();
        String value = TabellaDipendenti.getModel().getValueAt(row, 2).toString();
        int idDip = Integer.valueOf(value);
        String NomeLab = TabellaDipendenti.getModel().getValueAt(row, 4).toString();
        int i = controllerMainPage.AssegnaLaboratorio(idDip, NomeLab, dialogo, NuovoLab);
        if (i > 0) {
            PannelloDipendenti.setViewportView(TabellaDipendenti);
            JOptionPane.showMessageDialog(null, "Dipendente Assegnato");
            dialogo.setVisible(false);
            modello.setValueAt(NuovoLab, row, 4);
        }
    }
    private ModelloDipendenti getModelloDipendenti(){
        return (ModelloDipendenti) TabellaDipendenti.getModel();
    }

    private ModelloLaboratori getModelloLaboratori(){
        return (ModelloLaboratori) tabellaLaboratori.getModel();
    }

    private Dipendente getDipendenteSelezionato(){
        ModelloDipendenti modello = getModelloDipendenti();
        return modello.getDipendente(TabellaDipendenti.getSelectedRow());
    }
    public void showErrorMessage(String messaggio){
        JOptionPane.showMessageDialog(this , messaggio);
    }
    public void showInfoMessage(String messaggio){
        JOptionPane.showMessageDialog(this , messaggio);
    }
    private List<Progetto> getProgettiIdonei(){
        //todo
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


