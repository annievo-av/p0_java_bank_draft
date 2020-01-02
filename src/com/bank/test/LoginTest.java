package com.bank.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bank.bo.UserBo;
import com.bank.bo.UserBoImpl;
import com.bank.exception.BusinessException;
import com.bank.to.UserAccount;

class LoginTest {

	@Test
	void test(UserAccount userAccount) throws BusinessException {
		userAccount.setUsername("ann");
		userAccount.setPassword("ann");
		UserBo userBo = new UserBoImpl();
		userBo.signup(userAccount);
	}

}
