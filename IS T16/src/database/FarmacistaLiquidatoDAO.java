package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityFarmacistaLiquidato;
import exception.DAOException;
import exception.DBConnectionException;

public class FarmacistaLiquidatoDAO {
    private static final String INSERT_FARMACISTA_LIQUIDATO = "INSERT INTO FarmacistaLiquidato (id_farmacista, data_liquidazione) VALUES (?, ?)";
    private static final String SELECT_FARMACISTA_LIQUIDATO_BY_ID = "SELECT * FROM FarmacistaLiquidato WHERE id = ?";

    public void addFarmacistaLiquidato(EntityFarmacistaLiquidato farmacistaLiquidato) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FARMACISTA_LIQUIDATO)) {
            preparedStatement.setInt(1, farmacistaLiquidato.getId());
            preparedStatement.setDate(2, farmacistaLiquidato.getDataLiquidazione());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Farmacista Liquidato: " + e.getMessage());
        }
    }

    public EntityFarmacistaLiquidato readFarmacistaLiquidatoById(int id) throws DAOException, DBConnectionException {
        EntityFarmacistaLiquidato farmacistaLiquidato = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FARMACISTA_LIQUIDATO_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                farmacistaLiquidato = new EntityFarmacistaLiquidato(
                    resultSet.getInt("id"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("email"),
                    resultSet.getDate("data_liquidazione")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error reading Farmacista Liquidato by ID: " + e.getMessage());
        }
        return farmacistaLiquidato;
    }
}
