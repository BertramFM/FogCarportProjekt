package dk.ek.controllers;

import dk.ek.entities.Employee;
import dk.ek.persistence.EmployeeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {

    public static void addRoutes(Javalin app, EmployeeMapper employeeMapper) {

        app.get("/login", ctx -> showLogin(ctx));
        app.post("/login", ctx -> login(ctx,employeeMapper ));
        app.get("/logout", ctx -> logout(ctx));
        app.get("/registerUser", ctx -> showRegister(ctx));
    }

    private static void showLogin(Context ctx) {
        ctx.attribute("activeTab", "login");
        ctx.render("login");
    }

    private static void showRegister(Context ctx) {
        ctx.attribute("activeTab", "register");
        ctx.render("login");
    }

    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    private static void login(Context ctx, EmployeeMapper employeeMapper) {

        String email = ctx.formParam("email");

        if (email == null || email.trim().isEmpty()) {
            ctx.attribute("message", "Email skal udfyldes");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
            return;
        }

        Employee employee = employeeMapper.getEmployeeByEmail(email.trim());

        if (employee != null) {
            ctx.sessionAttribute("currentUser", employee);

            if ("Sales".equals(employee.getRole())) {
                ctx.render("admin.html");
            } else {
                ctx.redirect("/");
            }

        } else {
            ctx.attribute("message", "Bruger findes ikke");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
        }
    }
}