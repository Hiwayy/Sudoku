package BDD;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Profils {
    private JFrame frame;
    private JComboBox<String> comboBox;

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
                profiles.add(profile);
            }

            // Ajouter les profils à la liste déroulante
            for (String profile : profiles) {
                comboBox.addItem(profile);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }
    }
}
