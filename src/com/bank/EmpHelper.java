package com.bank;

import java.util.Scanner;

import com.bank.bo.EmpBo;
import com.bank.bo.EmpBoImpl;
import com.bank.bo.UserBo;
import com.bank.bo.UserBoImpl;
import com.bank.exception.BusinessException;

public class EmpHelper {
	Scanner input = new Scanner(System.in);
	UserBo userBo = new UserBoImpl();
	EmpBo empBo = new EmpBoImpl();
	String option;

	public void employeeEntryLogic() throws BusinessException {
		System.out.println("Enter task number according to the below criteria: ");
		System.out.println("1. View Customer Account");
		System.out.println("2. New pending user account");
		System.out.println("3. New pending customer card");
		System.out.println("4. Transaction Log");
		System.out.println("Enter any key to exit!");
		String option = input.nextLine();
		switch (option) {
		case "1":
			empBo.viewCustomerAccount();
			employeeEntryLogic();
			break;
		case "2":
			empBo.pendindAccount();
			employeeEntryLogic();
			break;
		case "3":
			empBo.pendindCard();
			employeeEntryLogic();
			break;
		case "4":
			empBo.viewTransactionLog();
			employeeEntryLogic();
			break;
		default:
			userBo.logout();
		}
	}

}
