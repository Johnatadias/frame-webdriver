package br.com.framework.test;

import br.com.framework.core.utils.StoredActions;
import br.com.framework.core.webdriver.DriverManager;
import br.com.framework.core.webdriver.DriverManagerFactory;
import br.com.framework.core.webdriver.enums.DriverType;

public class Context {
	
	private static DriverManager driverManager;
	private static StoredActions web;

	private Context() {
	}
	
	public static void setup(DriverType type) {
		if(driverManager == null) {
			driverManager = DriverManagerFactory.createDriver(type);
		}
		web = new StoredActions(driverManager.getDriver());
		action().getUrl("http:www.facebook.com");
	}
	
	public static StoredActions action() {
		if(web == null)
			throw new RuntimeException("Context was not setup, call Context.setup()");
		return web;
	}
}
