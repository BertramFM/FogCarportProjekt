package dk.ek.controllers;

import dk.ek.entities.Order;
import dk.ek.entities.OrderMaterials;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.*;
import dk.ek.services.CarportSvg;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class SellerController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/seller/draw/{id}", ctx -> showDrawing(ctx, connectionPool));
        app.get("/seller/edit/{id}", ctx -> showEditOrder(ctx, connectionPool));
        app.post("/seller/edit/{id}", ctx -> editOrder(ctx, connectionPool));
    }

    private static void editOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {

        int id = Integer.parseInt(ctx.pathParam("id"));

        String roofMaterial = ctx.formParam("roofMaterial");
        int carportWidth = Integer.parseInt(ctx.formParam("carportWidth"));
        int carportLength = Integer.parseInt(ctx.formParam("carportLength"));
        boolean hasToolShed = Boolean.parseBoolean(ctx.formParam("hasToolShed"));
        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));
        String note = ctx.formParam("note");
        int roofAngle = Integer.parseInt(ctx.formParam("roofAngle"));

        OrderMapper.updateOrderFields(
                id,
                roofMaterial,
                carportWidth,
                carportLength,
                hasToolShed,
                shedWidth,
                shedLength,
                note,
                roofAngle,
                connectionPool
        );

        ctx.redirect("/seller");
    }

    private static void showEditOrder(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String email = ctx.formParam("email");

        Order order = OrderMapper.getOrderById(id, connectionPool);

        ctx.attribute("order", order);
        ctx.render("edit-order.html");

    }


    private static void showDrawing(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.pathParam("id"));

        Order order = OrderMapper.getOrderById(orderId, connectionPool);
        List<OrderMaterials> bomList = OrderMaterialMapper.getOrderMaterials(orderId, connectionPool);
        String svg = CarportSvg.generateCarportSvg(order, connectionPool);

        ctx.attribute("order", order);
        ctx.attribute("bom", bomList);
        ctx.attribute("svg", svg);

        ctx.render("showDrawing.html");
    }
}