package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

@WebServlet("/removeMember")
public class RemoveMemberController extends HttpServlet {
	// 회원탈퇴전 비밀번호 입력폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
			}
		request.getRequestDispatcher("/WEB-INF/view/removeMember.jsp").forward(request, response);
	}
	// 회원탈퇴
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
		    Member loginMember = (Member) session.getAttribute("loginMember");
		    String memberPw = request.getParameter("memberPw");

		    Member member = new Member(loginMember.getMemberId(), memberPw, null, null); // 요청값을 하나의 데이터로 만들어냄
		
		// 회원탈퇴 Dao
		MemberDao memberDao = new MemberDao();
		int row = memberDao.removeMember(member);
		if(row==0) {//탈퇴실패
			System.out.println("탈퇴실패");
			response.sendRedirect(request.getContextPath()+"/removeMember");
			return;
		} else if(row==1) {//성공
			System.out.println("탈퇴성공");
			//세션값 지우기
			session.invalidate();
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		} else {
			System.out.println("remove member error");
		}
		}
	}

}
