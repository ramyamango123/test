import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Splitex {
	public static void main(String[] args) {

// To verify company address
		
String addressStr = "\n2475 Garcia Ave \n" +
				"Mountain View, CA \n" +
				"94043-1106";


String [] addressArray = addressStr.split("\n");
System.out.println(addressArray);
System.out.println("array1 " + addressArray[1].split(",")[0]);
System.out.println("array2 " + addressArray[2].substring(0,5));
}

// To verify company name

//String companyNameStr = "Company Name: \n" +
//        "Proseries_renew1";
//
//String [] companyNameArray = companyNameStr.split("\n");
//System.out.print(companynameArray[1]);
		
// To verify company contact
//String companyContactStr = "Company Contact: \n" +
//	 "ram nag \n" +
//	 "proseries_renew@apdqa.com \n" +
//	 "5106786543"; 
//String [] companyContactArray = companyContactStr.split("\n");
//String firstname ;
//System.out.print(companyContactArray[1].split(" ")[0]);
//String lastname ;
//System.out.print(companyContactArray[1].split(" ")[1] + "\n");
//String emailaddress ;
//System.out.print(companyContactArray[2]);
//// String ph no;
//System.out.print(companyContactArray[3]);
//		
		

//		String companyContactStr = "Company Contact: \n" 
//		                           + "ram nag \n" 
//				                   + "proseries_renew@apdqa.com \n" 
//				                   + "5106786543\n"
//		                           + "Edit\n" 
//		                           + "Your Cart";
//		
//		 String patternStr = "Company Contact:(.*?)Your Cart";
//		 Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
//		 Matcher matcher = pattern.matcher(companyContactStr);
//		 boolean matchFound = matcher.find();
//		//System.out.println(matcher.group(1));
//		 String Address = matcher.group(1);
//		 System.out.print(Address);
		
		
//		String companyNameStr = " Company Name:\n" 
//				               + "Proseries_renew1\n"
//				               + "Company Address:";
//		String [] companyNameArray = companyNameStr.split("\n");
//		//System.out.print(companyNameArray[0]);
//		System.out.print("data" + companyNameArray[1]);
//		 String patternStr = "Company Name:(.*?)Company Address";
		 
		 
		 
//		 String item =  "ProSeries Pay Per Return License\n" +
//			        "$275.00\n" + 
//			   	    "ProSeries Professional Product Delivery Tax Year:Tax Year 2011\n" +
//			        "Release:Release 1\n" +
//			 
//			 "Total:\n" +
//			 "$275.00";
//		String name = "ProSeries Pay Per Return License" ;
//	   //String patternStr = name + "\\s+\\$(\\d+(\\.\\d{2})?)";
//	   String patternStr = "Total:\\s+\\$(\\d+(\\.\\d{2})?)";
	   
		
//	   String name = "ProSeries Pay Per Return License\n" +
//	                        "$275.00\n" +
//	                        "ProSeries Professional Product Delivery Tax Year:Tax Year 2011\n" +
//	                        "Release:Release 1\n" +
//	                        "ProSeries Professional Product Delivery\n" +
//	                        "$0.00\n" +
//	                        "Total:\n" +
//                           
//	                        "$275.00";
//	   
//	  String name = " ProSeries Pay Per Return License\n" +
//	                 "Tax Year 2011\n" +
//	   
//	               "$700.00\n" +
//	   
//	   
//	     "ProSeries Professional Product Delivery\n" +
//	     "Tax Year 2011 | Release 1\n" +
//	   
//	    "$0.00\n" +
//	   
//	    "Please wait while we process your discount code:\n" +
//
//	    "Subtotal:\n" +
//	    "$295.00" +
//	   
//	    "Sales Tax:\n" +
//	    "$22.69\n" +
//	   
//	    "Total:\n" +
//	   "$297.69";
	   
	   
//	   String patternStr = "Sales Tax:.*?\\s+\\$(\\d+(\\.\\d{2})?)";
			   
			   //"ProSeries Pay Per Return License.*?\\s+\\$(\\d+(\\.\\d{2})?)";
			   
			   
			   
			   
			   //"Total:\\s+\\$(\\d+(\\.\\d{2})?)";
			  // "ProSeries Pay Per Return License\\s+\\$(\\d+(\\.\\d{2})?)";
			   //"Total:\\s+\\$(\\d+(\\.\\d{2})?)";
	   
	   

//	String namenew = "ProSeries Pay Per Return License\n" +
//	   "Tax Year 2011\n" +
//	   
//	   
//	   
//	   "$222.00\n" +
//	   "ProSeries Professional Product Delivery\n"+
//	   "Tax Year 2011 | Release 1\n" +
//	   
//	   
//	   
//	   
//	   "$0.00\n" +
//	   
//	   
//
//	   "Subtotal:\n" +
//	   "$218.00\n" +
//	   
//	   
//	   "Shipping Cost:\n" +
//	   "$0.00\n" +
//	   
//	   "Sales Tax:\n" +
//	   "$22.69\n" +
//	   
//	   
//	   "Total:\n" +
//	  "$297.69\n" ;
//	   
	   
   //  String pat = "Total:.*?\\s+\\$(\\d+(\\.\\d{2})?)";
    		 //"Sales Tax:.*?\\s+\\$(\\d+(\\.\\d{2})?)";
    		 //"Subtotal:.*?\\s+\\$(\\d+(\\.\\d{2})?)";
    		 //"ProSeries Pay Per Return License.*?\\s+\\$(\\d+(\\.\\d{2})?)";
//    
//		String namenew = "Fujitsu Fi-6130 Scanner Scans 30 ppm (pages per minute) at 300 dpi (dots per inch) and is\n" +
//		 "Intuit's #1 recommended scanner. It is the perfect complement to our\n" +
//		 "ProSeries Tax Import and Document Management System productivity.\n" +
//		 "solutions. Save $429 off the MSRP ($1,299).\n"+
//		 
//		 "$870.00\n" + 
//		 
//		 "Add to Cart Learn More\n" +
//		 
//
//		 
//		 
//		 
//		 "ProSeries Document Management System Save time and office space with this tax document management system. Easily store and retrieve electronic documents on your PC, so you don't have to waste time or space storing hard copies of client documents in filing cabinets.\n" +
//		 
//		 "$450.00\n" + 
//		 "Add to Cart Learn More" +
//		 "No Thanks";
		
//		String namenew = "ProSeries Document Management System\n" +
// "Tax Year 2011\n" +
// "Tax Year Tax Year 2011\n" +
// "$455,56,60.00\n" +
// 
// 
// 
// "ProSeries Federal\n" +
// "1040NR Individual | Tax Year 2011\n" +
// 
// "$309.00\n" + 
// 
//  "Subtotal:\n" +
// "$759.00\n" +
// 
// 
// "Total:\n" +
// "$759.00 \n";
//		
//		
//		String namenew = "ProSeries Basic 1040 Federal\n" +
//		 "50 Returns Tax Year 2011\n" +
//		 "Regular:\n" + 
//		 "$4444.00\n" + 
//		 
//		 "TY11 ProSeries $50 Early Renewal Discount:\n" +
//		 "-$50.00\n" +
//		 
//		 "Your Price:\n" +
//		 "$109.00\n" +
//		 "Subtotal:\n" + 
//		 "$777.00\n" + 
//		 
//		 "All Discounts:\n" +
//		 "-$50.00\n" + 
//		 
//		 "Total*:\n" + 
//		 "$4009.00";
////	   
//		//String pat = "ProSeries Document Management System.*\\$(\\d+(\\.\\d{2})?).*No Thanks";
//				
//				//"Fujitsu Fi-6130 Scanner.*?\\$(\\d+(\\.\\d{2})?)\\s+Add to Cart Learn More";
//		
//		String pat =  "ProSeries Basic 1040 Federal" + ".*" + "Regular:" +
//		          ".*?\\$([\\d,]+(\\.\\d{2})?)";
//		
//				//".*?\\s+\\$((\\d+(\\,)?\\d+(\\.\\d{2})?))";		
//		Pattern pattern = Pattern.compile(pat, Pattern.MULTILINE | Pattern.DOTALL);
//		Matcher matcher = pattern.matcher(namenew);
//		boolean matchFound = matcher.find();
//		//System.out.println(matcher.group(1));
//		String Address = matcher.group(1);
//		System.out.print("Address_new  " + Address);
//}

}


//String [] addressArray = addressStr.split("\n");
//outputmap.put("APDCompanyAddressLine1", addressArray[0]);
//outputmap.put("APDCompanyAddressCity", addressArray[1].split(",")[0]);
//outputmap.put("APDCompanyAddressState", addressArray[1].split(",")[1]);
//outputmap.put("APDCompanyAddressZipCode", addressArray[2].substring(0, 5));
////outputmap.put("APDCompanyAddressCountry", "US");