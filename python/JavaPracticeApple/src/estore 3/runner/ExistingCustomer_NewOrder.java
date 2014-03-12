package initiate.estore.runner;

import initiate.estore.util.EstoreUtil;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import selenium.util.SeleniumUtil;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;

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
	private NewCustomer_NewOrder newCustomerNewOrder;
	
	
	public ExistingCustomer_NewOrder()
	{
		sLog = Logger.getInstance();
		seleniumUtil = new SeleniumUtil();
		estoreUtil = new EstoreUtil();
		newCustomerNewOrder = new NewCustomer_NewOrder();
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
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Non-Shippable Order | Default Item Attributes");
		
		try
		{
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Non-Shippable Bundle Order | Default Item Attributes");
		
		try
		{
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap =estoreUtil.addBundleOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Multiple Non-Shippable Same Lines Order | Default Item Attributes | Credit Card");
		
		try
		{
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add items to the cart	
			for(int i=0;i<items.length;i++)
			{
				
				if(items[i].toString().contains("#"))
				{
					outputMap = estoreUtil.updateOfferingAndAddToShoppingCart(driver,items[i].toString(),maxPageLoadWaitTimeInMilliseconds,outputMap);  //This is the new method which handles the attribute
				}
				else
				{
					outputMap = estoreUtil.addOfferingToShoppingCart(driver,items[i].toString(),maxPageLoadWaitTimeInMilliseconds,outputMap);
				}
				
				if(i<items.length-1)
				{
					driver.navigate().back();
					//selenium.goBack();
					//selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
				}
			}
			
			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	public HashMap<String,Object> submitOrder_ExistingAccount(Object estoreURL,Object maxPageLoadWaitTimeInMinutes,Object newUserEmail,Object userFirstName,Object userLastName,Object companyAddress,Object shipAddressNew,Object shipAddressType, Object shipType,Object billAddressNew,Object billAddressType,Object paymentType,Object creditCardNumber,Object creditCardMonth,Object creditCardYear,Object accountType,Object accountNumber,Object routingNumber,Object item, Object productType, Object cpcIndividualBank, Object cpcIndividualUsage)
	{
		//Required Conversion of parameter types
						
		String estoreURLStr = (String)estoreURL;
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
				
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
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
			// Create new account to use it as Existing User
			//outputMap =estoreUtil.CreateNewAccount_To_UseAs_ExistingUserCC(driver,itemStr, maxPageLoadWaitTimeInMilliseconds,estoreURLStr,newUserEmailStr, companyAddressStr,userFirstNameStr, userLastNameStr,  shipAddressTypeStr, shipTypeStr, creditCardNumberStr,  creditCardMonthStr, creditCardYearStr, paymentTypeStr,  accountTypeStr,  routingNumberStr,accountNumberStr,outputMap);
			HashMap<String,Object> output = new HashMap<String, Object>();
			output = newCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes_CreditCard(estoreURLStr, itemStr, maxPageLoadWaitTimeInMinutesStr, newUserEmailStr, userFirstNameStr, userLastNameStr, creditCardNumberStr, creditCardMonthStr, creditCardYearStr);
			System.out.println((String)output.get("SignInUsername")+"-"+(String)output.get("SignInPassword"));
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
			//outputMap =estoreUtil.addOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			//Add Offering to Shopping Cart
			if(("CPC_Bundle_Tax_Online_2011").equals(productTypeStr))
			{
				outputMap =estoreUtil.addUsageBundleOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds, cpcIndividualBankStr, cpcIndividualUsageStr,outputMap);
			}
			else if(("IntuitQB_POS_Download_Reinstall").equals(productTypeStr))
			{
				outputMap =estoreUtil.addDownload_ReInstallOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			else if(("IntuitQB_Payroll_3User").equals(productTypeStr))
			{
				outputMap =estoreUtil.addPayRoll_3EmployeesOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			else
			{
				outputMap =estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
		
			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			outputMap = estoreUtil.signInAsExistingUser(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
		
			if(("Yes").equals(shipAddressNewStr) && estoreUtil.isElementPresent(driver, "id", "shippingAddressSameAsCompanyAddress"))//(selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
			{
				if(("Ship_Normal").equals(shipTypeStr))
				{
					//Populate New Shipping Information 
					outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
				else if(("Ship_3rdParty").equals(shipTypeStr))
				{
					//Populate New 3rd Party Shipping Information 
					outputMap = estoreUtil.populateCD_Hardware_New_3rdPartyShippingInfo(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			}
		
			if(("creditcard").equals(paymentTypeStr)) //&& (selenium.isElementPresent("link=Add a new card")==true))
			{
				if(("Yes").equals(billAddressNewStr))
				{
					//Load the Payment Method Page with new Billing Profile 
					estoreUtil.launchNew_ExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
					estoreUtil.populateCreditCard_ExistingBillingProfileNew(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
				
				}
				else
				{
					///Load the Payment Method Page  
					estoreUtil.launchNewPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
					//Populate the Payment Information
					estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
				}
			}
			else if(("EFT").equals(paymentTypeStr) && estoreUtil.isElementPresent(driver, "linktext", "Add a new bank account"))//(selenium.isElementPresent("link=Add a new bank account")==true)
			{
				//Load the Payment Method Page 
				estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
				//Populate the Payment Information
				estoreUtil.populateExistingUser_EFTPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
			} 
		
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	public HashMap<String,Object> submitShippableOrder_EditExistingUserShippingAddress(Object estoreURL,Object maxPageLoadWaitTimeInMinutes,Object newUserEmail,Object userFirstName,Object userLastName,Object companyAddress,Object editAdddress,Object shipAddressNew,Object shipAddressType,Object shipType,Object billAddressNew,Object billAddressType,Object paymentType,Object creditCardNumber,Object creditCardMonth,Object creditCardYear,Object accountType,Object accountNumber,Object routingNumber,Object userId ,Object password,Object item,Object productType,Object cpcIndividualBank,Object cpcIndividualUsage)																  		
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		String editAdddressStr = (String)editAdddress;
		
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
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		String TestCase_NameStr ="null";
		TestCase_NameStr = "Existing User | Edit "+editAdddressStr+" Address | " + itemStr + " | " + paymentTypeStr +" | ";

		TestRun.startSuite(TestCase_NameStr);
		
		try
		{
			estoreUtil.cleanExistingUserShoppingCartItems(driver, estoreURLStr, userIdStr, passwordStr, maxPageLoadWaitTimeInMilliseconds);
			
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
						
			if(("CPC_Bundle_Tax_Online_2011").equals(productTypeStr))
			{
				outputMap = estoreUtil.addUsageBundleOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds, cpcIndividualBankStr, cpcIndividualUsageStr,outputMap);										
			}
			else if(("IntuitQB_POS_Download_Reinstall").equals(productTypeStr))
			{
				outputMap = estoreUtil.addDownload_ReInstallOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);																	
			}
			else if(("IntuitQB_Payroll_3User").equals(productTypeStr))
			{
				outputMap = estoreUtil.addPayRoll_3EmployeesOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			else
			{
				outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			
			if(estoreUtil.isElementPresent(driver, "css", "input.coButtonNoSubmit.coCheckoutAndContinue"))
			{
				driver.findElement(By.cssSelector("input.coButtonNoSubmit.coCheckoutAndContinue")).click();
			}
			
//			//Launch the Sign In Page
//			estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
//			
//			//Sign In Existing User
//			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			if(("Yes").equals(shipAddressNewStr) && estoreUtil.isElementPresent(driver, "id", "shippingAddressSameAsCompanyAddress"))//(selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
			{
				if(("Ship_Normal").equals(shipTypeStr))
				{
					//Populate New Shipping Information 
					outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
				else if(("Ship_3rdParty").equals(shipTypeStr))
				{
					//Populate New 3rd Party Shipping Information 
					outputMap = estoreUtil.populateCD_Hardware_New_3rdPartyShippingInfo(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			}
			else if(("creditcard").equals(paymentTypeStr) || ("EFT").equals(paymentTypeStr)) 
			{
				estoreUtil.launchNew_ExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			}
			else if(("No").equals(paymentTypeStr))
			{
				estoreUtil.launch_SkipPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			}
			
			if(("creditcard").equals(paymentTypeStr))
			{
				if(("Yes").equals(billAddressNewStr))
				{
					//Load the Payment Method Page with new Billing Profile 
					estoreUtil.populateCreditCard_ExistingBillingProfileNew(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
				}
				else
				{
					///Load the Payment Method Page  
					//estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
					//Populate the Payment Information
					estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
				}
			}
			else if(("EFT").equals(paymentTypeStr))
			{
				//Load the Payment Method Page 
				//estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
				estoreUtil.populateExistingUser_EFTPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
			} 
			
			if(("Shipping_normal").equals(editAdddressStr))
			{
				outputMap = estoreUtil.edit_ShippingAddress_Normal(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				//outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(selenium,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
			}
			else if(("Shipping_3rdPaty").equals(editAdddressStr))
			{
				outputMap = estoreUtil.edit_3rdPartyShippingAddress_Normal(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
			}
			else if(("Billing").equals(editAdddressStr))
			{
				//outputMap = estoreUtil.edit_CC_BillingAddressInformation(selenium,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
				outputMap = estoreUtil.edit_CC_BillingAddressInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);	
			}	
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	public HashMap<String,Object> submitShippable_ExistingUser_Order_DefaultItemAttributes(Object estoreURL,Object maxPageLoadWaitTimeInMinutes,Object newUserEmail,Object userFirstName,Object userLastName,Object companyAddress,Object shipAddressNew,Object shipAddressType,Object shipType,Object billAddressNew,Object billAddressType,Object paymentType,Object creditCardNumber,Object creditCardMonth,Object creditCardYear,Object accountType,Object accountNumber,Object routingNumber,Object userId ,Object password,Object item,Object productType,Object cpcIndividualBank,Object cpcIndividualUsage)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		
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
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
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
			estoreUtil.cleanExistingUserShoppingCartItems(driver, estoreURLStr, userIdStr, passwordStr, maxPageLoadWaitTimeInMilliseconds);
			
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
						
			if(("CPC_Bundle_Tax_Online_2011").equals(productTypeStr))
			{
				outputMap = estoreUtil.addUsageBundleOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds, cpcIndividualBankStr, cpcIndividualUsageStr,outputMap);										
			}
			else if(("IntuitQB_POS_Download_Reinstall").equals(productTypeStr))
			{
				outputMap = estoreUtil.addDownload_ReInstallOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);																	
			}
			else if(("IntuitQB_Payroll_3User").equals(productTypeStr))
			{
				outputMap = estoreUtil.addPayRoll_3EmployeesOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			else
			{
				outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
			}
			
			driver.findElement(By.cssSelector("input.coButtonNoSubmit.coCheckoutAndContinue")).click();
			
//			//Launch the Sign In Page
//			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
//			
//			//Sign In Existing User
//			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			if(("Yes").equals(shipAddressNewStr) && estoreUtil.isElementPresent(driver, "id", "shippingAddressSameAsCompanyAddress"))//(selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
			{
				if(("Ship_Normal").equals(shipTypeStr))
				{
					//Populate New Shipping Information 
					outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
				else if(("Ship_3rdParty").equals(shipTypeStr))
				{
					//Populate New 3rd Party Shipping Information 
					outputMap = estoreUtil.populateCD_Hardware_New_3rdPartyShippingInfo(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			}
			else if(("creditcard").equals(paymentTypeStr) || ("EFT").equals(paymentTypeStr)) 
			{
				estoreUtil.launchNew_ExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			}
			else if(("No").equals(paymentTypeStr))
			{
				estoreUtil.launch_SkipPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			}
		
			if(("creditcard").equals(paymentTypeStr))
			{
				if(("Yes").equals(billAddressNewStr))
				{
					//Load the Payment Method Page with new Billing Profile 
					estoreUtil.populateCreditCard_ExistingBillingProfileNew(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
				}
				else
				{
					///Load the Payment Method Page  
					//estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
					//Populate the Payment Information
					estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
				}
			}
			else if(("EFT").equals(paymentTypeStr))
			{
				//Load the Payment Method Page 
				//estoreUtil.launchNew_ExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
				estoreUtil.populateExistingUser_EFTPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
			} 
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	public HashMap<String,Object> submitCD_Download_MultipleOrders_ExistingUser_DefaultItemAttributes(Object estoreURL,Object maxPageLoadWaitTimeInMinutes, Object newUserEmail,Object userFirstName,Object userLastName,Object companyAddress,Object shipAddressNew,Object shipAddressType,Object shipType, Object billAddressNew, Object billAddressType, Object paymentType, Object creditCardNumber, Object creditCardMonth, Object creditCardYear,Object accountType,Object accountNumber,Object routingNumber,Object userId,Object password,Object cpcIndividualBank, Object cpcIndividualUsage,Object productType, Object... items)
	{
		
		String estoreURLStr = (String)estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		String paymentTypeStr = (String)paymentType;
		String accountTypeStr = (String)accountType;
		String accountNumberStr = (String)accountNumber;
		String routingNumberStr = (String)routingNumber;
		String shipAddressTypeStr = (String)shipAddressType;
		String shipTypeStr = (String)shipType; 
		String shipAddressNewStr = (String)shipAddressNew;
		String billAddressTypeStr = (String)billAddressType;
		String billAddressNewStr = (String)billAddressNew;
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
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
			estoreUtil.cleanExistingUserShoppingCartItems(driver, estoreURLStr, userIdStr, passwordStr, maxPageLoadWaitTimeInMilliseconds);
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
						
			//Add items to the cart	
			for(int i=0;i<items.length;i++)
			{
				
				if(items[i].toString().contains("#"))
				{
					outputMap = estoreUtil.updateOfferingAndAddToShoppingCart(driver,items[i].toString(),maxPageLoadWaitTimeInMilliseconds,outputMap);  //This is the new method which handles the attribute
				}
				else
				{
					outputMap = estoreUtil.addOfferingToShoppingCart(driver,items[i].toString(),maxPageLoadWaitTimeInMilliseconds,outputMap);
				}
				
				if(i<items.length-1)
				{

					driver.navigate().back();//selenium.goBack();
					//selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
				}
			}
			
			if(estoreUtil.isElementPresent(driver, "css", "input.coButtonNoSubmit.coCheckoutAndContinue"))
			{
				driver.findElement(By.cssSelector("input.coButtonNoSubmit.coCheckoutAndContinue")).click();
			}
			
//			//Launch the Sign In Page
//			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
//			
//			//Sign In Existing User
//			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			if(("Yes").equals(shipAddressNewStr) && estoreUtil.isElementPresent(driver, "id", "shippingAddressSameAsCompanyAddress"))//(selenium.isElementPresent("id=shippingAddressSameAsCompanyAddress")==true)
			{
			if(("Ship_Normal").equals(shipTypeStr))
				{
				//Populate New Shipping Information 
				outputMap = estoreUtil.populateCD_Hardware_NewShippingInformation(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			else if(("Ship_3rdParty").equals(shipTypeStr))
				{
				//Populate New 3rd Party Shipping Information 
				outputMap = estoreUtil.populateCD_Hardware_New_3rdPartyShippingInfo(driver,userFirstNameStr,userLastNameStr,maxPageLoadWaitTimeInMilliseconds, shipAddressTypeStr, shipTypeStr, outputMap);
				}
			 }
			else if(("creditcard").equals(paymentTypeStr) || ("EFT").equals(paymentTypeStr)) 
				{
				estoreUtil.launchNew_ExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
				}
			else if(("No").equals(paymentTypeStr))
				{
				estoreUtil.launch_SkipPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
				}
		if(("creditcard").equals(paymentTypeStr))
			{
			if(("Yes").equals(billAddressNewStr)){
				//Load the Payment Method Page with new Billing Profile 
				
				estoreUtil.populateCreditCard_ExistingBillingProfileNew(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
			}
			else{
				estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
			}
		}else if(("EFT").equals(paymentTypeStr)){
			//Load EFT page
			estoreUtil.populateExistingUser_EFTPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,accountTypeStr,accountNumberStr,routingNumberStr);
		} 
			
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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

// Added by Samar
	
	public HashMap<String,Object> updateBillingProfileAtReviewOrderPage(Object estoreURL,Object item,Object creditCardNumber, Object creditCardMonth,
			Object creditCardYear, Object billAddressType, Object maxPageLoadWaitTimeInMinutes,Object userId, Object password)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		String billAddressTypeStr = (String)billAddressType;
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Edit Billing Profile CC from review order page & add new CC");
		
		try
		{
			
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(driver,maxPageLoadWaitTimeInMilliseconds);		//***blur				
			
			//Click on edit review page and it backs to payment page
			estoreUtil.clickUpdateBPLinkAtReviewPage(driver, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
			//Populates the CC field with user supplied CC info
			estoreUtil.populateCreditCard_ExistingBillingProfileNew(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr,billAddressTypeStr, outputMap);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			
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
	

	//Update user billing profile from review order page with alternate yet same card type info
	
	public HashMap<String,Object> updateBillingProfileWithUserPresentCardType(Object estoreURL,Object item,Object userId, Object password, Object maxPageLoadWaitTimeInMinutes)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		/*String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		String billAddressTypeStr = (String)billAddressType;*/
		HashMap<String,Object> output = new HashMap<String, Object>();
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Edit Billing Profile CC from review order page & add new CC as user's existing CC");
		
		try
		{
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(driver,maxPageLoadWaitTimeInMilliseconds);						
			
			//Click on edit review page and it backs to payment page
			output = estoreUtil.clickUpdateBPLinkAtReviewPage(driver, maxPageLoadWaitTimeInMilliseconds, outputMap);
			String cardType = (String)output.get("CardType");
			String cardEndingWith = (String)output.get("CardEndingWith");
			
			//Populates the CC using user's info
			outputMap = estoreUtil.updatePaymentWithExistingInfo(driver, cardType, cardEndingWith, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
			
			
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

	
	//Update EFT billing profile from review order page
	
	public HashMap<String,Object> updateEFTBillingProfileFromReviewPage(Object estoreURL,Object item,Object userId, Object password, Object maxPageLoadWaitTimeInMinutes)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		/*String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear; 
		String billAddressTypeStr = (String)billAddressType;*/
		HashMap<String,Object> output = new HashMap<String, Object>();
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Edit Billing Profile CC from review order page & add new CC as user's existing CC");
		
		try
		{
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingEFTPaymentProfile(driver,maxPageLoadWaitTimeInMilliseconds);						
			
			//Click on edit review page and it backs to payment page
			output = estoreUtil.clickUpdateBPLinkAtReviewPage(driver, maxPageLoadWaitTimeInMilliseconds, outputMap);
			String cardType = (String)output.get("CardType");
			String cardEndingWith = (String)output.get("CardEndingWith");
			
			//Populates the CC using user's info
			
			outputMap = estoreUtil.populateExistingUser_EFTPaymentInformationWithAlternateOption(driver, cardType, cardEndingWith, maxPageLoadWaitTimeInMilliseconds, outputMap);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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

	
	//By Samar for update an offering and submit
	
	public HashMap<String,Object> submitAnUpdatedOffering(Object estoreURL,Object item,Object userId, Object password, Object maxPageLoadWaitTimeInMinutes)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		
		
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
		
		TestRun.startSuite("Existing Customer | Non-Shippable Order | Default Item Attributes");
		
		try
		{
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.updateOfferingAndAddToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchExistingPaymentMethodPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.selectExistingPaymentProfile(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	
	
	public HashMap<String,Object> submitNonShippableOrder_DefaultItemAttributes_TaxExempt(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object userId, Object password,Object creditCardNumber,Object creditCardMonth,Object creditCardYear)
	{
		
		String estoreURLStr = (String)estoreURL;
		String itemStr = (String)item;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String userIdStr = (String)userId;
		String passwordStr = (String)password;
		String creditCardNumberStr =(String)creditCardNumber;
		String creditCardMonthStr=(String)creditCardMonth;
		String creditCardYearStr=(String)creditCardYear;
		
		//Required---------------------------------------------------------------------------------------------
		WebDriver driver = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String,Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		
		TestRun.startSuite("Existing Customer | Non-Shippable Order | Default Item Attributes");
		
		try
		{
			//Launch Offering Page
			estoreUtil.launchOfferingPage(driver,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
				
			//Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(driver,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);

			//Launch the Sign In Page
			estoreUtil.launchSignInPage(driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Sign In Existing User
			estoreUtil.signIn(driver,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
			
			//Load the Payment Method Page 
			estoreUtil.launchNewPaymentMethodPageforTaxExemptCustomer(estoreURL,driver,maxPageLoadWaitTimeInMilliseconds);
			
			//Choose the Payment Profile
			estoreUtil.populateCreditCardPaymentInformation(driver,maxPageLoadWaitTimeInMilliseconds,creditCardNumberStr,creditCardMonthStr,creditCardYearStr);
			
			//Submit the Order
			outputMap = estoreUtil.submitOrder(driver,maxPageLoadWaitTimeInMilliseconds,outputMap);
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
	


	
	
//	public HashMap<String,Object> singleOrder_Apply_Promotion(Object estoreURL,Object item,Object userId, Object password, Object promotionCode, Object maxPageLoadWaitTimeInMinutes)
//	{
//		
//		String estoreURLStr = (String)estoreURL;
//		String itemStr = (String)item;
//		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
//		String userIdStr = (String)userId;
//		String passwordStr = (String)password;
//		String promotionCodeStr = (String)promotionCode;
//		
//		//Required---------------------------------------------------------------------------------------------
//		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
//		HashMap<String,Object> outputMap = new HashMap<String, Object>();
//		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		//outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
//		//-----------------------------------------------------------------------------------------------------
//		
//		TestRun.startSuite("Existing Customer | Non-Shippable Order | Promotional Code | Default Item Attributes");
//		
//		try
//		{
//			selenium.windowFocus();
//			selenium.windowMaximize();
//			
//			//Launch Offering Page
//			estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
//				
//			//Add Offering to Shopping Cart
//			outputMap = estoreUtil.addOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds,outputMap);
//
//			//Launch the Sign In Page
//			estoreUtil.launchSignInPage(selenium,maxPageLoadWaitTimeInMilliseconds);
//			
//			//Sign In Existing User
//			estoreUtil.signIn(selenium,userIdStr,passwordStr,maxPageLoadWaitTimeInMilliseconds);
//			
//			//Load the Payment Method Page 
//			estoreUtil.launchExistingPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);
//			
//			//Choose the Payment Profile
//			estoreUtil.selectExistingPaymentProfile(selenium,maxPageLoadWaitTimeInMilliseconds);
//			
//			//Delete any existing promotion			
//			estoreUtil.deletePromotion(selenium, maxPageLoadWaitTimeInMilliseconds);
//			
//			//Applying the new promotion
//			estoreUtil.applyPromotionOnly(selenium, promotionCodeStr, maxPageLoadWaitTimeInMilliseconds);
//			
//			estoreUtil.isPromotionFoundTrue(selenium);
//			
//			//Submit the Order
//			outputMap = estoreUtil.submitOrder(selenium,maxPageLoadWaitTimeInMilliseconds,outputMap);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			sLog.info(e.getStackTrace().toString());
//		}
//		finally
//		{
//			seleniumUtil.tearDownSelenium();
//			TestRun.endSuite();
//		}
//		
//		return outputMap;
//	}
	
}
