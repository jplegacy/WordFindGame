package suite;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.*;


import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import model.Puzzle;
import model.Tile;
import model.word.SequenceWord;


@RunWith(JUnit4.class)
public class PuzzleTests
{
    Puzzle p;
    ArrayList<ArrayList<String>> board;

    @Test
    public void arrayListConstructorTest()
    {
        ArrayList<ArrayList<Tile>> tileBoard = Tile.stringGridToTiles("aeim\nbfjn\ncgko\ndhlp");

        Set<String> wordlist = Set.of("Bash", "Commit", "Nash");
        
        HashMap<String,SequenceWord> map = new HashMap();
        wordlist.forEach(t -> map.put(t, new SequenceWord(t, new ArrayList<Tile>())));

        Puzzle p = new Puzzle(tileBoard, map);

        assertTrue("Words in puzzle should be the same ones from ones put int", p.getWordsInPuzzle().equals(map));

        ArrayList<ArrayList<String>> board = new ArrayList<>();
        board.add(new ArrayList<>(Arrays.asList("a","e","i","m")));
        board.add(new ArrayList<>(Arrays.asList("b","f","j","n")));
        board.add(new ArrayList<>(Arrays.asList("c","g","k","o")));
        board.add(new ArrayList<>(Arrays.asList("d","h","l","p")));
        assertTrue("Board in puzzle should be the same as puzzle put in", p.getBoard().equals(board));
    }

    @Test
    public void stringConstructorTest()
    {
        String stringBoard = "aeim\nbfjn\ncgko\ndhlp";

        Set<String> wordlist = Set.of("Bash", "Stash", "Commit", "Nash");

        HashMap<String, SequenceWord> map = new HashMap();
        wordlist.forEach(t -> map.put(t, new SequenceWord(t, new ArrayList<Tile>())));

        Puzzle p = new Puzzle(stringBoard, map);

        ArrayList<ArrayList<String>> board = new ArrayList<>();
        board.add(new ArrayList<>(Arrays.asList("a","e","i","m")));
        board.add(new ArrayList<>(Arrays.asList("b","f","j","n")));
        board.add(new ArrayList<>(Arrays.asList("c","g","k","o")));
        board.add(new ArrayList<>(Arrays.asList("d","h","l","p")));

        assertTrue("Words in puzzle should be the same ones from ones put int", p.getWordsInPuzzle().equals(map));
        assertTrue("Board in puzzle should be the same as puzzle put in", p.getBoard().equals(board));
    }

    @Test
    public void emptyTests() {

     try {
        Puzzle badPuzzle = new Puzzle("", new HashMap<>());
        fail("Constructor should reject bad string as a parameter");

     } catch (final AssertionError e) {
         assertTrue(true);
     }

     try {
        Puzzle badPuzzle = new Puzzle(new ArrayList<ArrayList<Tile>>(), new HashMap());
        fail("Constructor should reject bad string as a parameter");

     } catch (final AssertionError e) {
         assertTrue(true);
     }
   } 

    
    @Before
    public void setUp()
    {   
        String stringBoard = "aeim\nbfjn\ncgko\ndhlp";

        board = new ArrayList<>();
        board.add(new ArrayList<>(Arrays.asList("a","e","i","m")));
        board.add(new ArrayList<>(Arrays.asList("b","f","j","n")));
        board.add(new ArrayList<>(Arrays.asList("c","g","k","o")));
        board.add(new ArrayList<>(Arrays.asList("d","h","l","p")));

        Set<String> wordlist = Set.of("Bash", "Stash", "Commit", "Nash");
        HashMap<String,SequenceWord> map = new HashMap();
        wordlist.forEach(t -> map.put(t, new SequenceWord(t, new ArrayList<Tile>())));

        p = new Puzzle(stringBoard, map);
    }
    
    
    @Test
    public void getBoardTest(){
        ArrayList<ArrayList<String>> actualBoard = p.getBoard();
        assertTrue("Board inside of puzzle should be the same", board.equals(actualBoard));

        board.add(new ArrayList<>());
        board.get(0).add("");

        assertTrue("Changing rows in board used for initalization should not change internal", board.size() != actualBoard.size());
        assertTrue("Changing elements in board used for initalization should not change internal", board.get(0).size() != actualBoard.get(0).size());

        actualBoard.add(new ArrayList<String>());
        actualBoard.get(0).add("");

        ArrayList<ArrayList<String>> actualBoard2 = p.getBoard();

        assertTrue("Changing rows in received board should not change internal", actualBoard.size() != actualBoard2.size());
        assertTrue("Changing elements in received board should not change internal", actualBoard.get(0).size() != actualBoard2.get(0).size());
    }

    @Test
    public void containsTest(){
        assertFalse("Word not in puzzle should not be contained", p.contains("TASH"));
        assertFalse("Empty Word should not be in puzzle", p.contains(""));

        assertTrue("Word in puzzle should be found", p.contains("Bash"));
    }

}

