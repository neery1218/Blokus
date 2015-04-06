import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Board extends JPanel implements KeyListener // creates a board object to play Blokus
{
    private BufferedImage board; // Image of board background
    private int selectedX, selectedY; // Pixel coordinates of selected tile on grid
    private int[] [] boardTracker; // Keeps track of tiles on board
    private int turnNumber, totalTurnNumber;
    private Tile selectedTile; // Tile currently selected
    private int[] [] tempBoard; // Used for validating tile

    public Board ()  // default constructor
    {
	// Assign values to initialized variables
	selectedX = 1; //  1 pixel offset
	selectedY = 1;
	boardTracker = new int [20] [20]; // Set board as 20 by 20
	tempBoard = new int [20] [20];
	turnNumber = 1;
	totalTurnNumber = 1;
	addKeyListener (this); // Adds key listener to Board
	setFocusable (true); // Set focus on board

	// Import board image
	try
	{
	    board = ImageIO.read (this.getClass ().getResource ("woodBoardBackground.jpg"));
	}
	catch (IOException e)
	{
	    // do nothing
	}
    }


    // Adds one to turn and total turnNumber, making the program switch players
    public void incrementTurn ()
    {
	turnNumber %= 4;
	turnNumber++;
	totalTurnNumber++;
    }


    // Sets background colour of the board JPanel
    public void setBackgroundColor (Color toSet)
    {
	this.setBackground (toSet);
    }


    // Accessor method to get turn number
    public int getTurnNumber ()
    {
	return turnNumber;
    }


    // Accessor method to get total turn number
    public int getTotalTurnNumber ()
    {
	return totalTurnNumber;
    }


    // Adds the tile to a temporary array - used to move the tile around before placement
    private int[] [] addSelectedTile (Tile tile, int x, int y)
    {
	int[] [] tempArray = new int [20] [20];


	for (int i = 0 ; i < 20 ; i++) // assigns all the values of boardTracker to tempArray
	    for (int j = 0 ; j < 20 ; j++)
		tempArray [j] [i] = boardTracker [j] [i];

	int temp = x, j;

	for (int i = 0 ; i < tile.length () ; i++, y++) // assigns the tile to the temporary array
	    for (j = 0, x = temp ; j < tile.width () ; j++, x++)
		if (tile.grid [j] [i]) // if the element in the tile boolean grid is true, the turnNumber is assigned to the array
		    tempArray [x] [y] = turnNumber;

	return tempArray;
    }


    // Validates tile placement, used to check if placement is valid according to blokus rules
    public boolean validateTile ()
    {
	int x = selectedX / 25, y = selectedY / 25;
	int temp = x, j, tempY = y;

	boolean overlap = false;
	boolean cornersTouching = false;
	boolean isValid = false;
	boolean edgesTouching = false;
	boolean firstPiece = false;
	String errorMessage = "";
	boolean needNewLine = false;
	JFrame errorFrame = new JFrame ();

	for (int i = 0 ; i < selectedTile.length () && !overlap ; i++, y++) // checks for overlap
	{
	    for (j = 0 ; j < selectedTile.width () && !overlap ; j++, x++)
		if (selectedTile.grid [j] [i] && boardTracker [x] [y] != 0) // if the selectedTile overlaps another selectedTile, the placement is invalid
		    overlap = true;

	    x = temp;
	}

	x = temp;
	y = tempY;

	if (totalTurnNumber < 5) // special rules for the first four turns; must go in each corner
	{
	    // checks if the tile goes in a corner
	    if (selectedTile.grid [0] [0] && x == 0 && y == 0)
		firstPiece = true;
	    else if (selectedTile.grid [0] [selectedTile.length () - 1] && x == 0 && y == (20 - selectedTile.length ()))
		firstPiece = true;
	    else if (selectedTile.grid [selectedTile.width () - 1] [0] && x == (20 - selectedTile.width ()) && y == 0)
		firstPiece = true;

	    else if (selectedTile.grid [selectedTile.width () - 1] [selectedTile.length () - 1] && x == (20 - selectedTile.width ()) && y == (20 - selectedTile.length ()))
		firstPiece = true;
	}
	else
	{

	    for (int i = 0 ; i < selectedTile.length () ; i++, y++) // checks if the piece is touching corners with another same coloured piece
	    {
		for (j = 0 ; j < selectedTile.width () ; j++, x++)
		{

		    // must have touching corners with same player's selectedTile piece
		    // check one up, one left, ; one up, one right; one down, one left; one down, one right;
		    if (selectedTile.grid [j] [i])
		    {
			// checks to see if it's touching a corner
			if (x > 0 && y > 0 && boardTracker [x - 1] [y - 1] == turnNumber)
			    cornersTouching = true;

			if (x > 0 && y < 19 && boardTracker [x - 1] [y + 1] == turnNumber)
			    cornersTouching = true;

			if (x < 19 && y > 0 && boardTracker [x + 1] [y - 1] == turnNumber)
			    cornersTouching = true;

			if (x < 19 && y < 19 && boardTracker [x + 1] [y + 1] == turnNumber)
			    cornersTouching = true;

			// checks to see if it's touching an edge of another selectedTile
			if (y > 0 && boardTracker [x] [y - 1] == turnNumber) //up
			    edgesTouching = true;

			if (y < 19 && boardTracker [x] [y + 1] == turnNumber)
			    edgesTouching = true;

			if (x < 19 && boardTracker [x + 1] [y] == turnNumber)
			    edgesTouching = true;

			if (x > 0 && boardTracker [x - 1] [y] == turnNumber)
			    edgesTouching = true;
		    }
		}
		x = temp;
	    }
	}

	isValid = !overlap && (cornersTouching && !edgesTouching || firstPiece); // placement is valid if all these conditions are met

	// Error messages that pop up if invalid tile placement
	if (!isValid)
	{
	    if (totalTurnNumber < 5)
	    {
		if (!firstPiece)
		{
		    if (needNewLine)
			errorMessage += "\n";
		    errorMessage += "The first piece of each colour must fill a corner of the board.";
		    needNewLine = true;

		}
		if (overlap)
		{
		    if (needNewLine)
			errorMessage += "\n";
		    errorMessage += "Tiles cannot overlap.";
		    needNewLine = true;
		}
		if (edgesTouching)
		{
		    if (needNewLine)
			errorMessage += "\n";
		    errorMessage += "The edges of your tiles cannot touch.";
		    needNewLine = true;
		}
	    }
	    else
	    {
		if (overlap)
		{
		    if (needNewLine)
			errorMessage += "\n";
		    errorMessage += "Tiles cannot overlap.";
		    needNewLine = true;
		}
		if (!cornersTouching)
		{
		    if (needNewLine)
			errorMessage += "\n";
		    errorMessage += "At least one of the corners on the tile you are attempting to\n place must touch the corner of another of your tiles already\n on the board.";
		    needNewLine = true;
		}
		if (edgesTouching)
		{
		    if (needNewLine)
			errorMessage += "\n";
		    errorMessage += "The edges of your tiles cannot touch.";
		    needNewLine = true;
		}
	    }

	    // Dialog box to show invalid placement explanation
	    JOptionPane.showMessageDialog (errorFrame, errorMessage, "Invalid placement!", JOptionPane.ERROR_MESSAGE);
	}
	return isValid;
    }


    // Moves the tile around the board using arrow and WASD keys
    public void keyPressed (KeyEvent e)
    {
	int arrow = e.getKeyCode ();

	// Left/A keys
	if ((arrow == KeyEvent.VK_LEFT) || (arrow == KeyEvent.VK_A))
	{
	    if (selectedX > 25)

		selectedX -= 25;
	}

	// Right/D keys
	else if ((arrow == KeyEvent.VK_RIGHT) || (arrow == KeyEvent.VK_D))
	{
	    if (selectedX < 501 - 25 * selectedTile.width ())
		selectedX += 25;
	}

	// Up/W keys
	else if ((arrow == KeyEvent.VK_UP) || (arrow == KeyEvent.VK_W))
	{
	    if (selectedY > 25)
		selectedY -= 25;
	}

	// Down/S keys
	else if ((arrow == KeyEvent.VK_DOWN) || (arrow == KeyEvent.VK_S))
	{
	    if (selectedY < 501 - 25 * selectedTile.length ())
		selectedY += 25;
	}

	// Update board to reflect change in selected x and y
	revalidate ();
	repaint ();
    }


    // Method that must be implemented because of KeyListener, does nothing
    public void keyReleased (KeyEvent e)
    {
	// Nothing happens
    }


    // Method that must be implemented because of KeyListener, does nothing
    public void keyTyped (KeyEvent e)
    {
	// Nothing happens
    }


    // Sets the passed tile to selectedTile, which is used for other methods
    public void setSelectedTile (Tile tile)
    {
	selectedTile = tile;
	this.requestFocus ();
	revalidate ();
	repaint ();
    }


    // Draws the Blokus tiles and grid
    public void paintComponent (Graphics g)
    {
	super.paintComponent (g); // override default paintComponent method

	g.drawImage (board, 0, 0, null); // draw board

	// Draw board
	g.setColor (Color.black); // Board has black lines
	for (int i = 0 ; i <= 500 ; i += 25) // Draws vertical lines of board
	{
	    g.drawLine (0, i, 500, i);
	}
	for (int i = 0 ; i <= 500 ; i += 25) // Draws the horizontal lines of board
	{
	    g.drawLine (i, 0, i, 500);
	}

	// Tile
	Color[] colorArray = {null, Color.blue, Color.yellow, Color.red, Color.green}; // Colors of tiles
	if (selectedTile != null) //  adds tile to gui
	{
	    //Resets the x and y coordinates if the user presses tries to move tile out of bounds
	    if (selectedY > 501 - 25 * selectedTile.length ())
		selectedY = 501 - 25 * selectedTile.length ();

	    if (selectedX > 501 - 25 * selectedTile.width ())
		selectedX = 501 - 25 * selectedTile.width ();

	    tempBoard = addSelectedTile (selectedTile, (selectedX / 25), (selectedY / 25)); // adds the tile to the tempBoard

	    // Draws tiles onto board
	    for (int i = 0 ; i < 20 ; i++)
	    {
		for (int j = 0 ; j < 20 ; j++)
		{
		    if (tempBoard [j] [i] != 0)
		    {
			g.setColor (colorArray [tempBoard [j] [i]]); // Set color to corresponding tile color
			g.fill3DRect ((j * 25), (i * 25), 25, 25, true); // Draws 3D rectangles for the tiles because, you know, 3D looks better

		    }

		}

	    }
	}
    }

    // Determines score of each player
    public int[] determineScore ()
    {
	int[] score = { - 89, -89, -89, -89};

	for (int i = 0 ; i < 20 ; i++) // looks at each element in the blokus array, and adds one each time to each player's score depending on the turnNumber
	    for (int j = 0 ; j < 20 ; j++)
		if (boardTracker [j] [i] != 0)
		    score [boardTracker [j] [i] - 1]++;

	for (int i = 0 ; i < 4 ; i++) // if all the tiles are played, a bonus of 20 points are given
	    if (score [i] == 0)
		score [i] += 20;

	return score;

    }


    // Places tile on board permanently (onto board tracker, not tempBoard)
    public void placeTile ()
    {
	for (int i = 0 ; i < 20 ; i++) // assigns all the values of tempBoard to boardTracker
	    for (int j = 0 ; j < 20 ; j++)
		boardTracker [j] [i] = tempBoard [j] [i];

	// Returns start position of tile to top left corner of board
	selectedX = 1;
	selectedY = 1;
	incrementTurn ();
    }
}


