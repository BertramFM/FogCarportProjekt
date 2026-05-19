package dk.ek.controllers;


import dk.ek.persistence.EmployeeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SellerController {

    public static void addRoutes(Javalin app, EmployeeMapper employeeMapper) {
        app.get("/seller", ctx -> showSellerPage(ctx, employeeMapper));
    }

    private static void showSellerPage(Context ctx, EmployeeMapper employeeMapper) {
        User currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/login");
            return;
        }

        if (!"admin".equals(currentUser.getRole()) && !"sales".equals(currentUser.getRole())) {
            ctx.redirect("/");
            return;
        }

        ctx.attribute("customers", employeeMapper.getAllCustomers());
        ctx.attribute("orders", employeeMapper.getAllOrders());

        ctx.render("seller.html");
    }
}