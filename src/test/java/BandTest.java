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
   public void Band_getIdCorrectly_int(){
     Band myBand = new Band("The Beatles");
     myBand.save();
     assertEquals(1, myBand.getId());
    }

}
