package database;

import entity.EntityFarmacistaLiquidato;
import exception.DAOException;
import exception.DBConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FarmacistaLiquidatoDAO {
    private static final String INSERT_FARMACISTA_LIQUIDATO =
    "INSERT INTO farmacista_liquidato (nome, cognome, email, data_liquidazione) VALUES (?, ?, ?, ?)";
    private static final String SELECT_FARMACISTA_LIQUIDATO_BY_ID = "SELECT * FROM FarmacistaLiquidato WHERE id = ?";

    public void addFarmacistaLiquidato(EntityFarmacistaLiquidato farmacistaLiquidato) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FARMACISTA_LIQUIDATO)) {
            preparedStatement.setString(1, farmacistaLiquidato.getNome());
            preparedStatement.setString(2, farmacistaLiquidato.getCognome());
            preparedStatement.setString(3, farmacistaLiquidato.getEmail());
            preparedStatement.setDate(4, new java.sql.Date(farmacistaLiquidato.getDataLiquidazione().getTime()));
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
    public java.util.List<entity.EntityFarmacistaLiquidato> getAll() throws exception.DAOException {
    java.util.List<entity.EntityFarmacistaLiquidato> lista = new java.util.ArrayList<>();
    String query = "SELECT * FROM farmacista_liquidato";
    try (Connection connection = DBManager.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         ResultSet resultSet = preparedStatement.executeQuery()) {
        while (resultSet.next()) {
            entity.EntityFarmacistaLiquidato f = new entity.EntityFarmacistaLiquidato(
                resultSet.getInt("id"),
                resultSet.getString("nome"),
                resultSet.getString("cognome"),
                resultSet.getString("email"),
                resultSet.getDate("data_liquidazione")
            );
            lista.add(f);
        }
    } catch (SQLException e) {
        throw new exception.DAOException("Error reading all Farmacisti Liquidati: " + e.getMessage());
    }
    return lista;
}
}
