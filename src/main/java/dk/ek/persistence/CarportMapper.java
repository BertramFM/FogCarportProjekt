    package dk.ek.persistence;

    import dk.ek.entities.Carport;
    import dk.ek.entities.Materials;
    import dk.ek.entities.RoofType;

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

            for (Materials m : carport.getCarportMaterial()) {

                materialPs.setInt(1, orderId);
                materialPs.setInt(2, m.getId());
                materialPs.setInt(3, 1);

                materialPs.addBatch();
            }

            for (Materials m : carport.getRoofMaterial()) {

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