package dk.ek.controllers;

import dk.ek.entities.User;
import dk.ek.persistence.SellerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SellerController {

    public static void addRoutes(Javalin app, SellerMapper sellerMapper) {
        app.get("/seller", ctx -> showSellerPage(ctx, sellerMapper));
    }

    private static void showSellerPage(Context ctx, SellerMapper sellerMapper) {
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/login");
            return;
        }

        if (!"admin".equals(currentUser.getRole()) && !"sales".equals(currentUser.getRole())) {
            ctx.redirect("/");
            return;
        }

        ctx.attribute("customers", sellerMapper.getAllCustomers());
        ctx.attribute("orders", sellerMapper.getAllOrders());

        ctx.render("seller.html");
    }
}