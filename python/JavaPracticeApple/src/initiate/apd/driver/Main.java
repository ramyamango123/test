package initiate.apd.driver;

import initiate.apd.runner.NewCustomer_NewOrder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;

// test comment


/**  
 * JMantis test driver class for APD flows.
 * This class is used only for standalone testing.
 * 
 * @author  Jeffrey Walker & Ramya Nagendra
 * @version 1.0.0 
 * 
 * @description
 * 
 * This script submits an order from estore for Lacerte/ProSeries REP/PPR/Unlimited segment items (TY 2010)
 * and performs OLR flow to renew the items to TY 2011
 * 
 * @note    Make sure "auto auth" firefox extension is installed in Firefox profile used to run these tests
 *          If the extension does not get activated due to version incompatibilities, add the following line to users.js in
 *          the Firefox profile directory - "user_pref("extensions.checkCompatibility.7.0", false);"
 * 
 */ 
public class Main 
{
	private static Logger sLog;

	public static void main(String[] args) throws InterruptedException 
	{
		try
		{
			//Initialize JMantis
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a zzzz");
			Date date = new Date();
			String dateStr = ".\\resources\\results\\" + dateFormat.format(date).replaceAll(":", "_");
			new File(dateStr).mkdir();
			TestRun.initializeTAF(dateStr + "\\EstoreInitateFlows_",".\\resources\\config\\config.properties",
					              TestRun.LOGLEVEL.INFO, false, true);
			sLog = Logger.getInstance();


		}
		catch (Exception e) 
		{
			
			sLog.info(e.getStackTrace().toString());
		}

	//////------------------Web URLs in ProSup env --------------//////	
		 
		// MDM/ProfileDb URL (ProSup Env)
		String estoreDbUrl = "jdbc:sqlserver://esintdb2W2K5.ptc.intuit.com:1433";  

		//APD URLs for Proseries and Lacerte domain in ProdSup environment
		String estoreURL = "https://wcgo2c3.proseries.intuit.com/";                          
		String apdProseriesURL = "https://wcgo2c3.proseries.intuit.com/myproseries/login/";  
		String apdLacerteURL = "https://wcgo2c3.lacerte.intuit.com/myaccount/login/";
		
		String apdProseriesProdURL = "https://proseries.intuit.com/myproseries/login/";
		String apdLacerteProdURL = "https://lacerte.intuit.com/myaccount/login/";

		//Profile DB and MDM DB invalidate cache url (ProdSup Env)
		String profileDBCacheURL = "http://wcgo2c3.quicken.intuit.com/dyn/admin/nucleus/atg/userprofiling/ProfileAdapterRepository/?shouldInvokeMethod=invalidateCaches";
		String mdmDBCacheURL = "https://wcgo2c3.quickbooks.intuit.com/dyn/admin/nucleus/intuit/estore/profile/repository/MDMRepository/";
		String siebelDbUserName = "fos";
		String siebelDbPassword = "fos123";
		String siebelDbUrl = "jdbc:oracle:thin:@ (DESCRIPTION=(ADDRESS_LIST=(ADDRESS= (PROTOCOL=TCP)(HOST= qyspdssldb01-vip.data.bosptc.intuit.net)(PORT=1521))" +
				"(ADDRESS= (PROTOCOL=TCP)(HOST= qyspdssldb02-vip.data.bosptc.intuit.net)(PORT=1521))(LOAD_BALANCE=yes))" +
				"(CONNECT_DATA=(SERVICE_NAME=CRMPDS01)(SERVER=dedicated)(FAILOVER_MODE=(TYPE=select)(METHOD=basic)(RETRIES=180)(DELAY=5))))";

		
		
	////////--------------------------------------------------------///////
		
		
		
		/////////------------------Web URLs in System env --------------//////
//
//		// MDM/ProfileDb URL (System Env)
//		String estoreDbUrl = "jdbc:sqlserver://escdevdb2.ptc.intuit.com:1433" ; 
//
//		//APD URLs for Proseries and Lacerte domain in System environment
//		String estoreURL = "https://sboqa.proseries.intuit.com";                              
//		String apdProseriesURL = "https://sboqa.proseries.intuit.com/myproseries/login/";    
//		String apdLacerteURL = "https://sboqa.lacerte.intuit.com/myaccount/login/";  
//
//		//Profile DB and MDM DB invalidate cache url (System Env)
//		String profileDBCacheURL = "http://sboqa.quicken.intuit.com/dyn/admin/nucleus/atg/userprofiling/ProfileAdapterRepository/?shouldInvokeMethod=invalidateCaches";
//		String mdmDBCacheURL = "https://sboqa.quickbooks.intuit.com/dyn/admin/nucleus/intuit/estore/profile/repository/MDMRepository/";
//
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
//         ////// ---------------------------------------------------------------------------- //////
        
		
		
		
		
		HashMap<String, Object> outputMap;
		
	//	int count = 0;
		
	
		//-------------------------ProdSupp env New Acct Creation on Lacerte/Proseries domain---------------------//
		
		 //ProSupp env - Submit an 2010 TY Order, login with Proseries account, Sign-up for a new account on APD Proseries domain  
	    runLoginWithExistingProSeriesAcct(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL,siebelDbUserName, siebelDbPassword, siebelDbUrl);
		runProseriesNewUserSignup(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		
		// ProSupp env - Submit an 2010 TY Order, login with Lacerte account, Sign-up for a new account on APD Lacerte domain  
		runLoginWithExistingLacerteAcct(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		runLacerteNewUserSignup(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		
	
		//-------------------------Production env New Acct Creation on Lacerte/Proseries domain---------------------//

		// Production env - Login to an existing Proseries account (User name - psc_unl_not_renewed  CAN - 237074551, Zip - 94086 & Sign-up for a new account on APD Proseries domain  
		runLoginWithExistingProSeriesProdAcct(apdProseriesProdURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL,siebelDbUserName, siebelDbPassword, siebelDbUrl);
		runProseriesNewUserSignupProd(apdProseriesProdURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);

		 //Production env - Login to an existing Lacerte account (Username - lsc_unl_not_renewed, CAN - 771873710, Zip - 94086 & Sign-up for a new account on APD Lacerte domain  
		runLoginWithExistingLacerteProdAcct(apdLacerteProdURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
     	runLacerteNewUserSignupProd( apdLacerteProdURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		
		
		
		// -----------------Test Cases to submit order for 2010 TY REP/PPR & Unlimited Segments from estore and to renew online to 2011---------------------//
		
		
		
		// To submit an order and renew online for REP segment - ProSeriesBasic1040Federal with auto renewal signup 
		
		do 
		{

			outputMap = runNewCustomerSubmitProSeriesBasic1040FederalEFT(apdProseriesURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
			sLog.info((String) outputMap.get("siebel_order_status"));
			System.out.println("siebel order status" + outputMap.get("siebel_order_status"));
		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));


		if (outputMap.get("siebel_order_status").equals("Complete"))
		{
			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalProSeriesBasicOptedInAutorenewalSignUp(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("ProSeriesBasic1040 order with auto renewal didn't go to complete state after 3 attempts");
		}


		// To submit an order and renew online for REP segment - ProSeriesBasic1040Federal without auto renewal signup 

		do 
		{
			outputMap = runNewCustomerSubmitProSeriesBasic1040FederalEFT(apdProseriesURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);

		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));

		if (outputMap.get("siebel_order_status").equals("Complete"))
		{

			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalProSeriesBasicOptedOutAutorenewalSignUp(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);			
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("ProSeriesBasic1040 without auto renewal order didn't go to complete state after 3 attempts");
		}


		// To submit an order and renew online for PPR segment - ProSeriesPPR with auto renewal signup

		do
		{
			outputMap = runNewCustomerSubmitProSeriesPPREFT(apdProseriesURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);

		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));

		if (outputMap.get("siebel_order_status").equals("Complete"))
		{
			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalProSeriesPPROptedInAutorenewalSignUp(apdProseriesURL,estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("ProSeries PPR with auto renewal order didn't go to complete state after 3 attempts");
		}


		// To submit an order and renew online for PPR segment - ProSeriesPPR without auto renewal signup

		do
		{
			outputMap = runNewCustomerSubmitProSeriesPPREFT(apdProseriesURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));

		if (outputMap.get("siebel_order_status").equals("Complete"))
		{

			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalProSeriesPPROptedOutAutorenewalSignUp(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("ProSeries PPR without auto renewal order didn't go to complete state after 3 attempts");
		}


		// To submit an order and renew online for PPR segment - LacerteREP with auto renewal signup

		do
		{
			outputMap = runNewCustomerSubmitLacerteREPEFT(apdLacerteURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));

		if (outputMap.get("siebel_order_status").equals("Complete"))
		{
			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalLacerteREPOptedInAutorenewalSignUp(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("Lacerte REP with auto renewal order didn't go to complete state after 3 attempts");
		}


		// To submit an order and renew online for PPR segment - LacerteREP without auto renewal signup

		do
		{
			outputMap = runNewCustomerSubmitLacerteREPEFT(apdLacerteURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));

		if (outputMap.get("siebel_order_status").equals("Complete"))
		{

			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalLacerteREPOptedOutAutorenewalSignUp(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL,siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("Lacerte REP without auto renewal order didn't go to complete state after 3 attempts");
		}


		// To submit an order and renew online for Unlimited segment - LacerteUnlimited 

		do
		{
			outputMap = runNewCustomerSubmitLacerteUnlimitedREFT(apdLacerteURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));

		if (outputMap.get("siebel_order_status").equals("Complete"))
		{
			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalLacerteUnlimited(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("Lacerte Unlimited order didn't go to complete state after 3 attempts");
		}


		// To submit an order and renew online for Unlimited segment - ProseriesFederal 

		do
		{
			outputMap = runNewCustomersubmitProseriesFederalEFT(apdProseriesURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		} while (count++ <3 && !outputMap.get("siebel_order_status").equals("Complete"));

		if (outputMap.get("siebel_order_status").equals("Complete"))
		{
			Thread.sleep(90000);
			runExistingCustomerOnlineRenewalProSeries1040Federal(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);	
		}
		else if (!outputMap.get("siebel_order_status").equals("Complete"))
		{
			sLog.info("Proseries Federal order didn't go to complete state after 3 attempts");
		}
		
		// New account creation for existing firm on APD Lacerte/Proseries
		
		// New account creation for existing firm on APD ProSeries domain 
		outputMap = runNewCustomerSubmitProSeriesBasic1040FederalEFT(apdProseriesURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		Thread.sleep(90000);
		runExistingCustomerOnlineRenewalProSeriesBasicOptedInAutorenewalSignUp(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL,siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		Thread.sleep(40000);
		runNewAcctCreationForExistingProSeriesFirm(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		
				
		// New account creation for existing firm on APD Lacerte domain 
		outputMap = runNewCustomerSubmitLacerteUnlimitedREFT(apdLacerteURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		Thread.sleep(90000);
		runExistingCustomerOnlineRenewalLacerteUnlimited(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		Thread.sleep(40000);
		runNewAcctCreationForExistingLacerteFirm(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		
	
		
		//-------------------------ProdSupp env New Acct Creation on Lacerte/Proseries domain---------------------//
		
		 //ProSupp env - Submit an 2010 TY Order, login with Proseries account, Sign-up for a new account on APD Proseries domain  
		outputMap = runNewCustomerSubmitProSeriesBasic1040FederalEFT(apdProseriesURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		Thread.sleep(30000);
		runLoginWithExistingProSeriesAcct(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL,siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		Thread.sleep(30000);
		runProseriesNewUserSignup(apdProseriesURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		
		// ProSupp env - Submit an 2010 TY Order, login with Lacerte account, Sign-up for a new account on APD Lacerte domain  
		outputMap = runNewCustomerSubmitLacerteUnlimitedREFT(apdLacerteURL, estoreURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		Thread.sleep(30000);
	    runLoginWithExistingLacerteAcct(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		Thread.sleep(30000);
		runLacerteNewUserSignup(apdLacerteURL, estoreDbUrl, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, outputMap);
		
		
		//Terminate JManitis
		TestRun.terminateTAF(true);
	}

	//  Methods to submit order for  2010 TY REP/PPR & Unlimited Segments from estore
	private static HashMap<String, Object> runNewCustomerSubmitProSeriesBasic1040FederalEFT(String apdProseriesURL, String estoreURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "ProSeries Basic 1040 Federal | 1099935"; 
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "ProSeriesBasicFederalPPR";
		String productName = "ProSeries";
		String accountPageName = "myproseries";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		HashMap<String, Object> outputMap = newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdProseriesURL, estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, 
				userLastName, AccountType, bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
		return(outputMap);
	} 

	private static HashMap<String, Object> runNewCustomerSubmitProSeriesPPREFT(String apdProseriesURL, String estoreURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "ProSeries Pay Per Return License | 1099907";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "ProSeriesPPR";
		String productName = "ProSeries";
		String accountPageName = "myproseries";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdProseriesURL, estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,
				AccountType, bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
	}

	private static HashMap<String, Object> runNewCustomerSubmitLacerteREPEFT(String apdLacerteURL, String estoreURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "Lacerte Remote Entry Processing License | 1099967";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "LacerteREP";
		String productName = "Lacerte";
		String accountPageName = "myaccount";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdLacerteURL, estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, 
				AccountType, bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
	}

	private static HashMap<String, Object> runNewCustomerSubmitLacerteUnlimitedREFT(String apdLacerteURL , String estoreURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "Lacerte Federal | 1099978";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "LacerteUnlimited";
		String productName = "Lacerte";
		String accountPageName = "myaccount";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdLacerteURL , estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, 
				AccountType, bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
	}

	private static HashMap<String, Object> runNewCustomersubmitProseriesFederalEFT(String apdProseriesURL, String estoreURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "ProSeries Federal | 1099919";
		String maxPageLoadWaitTimeInMinutes = "40";
		String email = "ramya_nagendra@apdqa.com";
		String segmentName = "ProSeriesFederal";
		String productName = "ProSeries";
		String accountPageName = "myproseries";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();

		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdProseriesURL, estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, email, userFirstName, userLastName, AccountType, 
				bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName, mdmDBCacheURL, profileDBCacheURL, siebelDbUserName, siebelDbPassword, siebelDbUrl);
	}


	// OLR Flow Methods

	// Methods to renew online for REP/PPR segments with/without Autorenewal Sign Up ( Proseries Basic - 1099935, Proseries PPR - 1099907, Lacerte REP - 1099967)
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesBasicOptedInAutorenewalSignUp(String apdProseriesURL,String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)

	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,
				maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth,
				creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page,productName, 
				accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);




	}

	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesBasicOptedOutAutorenewalSignUp(String apdProseriesURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)

	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithoutSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesBasicOptedOutAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword,siebelDbUrl, estoreDbUrl, 
				estoreDbPassword, MDMDbUserName, profileDbUserName, maxPageLoadWaitTimeInMinutes,
				newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, 
				productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);


	}

	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesPPROptedInAutorenewalSignUp(String apdProseriesURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Pay Per Return License";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesPPROptedInAutorenewalSignUp";
		String estoreDbSystemUrl = "escdevdb2.ptc.intuit.com"; 
		String estoreDbProSupUrl = "esintdb2W2K5.ptc.intuit.com"; 
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";



		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl, 
				estoreDbPassword, MDMDbUserName, profileDbUserName, maxPageLoadWaitTimeInMinutes,
				newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, 
				productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);


	}


	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesPPROptedOutAutorenewalSignUp(String apdProseriesURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Pay Per Return License";
		String page = "autoRenewalPageWithoutSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesPPROptedOutAutorenewalSignUp";
		String estoreDbSystemUrl = "escdevdb2.ptc.intuit.com"; 
		String estoreDbProSupUrl = "esintdb2W2K5.ptc.intuit.com"; 
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName, maxPageLoadWaitTimeInMinutes,
				newUserEmail, userFirstName, userLastName, creditCardNumber,  creditCardMonth, creditCardYear, paymentType,
				lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, 
				productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);


	}

	private static HashMap<String, Object> runExistingCustomerOnlineRenewalLacerteREPOptedInAutorenewalSignUp(String apdLacerteURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Remote Entry Processing License";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "Lacerte";
		String accountPageName = "myaccount";
		String segmentName = "LacerteREPOptedInAutorenewalSignUp";
		String estoreDbSystemUrl = "escdevdb2.ptc.intuit.com"; 
		String estoreDbProSupUrl = "esintdb2W2K5.ptc.intuit.com"; 
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewal(apdLacerteURL, siebelDbUserName,siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName, maxPageLoadWaitTimeInMinutes,
				newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, 
				productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);


	}

	//
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalLacerteREPOptedOutAutorenewalSignUp(String apdLacerteURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Remote Entry Processing License";
		String page = "autoRenewalPageWithoutSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "Lacerte";
		String accountPageName = "myaccount";
		String segmentName = "LacerteREPOptedOutAutorenewalSignUp";
		String estoreDbSystemUrl = "escdevdb2.ptc.intuit.com"; 
		String estoreDbProSupUrl = "esintdb2W2K5.ptc.intuit.com"; 
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";



		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewal(apdLacerteURL, siebelDbUserName, siebelDbPassword,siebelDbUrl, estoreDbUrl, 
				estoreDbPassword, MDMDbUserName, profileDbUserName, maxPageLoadWaitTimeInMinutes,
				newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, 
				productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);


	}

	//
	//// Methods to renew online for unlimited segments (Lacerte Unlimited - 1099978, Proseries 1040 Federal - 1099919)
	//	
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalLacerteUnlimited(String apdLacerteURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Federal";
		String interstitialItem = "Lacerte Productivity Plus"; //"ProSeries Document Management System , Fujitsu Fi-6130 Scanner";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "Lacerte";
		String accountPageName = "myaccount";
		String segmentName = "LacerteUnlimited";
		String estoreDbSystemUrl = "escdevdb2.ptc.intuit.com"; 
		String estoreDbProSupUrl = "esintdb2W2K5.ptc.intuit.com"; 
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewalForUnlimitedSegment(apdLacerteURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName, maxPageLoadWaitTimeInMinutes,
				newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, interstitialItem, 
				productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);

	}

	
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeries1040Federal(String apdProseriesURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Federal";
		String interstitialItem = "ProSeries Document Management System"; //"ProSeries Document Management System"; //"Fujitsu Fi-6130 Scanner";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeries1040Federal";
		String estoreDbSystemUrl = "escdevdb2.ptc.intuit.com"; 
		String estoreDbProSupUrl = "esintdb2W2K5.ptc.intuit.com"; 
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";



		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.onlineRenewalForUnlimitedSegment(apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl, 
				estoreDbPassword, MDMDbUserName, profileDbUserName, maxPageLoadWaitTimeInMinutes, 
				newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, interstitialItem,
				productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, outputMap);


	}
	
	
	
	
	// ----------------------Prod supp acct creation ----------------------///
	private static void runLoginWithExistingProSeriesAcct(String apdProseriesURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = "pscautoacc1";
		String password = "test123";
		String existingfirmCAN = "663545594";
		String firmname = "pscautomationacc";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "prodsupp";
		


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		 newCustomerNewOrder.loginWithExistingAccount(username, password, existingfirmCAN, firmname, apdProseriesURL,siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);


	}
	
	private static void runLoginWithExistingLacerteAcct(String apdLacerteURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = "lscautoacc";
		String password = "test123";
		String existingfirmCAN = "620407930";
		String firmname = "lscautomationacc";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "Lacerte Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "Lacerte";
		String accountPageName = "myaccount";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "prodsupp";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		 newCustomerNewOrder.loginWithExistingAccount(username, password, existingfirmCAN, firmname, apdLacerteURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);
	}
	
	
	
	private static void runProseriesNewUserSignup(String apdProseriesURL,String estoreDbUrl, String mdmDBCacheURL, 
			String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = " pscautoacc1";
		String password = "test123";
		String existingfirmCAN = "663545594";
		String firmname = "pscautomationacc";
		String zipcode = "94040";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "prodsupp";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.newAcctCreationAPD(username, password, existingfirmCAN, firmname, zipcode, apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);


	}
	
	private static void runLacerteNewUserSignup(String apdLacerteURL,  String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = "lscautoacc";
		String password = "test123";
		String existingfirmCAN = "620407930";
		String firmname = "lscautomationacc";
		String zipcode = "94040";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "Lacerte Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "Lacerte";
		String accountPageName = "myaccount";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "prodsupp";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.newAcctCreationAPD(username, password, existingfirmCAN, firmname, zipcode, apdLacerteURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);


	}
	
	
	//--------------------------Production testing methods ---------------------------//
	
	private static void runLoginWithExistingProSeriesProdAcct(String apdProseriesProdURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = "psc_unl_not_renewed";
		String password = "test123";
		String existingfirmCAN = "237074551";
		String firmname = "APD Test psc_unl_not_renewed";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "production";
		


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.loginWithExistingAccount(username, password, existingfirmCAN, firmname, apdProseriesProdURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);


	}
	
	private static void runLoginWithExistingLacerteProdAcct(String apdLacerteProdURL, String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = "lsc_unl_not_renewed";
		String password = "test123";
		String existingfirmCAN = "771873710";
		String firmname = "APD Test lsc_unl_not_renewed";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "Lacerte Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "Lacerte";
		String accountPageName = "myaccount";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "production";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		 newCustomerNewOrder.loginWithExistingAccount(username, password, existingfirmCAN, firmname, apdLacerteProdURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);
	}
	
	private static void runProseriesNewUserSignupProd(String apdProseriesProdURL,String estoreDbUrl, String mdmDBCacheURL, 
			String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		String username = "psc_unl_not_renewed";
		String password = "test123";
		String existingfirmCAN = "237074551";
		String firmname = "APD Test psc_unl_not_renewed";
		String zipcode = "94043";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "ProSeries";
		String accountPageName = "myproseries";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "production";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.newAcctCreationAPD(username, password, existingfirmCAN, firmname, zipcode, apdProseriesProdURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);


	}
	
	private static void runLacerteNewUserSignupProd(String apdLacerteProdURL,  String estoreDbUrl, String mdmDBCacheURL, String profileDBCacheURL, String siebelDbUserName, String siebelDbPassword, String siebelDbUrl)

	{
		
		String username = "lsc_unl_not_renewed";
		String password = "test123";
		String existingfirmCAN = "771873710";
		String firmname = "APD Test lsc_unl_not_renewed";
		String zipcode = "94040";
		String creditCardNumber="4444444444444448"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2021";
		String maxPageLoadWaitTimeInMinutes = "40";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "Lacerte Federal";
		String page = "autoRenewalPageWithSignup";
//		String siebelDbUserName = "diaguser";
//		String siebelDbPassword = "diaguser";
//		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "Lacerte";
		String accountPageName = "myaccount";
		String segmentName = "ProSeriesBasicOptedInAutorenewalSignUp";
		String estoreDbPassword = "atg";
		String MDMDbUserName = "mdmuser";
		String profileDbUserName = "profiledbuser";
		String env = "production";


		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		 newCustomerNewOrder.newAcctCreationAPD(username, password, existingfirmCAN, firmname, zipcode, apdLacerteProdURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, estoreDbUrl,
				estoreDbPassword, MDMDbUserName, profileDbUserName,maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, 
				creditCardNumber, creditCardMonth, creditCardYear, paymentType, lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, 
				itemName, page, productName, accountPageName, segmentName, mdmDBCacheURL, profileDBCacheURL, env);


	}
	
	

}
