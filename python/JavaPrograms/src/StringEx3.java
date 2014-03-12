import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringEx3 {
    
   public static void main(String[] args) {
//        double d = 858.48;
//        String s = Double.toString(d);
//        System.out.println(s);
//        int dot = s.indexOf('.');
//        System.out.println(s.length());
//        
//        System.out.println(dot + " digits " +
//            "before decimal point.");
//        System.out.println( (s.length() - dot - 1) +
//            " digits after decimal point.");
    
	   	 //  String s = "400.00";
	   	 //  System.out.println(s.matches("\\$(\\d+(\\.\\d\\d)?)"));
//	   	  
//	   	String inputStr = "$400.00";
//	   	String patternStr = "\\$(\\d+(\\.\\d\\d)?$)";
//
//	   	// Compile and use regular expression
//	   	Pattern pattern = Pattern.compile(patternStr);
//	   	Matcher matcher = pattern.matcher(inputStr);
//	   	boolean matchFound = matcher.find();
//
//	   	if (matchFound) {
//	   	    // Get all groups for this match
//	   	    // for (int i=0; i<=matcher.groupCount(); i++) {
//	   	    String groupStr = matcher.group(1);
//	   	    System.out.println(groupStr);
//	   	    // }
//	   	} else {
//	   		System.out.println("No match");
//	   	}
//	   	
//		String inputStr = "$400.00 Price";
//	   	String patternStr = "\\$(\\d+(\\.\\d{2})?)\\s+(\\w+)";
//
//	   	// Compile and use regular expression
//	   	Pattern pattern = Pattern.compile(patternStr);
//	   	Matcher matcher = pattern.matcher(inputStr);
//	   	boolean matchFound = matcher.find();
//
//	   	if (matchFound) {
//	   	    // Get all groups for this match
//	   	    // for (int i=0; i<=matcher.groupCount(); i++) {
//	   	    String groupStr = matcher.group(1)+ "\n" + matcher.group(3);
//	   	    System.out.println(groupStr);
//	   	    // }
//	   	} else {
//	   		System.out.println("No match");
//	   	}  
//	   	  
//   
//	   String p = "$400.00.56";
//	   float result = Float.parseFloat(p);
//	   System.out.println(result);
//	   String[] l = p.split("\\$");
//	   System.out.println(l[1]);
	   //String s = "a:b:cd:ef";
	  // String[] l = s.split(":+");
//	   for (int i=0; i < l.length; i++)
//	   {
//	       System.out.println(l[i]);
//	   }
	   String input = "proseries item Regular expressions price Regular:   $499.00";
	   String patternStr = "Regular:\\s+\\$(\\d+(\\.\\d{2})?)";
		
//		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(input);
		boolean matchFound = matcher.find();

		if (matchFound) {
			String groupStr = matcher.group(1);
			System.out.println(groupStr);
   
      }	   
}
}