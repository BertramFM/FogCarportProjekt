package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.Material;
import dk.ek.entities.RoofType;
import dk.ek.services.CarportService;
import dk.ek.services.MaterialService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class CarportController {

    public static void addRoutes(Javalin app, CarportService roofService, MaterialService materialService) {
        app.get("/rooftypes", ctx -> showRoofTypes(ctx, roofService));
        app.post("/carport", ctx -> tempCarport(ctx, roofService, materialService));
        app.post("/carport/save", ctx -> saveCarport(ctx, roofService, materialService));
        app.get("/carport", ctx -> showCarportPage(ctx, materialService));

    }

    private static void saveCarport(Context ctx, CarportService carportService, MaterialService materialService) {

        Carport carport = ctx.sessionAttribute("carport");

        int roofMaterialId = Integer.parseInt(ctx.formParam("roofMaterialId"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int width = Integer.parseInt(ctx.formParam("width"));
        int materialId = Integer.parseInt(ctx.formParam("carportMaterialId"));

        Material roofMaterial = materialService.getMaterialById(roofMaterialId);
        Material selectedMaterial = materialService.getMaterialById(materialId);

        carport.setLength(length);
        carport.setWidth(width);

        carport.getRoofMaterial().add(roofMaterial);
        carport.getCarportMaterial().add(selectedMaterial);

        ctx.sessionAttribute("carport", carport);

        carportService.saveCarport(carport);

        ctx.render("orderConfirmation.html");
    }

    private static void showRoofTypes(Context ctx, CarportService roofService) {
        List<RoofType> roofTypes = roofService.getRoofTypes();
        ctx.attribute("roofTypes", roofTypes);
        ctx.render("pickRoofType.html");
    }

    private static void tempCarport(Context ctx, CarportService roofService, MaterialService materialService) {
        int roofTypeId = Integer.parseInt(ctx.formParam("roofTypeId"));
        RoofType roofType = roofService.getRoofTypeById(roofTypeId);
        Carport carport = new Carport();
        carport.setRoofType(roofType);
        ctx.sessionAttribute("carport", carport);
        ctx.attribute("materials", materialService.getAllMaterials());
        ctx.render(getCarportTemplate(carport));
    }

    private static void showCarportPage(Context ctx, MaterialService materialService) {

        Carport carport = ctx.sessionAttribute("carport");

        ctx.attribute("carport", carport);

        ctx.attribute("materials", materialService.getAllMaterials());

        ctx.render(getCarportTemplate(carport));
    }

    private static String getCarportTemplate(Carport carport){
        int roofTypeId = carport.getRoofType().getId();

        if (roofTypeId == 1){
            return "flatRoof.html";
        }

        return "highRoof.html";
    }
}
