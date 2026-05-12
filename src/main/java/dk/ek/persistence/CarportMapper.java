    package dk.ek.persistence;

    import dk.ek.entities.Carport;
    import dk.ek.entities.Material;
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

            public void saveCarport(Carport carport){
                try(Connection connection = connectionPool.getConnection()){
                    String sql = "insert into orders (rooftypeid, length, width) values (?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, carport.getRoofType().getId());
                    ps.setInt(2, carport.getLength());
                    ps.setInt(3, carport.getWidth());
                    ps.executeUpdate();

                   ResultSet rs = ps.getGeneratedKeys();

                    if (!rs.next()) {
                        throw new RuntimeException("Failed to retrieve order id, no keys generated  ");
                    }

                   int orderId = rs.getInt(1);

                   String insertMaterialSql = "insert into order_material (order_id, material_id, quantity) values (?, ?, ?)";

                   PreparedStatement insertMaterialPs = connection.prepareStatement(insertMaterialSql);

                   for (Material m : carport.getCarportMaterial()){
                       insertMaterialPs.setInt(1, orderId);
                       insertMaterialPs.setInt(2, m.getId());
                       insertMaterialPs.setInt(3, 1);
                       insertMaterialPs.addBatch();
                   }

                   for (Material m : carport.getRoofMaterial()){
                       insertMaterialPs.setInt(1, orderId);
                       insertMaterialPs.setInt(2, m.getId());
                       insertMaterialPs.setInt(3, 1);
                       insertMaterialPs.addBatch();
                   }

                   insertMaterialPs.executeBatch();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        public RoofType getRoofTypeById(int id){
            try (Connection con  = connectionPool.getConnection()) {
                String sql = "select * from rooftypes where typeid = ?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    return new RoofType(rs.getInt("typeid"), rs.getString("name"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public List<RoofType> getRoofTypes(){
            List<RoofType> roofTypes = new ArrayList<>();

            try(Connection con = connectionPool.getConnection()){
                String sql = "select * from rooftypes";

                PreparedStatement ps = con.prepareStatement(sql);

                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    RoofType roofType = new RoofType(rs.getInt("typeid"), rs.getString("name"));
                    roofTypes.add(roofType);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return roofTypes;
        }

    }
