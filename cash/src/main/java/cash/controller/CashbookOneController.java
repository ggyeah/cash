package cash.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/cashbookOne")
public class CashbookOneController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 인증검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {// 로그인 상태가 아니면 로그인으로
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		//세션값 저장
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = (String)loginMember.getMemberId();		
		//넘겨받은 값 저장
		String cashbookDate = (String)request.getParameter("cashbookDate");
		// 디버깅
		System.out.println(memberId+"<-memberId");
		System.out.println(cashbookDate+"<-cashbookDate");
		
		CashbookDao cashbookDao = new CashbookDao();
		List<Cashbook> list = cashbookDao.cashbookOneList(memberId, cashbookDate);
		
		//뷰에 값넘기기(request 속성)
		request.setAttribute("list", list);
		request.setAttribute("memberId", memberId);
				
		//cashbook상세를 출력하는 뷰
		request.getRequestDispatcher("/WEB-INF/view/cashbookOne.jsp").forward(request, response);		
	}
}
