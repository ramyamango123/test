class CharPrg{
	String greeting;
	String language;
	
	public CharPrg(String value){
		greeting = value;
		
	}
	
	public void setLanguage (String lang){
		language = lang;
		
	}
	
	public String foo_test ()
	{
		String l = "foo";
		
		return l;
	}
	
	public String getlanguage (){
		System.out.println("The greeting language is " +language );
		String lang = foo_test();
		return lang; // language;
	}
	/*public void printvalues(){
	      System.out.println("Greeting message is  : " + greeting );
	      System.out.println("language type is :" + language);
	   }*/
	 public static void main (String []args) {
		 String s;
		 CharPrg obj1 = new CharPrg("Hello");
		 obj1.setLanguage("English");
		 System.out.println(obj1.getlanguage());
		// obj1.printvalues();
		 
		 String itemName = "Lacerte Federal";
		 if (itemName.equals("Lacerte Federal"))
		 {
			 System.out.println("matched");
		 }
		 
	 }
}