package dk.ek;

import dk.ek.config.SessionConfig;
import dk.ek.config.ThymeleafConfig;
import dk.ek.controllers.*;
import dk.ek.persistence.*;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "carport";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);


    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        MainController.addRoutes(app, connectionPool);
        SellerController.addRoutes(app, new CarportMapper(connectionPool), new EmployeeMapper(connectionPool), new CustomerMapper(connectionPool));
        UserController.addRoutes(app, new EmployeeMapper(connectionPool), new CustomerMapper(connectionPool));
        CarportController.addRoutes(app, new CarportMapper(connectionPool), new MaterialMapper(connectionPool));
    }
}