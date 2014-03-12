package initiate.estore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Alert;

import com.gargoylesoftware.htmlunit.ScriptException;
import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestResultStatus;
import com.intuit.taf.testing.TestRun;
import common.e2e.objects.Item;

/**  
* Utility class that handles many of the common actions in the Estore UI.
* 
* @author  Jeffrey Walker
* @version 1.0.0 
*/ 
public class EstoreUtil 
{
	private static Logger sLog;
	private ArrayList<Item> lineItemsList;
	
	public EstoreUtil()
	{
		sLog = Logger.getInstance();
		lineItemsList = new ArrayList<Item>();
	}
	
	/**
	 * Launches the Estore offering page and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param estoreURL Estore URL for the page of interest
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchOfferingPage(WebDriver driver,String estoreURL,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Launch Offering Page");
			
			//Opens the web page and waits for it to load 
			driver.get(estoreURL+"/commerce/checkout/demo/items_offerings.jsp");//selenium.open(estoreURL+"/commerce/checkout/demo/items_offerings.jsp");
			
			
			//Checks that the offering page was loaded successfully
			if(driver.getCurrentUrl().toLowerCase().contains("items_offerings.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
				
			}
		}
		catch(Exception e)
		{
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	/**
	 * Launches the Estore shopping cart page and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemString Item to be submitted
	 * @param outputMap outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("ItemsList"). This is an ArrayList of type common.e2e.objects.Item
	 */
	public HashMap<String,Object> addOfferingToShoppingCart(WebDriver driver,String itemString,String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add Offering to Shopping Cart");
			
			//Selects a specific offering from the dropdown menu
			WebElement dropDownListElement = driver.findElement(By.id("skus"));
			Select dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByVisibleText(itemString);
			
			//Add ItemsList to OutputMap
			Item item = new Item();
			item.setName(itemString.substring(0,itemString.indexOf("|")).trim().replace(".", ""));
			item.setNumber(itemString.substring(itemString.indexOf("|")+1).trim());
			lineItemsList.add(item);
			outputMap.put("ItemsList", lineItemsList);
			
			
			Thread.sleep(5000L);
			driver.findElement(By.id("submit")).click(); //driver.click("id=submit");
			
			//Checks that the shopping cart page was loaded successfully
			if(driver.getCurrentUrl().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
			}
			else if(driver.getCurrentUrl().toLowerCase().contains("signin.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The signin page was successfully loaded. (this is a special case for the Salesforce product)");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
		return outputMap;
	}
	


//	/**
//	 * Update attribute(s) of an offering and submit. Limited functionality works for 5 options below only.
//	 * @author sranjan
//	 * @param selenium Default Selenium 
//	 * @param itemString Item to be submitted
//	 * @param edition Edition of the item
//	 * @param fulfillmentMethod Fulfilment method of the item
//	 * @param productType Product Type of the item
//	 * @param users No of users for the item
//	 * @param version Version of the item 
//	 * @param maxPageLoadInMs maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
//	 * @param outputMap outputMap Map that will be populated with important information
//	 * @return Output Map with the value for the following key ("ItemsList").
//	 */
//	public HashMap<String,Object> updateOfferingAndAddToShoppingCart(WebDriver driver,String itemString, String edition, String fulfillmentMethod,
//			String productType, String users, String version, String maxPageLoadInMs,HashMap<String,Object> outputMap)
//	{
//		try
//		{
//			TestRun.startTest("Add Offering to Shopping Cart");
//			
//			//Selects a specific offering from the dropdown menu
//			selenium.select("id=skus", "label="+itemString);
//			
//			//Add ItemsList to OutputMap
//			Item item = new Item();
//			item.setName(itemString.substring(0,itemString.indexOf("|")).trim().replace(".", ""));
//			item.setNumber(itemString.substring(itemString.indexOf("|")+1).trim());
//			lineItemsList.add(item);
//			outputMap.put("ItemsList", lineItemsList);
//			
//			 ArrayList<String> listOfAttrib = new ArrayList<String>();
//			   int i=0;
//			   /*edition="";
//			   fulfillmentMethod = "";
//			   productType = "";
//			   users ="";
//			   version = "";*/
//			   
//			   
//			   do{
//				   Thread.sleep(1000);
//				   listOfAttrib.add("id=attribName"+i+"");
//				   selenium.highlight("id=attribName"+i+"");
//				   i++;
//					
//				}
//				while(selenium.isElementPresent("id=attribName"+i+"")); 
//			
//			   System.out.println(listOfAttrib);
//			   
//			   for(int j=0;j<listOfAttrib.size();j++)
//				{
//					
//					String elementAttrib = listOfAttrib.get(j);
//									
//					if(selenium.getText(elementAttrib).equalsIgnoreCase("edition") && !edition.equals(""))
//					{
//						selenium.select("id=attrib"+j, "label="+edition);
//						
//					}
//					if(selenium.getText(elementAttrib).equalsIgnoreCase("Fulfillment Method") && !fulfillmentMethod.equals(""))
//					{
//						selenium.select("id=attrib"+j, "label="+fulfillmentMethod);
//						
//					}
//					if(selenium.getText(elementAttrib).equalsIgnoreCase("Product Type") && !productType.equals(""))
//					{
//						selenium.select("id=attrib"+j, "label="+productType);
//						
//					}
//					
//					if(selenium.getText(elementAttrib).equalsIgnoreCase("Users") && !users.equals(""))
//					{
//						selenium.select("id=attrib"+j, "label="+users);
//						
//					}
//					if(selenium.getText(elementAttrib).equalsIgnoreCase("Version") && !version.equals(""))
//					{
//						selenium.select("id=attrib"+j, "label="+version);
//						
//					}
//				}
//			
//			Thread.sleep(10000L);
//			selenium.click("id=submit");
//			selenium.waitForPageToLoad(maxPageLoadInMs);
//			
//			//Checks that the shopping cart page was loaded successfully
//			if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
//			{
//				TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
//			}
//			else
//			{
//				TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int k=0;i<listOferrors.size();k++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(k));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));				
//			}
//		}
//		catch(Exception e)
//		{
//			ArrayList<String> listOferrors = captureErrorMessage(driver);
//			for(int i=0;i<listOferrors.size();i++)
//			{
//				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//			
//			}
//			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//		}
//		finally
//		{
//			TestRun.endTest();
//		}
//		
//		return outputMap;
//	}


	
	public enum attribs{name,fulfillmentMethod,users,edition,productType,version,domain,language,tier,pack,feeType,readerType,module,taxYear,numberOfEmployees,release,novalue;
	public static attribs fromString(String Str)
	{
	try {
		return valueOf(Str);
		} 
	catch (Exception ex)
	{
		return novalue;}
	}
	};
	
	
	/**
	 * Update attribute(s) of an offering and submit
	 * @author sranjan
	 * @param selenium Default Selenium
	 * @param itemWithAttribute item and attribute name as an appended string in a required format.
	 * @param maxPageLoadInMs maxPageLoadInMs maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param outputMap outputMap Map that will be populated with important information
	 * @returnOutput Map with the value for the following key ("ItemsList").
	 */
	public HashMap<String,Object> updateOfferingAndAddToShoppingCart(WebDriver driver,String itemWithAttribute, String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add Offering (specific attributes) to Shopping Cart");
			
			//Selects a specific offering from the dropdown menu
			String fulfillmentMethod ="";
			String users = "";
			String edition ="";
			String productType = "";
			String version = "";
			String domain = "";
			String language = "";
			String tier = "";
			String pack = "";
			String feeType = "";
			String readerType = "";
			String module = "";
			String taxYear = "";
			String numberOfEmployees = "";
			String release = "";
			
			String[] tempItemAtb = itemWithAttribute.split("#");         //Splitting the raw string to different attribute
			System.out.println(Arrays.toString(tempItemAtb));
			
			WebElement dropDownListElement;
			Select dropDownListElementSelect;
			
			for(int j=0;j<tempItemAtb.length;j++)
			{
				String atrib = (tempItemAtb[j].split("=")[0]);
				switch(attribs.fromString(atrib))
				{
				case name:
					String itemName = (tempItemAtb[j].split("=")[1]);
					//selenium.select("id=skus", "label="+itemName);
					dropDownListElement = driver.findElement(By.id("skus"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(itemName);
					
					Item item = new Item();
					item.setName(itemName.substring(0,itemName.indexOf("|")).trim().replace(".", ""));
					item.setNumber(itemName.substring(itemName.indexOf("|")+1).trim());
					lineItemsList.add(item);
					break;
				
				case fulfillmentMethod:
					fulfillmentMethod = (tempItemAtb[j].split("=")[1]);					
					break;
				
				case users:
					users = (tempItemAtb[j].split("=")[1]);
					break;
					
				case edition:
					edition = (tempItemAtb[j].split("=")[1]);
					break;
					
				case productType:
					productType = (tempItemAtb[j].split("=")[1]);
					break;
					
				case version:
					version = (tempItemAtb[j].split("=")[1]);
					break;
				
				case domain:
					domain = (tempItemAtb[j].split("=")[1]);
					break;
					
				case language:
					language = (tempItemAtb[j].split("=")[1]);
					break;
					
				case tier:
					tier = (tempItemAtb[j].split("=")[1]);
					
				case pack:
					pack = (tempItemAtb[j].split("=")[1]);
					break;
					
				case feeType:
					feeType = (tempItemAtb[j].split("=")[1]);
					break;
					
				case readerType:
					readerType = (tempItemAtb[j].split("=")[1]);
					break;
					
				case module:
					module = (tempItemAtb[j].split("=")[1]);
					break;
					
				case taxYear:
					taxYear = (tempItemAtb[j].split("=")[1]);
					break;
					
				case numberOfEmployees:
					numberOfEmployees = (tempItemAtb[j].split("=")[1]);
					break;
					
				case release:
					release = (tempItemAtb[j].split("=")[1]);
					break;
				}
			}	
				
			outputMap.put("ItemsList", lineItemsList);
			ArrayList<String> listOfAttrib = new ArrayList<String>();
			int i=0;
			
			do
			{
//				  	Thread.sleep(1000);
				   listOfAttrib.add("attribName"+i+"");
				   //selenium.highlight("id=attribName"+i+""); --> No equivalent method in WebDriver
				   i++;	
			}
			while(isElementPresent(driver, "id", "attribName"+i+"")); //Collecting all attribute present for the item
			//while(selenium.isElementPresent("id=attribName"+i+""));    
			
			System.out.println(listOfAttrib);
			   
			// Setting the attribute value
			for(int j=0;j<listOfAttrib.size();j++) 
			{
				String elementAttrib = listOfAttrib.get(j);
								
				//if(selenium.getText(elementAttrib).equalsIgnoreCase("edition") && !edition.equals(""))
				if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("edition") && !edition.equals(""))
				{
					//selenium.select("id=attrib"+j, "label="+edition);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(edition);
				}
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Fulfillment Method") && !fulfillmentMethod.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Fulfillment Method") && !fulfillmentMethod.equals(""))
				{
					//selenium.select("id=attrib"+j, "label="+fulfillmentMethod);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(fulfillmentMethod);
				}
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Product Type") && !productType.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Product Type") && !productType.equals(""))
				{
					//selenium.select("id=attrib"+j, "label="+productType);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(productType);
				}
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Users") && !users.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Users") && !users.equals(""))	
				{
					//selenium.select("id=attrib"+j, "label="+users);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(users);
					
				}
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Version") && !version.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Version") && !version.equals(""))		
				{
					//selenium.select("id=attrib"+j, "label="+version);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(version);
					
				}
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Domain") && !domain.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Domain") && !domain.equals(""))	
				{
					//selenium.select("id=attrib"+j, "label="+domain);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(domain);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Language") && !language.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Language") && !language.equals(""))	
				{
					//selenium.select("id=attrib"+j, "label="+language);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(language);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Tier") && !tier.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Tier") && !tier.equals(""))	
				{
					//selenium.select("id=attrib"+j, "label="+tier);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(tier);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Pack") && !pack.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Pack") && !pack.equals(""))	
				{
					//selenium.select("id=attrib"+j, "label="+pack);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(pack);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Fee Type") && !feeType.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Fee Type") && !feeType.equals(""))	
				{
					//selenium.select("id=attrib"+j, "label="+feeType);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(feeType);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Reader Type") && !readerType.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Reader Type") && !readerType.equals(""))	
				{
					//selenium.select("id=attrib"+j, "label="+readerType);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(readerType);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Module") && !module.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Module") && !module.equals(""))		
				{
					//selenium.select("id=attrib"+j, "label="+module);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(module);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Tax Year") && !taxYear.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Tax Year") && !taxYear.equals(""))
				{
					//selenium.select("id=attrib"+j, "label="+taxYear);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(taxYear);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Number of Employees") && !numberOfEmployees.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Number of Employees") && !numberOfEmployees.equals(""))
				{
					//selenium.select("id=attrib"+j, "label="+numberOfEmployees);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(numberOfEmployees);
				}
				
				//else if(selenium.getText(elementAttrib).equalsIgnoreCase("Release") && !release.equals(""))
				else if(driver.findElement(By.id(elementAttrib)).getText().equalsIgnoreCase("Release") && !release.equals(""))
				{
					//selenium.select("id=attrib"+j, "label="+release);
					dropDownListElement = driver.findElement(By.id("attrib"+j));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(release);
				}
				else
				{
					
				}	
			}
			
			//Thread.sleep(10000L);
			Thread.sleep(3000L);
			driver.findElement(By.id("submit")).click();//selenium.click("id=submit");
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			
			//Checks that the shopping cart page was loaded successfully
			if(driver.getCurrentUrl().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int k=0;i<listOferrors.size();k++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(k));			
				
				}				
			}
		}
		catch(Exception e)
		{
			
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
		return outputMap;
	}
	
	
	
	//End by Samar
	
	/**
	 * Launches the Estore offering page for bundles and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param item Bundle Item that's selected
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */

	public HashMap<String,Object> addBundleOfferingToShoppingCart(WebDriver driver,String item,String maxPageLoadInMs, HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add Offering to Shopping Cart");
			
			WebElement dropDownListElement;
			Select dropDownListElementSelect ;
			
			driver.findElement(By.linkText("Please click here to go back to CPC items")).click();//selenium.click("link=Please click here to go back to CPC items");
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			Thread.sleep(5000);
			
			//selenium.select("name=sku", "label="+item);
			dropDownListElement = driver.findElement(By.name("sku"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByVisibleText(item);
			//Thread.sleep(10000);
			
			driver.findElement(By.id("cpcInstance")).click();//selenium.click("id=cpcInstance");
			
			/*Item itemToAddToMap = new Item();
			itemToAddToMap.setName(item.substring(0,item.indexOf("|")).trim().replace(".", ""));
			itemToAddToMap.setNumber(item.substring(item.indexOf("|")+1).trim());
			lineItemsList.add(itemToAddToMap);
			outputMap.put("ItemsList", lineItemsList);*/
			
			driver.findElement(By.id("buttonFormSubmit")).click();//selenium.click("id=buttonFormSubmit");
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			
			
			if(driver.getCurrentUrl().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		return outputMap;
	}
	
	
	/**
	 * Launches the Estore Sign In page and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchSignInPage(WebDriver driver,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Launch the Sign-In page");
			
			if(isElementPresent(driver, "css", "input.coButtonNoSubmit.coCheckoutAndContinue"))
			{
				driver.findElement(By.cssSelector("input.coButtonNoSubmit.coCheckoutAndContinue")).click();
			}
			
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix 
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return proceedToCheckout()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			
			if(driver.getCurrentUrl().toLowerCase().contains("signin.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The sign in page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The sign in page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				System.out.println("\t\t\t"+driver.getCurrentUrl().toLowerCase());
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				
			}
		}
		catch(Exception e)
		{
			
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	/**
	 * Launches the Estore company information page and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param newUserEmail New User Email
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void createNewUser(WebDriver driver,String newUserEmail,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Create New User");
			
			//selenium.type("id=newUserEmail", newUserEmail);
			WebElement element = driver.findElement(By.id("newUserEmail"));
			element.sendKeys(newUserEmail);

			if(isElementPresent(driver, "id", "buttonContinueAsGuest"))
			{
				driver.findElement(By.id("buttonContinueAsGuest")).click();//selenium.click("id=buttonContinueAsGuest");
			}
			
			
			//Added due to change deployed on 4/19/12
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix 
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return validateAndCreateAccount()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			
				
			if(driver.getCurrentUrl().toLowerCase().contains("company_info.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The company info page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The company info page did not succesfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	/**
	 * Signs into an existing customer account 
	 * 
	 * @param selenium Default Selenium 
	 * @param userId UserId used to sign into an existing account
	 * @param password Password used to sign into an existing account
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void signIn(WebDriver driver,String userId,String password,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Sign In Existing User");
			
			WebElement element;
			
			element = driver.findElement(By.id("userid"));element.sendKeys(userId);//selenium.type("id=userid",userId);
			element = driver.findElement(By.id("formValuesPassword"));element.sendKeys(password);//selenium.type("id=formValuesPassword",password);
			 
			if(isElementPresent(driver, "id", "buttonLogin"))
			{
				driver.findElement(By.id("buttonLogin")).click();
			}
			
			//Added as a result of the UI change deployed on 4/19/12
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix 
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.signInForm.submit()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			
				
			if(driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The company shipping page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The company shipping page did not succesfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	/**
	 * Populates the new customer information 
	 * 
	 * @param selenium Default Selenium
	 * @param userFirstName User First Name
	 * @param userLastName User Last Name
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("AccountName","streetAddress", "city", "state", "zip", "country") 
	 */
	public HashMap<String,Object> populateNewCompanyInformation(WebDriver driver,String userFirstName,String userLastName,String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Populate New Company Information");
			
			int randomNumber = new Double(Math.random()*10000000).intValue();
			HashMap<String,String> addressMap = buildAddress(randomNumber);
			String accountName = "E2EAutomated" + randomNumber;
			outputMap.put("AccountName", accountName);
			sLog.info("Account Name: " + accountName);
			WebElement element;
			WebElement dropDownListElement;
			Select dropDownListElementSelect ;
			
			element = driver.findElement(By.id("accountName")); element.sendKeys(accountName);
			element = driver.findElement(By.id("firstName")); element.sendKeys(userFirstName);
			element = driver.findElement(By.id("lastName")); element.sendKeys(userLastName);
			element = driver.findElement(By.id("streetAddress")); element.sendKeys(addressMap.get("streetAddress"));
			element = driver.findElement(By.id("city")); element.sendKeys(addressMap.get("city"));
			
			dropDownListElement = driver.findElement(By.id("stateSelect"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue(addressMap.get("state"));
			
			element = driver.findElement(By.id("postalCode")); element.sendKeys(addressMap.get("postalCode"));
			element = driver.findElement(By.id("phoneNumber")); element.sendKeys(addressMap.get("phoneNumber"));
			element = driver.findElement(By.id("userName")); element.sendKeys(accountName);
			element = driver.findElement(By.id("password")); element.sendKeys("password123");
			element = driver.findElement(By.id("confirmPassword")); element.sendKeys("password123");
			
			dropDownListElement = driver.findElement(By.id("securityQuestion"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue("What was the name of your first pet?");
			
			element = driver.findElement(By.id("securityAnswer")); element.sendKeys("fido");
			
			JavascriptExecutor js;
			if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
			{
				try
				{
				driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
				}
				catch(Exception e)
				{
					
				}
			}
			
			//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
			
			else if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.company_info_new_customer.submit()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			
		if(driver.getPageSource().contains("found a matching address."))
		{	
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.company_info_new_customer.submit()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
		}
			//***********************************************************************************************************************
			
			
			//Add relevant information into the output map
			outputMap.put("streetAddress", addressMap.get("streetAddress"));
			outputMap.put("city", addressMap.get("city"));
			outputMap.put("state", addressMap.get("state"));
			outputMap.put("zip", addressMap.get("postalCode"));
			outputMap.put("country", "USA");
			outputMap.put("SignInUsername", accountName);
			outputMap.put("SignInPassword", "password123");
			
			
			if(driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Company Shipping Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The Company Shipping Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
		return outputMap;
	}

	public HashMap<String, Object> populateNewCompanyInformationWithUserPreferredAddress(
			WebDriver driver, String userFirstName, String userLastName,
			String streetAddress,
			String streetAddress2, String city, String state,
			String postalCode, String phoneNumber,String maxPageLoadWaitTimeInMilliseconds,
			HashMap<String, Object> outputMap) {
	
		try
		{
			TestRun.startTest("Populate New Company Information");
			
			int randomNumber = new Double(Math.random()*10000000).intValue();
			//HashMap<String,String> addressMap = buildAddress(randomNumber);
			String accountName = "E2EAutomated" + randomNumber;
			outputMap.put("AccountName", accountName);
			sLog.info("Account Name: " + accountName);
			WebElement element;
			WebElement dropDownListElement;
			Select dropDownListElementSelect ;
			
			element = driver.findElement(By.id("accountName")); element.sendKeys(accountName);
			element = driver.findElement(By.id("firstName")); element.sendKeys(userFirstName);
			element = driver.findElement(By.id("lastName")); element.sendKeys(userLastName);
			element = driver.findElement(By.id("streetAddress")); element.sendKeys(streetAddress);
			element = driver.findElement(By.id("streetAddress2")); element.sendKeys(streetAddress2);
			element = driver.findElement(By.id("city")); element.sendKeys(city);
			
			dropDownListElement = driver.findElement(By.id("stateSelect"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue(state);
			
			element = driver.findElement(By.id("postalCode")); element.sendKeys(postalCode);
			element = driver.findElement(By.id("phoneNumber")); element.sendKeys(phoneNumber);
			element = driver.findElement(By.id("userName")); element.sendKeys(accountName);
			element = driver.findElement(By.id("password")); element.sendKeys("password123");
			element = driver.findElement(By.id("confirmPassword")); element.sendKeys("password123");
			
			dropDownListElement = driver.findElement(By.id("securityQuestion"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue("What was the name of your first pet?");
			
			element = driver.findElement(By.id("securityAnswer")); element.sendKeys("fido");
			
			driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			//Hack since estore is not consistent
			if(!driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
			{
				driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			}
			
			//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.company_info_new_customer.submit()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			
			if(driver.getPageSource().contains("found a matching address."))
			{	
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.company_info_new_customer.submit()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			}
			//***********************************************************************************************************************
		
			
			
			//Add relevant information into the output map
			outputMap.put("streetAddress", streetAddress);
			outputMap.put("city", city);
			outputMap.put("state", state);
			outputMap.put("zip", postalCode);
			outputMap.put("country", "USA");
			outputMap.put("SignInUsername", accountName);
			outputMap.put("SignInPassword", "password123");
			
			
			if(driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Company Shipping Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The Company Shipping Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
		return outputMap;
	}

	public void launchNewPaymentMethodPageforTaxExemptCustomer(Object estoreURL,WebDriver driver,String maxPageLoadInMs)
	{
//		String orderNumber = "ORDER_NUMBER_COULD_NOT_BE_CAPTURED";
		String estoreURLStr=(String)estoreURL;
		try
		{
			TestRun.startTest("Launch New Payment Method Page");
			
			//driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.location.replace('/commerce/checkout/secure/estore/payment_method.jsp')");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			
			//***********************************************************************************************************************
			
			//Explicitly calling New payment method			
			driver.get(estoreURLStr+"/commerce/checkout/secure/estore/payment_method_new_user.jsp?payBy=Credit");
			
			//Clicking on Add new card. This page is result of explicit call we made above
			
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.location.replace('/commerce/checkout/secure/estore/payment_method_new_user.jsp?payBy=Credit')");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			
			if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The New Payment Method Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The New Payment Method Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
	}
	
	/**
	 * Populates the new customer information for an Restricted Party 
	 * 
	 * @param selenium Default Selenium
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("AccountName", "streetAddress", "city", "state", "zip", "country") 
	 */
	public HashMap<String,Object> populateRestrictedPartyInformation(WebDriver driver,String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Populate New Company Information");
			WebElement element;
			WebElement dropDownListElement;
			Select dropDownListElementSelect;
			
			int randomNumber = new Double(Math.random()*10000000).intValue();
			HashMap<String,String> addressMap = buildAddress(randomNumber);
			String accountName = "E2EAutomated" + randomNumber;
			outputMap.put("AccountName", accountName);
			sLog.info("Account Name: " + accountName);
			element = driver.findElement(By.id("accountName"));element.sendKeys(accountName);//selenium.type("id=accountName", accountName);
			element = driver.findElement(By.id("streetAddress"));element.sendKeys("605 Trail Lake Dr");//selenium.type("id=streetAddress","605 Trail Lake Dr");
			element = driver.findElement(By.id("city"));element.sendKeys("Richardson");//selenium.type("id=city","Richardson");
			//selenium.select("id=stateSelect", "label=TX");
			dropDownListElement = driver.findElement(By.id("stateSelect"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue("TX");
			element = driver.findElement(By.id("postalCode"));element.sendKeys("75081");//selenium.type("id=postalCode","75081");
			element = driver.findElement(By.id("firstName"));element.sendKeys("MAYSOON");//selenium.type("id=firstName","MAYSOON");
			element = driver.findElement(By.id("lastName"));element.sendKeys("AL KAYALI");//selenium.type("id=lastName","AL KAYALI");
			
			String signInUsername = "E2EAutomated" + randomNumber;
			element = driver.findElement(By.id("userName"));element.sendKeys(signInUsername);//selenium.type("id=userName",SignInUsername);
			element = driver.findElement(By.id("password"));element.sendKeys("password123");//selenium.type("id=password","password123");
			outputMap.put("SignInUsername", signInUsername);
			outputMap.put("SignInPassword", "password123");
			
			element = driver.findElement(By.id("confirmPassword"));element.sendKeys("password123");//selenium.type("id=confirmPassword","password123");
			//selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
			dropDownListElement = driver.findElement(By.id("securityQuestion"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue("What was the name of your first pet?");
			
			element = driver.findElement(By.id("securityAnswer"));element.sendKeys("fido");//selenium.type("id=securityAnswer","fido");

			if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]")==true)
			{
				driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			}
			
			//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.company_info_new_customer.submit()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			
			driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			
		if(driver.getPageSource().contains("found a matching address."))
		{	
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.company_info_new_customer.submit()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
		}
			//***********************************************************************************************************************
			
			//Add the address information into the output map
			outputMap.put("streetAddress", addressMap.get("streetAddress"));
			outputMap.put("city", addressMap.get("city"));
			outputMap.put("state", addressMap.get("state"));
			outputMap.put("zip", addressMap.get("postalCode"));
			outputMap.put("country", "USA");
			
			
			if(driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Company Shipping Page has successfully loaded.");
			}
			else if(driver.getCurrentUrl().toLowerCase().contains("company_info.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Company Info Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The Expected Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				sLog.error(driver.getCurrentUrl().toLowerCase());
				
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			sLog.error(e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
		return outputMap;
	}
	
	/**
	 * Launches the New Payment Method Page (assumes the current page is company_shipping.jsp) 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchNewPaymentMethodPage(WebDriver driver,String maxPageLoadInMs)
	{

		
		try
		{
			TestRun.startTest("Launch New Payment Method Page");
			
			//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return document.location.replace('/commerce/checkout/secure/estore/payment_method_new_user.jsp')");
					
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
				
			}
			else if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
			{
				driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			}
			
			//***********************************************************************************************************************
			
			//Hack because some versions of estore are not the same (need to click through an extra page)
			if(!driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp"))
			{
				driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			}

			if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The New Payment Method Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The New Payment Method Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
	}
	
	/**
	 * Launches the Existing Payment Method Page (assumes the current page is company_shipping.jsp) 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchExistingPaymentMethodPage(WebDriver driver,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Launch Existing Payment Method Page");
			
			driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(driver.getCurrentUrl().toLowerCase().contains("payment_method.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The Existing Payment Method Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Existing Payment Method Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	/**
	 * Chooses the default payment method (credit card or EFT) for an existing customer account
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void selectExistingPaymentProfile(WebDriver driver,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Select Existing Payment Profile");
			
			//Chooses the first payment profile and populates 
			//the CVV if it is an Credit Card profile
			WebElement element;
			
			//if(selenium.isElementPresent("id=paymentProfileId"))
			if(isElementPresent(driver, "id", "paymentProfileId")==true)
			{
				driver.findElement(By.id("paymentProfileId")).click();//selenium.click("id=paymentProfileId");
				element = driver.findElement(By.xpath("//div[2]/input"));element.sendKeys("123");//selenium.type("//div[2]/input", "123");
				//Need to explicitly kick off the javascript blur event
				element.sendKeys(Keys.TAB);//selenium.fireEvent("//div[2]/input", "blur");
			}
			else
			{
				driver.findElement(By.id("paymentType")).click();//selenium.click("id=paymentType");
			}
			
			driver.findElement(By.id("continueCheckOutFromPayment")).click();//selenium.click("id=continueCheckOutFromPayment");
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The Shopping Cart Review Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Shopping Cart Review Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}

	
	//Select the default EFT payment option
	
	public void selectExistingEFTPaymentProfile(WebDriver driver,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Select Existing EFT Payment Profile");
			
			//Chooses the first payment profile and populates 
			//the CVV if it is an Credit Card profile
			
			//if(selenium.isElementPresent("id=paymentType"))
			if(isElementPresent(driver, "id", "paymentType"))	
			{
				driver.findElement(By.id("paymentType")).click();//selenium.click("id=paymentType");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "Existing EFT profile is not showing up.");
			}
			
			//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			driver.findElement(By.id("continueCheckOutFromPayment")).click();//selenium.click("id=continueCheckOutFromPayment");
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The Shopping Cart Review Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Shopping Cart Review Page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			String screenshot = screenshot(driver, driver.getTitle());
			TestRun.updateStatus(TestResultStatus.FAIL,"An error has ocoured during the test. Please find the screen shot at - "+screenshot);
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	/**
	 * Populates the credit card payment information for a new Billing Profile 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param creditCardNumber Credit Card Number
	 * @param creditCardMonth Credit Card Expiration Month
	 * @param creditCardYear Credit Card Expiration Year
	 */
	public void populateCreditCardPaymentInformation(WebDriver driver,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear)
	{
		try
		{
			//driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			
			TestRun.startTest("Populate Credit Card Payment Information");
			
			WebElement element;
			WebElement dropDownListElement;
			Select dropDownListElementSelect ;
			
			element = driver.findElement(By.id("creditCardNumber")); element.sendKeys(creditCardNumber);//selenium.type("id=creditCardNumber", creditCardNumber);
			//element.LocationOnScreenOnceScrolledIntoView;
			
			//selenium.focus("id=creditCardNumber");
			//Thread.sleep(3000);
			//The native key press (TAB) is necessary in order to identify the correct credit card type
			//driver.keyPressNative("9");
			element.sendKeys(Keys.TAB);
			//Thread.sleep(3000);
			
			dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
			dropDownListElementSelect = new Select(dropDownListElement);
			switch(Integer.parseInt(creditCardMonth))
			{
				case 1: dropDownListElementSelect.selectByValue("01");break;
				case 2: dropDownListElementSelect.selectByValue("02");break;
				case 3: dropDownListElementSelect.selectByValue("03");break;
				case 4: dropDownListElementSelect.selectByValue("04");break;
				case 5: dropDownListElementSelect.selectByValue("05");break;
				case 6: dropDownListElementSelect.selectByValue("06");break;
				case 7: dropDownListElementSelect.selectByValue("07");break;
				case 8: dropDownListElementSelect.selectByValue("08");break;
				case 9: dropDownListElementSelect.selectByValue("09");break;
				case 10: dropDownListElementSelect.selectByValue("10");break;
				case 11: dropDownListElementSelect.selectByValue("11");break;
				case 12: dropDownListElementSelect.selectByValue("12");break;
				default: dropDownListElementSelect.selectByValue("01");break;
			}
			
			//selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
			dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationYYYY"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue(creditCardYear);
			
			element = driver.findElement(By.id("creditCardHolderName")); element.sendKeys("TestCC");//selenium.type("id=creditCardHolderName", "TestCC");
			element = driver.findElement(By.id("creditCardCSC")); element.sendKeys("123");//selenium.type("id=creditCardCSC", "123");
			
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))
			{
				try
				{
					driver.findElement(By.id("newPaymentMethod")).click();
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			else if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
			{
				driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			}
			
						
			if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
			}
			else if(driver.getCurrentUrl().toLowerCase().contains("confirmation.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The confirmation page has loaded successfully. - valid for special case of SalesForce item");
				
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}

	public void populateCreditCardPaymentInformationWithCVV(WebDriver driver,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear,String CVV)
	{
		try
		{
			
			
			TestRun.startTest("Populate Credit Card Payment Information");
			
			WebElement element;
			WebElement dropDownListElement;
			Select dropDownListElementSelect ;
			
			
			element = driver.findElement(By.id("creditCardNumber")); element.sendKeys(creditCardNumber);//selenium.type("id=creditCardNumber", creditCardNumber);
			
			element.sendKeys(Keys.TAB);
						
			dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
			dropDownListElementSelect = new Select(dropDownListElement);
			switch(Integer.parseInt(creditCardMonth))
			{
				case 1: dropDownListElementSelect.selectByValue("01");break;
				case 2: dropDownListElementSelect.selectByValue("02");break;
				case 3: dropDownListElementSelect.selectByValue("03");break;
				case 4: dropDownListElementSelect.selectByValue("04");break;
				case 5: dropDownListElementSelect.selectByValue("05");break;
				case 6: dropDownListElementSelect.selectByValue("06");break;
				case 7: dropDownListElementSelect.selectByValue("07");break;
				case 8: dropDownListElementSelect.selectByValue("08");break;
				case 9: dropDownListElementSelect.selectByValue("09");break;
				case 10: dropDownListElementSelect.selectByValue("10");break;
				case 11: dropDownListElementSelect.selectByValue("11");break;
				case 12: dropDownListElementSelect.selectByValue("12");break;
				default: dropDownListElementSelect.selectByValue("01");break;
			}
			
		
			dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationYYYY"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue(creditCardYear);
			
			element = driver.findElement(By.id("creditCardHolderName")); element.sendKeys("TestCC");
			element = driver.findElement(By.id("creditCardCSC")); element.sendKeys(CVV);
			//driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			//driver.findElement(By.id("newPaymentMethod")).click();
			
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))
			{
				try
				{
					driver.findElement(By.id("newPaymentMethod")).click();
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			}
			else if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
			{
				driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			}
			
			
			if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
			}
			else if(driver.getCurrentUrl().toLowerCase().contains("cart_confirmation.jsp")||driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The confirmation page has loaded successfully. - valid for special case of SalesForce item");
				
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	
	/**
	 * Populates the EFT payment information for a new Billing Profile 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param accountType Account Type (Checking or Savings)
	 * @param accountNumber Bank Account Number
	 * @param routingNumber Bank Routing Number
	 */
	public void populateEFTPaymentInformation(WebDriver driver,String maxPageLoadInMs,String accountType,String accountNumber,String routingNumber)
	{
		try
		{
			TestRun.startTest("Populate EFT Information");
			//Thread.sleep(2000);
			driver.findElement(By.id("radioPaymentEFT")).click();//selenium.click("id=radioPaymentEFT");
			//Thread.sleep(2000);
			
			WebElement element;
			WebElement dropDownListElement;
			Select dropDownListElementSelect;
			
			element = driver.findElement(By.id("accountName")); element.sendKeys("TestEFT");//selenium.type("id=accountName", "TestEFT");
			
			//selenium.select("id=dropdownAccountType", "value="+accountType);
			dropDownListElement = driver.findElement(By.id("dropdownAccountType"));
			dropDownListElementSelect = new Select(dropDownListElement);
			dropDownListElementSelect.selectByValue(accountType);
			
			element = driver.findElement(By.id("routingNumber")); element.sendKeys(routingNumber);//selenium.type("id=routingNumber",routingNumber);
			element = driver.findElement(By.id("accountNumber")); element.sendKeys(accountNumber);//selenium.type("id=accountNumber",accountNumber);
			element = driver.findElement(By.id("confirmAccountNumber")); element.sendKeys(accountNumber);//selenium.type("id=confirmAccountNumber",accountNumber);
			//selenium.focus("id=confirmAccountNumber");
			//The native key press (TAB) is necessary in order to identify the correct credit card type
			element.sendKeys(Keys.TAB);//selenium.keyPressNative("9");
			
			driver.findElement(By.id("newPaymentMethod")).click();
			//driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
	}
	
	/**
	 * Submits the order (assumes the current page is shoppingcart_review.jsp)
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("OrderNumber") 
	 */
	public HashMap<String,Object> submitOrder(WebDriver driver,String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		String orderNumber = "";
		try
		{
			orderNumber = driver.findElement(By.name("order_document_id")).getAttribute("value");
		}
		catch(Exception e)
		{
			orderNumber = "ORDER_NUMBER_COULD_NOT_BE_CAPTURED";
		}
		
		outputMap.put("OrderNumber",orderNumber);
		
		
		try
		{
			TestRun.startTest("Submit The Order");
			
			//driver.findElement(By.id("cart-place-order-button")).click();
			
			//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
			JavascriptExecutor js;
			if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
			{
				try
				{
					js = (JavascriptExecutor) driver;
					js.executeScript("return placeOrder()");
				}
				catch(ScriptException e)
				{
					//Do nothing since the page still loads
				}
			
			}
			else if(isElementPresent(driver, "id", "cart-place-order-button"))
			{
				driver.findElement(By.id("cart-place-order-button")).click();
			}
			
//***********************************************************************************************************************
			
			if(driver.getCurrentUrl().toLowerCase().contains("cart_confirmation.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"OrderNumber: {"+ orderNumber+"}. This order was successfully submitted from Estore.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Confirmation Page did not successfully load. Order " + orderNumber + " was NOT successfully submitted from Estore.");
				
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		
		return outputMap;
	}
	
	/**
	 * Using the Synthetic Person service, this method builds a new random (valid) address and phone number
	 * 
	 * @param randomNumber Random Number
	 * @return Output map of the address and phone number details (Keys: "streetAddress", "city","state","postalCode", and "phoneNumber")
	 */
	private HashMap<String,String> buildAddress(int randomNumber)
	{
		HashMap<String,String> addressMap = new HashMap<String,String>();
		
		try
		{
			URL oracle = new URL("http://perfapp02.pln.dtc.intuit.com/?key="+randomNumber);
		    URLConnection yc = oracle.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		    String inputLine;
		    String areaCode="";
		    
		    while ((inputLine = in.readLine()) != null) 
		    {
	    		String state = inputLine.substring(inputLine.indexOf("AdministrativeAreaName")+"AdministrativeAreaName".length()+1);
	    		state = state.substring(0,state.indexOf("<"));
	    		addressMap.put("state", state);

	    		String city = inputLine.substring(inputLine.indexOf("LocalityName")+"LocalityName".length()+1);
	    		city = city.substring(0,city.indexOf("<"));
	    		addressMap.put("city", city);
	    		
	    		String streetAddress = inputLine.substring(inputLine.indexOf("AddressLine")+"AddressLine".length()+1);
	    		streetAddress = streetAddress.substring(0,streetAddress.indexOf("<"));
	    		addressMap.put("streetAddress", streetAddress);
	    		
	    		String postalCode = inputLine.substring(inputLine.indexOf("PostalCodeNumber")+"PostalCodeNumber".length()+1);
	    		postalCode = postalCode.substring(0,postalCode.indexOf("<"));
	    		addressMap.put("postalCode", postalCode);
	    		
	    		areaCode = inputLine.substring(inputLine.indexOf("AreaCode")+"AreaCode".length()+1);
	    		areaCode = areaCode.substring(0,areaCode.indexOf("<"));
	    		
	    		String phoneNumber = inputLine.substring(inputLine.indexOf("<Number>")+"<Number>".length());
	    		phoneNumber = areaCode + "-" + new StringBuffer(phoneNumber.substring(0,phoneNumber.indexOf("<"))).insert(3, "-").toString();
	    		addressMap.put("phoneNumber", phoneNumber);
		    }
		    in.close();
		}
		catch(Exception e)
		{
			sLog.error("Error using the synthetic person service.");
		}
		return addressMap;
	}

	//Added By Shankar
	
	//Opens the web page and waits for it to load 

	/**
	 * Launches the Estore Sign In Page then signs In and again loads shopping cart page to delete the old items in cart if any.
	 * 
	 * @param selenium Default Selenium 
	 * @param estoreURL Estore URL for the page of interest
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void cleanExistingUserShoppingCartItems(WebDriver driver,String estoreURL,String userId,String password,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Existing User Accounts Items Clean up page ");
			
			WebElement element;
			
			//Opens the web page and waits for it to load 
			driver.get(estoreURL+"/commerce/checkout/secure/estore/signin.jsp");//)selenium.open(estoreURL+"/commerce/checkout/secure/estore/signin.jsp");
			Thread.sleep(500L);
			
			element = driver.findElement(By.id("userid"));element.sendKeys(userId);//selenium.type("id=userid",userId);
			element = driver.findElement(By.id("formValuesPassword"));element.sendKeys(password);//selenium.type("id=formValuesPassword",password);
			
			//if(selenium.isElementPresent("id=buttonLogin")==true)
			if(isElementPresent(driver, "id", "buttonLogin"))	
			{
				driver.findElement(By.id("buttonLogin")).click();//selenium.click("id=buttonLogin");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
			}
			
			driver.get(estoreURL+"/commerce/e2e/cart/shopping_cart.jsp");//selenium.open(estoreURL+"/commerce/e2e/cart/shopping_cart.jsp");
			//selenium.waitForPageToLoad(maxPageLoadInMs);
			
			//while (selenium.isElementPresent("css=img.space-bottom-10")==true)
			while (isElementPresent(driver, "css", "img.space-bottom-10"))	
			{
				driver.findElement(By.cssSelector("img.space-bottom-10")).click();//selenium.click("css=img.space-bottom-10");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
				Thread.sleep(10000L);
			}
			
			//if(selenium.isElementPresent("link=Sign Out")==true)
			if(isElementPresent(driver, "id", "buttonLogin"))	
			{
				driver.findElement(By.linkText("Sign Out")).click();//selenium.click("link=Sign Out");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
			}
			
			//Checks that the offering page was loaded successfully
			if(driver.getCurrentUrl().toLowerCase().contains("login.jsp") || driver.getCurrentUrl().toLowerCase().contains("signin.jsp")||driver.getCurrentUrl().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Existing user shopping cart items successfully  deleted.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"Existing user shopping cart items not successfully  deleted.");
				System.out.println("\t\t\t"+driver.getCurrentUrl().toLowerCase());
				
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
	}
	
	
//		/**
//		 * Create a new account and place order and the same account will be used as Existing Account with Billing profile  
//		 * 
//		 * @param userFirstName User First Name
//		 * @param userLastName User Last Name
//		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
//		 * @param outputMap Map that will be populated with important information
//		 * @return Output Map with the value for the following key ("AccountName" , SignInUsername, SignInPassord) 
//		 */
//		public HashMap<String,Object> CreateNewUserAccount_Use_As_ExistingAccount(WebDriver driver,String ExistingActEstoreURL,String userFirstName,String userLastName,String maxPageLoadInMs,String CompanyAddress,HashMap<String,Object> outputMap)
//		{
//			try
//			{
//				//TestRun.startTest("Populate New Guest Company Information");
//				//Date dNow = new Date();
//				TestRun.startTest("Creating New User Account To use as Existing account");
//				selenium.open(ExistingActEstoreURL);
//				Thread.sleep(5000L);
//				//selenium.waitForPageToLoad(maxPageLoadInMs);
//				java.util.Date dNow = new java.util.Date();
//				SimpleDateFormat ft =   new SimpleDateFormat ("yyyyMMddhhmmss"); //E yyyyMMddhhmmss
//			   	String NewComapanyName=(ft.format(dNow));
//			    
//				int randomNumber = new Double(Math.random()*10000000).intValue();
//				HashMap<String,String> addressMap = buildAddress(randomNumber);
//				String accountName = "E2E_" + NewComapanyName;
//				outputMap.put("AccountName", accountName);
//				sLog.info("Account Name: " + accountName);
//				//selenium.type("id=accountName", accountName);
//				// Sign In Page continue as a Guest.
//				selenium.type("id=newUserEmail",accountName+"@intuit.com");
//				if (selenium.isElementPresent("id=buttonContinueAsGuest")==true)
//				{
//				selenium.click("id=buttonContinueAsGuest");
//				selenium.waitForPageToLoad(maxPageLoadInMs);
//				}
//				
//				selenium.type("id=accountName", accountName);
//				if (CompanyAddress.equals("Company_StandardUSA")){
//					System.out.println("Inside Company USA Address");
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress",addressMap.get("streetAddress"));
//					selenium.type("id=city",addressMap.get("city"));
//					selenium.select("id=stateSelect", "label="+addressMap.get("state"));
//					selenium.type("id=postalCode",addressMap.get("postalCode"));
//				}else if (CompanyAddress.equals("Company_POBox_USA")){
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress","PO Box# "+NewComapanyName);
//					selenium.type("id=city",addressMap.get("city"));
//					selenium.select("id=stateSelect", "label="+addressMap.get("state"));
//					selenium.type("id=postalCode",addressMap.get("postalCode"));
//				}else if (CompanyAddress.equals("Company_APOBox_USA")){
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress","FPO Base# "+NewComapanyName);
//					selenium.type("id=city","FPO");
//					selenium.select("id=stateSelect", "label=AE");
//					selenium.type("id=postalCode","09409");
//				}else if (CompanyAddress.equals("Company_StandardCANADA")){
//					selenium.select("id=countrySelect", "label=Canada");
//					selenium.type("id=streetAddress","2210 Bank St");
//					selenium.type("id=city","Ottawa");
//					selenium.select("id=stateSelect", "label=ON");
//					selenium.type("id=postalCode","K1V 1J5");
//				}else if (CompanyAddress.equals("Company_POBox_CANADA")){
//					selenium.select("id=countrySelect", "label=Canada");
//					selenium.type("id=streetAddress","PO Box #"+NewComapanyName);
//					selenium.type("id=city",addressMap.get("city"));
//					selenium.select("id=stateSelect", "label=ON");
//					selenium.type("id=postalCode","K1V 1J5");
//				}else if(CompanyAddress.equals("Company_USPossession_USA")){
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress","1501 Ponce de Leon");
//					selenium.type("id=city","Santurce");
//					selenium.select("id=stateSelect", "label=PR");
//					selenium.type("id=postalCode","00907");
//				}	
//				selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
//				selenium.type("id=firstName",userFirstName);
//				selenium.type("id=lastName",userLastName);
//				if (selenium.isElementPresent("id=createLogin")==true)
//				{
//					selenium.click("id=createLogin");
//				}
//				
//				String ExistingUserName = accountName;
//				selenium.type("id=userName", ExistingUserName);
//				outputMap.put("SignInUsername", ExistingUserName);
//				String ExistingUserPassword = "password123";
//				selenium.type("id=password",ExistingUserPassword);
//				outputMap.put("SignInPassord", ExistingUserPassword);
//				selenium.type("id=confirmPassword",ExistingUserPassword);
//				selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
//				selenium.type("id=securityAnswer","fido");
//				if (selenium.isElementPresent("id=updateCompanyInfo")==true)
//					{
//					selenium.click("id=updateCompanyInfo");
//					selenium.waitForPageToLoad(maxPageLoadInMs);
//					}
//				
//				if (selenium.isElementPresent("id=updateCompanyInfo")==true)
//				{
//					selenium.click("id=updateCompanyInfo");
//					selenium.waitForPageToLoad(maxPageLoadInMs);
//				}
//				if (selenium.isElementPresent("link=Sign Out")==true)
//				{
//					selenium.click("link=Sign Out");
//					selenium.waitForPageToLoad(maxPageLoadInMs);
//				}
//				if(selenium.getLocation().toLowerCase().contains("signin.jsp"))
//				{
//					TestRun.updateStatus(TestResultStatus.PASS,"The Create new user Account SignIn Page successfully loaded.");
//				}
//				else
//				{
//					TestRun.updateStatus(TestResultStatus.FAIL,"The Create new user Account SignIn Page did not successfully load.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int i=0;i<listOferrors.size();i++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
//						
//			}
//			catch(Exception e)
//			{
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int i=0;i<listOferrors.size();i++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//			}
//			finally
//			{
//				TestRun.endTest();
//			}
//			
//			return outputMap;
//		}


		public HashMap<String,Object> addDownload_ReInstallOfferingToShoppingCart(WebDriver driver,String item,String maxPageLoadInMs,HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Add Offering to Shopping Cart");
				
				//Selects a specific offering from the dropdown menu
				//selenium.select("id=skus", "label="+item);
				WebElement dropDownListElement = driver.findElement(By.id("skus"));
				Select dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(item);
				
				
				Thread.sleep(5000L);
				//if (selenium.isElementPresent("css=#attrib1")==true)
				try
				{
					//selenium.select("css=#attrib1", "label=Download with Reinstall");
					dropDownListElement = driver.findElement(By.cssSelector("#attrib1"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("Download with Reinstall");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					Thread.sleep(5000L);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				//if (selenium.isElementPresent("id=submit")==true)
				try
				{
					driver.findElement(By.id("submit")).click();//selenium.click("id=submit");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					//Thread.sleep(5000L);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				//Checks that the shopping cart page was loaded successfully
				if(driver.getCurrentUrl().toLowerCase().contains("shopping_cart.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}


		public HashMap<String,Object> addPayRoll_3EmployeesOfferingToShoppingCart(WebDriver driver,String item,String maxPageLoadInMs,HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Add Offering to Shopping Cart");
				
				//Selects a specific offering from the dropdown menu
				//selenium.select("id=skus", "label="+item);
				WebElement dropDownListElement = driver.findElement(By.name("skus"));
				Select dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(item);
				
				Thread.sleep(10000);
				//if (selenium.isElementPresent("id=attrib0")==true)
				try
				{
					//selenium.select("id=attrib0", "label=Up to 3 Employees");
					dropDownListElement = driver.findElement(By.id("attrib0"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("Up to 3 Employees");
					Thread.sleep(5000L);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				driver.findElement(By.id("submit")).click();//selenium.click("id=submit");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
				
				//Checks that the shopping cart page was loaded successfully
				if(driver.getCurrentUrl().toLowerCase().contains("shopping_cart.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}

//		/**
//		 * Signs into an existing customer account 
//		 * 
//		 * @param selenium Default Selenium 
//		 * @param userId UserId used to sign into an existing account
//		 * @param password Password used to sign into an existing account
//		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
//		 */
//		public HashMap<String,Object>  ExistingUsersignIn(WebDriver driver,String maxPageLoadInMs, HashMap<String,Object> outputMap)
//		{
//			try
//			{
//				TestRun.startTest("Sign In with Existing User Credentails");
//				
//				selenium.type("id=userid",(String)outputMap.get("SignInUsername"));
//				selenium.type("id=formValuesPassword",(String)outputMap.get("SignInPassord"));
//				selenium.click("id=buttonLogin");
//				selenium.waitForPageToLoad(maxPageLoadInMs);
//					
//				if(selenium.getLocation().toLowerCase().contains("company_shipping.jsp"))
//				{
//					TestRun.updateStatus(TestResultStatus.PASS,"The company shipping page has successfully loaded.");
//				}
//				else
//				{
//					TestRun.updateStatus(TestResultStatus.FAIL,"The company shipping page did not succesfully load.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int i=0;i<listOferrors.size();i++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
//			}
//			catch(Exception e)
//			{
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int i=0;i<listOferrors.size();i++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//			}
//			finally
//			{
//				TestRun.endTest();
//			}
//			return outputMap;
//		}

		
		/**
		 * Populates the new customer information 
		 * 
		 * @param selenium Default Selenium
		 * @param userFirstName User First Name
		 * @param userLastName User Last Name
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param Company Address TypesCompany_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		 * @param outputMap Map that will be populated with important information
		 * @return Output Map with the value for the following key ("AccountName", Address) 
		 */
		public HashMap<String,Object> populateCD_Hardware_NewCompanyInformation(WebDriver driver,String userFirstName,String userLastName,String maxPageLoadInMs,String companyAddress,HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Populate New Company Information to Create new Company");
				
				int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				java.util.Date dNow = new java.util.Date();
				SimpleDateFormat ft =   new SimpleDateFormat ("yyyyMMddhhmmss");
			   	String newComapanyName=(ft.format(dNow));
			   	String accountName ="E2EAutomated_"+newComapanyName;
			   	outputMap.put("AccountName", accountName);
			   	sLog.info("Account Name: " + accountName);
			   	
			   	WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect ;
				
				element = driver.findElement(By.id("accountName")); element.sendKeys(accountName);//selenium.type("id=accountName", accountName);
			   	
				if (("Company_StandardUSA").equals(companyAddress))
				{
					//selenium.select("id=countrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("streetAddress")); element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=streetAddress",addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city")); element.sendKeys(addressMap.get("city"));//selenium.type("id=city",addressMap.get("city"));
					
					//selenium.select("id=stateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(addressMap.get("state"));
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode",addressMap.get("postalCode"));
					
					outputMap.put("companyStreetAddress", addressMap.get("streetAddress"));
					outputMap.put("companyCity", addressMap.get("city"));
					outputMap.put("companyState", addressMap.get("state"));
					outputMap.put("companyZip", addressMap.get("postalCode"));
					outputMap.put("companyCountry", "USA");
					
				}
				else if (("Company_POBox_USA").equals(companyAddress))
				{
					String companyStreetAddress ="PO Box# "+newComapanyName;
					
					//selenium.select("id=countrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("streetAddress")); element.sendKeys(companyStreetAddress);//selenium.type("id=streetAddress",companyStreetAddress);
					element = driver.findElement(By.id("city")); element.sendKeys(addressMap.get("city"));//selenium.type("id=city",addressMap.get("city"));
					
					//selenium.select("id=stateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(addressMap.get("state"));
					
					
					//selenium.type("id=postalCode",addressMap.get("postalCode"));
					element = driver.findElement(By.id("postalCode")); element.sendKeys(addressMap.get("postalCode"));
					
					outputMap.put("companyStreetAddress", companyStreetAddress);
					outputMap.put("companyCity", addressMap.get("city"));
					outputMap.put("companyState", addressMap.get("state"));
					outputMap.put("companyZip", addressMap.get("postalCode"));
					outputMap.put("companyCountry", "USA");
				}
				else if (("Company_APOBox_USA").equals(companyAddress))
				{
					String companyStreetAddress ="FPO Base# "+newComapanyName;
					String companyCity ="FPO";
					String companyState ="AE";
					
					String companyZip="09409";
					
					//selenium.select("id=countrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("streetAddress")); element.sendKeys(companyStreetAddress);//selenium.type("id=streetAddress",companyStreetAddress);
					
					element = driver.findElement(By.id("city")); element.sendKeys(companyCity);//selenium.type("id=city",companyCity);
					
					//selenium.select("id=stateSelect", "label="+companyState);
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(companyState);
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(companyZip);//selenium.type("id=postalCode",companyZip);
					
					outputMap.put("companyStreetAddress", companyStreetAddress);
					outputMap.put("companyCity",companyCity);
					outputMap.put("companyState", companyState);
					outputMap.put("companyZip", companyZip);
					outputMap.put("companyCountry", "USA");
				}
				else if (("Company_StandardCANADA").equals(companyAddress))
				{
					String companyStreetAddress ="1309 Carling Ave";
					String companyCity ="Ottawa";
					String companyState ="ON";
					String companyCountry ="Canada";
					String companyZip="K1Z 7L3";
					
					//selenium.select("id=countrySelect", "label=Canada");
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("Canada");
					
					element = driver.findElement(By.id("streetAddress")); element.sendKeys(companyStreetAddress);//selenium.type("id=streetAddress",companyStreetAddress);
					element = driver.findElement(By.id("city")); element.sendKeys(companyCity);//selenium.type("id=city",companyCity);
					//selenium.select("id=stateSelect", "label="+companyState);
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(companyState);
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(companyZip);//selenium.type("id=postalCode",companyZip);
					
					outputMap.put("companyStreetAddress", companyStreetAddress);
					outputMap.put("companyCity",companyCity);
					outputMap.put("companyState", companyState);
					outputMap.put("companyZip", companyZip);
					outputMap.put("companyCountry", companyCountry);
				}
				else if (("Company_POBox_CANADA").equals(companyAddress))
				{
					String companyStreetAddress ="PO Box #"+randomNumber;
					String companyCity ="Ottawa";
					String companyState ="ON";
					String companyCountry ="Canada";
					String companyZip="K1S 3W3";
					
					//selenium.select("id=countrySelect", "label=Canada");
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("Canada");
					
					element = driver.findElement(By.id("streetAddress")); element.sendKeys(companyStreetAddress);//selenium.type("id=streetAddress",companyStreetAddress);
					element = driver.findElement(By.id("city")); element.sendKeys(companyCity);//selenium.type("id=city",companyCity);
					//selenium.select("id=stateSelect", "label="+companyState);
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(companyState);
					element = driver.findElement(By.id("postalCode")); element.sendKeys(companyZip);//selenium.type("id=postalCode",companyZip);
					
					outputMap.put("companyStreetAddress", companyStreetAddress);
					outputMap.put("companyCity",companyCity);
					outputMap.put("companyState", companyState);
					outputMap.put("companyZip", companyZip);
					outputMap.put("companyCountry", companyCountry);
				}
				else if(("Company_USPossession_USA").equals(companyAddress))
				{
					String companyStreetAddress ="1501 Ponce de Leon";
					String companyCity ="Santurce";
					String companyState ="PR";
					String companyCountry ="USA";
					String companyZip="00907";
					
					//selenium.select("id=countrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("streetAddress")); element.sendKeys(companyStreetAddress);//selenium.type("id=streetAddress",companyStreetAddress);
					element = driver.findElement(By.id("city")); element.sendKeys(companyCity);//selenium.type("id=city",companyCity);
					//selenium.select("id=stateSelect", "label="+companyState);
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(companyState);
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(companyZip);//selenium.type("id=postalCode",companyZip);
					
					outputMap.put("companyStreetAddress", companyStreetAddress);
					outputMap.put("companyCity",companyCity);
					outputMap.put("companyState", companyState);
					outputMap.put("companyZip", companyZip);
					outputMap.put("companyCountry", companyCountry);
					
				}	
				
				element = driver.findElement(By.id("phoneNumber")); element.sendKeys(addressMap.get("phoneNumber"));//selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
				element = driver.findElement(By.id("firstName")); element.sendKeys(userFirstName);//selenium.type("id=firstName",userFirstName);
				element = driver.findElement(By.id("lastName")); element.sendKeys(userLastName);//selenium.type("id=lastName",userLastName);
					
				

				if(isElementPresent(driver, "id", "createLogin"))
				{
					driver.findElement(By.id("createLogin")).click();//selenium.click("id=createLogin");
				}
				
				//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
				JavascriptExecutor js;
				if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
				{
					try
					{
						js = (JavascriptExecutor) driver;
						js.executeScript("return showUaccount('coCreateContainer')");
					}
					catch(ScriptException e)
					{
						//Do nothing since the page still loads
					}
				}
				else if(isElementPresent(driver,"id","createLogin"))
				{
					driver.findElement(By.id("createLogin")).click();
				}
				//********************END*******************
												
				String existingUserName = accountName;
				element = driver.findElement(By.id("userName")); element.sendKeys(existingUserName);//selenium.type("id=userName", existingUserName);
				outputMap.put("signInUsername", existingUserName);
				String existingUserPassword = "password123";
				element = driver.findElement(By.id("password")); element.sendKeys(existingUserPassword);//selenium.type("id=password",existingUserPassword);
				outputMap.put("signInPassword", existingUserPassword);
				element = driver.findElement(By.id("confirmPassword")); element.sendKeys(existingUserPassword);//selenium.type("id=confirmPassword",existingUserPassword);
				//selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
				dropDownListElement = driver.findElement(By.id("securityQuestion"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByValue("What was the name of your first pet?");
				element = driver.findElement(By.id("securityAnswer")); element.sendKeys("fido");//selenium.type("id=securityAnswer","fido");
				
				
				if(isElementPresent(driver, "id", "updateCompanyInfo"))
				{
					driver.findElement(By.id("updateCompanyInfo")).click();//selenium.click("id=createLogin");
				}
				
				//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
				
				if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
				{
					try
					{
						js = (JavascriptExecutor) driver;
						js.executeScript("return document.company_info_new_customer.submit()");
					}
					catch(ScriptException e)
					{
						//Do nothing since the page still loads
					}
				}
				driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
				
				if(driver.getPageSource().contains("found a matching address."))
				{	
				if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
				 {
					try
					{
						js = (JavascriptExecutor) driver;
						js.executeScript("return document.company_info_new_customer.submit()");
					}
					catch(ScriptException e)
					{
						//Do nothing since the page still loads
					}
				 }
				}
				
				
				
				
/*				try
				{
					driver.findElement(By.id("buttonContinue")).click();
				}
				catch(Exception e)
				{
					try
					{
						driver.findElement(By.cssSelector("input.coButtonNoSubmit.coCheckoutAndContinue")).click();
					}
					catch(Exception e1)
					{
						try
						{
							driver.findElement(By.id("updateCompanyInfo")).click();//selenium.click("id=updateCompanyInfo");
						}
						catch(Exception e2)
						{
							//Button is not available
						}
					}
				}      */
				
			
				
				if(driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The Company Shipping Page has successfully loaded.");
				}
				else if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The Shopping Cart Review Page has successfully loaded.");
				}
				else if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The Payment Method New User Page has successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The Expected Page did not successfully load.");
					sLog.info("Page that was loaded -->" + driver.getCurrentUrl().toLowerCase());
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			
			return outputMap;
		}
		
		/**
		* Populates the new customer information 
			 * 
			 * @param selenium Default Selenium
			 * @param userFirstName User First Name
			 * @param userLastName User Last Name
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param outputMap Map that will be populated with important information
			 * @return Output Map with the value for the following key ("AccountName") 
			 */
		public HashMap<String,Object> populateCD_Hardware_NewShippingInformation(WebDriver driver,String userFirstName,String userLastName,String maxPageLoadInMs, String shipAddressType,String shipType, HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Populate New Shipping Information with New Shipping Address");
					
					int randomNumber = new Double(Math.random()*10000000).intValue();
					HashMap<String,String> addressMap = buildAddress(randomNumber);
					
					WebElement element;
					WebElement dropDownListElement;
					Select dropDownListElementSelect ;
					
					//if (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
					try
					{
						driver.findElement(By.id("shippingAddressSameAsCompanyAddress")).click();//selenium.click("id=shippingAddressSameAsCompanyAddress");
					}
					catch(Exception e)
					{
						//Button not available
					}
					
					//if (selenium.isElementPresent("link=Add address")==true)
					try
					{
						driver.findElement(By.linkText("Add Address")).click();//selenium.click("link=Add address");
					}
					catch(Exception e)
					{
						//Button not available
						
					}
					
//					JavascriptExecutor js;
//					if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
//					{
//						try
//						{
//							js = (JavascriptExecutor) driver;
//							js.executeScript("showShippingBox('newShippingAddress')");
//						}
//						catch(ScriptException e)
//						{
//							//Do nothing since the page still loads
//						}
//					}
					
					if (("Ship_Normal").equals(shipType))
					{	
						if (("Shipping_StandardUSA").equals(shipAddressType)) 
						{
							//selenium.select("id=shippingCountrySelect", "label=US");
							dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText("US");

							element = driver.findElement(By.id("address1")); element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=address1",addressMap.get("streetAddress"));
							element = driver.findElement(By.id("city")); element.sendKeys(addressMap.get("city"));//selenium.type("id=city",addressMap.get("city"));
							//selenium.select("id=shippingStateSelect", "label="+addressMap.get("state"));
							dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
							
							element = driver.findElement(By.id("postalCode")); element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode",addressMap.get("postalCode"));
							
							outputMap.put("shippingStreetAddress", addressMap.get("streetAddress"));
							outputMap.put("shippingCity", addressMap.get("city"));
							outputMap.put("shippingState", addressMap.get("state"));
							outputMap.put("shippingZip", addressMap.get("postalCode"));
							outputMap.put("shippingCountry", "USA");
						}
						else if(("Shipping_APOBox_USA").equals(shipAddressType))
						{
							//selenium.select("id=shippingCountrySelect", "label=US");
							dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText("US");
							
							//String streetAddress ="APO Base#"+randomNumber;
							String shippingStreetAddress ="APO Base#"+randomNumber;
							String shippingCity ="APO";
							String shippingState ="AA";
							String shippingCountry ="US";
							String shippingZip="34002";
							
							element = driver.findElement(By.id("address1")); element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
							element = driver.findElement(By.id("city")); element.sendKeys(shippingCity);//selenium.type("id=city",shippingCity);
							//selenium.select("id=shippingStateSelect", "label="+shippingState);
							dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText(shippingState);
							
							element = driver.findElement(By.id("postalCode")); element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
							
							outputMap.put("shippingStreetAddress", shippingStreetAddress);
							outputMap.put("shippingCity", shippingCity);
							outputMap.put("shippingState", shippingState);
							outputMap.put("shippingZip", shippingZip);
							outputMap.put("shippingCountry", shippingCountry);
						}
						else if( ("Shipping_POBox_USA").equals(shipAddressType))
						{
							//selenium.select("id=shippingCountrySelect", "label=US");
							dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText("US");
							
							String streetAddress ="PO Box #"+randomNumber;
							element = driver.findElement(By.id("address1")); element.sendKeys(streetAddress);//selenium.type("id=address1",streetAddress);
							element = driver.findElement(By.id("city")); element.sendKeys(addressMap.get("city"));//selenium.type("id=city",addressMap.get("city"));
							//selenium.select("id=shippingStateSelect", "label="+addressMap.get("state"));
							dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
							
							element = driver.findElement(By.id("postalCode")); element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode",addressMap.get("postalCode"));
							
							outputMap.put("shippingStreetAddress", streetAddress);
							outputMap.put("shippingCity", addressMap.get("city"));
							outputMap.put("shippingState", addressMap.get("state"));
							outputMap.put("shippingZip", addressMap.get("postalCode"));
							outputMap.put("shippingCountry", "USA");
						}
						else if( ("Shipping_StandardCANADA").equals(shipAddressType))
						{
							String shippingStreetAddress ="1350 Richmond Road";
							String shippingCity ="Ottawa";
							String shippingState ="ON";
							String shippingCountry ="Canada";
							String shippingZip="K2B 7Z3";
							
							//selenium.select("id=shippingCountrySelect", "label=CANADA");
							dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText("CANADA");
							
							element = driver.findElement(By.id("address1")); element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
							element = driver.findElement(By.id("city")); element.sendKeys(shippingCity);//selenium.type("id=city",shippingCity);
							//selenium.select("id=shippingStateSelect","label="+shippingState);
							dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText(shippingState);
							element = driver.findElement(By.id("postalCode")); element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
							
							outputMap.put("shippingStreetAddress", shippingStreetAddress);
							outputMap.put("shippingCity", shippingCity);
							outputMap.put("shippingState", shippingState);
							outputMap.put("shippingZip", shippingZip);
							outputMap.put("shippingCountry", shippingCountry);
							
						}
						else if(("Shipping_POBox_CANADA").equals(shipAddressType))
						{
							
							String shippingStreetAddress ="PO Box #"+randomNumber;
							String shippingCity ="Calgary";
							String shippingState ="AB";
							String shippingCountry ="Canada";
							String shippingZip="T2J 3V1";
							
							//selenium.select("id=shippingCountrySelect", "label=CANADA");
							dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText("CANADA");
							
							element = driver.findElement(By.id("address1")); element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
							element = driver.findElement(By.id("city")); element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
							//selenium.select("id=shippingStateSelect", "label="+shippingState);
							dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText(shippingState);
							
							element = driver.findElement(By.id("postalCode")); element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
							
							outputMap.put("shippingStreetAddress", shippingStreetAddress);
							outputMap.put("shippingCity", shippingCity);
							outputMap.put("shippingState", shippingState);
							outputMap.put("shippingZip", shippingZip);
							outputMap.put("shippingCountry", shippingCountry);
						}
						else if(("Shipping_USPossession_USA").equals(shipAddressType))
						{
							//selenium.select("id=shippingCountrySelect", "label=US");
							String shippingStreetAddress ="405 Ferrocaril St";
							String shippingCity ="Ponce";
							String shippingState ="PR";
							String shippingCountry ="USA";
							String shippingZip="00733";
							
							//selenium.select("id=shippingCountrySelect", "label=US");
							dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText("US");
							
							element = driver.findElement(By.id("address1")); element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
							element = driver.findElement(By.id("city")); element.sendKeys(shippingCity);//selenium.type("id=city",shippingCity);
							//selenium.select("id=shippingStateSelect", "label="+shippingState);
							dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
							dropDownListElementSelect = new Select(dropDownListElement);
							dropDownListElementSelect.selectByVisibleText(shippingState);
							element = driver.findElement(By.id("postalCode")); element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
							
							outputMap.put("shippingStreetAddress", shippingStreetAddress);
							outputMap.put("shippingCity", shippingCity);
							outputMap.put("shippingState", shippingState);
							outputMap.put("shippingZip", shippingZip);
							outputMap.put("shippingCountry", shippingCountry);
						}
												
						//if (selenium.isElementPresent("id=addNewShippingButton")==true)
						try
						{
							driver.findElement(By.id("addNewShippingButton")).click();//selenium.click("id=addNewShippingButton");
							//selenium.waitForPageToLoad(maxPageLoadInMs);
						}
						catch(Exception e)
						{
							//Button not available
						}
						
						//if (selenium.isElementPresent("id=standardizeAddressSelected2")==true)
						try
						{
							driver.findElement(By.id("standardizeAddressSelected2")).click();//selenium.click("id=standardizeAddressSelected2");
							driver.findElement(By.id("addNewShippingButton")).click();//selenium.click("id=addNewShippingButton");
							//selenium.waitForPageToLoad(maxPageLoadInMs);
						}
						catch(Exception e)
						{
							//Button not available
						}
						
						//if (selenium.isElementPresent("id=selectShippingButton")==true)
						try
						{
							driver.findElement(By.id("selectShippingButton")).click();//selenium.click("id=selectShippingButton");
							//selenium.waitForPageToLoad(maxPageLoadInMs);
						}	
						catch(Exception e)
						{
							//Button not available
						}
						
						if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp"))
						{
						
							TestRun.updateStatus(TestResultStatus.PASS,"The Payment Page has successfully loaded.");
						}
						else if(driver.getCurrentUrl().toLowerCase().contains("payment_method.jsp"))
						{
						
							TestRun.updateStatus(TestResultStatus.PASS,"The Payment Page has successfully loaded.");
						}
						else if(driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
						{
							driver.findElement(By.id("selectShippingButton")).click();//UpdateShippingAddress
							TestRun.updateStatus(TestResultStatus.PASS,"The Company Shipping Page has successfully loaded.");
						}
						else
						{
						TestRun.updateStatus(TestResultStatus.FAIL,"None of the expected pages loaded properly (payment_method_new_user.jsp || company_shipping.jsp)");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						System.out.println("\t\t\t"+driver.getCurrentUrl().toLowerCase());
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
				  }
				}
				catch(Exception e)
				{
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					System.out.println("\t\t\t"+driver.getCurrentUrl().toLowerCase());
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				
				return outputMap;
			}


	/**
	 * Populates the new customer information 
	 * 
	 * @param selenium Default Selenium
	 * @param userFirstName User First Name
	 * @param userLastName User Last Name
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("AccountName") 
	 */
	public HashMap<String,Object> populateCD_Hardware_New_3rdPartyShippingInfo(WebDriver driver,String userFirstName,String userLastName,String maxPageLoadInMs, String ShipAddressType,String ShipType, HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Populate New Shipping Information with New 3rd Party ship Addrtess");
			
			int randomNumber = new Double(Math.random()*10000000).intValue();
			HashMap<String,String> addressMap = buildAddress(randomNumber);
			//String accountName = "E2EAutomated" + randomNumber;
			//outputMap.put("AccountName", accountName);
			java.util.Date dNow = new java.util.Date();
			SimpleDateFormat ft =   new SimpleDateFormat ("yyyyMMddhhmmss"); //E yyyyMMddhhmmss
		   	String NewComapanyName=(ft.format(dNow));
			String accountName = "E2EAutomated_" + NewComapanyName;
			outputMap.put("accountName3rdPartyShip", accountName);
			sLog.info("Account Name: " + accountName);
			
			WebElement element;
			WebElement dropDownListElement;
			Select dropDownListElementSelect;
			
			//if (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
			try
			{
				driver.findElement(By.id("shippingAddressSameAsCompanyAddress")).click();//selenium.click("id=shippingAddressSameAsCompanyAddress");
			}
			catch(Exception e)
			{
				//Button not available
			}
			
			//if (selenium.isElementPresent("id=radioThirdPartyAddress")==true)
			try
			{
				driver.findElement(By.id("radioThirdPartyAddress")).click();//selenium.click("id=radioThirdPartyAddress");
			}
			catch(Exception e)
			{
				//Button not available
			}
			
			if(("Ship_3rdParty").equals(ShipType))
			{
				element = driver.findElement(By.id("accountName")); element.sendKeys("Ship"+accountName);//selenium.type("id=accountName", "Ship"+accountName);
					
				if(("Shipping_StandardUSA").equals(ShipAddressType))
				{
					//selenium.select("id=country", "label=US");
					dropDownListElement = driver.findElement(By.id("country"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("third_party_streetAddress")); element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=third_party_streetAddress",addressMap.get("streetAddress"));
					element = driver.findElement(By.name("city")); element.sendKeys(addressMap.get("city"));//selenium.type("name=city",addressMap.get("city"));
					//selenium.select("id=state", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("state"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					
					element = driver.findElement(By.name("postalCode")); element.sendKeys(addressMap.get("postalCode"));//selenium.type("name=postalCode",addressMap.get("postalCode"));
					
					outputMap.put("shipping3rdPartyStreetAddress", addressMap.get("streetAddress"));
					outputMap.put("shipping3rdPartyCity", addressMap.get("city"));
					outputMap.put("shipping3rdPartyState", addressMap.get("state"));
					outputMap.put("shipping3rdPartyZip", addressMap.get("postalCode"));
					outputMap.put("shipping3rdPartyCountry", "USA");
				}
				else if(("Shipping_APOBox_USA").equals(ShipAddressType))
				{
					String shippingStreetAddress ="APO Base # "+NewComapanyName;
					String shippingCity ="APO";
					String shippingState ="AA";
					String shippingCountry ="USA";
					String shippingZip="34002";
					
					//selenium.select("id=country", "label=US");
					dropDownListElement = driver.findElement(By.id("country"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("third_party_streetAddress")); element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
					element = driver.findElement(By.name("city")); element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
					//selenium.select("id=state", "label="+shippingState);
					dropDownListElement = driver.findElement(By.id("state"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(shippingState);
					
					element = driver.findElement(By.name("postalCode")); element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
					
					outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
					outputMap.put("shipping3rdPartyCity", shippingCity);
					outputMap.put("shipping3rdPartyState", shippingState);
					outputMap.put("shipping3rdPartyZip", shippingZip);
					outputMap.put("shipping3rdPartyCountry", shippingCountry);
							
				}
				else if(("Shipping_POBox_USA").equals(ShipAddressType))
				{
					//selenium.select("id=country", "label=US");
					dropDownListElement = driver.findElement(By.id("country"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					String streetAddress ="PO Box #"+randomNumber;
					element = driver.findElement(By.id("third_party_streetAddress")); element.sendKeys(streetAddress);//selenium.type("id=third_party_streetAddress",streetAddress);
					element = driver.findElement(By.name("city")); element.sendKeys(addressMap.get("city"));//selenium.type("name=city",addressMap.get("city"));
					//selenium.select("id=state", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("state"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					
					element = driver.findElement(By.name("postalCode")); element.sendKeys(addressMap.get("postalCode"));//selenium.type("name=postalCode",addressMap.get("postalCode"));
					
					outputMap.put("shipping3rdPartyStreetAddress", streetAddress);
					outputMap.put("shipping3rdPartyCity", addressMap.get("city"));
					outputMap.put("shipping3rdPartyState", addressMap.get("state"));
					outputMap.put("shipping3rdPartyZip", addressMap.get("postalCode"));
					outputMap.put("shippingCountry", "USA");
				}
				else if(("Shipping_USPossession_USA").equals(ShipAddressType))
				{
					String shippingStreetAddress ="405 Ferrocaril St";
					String shippingCity ="Ponce";
					String shippingState ="PR";
					String shippingCountry ="USA";
					String shippingZip="00733";
					
					//selenium.select("id=country", "label=US");
					dropDownListElement = driver.findElement(By.id("country"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("third_party_streetAddress")); element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
					element = driver.findElement(By.name("city")); element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
					//selenium.select("id=state", "label="+shippingState);
					dropDownListElement = driver.findElement(By.id("state"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(shippingState);
					
					element = driver.findElement(By.name("postalCode")); element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
					
					outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
					outputMap.put("shipping3rdPartyCity", shippingCity);
					outputMap.put("shipping3rdPartyState", shippingState);
					outputMap.put("shipping3rdPartyZip", shippingZip);
					outputMap.put("shipping3rdPartyCountry", shippingCountry);
				}
				else if(("Shipping_StandardCANADA").equals(ShipAddressType))
				{
					String shippingStreetAddress ="11300 Tuscany Blvd NW Apt";
					String shippingCity ="Calgary";
					String shippingState ="AB";
					String shippingCountry ="Canada";
					String shippingZip="T3L 2V7";
					
					//selenium.select("id=country", "label=CANADA");
					dropDownListElement = driver.findElement(By.id("country"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					
					element = driver.findElement(By.id("third_party_streetAddress")); element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
					element = driver.findElement(By.name("city")); element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
					//selenium.select("id=state", "label="+shippingState);
					dropDownListElement = driver.findElement(By.id("state"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(shippingState);
					
					element = driver.findElement(By.name("postalCode")); element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
					
					outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
					outputMap.put("shipping3rdPartyCity", shippingCity);
					outputMap.put("shipping3rdPartyState", shippingState);
					outputMap.put("shipping3rdPartyZip", shippingZip);
					outputMap.put("shipping3rdPartyCountry", shippingCountry);
				}
				else if(("Shipping_POBox_CANADA").equals(ShipAddressType))
				{
					String shippingStreetAddress ="PO Box #"+NewComapanyName;
					String shippingCity ="Calgary";
					String shippingState ="AB";
					String shippingCountry ="Canada";
					String shippingZip="T2J 3V1";
					
					//selenium.select("id=country", "label=CANADA");
					dropDownListElement = driver.findElement(By.id("country"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					
					element = driver.findElement(By.id("third_party_streetAddress")); element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
					element = driver.findElement(By.name("city")); element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
					//selenium.select("id=state", "label="+shippingState);
					dropDownListElement = driver.findElement(By.id("state"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(shippingState);
					
					element = driver.findElement(By.name("postalCode")); element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
					
					outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
					outputMap.put("shipping3rdPartyCity", shippingCity);
					outputMap.put("shipping3rdPartyState", shippingState);
					outputMap.put("shipping3rdPartyZip", shippingZip);
					outputMap.put("shipping3rdPartyCountry", shippingCountry);
				}
				
				element = driver.findElement(By.id("phoneNumber")); element.sendKeys(addressMap.get("phoneNumber"));//selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
				element = driver.findElement(By.id("firstName")); element.sendKeys(userFirstName);//selenium.type("id=firstName",userFirstName);
				element = driver.findElement(By.id("lastName")); element.sendKeys(userLastName);//selenium.type("id=lastName",userLastName);
				element = driver.findElement(By.id("email")); element.sendKeys("Ship"+accountName+"@intuit.com");//selenium.type("id=email","Ship"+accountName+"@intuit.com");			
			}
			//if (selenium.isElementPresent("id=thirdPartyShipping")==true)
			try
			{
				driver.findElement(By.id("thirdPartyShipping")).click();//selenium.click("id=thirdPartyShipping");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
			}
			catch(Exception e)
			{
				//Button not available
			}
			
			//if (selenium.isElementPresent("id=thirdPartyShipping")==true)
			try
			{
				driver.findElement(By.id("thirdPartyShipping")).click();//selenium.click("id=thirdPartyShipping");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
			}
			catch(Exception e)
			{
				//Button not available
			}
			
			//if (selenium.isElementPresent("id=selectShippingButton")==true)
			try
			{
				driver.findElement(By.id("selectShippingButton")).click();//selenium.click("id=selectShippingButton");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
			}
			catch(Exception e)
			{
				//Button not available
			}
				
			if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp") || driver.getCurrentUrl().toLowerCase().contains("payment_method.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Payment Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The Payment did not successfully load.");
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				System.out.println("\t\t\t"+driver.getCurrentUrl().toLowerCase());
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			}
		}
		catch(Exception e)
		{
			ArrayList<String> listOferrors = captureErrorMessage(driver);
			for(int i=0;i<listOferrors.size();i++)
			{
				sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
			
			}
			sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
			
		return outputMap;
	}
			

//		/**
//		 * Launches the New Payment Method Page (assumes the current page is company_shipping.jsp) 
//		 * 
//		 * @param selenium Default Selenium 
//		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
//		 */
//		public void launchPaymentMethod_SubmitCompanyPageMethodPage(WebDriver driver,String maxPageLoadInMs)
//		{
//			try
//			{
//				TestRun.startTest("Launch New Payment Method Page");
//				if (selenium.isElementPresent("id=shippingButton")==true)
//				{
//				selenium.click("id=shippingButton");
//				selenium.waitForPageToLoad(maxPageLoadInMs);
//				}
//				if (selenium.isElementPresent("id=buttonContinue")==true)
//				{
//					selenium.click("id=buttonContinue");
//					selenium.waitForPageToLoad(maxPageLoadInMs);
//				}
//				System.out.println("Inside Payment page");
//				
//				if(selenium.getLocation().toLowerCase().contains("payment_method_new_user.jsp"))
//				{
//					TestRun.updateStatus(TestResultStatus.PASS, "The New Payment Method Page has successfully loaded.");
//				}
//				else
//				{
//					TestRun.updateStatus(TestResultStatus.FAIL, "The New Payment Method Page did not successfully load.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int i=0;i<listOferrors.size();i++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
//			}
//			catch(Exception e)
//			{
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int i=0;i<listOferrors.size();i++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//			}
//			finally
//			{
//				TestRun.endTest();
//			}
//		}
		
		/**
		 * Populates the credit card payment information for a new Billing Profile 
		 * 
		 * @param selenium Default Selenium 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param creditCardNumber Credit Card Number
		 * @param creditCardMonth Credit Card Expiration Month
		 * @param creditCardYear Credit Card Expiration Year
		 * 
		 */
		public HashMap<String,Object> populateCreditCard_NewBillingProfilePaymentInformation(WebDriver driver,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear,String billAddressType, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Populate Credit Card Payment with new Billing Address Information");
				
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				element = driver.findElement(By.id("creditCardNumber")); element.sendKeys(creditCardNumber);//selenium.type("id=creditCardNumber", creditCardNumber);
				element.sendKeys(Keys.TAB);
				//selenium.focus("id=creditCardNumber");
				//The native key press (TAB) is necessary in order to identify the correct credit card type
				//selenium.keyPressNative("9");
				
				
				dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
				dropDownListElementSelect = new Select(dropDownListElement);
				
				switch(Integer.parseInt(creditCardMonth))
				{
					case 1: dropDownListElementSelect.selectByValue("01");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
					case 2: dropDownListElementSelect.selectByValue("02");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=02"); break;
					case 3: dropDownListElementSelect.selectByValue("03");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=03"); break;
					case 4: dropDownListElementSelect.selectByValue("04");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=04"); break;
					case 5: dropDownListElementSelect.selectByValue("05");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=05"); break;
					case 6: dropDownListElementSelect.selectByValue("06");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=06"); break;
					case 7: dropDownListElementSelect.selectByValue("07");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=07"); break;
					case 8: dropDownListElementSelect.selectByValue("08");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=08"); break;
					case 9: dropDownListElementSelect.selectByValue("09");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=09"); break;
					case 10: dropDownListElementSelect.selectByValue("10");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=10"); break;
					case 11: dropDownListElementSelect.selectByValue("11");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=11"); break;
					case 12: dropDownListElementSelect.selectByValue("12");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=12"); break;
					default: dropDownListElementSelect.selectByValue("01");break;//selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
				}
				
				//selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
				dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationYYYY"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(creditCardYear);
				
				element = driver.findElement(By.id("creditCardHolderName")); element.sendKeys("TestCC");//selenium.type("id=creditCardHolderName", "TestCC");
				element = driver.findElement(By.id("creditCardCSC")); element.sendKeys("123");//selenium.type("id=creditCardCSC", "123");
				
				
				
				
				//if (selenium.isElementPresent("id=payment_shipping_address")==true)
				if(isElementPresent(driver,"id","payment_shipping_address"))
				{
					driver.findElement(By.id("payment_shipping_address")).click();
					
				}
				
				//if (selenium.isElementPresent("id=billingAddressSelect")==true)
				try
				{
					//selenium.select("id=billingAddressSelect", "label=Create New");
					dropDownListElement = driver.findElement(By.id("billingAddressSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("Create New");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				
				if (("Billing_StandardUSA").equals(billAddressType))
				{ 
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("address1")); element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city")); element.sendKeys(addressMap.get("city"));//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("billingStreetAddress", addressMap.get("streetAddress"));
					outputMap.put("billingCity", addressMap.get("city"));
					outputMap.put("billingState", addressMap.get("state"));
					outputMap.put("billingZip", addressMap.get("postalCode"));
					outputMap.put("billingCountry", "USA");
				}
				else if (("Billing_APOBox_USA").equals(billAddressType))
				{ 
					String billingStreetAddress ="APO Base #"+randomNumber;
					String billingCity ="APO";
					String billingState ="AP";
					//String shippingCountry ="USA";
					String billingZip="96201";
					
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("address1")); element.sendKeys(billingStreetAddress);//selenium.type("id=address1",billingStreetAddress );
					element = driver.findElement(By.id("city")); element.sendKeys(billingCity);//selenium.type("id=city", billingCity);
					//selenium.select("id=billingStateSelect", "label="+billingState);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(billingZip);//selenium.type("id=postalCode", billingZip);
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", "USA");
					
				}
				else if (("Billing_POBox_USA").equals(billAddressType))
				{ 
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					String billingStreetAddress ="PO Box #"+randomNumber;
					element = driver.findElement(By.id("address1")); element.sendKeys(billingStreetAddress);//selenium.type("id=address1",billingStreetAddress );
					element = driver.findElement(By.id("city")); element.sendKeys(addressMap.get("city"));//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", addressMap.get("city"));
					outputMap.put("billingState", addressMap.get("state"));
					outputMap.put("billingZip", addressMap.get("postalCode"));
					outputMap.put("billingCountry", "USA");
				}
				else if (("Billing_StandardCANADA").equals(billAddressType))
				{ 
					
					String billingStreetAddress ="450 Terminal Avenue";
					String billingCity ="Ottawa";
					String billingState ="ON";
					String billingCountry ="Canada";
					String billingZip="K1G 0Z3";
					
					//selenium.select("id=billingCountrySelect", "label=CANADA");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					
					element = driver.findElement(By.id("address1")); element.sendKeys(billingStreetAddress);//selenium.type("id=address1",billingStreetAddress );
					element = driver.findElement(By.id("city")); element.sendKeys(billingCity);//selenium.type("id=city",billingCity );
					//selenium.select("id=billingStateSelect", "label="+billingState);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode")); element.sendKeys(billingZip);//selenium.type("id=postalCode",billingZip);
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				else if (("Billing_POBox_CANADA").equals(billAddressType))
				{ 
					String billingStreetAddress ="PO Box #"+randomNumber;
					String billingCity ="Fredericton";
					String billingState ="NB";
					String billingCountry ="Canada";
					String billingZip="E3B 1E4";
					
					//selenium.select("id=billingCountrySelect", "label=CANADA");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					
					element = driver.findElement(By.id("address1")); element.sendKeys(billingStreetAddress);//selenium.type("id=address1",billingStreetAddress );
					element = driver.findElement(By.id("city")); element.sendKeys(billingCity);//selenium.type("id=city", billingCity);
					//selenium.select("id=billingStateSelect", "label="+billingState);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(billingZip);//selenium.type("id=postalCode", billingZip);
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				else if (("Billing_USPossession_USA").equals(billAddressType))
				{ 
					String billingStreetAddress ="606 Tito Castro Avenue";
					String billingCity ="Ponce";
					String billingState ="PR";
					String billingCountry ="USA";
					String billingZip="00731";
					
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					
					element = driver.findElement(By.id("address1")); element.sendKeys(billingStreetAddress);//selenium.type("id=address1", billingStreetAddress);
					element = driver.findElement(By.id("city")); element.sendKeys(billingCity);//selenium.type("id=city", billingCity);
					//selenium.select("id=billingStateSelect", "label="+billingState);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					
					element = driver.findElement(By.id("postalCode")); element.sendKeys(billingZip);//selenium.type("id=postalCode",billingZip);
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				
				if(isElementPresent(driver,"id","newPaymentMethod"))
				{
					driver.findElement(By.id("newPaymentMethod")).click();
				}
				
				driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
				
				if(driver.getPageSource().contains("found a matching address."))
				{
								{
					driver.findElement(By.id("newPaymentMethod")).click();//selenium.click("id=payment_shipping_address");
				}
				

				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
				}
			}
			
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}
		

		/**
		 * Launches the Estore shopping cart page and verifies that the page has been loaded properly.
		 * 
		 * @param selenium Default Selenium 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param itemString
		 * @param productTypeStr; To select the product option String productType="IntuitQB_POS_Download_Reinstall";
		 * @param paymentTypeStr creditcard or EFT to enter that flow and create a new one.
		 * @param shipAddressTypeStr ship address type to be created Shipping_StandardUSA Shipping_POBox_USA Shipping_APOBox_USA Shipping_StandardCANADA Shipping_POBox_CANADA Shipping_USPossession_USA  
   		 * @param shipTypeStr for ship type String shipType="Ship_Normal"; or String shipType="Ship_Normal" ;
		 * @param shipAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
		 * @param billAddressTypeStr ship address type to be created Billing_StandardUSA Billing_POBox_USA Billing_APOBox_USA Billing_StandardCANADA Billing_POBox_CANADA Billing_USPossession_USA
		 * @param billAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
		 * @param companyAddressStr company address type to be created Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		 * @param outputMap
		 * @return Output Map with the value for the following key ("ItemsList"). This is an ArrayList of type common.e2e.objects.Item
		 */
	/*	public HashMap<String,Object> CreateNewAccount_To_UseAs_ExistingUserCC(WebDriver driver,String item,String maxPageLoadInMs,String estoreURL,String newUserEmail,String CompanyAddress,String userFirstName,String userLastName, String shipAddressType,String shipType, String creditCardNumber, String creditCardMonth,String creditCardYear,String paymentType, String accountType, String routingNumber, String accountNumber, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Add Offering to Shopping Cart");
				java.util.Date dNow = new java.util.Date();
				SimpleDateFormat ft =   new SimpleDateFormat ("yyyyMMddhhmmss"); //E yyyyMMddhhmmss
			   	String NewComapanyName=(ft.format(dNow));
			    
				int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				String accountName = "E2E_" + NewComapanyName;
				
				driver.get(estoreURL+"/commerce/checkout/demo/items_offerings.jsp");//selenium.open(estoreURL+"/commerce/checkout/demo/items_offerings.jsp");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				//selenium.select("id=skus", "label=Intuit QuickBooks Point Of Sale. | 1099874");
				dropDownListElement = driver.findElement(By.id("skus"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(item);
				
				Thread.sleep(5000L);
//				if (selenium.isElementPresent("css=#attrib1")==true)
//					{
//						selenium.select("css=#attrib1", "label=Download with Reinstall");
//						Thread.sleep(5000L);
//					}
				
				//if(selenium.isElementPresent("id=submit")==true)
				if(isElementPresent(driver, "id", "submit"))	
				{
					driver.findElement(By.id("submit")).click();//selenium.click("id=submit");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				//if(selenium.isElementPresent("css=input.coButtonNoSubmit.coCheckoutAndContinue")==true)
				if(isElementPresent(driver, "css", "input.coButtonNoSubmit.coCheckoutAndContinue"))	
				{
					driver.findElement(By.cssSelector("input.coButtonNoSubmit.coCheckoutAndContinue")).click();//selenium.click("css=input.coButtonNoSubmit.coCheckoutAndContinue");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				element = driver.findElement(By.id("newUserEmail"));element.sendKeys(newUserEmail);//selenium.type("id=newUserEmail", newUserEmail);
				
				//if(selenium.isElementPresent("id=buttonContinueAsGuest")==true)
				if(isElementPresent(driver, "id", "buttonContinueAsGuest"))
				{
					driver.findElement(By.id("buttonContinueAsGuest")).click();//selenium.click("id=buttonContinueAsGuest");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				element = driver.findElement(By.id("accountName"));element.sendKeys(accountName);//selenium.type("id=accountName", accountName);
				
				if (CompanyAddress.equals("Company_StandardUSA"))
				{
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress",addressMap.get("streetAddress"));
//					selenium.type("id=city",addressMap.get("city"));
//					selenium.select("id=stateSelect", "label="+addressMap.get("state"));
//					selenium.type("id=postalCode",addressMap.get("postalCode"));
					
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("streetAddress"));element.sendKeys(addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(addressMap.get("state"));
					element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));
					
				}
				else if (CompanyAddress.equals("Company_POBox_USA"))
				{
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress","PO Box# "+NewComapanyName);
//					selenium.type("id=city",addressMap.get("city"));
//					selenium.select("id=stateSelect", "label="+addressMap.get("state"));
//					selenium.type("id=postalCode",addressMap.get("postalCode"));
					
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("streetAddress"));element.sendKeys("PO Box# "+NewComapanyName);
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(addressMap.get("state"));
					element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));
				}
				else if (CompanyAddress.equals("Company_APOBox_USA"))
				{
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress","FPO Base# "+NewComapanyName);
//					selenium.type("id=city","FPO");
//					selenium.select("id=stateSelect", "label=AE");
//					selenium.type("id=postalCode","09409");
					
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("streetAddress"));element.sendKeys("FPO Base# "+NewComapanyName);
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("FPO"));
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue("AE");
					element = driver.findElement(By.id("postalCode"));element.sendKeys("09409");
				}
				else if (CompanyAddress.equals("Company_StandardCANADA"))
				{
//					selenium.select("id=countrySelect", "label=Canada");
//					selenium.type("id=streetAddress","2210 Bank St");
//					selenium.type("id=city","Ottawa");
//					selenium.select("id=stateSelect", "label=ON");
//					selenium.type("id=postalCode","K1V 1J5");
					
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					element = driver.findElement(By.id("streetAddress"));element.sendKeys("2210 Bank St");
					element = driver.findElement(By.id("city"));element.sendKeys("Ottawa");
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue("ON");
					element = driver.findElement(By.id("postalCode"));element.sendKeys("K1V 1J5");
				}
				else if (CompanyAddress.equals("Company_POBox_CANADA"))
				{
//					selenium.select("id=countrySelect", "label=Canada");
//					selenium.type("id=streetAddress","PO Box #"+NewComapanyName);
//					selenium.type("id=city",addressMap.get("city"));
//					selenium.select("id=stateSelect", "label=ON");
//					selenium.type("id=postalCode","K1V 1J5");
					
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					element = driver.findElement(By.id("streetAddress"));element.sendKeys("PO Box #"+NewComapanyName);
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue("ON");
					element = driver.findElement(By.id("postalCode"));element.sendKeys("K1V 1J5");
				}
				else if(CompanyAddress.equals("Company_USPossession_USA"))
				{
//					selenium.select("id=countrySelect", "label=US");
//					selenium.type("id=streetAddress","1501 Ponce de Leon");
//					selenium.type("id=city","Santurce");
//					selenium.select("id=stateSelect", "label=PR");
//					selenium.type("id=postalCode","00907");
					
					dropDownListElement = driver.findElement(By.id("countrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("streetAddress"));element.sendKeys("1501 Ponce de Leon");
					element = driver.findElement(By.id("city"));element.sendKeys("Santurce");
					dropDownListElement = driver.findElement(By.id("stateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue("PR");
					element = driver.findElement(By.id("postalCode"));element.sendKeys("00907");
				}
				
				element = driver.findElement(By.id("phoneNumber"));element.sendKeys(addressMap.get("phoneNumber"));//selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
				element = driver.findElement(By.id("firstName"));element.sendKeys(userFirstName);//selenium.type("id=firstName",userFirstName);
				element = driver.findElement(By.id("lastName"));element.sendKeys(userLastName);//selenium.type("id=lastName",userLastName);
				
				//if (selenium.isElementPresent("id=createLogin")==true)
				if (isElementPresent(driver, "id", "createLogin"))
				{
					driver.findElement(By.id("createLogin")).click();//selenium.click("id=createLogin");
				}
				
				element = driver.findElement(By.id("userName"));element.sendKeys(accountName);//selenium.type("id=userName", ExistingUserName);
				outputMap.put("SignInUsername", accountName);
				element = driver.findElement(By.id("password"));element.sendKeys("password123");//selenium.type("id=password",ExistingUserPassword);
				outputMap.put("SignInPassword", "password123");
				element = driver.findElement(By.id("confirmPassword"));element.sendKeys("password123");//selenium.type("id=confirmPassword",ExistingUserPassword);
				//selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
				dropDownListElement = driver.findElement(By.id("securityQuestion"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByValue("What was the name of your first pet?");
				element = driver.findElement(By.id("securityAnswer"));element.sendKeys("fido");//selenium.type("id=securityAnswer","fido");
															
				//if (selenium.isElementPresent("id=updateCompanyInfo")==true)
				if (isElementPresent(driver, "id", "updateCompanyInfo"))	
				{
					driver.findElement(By.id("updateCompanyInfo")).click();//selenium.click("id=updateCompanyInfo");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				//if (selenium.isElementPresent("id=updateCompanyInfo")==true)
				if (isElementPresent(driver, "id", "updateCompanyInfo"))
				{
					driver.findElement(By.id("updateCompanyInfo")).click();//selenium.click("id=updateCompanyInfo");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				//if (selenium.isElementPresent("id=buttonContinue")==true)
				if (isElementPresent(driver, "id", "buttonContinue"))
				{
					driver.findElement(By.id("buttonContinue")).click();//selenium.click("id=buttonContinue");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				if(("creditcard").equals(paymentType))
				{		
					element = driver.findElement(By.id("creditCardNumber"));element.sendKeys(creditCardNumber);//selenium.type("id=creditCardNumber", creditCardNumber);
					//selenium.focus("id=creditCardNumber");
					//The native key press (TAB) is necessary in order to identify the correct credit card type
					element.sendKeys(Keys.TAB);//selenium.keyPressNative("9");
					
					switch(Integer.parseInt(creditCardMonth))
					{
					
					case 1: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("01");
						break;
						
					case 2: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("02");
						break;
					case 3: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("03");
						break;
					
					case 4: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("04");
						break;
					
					case 5: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("05");
						break;
					
					case 6: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("06");
						break;
						
					case 7: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("07");
						break;
						
					case 8: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("08");
						break;
						
					case 9: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("09");
						break;
						
					case 10: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("10");
						break;
					
					case 11: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("11");
						break;	
					
					case 12: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("12");
						break;	
						
					default: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("01");
						break;
					
					
//						case 1: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
//						case 2: selenium.select("id=dropdownCreditCardExpirationMM", "value=02"); break;
//						case 3: selenium.select("id=dropdownCreditCardExpirationMM", "value=03"); break;
//						case 4: selenium.select("id=dropdownCreditCardExpirationMM", "value=04"); break;
//						case 5: selenium.select("id=dropdownCreditCardExpirationMM", "value=05"); break;
//						case 6: selenium.select("id=dropdownCreditCardExpirationMM", "value=06"); break;
//						case 7: selenium.select("id=dropdownCreditCardExpirationMM", "value=07"); break;
//						case 8: selenium.select("id=dropdownCreditCardExpirationMM", "value=08"); break;
//						case 9: selenium.select("id=dropdownCreditCardExpirationMM", "value=09"); break;
//						case 10: selenium.select("id=dropdownCreditCardExpirationMM", "value=10"); break;
//						case 11: selenium.select("id=dropdownCreditCardExpirationMM", "value=11"); break;
//						case 12: selenium.select("id=dropdownCreditCardExpirationMM", "value=12"); break;
//						default: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
					}
					//selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
					dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationYYYY"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(creditCardYear);
					
					element = driver.findElement(By.id("creditCardHolderName"));element.sendKeys("TestCC");//selenium.type("id=creditCardHolderName", "TestCC");
					element = driver.findElement(By.id("creditCardCSC"));element.sendKeys("123");//selenium.type("id=creditCardCSC", "123");
					
					if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
					{
						driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
						//selenium.waitForPageToLoad(maxPageLoadInMs);
					}
				}
				else if(("EFT").equals(paymentType))
				{
					driver.findElement(By.id("radioPaymentEFT")).click();//selenium.click("id=radioPaymentEFT");
					element = driver.findElement(By.id("accountName"));element.sendKeys("TestEFT");//selenium.type("id=accountName", "TestEFT");
					//selenium.select("id=dropdownAccountType", "value="+accountType);
					dropDownListElement = driver.findElement(By.id("dropdownAccountType"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue(accountType);
					
					element = driver.findElement(By.id("routingNumber"));element.sendKeys(routingNumber);//selenium.type("id=routingNumber",routingNumber);
					element = driver.findElement(By.id("accountNumber"));element.sendKeys(accountNumber);//selenium.type("id=accountNumber",accountNumber);
					element = driver.findElement(By.id("confirmAccountNumber"));element.sendKeys(accountNumber);//selenium.type("id=confirmAccountNumber",accountNumber);
					//selenium.focus("id=confirmAccountNumber");
					//The native key press (TAB) is necessary in order to identify the correct credit card type
					element.sendKeys(Keys.TAB);//selenium.keyPressNative("9");
					
					if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
					{
						driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
						//selenium.waitForPageToLoad(maxPageLoadInMs);
					}
				}
				//if (selenium.isElementPresent("id=cart-place-order-button")==true)
				if(isElementPresent(driver, "id", "cart-place-order-button"))
				{
					driver.findElement(By.id("cart-place-order-button")).click();//selenium.click("id=cart-place-order-button");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				//if (selenium.isElementPresent("id=cart-place-order-button")==true)
				if(isElementPresent(driver, "id", "cart-place-order-button"))
				{
					driver.findElement(By.id("cart-place-order-button")).click();//selenium.click("id=cart-place-order-button");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				//if (selenium.isElementPresent("link=Sign Out")==true)
				if(isElementPresent(driver, "linktext", "Sign Out"))
				{
					driver.findElement(By.linkText("Sign Out")).click();//selenium.click("link=Sign Out");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				Thread.sleep(10000L);	
				Thread.sleep(10000L);
				
				//Checks that the shopping cart page was loaded successfully
				if((driver.getCurrentUrl().toLowerCase().contains("logout_success.jsp")) ||driver.getCurrentUrl().toLowerCase().contains("signin.jsp") ||
						driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp")||driver.getCurrentUrl().toLowerCase().contains("logout_success.jsp")) 
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The Create New User Account was successful.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The Create New User Account was successful.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
			
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			
			return outputMap;
		} */
		

		/**
		 * Signs into an existing customer account 
		 * 
		 * @param selenium Default Selenium 
		 * @param userId UserId used to sign into an existing account
		 * @param password Password used to sign into an existing account
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 */
		public HashMap<String,Object> signInAsExistingUser(WebDriver driver,String maxPageLoadInMs, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Sign In Existing User");
				
				WebElement element;
				
				element = driver.findElement(By.id("userid"));element.sendKeys((String)outputMap.get("SignInUsername"));//selenium.type("id=userid",(String)outputMap.get("SignInUsername"));
				element = driver.findElement(By.id("formValuesPassword"));element.sendKeys((String)outputMap.get("SignInPassword"));//selenium.type("id=formValuesPassword",(String)outputMap.get("SignInPassword"));
				driver.findElement(By.id("buttonLogin")).click();//selenium.click("id=buttonLogin");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
					
				if(driver.getCurrentUrl().toLowerCase().contains("company_shipping.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The company shipping page has successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The company shipping page did not succesfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}
		

		/**
		 * Populates the credit card payment information for a new Billing Profile 
		 * 
		 * @param selenium Default Selenium 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param creditCardNumber Credit Card Number
		 * @param creditCardMonth Credit Card Expiration Month
		 * @param creditCardYear Credit Card Expiration Year
		 * @param billAddressTypeStr Bill address type to be created Billing_StandardUSA Billing_POBox_USA Billing_APOBox_USA Billing_StandardCANADA Billing_POBox_CANADA Billing_USPossession_USA
		 * @param billAddressNewStr to determine if new address needs to be created or not Vales are Yes or No
		 * 
		 */
		public HashMap<String,Object> populateCreditCard_ExistingBillingProfileNew(WebDriver driver,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear,String billAddressType, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Populate Credit Card Payment with new Billing Address Information");
				
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				//Thread.sleep(5000L);
				
				//if (selenium.isElementPresent("link=Add a new card")==true)
				if (isElementPresent(driver, "linktext", "Add a new card")==true)
				{
					driver.findElement(By.linkText("Add a new card")).click();//selenium.click("link=Add a new card");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					//Thread.sleep(5000L);
				}
				
				element = driver.findElement(By.id("creditCardNumber"));element.sendKeys(creditCardNumber);//selenium.type("id=creditCardNumber", creditCardNumber);
				//selenium.focus("id=creditCardNumber");
				//The native key press (TAB) is necessary in order to identify the correct credit card type
				//selenium.keyPressNative("9");
				element.sendKeys(Keys.TAB);
				
				switch(Integer.parseInt(creditCardMonth))
				{
					case 1: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("01");
						break;
						
					case 2: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("02");
						break;
					case 3: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("03");
						break;
					
					case 4: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("04");
						break;
					
					case 5: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("05");
						break;
					
					case 6: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("06");
						break;
						
					case 7: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("07");
						break;
						
					case 8: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("08");
						break;
						
					case 9: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("09");
						break;
						
					case 10: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("10");
						break;
					
					case 11: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("11");
						break;	
					
					case 12: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("12");
						break;	
						
					default: 
						dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByValue("01");
						break;
						
//					case 1: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
//					case 2: selenium.select("id=dropdownCreditCardExpirationMM", "value=02"); break;
//					case 3: selenium.select("id=dropdownCreditCardExpirationMM", "value=03"); break;
//					case 4: selenium.select("id=dropdownCreditCardExpirationMM", "value=04"); break;
//					case 5: selenium.select("id=dropdownCreditCardExpirationMM", "value=05"); break;
//					case 6: selenium.select("id=dropdownCreditCardExpirationMM", "value=06"); break;
//					case 7: selenium.select("id=dropdownCreditCardExpirationMM", "value=07"); break;
//					case 8: selenium.select("id=dropdownCreditCardExpirationMM", "value=08"); break;
//					case 9: selenium.select("id=dropdownCreditCardExpirationMM", "value=09"); break;
//					case 10: selenium.select("id=dropdownCreditCardExpirationMM", "value=10"); break;
//					case 11: selenium.select("id=dropdownCreditCardExpirationMM", "value=11"); break;
//					case 12: selenium.select("id=dropdownCreditCardExpirationMM", "value=12"); break;
//					default: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
				}
				
				//Thread.sleep(5000L);
				//selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
				dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationYYYY"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(creditCardYear);
				
				element = driver.findElement(By.id("creditCardHolderName"));element.sendKeys("TestCC");//selenium.type("id=creditCardHolderName", "TestCC");
				element = driver.findElement(By.id("creditCardCSC"));element.sendKeys("123");//selenium.type("id=creditCardCSC", "123");
				//Thread.sleep(5000L);
				
				//if (selenium.isElementPresent("id=payment_shipping_address")==true)
				if (isElementPresent(driver, "id", "payment_shipping_address")==true)
				{
					driver.findElement(By.id("payment_shipping_address")).click();//selenium.click("id=payment_shipping_address");
				}
				//if (selenium.isElementPresent("id=billingAddressSelect")==true)
				if (isElementPresent(driver, "id", "billingAddressSelect")==true)
				{
					//selenium.select("id=billingAddressSelect", "label=Create New");
					dropDownListElement = driver.findElement(By.id("billingAddressSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("Create New");
				}
				
				int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				
				if (("Billing_StandardUSA").equals(billAddressType))
				{ 
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("address1"));element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("shippingStreetAddress", addressMap.get("streetAddress"));
					outputMap.put("shippingCity", addressMap.get("city"));
					outputMap.put("shippingState", addressMap.get("state"));
					outputMap.put("shippingZip", addressMap.get("postalCode"));
					outputMap.put("shippingCountry", "USA");
				}
				else if (("Billing_APOBox_USA").equals(billAddressType))
				{ 
					
					String billingStreetAddress ="APO Base #"+randomNumber;
					String billingCity ="APO";
					String billingState ="AP";
					String billingZip ="96201";
					String billingCountry ="USA";
					
//					selenium.select("id=billingCountrySelect", "label=US");
//					selenium.type("id=address1",billingStreetAddress );
//					selenium.type("id=city", billingCity);
//					selenium.select("id=billingStateSelect", "label="+billingState);
//					selenium.type("id=postalCode", billingZip);
					
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);
					
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				else if (("Billing_POBox_USA").equals(billAddressType))
				{ 
					
					String billingStreetAddress ="PO Box #"+randomNumber;
					
//					selenium.select("id=billingCountrySelect", "label=US");
//					selenium.type("id=address1",billingStreetAddress );
//					selenium.type("id=city", addressMap.get("city"));
//					selenium.select("id=billingStateSelect", addressMap.get("state"));;
//					selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));
					
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("shippingCity", addressMap.get("city"));
					outputMap.put("shippingState", addressMap.get("state"));
					outputMap.put("shippingZip", addressMap.get("postalCode"));
					outputMap.put("shippingCountry", "USA");
				}
				else if (("Billing_StandardCANADA").equals(billAddressType))
				{ 
						
					String billingStreetAddress ="450 Terminal Avenue";
					String billingCity ="Ottawa";
					String billingState ="ON";
					String billingCountry ="Canada";
					String billingZip="K1G 0Z3";	
					
//					selenium.select("id=billingCountrySelect", "label=CANADA");
//					selenium.type("id=address1",billingStreetAddress );
//					selenium.type("id=city",billingCity );
//					selenium.select("id=billingStateSelect", "label="+billingState);
//					selenium.type("id=postalCode",billingZip);
					
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				else if (("Billing_POBox_CANADA").equals(billAddressType))
				{
					String billingStreetAddress ="PO Box #"+randomNumber;
					String billingCity ="Fredericton";
					String billingState ="NB";
					String billingCountry ="Canada";
					String billingZip="E3B 1E4";
					
//					selenium.select("id=billingCountrySelect", "label=CANADA");
//					selenium.type("id=address1",billingStreetAddress );
//					selenium.type("id=city",billingCity );
//					selenium.select("id=billingStateSelect", "label="+billingState);
//					selenium.type("id=postalCode",billingZip );
					
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				else if (("Billing_USPossession_USA").equals(billAddressType))
				{ 
						
					String billingStreetAddress ="606 Tito Castro Avenue";
					String billingCity ="Ponce";
					String billingState ="PR";
					String billingCountry ="USA";
					String billingZip="00731";	
					
//					selenium.select("id=billingCountrySelect", "label=US");
//					selenium.type("id=address1",billingStreetAddress );
//					selenium.type("id=city",billingCity );
//					selenium.select("id=billingStateSelect", "label="+billingState);
//					selenium.type("id=postalCode",billingZip);
					
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
						
											
				//if (selenium.isElementPresent("id=newPaymentMethod")==true)
				if (isElementPresent(driver, "id", "newPaymentMethod")==true)	
				{
					driver.findElement(By.id("newPaymentMethod")).click();//selenium.click("id=newPaymentMethod");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				//if (selenium.isElementPresent("id=newPaymentMethod")==true)
				if (isElementPresent(driver, "id", "newPaymentMethod")==true)	
				{
					driver.findElement(By.id("newPaymentMethod")).click();//selenium.click("id=newPaymentMethod");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
							
				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}
		
		
		/**
		 * Populates the EFT payment information for a new Billing Profile 
		 * 
		 * @param selenium Default Selenium 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param accountType Account Type (Checking or Savings)
		 * @param accountNumber Bank Account Number
		 * @param routingNumber Bank Routing Number
		 */
		public void populateExistingUser_EFTPaymentInformation(WebDriver driver,String maxPageLoadInMs,String accountType,String accountNumber,String routingNumber)
		{
			try
			{
				TestRun.startTest("Populate new EFT Information");

				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;

				//if(selenium.isElementPresent("link=Add a new bank account")==true)
				if(isElementPresent(driver, "linktext", "Add a new bank account"))
				{
					driver.findElement(By.linkText("Add a new bank account")).click();//selenium.click("link=Add a new bank account");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				//selenium.click("id=radioPaymentEFT");
				element = driver.findElement(By.id("accountName"));element.sendKeys("TestEFT");//selenium.type("id=accountName", "TestEFT");
				
				//selenium.select("id=dropdownAccountType", "value="+accountType);
				dropDownListElement = driver.findElement(By.id("dropdownAccountType"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(accountType);
				
				element = driver.findElement(By.id("routingNumber"));element.sendKeys(routingNumber);//selenium.type("id=routingNumber",routingNumber);
				element = driver.findElement(By.id("accountNumber"));element.sendKeys(accountNumber);//selenium.type("id=accountNumber",accountNumber);
				element = driver.findElement(By.id("confirmAccountNumber"));element.sendKeys(accountNumber);//selenium.type("id=confirmAccountNumber",accountNumber);
				//selenium.focus("id=confirmAccountNumber");
				
				//The native key press (TAB) is necessary in order to identify the correct credit card type
				element.sendKeys(Keys.TAB);//selenium.keyPressNative("9");
				
				if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
				{
					driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			
		}
		
		/**
		 * Launches the Existing Payment Method Page (assumes the current page is company_shipping.jsp) 
		 * 
		 * @param selenium Default Selenium 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 */
		public void launchNew_ExistingPaymentMethodPage(WebDriver driver,String maxPageLoadInMs)
		{
			try
			{
				TestRun.startTest("Launch New Payment Method Page");
				//if (selenium.isElementPresent("id=shippingButton")==true)
				JavascriptExecutor js;
				try
				{
					//driver.findElement(By.id("shippingButton")).click();
					if(isElementPresent(driver,"id","shippingButton"))
					{
						driver.findElement(By.id("shippingButton")).click();
					}
					
					//********************Added this after the UI change made by Estore on 4/11/12 in the TRN environment*******************
					
					else if(isElementPresent(driver, "classname", "clearfix"))//coBtn clearfix
					{
						try
						{
							js = (JavascriptExecutor) driver;
							
							//Will check which script is present for the page and then run
							if(driver.getPageSource().contains("payment_method_new_user.jsp"))
							{
								js.executeScript("document.location.replace('/commerce/checkout/secure/estore/payment_method_new_user.jsp')");
							}
							else if(driver.getPageSource().contains("payment_method.jsp"))
							{
								js.executeScript("document.location.replace('/commerce/checkout/secure/estore/payment_method.jsp')");
							}
							else if(driver.getPageSource().contains("shoppingcart_review.jsp"))
							{
								js.executeScript("document.location.replace('/commerce/checkout/secure/estore/shoppingcart_review.jsp')");
							}
							
						}
						catch(ScriptException e)
						{
							//Do nothing since the page still loads
						}
					}
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				//if (selenium.isElementPresent("id=buttonContinue")==true)
				try
				{
					driver.findElement(By.id("buttonContinue")).click();//selenium.click("id=buttonContinue");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp") || driver.getCurrentUrl().toLowerCase().contains("payment_method.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS, "The Existing Payment Method Page has successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL, "The Existing Payment Method Page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
		}
		
		/**
		 * Launches the Existing Payment Method Page (assumes the current page is company_shipping.jsp) 
		 * 
		 * @param selenium Default Selenium 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 */
		public void launch_SkipPaymentMethodPage(WebDriver driver,String maxPageLoadInMs)
		{
			try
			{
				TestRun.startTest("Skipping Payment Method Page");
				JavascriptExecutor js;
				if (isElementPresent(driver, "id", "shippingButton"))
				{
					driver.findElement(By.id("buttonContinue")).click();
				}
				
				
				
				else if(isElementPresent(driver, "classname", "clearfix") && driver.getPageSource().contains("shoppingcart_review.jsp"))//coBtn clearfix
				{
					try
					{
						js = (JavascriptExecutor) driver;
						js.executeScript("return document.location.replace('/commerce/checkout/secure/estore/shoppingcart_review.jsp')");
					}
					catch(ScriptException e)
					{
						//Do nothing since the page still loads
					}
				}
				
				
				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
		}


		/**
		 * Launches the Estore offering page for bundles and verifies that the page has been loaded properly.
		 * 
		 * @param selenium Default Selenium 
		 * @param item Bundle Item that's selected
		 * @param cpcIndividualBankStr max 25
		 * @param cpcIndividualUsageStr max 50
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 */
		public HashMap<String,Object> addUsageBundleOfferingToShoppingCart(WebDriver driver,String item,String maxPageLoadInMs,String cpcIndividualBankStr, String cpcIndividualUsageStr, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Add Offering to Shopping Cart");
				
				driver.findElement(By.linkText("Please click here to go back to CPC items")).click();//selenium.click("link=Please click here to go back to CPC items");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
				//selenium.select("name=sku", "label="+item);
				//Selects a specific offering from the dropdown menu
				WebElement dropDownListElement = driver.findElement(By.name("sku"));
				Select dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(item);
				
				Thread.sleep(5000L);
				
				driver.findElement(By.id("cpcInstance")).click();//selenium.click("id=cpcInstance");
				
				//if (selenium.isElementPresent("//form[2]/select")==true)
				try
				{
					Thread.sleep(5000L);
					//selenium.select("//form[2]/select","label="+cpcIndividualBankStr);
					dropDownListElement = driver.findElement(By.xpath("//form[2]/select"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(cpcIndividualBankStr);
					Thread.sleep(5000L);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				//if (driver.isElementPresent("//select[2]")==true)
				try
				{
					//selenium.select("//select[2]","label="+cpcIndividualUsageStr);
					dropDownListElement = driver.findElement(By.xpath("//select[2]"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(cpcIndividualUsageStr);
					Thread.sleep(5000L);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				//if (driver.isElementPresent("id=buttonFormSubmit")==true)
				try
				{
					driver.findElement(By.id("buttonFormSubmit")).click();//selenium.click("id=buttonFormSubmit");
				
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					//Thread.sleep(10000L);
				}
				catch(Exception e)
				{
					//Button not available
				}
				
				if(driver.getCurrentUrl().toLowerCase().contains("shopping_cart.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.endTest();
			}
			return outputMap;
		}
		
		
		
		public boolean isElementPresent(WebDriver driver, String type, String locator)
		{
			boolean isPresent = false;
			
			try
			{
				if(type.toLowerCase().equals("xpath"))
				{
//					element  = (WebElement) driver.findElement(By.xpath(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.xpath(locator)).size() != 0);
					
				}
				else if(type.toLowerCase().equals("id"))
				{
//					element  = (WebElement) driver.findElement(By.id(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.id(locator)).size() != 0);
				}
				else if(type.toLowerCase().equals("name"))
				{
//					element  = (WebElement) driver.findElement(By.name(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.name(locator)).size() != 0);
				}
				else if(type.toLowerCase().equals("css"))
				{
//					element  = (WebElement) driver.findElement(By.cssSelector(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.cssSelector(locator)).size() != 0);
				}
				else if(type.toLowerCase().equals("linktext"))
				{
//					element  = (WebElement) driver.findElement(By.linkText(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.linkText(locator)).size() != 0);
				}
				else if(type.toLowerCase().equals("partiallink"))
				{
//					element  = (WebElement) driver.findElement(By.partialLinkText(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.partialLinkText(locator)).size() != 0);
				}
				else if(type.toLowerCase().equals("tagname"))
				{
//					element  = (WebElement) driver.findElement(By.tagName(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.tagName(locator)).size() != 0);
				}
				else if(type.toLowerCase().equals("classname"))
				{
//					element  = (WebElement) driver.findElement(By.className(locator));
//					isPresent = element.isDisplayed();
					isPresent = (driver.findElements(By.className(locator)).size() != 0);
				}
			}
			catch(Exception e)
			{
				isPresent = false;
			}
			return isPresent;
		}
		
		
		
		
		
	

//		/**
//		 * Launches the Estore offering page for bundles and verifies that the page has been loaded properly.
//		 * 
//		 * @param selenium Default Selenium 
//		 * @param item Bundle Item that's selected
//		 * @param cpcIndividualBankStr max 25
//		 * @param cpcIndividualUsageStr max 50
//		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
//		 */
//		public HashMap<String,Object> addAnyOfferingToShoppingCart(WebDriver driver,String item,String maxPageLoadInMs,String cpcIndividualBankStr, String cpcIndividualUsageStr, HashMap<String,Object> outputMap)
//		{
//			try
//			{
//				TestRun.startTest("Add Offering to Shopping Cart");
//				
//				selenium.click("link=Please click here to go back to CPC items");
//				selenium.waitForPageToLoad(maxPageLoadInMs);
//				selenium.select("name=sku", "label="+item);
//				Thread.sleep(10000);
//				
//				//String order_id = selenium.getText(Utils.element("ConfirmationPage.OrderId"));
//				//String itemName = selenium.getText(Utils.element("ConfirmationPage.ItemName"));
//				//String totalPrice = selenium.getText(Utils.element("ConfirmationPage.TotalPrice"));
//				
//				selenium.isTextPresent("Edition");
//				selenium.isTextPresent("Fulfillment Method");
//				selenium.isTextPresent("Product Type");
//				selenium.isTextPresent("Number of Employees");
//				selenium.isTextPresent("Version");
//				selenium.isTextPresent("Users");
//				if(selenium.isTextPresent("Number of Employees") ==true)
//				{
//					System.out.println(selenium.getValue("id=attribName0"));
//					selenium.select("id=attrib0", "label=Up to 3 Employees");
//					//selenium.waitForPageToLoad(maxPageLoadInMs);
//					Thread.sleep(5000L);
//					
//				}
//				
//				
//				if (selenium.isElementPresent("//form[2]/select")==true)
//				
//				selenium.click("id=cpcInstance");
//				
//				if (selenium.isElementPresent("//form[2]/select")==true)
//					{
//					Thread.sleep(5000L);
//					selenium.select("//form[2]/select","label="+cpcIndividualBankStr);
//					Thread.sleep(5000L);
//				}
//				if (selenium.isElementPresent("//select[2]")==true)
//				{
//					selenium.select("//select[2]","label="+cpcIndividualUsageStr);
//					Thread.sleep(5000L);
//				}
//								
//				
//				Item itemToAddToMap = new Item();
//				itemToAddToMap.setName(item.substring(0,item.indexOf("|")).trim().replace(".", ""));
//				itemToAddToMap.setNumber(item.substring(item.indexOf("|")+1).trim());
//				lineItemsList.add(itemToAddToMap);
//				outputMap.put("ItemsList", lineItemsList);
//				
//				if (selenium.isElementPresent("id=buttonFormSubmit")==true)
//				{
//				selenium.click("id=buttonFormSubmit");
//				selenium.waitForPageToLoad(maxPageLoadInMs);
//				}
//				
//				if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
//				{
//					TestRun.updateStatus(TestResultStatus.PASS,"The product page was successfully loaded.");
//				}
//				else
//				{
//					TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not successfully load.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int i=0;i<listOferrors.size();i++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
//			}
//			catch(Exception e)
//			{
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int i=0;i<listOferrors.size();i++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//			}
//			finally
//			{
//				TestRun.endTest();
//			}
//			return outputMap;
//		}

		
		/**
		 * Populates the credit card payment information for a new Billing Profile 
		 * 
		 * @param selenium Default Selenium 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param creditCardNumber Credit Card Number
		 * @param creditCardMonth Credit Card Expiration Month
		 * @param creditCardYear Credit Card Expiration Year
		 * 
		 */
		public HashMap<String,Object> edit_CC_BillingAddressInformation(WebDriver driver,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear,String billAddressType,HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Edit & Populate Credit Card Payment with new Billing Address Information");
				
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				//if (selenium.isElementPresent("link=Edit")==true)
				if (isElementPresent(driver, "linktext", "Edit")==true)	
				{
					driver.findElement(By.linkText("Edit")).click();//selenium.click("link=Edit");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					//Thread.sleep(10000L);
				}
				if (isElementPresent(driver, "id", "creditCardCSC")==true)	
				{
					element = driver.findElement(By.id("creditCardCSC"));element.sendKeys("123");//selenium.type("id=creditCardCSC", "123");
				}
				
				
				//if (selenium.isElementPresent("id=payment_shipping_address")==true)
				if (isElementPresent(driver, "id", "payment_shipping_address")==true && driver.findElement(By.id("payment_shipping_address")).isSelected())	
				{
					driver.findElement(By.id("payment_shipping_address")).click();//selenium.click("id=payment_shipping_address");
				}
				//if (selenium.isElementPresent("id=billingAddressSelect")==true)
				if (isElementPresent(driver, "id", "billingAddressSelect")==true)	
				{
					//selenium.select("id=billingAddressSelect", "label=Create New");
					dropDownListElement = driver.findElement(By.id("billingAddressSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("Create New");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				
				if (("Billing_StandardUSA").equals(billAddressType))
				{ 
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("address1"));element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("billingStreetAddress", addressMap.get("streetAddress"));
					outputMap.put("billingCity", addressMap.get("city"));
					outputMap.put("billingState", addressMap.get("state"));
					outputMap.put("billingZip", addressMap.get("postalCode"));
					outputMap.put("billingCountry", "USA");
				}
				else if (("Billing_APOBox_USA").equals(billAddressType))
				{ 
					String billingStreetAddress ="APO Base #"+randomNumber;
					String billingCity ="APO";
					String billingState ="AP";
					String billingZip="96201";
					
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", "USA");
						
				}
				else if (("Billing_POBox_USA").equals(billAddressType))
				{ 
					
					String billingStreetAddress ="PO Box #"+randomNumber;
					
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
					element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", addressMap.get("city"));
					outputMap.put("billingState", addressMap.get("state"));
					outputMap.put("billingZip", addressMap.get("postalCode"));
					outputMap.put("billingCountry", "USA");
				}
				else if (("Billing_StandardCANADA").equals(billAddressType))
				{ 
					
					String billingStreetAddress ="450 Terminal Avenue";
					String billingCity ="Ottawa";
					String billingState ="ON";
					String billingCountry ="Canada";
					String billingZip="K1G 0Z3";
					
					//selenium.select("id=billingCountrySelect", "label=CANADA");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				else if (("Billing_POBox_CANADA").equals(billAddressType))
				{ 
					String billingStreetAddress ="PO Box #"+randomNumber;
					String billingCity ="Fredericton";
					String billingState ="NB";
					String billingCountry ="Canada";
					String billingZip="E3B 1E4";
					
					//selenium.select("id=billingCountrySelect", "label=CANADA");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("CANADA");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				else if (("Billing_USPossession_USA").equals(billAddressType))
				{ 
					String billingStreetAddress ="606 Tito Castro Avenue";
					String billingCity ="Ponce";
					String billingState ="PR";
					String billingCountry ="USA";
					String billingZip="00731";
					
					//selenium.select("id=billingCountrySelect", "label=US");
					dropDownListElement = driver.findElement(By.id("billingCountrySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText("US");
					element = driver.findElement(By.id("address1"));element.sendKeys(billingStreetAddress);//selenium.type("id=address1", addressMap.get("streetAddress"));
					element = driver.findElement(By.id("city"));element.sendKeys(billingCity);//selenium.type("id=city", addressMap.get("city"));
					//selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
					dropDownListElement = driver.findElement(By.id("billingStateSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(billingState);
					element = driver.findElement(By.id("postalCode"));element.sendKeys(billingZip);//selenium.type("id=postalCode", addressMap.get("postalCode"));
					
					outputMap.put("billingStreetAddress", billingStreetAddress);
					outputMap.put("billingCity", billingCity);
					outputMap.put("billingState", billingState);
					outputMap.put("billingZip", billingZip);
					outputMap.put("billingCountry", billingCountry);
				}
				
				if(isElementPresent(driver,"id","newPaymentMethod"))
				{
					driver.findElement(By.id("newPaymentMethod")).click();
				}
				
				driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
				
				if(driver.getPageSource().contains("found a matching address."))
				{
								{
					driver.findElement(By.id("newPaymentMethod")).click();//selenium.click("id=payment_shipping_address");
				}
				
				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			  }
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}

		
		/**
		* Populates the new customer information 
			 * 
			 * @param selenium Default Selenium
			 * @param userFirstName User First Name
			 * @param userLastName User Last Name
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param outputMap Map that will be populated with important information
			 * @return Output Map with the value for the following key ("AccountName") 
			 */
		public HashMap<String,Object> edit_ShippingAddress_Normal(WebDriver driver,String userFirstName,String userLastName,String maxPageLoadInMs,String shipAddressType,String shipType, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Edit & Populate New Shipping Information with New Shipping Address");
					
				int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				
				//if (selenium.isElementPresent("css=div.cart-review-right-section > #miniCartEditLink > a.blue_link_s")==true)
				if (isElementPresent(driver, "css", "div.cart-review-right-section > #miniCartEditLink > a.blue_link_s")==true)
				{
					driver.findElement(By.cssSelector("div.cart-review-right-section > #miniCartEditLink > a.blue_link_s")).click();//selenium.click("css=div.cart-review-right-section > #miniCartEditLink > a.blue_link_s");	
				}	
				//if (selenium.isElementPresent("link=Add address")==true)
				if (isElementPresent(driver, "linktext", "Add address")==true)
				{
					driver.findElement(By.linkText("Add address")).click();//selenium.click("link=Add address");
				}
					

				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;

				if (("Ship_Normal").equals(shipType))
			 	{	
					if (("Shipping_StandardUSA").equals(shipAddressType)) 
					{
						//selenium.select("id=shippingCountrySelect", "label=US");
						dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						
						element = driver.findElement(By.id("address1"));element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=address1",addressMap.get("streetAddress"));
						element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("id=city",addressMap.get("city"));
						//selenium.select("id=shippingStateSelect", "label="+addressMap.get("state"));
						dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
						element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode",addressMap.get("postalCode"));
						
						outputMap.put("shippingStreetAddress", addressMap.get("streetAddress"));
						outputMap.put("shippingCity", addressMap.get("city"));
						outputMap.put("shippingState", addressMap.get("state"));
						outputMap.put("shippingZip", addressMap.get("postalCode"));
						outputMap.put("shippingCountry", "USA");
					}
					else if(("Shipping_APOBox_USA").equals(shipAddressType))
					{
						//selenium.select("id=shippingCountrySelect", "label=US");
						dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						
						String shippingStreetAddress ="APO Base#"+randomNumber;
						String shippingCity ="APO";
						String shippingState ="AA";
						String shippingCountry ="US";
						String shippingZip="34002";
						
						element = driver.findElement(By.id("address1"));element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("id=city",shippingCity);
						//selenium.select("id=shippingStateSelect", "label="+shippingState);
						dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
						
						outputMap.put("shippingStreetAddress", shippingStreetAddress);
						outputMap.put("shippingCity", shippingCity);
						outputMap.put("shippingState", shippingState);
						outputMap.put("shippingZip", shippingZip);
						outputMap.put("shippingCountry", shippingCountry);
					}
					else if( ("Shipping_POBox_USA").equals(shipAddressType))
					{
						//selenium.select("id=shippingCountrySelect", "label=US");
						dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						
						String streetAddress ="PO Box #"+randomNumber;
						element = driver.findElement(By.id("address1"));element.sendKeys(streetAddress);//selenium.type("id=address1",streetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("id=city",addressMap.get("city"));
						//selenium.select("id=shippingStateSelect", "label="+addressMap.get("state"));
						dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
						element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode",addressMap.get("postalCode"));
						
						outputMap.put("shippingStreetAddress", streetAddress);
						outputMap.put("shippingCity", addressMap.get("city"));
						outputMap.put("shippingState", addressMap.get("state"));
						outputMap.put("shippingZip", addressMap.get("postalCode"));
						outputMap.put("shippingCountry", "USA");
					}
					else if( ("Shipping_StandardCANADA").equals(shipAddressType))
					{
						String shippingStreetAddress ="1350 Richmond Road";
						String shippingCity ="Ottawa";
						String shippingState ="ON";
						String shippingCountry ="Canada";
						String shippingZip="K2B 7Z3";
						
						//selenium.select("id=shippingCountrySelect", "label=CANADA");
						dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("CANADA");
						
						element = driver.findElement(By.id("address1"));element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("id=city",shippingCity);
						//selenium.select("id=shippingStateSelect","label="+shippingState);
						dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
						
						outputMap.put("shippingStreetAddress", shippingStreetAddress);
						outputMap.put("shippingCity", shippingCity);
						outputMap.put("shippingState", shippingState);
						outputMap.put("shippingZip", shippingZip);
						outputMap.put("shippingCountry", shippingCountry);
							
					}
					else if(("Shipping_POBox_CANADA").equals(shipAddressType))
					{
						String shippingStreetAddress ="PO Box #"+randomNumber;
						String shippingCity ="Calgary";
						String shippingState ="AB";
						String shippingCountry ="Canada";
						String shippingZip="T2J 3V1";
						
						//selenium.select("id=shippingCountrySelect", "label=CANADA");
						dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("CANADA");
						
						element = driver.findElement(By.id("address1"));element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
						//selenium.select("id=shippingStateSelect", "label="+shippingState);
						dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
						
						outputMap.put("shippingStreetAddress", shippingStreetAddress);
						outputMap.put("shippingCity", shippingCity);
						outputMap.put("shippingState", shippingState);
						outputMap.put("shippingZip", shippingZip);
						outputMap.put("shippingCountry", shippingCountry);
					}
					else if(("Shipping_USPossession_USA").equals(shipAddressType))
					{
						String shippingStreetAddress ="405 Ferrocaril St";
						String shippingCity ="Ponce";
						String shippingState ="PR";
						String shippingCountry ="USA";
						String shippingZip="00733";
						
						//selenium.select("id=shippingCountrySelect", "label=US");
						dropDownListElement = driver.findElement(By.id("shippingCountrySelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						
						element = driver.findElement(By.id("address1"));element.sendKeys(shippingStreetAddress);//selenium.type("id=address1",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("id=city",shippingCity);
						//selenium.select("id=shippingStateSelect", "label="+shippingState);
						dropDownListElement = driver.findElement(By.id("shippingStateSelect"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("id=postalCode",shippingZip);
						
						
						outputMap.put("shippingStreetAddress", shippingStreetAddress);
						outputMap.put("shippingCity", shippingCity);
						outputMap.put("shippingState", shippingState);
						outputMap.put("shippingZip", shippingZip);
						outputMap.put("shippingCountry", shippingCountry);
					}
												
					//if (selenium.isElementPresent("id=addNewShippingButton")==true)
					if (isElementPresent(driver, "id", "addNewShippingButton")==true)
					{
						driver.findElement(By.id("addNewShippingButton")).click();//selenium.click("id=addNewShippingButton");
						//selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					//if (selenium.isElementPresent("id=standardizeAddressSelected2")==true)
					if (isElementPresent(driver, "id", "standardizeAddressSelected2")==true)
					{
						driver.findElement(By.id("standardizeAddressSelected2")).click();//selenium.click("id=standardizeAddressSelected2");
						driver.findElement(By.id("addNewShippingButton")).click();//selenium.click("id=addNewShippingButton");
						//selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					//if (selenium.isElementPresent("id=selectShippingButton")==true)
					if (isElementPresent(driver, "id", "selectShippingButton")==true)
					{
						driver.findElement(By.id("selectShippingButton")).click();//selenium.click("id=selectShippingButton");
						//selenium.waitForPageToLoad(maxPageLoadInMs);
					}	
					
					
					if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp") || driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The Review order Page has successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The Review order Page did not successfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
				  }
				}
				catch(Exception e)
				{
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				
				return outputMap;
			}


		/**
		* Populates the new customer information 
			 * 
			 * @param selenium Default Selenium
			 * @param userFirstName User First Name
			 * @param userLastName User Last Name
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param outputMap Map that will be populated with important information
			 * @return Output Map with the value for the following key ("AccountName")
			 */
		public HashMap<String,Object> edit_3rdPartyShippingAddress_Normal(WebDriver driver,String userFirstName,String userLastName,String maxPageLoadInMs, String ShipAddressType,String ShipType, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Populate New Shipping Information with New 3rd Party ship Addrtess");
				
				int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				java.util.Date dNow = new java.util.Date();
				SimpleDateFormat ft =   new SimpleDateFormat ("yyyyMMddhhmmss"); //E yyyyMMddhhmmss
			   	String NewComapanyName=(ft.format(dNow));
				String accountName = "E2EAutomated_" + NewComapanyName;
				outputMap.put("accountName3rdPartyShip", accountName);
				sLog.info("Account Name: " + accountName);
				
				//if (selenium.isElementPresent("css=div.cart-review-right-section > #miniCartEditLink > a.blue_link_s")==true)
				if (isElementPresent(driver, "css", "css=div.cart-review-right-section > #miniCartEditLink > a.blue_link_s")==true)
				{
					driver.findElement(By.cssSelector("css=div.cart-review-right-section > #miniCartEditLink > a.blue_link_s")).click();//selenium.click("css=div.cart-review-right-section > #miniCartEditLink > a.blue_link_s");	
					Thread.sleep(10000L);
				}
				//if (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
				if (isElementPresent(driver, "id", "shippingAddressSameAsCompanyAddress")==true)
				{
					driver.findElement(By.id("shippingAddressSameAsCompanyAddress")).click();//selenium.click("id=shippingAddressSameAsCompanyAddress");
				}
				//if (selenium.isElementPresent("id=radioThirdPartyAddress")==true)
				if (isElementPresent(driver, "id", "radioThirdPartyAddress")==true)
				{
					driver.findElement(By.id("radioThirdPartyAddress")).click();//selenium.click("id=radioThirdPartyAddress");
				}
				
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				if(("Ship_3rdParty").equals(ShipType))
				{
					element = driver.findElement(By.id("accountName"));element.sendKeys("Ship"+accountName);//selenium.type("id=accountName", "Ship"+accountName);
						
					if(("Shipping_StandardUSA").equals(ShipAddressType))
					{
						//selenium.select("id=country", "label=US");
						dropDownListElement = driver.findElement(By.id("country"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");

						element = driver.findElement(By.id("third_party_streetAddress"));element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=third_party_streetAddress",addressMap.get("streetAddress"));
						element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("name=city",addressMap.get("city"));
						//selenium.select("id=state", "label="+addressMap.get("state"));
						dropDownListElement = driver.findElement(By.id("state"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
						element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("name=postalCode",addressMap.get("postalCode"));
						
						outputMap.put("shipping3rdPartyStreetAddress", addressMap.get("streetAddress"));
						outputMap.put("shipping3rdPartyCity", addressMap.get("city"));
						outputMap.put("shipping3rdPartyState", addressMap.get("state"));
						outputMap.put("shipping3rdPartyZip", addressMap.get("postalCode"));
						outputMap.put("shipping3rdPartyCountry", "USA");
					}
					else if(("Shipping_APOBox_USA").equals(ShipAddressType))
					{
						String shippingStreetAddress ="APO Base # "+NewComapanyName;
						String shippingCity ="APO";
						String shippingState ="AA";
						String shippingCountry ="USA";
						String shippingZip="34002";
						
						//selenium.select("id=country", "label=US");
						dropDownListElement = driver.findElement(By.id("country"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						
						element = driver.findElement(By.id("third_party_streetAddress"));element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
						//selenium.select("id=state", "label="+shippingState);
						dropDownListElement = driver.findElement(By.id("state"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
						
						outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
						outputMap.put("shipping3rdPartyCity", shippingCity);
						outputMap.put("shipping3rdPartyState", shippingState);
						outputMap.put("shipping3rdPartyZip", shippingZip);
						outputMap.put("shipping3rdPartyCountry", shippingCountry);
					}
					else if(("Shipping_POBox_USA").equals(ShipAddressType))
					{
						//selenium.select("id=country", "label=US");
						dropDownListElement = driver.findElement(By.id("country"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						
						String streetAddress ="PO Box #"+randomNumber;
						element = driver.findElement(By.id("third_party_streetAddress"));element.sendKeys(streetAddress);//selenium.type("id=third_party_streetAddress",streetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("name=city",addressMap.get("city"));
						//selenium.select("id=state", "label="+addressMap.get("state"));
						dropDownListElement = driver.findElement(By.id("state"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
						element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("name=postalCode",addressMap.get("postalCode"));
						
						outputMap.put("shipping3rdPartyStreetAddress", streetAddress);
						outputMap.put("shipping3rdPartyCity", addressMap.get("city"));
						outputMap.put("shipping3rdPartyState", addressMap.get("state"));
						outputMap.put("shipping3rdPartyZip", addressMap.get("postalCode"));
						outputMap.put("shippingCountry", "USA");
					}
					else if(("Shipping_USPossession_USA").equals(ShipAddressType))
					{
						String shippingStreetAddress ="405 Ferrocaril St";
						String shippingCity ="Ponce";
						String shippingState ="PR";
						String shippingCountry ="USA";
						String shippingZip="00733";
						
						//selenium.select("id=country", "label=US");
						dropDownListElement = driver.findElement(By.id("country"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						element = driver.findElement(By.id("third_party_streetAddress"));element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
						//selenium.select("id=state", "label="+shippingState);
						dropDownListElement = driver.findElement(By.id("state"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
						
						outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
						outputMap.put("shipping3rdPartyCity", shippingCity);
						outputMap.put("shipping3rdPartyState", shippingState);
						outputMap.put("shipping3rdPartyZip", shippingZip);
						outputMap.put("shipping3rdPartyCountry", shippingCountry);
					}
					else if(("Shipping_StandardCANADA").equals(ShipAddressType))
					{
						String shippingStreetAddress ="11300 Tuscany Blvd NW Apt";
						String shippingCity ="Calgary";
						String shippingState ="AB";
						String shippingCountry ="Canada";
						String shippingZip="T3L 2V7";
						
						//selenium.select("id=country", "label=CANADA");
						dropDownListElement = driver.findElement(By.id("country"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("CANADA");
						element = driver.findElement(By.id("third_party_streetAddress"));element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
						//selenium.select("id=state", "label="+shippingState);
						dropDownListElement = driver.findElement(By.id("state"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
						
						outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
						outputMap.put("shipping3rdPartyCity", shippingCity);
						outputMap.put("shipping3rdPartyState", shippingState);
						outputMap.put("shipping3rdPartyZip", shippingZip);
						outputMap.put("shipping3rdPartyCountry", shippingCountry);
					}
					else if(("Shipping_POBox_CANADA").equals(ShipAddressType))
					{
						String shippingStreetAddress ="PO Box #"+NewComapanyName;
						String shippingCity ="Calgary";
						String shippingState ="AB";
						String shippingCountry ="Canada";
						String shippingZip="T2J 3V1";
						
						//selenium.select("id=country", "label=US");
						dropDownListElement = driver.findElement(By.id("country"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText("US");
						element = driver.findElement(By.id("third_party_streetAddress"));element.sendKeys(shippingStreetAddress);//selenium.type("id=third_party_streetAddress",shippingStreetAddress);
						element = driver.findElement(By.id("city"));element.sendKeys(shippingCity);//selenium.type("name=city",shippingCity);
						//selenium.select("id=state", "label="+shippingState);
						dropDownListElement = driver.findElement(By.id("state"));
						dropDownListElementSelect = new Select(dropDownListElement);
						dropDownListElementSelect.selectByVisibleText(shippingState);
						element = driver.findElement(By.id("postalCode"));element.sendKeys(shippingZip);//selenium.type("name=postalCode",shippingZip);
						
						outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
						outputMap.put("shipping3rdPartyCity", shippingCity);
						outputMap.put("shipping3rdPartyState", shippingState);
						outputMap.put("shipping3rdPartyZip", shippingZip);
						outputMap.put("shipping3rdPartyCountry", shippingCountry);		
					}
					
					element = driver.findElement(By.id("phoneNumber"));element.sendKeys(addressMap.get("phoneNumber"));//selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
					element = driver.findElement(By.id("firstName"));element.sendKeys(userFirstName);//selenium.type("id=firstName",userFirstName);
					element = driver.findElement(By.id("lastName"));element.sendKeys(userLastName);//selenium.type("id=lastName",userLastName);
					element = driver.findElement(By.id("email"));element.sendKeys("Ship"+accountName+"@intuit.com");//selenium.type("id=email","Ship"+accountName+"@intuit.com");
					
				}
				
				//if (selenium.isElementPresent("id=thirdPartyShipping")==true)
				if (isElementPresent(driver, "id", "thirdPartyShipping")==true)
				{
					driver.findElement(By.id("thirdPartyShipping")).click();//selenium.click("id=thirdPartyShipping");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				//if (selenium.isElementPresent("id=thirdPartyShipping")==true)
				if (isElementPresent(driver, "id", "thirdPartyShipping")==true)
				{
					driver.findElement(By.id("thirdPartyShipping")).click();//selenium.click("id=thirdPartyShipping");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}			
				//if (selenium.isElementPresent("id=selectShippingButton")==true)
				if (isElementPresent(driver, "id", "selectShippingButton")==true)
				{
					driver.findElement(By.id("selectShippingButton")).click();//selenium.click("id=selectShippingButton");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}	
				
				
				if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp") || driver.getCurrentUrl().toLowerCase().contains("payment_method.jsp"))
				{
						TestRun.updateStatus(TestResultStatus.PASS,"The Payment Page has successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The Payment did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			
			return outputMap;
		}
	
		
		/**
		 * Click at review order page and send info of existing payment info from review order page
		 * @author sranjan
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param outputMap Map that will be populated with important information
		 * @return Output Map with the value for the following key ("CardType"), ,("CardEndingWith") 
		 */
		
		public HashMap<String,Object> clickUpdateBPLinkAtReviewPage(WebDriver driver,String maxPageLoadInMs, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Update Billing Profile at Review Order Page");
				
				//String cardType = selenium.getText("css=div.cart-review-right-details");
				String cardType = driver.findElement(By.cssSelector("div.cart-review-right-details")).getText();
				
				if(cardType.toLowerCase().contains("visa"))
				{
					outputMap.put("CardType", "visa");
					//System.out.println("-----------------------------------");
					//System.out.println("Test card -"+"visa");
					//System.out.println("-----------------------------------");
				}
				
				else if(cardType.toLowerCase().contains("amex"))
				{
					outputMap.put("CardType", "amex");
					
				}
				else if(cardType.toLowerCase().contains("mastercard"))
				{
					outputMap.put("CardType", "mastercard");
					
				}
				else if(cardType.toLowerCase().contains("discover"))
				{
					outputMap.put("CardType", "discover");
					
				}
				else if(cardType.toLowerCase().contains("Checking"))
				{
					outputMap.put("CardType", "EFT");
					
				}
				//String cardEndingNumber = selenium.getText("css=div.cart-review-right-details > b");
				String cardEndingNumber = driver.findElement(By.cssSelector("div.cart-review-right-details > b")).getText();
				
				
				outputMap.put("CardEndingWith", cardEndingNumber);
				
				driver.findElement(By.linkText("Edit")).click();//selenium.click("link=Edit");
					
				//selenium.waitForPageToLoad(maxPageLoadInMs);
								
				if(driver.getCurrentUrl().toLowerCase().contains("payment_method.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The payment method page has successfully loaded from review page.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The payment method page did not succesfully loaded from review page.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
				
							
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}
		
		
		/**
		 * Add payment option from review order page with user supplied info or user's existing type of BP
		 * @author sranjan
		 * @param selenium Default Selenium 
		 * @param cardType Card Type of the billing profile
		 * @param cardEndNo Card Ending number
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param outputMap Map that will be populated with important information
		 * @return Output Map with the value for the following key ("CardType"), ,("CreditCardNumber"),("ExpiryMonth"),("ExpiryYear") & ("CVV")
		 */
		
		public HashMap<String,Object> updatePaymentWithExistingInfo(WebDriver driver,String cardType, String cardEndNo, String maxPageLoadInMs, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Update Payment profile with CC info, considering user's existing card preference");
				
				Thread.sleep(5000L);
				//if (selenium.isElementPresent("link=Add a new card")==true)
				if (isElementPresent(driver, "linktext", "Add a new card")==true)	
				{
					driver.findElement(By.linkText("Add a new card")).click();//selenium.click("link=Add a new card");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					//Thread.sleep(5000L);
				}
					
				if(driver.getCurrentUrl().toLowerCase().contains("payment_method_new_user.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The new payment method page has successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The payment method page did not succesfully loaded.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
				
				
			   String newCard = alternateCardNumber(cardType, cardEndNo);
			   
	    	   //Setting the new card number
			   WebElement element;
			   WebElement dropDownListElement;
			   Select dropDownListElementSelect;
			   
			   element = driver.findElement(By.id("creditCardNumber"));element.sendKeys(newCard);//selenium.type("id=creditCardNumber", newCard);
	    	   
			   //The native key press (TAB) is necessary in order to identify the correct credit card type
			   //selenium.focus("id=creditCardNumber");
	    	   //selenium.keyPressNative("9");	
			   element.sendKeys(Keys.TAB);
			   
	    	   //selenium.select("id=dropdownCreditCardExpirationMM", "index=4");
	    	   dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationMM"));
	    	   dropDownListElementSelect = new Select(dropDownListElement);
	    	   dropDownListElementSelect.selectByIndex(4);
	    	   
	    	   //String monthInt = selenium.getSelectedIndex("id=dropdownCreditCardExpirationMM");  //*****************************
	    	   String monthInt = driver.findElement(By.id("dropdownCreditCardExpirationMM")).getText();
	    	   
	    	   //selenium.select("id=dropdownCreditCardExpirationYYYY", "index=9");
	    	   dropDownListElement = driver.findElement(By.id("dropdownCreditCardExpirationYYYY"));
	    	   dropDownListElementSelect = new Select(dropDownListElement);
	    	   dropDownListElementSelect.selectByIndex(9);
	    	   
	    	   //String yearInt = selenium.getSelectedIndex("id=dropdownCreditCardExpirationYYYY");  //*****************************
	    	   String yearInt = driver.findElement(By.id("dropdownCreditCardExpirationYYYY")).getText();
	    	   
	    	   //Setting cvv
	    	   String newCvv = "";
			   if(cardType.equals("amex"))
			   {
				   newCvv = getRandomValueInLimit(1000,9999);
				   element = driver.findElement(By.id("creditCardCSC"));element.sendKeys(newCvv);//selenium.type("id=creditCardCSC", newCvv);
			   }
			   else
			   {
				  newCvv = getRandomValueInLimit(100,410);
				  element = driver.findElement(By.id("creditCardCSC"));element.sendKeys(newCvv);//selenium.type("id=creditCardCSC", newCvv);
			   }
			   
			   element = driver.findElement(By.id("creditCardHolderName"));element.sendKeys("TestCC");//selenium.type("id=creditCardHolderName", "TestCC");
			   
			   
			   outputMap.put("CardType", cardType);
	    	   outputMap.put("CreditCardNumber", newCard);
	    	   outputMap.put("ExpiryMonth", monthInt);
	    	   outputMap.put("ExpiryYear", yearInt);
	    	   outputMap.put("CVV", newCvv);
	    	 	   
	    	   Thread.sleep(10000);
	    	   
	    	   driver.findElement(By.id("newPaymentMethod")).click();//selenium.click("id=newPaymentMethod");
					
	    	   //selenium.waitForPageToLoad(maxPageLoadInMs);
	    	   
	    	   if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
	    	   {
					TestRun.updateStatus(TestResultStatus.PASS,"The shoppingcart review page has successfully loaded. " +
							"The nEw Credit Card getting in purchase has these details. Credit card No - {"+newCard+"} , Expiry Month Index- {"+monthInt+"} , Expiry Year Index- {"+yearInt+"}, CVV -{"+newCvv+"}"); 
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The update shoppingcart review page did not succesfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}			
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
		}
		
		
		
		/**
		 * Add EFT info by checking existing EFT and providing alternate EFT
		 * @author sranjan
		 * @param selenium Default Selenium 
		 * @param payType Existing payment option
		 * @param accountNo Account no. of existing BP
		 * @param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param outputMap Map that will be populated with important information
		 * @return  Output Map with the value for the following key ("EFTRoutingNumber"),("EFTAccountNumber")
		 */
		
		
		public HashMap<String,Object> populateExistingUser_EFTPaymentInformationWithAlternateOption(WebDriver driver,
				String payType, String accountNo, String maxPageLoadInMs,HashMap<String,Object> outputMap)
		{
			
			String accountNumber ="";
			String routingNumber="";
			try
			{
				TestRun.startTest("Populate alternate EFT Information");
				
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				//if(selenium.isElementPresent("link=Add a new bank account")==true)
				if(isElementPresent(driver, "linktext", "Add a new bank account"))
				{
					driver.findElement(By.linkText("Add a new bank account")).click();//selenium.click("link=Add a new bank account");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				if(accountNo.endsWith("891"))
	    	    {
	    	    	
					driver.findElement(By.id("radioPaymentEFT")).click();//selenium.click("id=radioPaymentEFT");
					element = driver.findElement(By.id("accountName"));element.sendKeys("TestEFT");//selenium.type("id=accountName", "TestEFT");
					//selenium.select("id=dropdownAccountType", "value="+"Checking");
					dropDownListElement = driver.findElement(By.id("dropdownAccountType"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue("Checking");
					
					element = driver.findElement(By.id("routingNumber"));element.sendKeys("011302920");//selenium.type("id=routingNumber","011302920");
					element = driver.findElement(By.id("accountNumber"));element.sendKeys("87654");//selenium.type("id=accountNumber","87654");
					element = driver.findElement(By.id("confirmAccountNumber"));element.sendKeys("87654");//selenium.type("id=confirmAccountNumber","87654");
					//selenium.focus("id=confirmAccountNumber");
					
					accountNumber = "87654";
					routingNumber = "011302920";
	    	    	outputMap.put("EFTRoutingNumber", "011302920");
	    	    	outputMap.put("EFTAccountNumber", "87654");	
	    	    }
	    	    else
	    	    {
	    	    	driver.findElement(By.id("radioPaymentEFT")).click();//selenium.click("id=radioPaymentEFT");
	    	    	element = driver.findElement(By.id("accountName"));element.sendKeys("TestEFT");//selenium.type("id=accountName", "TestEFT");
					//selenium.select("id=dropdownAccountType", "value="+"Checking");
					dropDownListElement = driver.findElement(By.id("dropdownAccountType"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByValue("Checking");
					element = driver.findElement(By.id("routingNumber"));element.sendKeys("063210112");//selenium.type("id=routingNumber","063210112");
					element = driver.findElement(By.id("accountNumber"));element.sendKeys("81686868686891");//selenium.type("id=accountNumber","81686868686891");
					element = driver.findElement(By.id("confirmAccountNumber"));element.sendKeys("81686868686891");//selenium.type("id=confirmAccountNumber","81686868686891");
					//selenium.focus("id=confirmAccountNumber");
					accountNumber = "81686868686891";
					routingNumber = "063210112";
	    	    	outputMap.put("EFTRoutingNumber", "063210112");
	    	    	outputMap.put("EFTAccountNumber", "81686868686891");
	    	    }
				
				//The native key press (TAB) is necessary in order to identify the correct credit card type
				element.sendKeys(Keys.TAB);//selenium.keyPressNative("9");
				if(isElementPresent(driver, "xpath", "//input[contains(@class, 'ontinue')]"))
				{
					driver.findElement(By.xpath("//input[contains(@class, 'ontinue')]")).click();//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				
				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully. The billing profile got " +
							"updated with these EFT details. Routing Number - {"+routingNumber+"} and Account Number - {"+accountNumber+"}");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			
			{
				TestRun.endTest();
			}
			return outputMap;
			
		}
		
		
		/**
		 * Method to delete the promotion from review order after matching with the item names
		 * @author sranjan
		 * @selenium 
		 * @param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 */
		public void deletePromotion(WebDriver driver,String maxPageLoadInMs)   //Need proper implementation
		{
			try
			{
				TestRun.startTest("Delete promotional item from the cart");
				
				//As of now what we see promotion always the 1st items; so we ll delete the 1st item considering it as the promotion
				
				WebElement element = driver.findElement(By.tagName("html"));
				String pageHtml = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", element);
				
				while(pageHtml.contains("PromotionItemDelete"))
						{
							//selenium.click("xpath=//a[contains(@href,'PromotionItemDelete')]");		
							driver.findElement(By.xpath("//a[contains(@href,'PromotionItemDelete')]")).click();
							Alert alert = driver.switchTo().alert();
							alert.accept();
//							selenium.getConfirmation();
//							selenium.waitForPageToLoad(maxPageLoadInMs);
							
						}
						
				
				//Checks that the offering page was loaded successfully
				//if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp") && !selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML").contains("PromotionItemDelete"))
				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp") && !pageHtml.contains("PromotionItemDelete"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The existing promotion got deleted");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The existing promotion could not get deleted");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
		}
		
		/**
		 * Apply promotion. Verifies promotion applied or not. Then sends after & before price to output
		 * @author sranjan
		 * @param selenium Default Selenium 
		 * @param promotionalCode Promotion code to be used 
		 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		 * @param outputMap Map that will be populated with important information
		 * @return Output Map with the value for the following key ("initialPriceBeforePromtion"), ("finalPriceAfterPromtion")
		 */
		public HashMap<String,Object> applyPromotion(WebDriver driver,String promotionalCode,String maxPageLoadInMs, HashMap<String,Object> outputMap)
		{
			try
			{
				TestRun.startTest("Apply Promotion to the cart");
				Thread.sleep(500);
				boolean bProm = false;
				//WebElement element;
				WebElement element = driver.findElement(By.tagName("html"));
				String pageHtml = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", element);
				//String initialPrice = selenium.getText("id=orderTotal");
				String initialPrice = driver.findElement(By.id("orderTotal")).getText();
				
				//selenium.type("id=discount-code-input", promotionalCode);
				element = driver.findElement(By.id("discountCode"));element.clear();element.sendKeys(promotionalCode);
				//element = driver.findElement(By.id("discountCodeRD"));element.clear();element.sendKeys(promotionalCode);  //For NEW UI
				Thread.sleep(1000);
				
								
				  if(isElementPresent(driver, "id", "applyDiscount"))
					{
		    	    	driver.findElement(By.id("applyDiscount")).click();  //selenium.click("id=buttonApply");
					}
				  
//				  if(isElementPresent(driver, "id", "applyDiscountRD"))
//					{
//		    	    	driver.findElement(By.id("applyDiscountRD")).click();  //FOR NEW UI
//					}
				
				//String finalPrice = selenium.getText("id=orderTotal");
				  String finalPrice = driver.findElement(By.id("orderTotal")).getText();  
				outputMap.put("initialPriceBeforePromtion", initialPrice);
				outputMap.put("finalPriceAfterPromtion", finalPrice);
									
//				if(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML").contains("PromotionItemDelete"))
//				{
//					bProm = true;
//				}
				
				if(pageHtml.contains("PromotionItemDelete"))
				{
					bProm = true;
				}
				//if(driver.getLocation().toLowerCase().contains("shoppingcart_review.jsp") && (bProm = true))
				if(driver.getCurrentUrl().toLowerCase().contains("shoppingcart_review.jsp") && (bProm = true))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The promotion code applied successfully. " +
							"The intial price was{"+initialPrice+"} & now the final price is {"+finalPrice+"}");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The promotion code did not apply successfully.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));		
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}

			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				
				TestRun.endTest();
			}
			return outputMap;
		}
		
		
		
//		/**
//		 * Applies the promotions only and checks still in review page or not
//		 * @author sranjan
//		 * @param selenium Default Selenium 
//		 * @param promotionalCode Promotion code to be applied
//		 * @param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
//		 */
//		public void applyPromotionOnly(WebDriver driver,String promotionalCode,String maxPageLoadInMs)
//		{
//			try
//			{
//				TestRun.startTest("Apply Promotion to the cart");
//				Thread.sleep(500);
//				
//			
//				
//				selenium.type("id=discount-code-input", promotionalCode);
//				Thread.sleep(1000);
//				selenium.click("id=buttonApply");
//				selenium.waitForPageToLoad(maxPageLoadInMs);
//				
//									
//				
//				if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
//				{
//					TestRun.updateStatus(TestResultStatus.PASS,"The promotion code applied successfully.");
//				}
//				else
//				{
//					TestRun.updateStatus(TestResultStatus.FAIL,"The promotion code did not apply successfully.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int i=0;i<listOferrors.size();i++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
//
//			}
//			catch(Exception e)
//			{
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int i=0;i<listOferrors.size();i++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));;
//				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//			}
//			finally
//			{
//				TestRun.endTest();
//			}
//			
//		}	
//		
//		/**
//		 * This method checks for if Promotion is present in the cart and expects true for pass the case
//		 * @author sranjan
//		 * @param selenium Default Selenium 
//		 * @return bProm True if Promotion found in the cart. Else sends false.
//		 */
//		public boolean isPromotionFoundTrue(WebDriver driver)
//		{
//			
//			
//			boolean bProm = false;
//
//			try
//			{
//				TestRun.startTest("Checking if Promotion present in the cart = true");
//				Thread.sleep(500);
//				if(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML").contains("PromotionItemDelete"))
//					{
//						bProm = true;
//						TestRun.updateStatus(TestResultStatus.PASS,"Promotion found.");
//					}
//				else
//				{
//					bProm = false;
//					TestRun.updateStatus(TestResultStatus.FAIL,"Promotion not found.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int i=0;i<listOferrors.size();i++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
//					
//				
//
//			}
//			catch(Exception e)
//			{
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int i=0;i<listOferrors.size();i++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//			}
//			finally
//			{
//				TestRun.endTest();
//			}
//			return bProm;
//			
//		}
//		
//		/**
//		 * This methods checks if cart does not contain any promotion. It expects false to pass the test.
//		 * @author sranjan
//		 * @param selenium Default Selenium 
//		 * @return bProm False if promotion is not present in the cart. True if the promotion is present.
//		 */
//		public boolean isPromotionFoundFalse(WebDriver driver)
//		{
//			
//			
//			boolean bProm = false;
//
//			try
//			{
//				TestRun.startTest("Checking if Promotion present in the cart = false");
//				Thread.sleep(500);
//				if(!selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML").contains("PromotionItemDelete"))
//					{
//						
//						TestRun.updateStatus(TestResultStatus.PASS,"Promotion not found in the cart.");
//					}
//				else
//				{
//					bProm = true;
//					TestRun.updateStatus(TestResultStatus.FAIL,"Promotion found in the cart.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int i=0;i<listOferrors.size();i++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
//					
//				
//
//			}
//			catch(Exception e)
//			{
//				ArrayList<String> listOferrors = captureErrorMessage(driver);
//				for(int i=0;i<listOferrors.size();i++)
//				{
//					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
//				
//				}
//				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
//			}
//			finally
//			{
//				TestRun.endTest();
//			}
//			return bProm;
//			
//		}
		
			/**
			* Method to login to My Accounts using an existing account
			* @author sranjan
			* @param userId User id to USername to login
			* @param password Password for the account
			* @param estoreURLAccounts eStore My Account url
			* @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			*/
			public void myAccountLogin(WebDriver driver,String userId,String password,String estoreURL,String maxPageLoadInMs)
			{
				try
				{
					TestRun.startTest("Sign In to My Accounts with Existing User");
					
					WebElement element;
					
					//Opens the web page and waits for it to load 
					driver.get(estoreURL+"/commerce/account/secure/login.jsp");//selenium.open(estoreURL+"/commerce/account/secure/login.jsp");
					
					//selenium.waitForPageToLoad(maxPageLoadInMs);
									
					if(driver.getCurrentUrl().toLowerCase().contains("login.jsp"))	
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The account login page has successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The account login page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
					
					element = driver.findElement(By.id("userid"));element.sendKeys(userId);//selenium.type("id=userid",userId);
					element = driver.findElement(By.id("textPassword"));element.sendKeys(password);//selenium.type("id=textPassword",password);
					driver.findElement(By.id("wcgLoginButton")).click();//selenium.click("id=wcgLoginButton");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
						
					if(driver.getCurrentUrl().toLowerCase().contains("account.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The account page has successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The account page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
				}
				catch(Exception e)
				{
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
			}

			
			/**
			 * @param product The product on which service request will be created
			 * @author sranjan
			 * @param category Category of the service request
			 * @param subject Subject of the service request
			 * @param description Description of the service request
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param outputMap Map that will be populated with important information
			 * @return Output Map with the value for the following key ("ServiceRequestNumber") 
			 */
			
			public HashMap<String,Object> CreateServiceRequest(WebDriver driver,String product,String category,String subject,String description,String maxPageLoadInMs, HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Create a service request");
					
					WebElement element;
					WebElement dropDownListElement;
					Select dropDownListElementSelect;
					
					//Opens the new service request page and enters the data
					driver.findElement(By.linkText("Create New")).click();//selenium.click("link=Create New");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
									
					if(driver.getCurrentUrl().toLowerCase().contains("create_service_request.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The create service request page has successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The create service request page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
					
					//selenium.select("id=productSelect", "label="+product);
					dropDownListElement = driver.findElement(By.id("productSelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(product);
					
					//Thread.sleep(4000000);
					
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					//selenium.select("id=categorySelect", "label="+category);
					dropDownListElement = driver.findElement(By.id("categorySelect"));
					dropDownListElementSelect = new Select(dropDownListElement);
					dropDownListElementSelect.selectByVisibleText(category);
					
					element = driver.findElement(By.id("subjectText"));element.sendKeys(subject);//selenium.type("id=subjectText", subject);
					element = driver.findElement(By.name("details"));element.sendKeys(description);//selenium.type("name=details", description);
					driver.findElement(By.id("buttonCreateServiceRequest")).click();//selenium.click("id=buttonCreateServiceRequest");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					
					//Getting the whole text message which shows after creating a service request
					String successMessage = driver.findElement(By.xpath("//div[@id='myAcctContentPanel']/table/tbody/tr/td[2]")).getText();//selenium.getText("//div[@id='myAcctContentPanel']/table/tbody/tr/td[2]");
					String serviceRequestNumber = successMessage.substring(45,58);
					//Entering the service request to putputMap
					outputMap.put("ServiceRequestNumber", serviceRequestNumber);
					
					if(driver.getCurrentUrl().toLowerCase().contains("create_service_request_success.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The create service request success page has successfully loaded. " +
								"Service Request Number{"+serviceRequestNumber+"}");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The create service request success page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}

				}
				catch(Exception e)
				{
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				return outputMap;
			}

			
			/**
			 * Method to edit a credit card profile
			 * @author sranjan
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @return Output Map with the value for the following keys ("creditCardNumber"), ("expiryMonth"), ("expiryYear"), ("CVV")
			 */	
			
		    public HashMap<String,Object> editBillingProfile(WebDriver driver, String maxPageLoadInMs, HashMap<String,Object> outputMap) 
		    {
		    	try
				{
					TestRun.startTest("Edit billing profile (CC) of the user");
		    	    
					WebElement element;
					WebElement dropDownListElement;
					Select dropDownListElementSelect;
					
					//Click on the edit link of 1st credit card profile
					driver.findElement(By.xpath("//div[@id='myAcctContentCenter']/div[2]/table/tbody/tr[2]/td/div[2]/table/tbody/tr/td[3]/a/img")).click();//selenium.click("//div[@id='myAcctContentCenter']/div[2]/table/tbody/tr[2]/td/div[2]/table/tbody/tr/td[3]/a/img");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
		    	   
					driver.switchTo().frame(driver.findElement(By.className("updatePaymentForm")));
					 element = driver.findElement(By.id("creditCardNumber"));element.clear();element.sendKeys("123");
					
					//To handle warning which says profile is associated with any plan
					if(driver.getCurrentUrl().toLowerCase().contains("edit_payment_information_display.jsp"))
					{
						driver.findElement(By.id("buttonEditPaymentFormCc")).click();//selenium.click("id=buttonEditPaymentFormCc");
		    		   //selenium.waitForPageToLoad(maxPageLoadInMs);
					}
		    	   
					
					
		    	   if(driver.getCurrentUrl().toLowerCase().contains("cc_information_edit.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The update creditcard page has successfully loaded. "); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The update creditcard page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}

		    	   
		    	   String cardType="";
		    	   //Setting card type from the image shown at top    	   
		    	   //if(selenium.isElementPresent("//img[@src='/commerce/common/images/cctype/card_mc.gif']"))
		    	   if(isElementPresent(driver, "xpath", "//img[@src='/commerce/common/images/cctype/card_mc.gif']"))//("//img[@src='/commerce/common/images/cctype/card_mc.gif']"))
		    	   {
		    		   cardType="mastercard"; 
		    	   }
		    	   else if(isElementPresent(driver, "xpath", "//img[@src='/commerce/common/images/cctype/card_visa.gif']"))//else if(selenium.isElementPresent("//img[@src='/commerce/common/images/cctype/card_visa.gif']"))
		    	   {
		    		   cardType="visa"; 
		    	   }
		    	   //else if(selenium.isElementPresent("//img[@src='/commerce/common/images/cctype/card_amex.gif']"))
		    	   else if(isElementPresent(driver, "xpath", "//img[@src='/commerce/common/images/cctype/card_amex.gif']"))
		    	   {
		    		   cardType="amex"; 
		    	   }
		    	   //else if(selenium.isElementPresent("//src='/commerce/common/images/cctype/card_discover.gif']"))
		    	   else if(isElementPresent(driver, "xpath", "//src='/commerce/common/images/cctype/card_discover.gif']"))
		    	   {
		    		   cardType="discover"; 
		    	   }
		    	                                                                 
		    	   //Getting the new card number
		    	   String existingCard = driver.findElement(By.id("creditCardNumber")).getAttribute("value");//selenium.getValue("id=creditCardNumber");
		    	   String newCard = alternateCardNumber(cardType, existingCard);
		    	   //Setting the new card number
		    	   element = driver.findElement(By.id("creditCardNumber"));element.clear();
		    	   element = driver.findElement(By.id("creditCardNumber"));element.sendKeys(newCard);//selenium.type("id=creditCardNumber", newCard);
		    	   //selenium.focus("id=creditCardNumber");
				   //The native key press (TAB) is necessary in order to identify the correct credit card type
		    	   element.sendKeys(Keys.TAB);//selenium.keyPressNative("9");	
		    
		    	   //Getting Month
		    	   String existingExpireMonth = driver.findElement(By.id("expirationMonth")).getAttribute("value");//selenium.getSelectedIndex("id=expirationMonth");
		    	   //Parsing to integer
		    	   int monthInt = Integer.parseInt(existingExpireMonth);
		    	   if(monthInt==12)                                                                     //Increment of 1 month; if its Dec then make it to Jan
		    	   {
		    		  monthInt=1;
		    	   }
		    	   else
		    	   {
		    		   monthInt++;
		    	   }
		    	   //Setting the new month
		    	   //selenium.select("id=expirationMonth", "index="+(monthInt));
		    	   dropDownListElement = driver.findElement(By.id("expirationMonth"));
		    	   dropDownListElementSelect = new Select(dropDownListElement);
		    	   dropDownListElementSelect.selectByIndex(monthInt);
		    	     	   
		    	   //Getting the year
		    	   //String existingExpireYear = selenium.getSelectedValue("id=expirationYear");
		    	   String existingExpireYear = driver.findElement(By.id("expirationYear")).getAttribute("value");
		    	   
		    	   int yearInt = Integer.parseInt(existingExpireYear);
		    	   if(yearInt==2020)
		    	   {
		    		   yearInt--;
		    	   }
		    	   else
		    	   {
		    		   yearInt++;
		    	   }
		    	   //Setting the new year
		    	   //selenium.select("id=expirationYear", "label=regexp:"+yearInt);
		    	   dropDownListElement = driver.findElement(By.id("expirationYear"));
		    	   dropDownListElementSelect = new Select(dropDownListElement);
		    	   dropDownListElementSelect.selectByValue(new Integer(yearInt).toString());
		    	      	   
		    	   //Setting cvv
		    	   String newCvv = "";
				   if(cardType.equals("amex"))
				   {
					   newCvv = getRandomValueInLimit(1000,9999);
					   element = driver.findElement(By.id("securityCode"));element.sendKeys(newCvv);//selenium.type("id=securityCode", newCvv);
				   }
				   else
				   {
					  newCvv = getRandomValueInLimit(100,410);
					  element = driver.findElement(By.id("securityCode"));element.sendKeys(newCvv);//selenium.type("id=securityCode", newCvv);
				   }
				   
				   outputMap.put("CardType", cardType);
		    	   outputMap.put("CreditCardNumber", newCard);
		    	   outputMap.put("ExpiryMonth", monthInt);
		    	   outputMap.put("ExpiryYear", yearInt);
		    	   outputMap.put("CVV", newCvv);
		    	 	   
		    	   //Thread.sleep(10000);
		    	   driver.findElement(By.id("buttonContinue")).click();//selenium.click("id=buttonContinue");
		    	   //selenium.waitForPageToLoad(maxPageLoadInMs);
		    	   
		    	   if(driver.getCurrentUrl().toLowerCase().contains("cc_information_edit_success.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The update creditcard success page has successfully loaded. " +
								"Card has been updated with these details. Credit card No - {"+newCard+"} , Expiry Month - {"+monthInt+"} , Expiry Year - {"+yearInt+"}, CVV -{"+newCvv+"}"); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The update creditcard success page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
		    	   
		  	 
		    	  
				}
				catch(Exception e)
				{
					System.out.println(e);
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				return outputMap;
		    }
		    
		 

			/**
			 * Its a helper method. This will provide alternate card number
			 * @author sranjan
			 * Card Family:(VISA - 4012888888881881/4444584332100774),(MC - 5405107001894040/5405107001800278), (Amex - 378282246310005/371449635398431)
			 * @param cardType Card Type such as VISA, MC, Amex
			 * @param cardNumber existing card number
			 * @return newCardNumber New card Number
			 */
		    public String alternateCardNumber(String cardType, String cardNumber)
		    {
		    	String newCardNumber="";
		    	
		    	switch(CardTypes.fromString(cardType))
				{
		    	case visa:
		    		if(cardNumber.endsWith("1881"))
		    		{
		    			newCardNumber = "4444584332100774";
		    		}
		    		else
		    		{
		    			newCardNumber = "4012888888881881";
		    		}
		    		break;
		    	case mastercard:
		    		if(cardNumber.endsWith("4040"))
		    		{
		    			newCardNumber = "5405107001800278";
		    		}
		    		else
		    		{
		    			newCardNumber = "5405107001894040";
		    		}
		    		break;
		    	case amex:
		    		if(cardNumber.endsWith("0005"))
		    		{
		    			newCardNumber = "371449635398431";
		    		}
		    		else
		    		{
		    			newCardNumber = "378282246310005";
		    		}
		    		break;
		    	
		    	case discover:
		    		if(cardNumber.endsWith("9424"))
		    		{
		    			newCardNumber = "6011002397105248";
		    		}
		    		else
		    		{
		    			newCardNumber = "6011000990139424";
		    		}
		    		break;
				}
		    	
		    	return newCardNumber;
		    }
		  
		    public enum CardTypes {visa,mastercard,amex,discover,novalue;	
		    
		   
			public static CardTypes fromString(String Str)
			{
				try 
				{
					return valueOf(Str);
				} 
				catch (Exception ex)
				{
					return novalue;}
				}
		    };
			 

			/**
			 * Method returns a random value within a limit range
			 * @author sranjan
			 * @param min Minimum number to start with
			 * @param max Maximum number to end with
			 * @return randomNumber Generated random number
			 */

			public String getRandomValueInLimit(int min, int max)
			    {
			    	
			    	Random random = new Random();
			    	int randomNumber = random.nextInt(max - min) + min;
			    	return Integer.toString(randomNumber);
			    }
			    
			/**
			 * 	Method to editAccount with information supplied
			 * @author sranjan
			 * @param phoneNumber Phone number of the user
			 * @param extension Extension of the user
			 * @param address1 Address1 of the user
			 * @param address2 Address2 of the user
			 * @param city City of the user
			 * @param state State of the user
			 * @param country Country of the user
			 * @param zip Zip of the user
			 * @param eMail Email of the user
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
		     * @param outputMap Map that will be populated with important information
			 * @return Output Map with the value for the following keys ("MyAccountPhoneNumber"), ("MyAccountExtension"), ("MyAccountAddress1"), ("MyAccountAddress2"), ("MyAccountCity"), ("MyAccountState"), ("MyAccountCountry"), ("MyAccountZip"), ("MyAccountE-Mail")
			 */
			public HashMap<String,Object> editAccount(WebDriver driver, String phoneNumber,String extension, String address1, String address2, String city, String state, String country, String zip, String eMail, String maxPageLoadInMs, HashMap<String,Object> outputMap)
			{	
				try
				{
					TestRun.startTest("Edit account information of the user");
		    	    
					WebElement element;
					WebElement dropDownListElement;
					Select dropDownListElementSelect;
					
					//Click on the edit link
					driver.findElement(By.id("editAccountInfo")).click();//selenium.click("id=editAccountInfo");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
		    	   
					if(driver.getCurrentUrl().toLowerCase().contains("account_information_edit.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The account edit page has successfully loaded. "); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The account edit page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}

		    	   
		    	    if(!phoneNumber.equals(""))
		    	    {
		    	    	element = driver.findElement(By.id("textPhoneNumber"));element.clear();element.sendKeys(phoneNumber);//selenium.type("id=textPhoneNumber", phoneNumber);
		    	    }	
		    	        	   
		    	    if(!extension.equals(""))
		    	    {
		    	    	element = driver.findElement(By.id("phoneNumberExt"));element.clear();element.sendKeys(extension);//selenium.type("id=phoneNumberExt", extension);
		    	    }
		    	    
		    	    if(!address1.equals(""))
		    	    {
		    	    	element = driver.findElement(By.id("textStreetAddress"));element.clear();element.sendKeys(address1);//selenium.type("id=textStreetAddress", address1);
		    	    }
		    	    
		    	    if(!address2.equals(""))
		    	    {
		    	    	element = driver.findElement(By.id("textStreetAddress2"));element.clear();element.sendKeys(address2);//selenium.type("id=textStreetAddress2", address2);
		    	    }
		    	    
		    	    if(!city.equals(""))
		    	    {
		    	    	element = driver.findElement(By.id("textCity"));element.clear();element.sendKeys(city);//selenium.type("id=textCity", city);
		    	    }
		    	    
		    	    if(!state.equals(""))
		    	    {
		    	    	//selenium.select("id=stateSelect", "label="+state);
		    	    	dropDownListElement = driver.findElement(By.id("stateSelect"));
		    	    	dropDownListElementSelect = new Select(dropDownListElement);
		    	    	dropDownListElementSelect.selectByVisibleText(state);
		    	    }
		    	    
		    	    if(!country.equals(""))
		    	    {
		    	    	//selenium.select("id=countrySelect", "label="+country);
		    	    	dropDownListElement = driver.findElement(By.id("countrySelect"));
		    	    	dropDownListElementSelect = new Select(dropDownListElement);
		    	    	dropDownListElementSelect.selectByVisibleText(country);
		    	    }
		    	    
		    	    if(!zip.equals(""))
		    	    {
		    	    	element = driver.findElement(By.id("textPostalCode"));element.clear();element.sendKeys(zip);//selenium.type("id=textPostalCode", zip);
		    	    }
		    	    
		    	    if(!eMail.equals(""))
		    	    {
		    	    	element = driver.findElement(By.id("textCustomerEmailAddress"));element.clear();element.sendKeys(eMail);//selenium.type("id=textCustomerEmailAddress", eMail);
		    	    }
		    	    
		    	    
		    	    if(isElementPresent(driver, "id", "submitUpdateAccount"))
					{
		    	    	driver.findElement(By.id("submitUpdateAccount")).click();//selenium.click("id=submitUpdateAccount");
					}
		    	   
		    	    outputMap.put("MyAccountPhoneNumber", phoneNumber);
		    	    outputMap.put("MyAccountExtension", extension);
		    	    outputMap.put("MyAccountAddress1", address1);
		    	    outputMap.put("MyAccountAddress2", address2);
		    	    outputMap.put("MyAccountCity", city);
		    	    outputMap.put("MyAccountState", state);
		    	    outputMap.put("MyAccountCountry", country);
		    	    outputMap.put("MyAccountZip",zip);
		    	    outputMap.put("MyAccountE-Mail", eMail);
		    	      
		    	    //selenium.waitForPageToLoad(maxPageLoadInMs);
		    	    
		    	    
		    	    if(driver.getCurrentUrl().toLowerCase().contains("account_information_edit_success.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The account update success page has successfully loaded." +
								"Account has been updated with these information. " +
								"Phone Number {"+phoneNumber+"}, Extension {"+extension+"}, Address1{"+address1+"}, Address2{"+address2+"}, City{"+city+"}, State{"+state+"}, " +
								"Country{"+country+"}, Zip{"+zip+"}, E-Mail{"+eMail+"}"); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The account update success page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
		  	 
		    	  
				}
				catch(Exception e)
				{
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				return outputMap;
			}

			
			/**
			 * Method to modify a product with no. of user increment
			 * @author sranjan
			 * @param product Product which will be modified
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param Map that will be populated with important information
			 * @return Output Map with the value for the following keys ("No_of_User")
			 */
			public HashMap<String,Object> ModifyProduct(WebDriver driver, String product, String maxPageLoadInMs, HashMap<String,Object> outputMap)
			{	
				try
				{
					TestRun.startTest("Modify a product purchased by the user");
		    	    
					
					//Click on the product link
					driver.findElement(By.linkText("Products")).click();//selenium.click("link=Products");
					//selenium.waitForPageToLoad(maxPageLoadInMs);
		    	   
					if(driver.getCurrentUrl().toLowerCase().contains("products.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The product page has successfully loaded. "); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The product page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}

		    	   ArrayList<String> list = new ArrayList<String>();
		    	   int i=3;
		    	   do{
						
						list.add("//div[@id='myAcctContentPanel']/div["+i+"]/strong");
						i+=3;
						
					}
		    	   while(isElementPresent(driver, "xpath", "//div[@id='myAcctContentPanel']/div["+i+"]/strong"));
					//while(selenium.isElementPresent("//div[@id='myAcctContentPanel']/div["+i+"]/strong"));
		    	   
		    	   for(int j=0;j<list.size();j++)
	    	   		{
						
						String _element = list.get(j);
										
						//if(selenium.getText(_element).equals(product))
						if(driver.findElement(By.xpath(_element)).getText().equals(product))
						{
							String tmpLink = _element.replace("strong","div[3]/a[2]");
							//if(selenium.getText(tmpLink).toLowerCase().equals("update"))
							if(driver.findElement(By.xpath(tmpLink)).getText().toLowerCase().equals("update"))
							{
								driver.findElement(By.xpath(tmpLink)).click();//selenium.click(tmpLink);
							}
							else
							{
								tmpLink = _element.replace("strong","div[3]/a");
								driver.findElement(By.xpath(tmpLink)).click();//selenium.click(tmpLink);
							}
							
							break;
						}
					}
		    	   //selenium.waitForPageToLoad(maxPageLoadInMs);		
				 if(driver.getCurrentUrl().toLowerCase().contains("update_plan.jsp"))
					{
							TestRun.updateStatus(TestResultStatus.PASS,"The update plan page has successfully loaded."); 
					}
					else
					{
							TestRun.updateStatus(TestResultStatus.FAIL,"The update plan page did not succesfully load.");
							ArrayList<String> listOferrors = captureErrorMessage(driver);
							for(int j=0;j<listOferrors.size();j++)
							{
								sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
							
							}
							sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
					Thread.sleep(10000);	
					 //selenium.getSelectedLabel("id=S0_3");
									
					Select select = new Select(driver.findElement(By.id("S0_3")));
					WebElement existingUserLimit=select.getFirstSelectedOption();
					
					System.out.println(existingUserLimit.getText());
			  	   //Parsing to integer
					int userLimit = Integer.parseInt(existingUserLimit.getText());
					System.out.println(userLimit);
			  	   	if(userLimit==20)                                                                     
			  	   		{
			  	   		TestRun.updateStatus(TestResultStatus.FAIL,"User update has reached max. limit. Try with different user and item");
			  		 
			  	   		}
			  	
			  	   else
			  	   		{
			  		   outputMap.put("No_of_User", userLimit);
			  		 
			  		 if(isElementPresent(driver, "id", "updateBtn"))  
			  		 {
			  			 driver.findElement(By.id("updateBtn")).click();//selenium.click("id=updateBtn");
			  		 {
			  	 
			  		 //selenium.waitForPageToLoad(maxPageLoadInMs);		
			  		 if(driver.getCurrentUrl().toLowerCase().contains("change_plan_confirmation.jsp"))
			  		 	{
							TestRun.updateStatus(TestResultStatus.PASS,"The change plan confimation page has successfully loaded. "); 
			  		 	}
			  		 else
			  		 	{
							TestRun.updateStatus(TestResultStatus.FAIL,"The change plan confimation page did not succesfully load.");
							ArrayList<String> listOferrors = captureErrorMessage(driver);
							for(int j=0;j<listOferrors.size();j++)
							{
								sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
							
							}
							sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
			  		 	}
			  		
			  		  if(isElementPresent(driver, "id", "changePlanFormContinue"))  
			  		  {
			  			 driver.findElement(By.id("changePlanFormContinue")).click();//selenium.click("id=changePlanFormContinue");
			  		  }
			  		 
			  		 //selenium.waitForPageToLoad(maxPageLoadInMs);		
			  		 if(driver.getCurrentUrl().toLowerCase().contains("products.jsp?success=updateprd"))
			  		 	{
							TestRun.updateStatus(TestResultStatus.PASS,"The product update success page has successfully loaded. Product has been updated with {"+userLimit+"} users"); 
			  		 	}
					else
						{
							TestRun.updateStatus(TestResultStatus.FAIL,"The product update success page did not succesfully load.");
							ArrayList<String> listOferrors = captureErrorMessage(driver);
							for(int j=0;j<listOferrors.size();j++)
							{
								sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
							
							}
							sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
						}
		    	  
			  		 }
			  	 }
		 	   }
			}
				catch(Exception e)
				{
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int j=0;j<listOferrors.size();j++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				return outputMap;
			}

	
			/**
			 * This method update a service in eStore - My Account - Subscription. Works for only payroll basics.
			 * @author sranjan
			 * @param selenium Default Selenium 
			 * @param service The service\subscription to be updated.
			 * @param param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param outputMap Map that will be populated with important information 
			 * @return Output Map with the value for the following keys ("NewUpdatedOffering")
			 */
			public HashMap<String,Object> ModifySubscription(WebDriver driver, String service, String maxPageLoadInMs, HashMap<String,Object> outputMap)
			{
				
				try
				{
					TestRun.startTest("Modify a subscription purchased by the user");
				    //Click on the product link
					driver.findElement(By.linkText("Services & Subscriptions")).click();//selenium.click("link=Services & Subscriptions");
				   //selenium.waitForPageToLoad(maxPageLoadInMs);
				   
				   if(driver.getCurrentUrl().toLowerCase().contains("services_and_subscriptions.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The services_and_subscriptions page has successfully loaded. "); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The services_and_subscriptions page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int i=0;i<listOferrors.size();i++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
						
						}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
		
				   ArrayList<String> list = new ArrayList<String>();
				   int i=3;
				   do{
						
						list.add("//div[@id='myAcctContentPanel']/div["+i+"]/div/strong/a");
						i+=3;
						
					}
				   while(isElementPresent(driver, "xpath", "//div[@id='myAcctContentPanel']/div["+i+"]/div/strong/a"));
					//while(selenium.isElementPresent("//div[@id='myAcctContentPanel']/div["+i+"]/div/strong/a"));
				   
				   
				   
				   
				   for(int j=0;j<list.size();j++)
					{
						
						String element = list.get(j);
										
						//if(selenium.getText(element).equals(service))
						if(driver.findElement(By.xpath(element)).getText().equals(service))
						{
							String tmpLink = element.replace("/div/strong/a","/div[5]/a");
							//if(selenium.getText(tmpLink).toLowerCase().equals("update"))
							if(driver.findElement(By.xpath(tmpLink)).getText().toLowerCase().equals("update"))
							{
								driver.findElement(By.xpath(tmpLink)).click();//selenium.click(tmpLink);
							}
							else
							{
								tmpLink = element.replace("strong","div[5]/a[2]");
								driver.findElement(By.xpath(tmpLink)).click();//selenium.click(tmpLink);
							}
							
							break;
						}
					}
					   //selenium.waitForPageToLoad(maxPageLoadInMs);		
					 if(driver.getCurrentUrl().toLowerCase().contains("update_plan.jsp"))
						{
								TestRun.updateStatus(TestResultStatus.PASS,"The update plan page has successfully loaded."); 
						}
						else
						{
								TestRun.updateStatus(TestResultStatus.FAIL,"The update plan page did not succesfully load.");
								ArrayList<String> listOferrors = captureErrorMessage(driver);
								for(int j=0;j<listOferrors.size();j++)
								{
									sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
								
								}
								sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
						}
						Thread.sleep(9000);	
						
						driver.findElement(By.xpath("//input[@id='skuAttributesPrefix' and @value='S0']")).click();//selenium.click("//input[@id='skuAttributesPrefix' and @value='S0']");
						//String updatedOffering = selenium.getText("//form[@id='changePlanFrm']/div[12]/div/table/tbody/tr/td[3]/table/tbody/tr/td[2]/strong");
						String updatedOffering = driver.findElement(By.xpath("//form[@id='changePlanFrm']/div[12]/div/table/tbody/tr/td[3]/table/tbody/tr/td[2]/strong")).getText();
						outputMap.put("NewUpdatedOffering", updatedOffering);
						driver.findElement(By.id("updateBtn")).click();//selenium.click("id=updateBtn");
							
						//selenium.waitForPageToLoad(maxPageLoadInMs);		
						if(driver.getCurrentUrl().toLowerCase().contains("change_plan_confirmation.jsp"))
						 	{
							TestRun.updateStatus(TestResultStatus.PASS,"The change plan confimation page has successfully loaded."); 
						 	}
						 else
						 	{
							TestRun.updateStatus(TestResultStatus.FAIL,"The change plan confimation page did not succesfully load.");
							ArrayList<String> listOferrors = captureErrorMessage(driver);
							for(int j=0;j<listOferrors.size();j++)
							{
								sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
							
							}
							sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
						 	}
				 
						driver.findElement(By.id("changePlanFormContinue")).click();//selenium.click("id=changePlanFormContinue");
						// selenium.waitForPageToLoad(maxPageLoadInMs);		
						 if(driver.getCurrentUrl().toLowerCase().contains("services_and_subscriptions.jsp?success"))
						 	{
							TestRun.updateStatus(TestResultStatus.PASS,"The services_and_subscriptions update success page has successfully loaded.New updated offering is {"+updatedOffering+"}"); 
						 	}
					else
						{
							TestRun.updateStatus(TestResultStatus.FAIL,"The services_and_subscriptions update success page did not succesfully load.");
							ArrayList<String> listOferrors = captureErrorMessage(driver);
							for(int j=0;j<listOferrors.size();j++)
							{
								sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
							
							}
							sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
						}
				  
					   
				}
				catch(Exception e)
				{
					
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int j=0;j<listOferrors.size();j++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				return outputMap;
		}

			
			
			/**
			 * Update EFT with existing EFT Info
			 * @author sranjan
			 * @param selenium Default Selenium 
			 * @param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param outputMap Map that will be populated with important information
			 * @return Output Map with the value for the following key ("EFTRoutingNumber"),("EFTAccountNumber")
			 */
			 public HashMap<String,Object> editEFTBillingProfile(WebDriver driver, String maxPageLoadInMs, HashMap<String,Object> outputMap) 
			 {
				 try
				 {
					TestRun.startTest("Edit EFT profile of the user");
						
					WebElement element;
						
		    	    //Click on the edit link of 1st credit card profile
					driver.findElement(By.xpath("//div[@id='myAcctContentCenter']/div[2]/table[2]/tbody/tr[2]/td/div[2]/table/tbody/tr/td[3]/a/img")).click();//selenium.click("//div[@id='myAcctContentCenter']/div[2]/table[2]/tbody/tr[2]/td/div[2]/table/tbody/tr/td[3]/a/img");
					// selenium.waitForPageToLoad(maxPageLoadInMs);
			    	    
					if(driver.getCurrentUrl().toLowerCase().contains("bank_information_edit.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The update EFT page has successfully loaded. "); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The update EFT page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int j=0;j<listOferrors.size();j++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					}
			    	   
					// String bankRoutingNumber = selenium.getValue("id=eftRoutingNumber");
		    	    String bankRoutingNumber = driver.findElement(By.id("eftRoutingNumber")).getText();//getAttribute("value");//getValue("id=eftRoutingNumber");
			    	   
		    	    if(bankRoutingNumber.equals("063210112"))
		    	    {
		    	    	element = driver.findElement(By.id("eftRoutingNumber"));element.clear();element.sendKeys("011302920");//selenium.type("id=eftRoutingNumber", "011302920");
		    	    	element = driver.findElement(By.id("eftAccountNumber"));element.clear();element.sendKeys("87654");//selenium.type("id=eftAccountNumber", "87654");
		    	    	element = driver.findElement(By.id("eftConfirmAccountNumber"));element.clear();element.sendKeys("87654");//selenium.type("id=eftConfirmAccountNumber", "87654");
		    	    	outputMap.put("EFTRoutingNumber", "011302920");
		    	    	outputMap.put("EFTAccountNumber", "87654");
		    	    		    	    	
		    	    }
		    	    else
		    	    {
		    	    	element = driver.findElement(By.id("eftRoutingNumber"));element.clear();element.sendKeys("063210112");//selenium.type("id=eftRoutingNumber", "063210112");
		    	    	element = driver.findElement(By.id("eftAccountNumber"));element.clear();element.sendKeys("81686868686891");//selenium.type("id=eftAccountNumber", "81686868686891");
		    	    	element = driver.findElement(By.id("eftConfirmAccountNumber"));element.clear();element.sendKeys("81686868686891");//selenium.type("id=eftConfirmAccountNumber", "81686868686891");
		    	    	outputMap.put("EFTRoutingNumber", "063210112");
		    	    	outputMap.put("EFTAccountNumber", "81686868686891");
		    	    }
		    	    
		    	    if(isElementPresent(driver, "id", "buttonContinue"))
			    	   {
			    		   driver.findElement(By.id("buttonContinue")).click();
			    	   }
		    	    //selenium.click("id=buttonContinue");
		    	    //selenium.waitForPageToLoad(maxPageLoadInMs);
			    	   
		    	    if(driver.getCurrentUrl().toLowerCase().contains("bank_information_edit_success.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The update EFT success page has successfully loaded."); 
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The update EFT success page did not succesfully load.");
						ArrayList<String> listOferrors = captureErrorMessage(driver);
						for(int j=0;j<listOferrors.size();j++)
						{
							sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
						
						}
						sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					} 
				}			
				catch(Exception e)
				{
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int j=0;j<listOferrors.size();j++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				}
				finally
				{
					TestRun.endTest();
				}
				return outputMap;
		    }
			 
			 

			 /**
			  * Update EFT with user supplied info
			  * @author sranjan
			  * @param selenium Default Selenium 
			  * @param accountName account name for the EFT
			  * @param accountType account type for the EFT
			  * @param routingNumber routing number for the EFT
			  * @param accountNumber account number for the EFT
			  * @param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			  * @param outputMap Map that will be populated with important information
			  * @return  Output Map with the value for the following key ("AccountName"),("AccountType"),("RoutingNumber"),("AccountNumber")
			  */
	 public HashMap<String,Object> editEFTBillingProfileWithUserInfo(WebDriver driver, String accountName, String accountType, String routingNumber, String accountNumber, String maxPageLoadInMs, HashMap<String,Object> outputMap) 
	    {
	    	try
			{
				TestRun.startTest("Edit EFT profile of the user");
				WebElement element;
				
	    	    //Click on the edit link of 1st credit card profile
				driver.findElement(By.xpath("//div[@id='myAcctContentCenter']/div[2]/table[2]/tbody/tr[2]/td/div[2]/table/tbody/tr/td[3]/a/img")).click();
	    	   //selenium.click("//div[@id='myAcctContentCenter']/div[2]/table[2]/tbody/tr[2]/td/div[2]/table/tbody/tr/td[3]/a/img");
	    	   //selenium.waitForPageToLoad(maxPageLoadInMs);
	    	    	     	   
	    	   
				if(driver.getCurrentUrl().toLowerCase().contains("bank_information_edit.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The update EFT page has successfully loaded. "); 
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The update EFT page did not succesfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int j=0;j<listOferrors.size();j++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
//	    	   if(selenium.getLocation().toLowerCase().contains("bank_information_edit.jsp"))
//				{
//					TestRun.updateStatus(TestResultStatus.PASS,"The update EFT page has successfully loaded. "); 
//				}
//				else
//				{
//					TestRun.updateStatus(TestResultStatus.FAIL,"The update EFT page did not succesfully load.");
//					ArrayList<String> listOferrors = captureErrorMessage(driver);
//					for(int j=0;j<listOferrors.size();j++)
//					{
//						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
//					
//					}
//					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
//				}
	    	   
	    	   
	    	   if(!accountName.equals(""))
	    	    {

	    		   element = driver.findElement(By.id("eftAccountName"));
	    		   element.clear();element.sendKeys(accountName);
	    		   outputMap.put("AccountName",element.getText());
//	    	    	selenium.type("id=eftAccountName", accountName);
//	    	    	outputMap.put("AccountName", accountName);
	    	    }
	    	   
	    	   if(!accountType.equals(""))
	    	    {
	    	    	Select select = new Select(driver.findElement(By.id("eftAccountType")));
	    	    	select.selectByVisibleText(accountType);
	    	    	outputMap.put("AccountType",select.getFirstSelectedOption());
//	    		   selenium.select("id=eftAccountType", "label="+accountType);
//	    	    	outputMap.put("AccountType", accountType);
	    	    }
	    	   
	    	   if(!routingNumber.equals(""))
	    	    {
	    		   element = driver.findElement(By.id("eftRoutingNumber"));
	    		   element.clear();element.sendKeys(routingNumber);
	    		   outputMap.put("RoutingNumber",element.getText());
	    		   
//	    		   selenium.type("id=eftRoutingNumber", routingNumber);
//	    	    	outputMap.put("RoutingNumber", routingNumber);
	    	    }
	    	   
	    	   if(!accountNumber.equals(""))
	    	    {
	    		   element = driver.findElement(By.id("eftAccountNumber"));
	    		   element.clear();element.sendKeys(accountNumber);
	    		   element = driver.findElement(By.id("eftConfirmAccountNumber"));
	    		   element.clear();element.sendKeys(accountNumber);
	    		   outputMap.put("AccountNumber",element.getText());
//	    		   selenium.type("id=eftAccountNumber", accountNumber);
//	    	    	selenium.type("id=eftConfirmAccountNumber", accountNumber);
//	    	    	outputMap.put("AccountNumber", accountNumber);
	    	    }
	    	   
	    	   if(isElementPresent(driver, "id", "buttonContinue"))
	    	   {
	    		   driver.findElement(By.id("buttonContinue")).click();
	    	   }
//	    	    selenium.click("id=buttonContinue");
//	    	    selenium.waitForPageToLoad(maxPageLoadInMs);
	    	   
	    	   if(driver.getCurrentUrl().toLowerCase().contains("bank_information_edit_success.jsp"))
	    	   //if(selenium.getLocation().toLowerCase().contains("bank_information_edit_success.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The update EFT success page has successfully loaded. EFT is populated with " +
							"AccountName{"+accountName+"}, AccountType{"+accountType+"}, RoutingNumber{"+routingNumber+"}, AccountNumber{"+accountNumber+"}"); 
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The update EFT success page did not succesfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int j=0;j<listOferrors.size();j++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
					//sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
				}
	    	   
			}
	    	  			
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int j=0;j<listOferrors.size();j++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(j));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				//sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(selenium, selenium.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
				
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
	    }
		

	 /**
	  * Add new company to account
	  * @author sranjan 
	  * @param selenium Default Selenium 
	  * @param eMail email of the new company
	  * @param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	  * @param outputMap Map that will be populated with important information
	  * @return  Output Map with the value for the following key ("AccountName")
	  */
	 public HashMap<String,Object> addNewCompany(WebDriver driver, String eMail, String maxPageLoadInMs, HashMap<String,Object> outputMap) 
	    {
	    	try
			{
				TestRun.startTest("Add new company to account");
	    	    
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				//Click on the Add New link
				driver.findElement(By.linkText("Add New")).click();//selenium.click("link=Add New");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
	    	    	     	   
	    	   
	    	   if(driver.getCurrentUrl().toLowerCase().contains("account.jsp"))
	    	   {
					TestRun.updateStatus(TestResultStatus.PASS,"The account add page has successfully loaded. "); 
	    	   }
	    	   else
	    	   {
					TestRun.updateStatus(TestResultStatus.FAIL,"The account add page did not succesfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
	    	   
	    	    int randomNumber = new Double(Math.random()*10000000).intValue();
				HashMap<String,String> addressMap = buildAddress(randomNumber);
				String accountName = "E2EAutomated" + randomNumber;
				outputMap.put("AccountName", accountName);
				sLog.info("Account Name: " + accountName);
				
				element = driver.findElement(By.id("accountName"));element.sendKeys(accountName);//selenium.type("id=accountName", accountName);
				element = driver.findElement(By.id("streetAddress"));element.sendKeys(addressMap.get("streetAddress"));//selenium.type("id=streetAddress",addressMap.get("streetAddress"));
				element = driver.findElement(By.id("city"));element.sendKeys(addressMap.get("city"));//selenium.type("id=city",addressMap.get("city"));
				
				//selenium.select("id=stateSelect", "label="+addressMap.get("state"));
				dropDownListElement = driver.findElement(By.id("stateSelect"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(addressMap.get("state"));
				
				element = driver.findElement(By.id("postalCode"));element.sendKeys(addressMap.get("postalCode"));//selenium.type("id=postalCode",addressMap.get("postalCode"));
				element = driver.findElement(By.id("phoneNumber"));element.sendKeys(addressMap.get("phoneNumber"));//selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
				element = driver.findElement(By.id("email"));element.sendKeys(eMail);//selenium.type("id=email", eMail);
				driver.findElement(By.id("buttonMatchCreateCustomerAccount")).click();//selenium.click("id=buttonMatchCreateCustomerAccount");   	   
	    	    //selenium.waitForPageToLoad(maxPageLoadInMs);
	    	    
				//if(selenium.isElementPresent("id=buttonMatchCreateCustomerAccount"))
				if(isElementPresent(driver, "id", "buttonMatchCreateCustomerAccount"))
	    	    {
					driver.findElement(By.id("buttonMatchCreateCustomerAccount")).click();//selenium.click("id=buttonMatchCreateCustomerAccount");   	   
		    	    //selenium.waitForPageToLoad(maxPageLoadInMs);
	    	    }
	    	    
				if(driver.getCurrentUrl().toLowerCase().contains("account.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The add new account has successfully completed with New Account {"+accountName+"}"); 
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The dd new account has not successfully completed.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));	
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}	
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
	    }
	 
	 
	 
	 
	 /**
	  * Add new company with user preferences
	  * @author sranjan
	  * @param selenium Default Selenium
	  * @param accountName account name of the company
	  * @param streetAddress Street Address1 of the company
	  * @param city city of the company
	  * @param state state of the company
	  * @param zip zipcode of the company
	  * @param phoneNumber phone number of the company
	  * @param maxPageLoadInMs maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	  * @param outputMap Map that will be populated with important information
	  * @return  Output Map with the value for the following key ("AccountName"), ("streetAddress"), ("city"), ("state"), ("zip"), ("country")
	  */
	 public HashMap<String,Object> addNewCompanyWithUserDetails(WebDriver driver, String accountName, String streetAddress,
			 String city, String state, String zip, String phoneNumber, String eMail, String maxPageLoadInMs, HashMap<String,Object> outputMap) 
	    {
	    	try
			{
				TestRun.startTest("Add new company to account");
	    	    
				WebElement element;
				WebElement dropDownListElement;
				Select dropDownListElementSelect;
				
				//Click on the Add New link
				driver.findElement(By.linkText("Add New")).click();//selenium.click("link=Add New");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
	    	    	     	   
	    	   
				if(driver.getCurrentUrl().toLowerCase().contains("account.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The account add page has successfully loaded. "); 
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The account add page did not succesfully load.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));	
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
	    	   
	    	    
				element = driver.findElement(By.id("accountName"));element.sendKeys(accountName);//selenium.type("id=accountName", accountName);
				element = driver.findElement(By.id("streetAddress"));element.sendKeys(streetAddress);//selenium.type("id=streetAddress",streetAddress);
				element = driver.findElement(By.id("city"));element.sendKeys(city);//selenium.type("id=city",city);
				//selenium.select("id=stateSelect", "label="+state);
				dropDownListElement = driver.findElement(By.id("stateSelect"));
				dropDownListElementSelect = new Select(dropDownListElement);
				dropDownListElementSelect.selectByVisibleText(state);
				
				element = driver.findElement(By.id("postalCode"));element.sendKeys(zip);//selenium.type("id=postalCode",zip);
				element = driver.findElement(By.id("phoneNumber"));element.sendKeys(phoneNumber);//selenium.type("id=phoneNumber",phoneNumber);
				element = driver.findElement(By.id("email"));element.sendKeys(eMail);//selenium.type("id=email", eMail);
	    	    
	    	    outputMap.put("AccountName", accountName);
	    	    outputMap.put("streetAddress", streetAddress);
				outputMap.put("city", city);
				outputMap.put("state", state);
				outputMap.put("zip", zip);
				outputMap.put("country", "USA");
				
				driver.findElement(By.id("buttonMatchCreateCustomerAccount")).click();//selenium.click("id=buttonMatchCreateCustomerAccount");   	   
	    	    //selenium.waitForPageToLoad(maxPageLoadInMs);
	    	    //if(selenium.isElementPresent("id=buttonMatchCreateCustomerAccount"))
				if(isElementPresent(driver, "id", "buttonMatchCreateCustomerAccount"))
	    	    {
					driver.findElement(By.id("buttonMatchCreateCustomerAccount")).click();//selenium.click("id=buttonMatchCreateCustomerAccount");   	   
		    	    //selenium.waitForPageToLoad(maxPageLoadInMs);
	    	    }
	    	    
	    	   if(driver.getCurrentUrl().toLowerCase().contains("account.jsp"))
	    	   {
					TestRun.updateStatus(TestResultStatus.PASS,"The add new account has successfully completed with New Account {"+accountName+"}"); 
	    	   }
	    	   else
	    	   {
					TestRun.updateStatus(TestResultStatus.FAIL,"The dd new account has not successfully completed.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
	    	   
			}
	    	  			
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
			return outputMap;
	    }
	 
	 
	 /**
	  * Returns the screen shot of the page
	  * @param selenium Default Selenium
	  * @author sranjan
	  * @param pagename pagename of which screenshot will be taken
	  * @return createThisScreenshotAtLocation Location + file name which will be displayed at result page
	  */
	 public String screenshot(WebDriver driver, String pagename)
	 {
		 Date dateNow = new Date ();
		 
		 SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		 String finalpagename = pagename.replaceAll("[^=,\\da-zA-Z\\s]|(?<!,)\\s","");
	     StringBuilder timeStamp = new StringBuilder( dateformat.format( dateNow ) );
	     String location = "./resources/screenshot/estore/";
	     String fileformat = ".png";
	     String createThisScreenshotAtLocation = location+finalpagename+timeStamp+fileformat;
	      
	     Properties properties = new Properties();
	     try 
	     {
			//properties.load(new FileInputStream("project.properties"));
	    	 String fileSeparator = System.getProperty("file.separator");
	    	 properties.load(new FileInputStream("." + fileSeparator + "resources" + fileSeparator + "config" + fileSeparator + "initiate_estore.properties"));
				
	     }	
	     catch (IOException e1) 
	     {
			e1.printStackTrace();
	     }
			
	     if(properties.getProperty("selenium_driver").equalsIgnoreCase("HtmlUnit"))
	     {
	    	 createThisScreenshotAtLocation = "Since the HTMLUnitDriver was used (browserless driver), a screenshot could not be captured.";
	     }
	     else
	     {
	    	 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		     try 
		     {
				FileUtils.copyFile(scrFile, new File(createThisScreenshotAtLocation));
		     } 
		     catch (IOException e) 
		     {
				sLog.info(e.getMessage());
		     }
	     }
	     
	     return createThisScreenshotAtLocation;
	 }
	 
	 //Testing purpose
	 public void validateLoginPage(WebDriver driver, String estoreURL,String maxPageLoadInMs) 
	 {
		 try
		 {
			 	TestRun.startTest("Load Login Page");
				
				//Opens the web page and waits for it to load 
				driver.get(estoreURL+"/commerce/account/secure/login.jsp");//selenium.open(estoreURL+"/commerce/account/secure/login.jsp");
				
				//selenium.waitForPageToLoad(maxPageLoadInMs);
								
				if(driver.getCurrentUrl().toLowerCase().contains("login.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The account login page has successfully loaded.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The account login page did not succesfully load.");
				}
				TestRun.endTest();
				
				
				TestRun.startTest("Generate Login Error");
				
				WebElement element;
				element = driver.findElement(By.id("userid"));element.sendKeys("");//selenium.type("id=userid","");
				element = driver.findElement(By.id("textPassword"));element.sendKeys("");//selenium.type("id=textPassword","");
				driver.findElement(By.id("wcgLoginButton")).click();//selenium.click("id=wcgLoginButton");
				//selenium.waitForPageToLoad(maxPageLoadInMs);
					
				if(driver.getCurrentUrl().toLowerCase().contains("account.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The account page has successfully loaded, but an error was expected.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The account page did not succesfully load, which was expected.");					
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());	
			}
			finally
			{
				TestRun.endTest();
			}
		}
	 
	 
	 
	 
	 public void logout(WebDriver driver,String estoreURL,String maxPageLoadInMs)
		{
			try
			{
				TestRun.startTest("Logout from eStore");
				
				WebElement element;
				
				//Opens the web page and waits for it to load 
				driver.get(estoreURL+"/commerce/account/secure/login.jsp");//selenium.open(estoreURL+"/commerce/account/secure/login.jsp");
				
				//selenium.waitForPageToLoad(maxPageLoadInMs);
								
				if(driver.getCurrentUrl().toLowerCase().contains("account.jsp"))	
				{
					TestRun.updateStatus(TestResultStatus.PASS,"The user is ready to logout.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"The login session has already expired.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
				
				if(isElementPresent(driver, "linkText", "Sign Out"))
				{
					driver.findElement(By.linkText("Sign Out")).click();
				}
				
					
				if(driver.getCurrentUrl().toLowerCase().contains("login.jsp") || driver.getCurrentUrl().toLowerCase().contains("logout_success.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"User logged out successfully.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"user could not logged out successfully.");
					ArrayList<String> listOferrors = captureErrorMessage(driver);
					for(int i=0;i<listOferrors.size();i++)
					{
						sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
					
					}
					sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				}
			}
			catch(Exception e)
			{
				ArrayList<String> listOferrors = captureErrorMessage(driver);
				for(int i=0;i<listOferrors.size();i++)
				{
					sLog.info("\t\t\teStore UI Error -> "+listOferrors.get(i));			
				
				}
				sLog.info("\t\t\tScreenshot where error occurred -> "+screenshot(driver, driver.getTitle()));
				TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			}
			finally
			{
				TestRun.endTest();
			}
		}

		 
	 
	 public ArrayList<String> captureErrorMessage(WebDriver driver)
	 {
		ArrayList<String> errorOutputList = new ArrayList<String>();
		String rawHErrorCodeinHTML = "";

		WebElement element = driver.findElement(By.tagName("html"));
		String pageHtml = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", element);
		
		
		int i = countOccurrences(pageHtml,"class=\"wcgError\"");//int i = countOccurrences(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML"),"class=\"wcgError\"");
//		if(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML").contains("wcgError_920 wcgError"))
		if(pageHtml.contains("wcgError_920 wcgError"))
		{
			//i=i+countOccurrences(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML"),"class=\"wcgError_920 wcgError\"");
			i=i+countOccurrences(pageHtml,"class=\"wcgError_920 wcgError\"");
		}
		
		
		
		for(int j=0;j<i;j++)
		{
			//rawHErrorCodeinHTML=rawHErrorCodeinHTML.concat(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByClassName('wcgError')["+j+"].innerHTML"));
			List<WebElement> errorElementsList = driver.findElements(By.className("wcgError"));
			String error = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", errorElementsList.get(j));
			rawHErrorCodeinHTML=rawHErrorCodeinHTML.concat(error);
			
			//if(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByTagName('html')[0].innerHTML").contains("wcgError_920 wcgError"))
			if(pageHtml.contains("wcgError_920 wcgError"))
			{
				//rawHErrorCodeinHTML=rawHErrorCodeinHTML.concat(selenium.getEval("this.browserbot.getCurrentWindow().document.getElementsByClassName('wcgError_920 wcgError')["+j+"].innerHTML"));
				errorElementsList = driver.findElements(By.xpath("//*[@class='wcgError_920 wcgError']"));//errorElementsList = driver.findElements(By.className("wcgError_920 wcgError"));
				error = (String)((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML;", errorElementsList.get(j));
				rawHErrorCodeinHTML=rawHErrorCodeinHTML.concat(error);
			}
		}
		String[] HtmlListOfString = rawHErrorCodeinHTML.split("\n");
				
		for(int j=0;j<HtmlListOfString.length;j++)
		{
			
			if(HtmlListOfString[j].trim().startsWith("<li>"))
			{
				String errorMessage = HtmlListOfString[j].replaceAll("<li>", "");
				errorMessage = errorMessage.substring(0,errorMessage.indexOf("<")).trim();
				
				if(!errorOutputList.contains(errorMessage.trim()))
				{
					errorOutputList.add(errorMessage);
				}
				
				
			}
		}
			
		return errorOutputList;
	 }
	 
	 
	 public ArrayList<String> captureError(WebDriver driver,String pagename)
	 {
		 ArrayList<String> errorList = new ArrayList<String>();
		 ArrayList<String> errorListTemp = new ArrayList<String>();
		 errorListTemp = captureErrorMessage(driver);
		 errorList.addAll(errorListTemp);
		 return errorList;
	 }
	 
	//Helper method to change domain of eStore.
		public String changeDomain(String currenteStoreUrl, String newDomain)
		{
			String newUrl = "";
			if(!currenteStoreUrl.toLowerCase().contains(newDomain))
			{
				if(currenteStoreUrl.toLowerCase().contains("lacerte"))
				{
					newUrl = currenteStoreUrl.replace("lacerte",newDomain);
				}
				else if(currenteStoreUrl.toLowerCase().contains("proseries"))
				{
					newUrl = currenteStoreUrl.replace("proseries",newDomain);
				}

				else if(currenteStoreUrl.toLowerCase().contains("pointofsale"))
						{
					newUrl = currenteStoreUrl.replace("pointofsale",newDomain);
				        }
			    }
			return newUrl;
		}
		
	 
	 public static int countOccurrences(String arg1, String arg2) 
	 {
         int count = 0;
         int index = 0;
         while ((index = arg1.indexOf(arg2, index)) != -1) 
         {
              ++index;
              ++count;
         }
         return count;
    }

	
	 
}
