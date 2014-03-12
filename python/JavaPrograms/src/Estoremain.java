package initiate.estore.driver;

import initiate.estore.runner.ExistingCustomer_NewOrder;
import initiate.estore.runner.NewCustomer_NewOrder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;


/**  
* JMantis test driver class for Estore flows.<br>
* This class is used only for standalone testing.<br>
* 
* @author  Jeffrey Walker
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
		
		//Estore URLs
		//String estoreURL = "https://sbostg.pointofsale.intuit.com";//FUNC
		String estoreURL = "https://sboqa.pointofsale.intuit.com";//SYS
		//String estoreURL = "http://sboint.proseries.intuit.com/commerce/checkout/demo/items_offerings.jsp";//MIG	
		
		//New Customer Order Test Cases
		runNewCustomer_NonshippableOrder_CreditCard(estoreURL); //PASS
		//To run
//		runNewCustomer_NonshippableOrder_EFT(estoreURL);
 //CPC Tax Online issue		runNewCustomer_NonshippableBundleOrder_CreditCard(estoreURL);
// CPC 		runNewCustomer_NonshippableBundleOrder_EFT(estoreURL);
//		runNewCustomer_NonshippableMultipleLinesOrder_CreditCard(estoreURL); //PASS
	//	runNewCustomer_NonshippableMultipleLinesOrder_EFT(estoreURL); // payroll no EFT
		//To run end
//			
		//Existing Customer Order Test Cases
		//To run
//		runExistingCustomer_NonshippableOrder(estoreURL);
//CPC 		runExistingCustomer_NonshippableBundleOrder(estoreURL);
//		runExistingCustomer_NonshippableMultipleLinesOrder(estoreURL);
		//To Run End	
		
//		runNewAccount_Download_ReInstall_US_ShipaddressOrder_CreditCard(estoreURL); 
//		runNewAccount_Download_ReInstall_APOBox_ShipaddressOrder_CreditCard(estoreURL);//Pass
//		runNewAccount_Download_ReInstall_POBox_ShipaddressOrder_CreditCard(estoreURL); //Pass
//		runNewAccount_Download_ReInstall_USPossession_ShipaddressOrder_CreditCard(estoreURL); //Pass
//		runNewAccount_Download_ReInstall_StandardCANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue
//		runNewAccount_Download_ReInstall_POBox_CANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue
//		runNewAccount_Download_ReInstall_3rdParty_US_ShipaddressOrder_CreditCard(estoreURL);
//		runNewAccount_Download_ReInstall_3rdParty_APOBox_ShipaddressOrder_CreditCard(estoreURL);
//		runNewAccount_Download_ReInstall_3rdParty_POBox_ShipaddressOrder_CreditCard(estoreURL);
//		runNewAccount_Download_ReInstall_3rdParty_USPossession_ShipaddressOrder_CreditCard(estoreURL);
//		runNewAccount_Download_ReInstall_3rdParty_StandardCANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue
//		runNewAccount_Download_ReInstall_3rdParty_POBox_CANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue
//		
//		
//		runNewAccount_LacerteReplcement_US_ShipaddressOrder_CreditCard(estoreURL); //Passrun
//		runNewAccount_QB_POS_BundledHarware_US_ShipaddressOrder_CreditCard(estoreURL);//Pass
//		runNewAccount_Payroll_3Users_US_ShipaddressOrder_CreditCard(estoreURL);//Pass
//		runNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(estoreURL); //Pass
//	
//		runExistingNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(estoreURL);
//		runExistingAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(estoreURL);
 		
//		runNewAccount_CPC_Bundle_Tax_Online_2011_addressOrder_CreditCard(estoreURL);//Pass CPC
// 		runExistingAccount_TaxOnline_2011_Order_CreditCard(estoreURL);
 		
// 		runNewAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(estoreURL);
// 		runExistingAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(estoreURL);
 		//	
		
		
		
		
		
		//Terminate JManitis
		TestRun.terminateTAF(true);
		
	}
	
	
// Added By Shankar
	
	
	
	private static void runNewAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="normal";
	//String Product_Type="normal";
	
	String item1= "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
	String item2="Intuit QuickBooks Payroll Basic | 1099581";
	
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	//String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitMultipleOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew,item1,item2);
															
}

	private static void runExistingAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="normal";
	//String SignInURLs="normal";
	String item1= "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
	String item2="Intuit QuickBooks Payroll Basic | 1099581";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
//	String estoreUserType=" New Shipping and Billing Address";
	String userId = "shankar_1210_001";//FUNC Credit Card - "E2EAutomated663900";
	//String userId = "E2EAutomated2952507"; //SYS Credit Card
	String password = "test123";
	
	
	ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
	existingCustomerNewOrder.submitCD_Download_MultipleOrders_ExistingUser_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew,userId,password, item1, item2);
															
}
	
	

	private static void runExistingNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	//String estoreUserType=" New Shipping and Billing Address";
	
	ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
	existingCustomerNewOrder.submitOrder_ExistingAccount(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	

	private static void runExistingAccount_TaxOnline_2011_Order_CreditCard(String estoreURL)
{
	String paymentType="EFT"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="CPC_Bundle_Tax_Online_2011";
	//String SignInURLs="normal";
	String item= "Tax Online Tax Year 2011 Signup-1099830";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="No";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
//	String estoreUserType=" New Shipping and Billing Address";
	String userId = "shankar_1210_001";//FUNC Credit Card - "E2EAutomated663900";
	//String userId = "E2EAutomated2952507"; //SYS Credit Card
	String password = "test123";
	
	
	ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
	existingCustomerNewOrder.submitShippable_ExistingUser_Order_DefaultItemAttributes(estoreURL, item, maxPageLoadWaitTimeInMinutes, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew,userId,password);
															
}
	
	
	

	private static void runExistingAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="EFT"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String SignInURLs="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	String estoreUserType=" New Shipping and Billing Address";
	String userId = "shankar_1210_001";//FUNC Credit Card - "E2EAutomated663900";
	//String userId = "E2EAutomated2952507"; //SYS Credit Card
	String password = "test123";
	
	
	ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
	existingCustomerNewOrder.submitShippable_ExistingUser_Order_DefaultItemAttributes(estoreURL, item, maxPageLoadWaitTimeInMinutes, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew,userId,password);
															
}
	
	
	
	private static void runNewAccount_Payroll_3Users_US_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	//String productType="IntuitQB_POS_Download_Reinstall";
	String productType="IntuitQB_Payroll_3User";
	String item= "Intuit QuickBooks Payroll Basic | 1099581";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="No";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	//String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}

	private static void runNewAccount_QB_POS_BundledHarware_US_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="normal";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	//String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}


	private static void runNewAccount_CPC_Bundle_Tax_Online_2011_addressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="CPC_Bundle_Tax_Online_2011";
	//String Product_Type="normal";
	String item= "Tax Online Tax Year 2011 Signup-1099830";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="No";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	//String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}

	private static void runNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	//String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	
	
	
	"ProSeries Pay Per Return License | 1099907";


	private static void runNewAccount_LacerteReplcement_US_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="normal";
	//String Product_Type="normal";
	String item= "Lacerte Replacement | 1099964";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	//String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}

	
	//##############AllAddress Types
		
	private static void runNewAccount_Download_ReInstall_US_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	//String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	
	private static void runNewAccount_Download_ReInstall_APOBox_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_APOBox_USA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_APOBox_USA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_APOBox_USA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_POBox_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_POBox_USA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_POBox_USA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_POBox_USA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_USPossession_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_USPossession_USA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_USPossession_USA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_USPossession_USA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_StandardCANADA_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardCANADA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_StandardCANADA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_POBox_CANADA_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_POBox_CANADA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_Normal";
	String shipAddressType="Shipping_POBox_CANADA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}

		
	//##############AllAddress Types
	
	private static void runNewAccount_Download_ReInstall_3rdParty_US_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardUSA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_3rdParty";
	String shipAddressType="Shipping_StandardUSA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_3rdParty_APOBox_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_APOBox_USA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_3rdParty";
	String shipAddressType="Shipping_APOBox_USA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_APOBox_USA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_3rdParty_POBox_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_POBox_USA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_3rdParty";
	String shipAddressType="Shipping_POBox_USA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_POBox_USA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_3rdParty_USPossession_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_USPossession_USA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_3rdParty";
	String shipAddressType="Shipping_USPossession_USA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_USPossession_USA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_3rdParty_StandardCANADA_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_StandardCANADA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_3rdParty";
	String shipAddressType="Shipping_StandardCANADA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}
	private static void runNewAccount_Download_ReInstall_3rdParty_POBox_CANADA_ShipaddressOrder_CreditCard(String estoreURL)
{
	String paymentType="creditcard"; //EFT
	String accountType = "Savings";//Checking or Savings
	String routingNumber = "063210112";
	String accountNumber = "81686868686891";
	String creditCardNumber="4444222233331111"; 
	String creditCardMonth = "1"; 
	String creditCardYear = "2014";
	//String Product_Type="CPC_Bundle_Tax_Online_2011";
	String productType="IntuitQB_POS_Download_Reinstall";
	//String Product_Type="normal";
	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
	String cpcIndividualBank="25";
	String cpcIndividualUsage="50";
	String maxPageLoadWaitTimeInMinutes = "2";
	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
	String userFirstName = "Shankara"; 
	String userLastName = "Narayan";
	String companyAddress="Company_POBox_CANADA"; 
	String shipAddressNew="Yes";
	String shipType="Ship_3rdParty";
	String shipAddressType="Shipping_POBox_CANADA";
	String billAddressNew="Yes"; //No
	String billAddressType="Billing_StandardUSA";
	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
	
	//
	String estoreUserType=" New Shipping and Billing Address";
	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
	newCustomerNewOrder.submitOrder_newUser(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew);
															
}

	
// End Added By Shankar	
	
	
	
	
	
	
	
	
	
	
	
	private static void runNewCustomer_NonshippableOrder_CreditCard(String estoreURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
	}
	
	
	private static void runNewCustomer_NonshippableOrder_EFT(String estoreURL)
	{
		String accountType = "Checking";//Checking or Savings
		String routingNumber = "063210112";
		String accountNumber = "81686868686891";
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
	}
	
	private static void submitNonShippableOrder_DefaultItemAttributes_CreditCard(String estoreURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String item = "Tax Online Tax Year 2011 Signup-1099830";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
	}
	
	private static void runNewCustomer_NonshippableBundleOrder_EFT(String estoreURL)
	{
		String accountType = "Checking";//Checking or Savings
		String routingNumber = "063210112";
		String accountNumber = "81686868686891";
		String item = "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
	}
	
	private static void runNewCustomer_NonshippableMultipleLinesOrder_CreditCard(String estoreURL)
	{
		String creditCardNumber="4444222233331111"; 
		String creditCardMonth = "1"; 
		String creditCardYear = "2014";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes_CreditCard(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail,  userFirstName,  userLastName, creditCardNumber, creditCardMonth,  creditCardYear,item1,item2);
	}
	
	private static void runNewCustomer_NonshippableMultipleLinesOrder_EFT(String estoreURL)
	{
		String accountType = "Savings";//Checking or Savings
		String routingNumber = "063210112";
		String accountNumber = "81686868686891";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes_EFT(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail,  userFirstName,  userLastName, accountType, routingNumber,  accountNumber,item1,item2);
	}
	
	private static void runExistingCustomer_NonshippableOrder(String estoreURL)
	{
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String userId ="shankar_1210_001";
		//String userId = "E2EAutomated1816420";//FUNC Credit Card - "E2EAutomated663900";
		//String userId = "E2EAutomated2952507"; //SYS Credit Card
		//String password = "password123";
		String password = "password123";
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes(estoreURL,item,maxPageLoadWaitTimeInMinutes,userId, password);
	}
	
	private static void runExistingCustomer_NonshippableBundleOrder(String estoreURL)
	{
		String item = "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION";
		String maxPageLoadWaitTimeInMinutes = "5";
		String userId ="shankar_1210_001";
		//String userId = "E2EAutomated1816420";//FUNC Credit Card - "E2EAutomated663900";
		//String userId = "E2EAutomated2952507"; //SYS Credit Card
		//String password = "password123";
		String password = "test123";
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes(estoreURL, item, maxPageLoadWaitTimeInMinutes, userId, password);
	}
	
	private static void runExistingCustomer_NonshippableMultipleLinesOrder(String estoreURL)
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String userId = "E2EAutomated1816420";//FUNC Credit Card - "E2EAutomated663900";
		//String userId = "E2EAutomated2952507"; //SYS Credit Card
		String password = "password123";
		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, userId,password,item1,item2);
	}
}
