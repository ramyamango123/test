import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dataput {
	public static void main(String[] args) {


String addressStr = "1611 E Highway 1901\n" +
		         "Harker Heights, TX\n" +
		          "76548";

//String addressStr = "\n2475 Garcia Ave \n" +
//		"Mountain View, CA \n" +
//		"94043-1106";


String [] addressArray = addressStr.split("\n");
//System.out.println(addressArray);
System.out.println("array1 " + addressArray[0]);
System.out.println("array2 " + addressArray[1].split(",")[0]);
System.out.println("array2 " + addressArray[1].split(",")[1]);
System.out.println("array3 " + addressArray[2].substring(0,5));


//outputmap.put("APDCompanyAddressLine1", addressArray[0]);
//outputmap.put("APDCompanyAddressLine2", "");
//outputmap.put("APDCompanyAddressCity", addressArray[1].split(",")[0]);
//outputmap.put("APDCompanyAddressState", addressArray[1].split(",")[1]);
//outputmap.put("APDCompanyAddressZipCode", addressArray[2].substring(0, 5));
//outputmap.put("APDCompanyAddressCountry", "US");
}
}
/**
 * Verify Cart Confirmation Page for Unlimited segment item
 * @param selenium Default Selenium 
 * @param maxPageLoadInMs The maximum amount of time in minutes that page has to load before timing out
 * @param itemName ProSeries/Lacerte segment item name
 * @param paymentType Type of payment (checking/savings)
 * @param lastThreeDigitsAccNo Last three digits of EFT account number 
 * @param lastFourDigitsRoutingNo Last four digits of EFT routing number 
 * @param bankAccName Name of the bank account 
 * @param productName Name of the product(Lacerte/ProSeries) 
 * @param endTest Boolean (True/False) used to decide whether to end test or not 
 */