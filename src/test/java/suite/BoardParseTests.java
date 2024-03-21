package suite;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import model.BoardParse;
import model.Puzzle;
import model.WordDatabase;

@RunWith(JUnit4.class)
public class BoardParseTests
{
    WordDatabase corpus;


    @Before
    public void setUp()
    {
        Set<String> wordlist = Set.of("Bash", "Stash", "Commit", "Nash");
        corpus = new WordDatabase(wordlist);
    }
    
    @Test
    public void parsingTest()
    {
        BoardParse parser1;

        try{
            parser1 = new BoardParse("./src/test/resources/run/testLevels/validPuzzle.txt", corpus);
        
            Puzzle parsedPuzzle = parser1.getPuzzle();
            assertTrue("arms is a valid word in test txt", parsedPuzzle.contains("arms"));
            assertTrue("arts is a valid word in test txt", parsedPuzzle.contains("arts"));
            assertTrue("best is a valid word in test txt", parsedPuzzle.contains("best"));


        } catch(FileNotFoundException e){
            fail("Test files should exist");        
        }catch(Exception e){
            fail("Other Error: " + e);        
        }
        
        try{
           BoardParse parser2 = new BoardParse("./src/test/resources/run/testLevels/non4x4Grid.txt", corpus);
            fail("Unparseable file should not be parsed");        

        } catch(AssertionError e){
            assertTrue("Unparseable puzzle should be unparseable",true);
        
        } catch(Exception e){
            fail("Assertion Error should be thrown, not Exception");        

        }
        
    }

}

