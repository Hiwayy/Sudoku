package Gamefinal;

import BDD.*;
import twoSudoku.*;

public class Gamefinal {

    @SuppressWarnings("static-access")
	public static void main(String[] args) {
        Profils profils1 = new Profils();
        Sudoku sudoku1 = new Sudoku();

        profils1.main(args);
        sudoku1.main(args);
    }
}
