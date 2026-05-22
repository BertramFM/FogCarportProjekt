package dk.ek.persistence;

import dk.ek.exceptions.DatabaseException;
import dk.ek.entities.Customer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    public static boolean emailExists(String email, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select 1 from customers where email = ?";

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
                return rs.next();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static int createCustomer(Customer customer, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO customers (firstname, lastname, address, email, phone, zip_code, password_hash) \n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)\n" +
                "RETURNING id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, customer.getFirstname());
            ps.setString(2, customer.getLastname());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhone());
            ps.setInt(6, customer.getZipcode());

            if (customer.getPassword() != null) {
                // When password hashing needs to be implemented,
                // ps.setString(7, BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt()));
                ps.setString(7, customer.getPassword());
            } else {
                ps.setString(7, null);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new DatabaseException("Fejl ved oprettelse af kunde");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static Customer getCustomerById (int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT c.*, z.city FROM customers c\n" +
                "JOIN zip_code z USING (zip_code)\n" +
                "WHERE c.id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return customerRow(rs);
            } else {
                throw new DatabaseException("Kunde ikke fundet");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static Customer getCustomerByEmail (String email, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT c.*, z.city FROM customers c\n" +
                "JOIN zip_code z USING (zip_code)\n" +
                "WHERE c.email = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return customerRow(rs);
            } else {
                throw new DatabaseException("Kunde ikke fundet");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static List<Customer> getAllCustomers(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT c.*, z.city FROM customers c\n" +
                "JOIN zip_code z USING (zip_code)\n" +
                "ORDER BY c.lastname";
        List<Customer> customers = new ArrayList<>();

        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customers.add(customerRow(rs));
            }
            return customers;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static String getCityByZipcode (int zipcode, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT city FROM zip_code WHERE zip_code = ?";

        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, zipcode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("city");
            } else {
                throw new DatabaseException("Postnummer ikke fundet");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private static Customer customerRow(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("address"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getInt("zip_code"),
                rs.getString("city"),
                rs.getString("password_hash")
        );
    }
}