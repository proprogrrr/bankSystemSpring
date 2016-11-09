package com.cjon.bank;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.service.BankService;

public class Main {

	public static void main(String[] args) {
		
		String config = "classpath:applicationCtx.xml";
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		
		ctx.load(config);
		
		ctx.refresh();
	
		Scanner s = new Scanner(System.in);
		int menu = 0;

		do {
			System.out.println("--- 은행 시스템 ---");
			System.out.println("1. 입금");
			System.out.println("2. 출금");
			System.out.println("3. 이체");
			System.out.println("4. 종료");
			System.out.print("사용할 메뉴를 입력하세요 ==> ");
			String menuString = s.nextLine();
			menu = Integer.parseInt(menuString) ;
			if (menu == 1) {
				System.out.println("-- 입금업무입니다. --");
				System.out.print("입금할 사람ID를 입력하세요 ==> ");
				String id = s.nextLine();
				System.out.print("입금할 금액을 입력하세요 ==> ");
				String moneyString =s.nextLine(); 
				int money = Integer.parseInt(moneyString);
				// 데이터의 전달을 위해서 DTO객체를 생성
				BankDTO dto = ctx.getBean("dto", BankDTO.class);
				dto.setUserid(id);
				dto.setBalance(money);
				// 로직처리를 위해서 Sevice객체를 생성
//				BankService service = new BankService(); 
				BankService service = ctx.getBean("service", BankService.class);
				// DTO를 통해서 data를 넘겨주고 data를 받아오기 
				dto = service.deposit(dto);
				System.out.println("처리된 결과는 다음과 같습니다.");
				System.out.println("userID : " + dto.getUserid() + ", 잔액 : " + dto.getBalance());
				

			} else if (menu == 2) {
				System.out.println("-- 출금업무입니다. --");
				System.out.print("출금할 사람ID를 입력하세요 ==> ");
				String id = s.nextLine();
				System.out.print("출금할 금액을 입력하세요 ==> ");
				String moneyString =s.nextLine(); 
				int money = Integer.parseInt(moneyString);
				// 데이터의 전달을 위해서 DTO객체를 생성
				BankDTO dto = ctx.getBean("dto", BankDTO.class);
				dto.setUserid(id);
				dto.setBalance(money);
				// 로직처리를 위해서 Sevice객체를 생성
				BankService service = ctx.getBean("service", BankService.class);
				// DTO를 통해서 data를 넘겨주고 data를 받아오기 
				dto = service.withdraw(dto);
				System.out.println("처리된 결과는 다음과 같습니다.");
				System.out.println("userID : " + dto.getUserid() + ", 잔액 : " + dto.getBalance());
				
				
			} else if (menu == 3) {
				System.out.println("-- 이체업무입니다. --");
				System.out.print("출금할 사람ID를 입력하세요 ==> ");
				String id1 = s.nextLine();
				System.out.print("입금할 사람ID를 입력하세요 ==> ");
				String id2 = s.nextLine();
				System.out.print("이제 금액을 입력하세요 ==> ");
				String moneyString =s.nextLine(); 
				int money = Integer.parseInt(moneyString);
				// 데이터의 전달을 위해서 DTO객체를 생성
				BankDTO dto1 = ctx.getBean("dto", BankDTO.class); // 출금 처리를 위한 DTO
				BankDTO dto2 = ctx.getBean("dto", BankDTO.class); // 입금 처리를 위한 DTO
				dto1.setUserid(id1);
				dto1.setBalance(money);

				dto2.setUserid(id2);
				dto2.setBalance(money);
				// 로직처리를 위해서 Sevice객체를 생성
				BankService service = ctx.getBean("service", BankService.class);
				ArrayList<BankDTO> list = service.transfer(dto1,dto2);
				// DTO를 통해서 data를 넘겨주고 data를 받아오기 
				dto1 = list.get(0);
				dto2 = list.get(1);
				for(BankDTO temp : list){
					System.out.println(temp.getUserid());
				}
				System.out.println("처리된 결과는 다음과 같습니다.");
				System.out.println("출금된 userID : " + dto1.getUserid() + ", 잔액 : " + dto1.getBalance());
				System.out.println("입금된 userID : " + dto2.getUserid() + ", 잔액 : " + dto2.getBalance());
				
			} else if (menu == 4) {
				System.out.println("-- 시스템을 종료합니다. --");
			}
		} while (menu != 4);

		s.close();
		ctx.close();
		
	}

}
