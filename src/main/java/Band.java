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
}
