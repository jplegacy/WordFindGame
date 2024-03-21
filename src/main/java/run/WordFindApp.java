package run;

import java.io.IOException;
import java.util.ArrayList;

import appObservers.AppObserver;
import appObservers.LoadDisplay;
import gameObservers.BoardDisplay;
import gameObservers.EmojiDisplay;

import javafx.application.Application;


import javafx.stage.Stage;
import model.BoardParse;
import model.Puzzle;
import model.WordDatabase;
import model.WordFindGame;

public class WordFindApp extends Application {

    private String providedPath;
    private ArrayList<AppObserver> observers;
    private WordFindGame game;

    /*
     * This intializes the app containing no provided filePath
     */
    public WordFindApp() {
        observers = new ArrayList<AppObserver>();
        this.providedPath = "";
    }

    /*
     * This initializes the app with another parameter containing a filePath to
     * rerun the start method
     */
    public WordFindApp(String additionalParameter) {
        observers = new ArrayList<AppObserver>();
        this.providedPath = additionalParameter;
    }

    /*
     * This retrieves the additional parameter that the app contains
     */
    public String getParameter() {
        return this.providedPath;
    }

    @Override
    public void start(Stage primaryStage) {
        startGame();
    }

    public void restart(String path) {
        clear();
        providedPath = path;
        startGame();
    }

    private void startGame() {

        WordDatabase corpus;

        try {
            corpus = new WordDatabase("./data/wordlist.txt");
            System.out.println("Corpus Initalized");
        } catch (IOException e) {
            return;
        }

        /**
         * Set<String> wordlist = Set.of("Bash", "Stash", "Commit", "Nash");
         * 
         * corpus = new WordDatabase(wordlist);
         */

        BoardParse parse;
        try {
            if (this.getParameter() == "") {
                parse = new BoardParse("./levels/levelDemo.txt", corpus);
            } else {
                parse = new BoardParse(this.getParameter(), corpus);
            }
        } catch (Exception FileNotFoundException) {
            return;
        }

        //For random board generation
        //BoardParse parse = new BoardParse(corpus);

        Puzzle puzzle = parse.getPuzzle();
        System.out.println("Word of the Day is " + puzzle.getWordOfDay());

        System.out.println("Words in Puzzle:" + puzzle.getWordsInPuzzle().keySet());


        try {
            game = new WordFindGame(puzzle, corpus);
        } catch (AssertionError e) {
            System.err.println("Error Starting Game. Issue parsing Puzzle or Corpus");
            return;
        }

        BoardDisplay boardDisplay = new BoardDisplay(game);
        game.addListener(boardDisplay);

        EmojiDisplay emojiDisplay = new EmojiDisplay(game);
        game.addListener(emojiDisplay);

        LoadDisplay loadDisplay = new LoadDisplay(this);
        this.addListener(loadDisplay);

    }

    private void addListener(AppObserver l) {
        this.observers.add(l);
    }

    private void clear() {
        game.clear();
        for (AppObserver l : observers) {
            l.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}