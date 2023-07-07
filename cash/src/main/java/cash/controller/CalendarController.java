package cash.controller;

import java.io.IOException;
import java.util.Calendar;
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
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/calendar")
public class CalendarController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 인증검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {// 로그인 상태가 아니면 로그인으로
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		//세션값 저장
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = loginMember.getMemberId();
		
		//view에 넘겨줄 달력정보(모델값)
		Calendar firstDay = Calendar.getInstance(); //오늘날짜
		
		//오늘날짜로 기본 셋팅
		int targetYear = firstDay.get(Calendar.YEAR);
		int targetMonth = firstDay.get(Calendar.MONTH);
		int targetDay = firstDay.get(Calendar.DAY_OF_MONTH);
		firstDay.set(Calendar.DATE, 1);
		
	
		// 출력하고자 하는 년도와 월이 매개값으로 넘어 왔다면 
		// 사용자가 요청한 값이 넘어오면
		if(request.getParameter("targetYear") != null
				&& request.getParameter("targetMonth") != null) {
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
			firstDay.set(Calendar.YEAR, targetYear);
			firstDay.set(Calendar.MONTH, targetMonth);	
			//API에서 자동으로 Calendar.MONTH 값으로 12가 입력되면 월 1, 년 +1년
			//API에서 자동으로 Calendar.MONTH 값으로 -1가 입력되면 월 12, 년 -1년
			// 바뀐년도와 월 정보를 다시셋팅
			targetYear = firstDay.get(Calendar.YEAR);
	        targetMonth = firstDay.get(Calendar.MONTH);
	        targetDay = firstDay.get(Calendar.DAY_OF_MONTH);
		}
		
		//달력출력시 시작공백 -> 1일 날짜의 요일(일 1, 월 2,... 토 6) - 1 
		int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK)- 1 ;  
		System.out.println(beginBlank+"<-beginBlank");
		
		//출력되는 월의 마지막날짜
		int lastDate = firstDay.getActualMaximum(Calendar.DATE);
		System.out.println(lastDate+"<-lastDate");
		
		//달력출력시 마지막 날짜 출력 후 공백 개수 - > 전체 출력 셀의 개수가 7로 나누어 떨어져야 한다
		int endBlank = 0; // 마지막 날짜 출력후 공백개수
		if(((beginBlank + lastDate) % 7 ) != 0) {
			endBlank = 7 - ((beginBlank + lastDate) % 7);
		}
		int totalCell = beginBlank + lastDate + endBlank; // 전체 셀
		System.out.println(totalCell+"<-totalcell");
		System.out.println(endBlank+"<-endBlank");
		
		//모델을 호출(DAO의 
		List<Cashbook> list = new CashbookDao().selectCashbookLIstByMonth(memberId, targetYear, targetMonth);
		
		List<Map<String, Object>> htList = new HashtagDao().selectWordCountByMonth(memberId, targetYear,targetMonth+1);
		
		//뷰에 값넘기기(request 속성)
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("beginBlank", beginBlank);
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalCell", totalCell);
		request.setAttribute("targetDay", targetDay);
		
		request.setAttribute("list", list);
		request.setAttribute("htList", htList);
		
		
		//달력을 출력하는 뷰
		request.getRequestDispatcher("/WEB-INF/view/calendar.jsp").forward(request, response);
	}
}
