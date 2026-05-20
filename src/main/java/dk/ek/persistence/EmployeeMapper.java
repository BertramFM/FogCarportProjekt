package dk.ek.persistence;

import dk.ek.entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static Employee getEmployeeByEmail(String email, ConnectionPool connectionPool) {

        String sql = "select * from employees where email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Employee employee = new Employee();

                    employee.setId(rs.getInt("id"));
                    employee.setFirstname(rs.getString("firstname"));
                    employee.setLastname(rs.getString("lastname"));
                    employee.setEmail(rs.getString("email"));
                    employee.setRole(rs.getString("role"));

                    return employee;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean emailExists(String email, ConnectionPool connectionPool) {
        String sql = "select 1 from employees where email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createEmployee(String firstname, String lastname, String email, String role, ConnectionPool connectionPool) {

        String sql = """
            insert into employees (firstname, lastname, email, role)
            values (?, ?, ?, ?)
        """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
            ) {

            if (emailExists(email, connectionPool)) return false;

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, role);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee getEmployeeById(int id, ConnectionPool connectionPool) {

        String sql = "select * from employees where id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Employee employee = new Employee();

                    employee.setId(rs.getInt("id"));
                    employee.setFirstname(rs.getString("firstname"));
                    employee.setLastname(rs.getString("lastname"));
                    employee.setEmail(rs.getString("email"));
                    employee.setRole(rs.getString("role"));

                    return employee;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Employee> getAllEmployees(ConnectionPool connectionPool) {

        List<Employee> EmployeeList = new ArrayList<>();

        String sql = "select * from employees";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ) {

            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setFirstname(rs.getString("firstname"));
                employee.setLastname(rs.getString("lastname"));
                employee.setEmail(rs.getString("email"));
                employee.setRole(rs.getString("role"));

                EmployeeList.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return EmployeeList;
    }
}