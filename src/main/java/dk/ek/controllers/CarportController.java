package dk.ek.controllers;

import dk.ek.entities.*;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.*;
import dk.ek.services.MaterialCalculatorService;
import dk.ek.services.CarportSvg;
import dk.ek.services.BillOfMaterialsService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class CarportController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/carport", ctx -> createTempCarport(ctx, connectionPool));

        app.post("/carport/saveFlat", ctx -> saveCarportFlat(ctx, connectionPool));
        app.post("/carport/saveHigh", ctx -> saveCarportHigh(ctx, connectionPool));

        app.get("/carport", ctx -> showCarportPage(ctx, connectionPool));
        app.get("/confirmation", ctx -> confirmation(ctx));
        app.get("/drawing", ctx -> showDrawing(ctx, connectionPool));
    }

    private static void confirmation(@NotNull Context ctx) {
        ctx.render("orderConfirmation.html");
    }

    private static void saveCarportFlat(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        // === Carport Variables ===
        String roofMaterial = ctx.formParam("roofMaterial");
        int carportWidth = Integer.parseInt(ctx.formParam("carportWidth"));
        int carportLength = Integer.parseInt(ctx.formParam("carportLength"));
        boolean toolShed = Boolean.parseBoolean(ctx.formParam("toolShed"));
        int roofAngle = 0;

        int shedWidth = 0;
        int shedLength = 0;

        if (toolShed) {
            shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
            shedLength = Integer.parseInt(ctx.formParam("shedLength"));
        }

        String note = ctx.formParam("note");

        // === Customer Variables ===
        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String address = ctx.formParam("address");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        String city = ctx.formParam("city");
        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");
        String password = ctx.formParam("password");

        int customerId;

        if (CustomerMapper.emailExists(email, connectionPool)) {
            Customer exist = CustomerMapper.getCustomerByEmail(email, connectionPool);
            customerId = exist.getId();
        } else {
            Customer customer = new Customer(firstname, lastname, address, email, phone, zipcode, city, password);
            customerId = CustomerMapper.createCustomer(customer, connectionPool);
        }

        ctx.sessionAttribute("customerId", customerId);

        Order order = new Order(0, customerId, 0, roofMaterial, roofAngle, carportWidth, carportLength, toolShed, shedWidth, shedLength, note, "unclaimed", null);
        int orderId = OrderMapper.createOrder(order, connectionPool);

        // Get bill of materials
        BillOfMaterialsService.calculateFlatCarportMaterialList(order, connectionPool);

        ctx.sessionAttribute("orderId", orderId);

        // Send mail to customer
        //MailController.sendOrderConfirmation(ctx, order);

        ctx.redirect("/confirmation");
    }

    private static void saveCarportHigh(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        // === Carport Variables ===
        String roofMaterial = ctx.formParam("roofMaterial");
        int roofAngle = Integer.parseInt(ctx.formParam("roofAngle"));
        int carportWidth = Integer.parseInt(ctx.formParam("carportWidth"));
        int carportLength = Integer.parseInt(ctx.formParam("carportLength"));
        boolean toolShed = Boolean.parseBoolean(ctx.formParam("toolShed"));

        int shedWidth = 0;
        int shedLength = 0;

        if (toolShed) {
           shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
           shedLength = Integer.parseInt(ctx.formParam("shedLength"));
        }

        String note = ctx.formParam("note");

        // === Customer Variables ===
        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String address = ctx.formParam("address");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        String city = ctx.formParam("city");
        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");
        String password = ctx.formParam("password");

        int customerId;

        if (CustomerMapper.emailExists(email, connectionPool)) {
            Customer exist = CustomerMapper.getCustomerByEmail(email, connectionPool);
            customerId = exist.getId();
        } else {
            Customer customer = new Customer(firstname, lastname, address, email, phone, zipcode, city, password);
            customerId = CustomerMapper.createCustomer(customer, connectionPool);
        }

        ctx.sessionAttribute("customerId", customerId);

        Order order = new Order(0, customerId, 0, roofMaterial, roofAngle, carportWidth, carportLength, toolShed, shedWidth, shedLength, note, "unclaimed", null);
        int orderId = OrderMapper.createOrder(order, connectionPool);

        ctx.sessionAttribute("orderId", orderId);

        MailController.sendOrderConfirmation(ctx, order);

        ctx.redirect("/confirmation");
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

    private static void showDrawing(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
//        int orderId = ctx.sessionAttribute("orderId");
        int orderId = 1;
        Order order = OrderMapper.getOrderById(orderId, connectionPool);

        String svg = CarportSvg.generateCarportSvg(order, connectionPool);
//        List<BOMLine> bom = CalcService.calcMaterials(order.getCarportWidth(), order.getCarportLength());


        ctx.attribute("svg", svg);
//        ctx.attribute("bom", bom);
        ctx.render("showDrawing.html");
    }
}