package dk.ek.persistence;

import dk.ek.entities.Materials;

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

    public Materials getMaterialById(int id) {
        String sql = "select * from materials where id = ?";

        try (Connection con = connectionPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Materials(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("unit"),
                            rs.getDouble("price_per_unit"),
                            rs.getString("category")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Materials> getAllMaterials(){
        List<Materials> materials = new ArrayList<>();
        String sql = "select * from materials";

        try(Connection connection = connectionPool.getConnection() ){
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                materials.add(new Materials(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("unit"),
                        rs.getDouble("price_per_unit"),
                        rs.getString("category")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return materials;
    }
}
