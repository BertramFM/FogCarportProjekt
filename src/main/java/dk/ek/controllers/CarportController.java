package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.RoofType;
import dk.ek.services.CarportService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class CarportController {

    public static void addRoutes(Javalin app, CarportService roofService) {
        app.get("/rooftypes", ctx -> showRoofTypes(ctx, roofService));
        app.post("/carport", ctx -> tempCarport(ctx, roofService));
        app.post("/carport/save", ctx -> saveCarport(ctx, roofService));

    }

    private static void saveCarport(Context ctx, CarportService carportService) {
        Carport carport = ctx.sessionAttribute("carport");

        int length = Integer.parseInt(ctx.formParam("length"));
        int width = Integer.parseInt(ctx.formParam("width"));
        carport.setLength(length);
        carport.setWidth(width);
        carportService.saveCarport(carport);
        ctx.result("Carport saved");

    }

    private static void showRoofTypes(Context ctx, CarportService roofService) {
        List<RoofType> roofTypes = roofService.getRoofTypes();
        ctx.attribute("roofTypes", roofTypes);
        ctx.render("pickRoofType.html");
    }

    private static void tempCarport(Context ctx, CarportService roofService) {
        int roofTypeId = Integer.parseInt(ctx.formParam("roofTypeId"));
        RoofType roofType = roofService.getRoofTypeById(roofTypeId);
        Carport carport = new Carport();
        carport.setRoofType(roofType);
        ctx.sessionAttribute("carport", carport);
        ctx.render("carport.html");
    }
}
