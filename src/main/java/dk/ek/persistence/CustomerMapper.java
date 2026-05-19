package dk.ek.persistence;

import dk.ek.entities.Customers;
import dk.ek.exceptions.DatabaseException;
import dk.ek.entities.Customer;
import dk.ek.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    public static void createCustomer(Customers customer, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO customers (firstname, lastname, address, email, phone, zipcode) VALUES (?, ?, ?, ?, ?, ?)";

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
    public boolean emailExists(String email) {
        String sql = "select 1 from customers where email = ?";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createCustomer(String firstname, String lastname, String address, String email, String phone, int zipcodeId) {
        String sql = """
                insert into customers
                (firstname, lastname, address, email, phone, zipcode_id)
                values (?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (emailExists(email)) return false;

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setInt(6, zipcodeId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static Customers getCustomerById (int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM customers WHERE id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customers(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("zipcode")
                );
            } else {
                throw new DatabaseException("Kunde ikke fundet");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static Customers getCustomerByEmail (String email, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM customers WHERE email = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customers(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("zipcode")
                );
            } else {
                throw new DatabaseException("Kunde ikke fundet");
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static List<Customers> getAllCustomers(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM customers";
        List<Customers> customers = new ArrayList<>();

        try(
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                customers.add(new Customers(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("zipcode")
                ));
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
}