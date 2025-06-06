package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.EntityPrenotazione;
import exception.DAOException;
import exception.DBConnectionException;

public class PrenotazioneDAO {
    private static final String INSERT_PRENOTAZIONE = "INSERT INTO Prenotazione (id, CF, data, ora, esito, anamnesi, vaccino, IdFarmacia, mailFarmacista) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PRENOTAZIONE_BY_ID = "SELECT * FROM Prenotazione WHERE id = ?";

    public void addPrenotazione(EntityPrenotazione prenotazione, int sbor, String sbor2, int sbor3) throws DAOException, DBConnectionException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRENOTAZIONE)) {
            preparedStatement.setInt(1, prenotazione.getId());
            preparedStatement.setString(2, prenotazione.getCF());
            preparedStatement.setDate(3, new java.sql.Date(prenotazione.getDataPrenotazione().getTime()));
            preparedStatement.setTime(4, new java.sql.Time(prenotazione.getOrarioPrenotazione().getTime()));
            preparedStatement.setInt(5, prenotazione.getEsito());
            preparedStatement.setString(6, prenotazione.getAnamnesi());
            preparedStatement.setInt(7, prenotazione.getVaccinoType());
            preparedStatement.setInt(8, sbor); // Assuming sbor is idFarmacia
            preparedStatement.setString(9, sbor2); // Assuming sbor2 is idFarmacista
            preparedStatement.setInt(10, sbor3); // Assuming sbor3 is idTurno

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
                String CF = resultSet.getString("CF");
                java.util.Date dataPrenotazione = resultSet.getDate("data");
                java.util.Date orarioPrenotazione = resultSet.getTime("ora");
                String anamnesi = resultSet.getString("anamnesi");
                int vaccinoType = resultSet.getInt("vaccino");
                int esito = resultSet.getInt("esito");
                int idFarmacia = resultSet.getInt("IdFarmacia");
                String idFarmacista = resultSet.getString("mailFarmacista");
                int idTurno = resultSet.getInt("idTurno");
                return new EntityPrenotazione(id, idFarmacia, idFarmacista, CF, dataPrenotazione, orarioPrenotazione, anamnesi, vaccinoType, esito, idTurno);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving Prenotazione: " + e.getMessage()); 
        }
    }
}
