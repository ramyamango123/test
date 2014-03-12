public class ForEachEx {
	public static void main(String[] args) {
		String[] x = { "Ramya", "Ramu", "Coffee" };
		/* for (int i= 0; i < x.length; i++){ */
		for (String names : x) {
			System.out.println(names);
			// System.out.println(x[i]);
		}
	}
}