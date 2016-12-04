package blobs.src;
/* MyColor.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla
 */

import java.awt.Color;

/**
 * An enumeration for a color in the blobs grid, in which a
 * java.awt.Color, black or white, is paired with an integer,
 * 0 or 1 respectively.
 *
 * @author David Sevilla, Nicholas Gattuso
 * @version 1.0
 * @since 12/01/2016
 */
public enum MyColor {
    BLACK(Color.BLACK, 0), WHITE(Color.WHITE, 1);
    private Color color;
    private int number;

    MyColor(Color color, int number) {
        this.color = color;
        this.number = number;
    }

    public Color getColor() {
        return this.color;
    }

    public int getNumber() {
        return this.number;
    }
}
