package Model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Creazione del model Dipendente con getter e setter
 */
public class Dipendente {

        private String nome ;
        private String cognome ;
        private int idDip ;
        private boolean Dirigente;
        private Date Assunzione ;
        private Date Data_nascita;
        private String Laboratorio;
        public Dipendente(String nome, String cognome, int idDip, boolean dirigente, Date assunzione, Date data_nascita) {
                this.nome = nome;
                this.cognome = cognome;
                this.idDip = idDip;
                Dirigente = dirigente;
                Assunzione = assunzione;
                Data_nascita = data_nascita;
                Laboratorio = "Non Assegnato";
        }


        public Dipendente(String nome, String cognome, int idDip, boolean dirigente, Date assunzione, Date data_nascita, String laboratorio) {
                this.nome = nome;
                this.cognome = cognome;
                this.idDip = idDip;
                Dirigente = dirigente;
                Assunzione = assunzione;
                Data_nascita = data_nascita;
                if(laboratorio!=null) Laboratorio = laboratorio;
                else Laboratorio = "Non Assegnato";
        }






        public void CambioLab(String Nuovo_Lab){Laboratorio = Nuovo_Lab;}
        public void NuovoDir(){Dirigente = true;}

        public String getLaboratorio() {
                return Laboratorio;
        }

        public void setLaboratorio(String laboratorio) {
                Laboratorio = laboratorio;
        }

        public Date getData_nascita() {
                return Data_nascita;
        }

        public void setData_nascita(Date data_nascita) {
                Data_nascita = data_nascita;
        }

        public Date getAssunzione() {
                return Assunzione;
        }

        public void setAssunzione(Date anzianita) {
                Assunzione = anzianita;
        }

        public boolean isDirigente() {
                return Dirigente;
        }

        public void setDirigente(boolean dirigente) {
                Dirigente = dirigente;
        }

        public int getidDip() {
                return idDip;
        }

        public void setIdDip(int idDip) {
                this.idDip = idDip;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getCognome() {
                return cognome;
        }

        public void setCognome(String cognome) {
                this.cognome = cognome;
        }
}
