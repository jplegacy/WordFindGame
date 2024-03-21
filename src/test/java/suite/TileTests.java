package suite;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Tile;

public class TileTests {
    Tile tile1;
    Tile tile2;

    @Before
    public void setUp()
    {
        tile1 = new Tile("A", 0, 0);
    }
    @Test
    public void constructorTest(){
        tile1 = new Tile("A", 0, 0);
        assertTrue("Tile Class should return Correct location", tile1.getXPos()==0);
        assertTrue("Tile Class should return Correct location", tile1.getYPos()==0);

        assertTrue("Tile Class should return Correct location", tile1.getLetter()=="A");

    }
    
    @Test
    public void equals(){
        tile1 = new Tile("A", 0, 0);
        tile2 = new Tile("A", 0, 0);
        assertTrue("Two Classes that use the same character and location should be the same", tile1.equals(tile2));

        tile2 = new Tile("A", 1, 0);
        assertFalse("Two Classes that use the same character in different locations should not be the same", tile1.equals(tile2));

    }
}
