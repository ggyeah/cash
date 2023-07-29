package cash.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.MemberDao;
import cash.vo.Member;
import cash.service.CounterService;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private CounterService counterService = null;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember")!= null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		// 오늘접속자, 누적접속자 
		this.counterService = new CounterService();
		
		int counter = counterService.getCounter();
		int totalConter = counterService.getCounterAll();
		
		request.setAttribute("counter", counter);
		request.setAttribute("totalCounter", totalConter);
		
		//쿠키에 저장된 아이디가 있다면 request속성에 저장
		Cookie[] cookies = request.getCookies();
		if (cookies != null) { 
		for(Cookie c : cookies) {
			if(c.getName().equals("loginId") == true) {
					request.setAttribute("loginId", c.getValue());
				}
			}
		}	
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
		}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		Member member = new Member(memberId, memberPw, null, null); // 요청값을 하나의 데이터로 만들어냄
		
		MemberDao memberDao = new MemberDao();
		Member loginMember = memberDao.selectMemberById(member);
		
		if(loginMember == null) { 
			System.out.println("로그인 실패");
			response.sendRedirect(request.getContextPath()+"/login");
			return; // 페이지 돌리고 끝
		}
		// 로그인성공시 세션저장
		HttpSession session = request.getSession();
		System.out.println("로그인 성공");
		session.setAttribute("loginMember", loginMember);
		session.setAttribute("savaloginId", memberId);
		
		//idSave체크값이 넘어 왔다면 
		if(request.getParameter("idSave") != null) {
			Cookie loginIdcookie = new Cookie("savaloginId",memberId);
			//loginIdcookie.setMaxAge(60*60*24); // 초단위
			response.addCookie(loginIdcookie);
		}
		response.sendRedirect(request.getContextPath()+"/calendar");
	}

}
