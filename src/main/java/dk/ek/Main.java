package dk.ek;

import dk.ek.config.SessionConfig;
import dk.ek.config.ThymeleafConfig;
import dk.ek.controllers.MainController;
import dk.ek.controllers.CarportController;
import dk.ek.controllers.UserController;
import dk.ek.controllers.SellerController;
import dk.ek.persistence.ConnectionPool;
import dk.ek.persistence.CarportMapper;
import dk.ek.persistence.MaterialMapper;
import dk.ek.persistence.SellerMapper;
import dk.ek.services.UserService;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "carport";

    private static final ConnectionPool connectionPool =
            ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler ->
                    handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        UserService userService = new UserService(connectionPool);
        CarportMapper carportMapper = new CarportMapper(connectionPool);
        MaterialMapper materialMapper = new MaterialMapper(connectionPool);
        SellerMapper sellerMapper = new SellerMapper(connectionPool);

        MainController.addRoutes(app, connectionPool);
        UserController.addRoutes(app, userService);
        CarportController.addRoutes(app, carportMapper, materialMapper);
        SellerController.addRoutes(app, sellerMapper);
    }
}