package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Member;


@WebServlet("/removeCashbook")
public class RemoveCashbookController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			//세션값 저장
			Member loginMember = (Member)(session.getAttribute("loginMember"));
			String memberId = loginMember.getMemberId();
			//받아온값 저장
			int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
			String cashbookDate = request.getParameter("cashbookDate");
			//디버깅
			System.out.println(cashbookNo+"<-cashbookNo removecontroller");
			System.out.println(cashbookNo+"<-cashbookNo removecontroller");
			System.out.println(cashbookNo+"<-cashbookNo removecontroller");
			// 1) 해시태그먼저 삭제
			HashtagDao hashtagDao = new HashtagDao();
			int removerow = hashtagDao.removeHashtag(cashbookNo);
				if (removerow == 1) { 
					System.out.println("해시태그 삭제 성공");
				}else if (removerow == 0) { 
					System.out.println("해시태그 삭제 실패");
				}
            //2)cashbook 삭제
            CashbookDao cashbookDao = new CashbookDao();
            int row = cashbookDao.removeCashbook(memberId, cashbookNo);   
				if (row == 1) { 
					System.out.println("cashbook 삭제 성공");
				}else if (row == 0) { 
					System.out.println("cashbook 삭제 실패");
				} 	
			response.sendRedirect(request.getContextPath() + "/cashbookOne?cashbookDate=" + cashbookDate);	
	  } else if(session.getAttribute("loginMember") == null) {// 로그인 상태가 아니면 로그인으로
			response.sendRedirect(request.getContextPath()+"/login");
      }
		
	}
}
