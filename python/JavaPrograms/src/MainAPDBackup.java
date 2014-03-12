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
	
	public static void main(String[] args) 
	{
		try
		{
			//Initialize JMantis
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a zzzz");
			Date date = new Date();
			String dateStr = ".\\resources\\results\\" + dateFormat.format(date).replaceAll(":", "_");
			new File(dateStr).mkdir();
			TestRun.initializeTAF(dateStr + "\\EstoreInitateFlows_",".\\resources\\config\\config.properties",TestRun.LOGLEVEL.INFO, false, true);
			
			
		}
		catch (Exception e) 
	    {
	    	sLog = Logger.getInstance();
	    	sLog.info(e.getStackTrace().toString());
	    }
		
		//APD URLs
		
	//	String apdProseriesURL = "https://wcgo2c3.proseries.intuit.com/myproseries/login/";  //ProdSup
		String apdProseriesURL = "https://sboqa.proseries.intuit.com/myproseries/login/";    //SYS
		
		//String apdLacerteURL = "https://wcgo2c3.lacerte.intuit.com/myaccount/login/";     //ProdSup
		String apdLacerteURL = "https://sboqa.lacerte.intuit.com/myaccount/login/";         //SYS
		
		//Test Cases to renew online for REP/PPR segments
		runNewCustomer_OnlineRenewalProSeriesBasicOptedInAutorenewalSignUp(apdProseriesURL);
 //    	runNewCustomer_OnlineRenewalProSeriesBasicOptedOutAutorenewalSignUp(apdProseriesURL);
//		runNewCustomer_OnlineRenewalProSeriesPPROptedInAutorenewalSignUp(apdProseriesURL);
//		runNewCustomer_OnlineRenewalProSeriesPPROptedOutAutorenewalSignUp(apdProseriesURL);
//	    runNewCustomer_OnlineRenewalLacerteREPOptedInAutorenewalSignUp(apdLacerteURL);
//		runNewCustomer_OnlineRenewalLacerteREPOptedOutAutorenewalSignUp(apdLacerteURL);
		
		//Test Cases to renew online for Unlimited segments
//		runNewCustomer_OnlineRenewalLacerteUnlimited(apdLacerteURL);
//		//runNewCustomer_OnlineRenewalProSeries1040Federal(apdProseriesURL);
		
		
		
		//Terminate JManitis
		TestRun.terminateTAF(true);
		
	}
	
	// Methods to renew online for REP/PPR segments with/without Autorenewal Sign Up ( Proseries Basic - 1099935, Proseries PPR - 1099907, Lacerte REP - 1099967)
	private static void runNewCustomer_OnlineRenewalProSeriesBasicOptedInAutorenewalSignUp(String apdProseriesURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test" ;//"TestAcc";
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithSignup";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "proseries";
		String accountPageName = "myproseries";
		String segmenttName = "ProSeriesBasicOptedInAutorenewalSignUp";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword,siebelDbUrl, item, maxPageLoadWaitTimeInMinutes,
				                          newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				                          lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, productName, accountPageName, segmenttName);
				               
		
	}
	
	private static void runNewCustomer_OnlineRenewalProSeriesBasicOptedOutAutorenewalSignUp(String apdProseriesURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test"; //"TestAcc";
		String itemName = "ProSeries Basic 1040 Federal";
		String page = "autoRenewalPageWithoutSignup";
		String siebelDbUserName = "diaguser";
	    String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "proseries";
		String accountPageName = "myproseries";
		String segmenttName = "ProSeriesBasicOptedOutAutorenewalSignUp";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword,siebelDbUrl, item, maxPageLoadWaitTimeInMinutes,
				                          newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				                          lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, productName, accountPageName, segmenttName);
				                          

	}
	
	private static void runNewCustomer_OnlineRenewalProSeriesPPROptedInAutorenewalSignUp(String apdProseriesURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Pay Per Return License";
		String page = "autoRenewalPageWithSignup";
		String siebelDbUserName = "diaguser";
	    String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "proseries";
		String accountPageName = "myproseries";
		String segmenttName = "ProSeriesPPROptedInAutorenewalSignUp";

		HashMap<String, Object> input_args = new HashMap<String, Object>();
		HashMap<String, Object> output_map = new HashMap<String, Object>();

		input_args.put("apdProseriesURL", apdProseriesURL);
		
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, item, maxPageLoadWaitTimeInMinutes,
				                          newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				                          lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, productName, accountPageName, segmenttName, output_map);
				                          

	}
	

	private static void runNewCustomer_OnlineRenewalProSeriesPPROptedOutAutorenewalSignUp(String apdProseriesURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Pay Per Return License";
		String page = "autoRenewalPageWithoutSignup";
		String siebelDbUserName = "diaguser";
	    String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "proseries";
		String accountPageName = "myproseries";
		String segmenttName = "ProSeriesPPROptedOutAutorenewalSignUp";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewal(apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, item, maxPageLoadWaitTimeInMinutes,
				                          newUserEmail, userFirstName, userLastName, creditCardNumber,  creditCardMonth, creditCardYear, paymentType,
				                          lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, productName, accountPageName, segmenttName);
				                          

	}
	
	private static void runNewCustomer_OnlineRenewalLacerteREPOptedInAutorenewalSignUp(String apdLacerteURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Remote Entry Processing License";
		String page = "autoRenewalPageWithSignup";
		String siebelDbUserName = "diaguser";
	    String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "lacerte";
		String accountPageName = "myaccount";
		String segmenttName = "LacerteREPOptedInAutorenewalSignUp";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewal(apdLacerteURL, siebelDbUserName,siebelDbPassword, siebelDbUrl, item, maxPageLoadWaitTimeInMinutes,
				                          newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				                          lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, productName, accountPageName, segmenttName);
				                          

	}
	
//
	private static void runNewCustomer_OnlineRenewalLacerteREPOptedOutAutorenewalSignUp(String apdLacerteURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Remote Entry Processing License";
		String page = "autoRenewalPageWithoutSignup";
		String siebelDbUserName = "diaguser";
	    String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "lacerte";
		String accountPageName = "myaccount";
		String segmenttName = "LacerteREPOptedOutAutorenewalSignUp";

		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewal(apdLacerteURL, siebelDbUserName, siebelDbPassword,siebelDbUrl, item, maxPageLoadWaitTimeInMinutes,
				                           newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				                           lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, page, productName, accountPageName, segmenttName);
				                         

	}

//
//// Methods to renew online for unlimited segments (Lacerte Unlimited - 1099978, Proseries 1040 Federal - 1099919)
//	
	private static void runNewCustomer_OnlineRenewalLacerteUnlimited(String apdLacerteURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "Lacerte Federal";
		String interstitialItem = "Fujitsu Fi-6130 Scanner"; //"ProSeries Document Management System";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "lacerte";
		String accountPageName = "myaccount";
		String segmenttName = "LacerteUnlimited";
				
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewalForUnlimitedSegment(apdLacerteURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, item, maxPageLoadWaitTimeInMinutes,
                                                             newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
                                                             lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, interstitialItem, productName, accountPageName, segmenttName);

	}
	
//
	private static void runNewCustomer_OnlineRenewalProSeries1040Federal(String apdProseriesURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String paymentType = "Checking";
		String lastThreeDigitsAccNo = "234";
		String lastFourDigitsRoutingNo = "2616";
		String bankAccName = "test";
		String itemName = "ProSeries Federal";
		String interstitialItem = "ProSeries Document Management System";
		String siebelDbUserName = "diaguser";
		String siebelDbPassword = "diaguser";
		String siebelDbUrl = "jdbc:oracle:thin:@(DESCRIPTION=(LOAD_BALANCE=on)(ADDRESS=(PROTOCOL=TCP)(HOST=qyssyssldb01-vip.data.bosptc.intuit.net)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=crmsys01)))";
		String productName = "proseries";
		String accountPageName = "myproseries";
		String segmenttName = "ProSeries1040Federal";
	
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.onlineRenewalForUnlimitedSegment(apdProseriesURL, siebelDbUserName, siebelDbPassword, siebelDbUrl, item, maxPageLoadWaitTimeInMinutes, 
				                                             newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, paymentType,
				                                             lastThreeDigitsAccNo, lastFourDigitsRoutingNo, bankAccName, itemName, interstitialItem, productName, accountPageName, segmenttName);
				                          

	}
	
	
}
