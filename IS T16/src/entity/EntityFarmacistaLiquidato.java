package entity;

import java.util.Date;


public class EntityFarmacistaLiquidato {
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String psw;
    private Date dataLiquidazione;

    public EntityFarmacistaLiquidato(int id, String nome, String cognome, String email, Date dataLiquidazione) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.psw = null; // Password is not set for liquidated pharmacists
        this.dataLiquidazione = dataLiquidazione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw; // This should not be used for liquidated pharmacists
    }

    public Date getDataLiquidazione() {
        return dataLiquidazione;
    }

    public void setDataLiquidazione(Date dataLiquidazione) {
        this.dataLiquidazione = dataLiquidazione;
    }
}
