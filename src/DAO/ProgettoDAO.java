package DAO;

import Model.Dipendente;
import Model.Progetto;
import java.util.List;

/**
 * Interfaccia che deve essere implementata per gestire i progetti nel database
 */

public interface ProgettoDAO {

    /**
     * Metodo che ritorna tutti i progetti presenti nel Database
     * @return tutti i progetti
     * @throws Exception eventuali problemi nell'interazione con il database
     */
    List<Progetto> getProgetti() throws Exception;

    /**
     * Metodo che inserisce un progetto nel database
     * @param progetto progetto da inserire
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void inserisci(Progetto progetto) throws Exception;

    /**
     * Metodo che rimuove un progetto dal database
     * @param progetto progetto da inserire
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void rimuovi(Progetto progetto) throws Exception;

    /**
     * Metodo che setta il dipendente scelto come referente
     * @param dipendente dipendente da rendere referente
     * @param progetto progetto scelto
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void setReferente(Dipendente dipendente, Progetto progetto) throws Exception;

    /**
     * Metodo che setta il dipendente scelto come responsabile
     * @param dipendente dipendente da rendere responsabile
     * @param progetto progetto scelto
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void setResponsabile(Dipendente dipendente, Progetto progetto) throws Exception;

}
