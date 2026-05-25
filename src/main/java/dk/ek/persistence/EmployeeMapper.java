package dk.ek.persistence;

import dk.ek.entities.Employee;
import dk.ek.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static boolean emailExists(String email, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select 1 from employees where email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static Employee getEmployeeByEmail(String email, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "select * from employees where email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return employeeRow(rs);
                } else {
                    throw new DatabaseException("Ansat ikke fundet");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
    }

    public static Employee getEmployeeById(int id, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "select * from employees where id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return employeeRow(rs);
                } else {
                    throw new DatabaseException("Ansat ikke fundet");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
    }

    public static List<Employee> getAllEmployees(ConnectionPool connectionPool) throws DatabaseException {

        List<Employee> employeeList = new ArrayList<>();

        String sql = "select * from employees";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee employee = employeeRow(rs);

                    employeeList.add(employee);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: " + e.getMessage());
        }
        return employeeList;
    }


    private static Employee employeeRow(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("role"),
                rs.getString("password_hash")
        );
    }
}