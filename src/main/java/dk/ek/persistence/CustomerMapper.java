package dk.ek.persistence;

import dk.ek.entities.Customers;
import dk.ek.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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














