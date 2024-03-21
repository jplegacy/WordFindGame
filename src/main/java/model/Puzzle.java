package model;

import java.util.*;

import model.word.SequenceWord;

import model.wordType.Day;
import model.wordType.WordTypeFactory;

/*
 * A puzzle is defined to be a 4x4 grid of letters with a companion map of words 
 * representing the words to be found within the grid of letters following a succession of moves
 * from adjacent grid elements. It's intended to be immutable.
 */

public final class Puzzle {


    private int rows;
    private int cols;

    private final ArrayList<ArrayList<Tile>> board;
    private final HashMap<String, SequenceWord> wordMap;

    /**
     * Initalizes Puzzle based off of a string representation of a puzzle
     * Example:
     * "bash\nssfa\ndsoa\ncocp" represents the following puzzle
     * b a s h
     * s s f a
     * d s o a
     * c o c p
     * 
     * @param boardString 4 sets of 4 characters seperated by line break "\n"
     * @param validWords set of words expected to be found in puzzle
     *      
     */
    public Puzzle(String boardString, HashMap<String, SequenceWord> words) {
        board = Tile.stringGridToTiles(boardString);
        wordMap = words;
    }
    

    /**
     * Initalizes Puzzle based off of a 2D ArrayList
     * 
     * @param boardString 2D Arraylist of Strings
     * @param validWords  set of words expected to be found in puzzle
     */
    public Puzzle(ArrayList<ArrayList<Tile>> boardList, HashMap<String, SequenceWord> words) {
        rows = boardList.size();
        assert boardList.size() != 0;
        cols = boardList.get(0).size();
        assert boardList.size() == rows;
        for(ArrayList<Tile> row : boardList){
            assert row.size() == cols;
        }
        board = deepCopy(boardList);

        WordTypeFactory.specialAssignments(words);
        wordMap = (HashMap<String, SequenceWord>) words.clone();
    }


    /**
     * Returns the puzzle as an 2D Arraylist
     * 
     * @return a 2d arraylist of the puzzle
     */

    public ArrayList<ArrayList<String>> getBoard(){
        ArrayList<ArrayList<String>> b = new ArrayList<>();
        for(int i=0; i < board.size(); i++){
            ArrayList<String> row = new ArrayList<>();
            for(int j=0; j < board.get(i).size(); j++){
                row.add(board.get(i).get(j).getLetter());
            }
            b.add(row);
        }
        return b;
    }

    private ArrayList<ArrayList<Tile>> deepCopy(ArrayList<ArrayList<Tile>> boardList){
        ArrayList<ArrayList<Tile>> newList = new ArrayList<ArrayList<Tile>>();
        
        for(ArrayList<Tile> list: boardList){
            newList.add((ArrayList<Tile>)list.clone());
        }

        return newList;
    }

    /**
     * Checks if a given word is defined in this puzzle
     * 
     * @param word that needs to be found in the puzzle
     * @return true if the word in the puzzle or not
     */

    public boolean contains(String word){
        return wordMap.containsKey(word);
    }

    /**
     * This returns the latest word object found based on a given wordList
     * of attempts that were tracked
     * 
     * @param wordList a list of words being tracked
     * @return a word object that is found to be associated in the puzzle,
     *         otherwise,
     *         return a null word object if not found within the puzzle
     */
    public SequenceWord getLatestWord(ArrayList<String> wordList) {
        if (wordList.size() == 0) {
            return null;
        } else {
            String latestString = wordList.get(wordList.size() - 1);
            if (this.contains(latestString)) {
                return wordMap.get(latestString);
            } else {
                return null;
            }
        }
    }

    /**
     * Returns all the words contained within this puzzle
     * 
     * @return a set of the words
     */

    public HashMap<String, SequenceWord> getWordsInPuzzle(){
        return (HashMap<String, SequenceWord>) wordMap.clone();
    }

    /**
     * Creates a grid with the number of times tiles are used
     * @return a 2x2 grid matching the puzzles dimensions
     */
    public int[][] getNumOfUsedTiles() {

        int[][] total = new int[rows][cols];

        for(SequenceWord word: getWordsInPuzzle().values()){
            ArrayList<Tile> seq = word.getSequence();
            for(Tile tile : seq){
                total[tile.getXPos()][tile.getYPos()] += 1;
            }

        }


        return total;
    }

    /**
     * Creates a grid with the number of times tiles are used
     * @return a 2x2 grid matching the puzzles dimensions
     */
    public int[][] getNumOfStartingTiles() {

        int[][] total = new int[rows][cols];

        for(SequenceWord word : getWordsInPuzzle().values()){
            if (word.getSequence().size() > 0){
                Tile startingTile = word.getSequence().get(0);

                total[startingTile.getXPos()][startingTile.getYPos()] += 1;
            }
        }

        return total;
    }

    /**
     * Returns Word of Day or Empty string
     * @return
     */
    public String getWordOfDay(){
        for(SequenceWord word : wordMap.values()){
            if(word.containsInstanceOf(Day.class)){
                return word.toString();
            }
        }
        return "";
    }

    /**
     * Returns a string in the following structure:
     * 
     * '[["a","b","c","d"],["a","b","c","d"],["a","b","c","d"],["a","b","c","d"]]'
     * 
     * to represent the following puzzle:
     * a b c d
     * a b c d
     * a b c d
     * a b c d
     */
    public String toString() {
        return board.toString();
    }

}
