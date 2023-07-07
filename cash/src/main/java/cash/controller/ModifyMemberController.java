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


@WebServlet("/modifyMember")
public class ModifyMemberController extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
			}
		request.getRequestDispatcher("/WEB-INF/view/modifyMember.jsp").forward(request, response);
	}	

	//비밀번호수정
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
		    Member loginMember = (Member) session.getAttribute("loginMember");
		    String memberPw = request.getParameter("memberPw");

		    Member member = new Member(loginMember.getMemberId(), memberPw, null, null); // 요청값을 하나의 데이터로 만들어냄
		
		// 비밀번호수정 Dao
		MemberDao memberDao = new MemberDao();
		int row = memberDao.modifyMember(member);
		if(row==0) {//수정실패
			System.out.println("수정실패");
			response.sendRedirect(request.getContextPath()+"/modifyMember");
			return;
		} else if(row==1) {//성공
			System.out.println("수정성공");
			response.sendRedirect(request.getContextPath()+"/cashbook");
			return;
		} else {
			System.out.println("modify member error");
		}
	}
}
}