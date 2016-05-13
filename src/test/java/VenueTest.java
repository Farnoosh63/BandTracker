import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class VenueTest {

  @Rule
 public DatabaseRule database = new DatabaseRule();



 @Test
 public void Venue_instantiatesCorrectly_true(){
   Venue myVenue = new Venue("Crystal Ballroom");
   assertEquals(true, myVenue instanceof Venue);
  }

 }
