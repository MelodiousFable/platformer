package stickman.view;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import stickman.model.GameEngine;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KeyboardInputHandler{
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private boolean moveStopped = true;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    private Map<String, MediaPlayer> sounds = new HashMap<>();

    public KeyboardInputHandler(GameEngine model) {
        this.model = model;

        URL mediaUrl = getClass().getResource("/jump.wav");
        String jumpURL = mediaUrl.toExternalForm();

        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        sounds.put("jump", mediaPlayer);
    }

    public void handlePressed(KeyEvent keyEvent) {
        if (pressedKeys.contains(keyEvent.getCode())) {
            return;
        }
        pressedKeys.add(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.UP) && model.jump()) {
            MediaPlayer jumpPlayer = sounds.get("jump");
            jumpPlayer.stop();
            jumpPlayer.play();
        }

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            model.moveLeft();
            left = true;
        }
        else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            model.moveRight();
            right = true;
        }

        if(left && right) {
            model.stopMoving();
            left = false;
            right = false;
        }
    }

    public void handleReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());
        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            model.moveRight();
            left = false;
            if(pressedKeys.contains(KeyCode.RIGHT)) {
               right = true;
            }
        }
        else if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            model.moveLeft();
            right = false;
            if(pressedKeys.contains(KeyCode.LEFT)) {
                left = true;
            }
        }



        if (!(right || left)) {
            model.stopMoving();
            left = false;
            right = false;
        }
    }

    public boolean getLeft() {
      return this.left;
    }

    public boolean getRight() {
      return this.right;
    }
}
