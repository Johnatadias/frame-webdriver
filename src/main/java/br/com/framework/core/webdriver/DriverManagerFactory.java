package br.com.framework.core.webdriver;

import br.com.framework.core.webdriver.enums.DriverType;

public class DriverManagerFactory {
	
	private static DriverManager driver;
	private static DriverType driverType;
	
	public static DriverManager createDriver(DriverType type) {
		if (checkIfManagerOfTypeIsCreated(type)) {
			return driver;
		}
		
		tearDownCurrentManagerIfExistent();
		
		switch (type) {
		case CHROME:
			driver = new ChromeManager();
			driverType = DriverType.CHROME;
			break;
		case FIREFOX:
			//driver = new FirefoxDriver();
			driverType = DriverType.FIREFOX;
			break;
		default:
			throw new RuntimeException("DriverType not found");
		}
		return driver;
	}
	
	public static void tearDownCurrentManagerIfExistent() {
		if (driver != null) 
			driver.quitDriver();
	}
	
	private static boolean checkIfManagerOfTypeIsCreated(DriverType type) {
		return (driverType == type && driver != null);
	}
}
