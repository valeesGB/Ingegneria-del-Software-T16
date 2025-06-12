package database;

import entity.EntityCapoFarmacia;
import exception.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CapoFarmaciaDAO {
    private Connection conn;

    public CapoFarmaciaDAO() throws DAOException {
        try {
            this.conn = DBManager.getConnection();
        } catch (SQLException ex) {
            throw new DAOException("Impossibile connettersi al DB", ex);
        }
    }

    public EntityCapoFarmacia findByEmail(String email) throws DAOException {
        EntityCapoFarmacia c = null;
        String query = "SELECT * FROM capo_farmacia WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                c = new EntityCapoFarmacia();
                c.setNome(rs.getString("Nome"));
                c.setCognome(rs.getString("Cognome"));
                c.setEmail(rs.getString("Email"));
                c.setPassword(rs.getString("Password"));
                c.setIdFarmacia(rs.getInt("IdFarmacia"));
            }
        } catch (SQLException ex) {
            throw new DAOException("Errore in findByEmail()", ex);
        }
        return c;
    }

    public boolean authenticate(String email, String password) throws DAOException {
        String query = "SELECT Email FROM capo_farmacia WHERE Email = ? AND Password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            throw new DAOException("Errore in authenticate()", ex);
        }
    }

    public void insert(EntityCapoFarmacia c) throws DAOException {
        // Controllo che email e password non siano "admin"
        if ("admin".equalsIgnoreCase(c.getEmail()) || "admin".equalsIgnoreCase(c.getPassword())) {
            throw new DAOException("Email e password non possono essere 'admin'.");
        }
        String query = "INSERT INTO capo_farmacia (Nome, Cognome, Email, Password, IdFarmacia) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCognome());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPassword());
            ps.setInt(5, c.getIdFarmacia());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Errore in insert()", ex);
        }
    }

    public EntityCapoFarmacia findByFarmaciaId(int idFarmacia) throws DAOException {
        String query = "SELECT * FROM capo_farmacia WHERE IdFarmacia = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, idFarmacia);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            EntityCapoFarmacia c = new EntityCapoFarmacia();
            c.setNome(rs.getString("Nome"));
            c.setCognome(rs.getString("Cognome"));
            c.setEmail(rs.getString("Email"));
            c.setPassword(rs.getString("Password"));
            c.setIdFarmacia(rs.getInt("IdFarmacia"));
            return c;
        }
        } catch (SQLException ex) {
        throw new DAOException("Errore in findByFarmaciaId()", ex);
        }
    return null;
    }

    public boolean existsByFarmaciaId(int idFarmacia) throws DAOException {
        String query = "SELECT 1 FROM capo_farmacia WHERE IdFarmacia = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idFarmacia);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new DAOException("Errore in existsByFarmaciaId()", ex);
        }
    }

    public String CapoFByIdPrenotazione(int reservation_id) {
        String email = null;
        String query = "SELECT email FROM capo_farmacia WHERE IdFarmacia = (SELECT IdFarmacia FROM prenotazione WHERE Id = ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, reservation_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("email");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Errore in CapoFByIdPrenotazione() " + ex.getMessage());
        }
        return email;
    }
    
}
