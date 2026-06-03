package dk.ek.controllers;


import dk.ek.config.ThymeleafConfig;
import dk.ek.entities.Order;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.ConnectionPool;
import dk.ek.persistence.CustomerMapper;
import dk.ek.persistence.OrderMapper;
import dk.ek.services.MailService;
import io.javalin.Javalin;
import org.thymeleaf.context.Context;


public class MailController {

    public MailController() {
    }

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/completeOrder", ctx -> completeOrder(ctx, connectionPool));
    }

    public static void sendOrderConfirmation(io.javalin.http.Context ctx, Order order) {

        Context thymeleafContext = new Context();

        thymeleafContext.setVariable("roofMaterial", order.getRoofMaterial());
        thymeleafContext.setVariable("roofAngle", order.getRoofAngle());
        thymeleafContext.setVariable("carportWidth", order.getCarportWidth());
        thymeleafContext.setVariable("carportLength", order.getCarportLength());

        thymeleafContext.setVariable("toolShed", order.getHasToolShed());
        thymeleafContext.setVariable("shedWidth", order.getShedWidth());
        thymeleafContext.setVariable("shedLength", order.getShedLength());

        thymeleafContext.setVariable("note", order.getNote());

        thymeleafContext.setVariable("firstname", ctx.formParam("firstname"));
        thymeleafContext.setVariable("lastname", ctx.formParam("lastname"));
        thymeleafContext.setVariable("address", ctx.formParam("address"));
        thymeleafContext.setVariable("zipcode", ctx.formParam("zipcode"));
        thymeleafContext.setVariable("city", ctx.formParam("city"));
        thymeleafContext.setVariable("email", ctx.formParam("email"));
        thymeleafContext.setVariable("phone", ctx.formParam("phone"));

        String email = ctx.formParam("email");

        String html = ThymeleafConfig.render("/mail/orderConfirmation", thymeleafContext);

        MailService.sendMail(email, "Din ordre er modtaget", html);

        ctx.result("OK"); 
    }

    public static void sendOrderMail(io.javalin.http.Context ctx, int orderId, ConnectionPool connectionPool) throws DatabaseException {
        Context thymeleafContext = new Context();

        String email = CustomerMapper.getEmailByOrderId(orderId, connectionPool);

        String html = ThymeleafConfig.render("/mail/orderMail", thymeleafContext);

        MailService.sendMail(email, "Din ordre er klar til betaling", html);

        ctx.result("OK");
    }

    public static void completeOrder(io.javalin.http.Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        OrderMapper.updateStatus(orderId, "completed", connectionPool);

        ctx.render("paidFeedback.html");
    }
}