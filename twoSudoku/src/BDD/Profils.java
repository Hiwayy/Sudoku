package BDD;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profils {
    private JFrame frame;
    private JList<String> userList;
    private DefaultListModel<String> listModel;
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
        populateUserList();
    }
    

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        userList = new JList<String>();
        panel.add(new JScrollPane(userList));

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

    private void populateUserList() {
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

            listModel = new DefaultListModel<String>();
            while (resultSet.next()) {
                int playerId = resultSet.getInt("Player_id");
                String name = resultSet.getString("Name");
                String profile = playerId + ": " + name;
                listModel.addElement(profile);
            }
            userList.setModel(listModel);

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
                // Actualiser la liste des utilisateurs
                populateUserList();
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

            // Obtenir l'utilisateur sélectionné dans la liste
            String selectedUser = userList.getSelectedValue();
            if (selectedUser == null) {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un utilisateur à supprimer.");
                return;
            }

            // Extraire l'ID de l'utilisateur
            int userId = Integer.parseInt(selectedUser.split(":")[0].trim());

            // Supprimer l'utilisateur de la base de données
            String query = "DELETE FROM player WHERE Player_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(frame, "Utilisateur supprimé avec succès !");
                // Actualiser la liste des utilisateurs
                populateUserList();
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

