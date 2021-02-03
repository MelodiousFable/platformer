package stickman.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import stickman.model.movement_strategies.*;
import stickman.model.Entity.Layer;

public class JSONLevelReader {
  private String jsonFile;
  private double heroHeight;
  private double heroWidth;
  private double startXPos;
  private double levelWidth;
  private double levelHeight;
  private double floorHeight;
  private List<Entity> vanillaEntities;
  private List<MovingEntity> movingEntities;
  private StringConverter converter = new StringConverter();

  public JSONLevelReader(String jsonFile) {
    //Assigns default level arguments
    this.jsonFile = jsonFile;
    heroWidth = 20.0;
    heroHeight = 34.0;
    startXPos = 20.0;
    levelWidth = 640.0;
    levelHeight = 400.0;
    floorHeight = 320.0;
    movingEntities = new ArrayList<MovingEntity>();
    vanillaEntities = new ArrayList<Entity>();
  }

  public void read() {
    JSONParser parser = new JSONParser();
    FileReader reader = null;
    Object obj = null;

    try {
      reader = new FileReader(jsonFile);
    }
    catch(java.io.FileNotFoundException e) {
      System.out.println("Could not find specified level. Generating default level.");
      return;
    }

    try {
      obj = parser.parse(reader);
    }
    catch(java.io.IOException e) {
      return;
    }
    catch(org.json.simple.parser.ParseException e) {
      return;
    }

    JSONObject level = null;
    try {
      level = (JSONObject) obj;
    }
    catch(Exception e) {
      return;
    }

    String size = null;
    double xPosition = 0;

    try {
      size = (String) level.get("stickmanSize");
    }
    catch(Exception e) {
      size = "normal";
    }
    JSONObject pos = null;
    try {
      pos = (JSONObject) level.get("stickmanPos");
    }
    catch(Exception e) {
      xPosition = 20.0;
    }
    try {
      xPosition = (double) pos.get("x");
    }
    catch(Exception e) {
      xPosition = 20.0;
    }
    try {
      levelWidth = (double) level.get("levelWidth");
    }
    catch(Exception e) {
      levelWidth = 640.0;
    }
    try {
      levelHeight = (double) level.get("levelHeight");
    }
    catch(Exception e) {
      levelHeight = 400.0;
    }
    try {
      floorHeight = (double) level.get("floorHeight");
    }
    catch(Exception e) {
      floorHeight = 320.0;
    }

    if(levelHeight < 100) {
      levelHeight = 400;
    }

    if(levelWidth < 50) {
      levelWidth = 640;
    }

    if(xPosition > levelWidth || xPosition < 0) {
      xPosition = 20;
    }

    if(floorHeight > levelHeight || floorHeight < 0) {
      floorHeight = levelHeight - 80;
    }

    int h = 0;
    int w = 0;
    if(size == null) {
      h = 34;
      w = 20;
    }
    else if(size.equals("small")) {
      h = 17;
      w = 10;
    }
    else if(size.equals("large")) {
      h = 68;
      w = 40;
    }
    else if(size.equals("giant")) {
      h = 136;
      w = 80;
    }
    else {
      h = 34;
      w = 20;
    }

    this.heroHeight = h;
    this.heroWidth = w;
    this.startXPos = xPosition;

    JSONArray prefEntities = null;
    try {
      prefEntities = (JSONArray) level.get("entities");
    }
    catch(Exception e) {
      ;
    }
    if(prefEntities == null) {
      return;
    }

    for(Object ob : prefEntities) {
      boolean noErrors = true;
      JSONObject jObj = null;
      String pathName = null;
      double xCoord = 0.0;
      double yCoord = 0.0;
      double height = 0.0;
      double width = 0.0;
      Layer trueLayer = null;
      boolean hostile = false;
      MoveStrategy trueMovement = null;
      if(ob instanceof JSONObject) {
        try {
          jObj = (JSONObject) ob;
          pathName = (String) jObj.get("path");
          xCoord = (double) jObj.get("x");
          yCoord = (double) jObj.get("y");
          height = (double) jObj.get("height");
          width = (double) jObj.get("width");
          String layer = (String) jObj.get("layer");
          trueLayer = converter.makeLayer(layer);
          hostile = (boolean) jObj.get("is_hostile");
          String movement = (String) jObj.get("movement");
          trueMovement = converter.makeStrategy(movement);
        }
        catch(java.lang.ClassCastException e) {
          noErrors = false;
        }
        catch(java.lang.NullPointerException e) {
          noErrors = false;
        }
        if(xCoord > 0 && yCoord > 0 && xCoord < levelWidth && yCoord < levelHeight
            && height > 0 && width > 0
            && noErrors) {
          if(trueMovement == null) {
            Entity entToAdd = new StationaryEntityBuilder().addImagePath(pathName)
                                                           .addXPos(xCoord)
                                                           .addYPos(yCoord)
                                                           .addHeight(height)
                                                           .addWidth(width)
                                                           .addLayer(trueLayer)
                                                           .addHostile(hostile)
                                                           .build();
            if(entToAdd != null) {
              vanillaEntities.add(entToAdd);
            }
          }
          else {
            MovingEntity entToAdd = new MovingEntityBuilder().addImagePath(pathName)
                                                             .addXPos(xCoord)
                                                             .addYPos(yCoord)
                                                             .addHeight(height)
                                                             .addWidth(width)
                                                             .addLayer(trueLayer)
                                                             .addStrategy(trueMovement)
                                                             .addHostile(hostile)
                                                             .build();
            if(entToAdd != null) {
              movingEntities.add(entToAdd);
            }
          }
        }
      }
    }
  }

  public double getHeroHeight() {
    return heroHeight;
  }
  public double getHeroWidth() {
    return heroWidth;
  }
  public double getStartXPos() {
    return startXPos;
  }
  public List<Entity> getVanillaEnts() {
    return vanillaEntities;
  }
  public List<MovingEntity> getMovingEnts() {
    return movingEntities;
  }
  public double getLevelHeight() {
    return levelHeight;
  }
  public double getLevelWidth() {
    return levelWidth;
  }
  public double getFloorHeight() {
    return floorHeight;
  }
}
