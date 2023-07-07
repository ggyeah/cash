package cash.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.model.CashbookDao;
import cash.model.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Hashtag;
import cash.vo.Member;


@WebServlet("/addCashbook")
public class AddCashbookController extends HttpServlet {
	//입력폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null
			&& request.getParameter("cashbookDate") == null) {// 로그인 상태가 아니면 로그인으로
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		//세션값 저장
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = loginMember.getMemberId();
		//request 매개값 
		String cashbookDate = (String)request.getParameter("cashbookDate");
		System.out.println(cashbookDate+"<-cashbookDate");
		//뷰에 값넘기기(request 속성)
		request.setAttribute("cashbookDate", cashbookDate);
		request.setAttribute("memberId", memberId);
		//입력폼에서 사용자가 입력
		request.getRequestDispatcher("/WEB-INF/view/addCashbook.jsp").forward(request, response);
	}
	
	//입력액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//입력폼에서 입력된 값 저장
		String memberId = request.getParameter("memberId");
		String category = request.getParameter("category");
		String cashbookDate = request.getParameter("cashbookDate");
		int price = Integer.parseInt(request.getParameter("price"));
		String memo = request.getParameter("memo");
		
		//디버깅
		System.out.println(memberId+"<-memberId");
		System.out.println(category+"<-category");
		System.out.println(cashbookDate+"<-cashbookDate");
		System.out.println(price+"<-price");
		System.out.println(memo+"<-memo");
		
		//cashbook안에 받아온값 셋팅
		Cashbook cashbook = new Cashbook();
		cashbook.setMemberId(memberId);
		cashbook.setCategory(category);
		cashbook.setCashbookDate(cashbookDate);
		cashbook.setPrice(price);
		cashbook.setMemo(memo);
		
		CashbookDao cashbookDao = new CashbookDao();
		int cashbookNo = cashbookDao.insertCashbook(cashbook); //cashbookNo 키값 반환
		//입력실패시
		if(cashbookNo == 0) {
			System.out.println("입력실패");
			response.sendRedirect(request.getContextPath()+"/addCashbook");
			return;
		} else { // 입력성공시 - 해시태그가 있다면 - 해시태그 추출 - 해시태그입력 (반복)
		// 해시태그 추출 알고리즘
		// # # 구디 #구디 #자바
		HashtagDao hashtagDao = new HashtagDao();
		//입력된 메모값을 받아옴
		memo = cashbook.getMemo();
		// memo 문자열 내의 해시태그 앞에 공백을 추가
		String memo2 = memo.replace("#"," #");  // "#구디 #아카데미" -> "#구디 #아카데미"
		
		 Set<String> set = new HashSet<String>(); // 중복된 해시태그방지를 위해 set자료구조를 사용
		//memo2 문자열을 공백을 기준으로 분할
		for(String ht :memo2.split(" ")) {
			// #로 시작하는경우에만
			if (ht.startsWith("#")) {
				// ht 문자열에서 "#"을 제거
				String ht2 = ht.replace("#", "");
				if(ht2.length()>0) {
					set.add(ht2);
					}
				}
			}
			//문자열이 빈 문자열이 아닌 경우에만 해시태그로 처리
			for(String s : set) {
				Hashtag hashtag = new Hashtag();
				hashtag.setCashbookNo(cashbookNo);
				hashtag.setWord(s);
				hashtagDao.insertHashtag(hashtag);
			}
		}
		response.sendRedirect(request.getContextPath() + "/cashbookOne?cashbookDate=" + cashbookDate);
	}
}