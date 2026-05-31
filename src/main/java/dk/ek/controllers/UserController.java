package dk.ek.controllers;

import dk.ek.entities.Customer;
import dk.ek.entities.Employee;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.ConnectionPool;
import dk.ek.persistence.CustomerMapper;
import dk.ek.persistence.EmployeeMapper;
import dk.ek.persistence.OrderMapper;
import dk.ek.services.Validator;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;


public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.before("/userPanel", ctx -> {
            Customer customer = ctx.sessionAttribute("currentCustomer");

            if (customer == null) {
                ctx.redirect("/login");
            }
        });

        app.before("/seller", ctx -> {
            Employee employee = ctx.sessionAttribute("currentEmployee");

            if (employee == null) {
                ctx.redirect("/employeeLogin");
            }
        });

        app.get("/login", ctx -> showLogin(ctx));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("/logout", ctx -> logout(ctx));

        app.get("/employeeLogin", ctx -> showLogin(ctx));
        app.post("/employeeLogin", ctx -> employeeLogin(ctx, connectionPool));
        app.get("/seller", ctx -> showSeller(ctx, connectionPool));

        app.get("/registerUser", ctx -> showRegister(ctx));
        app.post("/registerUser", ctx -> register(ctx, connectionPool));

        app.get("/userPanel", ctx -> showUserPanel(ctx, connectionPool));
    }

    private static void showUserPanel(Context ctx, ConnectionPool connectionPool) {
        try {

            Customer customer = ctx.sessionAttribute("currentCustomer");

            int customerId = customer.getId();
            ctx.attribute("customerId", customerId);


            ctx.attribute("customerOrders", OrderMapper.getOrdersByCustomerId(customerId, connectionPool));

            ctx.render("userPage.html");

        } catch (DatabaseException e) {
            e.printStackTrace();
        }
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

    private static void login(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        if (email == null || email.trim().isEmpty()) {
            ctx.attribute("msg", "Email skal udfyldes");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
            return;
        }

        if (!CustomerMapper.emailExists(email.trim(), connectionPool)) {
            ctx.attribute("msg", "Email eller adgangskode er forkert");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
            return;
        }

        Customer customer = CustomerMapper.getCustomerByEmail(email.trim(), connectionPool);

        if (customer.getPassword() == null) {
            ctx.attribute("msg", "Du har ikke oprettet en konto. Gå til 'Registrer' og opret en konto");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
            return;
        }

        if (!BCrypt.    checkpw(password, customer.getPassword())) {
            ctx.attribute("msg", "Forkert email eller adgangskode");
            ctx.attribute("activeTab", "login");
            ctx.render("login");
            return;
        }

        ctx.sessionAttribute("currentCustomer", customer);
        ctx.redirect("/userPanel");
    }

    private static void employeeLogin(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        if (email == null || email.trim().isEmpty()) {
            ctx.attribute("msg", "Email skal udfyldes");
            ctx.attribute("activeTab", "employee");
            ctx.render("login");
            return;
        }

        if (!EmployeeMapper.emailExists(email.trim(), connectionPool)) {
            ctx.attribute("msg", "Email eller adgangskode er forkert");
            ctx.attribute("activeTab", "employee");
            ctx.render("login");
            return;
        }

        Employee employee = EmployeeMapper.getEmployeeByEmail(email.trim(), connectionPool);

        if (employee.getPassword() == null) {
            ctx.attribute("msg", "Email eller adgangskode er forkert");
            ctx.attribute("activeTab", "employee");
            ctx.render("login");
            return;
        }

        if (!password.matches(employee.getPassword())) {
            ctx.attribute("msg", "Forkert email eller adgangskode");
            ctx.attribute("activeTab", "employee");
            ctx.render("login");
            return;
        }

        ctx.sessionAttribute("currentEmployee", employee);
        ctx.redirect("/seller");
    }

    private static void showSeller(Context ctx, ConnectionPool connectionPool) {

        try {
            ctx.attribute("employees", EmployeeMapper.getAllEmployees(connectionPool));
            ctx.attribute("orders", OrderMapper.getAllOrders(connectionPool));
        } catch (DatabaseException e) {
            e.getMessage();
        }

        ctx.render("seller.html");
    }

    private static void register(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");
        String password = ctx.formParam("password");

        if (!CustomerMapper.emailExists(email, connectionPool)) {
            ctx.attribute("msg", "Email eller telefonnummer findes ikke");
            ctx.attribute("activeTab", "register");
            ctx.render("login");
            return;
        }

        Customer customer = CustomerMapper.getCustomerFromEmailAndPhone(email, phone, connectionPool);

        if (customer == null) {
            ctx.attribute("msg", "Email eller telefonnummer findes ikke");
            ctx.attribute("activeTab", "register");
            ctx.render("login");
            return;
        }

        if (customer.getPassword() != null) {
            ctx.attribute("msg", "Konto med denne email findes allerede");
            ctx.attribute("activeTab", "register");
            ctx.render("login");
            return;
        }

        String message = Validator.validatePassword(password);
        if (message != null) {
            ctx.attribute("msg", message);
            ctx.attribute("activeTab", "register");
            ctx.render("login");
            return;
        }

        CustomerMapper.updatePassword(email, password, connectionPool);
        ctx.sessionAttribute("currentUser", customer);
        ctx.redirect("/userPanel");
    }
}
