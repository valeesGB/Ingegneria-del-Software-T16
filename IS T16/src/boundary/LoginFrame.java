package boundary;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import database.CapoFarmaciaDAO;
import database.FarmacistaDAO;
import exception.DAOException;
import mailer.LocalQueryServer;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public LoginFrame() {
        setTitle("Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10)); // Cambia da 4 a 5 righe
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        messageLabel = new JLabel("");
        panel.add(messageLabel);
        panel.add(new JLabel(""));

        loginButton = new JButton("Login");
        panel.add(loginButton);

        // AGGIUNGI QUESTO: bottone per il cliente
        JButton clienteButton = new JButton("Accedi come Cliente");
        panel.add(clienteButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // AGGIUNGI QUESTO: azione per il cliente
        clienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ClienteFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void handleLogin() {
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword()).trim();

    // Controllo dirigente
    if (email.equals("admin") && password.equals("admin")) {
        new DirigenteFrame().setVisible(true);
        dispose();
        return;
    }

    // --- ACCESSO IMPIEGATO AMMINISTRATIVO ---
    if (email.equals("impiegato") && password.equals("impiegato")) {
        new ImpiegatoFrame().setVisible(true);
        dispose();
        return;
    }

    try {
        CapoFarmaciaDAO capoDAO = new CapoFarmaciaDAO();
        if (capoDAO.authenticate(email, password)) {
            new CapoFarmaciaFrame(email).setVisible(true);
            dispose();
            return;
        }

        FarmacistaDAO farmacistaDAO = new FarmacistaDAO();
        if (farmacistaDAO.authenticate(email, password)) {
            new FarmacistaFrame(email).setVisible(true);
            dispose();
            return;
        }

        // Se le credenziali non sono valide, mostra errore
        messageLabel.setText("Email o password non valide.");
    } catch (DAOException ex) {
        messageLabel.setText("Errore: " + ex.getMessage());
    }
}

    public static void main(String[] args) {
        try {
            LocalQueryServer.start(8000); // Avvia il server locale per le query
        } catch (java.io.IOException | DAOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Errore nell'avvio del server locale: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }


    
}



