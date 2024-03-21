package suite;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


import model.word.Word;


@RunWith(JUnit4.class)
public class WordTests{    
    Word word1;
    Word word2;

    @Before
    public void setUp()
    {
        word1 = new Word("Test");
    }
    @Test
    public void constructorTest(){
        Word word = new Word("Test");
        assertTrue("Word Class should return string that it was initalized with", word.toString().equals("test"));
    }
    @Test
    public void equals(){
        word1 = new Word("Test");
        word2 = new Word("Test");

        assertTrue("Two Classes that use the same word should be the same", word1.equals(word2));
    }


}

