package entity;

import java.util.Date;


public class EntityPrenotazione {
    private int id;
    private int idFarmacia;
    private int idFarmacista;
    private int idPaziente;
    private Date dataPrenotazione;
    private Date orarioPrenotazione;
    private int esito;
    private String Anamnesi;
    private int VaccinoType;

    private enum VaccinoType {
    PFISCHIO,
    ANTIQUA,
    ASPERA_ZENZERO;
    }

    private enum esito {
    NON_PRESENTE,
    COMPLETATA,
    ANNULLATA,
    CONFERMATA,
    RIFIUTATA,
    IN_ATTESA;
    }

    public EntityPrenotazione(int id, int idFarmacia, int idFarmacista, int idPaziente, Date dataPrenotazione, Date orarioPrenotazione, String anamnesi, int vaccinoType, int esito) {
        if (dataPrenotazione == null || orarioPrenotazione == null) {
            throw new IllegalArgumentException("Data e orario di prenotazione non possono essere null");
        }
        this.id = id;
        this.idFarmacia = idFarmacia;
        this.idFarmacista = idFarmacista;
        this.idPaziente = idPaziente;
        this.dataPrenotazione = dataPrenotazione;
        this.orarioPrenotazione = orarioPrenotazione;
        this.Anamnesi = anamnesi;
        this.VaccinoType = vaccinoType;
        this.esito = esito;
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

    public int getIdFarmacista() {
        return idFarmacista;
    }

    public void setIdFarmacista(int idFarmacista) {
        this.idFarmacista = idFarmacista;
    }

    public int getIdPaziente() {
        return idPaziente;
    }

    public void setIdPaziente(int idPaziente) {
        this.idPaziente = idPaziente;
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
        return VaccinoType;
    }

    public void setVaccinoType(int vaccinoType) {
        this.VaccinoType = vaccinoType;
    }
}
