package mailer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.mail.MessagingException;
import java.io.IOException;
import exception.DAOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.*;

import database.CapoFarmaciaDAO;


public class LocalQueryServer {

    // Parametri MySQL (come in phpMyAdmin)
    private static final String JDBC_URL      = "jdbc:mysql://localhost:3306/t16";
    private static final String JDBC_USER     = "root";
    private static final String JDBC_PASSWORD = "";

    // Indirizzo del capofarmacia a cui notificare

    public static void start(int port) throws IOException, DAOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("::", port), 0);
        server.createContext("/annullaPrenotazione", exchange -> {
            try {
                handleAnnul(exchange);
            } catch (DAOException e) {
                e.printStackTrace();
                String response = "Errore interno del server: " + e.getMessage();
                exchange.sendResponseHeaders(500, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        });
        server.setExecutor(null);
        server.start();
        System.out.println("LocalQueryServer avviato su porta " + port);
    }

    private static void handleAnnul(HttpExchange exchange) throws IOException, DAOException {
        String response;
        int statusCode;

        String query = exchange.getRequestURI().getQuery();
        String idParam = (query != null && query.startsWith("id="))
                          ? query.substring(3) : null;

        if (idParam == null) {
            statusCode = 400;
            response = "Parametro 'id' mancante";
        } else {
            try {
                int id = Integer.parseInt(idParam);
                int rows = annullaPrenotazione(id);

                
                if (rows > 0) {
                    statusCode = 200;
                    response = "Prenotazione con ID " + id + " annullata con successo.";

                    // Invia mail di notifica al capofarmacia
                    try {
                        String subject = "Prenotazione ANNULLATA: ID=" + id;
                        String body    = "La prenotazione con ID " + id +
                                         " è stata contrassegnata come ANNULLATA.";
                    
                        String CapoFById_EMAIL = new CapoFarmaciaDAO().CapoFByIdPrenotazione(id);
                        new Sender().sendSimpleMail(CapoFById_EMAIL, subject, body, id);
                    } catch (MessagingException me) {
                        // log dell’errore ma rispondi comunque 200
                        me.printStackTrace();
                    }

                } else {
                    statusCode = 404;
                    response = "Nessuna prenotazione trovata con ID " + id;
                }
            } catch (NumberFormatException e) {
                statusCode = 400;
                response = "ID non valido: " + idParam;
            } catch (SQLException e) {
                statusCode = 500;
                response = "Errore database: " + e.getMessage();
            }
        }

        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    /**
     * Esegue l'UPDATE sul campo esito della prenotazione.
     */
    private static int annullaPrenotazione(long id) throws SQLException {
        String sql = "UPDATE prenotazione SET esito = 'Annullata' WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }


    /*ATTENZIONE questo motodo va all'inizio del main del programma su cui avvierai se lo vuoi
    provare sulla tua macchina avvia prima il server(LocalQueryServer)
    public static void main(String[] args) throws IOException {
        LocalQueryServer.start(8000);
    }*/
}

//comandi utili all'evenenzia per la powershell di windows
//netstat -aon | findstr ":8000"
//taskkill /PID 1234 /F