import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class File_write {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		
		Path filePath = new File("/Users/ramya/Google Drive/eclipse/workspace/python/JavaPracticeApple/src/File_data.txt").toPath();
		Charset charset = Charset.defaultCharset();        
		List<String> stringList = null;
		try {
			stringList = Files.readAllLines(filePath, charset);
		   // System.out.println("stringList" + stringList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String i : stringList) {
		    System.out.println("i-------" + i);
		}
		
		// Write to a file ///
		PrintWriter writer = new PrintWriter("/Users/ramya/Google Drive/eclipse/workspace/python/JavaPracticeApple/src/File1_data.txt", "UTF-8");
		
		writer.println("new i///////:" + stringList);
		
		writer.println("The second line");
		writer.close();

	}

}
