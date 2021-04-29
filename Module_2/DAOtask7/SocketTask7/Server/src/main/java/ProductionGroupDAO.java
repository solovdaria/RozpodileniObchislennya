

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductionGroupDAO {
	public static ProductionGroup findById(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM productionGroup "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			ProductionGroup productionGroup = null;
			if(rs.next()) {	
				productionGroup = new ProductionGroup();
				productionGroup.setId(rs.getLong(1));
				productionGroup.setName(rs.getString(2));
				productionGroup.setProductionid(rs.getLong(3));
				productionGroup.setParameter(rs.getLong(4));
			}
			st.close();
			return productionGroup;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static ProductionGroup findByName(String name) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM productionGroup "
					+ "WHERE name = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();	
			ProductionGroup productionGroup = null;
			if(rs.next()) {	
				productionGroup = new ProductionGroup();
				productionGroup.setId(rs.getLong(1));
				productionGroup.setName(rs.getString(2));
				productionGroup.setProductionid(rs.getLong(3));
				productionGroup.setParameter(rs.getLong(4));
			}
			st.close();
			return productionGroup;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(ProductionGroup productionGroup) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE productionGroup "
					+ "SET name = ?, productionid = ?, parameter = ? "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, productionGroup.getName());
			st.setLong(2, productionGroup.getProductionid());
			st.setLong(3, productionGroup.getParameter());
			st.setLong(4, productionGroup.getId());
			var result = st.executeUpdate();
			st.close();
			if(result>0)
				return true;
			else
				return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean insert(ProductionGroup productionGroup) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO production (name,productionid,parameter) "
					+ "VALUES (?,?,?) "
					+ "RETURNING id";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, production.getName());
			st.setLong(2, production.getProductionid());
			st.setLong(3, production.getParameter());
			ResultSet rs = st.executeQuery();	
			if(rs.next()) {
				production.setId(rs.getLong(1));
			} else
				return false;
			st.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean delete(Production production) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM production "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, production.getId());
			var result = st.executeUpdate();
			st.close();
			if(result>0)
				return true;
			else
				return false;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static List<Production> findAll(){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM production";
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<Production> list = new ArrayList<>();
			while(rs.next()) {
				var production = new Production();
				production.setId(rs.getLong(1));
				production.setName(rs.getString(2));
				production.setProductionid(rs.getLong(3));
				production.setParameter(rs.getLong(4));
				list.add(production);
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static List<Production> findByProductionId(Long id){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM production "
					+ "WHERE productionid = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			List<Production> list = new ArrayList<>();
			while(rs.next()) {
				var production = new Production();
				production.setId(rs.getLong(1));
				production.setName(rs.getString(2));
				production.setProductionid(rs.getLong(3));
				production.setParameter(rs.getLong(4));
				list.add(production);
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
