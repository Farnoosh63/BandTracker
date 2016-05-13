import java.util.List;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;

public class Band {
  private int id;
  private String name;


  public Band(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object secondBand) {
    if(!(secondBand instanceof Band)){
      return false;
    }else {
      Band newBand = (Band) secondBand;
      return this.getName().equals(newBand.getName()) &&
      this.getId() == newBand.getId();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands where id=:id";
      Band band = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void update(String newBand) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE bands SET name = :name WHERE id = :id";
    con.createQuery(sql)
      .addParameter("name", newBand)
      .addParameter("id", this.id)
      .executeUpdate();
  }
}

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM bands WHERE id = :id;";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

      String joinDeleteQuery = "DELETE FROM bands_venues WHERE bandid = :bandid";
        con.createQuery(joinDeleteQuery)
          .addParameter("bandid", this.getId())
          .executeUpdate();
    }
  }

  public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (bandid, venueid) VALUES (:bandid, :venueid)";
      con.createQuery(sql)
      .addParameter("venueid", venue.getId())
      .addParameter("bandid", this.getId())
      .executeUpdate();
    }
  }

  public List<Venue> getVenue() {
    try(Connection con = DB.sql2o.open()){
     String joinQuery = "SELECT venueid FROM bands_venues WHERE bandid = :bandid";
     List<Integer> venueIds = con.createQuery(joinQuery)
       .addParameter("bandid", this.getId())
       .executeAndFetch(Integer.class);

     List<Venue> venues = new ArrayList<Venue>();

     for (Integer venueId : venueIds) {
       String venueQuery = "Select * From venues WHERE id = :venueid";
       Venue venue = con.createQuery(venueQuery)
         .addParameter("venueid", venueId)
         .executeAndFetchFirst(Venue.class);
       venues.add(venue);
     }
     return venues;
   }
 }
}
