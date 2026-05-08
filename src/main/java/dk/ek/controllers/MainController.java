package dk.ek.controllers;

import dk.ek.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class MainController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> homePage(ctx, connectionPool));
        app.get("/flatRoof", ctx -> flatRoof(ctx, connectionPool));
    }

    private static void homePage(@NotNull Context ctx, ConnectionPool connectionPool) {
        ctx.render("index.html");
    }
    private static void flatRoof(@NotNull Context ctx, ConnectionPool connectionPool) {
        ctx.render("flatRoof.html");
    }
}
