import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
class Array2{
	public static void main(String[] args){
//		int[] array1 = {3, 8, 17, 11};
//		for (int i=0; i<array1.length; i++) {
//			System.out.println("The numbers from 1 to 10 are:" + array1[i]);	
//		}
//
//		//Summing all elements
//		int sum = 0;
//		for (int i=0; i<array1.length; i++){
//			sum += array1[i];
//		}
//		System.out.println("Total is " + sum);
//
//		// Finding max element in the list
//		int max = array1[0];
//		for (int i=1; i<array1.length; i++){
//			if (array1[i] > max) max = array1[i];
//
//		}
//		System.out.println("Max is " + max);
//		//int num = 300 ;
		
//		String [] Str = new String("Welcome-to-Tutorialspoint.com");
//		String retval = Str.split("-", 2);
//		System.out.println(retval);

		
//		String[] x;
//		x = newnum.split(".");
//		System.out.println(x);
//		for (int i = 0; i < x.length; i++) {
//		      System.out.println(x[i]);
		
//		int itemValue = Integer.valueOf(newnum);
//		System.out.println(itemValue);
//		
//		
//		if (itemValue > num)
//		{
//			System.out.println("The answer is right");
//		}
//		String statement = "I will not compromise. I will not "
//		        + "cooperate. There will be no concession, no conciliation, no "
//		        + "finding the middle group, and no give and take.";
//
//		    String tokens[] = null;
//
//		    String splitPattern = "compromise|cooperate|concession|"
//		        + "conciliation|(finding the middle group)|(give and take)";
//
//		    tokens = statement.split(splitPattern);
//
//		    System.out.println("REGEX PATTERN:\n" + splitPattern + "\n");
//
//		    System.out.println("STATEMENT:\n" + statement + "\n");
//		    System.out.println("\nTOKENS");
//		    for (int i = 0; i < tokens.length; i++) {
//		      System.out.println(tokens[i]);
//		
//		String str = "st1-st2-st3";
//		String delimiter = "-";
//		String[] temp;
//		temp = str.split(delimiter);
//		System.out.println(temp);
//		for(int i =0; i < temp.length ; i++)
//		System.out.println(temp[i]);
//		
//		String str = "4,679.78";
//		Float price = java.text.DecimalFormat.parse(str).floatValue();
//		System.out.println(price);	
//		str.replace(',', "");
//		
//		String delimiter = ",";
//		String[] temp;
//		String concat = "";
//		temp = str.split(delimiter);
//		
//		for(int i =0; i < temp.length ; i++)
//		{
//		    
//		    concat = concat + temp[i];
//        }
//
//
//        String str = "4,679";
//        String delimiter = ",";
//        String[] temp;
//        String concat = "";
//        temp = str.split(delimiter);
//       
//        for(int i =0; i < temp.length ; i++)
//        {
//           
//            concat = concat + temp[i];
//        }
//        System.out.println(concat); 
        
        
		 String input = "567.00";
	     input = input.replaceAll(",","");
	     System.out.println(input);
		
		
	}

	}


