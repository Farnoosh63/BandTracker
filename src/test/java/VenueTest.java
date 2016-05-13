import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class VenueTest {

  @Rule
 public DatabaseRule database = new DatabaseRule();


 @Test
 public void Venue_instantiatesCorrectly_true(){
   Venue myVenue = new Venue("Crystal Ballroom");
   assertEquals(true, myVenue instanceof Venue);
  }

 @Test
 public void Venue_getNameCorrectly_String(){
   Venue myVenue = new Venue("Crystal Ballroom");
   assertEquals("Crystal Ballroom", myVenue.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void Venue_getIdCorrectly_int(){
    Venue myVenue = new Venue("Crystal Ballroom");
    assertEquals(0, myVenue.getId());
  }

 @Test
  public void Venue_getTwoSameLocation_true(){
    Venue firstVenue = new Venue("Crystal Ballroom");
    Venue secondVenue = new Venue("Crystal Ballroom");
    assertTrue(secondVenue.equals(firstVenue));
  }

  @Test
  public void Venue_getNameSaveIntoDB_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void Venue_getNamefindIntoDB_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void Venue_updateVenue_String() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    myVenue.update("crystal ballroom");
    assertEquals("crystal ballroom", Venue.find(myVenue.getId()).getName());
  }

  @Test
  public void Venue_DeleteVenue_null() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    int id = myVenue.getId();
    myVenue.delete();
    assertEquals(null, Venue.find(id));
  }

  @Test
  public void Venue_addBand_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    Band myBand = new Band("The Beatles");
    myBand.save();
    myVenue.addBand(myBand);
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(savedVenue.equals(myVenue));
  }


 }
