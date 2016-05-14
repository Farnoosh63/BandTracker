import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Brand Tracker App");
  }

  @Test
  public void addBand() {
    goTo("http://localhost:4567/");
    fill("#name").with ("The Beatles");
    submit("#addBand");
    String url = String.format("http://localhost:4567/");
    goTo(url);
    assertThat(pageSource()).contains("The Beatles");
  }

  @Test
  public void addVenue() {
    goTo("http://localhost:4567/");
    fill("#location").with ("Crystal Ballroom");
    submit("#addVenue");
    String url = String.format("http://localhost:4567/");
    goTo(url);
    assertThat(pageSource()).contains("Crystal Ballroom");
  }

  @Test
  public void manageBandPage() {
    Band bandName = new Band ("The Beatles");
    bandName.save();
    String url = String.format("http://localhost:4567/addBand/%d", bandName.getId());
    goTo(url);
    assertThat(pageSource()).contains("Changing your band name is so easy");
  }

  @Test
  public void editBandName() {
    Band bandName = new Band ("The Beatles");
    bandName.save();
    String url = String.format("http://localhost:4567/addBand/%d", bandName.getId());
    goTo(url);
    fill("#editBand").with ("Empire of the sun");
    submit("#editBandName");
    String listBands = String.format("http://localhost:4567/");
    goTo(listBands);
    assertThat(pageSource()).contains("Empire of the sun");
  }

  @Test
  public void addVenueToBandPage() {
    Band bandName = new Band ("The Beatles");
    bandName.save();
    Venue venueName = new Venue ("Crystal Ballroom");
    venueName.save();
    String url = String.format("http://localhost:4567/addBand/%d", bandName.getId());
    goTo(url);
    click("#addVenueToBand");
    String addVenueToBand = String.format("http://localhost:4567/addVenueToBand/%d", bandName.getId());
    goTo(addVenueToBand);
    assertThat(pageSource()).contains("Crystal Ballroom");
  }

  @Test
  public void addVenueToBandPost() {
    Band bandName = new Band ("The Beatles");
    bandName.save();
    Venue venueName = new Venue ("Crystal Ballroom");
    venueName.save();
    String url = String.format("http://localhost:4567/addBand/%d", bandName.getId());
    goTo(url);
    click("#addVenueToBand");
    String addVenueToBand = String.format("http://localhost:4567/addVenueToBand/%d", bandName.getId());
    goTo(addVenueToBand);
    find("#venueId").click();
    String addVenueToBandchecked = String.format("http://localhost:4567/addVenueToBand/%d", bandName.getId());
    goTo(addVenueToBandchecked);
    assertThat(pageSource()).contains("Crystal Ballroom");

  }



}
