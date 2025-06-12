package database;

import entity.EntityFarmacista;
import exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FarmacistaDAO {
    private Connection conn;

    public FarmacistaDAO() throws DAOException {
        try {
            this.conn = DBManager.getConnection();
        } catch (SQLException ex) {
            throw new DAOException("Impossibile connettersi al DB", ex);
        }
    }

    public EntityFarmacista findByEmail(String email) throws DAOException {
        EntityFarmacista f = null;
        String query = "SELECT * FROM farmacista WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f = new EntityFarmacista();
                f.setNome(rs.getString("Nome"));
                f.setCognome(rs.getString("Cognome"));
                f.setEmail(rs.getString("Email"));
                f.setPassword(rs.getString("Password"));
                f.setIdFarmacia(rs.getInt("IdFarmacia"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Errore in findByEmail()", ex);
        }
        return f;
    }

    public boolean authenticate(String email, String password) throws DAOException {
        String query = "SELECT Email FROM farmacista WHERE Email = ? AND Password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new DAOException("Errore in authenticate()", ex);
        }
    }

    public void insert(EntityFarmacista f) throws DAOException {
        String query = "INSERT INTO farmacista (Nome, Cognome, Email, Password, IdFarmacia) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getCognome());
            ps.setString(3, f.getEmail());
            ps.setString(4, f.getPassword());
            ps.setInt(5, f.getIdFarmacia());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Errore in insert()", ex);
        }
    }

    public List<EntityFarmacista> getAll() throws DAOException {
        List<EntityFarmacista> lista = new java.util.ArrayList<>();
        String query = "SELECT * FROM farmacista";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EntityFarmacista f = new EntityFarmacista();
                f.setNome(rs.getString("Nome"));
                f.setCognome(rs.getString("Cognome"));
                f.setEmail(rs.getString("Email"));
                f.setPassword(rs.getString("Password"));
                f.setIdFarmacia(rs.getInt("IdFarmacia"));
                lista.add(f);
            }
        } catch (SQLException ex) {
            throw new DAOException("Errore in getAll()", ex);
        }
        return lista;
    }

    public void assegnaFarmacia(String emailFarmacista, int idFarmacia) throws DAOException {
        String query = "UPDATE farmacista SET IdFarmacia = ? WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idFarmacia);
            ps.setString(2, emailFarmacista);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Errore in assegnaFarmacia()", ex);
        }
    }
}
