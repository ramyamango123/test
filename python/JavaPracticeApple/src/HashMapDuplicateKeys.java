import java.util.HashMap;


public class HashMapDuplicateKeys {
	
	public static void main(String[] args) {

		String [] names = {"Ramya", "Ramu", "priya", "Ramya", "sindu", "priya", "Ramya"};

		HashMap<String, Integer> outputMap = new HashMap<String, Integer>();

		for(int i = 0; i< names.length; i++)
		{
			System.out.println(names[i]);
			
			// To check if key exists in hash-map
			if (outputMap.containsKey(names[i])) {
				 Integer value = outputMap.get(names[i]);
				 System.out.println("value: " + value);
				 outputMap.put(names[i], value + 1);
			} else {
				outputMap.put(names[i], 1);
			}
		}

		System.out.println(outputMap);
	}

}



