package blobs.src;
//I need the above line in order to compile in eclipse
/* BlobsMouseListener.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla..Nick Gattuso
 */

import java.awt.event.MouseAdapter;

/**
 * DESCRIPTION
 *
 * @author David Sevilla
 * @version 1.0
 * @since 12/01/2016
 */
public class BlobsMouseListener extends MouseAdapter {
    private ColorGrid grid;

    public BlobsMouseListener(ColorGrid grid) {
        this.grid = grid;
    }
}
