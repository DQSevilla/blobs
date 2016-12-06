/* Blobs.java
 * 
 * I pledge my honor that I have abided by the Stevens Honor System.
 * David Sevilla, Nick Gattuso
 */

import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.Color;

/**
 * Main class for implementing the game Blobs.
 *
 * @author Nick Gattuso, David Sevilla
 * @version 1.0
 * @since 12/01/2016
 */
public class Blobs implements ActionListener{
    private ColorGrid grid;
    private JFrame board;
    private final int SIZE;
    private int turn = 1;

    /**
     * Initializes a new Blobs game with a board of the given size
     * @param size the given dimension of the Blobs board
     */
    public Blobs(int size) {
        this.SIZE = size;
        this.grid = new ColorGrid(SIZE, SIZE);
        this.board = new JFrame("Blobs");
        initBoard();
        switchTurn(); // So the proper turn (0) displays message
    }

    /**
     * Initializes the GUI by adding the ColorGrid and setting the
     * size and position of the JFrame.
     */
    private void initBoard() {
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.getContentPane().add(this.grid);
        board.pack();
        
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        
        menuBar = new JMenuBar();

        //Build the first menu.

        menu = new JMenu("Options");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
          "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Change Player Colors",
                           KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
          KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
          "This doesn't really do anything");
        menu.add(menuItem);
        board.setJMenuBar(menuBar);
        menuItem.setActionCommand("change");
        menuItem.addActionListener(this);
        
        if(SIZE < 40) {
            board.setSize(SIZE*20, SIZE*20);
        }
        else if(SIZE < 50) {
            board.setSize(SIZE*15, SIZE*15);
        }
        else {
            board.setSize(SIZE*10, SIZE*10);
        }
        board.setLocationRelativeTo(null); // centers the board
        board.setVisible(true);
        grid.addBlob(this);
    }

    /**
     * Implements a recursive algorithm to fill in all adjacent squares
     * in the grid of the same color that was clicked, as long as the
     * correct player clicked it.
     * @param row the row position of the click
     * @param col the column position of the click
     * @return true if the current row and column can be changed.
     */
    public boolean pick(int row, int col) {
        MyColor[][] colors = this.grid.getColorArray();
        if(inBounds(row, col)) {
            if(colors[row][col].getNumber() != this.turn) { // if different color
                grid.changeBoard(row, col);
                pick(row-1, col);
                pick(row+1, col);
                pick(row, col-1);
                pick(row, col+1);

                checkWinner();
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method which returns whether a given position is within
     * the bounds of the Blobs board
     * @param row the given row
     * @param col the given column
     * @return true if the row and column are contained in the board
     */
    private boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < SIZE && col < SIZE;
    }

    /**
     * Switches the current turn to the next player.
     */
    public void switchTurn() {
        this.turn = (turn == 0) ? 1 : 0;
        if(turn == 0) {
            JOptionPane.showMessageDialog(null, "It is Player 1's turn (BLACK)"); // TODO Need to change string. Possibly use static color array?
        }
        else {
            JOptionPane.showMessageDialog(null, "It is Player 2's turn (WHITE)");
        }
    }

    public int getTurn() {
        return turn;
    }

    /**
     * Checks if any player has won the game yet and, if so, prints
     * a congratulatory message, and terminates the program.
     */
    public void checkWinner() {
        MyColor[][] colors = this.grid.getColorArray();
        for(MyColor[] arr : colors) {
            for(MyColor color : arr) {
                if(color.getNumber() != turn) return;
            }
        }

        JOptionPane.showMessageDialog(null, "Player " + turn + " has won the game!");
        System.exit(0);
    }
    
    @Override
	public void actionPerformed(ActionEvent e) {
		if("change".equals(e.getActionCommand())){
			
			JPanel p = new JPanel(new BorderLayout());
			
			String[] playerStrings = { "Player 0" , "Player 1" };
			JComboBox playerList = new JComboBox(playerStrings);
			JLabel label = new JLabel("Please choose a player and a color");
			
			String[] colorPos = { "Black" , "White" , "Red" , "Blue" , "Green" };
			JComboBox colorSel = new JComboBox(colorPos);
			
			p.add(colorSel, BorderLayout.SOUTH);
			p.add(label, BorderLayout.WEST);
			p.add(playerList, BorderLayout.EAST);
			
			JOptionPane.showMessageDialog(null, p, "Multi-Select Example", JOptionPane.PLAIN_MESSAGE);

			int player = playerList.getSelectedIndex();
			Object col = colorSel.getSelectedItem();
			if(col.equals("Red")) {
			    this.grid.changeColor(Color.RED, player); // TODO ERROR CHECKING
            }
            else if(col.equals("Blue")) {
			    this.grid.changeColor(Color.BLUE, player);
            }
            else if(col.equals("Green")) {
			    this.grid.changeColor(Color.GREEN, player);
            }
            else if(col.equals("Black")) {
			    this.grid.changeColor(Color.BLACK, player);
            }
            else {
			    this.grid.changeColor(Color.WHITE, player);
            }
	    }
	}

    public static void main(String[] args) {
        // Fixes a weird Swing bug for large input sizes
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

        int size = 0;
        String input;
        String message = "Welcome to Blobs!\nEnter a board size (10-100):";

        while(true) {
            try {
                while(size < 10 || size > 100) {
                    input = JOptionPane.showInputDialog(null, message, "Input", 1);
                    size = Integer.parseInt(input);
                }
                break;
            }
            catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ERROR: Not a valid size. Please try again");
            }
        }
        Blobs blob = new Blobs(size);
    }

	
}
