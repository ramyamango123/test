package initiate.estore.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestResultStatus;
import com.intuit.taf.testing.TestRun;
import com.thoughtworks.selenium.Selenium;
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
	public void launchOfferingPage(Selenium selenium,String estoreURL,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Launch Offering Page");
			
			//Opens the web page and waits for it to load 
			selenium.open(estoreURL+"/commerce/checkout/demo/items_offerings.jsp");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
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
	 * @param outputMap
	 * @return Output Map with the value for the following key ("ItemsList"). This is an ArrayList of type common.e2e.objects.Item
	 */
	public HashMap<String,Object> addOfferingToShoppingCart(Selenium selenium,String itemString,String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add Offering to Shopping Cart");
			
			//Selects a specific offering from the dropdown menu
			selenium.select("id=skus", "label="+itemString);
			
			//Add ItemsList to OutputMap
			Item item = new Item();
			item.setName(itemString.substring(0,itemString.indexOf("|")).trim().replace(".", ""));
			item.setNumber(itemString.substring(itemString.indexOf("|")+1).trim());
			lineItemsList.add(item);
			outputMap.put("ItemsList", lineItemsList);
			
			
			Thread.sleep(10000L);
			selenium.click("id=submit");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			//Checks that the shopping cart page was loaded successfully
			if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
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
		
		return outputMap;
	}
	
	/**
	 * Launches the Estore offering page for bundles and verifies that the page has been loaded properly.
	 * 
	 * @param selenium Default Selenium 
	 * @param item Bundle Item that's selected
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */

	public HashMap<String,Object> addBundleOfferingToShoppingCart(Selenium selenium,String item,String maxPageLoadInMs, HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Add Offering to Shopping Cart");
			
			selenium.click("link=Please click here to go back to CPC items");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.select("name=sku", "label="+item);
			Thread.sleep(10000);
			
			selenium.click("id=cpcInstance");
			Item itemToAddToMap = new Item();
			itemToAddToMap.setName(item.substring(0,item.indexOf("|")).trim().replace(".", ""));
			itemToAddToMap.setNumber(item.substring(item.indexOf("|")+1).trim());
			lineItemsList.add(itemToAddToMap);
			outputMap.put("ItemsList", lineItemsList);
			
			
			selenium.click("id=buttonFormSubmit");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			
			if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
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
	 * Signs into an existing customer account 
	 * 
	 * @param selenium Default Selenium 
	 * @param userId UserId used to sign into an existing account
	 * @param password Password used to sign into an existing account
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void signIn(Selenium selenium,String userId,String password,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Sign In Existing User");
			
			selenium.type("id=userid",userId);
			selenium.type("id=formValuesPassword",password);
			selenium.click("id=buttonLogin");
			selenium.waitForPageToLoad(maxPageLoadInMs);
				
			if(selenium.getLocation().toLowerCase().contains("company_shipping.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"The company shipping page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL,"The company shipping page did not succesfully load.");
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
	 * @return Output Map with the value for the following key ("AccountName") 
	 */
	public HashMap<String,Object> populateNewCompanyInformation(Selenium selenium,String userFirstName,String userLastName,String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Populate New Company Information");
			
			int randomNumber = new Double(Math.random()*10000000).intValue();
			HashMap<String,String> addressMap = buildAddress(randomNumber);
			String accountName = "E2EAutomated" + randomNumber;
			outputMap.put("AccountName", accountName);
			
			selenium.type("id=accountName", accountName);
			selenium.type("id=streetAddress",addressMap.get("streetAddress"));
			selenium.type("id=city",addressMap.get("city"));
			selenium.select("id=stateSelect", "label="+addressMap.get("state"));
			selenium.type("id=postalCode",addressMap.get("postalCode"));
			selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
			selenium.type("id=firstName",userFirstName);
			selenium.type("id=lastName",userLastName);
			selenium.type("id=userName","E2EAutomated" + randomNumber);
			selenium.type("id=password","password123");
			selenium.type("id=confirmPassword","password123");
			selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
			selenium.type("id=securityAnswer","fido");
			selenium.click("id=createCustomerAccount");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			
			
			//Add the address information into the output map
			outputMap.put("streetAddress", addressMap.get("streetAddress"));
			outputMap.put("city", addressMap.get("city"));
			outputMap.put("state", addressMap.get("state"));
			outputMap.put("zip", addressMap.get("postalCode"));
			outputMap.put("country", "USA");
			
			
			if(selenium.getLocation().toLowerCase().contains("company_shipping.jsp"))
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
			
			selenium.click("xpath=//input[contains(@class, 'ontinue')]");
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
	 * Launches the Existing Payment Method Page (assumes the current page is company_shipping.jsp) 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void launchExistingPaymentMethodPage(Selenium selenium,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Launch Existing Payment Method Page");
			
			selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(selenium.getLocation().toLowerCase().contains("payment_method.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The Existing Payment Method Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Existing Payment Method Page did not successfully load.");
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
	 * Chooses the default payment method (credit card or EFT) for an existing customer account
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 */
	public void selectExistingPaymentProfile(Selenium selenium,String maxPageLoadInMs)
	{
		try
		{
			TestRun.startTest("Select Existing Payment Profile");
			
			//Chooses the first payment profile and populates 
			//the CVV if it is an Credit Card profile
			
			if(selenium.isElementPresent("id=cvv1"))
			{
				selenium.click("id=paymentProfileId");
				selenium.type("id=cvv1", "123");
				//Need to explicitly kick off the javascript blur event
				selenium.fireEvent("id=cvv1", "blur");
			}
			else
			{
				selenium.click("id=paymentType");
			}
			
			//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			selenium.click("id=continueCheckOutFromPayment");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS, "The Shopping Cart Review Page has successfully loaded.");
			}
			else
			{
				TestRun.updateStatus(TestResultStatus.FAIL, "The Shopping Cart Review Page did not successfully load.");
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
	 * Populates the credit card payment information for a new Billing Profile 
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param creditCardNumber Credit Card Number
	 * @param creditCardMonth Credit Card Expiration Month
	 * @param creditCardYear Credit Card Expiration Year
	 */
	public void populateCreditCardPaymentInformation(Selenium selenium,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear)
	{
		try
		{
			TestRun.startTest("Populate Credit Card Payment Information");
			
			selenium.type("id=creditCardNumber", creditCardNumber);
			selenium.focus("id=creditCardNumber");
			//The native key press (TAB) is necessary in order to identify the correct credit card type
			selenium.keyPressNative("9");
			switch(Integer.parseInt(creditCardMonth))
			{
				case 1: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
				case 2: selenium.select("id=dropdownCreditCardExpirationMM", "value=02"); break;
				case 3: selenium.select("id=dropdownCreditCardExpirationMM", "value=03"); break;
				case 4: selenium.select("id=dropdownCreditCardExpirationMM", "value=04"); break;
				case 5: selenium.select("id=dropdownCreditCardExpirationMM", "value=05"); break;
				case 6: selenium.select("id=dropdownCreditCardExpirationMM", "value=06"); break;
				case 7: selenium.select("id=dropdownCreditCardExpirationMM", "value=07"); break;
				case 8: selenium.select("id=dropdownCreditCardExpirationMM", "value=08"); break;
				case 9: selenium.select("id=dropdownCreditCardExpirationMM", "value=09"); break;
				case 10: selenium.select("id=dropdownCreditCardExpirationMM", "value=10"); break;
				case 11: selenium.select("id=dropdownCreditCardExpirationMM", "value=11"); break;
				case 12: selenium.select("id=dropdownCreditCardExpirationMM", "value=12"); break;
				default: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
			}
			
			selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
			selenium.type("id=creditCardHolderName", "TestCC");
			selenium.type("id=creditCardCSC", "123");
			selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
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
	 * 
	 * @param selenium Default Selenium 
	 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
	 * @param accountType Account Type (Checking or Savings)
	 * @param accountNumber Bank Account Number
	 * @param routingNumber Bank Routing Number
	 */
	public void populateEFTPaymentInformation(Selenium selenium,String maxPageLoadInMs,String accountType,String accountNumber,String routingNumber)
	{
		try
		{
			TestRun.startTest("Populate EFT Information");
			
			selenium.click("id=radioPaymentEFT");
			selenium.type("id=accountName", "TestEFT");
			selenium.select("id=dropdownAccountType", "value="+accountType);
			selenium.type("id=routingNumber",routingNumber);
			selenium.type("id=accountNumber",accountNumber);
			selenium.type("id=confirmAccountNumber",accountNumber);
			selenium.focus("id=confirmAccountNumber");
			//The native key press (TAB) is necessary in order to identify the correct credit card type
			selenium.keyPressNative("9");
			selenium.click("xpath=//input[contains(@class, 'ontinue')]");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
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
	public HashMap<String,Object> submitOrder(Selenium selenium,String maxPageLoadInMs,HashMap<String,Object> outputMap)
	{
		try
		{
			TestRun.startTest("Submit The Order");
			
			selenium.click("id=cart-place-order-button");
			selenium.waitForPageToLoad(maxPageLoadInMs);
			
			String currentPageBodyText = selenium.getBodyText();
			String orderNumber = currentPageBodyText.substring(currentPageBodyText.indexOf("Order Number:")+14);
			orderNumber = orderNumber.substring(0,orderNumber.indexOf('\n'));
			outputMap.put("OrderNumber", orderNumber);
			
			if(selenium.getLocation().toLowerCase().contains("cart_confirmation.jsp"))
			{
				TestRun.updateStatus(TestResultStatus.PASS,"OrderNumber: {"+ orderNumber+"}");
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
		public void cleanExistingUserShoppingCartItems(Selenium selenium,String estoreURL,String userId,String password,String maxPageLoadInMs)
		{
			try
			{
				TestRun.startTest("Existing User Accounts Items Clean up page ");
				
				//Opens the web page and waits for it to load 
				selenium.open(estoreURL+"/commerce/checkout/secure/estore/signin.jsp");
				Thread.sleep(500L);
				selenium.type("id=userid",userId);
				selenium.type("id=formValuesPassword",password);
				if(selenium.isElementPresent("id=buttonLogin")==true)
				{
				selenium.click("id=buttonLogin");
				selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				
				selenium.open(estoreURL+"/commerce/e2e/cart/shopping_cart.jsp");
				selenium.waitForPageToLoad(maxPageLoadInMs);
				while (selenium.isElementPresent("css=img.space-bottom-10")==true)
					{
						selenium.click("css=img.space-bottom-10");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						Thread.sleep(10000L);
					}
				if(selenium.isElementPresent("link=Sign Out")==true)
					{
					selenium.click("link=Sign Out");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					}
				
				//Checks that the offering page was loaded successfully
				if(selenium.getLocation().toLowerCase().contains("login.jsp") || selenium.getLocation().toLowerCase().contains("signin.jsp"))
				{
					TestRun.updateStatus(TestResultStatus.PASS,"Existing user shopping cart items successfully  deleted.");
				}
				else
				{
					TestRun.updateStatus(TestResultStatus.FAIL,"Existing user shopping cart items not successfully  deleted.");
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
			 * Create a new account and place order and the same account will be used as Existing Account with Billing profile  
			 * 
			?" * @param userFirstName User First Name
			
			
			
			
			
			
			
			"}}}}}}}}}}}}}}}}}
			 * @param userLastName User Last Name
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param outputMap Map that will be populated with important information
			 * @return Output Map with the value for the following key ("AccountName" , SignInUsername, SignInPassord) 
			 */
			public HashMap<String,Object> CreateNewUserAccount_Use_As_ExistingAccount(Selenium selenium,String ExistingActEstoreURL,String userFirstName,String userLastName,String maxPageLoadInMs,String CompanyAddress,HashMap<String,Object> outputMap)
			{
				try
				{
					//TestRun.startTest("Populate New Guest Company Information");
					//Date dNow = new Date();
					TestRun.startTest("Creating New User Account To use as Existing account");
					selenium.open(ExistingActEstoreURL);
					Thread.sleep(5000L);
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					java.util.Date dNow = new java.util.Date();
					SimpleDateFormat ft =   new SimpleDateFormat ("yyyyMMddhhmmss"); //E yyyyMMddhhmmss
				   	String NewComapanyName=(ft.format(dNow));
				    
					int randomNumber = new Double(Math.random()*10000000).intValue();
					HashMap<String,String> addressMap = buildAddress(randomNumber);
					String accountName = "E2E_" + NewComapanyName;
					outputMap.put("AccountName", accountName);
					//selenium.type("id=accountName", accountName);
					// Sign In Page continue as a Guest.
					selenium.type("id=newUserEmail",accountName+"@intuit.com");
					if (selenium.isElementPresent("id=buttonContinueAsGuest")==true)
					{
					selenium.click("id=buttonContinueAsGuest");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					
					selenium.type("id=accountName", accountName);
					if (CompanyAddress.equals("Company_StandardUSA")){
						System.out.println("Inside Company USA Address");
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress",addressMap.get("streetAddress"));
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label="+addressMap.get("state"));
						selenium.type("id=postalCode",addressMap.get("postalCode"));
					}else if (CompanyAddress.equals("Company_POBox_USA")){
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress","PO Box# "+NewComapanyName);
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label="+addressMap.get("state"));
						selenium.type("id=postalCode",addressMap.get("postalCode"));
					}else if (CompanyAddress.equals("Company_APOBox_USA")){
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress","FPO Base# "+NewComapanyName);
						selenium.type("id=city","FPO");
						selenium.select("id=stateSelect", "label=AE");
						selenium.type("id=postalCode","09409");
					}else if (CompanyAddress.equals("Company_StandardCANADA")){
						selenium.select("id=countrySelect", "label=Canada");
						selenium.type("id=streetAddress","2210 Bank St");
						selenium.type("id=city","Ottawa");
						selenium.select("id=stateSelect", "label=ON");
						selenium.type("id=postalCode","K1V 1J5");
					}else if (CompanyAddress.equals("Company_POBox_CANADA")){
						selenium.select("id=countrySelect", "label=Canada");
						selenium.type("id=streetAddress","PO Box #"+NewComapanyName);
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label=ON");
						selenium.type("id=postalCode","K1V 1J5");
					}else if(CompanyAddress.equals("Company_USPossession_USA")){
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress","1501 Ponce de Leon");
						selenium.type("id=city","Santurce");
						selenium.select("id=stateSelect", "label=PR");
						selenium.type("id=postalCode","00907");
					}	
					selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
					selenium.type("id=firstName",userFirstName);
					selenium.type("id=lastName",userLastName);
					if (selenium.isElementPresent("id=createLogin")==true)
					{
						selenium.click("id=createLogin");
					}
					
					String ExistingUserName = accountName;
					selenium.type("id=userName", ExistingUserName);
					outputMap.put("SignInUsername", ExistingUserName);
					String ExistingUserPassword = "password123";
					selenium.type("id=password",ExistingUserPassword);
					outputMap.put("SignInPassord", ExistingUserPassword);
					selenium.type("id=confirmPassword",ExistingUserPassword);
					selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
					selenium.type("id=securityAnswer","fido");
					if (selenium.isElementPresent("id=updateCompanyInfo")==true)
						{
						selenium.click("id=updateCompanyInfo");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					
					if (selenium.isElementPresent("id=updateCompanyInfo")==true)
					{
						selenium.click("id=updateCompanyInfo");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					if (selenium.isElementPresent("link=Sign Out")==true)
					{
						selenium.click("link=Sign Out");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					if(selenium.getLocation().toLowerCase().contains("signin.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The Create new user Account SignIn Page successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The Create new user Account SignIn Page did not successfully load.");
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


			public HashMap<String,Object> addDownload_ReInstallOfferingToShoppingCart(Selenium selenium,String item,String maxPageLoadInMs,HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Add Offering to Shopping Cart");
					
					//Selects a specific offering from the dropdown menu
					selenium.select("id=skus", "label="+item);
					Thread.sleep(5000L);
					if (selenium.isElementPresent("css=#attrib1")==true)
					{
							selenium.select("css=#attrib1", "label=Download with Reinstall");
							//selenium.waitForPageToLoad(maxPageLoadInMs);
							Thread.sleep(5000L);
					}
					if (selenium.isElementPresent("id=submit")==true)
					{
					selenium.click("id=submit");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					Thread.sleep(5000L);
					}
					//Checks that the shopping cart page was loaded successfully
					if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
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
				return outputMap;
			}


			public HashMap<String,Object> addPayRoll_3EmployeesOfferingToShoppingCart(Selenium selenium,String item,String maxPageLoadInMs,HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Add Offering to Shopping Cart");
					
					//Selects a specific offering from the dropdown menu
					selenium.select("id=skus", "label="+item);
					Thread.sleep(10000);
					if (selenium.isElementPresent("id=attrib0")==true)
					{
						System.out.println(selenium.getValue("id=attribName0"));
							selenium.select("id=attrib0", "label=Up to 3 Employees");
							//selenium.waitForPageToLoad(maxPageLoadInMs);
							Thread.sleep(5000L);
					}
					selenium.click("id=submit");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					
					//Checks that the shopping cart page was loaded successfully
					if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
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
				return outputMap;
			}

			/**
			 * Signs into an existing customer account 
			 * 
			 * @param selenium Default Selenium 
			 * @param userId UserId used to sign into an existing account
			 * @param password Password used to sign into an existing account
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 */
			public HashMap<String,Object>  ExistingUsersignIn(Selenium selenium,String maxPageLoadInMs, HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Sign In with Existing User Credentails");
					
					selenium.type("id=userid",(String)outputMap.get("SignInUsername"));
					selenium.type("id=formValuesPassword",(String)outputMap.get("SignInPassord"));
					selenium.click("id=buttonLogin");
					selenium.waitForPageToLoad(maxPageLoadInMs);
						
					if(selenium.getLocation().toLowerCase().contains("company_shipping.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The company shipping page has successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The company shipping page did not succesfully load.");
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
			public HashMap<String,Object> populatCD_Hardware_NewCompanyInformation(Selenium selenium,String userFirstName,String userLastName,String maxPageLoadInMs,String companyAddress,HashMap<String,Object> outputMap)
			{
				try
				{
					//TestRun.startTest("Populate New Guest Company Information");
					
					TestRun.startTest("Populate New Company Information to Create new Company");
					
					int randomNumber = new Double(Math.random()*10000000).intValue();
					HashMap<String,String> addressMap = buildAddress(randomNumber);
					//String accountName = "E2EAutomated" + randomNumber;
					java.util.Date dNow = new java.util.Date();
					SimpleDateFormat ft =   new SimpleDateFormat ("yyyyMMddhhmmss"); //E yyyyMMddhhmmss
				   	String newComapanyName=(ft.format(dNow));
				   	String accountName ="E2EAutomated_"+newComapanyName;
				   	
					outputMap.put("AccountName", accountName);
					//selenium.type("id=accountName", accountName);
					selenium.type("id=accountName", accountName);
					if (("Company_StandardUSA").equals(companyAddress))
						{
						System.out.println("Inside Company USA Address");
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress",addressMap.get("streetAddress"));
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label="+addressMap.get("state"));
						selenium.type("id=postalCode",addressMap.get("postalCode"));
						
						outputMap.put("companyStreetAddress", addressMap.get("streetAddress"));
						outputMap.put("companyCity", addressMap.get("city"));
						outputMap.put("companyState", addressMap.get("state"));
						outputMap.put("companyZip", addressMap.get("postalCode"));
						outputMap.put("companyCountry", "USA");
						
						}
					else if (("Company_POBox_USA").equals(companyAddress))
						{
						String companyStreetAddress ="PO Box# "+newComapanyName;
						
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress",companyStreetAddress);
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label="+addressMap.get("state"));
						selenium.type("id=postalCode",addressMap.get("postalCode"));
						
						
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
						
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress",companyStreetAddress);
						selenium.type("id=city",companyCity);
						selenium.select("id=stateSelect", "label="+companyState);
						selenium.type("id=postalCode",companyZip);
						
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
						
						selenium.select("id=countrySelect", "label=Canada");
						selenium.type("id=streetAddress",companyStreetAddress);
						selenium.type("id=city",companyCity);
						selenium.select("id=stateSelect", "label="+companyState);
						selenium.type("id=postalCode",companyZip);
						
						
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
						
						selenium.select("id=countrySelect", "label=Canada");
						selenium.type("id=streetAddress",companyStreetAddress);
						selenium.type("id=city",companyCity);
						selenium.select("id=stateSelect", "label="+companyState);
						selenium.type("id=postalCode",companyZip);
						
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
						
						
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress",companyStreetAddress);
						selenium.type("id=city",companyCity);
						selenium.select("id=stateSelect", "label="+companyState);
						selenium.type("id=postalCode",companyZip);
						
						outputMap.put("companyStreetAddress", companyStreetAddress);
						outputMap.put("companyCity",companyCity);
						outputMap.put("companyState", companyState);
						outputMap.put("companyZip", companyZip);
						outputMap.put("companyCountry", companyCountry);
						
						}	
						selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
						selenium.type("id=firstName",userFirstName);
						selenium.type("id=lastName",userLastName);
						
					if (selenium.isElementPresent("id=createLogin")==true)
						{
						selenium.click("id=createLogin");
						}
					
						String existingUserName = accountName;
						selenium.type("id=userName", existingUserName);
						outputMap.put("signInUsername", existingUserName);
						String existingUserPassword = "password123";
						selenium.type("id=password",existingUserPassword);
						outputMap.put("signInPassord", existingUserPassword);
						selenium.type("id=confirmPassword",existingUserPassword);
						selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
						selenium.type("id=securityAnswer","fido");
						

						
					
					if (selenium.isElementPresent("id=createLogin")==true)
						{
						selenium.click("id=createLogin");
						}
					
					if (selenium.isElementPresent("id=updateCompanyInfo")==true)
						{
						selenium.click("id=updateCompanyInfo");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					
					if (selenium.isElementPresent("id=updateCompanyInfo")==true)
						{
						selenium.click("id=updateCompanyInfo");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
						System.out.println("companyStreetAddress  "+outputMap.get("companyStreetAddress"));
						System.out.println("companyCity  "+outputMap.get("companyCity"));
						System.out.println("companyState  "+outputMap.get("companyState"));
						System.out.println("companyZip  "+outputMap.get("companyZip"));
						System.out.println("companyCountry  "+outputMap.get("companyCountry"));
						
						
					if(selenium.getLocation().toLowerCase().contains("company_shipping.jsp"))
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
			* Populates the new customer information 
				 * 
				 * @param selenium Default Selenium
				 * @param userFirstName User First Name
				 * @param userLastName User Last Name
				 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
				 * @param outputMap Map that will be populated with important information
				 * @return Output Map with the value for the following key ("AccountName") 
				 */
			public HashMap<String,Object> populateCD_Hardware_NewShippingInformation(Selenium selenium,String userFirstName,String userLastName,String maxPageLoadInMs, String shipAddressType,String shipType, HashMap<String,Object> outputMap)
				{
					try
					{
						TestRun.startTest("Populate New Shipping Information with New Shipping Address");
						
							int randomNumber = new Double(Math.random()*10000000).intValue();
							HashMap<String,String> addressMap = buildAddress(randomNumber);
							
						if (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
							{
							selenium.click("id=shippingAddressSameAsCompanyAddress");
							}
						if (selenium.isElementPresent("link=Add address")==true)
							{
							selenium.click("link=Add address");
							}
						
						if (("Ship_Normal").equals(shipType))
						 	{	
							if (("Shipping_StandardUSA").equals(shipAddressType)) 
								{
								selenium.select("id=shippingCountrySelect", "label=US");
								selenium.type("id=address1",addressMap.get("streetAddress"));
								selenium.type("id=city",addressMap.get("city"));
								selenium.select("id=shippingStateSelect", "label="+addressMap.get("state"));
								selenium.type("id=postalCode",addressMap.get("postalCode"));
								
								outputMap.put("shippingStreetAddress", addressMap.get("streetAddress"));
								outputMap.put("shippingCity", addressMap.get("city"));
								outputMap.put("shippingState", addressMap.get("state"));
								outputMap.put("shippingZip", addressMap.get("postalCode"));
								outputMap.put("shippingCountry", "USA");
								}
							else if(("Shipping_APOBox_USA").equals(shipAddressType))
								{
								selenium.select("id=shippingCountrySelect", "label=US");
								//String streetAddress ="APO Base#"+randomNumber;
								String shippingStreetAddress ="APO Base#"+randomNumber;
								String shippingCity ="APO";
								String shippingState ="AA";
								String shippingCountry ="US";
								String shippingZip="34002";
								
								selenium.type("id=address1",shippingStreetAddress);
								selenium.type("id=city",shippingCity);
								selenium.select("id=shippingStateSelect", "label="+shippingState);
								selenium.type("id=postalCode",shippingZip);
								
								outputMap.put("shippingStreetAddress", shippingStreetAddress);
								outputMap.put("shippingCity", shippingCity);
								outputMap.put("shippingState", shippingState);
								outputMap.put("shippingZip", shippingZip);
								outputMap.put("shippingCountry", shippingCountry);
								}
							else if( ("Shipping_POBox_USA").equals(shipAddressType))
								{
								selenium.select("id=shippingCountrySelect", "label=US");
								String streetAddress ="PO Box #"+randomNumber;
								selenium.type("id=address1",streetAddress);
								selenium.type("id=city",addressMap.get("city"));
								selenium.select("id=shippingStateSelect", "label="+addressMap.get("state"));
								selenium.type("id=postalCode",addressMap.get("postalCode"));
								
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
								
								selenium.select("id=shippingCountrySelect", "label=CANADA");
								selenium.type("id=address1",shippingStreetAddress);
								selenium.type("id=city",shippingCity);
								selenium.select("id=shippingStateSelect","label="+shippingState);
								selenium.type("id=postalCode",shippingZip);
								
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
								
								selenium.select("id=shippingCountrySelect", "label=CANADA");
								selenium.type("id=address1",shippingStreetAddress);
								selenium.type("name=city",shippingCity);
								selenium.select("id=shippingStateSelect", "label="+shippingState);
								selenium.type("id=postalCode",shippingZip);
								
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
								
								selenium.select("id=shippingCountrySelect", "label=US");
								selenium.type("id=address1",shippingStreetAddress);
								selenium.type("id=city",shippingCity);
								selenium.select("id=shippingStateSelect", "label="+shippingState);
								selenium.type("id=postalCode",shippingZip);
								
								
								outputMap.put("shippingStreetAddress", shippingStreetAddress);
								outputMap.put("shippingCity", shippingCity);
								outputMap.put("shippingState", shippingState);
								outputMap.put("shippingZip", shippingZip);
								outputMap.put("shippingCountry", shippingCountry);
								}
													
						if (selenium.isElementPresent("id=addNewShippingButton")==true)
							{
							selenium.click("id=addNewShippingButton");
							selenium.waitForPageToLoad(maxPageLoadInMs);
							}
						if (selenium.isElementPresent("id=standardizeAddressSelected2")==true)
							{
							selenium.click("id=standardizeAddressSelected2");
							selenium.click("id=addNewShippingButton");
							selenium.waitForPageToLoad(maxPageLoadInMs);
							}
						if (selenium.isElementPresent("id=selectShippingButton")==true)
							{
							selenium.click("id=selectShippingButton");
							selenium.waitForPageToLoad(maxPageLoadInMs);
							}	
						
						
						
						if(selenium.getLocation().toLowerCase().contains("payment_method_new_user.jsp") || selenium.getLocation().toLowerCase().contains("payment_method.jsp"))
							{
							TestRun.updateStatus(TestResultStatus.PASS,"The Payment Page has successfully loaded.");
							}
						else
						{
							TestRun.updateStatus(TestResultStatus.FAIL,"The Payment did not successfully load.");
						}
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
			* Populates the new customer information 
				 * 
				 * @param selenium Default Selenium
				 * @param userFirstName User First Name
				 * @param userLastName User Last Name
				 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
				 * @param outputMap Map that will be populated with important information
				 * @return Output Map with the value for the following key ("AccountName") 
				 */
			public HashMap<String,Object> populateCD_Hardware_New_3rdPartyShippingInfo(Selenium selenium,String userFirstName,String userLastName,String maxPageLoadInMs, String ShipAddressType,String ShipType, HashMap<String,Object> outputMap)
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
						//selenium.type("id=email",accountName+"@intuit.com");
						
						if (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
						{
						selenium.click("id=shippingAddressSameAsCompanyAddress");
						}
						if (selenium.isElementPresent("id=radioThirdPartyAddress")==true)
						{
						selenium.click("id=radioThirdPartyAddress");
						}
						
						if(("Ship_3rdParty").equals(ShipType))
						{
							selenium.type("id=accountName", "Ship"+accountName);
							
								if(("Shipping_StandardUSA").equals(ShipAddressType))
									{
									selenium.select("id=country", "label=US");
									selenium.type("id=third_party_streetAddress",addressMap.get("streetAddress"));
									selenium.type("name=city",addressMap.get("city"));
									selenium.select("id=state", "label="+addressMap.get("state"));
									selenium.type("name=postalCode",addressMap.get("postalCode"));
									
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
									
									selenium.select("id=country", "label=US");
									selenium.type("id=third_party_streetAddress",shippingStreetAddress);
									selenium.type("name=city",shippingCity);
									selenium.select("id=state", "label="+shippingState);
									selenium.type("name=postalCode",shippingZip);
									
									outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
									outputMap.put("shipping3rdPartyCity", shippingCity);
									outputMap.put("shipping3rdPartyState", shippingState);
									outputMap.put("shipping3rdPartyZip", shippingZip);
									outputMap.put("shipping3rdPartyCountry", shippingCountry);
									
									}
								else if(("Shipping_POBox_USA").equals(ShipAddressType))
									{
									selenium.select("id=country", "label=US");
									String streetAddress ="PO Box #"+randomNumber;
									selenium.type("id=third_party_streetAddress",streetAddress);
									selenium.type("name=city",addressMap.get("city"));
									selenium.select("id=state", "label="+addressMap.get("state"));
									selenium.type("name=postalCode",addressMap.get("postalCode"));
									
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
									
									selenium.select("id=country", "label=US");
									selenium.type("id=third_party_streetAddress",shippingStreetAddress);
									selenium.type("name=city",shippingCity);
									selenium.select("id=state", "label="+shippingState);
									selenium.type("name=postalCode",shippingZip);
									
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
									
									selenium.select("id=country", "label=CANADA");
									selenium.type("id=third_party_streetAddress",shippingStreetAddress);
									selenium.type("name=city",shippingCity);
									selenium.select("id=state", "label="+shippingState);
									selenium.type("name=postalCode",shippingZip);
									
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
									
									selenium.select("id=country", "label=CANADA");
									selenium.type("id=third_party_streetAddress",shippingStreetAddress);
									selenium.type("name=city",shippingCity);
									selenium.select("id=state", "label="+shippingState);
									selenium.type("name=postalCode",shippingZip);
									
									outputMap.put("shipping3rdPartyStreetAddress", shippingStreetAddress);
									outputMap.put("shipping3rdPartyCity", shippingCity);
									outputMap.put("shipping3rdPartyState", shippingState);
									outputMap.put("shipping3rdPartyZip", shippingZip);
									outputMap.put("shipping3rdPartyCountry", shippingCountry);
									
									
									}
									selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
									selenium.type("id=firstName",userFirstName);
									selenium.type("id=lastName",userLastName);
									selenium.type("id=email","Ship"+accountName+"@intuit.com");
									
									System.out.println("  shipping3rdPartyStreetAddress   "+outputMap.get("shipping3rdPartyStreetAddress"));
									System.out.println("  shipping3rdPartyCity   "+outputMap.get("shipping3rdPartyCity"));
									System.out.println("  shipping3rdPartyState   "+outputMap.get("shipping3rdPartyState"));
									System.out.println("  shipping3rdPartyZip   "+outputMap.get("shipping3rdPartyZip"));
									System.out.println("  shipping3rdPartyCountry   "+outputMap.get("shipping3rdPartyCountry"));
								}
							if (selenium.isElementPresent("id=thirdPartyShipping")==true)
									{
									selenium.click("id=thirdPartyShipping");
									selenium.waitForPageToLoad(maxPageLoadInMs);
									}
							if (selenium.isElementPresent("id=thirdPartyShipping")==true)
									{
									selenium.click("id=thirdPartyShipping");
									selenium.waitForPageToLoad(maxPageLoadInMs);
									}
										
							if (selenium.isElementPresent("id=selectShippingButton")==true)
							{
							selenium.click("id=selectShippingButton");
							selenium.waitForPageToLoad(maxPageLoadInMs);
							}	
						
							//selenium.waitForPageToLoad(maxPageLoadInMs);			
						
						if(selenium.getLocation().toLowerCase().contains("payment_method_new_user.jsp") || selenium.getLocation().toLowerCase().contains("payment_method.jsp"))
						{
							TestRun.updateStatus(TestResultStatus.PASS,"The Payment Page has successfully loaded.");
						}
						else
						{
							TestRun.updateStatus(TestResultStatus.FAIL,"The Payment did not successfully load.");
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
			public void launchPaymentMethod_SubmitCompanyPageMethodPage(Selenium selenium,String maxPageLoadInMs)
			{
				try
				{
					TestRun.startTest("Launch New Payment Method Page");
					if (selenium.isElementPresent("id=shippingButton")==true)
					{
					selenium.click("id=shippingButton");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					if (selenium.isElementPresent("id=buttonContinue")==true)
					{
						selenium.click("id=buttonContinue");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					System.out.println("Inside Payment page");
					
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
			 * Populates the credit card payment information for a new Billing Profile 
			 * 
			 * @param selenium Default Selenium 
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 * @param creditCardNumber Credit Card Number
			 * @param creditCardMonth Credit Card Expiration Month
			 * @param creditCardYear Credit Card Expiration Year
			 * 
			 */
			public HashMap<String,Object> populateCreditCard_NewBillingProfilePaymentInformation(Selenium selenium,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear,String billAddressType, HashMap<String,Object> outputMap)
			
			//public void populateCreditCard_NewBillingProfilePaymentInformation(Selenium selenium,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear)
			{
				try
				{
					TestRun.startTest("Populate Credit Card Payment with new Billing Address Information");
					
					selenium.type("id=creditCardNumber", creditCardNumber);
					selenium.focus("id=creditCardNumber");
					//The native key press (TAB) is necessary in order to identify the correct credit card type
					selenium.keyPressNative("9");
					switch(Integer.parseInt(creditCardMonth))
					{
						case 1: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
						case 2: selenium.select("id=dropdownCreditCardExpirationMM", "value=02"); break;
						case 3: selenium.select("id=dropdownCreditCardExpirationMM", "value=03"); break;
						case 4: selenium.select("id=dropdownCreditCardExpirationMM", "value=04"); break;
						case 5: selenium.select("id=dropdownCreditCardExpirationMM", "value=05"); break;
						case 6: selenium.select("id=dropdownCreditCardExpirationMM", "value=06"); break;
						case 7: selenium.select("id=dropdownCreditCardExpirationMM", "value=07"); break;
						case 8: selenium.select("id=dropdownCreditCardExpirationMM", "value=08"); break;
						case 9: selenium.select("id=dropdownCreditCardExpirationMM", "value=09"); break;
						case 10: selenium.select("id=dropdownCreditCardExpirationMM", "value=10"); break;
						case 11: selenium.select("id=dropdownCreditCardExpirationMM", "value=11"); break;
						case 12: selenium.select("id=dropdownCreditCardExpirationMM", "value=12"); break;
						default: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
					}
					
					selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
					selenium.type("id=creditCardHolderName", "TestCC");
					selenium.type("id=creditCardCSC", "123");
					
					
						if (selenium.isElementPresent("id=payment_shipping_address")==true)
						{
						selenium.click("id=payment_shipping_address");
						}
						if (selenium.isElementPresent("id=billingAddressSelect")==true)
						{
							selenium.select("id=billingAddressSelect", "label=Create New");
							//selenium.waitForPageToLoad(maxPageLoadInMs);
						}
						int randomNumber = new Double(Math.random()*10000000).intValue();
						HashMap<String,String> addressMap = buildAddress(randomNumber);
						//String accountName = "E2EAutomated" + randomNumber;
						//outputMap.put("AccountName", accountName);
						if (("Billing_StandardUSA").equals(billAddressType))
						{ 
							System.out.println("Inside USA Bill Address");
							selenium.select("id=billingCountrySelect", "label=US");
							selenium.type("id=address1", addressMap.get("streetAddress"));
							selenium.type("id=city", addressMap.get("city"));
							selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
							selenium.type("id=postalCode", addressMap.get("postalCode"));
							
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
							
							selenium.select("id=billingCountrySelect", "label=US");
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city", billingCity);
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode", billingZip);
							
							outputMap.put("billingStreetAddress", billingStreetAddress);
							outputMap.put("billingCity", billingCity);
							outputMap.put("billingState", billingState);
							outputMap.put("billingZip", billingZip);
							outputMap.put("billingCountry", "USA");
							
						}
						else if (("Billing_POBox_USA").equals(billAddressType))
						{ 
							selenium.select("id=billingCountrySelect", "label=US");
							String billingStreetAddress ="PO Box #"+randomNumber;
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city", addressMap.get("city"));
							selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
							selenium.type("id=postalCode", addressMap.get("postalCode"));
							
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
							
							selenium.select("id=billingCountrySelect", "label=CANADA");
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city",billingCity );
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode",billingZip);
							
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
							
							selenium.select("id=billingCountrySelect", "label=CANADA");
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city", billingCity);
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode", billingZip);
							
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
							
							selenium.select("id=billingCountrySelect", "label=US");
							selenium.type("id=address1", billingStreetAddress);
							selenium.type("id=city", billingCity);
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode",billingZip);
							
							outputMap.put("billingStreetAddress", billingStreetAddress);
							outputMap.put("billingCity", billingCity);
							outputMap.put("billingState", billingState);
							outputMap.put("billingZip", billingZip);
							outputMap.put("billingCountry", billingCountry);
						}
						System.out.println("billingStreetAddress " +outputMap.get("billingStreetAddress"));
						System.out.println("billingCity " +outputMap.get("billingCity"));
						System.out.println("billingState " +outputMap.get("billingState"));
						System.out.println("billingZip " +outputMap.get("billingZip"));
						System.out.println("billingCountry " +outputMap.get("billingCountry"));
						
						if (selenium.isElementPresent("id=newPaymentMethod")==true)
						{
							selenium.click("id=newPaymentMethod");
							selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					if (selenium.isElementPresent("id=newPaymentMethod")==true)
					{
					selenium.click("id=newPaymentMethod");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					//selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					//selenium.click("xpath=//input[contains(@class, 'ontinue')]");
					
					
					if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
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
			public HashMap<String,Object> CreateNewAccount_To_UseAs_ExistingUserCC(Selenium selenium,String item,String maxPageLoadInMs,String estoreURL,String newUserEmail,String CompanyAddress,String userFirstName,String userLastName, String shipAddressType,String shipType, String creditCardNumber, String creditCardMonth,String creditCardYear,String paymentType, String accountType, String routingNumber, String accountNumber, HashMap<String,Object> outputMap)
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
					
					selenium.open(estoreURL+"/commerce/checkout/demo/items_offerings.jsp");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					selenium.select("id=skus", "label="+item);
					Thread.sleep(5000L);
					if (selenium.isElementPresent("css=#attrib1")==true)
						{
							selenium.select("css=#attrib1", "label=Download with Reinstall");
							Thread.sleep(5000L);
						}
					//Thread.sleep(10000L);
					if(selenium.isElementPresent("id=submit")==true)
						{
						selenium.click("id=submit");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					if(selenium.isElementPresent("css=input.coButtonNoSubmit.coCheckoutAndContinue")==true)
						{
						selenium.click("css=input.coButtonNoSubmit.coCheckoutAndContinue");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					selenium.type("id=newUserEmail", newUserEmail);
					
					if(selenium.isElementPresent("id=buttonContinueAsGuest")==true)
						{
						selenium.click("id=buttonContinueAsGuest");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					
						selenium.type("id=accountName", accountName);
					if (CompanyAddress.equals("Company_StandardUSA"))
						{
						System.out.println("Inside Company USA Address");
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress",addressMap.get("streetAddress"));
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label="+addressMap.get("state"));
						selenium.type("id=postalCode",addressMap.get("postalCode"));
						}
					else if (CompanyAddress.equals("Company_POBox_USA"))
						{
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress","PO Box# "+NewComapanyName);
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label="+addressMap.get("state"));
						selenium.type("id=postalCode",addressMap.get("postalCode"));
						}
					else if (CompanyAddress.equals("Company_APOBox_USA"))
						{
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress","FPO Base# "+NewComapanyName);
						selenium.type("id=city","FPO");
						selenium.select("id=stateSelect", "label=AE");
						selenium.type("id=postalCode","09409");
						}
					else if (CompanyAddress.equals("Company_StandardCANADA"))
						{
						selenium.select("id=countrySelect", "label=Canada");
						selenium.type("id=streetAddress","2210 Bank St");
						selenium.type("id=city","Ottawa");
						selenium.select("id=stateSelect", "label=ON");
						selenium.type("id=postalCode","K1V 1J5");
						}
					else if (CompanyAddress.equals("Company_POBox_CANADA"))
						{
						selenium.select("id=countrySelect", "label=Canada");
						selenium.type("id=streetAddress","PO Box #"+NewComapanyName);
						selenium.type("id=city",addressMap.get("city"));
						selenium.select("id=stateSelect", "label=ON");
						selenium.type("id=postalCode","K1V 1J5");
						}
					else if(CompanyAddress.equals("Company_USPossession_USA"))
						{
						selenium.select("id=countrySelect", "label=US");
						selenium.type("id=streetAddress","1501 Ponce de Leon");
						selenium.type("id=city","Santurce");
						selenium.select("id=stateSelect", "label=PR");
						selenium.type("id=postalCode","00907");
						}	
						selenium.type("id=phoneNumber",addressMap.get("phoneNumber"));
						selenium.type("id=firstName",userFirstName);
						selenium.type("id=lastName",userLastName);
					if (selenium.isElementPresent("id=createLogin")==true)
						{
						selenium.click("id=createLogin");
						}
					
						String ExistingUserName = accountName;
						selenium.type("id=userName", ExistingUserName);
						outputMap.put("SignInUsername", ExistingUserName);
						String ExistingUserPassword = "password123";
						selenium.type("id=password",ExistingUserPassword);
						outputMap.put("SignInPassord", ExistingUserPassword);
						selenium.type("id=confirmPassword",ExistingUserPassword);
						selenium.select("id=securityQuestion", "value=What was the name of your first pet?");
						selenium.type("id=securityAnswer","pet3");
						System.out.println("UserID"  +  (String)outputMap.get("SignInUsername"));
						System.out.println("Password"  +  (String)outputMap.get("SignInPassord"));
																
					if (selenium.isElementPresent("id=updateCompanyInfo")==true)
						{
						selenium.click("id=updateCompanyInfo");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					
					if (selenium.isElementPresent("id=updateCompanyInfo")==true)
						{
						selenium.click("id=updateCompanyInfo");
						selenium.waitForPageToLoad(maxPageLoadInMs);
						}
					
				
					
					//outputMap.put("AccountName", accountName);
				if (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
					{
					selenium.click("id=shippingAddressSameAsCompanyAddress");
					}
				if (selenium.isElementPresent("link=Add address")==true)
					{
					selenium.click("link=Add address");
					}
				
				
					
					int randomNumber1 = new Double(Math.random()*10000000).intValue();
					HashMap<String,String> addressMap1 = buildAddress(randomNumber);
					
					if (("Shipping_StandardUSA").equals(shipAddressType)) 
					{
						selenium.select("id=shippingCountrySelect", "label=US");
						selenium.type("id=address1",addressMap1.get("streetAddress"));
						selenium.type("id=city",addressMap1.get("city"));
						selenium.select("id=shippingStateSelect", "label="+addressMap1.get("state"));
						selenium.type("id=postalCode",addressMap1.get("postalCode"));
						System.out.println("Inside standard USA Address");
					}
					else if(("Shipping_APOBox_USA").equals(shipAddressType))
					{
						selenium.select("id=shippingCountrySelect", "label=US");
						selenium.type("id=address1","APO Base#"+randomNumber);
						selenium.type("id=city","APO");
						selenium.select("id=shippingStateSelect", "label=AA");
						selenium.type("id=postalCode","34002");
					}
					else if( ("Shipping_POBox_USA").equals(shipAddressType))
					{
						selenium.select("id=shippingCountrySelect", "label=US");
						selenium.type("id=address1","PO Box #"+randomNumber);
						selenium.type("id=city",addressMap1.get("city"));
						selenium.select("id=shippingStateSelect", "label="+addressMap1.get("state"));
						selenium.type("id=postalCode",addressMap1.get("postalCode"));
					}
					else if( ("Shipping_StandardCANADA").equals(shipAddressType))
					{
						selenium.select("id=shippingCountrySelect", "label=CANADA");
						selenium.type("id=address1","1350 Richmond Road");
						selenium.type("id=city","Ottawa");
						selenium.select("id=shippingStateSelect","label=ON");
						selenium.type("id=postalCode","K2B 7Z3");
					}
					else if(("Shipping_POBox_CANADA").equals(shipAddressType))
					{
						selenium.select("id=shippingCountrySelect", "label=CANADA");
						selenium.type("id=address1","PO Box #"+randomNumber1);
						selenium.type("name=city","Calgary");
						selenium.select("id=shippingStateSelect", "label=AB");
						selenium.type("id=postalCode","T2J 3V1");
					}
					else if(("Shipping_USPossession_USA").equals(shipAddressType))
					{
						selenium.select("id=shippingCountrySelect", "label=US");
						selenium.type("id=address1","405 Ferrocaril St");
						selenium.type("id=city","Ponce");
						selenium.select("id=shippingStateSelect", "label=PR");
						selenium.type("id=postalCode","00733");
					}
				

					
				if (selenium.isElementPresent("id=addNewShippingButton")==true)
				{
					selenium.click("id=addNewShippingButton");
					selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				if (selenium.isElementPresent("id=standardizeAddressSelected2")==true)
				{
					selenium.click("id=standardizeAddressSelected2");
					selenium.click("id=addNewShippingButton");
					selenium.waitForPageToLoad(maxPageLoadInMs);
				}
				if (selenium.isElementPresent("id=selectShippingButton")==true)
				{
					selenium.click("id=selectShippingButton");
					selenium.waitForPageToLoad(maxPageLoadInMs);
				}	
					if(("creditcard").equals(paymentType)){		
					selenium.type("id=creditCardNumber", creditCardNumber);
					selenium.focus("id=creditCardNumber");
					//The native key press (TAB) is necessary in order to identify the correct credit card type
					selenium.keyPressNative("9");
					switch(Integer.parseInt(creditCardMonth))
					{
						case 1: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
						case 2: selenium.select("id=dropdownCreditCardExpirationMM", "value=02"); break;
						case 3: selenium.select("id=dropdownCreditCardExpirationMM", "value=03"); break;
						case 4: selenium.select("id=dropdownCreditCardExpirationMM", "value=04"); break;
						case 5: selenium.select("id=dropdownCreditCardExpirationMM", "value=05"); break;
						case 6: selenium.select("id=dropdownCreditCardExpirationMM", "value=06"); break;
						case 7: selenium.select("id=dropdownCreditCardExpirationMM", "value=07"); break;
						case 8: selenium.select("id=dropdownCreditCardExpirationMM", "value=08"); break;
						case 9: selenium.select("id=dropdownCreditCardExpirationMM", "value=09"); break;
						case 10: selenium.select("id=dropdownCreditCardExpirationMM", "value=10"); break;
						case 11: selenium.select("id=dropdownCreditCardExpirationMM", "value=11"); break;
						case 12: selenium.select("id=dropdownCreditCardExpirationMM", "value=12"); break;
						default: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
					}
						selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
						selenium.type("id=creditCardHolderName", "TestCC");
						selenium.type("id=creditCardCSC", "123");
						selenium.click("xpath=//input[contains(@class, 'ontinue')]");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					else if(("EFT").equals(paymentType))
						{
						selenium.click("id=radioPaymentEFT");
						selenium.type("id=accountName", "TestEFT");
						selenium.select("id=dropdownAccountType", "value="+accountType);
						selenium.type("id=routingNumber",routingNumber);
						selenium.type("id=accountNumber",accountNumber);
						selenium.type("id=confirmAccountNumber",accountNumber);
						selenium.focus("id=confirmAccountNumber");
						//The native key press (TAB) is necessary in order to identify the correct credit card type
						selenium.keyPressNative("9");
						selenium.click("xpath=//input[contains(@class, 'ontinue')]");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					if (selenium.isElementPresent("id=cart-place-order-button")==true)
					{
						selenium.click("id=cart-place-order-button");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					if (selenium.isElementPresent("id=cart-place-order-button")==true)
					{
						selenium.click("id=cart-place-order-button");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					if (selenium.isElementPresent("link=Sign Out")==true)
					{
						selenium.click("link=Sign Out");
						selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					
					Thread.sleep(10000L);	
					Thread.sleep(10000L);
					//Checks that the shopping cart page was loaded successfully
					if((selenium.getLocation().toLowerCase().contains("login.jsp")) ||selenium.getLocation().toLowerCase().contains("signin.jsp")) 
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The Create New User Account was successful.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The Create New User Account was successful.");
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
			 * Signs into an existing customer account 
			 * 
			 * @param selenium Default Selenium 
			 * @param userId UserId used to sign into an existing account
			 * @param password Password used to sign into an existing account
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 */
			public HashMap<String,Object> signInAsExistingUser(Selenium selenium,String maxPageLoadInMs, HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Sign In Existing User");
					
					selenium.type("id=userid",(String)outputMap.get("SignInUsername"));
					selenium.type("id=formValuesPassword",(String)outputMap.get("SignInPassord"));
					selenium.click("id=buttonLogin");
					selenium.waitForPageToLoad(maxPageLoadInMs);
						
					if(selenium.getLocation().toLowerCase().contains("company_shipping.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS,"The company shipping page has successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL,"The company shipping page did not succesfully load.");
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
			public HashMap<String,Object> populateCreditCard_ExistingBillingProfileNew(Selenium selenium,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear,String billAddressType, HashMap<String,Object> outputMap)
			
			//public void populateCreditCard_NewBillingProfilePaymentInformation(Selenium selenium,String maxPageLoadInMs,String creditCardNumber,String creditCardMonth,String creditCardYear)
			{
				try
				{
					TestRun.startTest("Populate Credit Card Payment with new Billing Address Information");
					Thread.sleep(5000L);
					if (selenium.isElementPresent("link=Add a new card")==true)
					{
					selenium.click("link=Add a new card");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					Thread.sleep(5000L);
					}
					
					selenium.type("id=creditCardNumber", creditCardNumber);
					selenium.focus("id=creditCardNumber");
					//The native key press (TAB) is necessary in order to identify the correct credit card type
					selenium.keyPressNative("9");
					switch(Integer.parseInt(creditCardMonth))
					{
						case 1: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
						case 2: selenium.select("id=dropdownCreditCardExpirationMM", "value=02"); break;
						case 3: selenium.select("id=dropdownCreditCardExpirationMM", "value=03"); break;
						case 4: selenium.select("id=dropdownCreditCardExpirationMM", "value=04"); break;
						case 5: selenium.select("id=dropdownCreditCardExpirationMM", "value=05"); break;
						case 6: selenium.select("id=dropdownCreditCardExpirationMM", "value=06"); break;
						case 7: selenium.select("id=dropdownCreditCardExpirationMM", "value=07"); break;
						case 8: selenium.select("id=dropdownCreditCardExpirationMM", "value=08"); break;
						case 9: selenium.select("id=dropdownCreditCardExpirationMM", "value=09"); break;
						case 10: selenium.select("id=dropdownCreditCardExpirationMM", "value=10"); break;
						case 11: selenium.select("id=dropdownCreditCardExpirationMM", "value=11"); break;
						case 12: selenium.select("id=dropdownCreditCardExpirationMM", "value=12"); break;
						default: selenium.select("id=dropdownCreditCardExpirationMM", "value=01"); break;
					}
					Thread.sleep(5000L);
					selenium.select("id=dropdownCreditCardExpirationYYYY", "label="+creditCardYear);
					selenium.type("id=creditCardHolderName", "TestCC");
					selenium.type("id=creditCardCSC", "123");
					Thread.sleep(5000L);
					
						if (selenium.isElementPresent("id=payment_shipping_address")==true)
						{
						selenium.click("id=payment_shipping_address");
						}
						if (selenium.isElementPresent("id=billingAddressSelect")==true)
						{
							selenium.select("id=billingAddressSelect", "label=Create New");
							
						}
						int randomNumber = new Double(Math.random()*10000000).intValue();
						HashMap<String,String> addressMap = buildAddress(randomNumber);
							if (("Billing_StandardUSA").equals(billAddressType))
							{ 
							selenium.select("id=billingCountrySelect", "label=US");
							selenium.type("id=address1", addressMap.get("streetAddress"));
							selenium.type("id=city", addressMap.get("city"));
							selenium.select("id=billingStateSelect", "label="+addressMap.get("state"));
							selenium.type("id=postalCode", addressMap.get("postalCode"));
							
							outputMap.put("shippingStreetAddress", addressMap.get("streetAddress"));
							outputMap.put("shippingCity", addressMap.get("city"));
							outputMap.put("shippingState", addressMap.get("state"));
							outputMap.put("shippingZip", addressMap.get("postalCode"));
							outputMap.put("shippingCountry", "USA");
							}
							else if (("Billing_APOBox_USA").equals(billAddressType))
							{ 
							selenium.select("id=billingCountrySelect", "label=US");
							String billingStreetAddress ="APO Base #"+randomNumber;
							String billingCity ="APO";
							String billingState ="AP";
							String billingZip ="96201";
							String billingCountry ="USA";
							
							selenium.type("id=address1",billingStreetAddress );
							
							selenium.type("id=city", billingCity);
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode", billingZip);
							
							outputMap.put("billingStreetAddress", billingStreetAddress);
							outputMap.put("billingCity", billingCity);
							outputMap.put("billingState", billingState);
							outputMap.put("billingZip", billingZip);
							outputMap.put("billingCountry", billingCountry);
							}
							else if (("Billing_POBox_USA").equals(billAddressType))
							{ 
							selenium.select("id=billingCountrySelect", "label=US");
							String billingStreetAddress ="PO Box #"+randomNumber;
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city", addressMap.get("city"));
							selenium.select("id=billingStateSelect", addressMap.get("state"));;
							selenium.type("id=postalCode", addressMap.get("postalCode"));
							
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
							selenium.select("id=billingCountrySelect", "label=CANADA");
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city",billingCity );
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode",billingZip);
							
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
							
							selenium.select("id=billingCountrySelect", "label=CANADA");
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city",billingCity );
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode",billingZip );
							
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
							selenium.select("id=billingCountrySelect", "label=US");
							selenium.type("id=address1",billingStreetAddress );
							selenium.type("id=city",billingCity );
							selenium.select("id=billingStateSelect", "label="+billingState);
							selenium.type("id=postalCode",billingZip);
							
							outputMap.put("billingStreetAddress", billingStreetAddress);
							outputMap.put("billingCity", billingCity);
							outputMap.put("billingState", billingState);
							outputMap.put("billingZip", billingZip);
							outputMap.put("billingCountry", billingCountry);
							}
							
												
						if (selenium.isElementPresent("id=newPaymentMethod")==true)
							{
							selenium.click("id=newPaymentMethod");
							selenium.waitForPageToLoad(maxPageLoadInMs);
							}
						if (selenium.isElementPresent("id=newPaymentMethod")==true)
							{
							selenium.click("id=newPaymentMethod");
							selenium.waitForPageToLoad(maxPageLoadInMs);
							}
								
					if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
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
			public void populateExistingUser_EFTPaymentInformation(Selenium selenium,String maxPageLoadInMs,String accountType,String accountNumber,String routingNumber)
			{
				try
				{
					TestRun.startTest("Populate new EFT Information");
					if(selenium.isElementPresent("link=Add a new bank account")==true)
					{
						selenium.click("link=Add a new bank account");
							selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					//selenium.click("id=radioPaymentEFT");
					selenium.type("id=accountName", "TestEFT");
					selenium.select("id=dropdownAccountType", "value="+accountType);
					selenium.type("id=routingNumber",routingNumber);
					selenium.type("id=accountNumber",accountNumber);
					selenium.type("id=confirmAccountNumber",accountNumber);
					selenium.focus("id=confirmAccountNumber");
					//The native key press (TAB) is necessary in order to identify the correct credit card type
					selenium.keyPressNative("9");
					selenium.click("xpath=//input[contains(@class, 'ontinue')]");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					
					if(selenium.getLocation().toLowerCase().contains("shoppingcart_review.jsp"))
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
			 * Launches the Existing Payment Method Page (assumes the current page is company_shipping.jsp) 
			 * 
			 * @param selenium Default Selenium 
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 */
			public void launchNew_ExistingPaymentMethodPage(Selenium selenium,String maxPageLoadInMs)
			{
				try
				{
					TestRun.startTest("Launch New Payment Method Page");
					if (selenium.isElementPresent("id=shippingButton")==true)
					{
					selenium.click("id=shippingButton");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					if (selenium.isElementPresent("id=buttonContinue")==true)
					{
					selenium.click("id=buttonContinue");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					
					
					
					if(selenium.getLocation().toLowerCase().contains("payment_method_new_user.jsp") || selenium.getLocation().toLowerCase().contains("payment_method.jsp"))
					{
						TestRun.updateStatus(TestResultStatus.PASS, "The Existing Payment Method Page has successfully loaded.");
					}
					else
					{
						TestRun.updateStatus(TestResultStatus.FAIL, "The Existing Payment Method Page did not successfully load.");
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
			 * Launches the Estore offering page for bundles and verifies that the page has been loaded properly.
			 * 
			 * @param selenium Default Selenium 
			 * @param item Bundle Item that's selected
			 * @param cpcIndividualBankStr max 25
			 * @param cpcIndividualUsageStr max 50
			 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
			 */
			public HashMap<String,Object> addUsageBundleOfferingToShoppingCart(Selenium selenium,String item,String maxPageLoadInMs,String cpcIndividualBankStr, String cpcIndividualUsageStr, HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Add Offering to Shopping Cart");
					
					selenium.click("link=Please click here to go back to CPC items");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					selenium.select("name=sku", "label="+item);
					Thread.sleep(10000);
					
					selenium.click("id=cpcInstance");
					
					if (selenium.isElementPresent("//form[2]/select")==true)
						{
						Thread.sleep(5000L);
						selenium.select("//form[2]/select","label="+cpcIndividualBankStr);
						Thread.sleep(5000L);
					}
					if (selenium.isElementPresent("//select[2]")==true)
					{
						selenium.select("//select[2]","label="+cpcIndividualUsageStr);
						Thread.sleep(5000L);
					}
									
					
					Item itemToAddToMap = new Item();
					itemToAddToMap.setName(item.substring(0,item.indexOf("|")).trim().replace(".", ""));
					itemToAddToMap.setNumber(item.substring(item.indexOf("|")+1).trim());
					lineItemsList.add(itemToAddToMap);
					outputMap.put("ItemsList", lineItemsList);
					
					if (selenium.isElementPresent("id=buttonFormSubmit")==true)
					{
					selenium.click("id=buttonFormSubmit");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					
					if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
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
				return outputMap;
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
			public HashMap<String,Object> addAnyOfferingToShoppingCart(Selenium selenium,String item,String maxPageLoadInMs,String cpcIndividualBankStr, String cpcIndividualUsageStr, HashMap<String,Object> outputMap)
			{
				try
				{
					TestRun.startTest("Add Offering to Shopping Cart");
					
					selenium.click("link=Please click here to go back to CPC items");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					selenium.select("name=sku", "label="+item);
					Thread.sleep(10000);
					
					//String order_id = selenium.getText(Utils.element("ConfirmationPage.OrderId"));
					//String itemName = selenium.getText(Utils.element("ConfirmationPage.ItemName"));
					//String totalPrice = selenium.getText(Utils.element("ConfirmationPage.TotalPrice"));
					
					selenium.isTextPresent("Edition");
					selenium.isTextPresent("Fulfillment Method");
					selenium.isTextPresent("Product Type");
					selenium.isTextPresent("Number of Employees");
					selenium.isTextPresent("Version");
					selenium.isTextPresent("Users");
					if(selenium.isTextPresent("Number of Employees") ==true)
					{
						System.out.println(selenium.getValue("id=attribName0"));
						selenium.select("id=attrib0", "label=Up to 3 Employees");
						//selenium.waitForPageToLoad(maxPageLoadInMs);
						Thread.sleep(5000L);
						
					}
					
					
					if (selenium.isElementPresent("//form[2]/select")==true)
					
					selenium.click("id=cpcInstance");
					
					if (selenium.isElementPresent("//form[2]/select")==true)
						{
						Thread.sleep(5000L);
						selenium.select("//form[2]/select","label="+cpcIndividualBankStr);
						Thread.sleep(5000L);
					}
					if (selenium.isElementPresent("//select[2]")==true)
					{
						selenium.select("//select[2]","label="+cpcIndividualUsageStr);
						Thread.sleep(5000L);
					}
									
					
					Item itemToAddToMap = new Item();
					itemToAddToMap.setName(item.substring(0,item.indexOf("|")).trim().replace(".", ""));
					itemToAddToMap.setNumber(item.substring(item.indexOf("|")+1).trim());
					lineItemsList.add(itemToAddToMap);
					outputMap.put("ItemsList", lineItemsList);
					
					if (selenium.isElementPresent("id=buttonFormSubmit")==true)
					{
					selenium.click("id=buttonFormSubmit");
					selenium.waitForPageToLoad(maxPageLoadInMs);
					}
					
					if(selenium.getLocation().toLowerCase().contains("shopping_cart.jsp"))
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
				return outputMap;
			}


}
