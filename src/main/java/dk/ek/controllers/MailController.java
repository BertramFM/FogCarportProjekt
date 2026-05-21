package dk.ek.controllers;


import dk.ek.config.ThymeleafConfig;
import dk.ek.services.MailService;
import io.javalin.Javalin;
import org.thymeleaf.context.Context;


public class MailController {

    public MailController() {
    }

    public static void sendOrderConfirmation(io.javalin.http.Context ctx) {

        Context thymeleafContext = new Context();

        thymeleafContext.setVariable("roofMaterialId", ctx.formParam("roofMaterialId"));
        thymeleafContext.setVariable("roofAngle", ctx.formParam("roofAngle"));
        thymeleafContext.setVariable("width", ctx.formParam("width"));
        thymeleafContext.setVariable("length", ctx.formParam("length"));
        thymeleafContext.setVariable("carportMaterialId", ctx.formParam("carportMaterialId"));

        thymeleafContext.setVariable("toolShed", ctx.formParam("toolShed"));
        thymeleafContext.setVariable("shedWidth", ctx.formParam("shedWidth"));
        thymeleafContext.setVariable("shedLength", ctx.formParam("shedLength"));

        thymeleafContext.setVariable("note", ctx.formParam("note"));

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