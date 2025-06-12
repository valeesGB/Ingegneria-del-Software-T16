package control;

import database.CapoFarmaciaDAO;
import database.FarmaciaDAO;
import database.FarmacistaDAO;
import database.PrenotazioneDAO;
import entity.EntityFarmacia;
import entity.EntityFarmacista;
import exception.DAOException;
import exception.OperationException;
import java.util.List;

public class GestioneFarmacia {
    private CapoFarmaciaDAO capoDAO;
    private FarmaciaDAO farmaciaDAO;
    private FarmacistaDAO farmacistaDAO;
    private PrenotazioneDAO prenotazioneDAO;

    public GestioneFarmacia() throws DAOException {
        this.capoDAO         = new CapoFarmaciaDAO();
        this.farmaciaDAO     = new FarmaciaDAO();
        this.farmacistaDAO   = new FarmacistaDAO();
        this.prenotazioneDAO = new PrenotazioneDAO();
    }

    public List<EntityFarmacia> listaFarmacie() throws DAOException {
        return farmaciaDAO.findAll();
    }

    public EntityFarmacia cercaFarmaciaPerId(int id) throws DAOException {
        return farmaciaDAO.findByPrimaryKey(id);
    }

    public void creaFarmacista(String nome, String cognome, int idFarmacia) 
            throws DAOException, OperationException {
        if (farmaciaDAO.findByPrimaryKey(idFarmacia) == null) {
            throw new OperationException("Farmacia inesistente (ID = " + idFarmacia + ")");
        }
        EntityFarmacista f = new EntityFarmacista();
        f.setNome(nome);
        f.setCognome(cognome);
        f.setEmail(null); // da riempire se necessario
        f.setPassword(null);
        f.setIdFarmacia(idFarmacia);
        farmacistaDAO.insert(f);
    }

}
