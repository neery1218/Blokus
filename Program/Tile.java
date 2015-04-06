import java.awt.Image;
public class Tile // creates a tile object, on a boolean n by m grid, the true values being the shape of the tile
{
    public boolean[] [] grid;
    public int x, y;
    public Image tileImage;
    public boolean isPlaced;


    public Tile (int x, int y, String values)  // basic constructor
    {
	grid = new boolean [x] [y];
	this.x = x;
	this.y = y;
	assign (values);
	isPlaced = false;
    }


    public void reset ()
    {
	isPlaced = false;
    }


    public void setPlaced ()  // sets isPlaced to true when it is placed on the blokus grid (mutator method)
    {
	isPlaced = true;
    }


    public int width ()  // returns width of tile
    {
	return x;
    }


    public int length ()  // returns length of tile
    {
	return y;
    }


    public boolean getIsPlaced ()  // accessor method
    {
	return isPlaced;
    }


    private void assign (String values)  // assigns the values of the squares on the tiles to the boolean grid
    {
	int position = 0;

	for (int i = 0 ; i < y ; i++)
	{
	    for (int j = 0 ; j < x ; j++, position++)
	    {
		grid [j] [i] = (values.charAt (position) == '1'); // if the character at that position is one, then the grid element is true
	    }
	}
    }


    public void rotate ()  // rotates the tile ninety degrees
    {
	int temp;
	temp = x;
	x = y;
	y = temp;

	boolean[] [] tempGrid = new boolean [x] [y];

	for (int i = 0 ; i < y ; i++) // rotates it 90 degrees and flips it horizontally (easy way of doing it)
	{
	    for (int j = 0 ; j < x ; j++)
	    {
		tempGrid [j] [i] = grid [i] [j]; // assigns the point that is it's reflection of itself if looked at with a diagonal line
	    }

	}

	grid = tempGrid; // assigns tempGrid to grid

	flipHorizontal (); // flips it horizontally so that it's just rotated 90 degrees
    }


    public void flipVertical ()  // flips the tile vertically
    {

	boolean[] [] tempGrid = new boolean [x] [y];

	for (int i = 0 ; i < y ; i++)
	{
	    for (int j = 0 ; j < x ; j++)
	    {
		tempGrid [j] [i] = grid [x - 1 - j] [i]; // switches the points over a horizontal line
	    }

	}

	grid = tempGrid; // assigns tempGrid to grid

    }


    public void flipHorizontal ()  // flips the tile horizontally
    {

	boolean[] [] tempGrid = new boolean [x] [y];

	for (int i = 0 ; i < y ; i++)
	{
	    for (int j = 0 ; j < x ; j++)
	    {
		tempGrid [j] [i] = grid [j] [y - 1 - i]; // switches the points over a vertical line
	    }

	}
	grid = tempGrid; // assigns tempGrid to grid
    }
}


