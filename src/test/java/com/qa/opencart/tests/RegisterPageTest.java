package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ExcelUtil;

public class RegisterPageTest extends BaseTest{
	
	
	@BeforeClass
	public void regSetup() {
		regPage = loginPage.navigateToRegisterPage();
	}
	
	
	public String getRandomEmailId() {
		return "openauto"+System.currentTimeMillis()+"@open.com";
	}
	
	
	
	@DataProvider
	public Object[][] getUserRegData() {
		return new Object[][] {
			{"Pooja", "agrawal", "9090909090", "pooja@123", "yes"},
			{"Shubham", "gupta", "9090909011", "shubh@123", "no"},
			{"mitaj", "kumar", "9090909012", "mitaj@123", "yes"},
		};
	}
	
	@DataProvider
	public Object[][] getUserRegSheetData() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}	
	
	
	@Test(dataProvider = "getUserRegSheetData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(regPage.registerUser(firstName, lastName, getRandomEmailId(), telephone,  password,  subscribe));
		
	}
	
	
	

}
