package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.Card;

public class CardDAO {
	public static Card findById(long userid, long phoneid) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"SELECT * "
					+ "FROM card "
					+ "WHERE userid = ? AND phoneid = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, userid);
			st.setLong(2, phoneid);
			ResultSet rs = st.executeQuery();	
			Card e = null;
			if(rs.next()) {	
				e = new Card();
				e.setUserid(rs.getLong(1));
				e.setPhoneid(rs.getLong(2));
				e.setNumber(rs.getLong(3));
			}
			st.close();
			return e;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static boolean update(Card e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"UPDATE card "
					+ "SET number = ? "
					+ "WHERE userid = ? AND phoneid = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, e.getNumber());
			st.setLong(2, e.getUserid());
			st.setLong(3, e.getPhoneid());
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
	
	public static boolean insert(Card e) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"INSERT INTO card (userid,phoneid,number) "
					+ "VALUES (?,?,?) ";		
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(3, e.getNumber());
			st.setLong(1, e.getUserid());
			st.setLong(2, e.getPhoneid());
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
	
	public static boolean delete(long userid, long phoneid) {
		try(Connection connection = DBConnection.getConnection();) {
			String sql = 
					"DELETE FROM card "
					+ "WHERE userid = ? AND phoneid = ?";
			PreparedStatement st = connection.prepareStatement(sql);
			st.setLong(1, userid);
			st.setLong(2, phoneid);
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
}
