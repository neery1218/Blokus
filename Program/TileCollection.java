import java.awt.Image;


public class TileCollection
{ // creates 21 different tile objects required to play blokus, allows the user to rotate and flip tiles to place on the board

    // values of the tiles manually entered
    private String[] tileValues = {"1", "11", "1101", "111", "1111", "010111", "1111", "001111", "011110", "10001111", "010010111", "100100111", "01111100", "001111100", "11111", "101111", "011110100", "111011", "011110010", "010111010", "01001111"};
    private int[] xLength = {1, 2, 2, 3, 2, 3, 4, 3, 3, 4, 3, 3, 4, 3, 1, 2, 3, 2, 3, 3, 4};
    private int[] yLength = {1, 1, 2, 1, 2, 2, 1, 2, 2, 2, 3, 3, 2, 3, 5, 3, 3, 3, 3, 3, 2};

    private Tile[] pieces = new Tile [21];
    private Tile temp;

    public TileCollection ()  // basic constructor
    {
	for (int i = 0 ; i < 21 ; i++)
	    pieces [i] = new Tile (xLength [i], yLength [i], tileValues [i]);
    }


    public Tile choose (int num)  // returns a tile object
    {
	return pieces [num];
    }
}

