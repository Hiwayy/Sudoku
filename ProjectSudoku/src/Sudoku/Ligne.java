package Sudoku;

public class Ligne {
    
    private int[] valeurs;  // tableau des valeurs de la ligne

    // Constructeur pour initialiser les valeurs de la ligne
    public Ligne(int[] valeurs) {
        this.valeurs = valeurs;
    }

    // Vérifier si toutes les valeurs de la ligne sont uniques
    public boolean estValide() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (valeurs[i] == valeurs[j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Getter pour accéder aux valeurs de la ligne
    public int[] getValeurs() {
        return valeurs;
    }
}

