package game.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;

import java.time.Duration;
import java.time.Instant;

public class SaveKeeper {
    private GameSaveState saveState;

    public void saveState(Level level, int levelId, Duration duration, int lives, double levelScore, double totalScore) {
        saveState = new GameSaveState(level, levelId, duration, lives, levelScore, totalScore);
        writeStateToFile();
    }

    public GameSaveState loadState() {
        return saveState;
    }

    @SuppressWarnings("unchecked")
    private void writeStateToFile() {
        String fileName = "saved_state.json";
        JSONObject mainObject = new JSONObject();
        JSONObject hero = new JSONObject();
        hero.put("x", saveState.getCurrentLevel().getHeroX());
        hero.put("y", saveState.getCurrentLevel().getHero().getYPos() + saveState.getCurrentLevel().getHero().getHeight());
        hero.put("xVelocity", saveState.getCurrentLevel().getHero().getXVel());
        String size = "normal";
        if(saveState.getCurrentLevel().getHero().getHeight() == 51.0)  {
            size = "normal";
        }
        else if(saveState.getCurrentLevel().getHero().getHeight() == 38.25) {
            size = "tiny";
        }
        else if(saveState.getCurrentLevel().getHero().getHeight() == 63.75) {
            size = "large";
        }
        else if(saveState.getCurrentLevel().getHero().getHeight() == 76.5) {
            size = "giant";
        }
        hero.put("size", size);
        mainObject.put("hero", hero);

        JSONArray immovableEntities = new JSONArray();
        for (Entity e : saveState.getCurrentLevel().getTangibles()) {
            String path = null;
            JSONObject ent = new JSONObject();
            if(e.getImagePath().equals("platform.png")) {
                path = "platform";
            }
            else if(e.getImagePath().equals("ice_platform.png")) {
                path = "ice_platform";
            }
            else if(e.getImagePath().equals("block.png")) {
                path = "block";
            }
            else if(e.getImagePath().equals("flag.png")) {
                path = "finish_flag";
            }
            if(path != null) {
                ent.put("type", path);
                ent.put("x", e.getXPos());
                ent.put("y", e.getYPos());
                immovableEntities.add(ent);
            }
        }
        mainObject.put("immovableEntities", immovableEntities);

        JSONArray movableEntities = new JSONArray();
        for (MovableEntity e : saveState.getCurrentLevel().getMovableEntities()) {
            String path = null;
            JSONObject ent = new JSONObject();
            if(e.getImagePath().equals("cloud_1.png")) {
                path = "cloud";
            }
            if(path != null) {
                ent.put("type", path);
                ent.put("x", e.getXPos());
                ent.put("y", e.getYPos());
                ent.put("xVelocity", e.getXVel());
                movableEntities.add(ent);
            }
        }
        mainObject.put("movableEntities", movableEntities);

        JSONArray enemies = new JSONArray();
        for (Enemy e : saveState.getCurrentLevel().getEnemies()) {
            String path = null;
            JSONObject ent = new JSONObject();
            String types = "slime[R, G, B, P, Y]";
            if(e.getImagePath().matches(types + "a.png") || e.getImagePath().matches(types + "b.png")) {
                path = "slime" + e.getImagePath().charAt(5);
            }
            if(path != null) {
                ent.put("type", path);
                ent.put("x", e.getXPos());
                enemies.add(ent);
            }
        }
        mainObject.put("enemies", enemies);

        mainObject.put("floorHeight", saveState.getCurrentLevel().getFloorHeight());
        mainObject.put("levelWidth", saveState.getCurrentLevel().getWidth());
        mainObject.put("levelHeight", saveState.getCurrentLevel().getHeight());
        mainObject.put("targetTime", saveState.getCurrentLevel().getTargetTime());

        try {
            FileWriter f = new FileWriter("src/main/resources/" + fileName);
            f.write(mainObject.toJSONString());
            f.flush();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
