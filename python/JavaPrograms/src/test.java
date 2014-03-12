package initiate.apd.driver;

import initiate.apd.runner.NewCustomer_NewOrder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;

/**
 * JMantis test driver class for APD flows.<br>
 * This class is used only for standalone testing.<br>
 *
 * @author  Jeffrey Walker & Ramya Nagendra
 * @version 1.0.0
 */
public class Main 
{
	private static Logger sLog;

	public static void main(String[] args) throws InterruptedException 
	{
		try
		{
			/*
             * Initialize JMantis
             */
			DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a zzzz");
			Date date = new Date();
			String dateStr = ".\\resources\\results\\" +
                             dateFormat.format(date).replaceAll(":", "_");
			new File(dateStr).mkdir();
			TestRun.initializeTAF(dateStr +
              "\\EstoreInitateFlows_",".\\resources\\config\\config.properties",
              TestRun.LOGLEVEL.INFO, false, true);
		}
		catch (Exception e) 
		{
			sLog = Logger.getInstance();
			sLog.info(e.getStackTrace().toString());
		}

		// Estore DB URLs
		
		// MDM/ProfileDb (System Env)
		 String estoreDbUrl = "jdbc:sqlserver://escdevdb2.ptc.intuit.com:1433" ; 
		 
		// MDM/ProfileDb (ProSup Env)
//		String estoreDbUrl = "jdbc:sqlserver://esintdb2W2K5.ptc.intuit.com:1433";  

		// APD URLs for Proseries and Lacerte domain in ProdSup environment
//		String estoreURL = "https://wcgo2c3.proseries.intuit.com/";                          
//      String apdProseriesURL = "https://wcgo2c3.proseries.intuit.com/myproseries/login/";  
//		String apdLacerteURL = "https://wcgo2c3.lacerte.intuit.com/myaccount/login/";         
		
		//APD URLs for Proseries and Lacerte domain in System environment
		String estoreURL = "https://sboqa.proseries.intuit.com";                              
		String apdProseriesURL = "https://sboqa.proseries.intuit.com/myproseries/login/";    
        String apdLacerteURL = "https://sboqa.lacerte.intuit.com/myaccount/login/";         


		// Test Cases to submit order for 2010 TY REP/PPR & Unlimited Segments from estore and to renew online to 2011
		HashMap<String, Object> outputMap;

//		// To submit an order and renew online for REP segment - ProSeriesBasic1040Federal with auto renewal signup
//		outputMap = runNewCustomerSubmitProSeriesBasic1040FederalEFT(apdProseriesURL, estoreURL, estoreDbUrl);
//		Thread.sleep(90000);
//		runExistingCustomerOnlineRenewalProSeriesBasicOptedInAutorenewalSignUp(apdProseriesURL, estoreDbUrl, outputMap);
//
//		// To submit an order and renew online for REP segment - ProSeriesBasic1040Federal without auto renewal signup
//		outputMap = runNewCustomerSubmitProSeriesBasic1040FederalEFT(apdProseriesURL, estoreURL, estoreDbUrl);
//		Thread.sleep(90000);
//		runExistingCustomerOnlineRenewalProSeriesBasicOptedOutAutorenewalSignUp(apdProseriesURL, estoreDbUrl, outputMap);			
//
//		// To submit an order and renew online for PPR segment - ProSeriesPPR with auto renewal signup
//		outputMap = runNewCustomerSubmitProSeriesPPREFT(apdProseriesURL, estoreURL, estoreDbUrl);
//		Thread.sleep(90000);
//		runExistingCustomerOnlineRenewalProSeriesPPROptedInAutorenewalSignUp(apdProseriesURL,estoreDbUrl, outputMap);
//
//		// To submit an order and renew online for PPR segment - ProSeriesPPR without auto renewal signup
//		outputMap = runNewCustomerSubmitProSeriesPPREFT(apdProseriesURL, estoreURL, estoreDbUrl);
//		Thread.sleep(90000);
//		runExistingCustomerOnlineRenewalProSeriesPPROptedOutAutorenewalSignUp(apdProseriesURL, estoreDbUrl, outputMap);
//
//		// To submit an order and renew online for PPR segment - LacerteREP with auto renewal signup
//		outputMap = runNewCustomerSubmitLacerteREPEFT(apdLacerteURL, estoreURL, estoreDbUrl);
//		Thread.sleep(90000);
//		runExistingCustomerOnlineRenewalLacerteREPOptedInAutorenewalSignUp(apdLacerteURL, estoreDbUrl, outputMap);

		// To submit an order and renew online for PPR segment - LacerteREP without auto renewal signup
//		outputMap = runNewCustomerSubmitLacerteREPEFT(apdLacerteURL, estoreURL, estoreDbUrl);
//		Thread.sleep(90000);
//		runExistingCustomerOnlineRenewalLacerteREPOptedOutAutorenewalSignUp(apdLacerteURL, estoreDbUrl, outputMap);

//		// To submit an order and renew online for Unlimited segment - LacerteUnlimited 
//		outputMap = runNewCustomerSubmitLacerteUnlimitedREFT(apdLacerteURL, estoreURL, estoreDbUrl);
//		Thread.sleep(90000);
//		runExistingCustomerOnlineRenewalLacerteUnlimited(apdLacerteURL, estoreDbUrl, outputMap);
//
		// To submit an order and renew online for Unlimited segment - ProseriesFederal 
		outputMap = runNewCustomersubmitProseriesFederalEFT(
                        apdProseriesURL, estoreURL, estoreDbUrl);
		Thread.sleep(90000);
		runExistingCustomerOnlineRenewalProSeries1040Federal(
            apdProseriesURL, estoreDbUrl, outputMap);				

		/*
         * Terminate JManitis
         */
		TestRun.terminateTAF(true);
	}

	/**
     *
     * Methods to submit order 2010 TY REP/PPR & Unlimited Segments from estore
     */

    /**
     *
     * Submit order from estore for item ProSeries Basic 1040 Federal.
     *
     * @param apdProseriesURL URL to access ProSeries domain
     * @param estoreURL       URL to access eStore
     * @param estoreDbUrl     URL to access estore MDM/Profile DB
     *
     * @return HashMap of 
     */
	private static HashMap<String, Object> runNewCustomerSubmitProSeriesBasic1040FederalEFT(String apdProseriesURL, String estoreURL, String estoreDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "ProSeries Basic 1040 Federal | 1099935";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "ProSeriesBasicFederalPPR";
		String productName = "ProSeries";
		String accountPageName = "myproseries";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		HashMap<String, Object> outputMap =
            newCustomerNewOrder.submitOrder2010TYAttributeEFT(
                apdProseriesURL,
                estoreURL,
                estoreDbUrl,
                item,
                maxPageLoadWaitTimeInMinutes,
                newUserEmail,
                userFirstName, 
				userLastName,
                AccountType,
                bankAccName,
                routingNumber,
                accountNumber,
                segmentName,
                productName,
                accountPageName);
		return(outputMap);
	}

	private static HashMap<String, Object> runNewCustomerSubmitProSeriesPPREFT(String apdProseriesURL, String estoreURL, String estoreDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "ProSeries Pay Per Return License | 1099907";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "ProSeriesPPR";
		String productName = "ProSeries";
		String accountPageName = "myproseries";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdProseriesURL, estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,
				AccountType, bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName);
	}

	private static HashMap<String, Object> runNewCustomerSubmitLacerteREPEFT(String apdLacerteURL, String estoreURL, String estoreDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "Lacerte Remote Entry Processing License | 1099967";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "LacerteREP";
		String productName = "Lacerte";
		String accountPageName = "myaccount";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdLacerteURL, estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, 
				AccountType, bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName);
	}

	private static HashMap<String, Object> runNewCustomerSubmitLacerteUnlimitedREFT(String apdLacerteURL , String estoreURL, String estoreDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "Lacerte Federal | 1099978";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String segmentName = "LacerteUnlimited";
		String productName = "Lacerte";
		String accountPageName = "myaccount";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdLacerteURL , estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, 
				AccountType, bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName);
	}

	private static HashMap<String, Object> runNewCustomersubmitProseriesFederalEFT(String apdProseriesURL, String estoreURL, String estoreDbUrl)
	{
		String AccountType = "Checking";
		String bankAccName = "test" ;
		String routingNumber = "011302616";
		String accountNumber = "1234";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String item = "ProSeries Federal | 1099919";
		String maxPageLoadWaitTimeInMinutes = "20";
		String email = "ramya_nagendra@apdqa.com";
		String segmentName = "ProSeriesFederal";
		String productName = "ProSeries";
		String accountPageName = "myproseries";

		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();

		return newCustomerNewOrder.submitOrder2010TYAttributeEFT(apdProseriesURL, estoreURL, estoreDbUrl, item, maxPageLoadWaitTimeInMinutes, email, userFirstName, userLastName, AccountType, 
				bankAccName, routingNumber, accountNumber, segmentName, productName, accountPageName);
	}


	// OLR Flow Methods

	// Methods to renew online for REP/PPR segments with/without Autorenewal Sign Up ( Proseries Basic - 1099935, Proseries PPR - 1099907, Lacerte REP - 1099967)
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesBasicOptedInAutorenewalSignUp(String apdProseriesURL,String estoreDbUrl, HashMap<String, Object> outputMap)

	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
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
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				accountPageName, segmentName, outputMap);




	}

	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesBasicOptedOutAutorenewalSignUp(String apdProseriesURL, String estoreDbUrl, HashMap<String, Object> outputMap)

	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; 
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithoutSignup";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				productName, accountPageName, segmentName, outputMap);


	}

	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesPPROptedInAutorenewalSignUp(String apdProseriesURL, String estoreDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Pay Per Return License";
		String page = "autoRenewalPageWithSignup";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				productName, accountPageName, segmentName, outputMap);


	}


	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeriesPPROptedOutAutorenewalSignUp(String apdProseriesURL, String estoreDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Pay Per Return License";
		String page = "autoRenewalPageWithoutSignup";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				productName, accountPageName, segmentName, outputMap);


	}

	private static HashMap<String, Object> runExistingCustomerOnlineRenewalLacerteREPOptedInAutorenewalSignUp(String apdLacerteURL, String estoreDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Remote Entry Processing License";
		String page = "autoRenewalPageWithSignup";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				productName, accountPageName, segmentName, outputMap);


	}

	//
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalLacerteREPOptedOutAutorenewalSignUp(String apdLacerteURL, String estoreDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Remote Entry Processing License";
		String page = "autoRenewalPageWithoutSignup";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				productName, accountPageName, segmentName, outputMap);


	}

	//
	//// Methods to renew online for unlimited segments (Lacerte Unlimited - 1099978, Proseries 1040 Federal - 1099919)
	//	
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalLacerteUnlimited(String apdLacerteURL, String estoreDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Federal";
		String interstitialItem = "Lacerte Productivity Plus"; //"ProSeries Document Management System , Fujitsu Fi-6130 Scanner";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				productName, accountPageName, segmentName, outputMap);

	}

	//
	private static HashMap<String, Object> runExistingCustomerOnlineRenewalProSeries1040Federal(String apdProseriesURL, String estoreDbUrl, HashMap<String, Object> outputMap)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "20";
		String newUserEmail = "ramya_nagendra@apdqa.com";
		String userFirstName = "Ramya"; 
		String userLastName = "Nagendra";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Federal";
		String interstitialItem = "ProSeries Document Management System"; //"ProSeries Document Management System"; //"Fujitsu Fi-6130 Scanner";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
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
				productName, accountPageName, segmentName, outputMap);
	}
}
