package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityFarmacista;
import exception.DAOException;
import exception.DBConnectionException;

public class FarmacistaDAO {
    private static final String INSERT_FARMACISTA = "INSERT INTO Farmacista (nome, cognome, email) VALUES (?, ?, ?)";
    private static final String SELECT_FARMACISTA_BY_ID = "SELECT * FROM Farmacista WHERE id = ?";

    public void addFarmacista(EntityFarmacista farmacista) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FARMACISTA)) {
            preparedStatement.setString(1, farmacista.getNome());
            preparedStatement.setString(2, farmacista.getCognome());
            preparedStatement.setString(3, farmacista.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Farmacista: " + e.getMessage());
        }
    }

    public EntityFarmacista readFarmacistaById(int id) throws DAOException, DBConnectionException {
        EntityFarmacista farmacista = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FARMACISTA_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                farmacista = new EntityFarmacista(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error reading Farmacista by ID: " + e.getMessage());
        }
        return farmacista;
    }
}