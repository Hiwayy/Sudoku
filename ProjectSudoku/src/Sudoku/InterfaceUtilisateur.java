package Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceUtilisateur {
    private JFrame frame;
    private JPanel gridPanel;
    private JButton[][] gridButtons;
    private JTextField statusField;
    private Sudoku sudoku;

    public InterfaceUtilisateur(Sudoku sudoku) {
        this.sudoku = sudoku;

        // create the frame
        frame = new JFrame("Sudoku");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the grid panel
        gridPanel = new JPanel(new GridLayout(9, 9, 1, 1));
        gridButtons = new JButton[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gridButtons[i][j] = new JButton("");
                gridButtons[i][j].addActionListener(new GridButtonListener(i, j));
                gridPanel.add(gridButtons[i][j]);
            }
        }

        // create the status field
        statusField = new JTextField();
        statusField.setEditable(false);
        statusField.setHorizontalAlignment(JTextField.CENTER);

        // add the components to the frame
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(statusField, BorderLayout.SOUTH);

        // display the frame
        frame.setVisible(true);
    }

    public void updateGrid() {
        int[][] grid = sudoku.getGrid();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    gridButtons[i][j].setText("");
                } else {
                    gridButtons[i][j].setText(Integer.toString(grid[i][j]));
                }
            }
        }
    }

    public void setStatus(String status) {
        statusField.setText(status);
    }

    private class GridButtonListener implements ActionListener {
        private int row;
        private int col;

        public GridButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent event) {
            String input = JOptionPane.showInputDialog(frame, "Enter a number (1-9)", "");
            if (input == null || input.equals("")) {
                return;
            }
            try {
                int value = Integer.parseInt(input);
                sudoku.setCell(row, col, value);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

	public void lancer() {
		this.frame.setVisible(true);
	}

}

