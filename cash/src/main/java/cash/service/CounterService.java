package cash.service;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import cash.model.*;

public class CounterService {
	private CounterDao counterDao;
	public void addCounter() {
		this.counterDao = new CounterDao();
		Connection conn = null;
		try {
			// conn.setAutoCommit(false);
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			counterDao.insertCounter(conn);
		} catch(Exception e) {
			// conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// conn.commit();
	}
	
	public void modifyCounter() {
		this.counterDao = new CounterDao();
		Connection conn = null;
		try {
			// conn.setAutoCommit(false);
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			counterDao.updateCounter(conn);
		} catch(Exception e) {
			// conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// conn.commit();
	}
	
	public int getCounter() {
		int counter = 0;
		this.counterDao = new CounterDao();
		Connection conn = null;
		try {
			// conn.setAutoCommit(false);
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			counter = counterDao.selectCounterCurdate(conn);
		} catch(Exception e) {
			// conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// conn.commit();
		return counter;
	}
	
	public int getCounterAll() {
		int totalCounter = 0;
		this.counterDao = new CounterDao();
		Connection conn = null;
		try {
			// conn.setAutoCommit(false);
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/cash","root","java1234");
			totalCounter = counterDao.selectCounterAll(conn);
		} catch(Exception e) {
			// conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// conn.commit();
		return totalCounter;
	}
}