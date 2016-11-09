package com.cjon.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.util.DBTemplate;

public class BankDAO {

	private DBTemplate template;

	public BankDAO(){ }
	
	public BankDAO(DBTemplate template) {
		this.template = template;
	}

	public DBTemplate getTemplate() {
		return template;
	}

	public void setTemplate(DBTemplate template) {
		this.template = template;
	}

	public BankDTO update(BankDTO dto) {

		// Database 처리를 해야 해요!!
		// 일반 JDBC 처리코드가 나오면 되겠죠
		Connection con = template.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC Driver Loading(Mysql용)
			// 3. 사용할 SQL을 작성하고 PreparedStatement를 생성
			String sql = "update bank set balance = balance+? where userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBalance());
			pstmt.setString(2, dto.getUserid());
			// 4. 실행
			int count = pstmt.executeUpdate();
			// 5. 결과처리
			if (count == 1) {
				// 정상적으로 처리되면.
				String sql1 = "select userid, balance from bank where userid=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);

				// IN Parameter처리
				pstmt1.setString(1, dto.getUserid());
				rs = pstmt1.executeQuery();

				if (rs.next()) {
					dto.setBalance(rs.getInt("balance"));
				}
				dto.setResult(true); // 정상처리되었어라는 징표를 DTO에 저장
				try {
					rs.close();
					pstmt1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				dto.setResult(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}

	public BankDTO updateWithdraw(BankDTO dto) {
		// Database 처리를 해야 해요!!
		// 일반 JDBC 처리코드가 나오면 되겠죠
		Connection con = template.getCon();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "update bank set balance = balance-? where userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBalance());
			pstmt.setString(2, dto.getUserid());
			// 4. 실행
			int count = pstmt.executeUpdate();
			// 5. 결과처리
			if (count == 1) {
				// 정상적으로 처리되면.
				String sql1 = "select userid, balance from bank where userid=?";
				PreparedStatement pstmt1 = con.prepareStatement(sql1);

				// IN Parameter처리
				pstmt1.setString(1, dto.getUserid());
				rs = pstmt1.executeQuery();

				if (rs.next()) {
					dto.setBalance(rs.getInt("balance"));
				}
				if (dto.getBalance() < 0) {
					System.out.println("잔액이 부족합니다. ");
					dto.setResult(false);
				} else {
					dto.setResult(true);
				}
				try {
					rs.close();
					pstmt1.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}

}
