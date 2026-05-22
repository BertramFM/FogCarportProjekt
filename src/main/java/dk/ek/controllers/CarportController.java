package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.Materials;
import dk.ek.entities.RoofType;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.CarportMapper;
import dk.ek.persistence.ConnectionPool;
import dk.ek.persistence.MaterialMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;


public class CarportController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/carport", ctx -> createTempCarport(ctx, connectionPool));

        app.post("/carport/save", ctx -> saveCarport(ctx, connectionPool));

        app.get("/carport", ctx -> showCarportPage(ctx, connectionPool));
    }

    private static void saveCarport(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.result("No carport found in session");
            return;
        }

        int roofMaterialId =
                Integer.parseInt(ctx.formParam("roofMaterialId"));

        int length =
                Integer.parseInt(ctx.formParam("length"));

        int width =
                Integer.parseInt(ctx.formParam("width"));


        carport.setLength(length);
        carport.setWidth(width);

        ctx.sessionAttribute("carport", carport);

        ctx.render("orderConfirmation.html");
    }


    private static void createTempCarport(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String roofTypeName = ctx.formParam("roofType");
        Carport carport = new Carport();
        RoofType roofType = new RoofType(0, roofTypeName);
        carport.setRoofType(roofType);
        List<Materials> allMaterials = MaterialMapper.getAllMaterials(connectionPool);

        ctx.sessionAttribute("carport", carport);
        ctx.attribute("materials", allMaterials);
        ctx.render(getCarportTemplate(carport));

    }

    private static void showCarportPage(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        List<Materials> allMaterials = MaterialMapper.getAllMaterials(connectionPool);
        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.result("No carport found");
            return;
        }

        ctx.attribute("carport", carport);

        ctx.attribute("materials", allMaterials);

        ctx.render(getCarportTemplate(carport));
    }

    private static String getCarportTemplate(Carport carport) {
        String roofType = carport.getRoofType().getName();

        if (roofType.equalsIgnoreCase("flat")) {
            return "flatRoof.html";
        }

        return "highRoof.html";
    }
}
