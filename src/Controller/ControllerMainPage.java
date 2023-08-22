package Controller;

import GUI.GUImain;
import Model.Dipendente;

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import DAO.*;
import Model.Laboratorio;
import Model.Progetto;

public class ControllerMainPage {
    private final ProgettoDAO progettoDAO;
    private final DipendenteDAO dipendenteDAO;
    private final LaboratorioDAO laboratorioDAO;
    private final GUImain guImain;
    private final Dipendente dipendenteVuoto = new Dipendente();
    private final Laboratorio laboratorioVuoto = new Laboratorio();
    private final Progetto progettoVuoto = new Progetto();

    public ControllerMainPage(DipendenteDAO dipendenteDAO, ProgettoDAO progettoDAO, LaboratorioDAO laboratorioDAO, GUImain guImain){
        this.dipendenteDAO = dipendenteDAO;
        this.laboratorioDAO = laboratorioDAO;
        this.progettoDAO = progettoDAO;
        this.guImain = guImain;
    }

    /**
     * Metodo che si occupa di creare e aggiungere al database
     * un nuovo dipendente con i dati fornitogli dall'utente
     *
     * @param nome
     * @param cognome
     * @param dir
     * @param datadiN
     */
    public void aggiungiDipendente(String nome, String cognome, boolean dir, Date dataAssunzione, Date datadiN, Date dataPromozione) {

        try {
            Dipendente dipendente = new Dipendente(nome, cognome, dir, dataAssunzione, datadiN, dataPromozione);
            dipendenteDAO.insertDipendente(dipendente);
            Laboratorio laboratorio = new Laboratorio();
            dipendente.setLaboratorio(laboratorio);
            guImain.aggiungiDipendente(dipendente);
            guImain.showInfoMessage("Dipendente Assunto!");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("OPS! Qualcosa è andato storto");
        }
    }

    /**
     * Metodo che inserisce nella tabella tutti i dipendenti
     * presenti nel database.
     * Funzione che permette quindi, nel caso in cui sia stato premuto il pulsante
     * per vedere solo i dipendenti assegnati, di visualizzarli di nuovo tutti
     *
     * @throws Exception
     */

    public void mostraTuttiDipendenti() throws Exception {
        List<Dipendente> dipendenti = dipendenteDAO.getDipendenti();
        guImain.setDipendenti(dipendenti);
    }

    /**
     * Metodo che gestisce il licenziamento di un dipendente
     * Se un dipendente è referente di laboratorio, allora
     * il metodo impedisce il licenziamento e manda un
     * messaggio di errore
     *
     * @param dipendente
     */
    public void licenziaDipendente(Dipendente dipendente) {
        try {
            if (dipendente.getId() != laboratorioDAO.getIdReferente(dipendente.getLaboratorio())) {
                dipendenteDAO.removeDipendente(dipendente);
                guImain.showInfoMessage("Dipendente Licenziato! Poverino :(");
                guImain.rimuoviDipendente(dipendente);
                guImain.aggiornaTabelleDopoLicenziamento(dipendente);
            } else {
                guImain.showErrorMessage("Il Dipendente è Responsabile di Laboratorio");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che riceve il dipendente selezionato dall'utente e
     * a quale laboratorio desidera assegnarlo, esegue l'assegnazione
     * e aggiorna le tabelle
     * @param dipendente
     * @param laboratorio
     */
    public void assegnaLaboratorio(Dipendente dipendente, Laboratorio laboratorio) {
        try {
            dipendenteDAO.setLaboratorio(laboratorio, dipendente);
            guImain.aggiornaLaboratorioDipendente(laboratorio, dipendente);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che gestisce l'eliminazione di un laboratorio
     * selezionato dall'utente
     *
     * @param laboratorio
     */
    public void eliminaLaboratorio(Laboratorio laboratorio) {
        try {
            laboratorioDAO.rimuovi(laboratorio);
            guImain.rimuoviLaboratorio(laboratorio);
            guImain.aggiornaTabelleDopoEliminazioneLaboratorio(laboratorio);
            guImain.showInfoMessage("Laboratorio Eliminato");
        } catch (Exception e) {
            guImain.showErrorMessage("Errore nel Database");
            e.printStackTrace();
        }
    }

    /**
     * Metodo che si occupa della creazione e dell'inserimento di un nuovo laboratorio
     * all'interno del database con i dati fornitogli dall'utente,
     * occupandosi di aggiornare le tabelle
     * @param nome
     * @param topic
     */

    public void nuovoLaboratorio(String nome , Laboratorio.Topic topic) {
        try {
            Laboratorio laboratorio = new Laboratorio(nome , topic);
            laboratorioDAO.inserisci(laboratorio);
            laboratorio.setReferente(dipendenteVuoto);
            laboratorio.setProgetto(progettoVuoto);
            guImain.aggiungiLaboratorio(laboratorio);
            guImain.showInfoMessage("Inserimento Riuscito!");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che si occupa di eliminare il progetto selezionato dall'utente
     * sia dalle tabelle che dal database
     *
     * @param progetto
     */

    public void eliminaProgetto(Progetto progetto) {
        try {
            progettoDAO.rimuovi(progetto);
            guImain.aggiornaTabelleDopoEliminazioneProgetto(progetto);
            guImain.rimuoviProgetto(progetto);
            guImain.showInfoMessage("Progetto Eliminato");
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che si occupa del degradamento di un database.
     * Il metodo controlla che sia possibile effettuare l'operazione e
     * in particolare verifica che il dipendente selezionato:
     * -Sia un dirigente
     * -Non sia responsabile di progetto
     * In caso non si verifichi una delle due cose, viene mandato
     * all'utente un messaggio di errore
     *
     * @param dipendente
     */

    public void degrada(Dipendente dipendente) {
        try {
            if (dipendente.isDirigente()) {
                Progetto progetto = dipendente.getLaboratorio().getProgetto();
                if(progetto != null){
                    if(progetto.getResponsabile().getId() == dipendente.getId()){
                        guImain.showErrorMessage("IMPOSSIBILE DEGRADARE");
                    }else{
                        dipendenteDAO.degrada(dipendente);
                        guImain.degradaDipendente(dipendente);
                        guImain.showInfoMessage("DEGRADATO!");
                    }
                }else {
                    dipendenteDAO.degrada(dipendente);
                    guImain.degradaDipendente(dipendente);
                    guImain.showInfoMessage("DEGRADATO!");
                }
            } else {
                guImain.showErrorMessage("IMPOSSIBILE DEGRADARE!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }


    /**
     * Metodo che rende il dipendente selezionato responsabile
     * del progetto assegnato al suo laboratorio di appartenenza.
     * Il metodo si occupa di aggiornare sia le tabelle che il database.
     *
     * @param dipendente
     * @param progetto
     */
    public void setResponsabile(Dipendente dipendente, Progetto progetto) {
        try {
            progettoDAO.setResponsabile(dipendente, progetto);
            guImain.showInfoMessage("Modifica Riuscita!");
            guImain.aggiornaResponsabileProgetto(progetto, dipendente);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che rende il dipendente selezionato referente
     *  del progetto assegnato al suo laboratorio di appartenenza.
     * Il metodo si occupa di aggiornare sia le tabelle che il database.
     *
     * @param dipendente
     * @param progetto
     */
    public void setReferenteProgetto(Dipendente dipendente, Progetto progetto) {
        try {
            progettoDAO.setReferente(dipendente, progetto);
            guImain.showInfoMessage("Aggiornamento Riuscito");
            guImain.aggiornaReferenteProgetto(progetto, dipendente);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che permette la promozione di un dipendente a dirigente.
     * Un dipendente, per essere promosso, non deve essere già un dirigente,
     * e nel caso già lo sia viene mandato all'utente un messaggio di errore
     * @param dipendente
     */
    public void promuovi(Dipendente dipendente) {
        try {
            if (!dipendente.isDirigente()) {
                dipendenteDAO.promuovi(dipendente);
                guImain.promuoviDipendente(dipendente);
                guImain.showInfoMessage("Promosso!");
            } else {
                guImain.showErrorMessage("IMPOSSIBILE PROMUOVERE!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che si occupa dell'aggiornamento di un progetto dopo che un laboratorio
     * viene eliminato, o viene assegnato un altro progetto a quel laboratorio.
     * In particolare, verifica se il referente o il responsabile del progetto
     * appartenessero a quel laboratorio e, nel caso, assegna al suo posto un
     * dipendente vuoto, ovvero un dipendente con id 0.
     *
     * @param progetto
     * @param laboratorio
     */
    public void aggiornaProgettoLaboratorio(Progetto progetto, Laboratorio laboratorio) {
        try {
            Dipendente referente = laboratorio.getProgetto().getReferente();
            Dipendente responsabile = laboratorio.getProgetto().getResponsabile();
            if (referente != null) {
                if (referente.getLaboratorio() != null) {
                    if (referente.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                        progettoDAO.setReferente(null, laboratorio.getProgetto());
                        laboratorio.getProgetto().setReferente(dipendenteVuoto);
                    }
                }

            }

            if (responsabile != null) {
                if (responsabile.getLaboratorio() != null) {
                    if (responsabile.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                        progettoDAO.setResponsabile(null, laboratorio.getProgetto());
                        laboratorio.getProgetto().setResponsabile(dipendenteVuoto);
                    }
                }

            }


            laboratorioDAO.assegnaProgetto(laboratorio, progetto);
            guImain.showInfoMessage("Aggiornamento Riuscito");
            guImain.aggiornaProgettoLaboratorio(laboratorio, progetto);

        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     *Metodo che si occupa della creazione di un nuovo progetto
     *e dell'inseriment
     *
     * @param
     */

    public Progetto nuovoProgetto(String nome){
        try {
            Progetto progetto = new Progetto(nome);
            progettoDAO.inserisci(progetto);
            progetto.setReferente(dipendenteVuoto);
            progetto.setResponsabile(dipendenteVuoto);
            return progetto;
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
        return null;
    }

    /**
     * funzione che si occupa di settare il referente del laboratorio selezionato
     *
     * @param dipendente
     * @param laboratorio
     */

    public void setReferenteLaboratorio(Dipendente dipendente, Laboratorio laboratorio) {
        try {
            laboratorioDAO.assegnaDipendente(laboratorio, dipendente);
            guImain.aggiornaReferenteLaboratorio(laboratorio, dipendente);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }



    /**
     * funzione che si occupa della visualizzazione degli scatti di carriera di un dipendente
     *
     * @param dipendente
     */

    public void getVisualizzaCarriera(Dipendente dipendente) {
        Date ora = Date.valueOf(LocalDate.now());
        long differenzaDiDate = ora.getTime() - dipendente.getDataAssunzione().getTime();
        long Anni = (TimeUnit.DAYS.convert(differenzaDiDate, TimeUnit.MILLISECONDS)) / 365;
        Date dataAssunzione = (Date) dipendente.getDataAssunzione();
        LocalDate dataDipendenteMiddle = dataAssunzione.toLocalDate();
        dataDipendenteMiddle = dataDipendenteMiddle.plusYears(3);
        LocalDate senior = dataAssunzione.toLocalDate();
        senior = senior.plusYears(7);
        Date dataCambio = (Date) dipendente.getDataPromozione();
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

    public void rimuoviReferente(Progetto progetto) {
        progetto.setReferente(dipendenteVuoto);
    }

    public void rimuoviResponsabile(Progetto progetto) {
        progetto.setResponsabile(dipendenteVuoto);
    }

    public void rimuoviLaboratorio(Dipendente dipendente) {
        dipendente.setLaboratorio(laboratorioVuoto);
    }

    public void rimuoviProgetto(Laboratorio laboratorio) {
        laboratorio.setProgetto(progettoVuoto);
    }



    public void setLaboratoriProgetto(List<Progetto> progetti , List<Laboratorio> laboratori){
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

    public void setReferenteProgetti(List<Progetto> progetti , List<Dipendente> dipendenti){
        for (Progetto progetto : progetti) {
            for (Dipendente dipendente : dipendenti) {
                if (progetto.getReferente().getId() == dipendente.getId()) {
                    progetto.setReferente(dipendente);
                }
            }
        }
    }

    public void setResponsabileProgetti(List<Progetto> progetti , List<Dipendente> dipendenti){
        for (Progetto progetto : progetti) {
            for (Dipendente dipendente : dipendenti) {
                if (progetto.getResponsabile().getId() == dipendente.getId()) {
                    progetto.setResponsabile(dipendente);
                }
            }
        }
    }

    public void setReferenteLaboratori(List<Laboratorio> laboratori , List<Dipendente> dipendenti){
        for (Laboratorio laboratorio : laboratori) {
            for (Dipendente dipendente : dipendenti) {
                if (laboratorio.getReferente().getId() == dipendente.getId()) {
                    laboratorio.setReferente(dipendente);
                }
            }
        }
    }

    public void setProgettoLaboratori(List<Laboratorio> laboratori , List<Progetto> progetti){
        for (Laboratorio laboratorio : laboratori) {
            for (Progetto progetto : progetti) {
                if (progetto.getCup() == laboratorio.getProgetto().getCup()) {
                    laboratorio.setProgetto(progetto);
                }
            }
        }
    }

    public void setLaboratorioDipendenti(List<Dipendente> dipendenti , List<Laboratorio> laboratori){
        for (Dipendente dipendente : dipendenti) {
            for (Laboratorio laboratorio : laboratori) {
                if (laboratorio.getNome() == dipendente.getLaboratorio().getNome()) {
                    dipendente.setLaboratorio(laboratorio);
                }
            }
        }
    }

    public void rimuoviDipendenti(List<Dipendente> dipendenti){
        int i = 0;
        while (i < dipendenti.size()) {
            if (dipendenti.get(i).getLaboratorio().getNome() != null) {
                guImain.rimuoviDipendente(dipendenti.get(i));
            } else {
                i++;
            }
        }
    }

    public List<Dipendente> listaReferentiProgettoPossibili(List<Dipendente> dipendenti , Progetto progetto){
        TimeUnit time = TimeUnit.DAYS;
        List<Dipendente> dipendentiScelti = new ArrayList<>();
        for (Dipendente dipendente : dipendenti) {
            for (Laboratorio laboratorio : progetto.getLaboratori()) {
                if (dipendente.getLaboratorio().getNome() != null) {
                    if (dipendente.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                        if (time.convert(Date.valueOf(LocalDate.now()).getTime() - dipendente.getDataAssunzione().getTime(), TimeUnit.MILLISECONDS) / 365 >= 7)
                            dipendentiScelti.add(dipendente);
                    }
                }
            }

        }
        return dipendentiScelti;
    }

    public List<Dipendente> listaReferentiLaboratorioPossibili(List<Dipendente> dipendenti , Laboratorio laboratorio){
        TimeUnit time = TimeUnit.DAYS;
        List<Dipendente> dipendentiScelti = new ArrayList<>();
        for (Dipendente dipendente : dipendenti) {
            if (dipendente.getLaboratorio().getNome() != null) {
                if (dipendente.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                    if (time.convert(Date.valueOf(LocalDate.now()).getTime() - dipendente.getDataAssunzione().getTime(), TimeUnit.MILLISECONDS) / 365 >= 7)
                        dipendentiScelti.add(dipendente);
                }
            }
        }
        return dipendentiScelti;
    }

    public List<Dipendente> listaResponsabiliProgettoPossibili(List<Dipendente> dipendenti , Progetto progetto){
        List<Dipendente> dipendentiScelti = new ArrayList<>();
        for (Dipendente dipendente : dipendenti) {
            for (Laboratorio laboratorio : progetto.getLaboratori()) {
                if (dipendente.getLaboratorio().getNome() != null) {
                    if (dipendente.getLaboratorio().getNome().equals(laboratorio.getNome())) {
                        if (dipendente.isDirigente())
                            dipendentiScelti.add(dipendente);
                    }
                }
            }
        }
        return dipendentiScelti;
    }

}
