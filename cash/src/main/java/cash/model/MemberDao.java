package cash.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cash.vo.Member;

public class MemberDao {
	//회원가입시 아이디 중복확인
	public boolean isDuplicateId(String memberId) {
        boolean isDuplicate = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM member WHERE member_id = ?";
        
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			
			 rs = stmt.executeQuery();
			 if (rs.next()) {
	                int count = rs.getInt(1);
	                if (count > 0) {
	                    isDuplicate = true;
	                }
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
		return isDuplicate;
	}	
	//회원정보 수정
	public int modifyMember(Member member) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "UPDATE member SET member_pw =PASSWORD(?),updatedate = now() WHERE member_id=?";
		int row = 0;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberPw());
			stmt.setString(2, member.getMemberId());
			
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
	
	//회원 탈퇴
	public int removeMember(Member member) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "DELETE FROM member WHERE member_id=? and member_pw=PASSWORD(?)";
		int row = 0;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			
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
	//회원 상세정보
	public Member selectMemberOne(String memberId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id, member_pw, updatedate, createdate from member where member_id = ?";
		Member member = null;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
			     member = new Member();
		         member.setMemberId(rs.getString("member_id"));
		         member.setMemberPw(rs.getString("member_pw"));
		         member.setUpdatedate(rs.getString("updatedate"));
		         member.setCreatedate(rs.getString("createdate"));
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
		return member;
	}
	
	
	//회원가입 메서드
	public int insertMember(Member member) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "INSERT INTO member VALUE(?,PASSWORD(?),NOW(),NOW())";
		int row = 0;
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			
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
	
	
	
	// 로그인 메서드
	public Member selectMemberById(Member paramMember) {
		Member returnMember = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id = ? AND member_pw = password(?)";
		
		try { 
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash", "root", "java1234");
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
			     returnMember = new Member();
		         returnMember.setMemberId(rs.getString("memberId"));
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
		return returnMember;
	}
}
