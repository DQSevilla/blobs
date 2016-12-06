/* MyColor.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla, Nick Gattuso
 */

import java.awt.Color;


public class MyColor {
    private Color color;
    private int number;
//    private static MyColor[] options = new MyColor[2];
    private static Color[] options = new Color[2];

    public MyColor(Color color, int num) {
        this.color = color;
        this.number = num;
        if(options[0] == null && options[1] == null) {
            options[0] = Color.BLACK;
            options[1] = Color.WHITE;
        }
        MyColor.options[num] = color; // rip? maybe check if same color before change
    }

    public Color getColor() {
        return this.color;
    }

    public int getNumber() {
        return this.number;
    }

    public static Color getOption(int num) {
        return MyColor.options[num];
    }

    public static int getNumOption(Color c) {
        if(options[0].equals(c)) {
            return 0;
        }
        else {
            return 1;
        }
    }
}