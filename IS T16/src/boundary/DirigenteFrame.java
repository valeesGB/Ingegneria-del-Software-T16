package boundary;


import database.CapoFarmaciaDAO;
import database.FarmaciaDAO;
import entity.EntityCapoFarmacia;
import entity.EntityFarmacia;
import exception.DAOException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DirigenteFrame extends JFrame {
    public DirigenteFrame() {
        setTitle("Dirigente - Registrazione CapoFarmacia");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel regPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        regPanel.setBorder(BorderFactory.createTitledBorder("Aggiungi CapoFarmacia"));

        JTextField nomeField = new JTextField();
        JTextField cognomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> farmacieCombo = new JComboBox<>();

        // Carica farmacie dal DB
        try {
            FarmaciaDAO farmaciaDAO = new FarmaciaDAO();
            List<EntityFarmacia> farmacie = farmaciaDAO.findAll();
            for (EntityFarmacia fa : farmacie) {
                farmacieCombo.addItem(fa.getId() + " - " + fa.getNome());
            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Errore caricamento farmacie: " + ex.getMessage());
        }

        regPanel.add(new JLabel("Nome:"));
        regPanel.add(nomeField);
        regPanel.add(new JLabel("Cognome:"));
        regPanel.add(cognomeField);
        regPanel.add(new JLabel("Email:"));
        regPanel.add(emailField);
        regPanel.add(new JLabel("Password:"));
        regPanel.add(passwordField);
        regPanel.add(new JLabel("Farmacia:"));
        regPanel.add(farmacieCombo);

        JButton aggiungiButton = new JButton("Aggiungi");
        regPanel.add(aggiungiButton);

        panel.add(regPanel, BorderLayout.CENTER);

        JPanel reportPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton reportGiornalieroButton = new JButton("Report Giornaliero");
        reportGiornalieroButton.setPreferredSize(new Dimension(140, 28));
        JButton reportSettimanaleButton = new JButton("Report Settimanale");
        reportSettimanaleButton.setPreferredSize(new Dimension(140, 28));
        JButton reportMensileButton = new JButton("Report Mensile");
        reportMensileButton.setPreferredSize(new Dimension(140, 28));
        reportPanel.add(reportGiornalieroButton);
        reportPanel.add(reportSettimanaleButton);
        reportPanel.add(reportMensileButton);

        panel.add(reportPanel, BorderLayout.NORTH);

        aggiungiButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String farmaciaSel = (String) farmacieCombo.getSelectedItem();

            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || farmaciaSel == null) {
                JOptionPane.showMessageDialog(panel, "Compila tutti i campi!");
                return;
            }

            int idFarmacia = Integer.parseInt(farmaciaSel.split(" - ")[0]);
            try {
                CapoFarmaciaDAO capoDAO = new CapoFarmaciaDAO();
                // Controllo unicità capo per farmacia
                if (capoDAO.existsByFarmaciaId(idFarmacia)) {
                    JOptionPane.showMessageDialog(panel, "Questa farmacia ha già un CapoFarmacia! Scegli un'altra farmacia.");
                    return;
                }
                EntityCapoFarmacia nuovo = new EntityCapoFarmacia();
                nuovo.setNome(nome);
                nuovo.setCognome(cognome);
                nuovo.setEmail(email);
                nuovo.setPassword(password);
                nuovo.setIdFarmacia(idFarmacia);
                capoDAO.insert(nuovo);

        try {
            mailer.Sender mailer = new mailer.Sender();
            String subject = "Credenziali CapoFarmacia";
            String body = "Ciao " + nome + ",\n\n" +
                        "Sei stato registrato come CapoFarmacia.\n" +
                        "Email: " + email + "\n" +
                        "Password: " + password + "\n\n" +
                        "Accedi al sistema per iniziare.";
            mailer.sendSimpleMail(email, subject, body, 0);
        } catch (javax.mail.MessagingException mex) {
            JOptionPane.showMessageDialog(panel, "Errore invio mail: " + mex.getMessage());
        }

        JOptionPane.showMessageDialog(panel, "CapoFarmacia aggiunto con successo!");

            JOptionPane.showMessageDialog(panel, "CapoFarmacia aggiunto con successo!");
                JOptionPane.showMessageDialog(panel, "CapoFarmacia aggiunto con successo!");
                nomeField.setText("");
                cognomeField.setText("");
                emailField.setText("");
                passwordField.setText("");
            } catch (DAOException ex2) {
                JOptionPane.showMessageDialog(panel, "Errore inserimento: " + ex2.getMessage());
            }
        });

        reportGiornalieroButton.addActionListener(e -> generaReport("giorno"));
reportSettimanaleButton.addActionListener(e -> generaReport("settimana"));
reportMensileButton.addActionListener(e -> generaReport("mese"));

        JButton logoutButton = new JButton("Logout");
        panel.add(logoutButton, BorderLayout.SOUTH);
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        add(panel);
    }

    private void generaReport(String tipo) {
        StringBuilder report = new StringBuilder();
        try {
            database.FarmaciaDAO farmaciaDAO = new database.FarmaciaDAO();
            database.PrenotazioneDAO prenotazioneDAO = new database.PrenotazioneDAO();
            List<entity.EntityFarmacia> farmacie = farmaciaDAO.findAll();

            java.text.SimpleDateFormat sdfInput = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.text.SimpleDateFormat sdfMonth = new java.text.SimpleDateFormat("MM/yyyy");
            java.text.SimpleDateFormat sdfISO = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataInizio = null, dataFine = null;

            if (tipo.equals("giorno")) {
                String giorno = JOptionPane.showInputDialog(this, "Inserisci data (GG/MM/AAAA):");
                dataInizio = sdfInput.parse(giorno);
                dataFine = dataInizio;
            } else if (tipo.equals("settimana")) {
                String giorno = JOptionPane.showInputDialog(this, "Inserisci data di inizio settimana (GG/MM/AAAA):");
                dataInizio = sdfInput.parse(giorno);
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(dataInizio);
                cal.add(java.util.Calendar.DAY_OF_MONTH, 6);
                dataFine = cal.getTime();
            } else if (tipo.equals("mese")) {
                String mese = JOptionPane.showInputDialog(this, "Inserisci mese (MM/AAAA):");
                dataInizio = sdfMonth.parse(mese);
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTime(dataInizio);
                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                dataInizio = cal.getTime();
                cal.add(java.util.Calendar.MONTH, 1);
                cal.add(java.util.Calendar.DAY_OF_MONTH, -1);
                dataFine = cal.getTime();
            }

            for (entity.EntityFarmacia f : farmacie) {
                int prenotazioni = 0;
                int annullamenti = 0;
                int effettuati = 0;
                List<entity.EntityPrenotazione> lista = prenotazioneDAO.getPrenotazioniByFarmacia(f.getId());
                for (entity.EntityPrenotazione p : lista) {
                    java.util.Date dataP = p.getDataPrenotazione();
                    if (!dataP.before(dataInizio) && !dataP.after(dataFine)) {
                        prenotazioni++;
                        if ("Annullata".equalsIgnoreCase(p.getEsito().trim())) {
                            annullamenti++;
                        }
                        if ("Effettuata".equalsIgnoreCase(p.getEsito().trim())) {
                            effettuati++;
                        }
                    }
                }
                report.append("Farmacia: ").append(f.getNome()).append("\n")
                      .append("  Prenotazioni: ").append(prenotazioni).append("\n")
                      .append("  Annullamenti: ").append(annullamenti).append("\n")
                      .append("  Vaccini effettuati: ").append(effettuati).append("\n\n");
            }

            JTextArea area = new JTextArea(report.toString());
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(400, 250));
            JOptionPane.showMessageDialog(this, scroll, "Report " + tipo, JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore generazione report: " + ex.getMessage());
        }
    }
}