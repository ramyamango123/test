import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateRegex {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String input = "09/11/2012";

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
		    System.out.println("month :" + month);
			
			String[] month_names = {"Jan", "Feb", "March", "April", "May", "june", "july","Aug", 
					                 "sep", "Oct", "Nov", "Dec"};
			
			for(int i=0; i< (month_names.length); i++){
				if (month == i)
				{
					System.out.println(month_names[i-1]);
				}

				}
				
			
			}

		 else {
			System.out.println("No match");

		}

	}
}


/*

days_1_31 = "([1-9]|[1-2][0-9]|30|31)"
days_1_30 = "([1-9]|[1-2][0-9]|30)"
days_1_28 = "([1-9]|[1-2][0-8]|19)"

month_31_days = "(1|3|5|7|8|10|12)"
month_30_days = "(4|6|9|11)"
month_28_days = "(2)"

year = "20[0-9][0-9]"

day_month = "(" + days_1_31 + "\/" + month_31_days + ")" + "|" + \
            "(" + days_1_30 + "\/" + month_30_days + ")" + "|" + \
            "(" + days_1_28 + "\/" + month_28_days + ")"
pattern = "(" + day_month + ")" + "\/" + year*/