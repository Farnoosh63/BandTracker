import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void Band_instantiatesCorrectlt_true() {
    Band myBand = new Band("The Beatles");
    assertEquals(true, myBand instanceof Band);
  }

  @Test
  public void Band_getNameCorrectly_String(){
    Band myBand = new Band("The Beatles");
    assertEquals("The Beatles", myBand.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Band myBand = new Band("The Beatles");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void Band_getIdCorrectly_int(){
    Band myBand = new Band("The Beatles");
    assertEquals(0, myBand.getId());
  }

  @Test
  public void Band_getTwoSameLocation_true(){
    Band firstBand = new Band("The Beatles");
    Band secondBand = new Band("The Beatles");
    assertTrue(secondBand.equals(firstBand));
  }

  @Test
  public void Band_getNameSaveIntoDB_true() {
    Band myBand = new Band("The Beatles");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void Band_getNamefindIntoDB_true() {
    Band myBand = new Band("The Beatles");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void Band_updateBand_String() {
    Band myBand = new Band("The Beatles");
    myBand.save();
    myBand.update("beatles");
    assertEquals("beatles", Band.find(myBand.getId()).getName());
  }

  @Test
  public void Band_DeleteBand_null() {
    Band myBand = new Band("The Beatles");
    myBand.save();
    int id = myBand.getId();
    myBand.delete();
    assertEquals(null, Band.find(id));
  }

  @Test
  public void Band_getBandNameReturnAllVenues_List() {
    Band myBand = new Band("The Beatles");
    myBand.save();
    Venue firstVenue = new Venue("Crystal Ballroom");
    firstVenue.save();
    Venue secondVenue = new Venue("Moda Center");
    secondVenue.save();
    myBand.addVenue(firstVenue);
    myBand.addVenue(secondVenue);
    List savedBand = myBand.getVenues();
    assertEquals(2, savedBand.size());
  }

}
