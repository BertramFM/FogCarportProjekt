package dk.ek.controllers;

import dk.ek.entities.*;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class CarportController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/carport", ctx -> createTempCarport(ctx, connectionPool));

        app.post("/carport/saveFlat", ctx -> saveCarportFlat(ctx, connectionPool));
        app.post("/carport/saveHigh", ctx -> saveCarportHigh(ctx));

        app.get("/carport", ctx -> showCarportPage(ctx, connectionPool));
        app.get("/confirmation", ctx -> confirmation(ctx));
    }

    private static void confirmation(@NotNull Context ctx) {
        ctx.render("orderConfirmation.html");
    }

    private static void saveCarportFlat(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String address = ctx.formParam("address");
        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");
        String city = ctx.formParam("city");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));

        String roofMaterial = ctx.formParam("roofMaterial");
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        boolean toolShed = Boolean.parseBoolean(ctx.formParam("toolShed"));
        String note = ctx.formParam("note");

        int shedWidth = 0;
        int shedLength = 0;
        if (toolShed) {
            shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
            shedLength = Integer.parseInt(ctx.formParam("shedLength"));
        }

        int customerId;
        if (CustomerMapper.emailExists(email, connectionPool)) {
            Customer exist = CustomerMapper.getCustomerByEmail(email, connectionPool);
            customerId = exist.getId();
        } else {
            Customer customer = new Customer(firstname, lastname, address, email, phone, zipcode, city);
            customerId = CustomerMapper.createCustomer(customer, connectionPool);
        }

        ctx.sessionAttribute("customerId", customerId);

        Order order = new Order(0, customerId, roofMaterial, width, length, toolShed, shedWidth, shedLength, note, "unclaimed", null);
        int orderId = OrderMapper.createOrder(order, connectionPool);

        ctx.sessionAttribute("orderId", orderId);
        ctx.redirect("/confirmation");
    }

    private static void saveCarportHigh(Context ctx) {

        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.result("No carport found in session");
            return;
        }

        String roofMaterialCategory = (ctx.formParam("roofMaterialId"));

        int length =
                Integer.parseInt(ctx.formParam("length"));

        int width =
                Integer.parseInt(ctx.formParam("width"));


        carport.setLength(length);
        carport.setWidth(width);

        ctx.sessionAttribute("carport", carport);

        MailController.sendOrderConfirmation(ctx);

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
