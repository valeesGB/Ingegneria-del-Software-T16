package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;

import entity.EntityPrenotazione;
import exception.DAOException;
import exception.DBConnectionException;

public class PrenotazioneDAO {
    private static final String INSERT_PRENOTAZIONE = "INSERT INTO Prenotazione (farmacia_id, farmacista_id, data, ora) VALUES (?, ?, ?, ?)";
    private static final String SELECT_PRENOTAZIONE_BY_ID = "SELECT * FROM Prenotazione WHERE id = ?";

    public void addPrenotazione(EntityPrenotazione prenotazione) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRENOTAZIONE)) {
            preparedStatement.setInt(1, prenotazione.getIdFarmacia());
            preparedStatement.setInt(2, prenotazione.getIdFarmacista());
            preparedStatement.setDate(3, prenotazione.getDataPrenotazione());
            preparedStatement.setTime(4, prenotazione.getOrarioPrenotazione());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding Prenotazione: " + e.getMessage());
        }
    }

    public EntityPrenotazione getPrenotazioneById(int id) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRENOTAZIONE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new EntityPrenotazione(
                    resultSet.getInt("id"),
                    resultSet.getInt("farmacia_id"),
                    resultSet.getInt("farmacista_id"),
                    resultSet.getInt("paziente_id"),
                    resultSet.getDate("data"),
                    resultSet.getTime("ora")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving Prenotazione: " + e.getMessage());
        }
    }
}
