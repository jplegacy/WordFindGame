package suite;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import model.Puzzle;
import model.Tile;
import model.WordDatabase;
import model.WordFindGame;
import model.word.SequenceWord;


@RunWith(JUnit4.class)
public class WordFindGameTests
{
    WordFindGame game;
    
    Puzzle puzzle;

    @Before
    public void setUp()
    {
        Set<String> wordlist = Set.of("Bash", "Stash", "Commit", "Nash");
        
        HashMap<String,SequenceWord> map = new HashMap();
        wordlist.forEach(t -> map.put(t.toLowerCase(), new SequenceWord(t, new ArrayList<Tile>())));    
    
        puzzle =  new Puzzle("jimp\nstiy\nsals\nsaod", map);    

        WordDatabase corpus = new WordDatabase(wordlist);
        
        game = new WordFindGame(puzzle, corpus);
    }
    @Test
    public void getPuzzleTest(){
        Puzzle p = game.getPuzzle();
        assertTrue("Puzzle Used to initalize game should be the same", puzzle.equals(p));
    }
    @Test
    public void attemptTest(){
        game.attempt("");
        assertTrue("No points should be rewarded from an empty attempt", game.getUserPoints() == 0);
        
        game.attempt("bash");
        assertTrue("Points Should be awarded for a word in the game", game.getUserPoints() == 1);
        
        game.attempt("BASH");
        game.attempt("bAsh");
        game.attempt("bAs");
        assertTrue("No additional points should be rewarded for the same word, different capitalizations, or partial words", game.getUserPoints() == 1);
        
        game.attempt("stash");
        assertTrue("Points Should be awarded for new word", game.getUserPoints() == 2);

    }
    @Test
    public void getWordsFoundTest(){
        game.attempt("");
        assertFalse("Empty strings should not be present in wordsFound", game.getWordsFound().contains(""));
        
        game.attempt("stash");
        assertTrue("Word Found should be in wordsFound", game.getWordsFound().contains("stash"));
       
        game.attempt("cca");
        assertFalse("Word not found should not be in wordsFound", game.getWordsFound().contains("cca"));
    }

    @Test
    public void getTotalPuzzlePointsTest(){
        assertTrue("Total number of words to find should match number of words to find in puzzle", game.getTotalPuzzlePoints() == 4);
    }

}

