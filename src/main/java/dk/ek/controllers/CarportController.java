package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.Material;
import dk.ek.entities.RoofType;
import dk.ek.persistence.CarportMapper;
import dk.ek.persistence.MaterialMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class CarportController {

    public static void addRoutes(Javalin app, CarportMapper carportMapper, MaterialMapper materialMapper) {
        app.post("/carport", ctx ->
                createTempCarport(ctx, materialMapper));

        app.post("/carport/save", ctx ->
                saveCarport(ctx, carportMapper, materialMapper));

        app.get("/carport", ctx ->
                showCarportPage(ctx, materialMapper));

    }

    private static void saveCarport(
            Context ctx,
            CarportMapper carportMapper,
            MaterialMapper materialMapper
    ) {

        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.result("No carport found in session");
            return;
        }

        int roofMaterialId =
                Integer.parseInt(ctx.formParam("roofMaterialId"));

        int carportMaterialId =
                Integer.parseInt(ctx.formParam("carportMaterialId"));

        int length =
                Integer.parseInt(ctx.formParam("length"));

        int width =
                Integer.parseInt(ctx.formParam("width"));

        Material roofMaterial =
                materialMapper.getMaterialById(roofMaterialId);

        Material carportMaterial =
                materialMapper.getMaterialById(carportMaterialId);

        carport.setLength(length);
        carport.setWidth(width);

        carport.getRoofMaterial().add(roofMaterial);
        carport.getCarportMaterial().add(carportMaterial);

        ctx.sessionAttribute("carport", carport);

        carportMapper.saveCarport(carport);

        ctx.render("orderConfirmation.html");
    }


    private static void createTempCarport(Context ctx, MaterialMapper materialMapper) {
        String roofTypeName = ctx.formParam("roofType");
        Carport carport = new Carport();
        RoofType roofType = new RoofType(0, roofTypeName);
        carport.setRoofType(roofType);
        ctx.sessionAttribute("carport", carport);
        ctx.attribute("materials", materialMapper.getAllMaterials());
        ctx.render(getCarportTemplate(carport));

    }

    private static void showCarportPage(
            Context ctx,
            MaterialMapper materialMapper
    ) {

        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.result("No carport found");
            return;
        }

        ctx.attribute("carport", carport);

        ctx.attribute(
                "materials",
                materialMapper.getAllMaterials()
        );

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
