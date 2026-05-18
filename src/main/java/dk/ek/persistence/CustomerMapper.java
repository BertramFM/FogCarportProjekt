package dk.ek.persistence;

import dk.ek.entities.Customer;
import dk.ek.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    private final ConnectionPool database;

    public CustomerMapper(ConnectionPool database) {
        this.database = database;
    }

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

    public boolean createCustomer(String firstname,
                                  String lastname,
                                  String address,
                                  String email,
                                  String phone,
                                  int zipcodeId) {

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
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer getCustomerById(int id) {

        String sql = "select * from customers where id = ?";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Customer customer = new Customer();

                    customer.setId(rs.getInt("id"));
                    customer.setFirstname(rs.getString("firstname"));
                    customer.setLastname(rs.getString("lastname"));
                    customer.setAddress(rs.getString("address"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setZipcodeId(rs.getInt("zipcode_id"));

                    return customer;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Customer> getAllCustomers() {

        List<Customer> list = new ArrayList<>();

        String sql = "select * from customers";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Customer customer = new Customer();

                customer.setId(rs.getInt("id"));
                customer.setFirstname(rs.getString("firstname"));
                customer.setLastname(rs.getString("lastname"));
                customer.setAddress(rs.getString("address"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setZipcodeId(rs.getInt("zipcode_id"));

                list.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}