package testcases;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecondTestCase {

	private static WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private static StringBuffer verificationErrors = new StringBuffer();

	@Before
	// Before and After class shd be static
	public void setUp() throws Exception{
		driver = new FirefoxDriver();
		baseUrl = "http://www.idocs.com/tags/linking/linking_famsupp_114.html";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}


	public void waitForPageToLoad(final String title) {
		(new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().equals(title);
			}
		});
	}


	// Assert title in  a page
	@Ignore
	@Test
	public void assertionTest() throws Exception  {

		driver.get("http://www.google.com/");
		driver.findElement(By.id("gbqfq")).clear();
		driver.findElement(By.id("gbqfq")).sendKeys("selenium");
		driver.findElement(By.linkText("Selenium - Web Browser Automation")).click();
		//waitForPageToLoad("Selenium - Web Browser Automation");
		WebElement header = driver.findElement(By.xpath("//*[@id='header']/h1/a"));
		String result = header.getText();
		System.out.println("result" +result);
		//driver.getCurrentUrl();
		try{

			assertEquals(driver.getTitle(), "Selenium - Web Browser Automation" );
			System.out.println("after assert");
			//Assert.assertEquals(expected, actual);

		}catch(Throwable e){
			System.out.println("Error: "+ e);
			System.out.println("Error Encoutered");
			verificationErrors.append(e.toString());
		}
		System.out.println("B");
	}

	//		try {
	//			waitForPageToLoad("Selenium - Web Browser Automation");
	//			assertEquals("Selenium - Web Browser Automation", driver.getTitle());
	//			//    "Selenium - Web Browser Automation"
	//		} 


	// click checkboxes in dropdown menu
	@Ignore
	@Test 
	public void clickCheckboxesTest() throws Exception{

		driver.get("http://www.jobserve.com/us/en/Job-Search/");

		driver.findElement(By.cssSelector("span.ui-dropdownchecklist-text")).click();
		driver.findElement(By.id("ddcl-selInd-i0")).click();
		String  id_full = "ddcl-selInd-i";

		for(int i=1; i<=10; i++){
			driver.findElement(By.id(id_full + i)).click();
		}

		Thread.sleep(3000);


	}
	
	// Click Checkboxes by name
	@Test 
	public void clickCheckboxesByNameTest() {

		driver.get("http://www.jobserve.com/us/en/Job-Search/");
		
		WebElement select = driver.findElement(By.cssSelector("span.ui-dropdownchecklist-text"));
		try{
			List<WebElement> checkboxnames = select.findElements(By.xpath("//label[contains(@for, 'ddcl-selInd-i')]"));
			
			//ul[@class='featureList' and contains(li, 'Type')]
			for(WebElement checkboxname: checkboxnames) {
				String text = checkboxname.getText();
				System.out.println("text is " + text);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		
//		driver.findElement(By.id("ddcl-selInd-i0")).click();
//		String  id_full = "ddcl-selInd-i";
//
//		for(int i=1; i<=10; i++){
//			driver.findElement(By.id(id_full + i)).click();
//		}

		//Thread.sleep(3000);


	}

	// Drop-down selection
	@Ignore
	@Test 
	public void dropdownSelectionTest() throws Exception{

		driver.get("http://www.idocs.com/tags/linking/linking_famsupp_114.html");
		WebElement select = driver.findElement(By.name("gourl"));
		//WebElement res = select.findElements(By.tagName("option"));
		//System.out.println(res.getText());
		List<WebElement> options = (List<WebElement>) select.findElements(By.tagName("option"));

		for (WebElement option: options){
			System.out.println(option.getText());
			if("Ninth Wonder".equals(option.getText())){
				option.click();
				driver.findElement(By.cssSelector("input[type=\"SUBMIT\"]")).click();
				Thread.sleep(1000);
			}
		}

	}

	// Pop-up window test
	@Ignore
	@Test
	public void popupWindowTest() throws Exception{

		driver.get("http://www.entheosweb.com/website_design/pop_up_windows.asp");
		String parentWindowHandle = driver.getWindowHandle(); // save the current window handle.
		System.out.println(parentWindowHandle);

		WebDriver popup = null;
		driver.findElement(By.xpath("//html/body/div[1]/div[4]/div[2]/table[2]/tbody/tr[1]/td[1]/a[1]")).click();

		Set<String> windowIterator = driver.getWindowHandles();
		System.out.println(windowIterator);

		for (String windowHandle : windowIterator) { 
			popup = driver.switchTo().window(windowHandle);
			System.out.println(popup.getTitle());
		}

		for (String windowHandle : windowIterator) { 
			popup = driver.switchTo().window(windowHandle);
			System.out.println(popup.getTitle());
			if (popup.getTitle().equals("Preview Business Template 1")) {
				popup.close();
				driver.switchTo().window(parentWindowHandle);
				break;
			}
		}


	}


	// Alert window test
	@Ignore
	@Test
	public void alertWindowTest() throws Exception{

		driver.get("http://www.quackit.com/javascript/javascript_alert_box.cfm");
		driver.findElement(By.xpath("//*[@id='content']/table[1]/tbody/tr[2]/td[2]/input")).click();
		try {

			//Now once we hit AlertButton we get the alert	
			Alert alert = driver.switchTo().alert();	

			//Text displayed on Alert using getText() method of Alert class
			String AlertText = alert.getText();
			
			System.out.println("Expected" + AlertText);

			//accept() method of Alert Class is used for ok button
			alert.accept();

			//Verify Alert displayed correct message to user
			assertEquals("Thanks... I feel much better now!", AlertText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Confirmation window test
	@Ignore
	@Test
	public void confirmationWindowTest() throws Exception{

		driver.get("http://www.quackit.com/javascript/codes/javascript_confirm.cfm");
		WebElement res = driver.findElement(By.xpath("//*[@id='content']/table[1]/tbody/tr[2]/td[2]/input"));
		String data = res.getAttribute("value");
		System.out.println("data :" + data);
		assertEquals("Click me...", data);
		
		try {
			
			driver.findElement(By.xpath("//*[@id='content']/table[1]/tbody/tr[2]/td[2]/input")).click();

			//Now once we hit AlertButton we get the alert	
			Alert alert = driver.switchTo().alert();	

			//Text displayed on Alert using getText() method of Alert class
			String AlertText = alert.getText();
			
			System.out.println("Expected" + AlertText);

			//accept() method of Alert Class is used for ok button
			alert.dismiss();

			//Verify Confirmation message displayed to the user
			assertEquals("Did you really mean to click the button?", AlertText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Prompt Window
	
	@Ignore
	@Test
	public void PromptWindowTest() throws Exception{

		driver.get("http://www.quackit.com/javascript/codes/javascript_prompt.cfm");
		WebElement res = driver.findElement(By.xpath("//input[@type = 'button']"));
		String data = res.getAttribute("value");
		System.out.println("data :" + data);
		assertEquals("Click me...", data);
		
		try {
			
			driver.findElement(By.xpath("//input[@type = 'button']")).click();

			//Now once we hit AlertButton we get the alert	
			Alert alert = driver.switchTo().alert();	
			
			alert.sendKeys("Ramya");
			
			Thread.sleep(3000);

			//Text displayed on Alert using getText() method of Alert class
			String AlertText = alert.getText();
			
			
			System.out.println("Expected" + AlertText);

			//accept() method of Alert Class is used for ok button
			alert.accept();

			//Verify Confirmation message displayed to the user
			assertEquals("What is your favorite website?", AlertText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@After
	public void tearDown() throws Exception {
		driver.close();
		String verificationErrorString = verificationErrors.toString();
		System.out.println(verificationErrorString);
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}



}



