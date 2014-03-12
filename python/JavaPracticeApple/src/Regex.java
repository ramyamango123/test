import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Regex {
    static String input = "09/11/2012";

	public static void main(String[] args) {
		
		String result = regexmatch(input);
		System.out.println("result :" + result);
		

	}
	
	
	public static String regexmatch(String input){
		
		//String input = "09/11/2012";

		String patternStr = "(\\d{1,2})\\/(\\d{1,2})\\/(\\d{4})";

		// create a pattern object
		Pattern pattern = Pattern.compile(patternStr, Pattern.MULTILINE
				| Pattern.DOTALL);
		System.out.println(pattern);

		// Now create a match object
		Matcher matcher = pattern.matcher(input);
		boolean matchFound = matcher.find();

		if (matchFound) {
			String groupStr = matcher.group(1);
		    int month = Integer.valueOf(groupStr);
		  //  System.out.println("month :" + month);
			
			String[] month_names = {"Jan", "Feb", "March", "April", "May", "june", "july","Aug", 
					                 "sep", "Oct", "Nov", "Dec"};
			
			for(int i=0; i< (month_names.length); i++){
				if (month == i)
				{
					//System.out.println(month_names[i-1]);
					String result = month_names[i-1];
					return result;
					
				}

				}
					
			}

		 else {
			System.out.println("No match");
			

		}
		return "No match";
		
	}
	
}


		
		
		



