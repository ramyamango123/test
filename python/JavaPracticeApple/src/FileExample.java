import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class FileExample {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Path filePath = new File("/Users/ramya/Google Drive/eclipse/workspace/python/JavaPracticeApple/src/File_data.txt").toPath();
		Charset charset = Charset.defaultCharset();        
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(filePath, charset);
		    System.out.println("stringList" + stringList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String i : stringList) {
		    System.out.println("i-------" + i);
		}

		String[] stringArray = stringList.toArray(new String[]{});
		System.out.println(stringArray);
		
		// Path filePath2 = new File("/Users/ramya/Google Drive/eclipse/workspace/python/JavaPracticeApple/src/HashMapExamplesCopy.java").toPath();
		// Files.write(filePath2, stringArray.);
	}

}	
