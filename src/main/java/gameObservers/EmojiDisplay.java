package gameObservers;

import model.WordFindGame;
import model.word.Word;
import model.wordType.WordType;
import model.wordType.Emoiji;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class EmojiDisplay implements WordFindObservers {

    private WordFindGame game;
    private Stage display;
    private Label emojiDisplay;
    private String previousWordString;

    /**
     * Initializes the emojiDisplay provided by a WordFindGame to update the display
     * as the game is played
     * 
     * @param game a WordFindGame object to identify which words have emoji's to
     *             display
     */
    public EmojiDisplay(WordFindGame game) {
        this.game = game;
        this.previousWordString = "";

        this.display = new Stage();
        this.display.setTitle("Emoji Display");
        this.emojiDisplay = new Label("");
        this.emojiDisplay.setPrefSize(100, 100);
        emojiDisplay.setStyle("-fx-font-size: 40px;");
        emojiDisplay.setFont(Font.font("Segoe UI Emoji"));
        Group root = new Group(emojiDisplay);
        Scene scene = new Scene(root, 150, 150);
        display.setScene(scene);
        display.show();
    }

    /**
     * This updates the previous word string found within a word to be referenced
     * 
     * @param newString the new word that will be updated to be the latest word
     *                  referenced
     */
    private void updatePreviousWordString(String newString) {
        previousWordString = newString;
    }

    /**
     * closes out the emojiDisplay screen
     */
    public void close() {
        display.close();
    }

    /**
     * This updates the emojiDisplay once an attempt is registered, captures it
     * based on the latest word that has been either found or not
     */
    public void update() {
        ArrayList<String> wordsFound = game.getWordsFound();

        Word latestWord = game.getPuzzle().getLatestWord(wordsFound);
        // the wordFound list contains no wordsFound currently in the game
        if (wordsFound.size() == 0) {
            emojiDisplay.setText("");
            // latest word in the wordsfound pile matches the latest word found, no update
        } else if (latestWord.getWord().equals(previousWordString)) {
            emojiDisplay.setText("");
            // the word has no emoji associated with it (no Emoji WordType)
        } else if (!latestWord.containsInstanceOf(Emoiji.class)) {
            emojiDisplay.setText("");
        } else { // word is a valid emoji and displays it
            WordType emoji = latestWord.getType(Emoiji.class);
            Emoiji emojiValue = (Emoiji) emoji;
            emojiDisplay.setText(emojiValue.getEmoji());
            this.updatePreviousWordString(latestWord.getWord());
        }
    }
}
