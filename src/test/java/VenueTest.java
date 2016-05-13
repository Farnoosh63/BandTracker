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
  public void Venue_getIdCorrectly_int(){
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    assertEquals(1, myVenue.getId());
   }

 }
