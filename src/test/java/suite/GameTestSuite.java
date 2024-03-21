package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses
({
    BoardParseTests.class,
    PuzzleTests.class,
    WordFindGameTests.class,
    WordTests.class,
    TileTests.class,

})
public class GameTestSuite
{ 
}
