package com.cjon.bank;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.service.BankService;


public class Main {
	
	public static void main(String[] args) {
		
		String config = "classpath:applicationCtx.xml";
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load(config);
		ctx.refresh();

		//서비스 객체 생성!!
		BankService service = ctx.getBean("bankService", BankService.class);
		BankDTO dto = ctx.getBean("dto", BankDTO.class);
		BankDTO dto1 = ctx.getBean("dto", BankDTO.class);
		BankDTO dto2 = ctx.getBean("dto", BankDTO.class);

		
		//입력
		Scanner s = new Scanner(System.in);
		int menu = 0;
		
		do{
			System.out.println("--은행 시스템 ---");
			System.out.println("1. 입금");
			System.out.println("2. 출금");
			System.out.println("3. 이체");
			System.out.println("4. 종료");	
			System.out.println("사용 할 메뉴를 입력하세요 ==>");
			
			String menuString = s.nextLine();
			menu = Integer.parseInt(menuString);

			if(menu==1){
				
				System.out.println("---입금 업무입니다---");
				System.out.println("입금할 사람ID를 입력하세요.==>");
				String id = s.nextLine();
				
				System.out.println("입금 할 금액을 입력하세요==>");
				String moneyString = s.nextLine();
				int money = Integer.parseInt(moneyString);
				//로직처리를 위해서 Service객체를 생성
				dto.setUserid(id);
				dto.setBalance(money);
				
				service.deposit(dto);
				System.out.println("처리된 결과는 다음과 같습니다.");
				System.out.println("userid : " + dto.getUserid()+" , 잔액 : "+dto.getBalance());	
			}
			
			if(menu==2){
				System.out.println("---출금 업무입니다---");
			
				System.out.println("출금 할 사람ID를 입력하세요.==>");
				String id = s.nextLine();
				
				System.out.println("출금 할 금액을 입력하세요==>");
				String moneyString = s.nextLine();
				int money = Integer.parseInt(moneyString);
				//로직처리를 위해서 Service객체를 생성
				dto.setUserid(id);
				dto.setBalance(money);
				
				
				service.withdraw(dto);
				System.out.println("처리된 결과는 다음과 같습니다.");
				System.out.println("userid : " + dto.getUserid()+" , 잔액 : "+dto.getBalance());	
			}
			
			if(menu==3){
				System.out.println("---이체 업무입니다---");
				System.out.println("출금 할 사람ID를 입력하세요.==>");
				String id1 = s.nextLine();
				System.out.println("입금 할 사람ID를 입력하세요.==>");
				String id2 = s.nextLine();
				System.out.println("이체금액을 입력하세요 ==>");
				String moneyString = s.nextLine();
				int money = Integer.parseInt(moneyString);
				//로직처리를 위해서 Service객체를 생성
				dto1.setUserid(id1);
				dto1.setBalance(money);
				
				dto2.setUserid(id2);
				dto2.setBalance(money);
				
				
				ArrayList<BankDTO> list = service.transfer(dto1,dto2);
				/*dto1 = service.withdraw(dto1);
				dto2 = service.deposit(dto2);*/
				
				dto1 = list.get(0); //처리된 출금자에 대한 정보를 가져와요
				dto2 = list.get(1); //처리된 입금자에 대한 정보를 가져와요
				
				System.out.println("처리된 결과는 다음과 같습니다.");
				System.out.println("출금된 userid : " + dto1.getUserid()+" , 잔액 : "+dto1.getBalance());	
				System.out.println("입금된 userid : " + dto2.getUserid()+" , 잔액 : "+dto2.getBalance());
								
			}
			if(menu==4){
				System.out.println("---시스템을 종료합니다.---");
			}
			
		}while(menu !=4);
		
		//로직
		s.close();
		ctx.close();
		
		//출력	
	}
}
