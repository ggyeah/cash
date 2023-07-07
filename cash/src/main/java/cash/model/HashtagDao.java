package cash.model;

import java.sql.*;
import java.util.*;

import cash.vo.Cashbook;
import cash.vo.Hashtag;
import cash.vo.Member;

public class HashtagDao {	
	//해시태그 삭제
	public int removeHashtag(int cashbookNo) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM hashtag WHERE cashbook_no = ?";
		int removerow = 0;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			
			removerow = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {// finally : catch절에서도 실행되고 실행종료되어서도 실행되야 하는코드
			try {
				stmt.close();
				conn.close();	
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return removerow;
	}
	
	//해시태그 별로 모아보기
	public List<Map<String,Object>> selectWordCountByMonth(String memberId, int targetYear, int targetMonth) {
		 List<Map<String,Object>> htList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 String sql = "SELECT word, COUNT(*) AS cnt "
		            + "FROM hashtag h INNER JOIN cashbook c "
		            + "ON h.cashbook_no = c.cashbook_no "
		            + "WHERE c.member_id = ? "
		            + "AND YEAR(c.cashbook_date) = ? AND MONTH(c.cashbook_date) = ? "
		            + "GROUP BY word "
		            + "ORDER BY COUNT(*) DESC";
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			//? 값 두개
			stmt.setString(1,memberId);
			stmt.setInt(2,targetYear);
			stmt.setInt(3,targetMonth);
			rs = stmt.executeQuery();
	         while(rs.next()) {
	             Map<String, Object> m = new HashMap<String, Object>();
	             m.put("word", rs.getString("word"));
	             m.put("cnt", rs.getString("cnt"));
	             htList.add(m);
	          }
		} catch(Exception e) {
			e.printStackTrace();
		} finally {// finally : catch절에서도 실행되고 실행종료되어서도 실행되야 하는코드
			try {
				rs.close();
				stmt.close();
				conn.close();	
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return htList;
		}	
	
	// 해시태그 추가
	public int insertHashtag(Hashtag hashtag) {
		
		int row = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
        String sql = "INSERT INTO hashtag(cashbook_no, word, updatedate, createdate) VALUES (?,?,NOW(),NOW())";
        
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			//? 값 두개
			stmt.setInt(1,hashtag.getCashbookNo());
			stmt.setString(2,hashtag.getWord());
			
			row = stmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {// finally : catch절에서도 실행되고 실행종료되어서도 실행되야 하는코드
			try {
				stmt.close();
				conn.close();	
			} catch(SQLException e2) {
				e2.printStackTrace();
			}
		}
		return row;
		}	
}	
