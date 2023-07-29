package cash.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cash.service.CounterService;

@WebListener
public class CountListener implements HttpSessionListener {
	private CounterService counterService;
    public void sessionCreated(HttpSessionEvent se)  { 
    	System.out.println(se.getSession().getId() + "의 새로운 세션이 생성되었습니다.");
        // 현재 접속자 수 +1 -> application.attribute
    	ServletContext application = se.getSession().getServletContext();
    	int currentCounter = (Integer)(application.getAttribute("currentCounter"));
    	application.setAttribute("currentCounter", currentCounter+1);
    
    	// 오늘 접속자 수 +1 -> DB
    	this.counterService = new CounterService();
    	int counter = counterService.getCounter(); // 오늘 카운트 수
    	if(counter == 0) {
    		counterService.addCounter();
    	} else {
    		counterService.modifyCounter();
    	}
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
    	System.out.println(se.getSession().getId() + " 세션이 소멸되었습니다.."); 
    	// 현재 접속자 수 -1 -> application.attribute
    	ServletContext application = se.getSession().getServletContext();
    	int currentCounter = (Integer)(application.getAttribute("currentCounter"));
    	application.setAttribute("currentCounter", currentCounter-1);
    }
	
}
