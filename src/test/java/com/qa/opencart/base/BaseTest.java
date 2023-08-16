package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	protected WebDriver driver;
	protected Properties prop;
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage regPage;
	DriverFactory df;
	
	protected SoftAssert softAssert;
	
	@Parameters({"browser", "browserversion", "testname"})
	@BeforeTest
	public void setup(String browserName, String browserVersion, String testName) {
		df = new DriverFactory();
		prop = df.initProp();
		
			if(browserName!=null) {//it means passing the browser from testng.xml
				prop.setProperty("browser", browserName);
				prop.setProperty("browserversion", browserVersion);
				prop.setProperty("testname", testName);
			}
		
		
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		softAssert = new SoftAssert();
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	

}
