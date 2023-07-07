package cash.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Member;

@WebServlet("/cashbookListByTag")
public class CashbookListController extends HttpServlet {

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
		String word = (String)request.getParameter("word");
		// 디버깅
		System.out.println(memberId+"<-memberId CashbookListController");
		System.out.println(word+"<-word CashbookListController");
		
		//페이징
		int totalCnt = new CashbookDao().cashbookByTagCnt(memberId, word); 
		int currentPage = 1; // 현재페이지는 1 넘어온값이 있으면 받아옴
		if (request.getParameter("currentPage") != null){
		  currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		System.out.println(currentPage+"<-currentPage CashbookListController");
		int rowPerPage = 5;
		int beginRow = (currentPage - 1) * rowPerPage; 


		int pagePerPage = 5;
		int lastPage = (totalCnt % rowPerPage == 0) ? (totalCnt / rowPerPage) : (totalCnt / rowPerPage + 1);
		int minPage = ((currentPage - 1) / pagePerPage) * pagePerPage + 1;
		int maxPage = Math.min(minPage + pagePerPage - 1, lastPage);
		
		
		List<Map<String, Object>> hoList = new CashbookDao().selectCashbookListByTag(memberId, word, beginRow, rowPerPage);
		System.out.println(totalCnt+"<------totalCnt");
		request.setAttribute("hoList", hoList);
		request.setAttribute("minPage", minPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("pagePerPage", pagePerPage);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("word", word);
		
		// 출력하는 뷰
		request.getRequestDispatcher("/WEB-INF/view/cashbookListByTag.jsp").forward(request, response);
		}
	}
