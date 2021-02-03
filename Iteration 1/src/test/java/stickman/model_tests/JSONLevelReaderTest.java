import stickman.model.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class JSONLevelReaderTest {
  @Test
  public void testStandardFile() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/standardFile.json");
    //Testing a file with no issues at all
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testImproperHeroSizeFile() {
    //Testing a file where the hero size is not small, normal, large or giant
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/improperHeroSize.json");
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testSmallHeroSizeFile() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/heroSizeSmall.json");
    //Testing a file where the hero size is small
    reader.read();
    assertTrue(reader.getHeroHeight() == 17);
    assertTrue(reader.getHeroWidth() == 10);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testLargeHeroSizeFile() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/heroSizeLarge.json");
    //Testing a file where the hero size is large
    reader.read();
    assertTrue(reader.getHeroHeight() == 68);
    assertTrue(reader.getHeroWidth() == 40);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testGiantHeroSizeFile() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/heroSizeGiant.json");
    //Testing a file where the hero size is giant
    reader.read();
    assertTrue(reader.getHeroHeight() == 136);
    assertTrue(reader.getHeroWidth() == 80);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testNonDoubleFloorHeight() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/nonDoubleFloorHeight.json");
    //Testing a file where the floor height is the wrong data type
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testFloorHeightGreaterThanLevelHeight() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/floorHeightGreaterThanLevelHeight.json");
    //Testing a file where the floor height is greater than the level height - ie,
    //the floor is taller than the level.
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 200.0);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 120.0);
  }

  @Test
  public void testFloorHeightLessThan0() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/floorHeightLessThan0.json");
    //Testing a file where the floor height is less than 0
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 200.0);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 120.0);
  }

  @Test
  public void testLevelHeightLessThan0() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/levelHeightLessThan0.json");
    //Testing a file where the level height is less than 0
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testNonDoubleLevelHeight() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/nonDoubleLevelHeight.json");
    //Testing a file where the level height is the wrong data type
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testLevelWidthLessThan0() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/levelWidthLessThan0.json");
    //Testing a file where the level width is less than 0
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 640.0);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testNonDoubleLevelWidth() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/nonDoubleLevelWidth.json");
    //Testing a file where the level width is the wrong data type
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 640.0);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testNonDoubleStartX() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/nonDoubleStartX.json");
    //Testing a file where the hero's start position is the wrong data type
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testStartXGreaterThanLevelWidth() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/startXGreaterThanLevelWidth.json");
    //Testing a file where the hero's start position is greater than the level's width
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testStartXLessThan0() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/startXLessThan0.json");
    //Testing a file where the hero's start position is less than 0
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 1);
    assertTrue(reader.getMovingEnts().size() == 2);
    assertTrue(reader.getLevelHeight() == 400.0);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testNoEntities() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/noEntities.json");
    //Testing a file where there are no entities
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 0);
    assertTrue(reader.getMovingEnts().size() == 0);
    assertTrue(reader.getLevelHeight() == 400.0);
    assertTrue(reader.getLevelWidth() == 700.0);
    assertTrue(reader.getFloorHeight() == 320.0);
  }

  @Test
  public void testOnlyInvalidEntities() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/onlyInvalidEntities.json");
    //The reader should not put invalid entities into the lists.
    //All of these entities have no image file path.
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 0);
    assertTrue(reader.getMovingEnts().size() == 0);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testMoreOnlyInvalidEntities() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/moreOnlyInvalidEntities.json");
    //The reader should not put invalid entities into the lists.
    //All of these entities have invalid x and y coordinates, and invalid heights and widths.
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 0);
    assertTrue(reader.getMovingEnts().size() == 0);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testEntitiesWithNoData() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/entitiesWithNoData.json");
    //The reader should not put invalid entities into the lists.
    //All of these entities have no data at all.
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 0);
    assertTrue(reader.getMovingEnts().size() == 0);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 700);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testJsonWithNoData() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/jsonWithNoData.json");
    //This file has no data at all.
    //The reader should assign the default level layout.
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 0);
    assertTrue(reader.getMovingEnts().size() == 0);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 640);
    assertTrue(reader.getFloorHeight() == 320);
  }

  @Test
  public void testJsonSomeDataMissing() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/someDataMissing.json");
    //This file has some data missing.
    //Where data is missing, the reader should assign default values.
    reader.read();
    assertTrue(reader.getHeroHeight() == 136);
    assertTrue(reader.getHeroWidth() == 80);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 0);
    assertTrue(reader.getMovingEnts().size() == 1);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 1080);
    assertTrue(reader.getFloorHeight() == 290);
  }

  @Test
  public void testFileDoesNotExist() {
    JSONLevelReader reader = new JSONLevelReader("testing-jsons/thisFileDoesNotExist.json");
    //This file does not exist.
    reader.read();
    assertTrue(reader.getHeroHeight() == 34);
    assertTrue(reader.getHeroWidth() == 20);
    assertTrue(reader.getStartXPos() == 20.0);
    assertTrue(reader.getVanillaEnts().size() == 0);
    assertTrue(reader.getMovingEnts().size() == 0);
    assertTrue(reader.getLevelHeight() == 400);
    assertTrue(reader.getLevelWidth() == 640);
    assertTrue(reader.getFloorHeight() == 320);
  }
}
