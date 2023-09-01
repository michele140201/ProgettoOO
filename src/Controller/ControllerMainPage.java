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

/**
 * Classe che si occupa di gestire tutte le operazioni che coinvolgono l'interfaccia grafica
 */

public class ControllerMainPage {
    private final ProgettoDAO progettoDAO;
    private final DipendenteDAO dipendenteDAO;
    private final LaboratorioDAO laboratorioDAO;
    private final GUImain guImain;
    private final Dipendente dipendenteVuoto = new Dipendente();
    private final Laboratorio laboratorioVuoto = new Laboratorio();
    private final Progetto progettoVuoto = new Progetto();

    /**
     * Costruttore che setta i dao con quelli fornitogli
     * @param dipendenteDAO oggetto per gestire i dati della tabella dipendente
     * @param progettoDAO oggetto per gestire i dati della tabella progetto
     * @param laboratorioDAO oggetto per gestire i dati della tabella laboratorio
     * @param guImain interfaccia grafica del programma
     */

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
     * @param nome nome del dipendente
     * @param cognome cognome del dipendente
     * @param dir se il dipendente assunto è dirigente
     * @param datadiN data di nascita del dipendente
     */
    public void nuovoDipendente(String nome, String cognome, boolean dir, Date dataAssunzione, Date datadiN, Date dataPromozione) {

        try {
            if(nome.isEmpty() || cognome.isEmpty()) {
                guImain.showErrorMessage("Dati inseriti errati");
                throw new RuntimeException("Dati inseriti errati");
            }else{
                Dipendente dipendente = new Dipendente(nome, cognome, dir, dataAssunzione, datadiN, dataPromozione);
                dipendenteDAO.insertDipendente(dipendente);
                Laboratorio laboratorio = new Laboratorio();
                dipendente.setLaboratorio(laboratorio);
                guImain.aggiungiDipendente(dipendente);
                guImain.showInfoMessage("Dipendente Assunto!");

            }

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
     * @throws Exception eccezione nel recupero dati dal database
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
     * @param dipendente dipendente da licenziare
     */
    public void licenziaDipendente(Dipendente dipendente) {
        try {
            if (!dipendente.equals(dipendente.getLaboratorio().getReferente())) {
                dipendenteDAO.removeDipendente(dipendente);
                guImain.showInfoMessage("Dipendente Licenziato! Poverino :(");
                guImain.rimuoviDipendente(dipendente);
                guImain.aggiornaTabelleDopoLicenziamento(dipendente);
            } else {
                guImain.showErrorMessage("Il Dipendente è referente di Laboratorio");
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
     * @param dipendente dipendente selezionato
     * @param laboratorio laboratorio scelto
     */
    public void assegnaLaboratorio(Dipendente dipendente, Laboratorio laboratorio) {
        try {
            if(dipendente.isReferenteLaboratorio()){
                guImain.showErrorMessage("Impossibile effettuare l'assegnazione");
            }else{
                dipendenteDAO.setLaboratorio(laboratorio, dipendente);
                guImain.aggiornaLaboratorioDipendente(laboratorio, dipendente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }

    /**
     * Metodo che gestisce l'eliminazione di un laboratorio
     * selezionato dall'utente
     *
     * @param laboratorio laboratorio da eliminare
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
     * @param nome nome del laboratorio
     * @param topic topic del laboratorio
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
     * @param progetto progetto da eliminare
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
     * @param dipendente dipendente da degradare
     */

    public void degrada(Dipendente dipendente) {
        try {
            if (dipendente.isDirigente()) {
                    if(dipendente.isResponsabileProgetto()){
                        guImain.showErrorMessage("IMPOSSIBILE DEGRADARE");
                    }else{
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
     * @param dipendente dipendente che diventa responsabile
     * @param progetto progetto selezionato
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
     * @param dipendente dipendente che diventa referente
     * @param progetto progetto selezionato
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
     * @param dipendente dipendente da promuovere
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
     * @param progetto progetto da aggiornare
     * @param laboratorio laboratorio eliminato
     */
    public void aggiornaProgettoLaboratorio(Progetto progetto, Laboratorio laboratorio) {
        try {
            Dipendente referente = laboratorio.getProgetto().getReferente();
            Dipendente responsabile = laboratorio.getProgetto().getResponsabile();
            if(referente != null){
                if (referente.getLaboratorio().equals(laboratorio)) {
                    progettoDAO.setReferente(null, laboratorio.getProgetto());
                    laboratorio.getProgetto().setReferente(dipendenteVuoto);
                }
            }

            if(responsabile != null){
                if (responsabile.getLaboratorio().equals(laboratorio)) {
                    progettoDAO.setResponsabile(null, laboratorio.getProgetto());
                    laboratorio.getProgetto().setResponsabile(dipendenteVuoto);
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
     *e dell'inserimento di questo nei database e nelle tabelle
     *
     * @param nome nome del progetto
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
     * Metodo che assegna il dipendente selezionato come referente del laboratorio
     * selezionato
     *
     * @param dipendente dipendente che diventa referente
     * @param laboratorio laboratorio selezionato
     */

    public void setReferenteLaboratorio(Dipendente dipendente, Laboratorio laboratorio) {
        try {
            laboratorioDAO.assegnaReferente(laboratorio, dipendente);
            guImain.aggiornaReferenteLaboratorio(laboratorio, dipendente);
        } catch (Exception e) {
            e.printStackTrace();
            guImain.showErrorMessage("Errore nel Database");
        }
    }



    /**
     *Metodo che si occupa di mostrare all'utente gli scatti di carriera di un dipendente.
     *Il metodo divide gli utenti in due categorie :
     * i dirigenti e i non dirigenti, per entrambi gli scatti sono identici nel layout, solo che i dirigenti
     * hanno anche la data di promozione a dirigente nello scatto di carriera
     *
     * @param dipendente dipendente selezionato
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

    /**
     * Metodo che serve a rimuovere il referente di progetto,
     * settandolo con il dipendente vuoto, ovvero un dipendente con id 0
     * @param progetto progetto a cui rimuovere il referente
     */
    public void rimuoviReferente(Progetto progetto) {
        progetto.setReferente(dipendenteVuoto);
    }

    /**
     * Metodo che serve a rimuovere il responsabile di progetto,
     * settandolo con il dipendente vuoto, ovvero un dipendente con id 0
     * @param progetto progetto a cui rimuovere il responsabile
     */

    public void rimuoviResponsabile(Progetto progetto) {
        progetto.setResponsabile(dipendenteVuoto);
    }

    /**
     * Metodo che serve a rimuovere il laboratorio assegnato ad un
     * dipendente settandolo con il laboratorio vuoto,
     * ovvero un laboratorio con nome null
     * * @param dipendente dipendente a cui rimuovere il laboratorio
     */

    public void rimuoviLaboratorio(Dipendente dipendente) {
        dipendente.setLaboratorio(laboratorioVuoto);
    }

    /**
     * Metodo che serve a rimuovere il progetto assegnato ad un
     * laboratorio settandolo con il progetto vuoto,
     * ovvero un progetto con cup 0
     * @param laboratorio laboratorio a cui rimuovere il progetto
     */

    public void rimuoviProgetto(Laboratorio laboratorio) {
        laboratorio.setProgetto(progettoVuoto);
    }

    /**
     * Metodo che si occupa di trovare e settare per ogni progetto i laboratori
     * ai quali è assegnato
     * @param progetti progetti del database
     * @param laboratori laboratori del database
     */

    public void setLaboratoriProgetto(List<Progetto> progetti , List<Laboratorio> laboratori){
        List<Laboratorio> laboratoriProgetto = new ArrayList<>();
        for (Progetto progetto : progetti) {
            for (Laboratorio laboratorio : laboratori) {
                if (progetto.getCup() == laboratorio.getProgetto().getCup()) {
                    laboratoriProgetto.add(laboratorio);
                }
            }
            progetto.setLaboratori(laboratoriProgetto);
        }
    }

    /**
     * Metodo che si occupa di trovare e settare per ogni progetto
     * il referente a lui assegnato
     * @param progetti progetti del database
     * @param dipendenti dipendenti del database
     */

    public void setReferentiProgetto(List<Progetto> progetti , List<Dipendente> dipendenti){
        for (Progetto progetto : progetti) {
            for (Dipendente dipendente : dipendenti) {
                if (progetto.getReferente().getId() == dipendente.getId()) {
                    progetto.setReferente(dipendente);
                }
            }
        }
    }

    /**
     *  Metodo che si occupa di trovare e settare per ogni progetto
     *  il responsabile a lui assegnato
     * @param progetti progetti del database
     * @param dipendenti dipendenti del database
     */

    public void setResponsabiliProgetto(List<Progetto> progetti , List<Dipendente> dipendenti){
        for (Progetto progetto : progetti) {
            for (Dipendente dipendente : dipendenti) {
                if (progetto.getResponsabile().getId() == dipendente.getId()) {
                    progetto.setResponsabile(dipendente);
                }
            }
        }
    }

    /**
     *  Metodo che si occupa di trovare e settare per ogni laboratorio
     *  il referente a lui assegnato
     * @param laboratori laboratori del database
     * @param dipendenti dipendenti del database
     */

    public void setReferentiLaboratorio(List<Laboratorio> laboratori , List<Dipendente> dipendenti){
        for (Laboratorio laboratorio : laboratori) {
            for (Dipendente dipendente : dipendenti) {
                if (laboratorio.getReferente().getId() == (dipendente.getId())) {
                    laboratorio.setReferente(dipendente);
                }
            }
        }
    }

    /**
     *  Metodo che si occupa di trovare e settare per ogni laboratorio
     *  il progetto a lui assegnato
     * @param laboratori laboratori del database
     * @param progetti progetti del database
     */

    public void setProgettiLaboratorio(List<Laboratorio> laboratori , List<Progetto> progetti){
        for (Laboratorio laboratorio : laboratori) {
            for (Progetto progetto : progetti) {
                if (progetto.getCup() == laboratorio.getProgetto().getCup()) {
                    laboratorio.setProgetto(progetto);
                }
            }
        }
    }

    /**
     *  Metodo che si occupa di trovare e settare per ogni dipendente
     *  il laboratorio a lui assegnato
     * @param dipendenti dipendenti del database
     * @param laboratori laboratori del database
     */

    //todo settare laboratori dipendenti

    public void setLaboratoriDipendente(List<Dipendente> dipendenti , List<Laboratorio> laboratori){
        for (Dipendente dipendente : dipendenti) {
            for (Laboratorio laboratorio : laboratori) {
                if (laboratorio.getNome().equals(dipendente.getLaboratorio().getNome())) {
                    dipendente.setLaboratorio(laboratorio);
                }
            }
        }
    }

    /**
     * Metodo che si occupa di rimuovere dalla tabella
     * tutti i dipendenti che hanno un laboratorio
     * @param dipendenti dipendenti del database
     */

    public void rimuoviDipendentiAssegnati(List<Dipendente> dipendenti){
        int i = 0;
        while (i < dipendenti.size()) {
            if (dipendenti.get(i).getLaboratorio().getNome() != null) {
                guImain.rimuoviDipendente(dipendenti.get(i));
            } else {
                i++;
            }
        }
    }

    /**
     * Metodo che si occupa di selezionare quali dipendenti
     * sono idonei per essere assegnati come referenti del
     * progetto selezionato
     * @param dipendenti dipendenti del database
     * @param progetto progetto selezionato
     * @return dipendenti scelti
     */

    public List<Dipendente> listaReferentiProgettoPossibili(List<Dipendente> dipendenti , Progetto progetto){
        TimeUnit time = TimeUnit.DAYS;
        List<Dipendente> dipendentiScelti = new ArrayList<>();
        for (Dipendente dipendente : dipendenti) {
            for (Laboratorio laboratorio : progetto.getLaboratori()) {
                    if (dipendente.getLaboratorio().equals(laboratorio)) {
                        if (time.convert(Date.valueOf(LocalDate.now()).getTime() - dipendente.getDataAssunzione().getTime(), TimeUnit.MILLISECONDS) / 365 >= 7)
                            dipendentiScelti.add(dipendente);
                    }
            }

        }
        return dipendentiScelti;
    }

    /**
     *  Metodo che si occupa di selezionare quali dipendenti
     *  sono idonei per essere assegnati come referenti del
     *  laboratorio selezionato
     * @param dipendenti dipendenti del database
     * @param laboratorio laboratorio selezionato
     * @return dipendenti scelti
     */

    public List<Dipendente> listaReferentiLaboratorioPossibili(List<Dipendente> dipendenti , Laboratorio laboratorio){
        TimeUnit time = TimeUnit.DAYS;
        List<Dipendente> dipendentiScelti = new ArrayList<>();
        for (Dipendente dipendente : dipendenti) {
            if (dipendente.getLaboratorio().getNome() != null) {
                if (dipendente.getLaboratorio().equals(laboratorio)) {
                    if (time.convert(Date.valueOf(LocalDate.now()).getTime() - dipendente.getDataAssunzione().getTime(), TimeUnit.MILLISECONDS) / 365 >= 7)
                        dipendentiScelti.add(dipendente);
                }
            }
        }
        return dipendentiScelti;
    }

    /**
     *  Metodo che si occupa di selezionare quali dipendenti
     *  sono idonei per essere assegnati come responsabili del
     *  progetto selezionato
     * @param dipendenti dipendenti del database
     * @param progetto progetto scelto
     * @return dipendenti scelti
     */

    public List<Dipendente> listaResponsabiliProgettoPossibili(List<Dipendente> dipendenti , Progetto progetto){
        List<Dipendente> dipendentiScelti = new ArrayList<>();
        for (Dipendente dipendente : dipendenti) {
            for (Laboratorio laboratorio : progetto.getLaboratori()) {
                    if (dipendente.getLaboratorio().equals(laboratorio)) {
                        if (dipendente.isDirigente())
                            dipendentiScelti.add(dipendente);
                    }
            }
        }
        return dipendentiScelti;
    }

}
