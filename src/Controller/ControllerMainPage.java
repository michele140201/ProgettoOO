package Controller;

import GUI.GUImain;
import Model.Dipendente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.*;
import Model.Laboratorio;
import Model.Progetto;

/**
 * Classe che si occupa dell'interazione tra
 * i DAO e l'interfaccia grafica
 */
public class ControllerMainPage {
    private final ProgettoDAO progettoDAO = new ProgettoDAOImpl();
    private final DipendenteDAO dipendenteDAO = new DipendenteDAOimpl();
    private final LaboratorioDAO laboratorioDAO = new LaboratorioDAOImpl();
    private final CambioRuoloDAO cambioRuoloDAO = new CambioRuoloDAOImpl();
    private GUImain guImain;

    /**
     * Funzione che si occupa della gestione dell'inserimento di un nuovo dipendente
     *
     * @param model
     * @param Nome
     * @param Cognome
     * @param Dir
     * @param data
     * @param DatadiN
     */
    public void ButtonConferma(DefaultTableModel model, String Nome, String Cognome, boolean Dir, Date data, Date DatadiN) {
        try {
            if (!Nome.matches("[A-Za-z]+") || !Cognome.matches("[A-Za-z]+")) throw new Exception();
            int id_dip = dipendenteDAO.Id_dip();
            Dipendente dip = new Dipendente(Nome, Cognome, id_dip, Dir, data, DatadiN);
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
        } catch (Exception e1) {

            JOptionPane.showMessageDialog(null, "Inserisci le Credenziali Corrette!");
        }
    }

    /**
     * funzione che si occupa del licenziamento di un dipendente
     * ritorna 1 se avviene , 0 altrimenti
     *
     * @param model
     * @param id
     * @param NomeLab
     * @param row
     * @return
     */
    public int ButtonLicenzia(DefaultTableModel model, String id, String NomeLab, int row) {
        int id_dip = Integer.valueOf(id);
        int i;
        try {
            if (id_dip != laboratorioDAO.getReferenteLab(NomeLab)) {
                i = dipendenteDAO.removeDipendente(id_dip);
                if (i > 0) {
                    JOptionPane.showMessageDialog(null, "Dipendente Licenziato! Poverino :(");
                    return 1;
                } else JOptionPane.showMessageDialog(null, "Qualcosa è andato storto!");
                return 0;
            } else {
                JOptionPane.showMessageDialog(null, "Il Dipendente è Responsabile di Laboratorio");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * funzione che permette il recupero di tutti i dipendenti
     *
     * @return
     */
    public List<Dipendente> getDipendenti() {
        try {
            List<Dipendente> Lista = dipendenteDAO.getDipendente();
            return Lista;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    /**
     * Funzione per assegnare un dipendente ad un laboratorio
     *
     * @param id_dip
     * @param NomeLab
     * @param dialogo
     * @param Nuovo_Lab
     */
    public int AssegnaLaboratorio(int id_dip, String NomeLab, JDialog dialogo, String Nuovo_Lab) {
        try {
            if (id_dip != laboratorioDAO.getReferenteLab(NomeLab)) {
                dipendenteDAO.setLaboratorio(Nuovo_Lab, id_dip);
                return 1;
            } else {
                JOptionPane.showMessageDialog(null, "Il Dipendente è Responsabile di Laboratorio");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * elimina un laboratorio
     *
     * @param nome
     * @return
     */
    public int EliminaSede(String nome) {
        try {
            int i = laboratorioDAO.remove(nome);
            if (i > 0) {
                JOptionPane.showMessageDialog(null, "Sede Eliminata");
                return 1;
            } else {
                JOptionPane.showMessageDialog(null, "OPS, Qualcosa è andato storto!");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * crea un laboratorio
     *
     * @param dialogo
     * @param l
     * @return
     */
    public int CreaNuovoLab(JDialog dialogo, Laboratorio l) {
        try {
            laboratorioDAO.Inserisci(l.getNome_Lab(), l.getTopic());
            dialogo.setVisible(false);
            JOptionPane.showMessageDialog(null, "Inserimento riuscito!");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * elimina un progetto
     */
    public int EliminaProgetto_(int cup) {
        try {
            int ritorno = progettoDAO.EliminaProgetto(cup);
            if (ritorno == 1) {
                JOptionPane.showMessageDialog(null, "Progetto Eliminato!");
                return 1;
            } else {
                JOptionPane.showMessageDialog(null, "Ops, Qualcosa è andato storto!");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * degrada un dipendente
     *
     * @param value
     * @param id_dip
     * @return
     */
    public int Degrada(String value, int id_dip) {
        try {
            if (value.equals("SI")) {
                dipendenteDAO.degrada(id_dip);
                try {
                    cambioRuoloDAO.removePromozione(id_dip);
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Errore nel Database");
                    return 0;
                }
            } else {
                JOptionPane.showMessageDialog(null, "IMPOSSIBILE DEGRADARE!");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * imposta il nuovo responsabile di un progetto
     *
     * @param id
     * @param cup
     * @return
     */
    public int setResponsabile(int id, int cup) {
        try {
            progettoDAO.setResponsabile(id, cup);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * imposta il nuovo referente
     *
     * @param id_dip
     * @param cup
     * @return
     */
    public int setReferente(int id_dip, int cup) {
        try {
            progettoDAO.setReferente(id_dip, cup);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * Assena un progetto ad un laboratorio
     *
     * @param Topic
     * @return
     */
    public List<Progetto> AssegnaProgettoLaboratorio(String Topic) {
        try {
            int lung = progettoDAO.conta_progetti();
            List<Progetto> Progetti = progettoDAO.ottieniprogetti();
            ArrayList<Progetto> ProgettiScelti = new ArrayList<>();
            int i = 0;
            while (i < lung) {
                Progetto p = Progetti.get(i);
                if (laboratorioDAO.countProgetti(p.getCUP()) < 3) {
                    {
                        if (p.getTopic().equals(Topic)) {
                            ProgettiScelti.add(p);
                        }
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
    }

    /**
     * trova quando un dipendente è stato promosso a dirigente
     *
     * @param id_dip
     * @return
     */
    public Date DataCambio(int id_dip) {
        try {
            Date dataCambio = cambioRuoloDAO.getDataCambio(id_dip);
            return dataCambio;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    /**
     * promuove un dipendente
     *
     * @param id
     * @param value
     * @return
     */
    public int Promuovi(int id, String value) {
        try {
            if (value.equals("NO")) {
                dipendenteDAO.promuovi(id);
                cambioRuoloDAO.setDataPromozione(id);
                return 1;
            } else {
                JOptionPane.showMessageDialog(null, "IMPOSSIBILE PROMUOVERE!");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * ottiene i referenti possibili per il laboratorio scelto
     *
     * @param cup
     * @return
     */
    public List<Dipendente> getReferenti(int cup) {
        try {
            List<Laboratorio> labs = laboratorioDAO.getLaboratori(cup);
            int i = 0;
            List<Dipendente> dips = new ArrayList<>();
            while (i < labs.size()) {
                try {
                    dips.addAll(dipendenteDAO.getSenior(labs.get(i).getNome_Lab()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Errore nel Database");
                }
                i++;
            }
            return dips;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    /**
     * ottiene i progetti disponibili per il laboratorio scelto
     *
     * @return
     */
    public List<Progetto> getProgetti() {
        try {
            List<Progetto> Progetti = progettoDAO.ottieniprogetti();
            return Progetti;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    /**
     * ottiene tutti i laboratori
     *
     * @return
     */
    public List<Laboratorio> getLaboratori() {
        try {
            List<Laboratorio> Laboratori = laboratorioDAO.getLabs();
            return Laboratori;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore di Darabase");
            return null;
        }
    }

    /**
     * Assegna il nuovo referente del progetto
     *
     * @param NomeLab
     * @return
     */
    public List<Dipendente> getSenior(String NomeLab) {
        try {
            List<Dipendente> DIR = dipendenteDAO.getSenior(NomeLab);
            return DIR;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    /**
     * ottiene tutti i dipendenti da assegnare
     *
     * @return
     */
    public List<Dipendente> getDipendentiAssegnare() {
        try {
            List<Dipendente> Lista = dipendenteDAO.getDir();
            return Lista;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }

    /**
     * riassegna un dipendente ad un nuovo laboratorio
     *
     * @param NomeLab
     * @param idDip
     * @return
     */
    public int riassegnaDipendente(String NomeLab, int idDip) {
        try {
            laboratorioDAO.riassegnaDipendente(NomeLab, idDip);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * crea un nuovo progetto
     *
     * @param p
     * @return
     */
    public int InserisciProgetto(Progetto p) {
        try {
            p.setCUP(progettoDAO.GeneraCup());
            progettoDAO.InserisciProgetto(p);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * assegna un nuovo progetto ad un laboratorio scelto
     *
     * @param NomeLab
     * @param idDip
     * @return
     */
    public int AssegnaProgetto(String NomeLab, int idDip) {
        try {
            laboratorioDAO.riassegnaProgetto(NomeLab, idDip);
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return 0;
        }
    }

    /**
     * trova i dirigenti del laboratorio
     *
     * @param cup
     * @return
     */
    public List<Dipendente> ResponsabiliLaboratorio(int cup) {
        try {
            List<Laboratorio> labs = laboratorioDAO.getLaboratori(cup);
            int i = 0;
            List<Dipendente> dips = new ArrayList<>();
            while (i < labs.size()) {
                dips.addAll(dipendenteDAO.getDirigentiLaboratorio(labs.get(i).getNome_Lab()));
                i++;
            }
            return dips;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nel Database");
            return null;
        }
    }
}
