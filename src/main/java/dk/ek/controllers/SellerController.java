package dk.ek.controllers;

import dk.ek.entities.Carport;
import dk.ek.entities.Employee;
import dk.ek.entities.Order;
import dk.ek.exceptions.DatabaseException;
import dk.ek.persistence.*;
import dk.ek.persistence.CarportMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SellerController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/seller/draw/{id}", ctx -> showDrawing(ctx, connectionPool));
        app.get("/seller/edit/{id}", ctx -> showEditOrder(ctx, connectionPool));
        app.post("/seller/edit/{id}", ctx -> editOrder(ctx, connectionPool));
    }

    public static void updateOrderFields(
            int id,
            String roofMaterial,
            int carportWidth,
            int carportLength,
            boolean hasToolShed,
            int shedWidth,
            int shedLength,
            String note,
            int roofAngle,
            ConnectionPool connectionPool
    ) throws DatabaseException {

        String sql = """
        UPDATE orders
        SET roof_type = ?,
            carport_width = ?,
            carport_length = ?,
            has_tool_shed = ?,
            shed_width = ?,
            shed_length = ?,
            note = ?,
            roof_angle = ?
        WHERE id = ?
        """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, roofMaterial);
            ps.setInt(2, carportWidth);
            ps.setInt(3, carportLength);
            ps.setBoolean(4, hasToolShed);
            ps.setInt(5, shedWidth);
            ps.setInt(6, shedLength);
            ps.setString(7, note);
            ps.setInt(8, roofAngle);
            ps.setInt(9, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
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


    private static void showDrawing(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.pathParam("id"));

        Carport order = CarportMapper.getCarportById(orderId, connectionPool);

        ctx.attribute("order", order);
        ctx.render("drawing.html");
    }
}