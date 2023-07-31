package com.qa.opencart.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {
	
	private WebDriver driver;
	private JavascriptExecutor js;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor)this.driver;
	}
	
	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}
	
	
	public void goBackWithJS() {
		js.executeScript("history.go(-1);");
	}
	
	public void goForwardWithJS() {
		js.executeScript("history.go(1);");
	}
	
	public void pageRefreshWithJS() {
		js.executeScript("history.go(0);");
	}
	
	public void generateJSAlert(String mesg) {
		js.executeScript("alert('"+mesg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	
	public void generateJSConfirm(String mesg) {
		js.executeScript("confirm('"+mesg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
	}
	
	public void generateJSPrompt(String mesg, String value) {
		js.executeScript("prompt('"+mesg+"')");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}
	
	
	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}
	
	
	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}

	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0, '" + height + "')");
	}

	
	public void scrollPageDownMiddlepage() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");
	}

	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	/**
	 * example: "document.body.style.zoom = '400.0%'"
	 * @param zoomPercentage
	 */
	public void zoomChromeEdgeSafari(String zoomPercentage) {
		String zoom = "document.body.style.zoom = '"+zoomPercentage+"%'";
		js.executeScript(zoom);
	}
	
	/**
	 * example: "document.body.style.MozTransform = 'scale(0.5)'; ";
	 * @param zoomPercentage
	 */
	public void zoomFirefox(String zoomPercentage) {
		String zoom = "document.body.style.MozTransform = 'scale("+zoomPercentage+")'";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript(zoom);
	}
	
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for (int i = 0; i < 10; i++) {
			changeColor("rgb(0,200,0)", element);// 1
			changeColor(bgcolor, element);// 2
		}
	}

	private void changeColor(String color, WebElement element) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);
		//G->P->G->P

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}

}
