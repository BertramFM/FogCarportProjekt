package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.Employee;
import dk.ek.persistence.CarportMapper;
import dk.ek.persistence.CustomerMapper;
import dk.ek.persistence.EmployeeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SellerController {

    public static void addRoutes(Javalin app, CarportMapper carportMapper, EmployeeMapper employeeMapper, CustomerMapper customerMapper) {
        app.get("/seller", ctx -> showSellerPage(ctx, carportMapper, customerMapper));
        app.post("/login", ctx -> login(ctx, employeeMapper));
        app.get("/seller/draw/{id}", ctx -> showDrawing(ctx, carportMapper));
    }

    private static void showDrawing(Context ctx, CarportMapper carportMapper) {
        int orderId = Integer.parseInt(ctx.pathParam("id"));

        Carport order = carportMapper.getCarportById(orderId);

        ctx.attribute("order", order);
        ctx.render("drawing.html");
    }

    private static void login(Context ctx, EmployeeMapper employeeMapper) {
        String email = ctx.formParam("email");

        Employee employee = employeeMapper.getEmployeeByEmail(email);

        if ("sales".equalsIgnoreCase(employee.getRole())) {
            ctx.sessionAttribute("currentUser", employee);
            ctx.redirect("/seller");
        } else {
            ctx.redirect("/");
        }


    }

    private static void showSellerPage(Context ctx, CarportMapper carportMapper, CustomerMapper customerMapper) {
        Employee currentUser = ctx.sessionAttribute("currentUser");

        if (currentUser == null) {
            ctx.redirect("/login");
            return;
        }

        if (!"sales".equals(currentUser.getRole())) {
            ctx.redirect("/");
            return;
        }

        ctx.attribute("carport", customerMapper.getAllCustomers());
        System.out.println(carportMapper.getAllOrders());
        ctx.attribute("orders", carportMapper.getAllOrders());


        ctx.render("/seller.html");
    }
}