package boundary;

import database.PrenotazioneDAO;
import entity.EntityPrenotazione;
import exception.DAOException;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class FarmacistaFrame extends JFrame {
    private String email;
    private JTable table;
    private List<EntityPrenotazione> prenotazioni;
    private PrenotazioneDAO prenotazioneDAO = new PrenotazioneDAO();

    public FarmacistaFrame(String email) {
        this.email = email;
        setTitle("Farmacista");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Benvenuto, Farmacista: " + email);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        JButton stampaButton = new JButton("Stampa Prenotazioni");
        JButton anamnesiButton = new JButton("Registrazione Dati Anamnestici");
        JButton esitoButton = new JButton("Registrazione Esito Vaccinazione");
        JButton logoutButton = new JButton("Logout");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(stampaButton);
        buttonPanel.add(anamnesiButton);
        buttonPanel.add(esitoButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Tabella prenotazioni
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        stampaButton.addActionListener(e -> stampaPrenotazioni());
        anamnesiButton.addActionListener(e -> registraAnamnesi());
        esitoButton.addActionListener(e -> registraEsito());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private void stampaPrenotazioni() {
    try {
        // Recupera idFarmacia del farmacista loggato
        database.FarmacistaDAO farmacistaDAO = new database.FarmacistaDAO();
        entity.EntityFarmacista farmacista = farmacistaDAO.findByEmail(email);
        if (farmacista == null) {
            JOptionPane.showMessageDialog(this, "Errore: farmacista non trovato.");
            return;
        }
        int idFarmacia = farmacista.getIdFarmacia();

        prenotazioni = prenotazioneDAO.getPrenotazioniByFarmacia(idFarmacia);
        String[] columns = {"Vaccino", "Cliente", "Data", "Ora", "Anamnesi", "Esito"};
        String[][] data = new String[prenotazioni.size()][columns.length];
        for (int i = 0; i < prenotazioni.size(); i++) {
            EntityPrenotazione p = prenotazioni.get(i);
            data[i][0] = p.getVaccinoType();
            data[i][1] = p.getCF();
            data[i][2] = p.getDataPrenotazione().toString();
            data[i][3] = p.getOrarioPrenotazione().toString();
            data[i][4] = p.getAnamnesi();
            data[i][5] = esitoToString(p.getEsito());
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    } catch (DAOException ex) {
        JOptionPane.showMessageDialog(this, "Errore caricamento prenotazioni: " + ex.getMessage());
    }
}

// Funzione di utilità per convertire il codice esito in stringa
private String esitoToString(Object esito) {
    if (esito == null) return "";
    String val = esito.toString().trim();
    if (val.equalsIgnoreCase("Effettuata")) return "Effettuata";
    if (val.equalsIgnoreCase("Non effettuata")) return "Non effettuata";
    if (val.equalsIgnoreCase("Annullata")) return "Annullata";
    return val;
}

    private void registraAnamnesi() {
        int row = table.getSelectedRow();
        if (row == -1 || prenotazioni == null || prenotazioni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleziona una prenotazione dalla tabella.");
            return;
        }
        int idPrenotazione = prenotazioni.get(row).getId();
        String anamnesi = JOptionPane.showInputDialog(this, "Inserisci dati anamnestici:");
        if (anamnesi != null && !anamnesi.trim().isEmpty()) {
            try {
                prenotazioneDAO.aggiornaAnamnesi(idPrenotazione, anamnesi);
                JOptionPane.showMessageDialog(this, "Dati anamnestici aggiornati.");
                stampaPrenotazioni();
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Errore aggiornamento: " + ex.getMessage());
            }
        }
    }

    private void registraEsito() {
        int row = table.getSelectedRow();
        if (row == -1 || prenotazioni == null || prenotazioni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleziona una prenotazione dalla tabella.");
            return;
        }
        int idPrenotazione = prenotazioni.get(row).getId();
        String[] opzioni = {"Effettuata", "Non effettuata", "Annullata"};
        String esito = (String) JOptionPane.showInputDialog(
                this, "Seleziona esito vaccinazione:", "Esito",
                JOptionPane.QUESTION_MESSAGE, null, opzioni, opzioni[0]);
        if (esito != null) {
            try {
                prenotazioneDAO.aggiornaEsito(idPrenotazione, esito);
                JOptionPane.showMessageDialog(this, "Esito aggiornato.");
                stampaPrenotazioni();
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(this, "Errore aggiornamento: " + ex.getMessage());
            }
        }
    }
    
    
    
}

