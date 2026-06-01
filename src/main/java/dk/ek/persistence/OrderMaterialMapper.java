package dk.ek.persistence;

import dk.ek.entities.OrderMaterials;
import dk.ek.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMaterialMapper {
    public static void createOrderMaterial(int orderID, OrderMaterials orderMaterial, ConnectionPool connectionPool) throws DatabaseException {
        String sql = """
            INSERT INTO order_materials (order_id, material_name, material_description, material_length, amount, unit_of_amount, usage_description)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
            ) {

            ps.setInt(1, orderID);
            ps.setString(2, orderMaterial.getName());
            ps.setString(3, orderMaterial.getMaterialDescription());
            ps.setInt(4, orderMaterial.getLengthMeasurement());
            ps.setInt(5, orderMaterial.getAmount());
            ps.setString(6, orderMaterial.getUnitOfAmount());
            ps.setString(7, orderMaterial.getUsageDescription());

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static List<OrderMaterials> getOrderMaterials(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        List<OrderMaterials> foundOrderMaterials = new ArrayList<>();

        String sql = """
            SELECT material_name, material_description, material_length, amount, unit_of_amount, usage_description 
            FROM order_materials
            WHERE order_id = ?
            """;

        try (
            Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                foundOrderMaterials.add(new OrderMaterials(rs.getString("material_name"), rs.getString("material_description"), rs.getInt("material_length"), rs.getInt("amount"), rs.getString("unit_of_amount"), rs.getString("usage_description")));
            }

            return foundOrderMaterials;

        } catch (SQLException e) {
            throw new DatabaseException("DB fejl: ", e.getMessage());
        }
    }
}
