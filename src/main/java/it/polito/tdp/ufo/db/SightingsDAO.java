package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Anno> getAnno(){
		String sql = "SELECT YEAR(DATETIME) AS anno, COUNT(*) AS avvistamenti "
				+ "FROM sighting "
				+ "GROUP BY YEAR(DATETIME) "
				+ "ORDER BY YEAR(DATETIME)" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Anno> list = new ArrayList<Anno>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Anno(res.getInt("anno"), res.getInt("avvistamenti"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getVertex(Integer anno){
		String sql = "SELECT DISTINCT state "
				+ "FROM sighting "
				+ "WHERE YEAR(DATETIME) = ? "
				+ "ORDER BY state" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			List<String> list = new ArrayList<String>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(res.getString("state")) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public LocalDateTime getFirst(String state, Integer anno) {
		String sql = "SELECT datetime AS date "
				+ "from sighting "
				+ "WHERE state = ? "
				+ "AND YEAR(DATETIME) = ? "
				+ "ORDER BY DATETIME "
				+ "LIMIT 1";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, state);
			st.setInt(2, anno);
			LocalDateTime date;
			
			ResultSet res = st.executeQuery() ;
			res.first();
			date = res.getTimestamp("date").toLocalDateTime();
			
			conn.close();
			return date ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getSuccessivi(String state, Integer anno, LocalDateTime data){
		String sql = "SELECT DISTINCT state "
				+ "FROM sighting "
				+ "WHERE YEAR(DATETIME) = ? "
				+ "AND state <> ? "
				+ "AND DATETIME > ?";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setString(2, state);
			st.setTimestamp(3, Timestamp.valueOf(data));
			List<String> list = new ArrayList<String>() ;
			
			ResultSet res = st.executeQuery() ;
			

			while(res.next()) {
				list.add(res.getString("state")) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
