package dk.ek.persistence;

import dk.ek.entities.Customer;
import dk.ek.entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerMapper {
    private ConnectionPool connectionPool;

    public SellerMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        String sql = """
                SELECT c.id, c.firstname, c.lastname, c.address, c.email, c.phone,
                       c.zipcode, z.city
                FROM customers c
                JOIN zip_code z ON c.zipcode = z.zip_code
                ORDER BY c.id DESC
                """;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("zipcode"),
                        rs.getString("city")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        String sql = """
                SELECT o.id,
                       c.firstname || ' ' || c.lastname AS customer_name,
                       e.firstname || ' ' || e.lastname AS employee_name,
                       o.roof_type,
                       o.width,
                       o.length,
                       o.tool_shed,
                       o.shed_width,
                       o.shed_length,
                       o.note,
                       o.status,
                       o.created_at
                FROM orders o
                JOIN customers c ON o.customer_id = c.id
                JOIN employees e ON o.employee_id = e.id
                ORDER BY o.created_at DESC
                """;

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("employee_name"),
                        rs.getString("roof_type"),
                        rs.getInt("width"),
                        rs.getInt("length"),
                        rs.getBoolean("tool_shed"),
                        (Integer) rs.getObject("shed_width"),
                        (Integer) rs.getObject("shed_length"),
                        rs.getString("note"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}