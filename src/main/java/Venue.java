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

  public static List<Venue> all() {
    String sql = "SELECT id, location FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (location) VALUES (:location)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("location", this.location)
        .executeUpdate()
        .getKey();
    }
  }

@Override
  public boolean equals(Object secondVenue) {
    if(!(secondVenue instanceof Venue)){
      return false;
    }else {
      Venue newVenue = (Venue) secondVenue;
      return this.getName().equals(newVenue.getName()) &&
      this.getId() == newVenue.getId();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where id=:id";
      Venue venue = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }
}
