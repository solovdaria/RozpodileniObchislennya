

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductionDAO {
	public static Production findById(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM production "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			Production production = null;
			if(rs.next()) {	
				production = new Production();
				production.setId(rs.getLong(1));
				production.setName(rs.getString(2));
			}
			st.close();
			return production;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static Production findByName(String name) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM production "
					+ "WHERE name = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, name);
			ResultSet rs = st.executeQuery();	
			Production production = null;
			if(rs.next()) {	
				production = new Production();
				production.setId(rs.getLong(1));
				production.setName(rs.getString(2));
			}
			st.close();
			return production;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(Production production) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE production "
					+ "SET name = ? "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, production.getName());
			st.setLong(2, production.getId());
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
	
	public static boolean insert(Production production) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO production (name) "
					+ "VALUES (?) "
					+ "RETURNING id";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, production.getName());
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
