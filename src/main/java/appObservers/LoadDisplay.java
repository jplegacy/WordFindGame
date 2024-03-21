package appObservers;
import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import run.WordFindApp;

public class LoadDisplay implements AppObserver{

    private Stage display;

    public LoadDisplay(WordFindApp app){

        display = new Stage();
        display.setTitle("Load a puzzle txt file");

        Group root = new Group();
        Button load = new Button("Load");
        load.setOnAction(event -> {
            FileChooser chooseFile = new FileChooser();
            File file = chooseFile.showOpenDialog(display);
            if (file != null) {
                String fileAsString = file.toString();
                app.restart(fileAsString);
            }

        });

        root.getChildren().add(load);
        VBox vbox = new VBox(root);
        vbox.setAlignment(Pos.CENTER);

        display.setScene(new Scene(vbox, 100, 100));
        display.show();
    }

    public void close(){
        display.close();
    }

}