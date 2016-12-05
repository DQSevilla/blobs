package blobs.src;
/* MyColor.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla, Nick Gattuso
 */

import java.awt.Color;

/**
 * An enumeration for a color in the blobs grid, in which a
 * java.awt.Color, black or white, is paired with an integer,
 * 0 or 1 respectively.
 *
 * @author David Sevilla, Nick Gattuso
 * @version 1.0
 * @since 12/01/2016
 */
public enum MyColor { // change completely, to a class, and generalize it
    BLACK(Color.BLACK, 0), WHITE(Color.WHITE, 1);
    private Color color;
    private int number;
    /*
    TODO
    When turning this into a class, you can use:
    private static Color[] colors = new Color[2];
    and add the color that has initialized this MyColor
    object to the last available array index, and write a method
    to return this array so that ColorGrid and Blobs can use it to determine what
    colors to add and print out.
    Ex. label.setBackground(MyColor.colorArr()[1]) to set it to the second player's color
     */

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
