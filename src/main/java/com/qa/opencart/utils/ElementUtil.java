package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

//import CustomException.FrameworkException;

public class ElementUtil {

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;


	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	
	@Step("...entering value: {1}  in locator: {0}")
	public void doSendKeys(By locator, String value) {

		if (value == null) {
			System.out.println("value can not be null while using sendKeys method");
//			throw new FrameworkException("VALUECANNOTBENULL");
		}

		getElement(locator).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text is ====>" + eleText);
		return eleText;
	}
	
	public WebElement getLinkEleByText(String linkText) {
		return driver.findElement(By.linkText(linkText));
	}

	public boolean checkElementIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public String getElementAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

	public boolean checkElementIsDisabled(By locator) {
		String disabledValue = getElement(locator).getAttribute("disabled");
		if (disabledValue.equals("true")) {
			return true;
		}
		return false;
	}

	@Step("getting element for locator: {0}")
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();// pc=0
		for (WebElement e : eleList) {
			String text = e.getText();
			if (!text.isEmpty()) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public void clickOnElement(By locator, String linkText) {
		List<WebElement> linksList = getElements(locator);
		System.out.println("total number of links = " + linksList.size());

		for (WebElement e : linksList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(linkText)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchLocator, By searchSuggLocator, String searchKey, String suggName)
			throws InterruptedException {
		doSendKeys(searchLocator, searchKey);
		Thread.sleep(4000);
		List<WebElement> suggList = getElements(searchSuggLocator);
		System.out.println(suggList.size());
		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggName)) {
				e.click();
				break;
			}
		}
	}

	// ****************************Drop Down Utils************************//
	public void doSelectDropDownByIndex(By locator, int index) {
		if (index < 0) {
			System.out.println("please pass the right (+ve) index");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectDropDownByVisibleText(By locator, String visibleText) {
		if (visibleText == null) {
			System.out.println("please pass the right visible text and it can not be null");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectDropDownByValue(By locator, String value) {
		if (value == null) {
			System.out.println("please pass the right visible text and it can not be null");
			return;
		}
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public List<String> getDropDownTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsTextList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			optionsTextList.add(text);
		}
		return optionsTextList;
	}

	public void doSelectDropDownValue(By locator, String dropDownValue) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	public void doSelectDropDownValueUsingLocator(By locator, String dropDownValue) {
		List<WebElement> optionsList = getElements(locator);
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(dropDownValue)) {
				e.click();
				break;
			}
		}
	}

	// ****************Actions utils********************//
	
	
	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}
	
	public void doActionsSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}
	
	

	public void selectRightClickOption(By contextMenuLocator, String optionValue) {
		act.contextClick(getElement(contextMenuLocator)).perform();
		By optionLocator = By.xpath("//*[text()='" + optionValue + "']");
		doClick(optionLocator);
	}

	/**
	 * this method will handle the menu upto two levels
	 * @param level1MenuLocator
	 * @param level2MenuLocator
	 * @throws InterruptedException
	 */
	public void multiLevelMenuHandling(By level1MenuLocator, By level2MenuLocator) throws InterruptedException {
		act.moveToElement(getElement(level1MenuLocator)).perform();
		Thread.sleep(1500);
		doClick(level2MenuLocator);
	}
	
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3, String level4) throws InterruptedException {
		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level3)).perform();
		Thread.sleep(1500);
		getLinkEleByText(level4).click();

	}
	
	public void multiLevelMenuHandling(By level1Locator, String level2, String level3) throws InterruptedException {
		act.moveToElement(getElement(level1Locator)).perform();
		Thread.sleep(1500);
		act.moveToElement(getLinkEleByText(level2)).perform();
		Thread.sleep(1500);
		getLinkEleByText(level3).click();
	}
	
	
	//**************WaitUtils*************************//
	/**
	 * An expectation for checking that an element is present on the DOM of a page. 
	 * This does not necessarily mean that the element is visible.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public WebElement waitForElementPresence(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 *  Visibility means that the element is not only displayed 
	 *  but also has a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	@Step("....waiting for element is visible for locator: {0} with timeout: {1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	/**
	 * An expectation for checking that all elements present on the web page that match the locator are visible.
	 *  Visibility means that the elements are not only displayed 
	 *  but also have a height and width that is greater than 0.
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	
	/**
	 * An expectation for checking that the title contains a case-sensitive substring.
	 * @param titleFraction
	 * @param timeOut
	 * @return
	 */
	public String waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
				return driver.getTitle();
			} else {
				System.out.println(titleFraction + " title value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(titleFraction + " title value is not present...");
			return null;
		}
	}
	
	@Step("waiting for the title and capture the title....")
	public String waitForTitleIs(String titleValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.titleIs(titleValue))) {
				return driver.getTitle();
			} else {
				System.out.println(titleValue + " title value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(titleValue + " title value is not present...");
			return null;
		}
	}
	
	/**
	 * An expectation for the URL of the current page to contain specific text.
	 * @param urlFraction
	 * @param timeOut
	 * @return
	 */
	@Step("...waiting for page url and fetching it...url fraction : {0} ")
	public String waitForURLContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlFraction + " url value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(urlFraction + " url value is not present...");
			return null;
		}
	}
	
	/**
	 * An expectation for the URL of the current page to be a specific url.
	 * @param urlValue
	 * @param timeOut
	 * @return
	 */
	public String waitForURLToBe(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
				return driver.getCurrentUrl();
			} else {
				System.out.println(urlValue + " url value is not present...");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(urlValue + " url value is not present...");
			return null;
		}
	}

	
	/**
	 * An expectation for the alert (JS) to be appeared on the page.
	 * @param timeOut
	 * @return
	 */
	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public Alert waitForJSAlert(int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
		

	
	
	public boolean waitForNumberOfBrowserWindows(int timeOut, int numberOfWindowsToBe) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindowsToBe));
	}
	
	
	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameByFrameNameOrID(String frameNameOrID, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
	}
	
	public void waitForFrameByFrameElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	//***********************FluentWait Utils**************//
	
	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("----time out is done...element is not found..." + locator);	

			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}
	
	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage("----time out is done...element is not found..." + locator);	

			return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}
		
	public Alert waitForJSAlertWithFluentWait(int timeOut, int pollingTime) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoAlertPresentException.class)
				.withMessage("----time out is done...Js alert is not found...");
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void waitForFrameWithFluentWait(String frameNameOrID, int timeOut, int pollingTime) {
		
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchFrameException.class)
				.withMessage("----time out is done...Frame is not found...with name or id: " + frameNameOrID);
		 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrID));
	}
	
	public void waitForElementAndEnterValue(By locator, int timeOut, int pollingTime, String value) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait
			.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
					.withMessage("----time out is done...element is not found..." + locator)
						.until(ExpectedConditions.presenceOfElementLocated(locator))
							.sendKeys(value);
	}
	
	public void waitForElementAndClick(By locator, int timeOut, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait
			.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
					.withMessage("----time out is done...element is not found..." + locator)
						.until(ExpectedConditions.presenceOfElementLocated(locator))
							.click();
	}
	
	//**************Custom Waits**************//
	
	public WebElement retryingElement(By locator, int timeOut) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {//10
			try {
				element = getElement(locator);
				System.out.println("element is found...." + locator + " in attempt " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("element is not found...." + locator + " in attempt " + attempts);
				try {
					Thread.sleep(500);//default polling time = 500 ms
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}	
			}
			attempts++;

		}

		if (element == null) {
			System.out.println("element is not found....tried for " + timeOut  + " secs " + " with the interval of " + 500 + " milli secs" );
		}

		return element;

	}
	
	
	public WebElement retryingElement(By locator, int timeOut, long pollingTime) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {//10
			try {
				element = getElement(locator);
				System.out.println("element is found...." + locator + " in attempt " + attempts);
				break;

			} catch (NoSuchElementException e) {
				System.out.println("element is not found...." + locator + " in attempt " + attempts);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}	
			}
			attempts++;

		}

		if (element == null) {
			System.out.println("element is not found....tried for " + timeOut  + " secs " + " with the interval of " + pollingTime + " mill secs" );
		}

		return element;

	}
	
	
	public boolean isPageLoaded(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'")).toString();
		return Boolean.parseBoolean(flag);
	}
	
	
}
