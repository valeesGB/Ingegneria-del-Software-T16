package mailer;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Sender {

    // SMTP e credenziali
    private final String senderEmail       = "framacianumero1@gmail.com";
    private final String senderAppPassword = "buwr xlys krky lmxo";  // app-password Gmail
    private final String smtpHost          = "smtp.gmail.com";
    private final String smtpPortSSL       = "465";

    /**
     * Invia una mail HTML contenente un pulsante che richiama
     * l'endpoint locale per annullare la prenotazione con l'ID fornito.
     *
     * @param receiverEmail indirizzo del destinatario
     * @param subject       oggetto della mail
     * @param textBody      testo plain-text di fallback
     * @param reservationId ID della prenotazione da annullare
     * @throws MessagingException in caso di errore SMTP
     */
    public void sendDeleteButtonMail(
            String receiverEmail,
            String d,
            String h,
            long reservationId
    ) throws MessagingException {
        // 0) inizializzazione variabili
        String subject = "Prenotazione";
        String textBody = "La tua prenotazione è fissata per il giorno " + d + " alle ore: " + h + "\n \n Clicca sul pulsante qui sotto per eliminare la prenotazione dal database:";

        // 1) configura le proprietà SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPortSSL);
        props.put("mail.smtp.socketFactory.port", smtpPortSSL);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        // 2) crea la session autenticata
        Session session = Session.getInstance(props, new SMTPAuthenticator());
        session.setDebug(true);  // true per vedere il log SMTP

        // 3) prepara il messaggio
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiverEmail, false)
        );
        message.setSubject(subject);

        // 4) costruisci multipart/alternative (text + html)
        MimeMultipart multipart = new MimeMultipart("alternative");

        // 4a) parte plain-text
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(textBody, "utf-8");

        // 4b) parte HTML con pulsante
        String annulUrl = "http://localhost:8000/annullaPrenotazione?id=" + reservationId;
        String html =
                "<html>" +
                  "<body style=\"font-family:Arial,sans-serif;\">" +
                    "<p>" + textBody + "</p>" +
                    "<p>" +
                      "<a href=\"" + annulUrl + "\" " +
                         "style=\"" +
                           "display:inline-block;" +
                           "padding:10px 20px;" +
                           "font-size:16px;" +
                           "color:#ffffff;" +
                           "background-color:#dc3545;" +
                           "text-decoration:none;" +
                           "border-radius:5px;" +
                         "\">" +
                        "Annulla Prenotazione" +
                      "</a>" +
                    "</p>" +
                  "</body>" +
                "</html>";
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(html, "text/html; charset=utf-8");

        multipart.addBodyPart(textPart);
        multipart.addBodyPart(htmlPart);

        // 5) invia il messaggio
        message.setContent(multipart);
        Transport.send(message);

        System.out.println("Mail inviata a " +
            receiverEmail +
            " con link di annullamento per ID=" +
            reservationId);
    }

    public void sendSimpleMail(
        String receiverEmail,
        String subject,
        String textBody,
        long reservationID
    ) throws MessagingException {
        // 1) configura le proprietà SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPortSSL);
        props.put("mail.smtp.socketFactory.port", smtpPortSSL);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        // 2) crea la session autenticata
        Session session = Session.getInstance(props, new SMTPAuthenticator());
        session.setDebug(true);  // true per vedere il log SMTP

        // 3) prepara il messaggio
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(receiverEmail, false)
        );
        message.setSubject(subject);

        // 4) corpo plain-text
        message.setText(textBody, "utf-8");

        // 5) invia il messaggio
        Transport.send(message);
        System.out.println("Mail inviata a " +
            receiverEmail +
            " per prenotazione ID=" +
            reservationID);
    }

    /**
     * Inner class che fornisce l'app-password per l'autenticazione.
     */
    private class SMTPAuthenticator extends Authenticator {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderAppPassword);
        }
    }
}