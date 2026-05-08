    package dk.ek.services;

    import dk.ek.entities.Carport;
    import dk.ek.entities.RoofType;
    import dk.ek.persistence.ConnectionPool;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.util.ArrayList;
    import java.util.List;

    public class CarportService {
        private ConnectionPool connectionPool;

        public CarportService(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
        }

        public void saveCarport(Carport carport){
            try(Connection connection = connectionPool.getConnection()){
                String sql = "insert into orders (rooftypeid, length, width) values (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, carport.getRoofType().getId());
                ps.setInt(2, carport.getLength());
                ps.setInt(3, carport.getWidth());
                ps.executeUpdate();

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
