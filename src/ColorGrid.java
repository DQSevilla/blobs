/* ColorGrid.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla
 */

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The ColorGrid object is the main grid in Blobs
 *
 * @author David Sevilla
 * @version 1.0
 * @since 12/01/2016
 */
public class ColorGrid extends JPanel {
    private MyColor[][] colors;
    private JLabel[][] labels;
    private BlobsMouseListener listener;
}
