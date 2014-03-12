package com.example.tests;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class Untitled 2 extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://localhost:8080/", "*chrome");
	}
	public void testUntitled 2() throws Exception {
		selenium.open("/java_prgs/company_shipping.htm");
		verifyEquals("Intuit | Shipping", selenium.getTitle());
	}
}
