package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.Materials;
import dk.ek.entities.RoofType;
import dk.ek.persistence.CarportMapper;
import dk.ek.persistence.MaterialMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class CarportController {

    public static void addRoutes(Javalin app, CarportMapper roofService, MaterialMapper materialMapper) {
        app.get("/rooftypes", ctx -> showRoofTypes(ctx, roofService));
        app.post("/carport", ctx -> tempCarport(ctx, roofService, materialMapper));
        app.post("/carport/save", ctx -> saveCarport(ctx, roofService, materialMapper));
        app.get("/carport", ctx -> showCarportPage(ctx, materialMapper));

    }

    private static void saveCarport(Context ctx, CarportMapper carportMapper, MaterialMapper materialMapper) {

        Carport carport = ctx.sessionAttribute("carport");

        int roofMaterialId = Integer.parseInt(ctx.formParam("roofMaterialId"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int width = Integer.parseInt(ctx.formParam("width"));
        int materialId = Integer.parseInt(ctx.formParam("carportMaterialId"));

        Materials roofMaterials = materialMapper.getMaterialById(roofMaterialId);
        Materials selectedMaterials = materialMapper.getMaterialById(materialId);

        carport.setLength(length);
        carport.setWidth(width);

        carport.getRoofMaterial().add(roofMaterials);
        carport.getCarportMaterial().add(selectedMaterials);

        ctx.sessionAttribute("carport", carport);

        carportMapper.saveCarport(carport);

        ctx.render("orderConfirmation.html");
    }

    private static void showRoofTypes(Context ctx, CarportMapper roofService) {
        List<RoofType> roofTypes = roofService.getRoofTypes();
        ctx.attribute("roofTypes", roofTypes);
        ctx.render("pickRoofType.html");
    }

    private static void tempCarport(Context ctx, CarportMapper roofService, MaterialMapper materialMapper) {
        int roofTypeId = Integer.parseInt(ctx.formParam("roofTypeId"));
        RoofType roofType = roofService.getRoofTypeById(roofTypeId);
        Carport carport = new Carport();
        carport.setRoofType(roofType);
        ctx.sessionAttribute("carport", carport);
        ctx.attribute("materials", materialMapper.getAllMaterials());
        ctx.render(getCarportTemplate(carport));
    }

    private static void showCarportPage(Context ctx, MaterialMapper materialMapper) {

        Carport carport = ctx.sessionAttribute("carport");

        ctx.attribute("carport", carport);

        ctx.attribute("materials", materialMapper.getAllMaterials());

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
