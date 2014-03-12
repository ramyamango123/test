package initiate.estore.runner;

import initiate.estore.util.EstoreUtil;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;

import selenium.util.SeleniumUtil;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;

/**  
* Selenium-Automated Estore Order Flows for an Existing Customer<br>
* 
* @author  sranjan
* @version 1.0.0 
*/ 
public class MyAccount_Info
{
	SeleniumUtil seleniumUtil;
	private static Logger sLog;
	private EstoreUtil estoreUtil;
	
	
	public MyAccount_Info()
	{
		sLog = Logger.getInstance();
		seleniumUtil = new SeleniumUtil();
		estoreUtil = new EstoreUtil();
	}

	
	
	
	/**
	 * Submits a service request <br>
	 * 
	 * @param estoreURL URL for the Estore 
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param userId The userId used to sign into the existing account
	 * @param password The password used to sign into the existing account
	 * @param product is the item for which service request will be created.<br>
	 * @param category is the category the request.<br>
	 * @param subject is the subject of the request.<br>
	 * @param description description of the request.<br>
	 * 				
	 * @return Output Map with values for the following key ("service request") 
	 */
	
	
	public HashMap<String,Object> submitServiceRequest(Object estoreURL,Object maxPageLoadWaitTimeInMinutes,Object userId, Object password, Object product,Object category,Object subject,Object description)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String productStr = (String)product;
		String categoryStr = (String)category;
		String subjectStr = (String)subject;
		String descriptionStr = (String)description;
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | New Service Request");
		
		try
		{
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
				
			//Create a new service request
			outputMap = estoreUtil.CreateServiceRequest(driver, productStr, categoryStr, subjectStr, descriptionStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		}
		finally
		{
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}
		
		return outputMap;
		
		
	}

	
	public HashMap<String,Object> NewCustomerSubmitServiceRequest(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object newUserEmail, 
			Object userFirstName, Object userLastName,Object creditCardNumber,Object creditCardMonth, Object creditCardYear, Object product,
			Object category,Object subject,Object description)
	
	{
		//Required Conversion of parameter types
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String productStr = (String)product;
		String categoryStr = (String)category;
		String subjectStr = (String)subject;
		String descriptionStr = (String)description;
		String itemStr = (String)item;
		String newUserEmailStr = (String)newUserEmail;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear;
		HashMap<String,Object> output = new HashMap<String, Object>();
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("New Customer with New service Order | Modify serivce");
		
		try
		{			
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Add Offering to Shopping Cart
			output = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			//Launch the Sign In Page
			
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			//Create New User
			estoreUtil.createNewUser(driver,newUserEmailStr,maxPageLoadWaitTimeInMilliseconds);
		
			//Populate Company Information 
			output = estoreUtil.populateNewCompanyInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			//Load the Payment Method Page 
			estoreUtil.launchNewPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Populate the Payment Information
			estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
			
			//Submit the Order
			output = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
			//Sleep time till to order gets fulfilled. Time - 5 mins
			estoreUtil.logout(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
			try 
			{
				Thread.sleep(180000L);
			} 
			catch (InterruptedException e) 
			{
				sLog.info(e.getStackTrace().toString());
			}
			
			String username = (String)output.get("SignInUsername");
			String password = (String)output.get("SignInPassword");
			
					
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, username, password, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
				
			//Create a new service request
			outputMap = estoreUtil.CreateServiceRequest(driver, productStr, categoryStr, subjectStr, descriptionStr, maxPageLoadWaitTimeInMilliseconds, outputMap);			
			
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		sLog.info(e.getStackTrace().toString());
	}
	finally
	{
		seleniumUtil.tearDownSelenium();
		TestRun.endSuite();
	}
	return outputMap;
	}
		
	
	
	/**
	 * Update billing/CC information <br>
	 * @param estoreURL URL for the Estore 
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param userId The userId used to sign into the existing account
	 * @param password The password used to sign into the existing account
	 * @param product is the item for which service request will be created.<br>
	 * @return Output Map with values for the following key ("creditCardNumber"),("ExpiryMonth") , ("ExpiryYear") & ("CVV")
	 */	
	public HashMap<String,Object> updateBillingCreditCard(Object estoreURL, Object userId, Object password, Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		
		
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Update billing profile");
		
		try
		{
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			outputMap=estoreUtil.editBillingProfile(driver, maxPageLoadWaitTimeInMilliseconds, outputMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		}
		finally
		{
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}
		return outputMap;
	}

	/**
	 * Update account information <br>
	 * @param estoreURL URL for the Estore 
	 * @param userId username of the account
	 * @param password Password for the account
	 * @param phoneNumber Phone number of the user
	 * @param extension Extension of the user
	 * @param address1 Address1 of the user
	 * @param address2 Address2 of the user
	 * @param city City of the user
	 * @param state State of the user
	 * @param country Country of the user 
	 * @param zip Zip code of the user
	 * @param eMail email of the user 
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @return Output Map with the value for the following keys ("MyAccountPhoneNumber"), ("MyAccountExtension"), ("MyAccountAddress1"), ("MyAccountAddress2"), ("MyAccountCity"), ("MyAccountState"), ("MyAccountCountry"), ("MyAccountZip"), ("MyAccountE-Mail")
	 */
	
	
	public HashMap<String,Object> updateAccount(Object estoreURL, Object userId, Object password, Object phoneNumber,Object extension, Object address1, Object address2, Object city, 
			Object state, Object country, Object zip, Object eMail, Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String phoneNumberStr = (String)phoneNumber;
		String extensionStr = (String)extension;
		String address1Str = (String)address1;
		String address2Str = (String)address2;
		String cityStr = (String)city;
		String stateStr = (String)state;
		String countryStr = (String)country;
		String zipStr = (String)zip;
		String eMailStr = (String)eMail;
		String newEStoreUrl = estoreUtil.changeDomain(estoreURLStr,"pointofsale");
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Update Account");
		
		try
		{
			//Launch My Account page
			//estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, newEStoreUrl, maxPageLoadWaitTimeInMilliseconds);
			outputMap=estoreUtil.editAccount(driver, phoneNumberStr, extensionStr, address1Str, address2Str, cityStr, stateStr, countryStr, zipStr, eMailStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		sLog.info(e.getStackTrace().toString());
	}
	finally
	{
		seleniumUtil.tearDownSelenium();
		TestRun.endSuite();
	}
	return outputMap;
	}
	
	/**
	 * 
	 * @param estoreURL URL for the Estore
	 * @param userId Userid to login to Account
	 * @param password Password to login to login	
	 * @param product Product to be modified
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @return Output Map with the value for the following keys ("No_of_User")
	 */

	public HashMap<String,Object> updateOrder(Object estoreURL, Object userId, Object password, Object product, Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String productStr = (String)product;
		
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Modify order");
		
		try
		{
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			outputMap=estoreUtil.ModifyProduct(driver, productStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		}
		finally
		{
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}
		return outputMap;
	}
	
//	/**
//	 * Update a subscription/service
//	 * @author sranjan
//	 * @param estoreURL Url for eStore
//	 * @param userId username for the account
//	 * @param password password for the account
//	 * @param service Service to be updated 
//	 * @param maxPageLoadWaitTimeInMinutes maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
//	 * @return 
//	 */
	public HashMap<String,Object> updateService(Object estoreURL, Object userId, Object password, Object service, Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String serviceStr = (String)service;
		
		
		//Required---------------------------------------------------------------------------------------------
		//Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Modify serivce");
		
		try
		{
						
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			outputMap=estoreUtil.ModifySubscription(driver, serviceStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
			
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		sLog.info(e.getStackTrace().toString());
	}
	finally
	{
		seleniumUtil.tearDownSelenium();
		TestRun.endSuite();
	}
	return outputMap;
	}
	
	/**
	 * Place a new service/subscription order with a new account and then update purchase
	 * @param estoreURL eStore Url
	 * @param item item to be purchased
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param newUserEmail Email id for the new user
	 * @param userFirstName First name of the user
	 * @param userLastName Last name of the user
	 * @param creditCardNumber Credit card to be used for the purchase
	 * @param creditCardMonth Expiry month of the credit card
	 * @param creditCardYear Expiry year of the credit card
	 * @param service Service/subscription to be updated
	 * @return outputMap Map with values for the following keys ("AccountName" and "OrderNumber") 
	 */
	public HashMap<String,Object> NewCustomerUpdateService(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object newUserEmail, 
			Object userFirstName, Object userLastName,Object creditCardNumber,Object creditCardMonth, Object creditCardYear, Object service)
	
	{
		//Required Conversion of parameter types
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String serviceStr = (String)service;
		String itemStr = (String)item;
		String newUserEmailStr = (String)newUserEmail;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear;
		
		HashMap<String,Object> output = new HashMap<String, Object>();
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("New Customer with New service Order | Modify serivce");
		
		try
		{
			//NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
			//output=newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Create New User
			estoreUtil.createNewUser(driver,newUserEmailStr,maxPageLoadWaitTimeInMilliseconds);
		
			//Populate Company Information 
			outputMap = estoreUtil.populateNewCompanyInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			//Load the Payment Method Page 
			estoreUtil.launchNewPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Populate the Payment Information
			estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			estoreUtil.logout(driver, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			
			//Sleep time till to order gets fulfilled. Time - 5 mins
			try 
			{
				Thread.sleep(300000L);
			} 
			catch (InterruptedException e) 
			{
				sLog.info(e.getStackTrace().toString());
			}
			
			String username = (String)outputMap.get("SignInUsername");
			String password = (String)outputMap.get("SignInPassword");
			
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, username, password, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			outputMap=estoreUtil.ModifySubscription(driver, serviceStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		}
		finally
		{
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}
		return outputMap;
	}
	
	public HashMap<String,Object> newCustomerUpdateOrder(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object newUserEmail, 
			Object userFirstName, Object userLastName,Object creditCardNumber,Object creditCardMonth, Object creditCardYear, Object product)
	
	{
		//Required Conversion of parameter types
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String productStr = (String)product;
		String itemStr = (String)item;
		String newUserEmailStr = (String)newUserEmail;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear;
		
		HashMap<String,Object> output = new HashMap<String, Object>();
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("New Customer with New product Order | Then Modify Order");
		
		try
		{
			//NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
			//output=newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Create New User
			estoreUtil.createNewUser(driver,newUserEmailStr,maxPageLoadWaitTimeInMilliseconds);
		
			//Populate Company Information 
			outputMap = estoreUtil.populateNewCompanyInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			//Load the Payment Method Page 
			estoreUtil.launchNewPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Populate the Payment Information
			estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			estoreUtil.logout(driver, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			
			//Sleep time till to order gets fulfilled. Time - 5 mins
			try 
			{
				Thread.sleep(300000L);
			} 
			catch (InterruptedException e) 
			{
				sLog.info(e.getStackTrace().toString());
			}
			
			String username = (String)outputMap.get("SignInUsername");
			String password = (String)outputMap.get("SignInPassword");
			
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, username, password, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			//outputMap=estoreUtil.ModifySubscription(driver, serviceStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
			outputMap=estoreUtil.ModifyProduct(driver, productStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		}
		finally
		{
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}
		return outputMap;
	}
	
	
	/**
	 * Update EFT profile of an account
	 * @author sranjan
	 * @param estoreURL eStore url 
	 * @param userId Username of the account
	 * @param password password of the account
	 * @param maxPageLoadWaitTimeInMinutes maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @return outputMap Map with the value for the following key ("EFTRoutingNumber"),("EFTAccountNumber")
	 */
	public HashMap<String,Object> updateEFTBillingProfile(Object estoreURL, Object userId, Object password, Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		
		
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Update EFT profile ");
		
		try
		{
			
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			outputMap=estoreUtil.editEFTBillingProfile(driver, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		sLog.info(e.getStackTrace().toString());
	}
	finally
	{
		seleniumUtil.tearDownSelenium();
		TestRun.endSuite();
	}
	return outputMap;
	}
	
	
	
	/**
	 * Create new account and order with EFT. Then update the EFT profile from my account
	 * @param estoreURL eStore url.
	 * @param item Item to be purchased.
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param newUserEmail Email id for the new user
	 * @param userFirstName First name of the user
	 * @param userLastName Last name of the user
	 * @param accountType New EFT account type
	 * @param routingNumber NEW EFT routing number
	 * @param accountNumber NEW EFT account number
	 * @return outputMap Map with the value for the following key ("AccountName"),("AccountType"),("RoutingNumber"),("AccountNumber")
	 */
	public HashMap<String,Object> NewCustomerupdateEFTBillingProfile(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object newUserEmail, 
			Object userFirstName, Object userLastName,Object accountType,Object routingNumber,Object accountNumber)
	{
		//Required Conversion of parameter types
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String itemStr = (String)item;
		String newUserEmailStr = (String)newUserEmail;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String accountTypeStr = (String)accountType;
		String routingNumberStr = (String)routingNumber;
		String accountNumberStr = (String)accountNumber;
		HashMap<String,Object> output = new HashMap<String, Object>();
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("New Customer | Update EFT profile");
		
		try
		{
//			NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//			output=newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);

			
			
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Create New User
			estoreUtil.createNewUser(driver,newUserEmailStr,maxPageLoadWaitTimeInMilliseconds);
		
			//Populate Company Information 
			outputMap = estoreUtil.populateNewCompanyInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			//Load the Payment Method Page 
			estoreUtil.launchNewPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Populate the Payment Information
			estoreUtil.populateEFTPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			estoreUtil.logout(driver, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			
			
			//Sleep time till to order gets fulfilled. Time - 3 mins
			try 
			{
				Thread.sleep(180000L);
			} 
			catch (InterruptedException e) 
			{
				sLog.info(e.getStackTrace().toString());
			}
			
			String userIdStr = (String)outputMap.get("SignInUsername");
			String passwordStr = (String)outputMap.get("SignInPassword");
			//System.out.println("Username -"+userIdStr+"<> Password - "+passwordStr);
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			outputMap = estoreUtil.editEFTBillingProfile(driver, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		sLog.info(e.getStackTrace().toString());
	}
	finally
	{
		seleniumUtil.tearDownSelenium();
		TestRun.endSuite();
	}
	return outputMap;
	}
	
	
	
	/**
	 * Update an existing account's EFT profile with user supplied Info
	 * @param estoreURL eStore Url
	 * @param userId Username of the account
	 * @param password password of the account
	 * @param accountName Account Name for new EFT profile
	 * @param accountType Account Type for the new EFT profile
	 * @param routingNumber Routing Number for the new EFT profile
	 * @param accountNumber Account Number for the new EFT profile
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @return outputMap Map with the value for the following key ("AccountName"),("AccountType"),("RoutingNumber"),("AccountNumber")
	 */
	public HashMap<String,Object> updateEFTBillingProfileWithUserInfo(Object estoreURL, Object userId, Object password, Object accountName, Object accountType, Object routingNumber, Object accountNumber,Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String accountNameStr = (String)accountName;
		String accountTypeStr = (String)accountType;
		String routingNumberStr = (String)routingNumber;
		String accountNumberStr = (String)accountNumber;
				
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		//Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Update EFT profile with User Info");
		
		try
		{
			//selenium.windowFocus();
			//selenium.windowMaximize();
			
			//Launch My Account page
			//estoreUtil.myAccountLogin(selenium, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
			//outputMap=estoreUtil.editEFTBillingProfileWithUserInfo(selenium, accountNameStr, accountTypeStr, routingNumberStr, accountNumberStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
			outputMap=estoreUtil.editEFTBillingProfileWithUserInfo(driver, accountNameStr, accountTypeStr, routingNumberStr, accountNumberStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		sLog.info(e.getStackTrace().toString());
	}
	finally
	{
		seleniumUtil.tearDownSelenium();
		TestRun.endSuite();
	}
	return outputMap;
	}
	
	/**
	 * Add a random generated company to user account (Functionality is now disabled now)
	 * @param estoreURL eStore Url
	 * @param userId User name of the account
	 * @param password Password of the account 
	 * @param eMail E-mail for the new company
	 * @param maxPageLoadWaitTimeInMinutes
	 * @return outputMap Map with the value for the following key ("AccountName")
	 */
	public HashMap<String,Object> existingUserAddNewCompany(Object estoreURL, Object userId, Object password, Object eMail, Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String eMailStr = (String)eMail;
		String newEStoreUrl = estoreUtil.changeDomain(estoreURLStr,"pointofsale");  //Since add company works in pointofsale domain
		
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Update EFT profile");
		
		try
		{
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, newEStoreUrl, maxPageLoadWaitTimeInMilliseconds);
			outputMap = estoreUtil.addNewCompany(driver, eMailStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		sLog.info(e.getStackTrace().toString());
	}
	finally
	{
		seleniumUtil.tearDownSelenium();
		TestRun.endSuite();
	}
	return outputMap;
	}
	
	
	
	/**
	 * 
	 * @param estoreURL eStore Url to start test with
	 * @param userId UserId for the account
	 * @param password Password for the account
	 * @param accountName Account name to be added
	 * @param streetAddress Street address for new account
	 * @param city City for the new account
	 * @param state State for the new account
	 * @param zip Zip for the new account
	 * @param phoneNumber Phone number for the new account
	 * @param eMail eMail of the new account 
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @return The details of the new Company
	 */
	public HashMap<String,Object> existingUserAddNewCompanyWithUserDetails(Object estoreURL, Object userId, Object password, Object accountName, Object streetAddress,
			Object city, Object state, Object zip, Object phoneNumber, Object eMail, Object maxPageLoadWaitTimeInMinutes)
	{
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String accountNameStr = (String)accountName;
		String streetAddressStr = (String)streetAddress;
		String cityStr = (String)city;
		String stateStr = (String)state;
		String zipStr = (String)zip;
		String phoneNumberStr = (String)phoneNumber;
		String eMailStr = (String)eMail;
		String newEStoreUrl = estoreUtil.changeDomain(estoreURLStr,"pointofsale");  //Since add company works in pointofsale domain
		
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Update EFT profile");
		
		
		try
		{
			//Launch My Account page
			estoreUtil.myAccountLogin(driver, userIdStr, passwordStr, newEStoreUrl, maxPageLoadWaitTimeInMilliseconds);
			outputMap=estoreUtil.addNewCompanyWithUserDetails(driver, accountNameStr, streetAddressStr, cityStr, stateStr, zipStr, phoneNumberStr, eMailStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		}
		finally
		{
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}
		return outputMap;
	}




	public void validateLoginPage(String estoreURL,Object maxPageLoadWaitTimeInMinutes) 
	{
		
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		try
		{
			TestRun.startSuite("eStore My Account | Verify required fields in Account login page");
			estoreUtil.validateLoginPage(driver, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);
		}
		catch(Exception e)
		{
			sLog.info(e.getStackTrace().toString());
		}
		finally
		{
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}
	
		
	}
	
	
}
	



