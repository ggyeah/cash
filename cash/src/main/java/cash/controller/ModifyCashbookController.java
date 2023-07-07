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

@WebServlet("/modifyCashbook")
public class ModifyCashbookController extends HttpServlet {
	//수정폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		//세션값 저장
		Member loginMember = (Member)(session.getAttribute("loginMember"));
		String memberId = loginMember.getMemberId();
		//넘어온값 저장
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		
		System.out.println(cashbookNo+"<--cashbookNo");
		//수정할 cashbook값 받아오기 
		CashbookDao cashbookDao = new CashbookDao();
		Cashbook cashbook = cashbookDao.cashbookOne(memberId, cashbookNo);
		// request안에 넣어서 공유
		request.setAttribute("cashbook", cashbook);
		request.setAttribute("cashbookNo", cashbookNo);
		// 수정폼으로 보내기
		request.getRequestDispatcher("/WEB-INF/view/modifyCashbook.jsp").forward(request, response);
	}	
	
	//수정액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") != null) {
			//세션값 저장
			Member loginMember = (Member)(session.getAttribute("loginMember"));
			String memberId = loginMember.getMemberId();
			
			//수정폼에서 받아온 값 저장
			String category = request.getParameter("category");
			String cashbookDate = request.getParameter("cashbookDate");
			int price = Integer.parseInt(request.getParameter("price"));
			String memo = request.getParameter("memo");
			int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
			//디버깅
			System.out.println(category+"<-category");
			System.out.println(cashbookDate+"<-cashbookDate");
			System.out.println(price+"<-price");
			System.out.println(memo+"<-memo");
			System.out.println(cashbookNo+"<-cashbookNo");
			
			
			//cashbook안에 받아온값 셋팅
			Cashbook cashbook = new Cashbook();
			cashbook.setCategory(category);
			cashbook.setCashbookDate(cashbookDate);
			cashbook.setPrice(price);
			cashbook.setMemo(memo);
			cashbook.setCashbookNo(cashbookNo);
			
			//수정 Dao
			CashbookDao cashbookDao = new CashbookDao();
			int row = cashbookDao.modifyCashbook(cashbook, memberId);
			
			if(row==0)  {
				System.out.println("수정실패");
				response.sendRedirect(request.getContextPath()+"/modifyCashbook?memberId="+memberId+"&cashbookNo="+cashbookNo);
				return;
			} else if(row==1) { //수정성공시 (기존에 있던 해시태그를 삭제하고 해시태그 새로 추가)
				HashtagDao hashtagDao = new HashtagDao();
				int removerow = hashtagDao.removeHashtag(cashbookNo);
				if (removerow >=0) { // 삭제가 되거나 안되거나 새로추가
					// 기존해시태그 삭제 성공 -> 새로해시태그 추가
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
				}
			response.sendRedirect(request.getContextPath() + "/cashbookOne?cashbookDate=" + cashbookDate);
		}
	}
}

