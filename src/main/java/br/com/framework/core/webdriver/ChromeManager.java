package br.com.framework.core.webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeManager extends DriverManager{

	@Override
	void createDriver() {
		try {
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			
		}catch (Exception e) {
			throw new RuntimeException("Not created ChromeDriver()!!!");
		}
	}
}
