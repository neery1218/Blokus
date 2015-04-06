import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowFocusListener;

public class MainMenu extends JFrame implements ActionListener, WindowListener, WindowFocusListener
{
    private BufferedImage titleImage, iconImage;
    private JButton bPlay = new JButton ("Play");
    private JButton bInst = new JButton ("Instructions");
    private JButton bQuit = new JButton ("Quit");
    private JButton bSett = new JButton ("Settings");
    private Settings settingsWindow = new Settings ();
    private GameScreen gameScreenWindow = new GameScreen ();
    private Instructions instructionsWindow = new Instructions ();
    private Color[] colorScheme = new Color [5];
    private String[] playerNames = new String [4];
    private JPanel screen = new JPanel (new BorderLayout ());

    // Default constructor
    public MainMenu ()
    {   
	// import images
	try
	{
	    titleImage = ImageIO.read (this.getClass ().getResource ("Blokus.png"));
	    iconImage = ImageIO.read (this.getClass ().getResource ("frameIcon.png"));
	}
	catch (IOException e)
	{
	    // do nothing
	}
	// set up screen
	this.setTitle ("Blokus v.1.0: Main Menu");
	this.setIconImage (iconImage);
	setSize (400, 400);
	setResizable (false);
	
	// set up colors
	colorScheme = settingsWindow.getColors ();
	bPlay.setBackground (colorScheme [1]);
	bInst.setBackground (colorScheme [2]);
	bQuit.setBackground (colorScheme [3]);
	bSett.setBackground (colorScheme [4]);

	// get player names
	playerNames = settingsWindow.getNames ();

	// set settings from settings window
	gameScreenWindow.setSettings (colorScheme, playerNames);

	// add action listeners
	bPlay.addActionListener (this);
	bInst.addActionListener (this);
	bQuit.addActionListener (this);
	bSett.addActionListener (this);

	// Set up other screens
	gameScreenWindow.setVisible (false);
	gameScreenWindow.setResizable (false);

	instructionsWindow.setVisible (false);
	instructionsWindow.setResizable (false);

	settingsWindow.setVisible (false);
	settingsWindow.setResizable (false);

	// set up title image
	JLabel title = new JLabel (new ImageIcon (titleImage));
	title.setPreferredSize (new Dimension (320, 130));
	
	// set up options
	JPanel options = new JPanel (new GridLayout (0, 1));
	options.setSize (new Dimension (300, 300));
	options.add (bPlay);
	options.add (bInst);
	options.add (bSett);
	options.add (bQuit);

	screen.add (options, BorderLayout.CENTER);
	screen.add (title, BorderLayout.NORTH);
	screen.setBorder (BorderFactory.createEmptyBorder (40, 40, 40, 40));
	screen.setBackground (colorScheme [0]);

	// add everything to main jframe
	this.getContentPane ().add (screen);
	
	// set up custom closing operation, using window listener
	setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
	addWindowListener (this);
	addWindowFocusListener (this);
    }

    // Windowevent that happens when just exited settings or instructions
    public void windowGainedFocus (WindowEvent e)
    {
	colorScheme = settingsWindow.getColors (); // get colors

	// set colors
	screen.setBackground (colorScheme [0]);
	bPlay.setBackground (colorScheme [1]);
	bInst.setBackground (colorScheme [2]);
	bQuit.setBackground (colorScheme [3]);
	bSett.setBackground (colorScheme [4]);

	// update
	this.getRootPane ().revalidate ();
	this.getContentPane ().repaint ();

	playerNames = settingsWindow.getNames ();

	gameScreenWindow.setSettings (colorScheme, playerNames);
    }

    // method must be implemented because of windoweventlistener, does nothing
    public void windowLostFocus (WindowEvent e)
    {

    }

    // method must be implemented because of windoweventlistener, does nothing
    public void windowClosed (WindowEvent arg0)
    {
    }

    // method must be implemented because of windoweventlistener, does nothing
    public void windowActivated (WindowEvent arg0)
    {
    }

    // exit everything if window is closed
    public void windowClosing (WindowEvent arg0)
    {
	System.exit (0);
    }

    // method must be implemented because of windoweventlistener, does nothing
    public void windowDeactivated (WindowEvent arg0)
    {
    }

    // method must be implemented because of windoweventlistener, does nothing
    public void windowDeiconified (WindowEvent arg0)
    {
    }

    // method must be implemented because of windoweventlistener, does nothing
    public void windowIconified (WindowEvent arg0)
    {
    }

    // method must be implemented because of windoweventlistener, does nothing
    public void windowOpened (WindowEvent arg0)
    {
    }

    // set up actions for various jcomponents
    public void actionPerformed (ActionEvent e)
    {
	JButton temp = (JButton) e.getSource (); // determine source of actionevent

	if (temp.equals (bPlay)) // play
	    gameScreenWindow.setVisible (true);

	else if (temp.equals (bInst)) // instructions
	    instructionsWindow.setVisible (true);

	else if (temp.equals (bSett))// settings
	    settingsWindow.setVisible (true);

	else // quit
	    System.exit (0);
    }

    // Main
    public static void main (String s[])
    {
	MainMenu main = new MainMenu ();
	main.setVisible (true);
    }
}
