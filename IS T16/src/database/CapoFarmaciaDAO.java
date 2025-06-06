package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityCapoFarmacia;
import exception.DAOException;
import exception.DBConnectionException;

public class CapoFarmaciaDAO {
    private static final String INSERT_CAPO_FARMACIA = "INSERT INTO CapoFarmacia (nome, cognome, email) VALUES (?, ?, ?)";
    private static final String SELECT_CAPO_FARMACIA_BY_ID = "SELECT * FROM CapoFarmacia WHERE id = ?";
    
    public void addCapoFarmacia(EntityCapoFarmacia capoFarmacia) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CAPO_FARMACIA)) {
            preparedStatement.setString(1, capoFarmacia.getNome());
            preparedStatement.setString(2, capoFarmacia.getCognome());
            preparedStatement.setString(3, capoFarmacia.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Capo Farmacia: " + e.getMessage());
        }
    }

    public EntityCapoFarmacia readCapoFarmaciaById(int id) throws DAOException, DBConnectionException {
        EntityCapoFarmacia capoFarmacia = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CAPO_FARMACIA_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                capoFarmacia = new EntityCapoFarmacia(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error reading Capo Farmacia by ID: " + e.getMessage());
        }
        return capoFarmacia;
    }
}