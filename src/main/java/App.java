import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;

import java.util.ArrayList;

public class App {
  public static void main (String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
    //  model.put("venues", Venue.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addBand", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String bandName = request.queryParams("name");
      Band newBandName = new Band(bandName);
      newBandName.save();
      response.redirect("/");
      return null;
    });

    post("/addVenue", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String venueName = request.queryParams("location");
      Band newVenueName = new Band(venueName);
      newVenueName.save();
      response.redirect("/");
      return null;
    });

  }
}
