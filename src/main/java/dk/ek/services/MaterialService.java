package dk.ek.services;

import dk.ek.entities.Material;
import dk.ek.persistence.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaterialService {
    private ConnectionPool connectionPool;

    public MaterialService(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Material getMaterialById(int id) {
        String sql = "select * from materials where id = ?";

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Material(
                           rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("unit")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Material> getAllMaterials(){
        List<Material> materials = new ArrayList<>();
        String sql = "select * from materials";

        try(Connection connection = connectionPool.getConnection() ){
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                materials.add(new Material(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getString("unit")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return materials;
    }
}
