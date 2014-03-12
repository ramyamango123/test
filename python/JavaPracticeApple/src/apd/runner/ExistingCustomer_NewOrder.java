package initiate.apd.runner;

import initiate.apd.util.APDUtil;

import java.util.HashMap;

import selenium.util.SeleniumUtil;

import com.intuit.taf.logging.Logger;
import com.intuit.taf.testing.TestRun;
import com.thoughtworks.selenium.Selenium;

/**  
* Selenium-Automated APD Order Flows for an Existing Customer<br>
* 
* @author  Jeffrey Walker
* @version 1.0.0 
*/ 
public class ExistingCustomer_NewOrder
{
	SeleniumUtil seleniumUtil;
	private static Logger sLog;
	private APDUtil estoreUtil;
	
	
	public ExistingCustomer_NewOrder()
	{
		sLog = Logger.getInstance();
		seleniumUtil = new SeleniumUtil();
		estoreUtil = new APDUtil();
	}
//
//	/**
//	 * Submits a Nonshippable Order (Download/Subscription) with default item attributes <br>
//	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
//	 * 
//	 * @param estoreURL URL for the Estore Offering page 
//	 * @param item The item number of the offering being purchased. This has to be the exact value in the combobox on the estore offering page (ex: "Intuit QuickBooks Point Of Sale. | 1099874")
//	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
//	 * @param userId The userId used to sign into the existing account
//	 * @param password The password used to sign into the existing account
//	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
//	 */
//	public HashMap<String,Object> submitNonShippableOrder_DefaultItemAttributes(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object userId, Object password)
//	{
//		
//		String estoreURLStr = (String)estoreURL;
//		String itemStr = (String)item;
//		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
//		String userIdStr = (String)userId;
//		String passwordStr = (String)password;
//		
//		//Required---------------------------------------------------------------------------------------------
//		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
//		HashMap<String,Object> outputMap = new HashMap<String, Object>();
//		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
//		//-----------------------------------------------------------------------------------------------------
//		
//		TestRun.startSuite("Existing Customer | Non-Shippable Order | Default Item Attributes");
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
//	
//	/**
//	 * Submits a Nonshippable Bundle Order with default item attributes <br>
//	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
//	 * 
//	 * @param estoreURL URL for the Estore Offering page 
//	 * @param item The item number of the offering being purchased. This has to be the exact value in the combobox from the estore CPC bundle offering page (ex: "Buy ProSeries Tax Import Unlimited and ProSeries Document Management System for $999-1099799-PROMOTION")
//	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
//	 * @param userId The userId used to sign into the existing account
//	 * @param password The password used to sign into the existing account
//	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
//	 */
//	public HashMap<String,Object> submitNonShippableBundleOrder_DefaultItemAttributes(Object estoreURL,Object item,Object maxPageLoadWaitTimeInMinutes,Object userId, Object password)
//	{
//		
//		String estoreURLStr = (String)estoreURL;
//		String itemStr = (String)item;
//		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
//		String userIdStr = (String)userId;
//		String passwordStr = (String)password;
//		
//		
//		//Required---------------------------------------------------------------------------------------------
//		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
//		HashMap<String,Object> outputMap = new HashMap<String, Object>();
//		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
//		//-----------------------------------------------------------------------------------------------------
//		
//		TestRun.startSuite("Existing Customer | Non-Shippable Bundle Order | Default Item Attributes");
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
//			estoreUtil.addBundleOfferingToShoppingCart(selenium,itemStr,maxPageLoadWaitTimeInMilliseconds);
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
//	
//	/**
//	 * Submits a Nonshippable Multiple Line Order (Download/Subscription) with default item attributes <br>
//	 * using the default billing profile (Credit Card or EFT) for an Existing Customer Account. <br>
//	 * 
//	 * @param estoreURL URL for the Estore Offering page 
//	 * @param maxPageLoadWaitTimeInMinutes The maximum amount of time in minutes the script will wait for any given page to load before timing out.
//	 * @param userId The userId used to sign into the existing account
//	 * @param password The password used to sign into the existing account
//	 * @param items The item number of the offering being purchased. The values here have to be the exact values in the combobox from the estore offering page.<br>
//	 * 				***Important:This parameter can take any number of items.  (ex: "Intuit QuickBooks Point Of Sale. | 1099874","Intuit QuickBooks Payroll Enhanced | 1099581").
//	 * @return Output Map with values for the following keys ("AccountName" and "OrderNumber") 
//	 */
//	public HashMap<String,Object> submitNonShippableMultipleLinesOrder_DefaultItemAttributes(Object estoreURL,
//			Object maxPageLoadWaitTimeInMinutes,Object userId, Object password, Object... items)
//    {
//		
//		String estoreURLStr = (String)estoreURL;
//		String maxPageLoadWaitTimeInMinutesStr = (String)maxPageLoadWaitTimeInMinutes;
//		String userIdStr = (String)userId;
//		String passwordStr = (String)password;
//		String[]itemsArray = (String[])items;
//		
//		
//		//Required---------------------------------------------------------------------------------------------
//		Selenium selenium = seleniumUtil.setUpSelenium(estoreURLStr);
//		HashMap<String,Object> outputMap = new HashMap<String, Object>();
//		outputMap.put("AccountName","ACCOUNT_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		outputMap.put("OrderNumber","ORDER_WAS_NOT_SUBMITTED_FROM_ESTORE");
//		String maxPageLoadWaitTimeInMilliseconds = Integer.toString(Integer.parseInt(maxPageLoadWaitTimeInMinutesStr)*60000);
//		//-----------------------------------------------------------------------------------------------------
//		
//		TestRun.startSuite("Existing Customer | Multiple Non-Shippable Same Lines Order | Default Item Attributes | Credit Card");
//		
//		try
//		{
//			selenium.windowFocus();
//			selenium.windowMaximize();
//			
//			//Launch Offering Page
//			estoreUtil.launchOfferingPage(selenium,estoreURLStr,maxPageLoadWaitTimeInMilliseconds);
//				
//			//Add Offerings to Shopping Cart
//			for(int i=0;i<itemsArray.length;i++)
//			{
//				outputMap = estoreUtil.addOfferingToShoppingCart(selenium,itemsArray[i],maxPageLoadWaitTimeInMilliseconds,outputMap);
//				
//				if(i<items.length-1)
//				{
//					selenium.goBack();
//					selenium.waitForPageToLoad(maxPageLoadWaitTimeInMilliseconds);
//				}
//			}
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
//    }
}
