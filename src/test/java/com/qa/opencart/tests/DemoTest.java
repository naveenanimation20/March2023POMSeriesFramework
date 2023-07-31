package com.qa.opencart.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ElementUtil;

public class DemoTest extends BaseTest{
	
	ElementUtil eleUtil;
	
	@BeforeMethod
	public void demoSetup() {
		driver.get("https://classic.crmpro.com/");
		eleUtil = new ElementUtil(driver);
	}
	
	
	@Test
	public void testdemo() {
		eleUtil.doSendKeys(By.name("username"), "testautomation");
		eleUtil.doSendKeys(By.name("password"), "testautomation");
		eleUtil.doClick(By.xpath("//input[@value='Login']"));

	}
	
	
	
	
	
	

}
