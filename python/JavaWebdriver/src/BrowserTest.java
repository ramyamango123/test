import org.openqa.selenium.firefox.FirefoxDriver;


public class BrowserTest {


	public static void main(String[] args) {
		FirefoxDriver dr1 = new FirefoxDriver();
		dr1.get("http://google.com");
		dr1.getCurrentUrl();
		

	}

}
