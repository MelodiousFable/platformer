package stickman.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.util.Duration;
import stickman.model.*;
import java.lang.Math;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameWindow {
    private final int width;
    private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private BackgroundDrawer backgroundDrawer;
    private double timerCounter;
    private Text timer;
    private Text lives;
    private double counterRound;
    private Double timeTaken = null;
    private GameEndStrategy gameEndStrategy;

    private double xViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

    public GameWindow(GameEngine model, int width, int height) {
        this.model = model;
        this.pane = new Pane();
        this.width = width;
        this.scene = new Scene(pane, width, height);
        timerCounter = 0;
        counterRound = 0;
        timer = new Text(5, 30, Double.toString(timerCounter));
        timer.setFill(Color.BLACK);
        timer.setFont(new Font(30));

        lives = new Text(280, 30, "Lives: " + Integer.toString(model.getHero().getLives()));
        lives.setFill(Color.GREEN);
        lives.setFont(new Font(30));

        this.entityViews = new ArrayList<>();

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        this.backgroundDrawer = new ParallaxBackground();

        backgroundDrawer.draw(model, pane);
        pane.getChildren().addAll(timer, lives);
    }

    public Scene getScene() {
        return this.scene;
    }

    public void run() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void draw() {
        counterRound = Math.round(timerCounter*100);
        counterRound = counterRound/100;
        chooseStrategy(model.youWin(), model.gameOver());
        if(gameEndStrategy != null) {
            gameEndStrategy.endGame();
        }
        timerCounter += 0.0085;
        model.tick();
        timer.setText(Double.toString(counterRound));
        lives.setText("Lives: " + Integer.toString(model.getHero().getLives()));

        List<Entity> entities = new ArrayList<Entity>();
        addEntities(model.getCurrentLevel().getVanillaEntities(), entities);
        addEntities(model.getCurrentLevel().getMovingEntities(), entities);
        entities.add(model.getHero());

        for (EntityView entityView: entityViews) {
            entityView.markForDelete();
        }

        double heroXPos = model.getCurrentLevel().getHeroX();
        heroXPos -= xViewportOffset;

        if (heroXPos < VIEWPORT_MARGIN && xViewportOffset >= 0) {
            xViewportOffset -= VIEWPORT_MARGIN - heroXPos;
        }
        else if (heroXPos > width - VIEWPORT_MARGIN) {
            xViewportOffset += heroXPos - (width - VIEWPORT_MARGIN);
        }
        if (xViewportOffset < 0) {
            xViewportOffset = 0;
        }

        backgroundDrawer.update(xViewportOffset);
        for (Entity entity: entities) {
            boolean notFound = true;
            for (EntityView view: entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset);
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
        }

        for (EntityView entityView: entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);
    }

    private void chooseStrategy(boolean win, boolean lose) {
        if(gameEndStrategy != null) {
            return;
        }
        if(win) {
            gameEndStrategy = new YouWinStrategy(counterRound, model.getHero().getLives(), pane);
        }
        else if(lose) {
            gameEndStrategy = new GameOverStrategy(counterRound, pane);
        }
    }

    private void addEntities(List<? extends Entity> entsToAdd, List<Entity> array) {
        for(Entity e : entsToAdd) {
            array.add(e);
        }
    }
}
