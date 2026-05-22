package dk.ek.persistence;

import dk.ek.entities.Order;
import dk.ek.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static int createOrder(Order order, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO orders (customer_id, employee_id, roof_type, width, length, tool_shed, shed_width, shed_length, note)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)\n" +
                "RETURNING id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, order.getCustomerId());
            ps.setInt(2, order.getEmployeeId());
            ps.setString(3, order.getRoofType());
            ps.setInt(4, order.getWidth());
            ps.setInt(5, order.getLength());
            ps.setBoolean(6, order.getIsToolShed());
            ps.setInt(7, order.getShedWidth());
            ps.setInt(8, order.getShedLength());
            ps.setString(9, order.getNote());

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
        String sql = "SELECT * FROM order WHERE customer_id = ? ORDER BY created_at DESC";
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
