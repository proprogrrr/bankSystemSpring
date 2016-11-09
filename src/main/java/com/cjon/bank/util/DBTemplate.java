package com.cjon.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTemplate {

	
	private Connection con;
	//data baseo 처리를 해야 해요!!
	//일반 JDBC처리 코드가 나오면 되겠죵!
	
	public DBTemplate(){
		try {
			
			//1. JDBC 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");
			//2. Database 접속
			String url = "jdbc:mysql://localhost:3306/library";
			String id = "jQu";
			String pw = "jQu";
			
			con = DriverManager.getConnection(url,id,pw);			
			con.setAutoCommit(false);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commit(){
		try {
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rollback(){
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getCon(){
		return con;
	}
	
	public void setCon(Connection con){
		this.con = con;
	}
	
	
}
