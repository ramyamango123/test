
public class StringFunctions {


	public static void main(String[] args) {
		
		// creating strings //
		/*char [] x1 = new char[2];
		x1[0] = 'w';
		x1[1] = 'e';
		System.out.println(x1);*/
		
		
		// creating strings //
		char [] x2 = {'h','e','l','l', 'o'};
		System.out.println(x2);
		String hello = new String(x2);
		System.out.println(hello);
		
		
		// String length
		String  name = "palindrome";
		int len = name.length();
		System.out.println(len);
		
		
		// Concat string1.concat(string2);
       String concatenation =  "My name is ".concat("Zara");
       System.out.println(concatenation );
       
       //or
       System.out.println("hello," + " world" + "!");
       
       // ends with method
       String s1 = new String("I am learning java");
       boolean eval;
       eval = s1.endsWith("learn");
       System.out.println(eval);
       
       // indexOf method
       
       String str = new String("Welcome to tutorial point");
       System.out.println(str.indexOf('l', 3));
       
       // str.matches(regex)
       String Str = new String("Welcome to Tutorialspoint.com");

       System.out.print("Return Value :" );
       System.out.println(Str.matches("(.*)Tutorials(.*)"));
       
       // Replace replace(char oldChar, char newChar)
       String str1 = new String("Welcome to# Tutorialspoint.com");
       System.out.println(str1.replace('T', 't'));
       
       // ReplaceAll(String regex, String replacement)
       String str2 = new String("Welcome to# Tutorialspoint.com");
       System.out.println(str2.replaceAll("Welcome.*#", "Hello"));

       
       // Split
       String Str3 = new String("Welcome-to-Tutorialspoint.com");
       for (String x:Str3.split("-")){
    	   System.out.println("x" + x);
       }
       // along with limit
       for (String x:Str3.split("-", 2)){
    	   System.out.println("x1" + x);
       }
       
       // substring()
       String Str4 = new String("Welcome to   Tutorialspoint.com    ");

       System.out.print("Return Value :" );
       System.out.println(Str4.substring(10) );

       System.out.print("Return Value :" );
       System.out.println(Str4.substring(10, 15) );
       
       // lowercase
       System.out.println(Str4.toLowerCase());
       System.out.println(Str4.toUpperCase());
       
       // toString()
       System.out.println(Str4.toString());
        String Str5 = "ramya    yyy";
       // trim() - It returns a copy of this string with leading and trailing white space removed
       System.out.println(Str5.trim() );
       
       
       // valueOf
       
       double d = 102939939.939;
       boolean b = true;
       long l = 1232874;
       char[] arr = {'a', 'b', 'c', 'd', 'e', 'f','g' };

       System.out.println("Return Value : " + String.valueOf(d) );
       System.out.println("Return Value : " + String.valueOf(b) );
       System.out.println("Return Value : " + String.valueOf(l) );
       System.out.println("Return Value : " + String.valueOf(arr) );
       
       // String to Int
       
      String s = "1234";
      int foo = Integer.parseInt(s);
      System.out.println(foo);
      //or 
      int value = Integer.valueOf(foo);
      System.out.println(value);
      
      // String to float
      String data = "45";
      Float res = Float.valueOf(data).floatValue();
      System.out.println(res);
      
      // String to double
      String text = "12.34"; // example String
      double value1 = Double.parseDouble(text);
      System.out.println(value1);
      
      
      // int to string
      int i = 10;
      Integer.toString(i);
      System.out.println(i);
      
      // float to String
      double x = 34.0;
      Double.toString(x);
      System.out.println(x);
      
      
      
      
    }
       
    
       
       

	}


