package database;

import entity.EntityPrenotazione;
import exception.DAOException;
import exception.DBConnectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDAO {
    private static final String INSERT_PRENOTAZIONE = "INSERT INTO prenotazione (Id, NomeC, CognomeC, Data, Ora, Esito, Anamnesi, Vaccino, IdFarmacia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_PRENOTAZIONE_BY_ID = "SELECT * FROM Prenotazione WHERE id = ?";

    public long addPrenotazione(EntityPrenotazione prenotazione, int idFarmacia) throws DAOException {
    try (Connection connection = DBManager.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(
             INSERT_PRENOTAZIONE, java.sql.Statement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setInt(1, prenotazione.getId());
        String[] nomeCognome = prenotazione.getCF().split(" ", 2);
        preparedStatement.setString(2, nomeCognome.length > 0 ? nomeCognome[0] : "");
        preparedStatement.setString(3, nomeCognome.length > 1 ? nomeCognome[1] : "");
        preparedStatement.setDate(4, new java.sql.Date(prenotazione.getDataPrenotazione().getTime()));
        preparedStatement.setTime(5, new java.sql.Time(prenotazione.getOrarioPrenotazione().getTime()));
        preparedStatement.setString(6, prenotazione.getEsito());
        preparedStatement.setString(7, prenotazione.getAnamnesi());
        preparedStatement.setString(8, prenotazione.getVaccinoType());
        preparedStatement.setInt(9, idFarmacia);
        preparedStatement.executeUpdate();

        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new DAOException("Errore: nessun ID generato per la prenotazione.");
            }
        }
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
                String vaccinoType = resultSet.getString("vaccino");
                String esito = resultSet.getString("esito");
                int idFarmacia = resultSet.getInt("IdFarmacia");
                int idTurno = resultSet.getInt("idTurno");
                return new EntityPrenotazione(id, idFarmacia, CF, dataPrenotazione, orarioPrenotazione, anamnesi, vaccinoType, esito, idTurno);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving Prenotazione: " + e.getMessage()); 
        }
    }

    public void aggiornaAnamnesi(int idPrenotazione, String anamnesi) throws DAOException {
        String query = "UPDATE prenotazione SET Anamnesi = ? WHERE Id = ?";
        try (Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, anamnesi);
            ps.setInt(2, idPrenotazione);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Errore in aggiornaAnamnesi()", ex);
        }
    }

    public void aggiornaEsito(int idPrenotazione, String esito) throws DAOException {
        String query = "UPDATE prenotazione SET Esito = ? WHERE Id = ?";
        try (Connection conn = DBManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, esito);
            ps.setInt(2, idPrenotazione);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Errore in aggiornaEsito()", ex);
        }
    }
    public java.sql.Time calcolaOrarioDisponibile(int idFarmacia, java.sql.Date dataPren) throws DAOException {
    // Fasce orarie: 8-14 e 14-20, slot ogni 15 minuti
    int[] startHours = {8, 14};
    int[] endHours = {14, 20};

    // Recupera tutti gli orari già prenotati per quella farmacia e giorno
    List<java.sql.Time> orariOccupati = new ArrayList<>();
    String query = "SELECT Ora FROM prenotazione WHERE IdFarmacia = ? AND Data = ?";
    try (Connection conn = DBManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, idFarmacia);
        ps.setDate(2, dataPren);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            orariOccupati.add(rs.getTime("Ora"));
        }
    } catch (SQLException ex) {
        throw new DAOException("Errore nel recupero orari occupati", ex);
    }

    // Cerca il primo slot libero
    for (int f = 0; f < 2; f++) {
        int start = startHours[f];
        int end = endHours[f];
        for (int h = start; h < end; h++) {
            for (int m = 0; m < 60; m += 15) {
                java.sql.Time slot = java.sql.Time.valueOf(String.format("%02d:%02d:00", h, m));
                if (!orariOccupati.contains(slot)) {
                    return slot;
                }
            }
        }
    }
    // Se non ci sono slot disponibili
    throw new DAOException("Nessun orario disponibile per il giorno selezionato.");
    }
    public List<EntityPrenotazione> getPrenotazioniByFarmacia(int idFarmacia) throws DAOException {
    List<EntityPrenotazione> lista = new ArrayList<>();
    String query = "SELECT * FROM prenotazione WHERE IdFarmacia = ? ORDER BY Data ASC, Ora ASC";
    try (Connection conn = DBManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, idFarmacia);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            EntityPrenotazione p = new EntityPrenotazione(
        rs.getInt("Id"),
        rs.getInt("IdFarmacia"),
        rs.getString("NomeC") + " " + rs.getString("CognomeC"),
        rs.getDate("Data"),
        rs.getTime("Ora"),
        rs.getString("Anamnesi"),
        rs.getString("Vaccino"), // <-- qui ora passi la stringa vaccino
        rs.getString("Esito"),
        0
        );
            lista.add(p);
        }
    } catch (SQLException ex) {
        throw new DAOException("Errore in getPrenotazioniByFarmacia()", ex);
    }
    return lista;
    }
}
