import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class Filesex {
	
	private static void submitOrder ()
	{
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		//System.out.println("Starting Selenium \n\t" + Arrays.toString(Thread.currentThread().getStackTrace()));
		System.out.println(stack[2].getMethodName());
		//System.out.println("Starting selenium " + 
		                 //  stack[2].getFileName() + ":" + stack[2].getMethodName() + "():" + stack[2].getLineNumber());
		
	}
	
	private static void test1 ()
	{
		submitOrder();
		
	}
	
	private static void test2 ()
	{
		submitOrder();
	}
    public static void main(String[] args) throws IOException{
        String bodyText = readEntireFile("c:\\test.txt");
       // String pat =  "ProSeries Basic 1040 Federal" + ".*" + "" +
		         // ".*?\\$([\\d,]+(\\.\\d{2})?)";
        
//        String pat = "" + "Sales Tax:"  + ".*?\\$([\\d,]+(\\.\\d{2})?)";
//        		//"" + "Total:"  + ".*?\\$([\\d,]+(\\.\\d{2})?)";
//        		//"ProSeries Federal" + "" + "" + ".*?\\$([\\d,]+(\\.\\d{2})?)";
        
        test1();
        test2();
        
        String pat = "Customer Account #:\\s+(\\d+)\\s+";
        
        
     
        Pattern pattern = Pattern.compile(pat, Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(bodyText);
		boolean matchFound = matcher.find();
		//System.out.println(matcher.group(1));
		String Address = matcher.group(1);
		System.out.print("Address_new  " + Address);
        //System.out.println(bodyText);
        //writeEntireString(bodyText, "c:\\test_out.txt");

    }
 
    private static String readEntireFile(String filename) throws IOException {
        FileReader in = new FileReader(filename);
        StringBuilder contents = new StringBuilder();
        char[] buffer = new char[4096];
        int read = 0;
        do {
            contents.append(buffer, 0, read);
            read = in.read(buffer);
        } while (read >= 0);
        
        in.close();
        return contents.toString();
    }

    private static void writeEntireString(String bodyText, String filename) throws IOException {
        FileWriter out = new FileWriter("c:\\test_output.txt"); out.write(bodyText); out.close();
    }

}

// String pat =  "ProSeries Basic 1040 Federal" + ".*" + " Total:" +
// ".*?\\$([\\d,]+(\\.\\d{2})?)";
//  String pat =  "ProSeries Basic 1040 Federal" + ".*" + " Total:" +
// ".*?\\$([\\d,]+(\\.\\d{2})?)";