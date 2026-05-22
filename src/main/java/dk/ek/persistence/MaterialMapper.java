package dk.ek.persistence;

import dk.ek.entities.Materials;
import dk.ek.exceptions.DatabaseException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {

    public static Materials getMaterialById(int id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM materials WHERE id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return materialsMap(rs);
            } else {
                throw new DatabaseException("Materiale ikke fundet");
            }

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }

    public static List<Materials> getAllMaterials(ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM materials ORDER BY category, name";
        List<Materials> materials = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                materials.add(materialsMap(rs));
            }
            return materials;

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }

    public static List<Materials> getMaterialsByCategory(String category, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM materials WHERE category = ? ORDER BY name";
        List<Materials> materials = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                materials.add(materialsMap(rs));
            }
            return materials;

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }

    public static List<Materials> getMaterialsById (int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT m.*, om.amount FROM materials m\n" +
                "JOIN order_materials om ON om.material_id = m.id\n" +
                "WHERE om.order_id = ?\n" +
                "ORDER BY m.category, m.name";
        List<Materials> materials = new ArrayList<>();

        try (
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
            ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                materials.add(materialsMap(rs));
            }
            return materials;

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }

    private static Materials materialsMap(ResultSet rs) throws SQLException {
        return new Materials(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("unit"),
                rs.getDouble("price_per_unit"),
                rs.getString("category")
        );
    }
}