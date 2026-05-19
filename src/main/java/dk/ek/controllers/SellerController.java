package dk.ek.controllers;

import dk.ek.entities.Employee;
import dk.ek.persistence.CustomerMapper;
import dk.ek.persistence.EmployeeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SellerController {

    public static void addRoutes(Javalin app, EmployeeMapper employeeMapper, CustomerMapper customerMapper) {
        app.get("/seller", ctx -> showSellerPage(ctx, employeeMapper, customerMapper));
    }

    private static void showSellerPage(Context ctx, EmployeeMapper employeeMapper, CustomerMapper customerMapper) {
        Employee currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/login");
            return;
        }

        if (!"admin".equals(currentUser.getRole()) && !"sales".equals(currentUser.getRole())) {
            ctx.redirect("/");
            return;
        }

        ctx.attribute("customers", customerMapper.getAllCustomers());
        //ctx.attribute("orders", orderMapper.getAllOrders());

        ctx.render("seller.html");
    }
}