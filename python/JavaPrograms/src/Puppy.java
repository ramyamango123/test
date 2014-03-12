class Puppy{
	
	int puppyAge1;
	int puppyAge2;
	
	public Puppy(String name){
		System.out.println("Passed name is " + name);
	}
	public void setAge1 (int age){
		puppyAge1 = age;
		
	}
	public void setAge2 (int age){
		puppyAge2 = age;
	}
	public int getAge1(){
		System.out.println("Puppy's age is " + puppyAge1);
		return puppyAge1;
	}
	public int getAge2(){
	    System.out.println("Puppy's age is " + puppyAge2);
	    return puppyAge2;
				
	}

    public static void main(String []args){
    	Puppy myPuppy1 = new Puppy("tommy");
    	Puppy myPuppy2 = new Puppy("pinky");
    	myPuppy2.setAge2(4);
    	myPuppy2.getAge2();
    	System.out.println("variable value:" + myPuppy1.puppyAge1);
        	
    }
       
}