import java.util.ArrayList;


public class Player// contains the name of the player, and the tiles
{
    private String name;
    private TileCollection pieces = new TileCollection ();

    public Player (String name)// basic constructor
    {
	this.name = name;
    }

    public String getName ()// accessor method
    {
	return name;
    }
    
    public Tile choose (int num)// returns a tile from tileCollection
    {
	return pieces.choose (num);
    }

    public void rotate (int num)//rotates the given tile
    {
	pieces.choose (num).rotate ();
    }
}
