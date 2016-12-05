/* BlobsMouseListener.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla, Nick Gattuso
 */

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A mouse listener that waits for clicks on the grid from users
 * and alters the state of the board.
 *
 * @author Nick Gattuso, David Sevilla
 * @version 1.0
 * @since 12/01/2016
 */
public class BlobsMouseListener extends MouseAdapter {
    private ColorGrid grid;
    private Blobs blob;

    /**
     * Initializes the color grid to the given one
     * @param grid a ColorGrid
     */
    public BlobsMouseListener(ColorGrid grid) {
        this.grid = grid;
    }

    /**
     * Adds a Blobs object to this listener so that the pick
     * method can be accessed.
     * @param blob a Blobs object
     */
    public void addBlob(Blobs blob) {
        this.blob = blob;
    }

    /**
     * When a tile on the blobs grid is pressed, this event listener
     * will retrieve it and call the pick function of Blobs to proceed the game.
     * @param e the current mouse event
     */
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            JLabel[][] labels = grid.getJLabelArray();

            for(int i = 0; i < labels.length; i++) {
                for(int j = 0; j < labels[i].length; j++) {

                    if(labels[i][j] == e.getSource()) {
                        if(grid.getColorArray()[i][j].getNumber() == blob.getTurn()) {
                            return;
                        }
                        blob.pick(i, j);
                        blob.switchTurn();
                        break;
                    }
                }
            }
        }
    }

}
