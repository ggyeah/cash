package cash.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;

/**
 * Servlet implementation class AddMemberController
 */
@WebServlet("/addMember")
public class AddMemberController extends HttpServlet {
	
	// 회원가입 홈
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	if(session.getAttribute("loginMember") != null) {
		response.sendRedirect(request.getContextPath()+"/cashbook");
		return;
		}
	request.getRequestDispatcher("/WEB-INF/view/addMember.jsp").forward(request, response);
	}
	
	// 회원가입 액션
	@Override	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	if(session.getAttribute("loginMember") == null
		||request.getParameter("memberId") ==""
		||request.getParameter("memberPw") =="") {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		// 중복 확인 로직을 수행하고 결과를 얻어옴
		MemberDao memberDao = new MemberDao();
		boolean isDuplicate = memberDao.isDuplicateId(memberId);

		// 결과에 따라 메시지 출력
		if (isDuplicate) {
			// 중복된 아이디인 경우
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('이미 사용 중인 아이디입니다.'); window.location.href='" + request.getContextPath() + "/addMember';</script>");
			out.flush();
		} else {
			// 중복되지 않은 경우, 회원가입 처리 진행

		
		Member member = new Member(memberId, memberPw, null, null);
		//회원가입 Dao 호출
		int row = memberDao.insertMember(member);
		if(row==0) {//회원가입실패
		    System.out.println("회원가입 실패");
		    //인코딩
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    out.println("<script>alert('회원가입에 실패했습니다. 사용할 수 없는 아이디입니다.'); window.location.href='" + request.getContextPath() + "/addMember';</script>");
		    out.flush();
		    return;
		} else if(row==1) {//성공
			System.out.println("회원가입성공");
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		} else {
			System.out.println("add member error");
		}
	}
	}

}
}
