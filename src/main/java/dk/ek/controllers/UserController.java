package dk.ek.controllers;

import dk.ek.entities.Employee;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.ConnectionPool;
import dk.ek.persistence.CustomerMapper;
import dk.ek.persistence.EmployeeMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.get("/login", ctx -> showLogin(ctx));
        app.post("/login", ctx -> login(ctx, connectionPool ));
        app.get("/logout", ctx -> logout(ctx));

        app.get("/registerUser", ctx -> showRegister(ctx));
//        app.post("/registerUser", ctx -> register(ctx, connectionPool));
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

    private static void login(Context ctx, ConnectionPool connectionPool) {

        String email = ctx.formParam("email");

        if (email == null || email.trim().isEmpty()) {
            ctx.attribute("message", "Email skal udfyldes");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
            return;
        }

        Employee employee = EmployeeMapper.getEmployeeByEmail(email.trim(), connectionPool);

        if (employee != null) {
            ctx.sessionAttribute("currentUser", employee);

            if ("Sales".equals(employee.getRole())) {
                ctx.render("seller.html");
            } else {
                ctx.redirect("/");
            }
        } else {
            ctx.attribute("message", "Forkert mail, brugernavn eller kodeord");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
        }
    }

//    private static void register(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
//
//        String email = ctx.formParam("email");
//        String password = ctx.formParam("password");
//
//
//        if (email == null || password == null ||
//                email.trim().isEmpty() || password.trim().isEmpty()) {
//            ctx.attribute("message", "Alle felter skal udfyldes");
//            ctx.attribute("activeTab", "register");
//            ctx.render("login");
//            return;
//        }
//
//        if(CustomerMapper.emailExists(email, connectionPool)){
//            ctx.attribute("message", "Email findes allerede. Prøv igen");
//            ctx.attribute("activeTab", "register");
//            ctx.render("login");
//            return;
//        }
//
//
//        String trimEmail = email.trim();
//        String trimPassword = password.trim();
//
//
//        boolean created = CustomerMapper.createCustomer(trimEmail, trimPassword);
//
//        if (created) {
//            ctx.attribute("message", "Bruger oprettet! Du kan nu logge ind.");
//            ctx.attribute("activeTab", "login");
//            ctx.render("login");
//        } else {
//            ctx.attribute("message", "Brugeren findes allerede");
//            ctx.attribute("activeTab", "register");
//            ctx.render("login");
//        }
//    }
}