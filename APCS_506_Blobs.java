/* APCS 506: Blobs
 * @author David Sevilla
 *
 * I created a Blobs GUI because one
 * wasn't provided. And it was fun.
 *
 * Despite this program being a sort
 * of crazy huge mess, I have included
 * as many Javadoc comments as possible
 * to make things clear and organized.
 */

// Lab Complete
// Score 10/10

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Tester class for Blobs game */
public class APCS_506_Blobs {
	
	public static void main(String[] args) {
	
		// Fixes a weird swing bug for large input sizes
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		
		int size = 0;
		int tries = 0;
		String input;
		
		// Error checking for Blobs board size
		while (true) {
			try {
				
				tries++;
				while (size < 10 || size > 60) {
					
					input = JOptionPane.showInputDialog(null,"Welcome to Blobs!\nEnter a size: (10 - 60)","Input",1);
					size = Integer.parseInt(input);
				}
				break;
			} 
			catch(NumberFormatException e) {
				
				JOptionPane.showMessageDialog(null, "Error: Not a valid size. Please try again");
				
				if (tries > 5) {
					JOptionPane.showMessageDialog(null, "Try again later, and read the rules");
					throw e;
				}
			}
		}
		
		// Starting the Blobs game
		Blobs blob = new Blobs(size);
		JOptionPane.showMessageDialog(null, "It is now Player One's turn (BLACK).\nClick on a white square to start.");
	}
}

/*class Blobs represents the game
* itself, and has a ColorGrid and
* board attribute, which together
* create and display the Blobs board.
* Also includes methods for game functions */
class Blobs {
	
	private ColorGrid grid;
	private JFrame board;
	
	// 0 -> black; 1 -> white;
	private int whoseTurn = 0;
	
	/** Constructor
	*	
	*	Creates Blobs object with some
	*	initial size, using grid and board.
	*
	*	@param size : length & width of Blobs board
	*/
	Blobs(int size) {
		
		grid = new ColorGrid(size, size, 10);
		board = new JFrame("Blobs");
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.getContentPane().add(grid);
		board.pack();
		board.setLocation(0,0);
		if (size < 40)
			board.setSize(size*20, size*20);
		else if (size < 50)
			board.setSize(size*15, size*15);
		else
			board.setSize(size*10, size*10);
		board.setVisible(true);
		
		grid.addBlobs(this);
	}
	
	/** This method accepts a row and
	*	column, then follows this rule:
	*	-check if spot is other color
	*	TRUE: -change color
	*		  -spread up, down, right, left
	*		  -check to see if player has won
	*			TRUE: 
	*			  -Exit program
	*			FALSE:
	*		  	  -return true (in pick method)
	*	FALSE:-exit method
	*
	*	@param row : row index of spot
	*	@param col : col index of spot
	*	@return whether new spot is
	*	different color
	*/
	public boolean pick(int row, int col) {
		
		MyColor[][] colors = grid.getColorArr();
		if (row >= 0 && row < colors.length && col >= 0 && col < colors.length) {
			
			if (colors[row][col].getNum() != whoseTurn) { // if different color
				
				grid.changeBoard(row, col);
				pick(row-1, col);
				pick(row+1, col);
				pick(row, col-1);
				pick(row, col+1);
				
				if (checkWin(colors))
					System.exit(0);
					
				return true;
			}
		}
		return false;
	}
	
	/** Checks to see if the current player has won the game
	*
	*	@param colors : MyColor array from ColorGrid
	*	@return whether current player won
	*/
	public boolean checkWin(MyColor[][] colors) {
		
		for (int r = 0; r < colors.length; r++)
			for (int c = 0; c < colors[r].length; c++) {
				
				if (colors[r][c].getNum() != whoseTurn)
					return false;
			}
		
		if (whoseTurn == 0)
			JOptionPane.showMessageDialog(null, "Player 1 (BLACK) has won the game!");
		else
			JOptionPane.showMessageDialog(null, "Player 2 (WHITE) has won the game!");
		return true;
	}
	
	/**	Mutator: Changes turns between players
	*	
	*	Precondition: t is 0 or 1
	*	@param t : the player number
	*/
	public void setTurn(int t) {
		
		whoseTurn = t;
		
		if (whoseTurn == 0)
			JOptionPane.showMessageDialog(null, "It is now Player One's turn (BLACK)");
		else
			JOptionPane.showMessageDialog(null, "It is now Player Two's turn (WHITE)");
	}
	
	/**	Accessor for player turn
	*	
	*	@return whoseTurn : current player
	*	(1 or 0)
	*/
	public int getTurn() {
		
		return whoseTurn;
	}
}

/*enum MyColor to represent
* Blobs board background colors, and
* provides access to numerical value
* (1 or 0) of color */
enum MyColor {

	BLACK(Color.black, 0), WHITE(Color.white, 1);
	private Color color;
	private int number;
	
	/** Constructor
	*
	*	Creates MyColor Object with
	*	specified color and numerical
	*	value
	*
	*	@param col : Color of MyColor
	*	@param num : Number value of color
	*/
	MyColor(Color col, int num) {
		
		color = col;
		number = num;
	}
	
	/**	Accessor for color variable
	*	@return color : Color of MyColor
	*/
	public Color getColor() {
		
		return color;
	}
	
	/** Accessor for num variable
	*	@return num : MyColor number value
	*/
	public int getNum() {
		
		return number;
	}
}

/*class ColorGrid is a Blobs game board
* made from JLabels, which use the MyColor
* enum to differentiate colors */
class ColorGrid extends JPanel {
	
	private MyColor[][] colors;
	private JLabel[][] myLabels;
	MyMouseListener myListener;
	
	/** Constructor
	*	
	*	@param rows : the number of rows in Blobs board
	*	@param cols : the number of columns in Blobs board
	*	@param width : the width of each cell, used for coder's convinience
	*/
	public ColorGrid(int rows, int cols, int width) {
		
		colors = new MyColor[rows][cols];
		myLabels = new JLabel[rows][cols];
		myListener = new MyMouseListener(this);
		
		setLayout(new GridLayout(rows, cols));
		Dimension prefSize = new Dimension(width, width);
		
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++) {
				
				JLabel myLabel = new JLabel();
				myLabel.setOpaque(true);
				MyColor myColor;
				
				if ((int)((Math.random()*10) + 1) % 2 == 0)
					myColor = MyColor.BLACK;
				else
					myColor = MyColor.WHITE;
				
				colors[row][col] = myColor;
				myLabel.setBackground(myColor.getColor());
				myLabel.addMouseListener(myListener);
				myLabel.setPreferredSize(prefSize);
				
				add(myLabel);
				myLabels[row][col] = myLabel;
			}
	}
	
	/**	Accessor for colors variable
	*	@return colors : array of MyColor enums
	*/
	public MyColor[][] getColorArr() {
		
		return colors;
	}
	
	/**	Accessor for myLabels variable
	*	@return myLabels : array of JLabels
	*/
	public JLabel[][] getJLabelArr() {
		
		return myLabels;
	}
	
	/**	Helper method adds Blobs object
	*	to MyMouseListener class
	*
	*	@param b : Blobs object
	*/
	public void addBlobs(Blobs b) {
		
		myListener.addBlob(b);
	}
	
	/**	Method to change the color of a specified
	*	JLabel in the myLabels array, and also
	*	updates the colors array.
	*
	*	Precondition: row and col are board indicies
	*	@param row : row number of spot to change color
	*	@param col : column number of spot to change color
	*/
	public void changeBoard(int row, int col) {
		
		if (colors[row][col].getNum() == 0) {
			
			colors[row][col] = MyColor.WHITE;
			myLabels[row][col].setBackground(Color.white);
		} else {
			
			colors[row][col] = MyColor.BLACK;
			myLabels[row][col].setBackground(Color.black);
		}
	}
}

/* Class MyMouseListener provides support for
 * button click recognition, which will in turn
 * affect the blobs board, but outsources the
 * changing of the board to Blobs and ColorGrid*/
class MyMouseListener extends MouseAdapter {
	
	private ColorGrid colorGrid;
	private Blobs blob;
	
	/**	Constructor
	*	
	*	Initializes MouseListener for specific
	*	ColorGrid object.
	*	
	*	@param grid : ColorGrid board
	*/
	public MyMouseListener(ColorGrid grid) {
		
		colorGrid = grid;
	}
	
	/**	Adds Blobs object to MyMouseListener
	*	so that it can access the pick method
	*
	*	@param b : Blobs object
	*/
	public void addBlob(Blobs b) {
		
		blob = b;
	}
	
	/**	Calls the pick method of Blobs for
	*	the specific row and column pressed
	*	by the left mouse button. ALso in charge
	*	of calling the change to player's turn.
	*
	*	@param e : current MouseEvent
	*/
	public void mousePressed(MouseEvent e) {
	
		if (e.getButton() == MouseEvent.BUTTON1) {
			
			JLabel[][] labels = colorGrid.getJLabelArr();
			
			for (int i = 0; i < labels.length; i++)
				for (int j = 0; j < labels.length; j++)
					if (labels[i][j] == e.getSource()) {
						
						blob.pick(i, j);
						
						if (blob.getTurn() == 0)
							blob.setTurn(1);
						else
							blob.setTurn(0);
							
						break;
					}
		}
		
	}
}