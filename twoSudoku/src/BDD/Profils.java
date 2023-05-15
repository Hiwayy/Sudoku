package BDD;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Profils {
    private JFrame frame;
    private JComboBox<String> comboBox;
    private JButton createButton;
    private JButton deleteButton;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Profils window = new Profils();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Profils() {
        initialize();
        populateComboBox();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        comboBox = new JComboBox<String>();
        panel.add(comboBox);
        
        createButton = new JButton("Créer Utilisateur");
        panel.add(createButton);
        
        deleteButton = new JButton("Supprimer Utilisateur");
        panel.add(deleteButton);
        
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
    }
    

    private void populateComboBox() {
        // Vos informations de connexion à une base de données
        String BDD = "sudoku";
        String url = "jdbc:mysql://localhost:3306/" + BDD;
        String user = "root";
        String passwd = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connecté");

            String query = "SELECT Player_id, Name FROM player";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<String> profiles = new ArrayList<String>();
            while (resultSet.next()) {
                int playerId = resultSet.getInt("Player_id");
                String name = resultSet.getString("Name");
                String profile = playerId + ": " + name;
                comboBox.addItem(profile);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }
    }


            
            private void createUser() {
                // Vos informations de connexion à la base de données
                String BDD = "sudoku";
                String url = "jdbc:mysql://localhost:3306/" + BDD;
                String user = "root";
                String passwd = "";

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(url, user, passwd);

                    // Obtenir le nom d'utilisateur pour le nouvel utilisateur (par exemple, à partir d'un champ de texte)
                    String username = JOptionPane.showInputDialog(frame, "Nom d'utilisateur :");

                    // Insérer le nouvel utilisateur dans la base de données
                    String query = "INSERT INTO player (Name) VALUES (?)";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, username);
                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "Utilisateur créé avec succès !");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la création de l'utilisateur.");
                    }
             

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }
    }


private void deleteUser() {
    // Vos informations de connexion à la base de données
    String BDD = "sudoku";
    String url = "jdbc:mysql://localhost:3306/" + BDD;
    String user = "root";
    String passwd = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, passwd);

        // Obtenir l'utilisateur sélectionné dans la liste déroulante
        String selectedProfile = comboBox.getSelectedItem().toString();
        int playerId = Integer.parseInt(selectedProfile.split(":")[0].trim());

        // Supprimer l'utilisateur de la base de données
        String query = "DELETE FROM player WHERE Player_id = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, playerId);
        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(frame, "Utilisateur supprimé avec succès !");
            populateComboBox();
        } else {
            JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression de l'utilisateur.");
        }

        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Erreur");
        System.exit(0);
    }
}
}
