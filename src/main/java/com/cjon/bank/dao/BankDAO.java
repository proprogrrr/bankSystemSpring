package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.util.DBTemplate;

public class BankDAO {

	
	private DBTemplate template;
	
	public BankDAO(){
		
	}
	public BankDAO(DBTemplate template){
		this.template = template;
	}

	public BankDTO update(BankDTO dto) {

		//data baseo 처리를 해야 해요!!
		//일반 JDBC처리 코드가 나오면 되겠죵!
		
		Connection con = template.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try {
			
	
			String sql = "update bank set balance = balance + ? where userid =? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBalance()); //입금액이 첫번째 물음표에 셋팅됨
			pstmt.setString(2, dto.getUserid());
			//4. 실행
			int count = pstmt.executeUpdate();
			//5. 결과처리
			if(count==1){
				//정상적으로 처리되면,
				String sql1 = "select userid,balance from bank where userid=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1, dto.getUserid());
				rs = pstmt1.executeQuery();
				if(rs.next()){
					dto.setBalance(rs.getInt(2)); //최종 업데이트 된값으로 dto객체의 balance 속성을 바꿔줌 !!
				}
				dto.setResult(true);
//				con.commit();
				
				rs.close();
				pstmt1.close();
				
				}else{
					dto.setResult(false);

				}
			//6. 최종적으로 업데이트된 dto 객체를 보내면됨ㅋㅋ
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

	public BankDTO withdraw(BankDTO dto) {

		//data baseo 처리를 해야 해요!!
		//일반 JDBC처리 코드가 나오면 되겠죵!
		

		Connection con = template.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try {
			
			
			String sql = "update bank set balance = balance - ? where userid =? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBalance()); //입금액이 첫번째 물음표에 셋팅됨
			pstmt.setString(2, dto.getUserid());
			//4. 실행
			int count = pstmt.executeUpdate();
			//5. 결과처리
			
			
			if(count==1){
				//정상적으로 처리되면,
				String sql1 = "select userid,balance from bank where userid=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);
				pstmt1.setString(1, dto.getUserid());
				rs = pstmt1.executeQuery();
				if(rs.next()){
					dto.setBalance(rs.getInt(2)); //최종 업데이트 된값으로 dto객체의 balance 속성을 바꿔줌 !!
				}

				if(dto.getBalance()<0){
					System.out.println("예금금액이 작아서 출금 할 수 있어요!");
					dto.setResult(false);
				}else{
					dto.setResult(true);
				}
				
				rs.close();
				pstmt1.close();
				
				}
			
			//6. 최종적으로 업데이트된 dto 객체를 보내면됨ㅋㅋ
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return dto;
	}

}
