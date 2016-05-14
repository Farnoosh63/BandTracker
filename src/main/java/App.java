import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class App {
  public static void main (String[] args){
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";


    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("venues", Venue.all());
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
      Venue newVenueName = new Venue(venueName);
      newVenueName.save();
      response.redirect("/");
      return null;
    });

    get("/addBand/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band bandClicked = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", bandClicked);
      model.put("template", "templates/manageBand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addBand/:id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band bandClicked = Band.find(Integer.parseInt(request.params(":id")));
      String newNameForBand = request.queryParams("editBand");
      bandClicked.update(newNameForBand);
      model.put("bands", bandClicked);
      response.redirect("/");
      return null;
    });

    get("/addVenueToBand/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Band bandClicked = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band",bandClicked);
      model.put("allVenues", Venue.all());
      model.put("template", "templates/addVenueToBand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addVenueToBand/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String venueChecked = request.queryParams("venueId");
      Venue venue = Venue.find(Integer.parseInt(venueChecked));
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      band.addVenue(venue);
      String url = String.format("/addVenueToBand/%d", band.getId());
      response.redirect(url);
      return null;
    });

    get("/addBand/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int bandId = Integer.parseInt(request.params(":id"));;
      Band band = Band.find(bandId);
      band.delete();
      response.redirect("/");
      return null;
    });

    get("/manageVenue/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Venue venueClicked = Venue.find(Integer.parseInt(request.params(":id")));
      model.put("venue", venueClicked);
      model.put("template", "templates/manageVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/manageVenue/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venueId = Integer.parseInt(request.params(":id"));;
      Venue venue = Venue.find(venueId);
      venue.delete();
      response.redirect("/");
      return null;
    });

    get("/search", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      if(request.queryParams().contains("bandId")) {
        String bandNameSelect = request.queryParams("bandId");
        Band searchedBand = Band.find(Integer.parseInt(bandNameSelect));
        List<Venue> venueSearched = searchedBand.getVenues();
        model.put("venues", venueSearched);
      }
      model.put("allbands", Band.all());
      model.put("template", "templates/search.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
