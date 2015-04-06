import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Instructions extends JDialog implements ActionListener, WindowListener
{
    private JButton backButton, nextButton;
    private JPanel backNext;
    public int instStep = 0;
    private JPanel[] inst = new JPanel [6];
    private JLabel[] instTxt = new JLabel [6];
    private BufferedImage[] instImg = new BufferedImage [5];
    private JLabel[] instImgLabel = new JLabel [5];
    
    // Default constructor
    public Instructions ()
    {
	// Import images
	try
	{
	    for (int i = 0 ; i < 5 ; i++)
	    {
		instImg [i] = ImageIO.read (this.getClass ().getResource ("instImg" + i + ".png")); // instructions images
		instImgLabel [i] = new JLabel (new ImageIcon (instImg [i])); // Set instructions images to labels
	    }
	}
	catch (IOException e)
	{
	    // do nothing
	}
	
	// Set up JDialog
	setSize (600, 600);
	setModal (true); // Always on top
	this.setTitle ("Blokus v.1.0: Instructions");
	
	// Set up text on the instructions slides
	instTxt [0] = new JLabel ("<html><p align=center>Blokus is played on a 20-by-20 board with 84 game pieces (21 shapes in blue, yellow, red, and green). The 21 shapes are based on polynominoes made of 1-5 squares.<br><br>Here are the game pieces.</p></html>");
	instTxt [1] = new JLabel ("<html><p align=center>The controls are pretty self-explanatory. Keyboard shortcuts to move the tile around the board are the WASD and arrow keys.</p></html>");
	instTxt [2] = new JLabel ("<html><p align=center>The order of play is blue, yellow, red, and green.</p></html>");
	instTxt [3] = new JLabel ("<html><p align=center>The first four pieces played (one of each colour) must be placed in each of the board's four corners. The tile must cover the corner square of the board.</p></html>");
	instTxt [4] = new JLabel ("<html><p align=center>After the first four turns, each piece played must touch an already placed piece by one or more corners (flat edges cannot touch).</p></html>");
	instTxt [5] = new JLabel ("<html><p align=center>A player passes when he or she cannot play. The game ends when all four players pass. A player's score is based on his or her unplayed pieces. One point is lost for each square (e.g. a pentomino is worth -5 points).</p></html>");

	// Set up each instructions slide
	for (int i = 0 ; i < 6 ; i++)
	{
	    inst [i] = new JPanel (new BorderLayout ());
	    inst [i].add (instTxt [i], BorderLayout.NORTH); // add text
	    if (i < 5)
		inst [i].add (instImgLabel [i], BorderLayout.CENTER); // last slide doesn't have picture, add picture to everything else
	}
	
	// Set up back/next buttons and attach to a jpanel
	backNext = new JPanel (new FlowLayout ());
	backButton = new JButton ("back");
	nextButton = new JButton ("next");
	
	backButton.addActionListener (this); 
	nextButton.addActionListener (this);

	backNext.add (backButton);
	backNext.add (nextButton);

	// Set instructions layout to borderlayout
	this.getContentPane ().setLayout (new BorderLayout ());
	
	// Draw default slide
	drawInstructions ();
	
	// Setting up window close operation manually
	setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
	addWindowListener (this); // thus, adding window listener
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

    // Draws each instructions slide
    private void drawInstructions ()
    {
	this.getContentPane ().removeAll (); // remove old slide

	// Add content of slide: text/image panel, buttons panel
	this.getContentPane ().add (inst [instStep], "North");
	this.getContentPane ().add (backNext, "South");
	
	// Set all buttons to enabled
	backButton.setEnabled (true);
	nextButton.setEnabled (true);

	if (instStep == 0)
	    backButton.setEnabled (false); // if step 1 (first) you can't go to previous step
	if (instStep == 5)
	    nextButton.setEnabled (false); // if step 6 (last) you can't go to next step
	
	// Update
	this.getRootPane ().revalidate ();
	this.getContentPane ().repaint ();
    }

    // Perform action depending on what button is pressed
    public void actionPerformed (ActionEvent e)
    {
	JButton temp = (JButton) e.getSource (); // used to determine which button was pressed

	// back = move back to last step
	if (temp.equals (backButton)) 
	    instStep--;

	// next = next step
	else
	    instStep++;

	drawInstructions (); // redraw everything
    }
}



