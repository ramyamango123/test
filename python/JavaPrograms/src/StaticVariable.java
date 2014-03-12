public class StaticVariable {
	static int noOfInstances = 3;
	// This is a class variable(static keyword in front).
	// Here all objects will have the same value. So if object1 his set 2, obj2
	// will also become 2
	int numbers = 6;

	// This is inheritance variable. Here each object will have its own value.
	// Here if object1 is set to 2, obj2 won't become 2

	void setVariable(int tree) {
		noOfInstances = tree;
		numbers = tree;
	}

	public static void main(String[] args) {
		StaticVariable sv1 = new StaticVariable();
		StaticVariable sv2 = new StaticVariable();
		sv1.setVariable(1);
		System.out.println("No. of instances for sv1 : " + sv1.noOfInstances);
		System.out.println("No. of instances for sv2 : " + sv2.noOfInstances);
		System.out.println("No. of numbers for sv1 : " + sv1.numbers);
		System.out.println("No. of numbers for sv2 : " + sv2.numbers);
	}
}