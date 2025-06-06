package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import database.DBManager;
import database.FarmaciaDAO;
import database.FarmacistaDAO;
import database.PrenotazioneDAO;
import database.TurnoDAO;
import entity.EntityPrenotazione;
import entity.EntityTurno;
import exception.DAOException;
import exception.DBConnectionException;

public class GestioneFarmacia {
    // Implementazione dei metodi per la gestione della farmacia
    // Questi metodi possono includere operazioni come aggiungere, modificare o eliminare,
    // gestire prenotazioni, generare report, ecc.

    public String codeToVaccino(int code){
        switch (code) {
            case 1: return "PFISCHIO";
            case 2: return "MODERNA";
            case 3: return "JHONSON";
            default: throw new IllegalArgumentException("Invalid vaccine code: " + code);
        }
    }

    public String codeToEsito(int code){
        switch (code) {
            case 0: return "EFFETTUATA";
            case 1: return "NON_EFFETTUATA";
            case 2: return "ANNULLATA";
            default: throw new IllegalArgumentException("Invalid esito code: " + code);
        }
    }

    public String codeToTurno(int code){
        switch (code) {
            case 1: return "MATTINA";
            case 2: return "POMERIGGIO";
            default: throw new IllegalArgumentException("Invalid turno code: " + code);
        }
    }

    private void aggiungiFarmacia() {
        // Logica per aggiungere una farmacia
    }

    private void modificaFarmacia() {
        // Logica per modificare una farmacia esistente
    }

    private void eliminaFarmacia() {
        // Logica per eliminare una farmacia
    }

    private void aggiungiPrenotazione(String name, String address) {

        String anamnesi;
        Date dataPrenotazione;
        Date orarioPrenotazione;
        String vaccino = codeToVaccino(1 + (int)(Math.random() * 3));
                
        FarmaciaDAO farmaciaDAO = new FarmaciaDAO();
        FarmacistaDAO farmacistaDAO = new FarmacistaDAO();

        int sbor = 0; //IdFarmacia
        //questi sbor vanno cambiati con i risultati di una query perchè sono chiavi esterne
        try {
            sbor = farmaciaDAO.readIdFarmaciaByNameAddress(name, address);
        } catch (DAOException | DBConnectionException e) {
            System.err.println("Error retrieving idFarmacia: " + e.getMessage());
            // Handle error appropriately, e.g., return or throw a runtime exception
            return;
        }

        String sbor2 = "a"; // mailFarmacista  
        int sbor3 = 0; // idTurno

        // 1) Test connessione

        System.out.println("=== TEST: apertura e chiusura connessione ===");
        try (Connection connection = DBManager.getConnection()) {
            boolean open = (connection != null && !connection.isClosed());
            System.out.println("Connection established successfully: " + open);
        } catch (SQLException e) {
            System.err.println("Error establishing/closing connection: " + e.getMessage());
        }


        // 2) Test inserimento Prenotazione

        System.out.println("\n=== TEST: inserimento Prenotazione ===");
        // Creo l'entità all'interno
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
            prenotazioneDAO.addPrenotazione(prenotazione, sbor, sbor2, sbor3);
            System.out.println("Farmacista added successfully.");
        } catch (DBConnectionException e) {
            System.err.println("Database connection error: " + e.getMessage());
        } catch (DAOException e) {
            System.err.println("DAO error: " + e.getMessage());
        }
    }

    private void gestisciPrenotazioni() {
        // Logica per gestire le prenotazioni delle farmacie
    }

    private void generaReport() {
        // Logica per generare report sulle farmacie
    }
}
