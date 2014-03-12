package initiate.estore.driver;

import initiate.estore.runner.ExistingCustomer_NewOrder;
import initiate.estore.runner.MyAccount_Info;
import initiate.estore.runner.NewCustomer_NewOrder;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	//public static final String environment = "SYS"; public static final String estoreURL = "https://sboqa.pointofsale.intuit.com"; public static final String userId="E2EAutomated646636"; public static final String password="password123";
	public static final String environment = "TRN"; public static final String estoreURL = "https://sboqa.lacerte.intuit.com"; 
	public static final String userId="sboqa_0418210555"; 
	public static final String password="password-9";
	
	private static final String creditCardNumber="5100410000039130"; 
	private static final String creditCardMonth = "7"; 
	private static final String creditCardYear = "2015";
	private static final String routingNumber = "063210112";
	private static final String accountNumber = "81686868686891";
	private static final String CVV = "123";
	private static final String streetAddress="EcoSpace";
	private static final String streetAddress2="Bellandur";
	private static final String city="San Diego";
	private static final String state="CA";
	private static final String postalCode="92101";
	private static final String phoneNumber="1234567890";

	
		public static void main(String[] args) 
		{
			long start = 0;
			start = System.currentTimeMillis();
				
			try
			{
				//Initialize JMantis
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a zzzz");
				Date date = new Date();
				String dateStr = ".\\resources\\results\\" + dateFormat.format(date).replaceAll(":", "_");
				new File(dateStr).mkdir();
				TestRun.initializeTAF(dateStr + "\\EstoreInitateFlows_",".\\resources\\config\\config.properties",TestRun.LOGLEVEL.INFO, false, true);
				sLog = Logger.getInstance();
				TestRun.startSuite("Environment: " + environment);
			}
			catch (Exception e) 
		    {
				sLog.info(e.getStackTrace().toString());
		    }
			
			
			
	//		***************************Order Submission Test Cases (New Account) Test Cases
	//		new Main().runNewCustomer_NonshippableOrder_CreditCard_CVV(estoreURL);
	//		new Main().runNewCustomer_NonshippableOrder_CreditCard(estoreURL);
	//		new Main().runNewCustomer_NonshippableOrder_UserAddress(estoreURL);
	//		new Main().runNewCustomer_NonshippableOrder_EFT(estoreURL);
	//		new Main().runNewAccount_Perpetual_Free_Order(estoreURL);
	//		new Main().runNewAccount_Download_ReInstall_US_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_Download_ReInstall_APOBox_ShipaddressOrder_CreditCard(estoreURL); 
	//		new Main().runNewAccount_Download_ReInstall_POBox_ShipaddressOrder_CreditCard(estoreURL); 
	//		new Main().runNewAccount_Download_ReInstall_USPossession_ShipaddressOrder_CreditCard(estoreURL); 
	//		new Main().runNewAccount_Download_ReInstall_3rdParty_US_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_Download_ReInstall_3rdParty_APOBox_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_Download_ReInstall_3rdParty_POBox_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_Download_ReInstall_3rdParty_USPossession_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_LacerteReplcement_US_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_QB_POS_BundledHarware_US_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_CPC_Bundle_Tax_Online_2011_addressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runNewAccount_OptOutTrial_Order(estoreURL);
	//		new Main().runNewCustomer_NonshippableOrderUpdated_EFT(estoreURL);
	//		new Main().runNewCustomer_NonshippableBundleOrder_CreditCard(estoreURL);
	//		new Main().runNewCustomer_NonshippableBundleOrder_EFT(estoreURL);
	//		new Main().runNewCustomer_NonshippableMultipleLinesOrder_CreditCard(estoreURL);
	//		new Main().runNewCustomer_NonshippableMultipleLinesOrder_EFT(estoreURL);
	//		new Main().runNewAccount_QB_POS_BundledHarware_EditBillingAddress_Creditcard(estoreURL);
	//		new Main().runNewAccountSubmitRestrictedPartyOrder(estoreURL);
	//		new Main().runNewAccount_OptInTrial_Order(estoreURL); 
	//		new Main().runNewCustomer_SalesForce_CreditCard(estoreURL);
			 
			
	//		***************************Updated Offering (Item Attributes) Test Cases
	//		new Main().runNewCustomer_OrderWithUpdatedConfig_CreditCard(estoreURL);
	//		new Main().runExistingAccount_AddAnUpdatedOffering(estoreURL);
			
	//		***************************Update Account Test Cases
	//		new Main().runExistingAccountUpdateCCBPAtReviewOrderPageWithUserInfo(estoreURL);
	//		new Main().runExistingAccountUpdateCCBPAtReviewOrderPage(estoreURL);
	//		new Main().runExistingUpdateEFTBPAtReviewOrderPage(estoreURL);//E2EAutomated2617937<-- SPECIAL CASE: Account needs to have an EFT Billing Profile*********************************
	//		
	////		***************************Order Submission Test Cases (Existing Account) Test Cases
	//		new Main().runExisting_TaxExemptUser_NonShippableOrder(estoreURL);
	//		new Main().runExistingNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(estoreURL);//<--This creates a new account first
	//		new Main().runExistingCustomer_NonshippableOrder(estoreURL);
	//		new Main().runExistingCustomer_NonshippableBundleOrder(estoreURL);
	//		new Main().runExistingCustomer_NonshippableMultipleLinesOrder(estoreURL);
	//		new Main().runExistingAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_EFT(estoreURL);
	//		new Main().runExistingAccount_TaxOnline_2011_Order_CreditCard(estoreURL);
	//		new Main().runExistingAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(estoreURL);
	//		new Main().runExistingAccount_Download_ReInstall_StandardUSA_EditShipaddress(estoreURL);
	//		new Main().runExistingAccount_Download_ReInstall_StandardUSA_EditBillingAddress(estoreURL);
	//		new Main().runExistingAccount_Perpetual_Free_Order(estoreURL);
	//		new Main().runExistingAccount_OptInTrial_Order(estoreURL);
	//		new Main().runExistingAccount_OptOutTrial_Order(estoreURL);
	//		
	//		
	//		***************************My Accounts Test Cases
	//		new Main().myAccounts_ExistingAccountCreateServiceRequest(estoreURL);
	//		new Main().myAccounts_NewUserCreateServiceRequest(estoreURL);
	//		new Main().myAccounts_updateBillingCredicardInfo(estoreURL);
//			new Main().myAccounts_NewCustomerupdateBillingEFTInfo(estoreURL);
			new Main().myAccounts_updateAccount(estoreURL);
	//		new Main().myAccounts_validateLoginPage(estoreURL);
					
	//		new Main().myAccounts_ExistingUserAddNewCompany(estoreURL); //<--Broken: there is no longer an "Add New" link for adding a new company
	//		new Main().myAccounts_ExistingUserAddNewCompanyWithUserPreferences(estoreURL); //<--Broken: there is no longer an "Add New" link for adding a new company
	//		new Main().myAccounts_updateBillingEFTInfo(estoreURL);	
	//		new Main().myAccounts_ExistingUserUpdateEFTBillingProfileWithUserInfo(estoreURL);  //Account - E2EAutomated2617937
	//		new Main().myAccounts_NewUserModifyOrderMac(estoreURL);   // Works for asset - "Intuit QuickBooks Point Of Sale. | 1099874"		
	//		new Main().myAccounts_modifyOrderMAC(estoreURL);//        // Works for asset - "Intuit QuickBooks Point Of Sale. | 1099874" //Account - E2EAutomated2617937 // Upto 20 users	
	//		new Main().myAccounts_NewUserModifyServiceMACD(estoreURL);// Only Asset it works - "Intuit QuickBooks Payroll Basic | 1099581" 		
	//		new Main().myAccounts_modifyServiceMAC(estoreURL);  //Always need an aaccount with asset - "Intuit QuickBooks Payroll Basic | 1099581". Will work once.
	//		**************************Exception Handling Test Cases
	//		runLoginFailureAndCapture(estoreURL);
	
			
	
	//		***************************Broken Tests
	//		runNewAccount_Download_ReInstall_StandardCANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue
	//		runNewAccount_Download_ReInstall_POBox_CANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue
	//		runNewAccount_Download_ReInstall_3rdParty_StandardCANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue
	//		runNewAccount_Download_ReInstall_3rdParty_POBox_CANADA_ShipaddressOrder_CreditCard(estoreURL); //Issue 
	//		new Main().runNewAccount_Payroll_3Users_US_ShipaddressOrder_CreditCard(estoreURL);//Issue 
	//		runNewAccount_QB_POS_BundledHarware_EditBillingAddress_Creditcard(estoreURL); //Edit Billing address issue
	//		// Shankar has issues runNewAccount_QB_POS_BundledHarware_EditShippingAddress(estoreURL); //Edit Billing address issue
	//		runNewAccount_ifMultipleItem_then_promotion(estoreURL);
	//		runNewAccount_MultipleItem_then_promtion_due_to_one_Item(estoreURL);
	//		runNewAccount_MultipleItem_then_code_not_applied_to_cart(estoreURL);
	//		runNewAccount_MultipleItem_then_Promotion_condition_does_not_meet(estoreURL);
	//		runExistingAccount_order_apply_promtion(estoreURL); */
	
			
			
			//Process total elapsed time for test run 
			long elapsedTimeMillis = System.currentTimeMillis()-start;
			long seconds = (elapsedTimeMillis/1000)% 60;
			long minutes = (elapsedTimeMillis/(1000*60))% 60;
			long hours = (elapsedTimeMillis/(1000*60*60))% 24;
			System.out.println("Total Test Execution Time --> " +hours + " hours " + minutes + " minutes " + seconds + " seconds");
			sLog.info("Total Test Execution Time --> " +hours + " hours " + minutes + " minutes " + seconds + " seconds");	
			
			//Terminate JManitis
			TestRun.endSuite();
			TestRun.terminateTAF(true);
		}
	
	
	//**********************************************************************************************************************************************************************
	//Order Submission Test Cases (New Account)
	//**********************************************************************************************************************************************************************
	
	public void runNewCustomer_NonshippableOrder_CreditCard(String estoreURL)
	{
		
		//String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String item = "Intuit QuickBooks Point Of Sale VAD. | 1099873";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
	}
	
	public void runNewCustomer_NonshippableOrder_EFT(String estoreURL)
	{
		String accountType = "Checking";//Checking or Savings
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@inuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
	}
	
	public void runNewAccount_Perpetual_Free_Order(String estoreURL)
	{
		String paymentType="No"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale Free Download. | 1099871";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@inuit.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_StandardUSA"; 
		String shipAddressNew="No";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_StandardUSA";
		String billAddressNew="No"; //No
		String billAddressType="Billing_StandardUSA";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_Download_ReInstall_US_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_StandardUSA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_StandardUSA";
		String billAddressNew="No"; //No
		String billAddressType="Billing_StandardUSA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		//String estoreUserType=" New Shipping and Billing Address";
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_Download_ReInstall_APOBox_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_APOBox_USA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_APOBox_USA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_APOBox_USA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		//String estoreUserType=" New Shipping and Billing Address";
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);													
	}
	
	public void runNewAccount_Download_ReInstall_POBox_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_POBox_USA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_POBox_USA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_POBox_USA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_Download_ReInstall_USPossession_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_USPossession_USA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_USPossession_USA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_USPossession_USA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_Download_ReInstall_3rdParty_US_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_StandardUSA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_3rdParty";
		String shipAddressType="Shipping_StandardUSA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_StandardUSA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_Download_ReInstall_3rdParty_APOBox_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_APOBox_USA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_3rdParty";
		String shipAddressType="Shipping_APOBox_USA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_APOBox_USA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);														
	}
	
	public void runNewAccount_Download_ReInstall_3rdParty_POBox_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_POBox_USA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_3rdParty";
		String shipAddressType="Shipping_POBox_USA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_POBox_USA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_Download_ReInstall_3rdParty_USPossession_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_USPossession_USA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_3rdParty";
		String shipAddressType="Shipping_USPossession_USA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_USPossession_USA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_LacerteReplcement_US_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
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
		
		//String estoreUserType=" New Shipping and Billing Address";
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);														
	}
	
	public void runNewAccount_QB_POS_BundledHarware_US_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
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
		
		//String estoreUserType=" New Shipping and Billing Address";
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);														
	}
	
	public void runNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
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
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);													
	}
	
	public void runNewAccount_CPC_Bundle_Tax_Online_2011_addressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
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
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	public void runNewAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String Product_Type="normal";
		
		String item1= "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
		String item2= "Intuit QuickBooks Payroll Basic | 1099581";
		
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_StandardUSA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_StandardUSA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_StandardUSA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		//newCustomerNewOrder.submitMultipleOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, shipAddressType, shipType,companyAddress,billAddressType,productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType,accountNumber,routingNumber,shipAddressNew,billAddressNew,item1,item2);
		newCustomerNewOrder.submitMultipleOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,productType,cpcIndividualBank, cpcIndividualUsage,item1,item2);														
	}
	
	public void runNewAccount_OptOutTrial_Order(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String Product_Type="normal";
		String item= "Intuit Data Protect With 30 Day Opt Out Trial | 1099621";
		//
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
		String billAddressNew="yes"; //No
		String billAddressType="Billing_StandardUSA";
		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
		
		//String estoreUserType=" New Shipping and Billing Address";
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);													
	}
	
	public void runNewCustomer_NonshippableOrderUpdated_EFT(String estoreURL)
	{
		String accountType = "Checking";//Checking or Savings
		//String item = "name=Intuit QuickBooks2009-Premier | 1099588#fulfillmentMethod=Download with CD#users=2 Users";
		String item = "name=Intuit QuickBooks Point Of Sale. | 1099874#edition=Pro#fulfillmentMethod=Download with Reinstall#productType=Upgrade User";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitUpdatedItemConfigOrder_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
	}
	
	public void runNewCustomer_NonshippableBundleOrder_CreditCard(String estoreURL)
	{
		String item = "Tax Online Tax Year 2011 Signup-1099830";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
	}
	
	
	public void runNewCustomer_NonshippableBundleOrder_EFT(String estoreURL)
	{
		String accountType = "Checking";//Checking or Savings
		String item = "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
	}
	
	public void runNewCustomer_NonshippableMultipleLinesOrder_CreditCard(String estoreURL)
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@intit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
		
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes_CreditCard(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail,  userFirstName,  userLastName, creditCardNumber, creditCardMonth,  creditCardYear,item1,item2);
	}
	
	public void runNewCustomer_NonshippableMultipleLinesOrder_EFT(String estoreURL)
	{
		String accountType = "Savings";//Checking or Savings
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
		String item2 = "Intuit QuickBooks Point of Sale Multi-Store Monthly Plan. | 1099859";
		
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes_EFT(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail,  userFirstName,  userLastName, accountType, routingNumber,  accountNumber,item1,item2);
	}
	
	public void runNewAccount_QB_POS_BundledHarware_EditBillingAddress_Creditcard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@inuit.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_StandardUSA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_StandardUSA";
		String editAdddress="Billing";
		
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_StandardUSA";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.newUsersubmitOrder_EditBillingShippingAddress_Creditcard_EFT(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,editAdddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);													
	}
	
	public void runNewAccountSubmitRestrictedPartyOrder(String estoreURL)
	{
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitOrder_RestrictedParty(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, creditCardNumber, creditCardMonth, creditCardYear);
	}
	
	public void runNewAccount_OptInTrial_Order(String estoreURL)
	{
		String paymentType="No"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Payroll Assisted | 1099734";//"Intuit Assisted Payroll | 1099734";
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
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);													
	}
	
	
	//**********************************************************************************************************************************************************************
	//Updated Offering (Item Attributes) Test Cases
	//**********************************************************************************************************************************************************************
	
	public void runNewCustomer_OrderWithUpdatedConfig_CreditCard(String estoreURL)
	{
		String item = "name=Intuit QuickBooks Point Of Sale. | 1099874#edition=Pro#fulfillmentMethod=Download with Reinstall#productType=Upgrade User";
		//String item = "name=Intuit QuickBooks Point Of Sale. | 1099874#edition=Pro#fulfillmentMethod=Download#productType=New User#users=2";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitOrder_WithUpdatedItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
	}
	
	public void runExistingAccount_AddAnUpdatedOffering(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		//String item= "Intuit QuickBooks Payroll Standard | 109958123";//#name=Intuit QuickBooks2009-Premier | 1099588/fulfillmentMethod=Download with CD#Intuit QuickBooks Payroll Standard | 1099581";
		String item = "name=Intuit QuickBooks Point Of Sale. | 1099874#edition=Pro#fulfillmentMethod=Download with Reinstall#productType=Upgrade User";
		
		ExistingCustomer_NewOrder updateOffering = new ExistingCustomer_NewOrder();
		updateOffering.submitAnUpdatedOffering(estoreURL, item, userId, password, maxPageLoadWaitTimeInMinutes);
		
	}
	
	
	//**********************************************************************************************************************************************************************
	//Update Account Test Cases
	//**********************************************************************************************************************************************************************
		
	public void runExistingAccountUpdateCCBPAtReviewOrderPageWithUserInfo(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String billAddressType="Billing_StandardUSA";
		ExistingCustomer_NewOrder updateBPAtReviewPageWithUserInfo = new ExistingCustomer_NewOrder();
		updateBPAtReviewPageWithUserInfo.updateBillingProfileAtReviewOrderPage(estoreURL, item, creditCardNumber, creditCardMonth, creditCardYear, billAddressType, maxPageLoadWaitTimeInMinutes, userId, password);
	}
	
	public void runExistingAccountUpdateCCBPAtReviewOrderPage(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		ExistingCustomer_NewOrder updateBPAtReviewPageWithUserPresentCCType = new ExistingCustomer_NewOrder();
		updateBPAtReviewPageWithUserPresentCCType.updateBillingProfileWithUserPresentCardType(estoreURL, item, userId, password, maxPageLoadWaitTimeInMinutes);
	}
	
	public void runExistingUpdateEFTBPAtReviewOrderPage(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		//String userId = "E2EAutomated1816420";//FUNC Credit Card - "E2EAutomated663900";
		//String userId = "shankar_1210_001"; //SYS 
		//String password = "test123";
		
		String userId = "E2EAutomated555711"; //SYS <-- Account needs to have an EFT Billing Profile*********************************
		String password = "password123";
		
		ExistingCustomer_NewOrder updateEFTAtReviewPageWithUserPresentCCType = new ExistingCustomer_NewOrder();
		updateEFTAtReviewPageWithUserPresentCCType.updateEFTBillingProfileFromReviewPage(estoreURL, item, userId, password, maxPageLoadWaitTimeInMinutes);
	}
		
	
	//**********************************************************************************************************************************************************************
	//Order Submission Test Cases (Existing Account) Test Cases
	//**********************************************************************************************************************************************************************
	
	public void runExistingNewAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String Product_Type="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_StandardUSA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_StandardUSA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_StandardUSA";
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitOrder_ExistingAccount(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
	}
	
	
	
	public void runExistingCustomer_NonshippableOrder(String estoreURL)
	{
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String userId = "E2EAutomated2617937";
		String password = "password123";
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes(estoreURL,item,maxPageLoadWaitTimeInMinutes,userId, password);
	}
	
	public void runExistingCustomer_NonshippableBundleOrder(String estoreURL)
	{
		String item = "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION";
		String maxPageLoadWaitTimeInMinutes = "5";
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes(estoreURL, item, maxPageLoadWaitTimeInMinutes, userId, password);
	}
	
	public void runExistingCustomer_NonshippableMultipleLinesOrder(String estoreURL)
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, userId,password,item1, item2);
	}	
	
	public void runExistingAccount_Download_ReInstall_StandardUSA_ShipaddressOrder_EFT(String estoreURL)
	{
		String paymentType="EFT"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="IntuitQB_POS_Download_Reinstall";
		//String SignInURLs="normal";
		String item= "Intuit QuickBooks Point Of Sale. | 1099874";
		String cpcIndividualBank="25";
		String cpcIndividualUsage="50";
		String maxPageLoadWaitTimeInMinutes = "2";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		String userFirstName = "Shankara"; 
		String userLastName = "Narayan";
		String companyAddress="Company_StandardUSA"; 
		String shipAddressNew="Yes";
		String shipType="Ship_Normal";
		String shipAddressType="Shipping_StandardUSA";
		String billAddressNew="Yes"; //No
		String billAddressType="Billing_StandardUSA";
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitShippable_ExistingUser_Order_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, companyAddress, shipAddressNew, shipAddressType, shipType, billAddressNew, billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear, accountType, accountNumber, routingNumber, userId , password,item, productType, cpcIndividualBank, cpcIndividualUsage);														
	}
	
	public void runExistingAccount_TaxOnline_2011_Order_CreditCard(String estoreURL)
	{
		String paymentType="EFT"; //EFT
		String accountType = "Savings";//Checking or Savings
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
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitShippable_ExistingUser_Order_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, companyAddress, shipAddressNew, shipAddressType, shipType, billAddressNew, billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear, accountType, accountNumber, routingNumber, userId , password,item, productType, cpcIndividualBank, cpcIndividualUsage);  
	}
	
	public void runExistingAccount_multipleProductsCD_Download_US_ShipaddressOrder_CreditCard(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String SignInURLs="normal";
		String item1 = "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
		String item2 = "Intuit QuickBooks Payroll Basic | 1099581";
		
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
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitCD_Download_MultipleOrders_ExistingUser_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes,  newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,userId,password,cpcIndividualBank, cpcIndividualUsage,productType, item1, item2);												
	}
	
	public void runExistingAccount_Download_ReInstall_StandardUSA_EditShipaddress(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
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
		String editAdddress="Shipping_normal";
		
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitShippableOrder_EditExistingUserShippingAddress(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, companyAddress, editAdddress, shipAddressNew, shipAddressType, shipType, billAddressNew, billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear, accountType, accountNumber, routingNumber, userId , password,item, productType, cpcIndividualBank, cpcIndividualUsage);																										 
	}
	
	
	public void runExistingAccount_Download_ReInstall_StandardUSA_EditBillingAddress(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
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
		String editAdddress="Billing";
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitShippableOrder_EditExistingUserShippingAddress(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, companyAddress, editAdddress, shipAddressNew, shipAddressType, shipType, billAddressNew, billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear, accountType, accountNumber, routingNumber, userId , password,item, productType, cpcIndividualBank, cpcIndividualUsage);														
	}
	
	public void runExistingAccount_Perpetual_Free_Order(String estoreURL)
	{
		String paymentType="No"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String SignInURLs="normal";
		String item= "Intuit QuickBooks Point Of Sale Free Download. | 1099871";
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
		String billAddressNew="No"; //No
		String billAddressType="Billing_StandardUSA";
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitShippable_ExistingUser_Order_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, companyAddress, shipAddressNew, shipAddressType, shipType, billAddressNew, billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear, accountType, accountNumber, routingNumber, userId , password,item, productType, cpcIndividualBank, cpcIndividualUsage);													
	}
	
	
	public void runExistingAccount_OptInTrial_Order(String estoreURL)
	{
		String paymentType="No"; //EFT
		String accountType = "Savings";//Checking or Savings
		//String Product_Type="CPC_Bundle_Tax_Online_2011";
		String productType="normal";
		//String SignInURLs="normal";
		String item= "Intuit QuickBooks Payroll Assisted | 1099734";//"Intuit Assisted Payroll | 1099734";
		//Intuit QuickBooks Point Of Sale Retailer Success Kit 30 Day Trial. | 1099863
		
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
		String billAddressNew="No"; //No
		String billAddressType="Billing_StandardUSA";
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitShippable_ExistingUser_Order_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, companyAddress, shipAddressNew, shipAddressType, shipType, billAddressNew, billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear, accountType, accountNumber, routingNumber, userId , password,item, productType, cpcIndividualBank, cpcIndividualUsage);														
	}
	
	
	public void runExistingAccount_OptOutTrial_Order(String estoreURL)
	{
		String paymentType="creditcard"; //EFT
		String accountType = "Savings";//Checking or Savings
		String productType="normal";
		String item= "Intuit Data Protect With 30 Day Opt Out Trial | 1099621";
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
		
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitOrder_ExistingAccount(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
																
	}
	
	
	//**********************************************************************************************************************************************************************
	//My Account Test Cases
	//**********************************************************************************************************************************************************************
	
	public void myAccounts_ExistingAccountCreateServiceRequest(String estoreURL)
	{
		String maxPageLoadWaitTimeInMinutes = "10";
		String product = "ProSeries";//"Lacerte - Business";
		String category = "I need help using the program";
		String subject = "This is the subject of the request";
		String description = "This is my description of the request";
		
		MyAccount_Info createCustomerServiceRequest = new MyAccount_Info();
		createCustomerServiceRequest.submitServiceRequest(estoreURL, maxPageLoadWaitTimeInMinutes,userId, password, product,category, subject, description);                                                
	}
	
	public void myAccounts_NewUserCreateServiceRequest(String estoreURL) 
	{
		String item = "Intuit QuickBooks Payroll Basic | 1099581";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String product = "ProSeries";//"Lacerte - Business";
		String category = "I need help using the program";
		String subject = "This is the subject of the request";
		String description = "This is my description of the request";
		
		MyAccount_Info newCustomerCreateServiceRequest = new MyAccount_Info();
		newCustomerCreateServiceRequest.NewCustomerSubmitServiceRequest(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, product, category, subject, description);
	}
	
	public void myAccounts_updateBillingCredicardInfo(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		
		MyAccount_Info updateCreditCardInfo = new MyAccount_Info();
		updateCreditCardInfo.updateBillingCreditCard(estoreURL, userId, password, maxPageLoadWaitTimeInMinutes);
	}
	
	public void myAccounts_updateAccount(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "10";
		String phoneNumber = "5602014325";
		String extension = "";
		String address1 = "25 Nelson Street";
		String address2 = "";
		String city = "San Diego";
		String state = "CA";
		String country = "";
		String zip = "92123";
		String eMail = "superuser@gmail.com";
				
		MyAccount_Info updateAccountInfo = new MyAccount_Info();
		updateAccountInfo.updateAccount(estoreURL, userId, password, phoneNumber, extension, address1, address2, city, state, country, zip, eMail, maxPageLoadWaitTimeInMinutes);
	}
	
	public void myAccounts_modifyOrderMAC(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String product = "Intuit QuickBooks Point of Sale";
		String userId = "E2EAutomated2617937";
		String password = "password123";
		MyAccount_Info modifyUserOrder = new MyAccount_Info();
		modifyUserOrder.updateOrder(estoreURL, userId, password, product, maxPageLoadWaitTimeInMinutes);
	}
	public void myAccounts_NewUserModifyOrderMac(String estoreURL) 
	{
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String product = "Intuit QuickBooks Point of Sale";
		
		MyAccount_Info newCustomerUpdateOrder = new MyAccount_Info(); 
		newCustomerUpdateOrder.newCustomerUpdateOrder(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, product);
	}
	
	public void myAccounts_NewUserModifyServiceMACD(String estoreURL) 
	{
		String item = "Intuit QuickBooks Payroll Basic | 1099581";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		String service = "Intuit QuickBooks Payroll Annual.";
		
		MyAccount_Info newCustomerUpdateService = new MyAccount_Info(); 
		newCustomerUpdateService.NewCustomerUpdateService(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear, service);
	}
	
	public void myAccounts_NewCustomerupdateBillingEFTInfo(String estoreURL) 
	{
		String accountType = "Checking";//Checking or Savings
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@int.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		MyAccount_Info updateEFTInfo = new MyAccount_Info();
		updateEFTInfo.NewCustomerupdateEFTBillingProfile(estoreURL, item, maxPageLoadWaitTimeInMinutes,newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
	}
		
	public void myAccounts_ExistingUserAddNewCompany(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		
		MyAccount_Info existingUserAddNewCompany = new MyAccount_Info();
		existingUserAddNewCompany.existingUserAddNewCompany(estoreURL, userId, password, newUserEmail, maxPageLoadWaitTimeInMinutes);
	}
	
	public void myAccounts_ExistingUserAddNewCompanyWithUserPreferences(String estoreURL) 
	{
		
		String maxPageLoadWaitTimeInMinutes = "10";
		String accountName = userId;
		String streetAddress = "33 Wellington town";
		String city = "San Diego";
		String state = "CA";
		String zip = "92101";
		String phoneNumber = "";
		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
		
		MyAccount_Info existingUserAddNewCompanyWithUserInfo = new MyAccount_Info();
		existingUserAddNewCompanyWithUserInfo.existingUserAddNewCompanyWithUserDetails(estoreURL, userId, password, accountName, streetAddress, city, state, zip, phoneNumber, newUserEmail, maxPageLoadWaitTimeInMinutes);
	}
	
	public void myAccounts_validateLoginPage(String estoreURL) 
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		MyAccount_Info validate = new MyAccount_Info();
		validate.validateLoginPage(estoreURL,maxPageLoadWaitTimeInMinutes);
	}
	

//	public void runNewAccount_Payroll_3Users_US_ShipaddressOrder_CreditCard(String estoreURL)
//	{
//		String paymentType="creditcard"; //EFT
//		String accountType = "Savings";//Checking or Savings
//		//String Product_Type="CPC_Bundle_Tax_Online_2011";
//		//String productType="IntuitQB_POS_Download_Reinstall";
//		String productType="IntuitQB_Payroll_3User";
//		String item= "Intuit QuickBooks Payroll Basic | 1099581";
//		String cpcIndividualBank="25";
//		String cpcIndividualUsage="50";
//		String maxPageLoadWaitTimeInMinutes = "2";
//		String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
//		String userFirstName = "Shankara"; 
//		String userLastName = "Narayan";
//		String companyAddress="Company_StandardUSA"; 
//		String shipAddressNew="No";
//		String shipType="Ship_Normal";
//		String shipAddressType="Shipping_StandardUSA";
//		String billAddressNew="Yes"; //No
//		String billAddressType="Billing_StandardUSA";
//		//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
//		
//		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//		newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
//																
//	}
		
//		private static void runExistingAccount_order_apply_promtion(String estoreURL) {
//			String item = "Intuit QuickBooks Point Of Sale. | 1099874";
//			String maxPageLoadWaitTimeInMinutes = "5";
//			String promotionCode = "59775761028";
//			String username = "E2EAutomated964643";//"E2EAutomated3944339";//FUNC Credit Card - "E2EAutomated663900";
//			String password = "password123";
//			
//			ExistingCustomer_NewOrder itemWithPromotion = new ExistingCustomer_NewOrder();
//			itemWithPromotion.singleOrder_Apply_Promotion(estoreURL, item, username, password, promotionCode, maxPageLoadWaitTimeInMinutes);
//			
//			
//		}
//		
//		private static void runNewAccount_ifMultipleItem_then_promotion(String estoreURL) {
//			

//			String maxPageLoadWaitTimeInMinutes = "5";
//			String newUserEmail = "jeffrey_walker@int.com";
//			String userFirstName = "Jeffrey"; 
//			String userLastName = "Walker";
//			String item1 = "name=Intuit QuickBooks2009-Premier | 1099588#fulfillmentMethod=Download with CD";
//			String item2 = "Intuit QuickBooks Payroll Basic | 1099580";
//			String item3 = "Intuit Care Plus Plan Monthly - QB 2011 Onwards | 1099629";
//			String promotionCode = "59730389293";
//			
//			NewCustomer_NewOrder ItemWithPromotion = new NewCustomer_NewOrder();
//			ItemWithPromotion.submitMultipleLinesOrdersWithPromotionAutoApplied_DefaultItemAttributes_CreditCard(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear,promotionCode,item1,item2,item3);
//			
//		}
//
//
//		
//		private static void runNewAccount_MultipleItem_then_Promotion_condition_does_not_meet(String estoreURL) {

//			String maxPageLoadWaitTimeInMinutes = "5";
//			String newUserEmail = "jeffrey_walker@int.com";
//			String userFirstName = "Jeffrey"; 
//			String userLastName = "Walker";
//			String item1 = "name=Intuit QuickBooks2009-Premier | 1099588#fulfillmentMethod=Download with CD";
//			String item2 = "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
//			String promotionCode = "59775761028";
//			
//			NewCustomer_NewOrder Items_Promotion_does_not_meet_condition = new NewCustomer_NewOrder();
//			Items_Promotion_does_not_meet_condition.submitMultipleLinesOrdersWithPromotion_NotMeet_Condition_DefaultItemAttributes_CreditCard(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear,promotionCode,item1,item2);
//			
//			
//		}
//		
//		private static void runNewAccount_MultipleItem_then_promtion_due_to_one_Item(String estoreURL) {

//			String maxPageLoadWaitTimeInMinutes = "5";
//			String newUserEmail = "jeffrey_walker@int.com";
//			String userFirstName = "Jeffrey"; 
//			String userLastName = "Walker";
//			String item1 = "name=Intuit QuickBooks2009-Premier | 1099588#fulfillmentMethod=Download with CD";
//			String item2 = "Intuit QuickBooks Payroll Standard | 1099581";
//			String promotionCode="59775761028";
//			
//			NewCustomer_NewOrder multipleItemWithPromotion = new NewCustomer_NewOrder();
//			multipleItemWithPromotion.submitMultipleLinesOrdersWithPromotion_Applied_for_One_DefaultItemAttributes_CreditCard(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear,promotionCode,item1, item2);
//			
//			
//		}
//		
//		private static void runNewAccount_MultipleItem_then_code_not_applied_to_cart(String estoreURL) {

//			String maxPageLoadWaitTimeInMinutes = "5";
//			String newUserEmail = "jeffrey_walker@int.com";
//			String userFirstName = "Jeffrey"; 
//			String userLastName = "Walker";
//			String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
//			String item2 = "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
//			String promotionalCode = "59779879656";
//			
//			NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//			newCustomerNewOrder.submitMultipleLinesOrder_With_NoPromotion_applied_to_cart(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail,  userFirstName,  userLastName, creditCardNumber, creditCardMonth,  creditCardYear,promotionalCode,item1, item2);
//			
//		}
//
//	private static void runNewAccount_QB_POS_BundledHarware_EditShippingAddress(String estoreURL)
//{
//	String paymentType="creditcard"; //EFT
//	String accountType = "Savings";//Checking or Savings
//	//String Product_Type="CPC_Bundle_Tax_Online_2011";
//	String productType="normal";
//	//String Product_Type="normal";
//	String item= "Intuit QuickBooks Point of Sale Bundled Hardware. | 1099872";
//	String cpcIndividualBank="25";
//	String cpcIndividualUsage="50";
//	String maxPageLoadWaitTimeInMinutes = "2";
//	String newUserEmail = "Shankaranarayana_narayanasamy@intuit.com";
//	String userFirstName = "Shankara"; 
//	String userLastName = "Narayan";
//	String companyAddress="Company_StandardUSA"; 
//	String shipAddressNew="Yes";
//	String shipType="Ship_Normal";
//	String shipAddressType="Shipping_StandardUSA";
//	String editAdddress="Shipping_normal";
//	
//	String billAddressNew="Yes"; //No
//	String billAddressType="Billing_StandardUSA";
//	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//	newCustomerNewOrder.newUsersubmitOrder_EditBillingShippingAddress_Creditcard_EFT(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,editAdddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
//															
//}	
//	private static void runNewAccount_Download_ReInstall_StandardCANADA_ShipaddressOrder_CreditCard(String estoreURL)
//{
//	String paymentType="creditcard"; //EFT
//	String accountType = "Savings";//Checking or Savings
//	//String Product_Type="CPC_Bundle_Tax_Online_2011";
//	String productType="IntuitQB_POS_Download_Reinstall";
//	//String Product_Type="normal";
//	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
//	String cpcIndividualBank="25";
//	String cpcIndividualUsage="50";
//	String maxPageLoadWaitTimeInMinutes = "2";
//	String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
//	String userFirstName = "Shankara"; 
//	String userLastName = "Narayan";
//	String companyAddress="Company_StandardCANADA"; 
//	String shipAddressNew="Yes";
//	String shipType="Ship_Normal";
//	String shipAddressType="Shipping_StandardCANADA";
//	String billAddressNew="Yes"; //No
//	String billAddressType="Billing_StandardCANADA";
//	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
//	
//	//
//	String estoreUserType=" New Shipping and Billing Address";
//	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
//	newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
//															
//}
//	private static void runNewAccount_Download_ReInstall_POBox_CANADA_ShipaddressOrder_CreditCard(String estoreURL)
//{
//	String paymentType="creditcard"; //EFT
//	String accountType = "Savings";//Checking or Savings
//	//String Product_Type="CPC_Bundle_Tax_Online_2011";
//	String productType="IntuitQB_POS_Download_Reinstall";
//	//String Product_Type="normal";
//	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
//	String cpcIndividualBank="25";
//	String cpcIndividualUsage="50";
//	String maxPageLoadWaitTimeInMinutes = "2";
//	String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
//	String userFirstName = "Shankara"; 
//	String userLastName = "Narayan";
//	String companyAddress="Company_POBox_CANADA"; 
//	String shipAddressNew="Yes";
//	String shipType="Ship_Normal";
//	String shipAddressType="Shipping_POBox_CANADA";
//	String billAddressNew="Yes"; //No
//	String billAddressType="Billing_POBox_CANADA";
//	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
//	
//	//
//	String estoreUserType=" New Shipping and Billing Address";
//	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
//	newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
//															
//}
//
//	private static void runNewAccount_Download_ReInstall_3rdParty_StandardCANADA_ShipaddressOrder_CreditCard(String estoreURL)
//{
//	String paymentType="creditcard"; //EFT
//	String accountType = "Savings";//Checking or Savings
//	//String Product_Type="CPC_Bundle_Tax_Online_2011";
//	String productType="IntuitQB_POS_Download_Reinstall";
//	//String Product_Type="normal";
//	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
//	String cpcIndividualBank="25";
//	String cpcIndividualUsage="50";
//	String maxPageLoadWaitTimeInMinutes = "2";
//	String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
//	String userFirstName = "Shankara"; 
//	String userLastName = "Narayan";
//	String companyAddress="Company_StandardCANADA"; 
//	String shipAddressNew="Yes";
//	String shipType="Ship_3rdParty";
//	String shipAddressType="Shipping_StandardCANADA";
//	String billAddressNew="Yes"; //No
//	String billAddressType="Billing_StandardCANADA";
//	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
//	
//	//
//	String estoreUserType=" New Shipping and Billing Address";
//	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
//	newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
//															
//}
//	private static void runNewAccount_Download_ReInstall_3rdParty_POBox_CANADA_ShipaddressOrder_CreditCard(String estoreURL)
//{
//	String paymentType="creditcard"; //EFT
//	String accountType = "Savings";//Checking or Savings
//	//String Product_Type="CPC_Bundle_Tax_Online_2011";
//	String productType="IntuitQB_POS_Download_Reinstall";
//	//String Product_Type="normal";
//	String item= "Intuit QuickBooks Point Of Sale. | 1099874";
//	String cpcIndividualBank="25";
//	String cpcIndividualUsage="50";
//	String maxPageLoadWaitTimeInMinutes = "2";
//	String newUserEmail = "Shankaranarayana_narayanasamy@int.com";
//	String userFirstName = "Shankara"; 
//	String userLastName = "Narayan";
//	String companyAddress="Company_POBox_CANADA"; 
//	String shipAddressNew="Yes";
//	String shipType="Ship_3rdParty";
//	String shipAddressType="Shipping_POBox_CANADA";
//	String billAddressNew="Yes"; //No
//	String billAddressType="Billing_POBox_CANADA";
//	//Company_StandardUSA Company_POBox_USA Company_APOBox_USA Company_StandardCANADA Company_POBox_CANADA Company_USPossession_USA
//	
//	//
//	String estoreUserType=" New Shipping and Billing Address";
//	NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//	//estoreURL,existingActEstoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber,creditCardMonth,  creditCardYear, shipAddressType, shipType, companyAddress,billAddressType, productType,cpcIndividualBank,cpcIndividualUsage,paymentType,accountType, accountNumber, routingNumber, shipAddressNew, billAddressNew, estoreUserType)
//	newCustomerNewOrder.submitOrder_newUser(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName,companyAddress,shipAddressNew,shipAddressType, shipType,billAddressNew,billAddressType, paymentType, creditCardNumber, creditCardMonth, creditCardYear,accountType,accountNumber,routingNumber,item, productType, cpcIndividualBank, cpcIndividualUsage);
//															
//}
//	/*private static void runNewCustomer_NonshippableOrder_CreditCard(String estoreURL)
//	{
//		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
//		String maxPageLoadWaitTimeInMinutes = "5";
//		String newUserEmail = "jeffrey_walker@int.com";
//		String userFirstName = "Jeffrey"; 
//		String userLastName = "Walker";
//		
//		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
//	}
//	
//	
//	private static void runNewCustomer_NonshippableOrder_EFT(String estoreURL)
//	{
//		String accountType = "Checking";//Checking or Savings
//		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
//		String maxPageLoadWaitTimeInMinutes = "5";
//		String newUserEmail = "jeffrey_walker@intuit.com";
//		String userFirstName = "Jeffrey"; 
//		String userLastName = "Walker";
//		
//		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
//	}
//	
//	private static void runNewCustomer_NonshippableBundleOrder_CreditCard(String estoreURL)
//	{
//		String item = "Tax Online Tax Year 2011 Signup-1099830";
//		String maxPageLoadWaitTimeInMinutes = "5";
//		String newUserEmail = "jeffrey_walker@intuit.com";
//		String userFirstName = "Jeffrey"; 
//		String userLastName = "Walker";
//		
//		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//		newCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes_CreditCard(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear);
//	}
//	
//	private static void runNewCustomer_NonshippableBundleOrder_EFT(String estoreURL)
//	{
//		String accountType = "Checking";//Checking or Savings
//		String item = "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION";
//		String maxPageLoadWaitTimeInMinutes = "5";
//		String newUserEmail = "jeffrey_walker@intuit.com";
//		String userFirstName = "Jeffrey"; 
//		String userLastName = "Walker";
//		
//		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//		newCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes_EFT(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, accountType,routingNumber,accountNumber);
//	}
//	
//	private static void runNewCustomer_NonshippableMultipleLinesOrder_CreditCard(String estoreURL)
//	{
//		String maxPageLoadWaitTimeInMinutes = "5";
//		String newUserEmail = "jeffrey_walker@intuit.com";
//		String userFirstName = "Jeffrey"; 
//		String userLastName = "Walker";
//		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
//		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
//		
//		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//		newCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes_CreditCard(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail,  userFirstName,  userLastName, creditCardNumber, creditCardMonth,  creditCardYear,item1,item2);
//	}
//	
//	private static void runNewCustomer_NonshippableMultipleLinesOrder_EFT(String estoreURL)
//	{
//		String accountType = "Savings";//Checking or Savings
//		String maxPageLoadWaitTimeInMinutes = "5";
//		String newUserEmail = "jeffrey_walker@intuit.com";
//		String userFirstName = "Jeffrey"; 
//		String userLastName = "Walker";
//		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
//		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
//		
//		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
//		newCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes_EFT(estoreURL, maxPageLoadWaitTimeInMinutes, newUserEmail,  userFirstName,  userLastName, accountType, routingNumber,  accountNumber,item1,item2);
//	}
//	
//	private static void runExistingCustomer_NonshippableOrder(String estoreURL)
//	{
//		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
//		String maxPageLoadWaitTimeInMinutes = "5";
//		//String userId ="shankar_1210_001";
//		//String userId = "E2EAutomated1816420";//FUNC Credit Card - "E2EAutomated663900";
//		String userId = "E2EAutomated2952507"; //SYS Credit Card
//		String password = "password123";
//	//	String password = "password123";
//		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
//		existingCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes(estoreURL,item,maxPageLoadWaitTimeInMinutes,userId, password);
//	}
//	
//	private static void runExistingCustomer_NonshippableBundleOrder(String estoreURL)
//	{
//		String item = "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION";
//		String maxPageLoadWaitTimeInMinutes = "5";
//		//String userId ="shankar_1210_001";
//		//String userId = "E2EAutomated1816420";//FUNC Credit Card - "E2EAutomated663900";
//		String userId = "E2EAutomated2952507"; //SYS Credit Card
//		String password = "password123";
//		//String password = "test123";
//		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
//		existingCustomerNewOrder.submitNonShippableBundleOrder_DefaultItemAttributes(estoreURL, item, maxPageLoadWaitTimeInMinutes, userId, password);
//	}
//	
//	private static void runExistingCustomer_NonshippableMultipleLinesOrder(String estoreURL)
//	{
//		String maxPageLoadWaitTimeInMinutes = "5";
//		//String userId = "E2EAutomated1816420";//FUNC Credit Card - "E2EAutomated663900";
//		String userId = "E2EAutomated2952507"; //SYS Credit Card
//		String password = "password123";
//		String item1 = "Intuit QuickBooks Point Of Sale. | 1099874";
//		String item2 = "Intuit QuickBooks Payroll Enhanced | 1099581";
//		
//		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
//		existingCustomerNewOrder.submitNonShippableMultipleLinesOrder_DefaultItemAttributes(estoreURL, maxPageLoadWaitTimeInMinutes, userId,password,item1,item2);
//	}*/

	private static void myAccounts_modifyServiceMAC(String estoreURL) {
		String maxPageLoadWaitTimeInMinutes = "5";
		String username = "E2EAutomated964643";//"E2EAutomated3944339";//FUNC Credit Card - "E2EAutomated663900";
		String password = "password123";
		//String userId = "E2EAutomated2952507"; //SYS Credit Card */
		String service = "Intuit QuickBooks Payroll Annual.";
				
		MyAccount_Info modifyUserOrder = new MyAccount_Info(); 
		modifyUserOrder.updateService(estoreURL, username, password, service, maxPageLoadWaitTimeInMinutes);
		
	}
	private static void myAccounts_updateBillingEFTInfo(String estoreURL) {
		String maxPageLoadWaitTimeInMinutes = "5";
		String userId = "E2EAutomated2617937";//FUNC Credit Card - "E2EAutomated663900";
		String password = "password123";
		
		
		MyAccount_Info updateEFTInfo = new MyAccount_Info();
		updateEFTInfo.updateEFTBillingProfile(estoreURL, userId, password, maxPageLoadWaitTimeInMinutes);
		
		
	}
	
	private static void myAccounts_ExistingUserUpdateEFTBillingProfileWithUserInfo(String estoreURL) {
		String maxPageLoadWaitTimeInMinutes = "5";
		String userId = "E2EAutomated2617937";//FUNC Credit Card - "E2EAutomated663900";
		String password = "password123";
		String accountName = "TestUser";
		String accountType = "Checking";//Checking or Savings
		
		MyAccount_Info updateEFTWithUserInfo = new MyAccount_Info();
		updateEFTWithUserInfo.updateEFTBillingProfileWithUserInfo(estoreURL, userId, password, accountName, accountType, routingNumber, accountNumber, maxPageLoadWaitTimeInMinutes);
		
	}
	
	private void runNewCustomer_NonshippableOrder_CreditCard_CVV(
			String estoreURL) {
		// TODO Auto-generated method stub
		String item = "Intuit QuickBooks Enterprise Solutions | 1099578";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "LOL@intuit.com";
		String userFirstName = "Sanketh"; 
		String userLastName = "Dhanya";
	 
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_CreditCard_CVV(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear,CVV);
	}
	private void runNewCustomer_NonshippableOrder_UserAddress(
			String estoreURL) {
		// TODO Auto-generated method stub
		String item = "Intuit QuickBooks Enterprise Solutions | 1099578";
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "sanketh_b@intuit.com";
		String userFirstName = "Sanketh"; 
		String userLastName = "Dhanya";
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_UserAddress(estoreURL, item, maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, creditCardNumber, creditCardMonth, creditCardYear,streetAddress,streetAddress2,city,state,postalCode,phoneNumber);
	}
	
	private void runExisting_TaxExemptUser_NonShippableOrder(String estoreURL) {
		String item = "Intuit QuickBooks Point Of Sale. | 1099874";
		String maxPageLoadWaitTimeInMinutes = "5";
		String taxExemptUserID="taxexemptuser";
		String password2="test123";
		ExistingCustomer_NewOrder existingCustomerNewOrder = new ExistingCustomer_NewOrder();
		existingCustomerNewOrder.submitNonShippableOrder_DefaultItemAttributes_TaxExempt(estoreURL,item,maxPageLoadWaitTimeInMinutes,taxExemptUserID, password2,creditCardNumber,creditCardMonth,creditCardYear);
		
	}
	
	public void runNewCustomer_SalesForce_CreditCard(String estoreURL)
	{
		String maxPageLoadWaitTimeInMinutes = "5";
		String newUserEmail = "jeffrey_walker@inuit.com";
		String userFirstName = "Jeffrey"; 
		String userLastName = "Walker";
		
		
		NewCustomer_NewOrder newCustomerNewOrder = new NewCustomer_NewOrder();
		newCustomerNewOrder.submitNonShippableOrder_SalesForce_CreditCard(estoreURL, "Salesforce for QuickBooks | 1100234", maxPageLoadWaitTimeInMinutes, newUserEmail, userFirstName, userLastName, "378282246310005", creditCardMonth, creditCardYear,"1234");
	}


}