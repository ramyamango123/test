package com.example.tests;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class Untitled 8 extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://sboqa.proseries.intuit.com/", "*chrome");
	}
	public void testUntitled 8() throws Exception {
		selenium.open("/commerce/e2e/cart/shopping_cart.jsp");
		selenium.click("css=a.proseriesNavHov");
		selenium.select("id=skus", "label=ProSeries Basic 1040 Federal | 1099935");
		selenium.click("id=submit");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=No Thanks");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=input.coButtonNoSubmit.coCheckoutAndContinue");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=newUserEmail", "ram2@apdqa.com");
		selenium.click("id=buttonContinueAsGuest");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=accountName");
		selenium.type("id=accountName", "automationdebug");
		selenium.type("id=streetAddress", "67 garcia ave");
		selenium.type("id=city", "mountain view");
		selenium.select("id=stateSelect", "label=CA");
		selenium.type("id=postalCode", "94040");
		selenium.type("id=firstName", "ram");
		selenium.type("id=lastName", "nag");
		selenium.click("id=createLogin");
		selenium.type("id=email", "ramnew@apdqa.com");
		selenium.type("id=userName", "automationtest1");
		selenium.type("id=password", "test123");
		selenium.type("id=confirmPassword", "test123");
		selenium.select("id=securityQuestion", "label=regexp:What was the name of your first pet\\?\\s+");
		selenium.type("id=securityAnswer", "pet");
		selenium.selectWindow("name=fbMainContainer");
		selenium.click("id=fbInspectButton");
		selenium.selectWindow("null");
		selenium.click("id=updateCompanyInfo");
	}
}
