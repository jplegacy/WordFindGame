package model;

import java.util.*;

import model.word.SequenceWord;

import java.io.File;
import java.io.FileNotFoundException;


public class BoardParse {
    private Puzzle board;
    private WordDatabase corpus;

    private final String RAND_LETTERS = "aaaaaaaabbbcccddddeeeeeeeeeeffggghhhiiiiiiijkklllllmmmnnnnnoooooopppqrrrrssssstttttuuuuvvwwxyyyz";
    private final String RAND_LETTERS2 = "abcdefghijklmnoprstu";
    private final int MIN_SOLUTIONS = 5;
    private final int MAX_WORD_LENGTH = 6;

    private final int DEFAULT_RAND_BOARD_ROW = 4;
    private final int DEFAULT_RAND_BOARD_COL = 4;


    private int rows;
    private int cols;

    /**
     * Parses a board configuration file 
     * @param filePath the path to the puzzle 
     * @param wordDB the wordDatabase
     * @throws FileNotFoundException when Filepath string leads to an invalid file location
     */
    public BoardParse(String filePath, WordDatabase wordDB) throws FileNotFoundException{
        corpus = wordDB;
        String extractedFile = getBoardFileContents(filePath);
        unmarshal(extractedFile);
    }

    /**
     * alternate constructor. creates a puzzle object with a random board if no board.txt file is given
     * @param wordDB the wordDatabase
     */
    public BoardParse(WordDatabase wordDB) {
        corpus = wordDB;
        ArrayList<ArrayList<Tile>> boardList = genRandBoard();
        HashMap<String, SequenceWord> wordList = findAllWords(boardList, corpus);
        while (wordList.size() < MIN_SOLUTIONS) {
            boardList = genRandBoard();
            wordList = findAllWords(boardList,corpus);
        }
        board = new Puzzle(boardList, wordList);
    }

    /**
     * generates a random board
     * @return the letters in the board, as an ArrayList<ArrayList<Tile>>
     */
    private ArrayList<ArrayList<Tile>> genRandBoard() {
        Random rand =  new Random();
        ArrayList<ArrayList<Tile>> boardList = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < DEFAULT_RAND_BOARD_ROW; i++) {
            ArrayList<Tile> rowList = new ArrayList<Tile>();
            for (int j = 0; j < DEFAULT_RAND_BOARD_COL; j++) {
                char charLetter = RAND_LETTERS.charAt(rand.nextInt(RAND_LETTERS.length()));
                String letter = Character.toString(charLetter);
                Tile tile = new Tile(letter, i, j);
                rowList.add(tile);
            }
            boardList.add(rowList);
        }
        return boardList;
    }

    /**
     * gets the contents of a file
     * 
     * @param filePath a string, the filepath of a level.txt file
     * @return the string parsed from that file
     * @throws FileNotFoundException
     */
    private String getBoardFileContents(String filePath) throws FileNotFoundException{
        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        String tempString = "";
        while (sc.hasNextLine())
            tempString += sc.nextLine();
        sc.close();

        String lowerCaseString = tempString.toLowerCase();
        return lowerCaseString;
    }

    /**
     * splits the template string into letters in the board and words,
     * then creates a puzzle object
     * 
     * @param configurationTemplate the string that has been parsed from the board txt file
     */
    private void unmarshal(String configurationTemplate){
        String[] arrSplit = configurationTemplate.split("~");
        String boardString = arrSplit[0];
        String validWords;
        if (arrSplit.length > 1) {
            validWords = arrSplit[1];
        } else {
            validWords = "";
        }

        ArrayList<ArrayList<Tile>> boardList = splitBoardString(boardString);
        // System.out.println("AFter splitWord - BP");
        HashMap<String, SequenceWord> wordList;
        if (!validWords.equals("")) {
            wordList = splitValidWords(boardList, validWords);
            } 
        else {
            wordList = findAllWords(boardList,corpus);
        }
        // System.out.println("After FindAllWords - BP");
        board = new Puzzle(boardList, wordList);
        // System.out.println("AFTER MAKING PUZZLE");
    }

    /**
     * finds all valid words in a grid
     * 
     * @param boardList ArrayList<ArrayList<String>> of letters in the grid
     * @return the wordmap of valid words and their types
     */
    private HashMap<String, SequenceWord> findAllWords(ArrayList<ArrayList<Tile>> boardList, WordDatabase corpus) {
        ArrayList<Tile> tilesWord = new ArrayList<Tile>();

        boolean checked[][] = new boolean[rows][cols];
        HashMap<String, SequenceWord> wordMap = new HashMap<String, SequenceWord>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                wordMap = findAllWordsRecursive(boardList, wordMap, checked, i, j, tilesWord, corpus);
            }
        }
        
        return wordMap;
    }

    /**
     * private recursive helper for find all words
     * 
     * @param boardList ArrayList<ArrayList<String>> containing all letters in the grid
     * @param wordMap HashMap<String, WordType> a map of valid words and their type
     * @param checked array of arrays indicating which letters have been checked
     * @param i the row to start at
     * @param j the column to start at
     * @param word the string being checked
     * @return the wordmap of valid words and their types
     */
    private HashMap<String, SequenceWord> findAllWordsRecursive(ArrayList<ArrayList<Tile>> boardList,
            HashMap<String, SequenceWord> wordMap, boolean checked[][], int i ,int j, ArrayList<Tile> tilesWord, WordDatabase corpus) {
        checked[i][j] = true;
        tilesWord.add(boardList.get(i).get(j));
        //System.out.println("STEP 1");
        String word = findWordFromTilesWord(tilesWord);
        if (corpus.contains(word)) {
            wordMap.put(word, new SequenceWord(word, (ArrayList<Tile>) tilesWord.clone()));
            Tile firstTile = tilesWord.get(0);
            }
            //System.out.println("STEP 2");
        if (tilesWord.size()<=MAX_WORD_LENGTH)
        {
            for (int row = i - 1; row <= i + 1 && row < rows; row++) {
                for (int col = j - 1; col <= j + 1 && col < cols; col++) {
                    if (row >= 0 && col >= 0 && !checked[row][col]) {
                        //System.out.println("STEP 4 before boom");
                        wordMap = findAllWordsRecursive(boardList, wordMap, checked, row, col, tilesWord, corpus);
                        //System.out.println("BOOM");
                    }
                }
            }
        }

        //System.out.println(tilesWord);
        tilesWord.remove(tilesWord.size() - 1);
        checked[i][j] = false;
        //System.out.println("BOOM");

        return wordMap;
    }

    private String findWordFromTilesWord(ArrayList<Tile> tilesWord) {
        String toReturn = "";
        for (Tile tile : tilesWord) {
            toReturn += tile.getLetter();
        }
        return toReturn;
    }
    
    /**
     * parses a String and returns the valid words
     * 
     * @param ValidWords a string of valid words, ex "Agape,Algae,Plate,Platter"
     * @return the hash map of strings and their associated wordtype
    */

    private HashMap<String, SequenceWord> splitValidWords(ArrayList<ArrayList<Tile>> boardList , String ValidWords) {
        String[] tempWordArr = ValidWords.split(",");

        Set<String> wordlist = Set.of(tempWordArr);
        WordDatabase requiredWords = new WordDatabase(wordlist);

        return findAllWords(boardList, requiredWords);
    }

    /**
     * parses a String and returns the letters in the board
     * 
     * @param boardString (ex "EILA,TPAG,RETO,HTAY")
     * @return the letters in the board in an ArrayList<ArrayList<Tile>>
     */
    private ArrayList<ArrayList<Tile>> splitBoardString(String boardString) {
        String[] boardArr = boardString.split(",");
        rows = boardArr.length;
        cols = boardArr[0].length();
        System.out.println("ROWS: "+ rows + " COLS: " + cols);
        ArrayList<ArrayList<Tile>> boardList = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < boardArr.length; i++) {
            String line = boardArr[i];
            assert (rows == line.length()) : "Number of Rows in grid must be " + rows;

            String[] rowLetters = line.split("");
            ArrayList<Tile> rowLettersList = new ArrayList<Tile>();
            for (int j = 0; j < rowLetters.length; j++) {
                String letter = rowLetters[j];
                Tile tile = new Tile(letter, i, j);
                rowLettersList.add(tile);
            }

            assert (cols == rowLettersList.size()): "Number of Columns in grid must be " + cols;

            boardList.add(rowLettersList);
        }
  

        return boardList;
    }

    /**
     * @return the parsed Board in the specified location 
     */
    public Puzzle getPuzzle(){
        return board;
    }

}