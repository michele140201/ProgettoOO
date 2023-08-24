package DAO;

import Model.Dipendente;
import Model.Laboratorio;
import java.util.List;

/**
 * Interfaccia che deve essere implementata per gestire i dipendenti nel database
 */

public interface DipendenteDAO {

    /**
     * Metodo che ritorna tutti i dipendenti presenti nel database
     * @return tutti i dipendenti
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    List<Dipendente> getDipendenti() throws Exception;

    /**
     * Metodo che rimuove un dipendente dal database
     * @param dipendente dipendente selezionato
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void removeDipendente(Dipendente dipendente) throws Exception;

    /**
     * Metodo che inserisce un dipendente nel database
     * @param dipendente dipendente da inserire
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void insertDipendente(Dipendente dipendente) throws Exception;

    /**
     * Metodo che setta il laboratorio a cui è assegnato il dipendente
     * @param laboratorio laboratorio selezionato
     * @param dipendente dipendente selezionato
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void setLaboratorio(Laboratorio laboratorio, Dipendente dipendente) throws Exception;

    /**
     * Metodo che promuove il dipendente nel database
     * @param dipendente dipendente selezionato
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void promuovi(Dipendente dipendente) throws Exception;

    /**
     * Metodo che degrada il dipendente nel database
     * @param dipendente dipendente selezionato
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void degrada(Dipendente dipendente) throws Exception;
}
