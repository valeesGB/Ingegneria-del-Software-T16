package boundary;

import database.FarmaciaDAO;
import database.PrenotazioneDAO;
import entity.EntityFarmacia;
import entity.EntityPrenotazione;
import exception.DAOException;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ClienteFrame extends JFrame {
    public ClienteFrame() {
        setTitle("Cliente - Prenotazione");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nomeField = new JTextField();
        JTextField cognomeField = new JTextField();
        JTextField emailField = new JTextField();
        JComboBox<String> farmaciaCombo = new JComboBox<>();
        JTextField giornoField = new JTextField("DD/MM/YYYY");
        JComboBox<String> vaccinoCombo = new JComboBox<>(new String[]{"Pfizer", "Moderna", "Johnson"});
        JTextField allergieField = new JTextField();

        // Carica farmacie dal DB
        try {
            FarmaciaDAO farmaciaDAO = new FarmaciaDAO();
            List<EntityFarmacia> farmacie = farmaciaDAO.findAll();
            for (EntityFarmacia f : farmacie) {
                farmaciaCombo.addItem(f.getId() + " - " + f.getNome() + " (" + f.getCitta() + ", " + f.getIndirizzo() + ")");
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Errore caricamento farmacie: " + ex.getMessage());
        }

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Cognome:"));
        panel.add(cognomeField);
        panel.add(new JLabel("Email (solo per conferma):"));
        panel.add(emailField);
        panel.add(new JLabel("Farmacia:"));
        panel.add(farmaciaCombo);
        panel.add(new JLabel("Giorno (DD/MM/YYYY):"));
        panel.add(giornoField);
        panel.add(new JLabel("Vaccino:"));
        panel.add(vaccinoCombo);
        panel.add(new JLabel("Allergie (opzionale):"));
        panel.add(allergieField);

        JButton prenotaButton = new JButton("Prenota");
        panel.add(prenotaButton);

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton);

        add(panel);

        prenotaButton.addActionListener(e -> {
    String nome = nomeField.getText().trim();
    String cognome = cognomeField.getText().trim();
    String email = emailField.getText().trim();
    String farmaciaSel = (String) farmaciaCombo.getSelectedItem();
    String giorno = giornoField.getText().trim();
    String vaccino = (String) vaccinoCombo.getSelectedItem();
    String allergie = allergieField.getText().trim();

    // Controllo nome
    if (nome.isEmpty()) {
        JOptionPane.showMessageDialog(panel, "Nome non valido");
        return;
    }
    // Controllo cognome
    if (cognome.isEmpty()) {
        JOptionPane.showMessageDialog(panel, "Cognome non valido");
        return;
    }
    // Controllo email con @
    if (email.isEmpty() || !email.contains("@")) {
        JOptionPane.showMessageDialog(panel, "Email senza simbolo @");
        return;
    }
    // Controllo formato data GG/MM/AAAA
    if (!giorno.matches("\\d{2}/\\d{2}/\\d{4}")) {
        JOptionPane.showMessageDialog(panel, "Data Formato non valido");
        return;
    }

    int idFarmacia = Integer.parseInt(farmaciaSel.split(" - ")[0]);

    try {
        // Conversione data da GG/MM/AAAA a java.sql.Date
        String[] parts = giorno.split("/");
        String dataISO = parts[2] + "-" + parts[1] + "-" + parts[0];
        java.sql.Date dataPren = java.sql.Date.valueOf(dataISO);

        PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();
        java.sql.Time orarioPren;
        try {
            orarioPren = prenotazioneDAO.calcolaOrarioDisponibile(idFarmacia, dataPren);
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(panel, "Data non disponibile");
            return;
        }

        EntityPrenotazione pren = new EntityPrenotazione(
            0, // id
            idFarmacia,
            nome + " " + cognome,
            dataPren,
            orarioPren,
            allergie,
            vaccino, // vaccinoType
            "Non effettuata", // esito
            0
        );
        long idPrenotazione = prenotazioneDAO.addPrenotazione(pren, idFarmacia);
    try {
    mailer.Sender mailer = new mailer.Sender();
    String subject = "Conferma Prenotazione Vaccino";
    String body = "Gentile " + nome + " " + cognome + ",\n\n" +
                  "La tua prenotazione è confermata per il giorno " + giorno +
                  " alle ore " + orarioPren + " presso la farmacia " + farmaciaSel + ".\n\n" +
                  "Allergie: " + allergie + "\n" +
                  "Vaccino: " + vaccino + "\n\n" +
                  "Se vuoi annullare la prenotazione, clicca sul pulsante qui sotto.";
    // Usa l'ID della prenotazione appena creata (recuperalo se necessario)
    mailer.sendDeleteButtonMail(email, giorno, orarioPren.toString(), idPrenotazione);
} catch (javax.mail.MessagingException mex) {
    JOptionPane.showMessageDialog(panel, "Errore invio mail: " + mex.getMessage());
}
        JOptionPane.showMessageDialog(panel, "Prenotazione effettuata per le " + orarioPren + "! Riceverai una mail di conferma.");
        nomeField.setText("");
        cognomeField.setText("");
        emailField.setText("");
        giornoField.setText("DD/MM/YYYY");
        allergieField.setText("");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(panel, "Errore prenotazione: " + ex.getMessage());
    }
});

        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
