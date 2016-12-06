
/* ColorGrid.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla, Nick Gattuso
 */

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * The ColorGrid object is the main grid in Blobs, maintaining an
 * array of colors and of JLabels to fill the GUI
 *
 * @author David Sevilla, Nick Gattuso
 * @version 1.0
 * @since 12/01/2016
 */
public class ColorGrid extends JPanel {
    private static final int CELL_DIM = 10;
    private MyColor[][] colors;
    private JLabel[][] labels;
    private BlobsMouseListener listener;

    public ColorGrid(int rows, int cols) {
        colors = new MyColor[rows][cols];
        labels = new JLabel[rows][cols];
        listener = new BlobsMouseListener(this);
        initGrid();
    }

    /**
     * Initializes the grid with a grid layout and randomly
     * selects tiles to be one of the two colors in the game.
     * MouseListeners are added to each JLabel.
     */
    private void initGrid() {
        int rows, cols;
        rows = cols = colors.length;
        this.setLayout(new GridLayout(rows, cols));
        Dimension prefSize = new Dimension(CELL_DIM, CELL_DIM);

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                MyColor dummy = new MyColor(Color.BLACK, 0); // dummy
                Color temp = (Math.random() > 0.5) ? MyColor.getOption(0) : MyColor.getOption(1);
                MyColor color = new MyColor(temp, MyColor.getNumOption(temp));
                this.colors[row][col] = color;
                label.setBackground(color.getColor());
                label.addMouseListener(this.listener);
                label.setPreferredSize(prefSize);

                this.add(label);
                this.labels[row][col] = label;
            }
        }
    }

    public MyColor[][] getColorArray() {
        return this.colors;
    }

    public JLabel[][] getJLabelArray() {
        return this.labels;
    }

    public void addBlob(Blobs blob) {
        listener.addBlob(blob);
    }

    public void changeColor(Color color, int player) {
        int opposite = (player == 0) ? 1 : 0;
        for(int i = 0; i < labels.length; i++) {
            for(int j = 0; j < labels[i].length; j++) {
                if(colors[i][j].getNumber() == player) {
                    colors[i][j] = new MyColor(color, player);
                    labels[i][j].setBackground(color);
                }
            }
        }
    }

    /**
     * Changes the color of the tile at the current given position
     * to the opposite color, updating the grid.
     * @param row the given row
     * @param col the given column
     */
    public void changeBoard(int row, int col) {
        int num = colors[row][col].getNumber();
        Color color = (num == 0) ? MyColor.getOption(1) : MyColor.getOption(0);
        colors[row][col] = new MyColor(color, MyColor.getNumOption(color));
        this.labels[row][col].setBackground(colors[row][col].getColor());
    }
}
