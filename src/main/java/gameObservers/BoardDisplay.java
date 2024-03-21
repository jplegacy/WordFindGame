package gameObservers;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.WordFindGame;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

public class BoardDisplay extends GridPane implements WordFindObservers {

    private WordFindGame game;
    private StringBuilder currentWord = new StringBuilder();
    private Label currentWordLabel;
    private Label currentWordDisplay;
    private Label totalPointsLabel;
    private Label userPointsLabel;
    private Label bonusPointsLabel;

    private int numRows;
    private int numCols;
    private static int buttonWidth = 100;
    private static int buttonHeight = 100;
    private static int gap = 5;

    private Stage display;
    private Button lastClickedButton = null;

    public BoardDisplay(WordFindGame game) {
        this.game = game;
        this.numRows = game.getPuzzle().getBoard().size();
        this.numCols = game.getPuzzle().getBoard().get(0).size();
        setPadding(new Insets(10));
        setHgap(gap);
        setVgap(gap);

        addPointSection();
        addCurrentWordSection();
        initializeButtons();
        initializeStage();
    }

    /*
     * initialize the scene
     * add style sheet
     * set title
     */
    private void initializeStage() {
        int sceneWidth = numCols * (buttonWidth + gap) + gap;
        int sceneHeight = (numRows + 3) * (buttonHeight + gap) + gap;

        Scene scene = new Scene(this, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getResource("boardDisplay.css").toExternalForm());

        display = new Stage();
        display.setTitle("WordFindGame");

        display.setScene(scene);
        display.show();
    }

    /**
     * Closes display
     */
    public void close(){
        display.close();
    }

    /*
     * return starting pane for the Model
     */
    public GridPane startingPane() {
        return this; 
    }

    /*
     * initialize the buttons
     */
    private void initializeButtons() {
        int[][] usedTiles = game.getNumOfUsedTiles();

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                String letter = game.getPuzzle().getBoard().get(i).get(j);
                Button button = createButton(letter.toUpperCase(), i, j);

            // Disable the button if the number of used tiles is 0
            if (usedTiles[i][j] == 0) {
                button.setDisable(true);
            }

                button.getStyleClass().add("gridButton");
                button.setId("button" + i + j);
                add(button, j, i + 2); 
            }
        }
    }
    
   /*
    * Create Button with given letters and index
    */
    private Button createButton(String letter, int row, int column) {
        Button button = new Button(letter);
        button.setPrefSize(buttonWidth, buttonHeight);
        button.setOnAction(event -> {
            //check adjacent button to last clicked
            if (isAdjacentButton(button) && !button.isDisabled()) {
                lastClickedButton = button;
                currentWord.append(letter);
                updateCurrentWordDisplay();
                button.setDisable(true);
            }
        });
        // set hover color 
        button.setOnMouseEntered(event -> {
            if (!button.isDisabled() && isAdjacentButton(button)) {
                button.setStyle("-fx-background-color: lightblue;");
            }
        });
        // disable mouse however event
        button.setOnMouseExited(event -> {
            if (!button.isDisabled()) {
                button.setStyle("");
            }
        });

        // Add a label inside the button to display the count
        int count = game.getNumOfStartingTiles()[row][column];
        Label countLabel = new Label(String.valueOf(count));
        countLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: gray;");
        button.setGraphic(countLabel);
        button.setContentDisplay(ContentDisplay.BOTTOM);

        return button;
    }
    
    /*
    * add current word display
    */
    private void addCurrentWordSection() {
        currentWordLabel = new Label("Current Word:");
        currentWordDisplay = new Label("");

        Button submitButton = new Button("Submit");
        submitButton.setId("submitButton");
        submitButton.setOnAction(event -> {
            String attemptedWord = currentWord.toString();
            System.out.println("Attempted Word: " + attemptedWord);
            game.attempt(attemptedWord.toLowerCase());
            System.out.println("User points: " + game.getUserPoints());

            update();

            isWon();
        });

        Button resetButton = new Button("Reset");
        resetButton.setId("resetButton");
        resetButton.setOnAction(event -> {
            // resetBoard();
            // resetCurrentWord();
            update();
        });

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(submitButton, resetButton);

        VBox currentWordSection = new VBox(10);
        currentWordSection.setPadding(new Insets(10));
        currentWordSection.getChildren().addAll(currentWordLabel, currentWordDisplay, buttonsBox);
        add(currentWordSection, 0, numRows + 2, numCols, 1);
    }


    /*
    * add point section
    */
    private void addPointSection() {
        totalPointsLabel = new Label("Total Points to find: " + game.getTotalPuzzlePoints());
        userPointsLabel = new Label("User Points: " + game.getUserPoints());
        bonusPointsLabel = new Label("Bonus Points: " + game.getBonusPoints());

        VBox pointSection = new VBox(10);
        pointSection.setAlignment(Pos.CENTER);
        pointSection.getChildren().addAll(userPointsLabel, bonusPointsLabel, totalPointsLabel);
        add(pointSection, 0, 1, numCols, 1); // Add the pointSection at row 1, spanning all columns
    }


    /*
     * set text for current display
     */
    private void updateCurrentWordDisplay() {
        currentWordDisplay.setText(currentWord.toString());
    }
    

    /*
     * reset the board
     */
    private void resetBoard() {
        // Enable all buttons and reset their styles
        for (Node node : getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(false);
                button.setStyle("");
            }
        }

        lastClickedButton = null; // Reset the last clicked button
    }

    /*
     * reset current word display
     */
        private void resetCurrentWord() {
            currentWord.setLength(0);
            updateCurrentWordDisplay();
        }

        /*
        * Check adjacent buttons from last clicked
        */
        private boolean isAdjacentButton(Button button) {
            if (lastClickedButton == null) {
                return true; // No button clicked yet, all buttons are considered adjacent
            }

            int lastRow = GridPane.getRowIndex(lastClickedButton);
            int lastCol = GridPane.getColumnIndex(lastClickedButton);
            int row = GridPane.getRowIndex(button);
            int col = GridPane.getColumnIndex(button);

            return (row == lastRow && Math.abs(col - lastCol) == 1) ||
                (col == lastCol && Math.abs(row - lastRow) == 1) ||
                (Math.abs(row - lastRow) == 1 && Math.abs(col - lastCol) == 1);
        }

        /*
        * check if the game is won
        */
        private void isWon() {
            if (game.getUserPoints() == game.getTotalPuzzlePoints()) {
                // Disable all the buttons
                for (Node node : getChildren()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        button.setDisable(true);
                    }
                }

                Label winLabel = new Label("YOU WON!!!");
                winLabel.setStyle("-fx-font-size: 24pt; -fx-text-fill: green;");
                int row = game.getPuzzle().getBoard().size() / 2; 
                int colSpan = game.getPuzzle().getBoard().get(0).size();
                GridPane.setHalignment(winLabel, HPos.CENTER);
                GridPane.setValignment(winLabel, VPos.CENTER);
                add(winLabel, 0, row, colSpan, 1);

                // Disable the submit and reset buttons
                Button submitButton = (Button) getScene().lookup("#submitButton");
                submitButton.setDisable(true);

                Button resetButton = (Button) getScene().lookup("#resetButton");
                resetButton.setDisable(true);
            }
        }

        private void updateButtonCounter() {
            int[][] startingTiles = game.getNumOfStartingTiles();
            //System.out.println(startingTiles);
            for (Node node : getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    String id = button.getId();
                    int row = Integer.parseInt(id.substring(6, 7)); // Extract row number from button id
                    int col = Integer.parseInt(id.substring(7, 8)); // Extract column number from button id
                    int count = startingTiles[row][col];
                    Label countLabel = (Label) button.getGraphic();
                    countLabel.setText(String.valueOf(count));
                }
            }
        }
        
        private void updateButtonDisable() {
            int[][] usedTiles = game.getNumOfUsedTiles();
        
            for (Node node : getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    String id = button.getId();
                    // Extract row number from button id
                    int row = Integer.parseInt(id.substring(6, 7));
                    // Extract column number from button id
                    int col = Integer.parseInt(id.substring(7, 8));
                    if (usedTiles[row][col] == 0) {
                        button.setDisable(true);
                    } else {
                        button.setDisable(false);
                    }
                }
            }
        }
        

        public void update() {
            resetBoard();
            resetCurrentWord();

            totalPointsLabel.setText("Total Points to find: " + game.getTotalPuzzlePoints());
            userPointsLabel.setText("User Points: " + game.getUserPoints());
            bonusPointsLabel.setText("Bonus Points: " + game.getBonusPoints());
            updateButtonCounter();
            updateButtonDisable();


        }
}


