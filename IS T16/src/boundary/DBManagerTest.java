package boundary;

import java.sql.Connection;
import java.sql.SQLException;

import database.DBManager;
import exception.DBConnectionException;
import exception.DAOException;
import database.PrenotazioneDAO;
import entity.EntityPrenotazione;

public class DBManagerTest {

    public static void main(String[] args) {

        int sbor = 1; // idFarmacia
        String sbor2 = "paolo@liberti.si"; // mailFarmacista
        int sbor3 = 0; // idTurno, inizialmente 0
        // 1) Test connessione
        System.out.println("=== TEST: apertura e chiusura connessione ===");
        try (Connection connection = DBManager.getConnection()) {
            boolean open = (connection != null && !connection.isClosed());
            System.out.println("Connection established successfully: " + open);
        } catch (SQLException e) {
            System.err.println("Error establishing/closing connection: " + e.getMessage());
        }

        // 2) Test inserimento Farmacia
        System.out.println("\n=== TEST: inserimento Farmacista ===");
        // Creo l'entità all'interno di main
        EntityPrenotazione prenotazione = new EntityPrenotazione(
                1, // id
                sbor, // idFarmacia
                sbor2, // idFarmacista
                "RSSMRA85M01H501Z", // CF
                new java.util.Date(), // dataPrenotazione
                new java.util.Date(), // orarioPrenotazione
                "Nessuna anamnesi rilevante", // Anamnesi
                1, // VaccinoType (es. PFISCHIO)
                0, // esito (es. NON_PRESENTE)
                sbor3
        );

        try {
            PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
            prenotazioneDAO.addPrenotazione(prenotazione, sbor, sbor2);
            System.out.println("Farmacista added successfully.");
        } catch (DBConnectionException e) {
            System.err.println("Database connection error: " + e.getMessage());
        } catch (DAOException e) {
            System.err.println("DAO error: " + e.getMessage());
        }
    }
}