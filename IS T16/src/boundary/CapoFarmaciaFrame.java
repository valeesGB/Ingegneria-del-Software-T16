package boundary;

import database.FarmacistaDAO;
import entity.EntityFarmacista;
import exception.DAOException;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

public class CapoFarmaciaFrame extends JFrame {
    private String email;

    public CapoFarmaciaFrame(String email) {
        this.email = email;
        setTitle("Capo Farmacia");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Benvenuto, Capo Farmacia: " + email);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        // --- Pannello Aggiunta Nuovo Farmacista ---
        JPanel regPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        regPanel.setBorder(BorderFactory.createTitledBorder("Aggiungi Nuovo Farmacista"));

        JTextField nomeField = new JTextField();
        JTextField cognomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        regPanel.add(new JLabel("Nome:"));
        regPanel.add(nomeField);
        regPanel.add(new JLabel("Cognome:"));
        regPanel.add(cognomeField);
        regPanel.add(new JLabel("Email:"));
        regPanel.add(emailField);
        regPanel.add(new JLabel("Password:"));
        regPanel.add(passwordField);

        JButton aggiungiButton = new JButton("Aggiungi");
        regPanel.add(aggiungiButton);

        panel.add(regPanel, BorderLayout.CENTER);

        // --- Pannello bottoni sotto ---
        JButton turniButton = new JButton("Definire Turni Farmacisti");
        JButton liquidazioneButton = new JButton("Liquidazione Farmacista");
        JButton eliminaPrenAnnullateButton = new JButton("Elimina Prenotazioni Annullate");
        JButton logoutButton = new JButton("Logout");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        buttonPanel.add(turniButton);
        buttonPanel.add(liquidazioneButton);
        buttonPanel.add(eliminaPrenAnnullateButton);
        buttonPanel.add(logoutButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Azione bottone Aggiungi
        aggiungiButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String emailF = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (nome.isEmpty() || cognome.isEmpty() || emailF.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Compila tutti i campi!");
                return;
            }

            int idFarmacia = getIdFarmaciaCapo();
            try {
                FarmacistaDAO farmacistaDAO = new FarmacistaDAO();
                EntityFarmacista nuovo = new EntityFarmacista();
                nuovo.setNome(nome);
                nuovo.setCognome(cognome);
                nuovo.setEmail(emailF);
                nuovo.setPassword(password);
                nuovo.setIdFarmacia(idFarmacia);
                farmacistaDAO.insert(nuovo);
                try {
                    mailer.Sender mailer = new mailer.Sender();
                    String subject = "Credenziali Farmacista";
                    String body = "Ciao " + nome + ",\n\n" +
                            "Sei stato registrato come Farmacista.\n" +
                            "Email: " + emailF + "\n" +
                            "Password: " + password + "\n\n" +
                            "Accedi al sistema per iniziare.";
                    mailer.sendSimpleMail(emailF, subject, body, 0);
                } catch (javax.mail.MessagingException mex) {
                    JOptionPane.showMessageDialog(panel, "Errore invio mail: " + mex.getMessage());
                }
                JOptionPane.showMessageDialog(panel, "Farmacista aggiunto con successo!");
                nomeField.setText("");
                cognomeField.setText("");
                emailField.setText("");
                passwordField.setText("");
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(panel, "Errore inserimento: " + ex.getMessage());
            }
        });

        // Listener nuovi pulsanti
        turniButton.addActionListener(e -> definireTurniFarmacisti());
        liquidazioneButton.addActionListener(e -> liquidazioneFarmacista());
        eliminaPrenAnnullateButton.addActionListener(e -> eliminaPrenotazioniAnnullate());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        add(panel);
    }

    // ----------- FUNZIONE: Definire Turni Farmacisti -----------
    private void definireTurniFarmacisti() {
        try {
            database.FarmacistaDAO farmacistaDAO = new database.FarmacistaDAO();
            List<entity.EntityFarmacista> farmacisti = farmacistaDAO.getAll();
            JComboBox<String> farmacistaCombo = new JComboBox<>();
            for (entity.EntityFarmacista f : farmacisti) {
                if (f.getIdFarmacia() == getIdFarmaciaCapo()) {
                    farmacistaCombo.addItem(f.getEmail() + " - " + f.getNome() + " " + f.getCognome());
                }
            }
            String[] fasce = {"8-14", "14-20"};
            JComboBox<String> fasciaCombo = new JComboBox<>(fasce);
            JTextField dataField = new JTextField("GG/MM/AAAA");

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Farmacista:"));
            panel.add(farmacistaCombo);
            panel.add(new JLabel("Fascia oraria:"));
            panel.add(fasciaCombo);
            panel.add(new JLabel("Data:"));
            panel.add(dataField);

            int res = JOptionPane.showConfirmDialog(this, panel, "Definisci Turno", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String emailFarmacista = ((String) farmacistaCombo.getSelectedItem()).split(" - ")[0];
                String fascia = (String) fasciaCombo.getSelectedItem();
                String dataStr = dataField.getText().trim();
                String[] parts = dataStr.split("/");
                java.sql.Date giorno = java.sql.Date.valueOf(parts[2] + "-" + parts[1] + "-" + parts[0]);
                int tipoTurno = fascia.equals("8-14") ? 1 : 2;
                database.TurnoDAO turnoDAO = new database.TurnoDAO();
                turnoDAO.addTurno(new entity.EntityTurno(0, emailFarmacista, giorno, tipoTurno));
                JOptionPane.showMessageDialog(this, "Turno assegnato!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore assegnazione turno: " + ex.getMessage());
        }
    }

    // ----------- FUNZIONE: Liquidazione Farmacista -----------
    private void liquidazioneFarmacista() {
        try {
            database.FarmacistaDAO farmacistaDAO = new database.FarmacistaDAO();
            List<entity.EntityFarmacista> farmacisti = farmacistaDAO.getAll();
            JComboBox<String> farmacistaCombo = new JComboBox<>();
            for (entity.EntityFarmacista f : farmacisti) {
                if (f.getIdFarmacia() == getIdFarmaciaCapo()) {
                    farmacistaCombo.addItem(f.getEmail() + " - " + f.getNome() + " " + f.getCognome());
                }
            }
            int res = JOptionPane.showConfirmDialog(this, farmacistaCombo, "Seleziona farmacista da liquidare", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                String emailFarmacista = ((String) farmacistaCombo.getSelectedItem()).split(" - ")[0];
                entity.EntityFarmacista f = null;
                for (entity.EntityFarmacista ff : farmacisti) {
                    if (ff.getEmail().equals(emailFarmacista)) {
                        f = ff;
                        break;
                    }
                }
                if (f == null) {
                    JOptionPane.showMessageDialog(this, "Farmacista non trovato.");
                    return;
                }
                database.FarmacistaLiquidatoDAO liquidatoDAO = new database.FarmacistaLiquidatoDAO();
                entity.EntityFarmacistaLiquidato liquidato = new entity.EntityFarmacistaLiquidato(
                    0, f.getNome(), f.getCognome(), f.getEmail(), new java.util.Date()
                );
                liquidatoDAO.addFarmacistaLiquidato(liquidato);
                String query = "DELETE FROM farmacista WHERE Email = ?";
                try (java.sql.Connection conn = database.DBManager.getConnection();
                     java.sql.PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, f.getEmail());
                    ps.executeUpdate();
                }
                JOptionPane.showMessageDialog(this, "Farmacista liquidato!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore liquidazione: " + ex.getMessage());
        }
    }

    // ----------- Utility per idFarmacia del capo -----------
    private int getIdFarmaciaCapo() {
        try {
            database.CapoFarmaciaDAO capoDAO = new database.CapoFarmaciaDAO();
            entity.EntityCapoFarmacia capo = capoDAO.findByEmail(email);
            return capo.getIdFarmacia();
        } catch (DAOException ex) {
            return -1;
        }
    }

    // ----------- Elimina Prenotazioni Annullate -----------
    private void eliminaPrenotazioniAnnullate() {
        try {
            database.PrenotazioneDAO prenotazioneDAO = new database.PrenotazioneDAO();
            int idFarmacia = getIdFarmaciaCapo();
            java.util.List<entity.EntityPrenotazione> annullate = new java.util.ArrayList<>();
            for (entity.EntityPrenotazione p : prenotazioneDAO.getPrenotazioniByFarmacia(idFarmacia)) {
    String esito = p.getEsito();
    if (esito != null && "Annullata".equalsIgnoreCase(esito.trim())) {
        annullate.add(p);
    }
}
            if (annullate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nessuna prenotazione annullata da eliminare.");
                return;
            }
            String[] opzioni = new String[annullate.size()];
            for (int i = 0; i < annullate.size(); i++) {
                entity.EntityPrenotazione p = annullate.get(i);
                opzioni[i] = "ID: " + p.getId() + " - " + p.getCF() + " - " + p.getDataPrenotazione() + " " + p.getOrarioPrenotazione();
            }
            String scelta = (String) JOptionPane.showInputDialog(
                this,
                "Seleziona la prenotazione annullata da eliminare:",
                "Elimina Prenotazione Annullata",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opzioni,
                opzioni[0]
            );
            if (scelta != null) {
                int id = Integer.parseInt(scelta.split(" ")[1]);
                int conferma = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare la prenotazione ID " + id + "?", "Conferma eliminazione", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    String query = "DELETE FROM prenotazione WHERE Id = ?";
                    try (java.sql.Connection conn = database.DBManager.getConnection();
                         java.sql.PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setInt(1, id);
                        ps.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(this, "Prenotazione eliminata!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore eliminazione: " + ex.getMessage());
        }
    }
}