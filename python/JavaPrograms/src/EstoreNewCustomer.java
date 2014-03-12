package initiate.estore.runner;

import initiate.estore.util.EstoreUtil;

import java.util.HashMap;

import selenium.util.SeleniumUtil;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;
import com.thoughtworks.selenium.Selenium;

/**
 * Selenium-Automated Estore Order Flows for a New Customer<br>
 * 
 * @author Jeffrey Walker
 * @version 1.0.0
 */
public class NewCustomer_NewOrder {
	SeleniumUtil seleniumUtil;
	private static Logger sLog;
	private EstoreUtil estoreUtil;

	public NewCustomer_NewOrder() {
		sLog = Logger.getInstance();
		seleniumUtil = new SeleniumUtil();
		estoreUtil = new EstoreUtil();
	}

	/**
	 * Submits a Nonshippable Order (Download/Subscription) with default item
	 * attributes <br>
	 * using a Credit Card Billing Profile for a New Customer Account. <br>
	 * 
	 * @param estoreURL
	 *            URL for the Estore Offering page
	 * @param item
	 *            The item number of the offering being purchased. This has to
	 *            be the exact value in the combobox on the estore offering page
	 *            (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
	 * @param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out.
	 * @param newUserEmail
	 *            Email for the new user
	 * @param userFirstName
	 *            First Name of the new contact
	 * @param userLastName
	 *            Last Name of the new contact
	 * @param creditCardNumber
	 *            Credit Card Number
	 * @param creditCardMonth
	 *            Credit Card Expiration Month
	 * @param creditCardYear
	 *            Credit Card Expiration Year
	 * @return Output Map with values for the following keys ("AccountName" and
	 *         "OrderNumber")
	 */
	public HashMap<String, Object> submitNonShippableOrder_DefaultItemAttributes_CreditCard(
			Object estoreURL, Object item, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object creditCardNumber, Object creditCardMonth,
			Object creditCardYear) {

		// Required Conversion of parameter types
		String estoreURLStr = (String) estoreURL;
		String itemStr = (String) item;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String creditCardNumberStr = (String) creditCardNumber;
		String creditCardMonthStr = (String) creditCardMonth;
		String creditCardYearStr = (String) creditCardYear;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		TestRun.startSuite("New Customer | Non-Shippable Order | Default Item Attributes | Credit Card");

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(selenium, itemStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populateNewCompanyInformation(selenium,
					userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Load the Payment Method Page
			estoreUtil.launchNewPaymentMethodPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate the Payment Information
			estoreUtil.populateCreditCardPaymentInformation(selenium,
					maxPageLoadWaitTimeInMilliseconds, creditCardNumberStr,
					creditCardMonthStr, creditCardYearStr);

			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);
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
	 * Submits a Nonshippable Order (Download/Subscription) with default item
	 * attributes <br>
	 * using an EFT Billing Profile for a New Customer Account. <br>
	 * 
	 * @param estoreURL
	 *            URL for the Estore Offering page
	 * @param item
	 *            The item number of the offering being purchased. This has to
	 *            be the exact value in the combobox on the estore offering page
	 *            (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
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
	public HashMap<String, Object> submitNonShippableOrder_DefaultItemAttributes_EFT(
			Object estoreURL, Object item, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object accountType, Object routingNumber, Object accountNumber) {

		// Required Conversion of parameter types
		String estoreURLStr = (String) estoreURL;
		String itemStr = (String) item;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String accountTypeStr = (String) accountType;
		String routingNumberStr = (String) routingNumber;
		String accountNumberStr = (String) accountNumber;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		TestRun.startSuite("New Customer | Non-Shippable Order | Default Item Attributes | EFT");

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Add Offering to Shopping Cart
			outputMap = estoreUtil.addOfferingToShoppingCart(selenium, itemStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populateNewCompanyInformation(selenium,
					userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Load the Payment Method Page
			estoreUtil.launchNewPaymentMethodPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate the Payment Information
			estoreUtil.populateEFTPaymentInformation(selenium,
					maxPageLoadWaitTimeInMilliseconds, accountTypeStr,
					accountNumberStr, routingNumberStr);

			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);
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
	 * Submits a Nonshippable Bundle Order with default item attributes <br>
	 * using a Credit Card Billing Profile for a New Customer Account. <br>
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
	 * @param creditCardMonth
	 *            Credit Card Expiration Month
	 * @param creditCardYear
	 *            Credit Card Expiration Year
	 * @return Output Map with values for the following keys ("AccountName" and
	 *         "OrderNumber")
	 */
	public HashMap<String, Object> submitNonShippableBundleOrder_DefaultItemAttributes_CreditCard(
			Object estoreURL, Object item, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object creditCardNumber, Object creditCardMonth,
			Object creditCardYear) {

		// Required Conversion of parameter types
		String estoreURLStr = (String) estoreURL;
		String itemStr = (String) item;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String creditCardNumberStr = (String) creditCardNumber;
		String creditCardMonthStr = (String) creditCardMonth;
		String creditCardYearStr = (String) creditCardYear;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		TestRun.startSuite("New Customer | Non-Shippable Bundle Order | Default Item Attributes | Credit Card");

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Add Offering to Shopping Cart
			outputMap = estoreUtil.addBundleOfferingToShoppingCart(selenium,
					itemStr, maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populateNewCompanyInformation(selenium,
					userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Load the Payment Method Page
			estoreUtil.launchNewPaymentMethodPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate the Payment Information
			estoreUtil.populateCreditCardPaymentInformation(selenium,
					maxPageLoadWaitTimeInMilliseconds, creditCardNumberStr,
					creditCardMonthStr, creditCardYearStr);

			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);
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
	 * Submits a Nonshippable Bundle Order with default item attributes <br>
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
	public HashMap<String, Object> submitNonShippableBundleOrder_DefaultItemAttributes_EFT(
			Object estoreURL, Object item, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object accountType, Object routingNumber, Object accountNumber) {

		// Required Conversion of parameter types
		String estoreURLStr = (String) estoreURL;
		String itemStr = (String) item;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String accountTypeStr = (String) accountType;
		String routingNumberStr = (String) routingNumber;
		String accountNumberStr = (String) accountNumber;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		TestRun.startSuite("New Customer | Non-Shippable Bundle Order | Default Item Attributes | EFT");

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Add Offering to Shopping Cart
			outputMap = estoreUtil.addBundleOfferingToShoppingCart(selenium,
					itemStr, maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populateNewCompanyInformation(selenium,
					userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Load the Payment Method Page
			estoreUtil.launchNewPaymentMethodPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate the Payment Information
			estoreUtil.populateEFTPaymentInformation(selenium,
					maxPageLoadWaitTimeInMilliseconds, accountTypeStr,
					accountNumberStr, routingNumberStr);

			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);
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
	 * Submits a Nonshippable Multiple line Order (Download/Subscription) with
	 * default item attributes <br>
	 * using a Credit Card Billing Profile for a New Customer Account. <br>
	 * 
	 * @param estoreURL
	 *            URL for the Estore Offering page
	 * @param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out.
	 * @param newUserEmail
	 *            Email for the new user
	 * @param userFirstName
	 *            First Name of the new contact
	 * @param userLastName
	 *            Last Name of the new contact
	 * @param creditCardMonth
	 *            Credit Card Expiration Month
	 * @param creditCardYear
	 *            Credit Card Expiration Year
	 * @param items
	 *            The item number of the offering being purchased. The values
	 *            here have to be the exact values in the combobox from the
	 *            estore offering page.<br>
	 *            ***Important:This parameter can take any number of items. (ex:
	 *            "Intuit QuickBooks Point Of Sale. | 1099874"
	 *            ,"Intuit QuickBooks Payroll Enhanced | 1099581").
	 * @return Output Map with values for the following keys ("AccountName" and
	 *         "OrderNumber")
	 */
	public HashMap<String, Object> submitNonShippableMultipleLinesOrder_DefaultItemAttributes_CreditCard(
			Object estoreURL, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object creditCardNumber, Object creditCardMonth,
			Object creditCardYear, Object... items) {

		// Required Conversion of parameter types
		String estoreURLStr = (String) estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String creditCardNumberStr = (String) creditCardNumber;
		String creditCardMonthStr = (String) creditCardMonth;
		String creditCardYearStr = (String) creditCardYear;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		TestRun.startSuite("New Customer | Multiple Non-Shippable Same Lines Order | Default Item Attributes | Credit Card");

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			for (int i = 0; i < items.length; i++) {
				outputMap = estoreUtil.addOfferingToShoppingCart(selenium,
						items[i].toString(), maxPageLoadWaitTimeInMilliseconds,
						outputMap);

				if (i < items.length - 1) {
					selenium.goBack();
					selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
				}
			}

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populateNewCompanyInformation(selenium,
					userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Load the Payment Method Page
			estoreUtil.launchNewPaymentMethodPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate the Payment Information
			estoreUtil.populateCreditCardPaymentInformation(selenium,
					maxPageLoadWaitTimeInMilliseconds, creditCardNumberStr,
					creditCardMonthStr, creditCardYearStr);

			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);
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
	 * Submits a Nonshippable Multiple line Order (Download/Subscription) with
	 * default item attributes <br>
	 * using an EFT Billing Profile for a New Customer Account. <br>
	 * 
	 * @param estoreURL
	 *            URL for the Estore Offering page
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
	 * @param items
	 *            The item number of the offering being purchased. The values
	 *            here have to be the exact values in the combobox from the
	 *            estore offering page.<br>
	 *            ***Important:This parameter can take any number of items. (ex:
	 *            "Intuit QuickBooks Point Of Sale. | 1099874"
	 *            ,"Intuit QuickBooks Payroll Enhanced | 1099581").
	 * @return Output Map with values for the following keys ("AccountName" and
	 *         "OrderNumber")
	 */
	public HashMap<String, Object> submitNonShippableMultipleLinesOrder_DefaultItemAttributes_EFT(
			Object estoreURL, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object accountType, Object routingNumber, Object accountNumber,
			Object... items) {

		// Required Conversion of parameter types
		String estoreURLStr = (String) estoreURL;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String accountTypeStr = (String) accountType;
		String routingNumberStr = (String) routingNumber;
		String accountNumberStr = (String) accountNumber;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		TestRun.startSuite("New Customer | Multiple Non-Shippable Same Lines Order | Default Item Attributes | EFT");

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Add Offerings to Shopping Cart
			for (int i = 0; i < items.length; i++) {
				outputMap = estoreUtil.addOfferingToShoppingCart(selenium,
						items[i].toString(), maxPageLoadWaitTimeInMilliseconds,
						outputMap);

				if (i < items.length - 1) {
					selenium.goBack();
					selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
				}
			}

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populateNewCompanyInformation(selenium,
					userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

			// Load the Payment Method Page
			estoreUtil.launchNewPaymentMethodPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate the Payment Information
			estoreUtil.populateEFTPaymentInformation(selenium,
					maxPageLoadWaitTimeInMilliseconds, accountTypeStr,
					accountNumberStr, routingNumberStr);

			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);
		} catch (Exception e) {
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		} finally {
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}

		return outputMap;
	}

	// Added By Shankar

	/**
	 * Submits a Shippable, Download Order (Download/Subscription/CD/Hardware)
	 * with default item attributes <br>
	 * using a Credit Card Billing/ EFT Profile for a New Customer Account. <br>
	 * 
	 * @param estoreURL
	 *            URL for the Estore Offering page
	 * @param item
	 *            The item number of the offering being purchased. This has to
	 *            be the exact value in the combobox on the estore offering page
	 *            (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
	 * @param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out.
	 * @param newUserEmail
	 *            Email for the new user
	 * @param userFirstName
	 *            First Name of the new contact
	 * @param userLastName
	 *            Last Name of the new contact
	 * @param creditCardNumber
	 *            Credit Card Number
	 * @param creditCardMonth
	 *            Credit Card Expiration Month
	 * @param creditCardYear
	 *            Credit Card Expiration Year
	 * @param productTypeStr
	 *            ; To select the product option String
	 *            productType="IntuitQB_POS_Download_Reinstall";
	 * @param cpcIndividualBankStr
	 *            CPC product quantity selection max 25.
	 * @param cpcIndividualUsageStr
	 *            CPC product quantity selection max 50.
	 * @param paymentTypeStr
	 *            creditcard or EFT to enter that flow and create a new one.
	 * @param accountTypeStr
	 *            EFT account type Checking or Savings.
	 * @param accountNumberStr
	 *            EFT account number.
	 * @param routingNumberStr
	 *            EFT routing number.
	 * @param shipAddressTypeStr
	 *            ship address type to be created Shipping_StandardUSA
	 *            Shipping_POBox_USA Shipping_APOBox_USA Shipping_StandardCANADA
	 *            Shipping_POBox_CANADA Shipping_USPossession_USA
	 * @param shipTypeStr
	 *            for ship type String shipType="Ship_Normal"; or String
	 *            shipType="Ship_Normal" ;
	 * @param shipAddressNewStr
	 *            to determine if new address needs to be created or not Vales
	 *            are Yes or No
	 * @param billAddressTypeStr
	 *            ship address type to be created Billing_StandardUSA
	 *            Billing_POBox_USA Billing_APOBox_USA Billing_StandardCANADA
	 *            Billing_POBox_CANADA Billing_USPossession_USA
	 * @param billAddressNewStr
	 *            to determine if new address needs to be created or not Vales
	 *            are Yes or No
	 * @param companyAddressStr
	 *            ship address type to be created Company_StandardUSA
	 *            Company_POBox_USA Company_APOBox_USA Company_StandardCANADA
	 *            Company_POBox_CANADA Company_USPossession_USA
	 * @return Output Map with values for the following keys ("AccountName" and
	 *         "OrderNumber")
	 */
	public HashMap<String, Object> submitOrder_newUser(Object estoreURL,
			Object item, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object creditCardNumber, Object creditCardMonth,
			Object creditCardYear, Object shipAddressType, Object shipType,
			Object companyAddress, Object billAddressType, Object productType,
			Object cpcIndividualBank, Object cpcIndividualUsage,
			Object paymentType, Object accountType, Object accountNumber,
			Object routingNumber, Object shipAddressNew, Object billAddressNew) {
		// Required Conversion of parameter types

		// Object estoreURL,
		String estoreURLStr = (String) estoreURL;
		String itemStr = (String) item;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String creditCardNumberStr = (String) creditCardNumber;
		String creditCardMonthStr = (String) creditCardMonth;
		String creditCardYearStr = (String) creditCardYear;
		String companyAddressStr = (String) companyAddress;

		String productTypeStr = (String) productType;
		String cpcIndividualBankStr = (String) cpcIndividualBank;
		String cpcIndividualUsageStr = (String) cpcIndividualUsage;
		String paymentTypeStr = (String) paymentType;
		String accountTypeStr = (String) accountType;
		String accountNumberStr = (String) accountNumber;
		String routingNumberStr = (String) routingNumber;
		String shipAddressTypeStr = (String) shipAddressType;
		String shipTypeStr = (String) shipType;
		String shipAddressNewStr = (String) shipAddressNew;
		String billAddressTypeStr = (String) billAddressType;
		String billAddressNewStr = (String) billAddressNew;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		String TestCase_NameStr = "null";

		if (("Yes").equals(shipAddressNew) && ("Yes").equals(billAddressNew)) {
			TestCase_NameStr = "New User | " + itemStr + " | " + shipType
					+ "  " + shipAddressTypeStr + " | " + paymentTypeStr + " "
					+ billAddressType + " | ";
		} else if (("Yes").equals(shipAddressNew)
				&& ("No").equals(billAddressNew)) {
			TestCase_NameStr = "New User | " + itemStr + " | " + shipType
					+ "  " + shipAddressTypeStr + " | " + paymentTypeStr
					+ " | ";
		} else if (("No").equals(shipAddressNew)
				&& ("No").equals(billAddressNew)) {
			TestCase_NameStr = "New User | " + itemStr + " | " + paymentTypeStr
					+ " | ";
		} else {
			TestCase_NameStr = "New User | " + itemStr + " | " + paymentTypeStr
					+ " | ";
		}
		TestRun.startSuite(TestCase_NameStr);

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// if(("New_User_New_Bill_Ship").equals(Estore_User_TypeStr)){

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Add Offering to Shopping Cart
			if (("CPC_Bundle_Tax_Online_2011").equals(productTypeStr)) {
				outputMap = estoreUtil.addUsageBundleOfferingToShoppingCart(
						selenium, itemStr, maxPageLoadWaitTimeInMilliseconds,
						cpcIndividualBankStr, cpcIndividualUsageStr, outputMap);

			} else if (("IntuitQB_POS_Download_Reinstall")
					.equals(productTypeStr)) {
				outputMap = estoreUtil
						.addDownload_ReInstallOfferingToShoppingCart(selenium,
								itemStr, maxPageLoadWaitTimeInMilliseconds,
								outputMap);
			} else if (("IntuitQB_Payroll_3User").equals(productTypeStr)) {
				outputMap = estoreUtil
						.addPayRoll_3EmployeesOfferingToShoppingCart(selenium,
								itemStr, maxPageLoadWaitTimeInMilliseconds,
								outputMap);
			} else {
				outputMap = estoreUtil.addOfferingToShoppingCart(selenium,
						itemStr, maxPageLoadWaitTimeInMilliseconds, outputMap);
			}

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populatCD_Hardware_NewCompanyInformation(
					selenium, userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, companyAddressStr,
					outputMap);

			if (("Yes").equals(shipAddressNewStr)
					&& (selenium
							.isElementPresent("id=shippingAddressSameAsCompanyAddress") == true)) {
				if (("Ship_Normal").equals(shipTypeStr)) {
					// Populate New Shipping Information
					outputMap = estoreUtil
							.populateCD_Hardware_NewShippingInformation(
									selenium, userFirstNameStr,
									userLastNameStr,
									maxPageLoadWaitTimeInMilliseconds,
									shipAddressTypeStr, shipTypeStr, outputMap);
				} else if (("Ship_3rdParty").equals(shipTypeStr)) {
					// Populate New 3rd Party Shipping Information
					outputMap = estoreUtil
							.populateCD_Hardware_New_3rdPartyShippingInfo(
									selenium, userFirstNameStr,
									userLastNameStr,
									maxPageLoadWaitTimeInMilliseconds,
									shipAddressTypeStr, shipTypeStr, outputMap);
				}
			}
			// outputMap =
			// estoreUtil.populateCD_Hardware_NewShippingInformation(selenium,userFirstName,userLastName,maxPageLoadWaitTimeInMilliseconds,
			// ShipAddressType, ShipType, outputMap);

			// Load the Payment Method Page
			// estoreUtil.launchNewPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);

			if (("creditcard").equals(paymentTypeStr)) {
				if (("Yes").equals(billAddressNewStr)) {
					// Load the Payment Method Page
					estoreUtil.launchPaymentMethod_SubmitCompanyPageMethodPage(
							selenium, maxPageLoadWaitTimeInMilliseconds);
					estoreUtil
							.populateCreditCard_NewBillingProfilePaymentInformation(
									selenium,
									maxPageLoadWaitTimeInMilliseconds,
									creditCardNumberStr, creditCardMonthStr,
									creditCardYearStr, billAddressTypeStr,
									outputMap);
				} else {
					// Load the EFT Payment Method Page
					estoreUtil.launchNewPaymentMethodPage(selenium,
							maxPageLoadWaitTimeInMilliseconds);
					// Populate the Payment Information
					estoreUtil.populateCreditCardPaymentInformation(selenium,
							maxPageLoadWaitTimeInMilliseconds,
							creditCardNumberStr, creditCardMonthStr,
							creditCardYearStr);
				}
			} else if (("EFT").equals(paymentTypeStr)) {
				// Load the Payment Method Page
				estoreUtil.launchNewPaymentMethodPage(selenium,
						maxPageLoadWaitTimeInMilliseconds);
				// Populate the Payment Information
				estoreUtil.populateEFTPaymentInformation(selenium,
						maxPageLoadWaitTimeInMilliseconds, accountTypeStr,
						accountNumberStr, routingNumberStr);
			}
			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

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
	 * Submits a Shippable, Download Order (Download/Subscription/CD/Hardware)
	 * with default item attributes <br>
	 * using a Credit Card Billing/ EFT Profile for a New Customer Account. <br>
	 * 
	 * @param estoreURL
	 *            URL for the Estore Offering page
	 * @param item
	 *            The item number of the offering being purchased. This has to
	 *            be the exact value in the combobox on the estore offering page
	 *            (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
	 * @param maxPageLoadWaitTimeInMinutes
	 *            The maximum amount of time in minutes the script will wait for
	 *            any given page to load before timing out.
	 * @param newUserEmail
	 *            Email for the new user
	 * @param userFirstName
	 *            First Name of the new contact
	 * @param userLastName
	 *            Last Name of the new contact
	 * @param creditCardNumber
	 *            Credit Card Number
	 * @param creditCardMonth
	 *            Credit Card Expiration Month
	 * @param creditCardYear
	 *            Credit Card Expiration Year
	 * @param productTypeStr
	 *            ; To select the product option String
	 *            productType="IntuitQB_POS_Download_Reinstall";
	 * @param cpcIndividualBankStr
	 *            CPC product quantity selection max 25.
	 * @param cpcIndividualUsageStr
	 *            CPC product quantity selection max 50.
	 * @param paymentTypeStr
	 *            creditcard or EFT to enter that flow and create a new one.
	 * @param accountTypeStr
	 *            EFT account type Checking or Savings.
	 * @param accountNumberStr
	 *            EFT account number.
	 * @param routingNumberStr
	 *            EFT routing number.
	 * @param shipAddressTypeStr
	 *            ship address type to be created Shipping_StandardUSA
	 *            Shipping_POBox_USA Shipping_APOBox_USA Shipping_StandardCANADA
	 *            Shipping_POBox_CANADA Shipping_USPossession_USA
	 * @param shipTypeStr
	 *            for ship type String shipType="Ship_Normal"; or String
	 *            shipType="Ship_Normal" ;
	 * @param shipAddressNewStr
	 *            to determine if new address needs to be created or not Vales
	 *            are Yes or No
	 * @param billAddressTypeStr
	 *            ship address type to be created Billing_StandardUSA
	 *            Billing_POBox_USA Billing_APOBox_USA Billing_StandardCANADA
	 *            Billing_POBox_CANADA Billing_USPossession_USA
	 * @param billAddressNewStr
	 *            to determine if new address needs to be created or not Vales
	 *            are Yes or No
	 * @param companyAddressStr
	 *            ship address type to be created Company_StandardUSA
	 *            Company_POBox_USA Company_APOBox_USA Company_StandardCANADA
	 *            Company_POBox_CANADA Company_USPossession_USA
	 * @return Output Map with values for the following keys ("AccountName" and
	 *         "OrderNumber")
	 */
	public HashMap<String, Object> submitMultipleOrder_newUser(
			Object estoreURL, Object maxPageLoadWaitTimeInMinutes,
			Object newUserEmail, Object userFirstName, Object userLastName,
			Object creditCardNumber, Object creditCardMonth,
			Object creditCardYear, Object shipAddressType, Object shipType,
			Object companyAddress, Object billAddressType, Object productType,
			Object cpcIndividualBank, Object cpcIndividualUsage,
			Object paymentType, Object accountType, Object accountNumber,
			Object routingNumber, Object shipAddressNew, Object billAddressNew,
			Object... items) {
		// Required Conversion of parameter types

		// Object estoreURL,
		String estoreURLStr = (String) estoreURL;
		// String itemStr = (String)item;
		// String item1Str = (String)item1;
		// String item2Str = (String)item2;
		String maxPageLoadWaitTimeInMinutesStr = (String) maxPageLoadWaitTimeInMinutes;
		String newUserEmailStr = (String) newUserEmail;
		String userFirstNameStr = (String) userFirstName;
		String userLastNameStr = (String) userLastName;
		String creditCardNumberStr = (String) creditCardNumber;
		String creditCardMonthStr = (String) creditCardMonth;
		String creditCardYearStr = (String) creditCardYear;
		String companyAddressStr = (String) companyAddress;

		String productTypeStr = (String) productType;
		String cpcIndividualBankStr = (String) cpcIndividualBank;
		String cpcIndividualUsageStr = (String) cpcIndividualUsage;
		String paymentTypeStr = (String) paymentType;
		String accountTypeStr = (String) accountType;
		String accountNumberStr = (String) accountNumber;
		String routingNumberStr = (String) routingNumber;
		String shipAddressTypeStr = (String) shipAddressType;
		String shipTypeStr = (String) shipType;
		String shipAddressNewStr = (String) shipAddressNew;
		String billAddressTypeStr = (String) billAddressType;
		String billAddressNewStr = (String) billAddressNew;

		// Required---------------------------------------------------------------------------------------------
		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		outputMap.put("AccountName", "ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
		outputMap.put("OrderNumber", "ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer
				.parseInt(maxPageLoadWaitTimeInMinutesStr) * 60000);
		// -----------------------------------------------------------------------------------------------------

		String TestCase_NameStr = "null";

		if (("Yes").equals(shipAddressNew) && ("Yes").equals(billAddressNew)) {
			TestCase_NameStr = "New User |  Multiple Product Order CD & Download| "
					+ shipType
					+ "  "
					+ shipAddressTypeStr
					+ " | "
					+ paymentTypeStr + " " + billAddressType + " | ";
		} else if (("Yes").equals(shipAddressNew)
				&& ("No").equals(billAddressNew)) {
			TestCase_NameStr = "New User |  Multiple Product Order CD & Download | "
					+ shipType
					+ "  "
					+ shipAddressTypeStr
					+ " | "
					+ paymentTypeStr + " | ";
		} else if (("No").equals(shipAddressNew)
				&& ("No").equals(billAddressNew)) {
			TestCase_NameStr = "New User |  Multiple Product Order CD & Download | "
					+ paymentTypeStr + " | ";
		} else {
			TestCase_NameStr = "New User |  Multiple Product Order CD & Download | "
					+ paymentTypeStr + " | ";
		}
		TestRun.startSuite(TestCase_NameStr);

		try {
			selenium.windowFocus();
			selenium.windowMaximize();

			// if(("New_User_New_Bill_Ship").equals(Estore_User_TypeStr)){

			// Launch Offering Page
			estoreUtil.launchOfferingPage(selenium, estoreURLStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Add Offerings to Shopping Cart
			for (int i = 0; i < items.length; i++) {

				outputMap = estoreUtil.addOfferingToShoppingCart(selenium,
						items[i].toString(), maxPageLoadWaitTimeInMilliseconds,
						outputMap);

				if (i < items.length - 1) {
					selenium.goBack();
					selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
				}
			}

			// Launch the Sign In Page
			estoreUtil.launchSignInPage(selenium,
					maxPageLoadWaitTimeInMilliseconds);

			// Create New User
			estoreUtil.createNewUser(selenium, newUserEmailStr,
					maxPageLoadWaitTimeInMilliseconds);

			// Populate Company Information
			outputMap = estoreUtil.populatCD_Hardware_NewCompanyInformation(
					selenium, userFirstNameStr, userLastNameStr,
					maxPageLoadWaitTimeInMilliseconds, companyAddressStr,
					outputMap);

			if (("Yes").equals(shipAddressNewStr)
					&& (selenium
							.isElementPresent("id=shippingAddressSameAsCompanyAddress") == true)) {
				if (("Ship_Normal").equals(shipTypeStr)) {
					// Populate New Shipping Information
					outputMap = estoreUtil
							.populateCD_Hardware_NewShippingInformation(
									selenium, userFirstNameStr,
									userLastNameStr,
									maxPageLoadWaitTimeInMilliseconds,
									shipAddressTypeStr, shipTypeStr, outputMap);
				} else if (("Ship_3rdParty").equals(shipTypeStr)) {
					// Populate New 3rd Party Shipping Information
					outputMap = estoreUtil
							.populateCD_Hardware_New_3rdPartyShippingInfo(
									selenium, userFirstNameStr,
									userLastNameStr,
									maxPageLoadWaitTimeInMilliseconds,
									shipAddressTypeStr, shipTypeStr, outputMap);
				}
			}
			// outputMap =
			// estoreUtil.populateCD_Hardware_NewShippingInformation(selenium,userFirstName,userLastName,maxPageLoadWaitTimeInMilliseconds,
			// ShipAddressType, ShipType, outputMap);

			// Load the Payment Method Page
			// estoreUtil.launchNewPaymentMethodPage(selenium,maxPageLoadWaitTimeInMilliseconds);

			if (("creditcard").equals(paymentTypeStr)) {
				if (("Yes").equals(billAddressNewStr)) {
					// Load the Payment Method Page
					estoreUtil.launchPaymentMethod_SubmitCompanyPageMethodPage(
							selenium, maxPageLoadWaitTimeInMilliseconds);
					estoreUtil
							.populateCreditCard_NewBillingProfilePaymentInformation(
									selenium,
									maxPageLoadWaitTimeInMilliseconds,
									creditCardNumberStr, creditCardMonthStr,
									creditCardYearStr, billAddressTypeStr,
									outputMap);
				} else {
					// Load the EFT Payment Method Page
					estoreUtil.launchNewPaymentMethodPage(selenium,
							maxPageLoadWaitTimeInMilliseconds);
					// Populate the Payment Information
					estoreUtil.populateCreditCardPaymentInformation(selenium,
							maxPageLoadWaitTimeInMilliseconds,
							creditCardNumberStr, creditCardMonthStr,
							creditCardYearStr);
				}
			} else if (("EFT").equals(paymentTypeStr)) {
				// Load the Payment Method Page
				estoreUtil.launchNewPaymentMethodPage(selenium,
						maxPageLoadWaitTimeInMilliseconds);
				// Populate the Payment Information
				estoreUtil.populateEFTPaymentInformation(selenium,
						maxPageLoadWaitTimeInMilliseconds, accountTypeStr,
						accountNumberStr, routingNumberStr);
			}
			// Submit the Order
			outputMap = estoreUtil.submitOrder(selenium,
					maxPageLoadWaitTimeInMilliseconds, outputMap);

		} catch (Exception e) {
			e.printStackTrace();
			sLog.info(e.getStackTrace().toString());
		} finally {
			seleniumUtil.tearDownSelenium();
			TestRun.endSuite();
		}

		return outputMap;
	}

	// End Added By shankar

}
