
public class TestCar {
    int x;
    int y;
    //Construtor - same name as class name (no static) 
    // As soon as we create construtor of the class, 
    // construtor is called
    // It will help us intialize the object.
    // no return type
    public TestCar(int x){
    	x=x;
    	// local variable is linking to local variable. 
    	//The actual global variable is hiding
    	//so if we use the same name do this.x = x. Now
    			//this.x points to the global variable x
    	
    	// same function name bcz diff arguments. 
    	// They are called overloading.
    	// swap(int a, int b), & swap (int t)
    	
    	
    }
	
	public static void main(String[] args) {
		
		swap(3,4);
		test(3,7);
		

	}

	public static int swap(int a, int b) {
		
		int temp = a;
		 a = b;
		 b = temp;
		 return a;
		 
		//System.out.println(a);
		//System.out.println(b);
	}
	
    public static void test(int a, int b) {
    	
    	int result = TestCar.swap(3, 7);
    	System.out.println(result);
		//System.out.println(p.x);
		
    }
}
