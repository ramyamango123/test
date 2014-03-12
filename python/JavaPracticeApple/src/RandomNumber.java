// Return a random number <n
public class RandomNumber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int random_num = randomNumberGenerate(45);
		System.out.println("The random number " + random_num + " is greater than 45: ");
		
	}

	
	public static int randomNumberGenerate(int n)
	{
		
		//System.out.println("r1" + r1);
		
		while(true){
			double r1 = Math.random()*100;
			int r2 = (int) r1;
			System.out.println("r2 :" + r2);
			if (r2>n)
			{
				return r2;
			}
			
			
		}
		
		
	}
}
