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


@WebServlet("/memberOne")
public class MemberOneController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {// 로그인 상태가 아니면 로그인으로
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		//세션에 있는 로그인 아이디 받아오기
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		
		//모델값 구하기(dao 메서드 호출)
		MemberDao memberDao = new MemberDao();
		Member member = memberDao.selectMemberOne(loginMember.getMemberId());
		//Member 출력하는(포워딩대상) memberOne.jsp 에도 공유되어야한다
		//request가 공유되니까 request안에 넣어서 공유
		request.setAttribute("member", member);
		
		//memberOne.jsp 포워딩
		request.getRequestDispatcher("/WEB-INF/view/memberOne.jsp").forward(request, response);
	}
	
}
