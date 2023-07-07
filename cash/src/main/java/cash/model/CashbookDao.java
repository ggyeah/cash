package cash.model;

import java.sql.*;

import java.util.*;

import cash.vo.Cashbook;
import cash.vo.Member;

public class CashbookDao {
	//cashbook 삭제
	public int removeCashbook(String memberId, int cashbookNo) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM cashbook WHERE member_id = ? AND cashbook_no = ?";
		int row = 0;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, cashbookNo);
			
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
	
	//cashbookOne 상세정보
	public Cashbook cashbookOne(String memberId, int cashbookNo) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT cashbook_no, member_id, category, cashbook_date, price, memo, updatedate, createdate from cashbook where member_id = ? AND cashbook_no = ?";
		Cashbook cashbook = null;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, cashbookNo);
			rs = stmt.executeQuery();
			if(rs.next()) {
				cashbook = new Cashbook();
				cashbook.setCashbookNo(rs.getInt("cashbook_no"));
				cashbook.setMemberId(rs.getString("member_id"));
				cashbook.setCategory(rs.getString("category"));
				cashbook.setCashbookDate(rs.getString("cashbook_date"));
				cashbook.setPrice(rs.getInt("price"));
				cashbook.setMemo(rs.getString("memo"));
				cashbook.setCreatedate(rs.getString("createdate"));
				cashbook.setUpdatedate(rs.getString("updatedate"));
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
		return cashbook;
	}
	
	//cashbook 수정
	public int modifyCashbook(Cashbook cashbook, String memberId) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE cashbook SET category = ?, cashbook_date = ?, price = ?,memo = ?, updatedate = now() WHERE member_id =? AND cashbook_no =?";
		int row = 0;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cashbook.getCategory());
			stmt.setString(2, cashbook.getCashbookDate());
			stmt.setInt(3, cashbook.getPrice());
			stmt.setString(4, cashbook.getMemo());
			stmt.setString(5, memberId);
			stmt.setInt(6, cashbook.getCashbookNo());
			
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
		
	// hashtag에 따른 cnt
	public int cashbookByTagCnt(String memberId, String word){
	    int totalCnt = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    String sql = "SELECT COUNT(*) FROM  hashtag h INNER JOIN cashbook c ON h.cashbook_no = c.cashbook_no WHERE c.member_id = ? AND h.word = ?";

	try { 
		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, memberId);
		stmt.setString(2, word);
		
		rs = stmt.executeQuery();
		

	    if (rs.next()) {
	    	totalCnt = rs.getInt("COUNT(*)");
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
	return totalCnt;
	}	
	
	//wordList
	//해시태그별 cashbook
	public List<Map<String,Object>>  selectCashbookListByTag(String memberId, String word, int beginRow, int rowPerPage) {
		
		List<Map<String,Object>> hoList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    String sql = "SELECT category, cashbook_date AS cashbookDate, price, memo, word, c.updatedate, c.createdate "
	            + "FROM hashtag h INNER JOIN cashbook c "
	            + "ON h.cashbook_no = c.cashbook_no "
	            + "WHERE h.word = ? AND member_id = ? "
	            + "ORDER BY cashbook_date ASC LIMIT ?, ?";
	    
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, word);
			stmt.setString(2, memberId);
			stmt.setInt(3, beginRow);
			stmt.setInt(4, rowPerPage);
			
			System.out.println(word+"<-word cashbookDao");
			System.out.println(memberId+"<-memberId cashbookDao");
			System.out.println(beginRow+"<-beginRow cashbookDao");
			System.out.println(rowPerPage+"<-rowPerPage cashbookDao");
			
			rs = stmt.executeQuery();
	         while(rs.next()) {
	             Map<String, Object> m = new HashMap<String, Object>();
	             m.put("category", rs.getString("category"));
	             m.put("cashbookDate", rs.getString("cashbookDate"));
	             m.put("price", rs.getString("price"));
	             m.put("memo", rs.getString("memo"));
	             m.put("word", rs.getString("word"));
	             m.put("updatedate", rs.getString("updatedate"));
	             m.put("createdate", rs.getString("createdate"));
	             hoList.add(m);
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
		return hoList;
		}	
			
	//반환값 cashbook_no키값
	public int insertCashbook (Cashbook cashbook) {
		
		int cashbookNo = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
        String sql = "INSERT INTO cashbook(member_id, category, cashbook_date, price, memo, updatedate, createdate) VALUES (?,?,?,?,?,NOW(),NOW())";
        
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cashbook.getMemberId());
			stmt.setString(2, cashbook.getCategory());
			stmt.setString(3, cashbook.getCashbookDate());
			stmt.setInt(4, cashbook.getPrice());
			stmt.setString(5, cashbook.getMemo());
			int row = stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				 cashbookNo =rs.getInt(1);
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
		return cashbookNo;
		}	
	
	
	
	//달력에 표시할 수입지출
	public List<Cashbook> selectCashbookLIstByMonth (String memberId, int targetYear, int targetMonth) {
		
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
        String sql = "SELECT cashbook_no cashbookNO, category, price, cashbook_date cashbookDate FROM cashbook WHERE member_id = ? AND YEAR(cashbook_date) =? AND MONTH(cashbook_date) = ? ORDER BY cashbook_date ASC";
        
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			
			 rs = stmt.executeQuery();
			 while (rs.next()) {
				 Cashbook c = new Cashbook();
				 c.setCashbookNo(rs.getInt("cashbookNo"));
				 c.setCategory(rs.getString("category"));
				 c.setCashbookDate(rs.getString("cashbookDate"));
				 c.setPrice(rs.getInt("price"));
				 list.add(c);
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
		return list;
		}	

	//cash리스트 출력 (해당년,월)
	public List<Cashbook> cashbookOneList (String memberId, String cashbookDate) {
		
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    String sql = "SELECT cashbook_no, category, cashbook_date cashbookDate, price, memo, updatedate, createdate FROM cashbook WHERE member_id = ? AND cashbook_date = ? ORDER BY cashbook_date ASC";
	    
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, cashbookDate);
			
			System.out.println(memberId+"<-dao");
			System.out.println(cashbookDate+"<-dao");
			
			 rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 Cashbook c = new Cashbook();
				 c.setCashbookNo(rs.getInt("cashbook_no"));
				 c.setCategory(rs.getString("category"));
				 c.setCashbookDate(rs.getString("cashbookDate"));
				 c.setPrice(rs.getInt("price"));
				 c.setMemo(rs.getString("memo"));
				 c.setUpdatedate(rs.getString("updatedate"));
				 c.setCreatedate(rs.getString("createdate"));
				 list.add(c);
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
		return list;
		}	
	}

