package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.Employee;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.*;
import dk.ek.persistence.CarportMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SellerController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/seller", ctx -> showSellerPage(ctx, connectionPool));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("/seller/draw/{id}", ctx -> showDrawing(ctx, connectionPool));
    }

    private static void showSellerPage(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        Employee currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/login");
            return;
        }

        if (!"sales".equals(currentUser.getRole())) {
            ctx.redirect("/");
            return;
        }

        ctx.attribute("customers", CustomerMapper.getAllCustomers(connectionPool));
        ctx.attribute("orders", OrderMapper.getAllOrders(connectionPool));

        ctx.render("seller.html");
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");

        Employee employee = EmployeeMapper.getEmployeeByEmail(email);

        if ("sales".equalsIgnoreCase(employee.getRole())) {
            ctx.sessionAttribute("currentUser", employee);
            ctx.redirect("/seller");
        } else {
            ctx.redirect("/");
        }


    }

    private static void showDrawing(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.pathParam("id"));

        Carport order = CarportMapper.getCarportById(orderId);

        ctx.attribute("order", order);
        ctx.render("drawing.html");
    }
}