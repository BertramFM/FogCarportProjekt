package dk.ek.persistence;

import dk.ek.entities.Carport;
import dk.ek.entities.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {

    private ConnectionPool connectionPool;

    public CarportMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Carport getCarportById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        try(Connection connection = connectionPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Carport carport = new Carport();
                carport.setId(rs.getInt("id"));
                carport.setWidth(rs.getInt("width"));
                carport.setLength(rs.getInt("length"));
                carport.setToolShed(rs.getBoolean("tool_shed"));
                carport.setShedWidth(rs.getInt("shed_width"));
                carport.setShedLength(rs.getInt("shed_length"));
                carport.setNote(rs.getString("note"));
                carport.setStatus(rs.getString("status"));
                carport.setCreatedAt(rs.getDate("created_at"));
                return carport;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Carport> getAllOrders() {
        List<Carport> allOrders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try(Connection connection = connectionPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

             while(rs.next()){
                 allOrders.add(new Carport(
                         rs.getInt("id"),
                         rs.getInt("customer_id"),
                         rs.getInt("employee_id"),
                         rs.getString("roof_type"),
                         rs.getInt("width"),
                         rs.getInt("length"),
                         rs.getBoolean("tool_shed"),
                         rs.getInt("shed_width"),
                         rs.getInt("shed_length"),
                         rs.getString("note"),
                         rs.getString("status"),
                         rs.getDate("created_at")
                 ));
             }

        }catch (Exception e){
            e.printStackTrace();
        }
        return allOrders;
    }

    public void saveCarport(Carport carport) {

        String orderSql = """
        INSERT INTO orders
        (customer_id, employee_id, roof_type, width, length,
         tool_shed, shed_width, shed_length, note, status, created_at)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())
        """;

        try (Connection connection = connectionPool.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(
                    orderSql,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setInt(1, 3);

            // employee_id
            ps.setInt(2, 1);

            ps.setString(3, carport.getRoofType().getName());

            ps.setInt(4, carport.getWidth());
            ps.setInt(5, carport.getLength());

            ps.setBoolean(6, false);

            ps.setInt(7, 0);
            ps.setInt(8, 0);

            ps.setString(9, "Generated order");
            ps.setString(10, "pending");

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (!rs.next()) {
                throw new RuntimeException("No order ID generated");
            }

            int orderId = rs.getInt(1);

            String materialSql = """
                    INSERT INTO order_materials
                    (order_id, material_id, amount)
                    VALUES (?, ?, ?)
                    """;

            PreparedStatement materialPs =
                    connection.prepareStatement(materialSql);

            for (Material m : carport.getCarportMaterial()) {

                materialPs.setInt(1, orderId);
                materialPs.setInt(2, m.getId());
                materialPs.setInt(3, 1);

                materialPs.addBatch();
            }

            for (Material m : carport.getRoofMaterial()) {

                materialPs.setInt(1, orderId);
                materialPs.setInt(2, m.getId());
                materialPs.setInt(3, 1);

                materialPs.addBatch();
            }

            materialPs.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}