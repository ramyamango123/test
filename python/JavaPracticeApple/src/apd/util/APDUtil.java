package initiate.apd.util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import validate.siebel.util.SiebelDatabaseUtil;


import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.Assert;
import com.intuit.taf.testing.TestResultStatus;
import com.intuit.taf.testing.TestRun;
import com.thoughtworks.selenium.Selenium;
import common.e2e.objects.Item;

/**  
 * Utility class that handles many of the common actions in the Estore UI.
 * 
 * @author  Jeffrey Walker & Ramya Nagendra
 * @version 1.0.0 
 */ 
public class APDUtil 
{
	private static Logger sLog;
	private ArrayList<Item> lineItemsList;
	private Connection connection;
	private DatabaseUtil siebelDatabaseUtil;
	private SQLServerDatabaseUtil estoreDatabaseUtil;
	private String separator = "\t\t\t\t\t";

	public APDUtil()
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

	public void launchOfferingPage(Selenium selenium, String estoreURL, String maxPageLoadInMs)
	{
		//do_unit_test(selenium, maxPageLoadInMs);
		try
		{
			TestRun.startTest("Launch Offering Page");

			//Opens the web page and waits for it to load 
			selenium.open(estoreURL + "/commerce/checkout/demo/items_offerings.jsp");

			//Checks that the offering page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("items_offerings.jsp"))
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
	 * @param itemString
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("ItemsList"). This is an ArrayList of type common.e2e.objects.Item
	 */
	public HashMap<String,Object> addOfferingToShoppingCart(Selenium selenium, String itemString, String maxPageLoadInMs, String estoreURL, HashMap<String, Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add Offering to Shopping Cart");

			//Selects a specific offering from the drop down menu
			while (true) {
				selenium.select("id=skus", "label=" + itemString);
				String item_result = selenium.getSelectedLabels("skus")[0];
				System.out.println(item_result);
				if (item_result.equals(itemString)) break;
				Thread.sleep(1000);	
			}
			if ((estoreURL == "https://wcgo2c3.proseries.intuit.com") && (itemString == "Lacerte Federal | 1099978"))
			{
				selenium.select("id=attrib0", "label=Download with CD");
				selenium.waitForPageToLoad(maxPageLoadInMs);
			}

			//Add ItemsList to OutputMap
			Item item = new Item();
			item.setName(itemString.substring(0,itemString.indexOf("|")).trim());
			item.setNumber(itemString.substring(itemString.indexOf("|")+1).trim());
			lineItemsList.add(item);
			outputMap.put("ItemsList", lineItemsList);

			Thread.sleep(15000);
			selenium.click("id=submit");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=No Thanks");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			//Checks that the shopping cart page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The shopping cart page did not successfully load.");
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

		return outputMap;
	}


	/**
	 * Launches the Estore Sign In page and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchSignInPage(Selenium selenium,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Launch the Sign-In page");

			selenium.click("css=input.coButtonNoSubmit.coCheckoutAndContinue");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			if(selenium.getLocation().toLowerCase().contains("signin.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The sign in page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The sign in page did not successfully load.");
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


	/**
	 * Launches the Estore company information page and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param newUserEmail New User Email
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void createNewUser(Selenium selenium,String newUserEmail,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Create New User");

			selenium.type("id=newUserEmail", newUserEmail);
			selenium.click("id=buttonContinueAsGuest");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			if(selenium.getLocation().toLowerCase().contains("company_info.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The company info page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The company info page did not succesfully load.");
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


	/**
	 * Populates the new customer information 
	 * 
	 * @param selenium Default Selenium
	 * @param userFirstName User First Name
	 * @param userLastName User Last Name
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following keys:"AccountName", "UserName", "streetAddress", "city","state","zip", and "country"
	 */
	public HashMap<String,Object> populateNewCompanyInformation(Selenium selenium,String userFirstName,String userLastName,
			String maxPageLoadInMs, Object siebelDbUserName, Object siebelDbPassword, Object siebelDbUrl,
			HashMap<String,Object> outputMap)
			{
		try
		{

			TestRun.startTest("Populate New Company Information");
			int randomNumber = new Double(Math.random()*10000000).intValue();
			HashMap<String,String> addressMap = buildAddress(randomNumber);
			String accountName = "E2EAutomated" + randomNumber;
			outputMap.put("AccountName", accountName);
			System.out.println("-----------------AccountName------------------" + accountName);

			selenium.type("id=accountName", accountName);
			selenium.type("id=streetAddress",addressMap.get("streetAddress"));
			selenium.type("id=city",addressMap.get("city"));
			selenium.select("id=stateSelect", "label="+addressMap.get("state"));
			selenium.type("id=postalCode",addressMap.get("postalCode"));
			selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
			selenium.type("id=firstName",userFirstName);
			selenium.type("id=lastName",userLastName);
			selenium.click("id=createLogin");
			selenium.type("id=userName","E2EAutomated" + randomNumber);
			System.out.println("-----------------userName------------------" + accountName);
			outputMap.put("UserName", "E2EAutomated" + randomNumber);
			selenium.type("id=password","test123");
			selenium.type("id=confirmPassword","test123");
			selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
			selenium.type("id=securityAnswer","pet");
			selenium.click("id=updateCompanyInfo");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			try 
			{
				selenium.click("id=createCustomerAccount");
				selenium.waitForPageToLoad(maxPageLoadInMs);
			} catch (Exception e) { }

			try 
			{
				selenium.click("id=updateCompanyInfo");
				selenium.waitForPageToLoad(maxPageLoadInMs);
			} catch (Exception e) { }

			String CANSiebelDB = querySiebelDBforCANUsingAccountName(siebelDbUserName,  siebelDbPassword,  siebelDbUrl,outputMap.get("AccountName"), false);
			outputMap.put("CANSiebelDB", CANSiebelDB);

			//Add the address information into the output map
			outputMap.put("streetAddress", addressMap.get("streetAddress"));
			outputMap.put("city", addressMap.get("city"));
			outputMap.put("state", addressMap.get("state"));
			outputMap.put("zip", addressMap.get("postalCode"));
			outputMap.put("country", "USA");


			if(selenium.getLocation().toLowerCase().contains("company_shipping.jsp?"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Company Shipping Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The Company Shipping Page did not successfully load.");
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
		return outputMap;
			}


	/**
	 * Launches the New Payment Method Page (assumes the current page is company_shipping.jsp) 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchNewPaymentMethodPage(Selenium selenium,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Launch New Payment Method Page");

			selenium.click("id=buttonContinue");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			if(selenium.getLocation().toLowerCase().contains("payment_method_new_user.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The New Payment Method Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The New Payment Method Page did not successfully load.");
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


	/**
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param accountType Account Type (Checking or Savings)
	 * @param accountNumber Bank Account Number
	 * @param routingNumber Bank Routing Number
	 */
	public void populateEFTPaymentInformation(Selenium selenium, String maxPageLoadInMs, String accountType, String accountNumber, String routingNumber)
	{
		try
		{
			TestRun.startTest("Populate EFT Information");

			selenium.click("id=radioPaymentEFT");
			selenium.type("id=accountName", "test"); 
			selenium.select("id=dropdownAccountType", "value="+accountType);
			selenium.type("id=routingNumber",routingNumber);
			selenium.type("id=accountNumber",accountNumber);
			selenium.type("id=confirmAccountNumber",accountNumber);
			selenium.click("id=newPaymentMethod");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp?"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart review page has loaded successfully.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The shopping cart review page did not successfully load.");
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


	/**
	 * Submits the order (assumes the current page is shoppingcart_review.jsp)
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("OrderNumber") 
	 */
	public HashMap<String,Object> submitOrder(Selenium selenium, String maxPageLoadInMs, String segmentName, Object dbUserName, Object dbPassword, Object dbUrl, Object estoreDbUrl, 
			Object MDMDbUserName, Object profileDbUserName, Object estoreDbPassword, HashMap<String,Object> outputMap)
			{
		String orderNumber = "";

		try
		{
			TestRun.startTest("Submit The Order");

			selenium.click("id=cart-place-order-button");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			String currentPageBodyText = selenium.getBodyText();
			orderNumber = currentPageBodyText.substring(currentPageBodyText.indexOf("Order Number:")+14);
			orderNumber = orderNumber.substring(0,orderNumber.indexOf('\n'));
			Object OrderNumber = outputMap.put("OrderNumber", orderNumber);
			System.out.println(OrderNumber);


			String location = selenium.getLocation().toLowerCase();

			if(location.contains("cart_confirmation.jsp?"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"OrderNumber for item " + segmentName + " is: { " + orderNumber +"}");

			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Confirmation Page did not successfully load.");
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

		querySiebelDBforOrderStatusUsingOrderNumber(dbUserName, dbPassword, dbUrl, orderNumber, "Complete", outputMap);

				String siebelOrderstatus = (String) outputMap.get("siebel_order_status");
				sLog.info("Finally returned siebel regular order status: " + outputMap.get("siebel_order_status"));
				System.out.println("Finally returned siebel regular order status: " + outputMap.get("siebel_order_status"));
		//		
		//		if (orderNumber != "") {
		//			queryProfileDBforOrderNumberUsingCAN(profileDbUserName, estoreDbPassword, estoreDbUrl, outputMap.get("CANSiebelDB"), orderNumber, selenium, maxPageLoadInMs);
		//			queryProfileDBToVerifyTaxYearUsingCAN(profileDbUserName, estoreDbPassword,  estoreDbUrl, outputMap.get("CANSiebelDB"),"Tax Year 2010", selenium, maxPageLoadInMs);
		//		}
		return outputMap;
			}


	/**
	 * Retrieves Customer Account Number from account management page 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param segmentName (REP/PPR/UL) ProSeries/Lacerte segment item name
	 * @param productName  Name of the product(Lacerte/ProSeries) 
	 * @param accountPageName Account Management page url's relative path name (Proseries item - "myproseries"  and lacerte item - "myaccount") 
	 * @param apdURL  APD URL for the page of interest
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following key ("CAN") 
	 */
	public HashMap<String,Object> retrieveCANFromAccountPage(Selenium selenium,String maxPageLoadInMs, String segmentName, 
			String productName, String accountPageName, String apdURL, HashMap<String,Object> outputMap)

			{
		Matcher matcher=null;
		boolean matchFound = false;
		try
		{
			TestRun.startTest("Retrieve CAN from Account page");

			String userName = (String) outputMap.get("UserName");

			selenium.click("link=My Account");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			try
			{
				selenium.open(apdURL);
			}
			catch(Exception e)
			{
				selenium.waitForPageToLoad(maxPageLoadInMs);	
			}

			//Checks that the SignIn page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("login"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The sign in page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The sign in page did not successfully load.");
			}
			Thread.sleep(90000);
			selenium.type("id=userid", userName);
			selenium.type("name=/intuit/apd/formhandlers/ProTaxLoginFormHandler.formValues.password","test123");
			selenium.click("name=SignIn");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			if (selenium.isTextPresent("Enter Your Customer Number and Zip Code"))
			{
				selenium.click("link=Sign Out");
				selenium.waitForPageToLoad(maxPageLoadInMs);
				selenium.click("link=here");
				selenium.waitForPageToLoad(maxPageLoadInMs);
				try
				{
					selenium.open(apdURL);
				}
				catch(Exception e)
				{
					selenium.waitForPageToLoad(maxPageLoadInMs);	
				}

				selenium.type("id=userid", userName);
				selenium.type("name=/intuit/apd/formhandlers/ProTaxLoginFormHandler.formValues.password","test123");
				selenium.click("name=SignIn");
				selenium.waitForPageToLoad(maxPageLoadInMs);
			}

			String currentPageBodyText = selenium.getBodyText();
			System.out.println("userName" + userName);

			int count = 0;
			while (count < 100)
			{
				String patternStr = "Customer Account #:\\s+(\\d+)\\s+";
				Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
				matcher = pattern.matcher(currentPageBodyText);
				matchFound = matcher.find();
				count += 1;
				if (matchFound == true)
				{
					break;
				}
				else
				{
					Thread.sleep(5);
					selenium.click("link=Sign Out");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					selenium.click("link=here");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					try 
					{
						selenium.open(apdURL);
					}
					catch(Exception e)
					{
						selenium.waitForPageToLoad(maxPageLoadInMs);	
					}
					selenium.type("id=userid", userName);
					selenium.type("name=/intuit/apd/formhandlers/ProTaxLoginFormHandler.formValues.password", "test123");
					selenium.click("name=SignIn");
					selenium.waitForPageToLoad(maxPageLoadInMs);
				}
			}

			String CAN = "";
			if 	(matchFound == true)
			{
				CAN = matcher.group(1);
				System.out.print("------------Customer Account #:----------------" + CAN);
				outputMap.put("CAN", CAN);
			}
			Assert.assertTrue("matchFound is false", matchFound);

			//Checks that the account management page page was loaded successfully
			if (matchFound && selenium.getLocation().toLowerCase().contains(productName.toLowerCase() + ".intuit.com/" + accountPageName))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"CAN for item " + segmentName + " is: { " + CAN +"}");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The account management page did not succesfully load from CAN Renewal Method, CAN is " + CAN);
			}

			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);

		}
		catch(Exception e)
		{
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
				if (streetAddress.length() > 29) {
					addressMap.put("streetAddress", streetAddress.substring(0, 29));
				} else {
					addressMap.put("streetAddress", streetAddress);				
				}

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




	/// APD LACERTE/PROSERIES SEGMENT ITEM ONLINE RENEWAL FLOW METHODS /////////


	/**
	 * Launches the APD sign in page and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param estoreURL APD URL for the page of interest
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchSignInPage(Selenium selenium,String apdURL,String maxPageLoadInMs)
	{

		try
		{
			TestRun.startTest("Launch Sign In Page");

			//Opens the web page and waits for it to load 
			try
			{
				selenium.open(apdURL);
			}
			catch(Exception e)
			{
				selenium.waitForPageToLoad(maxPageLoadInMs);	
			}

			//Checks that the SignIn page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("login"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The sign in page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The sign in page did not successfully load.");
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


	/**
	 * Signs into an existing customer account 
	 * 
	 * @param selenium Default Selenium 
	 * @param userId UserId used to sign into an existing account
	 * @param password Password used to sign into an existing account
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param productName  Name of the product(Lacerte/ProSeries) 
	 * @param accountPageName Account Management page url's relative path name (Proseries item - "myproseries"  and lacerte item - "myaccount") 
	 */
	public void signIn(Selenium selenium, String userId,String password, String maxPageLoadInMs, String productName, String accountPageName )
	{
		try
		{
			TestRun.startTest("Sign In Existing User");
			// Sign In with valid login credentials
			selenium.type("id=userid",userId);
			selenium.type("name=/intuit/apd/formhandlers/ProTaxLoginFormHandler.formValues.password",password);
			selenium.click("name=SignIn");
			selenium.waitForPageToLoad(maxPageLoadInMs);


			//Checks that the account management page page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains(productName.toLowerCase() + ".intuit.com/" + accountPageName)) 
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The account management page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The account management page did not succesfully load.");
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



	public void loginWithExistingAcct(Selenium selenium, String apdURL, String userId, String password, String maxPageLoadInMs, String productName,
			String existingfirmCAN, String accountPageName, String firmName, String env)
	{
		try
		{
			if (env.equals("prodsupp") && productName.equals("ProSeries") )
			{
				TestRun.startTest("Login with an existing Prodsup ProSeries account");
			}
			else if (env.equals("prodsupp") && productName.equals("Lacerte") )
			{
				TestRun.startTest("Login with an existing Prodsup Lacerte account");
			}
			else if (env.equals("production") && productName.equals("ProSeries"))
			{
				TestRun.startTest("Login with an existing Production Proseries account");
			}
			else if (env.equals("production") && productName.equals("Lacerte"))
			{
				TestRun.startTest("Login with an existing production Lacerte account");
			}

			try
			{

				if (apdURL.equals("https://wcgo2c3.proseries.intuit.com/myproseries/login/"))
				{
					selenium.open("https://wcgo2c3.proseries.intuit.com/myproseries/login/" );
					System.out.println("Logging in with existing Proseries acct in prodsup env");
					sLog.info("Logging in with existing Proseries acct in prodsup env");
				}
				else if (apdURL.equals("https://wcgo2c3.lacerte.intuit.com/myaccount/login/"))
				{
					selenium.open("https://wcgo2c3.lacerte.intuit.com/myaccount/login/");
					System.out.println("Logging in with existing Lacerte acct in prodsup env");
					sLog.info("Logging in with existing Lacerte acct in prodsup env");
				}
				else if (apdURL.equals("https://proseries.intuit.com/myproseries/login/"))
				{
					selenium.open("https://proseries.intuit.com/myproseries/login/");
					System.out.println("Logging in with existing Proseries acct in production env");
					sLog.info("Logging in with existing Proseries acct in production env");
				}
				else if (apdURL.equals("https://lacerte.intuit.com/myaccount/login/"))
				{
					selenium.open("https://lacerte.intuit.com/myaccount/login/");
					System.out.println("Logging in with existing Lacerte acct in production env");
					sLog.info("Logging in with existing Lacerte acct in production env");
				}	
			}	

		catch(Exception e)
		{
			selenium.waitForPageToLoad(maxPageLoadInMs);	
		}
		
		selenium.type("id=userid", userId);
		selenium.type("name=/intuit/apd/formhandlers/ProTaxLoginFormHandler.formValues.password", password);
		selenium.click("name=SignIn");
		selenium.waitForPageToLoad(maxPageLoadInMs);
		if (selenium.isTextPresent("Enter Your Customer Number and Zip Code"))
		{
			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=here");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.type("id=userid", userId);
			selenium.type("name=/intuit/apd/formhandlers/ProTaxLoginFormHandler.formValues.password", password);
			selenium.click("name=SignIn");
			selenium.waitForPageToLoad(maxPageLoadInMs);
		}
		//Checks that the account management page page was loaded successfully
		if(selenium.getLocation().toLowerCase().contains(productName.toLowerCase() + ".intuit.com/" + accountPageName)) 
		{
			TestRun.updateStatus(TestResultStatus.PASS,"The account management page has successfully loaded.");
		}
		else
		{
			TestRun.updateStatus(TestResultStatus.FAIL,"The account management page did not succesfully load.");
		}

		Assert.assertEquals("The Customer Account number is not present on the account management page.", selenium.isTextPresent("Customer Account #: " + existingfirmCAN), true);
		Assert.assertEquals("The firm name is not displayed correctly on the account management page.", selenium.isTextPresent("Firm Name: " + firmName), true);
		selenium.click("link=Sign Out");
		selenium.waitForPageToLoad(maxPageLoadInMs);
		selenium.click("link=here");
		selenium.waitForPageToLoad(maxPageLoadInMs);
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


	//  Create a new account for an existing  ProSeries firm
	public void createNewAccountForExistingProseriesFirm(Selenium selenium, String maxPageLoadInMs,String userFirstName,String userLastName, 
			String existingfirmCAN, String productName, String accountPageName, String apdURL,Object siebelDbUserName, Object siebelDbPassword, Object siebelDbUrl,
			String firmname, String zipcode, String env)

	{
		try
		{
			if (env.equals("prodsupp"))
			{
				TestRun.startTest("In prodsupp env, create a new account for an existing proseries firm");
			}
			else if (env.equals("production"))
			{
				TestRun.startTest("In production env, create a new account for an existing proseries firm");
			}
			
			int randomNumber = new Double(Math.random()*10000000).intValue();
			String emailAddress = "E2EAutomatedpsc" + randomNumber + "@apdqa.com";
			try
			{
				if (apdURL.equals("https://wcgo2c3.proseries.intuit.com/myproseries/login/"))
				{
					selenium.open("https://wcgo2c3.proseries.intuit.com/myproseries/login/" );
					System.out.println("Creating new acct for prodsup proseries domain");
					sLog.info("Creating new acct for prodsup Proseries domain");
				}
				else if (apdURL.equals("https://wcgo2c3.lacerte.intuit.com/myaccount/login/"))
				{
					selenium.open("https://wcgo2c3.lacerte.intuit.com/myaccount/login/");
					System.out.println("Creating new acct for prodsup Lacerte domain");
					sLog.info("Creating new acct for prodsup Lacerte domain");
				}
				else if (apdURL.equals("https://proseries.intuit.com/myproseries/login/"))
				{
					selenium.open("https://proseries.intuit.com/myproseries/login/");
					System.out.println("Creating new acct for production Proseries domain");
					sLog.info("Creating new acct for production Proseries domain");
				}
				else if (apdURL.equals("https://lacerte.intuit.com/myaccount/login/"))
				{
					selenium.open("https://lacerte.intuit.com/myaccount/login/");
					System.out.println("Creating new acct for production Lacerte domain");
					sLog.info("Creating new acct for production Lacerte domain");
				}	
			}
			catch(Exception e)
			{
				selenium.waitForPageToLoad(maxPageLoadInMs);	
			}

			Assert.assertTrue(selenium.isTextPresent("Create an Account"));
			selenium.click("css=a.btn-lg-clr01 > span");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			if(selenium.getLocation().contains("login/create_choose"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"During ProSeries acct creation, email address page was successfully loaded");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"During ProSeries acct creation, email address page didn't load successfully");
			}

			Assert.assertEquals("During ProSeries account signup, on Enter your Email Address page, title doesn't match ", "Create Account - Step 1", selenium.getTitle());
			Assert.assertTrue(selenium.isTextPresent("Enter Your Email Address"));
			selenium.type("id=wcgEmail", emailAddress);
			System.out.println("ProSeries Signup emailAddress/UserID " + emailAddress);
			sLog.info("Signup emailAddress/UserID" + emailAddress);
			selenium.click("id=continueButton");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			Assert.assertTrue(selenium.getLocation().contains("login/create_account"));
			Assert.assertTrue(selenium.isTextPresent("Choose User ID and Password"));
			selenium.type("id=password", "test123");
			selenium.type("name=/intuit/apd/formhandlers/ProTaxAccountFormHandler.uniAuth.confirmPassword", "test123");
			selenium.select("id=question", "label=What was the name of your first pet?");
			selenium.type("name=/intuit/apd/formhandlers/ProTaxAccountFormHandler.uniAuth.securityAnswer", "pet");
			selenium.click("name=/intuit/apd/formhandlers/ProTaxAccountFormHandler.authRegister");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			Assert.assertTrue(selenium.getLocation().contains("login/create_sso"));
			Assert.assertTrue(selenium.isTextPresent("Enter Your Customer Number and Zip Code"));
			selenium.type("name=/intuit/apd/proseries/formhandler/ProSeriesLoginFormHandler.accountId", existingfirmCAN);
			selenium.type("name=/intuit/apd/proseries/formhandler/ProSeriesLoginFormHandler.selectedPostalCode", zipcode);
		    selenium.click("name=/intuit/apd/proseries/formhandler/ProSeriesLoginFormHandler.findCustomer");
		    selenium.waitForPageToLoad(maxPageLoadInMs);
		    Thread.sleep(5000);

		    //Assert.assertEquals("Error message displayed is - Your account was found but it does not have eligible ProSeries products...",
		    //	            	selenium.isTextPresent("Your account was found but it does not have eligible ProSeries products."),
		    //		            true);

		    if (selenium.isTextPresent("Your account was found but it does not have eligible ProSeries products."))
			{
				sLog.info("Error message displayed is - Your account was found but it does not have eligible ProSeries products...");
				System.out.println("Error message displayed is - Your account was found but it does not have eligible ProSeries products...");
				Assert.assertTrue(false);
			} 
		    if (selenium.isTextPresent("Our system is unable to verify your account at this time"))
			{
				sLog.info("Error message displayed is - Our system is unable to verify your Proseries account at this time...");
				System.out.println("Error message displayed is - Our system is unable to verify your Proseries account at this time...");
				Assert.assertTrue(false);
			}
		    if (selenium.isTextPresent("Account Billing Zip does not match"))
			{
				sLog.info("Proseries Account Billing Zip does not match"); 
				System.out.println("Proseries Account Billing Zip does not match");
				Assert.assertTrue(false);
			}
			
			Assert.assertTrue(selenium.getLocation().contains("create_contact"));
			Assert.assertTrue(selenium.isTextPresent("Please Enter Your Name"));
			selenium.type("name=/intuit/apd/proseries/formhandler/ProSeriesLoginFormHandler.firstName", userFirstName);
			selenium.type("name=/intuit/apd/proseries/formhandler/ProSeriesLoginFormHandler.lastName",  userLastName);
			selenium.click("id=clicker");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			Thread.sleep(5000);
			Assert.assertTrue(selenium.isTextPresent("Confirmation"));
			Assert.assertTrue(selenium.isElementPresent("css=img[alt=\"Finished\"]"));
			selenium.click("css=input.wcgButtonMedium");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			Assert.assertTrue(selenium.getLocation().toLowerCase().contains(productName.toLowerCase() + ".intuit.com/" + accountPageName));
			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=here");
			selenium.waitForPageToLoad(maxPageLoadInMs);

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

	//  Create a new account for an existing Lacerte firm
	public void createNewAccountForExistingLacerteFirm(Selenium selenium, String maxPageLoadInMs,String userFirstName,String userLastName, 
			String existingfirmCAN, String productName, String accountPageName, String apdURL,Object siebelDbUserName, Object siebelDbPassword, Object siebelDbUrl, 
			String firmname, String zipcode, String env)
	{
		try
		{
			if (env.equals("prodsupp"))
			{
				TestRun.startTest("In prodsupp env, create a new account for an existing Lacerte firm");
			}
			else if (env.equals("production"))
			{
				TestRun.startTest("In production env, create a new account for an existing Lacerte firm");
			}
			
			int randomNumber = new Double(Math.random()*10000000).intValue();
			String emailAddress = "E2EAutomatedlsc" + randomNumber + "@apdqa.com";
			try
			{
				if (apdURL.equals("https://wcgo2c3.proseries.intuit.com/myproseries/login/"))
				{
					selenium.open("https://wcgo2c3.proseries.intuit.com/myproseries/login/" );
				}
				else if (apdURL.equals("https://wcgo2c3.lacerte.intuit.com/myaccount/login/"))
				{
					selenium.open("https://wcgo2c3.lacerte.intuit.com/myaccount/login/");
				}
				else if (apdURL.equals("https://proseries.intuit.com/myproseries/login/"))
				{
					selenium.open("https://proseries.intuit.com/myproseries/login/");
				}
				else if (apdURL.equals("https://lacerte.intuit.com/myaccount/login/"))
				{
					selenium.open("https://lacerte.intuit.com/myaccount/login/");
				}	
			}
			catch(Exception e)
			{
				selenium.waitForPageToLoad(maxPageLoadInMs);	
			}

			Assert.assertTrue(selenium.isTextPresent("Create an Account"));
			selenium.click("css=a.btn-lg-clr01 > span");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			if(selenium.getLocation().contains("login/CreateChoose"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"During Lacerte acct creation, email address page was successfully loaded");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"During Lacerte acct creation, email address page didn't load successfully");
			}

			Assert.assertEquals("During Lacerte account signup, on Enter your Email Address page, title doesn't match", "Login or Create a New Account", selenium.getTitle());
			Assert.assertTrue(selenium.isTextPresent("Enter Your Email Address"));
			selenium.type("id=wcgEmail", emailAddress);
			System.out.println("Lacerte Signup emailAddress/UserID " + emailAddress);
			sLog.info("Signup emailAddress/UserID" + emailAddress);
			selenium.click("id=continueButton");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			Assert.assertTrue(selenium.getLocation().contains("login/CreateAccountStepOne"));
			Assert.assertTrue(selenium.isTextPresent("Choose User ID and Password"));
			selenium.type("id=password", "test123");
			selenium.type("name=/intuit/apd/formhandlers/ProTaxAccountFormHandler.uniAuth.confirmPassword", "test123");
			selenium.select("id=question", "label=What was the name of your first pet?");
			selenium.type("name=/intuit/apd/formhandlers/ProTaxAccountFormHandler.uniAuth.securityAnswer", "pet");
			selenium.click("name=/intuit/apd/formhandlers/ProTaxAccountFormHandler.authRegister");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			Assert.assertTrue(selenium.getLocation().contains("login/CreateAccountStepTwo"));
			Assert.assertTrue(selenium.isTextPresent("Enter Your Customer Number and Zip Code"));
	       	selenium.type("name=/intuit/apd/lacerte/formhandler/LacerteLoginFormHandler.accountId", existingfirmCAN);
			selenium.type("name=/intuit/apd/lacerte/formhandler/LacerteLoginFormHandler.selectedPostalCode", zipcode);
 			selenium.click("name=/intuit/apd/lacerte/formhandler/LacerteLoginFormHandler.findCustomer");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			Thread.sleep(5000);
			
			if (selenium.isTextPresent("Your account was found but it does not have eligible Lacerte products."))
			{
				sLog.info("Error message displayed is - Your account was found but it does not have eligible Lacerte products...");
				System.out.println("Error message displayed is - Your account was found but it does not have eligible Lacerte products...");
				Assert.assertTrue(false);
			}
			if (selenium.isTextPresent("Our system is unable to verify your account at this time"))
			{
				sLog.info("Error message displayed is - (Lacerte acct) Our system is unable to verify your account at this time...");
				System.out.println("Error message displayed is - (Lacerte acct) Our system is unable to verify your account at this time...");
				Assert.assertTrue(false);
			}
			if (selenium.isTextPresent("Account Billing Zip does not match"))
			{
				sLog.info("Lacerte Account Billing Zip does not match"); 
				System.out.println("Lacerte Account Billing Zip does not match");
				Assert.assertTrue(false);
			}


			Assert.assertTrue(selenium.getLocation().contains("login/CreateAccountStepThree"));
			Assert.assertTrue(selenium.isTextPresent("Please Enter Your Name"));
			selenium.type("name=/intuit/apd/lacerte/formhandler/LacerteLoginFormHandler.firstName", userFirstName);
			selenium.type("name=/intuit/apd/lacerte/formhandler/LacerteLoginFormHandler.lastName", userLastName);
			selenium.click("id=clicker");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			Thread.sleep(5000);
			Assert.assertTrue(selenium.isTextPresent("Confirmation"));
			Assert.assertTrue(selenium.isElementPresent("css=img[alt=\"Finished\"]"));
			selenium.click("css=input.wcgButtonMedium");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			Assert.assertTrue(selenium.getLocation().toLowerCase().contains(productName.toLowerCase() + ".intuit.com/" + accountPageName));
			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=here");
			selenium.waitForPageToLoad(maxPageLoadInMs);

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

	/**
	 * Verifies Account Renewal Information on account management page 
	 * 
	 * @param selenium Default Selenium 
	 * @param userName User account name
	 * @param userName User account password
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param accountName Firm name
	 * @param CAN Customer Account Number
	 * @param siebelDbUserName  Siebel DB username
	 * @param siebelDbUserPassword  Siebel DB password
	 * @param siebelDbURL Siebel DB URL
	 * @param estoreDbUrl Estore DB URL
	 * @param MDMDbUserName MDM DB username
	 * @param profileDbUserName Profile DB username
	 * @param estoreDbPassword Estore DB password
	 * @param outputMap Map that will be populated with important information
	 */
	public HashMap<String,Object> verifyAccountRenewalPage(Selenium selenium, String userName, String password, String maxPageLoadInMs, 
			String itemName, String accountName, String CAN, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, String estoreDbUrl, 
			String MDMDbUserName, String profileDbUserName, String estoreDbPassword, HashMap<String,Object> outputMap)
			{
		try
		{
			TestRun.startTest("Verify Account Renewal Info");
			// Clicks Renew Online link, signs out and signs back in for the item name to populate on account management page
			int count = 0;
			while (count < 100) 
			{
				selenium.click("link=Renew Online");
				selenium.waitForPageToLoad(maxPageLoadInMs);
				count += 1;
				if (!selenium.isTextPresent("We are unable to display")) 
				{
					break;
				}
				Thread.sleep(5);
			}
			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=here");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.type("id=userid",userName);
			selenium.type("name=/intuit/apd/formhandlers/ProTaxLoginFormHandler.formValues.password",password);
			selenium.click("name=SignIn");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			Thread.sleep(20000);
			// Verifies Firm Name, CAN, Renewal Status, Renew Now button & Item Name

			Assert.assertEquals("The firm name is not displayed correctly on the account management page.", selenium.isTextPresent("Firm Name: " + outputMap.get("AccountName")), true);
			Assert.assertEquals("The firm name is not displayed correctly on the account management page.", selenium.isTextPresent("Firm Name: " + accountName), true);
			Assert.assertEquals("The Customer Account number is not present on the account management page.", selenium.isTextPresent("Customer Account #: " + CAN), true);

			Assert.assertEquals("CAN displayed on the current page doesn't match with that stored in SiebelDB.",
					selenium.getText("css=p.bot"), "Customer Account #: " + outputMap.get("CANSiebelDB"));

			Assert.assertEquals("The text DUE is not present on the account management page", "DUE",selenium.getText("css=span.status"));
			Assert.assertEquals("The button Renew Now is not present on the account management page", "Renew Now", selenium.getText("link=Renew Now"));

			if (itemName == "Lacerte Federal")
				Assert.assertEquals("The item name is not displayed correctly on the account management page", itemName + " Unlimited" , 
						selenium.getText("//div[@id='myAcctMyTaxZoneA-2']/div/p[2]/strong"));

			else if ((itemName == "ProSeries Pay Per Return License"))
				Assert.assertEquals("The item name is not displayed correctly on the account management page", "ProSeries PPR License" ,
						selenium.getText("//div[@id='myAcctMyTaxZoneA-2']/div/p[2]/strong"));

			else if ((itemName == "Lacerte Remote Entry Processing License"))
				Assert.assertEquals("The item name is not displayed correctly on the account management page", "Lacerte REP License" ,
						selenium.getText("//div[@id='myAcctMyTaxZoneA-2']/div/p[2]/strong"));

			else
				Assert.assertEquals("The item name is not displayed correctly on the account management page", itemName, 
						selenium.getText("//div[@id='myAcctMyTaxZoneA-2']/div/p[2]/strong"));
		}	
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
			System.out.println(e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		// Verifying account name in MDM DB 
		queryMDMDBToVerifyAccountNameUsingCAN(MDMDbUserName, estoreDbPassword, estoreDbUrl, outputMap.get("AccountName"), outputMap.get("CANSiebelDB"), selenium, maxPageLoadInMs);
		return outputMap;

			}


	/**
	 * Verifies OLR Quote page 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param firmName Name of the firm 
	 * @param CAN Customer Account Number
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param accountPageName Account Management page url's relative path name (Proseries item - "myproseries"  and lacerte item - "myaccount") 
	 */
	public void verifyOLRQuotePage(Selenium selenium,String maxPageLoadInMs, String firmName, String CAN, String itemName, String accountPageName)
	{
		try
		{
			TestRun.startTest("Verify OLR Quote page");
			// Clicks Renew Now button to launch OLR Quote page
			selenium.click("link=Renew Now");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			// Checks that the OLR Quote page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("/olr/index.jsp?s_ev1=" + accountPageName + "_header_olr-button"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The OLR Quote page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The OLR Quote page did not succesfully load." + 
						selenium.getLocation());
			}

			Assert.assertEquals("OLR Quote", selenium.getTitle());
			Assert.assertEquals("Add to Cart", selenium.getText("link=Add to Cart"));
			Assert.assertEquals(itemName , selenium.getText("css=div.item-name"));

			// Captures the entire text of the page
			String currentPageBodyText = selenium.getBodyText();

			// Calling validatePrice utility method to validate item's price

			validatePriceOnOLRQuotePage(selenium, currentPageBodyText, itemName, "Regular:");
			validatePriceOnOLRQuotePage(selenium, currentPageBodyText, "", "Your Price:");
			validatePriceOnOLRQuotePage(selenium, currentPageBodyText, "", "Subtotal:");
			validatePriceOnOLRQuotePage(selenium, currentPageBodyText, "", "Total\\*:");

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


	/**
	 * Adding quote items to shopping cart opting-in auto renewal sign-up
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param outputMap Map that will be populated with important information
	 */
	public HashMap<String,Object> addQuoteItemsToShoppingCartWithAutoRenewalSignUp(Selenium selenium, String maxPageLoadInMs, String itemName, HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add quote items to shopping cart with auto renewal sign-up");

			selenium.click("link=Add to Cart");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			//Checks that the auto renewal page was loaded successfully
			if(selenium.getLocation().contains("product_selection_renew.jsp?"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Auto Renewal page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The Auto Renewal page did not successfully load.");
			}
			//Checks the auto renewal page title
			Assert.assertEquals("Complete Your Accounting & Tax Solution", selenium.getTitle());

			// Opting for auto renewal
			selenium.click("id=signUpbox");
			Assert.assertEquals("Sign Up button doesn't exist", "Sign Up", selenium.getValue("name=submitBtn"));
			selenium.click("name=submitBtn");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			//Checks that the shopping cart page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The shopping cart page did not successfully load." + selenium.getLocation());
			}
			String currentPageBodyText = selenium.getBodyText();


			validatePriceOnShoppingCartPage(selenium, currentPageBodyText, itemName, "", "");
			validatePriceOnShoppingCartPage(selenium, currentPageBodyText, "", "Subtotal:", "");
			validatePriceOnShoppingCartPage(selenium, currentPageBodyText, "", "Total:", "");

		}
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		return outputMap;

	}


	/**
	 * Adding quote items to shopping cart opting-out auto renewal sign-up
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param outputMap Map that will be populated with important information
	 */
	public HashMap<String,Object> addQuoteItemsToShoppingCartWithoutAutoRenewalSignUp(Selenium selenium, String maxPageLoadInMs, String itemName, HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add quote items to shopping cart without auto renewal sign-up");

			selenium.click("link=Add to Cart");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			//Checks that the auto renewal page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("product_selection_renew.jsp?"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The Auto Renewal page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The Auto Renewal page did not successfully load.");
			}
			// Checks the auto renewal page title
			Assert.assertEquals("Complete Your Accounting & Tax Solution", selenium.getTitle());

			// Not opting for auto renewal program
			Assert.assertEquals("No Thanks", selenium.getText("link=No Thanks"));
			selenium.click("link=No Thanks");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			//Checks that the shopping cart page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The shopping cart page did not successfully load." + selenium.getLocation());
			}
			String currentPageBodyText = selenium.getBodyText();

			validatePriceOnShoppingCartPage(selenium, currentPageBodyText, itemName, "", "");
			validatePriceOnShoppingCartPage(selenium, currentPageBodyText, "", "Subtotal:", "");
			validatePriceOnShoppingCartPage(selenium, currentPageBodyText, "", "Total:", "");

		}
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		return outputMap;

	}


	/**
	 * Adding quote items along with product interstitial page item(s) to shopping cart
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param outputMap Map that will be populated with important information
	 */
	public HashMap<String,Object> addQuoteItemsWithInterstitialPageItemToShoppingCart(Selenium selenium, String maxPageLoadInMs, String itemName, HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add quote items along with product interstitial page item(s) to shopping cart");

			selenium.click("link=Add to Cart");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			//Checks that the auto renewal page was loaded successfully
			if(selenium.getLocation().contains("product_selection.jsp?"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The product selection page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The product selection page did not successfully load.");
			}
			String ProductInterstitialPageBodyText;
			if (itemName == "ProSeries Federal")
			{	
				//Adding a product selection page item (ProSeries Document Management System) to cart
				Assert.assertTrue(selenium.isTextPresent("ProSeries Document Management System"));
				ProductInterstitialPageBodyText = selenium.getBodyText();
				validatePriceOnProductInterstitialPage(selenium, ProductInterstitialPageBodyText,"ProSeries Document Management System");
				selenium.click("css=div.hero_box_bottom > div.hero_right > a.lgBtn01.lgBtnClr01");
				selenium.waitForPageToLoad(maxPageLoadInMs);
			}

			if (itemName ==  "Lacerte Federal")
			{
				//Adding a product selection page item (Lacerte Productivity Plus) to cart
				Assert.assertTrue(selenium.isTextPresent("Lacerte Productivity Plus"));
				ProductInterstitialPageBodyText = selenium.getBodyText();
				validatePriceOnProductInterstitialPage(selenium, ProductInterstitialPageBodyText,"Lacerte Productivity Plus");
				selenium.click("css=div.hero_box_bottom > div.hero_right > a.lgBtn01.lgBtnClr01");
				selenium.waitForPageToLoad(maxPageLoadInMs);
			}


			//Checks if the shopping cart page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The shopping cart page was successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The shopping cart page did not successfully load." + selenium.getLocation());
			}
			String shoppingcartPageBodyText = selenium.getBodyText();
			if (itemName == "ProSeries Federal")
			{
				validatePriceOnShoppingCartPage(selenium, shoppingcartPageBodyText, "", "", "ProSeries Document Management System");
			}

			if (itemName ==  "Lacerte Federal")
			{
				validatePriceOnShoppingCartPage(selenium, shoppingcartPageBodyText, "", "", "Lacerte Productivity Plus");
			}

			validatePriceOnShoppingCartPage(selenium, shoppingcartPageBodyText, itemName, "", "");
			validatePriceOnShoppingCartPage(selenium, shoppingcartPageBodyText, "", "Subtotal:", "");
			validatePriceOnShoppingCartPage(selenium, shoppingcartPageBodyText, "", "Total:", "");
		}
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			TestRun.endTest();
		}
		return outputMap;

	}


	/**
	 * Verifies Company information page 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param siebelDbUserName  Siebel DB username
	 * @param siebelDbUserPassword  Siebel DB password
	 * @param siebelDbURL Siebel DB URL
	 * @param estoreDbUrl Estore DB URL
	 * @param MDMDbUserName MDM DB username
	 * @param profileDbUserName Profile DB username
	 * @param estoreDbPassword Estore DB password
	 * @param outputMap Map that will be populated with important information
	 * @return Output Map with the value for the following keys:"APDCompanyAddressLine1", "APDCompanyAddressCity", "APDCompanyAddressState",
	 *                                                           "APDCompanyAddressZipCode","APDCompanyAddressCountry","APDCompanyContactFirstName",
	 *                                                           "APDCompanyContactLastName", "APDCompanyContactEmailAddress", "APDCompanyName"
	 */
	public HashMap<String,Object> verifyCompanyInformationPage(Selenium selenium, String maxPageLoadInMs,
			String itemName,String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, Object estoreDbUrl, Object MDMDbUserName, Object profileDbUserName, 
			Object estoreDbPassword, HashMap<String, Object> outputMap, boolean endTest)

			{
		try
		{
			TestRun.startTest("Verify Company Information page");

			selenium.click("css=input.coButtonNoSubmit.coCheckoutAndContinue");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			// Checks if Your Company page was loaded successfully
			if(selenium.getLocation().contains("company_shipping.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Your company information page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"our company information page did not successfully load." + 
						selenium.getLocation());
			}
			Assert.assertEquals("Your Company", selenium.getText("css=div.co_float_left"));

			String bodyText = selenium.getBodyText();

			//Using regular expressions to extract company address from "Your Company" page
			String patternStr1 = "Company Address:\\s+(.*?)Company Contact";

			// Compile and use regular expression
			Pattern pattern1 = Pattern.compile(patternStr1, Pattern.MULTILINE | Pattern.DOTALL);
			Matcher matcher1 = pattern1.matcher(bodyText);
			boolean matchFound1 = matcher1.find();
			String [] addressArray;


			if (matchFound1)
			{
				String companyAddressStr = matcher1.group(1);
				addressArray = companyAddressStr.split("\n");
				//HashMap<String,Object> outputmap = new HashMap<String,Object>();
				outputMap.put("APDCompanyAddressLine1", addressArray[0]);
				outputMap.put("APDCompanyAddressLine2", "");
				outputMap.put("APDCompanyAddressCity", addressArray[1].split(",")[0].trim());
				outputMap.put("APDCompanyAddressState", addressArray[1].split(",")[1].trim());
				outputMap.put("APDCompanyAddressZipCode", addressArray[2].substring(0, 6).trim());
				outputMap.put("APDCompanyAddressCountry", "US");
				System.out.print("--------------------\n" + companyAddressStr + "---------------\n");
			} 
			else 
			{
				System.out.print("---------------------- NOMATCH ------------------\n");
			}

			//Pattern to extract company contact info from "Your Company" page
			String PatternStr2 = "Company Contact:\\s+(.*?)Your Cart";

			// Compile and use regular expression
			Pattern pattern2 = Pattern.compile(PatternStr2, Pattern.MULTILINE | Pattern.DOTALL);
			Matcher matcher2 = pattern2.matcher(bodyText);
			boolean matchFound2 = matcher2.find();

			if (matchFound2)
			{
				String companyContactStr = matcher2.group(1);
				String [] companyContactArray = companyContactStr.split("\n");
				//HashMap<String,Object> outputmap = new HashMap<String,Object>();
				outputMap.put("APDCompanyContactFirstName", companyContactArray[0].split(" ")[0]);
				outputMap.put("APDCompanyContactLastName", companyContactArray[0].split(" ")[1]);
				outputMap.put("APDCompanyContactEmailAddress", companyContactArray[1]);
				System.out.print("--------------------\n" + companyContactStr + "---------------\n");
			} 
			else 
			{
				System.out.print("---------------------- NOMATCH ------------------\n");
			}

			//Pattern to extract company name from "Your Company" web page
			String PatternStr3 = "Company Name:\\s+(.*?)Company Address";

			// Compile and use regular expression
			Pattern pattern3 = Pattern.compile(PatternStr3, Pattern.MULTILINE | Pattern.DOTALL);
			Matcher matcher3 = pattern3.matcher(bodyText);
			boolean matchFound3 = matcher3.find();

			if (matchFound3)
			{
				String companyContactNameStr = matcher3.group(1);
				String [] companyContactNameArray = companyContactNameStr.split("\n");
				//HashMap<String,Object> outputmap = new HashMap<String,Object>();
				outputMap.put("APDCompanyName", companyContactNameArray[0]);
				System.out.print("--------------------\n" + companyContactNameStr + "---------------\n");
			} 
			else 
			{
				System.out.print("---------------------- NOMATCH ------------------\n");
			}

			// Verifies Change Company, Create New Company and Edit Company links are not present for OLR flow
			Assert.assertFalse("Change Company link is displayed on Your Company page for OLR flow.", selenium.isElementPresent("link=Change Company"));
			Assert.assertFalse("Create New Company link is displayed on Your Company page.", selenium.isElementPresent("link=Create New Company"));

			// "Edit" link shows up sometime during OLR flow (This is a known bug. So commenting it out)
			//Assert.assertFalse("Edit link is displayed on Your Company page.", selenium.isElementPresent("link=Edit"));

			String currentPageBodyText = selenium.getBodyText();

			validatePriceOnYourCompanyPage(selenium, currentPageBodyText, itemName, "");
			validatePriceOnYourCompanyPage(selenium, currentPageBodyText, "", "Total:");

		}	

		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{ 
			if (endTest) TestRun.endTest();
		}

		querySiebelDBforCANUsingAccountName(siebelDbUserName,  siebelDbPassword,  siebelDbUrl, outputMap.get("AccountName"), endTest);

		querymdmDBToVerifyAddressUsingCAN(MDMDbUserName, estoreDbPassword, estoreDbUrl, outputMap.get("APDCompanyAddressLine1"), 
				outputMap.get("APDCompanyAddressCity"),outputMap.get("APDCompanyAddressState"),outputMap.get("APDCompanyAddressZipCode"),
				outputMap.get("APDCompanyAddressCountry"), outputMap.get("CANSiebelDB"), endTest, selenium, maxPageLoadInMs);

		querymdmDBToVerifyContactInfoUsingCAN(MDMDbUserName, estoreDbPassword, estoreDbUrl, outputMap.get("APDCompanyContactFirstName"),
				outputMap.get("APDCompanyContactLastName"),outputMap.get("APDCompanyContactEmailAddress"), 
				outputMap.get("CANSiebelDB"), endTest, selenium, maxPageLoadInMs);

		return outputMap;

			}


	/**
	 * Launches the Existing Payment Method Page (assumes the current page is company_shipping.jsp) 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param paymentType Type of payment (checking/savings)
	 * @param lastThreeDigitsAccNo Last three digits of EFT account number 
	 * @param lastFourDigitsRoutingNo Last four digits of EFT routing number 
	 * @param bankAccName Name of the bank account 
	 * @param buttonID "Continue" button id is stored
	 */
	public void verifyExistingPaymentMethodPage(Selenium selenium, String maxPageLoadInMs, String itemName, String paymentType, String lastThreeDigitsAccNo, 
			String lastFourDigitsRoutingNo, String bankAccName, boolean endTest,String buttonID)
	{
		try
		{
			TestRun.startTest("Verifying Payment Method Page");


			selenium.click(buttonID);
			selenium.waitForPageToLoad(maxPageLoadInMs);

			if(selenium.getLocation().toLowerCase().contains("payment_method.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The Existing Payment Method Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Existing Payment Method Page did not successfully load.");
			}

			Assert.assertTrue(selenium.isTextPresent("You do not have any credit cards on file."));

			Assert.assertEquals("Incorrect EFT Information", "Account number: xxxxxxx" + lastThreeDigitsAccNo + "\n" + " Routing number: xxxxxxxxxxxxx" + lastFourDigitsRoutingNo 
					+ "\n" + " Name on account: " + bankAccName + "\n" + " Edit", selenium.getText("//div[@id='corner_tr_blue1']/div/div[5]"));
			Assert.assertTrue(selenium.isTextPresent("Account Type:" + paymentType));
			Assert.assertEquals("Missing new bank acct creation link", "Add a new bank account", selenium.getText("link=Add a new bank account"));
			String bodyText = selenium.getBodyText();


			validatePriceOnYourPaymentPage(selenium, bodyText, itemName, "");
			validatePriceOnYourPaymentPage(selenium, bodyText, "", "Total:");
			selenium.click("id=paymentType");
		}
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			if (endTest) TestRun.endTest();
		}
	}


	/**
	 * Verifies the Shopping Cart review Page
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
	 * @param paymentType Type of payment (checking/savings)
	 * @param lastThreeDigitsAccNo Last three digits of EFT account number 
	 * @param lastFourDigitsRoutingNo Last four digits of EFT routing number 
	 * @param bankAccName Name of the bank account 
	 * @param buttonID "Continue" button id is stored
	 */
	public void verifyShoppingCartReviewPage(Selenium selenium,String maxPageLoadInMs,String itemName, String paymentType, String lastThreeDigitsAccNo, 
			String lastFourDigitsRoutingNo, String bankAccName, boolean endTest, String buttonID)
	{
		try
		{
			TestRun.startTest("Verifying Shopping Cart review Page");


			selenium.click(buttonID);
			selenium.waitForPageToLoad(maxPageLoadInMs);

			if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The Shopping Cart Review Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Shopping Cart Review Page did not successfully load.");
			}

			Assert.assertTrue(selenium.isElementPresent("id=cart-place-order-button"));
			Assert.assertTrue(selenium.isTextPresent("Payment"));
			Assert.assertEquals("Edit", selenium.getText("link=Edit"));
			Assert.assertTrue(selenium.isTextPresent(paymentType));
			Assert.assertEquals("Missing account name: " + "Account Holder: " + bankAccName,
					true, selenium.isTextPresent("Account Holder: " + bankAccName));
			Assert.assertTrue(selenium.isTextPresent("Ending with: xxxxxxx" + lastThreeDigitsAccNo));

			String bodyText = selenium.getBodyText();

			validatePriceOnShoppingCartReviewPage(selenium, bodyText, itemName, "");
			validatePriceOnShoppingCartReviewPage(selenium, bodyText, "", "Subtotal:");
			validatePriceOnShoppingCartReviewPage(selenium, bodyText, "", "Sales Tax:");
			validatePriceOnShoppingCartReviewPage(selenium, bodyText, "", "Total:");
		}
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			if (endTest) TestRun.endTest();
		}
	}

	/**
	 * Submits the OLR order
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param segmentName (REP/PPR/UL) ProSeries/Lacerte segment item name
	 * @param siebelDbUserName  Siebel DB username
	 * @param siebelDbUserPassword  Siebel DB password
	 * @param siebelDbURL Siebel DB URL
	 * @param estoreDbUrl Estore DB URL
	 * @param MDMDbUserName MDM DB username
	 * @param profileDbUserName Profile DB username
	 * @param estoreDbPassword Estore DB password
	 * @param outputMap Map that will be populated with important information
	 */
	public HashMap<String,Object> submitOLROrder(Selenium selenium, String maxPageLoadInMs, String segmentName, String itemName, Object dbUserName, Object dbPassword, Object dbUrl,
			Object estoreDbUrl, Object MDMDbUserName, Object profileDbUserName, Object estoreDbPassword, HashMap<String,Object> outputMap)

			{
		String OLRorderNumber = "";

		try
		{
			TestRun.startTest("Submit OLR Order");

			selenium.click("id=cart-place-order-button");
			selenium.waitForPageToLoad(maxPageLoadInMs);

			String currentPageBodyText = selenium.getBodyText();
			OLRorderNumber = currentPageBodyText.substring(currentPageBodyText.indexOf("Order Number:")+14);
			OLRorderNumber = OLRorderNumber.substring(0, OLRorderNumber.indexOf('\n'));
			outputMap.put("OLROrderNumber", OLRorderNumber);
			System.out.println(OLRorderNumber);


			if(selenium.getLocation().contains("cart_confirmation.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"OrderNumber for item " + segmentName + " is: { " + OLRorderNumber +"}");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Confirmation Page did not successfully load.");
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

		if (OLRorderNumber != "") {
			querySiebelDBforOrderStatusUsingOrderNumber(dbUserName, dbPassword, dbUrl, OLRorderNumber, "Complete", outputMap);
			queryProfileDBforOrderNumberUsingCAN(profileDbUserName, estoreDbPassword, estoreDbUrl, outputMap.get("CANSiebelDB"), OLRorderNumber, selenium, maxPageLoadInMs);

			if (itemName != "ProSeries Basic 1040 Federal") // bug
			{
				queryProfileDBToVerifyTaxYearUsingCAN(profileDbUserName, estoreDbPassword,  estoreDbUrl, outputMap.get("CANSiebelDB"),"Tax Year 2011", selenium, maxPageLoadInMs);
			}
		}

		return outputMap;
			}


	/**
	 * Verify Cart Confirmation Page for the user who has Opted for Auto Renewal SignUp
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment (checking/savings)
	 * @param lastThreeDigitsAccNo Last three digits of EFT account number 
	 * @param lastFourDigitsRoutingNo Last four digits of EFT routing number 
	 * @param bankAccName Name of the bank account 
	 * @param productName  Name of the product(Lacerte/ProSeries) 
	 */
	public void verifyCartConfirmationPageOptedInAutoRenewalSignUp(Selenium selenium,String maxPageLoadInMs,String itemName, String paymentType, 
			String lastThreeDigitsAccNo, String lastFourDigitsRoutingNo, String bankAccName,
			String productName)
	{
		try
		{
			TestRun.startTest("Verify Cart Confirmation Page for the user who has Opted for Auto Renewal SignUp");

			Assert.assertTrue(selenium.getLocation().contains("cart_confirmation.jsp"));
			Assert.assertTrue(selenium.isTextPresent("Thank you for your purchase."));
			Assert.assertTrue(selenium.isTextPresent("We currently have your " + productName + " license set to Auto Renew on an annual basis."));
			Assert.assertTrue(selenium.isTextPresent("Payment"));
			Assert.assertTrue(selenium.isTextPresent(paymentType));
			Assert.assertEquals("Missing account name: " + "Account Holder: " + bankAccName,
					true, selenium.isTextPresent("Accountholder: " + bankAccName));
			Assert.assertTrue(selenium.isTextPresent("Ending with: xxxxxxx" + lastThreeDigitsAccNo));

			Assert.assertEquals("Sign Out link is not present", "Sign Out", selenium.getText("link=Sign Out"));
			Assert.assertEquals("My Account link is not present", "My Account", selenium.getText("link=My Account"));
			Assert.assertTrue(selenium.isTextPresent("Thank you for your purchase."));

			String currentPageBodyText = selenium.getBodyText();

			validatePriceOncartReviewPage(selenium, currentPageBodyText, itemName, "");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Subtotal:");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Sales Tax:");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Total:");
			selenium.click("link=My Account");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);

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


	/**
	 * Verify Cart Confirmation Page for the user who has not Opted for Auto Renewal SignUp
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment (checking/savings)
	 * @param lastThreeDigitsAccNo Last three digits of EFT account number 
	 * @param lastFourDigitsRoutingNo Last four digits of EFT routing number 
	 * @param bankAccName Name of the bank account 
	 * @param productName  Name of the product(Lacerte/ProSeries) 
	 */
	public void verifyCartConfirmationPageOptedOutAutoRenewalSignUp(Selenium selenium, String maxPageLoadInMs, String itemName, String paymentType, 
			String lastThreeDigitsAccNo, String lastFourDigitsRoutingNo, String bankAccName, String productName)
	{
		try
		{
			TestRun.startTest("Verify Cart Confirmation Page for the user who has not Opted for Auto Renewal SignUp");

			Assert.assertTrue(selenium.getLocation().contains("cart_confirmation.jsp"));
			Assert.assertTrue(selenium.isTextPresent("Thank you for your purchase."));
			Assert.assertTrue(selenium.isTextPresent("We currently don’t have your " + productName + " license set to Auto Renew on an annual basis."));
			Assert.assertTrue(selenium.isTextPresent("Payment"));
			Assert.assertTrue(selenium.isTextPresent(paymentType));
			Assert.assertEquals("Missing account name: " + "Account Holder: " + bankAccName,
					true, selenium.isTextPresent("Accountholder: " + bankAccName));

			Assert.assertTrue(selenium.isTextPresent("Ending with: xxxxxxx" + lastThreeDigitsAccNo));

			Assert.assertEquals("Sign Out link is not present", "Sign Out", selenium.getText("link=Sign Out"));
			Assert.assertEquals("My Account link is not present", "My Account", selenium.getText("link=My Account"));


			String currentPageBodyText = selenium.getBodyText();

			validatePriceOncartReviewPage(selenium, currentPageBodyText, itemName, "");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Subtotal:");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Sales Tax:");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Total:");
			selenium.click("link=My Account");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("link=Sign Out");
			selenium.waitForPageToLoad(maxPageLoadInMs);

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


	/**
	 * Verify Cart Confirmation Page for Unlimited segment item
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment (checking/savings)
	 * @param lastThreeDigitsAccNo Last three digits of EFT account number 
	 * @param lastFourDigitsRoutingNo Last four digits of EFT routing number 
	 * @param bankAccName Name of the bank account 
	 * @param productName Name of the product(Lacerte/ProSeries) 
	 * @param endTest Boolean (True/False) used to decide whether to end test or not 
	 */
	public void verifyCartConfirmationPageWithInterstitialItem(Selenium selenium, String maxPageLoadInMs, String itemName, String paymentType,
			String lastThreeDigitsAccNo, String lastFourDigitsRoutingNo, String bankAccName, boolean endTest)
	{
		try
		{
			TestRun.startTest("Verify Cart Confirmation Page for Unlimited segment item");

			Assert.assertTrue(selenium.getLocation().contains("cart_confirmation.jsp"));
			Assert.assertTrue(selenium.isTextPresent("Thank you for your purchase."));

			Assert.assertTrue(selenium.isTextPresent("Payment"));
			Assert.assertTrue(selenium.isTextPresent(paymentType));
			Assert.assertEquals("Missing account name: " + "Account Holder: " + bankAccName,
					true, selenium.isTextPresent("Accountholder: " + bankAccName));
			Assert.assertTrue(selenium.isTextPresent("Ending with: xxxxxxx" + lastThreeDigitsAccNo));

			Assert.assertEquals("Sign Out link is not present", "Sign Out", selenium.getText("link=Sign Out"));
			Assert.assertEquals("My Account link is not present", "My Account", selenium.getText("link=My Account"));


			String currentPageBodyText = selenium.getBodyText();

			validatePriceOncartReviewPage(selenium, currentPageBodyText, itemName, "");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Subtotal:");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Sales Tax:");
			validatePriceOncartReviewPage(selenium, currentPageBodyText, "", "Total:");

			if (endTest)
			{
				selenium.click("link=My Account");
				selenium.waitForPageToLoad(maxPageLoadInMs);
				selenium.click("link=Sign Out");
				selenium.waitForPageToLoad(maxPageLoadInMs);
			}

		}
		catch(Exception e)
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE, e.getMessage());
		}
		finally
		{
			if (endTest) TestRun.endTest();
		}

	}



	// Utility methods//

	/**
	 * Utility method which helps to validate item's price on OLR Quote page
	 * @param selenium Default Selenium 
	 * @param bodyText HTML source page
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment (checking/savings)
	 */
	public void validatePriceOnOLRQuotePage(Selenium selenium, String bodyText, String itemName, String priceType)
	{
		String patternStr = itemName + ".*" + priceType +  ".*?\\$([\\d,]+(\\.\\d{2})?)";

		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			System.out.println(groupStr);
			groupStr = groupStr.replaceAll(",","");
			System.out.println("second" + groupStr);
			float itemValue = (Float.valueOf(groupStr)).floatValue();
			System.out.println("itemPrice" + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0) 
			{

				TestRun.updateStatus(TestResultStatus.PASS,"Non-zero " + priceType + " item price of " + "value: {"+ itemValue +"}" + " is displayed on the OLR Quote Page");
				System.out.println(priceType + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price " + "of value: {"+ itemValue +"}" + " which is <= zero is displayed on the OLR Quote Page");
				System.out.println(priceType + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, priceType +  " Item price is not present on the OLR Quote page.");

		}
	}


	/**
	 * Utility method which helps to validate item's price on Shopping Cart page
	 * @param selenium Default Selenium 
	 * @param bodyText HTML source page
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment(checking/savings)
	 * @param InterstitialPageItem  An Item chosen from Interstitial page to validate it's price - "Lacerte Productivity Plus"    
	 */
	public void validatePriceOnShoppingCartPage(Selenium selenium, String bodyText, String itemName, String priceType, String InterstitialPageItem)
	{

		String patternStr = itemName + priceType + InterstitialPageItem + ".*?\\$([\\d,]+(\\.\\d{2})?)";

		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			String commaCheck = groupStr.replaceAll(",","");
			float itemValue = (Float.valueOf(commaCheck)).floatValue();
			System.out.println("itemPrice" + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0) 
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Non-zero " + priceType + "item price of " + "value: {"+ itemValue +"}" + " is displayed on the shopping cart page.");
				System.out.println(priceType + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price " + "of value: {"+ itemValue +"}" + " which is <= zero is displayed on the shopping cart page.");
				System.out.println(priceType + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, priceType + " Item price is not present on the shopping cart page.");
		}
	}


	/**
	 * Utility method which helps to validate item's price on Your Company page
	 * @param selenium Default Selenium 
	 * @param bodyText HTML source page
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment(checking/savings)
	 */
	public void validatePriceOnYourCompanyPage(Selenium selenium, String bodyText, String itemName, String priceType)
	{
		String patternStr = itemName + priceType  + ".*?\\$([\\d,]+(\\.\\d{2})?)";

		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			String commaCheck = groupStr.replaceAll(",","");
			float itemValue = (Float.valueOf(commaCheck)).floatValue();
			System.out.println("itemPrice" + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0) 
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Non-zero " + priceType + "item price of " + " value: {"+ itemValue +"}" + " is displayed for " + itemName + " on the company information page.");
				System.out.println(priceType + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price " + "of value: {"+ itemValue +"}" + " which is <= zero  is displayed on the company information page.");
				System.out.println(priceType + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, priceType + " Item price is not present on the company information page.");
		}
	}


	/**
	 * Utility method which helps to validate item's price on payment page
	 * @param selenium Default Selenium 
	 * @param bodyText HTML source page
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment (checking/savings)
	 */
	public void validatePriceOnYourPaymentPage(Selenium selenium, String bodyText, String itemName, String priceType)
	{
		String patternStr = itemName + priceType  + ".*?\\$([\\d,]+(\\.\\d{2})?)";

		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			String commaCheck = groupStr.replaceAll(",","");
			float itemValue = (Float.valueOf(commaCheck)).floatValue();
			System.out.println("itemPrice" + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0) 
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Non-zero " + priceType + "item price of " + "value: {" + itemValue + "}" + " is displayed on payment page.");
				System.out.println(priceType + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price " + "of value: {"+ itemValue + "}" + " which is <= zero is displayed on the payment page.");
				System.out.println(priceType + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, priceType + " Item price is not present on the payment page.");
		}
	}


	/**
	 * Utility method which helps to validate item's price on Shopping Cart review page
	 * @param selenium Default Selenium 
	 * @param bodyText HTML source page
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment (checking/savings)
	 */
	public void validatePriceOnShoppingCartReviewPage(Selenium selenium, String bodyText, String itemName, String priceType)
	{
		String patternStr = itemName + priceType + ".*?\\$([\\d,]+(\\.\\d{2})?)";

		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			String commaCheck = groupStr.replaceAll(",","");
			float itemValue = (Float.valueOf(commaCheck)).floatValue();
			System.out.println("itemPrice" + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0 || (itemValue == 0.0 && priceType == "Sales Tax:")) 
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Non-zero " + priceType + "item price of " + "value: {"+ itemValue +"}" + " is displayed on shopping cart review page.");
				System.out.println(priceType + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price " + "of value: {" + itemValue + "}" + " which is <= zero is displayed on shopping cart review page.");
				System.out.println(priceType + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, priceType + " Item price is not present on shopping cart review page.");
		}
	}


	/**
	 * Utility method which helps to validate item's price on Cart review page
	 * @param selenium Default Selenium 
	 * @param bodyText HTML source page
	 * @param itemName ProSeries/Lacerte segment item name
	 * @param paymentType Type of payment (checking/savings)
	 */
	public void validatePriceOncartReviewPage(Selenium selenium, String bodyText, String itemName, String priceType)
	{
		String patternStr = itemName + priceType + ".*?\\$([\\d,]+(\\.\\d{2})?)";


		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			String commaCheck = groupStr.replaceAll(",","");
			float itemValue = (Float.valueOf(commaCheck)).floatValue();
			System.out.println("itemPrice" + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0 || (itemValue == 0.0 && priceType == "Sales Tax:")) 
			{
				TestRun.updateStatus(TestResultStatus.PASS,"Non-zero " + priceType + "item price of " + "value: {"+ itemValue +"}" + " is displayed on cart review page.");
				System.out.println(itemValue + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, priceType + "Item price " + "of value: {" + itemValue + "}" + " which is <= zero is displayed on cart review page.");
				System.out.println(itemValue + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, priceType + " Item price is not present on the cart review page.");
		}
	}


	/**
	 * Utility method which helps to validate item's price on interstitial page
	 * @param selenium Default Selenium 
	 * @param bodyText HTML source page
	 * @param itemName ProSeries/Lacerte segment item name
	 */
	public void validatePriceOnProductInterstitialPage(Selenium selenium, String bodyText, String itemName)
	{
		//		String patternStr = itemName + ".*?\\$(\\d+(\\.\\d{2})?)\\s+Add to Cart Learn More";

		String patternStr = itemName + ".*\\$(\\d+(\\.\\d{2})?).*No Thanks";

		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();

		if (matchFound)
		{
			String groupStr = matcher.group(1);
			String commaCheck = groupStr.replaceAll(",","");
			float itemValue = (Float.valueOf(commaCheck)).floatValue();
			System.out.println("itemPrice" + " " + itemValue);

			// Checks if the item price is a non-zero value
			if  (itemValue > 0) 
			{
				TestRun.updateStatus(TestResultStatus.PASS, "Non-zero price of " + "value: {"+ itemValue +"}" + " is displayed for " + itemName  + " on product selection page.");
				System.out.println(itemValue + "good");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,  "itemValue: {" + itemValue + "}" + " which is <= zero is displayed for item " + itemName + " on product selection page.");
				System.out.println(itemValue + "bad");

			}
		}
		else 
		{
			System.out.println("No match");
			TestRun.updateStatus(TestResultStatus.FAIL, "Item price is not present for the selected item on product selection page");
		}
	}


	/// MDM and Profile DB CACHE Invalidate methods ///

	/**
	 * Utility method which helps to invalidate cache in Profile DB
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void invalidateCacheInProfileDB(Selenium selenium, String profileDBCacheURL, String maxPageLoadInMs)
	{

		try 
		{
			TestRun.startTest("Invalidating Profile DB cache");

			selenium.open(profileDBCacheURL);
			selenium.click("name=submit");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			sLog.info("Profile DB Cache invalidated");
			TestRun.updateStatus(TestResultStatus.PASS, "Profile DB Cache invalidated");
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


	/**
	 * Utility method which helps to invalidate cache in MDM DB
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void invalidateCacheInMDMDB(Selenium selenium, String mdmDBCacheURL, String maxPageLoadInMs)
	{

		try 
		{
			TestRun.startTest("Invalidating MDM DB cache");


			selenium.open(mdmDBCacheURL);
			selenium.click("link=invalidateCaches");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("name=submit");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			sLog.info("MDM DB Cache invalidated");
			TestRun.updateStatus(TestResultStatus.PASS, "MDM DB Cache invalidated");
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


	////  Database validation Methods //////

	/**
	 * Siebel DB Validation: Verifies customer account number(CAN) using Account name 
	 * @param dbUserName  Siebel DB username 
	 * @param dbPassword  Siebel DB password 
	 * @param dbUrl Siebel DB URL 
	 * @param accountName  Name of the firm
	 * @param startTest Boolean (True/False) used to decide whether to start test or not 
	 * @param outputMap Map that will be populated with important information
	 */
	private String querySiebelDBforCANUsingAccountName(Object dbUserName, Object dbPassword, Object dbUrl,Object accountName, boolean startTest)
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String accountNameStr = (String)accountName;

		String query = "";
		sLog = Logger.getInstance();
		String CAN = "";

		if (startTest) {
			TestRun.startSuite("Query Siebel DB to verify CAN By Account name");
			TestRun.startTest("Validating Account name Sync to Siebel DB");
		}

		int count = 0;
		try 
		{

			siebelDatabaseUtil = new DatabaseUtil();
			connection = siebelDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);

			query = "select * from siebel.s_org_ext where NAME='" + accountNameStr + "'";

			ResultSet rset;
			Statement mStatement;
			mStatement = connection.createStatement();

			while (true) {			

				rset = mStatement.executeQuery(query);
				System.out.println("Result" + rset);

				boolean resultIsPresent = rset.next();

				if(resultIsPresent)
				{
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "ACCOUNT: \""+accountNameStr+"\" is successfully synched to Siebel.");
					sLog.info(separator + "CAN: "+rset.getString("INTEGRATION_ID"));
					CAN = rset.getString("INTEGRATION_ID");
					TestRun.updateStatus(TestResultStatus.PASS, "querySiebelDBforCANUsingAccountName passed");
					break;
				}
				if (count++ > 50)
				{
					TestRun.updateStatus(TestResultStatus.FAIL);
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "ACCOUNT: \""+accountNameStr+"\" did not sync to Siebel");
					break;
				}

				sLog.info("querySiebelDBforCANUsingAccountName() Waiting for db output" + count);
				System.out.println("querySiebelDBforCANUsingAccountName() " + count);
				Thread.sleep(5000);
			}
			connection.close();

		}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			sLog.info(separator + "Exception occurred while trying to connect to the Siebel Database for validation:");
		}
		finally
		{
			if (startTest)
			{
				TestRun.endTest();
				TestRun.endSuite();
			}
		}

		return CAN;
	}


	/**
	 * Siebel DB Validation: Verifies Order Status using Order Number
	 * @param dbUserName  Siebel DB username 
	 * @param dbPassword  Siebel DB password 
	 * @param dbUrl Siebel DB URL 
	 * @param orderNumber OrderNumber which got generated on estore UI after placing an order
	 * @param expectedStatus excpected Order Status in Siebel DB after placing an order (i.e Complete)
	 */
	private HashMap<String, Object>  querySiebelDBforOrderStatusUsingOrderNumber(Object dbUserName, Object dbPassword, Object dbUrl, 
			Object orderNumber, Object expectedStatus, HashMap<String,Object> outputMap)
			{

		SiebelDBQueryForOrder siebelDatabaseUtil = new SiebelDBQueryForOrder();

		return siebelDatabaseUtil.queryOrderStatusByOrderNumber(dbUserName, dbPassword, dbUrl, orderNumber, expectedStatus, outputMap);
			}

	/**
	 * MDM DB Validation: Verifies Account Name(firm name) using CAN
	 * @param dbUserName MDM DB username
	 * @param dbPassword Estore DB password
	 * @param dbUrl Estore DB URL
	 * @param AccountName Name of the firm
	 * @param CANSiebelDB Customer account number which was obtained from Siebel DB
	 * @param orderNumber  OrderNumber which got generated on estore UI after placing an order
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	private void queryMDMDBToVerifyAccountNameUsingCAN(Object dbUserName, Object dbPassword, Object dbUrl, Object AccountName, Object CANSiebelDB, Selenium selenium,String maxPageLoadInMs )
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String CANSiebelDBStr = (String)CANSiebelDB;

		String query = "";
		sLog = Logger.getInstance();
		String expectedAccountName = "";

		TestRun.startSuite("Query MDM DB to verify Account name By CAN");
		TestRun.startTest("Validating CAN Sync to MDM DB");


		try 
		{
			siebelDatabaseUtil = new DatabaseUtil();

			estoreDatabaseUtil = new SQLServerDatabaseUtil();
			connection = estoreDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);

			query = "select * from e2e_mdm_customer_account where mdm_account_id='"+CANSiebelDBStr+"'";


			ResultSet rset;
			Statement mStatement;
			mStatement = connection.createStatement();
			int count = 0;

			while (true)
			{
				rset = mStatement.executeQuery(query);
				System.out.println("Result" + rset);

				boolean resultIsPresent = rset.next();

				if(resultIsPresent)
				{
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "CANSiebelDB: \""+CANSiebelDBStr+"\" is successfully synched to MDM DB.");
					sLog.info(separator + "AccountName: "+rset.getString("name"));
					expectedAccountName = rset.getString("name");
					Assert.assertEquals("The firm name displayed on the account management page doesn't match with that stored in MDMDB", expectedAccountName, AccountName);
					TestRun.updateStatus(TestResultStatus.PASS, "queryMDMDBToVerifyAccountNameUsingCAN passed");
					break;
				}

				if (count++ > 50)
				{
					TestRun.updateStatus(TestResultStatus.FAIL);
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "CANSiebelDB: \""+CANSiebelDBStr+"\" did not sync to MDM DB");
					break;
				}
				sLog.info("queryMDMDBToVerifyAccountNameUsingCAN() Waiting for db output" + count);
				System.out.println("queryMDMDBToVerifyAccountNameUsingCAN() " + count);
				Thread.sleep(5000);
			}
			connection.close();
		}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			sLog.info(separator + "Exception occurred while trying to connect to the MDM Database for validation:");
		} 
		finally
		{

			TestRun.endTest();
			TestRun.endSuite();
		}


	}


	/**
	 * Profile DB Validation: Verifies Order Number using CAN
	 * @param dbUserName Profile DB username
	 * @param dbPassword Estore DB password
	 * @param dbUrl Estore DB URL
	 * @param AccountName Name of the firm
	 * @param CANSiebelDB Customer account number which was obtained from Siebel DB
	 * @param orderNumber  OrderNumber which got generated on estore UI after placing an order
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	private void queryProfileDBforOrderNumberUsingCAN(Object dbUserName, Object dbPassword, Object dbUrl, Object CANSiebelDB, String orderNumber, Selenium selenium,String maxPageLoadInMs )
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String CANSiebelDBStr = (String)CANSiebelDB;

		String query = "";
		sLog = Logger.getInstance();
		String expectedOrderNumber = "";

		TestRun.startSuite("Query Profile DB to verify Order Number By CAN");
		TestRun.startTest("Validating CAN Sync to Profile DB");

		try 
		{
			estoreDatabaseUtil = new SQLServerDatabaseUtil();
			connection = estoreDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);

			query = "use profiledb select * from entitlement where customer_account_number='"+CANSiebelDBStr+"'";

			ResultSet rset;
			Statement mStatement;
			mStatement = connection.createStatement();
			int count = 0;

			while (true)
			{
				rset = mStatement.executeQuery(query);

				System.out.println("Result" + rset);

				boolean isOrderPresent = false;
				while(rset.next())
				{
					expectedOrderNumber = rset.getString("order_number");

					if (expectedOrderNumber.equals(orderNumber))

					{
						sLog.info(separator + "QUERY: " + query);
						sLog.info(separator + "CANSiebelDB: \""+CANSiebelDBStr+"\" is successfully synched to Profile DB.");
						sLog.info(separator + "OrderNumber: "+ rset.getString("order_number"));
						expectedOrderNumber = rset.getString("order_number");
						System.out.println("ExpectedOrderno:"+ expectedOrderNumber);

						Assert.assertEquals("OrderNumber displayed on Order Confirmation page doens't match with that stored in Prodfile db", 
								expectedOrderNumber, orderNumber);
						TestRun.updateStatus(TestResultStatus.PASS, "queryProfileDBforOrderNumberUsingCAN passed");
						isOrderPresent = true;
						break;
					}
				}

				if (isOrderPresent) break;

				if (count++ > 50) {	
					sLog.info("OrderNumber displayed on Order Confirmation page was not available in the MDM Database.");
					Assert.assertTrue(false);
					break;
				}
				sLog.info("queryProfileDBforOrderNumberUsingCAN() Waiting for db output" + count);
				System.out.println("queryProfileDBforOrderNumberUsingCAN() " + count);
				Thread.sleep(5000);
			}
			connection.close();
		}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			sLog.info(separator + "Exception occurred while trying to connect to the Profile Database for validation:");
		}
		finally
		{

			TestRun.endTest();
			TestRun.endSuite();
		}


	}


	/**
	 * Profile DB Validation: Verifies Tax Year using CAN
	 * @param dbUserName Profile DB username
	 * @param dbPassword Estore DB password
	 * @param dbUrl Estore DB URL
	 * @param CANSiebelDB Customer account number which was obtained from Siebel DB
	 * @param attibuteValue Expected Tax Year in entitlement atrribute's table after placing an order (2010/2011)
	 * @param orderNumber  OrderNumber which got generated on estore UI after placing an order
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	private void queryProfileDBToVerifyTaxYearUsingCAN(Object dbUserName, Object dbPassword, Object dbUrl, Object CANSiebelDB, Object attibuteValue, Selenium selenium, String maxPageLoadInMs)
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String CANSiebelDBStr = (String)CANSiebelDB;
		String attributeValueStr = (String)attibuteValue;

		String query = "";
		sLog = Logger.getInstance();
		String expectedAttributeValue = "";

		TestRun.startSuite("Query Profile DB to verify Tax year By CAN");
		TestRun.startTest("Validating CAN Sync to profile DB");

		try 
		{
			estoreDatabaseUtil = new SQLServerDatabaseUtil();
			connection = estoreDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);

			query = "use profiledb select  * from entitlement_item_attribute where entitlement_id in (select entitlement_id from entitlement " + 
					"where customer_account_number = '"+CANSiebelDBStr+"')";

			ResultSet rset;
			Statement mStatement;
			mStatement = connection.createStatement();
			int count = 0;

			while (true)
			{
				rset = mStatement.executeQuery(query);
				System.out.println("Result" + rset);

				boolean isAttributeValue = false;

				while(rset.next())
				{
					expectedAttributeValue = rset.getString("attribute_value");

					if(expectedAttributeValue.equals(attributeValueStr))
					{ 
						sLog.info(separator + "QUERY: " + query);
						sLog.info(separator + "CANSiebelDB: \""+CANSiebelDBStr+"\" is successfully synched to Profile DB.");
						sLog.info(separator + "expectedAttributeValue: "+ rset.getString("attribute_value"));
						Assert.assertEquals(attributeValueStr + "Tax Year was found in entitlement table", attributeValueStr, expectedAttributeValue );
						TestRun.updateStatus(TestResultStatus.PASS, "queryProfileDBToVerifyTaxYearUsingCAN passed");
						isAttributeValue = true;
						break;
					}
				}

				if (isAttributeValue) break;

				if (count++ > 50) { 
					sLog.info(attributeValueStr + "is not present in entitlement table of Profile Database.");
					Assert.assertTrue(false);
					break;
				}
				sLog.info("queryProfileDBToVerifyTaxYearUsingCAN() Waiting for db output" + count);
				System.out.println("queryProfilqueryProfileDBToVerifyTaxYearUsingCANeDBforOrderNumberUsingCAN() " + count);
				Thread.sleep(5000);
			}

			connection.close();

		}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			sLog.info(separator + "Exception occurred while trying to connect to the Profile Database for validation:");
		}
		finally
		{

			TestRun.endTest();
			TestRun.endSuite();
		}

	}


	/**
	 * MDM DB Validation: Verifies Customer Address using CAN
	 * @param dbUserName MDM DB username
	 * @param dbPassword Estore DB password
	 * @param dbUrl Estore DB URL
	 * @param address1, city, state, zipcode, country - Company address
	 * @param CANSiebelDB Customer account number which was obtained from Siebel DB
	 * @param startTest Boolean (True/False) used to decide whether to start the test or not 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	private void querymdmDBToVerifyAddressUsingCAN(Object dbUserName, Object dbPassword, Object dbUrl, Object address1, Object city,
			Object state, Object zipcode, Object country, Object CANSiebelDB, boolean startTest, Selenium selenium,String maxPageLoadInMs)
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String CANSiebelDBStr = (String)CANSiebelDB;
		String address1Str = (String)address1;
		String cityStr = (String)city;
		String stateStr = (String)state;
		String zipcodeStr = (String)zipcode;
		String countryStr = (String)country;


		String query = "";
		sLog = Logger.getInstance();

		if (startTest) {
			TestRun.startSuite("Query MDM DB to verify address By CAN");
			TestRun.startTest("Validating CAN Sync to MDM DB");
		}

		try 
		{
			siebelDatabaseUtil = new DatabaseUtil();

			estoreDatabaseUtil = new SQLServerDatabaseUtil();
			connection = estoreDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);

			query = "SELECT * FROM [MDM].[dbo].[mdm_address] where id in" +
					" (SELECT address_id FROM [MDM].[dbo].[e2e_mdm_customer_account_addresses_mapping] " +
					"where id in (SELECT id FROM [MDM].[dbo].[e2e_mdm_customer_account] where mdm_account_id = '"+CANSiebelDBStr+"'))";


			ResultSet rset;
			Statement mStatement;
			mStatement = connection.createStatement();
			int count = 0;

			while (true)
			{
				rset = mStatement.executeQuery(query);
				System.out.println("Result" + rset);

				boolean resultIsPresent = rset.next();

				if(resultIsPresent)
				{
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "CANSiebelDB: \""+CANSiebelDBStr+"\" is successfully synched to MDM DB.");

					String expectedOutput = rset.getString("streetAddress1").replaceAll("\\s+", " ");
					sLog.info(separator + "streetAddress1: "+ expectedOutput);
					Assert.assertEquals( "streetAddress1 does not match", expectedOutput, address1Str);				

					expectedOutput = rset.getString("city");
					sLog.info(separator + "city: "+ expectedOutput);
					Assert.assertEquals( "city does not match", expectedOutput, cityStr);				

					expectedOutput = rset.getString("state");
					sLog.info(separator + "state: "+ expectedOutput);
					Assert.assertEquals( "state does not match", expectedOutput, stateStr);

					expectedOutput = rset.getString("postal_code");
					sLog.info(separator + "postal_code: "+ expectedOutput);
					Assert.assertEquals( "zipcode does not match", expectedOutput, zipcodeStr);				

					expectedOutput = rset.getString("country");
					sLog.info(separator + "country: "+ expectedOutput);
					Assert.assertEquals( "country does not match", expectedOutput, countryStr);		
					TestRun.updateStatus(TestResultStatus.PASS, "querymdmDBToVerifyAddressUsingCAN passed");

					break;
				}
				if (count++ > 50)
				{
					TestRun.updateStatus(TestResultStatus.FAIL);
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "CANSiebelDB: \""+CANSiebelDBStr+"\" did not sync to MDM DB");
					break;
				}

				sLog.info("querymdmDBToVerifyAddressUsingCAN() Waiting for db output" + count);
				System.out.println("querymdmDBToVerifyAddressUsingCAN() " + count);
				Thread.sleep(5000);
			}
			connection.close();
		}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			sLog.info(separator + "Exception occurred while trying to connect to the MDM Database for validation:");
		}
		finally
		{

			if (startTest)
			{
				TestRun.endTest();
				TestRun.endSuite();
			}
		}
	}


	/**
	 * MDM DB Validation: Verifies Customer Contact info using CAN
	 * @param dbUserName MDM DB username
	 * @param dbPassword Estore DB password
	 * @param dbUrl Estore DB URL
	 * @param firstName, lastname, emailAddress - Contact information
	 * @param CANSiebelDB Customer account number which was obtained from Siebel DB
	 * @param startTest Boolean (True/False) used to decide whether to start the test or not 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	private void querymdmDBToVerifyContactInfoUsingCAN(Object dbUserName, Object dbPassword, Object dbUrl, Object firstName, Object lastName,
			Object emailAddress, Object CANSiebelDB, boolean startTest, Selenium selenium,String maxPageLoadInMs)
	{
		//Required conversion of parameters
		String dbUserNameStr = (String)dbUserName;
		String dbPasswordStr = (String)dbPassword;
		String dbUrlStr = (String)dbUrl;
		String CANSiebelDBStr = (String)CANSiebelDB;
		String firstNameStr = (String) firstName;
		String lastNameStr = (String) lastName;
		String emailAddressStr = (String) emailAddress;

		String query = "";
		sLog = Logger.getInstance();

		if (startTest)
		{
			TestRun.startSuite("Query MDM DB to verify contact Info By CAN");
			TestRun.startTest("Validating CAN Sync to MDM DB");
		}

		try 
		{
			siebelDatabaseUtil = new DatabaseUtil();

			estoreDatabaseUtil = new SQLServerDatabaseUtil();
			connection = estoreDatabaseUtil.createDatabaseConnection(dbUserNameStr, dbPasswordStr, dbUrlStr);

			query = "select * from e2e_mdm_contact where id in " +
					"(select contact_id from dbo.e2e_mdm_customer_account_contacts_mapping " + 
					"where id in (select id from e2e_mdm_customer_account where mdm_account_id  = '"+CANSiebelDBStr+"'))";

			ResultSet rset;
			Statement mStatement;
			mStatement = connection.createStatement();
			int count = 0;

			while (true)
			{
				rset = mStatement.executeQuery(query);
				System.out.println("Result" + rset);

				boolean resultIsPresent = rset.next();


				if (resultIsPresent)
				{
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "CANSiebelDB: \""+ CANSiebelDBStr + "\" is successfully synched to MDM DB.");

					String expectedOutput = rset.getString("first_name");
					sLog.info(separator + "first_name: "+ expectedOutput);

					Assert.assertEquals( "first_name does not match", expectedOutput, firstNameStr);				

					expectedOutput = rset.getString("last_name");
					sLog.info(separator + "last_name: "+ expectedOutput);
					Assert.assertEquals( "last_name does not match", expectedOutput, lastNameStr);				

					expectedOutput = rset.getString("email");
					sLog.info(separator + "email: "+ expectedOutput);
					Assert.assertEquals( "email address does not match", expectedOutput.trim(), emailAddressStr.trim());
					TestRun.updateStatus(TestResultStatus.PASS, "querymdmDBToVerifyContactInfoUsingCAN passed");
					break;
				}
				if (count++ > 50)
				{
					TestRun.updateStatus(TestResultStatus.FAIL);
					sLog.info(separator + "QUERY: " + query);
					sLog.info(separator + "CANSiebelDB: \""+CANSiebelDBStr+"\" did not sync to MDM DB");
					break;
				}
				sLog.info("querymdmDBToVerifyContactInfoUsingCAN() Waiting for db output" + count);
				System.out.println("querymdmDBToVerifyContactInfoUsingCAN() " + count);
				Thread.sleep(5000);
			}
			connection.close();	
		}
		catch (Exception e) 
		{
			TestRun.updateStatus(TestResultStatus.INCOMPLETE);
			sLog.info(separator + "QUERY: " + query);
			sLog.info(separator + "Exception occurred while trying to connect to the MDM Database for validation:");
		}
		finally
		{

			if (startTest)
			{
				TestRun.endTest();
				TestRun.endSuite();
			}		
		}
	}
}

