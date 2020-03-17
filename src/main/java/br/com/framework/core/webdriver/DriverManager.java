package br.com.framework.core.webdriver;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

	protected WebDriver driver;
	
	public WebDriver getDriver() {
		if(driver == null)
			createDriver();
		return driver;
	}
	
	abstract void createDriver();
	
	public void quitDriver() {
		if(driver != null) 
			driver.close();
			driver.quit();
			driver = null;
	}
}
