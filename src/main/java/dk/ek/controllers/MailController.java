package dk.ek.controllers;


import dk.ek.config.ThymeleafConfig;
import dk.ek.entities.Order;
import dk.ek.services.MailService;
import org.thymeleaf.context.Context;


public class MailController {

    public MailController() {
    }

    public static void sendOrderConfirmation(io.javalin.http.Context ctx, Order order) {

        Context thymeleafContext = new Context();

        thymeleafContext.setVariable("roofMaterial", order.getRoofMaterial());
        thymeleafContext.setVariable("roofAngle", order.getRoofAngle());
        thymeleafContext.setVariable("carportWidth", order.getCarportWidth());
        thymeleafContext.setVariable("carportLength", order.getCarportLength());
        //thymeleafContext.setVariable("carportMaterialId", ctx.formParam("carportMaterialId"));

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

        MailService.sendMail(email, "Order received", html);

        ctx.result("OK");
    }
}