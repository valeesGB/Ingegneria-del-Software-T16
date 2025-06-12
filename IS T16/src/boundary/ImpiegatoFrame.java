package boundary;

import database.FarmacistaDAO;
import database.FarmacistaLiquidatoDAO;
import database.TurnoDAO;
import entity.EntityFarmacista;
import entity.EntityFarmacistaLiquidato;
import entity.EntityTurno;
import exception.DAOException;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ImpiegatoFrame extends JFrame {
    public ImpiegatoFrame() {
        setTitle("Impiegato Amministrativo");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton reportButton = new JButton("Genera Report Farmacista");
        reportButton.setPreferredSize(new Dimension(180, 30)); // Bottone più piccolo

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(reportButton);

        reportButton.addActionListener(e -> generaReport());

        // --- Bottone Logout ---
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 28));
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(logoutButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void generaReport() {
        StringBuilder report = new StringBuilder();

        try {
            // Farmacisti attivi
            FarmacistaDAO farmacistaDAO = new FarmacistaDAO();
            List<EntityFarmacista> farmacisti = farmacistaDAO.getAll();
            TurnoDAO turnoDAO = new TurnoDAO();

            if (farmacisti.isEmpty()) {
                report.append("Nessun farmacista attivo trovato.\n\n");
            } else {
                for (EntityFarmacista f : farmacisti) {
                    report.append("Farmacista: ").append(f.getNome()).append(" ").append(f.getCognome())
                          .append(" (").append(f.getEmail()).append(")\n");
                    List<EntityTurno> turni = turnoDAO.getTurniFarmacista(f.getEmail(), null); // null = tutti i giorni
                    report.append("  Turni svolti: ").append(turni != null ? turni.size() : 0).append("\n\n");
                }
            }

            // Farmacisti liquidati
            FarmacistaLiquidatoDAO liquidatoDAO = new FarmacistaLiquidatoDAO();
            List<EntityFarmacistaLiquidato> liquidati = liquidatoDAO.getAll();
            if (liquidati.isEmpty()) {
                report.append("Nessun farmacista liquidato.\n");
            } else {
                for (EntityFarmacistaLiquidato l : liquidati) {
                    report.append("Farmacista LIQUIDATO: ").append(l.getNome()).append(" ").append(l.getCognome())
                          .append(" (").append(l.getEmail()).append(")\n");
                    report.append("  DA RICOMPENSARE DELLA LIQUIDAZIONE\n\n");
                }
            }

            JTextArea area = new JTextArea(report.toString());
            area.setEditable(false);
            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(400, 250));
            JOptionPane.showMessageDialog(this, scroll, "Report Farmacisti", JOptionPane.INFORMATION_MESSAGE);

        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(this, "Errore generazione report: " + ex.getMessage());
        }
    }
}