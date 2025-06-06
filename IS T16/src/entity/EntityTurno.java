package entity;
import java.util.Date;

public class EntityTurno {

  private int id;
  private String mailFarmacista;
  private Date giorno;             // java.time.DayOfWeek
  private int tipoTurno;          // enum { MATTINA, POMERIGGIO }
  // + costruttori/getter/setter

    public EntityTurno(int id, String mailFarmacista, Date giorno, int tipoTurno) {
        this.id = id;
        this.mailFarmacista = mailFarmacista;
        this.giorno = giorno;
        this.tipoTurno = tipoTurno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMailFarmacista() {
        return mailFarmacista;
    }

    public void setMailFarmacista(String mailFarmacista) {
        this.mailFarmacista = mailFarmacista;
    }

    public Date getGiorno() {
        return giorno;
    }

    public void setGiorno(Date giorno) {
        this.giorno = giorno;
    }

    public int getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(int tipoTurno) {
        this.tipoTurno = tipoTurno;
    }
}

