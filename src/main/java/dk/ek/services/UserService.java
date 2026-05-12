package dk.ek.services;



import dk.ek.entities.User;
import dk.ek.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private ConnectionPool database;

    public UserService(ConnectionPool database) {
        this.database = database;
    }

    public boolean emailExists(String email){
        try(Connection connection = database.getConnection()){
            String sql = "select 1 from users where email = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean addBalance(int userId, double amount) {
        String sql = "update users set balance = balance + ? where id = ?";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setInt(2, userId);

            int updated = ps.executeUpdate();
            return updated == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createUser(String email, String password) {

        try (Connection connection = database.getConnection()) {
            String checkSql = "select 1 from users where email = ?";
            PreparedStatement cehckPs = connection.prepareStatement(checkSql);
            cehckPs.setString(1, email);


            ResultSet rs = cehckPs.executeQuery();

            if (rs.next()) {
                return false;
            }

            String sql = "insert into users (email, password) values (?, ?,?, ?)";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(3, password);


            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int id) {
        String sql = "select id, email,password, role from users where id = ?";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role")
                    );

                    user.setId(rs.getInt("id"));

                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User login(String email, String password) {
        String sql = "select id, email, password, role from users where email = ?  and password = ?";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(3, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                    user.setId(rs.getInt("id"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "select id, email, password, role from users";

        try (Connection con = database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );

                user.setId(rs.getInt("id"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}

