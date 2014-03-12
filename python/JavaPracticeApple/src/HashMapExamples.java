import java.util.HashMap;


public class HashMapExamples {

	public static void main(String[] args) {
		
		HashMap<String, Object> result = hash_map_return();
		System.out.println(result);
		
		// To get the value of specified key
		System.out.println(result.get("Ramu"));
		
		// To check if key exists in hashmap
		if (result.containsKey("Ramu")){
			System.out.println("Ramu exists");
			
		}
		
		// To get all keys
		System.out.println(result.keySet());
		
		// To get all values
		System.out.println(result.values());
			
			

	}
	
	public static HashMap<String, Object> hash_map_return()
	{
		HashMap<String, Object> outputMap = new HashMap<String, Object>();
		
		// To put the values to key in hashmap
		outputMap.put("Ramya", "510-456-9876");
		outputMap.put("Ramu", "310-156-9876");
		return outputMap;
		
	}

}
