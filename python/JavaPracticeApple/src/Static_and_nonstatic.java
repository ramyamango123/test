
public class Static_and_nonstatic {
	
	/*global variables - can be used across all functions with 
	some conditions. There are declared inside the class but 
    outside functions*/
	
  String name; // non static global variable
  static int age; // static global variable
// static funs can only access static global variables and only call static funcs
  
  
	public static void main(String[] args) { 
	/* main is static function bcz it has 
	static keyword associated with it.
	Ex: we can call sum() - it is static not sendMail() - non static
	age 0k not name
		
                                          */
	}
	
	public static void sum() { // static function
		int i = 100; // local variable - It can only be used inside 
				    // this function. These are neither static nor non-static
		            // u cannot put static keyword in front of it
		
	}
	
	public void sendMail(){ // no static (no static keyword)
		int y =300; 
		
		/*local variables can be in both static and 
		   non-static fucntion. In non-static func no rule. u can use static/no static 
		   global variable*/
	}

}
