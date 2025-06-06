package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityFarmacia;
import exception.DAOException;
import exception.DBConnectionException;

public class FarmaciaDAO {
    private static final String INSERT_FARMACIA = "INSERT INTO Farmacia (id, nome, indirizzo, citta) VALUES (?, ?, ?, ?)";
    private static final String SELECT_FARMACIA_BY_ID = "SELECT * FROM Farmacia WHERE id = ?";
    private static final String SELECT_FARMACIA_BY_NAME_ADDRESS = "SELECT IdFarmacia FROM Farmacia WHERE Nome = ? and Indirizzo = ?";


    public void addFarmacia(EntityFarmacia farmacia) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FARMACIA)) {
            preparedStatement.setInt(1, farmacia.getId());
            preparedStatement.setString(2, farmacia.getNome());
            preparedStatement.setString(3, farmacia.getIndirizzo());
            preparedStatement.setString(4, farmacia.getCitta());
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

    public int readIdFarmaciaByNameAddress(String name, String address) throws DAOException, DBConnectionException {
    int idFarmacia = -1; // Default value if not found

        try (Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FARMACIA_BY_NAME_ADDRESS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idFarmacia = resultSet.getInt("IdFarmacia");
            }
        } catch (SQLException e) {
            throw new DAOException("Error reading Farmacia by ID: " + e.getMessage());
        }

        if (idFarmacia == -1) {
            throw new DAOException("Farmacia not found with name: " + name + " and address: " + address);
        }
        else {
            return idFarmacia;
        }
    }
}
