import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Venue {
  private int id;
  private String location;


  public Venue(String location){
    this.location = location;
  }

  public String getName() {
    return location;
  }

  public int getId() {
    return id;
  }

  public static List<Venue>all() {
    String sql = "SELECT id, location FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }
}
