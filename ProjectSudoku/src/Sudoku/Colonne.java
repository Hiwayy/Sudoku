package Sudoku;

public class Colonne {

    private Cellule[] cellules;  // tableau des cellules de la colonne

    // Constructeur pour initialiser les cellules de la colonne
    public Colonne(Cellule[] cellules) {
        this.cellules = cellules;
    }

    // Vérifier si toutes les valeurs de la colonne sont uniques
    public boolean estValide() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (cellules[i].getValue() == cellules[j].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Getter pour accéder aux cellules de la colonne
    public Cellule[] getCellules() {
        return cellules;
    }
}
