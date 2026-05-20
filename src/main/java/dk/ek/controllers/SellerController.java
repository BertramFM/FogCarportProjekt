package dk.ek.controllers;

import dk.ek.entities.Employee;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.ConnectionPool;
import dk.ek.persistence.CustomerMapper;
import dk.ek.persistence.EmployeeMapper;
import dk.ek.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SellerController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/seller", ctx -> showSellerPage(ctx, connectionPool));
    }

    private static void showSellerPage(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Employee currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/login");
            return;
        }

        if (!"admin".equals(currentUser.getRole()) && !"sales".equals(currentUser.getRole())) {
            ctx.redirect("/");
            return;
        }

        ctx.attribute("customers", CustomerMapper.getAllCustomers(connectionPool));
        ctx.attribute("orders", OrderMapper.getAllOrders(connectionPool));

        ctx.render("seller.html");
    }
}