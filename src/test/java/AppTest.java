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
    assertThat(pageSource()).contains("Manage your Band Account");
  }

}
