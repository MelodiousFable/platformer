package game;

import game.model.GameEngineImplementation;
import javafx.application.Application;
import javafx.stage.Stage;
import game.model.GameEngine;
import game.model.GameEngineImplementation;
import game.view.GameWindow;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Map<String, String> params = getParameters().getNamed();

        String s = "Java 11 sanity check";
        if (s.isBlank()) {
            throw new IllegalStateException("You must be running Java 11+. You won't ever see this exception though" +
                    " as your code will fail to compile on Java 10 and below.");
        }
        List<String> levels = new ArrayList<String>();
        levels.add("level_2.json");
        levels.add("level_1.json");
        GameEngine model = new GameEngineImplementation(levels, 600);
        GameWindow window = new GameWindow(model, 960, 600);
        window.run();

        primaryStage.setTitle("Stickman");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
    }
}
