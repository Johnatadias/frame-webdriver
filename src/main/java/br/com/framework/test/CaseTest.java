package br.com.framework.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.framework.core.webdriver.DriverManagerFactory;
import br.com.framework.core.webdriver.enums.DriverType;

public class CaseTest {
	
	@Before
	public void setup() {
		Context.setup(DriverType.CHROME);
	}
	
	@Test
	public void test() {
	}
	
	@After
	public void tearDown() {
		DriverManagerFactory.tearDownCurrentManagerIfExistent();
	}
}
