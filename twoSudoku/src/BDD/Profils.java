package BDD;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import twoSudoku.Sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Profils {
	
	public  JPanel panel = new JPanel();
    private JFrame frame;
    private Sudoku sudokuGame;
    private JList<String> userList;
    private DefaultListModel<String> listModel;
    private JLabel scoreLabel;
    private JButton createButton;
    private JButton deleteButton;
    private int victoires;
    private int defaites;

    @SuppressWarnings("static-access")
	public Profils() {
        initialize();
        populateUserList();
        
        this.sudokuGame = new Sudoku();		// Instanciez Sudoku ici
        
        //ajout du bouton jouer
        JButton startGameButton = new JButton("Jouer");
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	startGame();
            }
        });
        panel.add(startGameButton);
        }
        
    public void startGame() {
        String selectedUser = userList.getSelectedValue();
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un utilisateur pour commencer le jeu.");
            return;
        }
        int userId = Integer.parseInt(selectedUser.split(":")[0].trim());
        sudokuGame.DebutJeu(userId); // Suppose que la méthode startGame() de Sudoku prend l'ID utilisateur en argument
    }

    
    public void startInterface() {
    	this.frame.setVisible(true);
    }
    
    public void insertGameResult(int Player_id, int victoire, int defaite) {
        String url = "jdbc:mysql://localhost:3306/sudoku";
        String username = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO game (Player_id, Victoires, Defaites) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Player_id);
            statement.setInt(2, victoire);
            statement.setInt(3, defaite);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Un nouveau jeu a été inséré avec succès !");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    private void initialize() {
    	scoreLabel = new JLabel();
    	panel.add(scoreLabel);
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);

        userList = new JList<String>();
        userList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    int userId = Integer.parseInt(selectedUser.split(":")[0].trim());
                }
            }
        });

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

            String query = "SELECT p.Player_id, p.Name, g.Victoires, g.Defaites FROM Player p LEFT JOIN Game g ON p.Player_id = g.Player_id";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            listModel = new DefaultListModel<String>();
            while (resultSet.next()) {
                int playerId = resultSet.getInt("Player_id");
                String name = resultSet.getString("Name");
                int victories = resultSet.getInt("Victoires");
                int defeats = resultSet.getInt("Defaites");
                String profile = playerId + ": " + name + " (Victoires: " + victories + ", Défaites: " + defeats + ")";
                listModel.addElement(profile);
                if (userList.getSelectedValue() != null && userList.getSelectedValue().startsWith(playerId + ":")) {
                    scoreLabel.setText("Victoires " + victories + ", Défaites");
                }
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
    
    private void updateGameResults(int playerId, boolean Victoire) {
        // Vos informations de connexion à la base de données
        String BDD = "sudoku";
        String url = "jdbc:mysql://localhost:3306/" + BDD;
        String user = "root";
        String passwd = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, passwd);

            // Mettre à jour les victoires et les défaites du joueur
            String updateQuery = "";
            if (Victoire) {
                updateQuery = "UPDATE Game SET Victoires = Victoires + 1 WHERE Player_id = ?";
            } else {
                updateQuery = "UPDATE Game SET Defaites = Defaites + 1 WHERE Player_id = ?";
            }
            
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setInt(1, playerId);
            updateStatement.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }
    }



    
}

