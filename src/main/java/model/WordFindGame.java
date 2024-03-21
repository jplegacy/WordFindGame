package model;

import java.util.*;

import gameObservers.WordFindObservers;
import model.word.Word;
import model.wordType.Bonus;
import model.wordType.Required;
import model.word.SequenceWord;

public class WordFindGame {
    private ArrayList<WordFindObservers> listeners;
    private ArrayList<SequenceWord> wordsFound;

    private Puzzle puzzle;
    private WordDatabase corpus;

    private int[][] numOfStartingTiles;
    private int[][] numOfUsedTiles;


    /**
     * Initalizes game using puzzle specified
     * 
     * @param p  Puzzle for game instance
     * @param db Word Database for game instance
     */
    public WordFindGame(Puzzle p, WordDatabase db) {
        puzzle = p;
        corpus = db;

        wordsFound = new ArrayList<SequenceWord>();

        listeners = new ArrayList<WordFindObservers>();

        numOfStartingTiles = puzzle.getNumOfStartingTiles();
        numOfUsedTiles = puzzle.getNumOfUsedTiles();
    }

    /**
     * Assigns a point to the user if word attempt is in puzzle or in corpus
     * 
     * @param attempt
     */
    public void attempt(String attempt) {
        SequenceWord word = attemptPoint(attempt);
        update_tiles(word);
        notifyListener();
    }

    private void update_tiles(SequenceWord word) {
        if (word == null){
            return ;
        }
        ArrayList<Tile> letterSequence = word.getSequence();
        if (word.getSequence().size() == 0){
            return ;
        }

        numOfStartingTiles[letterSequence.get(0).getXPos()][letterSequence.get(0).getYPos()] -= 1;

        for(Tile letter : letterSequence){
            numOfUsedTiles[letter.getXPos()][letter.getYPos()] -= 1;
        }
    }


    // Checks input to see if already taken
    private SequenceWord attemptPoint(String attempt) {
        attempt = attempt.toLowerCase(); // All words are displayed as a lowercase
        if(puzzle.contains(attempt)){
            SequenceWord puzzleWord = puzzle.getWordsInPuzzle().get(attempt);
            if (!wordsFound.contains(puzzleWord)) {
                wordsFound.add(puzzleWord);
                return puzzleWord;
            }
        }
        return null;
    }

    /**
     * Gets games puzzle
     */
    public Puzzle getPuzzle() {
        return puzzle;
    }

    /*
     * Gets the words found from the game
     */
    public ArrayList<String> getWordsFound() {
        ArrayList<String> stringify = new ArrayList<String>();
        for(Word word: wordsFound){
            stringify.add(word.toString());
        }

        return stringify;
    }

    /**
     * Gets the number of points the user has
     */
    public int getUserPoints() {
        int total = 0;
        for(Word word: wordsFound){
            if(word.containsInstanceOf(Required.class)){
                total++;
            }
        }

        return total;
    }
    /**
     * Gets the number of points the user has
     */
    public int getBonusPoints() {
        int total = 0;
        for(Word word: wordsFound){
            if(word.containsInstanceOf(Bonus.class)){
                total++;
            }
        }

        return total;
    }


    /**
     * Gets the number of points required to finish puzzle
     */
    public int getTotalPuzzlePoints() {

        HashMap<String, SequenceWord> wordMap = puzzle.getWordsInPuzzle();

        int total = 0;
        for(Word word: wordMap.values()){
            if(word.containsInstanceOf(Required.class)){
                total++;
            }
        }

        return total;
    }

    public int[][] getNumOfStartingTiles(){
        return numOfStartingTiles;
    }


    public int[][] getNumOfUsedTiles(){
        return numOfUsedTiles;
    }

    // ---------------------- LISTENERS/OBSERVERS

    public void addListener(WordFindObservers l) {
        listeners.add(l);
    }

    public void notifyListener() {
        for (WordFindObservers l : listeners) {
            l.update();
        }
    }
    public void clear() {
        for (WordFindObservers l : listeners) {
            l.close();
        }
    }
}