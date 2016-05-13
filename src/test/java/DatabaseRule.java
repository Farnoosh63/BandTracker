import org.junit.rules.ExternalResource;
import org.sql2o.*;

  public class DatabaseRule extends ExternalResource {

    @Override
    protected void before() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_tracker_test", null, null);
    }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteVenuesQuery = "DELETE FROM venues *;";
      String deleteBandsQuery = "DELETE FROM bands *;";
      String deleteBandsVenuesQuery = "DELETE FROM bands_venues *;";
      con.createQuery(deleteVenuesQuery).executeUpdate();
      con.createQuery(deleteBandsQuery).executeUpdate();
      con.createQuery(deleteBandsVenuesQuery).executeUpdate();
    }
  }
}
