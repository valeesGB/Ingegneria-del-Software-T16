package entity;

import java.util.Date;


public class EntityPrenotazione {

    private int id;
    private int idFarmacia;
    private String mailFarmacista;
    private String CF;
    private Date dataPrenotazione;
    private Date orarioPrenotazione;
    private int esito;
    private String Anamnesi;
    private int vaccinoType;
    private int idTurno;

    public EntityPrenotazione(int id, int idFarmacia, String mailFarmacista, String CF, Date dataPrenotazione, Date orarioPrenotazione, String anamnesi, int vaccinoType, int esito, int idTurno) {
        if (dataPrenotazione == null || orarioPrenotazione == null) {
            throw new IllegalArgumentException("Data e orario di prenotazione non possono essere null");
        }
        this.id = id;
        this.idFarmacia = idFarmacia;
        this.mailFarmacista = mailFarmacista;
        this.CF = CF;
        this.dataPrenotazione = dataPrenotazione;
        this.orarioPrenotazione = orarioPrenotazione;
        this.Anamnesi = anamnesi;
        this.vaccinoType = vaccinoType;
        this.esito = esito;
        this.idTurno = idTurno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getMailFarmacista() {
        return mailFarmacista;
    }

    public void setMailFarmacista(String mailFarmacista) {
        if (mailFarmacista == null || mailFarmacista.isEmpty()) {
            throw new IllegalArgumentException("Email del farmacista non può essere null o vuota");
        }
        this.mailFarmacista = mailFarmacista;
    }

    public String getCF() {
        return CF;
    }

    public void setCF(String CF) {
        this.CF = CF;
    }

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Date dataPrenotazione) {
        if (dataPrenotazione == null) {
            throw new IllegalArgumentException("Data di prenotazione non può essere null");
        }
        this.dataPrenotazione = dataPrenotazione;
    }

    public Date getOrarioPrenotazione() {
        return orarioPrenotazione;
    }

    public void setOrarioPrenotazione(Date orarioPrenotazione) {
        if (orarioPrenotazione == null) {
            throw new IllegalArgumentException("Orario di prenotazione non può essere null");
        }
        this.orarioPrenotazione = orarioPrenotazione;
    }

    public int getEsito() {
        return esito;
    }

    public void setEsito(int esito) {
        this.esito = esito;
    }

    public String getAnamnesi() {
        return Anamnesi;
    }

    public void setAnamnesi(String anamnesi) {
        Anamnesi = anamnesi;
    }

    public int getVaccinoType() {
        return vaccinoType;
    }

    public void setVaccinoType(int vaccinoType) {
        this.vaccinoType = vaccinoType;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }
}
