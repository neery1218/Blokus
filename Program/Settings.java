import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Settings extends JDialog implements ActionListener, WindowListener
{
    private Color[] colorScheme = new Color [5];
    private int i;
    private String[] playerNames = new String [4];
    private JTextField[] nameField = new JTextField [4];
    private JLabel[] nameLabel = new JLabel [4];
    private JRadioButton[] presetTheme = new JRadioButton [5];
    private BufferedImage[] themeIcon = new BufferedImage [5];
    private JPanel setPresetTheme = new JPanel ();
    private ImageIcon[] tabIcon = new ImageIcon [3];
    private JButton[] colorChooserBtn = new JButton [5];

    // Default constructor
    public Settings ()
    {
	// Import images
	try
	{
	    for (int i = 0 ; i < 5 ; i++)
		themeIcon [i] = ImageIO.read (this.getClass ().getResource ("radioIcon" + i + ".PNG"));

	    for (int i = 0 ; i < 3 ; i++)
		tabIcon [i] = new ImageIcon (ImageIO.read (this.getClass ().getResource ("tabIcon" + i + ".PNG")));
	}
	catch (IOException e)
	{
	    // do nothing
	}

	// Set up dialog
	setSize (420, 350);
	setModal (true);
	this.setTitle ("Blokus v.1.0: Settings");

	// Default colour scheme is Adrift in Dreams
	colorScheme [0] = new Color (207, 240, 158);
	colorScheme [1] = new Color (168, 219, 168);
	colorScheme [2] = new Color (121, 189, 154);
	colorScheme [3] = new Color (59, 134, 134);
	colorScheme [4] = new Color (11, 72, 107);

	// Default player names
	for (int i = 0 ; i < 4 ; i++)
	    playerNames [i] = "user" + i;

	// First Tab
	SpringLayout layout = new SpringLayout (); // Set layout to spring layout because it allows more control over aligning 
	JPanel setNames = new JPanel ();
	setNames.setLayout (layout);

	for (int i = 0 ; i < 4 ; i++) // 4 users
	{
	    nameField [i] = new JTextField ("user" + (i + 1), 15); // By default, the JTextField is created with "user1", "user2", etc.
	    nameField [i].addActionListener (this);

	    nameLabel [i] = new JLabel ("User " + (i + 1) + ":"); // label to tell user what name to enter where

	    // Add name labels and fields to the panel
	    setNames.add (nameLabel [i]);
	    setNames.add (nameField [i]);
	    
	    // Set up SpringLayout
	    layout.putConstraint (SpringLayout.WEST, nameLabel [i], 90, SpringLayout.WEST, setNames); // left edge of name label 90 pixels to the right of main panel
	    layout.putConstraint (SpringLayout.NORTH, nameLabel [i], 20 + (i * 40), SpringLayout.NORTH, setNames);  // top edge of name label 20 + (i * 40) (a line) below top edge of main panel

	    layout.putConstraint (SpringLayout.WEST, nameField [i], 5, SpringLayout.EAST, nameLabel [i]); // left edge of text field 5 pixels to the right of name label
	    layout.putConstraint (SpringLayout.NORTH, nameField [i], 20 + (i * 40), SpringLayout.NORTH, setNames); // top edge of text field 20 + (i * 40) ( a line) below top edge of main panel
	}

	// Second Tab
	
	// set up radio buttons
	presetTheme [0] = new JRadioButton ("Adrift in Dreams", true);
	presetTheme [0].addActionListener (this);

	presetTheme [1] = new JRadioButton ("1001 Stories", false);
	presetTheme [1].addActionListener (this);

	presetTheme [2] = new JRadioButton ("Dig My Olive Branch", false);
	presetTheme [2].addActionListener (this);

	presetTheme [3] = new JRadioButton ("Melting Puppies", false);
	presetTheme [3].addActionListener (this);

	presetTheme [4] = new JRadioButton ("h a n a i r o", false);
	presetTheme [4].addActionListener (this);

	ButtonGroup group = new ButtonGroup (); // group buttons
	for (int i = 0 ; i < 5 ; i++)
	    group.add (presetTheme [i]);
	setPresetTheme.add (new JLabel (new ImageIcon (themeIcon [0])));
	for (int i = 0 ; i < 5 ; i++)
	    setPresetTheme.add (presetTheme [i]);

	// Third Tab
	JPanel setCustomTheme = new JPanel ();

	for (int i = 0 ; i < 5 ; i++) // Set up color chooser buttons
	{
	    colorChooserBtn [i] = new JButton ("Set Color " + (i + 1));
	    colorChooserBtn [i].addActionListener (this);
	    setCustomTheme.add (colorChooserBtn [i]);
	}

	// Add all panels to a tabbed pane
	JTabbedPane tabbedPane = new JTabbedPane ();
	tabbedPane.addTab ("Set Player Names", tabIcon [0], setNames, "Enter names for the players");
	tabbedPane.addTab ("Preset Themes", tabIcon [1], setPresetTheme, "Choose one of five preset colour themes!");
	tabbedPane.addTab ("Custom Theme", tabIcon [2], setCustomTheme, "Choose your own colours for your colour theme");

	// Add tabbed pane to main jframe
	this.getContentPane ().add (tabbedPane);

	// Make it so that the program does not terminate on exit,
	// but instead makes the settings dialog invisible
	setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
	addWindowListener (this);
    }

    // Set up actions performed when various JComponents are used
    public void actionPerformed (ActionEvent e)
    {
	JComponent temp = (JComponent) e.getSource ();
	
	// Third pane options - manually choose colours, so JColorChooser dialog is shown
	if (temp.equals (colorChooserBtn [0]))
	    colorScheme [0] = JColorChooser.showDialog (null, "Color 1", colorScheme [0]);
	    
	else if (temp.equals (colorChooserBtn [1]))
	    colorScheme [1] = JColorChooser.showDialog (null, "Color 2", colorScheme [1]);
	    
	else if (temp.equals (colorChooserBtn [2]))
	    colorScheme [2] = JColorChooser.showDialog (null, "Color 3", colorScheme [2]);
	    
	else if (temp.equals (colorChooserBtn [3]))
	    colorScheme [3] = JColorChooser.showDialog (null, "Color 4", colorScheme [3]);
	    
	else if (temp.equals (colorChooserBtn [4]))
	    colorScheme [4] = JColorChooser.showDialog (null, "Color 5", colorScheme [4]);

	// Second pane options - choose one of 5 preset themes
	else if (temp.equals (presetTheme [0]))
	{
	    // set colours to preset colours
	    colorScheme [0] = new Color (207, 240, 158);
	    colorScheme [1] = new Color (168, 219, 168);
	    colorScheme [2] = new Color (121, 189, 154);
	    colorScheme [3] = new Color (59, 134, 134);
	    colorScheme [4] = new Color (11, 72, 107);
	    
	    // update preview colours image
	    setPresetTheme.removeAll ();
	    setPresetTheme.add (new JLabel (new ImageIcon (themeIcon [0])));
	    for (int i = 0 ; i < 5 ; i++)
		setPresetTheme.add (presetTheme [i]);
		
	    // update
	    setPresetTheme.getRootPane ().revalidate ();
	    setPresetTheme.repaint ();
	}
	else if (temp.equals (presetTheme [1]))
	{
	    // set colours to preset colours
	    colorScheme [0] = new Color (248, 177, 149);
	    colorScheme [1] = new Color (246, 114, 128);
	    colorScheme [2] = new Color (192, 108, 132);
	    colorScheme [3] = new Color (108, 91, 123);
	    colorScheme [4] = new Color (53, 92, 125);

	    // update preview colours image
	    setPresetTheme.removeAll ();
	    setPresetTheme.add (new JLabel (new ImageIcon (themeIcon [1])));
	    for (int i = 0 ; i < 5 ; i++)
		setPresetTheme.add (presetTheme [i]);
	    
	    // update
	    setPresetTheme.getRootPane ().revalidate ();
	    setPresetTheme.repaint ();
	}
	else if (temp.equals (presetTheme [2]))
	{
	    // set colours to preset colours
	    colorScheme [0] = new Color (48, 0, 24);
	    colorScheme [1] = new Color (90, 61, 49);
	    colorScheme [2] = new Color (131, 123, 71);
	    colorScheme [3] = new Color (173, 184, 95);
	    colorScheme [4] = new Color (229, 237, 184);

	    // update preview colours image
	    setPresetTheme.removeAll ();
	    setPresetTheme.add (new JLabel (new ImageIcon (themeIcon [2])));
	    for (int i = 0 ; i < 5 ; i++)
		setPresetTheme.add (presetTheme [i]);
		
	    // update
	    setPresetTheme.getRootPane ().revalidate ();
	    setPresetTheme.repaint ();
	}
	else if (temp.equals (presetTheme [3]))
	{
	    // set colours to preset colours
	    colorScheme [0] = new Color (147, 223, 184);
	    colorScheme [1] = new Color (255, 200, 186);
	    colorScheme [2] = new Color (227, 170, 214);
	    colorScheme [3] = new Color (181, 216, 235);
	    colorScheme [4] = new Color (255, 189, 216);

	    // update preview colours image
	    setPresetTheme.removeAll ();
	    setPresetTheme.add (new JLabel (new ImageIcon (themeIcon [3])));
	    for (int i = 0 ; i < 5 ; i++)
		setPresetTheme.add (presetTheme [i]);
		
	    // update
	    setPresetTheme.getRootPane ().revalidate ();
	    setPresetTheme.repaint ();
	}
	else if (temp.equals (presetTheme [4]))
	{
	    // set colours to preset colours
	    colorScheme [0] = new Color (248, 237, 205);
	    colorScheme [1] = new Color (228, 226, 201);
	    colorScheme [2] = new Color (186, 198, 176);
	    colorScheme [3] = new Color (91, 57, 48);
	    colorScheme [4] = new Color (125, 106, 102);

	    // update preview colours image
	    setPresetTheme.removeAll ();
	    setPresetTheme.add (new JLabel (new ImageIcon (themeIcon [4])));
	    for (int i = 0 ; i < 5 ; i++)
		setPresetTheme.add (presetTheme [i]);
	      
	    // update
	    setPresetTheme.getRootPane ().revalidate ();
	    setPresetTheme.repaint ();
	}
	else
	{
	    // Get the text in each of the text fields
	    for (int i = 0 ; i < 4 ; i++)
	    {
		playerNames [i] = nameField [i].getText ();
	    }
	}
    }

    // Accessor method to get color scheme
    public Color[] getColors ()
    {
	return colorScheme;
    }

    // Accessor method to get player names
    public String[] getNames ()
    {
	return playerNames;
    }
    
    // Method that must be implemented as a result of windowlistener, does nothing
    public void windowClosed (WindowEvent e)
    {
    }

    // Method that must be implemented as a result of windowlistener, does nothing
    public void windowActivated (WindowEvent e)
    {
    }

    // Custom window close event
    public void windowClosing (WindowEvent e)
    {
	this.setVisible (false);
	this.setFocusable (false);
	for (int i = 0 ; i < 4 ; i++) // get text from player name text fields
	{
	    playerNames [i] = nameField [i].getText ();
	}
    }

    // Method that must be implemented as a result of windowlistener, does nothing
    public void windowDeactivated (WindowEvent e)
    {
    }

    // Method that must be implemented as a result of windowlistener, does nothing
    public void windowDeiconified (WindowEvent e)
    {
    }

    // Method that must be implemented as a result of windowlistener, does nothing
    public void windowIconified (WindowEvent e)
    {
    }

    // Method that must be implemented as a result of windowlistener, does nothing
    public void windowOpened (WindowEvent e)
    {
    }
}
