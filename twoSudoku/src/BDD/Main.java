package BDD;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    // Vos informations de connexion à une base de données
    private static String BDD = "sudoku";
    private static String url = "jdbc:mysql://localhost:3306/" + BDD;
    private static String user = "root";
    private static String passwd = "";

    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("Connecté");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Erreur");
            System.exit(0);
        }
    }

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, passwd);
    }
}