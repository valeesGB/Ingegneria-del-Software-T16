package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityFarmacia;
import exception.DAOException;
import exception.DBConnectionException;

public class FarmaciaDAO {
    private static final String INSERT_FARMACIA = "INSERT INTO Farmacia (nome, indirizzo, telefono) VALUES (?, ?, ?)";
    private static final String SELECT_FARMACIA_BY_ID = "SELECT * FROM Farmacia WHERE id = ?";

    public void addFarmacia(EntityFarmacia farmacia) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FARMACIA)) {
            preparedStatement.setString(1, farmacia.getNome());
            preparedStatement.setString(2, farmacia.getIndirizzo());
            preparedStatement.setString(3, farmacia.getCitta());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Farmacia: " + e.getMessage());
        }
    }

    public EntityFarmacia readFarmaciaById(int id) throws DAOException, DBConnectionException {
        EntityFarmacia farmacia = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FARMACIA_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                farmacia = new EntityFarmacia(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("indirizzo"),
                    resultSet.getString("citta")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error reading Farmacia by ID: " + e.getMessage());
        }
        return farmacia;
    }
}
