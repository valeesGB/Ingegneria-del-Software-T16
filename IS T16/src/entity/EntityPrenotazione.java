package entity;

import java.util.Date;


public class EntityPrenotazione {

    private int id;
    private int idFarmacia;
    private String CF;
    private Date dataPrenotazione;
    private Date orarioPrenotazione;
    private String esito;
    private String Anamnesi;
    private String vaccinoType;
    private int idTurno;

    public EntityPrenotazione(int id, int idFarmacia, String CF, Date dataPrenotazione, Date orarioPrenotazione, String anamnesi, String vaccinoType, String esito2, int idTurno) {
        if (dataPrenotazione == null || orarioPrenotazione == null) {
            throw new IllegalArgumentException("Data e orario di prenotazione non possono essere null");
        }
        this.id = id;
        this.idFarmacia = idFarmacia;
        this.CF = CF;
        this.dataPrenotazione = dataPrenotazione;
        this.orarioPrenotazione = orarioPrenotazione;
        this.Anamnesi = anamnesi;
        this.vaccinoType = vaccinoType;
        this.esito = esito2;
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

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public String getAnamnesi() {
        return Anamnesi;
    }

    public void setAnamnesi(String anamnesi) {
        Anamnesi = anamnesi;
    }

    public String getVaccinoType() {
        return vaccinoType;
    }

    public void setVaccinoType(String vaccinoType) {
        this.vaccinoType = vaccinoType;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }
}
