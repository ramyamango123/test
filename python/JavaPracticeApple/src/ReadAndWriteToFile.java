import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class ReadAndWriteToFile {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// Write to a file /////////
		String data = "this is first line. This is second line";
		PrintWriter writer = new PrintWriter("/Users/ramya/Google Drive/eclipse/workspace/python/JavaPracticeApple/src/File1_data.txt", "UTF-8");
		
		writer.println( data);
		
		writer.println("The third line");
		writer.close();
		
		// Read the file //////
		Path filePath = new File("/Users/ramya/Google Drive/eclipse/workspace/python/JavaPracticeApple/src/File1_data.txt").toPath();
		Charset charset = Charset.defaultCharset();        
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(filePath, charset);
		    System.out.println("stringList ---" + stringList.getClass());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < stringList.size(); i++) {
			System.out.println("i------" + stringList.get(i));
		}
		for (String item : stringList) { // for item in stringList:
		    System.out.println("i-------" + item);
		    System.out.println(item.length());
		}
		
		for (int i=0; i< stringList.size(); i++)
		{
			System.out.println("second for loop-------" + stringList.get(i));
		}


	}

}
