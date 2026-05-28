package dk.ek.persistence;

import dk.ek.entities.Order;
import dk.ek.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

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
            width = ?,
            length = ?,
            tool_shed = ?,
            shed_width = ?,
            shed_length = ?,
            note = ?,
            roof_slope = ?
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

    public static int createOrder(Order order, ConnectionPool connectionPool) throws DatabaseException {
        String sql = """
        INSERT INTO orders (
            customer_id, roof_type, width, length, tool_shed, shed_width, shed_length, note, roof_slope
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        RETURNING id
        """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, order.getCustomerId());
            ps.setString(2, order.getRoofMaterial());
            ps.setInt(3, order.getCarportWidth());
            ps.setInt(4, order.getCarportLength());
            ps.setBoolean(5, order.getHasToolShed());
            ps.setInt(6, order.getShedWidth());
            ps.setInt(7, order.getShedLength());
            ps.setString(8, order.getNote());

            if (order.getRoofAngle() == 0) {
                ps.setNull(9, Types.INTEGER);
            } else {
                ps.setInt(9, order.getRoofAngle());
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new DatabaseException("Fejl ved oprettelse af ordre");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl;", e.getMessage());
        }
    }

    public static Order getOrderById(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return orderMap(rs);
            } else {
                throw new DatabaseException("Ordre ikke fundet");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }

    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(orderMap(rs));
            }
            return orders;

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl; ", e.getMessage());
        }
    }

    public static List<Order> getOrdersByCustomerId(int customerId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY created_at DESC";
        List<Order> orders = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orders.add(orderMap(rs));
            }
            return orders;

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }

    public static void updateStatus(int orderId, String status, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved opdatering af ordre");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }

    private static Order orderMap(ResultSet rs) throws SQLException {
        return new Order(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("employee_id"),
                rs.getString("roof_type"),
                rs.getInt("width"),
                rs.getInt("length"),
                rs.getBoolean("tool_shed"),
                rs.getInt("shed_width"),
                rs.getInt("shed_length"),
                rs.getString("note"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
