package cash.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
        System.out.println("실행확인 : ServletContextListener.contextInitialized()");
        
        // application속성 영역에 "currentCounter"속성변수 초기화
        ServletContext application = sce.getServletContext();
        application.setAttribute("currentCounter", 0);
        
        try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MariaDB 드라이버 로딩 실패...");
			e.printStackTrace();
		}
         System.out.println("MariaDB 드라이버 로딩 성공...");
    }
	
}