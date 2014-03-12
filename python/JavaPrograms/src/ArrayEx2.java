public class ArrayEx2 {
	public static void main(String[] args) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(1); l.add(1); l.add(2); l.add(3);
		for (Integer i : l) {
			System.out.println(i);
		}
	}
}

/**
 * Verifies Account Renewal Information on account management page 
 * 
 * @param selenium Default Selenium 
 * @param userName User account name
 * @param userName User account password
 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
 * @param itemName ProSeries/Lacerte segment item name as displayed on account management page
 * @param accountName Firm name
 * @param CAN Customer Account Number
 * @param siebelDbUserName  Siebel DB username
 * @param siebelDbUserPassword  Siebel DB password
 * @param siebelDbURL Siebel DB URL
 * @param estoreDbUrl Estore DB URL
 * @param MDMDbUserName MDM DB username
 * @param profileDbUserName Profile DB username
 * @param estoreDbPassword Estore DB password
 * @param outputMap Map that will be populated with important information
 * @return Output Map with the value for the following key ("OrderNumber") 
 * @param accountPageName Account Management page url's relative path name (Proseries item - "myproseries"  and lacerte item - "myaccount") 
 */

/**
 * Utility method which helps to validate item's price on Shopping Cart page
 * @param selenium Default Selenium 
 * @param bodyText HTML source page
 * @param itemName ProSeries/Lacerte segment item name
 * @param paymentType Type of payment (checking/savings)
 * @param InterstitialPageItem An Item choosen to validate the price from Interstitial page - "ProSeries Document Management System"
 */