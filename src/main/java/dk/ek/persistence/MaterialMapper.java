package dk.ek.persistence;

import dk.ek.entities.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaterialMapper {

    private ConnectionPool connectionPool;

    public MaterialMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Material getMaterialById(int id) {

        String sql = "SELECT * FROM materials WHERE id = ?";

        try (
                Connection con = connectionPool.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getDouble("price_per_unit")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Material> getAllMaterials() {

        List<Material> materials = new ArrayList<>();

        String sql = "SELECT * FROM materials";

        try (Connection con = connectionPool.getConnection()) {

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                materials.add(new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getDouble("price_per_unit")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return materials;
    }
}