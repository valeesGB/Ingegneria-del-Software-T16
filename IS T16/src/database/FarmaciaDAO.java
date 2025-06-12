package database;

import entity.EntityFarmacia;
import exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FarmaciaDAO {
    private Connection conn;

    public FarmaciaDAO() throws DAOException {
        try {
            this.conn = DBManager.getConnection();
        } catch (SQLException ex) {
            throw new DAOException("Impossibile connettersi al DB", ex);
        }
    }

    public List<EntityFarmacia> findAll() throws DAOException {
        List<EntityFarmacia> lista = new ArrayList<>();
        String query = "SELECT * FROM farmacia";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                EntityFarmacia f = new EntityFarmacia();
                f.setId(rs.getInt("Id"));
                f.setNome(rs.getString("Nome"));
                f.setIndirizzo(rs.getString("Indirizzo"));
                f.setCitta(rs.getString("Citta"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new DAOException("Errore in findAll()", ex);
        }
        return lista;
    }

    public EntityFarmacia findByPrimaryKey(int id) throws DAOException {
        EntityFarmacia f = null;
        String query = "SELECT * FROM farmacia WHERE Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f = new EntityFarmacia();
                f.setId(rs.getInt("Id"));
                f.setNome(rs.getString("Nome"));
                f.setIndirizzo(rs.getString("Indirizzo"));
                f.setCitta(rs.getString("Citta"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Errore in findByPrimaryKey()", ex);
        }
        return f;
    }

    public void insert(EntityFarmacia f) throws DAOException {
        String query = "INSERT INTO farmacia (Id, Nome, Indirizzo, Citta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, f.getId());
            ps.setString(2, f.getNome());
            ps.setString(3, f.getIndirizzo());
            ps.setString(4, f.getCitta());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Errore in insert()", ex);
        }
    }

}
