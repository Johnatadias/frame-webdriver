package br.com.framework.core.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StoredActions {
	
	private WebDriver driver;
	private static final Integer DEFAULTPOLLINGSECONDS = 1;
	private static final Integer DEFAULTTIMEOUTSECONDS = 10;

	public StoredActions(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void getUrl(String url) {
		driver.get(url);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public String getUrlFromPage() {
		return driver.getCurrentUrl();
	}

	public String getHtml() {
		return driver.getPageSource();
	}

	public void clickComumSelenium(WebElement element) {
		element.click();
	}

	public void click(WebElement element) throws InterruptedException {
		try {
			executeJs("arguments[0].click", element);
		} catch (StaleElementReferenceException e) {
			this.clickComumSelenium(element);
		} catch (WebDriverException we) {
			this.sleep(2);
			this.clickComumSelenium(element);
		}
	}

	public void insertText(WebElement element, String value) {
		this.fluentlyWaitUtilVisibility(element).sendKeys(value);
	}

	public void clearAndInsertText(WebElement element, String value) {
		this.fluentlyWaitUtilVisibility(element);
		element.clear();
		element.sendKeys(value);
	}

	public void clearElement(WebElement element) {
		this.fluentlyWaitUtilVisibility(element).clear();
	}

	public String getText(WebElement element) {
		return this.fluentlyWaitUtilVisibility(element).getText();
	}

	public void elementSubmit(WebElement element) {
		element.submit();
	}

	public void selectElementByIndex(WebElement element, int index) {
		try {
			Select drop = new Select(this.fluentlyWaitUtilVisibility(element));
			drop.selectByIndex(index);
		} catch (Exception e) {
			System.out.println("Error select element from index!!!");
		}
	}

	public void selectElementByVisibleText(WebElement element, String text) {
		try {
			Select drop = new Select(this.fluentlyWaitUtilVisibility(element));
			drop.selectByVisibleText(text);
		} catch (Exception e) {
			System.out.println("Error select element from text!!!");
		}
	}

	public void selectElementByValue(WebElement element, String value) {
		try {
			Select drop = new Select(this.fluentlyWaitUtilVisibility(element));
			drop.selectByValue(value);
		} catch (Exception e) {
			System.out.println("Error select element from value: " + e.getMessage());
		}
	}

	public void selectRadioFromList(List<WebElement> listElement, int option) throws InterruptedException {
		try {
			if (option >= 0 && option <= listElement.size()) {
				this.click(listElement.get(option));
			}
		} catch (Exception e) {
			System.out.println("option not exist or element not located: " + e.getMessage());
		}
	}

	public void moveToElement(WebElement element) {
		new Actions(driver).moveToElement(this.fluentlyWaitUtilVisibility(element));
	}

	public void moveToElementAndClick(WebElement element) {
		new Actions(driver).moveToElement(element).click();
	}
	
	/*********************** DATE ****************************************/
	
	public String getDateNow(String formact) {
		return String.valueOf(new SimpleDateFormat(formact).format(new Date()));
	}
	
//	public String getDateNowPlusDaysSelected(Long days) {
//		LocalDate dateNow = LocalDate.now();
//		LocalDate date = dateNow.plusDays(days);
//		return String.valueOf(date);
//	}

	/*********************** JAVASCRIPTEXECUTOR **************************/

	private Object executeJs(String script, Object... args) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(script, args);
	}

	public void scrollToElement(WebElement element) {
		executeJs("Arguments[0].scrollIntoView(true)", element);
	}

	public void scroll(int scroll) {
		executeJs("window.scrollBy(0," + scroll + ")", "");
	}

	/*********************** METODOS DE ESPERA ***************************/

	public void sleep(int seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
	}

	public Boolean elementIsVisible(WebElement element, Integer seconds) {
		try {
			return this.waitForElementToBeClickable(element, seconds) != null;
		} catch (NoSuchElementException e) {
			System.out.println("Elemento nao visivel (NoSuchElementException).");
			return false;
		} catch (StaleElementReferenceException e) {
			System.out.println("Elemento nao visivel (StaleElementReferenceException).");
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public Boolean elementIsClickable(WebElement element, Integer seconds) {
		try {
			return this.waitForElementToBeClickable(element, seconds) != null;
		} catch (NoSuchElementException e) {
			System.out.println("Elemento nao visivel (NoSuchElementException).");
			return false;
		} catch (StaleElementReferenceException e) {
			System.out.println("Elemento nao visivel (StaleElementReferenceException).");
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private WebElement fluentlyWaitUtilVisibility(WebElement element) {
		return (new FluentWait<WebDriver>(driver)) //
				.withTimeout(Duration.ofSeconds(DEFAULTTIMEOUTSECONDS)) //
				.pollingEvery(Duration.ofSeconds(DEFAULTPOLLINGSECONDS)) //
				.ignoring(StaleElementReferenceException.class) //
				.until(ExpectedConditions.visibilityOf(element)); 
	}

	private WebElement waitForElementToBeClickable(WebElement element, int seconds) {
		return new WebDriverWait(driver, seconds) //
				.ignoring(StaleElementReferenceException.class) //
				.ignoring(NoSuchElementException.class) //
				.until(ExpectedConditions.elementToBeClickable(element));
	}

}
