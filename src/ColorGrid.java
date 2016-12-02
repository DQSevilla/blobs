/* ColorGrid.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla
 */

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

/**
 * The ColorGrid object is the main grid in Blobs
 *
 * @author David Sevilla
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

    private void initGrid() {
        int rows, cols;
        rows = cols = colors.length;
        this.setLayout(new GridLayout(rows, cols));
        Dimension prefSize = new Dimension(CELL_DIM, CELL_DIM);

        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                MyColor color = (Math.random() > 0.5) ? MyColor.BLACK : MyColor.WHITE;
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

    // TODO: See if I can work around passing around a Blobs instance to the listener
}
