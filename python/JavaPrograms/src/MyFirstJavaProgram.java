import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFirstJavaProgram {

	/*
	 * This is my first java program. This will print 'Hello World' as the
	 * output
	 */

	public static void main(String[] args) {
		// System.out.println("Hello World"); // prints Hello World
//		String p = "400.00";
//		float result = (Float.valueOf(p)).floatValue();
//		System.out.println(result);

//		String input = "" + " Billing: \n"
//				+ " 56 garcia ave, Mountain view, CA, 94040 \n" + "\n"
//				+ " Shipping: \n"
//				+ " 56 garcia ave, Mountain view, CA, 94040 \n" + "\n"
//				+ " Regular: \n" + " $499.00 \n" + "\n" + " Your Price: \n"
//				+ " $499.00 \n" + "\n";

		// input = "\n" +
		// "$400.00\n" +
		// "foo\ndfds jfds fdsfds";
//
//		String patternStr = "Billing"
//				+ ".*?Regular: \n (\\$499\\.00) \n\n Your Price: \n (\\$499\\.00)";
////	 
		//String r = "ProSeries PowerTax Library      gjhhjhjkhjhjh7887786              $4,649.00" + "\n"
//                    + "ProSeries Professional Product Delivery                         $300,567.34\n"
		//		    + "Subtotal: \n" + " $4,649.45 \n" + "\n" + "Total: \n"
		//		    + "	$4,649.99\n" ;
         String r = "APD Test lsc_unl_not_renewed\n\n\n" +
 "Company Address:\n" +
 "78 garcia Ave \n" +
 "Mountain View, CA \n" +
 "94043-1106" +
 "Company Contact:\n" +
 "Unlimit";
//                    
//                   
          //String item =   "ProSeries PowerTax Library"; 
//        String total = "Subtotal:";
		//String patternStr = "ProSeries PowerTax Library.*?(\\$(\\d+(\\,)?\\d+(\\.\\d{2})?))";
		//String patternStr = item + ".*?\\s+\\$((\\d+(\\,)?\\d+(\\.\\d{2})?))";
		//String patternStr = total  + ".*?\\s+\\$([\\d+\\,]+?\\d+(\\.\\d{2})?)";
		// patternStr = "\n\\$(\\d+)\\.\\d+\nfoo";

      // String patternStr = "Subtotal:\\s+\\$([\\d+\\,]+?\\d+(\\.\\d{2})?)";
		// // Compile and use regular expression
     String patternStr = "Company Address:(.*?)Company Contact";
        //String patternStr = "Address(.*)Company";

		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(r);
		boolean matchFound = matcher.find();
		//System.out.println(matcher.group(1));
		String Address = matcher.group(1);
		Address = Address.replaceAll("\n","");
		Address = Address.replaceAll(",","");
	    System.out.println(Address);

		//System.out.print("address:" + Address);
	}
	//ProSeries PowerTax Library                    $4,649.00
	
	String item =  "ProSeries Pay Per Return License\n" +
			        "$275.00\n" + 
			   	    "ProSeries Professional Product Delivery Tax Year:Tax Year 2011\n" +
			        "Release:Release 1\n" +
			 
			 "Total:\n" +
			 "$275.00";
	String patternStr = "ProSeries Pay Per Return License\\s+\\$(\\d+(\\.\\d{2})?)";
			 

	 //String patternStr = "Regular:\\s+\\$(\\d+(\\.\\d{2})?)";
	
	 // Compile and use regular expression
	 Pattern pattern = Pattern.compile(patternStr);
	 Matcher matcher = pattern.matcher(item);
	 boolean matchFound = matcher.find();
	
//	 if (matchFound)
//	 {
	 String groupStr = matcher.group(1);
	 system.out.println(groupStr);
//	 float itemValue = (Float.valueOf(groupStr)).floatValue();
//	 System.out.println("Regular price" + itemValue);
//	
//	 if (itemValue > 0)
//	 {
//	 TestRun.updateStatus(TestResultStatus.PASS,"Non-zero item price is displayed on the account management page.");
//	 }
//	 else
//	 {
//	 TestRun.updateStatus(TestResultStatus.FAIL,"Item price displayed is <= zero on the account manapage page.");
//	 }
//	 }
//
//	 else
//	 {
//	 System.out.println("No match");
//	 }
//}
}
