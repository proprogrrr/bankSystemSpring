package com.cjon.bank.service;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.util.DBTemplate;

public class BankService {

	private BankDAO dao;
	private DBTemplate template;
	
	public BankService(){}
	
	public BankService(BankDAO dao){
		
		this.dao= dao;
	}

	
	public DBTemplate getTemplate() {
		return template;
	}

	public void setTemplate(DBTemplate template) {
		this.template = template;
	}

	//dao에 대한 getter/setter부분
	public BankDAO getDao() {
		return dao;
	}

	public void setDao(BankDAO dao) {
		this.dao = dao;
	}


	public BankDTO deposit(BankDTO dto){
		//입금에 대한 로직 처리
		//database처리를 위해서 DAO를 생성
		dto = dao.update(dto);
		
		if(dto.isResult()){
			template.commit();
		}else{
			template.rollback();
		}
		try {
			template.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public BankDTO withdraw(BankDTO dto){
		//입금에 대한 로직 처리
		//database처리를 위해서 DAO를 생성
		
		dto = dao.withdraw(dto);
		
		if(dto.isResult()){
			template.commit();
		}else{
			template.rollback();
		}
		
		try {
			template.getCon().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	public ArrayList<BankDTO> transfer(BankDTO dto1, BankDTO dto2) {
		
		
		dto1 = dao.withdraw(dto1);   //출금처리
		dto2 = dao.update(dto2); //입금처리
		
		if(dto1.isResult()==true && dto1.isResult()) template.commit(); //출금 입금 처리가 모두 성공하면!!
		else template.rollback(); //출금 입금 하나라도 실패하면 rollback
		
		ArrayList<BankDTO> list = new ArrayList<BankDTO>();
		list.add(dto1);
		list.add(dto2);
		
		return list;
		
	}	
}