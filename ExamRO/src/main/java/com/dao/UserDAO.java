package com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Card;
import com.entity.User;

public class UserDAO {
	public static User findById(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM users "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			User e = null;
			if(rs.next()) {	
				e = new User();
				e.setId(rs.getLong(1));
				e.setDate(rs.getDate(2));
			}
			st.close();
			return e;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(User e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE users "
					+ "SET date = ? "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setDate(1, e.getDate());
			st.setLong(2, e.getId());
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
	
	public static boolean insert(User e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO users (date) "
					+ "VALUES (?) "
					+ "RETURNING id";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setDate(1, e.getDate());
			ResultSet rs = st.executeQuery();	
			if(rs.next()) {
				e.setId(rs.getLong(1));
			} else
				return false;
			st.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean delete(long id) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM users "
					+ "WHERE id = ?";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
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
	
	public static String getPrettyInfo(long id) {
		var e = findById(id);
		if(e == null)
			return "";
		
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT name,description,price,number "
					+ "FROM (users INNER JOIN card ON users.id = userid) "
					+ "INNER JOIN phone ON phoneid = phone.id "
					+ "WHERE userid = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();	
			var str = new StringBuilder();
			
			str.append("Date: "+e.getDate().toString()+ "\n");
			while(rs.next()) {	
				str.append(rs.getString(1)+"\n\t"+ rs.getString(2)+"\nPrice: "+rs.getLong(3)
						+"\nNumber: "+rs.getLong(4)+"\n");
			}
			st.close();
			return str.toString();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static List<Long> findByPhoneid(long id){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT id "
					+ "FROM users INNER JOIN card ON id = userid "
					+ "WHERE phoneid = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			while(rs.next()) {
				list.add(rs.getLong(1));
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static List<Long> findByNotPhoneidAndToday(long phoneid){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT id "
					+ "FROM users "
					+ "WHERE date = CURRENT_DATE AND id NOT IN ( "
					+"	SELECT id "
					+"	FROM users INNER JOIN card ON id=userid "
					+"	WHERE phoneid = ? "
					+")";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, phoneid);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			while(rs.next()) {
				list.add(rs.getLong(1));
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean placeTodayUser() {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT DISTINCT phoneid "
					+ "FROM card INNER JOIN users ON userid = id "
					+ "WHERE date = CURRENT_DATE";		
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			while(rs.next()) {
				list.add(rs.getLong(1));
			}
			st.close();
			
			var user = new User();
			user.setDate(new Date(System.currentTimeMillis()));
			if(!insert(user))
				return false;
			for(long id : list) {
				var c = new Card(user.getId(),id,1);
				CardDAO.insert(c);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteSpecial(long phoneid, long number) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM users "
					+ "WHERE id IN ( "
					+ "SELECT id "
					+ "FROM users INNER JOIN card ON id = userid "
					+ "WHERE phoneid = ? AND number = ?)";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, phoneid);
			st.setLong(2, number);
			var result = st.executeUpdate();
			st.close();
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static List<Long> filterByCostAndNumber(long cost, long q){
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT userid,price,number "
							+ "FROM phone INNER JOIN card ON id = phoneid "
							+ "USER BY userid";
			PreparedStatement st = connection.prepareStatement(sql);
			ResultSet rs = st.executeQuery();			
			List<Long> list = new ArrayList<>();
			long currentCost = 0;
			long number = 0;
			long currentId = 0;
			while(rs.next()) {
				var id = rs.getLong(1);
				if(id != currentId) {
					if (number == q && currentCost <= cost) {
						list.add(currentId);
					}
					currentCost = 0;
					number = 0;
					currentId = id;
				}
				var newPr = rs.getLong(3);
				number += newPr;
				currentCost += newPr * rs.getLong(2);
			}
			st.close();
			return list;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
