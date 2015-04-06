import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameScreen extends JFrame implements ActionListener, WindowListener
{
    private BufferedImage selectedTile, rotateLeftImg, rotateRightImg, iconImage;
    private BufferedImage[] [] images = new BufferedImage [21] [4];

    private String fileName;
    private int i, consecutivePass;
    public boolean back, restart;
    private int scrollLength;

    public Player[] playerList = new Player [4];
    private Board displayBoard = new Board ();
    private Color[] colorScheme = new Color [5];
    private String[] playerNames = new String [4];

    private JLabel chooseTileTitle = new JLabel ("Choose a tile: ");
    private JPanel rightButtons = new JPanel (new BorderLayout ());
    private JButton rotateLeft = new JButton ("r.left");
    private JButton placeTile = new JButton ("PLACE");
    private JButton rotateRight = new JButton ("r.right");
    private JButton pass = new JButton ("PASS");
    private JButton[] button = new JButton [22];
    
    private JPanel right = new JPanel (new BorderLayout ());
    private JPanel tileOptions = new JPanel ();
    private JScrollPane tileScroll = new JScrollPane ();

    private JMenuBar menuBar = new JMenuBar ();
    private JMenu game = new JMenu ("Game");
    private JMenu help = new JMenu ("Help");
    private JMenuItem newGame = new JMenuItem ("New Game");
    private JMenuItem backToMenu = new JMenuItem ("Back to Menu");
    private JMenuItem changeSettings = new JMenuItem ("Change Settings");
    private JMenuItem stats = new JMenuItem ("View Game Statistics");
    private JMenuItem instructions = new JMenuItem ("View Instructions");
    private JMenuItem aboutBlokus = new JMenuItem ("About Blokus");

    // Default constructor
    public GameScreen ()
    {
	back = false;
	restart = false;
	
	// Import images
	String[] letters = {"A.png", "B.png", "C.png", "D.png"};
	try
	{
	    for (int i = 0 ; i < 21 ; i++)
	    {
		// Images are named 00A, 01A, 02A, ... 00B, 01B, etc.
		for (int j = 0 ; j < 4 ; j++)
		{
		    if (i < 10)
			fileName = "0" + i + letters [j];

		    else
			fileName = i + letters [j];

		    images [i] [j] = ImageIO.read (this.getClass ().getResource (fileName));
		}

		button [i] = new JButton (new ImageIcon (images [i] [displayBoard.getTurnNumber () - 1])); // adds image to tile buttons
		button [i].addActionListener (this); // add action listener to each tile in the scroll pane
		tileOptions.add (button [i]);
	    }
	    
	    // Set image to rotation buttons
	    rotateLeftImg = ImageIO.read (this.getClass ().getResource ("rotateLeft.png")); 
	    rotateLeft = new JButton (new ImageIcon (rotateLeftImg));

	    rotateRightImg = ImageIO.read (this.getClass ().getResource ("rotateRight.png"));
	    rotateRight = new JButton (new ImageIcon (rotateRightImg));
	    
	    // Import JFrame icon image
	    iconImage = ImageIO.read (this.getClass ().getResource ("frameIcon.png"));
	}
	catch (IOException e)
	{
	    // do nothing
	}

	// Initialize players
	for (int i = 0 ; i < 4 ; i++)
	    playerList [i] = new Player (playerNames [i]);

	// Set up game screen
	setSize (670, 575);
	this.setIconImage (iconImage); // set icon to jframe
	this.getContentPane ().setLayout (new BorderLayout ());
	this.setTitle ("Blokus v.1.0: Play!");

	menuBar.setOpaque (true);

	// Set up menu bar
	game.setOpaque (true);
	newGame.setOpaque (true);
	newGame.addActionListener (this);
	backToMenu.setOpaque (true);
	backToMenu.addActionListener (this);
	changeSettings.setOpaque (true);
	stats.setOpaque (true);

	game.add (backToMenu);
	game.add (newGame);
	game.add (changeSettings);
	game.add (stats);

	help.setOpaque (true);
	instructions.setOpaque (true);
	instructions.addActionListener (this);
	aboutBlokus.setOpaque (true);

	help.add (instructions);
	help.add (aboutBlokus);

	menuBar.add (game);
	menuBar.add (help);
	this.setJMenuBar (menuBar);

	// Set up tile selection pane

	tileOptions.setLayout (new FlowLayout ());
	scrollLength = 1525;
	tileOptions.setPreferredSize (new Dimension (110, scrollLength)); // 1525
	// make tile pane scrollable with a scrollpane
	tileScroll = new JScrollPane (tileOptions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 

	// Set up right side of screen (tileScroll + options)
	rotateLeft.addActionListener (this);
	rotateRight.addActionListener (this);
	placeTile.addActionListener (this);
	pass.addActionListener (this);

	rightButtons.add (pass, "North");
	rightButtons.add (rotateLeft, "West");
	rightButtons.add (rotateRight, "East");
	rightButtons.add (placeTile, "South");

	right.add (chooseTileTitle, BorderLayout.NORTH);
	right.add (tileScroll, BorderLayout.CENTER);
	right.add (rightButtons, BorderLayout.SOUTH);

	setBackgroundColors (); // Assign background colours to everything in the jframe

	// Add all components to main JFrame
	this.getContentPane ().add (displayBoard, "Center");
	this.getContentPane ().add (right, "East");
	
	// Using window listener to set custom window close operation
	setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE); 
	addWindowListener (this); // thus, adding window listener
    }

    // Changes color scheme and player names as returned from settings
    public void setSettings (Color[] colors, String[] names)
    {
	colorScheme = colors;
	setBackgroundColors ();
	playerNames = names;
    }

    // Assigns colours to everything
    public void setBackgroundColors ()
    {
	this.setBackground (colorScheme [0]);
	backToMenu.setBackground (colorScheme [3]);
	menuBar.setBackground (colorScheme [1]);
	game.setBackground (colorScheme [2]);
	newGame.setBackground (colorScheme [3]);
	changeSettings.setBackground (colorScheme [3]);
	stats.setBackground (colorScheme [3]);
	help.setBackground (colorScheme [2]);
	instructions.setBackground (colorScheme [3]);
	aboutBlokus.setBackground (colorScheme [3]);
	tileOptions.setBackground (colorScheme [1]);
	tileScroll.setBackground (colorScheme [1]);
	right.setBackground (colorScheme [0]);
	rightButtons.setBackground (colorScheme [1]);
	rotateLeft.setBackground (colorScheme [2]);
	rotateRight.setBackground (colorScheme [2]);
	placeTile.setBackground (colorScheme [2]);
	pass.setBackground (colorScheme [2]);
	displayBoard.setBackgroundColor (colorScheme [1]);
    }

    // Method that must be implemented because of Window Listener, does nothing
    public void windowClosed (WindowEvent e)
    {
    }

    // Method that must be implemented because of Window Listener, does nothing
    public void windowActivated (WindowEvent e)
    {
    }

    // When the window is "closed", it still exists, but is not visible
    public void windowClosing (WindowEvent e)
    {
	this.setVisible (false);
    }

    // Method that must be implemented because of Window Listener, does nothing
    public void windowDeactivated (WindowEvent e)
    {
    }

    // Method that must be implemented because of Window Listener, does nothing
    public void windowDeiconified (WindowEvent e)
    {
    }

    // Method that must be implemented because of Window Listener, does nothing
    public void windowIconified (WindowEvent e)
    {
    }

    // Method that must be implemented because of Window Listener, does nothing
    public void windowOpened (WindowEvent e)
    {
    }

    // Changes the button icons and removes buttons that have already been placed
    public void removeButton ()  
    {
	scrollLength = 0;

	playerList [displayBoard.getTurnNumber () - 1].choose (i).setPlaced (); // sets the current tile variable's isPlaced variable to true
	for (int i = 0 ; i < 21 ; i++)
	{
	    button [i].setIcon (new ImageIcon (images [i] [displayBoard.getTurnNumber () % 4])); // switches the image icons on the buttons
	    if (playerList [displayBoard.getTurnNumber () % 4].choose (i).isPlaced) // if the tile has already been placed, the button will not be displayed and the scroll length is shortened
	    {
		button [i].setVisible (false);
		scrollLength += images [i] [displayBoard.getTurnNumber () % 4].getHeight ();
	    }
	    else
		button [i].setVisible (true);
	}
	// Update
	tileOptions.revalidate ();
	tileScroll.revalidate ();
	tileScroll.repaint ();
    }
    
    // Sets actions to perform when various JComponents are used
    public void actionPerformed (ActionEvent e)
    { 

	JComponent temp = (JComponent) e.getSource (); // used to determine what called actionPerformed

	int[] scoreArray = new int [4];

	if (temp.equals (placeTile)) // if place tile was pressed
	{
	    if (displayBoard.validateTile ()) // if placement's valid, the buttons are changed and consecutive pass is reset
	    {
		removeButton ();
		displayBoard.placeTile ();
	    }
	    else if (!playerList [displayBoard.getTurnNumber () - 1].choose (i).isPlaced)
		displayBoard.setSelectedTile (playerList [displayBoard.getTurnNumber () - 1].choose (i)); // sets the selected tile to the corresponding button and paints it on the board
	    else
		;

	}
	else if (temp.equals (pass)) // if pass is pressed:
	{
	    if (displayBoard.getTotalTurnNumber () > 4) 
	    {
		removeButton (); // buttons are changed
		displayBoard.incrementTurn (); // turn is incremented
		consecutivePass++;
	    }
	    else // Impossible to pass on first turn..
	    {
		JFrame errorFrame = new JFrame ();
		// Inform user of their invalid placement
		JOptionPane.showMessageDialog (errorFrame, "No way are you that bad, the game just started.", "Invalid pass", JOptionPane.ERROR_MESSAGE);
	    }

	    if (consecutivePass > 3) // game ends if all users pass
	    {
		consecutivePass = 0;
		scoreArray = displayBoard.determineScore ();
		JFrame gameEndFrame = new JFrame ();
		
		// Set up options to appear on game end dialog
		Object[] options = {"Back to main menu", "Restart"};
		
		// Set up game end dialog
		String gameEndDialog = "<html>" + playerNames [0] + " (blue) :" + scoreArray [0] + "<br>" + playerNames [1] + " (yellow) :" + scoreArray [1] + "<br>" + playerNames [2] + " (red) :" + scoreArray [2] + "<br>" + playerNames [3] + " (green) : " + scoreArray [3] + "</html>";
		
		// Finds winner
		int tempScore = scoreArray [0], tempWinner = 0;
		for (int i = 0 ; i < 4 ; i++)
		{
		    if (scoreArray [i] > tempScore)
		    {
			tempScore = scoreArray [i];
			tempWinner = i;
		    }
		}
		
		// Set up title of dialog
		String whoWins = playerNames [tempWinner] + " wins!";
		
		// Create the game end dialog
		int n = JOptionPane.showOptionDialog (gameEndFrame, gameEndDialog, whoWins, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options [1]);

		if (n == 0) // if back to main menu is selected
		    this.setVisible (false);
		else
		{
		}
	    }

	}

	else if (temp.equals (instructions)) // view instructions if instructions is pressed
	{
	    Instructions instWindow = new Instructions ();
	    instWindow.setVisible (true);
	}

	else if (temp.equals (backToMenu))
	{
	    back = true;
	}

	else
	{
	    if (temp.equals (rotateLeft)) // rotates tile left
		playerList [displayBoard.getTurnNumber () - 1].choose (i).rotate ();

	    else if (temp.equals (rotateRight)) // rotates tile right
	    {
		for (int j = 0 ; j < 3 ; j++)
		    playerList [displayBoard.getTurnNumber () - 1].choose (i).rotate ();

	    }

	    else
	    {
		for (i = 0 ; i < 21 && !button [i].equals (temp) ; i++) // finds the button that was pressed for the tile
		{
		}

	    }

	    displayBoard.setSelectedTile (playerList [displayBoard.getTurnNumber () - 1].choose (i)); // sets the selected tile to the corresponding button and paints it on the board
	}

    }
}


