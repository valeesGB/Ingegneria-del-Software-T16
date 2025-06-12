package entity;

public class EntityFarmacista {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private int idFarmacia;

    public EntityFarmacista(String nome, String cognome, String email, String password, int idFarmacia) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.idFarmacia = idFarmacia;
    }

    public EntityFarmacista() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }
}
