import java.util.ArrayList;


public class ArrayExamples {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
	
		//ArrayList<String> singleAddress ;
		
		//-------- First way of creating an array ---------//
		/*int[] ar1 = new int[3];
		ar1[0] = 32;
		ar1[1] = 33;
		ar1[2] = 34;
		//System.out.println(ar1[2]);
		for(int i=0; i<ar1.length; i++){
			System.out.println(ar1[i]);*/
		
		
		
		// ----- Second way of creating an array ----------//
		/*String [] array2 = {"ramya", "priya", "ramu"};
		for(int i=0; i<array2.length; i++){
			System.out.println(array2[i]);*/
		
		
			
		//------  Array List of String --------//
		ArrayList<String> singleAddress = new ArrayList<String>();
		singleAddress.add("17 street");
		singleAddress.add("Milpitas");
		//singleAddress.clear();
		singleAddress.indexOf("Milpitas");
		System.out.println(singleAddress.indexOf("Milpitas"));
		
		
		
		
		// --------  Array List of Integer ------//
		int [] numlist = {3, 5, 8, 9};
		ArrayList<Integer> intList = new ArrayList<Integer>();
		//intList.add(numlist[1]);
		System.out.println("intList" + intList);		
		
		for( int i = 0; i <numlist.length; i++)
		{
			intList.add(numlist[i]);
			
		}
		// How to know what kind of data type ????
		System.out.println("intList :" +  intList);
		System.out.println("intList :" +  intList.getClass()); //  In python, print intList.__class__
		System.out.println("intList.get(0) : " + intList.get(0));
		
		
		
		
		// ------ Array of objects -------//
		Object [] obj1 = new Object[4];
		// It can contain integer, string, chars
		obj1[0] = "ramya";
		obj1[1] = 45;
		obj1[2] = 'r';
		obj1[3] = new ArrayList<Integer>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(100);
		temp.add(101);
		System.out.println(temp);
		((ArrayList<Integer>) obj1[3]).addAll(temp);
		((ArrayList<Integer>) obj1[3]).add(200);

		
		for(int i=0; i<obj1.length; i++){
			System.out.println(obj1[i]);
		
		
		}
	
		
		}
			

	}


