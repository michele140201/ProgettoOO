package DAO;

import Model.Dipendente;
import Model.Laboratorio;
import Model.Progetto;
import java.util.*;

/**
 * Interfaccia che deve essere implementata per gestire i laboratori nel database
 */

public interface LaboratorioDAO {

    /**
     * Metodo che ritorna tutti i laboratori presenti nel database
     * @return tutti i laboratori
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    List<Laboratorio> getLaboratori() throws Exception;

    /**
     * Metodo che inserisce un laboratorio nel database
     * @param laboratorio laboratorio da inserire
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void inserisci(Laboratorio laboratorio) throws Exception;

    /**
     * Metodo che rimuove un laboratorio dal database
     * @param laboratorio laboratorio da rimuovere
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void rimuovi(Laboratorio laboratorio) throws Exception;

    /**
     * Metodo che assegna il referente al laboratorio
     * @param laboratorio laboratorio scelto
     * @param dipendente dipendente scelto
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void assegnaReferente(Laboratorio laboratorio, Dipendente dipendente) throws Exception;

    /**
     * Metodo che assegna il progetto al laboratorio
     * @param laboratorio laboratorio scelto
     * @param progetto progetto scelto
     * @throws Exception eventuali problemi nell'interazione con il database
     */

    void assegnaProgetto(Laboratorio laboratorio, Progetto progetto) throws Exception;

}
