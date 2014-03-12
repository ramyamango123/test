package initiate.apd.runner;

import initiate.apd.util.APDUtil;

import java.util.Arrays;
import java.util.HashMap;

import selenium.util.SeleniumUtil;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;
import com.thoughtworks.selenium.Selenium;

/**  
 * Selenium-Automated APD Order Flows for a New Customer<br>
 * 
 * @author  Jeffrey Walker
 * @version 1.0.0 
 */ 
public class NewCustomer_NewOrder
{
	SeleniumUtil seleniumUtil;
	private static Logger sLog;
	private APDUtil apdUtil;
	//private String ;

	public NewCustomer_NewOrder()
	{
		sLog = Logger.getInstance();
		seleniumUtil = new SeleniumUtil();
		apdUtil = new APDUtil();
	}


	/**
	 * Submits Order with default item attributes <br>
	 * using an EFT Billing Profile for a New Customer Account. <br>
	 * 
	 * @param estoreURL
	 *            URL for the Estore Offering page
	 * @param item
	 *            The item number of the offering being purchased. This has to
	 *            be the exact value in the combobox on the CPC Bundle offering
	 *            page (ex:
	 *            "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION"
	 *            )
	 * @param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out.
	 * @param newUserEmail
	 *            Email for the new user
	 * @param userFirstName
	 *            First Name of the new contact
	 * @param userLastName
	 *            Last Name of the new contact
	 * @param accountType
	 *            Account Type ("Checking" or "Savings")
	 * @param routingNumber
	 *            The bank routing number
	 * @param accountNumber
	 *            The bank account number
	 * @return Output Map with values for the following keys ("AccountName" and
	 *         "OrderNumber")
	 */

	public HashMap<String, Object> submitOrder2010TYAttributeEFT(Object apdURL, Object estoreURL, Object estoreDbUrl, Object item, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object accountType, Object bankAccName, Object routingNumber, Object accountNumber, 	
			String segmentName, String productName, String accountPageName)

			{

		// Required Conversion of parameter types
		String apdURLStr = (String) apdURL;
		String estoreURLStr = (String) estoreURL;
		String estoreDbUrlStr = (String) estoreDbUrl;
		String itemStr = (String) item;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String accountTypeStr = (String) accountType;
		String bankAccNameStr = (String) bankAccName;
		String routingNumberStr = (String) routingNumber;
		String accountNumberStr = (String) accountNumber;
		String apdProseiresURL = "https://sboqa.proseries.intuit.com/myproseries/login/";
		String apdLacerteURL = "https://sboqa.lacerte.intuit.com/myaccount/login/"; 

		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		TestRun.startSuite("New Customer | Submit Order for REP/PPR/UNlIMTED SEGMENT ITEMS | 2010 Tax Year Attribute | EFT"
				+ "\n\t" + stack[2].getMethodName());


		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// Launch Offering Page
			apdUtil.launchOfferingPage(selenium, estoreURLStr, maxPageLoadWaitTimeInMilliseconds);


			// Add Offering to Shopping Cart
			outputMap = apdUtil.addOfferingToShoppingCart(selenium, itemStr, maxPageLoadWaitTimeInMilliseconds,estoreURLStr, outputMap);


			// Launch the Sign In Page
			apdUtil.launchSignInPage(selenium, maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			apdUtil.createNewUser(selenium, newUserEmailStr, maxPageLoadWaitTimeInMilliseconds);


			// Populate Company Information
			outputMap = apdUtil.populateNewCompanyInformation(selenium,userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);

			// Load the Payment Method Page
			apdUtil.launchNewPaymentMethodPage(selenium, maxPageLoadWaitTimeInMilliseconds);


			// Populate the Payment Information
			apdUtil.populateEFTPaymentInformation(selenium, maxPageLoadWaitTimeInMilliseconds, accountTypeStr,
					accountNumberStr, routingNumberStr);

			// Submit the Order
			outputMap = apdUtil.submitOrder(selenium, maxPageLoadWaitTimeInMilliseconds, segmentName, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrlStr, MDMDbUserName, 
					profileDbUserName, estoreDbPassword, outputMap);

			// Retrieve CAN from Account Management page
			outputMap = apdUtil.retrieveCANFromAccountPage(selenium, maxPageLoadWaitTimeInMilliseconds, segmentName, productName, accountPageName, apdURLStr, outputMap);
			
			apdUtil.invalidateCacheInMDMDB(selenium,  maxPageLoadWaitTimeInMilliseconds);
			apdUtil.invalidateCacheInProfileDB(selenium, maxPageLoadWaitTimeInMilliseconds);

		} catch (Exception e) {
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		} finally {
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}

		return outputMap;
			}


	/**
	 *Online renewal flow for REP/PPR segment items (Proseries Basic - 1099935, Proseries PPR - 1099907, Lacerte REP - 1099967)
	 * @param apdURL
	 *            APD URL for the ProSeries/Lacerte domain
	 *@param siebelDbUserName
	 *            Siebel DB username
	 *@param siebelDbPassword
	 *           Siebel DB password
	 *@param siebelDbUrl
	 *            Siebel DB URL
	 *@param estoreDbUrl
	 *            MDM/Profile DB URL
	 *@param estoreDbPassword
	 *            MDM/Profile DB password
	 *@param MDMDbUserName
	 *            MDM DB username
	 *@param profileDbUserName
	 *            Profile DB username           
	 *@param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out
	 *@param newUserEmail
	 *            Email for the new user
	 *@param userFirstName
	 *             First Name of the new contact
	 *@param userLastName
	 *            Last Name of the new contact
	 *@param creditCardNumber
	 *            Credit Card number
	 *@param creditCardMonth
	 *            Credit Card Expiration Month
	 *@param creditCardYear
	 *             Credit Card Expiration Year
	 *@param lastThreeDigitsAccNo
	 *            Last three digits of account number (EFT payment)
	 **@param lastFourDigitsRoutingNo 
	 *            Last four digits of routing number (EFT payment
	 *@param bankAcctName
	 *           Name of the bank account holder
	 *@param itemName
	 *            ProSeries/Lacerte item name
	 *@param page
	 *            String value assigned to this variable in-order to perform OLR flow with/without auto-renewal signup for all REP/PPR segment items
	 *@param productName
	 *            Name of the product(Lacerte/ProSeries) 
	 *@param accountPageName
	 *            Account Management page url's relative path name (Proseries item - "myproseries"  and lacerte item - "myaccount") 
	 *@param segmenttName
	 *             (REP/PPR/UL) ProSeries/Lacerte segment item name
	 *@param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out.
	 *@param outputMap Map that will be populated with important information
	 */

	public HashMap<String,Object> onlineRenewal(Object apdURL, Object siebelDbUserName, Object siebelDbPassword, Object siebelDbUrl,
			Object estoreDbUrl, Object estoreDbPassword, Object MDMDbUserName, Object profileDbUserName, 
			Object maxPageLoadWaitTimeInMinutes,Object newUserEmail, Object userFirstName, Object userLastName, Object creditCardNumber, Object creditCardMonth,
			Object creditCardYear, Object paymentType, Object lastThreeDigitsAccNo, Object lastFourDigitsRoutingNo, Object bankAcctName, String itemName, String page,
			String productName, String accountPageName, String segmenttName, HashMap<String, Object> outputMap)
			{



		//Required Conversion of parameter types
		String apdURLStr = (String)apdURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String)newUserEmail;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear;
		String paymentTypeStr = (String)paymentType;
		String lastThreeDigitsAccNoStr = (String)lastThreeDigitsAccNo;
		String lastFourDigitsRoutingNoStr = (String)lastFourDigitsRoutingNo;
		String bankAccNameStr = (String)bankAcctName;
		String siebelDbUserNameStr = (String)siebelDbUserName;
		String siebelDbPasswordStr = (String)siebelDbPassword;
		String siebelDbUrlStr = (String)siebelDbUrl;
		String estoreDbUrlStr = (String)estoreDbUrl;
		String estoreDbPasswordStr =  (String)estoreDbPassword;
		String MDMDbUserNameStr = (String)MDMDbUserName;
		String profileDbUserNameStr = (String)profileDbUserName;



		//Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(apdURLStr);

		String accountName = (String) outputMap.get("AccountName"); 
		String CAN = (String) outputMap.get("CAN");
		String userName = (String) outputMap.get("UserName");

		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		TestRun.startSuite("Existing Customer | Online Renewal Flow for REP/PPR Segment Items | 2010 Tax Year Attribute | EFT"
				+ "\n\t" + stack[2].getMethodName());

		try
		{
			selenium.windowFocus();
			selenium.windowMaximize();
			
			// To debug
//			apdUtil.signIn(selenium, "E2EAutomated3657839", "test123", maxPageLoadWaitTimeInMilliseconds, productName, accountPageName);
//			
//			// To debug
//			outputMap = apdUtil.verifyAccountRenewalPage(selenium, "E2EAutomated3657839", "test123", maxPageLoadWaitTimeInMilliseconds, 
//								itemName, "E2EAutomated3657839", "371362841", siebelDbUserNameStr, siebelDbPasswordStr, siebelDbUrlStr, estoreDbUrlStr, MDMDbUserNameStr, 
//								profileDbUserNameStr, estoreDbPasswordStr, outputMap);
//
//	        // To debug
//			apdUtil.verifyOLRQuotePage(selenium, maxPageLoadWaitTimeInMilliseconds, "E2EAutomated3657839", "371362841", itemName, accountPageName);
			

			//Sign In 
			apdUtil.launchSignInPage(selenium, apdURLStr, maxPageLoadWaitTimeInMilliseconds);
			
			apdUtil.signIn(selenium, userName, "test123", maxPageLoadWaitTimeInMilliseconds, productName, accountPageName);

			//Verify Account Renewal Page
			outputMap = apdUtil.verifyAccountRenewalPage(selenium, userName, "test123", maxPageLoadWaitTimeInMilliseconds, 
					itemName, accountName, CAN, siebelDbUserNameStr, siebelDbPasswordStr, siebelDbUrlStr, estoreDbUrlStr, MDMDbUserNameStr, 
					profileDbUserNameStr, estoreDbPasswordStr, outputMap);

			//Verify OLR Quote Page
			apdUtil.verifyOLRQuotePage(selenium, maxPageLoadWaitTimeInMilliseconds, accountName, CAN, itemName, accountPageName);

			// OLR flow with/without auto renewal sign up
			if (page == "autoRenewalPageWithSignup") {
				apdUtil.addQuoteItemsToShoppingCartWithAutoRenewalSignUp(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, outputMap);
			} else if (page == "autoRenewalPageWithoutSignup") {
				apdUtil.addQuoteItemsToShoppingCartWithoutAutoRenewalSignUp(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, outputMap);
			}

			//Verify Company Information page
			apdUtil.verifyCompanyInformationPage(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, siebelDbUserNameStr,
					siebelDbPasswordStr, siebelDbUrlStr, estoreDbUrlStr, MDMDbUserName, profileDbUserName, estoreDbPassword, outputMap, true);		

			//Verify the Payment Method Page 
			apdUtil.verifyExistingPaymentMethodPage(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, paymentTypeStr, lastThreeDigitsAccNoStr, 
					lastFourDigitsRoutingNoStr, bankAccNameStr, true, "id=buttonContinue");

			//Verify Shopping Cart review page
			apdUtil.verifyShoppingCartReviewPage(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, paymentTypeStr, lastThreeDigitsAccNoStr, 
					lastFourDigitsRoutingNoStr, bankAccNameStr, true, "id=continueCheckOutFromPayment");

			//Submit the Order
			outputMap = apdUtil.submitOLROrder(selenium, maxPageLoadWaitTimeInMilliseconds, segmenttName,siebelDbUserNameStr, siebelDbPasswordStr, siebelDbUrlStr, estoreDbUrlStr, MDMDbUserName, 
					profileDbUserName, estoreDbPassword, outputMap);

			if (page == "autoRenewalPageWithSignup") {

				// Verify Cart Confirmation page for the user who has Opted for AutoRenewal SignUp
				apdUtil.verifyCartConfirmationPageOptedInAutoRenewalSignUp(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, paymentTypeStr, 
						lastThreeDigitsAccNoStr, lastFourDigitsRoutingNoStr, bankAccNameStr, productName);
			} else if (page == "autoRenewalPageWithoutSignup") {

				// Verify Cart Confirmation page for the user who has not Opted for AutoRenewal SignUp
				apdUtil.verifyCartConfirmationPageOptedOutAutoRenewalSignUp(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, paymentTypeStr, lastThreeDigitsAccNoStr,
						lastFourDigitsRoutingNoStr, bankAccNameStr, productName);
			}
			apdUtil.invalidateCacheInMDMDB(selenium,  maxPageLoadWaitTimeInMilliseconds);
			apdUtil.invalidateCacheInProfileDB(selenium, maxPageLoadWaitTimeInMilliseconds);
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
	 *Online renewal flow for Unlimited Segment items (Lacerte Unlimited - 1099978, Proseries 1040 Federal - 1099919)
	 * @param apdURL
	 *            APD URL for the ProSeries/Lacerte domain
	 *@param siebelDbUserName
	 *            Siebel DB username
	 *@param siebelDbPassword
	 *           Siebel DB password
	 *@param siebelDbUrl
	 *            Siebel DB URL
	 *@param estoreDbUrl
	 *            MDM/Profile DB URL
	 *@param estoreDbPassword
	 *            MDM/Profile DB password
	 *@param MDMDbUserName
	 *            MDM DB username
	 *@param profileDbUserName
	 *            Profile DB username           
	 *@param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out
	 *@param newUserEmail
	 *            Email for the new user
	 *@param userFirstName
	 *             First Name of the new contact
	 *@param userLastName
	 *            Last Name of the new contact
	 *@param creditCardNumber
	 *            Credit Card number
	 *@param creditCardMonth
	 *            Credit Card Expiration Month
	 *@param creditCardYear
	 *             Credit Card Expiration Year
	 *@param lastThreeDigitsAccNo
	 *            Last three digits of account number (EFT payment)
	 **@param lastFourDigitsRoutingNo 
	 *            Last four digits of routing number (EFT payment
	 *@param bankAcctName
	 *           Name of the bank account holder
	 *@param itemName
	 *            ProSeries/Lacerte item name
	 *@param page
	 *            String value is assigned to this variable in-order to perform OLR flow with/without auto-renewal signup for all REP/PPR segment items
	 *@param productName
	 *            Name of the product(Lacerte/ProSeries) 
	 *@param  interstitialItem  
	 *          An Item chosen from Interstitial page to validate it's price - "ProSeries Document Management System"            
	 *@param accountPageName
	 *            Account Management page url's relative path name (Proseries item - "myproseries"  and lacerte item - "myaccount") 
	 *@param segmenttName
	 *             (REP/PPR/UL) ProSeries/Lacerte segment item name
	 *@param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out.
	 *@param outputMap Map that will be populated with important information
	 */

	public HashMap<String,Object> onlineRenewalForUnlimitedSegment(Object apdURL, Object siebelDbUserName, Object siebelDbPassword, Object siebelDbUrl,
			Object estoreDbUrl, Object estoreDbPassword, Object MDMDbUserName, Object profileDbUserName,
			Object maxPageLoadWaitTimeInMinutes, Object newUserEmail, Object userFirstName, Object userLastName,
			Object creditCardNumber, Object creditCardMonth,  Object creditCardYear, Object paymentType, Object lastThreeDigitsAccNo,
			Object lastFourDigitsRoutingNo, Object bankAcctName, String itemName, String interstitialItem, String productName,
			String accountPageName, String segmenttName, HashMap<String, Object> outputMap)
			{

		//Required Conversion of parameter types
		String apdURLStr = (String)apdURL;
		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String)newUserEmail;
		String userFirstNameStr = (String)userFirstName;
		String userLastNameStr = (String)userLastName;
		String creditCardNumberStr = (String)creditCardNumber;
		String creditCardMonthStr = (String)creditCardMonth;
		String creditCardYearStr = (String)creditCardYear;
		String paymentTypeStr = (String)paymentType;
		String lastThreeDigitsAccNoStr = (String)lastThreeDigitsAccNo;
		String lastFourDigitsRoutingNoStr = (String)lastFourDigitsRoutingNo;
		String bankAccNameStr = (String)bankAcctName;
		String siebelDbUserNameStr = (String)siebelDbUserName;
		String siebelDbPasswordStr = (String)siebelDbPassword;
		String siebelDbUrlStr = (String)siebelDbUrl;
		String estoreDbUrlStr = (String)estoreDbUrl;
		String estoreDbPasswordStr =  (String)estoreDbPassword;
		String MDMDbUserNameStr = (String)MDMDbUserName;
		String profileDbUserNameStr = (String)profileDbUserName;

		//Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(apdURLStr);

		String accountName = (String) outputMap.get("AccountName"); 
		String CAN = (String) outputMap.get("CAN");
		String userName = (String) outputMap.get("UserName");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
		//-----------------------------------------------------------------------------------------------------

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		TestRun.startSuite("Existing Customer | Online Renewal Flow for Unlimited Segment Items | 2010 Tax Year Attribute | EFT"
				+ "\n\t" + stack[2].getMethodName());


		try
		{
			selenium.windowFocus();
			selenium.windowMaximize();

			//Sign In 
			apdUtil.launchSignInPage(selenium, apdURLStr, maxPageLoadWaitTimeInMilliseconds);
			apdUtil.signIn(selenium, userName, "test123", maxPageLoadWaitTimeInMilliseconds, productName, accountPageName);

			//Renewal 
			outputMap = apdUtil.verifyAccountRenewalPage(selenium, userName, "test123", maxPageLoadWaitTimeInMilliseconds, 
					itemName, accountName, CAN, siebelDbUserNameStr, siebelDbPasswordStr, siebelDbUrlStr, estoreDbUrlStr, MDMDbUserNameStr, 
					profileDbUserNameStr, estoreDbPasswordStr, outputMap);	

			//Add items in Quote page to Shopping Cart
			apdUtil.verifyOLRQuotePage(selenium, maxPageLoadWaitTimeInMilliseconds, accountName, CAN, itemName, accountPageName);

			apdUtil.addQuoteItemsWithInterstitialPageItemToShoppingCart(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, outputMap);


			//Verify Company Information page
			apdUtil.verifyCompanyInformationPage(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, 
					siebelDbUserNameStr, siebelDbPasswordStr, siebelDbUrlStr,estoreDbUrlStr, MDMDbUserNameStr, profileDbUserNameStr,
					estoreDbPasswordStr, outputMap, false);

			apdUtil.validatePriceOnYourCompanyPage(selenium, selenium.getBodyText(), interstitialItem, "");
			TestRun.endTest();

			//Verify the Payment Method Page 
			apdUtil.verifyExistingPaymentMethodPage(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, paymentTypeStr, lastThreeDigitsAccNoStr, 
					lastFourDigitsRoutingNoStr, bankAccNameStr, false,"id=shippingButton");
			apdUtil.validatePriceOnYourPaymentPage(selenium, selenium.getBodyText(), interstitialItem, "");
			TestRun.endTest();

			//Verify Shopping Cart review page
			apdUtil.verifyShoppingCartReviewPage(selenium, maxPageLoadWaitTimeInMilliseconds, itemName, paymentTypeStr, lastThreeDigitsAccNoStr, 
					lastFourDigitsRoutingNoStr, bankAccNameStr, false,"id=continueCheckOutFromPayment");
			apdUtil.validatePriceOnShoppingCartReviewPage(selenium, selenium.getBodyText(), interstitialItem, "");
			TestRun.endTest();							

			//Submit the OLR Order
			outputMap = apdUtil.submitOLROrder(selenium, maxPageLoadWaitTimeInMilliseconds, segmenttName, siebelDbUserNameStr, siebelDbPasswordStr, siebelDbUrlStr, estoreDbUrlStr, MDMDbUserName, 
					profileDbUserName, estoreDbPassword, outputMap);

			//Verify Cart Confirmation page for Unlimited segment items
			apdUtil.verifyCartConfirmationPageWithInterstitialItem(selenium, maxPageLoadWaitTimeInMilliseconds, itemName,
					paymentTypeStr, lastThreeDigitsAccNoStr, lastFourDigitsRoutingNoStr, bankAccNameStr, false);
			apdUtil.validatePriceOncartReviewPage(selenium, selenium.getBodyText(), interstitialItem, "");
			TestRun.endTest();
			apdUtil.invalidateCacheInMDMDB(selenium,  maxPageLoadWaitTimeInMilliseconds);
			apdUtil.invalidateCacheInProfileDB(selenium, maxPageLoadWaitTimeInMilliseconds);
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



}
