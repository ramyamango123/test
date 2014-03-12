package initiate.estore.runner;

import initiate.estore.util.EstoreUtil;

import java.util.HashMap;

import selenium.util.SeleniumUtil;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;
import com.thoughtworks.selenium.Selenium;

/**  
* Selenium-Automated Estore Order Flows for an Existing Customer<br>
* 
* @author  Jeffrey Walker
* @version 1.0.0 
*/ 
public class ExistingCustomer_NewOrder
{
	SeleniumUtil seleniumUtil;
	private static Logger sLog;
	private EstoreUtil estoreUtil;
	
	
	public ExistingCustomer_NewOrder()
	{
		sLog = Logger.getInstance();
		seleniumUtil = new SeleniumUtil();
		estoreUtil = new EstoreUtil();
	}

	/**
	 * Submits a Nonshippable Order (Download/Subscription) with default item attributes <br>
	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
	 * 
	 * @param estoreURL URL for the Estore Offering page 
	 * @param item The item number of the offering being purchased. This has to be the exact value in the combobox on the estore offering page (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param userId The userId used to sign into the existing account
	 * @param password The password used to sign into the existing account
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 */
	public HashMap<String,Object> submitNonShippableOrder_DefaultItemAttributes(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object userId, Object password)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		
		//Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Non-Shippable Order | Default Item Attributes");
		
		try
		{
			selenium.windowFocus();
			selenium.windowMaximize();
			
			//Launch Offering Page
			estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	 * Submits a Nonshippable Bundle Order with default item attributes <br>
	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
	 * 
	 * @param estoreURL URL for the Estore Offering page 
	 * @param item The item number of the offering being purchased. This has to be the exact value in the combobox from the estore CPC bundle offering page (ex: "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION")
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param userId The userId used to sign into the existing account
	 * @param password The password used to sign into the existing account
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 */
	public HashMap<String,Object> submitNonShippableBundleOrder_DefaultItemAttributes(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object userId, Object password)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		
		
		//Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Non-Shippable Bundle Order | Default Item Attributes");
		
		try
		{
			selenium.windowFocus();
			selenium.windowMaximize();
			
			//Launch Offering Page
			estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap =estoreUtil.addBundleOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	 * Submits a Nonshippable Multiple Line Order (Download/Subscription) with default item attributes <br>
	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
	 * 
	 * @param estoreURL URL for the Estore Offering page 
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param userId The userId used to sign into the existing account
	 * @param password The password used to sign into the existing account
	 * @param items The item number of the offering being purchased. The values here have to be the exact values in the combobox from the estore offering page.<br>
	 * 				***Important:This parameter can take any number of items.  (ex: "Intuit QuickBooks Point Of Sale. | 1099874","Intuit QuickBooks Payroll Enhanced | 1099581").
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 */
	public HashMap<String,Object> submitNonShippableMultipleLinesOrder_DefaultItemAttributes(Object estoreURL,
			Object maxPageLoadWaitTimeInMinutes,Object userId, Object password, Object... items)
    {
		
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		
		
		//Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Multiple Non-Shippable Same Lines Order | Default Item Attributes | Credit Card");
		
		try
		{
			selenium.windowFocus();
			selenium.windowMaximize();
			
			//Launch Offering Page
			estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offerings to Shopping Cart
			for(int i=0;i<items.length;i++)
			{
				outputMap = estoreUtil.addOfferingToShoppingCart(selenium,items[i].toString(),maxPageLoadWaitTimeInMilliseconds,outputMap);
				
				if(i<items.length-1)
				{
					selenium.goBack();
					selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
				}
			}
			
			//Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
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

//Added By Shankar


	/**
	 * Submits a Shippable/Non Shippable Order (CD/hardware/Download/Subscription) with default item attributes <br>
	 * using a Credit Card Billing Profile for a New Customer Account. <br>
	 * @param estoreURL URL for the Estore Offering page 
	 * @param item The item number of the offering being purchased. This has to be the exact value in the combobox on the estore offering page (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param newUserEmail Email for the new user 
	 * @param userFirstName First Name of the new contact
	 * @param userLastName Last Name of the new contact
	 * @param creditCardNumber Credit Card Number
	 * @param creditCardMonth Credit Card Expiration Month
	 * @param creditCardYear Credit Card Expiration Year
	 * @param productTypeStr; To select the product option String productType="IntuitQB_POS_Download_Reinstall";
	 * @param cpcIndividualBankStr  CPC product quantity selection max 25.
	 * @param cpcIndividualUsageStr CPC product quantity selection max 50.
	 * @param paymentTypeStr creditcard or EFT to enter that flow and create a new one.
	 * @param accountTypeStr  EFT account type Checking or Savings.
	 * @param accountNumberStr EFT account number.
	 * @param routingNumberStr EFT routing number.
	 * @param shipAddressTypeStr ship address type to be created Shipping_StandardUSA Shipping_POBox_USA Shipping_APOBox_USA Shipping_StandardCANADA Shipping_POBox_CANADA Shipping_USPossession_USA  
	 * @param shipTypeStr for ship type String shipType="Ship_Normal"; or String shipType="Ship_Normal" ;
	 * @param shipAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
	 * @param billAddressTypeStr ship address type to be created Billing_StandardUSA Billing_POBox_USA Billing_APOBox_USA Billing_StandardCANADA Billing_POBox_CANADA Billing_USPossession_USA
	 * @param billAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
	 * @param companyAddressStr ship address type to be created Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 */
	public HashMap<String,Object> submitOrder_ExistingAccount(Object estoreURL, Object item, Object maxPageLoadWaitTimeInMinutes, Object newUserEmail,Object userFirstName,Object userLastName,Object creditCardNumber, Object creditCardMonth, Object creditCardYear,Object shipAddressType,Object shipType,Object companyAddress,Object billAddressType,Object productType,Object cpcIndividualBank,Object cpcIndividualUsage,Object paymentType,Object accountType,Object accountNumber,Object routingNumber,Object shipAddressNew,Object billAddressNew)
	{
		//Required Conversion of parameter types
						
				//Object estoreURL,
				String estoreURLStr = (String)estoreURL;
				//String ExistingActEstoreURLStr= (String)existingActEstoreURL;
				String itemStr = (String)item;
				String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
				String newUserEmailStr = (String)newUserEmail; 
				String userFirstNameStr = (String)userFirstName;
				String userLastNameStr = (String)userLastName;
				String creditCardNumberStr = (String)creditCardNumber;
				String creditCardMonthStr = (String)creditCardMonth;
				String creditCardYearStr = (String)creditCardYear; 
				String companyAddressStr = (String)companyAddress;
				
				String productTypeStr = (String)productType;
				String cpcIndividualBankStr = (String)cpcIndividualBank; 
				String cpcIndividualUsageStr = (String)cpcIndividualUsage;
				String paymentTypeStr = (String)paymentType;
				String accountTypeStr = (String)accountType;
				String accountNumberStr = (String)accountNumber;
				String routingNumberStr = (String)routingNumber;
				String shipAddressTypeStr = (String)shipAddressType;
				String shipTypeStr = (String)shipType; 
				String shipAddressNewStr = (String)shipAddressNew;
				String billAddressTypeStr = (String)billAddressType;
				String billAddressNewStr = (String)billAddressNew;
				//String TestCase_NameStr = (String)TestCase_Name ;
				
				
				
				//Required---------------------------------------------------------------------------------------------
				Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
				HashMap<String,Object> outputMap = new HashMap<String, Object>();
				outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
				outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
				String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
				//-----------------------------------------------------------------------------------------------------
				
				String TestCase_NameStr ="null";
				
				if(("Yes").equals(shipAddressNew) && ("Yes").equals(billAddressNew))
				{
					 TestCase_NameStr = "Existing User | " + itemStr + " | " +shipType+"  "+ shipAddressTypeStr + " | " + paymentTypeStr +" "+billAddressType+" | ";
				}
				else if(("Yes").equals(shipAddressNew) && ("No").equals(billAddressNew))
				{
					 TestCase_NameStr = "Existing User | " + itemStr + " | " +shipType+"  "+ shipAddressTypeStr + " | " + paymentTypeStr +" | ";
				}
				else if(("No").equals(shipAddressNew) && ("No").equals(billAddressNew))
				{
					 TestCase_NameStr = "Existing User | " + itemStr + " | " + paymentTypeStr +" | ";
				}
				else 
				{
					 TestCase_NameStr = "Existing User | " + itemStr + " | " + paymentTypeStr +" | ";
				}
				
				TestRun.startSuite(TestCase_NameStr);
				
				try
				{
					selenium.windowFocus();
					selenium.windowMaximize();
			
					
					// Create new account to use it as Existing User
					outputMap =estoreUtil.CreateNewAccount_To_UseAs_ExistingUserCC(selenium,itemStr, maxPageLoadWaitTimeInMilliseconds,estoreURLStr,newUserEmailStr, companyAddressStr,userFirstNameStr, userLastNameStr,  shipAddressTypeStr, shipTypeStr, creditCardNumberStr,  creditCardMonthStr, creditCardYearStr, paymentTypeStr,  accountTypeStr,  routingNumberStr,accountNumberStr,outputMap);
								
					//Launch Offering Page
					estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
					//outputMap =estoreUtil.addOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
					
					//Add Offering to Shopping Cart
					if(("CPC_Bundle_Tax_Online_2011").equals(productTypeStr))
						{
						outputMap =estoreUtil.addUsageBundleOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds, cpcIndividualBankStr, cpcIndividualUsageStr,outputMap);
						
						}
					else if(("IntuitQB_POS_Download_Reinstall").equals(productTypeStr))
						{
						outputMap =estoreUtil.addDownload_ReInstallOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
						}
					else if(("IntuitQB_Payroll_3User").equals(productTypeStr))
					{
						outputMap =estoreUtil.addPayRoll_3EmployeesOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
					}
					else
					{
						outputMap =estoreUtil.addOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
					}
				
				//Launch the Sign In Page
				estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
				
				//Sign In Existing User
				
				outputMap = estoreUtil.signInAsExistingUser(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
				if(("Yes").equals(shipAddressNewStr) && (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true))
					{
					if(("Ship_Normal").equals(shipTypeStr))
						{
						//Populate New Shipping Information 
						outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(selenium,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
						}
					else if(("Ship_3rdParty").equals(shipTypeStr))
						{
						//Populate New 3rd Party Shipping Information 
						outputMap = estoreUtil.populateCD_Hardware_New_3rdPartyShippingInfo(selenium,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
						}
					}
				
				if(("creditcard").equals(paymentTypeStr) && (selenium.isElementPresent("link=Add a new card")==true))
				{
					if(("Yes").equals(billAddressNewStr)){
						//Load the Payment Method Page with new Billing Profile 
						estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
						estoreUtil.populateCreditCard_ExistingBillingProfileNew(selenium,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
					
					}
					else{
						///Load the Payment Method Page  
						estoreUtil.launchNewPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
						//Populate the Payment Information
						estoreUtil.populateCreditCardPaymentInformation(selenium,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
					}
				}else if(("EFT").equals(paymentTypeStr) && (selenium.isElementPresent("link=Add a new bank account")==true)){
					//Load the Payment Method Page 
					estoreUtil.launchExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
					//Populate the Payment Information
					estoreUtil.populateExistingUser_EFTPaymentInformation(selenium,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
				} 
				
				//Submit the Order
				outputMap = estoreUtil.submitOrder(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	 * Submits a ALl Order Types CD/Hardware/Download/Payroll/Subscription can be placed with attributes set at main page <br>
	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
	 * @param estoreURL URL for the Estore Offering page 
	 * @param item The item number of the offering being purchased. This has to be the exact value in the combobox on the estore offering page (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param userId The userId used to sign into the existing account
	 * @param password The password used to sign into the existing account
	 * @param productTypeStr; To select the product option String productType="IntuitQB_POS_Download_Reinstall";
	 * @param cpcIndividualBankStr  CPC product quantity selection max 25.
	 * @param cpcIndividualUsageStr CPC product quantity selection max 50.
	 * @param paymentTypeStr creditcard or EFT to enter that flow and create a new one.
	 * @param accountTypeStr  EFT account type Checking or Savings.
	 * @param accountNumberStr EFT account number.
	 * @param routingNumberStr EFT routing number.
	 * @param shipAddressTypeStr ship address type to be created Shipping_StandardUSA Shipping_POBox_USA Shipping_APOBox_USA Shipping_StandardCANADA Shipping_POBox_CANADA Shipping_USPossession_USA  
	 * @param shipTypeStr for ship type String shipType="Ship_Normal"; or String shipType="Ship_Normal" ;
	 * @param shipAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
	 * @param billAddressTypeStr ship address type to be created Billing_StandardUSA Billing_POBox_USA Billing_APOBox_USA Billing_StandardCANADA Billing_POBox_CANADA Billing_USPossession_USA
	 * @param billAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
	 * @param companyAddressStr ship address type to be created Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 */
	public HashMap<String,Object> submitShippable_ExistingUser_Order_DefaultItemAttributes(Object estoreURL,Object item, Object maxPageLoadWaitTimeInMinutes,Object userFirstName,Object userLastName,Object creditCardNumber,Object creditCardMonth,Object creditCardYear,Object shipAddressType,Object shipType,Object companyAddress,Object billAddressType,Object productType,Object cpcIndividualBank,Object cpcIndividualUsage,Object paymentType,Object accountType,Object accountNumber,Object routingNumber,Object shipAddressNew,Object billAddressNew, Object userId,Object password)
																							
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		//String newUserEmailStr = (String)newUserEmail; 
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		//String companyAddressStr = (String)companyAddress;
		
		String productTypeStr = (String)productType;
		String cpcIndividualBankStr = (String)cpcIndividualBank; 
		String cpcIndividualUsageStr = (String)cpcIndividualUsage;
		String paymentTypeStr = (String)paymentType;
		String accountTypeStr = (String)accountType;
		String accountNumberStr = (String)accountNumber;
		String routingNumberStr = (String)routingNumber;
		String shipAddressTypeStr = (String)shipAddressType;
		String shipTypeStr = (String)shipType; 
		String shipAddressNewStr = (String)shipAddressNew;
		String billAddressTypeStr = (String)billAddressType;
		String billAddressNewStr = (String)billAddressNew;
		//String TestCase_NameStr = (String)TestCase_Name ;
		//String estoreUserTypeStr = (String)estoreUserType;
		
		//Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		String TestCase_NameStr ="null";
		
		if(("Yes").equals(shipAddressNew) && ("Yes").equals(billAddressNew))
		{
			 TestCase_NameStr = "Existing User | " + itemStr + " | " +shipType+"  "+ shipAddressTypeStr + " | " + paymentTypeStr +" "+billAddressType+" | ";
		}
		else if(("Yes").equals(shipAddressNew) && ("No").equals(billAddressNew))
		{
			 TestCase_NameStr = "Existing User | " + itemStr + " | " +shipType+"  "+ shipAddressTypeStr + " | " + paymentTypeStr +" | ";
		}
		else if(("No").equals(shipAddressNew) && ("No").equals(billAddressNew))
		{
			 TestCase_NameStr = "Existing User | " + itemStr + " | " + paymentTypeStr +" | ";
		}
		else 
		{
			 TestCase_NameStr = "Existing User | " + itemStr + " | " + paymentTypeStr +" | ";
		}
		
		TestRun.startSuite(TestCase_NameStr);
		
		try
		{
			selenium.windowFocus();
			selenium.windowMaximize();
			
			estoreUtil.cleanExistingUserShoppingCartItems( selenium, estoreURLStr, userIdStr, passwordStr, maxPageLoadWaitTimeInMilliseconds);
			//Launch Offering Page
			estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
						
			if(("CPC_Bundle_Tax_Online_2011").equals(productTypeStr))
				{
				outputMap =estoreUtil.addUsageBundleOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds, cpcIndividualBankStr, cpcIndividualUsageStr,outputMap);
														
				}
			else if(("IntuitQB_POS_Download_Reinstall").equals(productTypeStr))
				{
				outputMap =estoreUtil.addDownload_ReInstallOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
																					
				}
			else if(("IntuitQB_Payroll_3User").equals(productTypeStr))
			{
				outputMap =estoreUtil.addPayRoll_3EmployeesOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			else
			{
				outputMap =estoreUtil.addOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			//Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			if(("Yes").equals(shipAddressNewStr) && (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true))
			{
			if(("Ship_Normal").equals(shipTypeStr))
				{
				//Populate New Shipping Information 
				outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(selenium,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			else if(("Ship_3rdParty").equals(shipTypeStr))
				{
				//Populate New 3rd Party Shipping Information 
				outputMap = estoreUtil.populateCD_Hardware_New_3rdPartyShippingInfo(selenium,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			 }
			else 
				{
				estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
				}
		if(("creditcard").equals(paymentTypeStr))
		{
			if(("Yes").equals(billAddressNewStr)){
				//Load the Payment Method Page with new Billing Profile 
				
				estoreUtil.populateCreditCard_ExistingBillingProfileNew(selenium,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
			}
			else{
				///Load the Payment Method Page  
				//estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
				//Populate the Payment Information
				estoreUtil.populateCreditCardPaymentInformation(selenium,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
			}
		}else if(("EFT").equals(paymentTypeStr)){
			//Load the Payment Method Page 
			//estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			estoreUtil.populateExistingUser_EFTPaymentInformation(selenium,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
		} 
			
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	 * Submits All Order Types CD/Hardware/Download/Payroll/Subscription can be placed from offering page <br>
	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
	 * @param estoreURL URL for the Estore Offering page 
	 * @param item The item number of the offering being purchased. This has to be the exact value in the combobox on the estore offering page (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
	 * @param userId The userId used to sign into the existing account
	 * @param password The password used to sign into the existing account
	 * @param productTypeStr; To select the product option String productType="IntuitQB_POS_Download_Reinstall";
	 * @param cpcIndividualBankStr  CPC product quantity selection max 25.
	 * @param cpcIndividualUsageStr CPC product quantity selection max 50.
	 * @param paymentTypeStr creditcard or EFT to enter that flow and create a new one.
	 * @param accountTypeStr  EFT account type Checking or Savings.
	 * @param accountNumberStr EFT account number.
	 * @param routingNumberStr EFT routing number.
	 * @param shipAddressTypeStr ship address type to be created Shipping_StandardUSA Shipping_POBox_USA Shipping_APOBox_USA Shipping_StandardCANADA Shipping_POBox_CANADA Shipping_USPossession_USA  
	 * @param shipTypeStr for ship type String shipType="Ship_Normal"; or String shipType="Ship_Normal" ;
	 * @param shipAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
	 * @param billAddressTypeStr ship address type to be created Billing_StandardUSA Billing_POBox_USA Billing_APOBox_USA Billing_StandardCANADA Billing_POBox_CANADA Billing_USPossession_USA
	 * @param billAddressNewStr to determine if new address needs to be created or not Vales are Yes or No 
	 * @param companyAddressStr ship address type to be created Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
	 */
	public HashMap<String,Object> submitCD_Download_MultipleOrders_ExistingUser_DefaultItemAttributes(Object estoreURL,Object maxPageLoadWaitTimeInMinutes,Object userFirstName,Object userLastName,Object creditCardNumber,Object creditCardMonth,Object creditCardYear,Object shipAddressType,Object shipType,Object companyAddress,Object billAddressType,Object productType,Object cpcIndividualBank,Object cpcIndividualUsage,Object paymentType,Object accountType,Object accountNumber,Object routingNumber,Object shipAddressNew,Object billAddressNew, Object userId,Object password,Object... items)
																							
	{
		
		String estoreURLStr = (String)estoreURL;
		//String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		//String newUserEmailStr = (String)newUserEmail; 
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		//String companyAddressStr = (String)companyAddress;
		
		String productTypeStr = (String)productType;
		String cpcIndividualBankStr = (String)cpcIndividualBank; 
		String cpcIndividualUsageStr = (String)cpcIndividualUsage;
		String paymentTypeStr = (String)paymentType;
		String accountTypeStr = (String)accountType;
		String accountNumberStr = (String)accountNumber;
		String routingNumberStr = (String)routingNumber;
		String shipAddressTypeStr = (String)shipAddressType;
		String shipTypeStr = (String)shipType; 
		String shipAddressNewStr = (String)shipAddressNew;
		String billAddressTypeStr = (String)billAddressType;
		String billAddressNewStr = (String)billAddressNew;
		//String TestCase_NameStr = (String)TestCase_Name ;
		//String estoreUserTypeStr = (String)estoreUserType;
		
		//Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		String TestCase_NameStr ="null";
		
		if(("Yes").equals(shipAddressNew) && ("Yes").equals(billAddressNew))
		{
			 TestCase_NameStr = "Existing User |  Multiple Product Order CD & Download| " +shipType+"  "+ shipAddressTypeStr + " | " + paymentTypeStr +" "+billAddressType+" | ";
		}
		else if(("Yes").equals(shipAddressNew) && ("No").equals(billAddressNew))
		{
			 TestCase_NameStr = "Existing User |  Multiple Product Order CD & Download | " +shipType+"  "+ shipAddressTypeStr + " | " + paymentTypeStr +" | ";
		}
		else if(("No").equals(shipAddressNew) && ("No").equals(billAddressNew))
		{
			 TestCase_NameStr = "Existing User |  Multiple Product Order CD & Download | " + paymentTypeStr +" | ";
		}
		else 
		{
			 TestCase_NameStr = "Existing User |  Multiple Product Order CD & Download | " + paymentTypeStr +" | ";
		}
		TestRun.startSuite(TestCase_NameStr);
		
		
		
		try
		{
			selenium.windowFocus();
			selenium.windowMaximize();
			
			estoreUtil.cleanExistingUserShoppingCartItems( selenium, estoreURLStr, userIdStr, passwordStr, maxPageLoadWaitTimeInMilliseconds);
			//Launch Offering Page
			estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
						
			for(int i=0;i<items.length;i++)
			{
				
				outputMap =estoreUtil.addOfferingToShoppingCart(selenium,items[i].toString(),maxPageLoadWaitTimeInMilliseconds,outputMap);
							
				if(i<items.length-1)
				{
					selenium.goBack();
					selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
				}
			}
			//Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			if(("Yes").equals(shipAddressNewStr) && (selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true))
			{
			if(("Ship_Normal").equals(shipTypeStr))
				{
				//Populate New Shipping Information 
				outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(selenium,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			else if(("Ship_3rdParty").equals(shipTypeStr))
				{
				//Populate New 3rd Party Shipping Information 
				outputMap = estoreUtil.populateCD_Hardware_New_3rdPartyShippingInfo(selenium,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			 }
			else 
				{
				estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
				}
		if(("creditcard").equals(paymentTypeStr))
		{
			if(("Yes").equals(billAddressNewStr)){
				//Load the Payment Method Page with new Billing Profile 
				
				estoreUtil.populateCreditCard_ExistingBillingProfileNew(selenium,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
			}
			else{
				estoreUtil.populateCreditCardPaymentInformation(selenium,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
			}
		}else if(("EFT").equals(paymentTypeStr)){
			//Load EFT page
			estoreUtil.populateExistingUser_EFTPaymentInformation(selenium,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
		} 
			
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	

	
// End Added by Shankar	




}
