package Sudoku;

public class Sudoku {
    private int[][] grid;

    public Sudoku(int[][] grid) {
        this.grid = grid;
    }

    public boolean checkRules() {
        // Check rows
        for (int row = 0; row < 9; row++) {
            boolean[] found = new boolean[9];
            for (int col = 0; col < 9; col++) {
                int value = grid[row][col];
                if (value != 0) {
                    if (found[value - 1]) {
                        return false;
                    } else {
                        found[value - 1] = true;
                    }
                }
            }
        }

        // Check columns
        for (int col = 0; col < 9; col++) {
            boolean[] found = new boolean[9];
            for (int row = 0; row < 9; row++) {
                int value = grid[row][col];
                if (value != 0) {
                    if (found[value - 1]) {
                        return false;
                    } else {
                        found[value - 1] = true;
                    }
                }
            }
        }

        // Check regions
        for (int i = 0; i < 9; i++) {
            boolean[] found = new boolean[9];
            for (int j = 0; j < 9; j++) {
                int row = (i / 3) * 3 + (j / 3);
                int col = (i % 3) * 3 + (j % 3);
                int value = grid[row][col];
                if (value != 0) {
                    if (found[value - 1]) {
                        return false;
                    } else {
                        found[value - 1] = true;
                    }
                }
            }
        }

        return true;
    }

    public boolean solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int value = 1; value <= 9; value++) {
                        grid[row][col] = value;
                        if (checkRules() && solve()) {
                            return true;
                        }
                        grid[row][col] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }

	public void setCell(int row, int col, int value) {
		// TODO Auto-generated method stub
		
	}
}

