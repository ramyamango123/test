
/*
 * @Project: Selenium Test Suite for icloud.com
 * @Developers: Aravind Palakkurishi, Sreejith Sreekantan, Uttam Chauhan
 * @Reviewed By: Arjun Sadanandan, Jojo John
 * @Re-written and extended By: Sadia Rauf, Deepak Srikanth, Pedro Beltran
 */
package com.webtest.icloud.iwork;
import static org.junit.Assert.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.cmp.GenRepContent;
import org.ini4j.InvalidFileFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;


import com.thoughtworks.selenium.Selenium;

public class iCloudWebAutomationUsingDriver
{
	private static final String SCREENSHOT_FOLDER = "Screenshots/";	
	private static final String SCREENSHOT_FORMAT = ".png";
	private WebDriver driver = null;
	private PropertyReader  propertyReader = null;
	private ChromeDriverService service = null;
	private static Logger testResultsLogger = Logger.getLogger("seleniumTestResultsLogger");
	private static Logger debugLogger = Logger.getLogger("seleniumDebugStatementsLogger");
	private Selenium selenium = null;
	private Dimension screenDimension = null;
	private String operatingSystem,browserType,downloadDocumentInPdfFormat = null;
	private BrowserCoordinates browserCoordinates = null;
	private GeneralUtility generalUtility = null;
	private String parentWindow = null;
	private TakesScreenshot d = null;
	private String mainParentHandle = null;
	private Robot tempRobot = null;  

	
	@Before
	public void setUp()
	{
		propertyReader = PropertyReader.getInstance();
		//propertyReader = new PropertyReader();
		generalUtility = new GeneralUtility();
		//iCloudConstants = new iCloudConstants();
		
		try
		{
			testResultsLogger.info("\n**********"+" Automation Test Suite started on: " 
					+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date())+"***********");
			operatingSystem = System.getProperty("os.name");
			browserType = System.getProperty("browser.type");
			//loading generic selenium properties
			propertyReader.loadSeleniumPropertiesFile();
			//loading xpath properties based on the browser type
			propertyReader.loadXpathProperties(browserType);
			browserCoordinates = new BrowserCoordinates();
			if ((com.webtest.icloud.iwork.iCloudConstants.googleChromeBrowser).equalsIgnoreCase(browserType))
			{
				String chromedriverPath = operatingSystem.equalsIgnoreCase(com.webtest.icloud.iwork.iCloudConstants.MacOsX) ? 
											System.getProperty("chromedriver.path.mac") : System.getProperty("chromedriver.path.windows");
				
				browserType = com.webtest.icloud.iwork.iCloudConstants.googleChromeBrowser;
				screenDimension = generalUtility.getScreenResolution();
				String screenResolution = screenDimension.getWidth()+"*"+screenDimension.getHeight();
				//debugLogger.debug("The screen resolution is "+screenResolution);
				browserCoordinates.parseXmlToSelectCoordinates(screenResolution,System.getProperty("parse.browser.coordinates.file"),operatingSystem);
				//exiting if the parsing was unsuccessful
				//debugLogger.debug("The coordinatesExistsForResolution for chrome browser is "+BrowserCoordinates.coordinatesExistsForResolution);
				if (!(BrowserCoordinates.coordinatesExistsForResolution))
				{
					testResultsLogger.info("The Test suite for chrome is not applicable for this resolution.Hence quitting");
					System.exit(0);
				}
				//service = new ChromeDriverService.Builder().usingChromeDriverExecutable(new File(chromedriverPath))
		    //    .usingAnyFreePort().build();
				service.start();
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			    driver = new RemoteWebDriver(service.getUrl(),capabilities);
			}
			
			if ((iCloudConstants.safari).equalsIgnoreCase(browserType))
			{
				//String chromedriverPath = operatingSystem.equalsIgnoreCase(iCloudConstants.MacOsX) ? 
					//						System.getProperty("chromedriver.path.mac") : System.getProperty("chromedriver.path.windows");
				//String chromedriverPath = "/Users/test/Desktop/e_test/iWorks/drivers/mac/chromedriver";
				//Users/test/Desktop/e_test/iWorks/drivers/mac/chromedriver
				//String chromedriverPath = "/Users/test/Downloads/chromedriver";
				//debugLogger.debug("The chrome driver path chosen is "+chromedriverPath);
				
				browserType = iCloudConstants.safari;
				screenDimension = generalUtility.getScreenResolution();
				String screenResolution = screenDimension.getWidth()+"*"+screenDimension.getHeight();
				//debugLogger.debug("The screen resolution is "+screenResolution);
				browserCoordinates.parseXmlToSelectCoordinates(screenResolution,System.getProperty("parse.browser.coordinates.file"),operatingSystem);
				//exiting if the parsing was unsuccessful
				//debugLogger.debug("The coordinatesExistsForResolution for chrome browser is "+BrowserCoordinates.coordinatesExistsForResolution);
				if (!(BrowserCoordinates.coordinatesExistsForResolution))
				{
					testResultsLogger.info("The Test suite for chrome is not applicable for this resolution.Hence quitting");
					System.exit(0);
				}
				//service = new ChromeDriverService.Builder().usingChromeDriverExecutable(new File(chromedriverPath))
		        //.usingAnyFreePort().build();
				//service.start();
				//DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				  driver = new SafariDriver();  
				  //Thread.sleep(3000);
			}
			
			  selenium = new WebDriverBackedSelenium(driver, propertyReader.getUrl());
			  selenium.windowMaximize();
			  driver.get(propertyReader.getUrl());
			  Thread.sleep(2000);
			  String applicationType = "pages";
			  boolean isLoginSuccessful = login(applicationType);
			  if (!isLoginSuccessful)
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Gilligan login: FAILED");
			  else
			    testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Gilligan login: PASSED");
			  
		}
		catch (Exception e)
		{
			testResultsLogger.error(e.getMessage());
		}
	}

	
	private void testUpload()
	{
		try
		{
			driver.get(propertyReader.getUrl());
			Thread.sleep(2000);

			testResultsLogger.info("**********************************************************************");
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": SCENARIO - Upload");
		    testResultsLogger.info("**********************************************************************");
		    try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		    if (!uploadPages("test_01.pages")){
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Upload: FAILED");
		    }
			else{
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Upload: PASSED");
			}
		    try {Thread.sleep(13000);} catch (InterruptedException e) {e.printStackTrace();}
		    
			testResultsLogger.info("**********************************************************************");
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": SCENARIO - Logout");
		    testResultsLogger.info("**********************************************************************");
		    Thread.sleep(6000);
		    
		} catch (Exception e) {
			testResultsLogger.error("either login failed, or upload failed, or rename or logoout failed, please check further...");
			debugLogger.debug(e.getMessage());
			fail("either login failed, or upload failed, or rename or logoout failed");
		}
	}
	
	public void testScenario04()
	{
		boolean isTestScenario04success = false;
		try {
			choosePersonalPhotoLetterTheme();
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Personal Photo Letter (add shapes) : PASSED");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Personal Photo Letter (add shapes) : PASSED");
			isTestScenario04success = true;
			Thread.sleep(9000);
		} 
	    catch (Exception e) {
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": PERSONAL PHOTO LETTER (ADD SHAPES)  : ########## FAILED ##########");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": PERSONAL PHOTO LETTER (ADD SHAPES)  : ########## FAILED ##########");
			captureScreenshot(isTestScenario04success, "ChoosePersonalPhotoLetterFailed");
		}
	}//end Scenario04()

	public void testScenario05()
	{
		boolean isTestScenario05success = false;
		try {
			chooseProjectProposalTheme();
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Project Proposal (Image Edit) : PASSED");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Project Proposal (Image Edit) : PASSED");
			isTestScenario05success = true;
			Thread.sleep(9000);
		} 
	    catch (Exception e) {
	    	testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": PROJECT PROPOSAL (IMAGE EDIT)  : ########## FAILED ##########");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": PROJECT PROPOSAL (IMAGE EDIT)  : ########## FAILED ##########");
			captureScreenshot(isTestScenario05success, "ChooseProjectProposalFailed");
		}
	}
	
	//@Ignore
	//@Test
	public void testScenario03(){
		boolean isTestScenario03success = false;
		try {
			System.out.println("1 : ");
			try {
				System.out.println("2 : ");
				deleteAllDoc();
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Deleting All Existing Document : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Deleting All Existing Document : PASSED");
				isTestScenario03success = true;
				Thread.sleep(6000);
			}
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DELETING ALL EXISTING DOCUMENTS : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DELETING ALL EXISTING DOCUMENTS : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "DeleteAllExistingFailed");
			}
			
			try {
				createDocGearMenu();
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Creating Doc (Blank Theme Via Gear Icon) : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Creating Doc (Blank Theme Via Gear Icon) : PASSED");
				isTestScenario03success = true;
				Thread.sleep(6000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING DOC (BLANK THEME VIA GEAR ICON) : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING DOC (BLANK THEME VIA GEAR ICON) : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "CreateBlankDocGearFailed");
			}
			
			try {
				duplicateInPagesGearMenu(iCloudConstants.docSelection);
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Duplicate (Via Gear Icon) : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Duplicate (Via Gear Icon) : PASSED");
				isTestScenario03success = true;
				Thread.sleep(6000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DUPLICATE (VIA GEAR ICON) : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DUPLICATE (VIA GEAR ICON) : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "DuplicateViaContextFailed");
			}
			
			try {
				downloadInPagesGearMenu(iCloudConstants.docSelection);
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (In Word Via Gear Icon) : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (In Word Via Gear Icon) : PASSED");
				isTestScenario03success = true;
				Thread.sleep(6000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOAD (IN WORD VIA GEAR ICON) : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOAD (IN WORD VIA GEAR ICON) : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "DownloadViaContextFailed");
			}
			
			try {
				deleteInPagesGearMenu(iCloudConstants.docSelection);
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Delete Document (Via Gear Icon) : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Delete Document (Via Gear Icon) : PASSED");
				isTestScenario03success = true;
				Thread.sleep(9000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DELETE DOCUMENT (VIA GEAR ICON) : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DELETE DOCUMENT (VIA GEAR ICON) : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "DeleteViaContextFailed");
			}
			
			try {
				renameDoc();
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Rename Doc : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Rename Doc : PASSED");
				isTestScenario03success = true;
				Thread.sleep(6000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": RENAME DOC : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": RENAME DOC : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "RenameDocFailed");
			}
			
			try {
				duplicateContextMenu(iCloudConstants.docSelection);
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Duplicate (Via Context Menu) : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Duplicate (Via Context Menu) : PASSED");
				isTestScenario03success = true;
				Thread.sleep(6000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DUPLICATE (VIA CONTEXT MENU) : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DUPLICATE (VIA CONTEXT MENU) : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "DuplicateViaContextFailed");
			}
			
			try {
				downloadInWordContextMenu(iCloudConstants.docSelection);
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (In Word Via Context Menu) : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (In Word Via Context Menu) : PASSED");
				isTestScenario03success = true;
				Thread.sleep(6000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOAD (IN WORD VIA CONTEXT MENU) : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOAD (IN WORD VIA CONTEXT MENU) : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "DownloadViaContextFailed");
			}
			
			try {
				deleteContextMenu(iCloudConstants.docSelection);
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Delete Document (Via Context Menu) : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Delete Document (Via Context Menu) : PASSED");
				isTestScenario03success = true;
				Thread.sleep(9000);
			} 
			catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DELETE DOCUMENT (VIA CONTEXT MENU) : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DELETE DOCUMENT (VIA CONTEXT MENU) : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "DeleteViaContextFailed");
			}
			
		    try {
				editBlankDocFontAndStyle(iCloudConstants.docSelection);
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Blank Document [Edit/Font&Style/Add Text Box/Format Menu/View(Grid/Zoom)/Tools(Find/Find&Replace)/Help)] : PASSED");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Blank Document [Edit/Font&Style/Add Text Box/Format Menu/View(Grid/Zoom)/Tools(Find/Find&Replace)/Help)] : PASSED");
				isTestScenario03success = true;
				Thread.sleep(9000);
			} 
		    catch (Exception e) {
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": BLANK DOCUMENT [EDIT/FONT&STYLE/ADD TEXT BOX/FORMAT MENU/VIEW(GRID/ZOOM)/TOOLS(FIND/FIND&REPLACE)/HELP)] : ########## FAILED ##########");
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": BLANK DOCUMENT [EDIT/FONT&STYLE/ADD TEXT BOX/FORMAT MENU/VIEW(GRID/ZOOM)/TOOLS(FIND/FIND&REPLACE)/HELP)] : ########## FAILED ##########");
				captureScreenshot(isTestScenario03success, "BlankDocEditFailed");
			}
		    		    
		} catch (Exception e) {
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": TEST SCENARIO 03  : ########## FAILED ##########");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": TEST SCENARIO 03  : ########## FAILED ##########");
			captureScreenshot(isTestScenario03success, "TestScenario03Failed");
		}
		
	}
	
	@After
	public void tearDown()
	{
		  boolean isLogoutSuccessful = logout();
		  if (!isLogoutSuccessful)
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Gilligan logout: FAILED");
		  else
		    testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Gilligan logout: PASSED");
		  if (service !=null )
		  {
				service.stop();
		  }
		  //close the browser
		  driver.quit();
	}

	public boolean renameDoc()
	{
		
		Actions aObject = new Actions(driver);
		boolean renameDocSuccessful = false;
		
		try {
			//generalUtility.bringTheFocusToCentreOfScreen();
			//driver.switchTo().frame("pages");
			
			Thread.sleep(3000);
			if(isElementPresent(By.xpath(iCloudConstants.docSelection)))
			{			
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					createDocGearMenu();
					renameDoc();
				}else{
					String selectFirstDoc ="/html/body/div/div[3]/div/div/div/div/div[2]/div";
					String selectFirstDoc_alt = "//div[@class='img-container']";				
					String FirstDocName = "//div[@class='document-name']";
					//find the name of the document
					String docName = driver.findElement(By.xpath(FirstDocName)).getText();
					
					if(isElementPresent(By.xpath(selectFirstDoc_alt)))
					{
						driver.findElement(By.xpath(selectFirstDoc_alt)).click();
					} else {
						//testResultsLogger.info("Unable to select first document");
						debugLogger.info("Unable to select first document");
						captureScreenshot(renameDocSuccessful, "UnableToSelect");
					}
					if(isElementPresent(By.xpath(iCloudConstants.docToRename)))
					{
						Thread.sleep(2000);
						//driver.findElement(By.xpath(iCloudConstants.docToRename)).isSelected();
						driver.findElement(By.xpath(iCloudConstants.docToRename)).click();
						Thread.sleep(2000);
						// enter the new file name
						aObject.sendKeys("Test_00.pages").perform();
						aObject.sendKeys(Keys.RETURN).build().perform();
						Thread.sleep(3000);
					} else {
						debugLogger.info("Dcoument with same name already exists");
						captureScreenshot(renameDocSuccessful, "SameNameExists");
					}
				}
				renameDocSuccessful = true;
			}else{
				debugLogger.info("Unable to find document to rename");
				captureScreenshot(renameDocSuccessful, "SameNameExists");
			}
		} catch (Exception e) {
			debugLogger.info("Rename failed");
			captureScreenshot(renameDocSuccessful, "RenameFailed");
		}
		return renameDocSuccessful;
	}

	public boolean downloadInPDFContextMenu(String docPath)
	{
		boolean downloadSuccessful = false;
		
		try {
			Actions action= new Actions(driver);
			driver.switchTo().frame("pages");		// uncommented this line for error scenario.
			
			if(isElementPresent(By.xpath(docPath)))
			{
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					//createDoc();
					downloadInPDFContextMenu(docPath);
				}else{
					String selectFirstDoc_alt = "//div[@class='img-container']";
					//find the name of the document
					String FirstDocName = "//div[@class='document-name']";			
					String docName = driver.findElement(By.xpath(FirstDocName)).getText();				
					//docCount = driver.findElements(By.xpath(selectFirstDoc)).size();				
					driver.findElement(By.xpath(selectFirstDoc_alt)).click();
					Thread.sleep(3000);
					
					//send keys (ctrl + enter)
					action.contextClick().sendKeys(Keys.LEFT_ALT).sendKeys(Keys.RETURN);				
					action.build().perform();
					Thread.sleep(3000);
					String downloadContextMenu = "/html/body/div[5]/div[2]/div/div/div[1]/a";
					
					if(isElementPresent(By.xpath(downloadContextMenu)))
					{
						driver.findElement(By.xpath(downloadContextMenu)).isEnabled();
						driver.findElement(By.xpath(downloadContextMenu)).isSelected();
						driver.findElement(By.xpath(downloadContextMenu)).click();
							
						Thread.sleep(3000);
					
						String downloadPages = "//div[contains(@class,'type-choice type-choice-1')]";
						
						if(isElementPresent(By.xpath(downloadPages)))
						{
							driver.findElement(By.xpath(downloadPages)).click();
							
							Thread.sleep(6000);
							
						} else {
							downloadSuccessful = false;
							captureScreenshot(downloadSuccessful, "DownloadPDFIconNotClickable");
							action.release();
						}
					} else {
							downloadSuccessful = false;
							captureScreenshot(downloadSuccessful, "DownloadOptionNotSeen");
							action.release();
					}
					downloadSuccessful = true;
				}
			}
		} catch (Exception e) {
			captureScreenshot(downloadSuccessful, "UnableToLocateFrame");
			debugLogger.info("Unable to locate the frame.");
			captureScreenshot(downloadSuccessful, "DownloadPDFViaContextMenuFailed");
			debugLogger.info("Download PDF Via Context Menu failed.");
		}
		//selenium.focus("link=Selenium Overview - Wiki - Liferay.com");
		//action.release();
		return downloadSuccessful;
}
	//deepak*** commented the if & calling createdoc if no doc found.
	public boolean deleteInPagesGearMenu(String docPath)
	{
		boolean deleteSuccessful = false;
		
		try {
			/*if(isElementPresent(By.xpath(docPath)))
			{*/	
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					createDocGearMenu();
					deleteInPagesGearMenu(docPath);
				}else{
					
					String selectFirstDoc ="/html/body/div/div[3]/div/div/div/div/div[2]/div";
					String selectFirstDoc_alt = "//div[@class='img-container']";
					String FirstDocName = "//div[@class='document-name']";
					
					//find the name of the document
					String docName = driver.findElement(By.xpath(FirstDocName)).getText();
					
					//docCount = driver.findElements(By.xpath(selectFirstDoc)).size();				
					driver.findElement(By.xpath(selectFirstDoc_alt)).click();
					Thread.sleep(2000);
					
					driver.findElement(By.xpath(iCloudConstants.actionMenu)).click();
					
					Thread.sleep(3000);
					
					//String downloadOptionGear = "//*[@class='atv3 sc-view sc-menu-item focus']";
					String deleteOptionGear = "/html/body/div[5]/div[3]/div[2]/div/div[6]/a";
					if(isElementPresent(By.xpath(deleteOptionGear)))
					{
						Thread.sleep(3000);
						driver.findElement(By.xpath(deleteOptionGear)).click();
						Thread.sleep(6000);
						String deleteButtonConfirmation = "//label[text()='Delete']";
						if(isElementPresent(By.xpath(deleteButtonConfirmation))){
							driver.findElement(By.xpath(deleteButtonConfirmation)).click();
						}else {
//deepak							deleteSuccessful = false;
							captureScreenshot(deleteSuccessful, "DeleteButtonUnclickable");
							debugLogger.info("Delete Confirmation Button not clickable");	//deepak
							}

/*					String cancelButtonConfirmation = "//label[text()='Cancel']";
						if(isElementPresent(By.xpath(cancelButtonConfirmation))){
							driver.findElement(By.xpath(cancelButtonConfirmation)).click();
						}else {deleteSuccessful = false;}
*/					
						Thread.sleep(3000);
					}
					else{
						captureScreenshot(deleteSuccessful, "DeleteGearOptionNotSeen");
						debugLogger.info("Delete Gear Option Not Seen");	//deepak
					}
				}
				deleteSuccessful = true;
			/*} else {
				deleteSuccessful = false;
				captureScreenshot(deleteSuccessful, "UnableToSelectDocToDelete");
			}*/
			//driver.switchTo().frame(driver.findElement(By.xpath(iCloudConstants.iFrame)));
		} catch (Exception e) {
			captureScreenshot(deleteSuccessful, "DeleteViaGearUnsuccessful");
			debugLogger.info("Delete Via Gear Unsuccessful");
		}
		return deleteSuccessful;
	}

	public boolean deleteContextMenu(String docPath)
	{
		boolean deleteSuccessful = false;
		Actions action= new Actions(driver);
		
		try {
			int docCount = driver.findElements(By.xpath(com.webtest.icloud.iwork.iCloudConstants.docCount)).size();
			if (docCount <= 1){
				//no document found, please create a new document 
				if(createDocGearMenu())
					debugLogger.info("Delete via context menu: Unable to create a new doc");
				else
					debugLogger.info("Delete via context menu: No document found, created a new doc");
			}
			else
			{
				if(isElementPresent(By.xpath(docPath)))
				{
						String selectFirstDoc_alt = "//div[@class='img-container']";
						//find the name of the document
						String FirstDocName = "//div[@class='document-name']";			
						String docName = driver.findElement(By.xpath(FirstDocName)).getText();						
						driver.findElement(By.xpath(selectFirstDoc_alt)).click();
						Thread.sleep(2000);
						
						//send keys (ctrl + enter)
						WebElement firstDoc = driver.findElement(By.xpath(selectFirstDoc_alt));
						action.contextClick(firstDoc).sendKeys(Keys.LEFT_CONTROL).sendKeys(Keys.RETURN).perform();
						Thread.sleep(3000);
						//action.build().perform();
						action.release().sendKeys(Keys.NULL).perform();
						Thread.sleep(3000);
						
						String deleteContextMenu = "/html/body/div[5]/div[2]/div/div/div[3]/a";
						
						if(isElementPresent(By.xpath(deleteContextMenu)))
						{					
							driver.findElement(By.xpath(deleteContextMenu)).click();
								
							Thread.sleep(3000);
							
							String deleteButtonConfirmation = "//label[text()='Delete']";
							if(isElementPresent(By.xpath(deleteButtonConfirmation))){
								driver.findElement(By.xpath(deleteButtonConfirmation)).isSelected();
								driver.findElement(By.xpath(deleteButtonConfirmation)).click();
								Thread.sleep(7000);
								
							}
							else {
								captureScreenshot(deleteSuccessful, "UnabelToConfirmDelete");
								debugLogger.info("Unable to Click on confirm delete");
							}
							
						} 
						else {
							captureScreenshot(deleteSuccessful, "UnableToLocateDeleteOptionViaContext");
							debugLogger.info("Unable to locate delete option via context.");
						}				
				}//doc path
			}//else of doc > 1
			deleteSuccessful = true;
		} 
		catch (Exception e) {
			captureScreenshot(deleteSuccessful, "UnableToDeleteViaContext");
			debugLogger.info("Unable to delete via context.");
		}
		return deleteSuccessful;
	}
//deepak **** commented the if-else condition on entry and added create doc method before download if nothing present.
	public boolean downloadInPagesGearMenu(String docPath)
	{
		boolean downloadSuccessful = false;
		//generalUtility.bringTheFocusToCentreOfScreen();
		//driver.switchTo().frame("pages");
		
		try {
			//driver.switchTo().frame("pages");
			//downloadSuccessful = false;
			
			/*if(isElementPresent(By.xpath(docPath)))
			{*/
				//driver.findElement(By.xpath(docPath)).isDisplayed();
				//driver.findElement(By.xpath(docPath)).isSelected();
				//driver.findElement(By.xpath(docPath)).click();
				
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					createDocGearMenu();
					downloadInPagesGearMenu(docPath);
				}else{
					//div[@class='atv3 sc-view sc-collection-item document pages sel sc-regular-size']/div[@class='atv3 sc-view document-image sc-regular-size']
					
					String selectFirstDoc ="/html/body/div/div[3]/div/div/div/div/div[2]/div";
					String selectFirstDoc_alt = "//div[@class='img-container']";
					
					String FirstDocName = "//div[@class='document-name']";
					
					//find the name of the document
					String docName = driver.findElement(By.xpath(FirstDocName)).getText();
					
					//docCount = driver.findElements(By.xpath(selectFirstDoc)).size();				
					driver.findElement(By.xpath(selectFirstDoc_alt)).click();
					Thread.sleep(2000);
					
					driver.findElement(By.xpath(iCloudConstants.actionMenu)).click();
					
					Thread.sleep(3000);
					
					//String downloadOptionGear = "//*[@class='atv3 sc-view sc-menu-item focus']";
					String downloadOptionGear = "/html/body/div[5]/div[3]/div[2]/div/div[4]/a";
					if(isElementPresent(By.xpath(downloadOptionGear)))
					{
						Thread.sleep(3000);
						driver.findElement(By.xpath(downloadOptionGear)).click();
						Thread.sleep(4000);
						//String downloadPages = "//img[contains(@src,'export-pages')]";
						String downloadPages = "//div[contains(@class,'type-choice type-choice-0')]";
						
						if(isElementPresent(By.xpath(downloadPages)))
						{
							driver.findElement(By.xpath(downloadPages)).click();
							//selenium.click(downloadPages);
							Thread.sleep(7000);
						}
					}
					else
					{
						captureScreenshot(downloadSuccessful, "DownloadOptionNotPresent");
						debugLogger.info("Download Option Not present.");
					}
				}
				downloadSuccessful = true;
				//driver.switchTo().frame("pages");
			/*}
			else
			{
				downloadSuccessful = false;
				captureScreenshot(downloadSuccessful, "UnableToFindDocPath");
			}*/
			//driver.switchTo().frame(driver.findElement(By.xpath(iCloudConstants.iFrame)));
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return downloadSuccessful;
	}

	public boolean duplicateContextMenu(String docPath)
	{
		boolean duplicateSuccessful = false;
		//driver.switchTo().frame("pages");
		
		try {
			Actions action= new Actions(driver);
			generalUtility.bringTheFocusToCentreOfScreen();
			
			if(isElementPresent(By.xpath(docPath)))
			{
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					createDocGearMenu();
					duplicateContextMenu(docPath);
				}else{
					String selectFirstDoc_alt = "//div[@class='img-container']";
					//find the name of the document
					String FirstDocName = "//div[@class='document-name']";			
					String docName = driver.findElement(By.xpath(FirstDocName)).getText();				
					//docCount = driver.findElements(By.xpath(selectFirstDoc)).size();				
					driver.findElement(By.xpath(selectFirstDoc_alt)).click();
					Thread.sleep(2000);
					
					//send keys (ctrl + enter)
					action.contextClick().sendKeys(Keys.CONTROL).sendKeys(Keys.ENTER);
					action.build().perform();
					Thread.sleep(3000);
					String duplicateContextMenu = "/html/body/div[5]/div[2]/div/div/div[2]/a";
					
					if(isElementPresent(By.xpath(duplicateContextMenu)))
					{
						Thread.sleep(3000);
						driver.findElement(By.xpath(duplicateContextMenu)).isEnabled();
						driver.findElement(By.xpath(duplicateContextMenu)).isSelected();
						driver.findElement(By.xpath(duplicateContextMenu)).click();
						Thread.sleep(3000);
					} 
					else {
						captureScreenshot(duplicateSuccessful, "DuplicateContextNotClickable");
						debugLogger.info("Duplicate Option not clickable.");
						action.release();
					}
					Thread.sleep(3000);
					action.sendKeys(Keys.CONTROL).build().perform();
				}
				duplicateSuccessful = true;
			}
		} catch (Exception e) {
			debugLogger.info("Duplicate Via Context failed");
			captureScreenshot(duplicateSuccessful, "DuplicateContextFail");
		}
		return duplicateSuccessful;
	}
	
	public boolean duplicateInPagesGearMenu(String docPath)
	{
		
		boolean duplicateSuccessful = false;
		//generalUtility.bringTheFocusToCentreOfScreen();
		//driver.switchTo().frame("bight");
		try {
			if(isElementPresent(By.xpath(docPath)))
			{
				
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					//createDoc();
					duplicateInPagesGearMenu(docPath);
				}else{
					
					String selectFirstDoc ="/html/body/div/div[3]/div/div/div/div/div[2]/div";
					String selectFirstDoc_alt = "//div[@class='img-container']";				
					String FirstDocName = "//div[@class='document-name']";
					
					//find the name of the document
					String docName = driver.findElement(By.xpath(FirstDocName)).getText();
					
					//docCount = driver.findElements(By.xpath(selectFirstDoc)).size();				
					driver.findElement(By.xpath(selectFirstDoc_alt)).click();
					Thread.sleep(2000);
					
					driver.findElement(By.xpath(iCloudConstants.actionMenu)).click();
					
					Thread.sleep(3000);
					
					
					//String downloadOptionGear = "//*[@class='atv3 sc-view sc-menu-item focus']";
					String duplicateOptionGear = "/html/body/div[5]/div[3]/div[2]/div/div[5]/a";
					if(isElementPresent(By.xpath(duplicateOptionGear)))
					{
						Thread.sleep(5000);
						driver.findElement(By.xpath(duplicateOptionGear)).click();
						Thread.sleep(6000);
					}
					else{
						duplicateSuccessful = false;
						captureScreenshot(duplicateSuccessful, "DuplicateOptionUnavailable");
					}
				}
				duplicateSuccessful = true;
			} else {
				duplicateSuccessful = false;
				captureScreenshot(duplicateSuccessful, "DuplicateGearMenuNotSeen");
			}
		} catch (Exception e) {
			e.printStackTrace();
			captureScreenshot(duplicateSuccessful, "DuplicateUnSucessfull");
		}
		//driver.switchTo().frame("pages");
		return duplicateSuccessful;
	}

	public boolean editBlankDocFontAndStyle(String docPath){
		
		boolean isEditBlankSucessfull = false;
		Actions actionObject = new Actions(driver);
		
		try {Thread.sleep(3000);} catch (InterruptedException e1) {e1.printStackTrace();}
		
		//Get current windows		
		final Set<String> beforeHandles = driver.getWindowHandles();
		//String beforeHandle = driver.getWindowHandle();
		mainParentHandle = driver.getWindowHandle();
		
		String openFirstDoc = "xpath=//*[@class='preview clickable preview-img select-border']";
		String fontTypeDropDown = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme popup sc-view sc-button-view sc-select-view iw-font-family-select-view button sc-regular-size icon']/label";
		String fontFutura = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view']/div[24]";
		String sizeChange = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view iw-text-spinner-view']";
		String allignmentChange = "//*[@class='sc-button-label sc-regular-size ellipsis icon']";
		String toolsIcon = "//div[text()='Tools']";

		try{
			if(isElementPresent(By.xpath(docPath)))
			{
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					createDocGearMenu();
					editBlankDocFontAndStyle(docPath);
				}
				else{
					try {
						//below code is for opening the blank document
						selenium.focus(openFirstDoc);
						Thread.sleep(2000);
						selenium.doubleClick(openFirstDoc);
					} catch (Exception e) {
						captureScreenshot(isEditBlankSucessfull, "OpeningFirstDocFailed");
						debugLogger.info("Opening of first blank document failed while editing.");
					}
				
				//below code written for handling the pop up********************
				
				try {
					Thread.sleep(2000);
					
					//Get current window handles
					Set<String> cHandle = driver.getWindowHandles();
					int windowHandleCount = cHandle.size();
					
					//remove all before handles from after.  Leaves you with new window handle
					cHandle.removeAll(beforeHandles);		
					//Switch to the new window
					String newWindowHandle = cHandle.iterator().next();

					driver.switchTo().window(newWindowHandle);
					
					//open another browser window and non-supported browser pop-up shows up, close that one. OK Alert
					if(isElementPresent(By.xpath(iCloudConstants.nonSupportedBrowserPopup)))
					{
						driver.findElement(By.xpath(iCloudConstants.nonSupportedBrowserPopup)).click();
					}
					driver.switchTo().window(newWindowHandle);
					
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "HandlingAlertFailed");
					debugLogger.info("Clicking on OK Alert option failed.");
				}
				
				// pop up alert handled ends here...
				
				// the first paragraph starts

				try {
					Thread.sleep(13000);
					String docWindow = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme iw-pages-canvas sc-view iw-canvas-root-layer-view']/div[1]/div[1]/div[1]/div[1]";
					
					actionObject.sendKeys("A published report says Apple may introduce a cheaper iPhone in an effort to reclaim some of the sales that the company has been losing to less expensive handsets running on Google's Android software." +
							"Wednesday's story in the Wall Street Journal speculates that Apple could lower the iPhone's price by equipping the device with an exterior that costs less than aluminum housing on current models").perform();
					actionObject.build().perform();
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "WritingFirstParaFailed");
					debugLogger.info("Writing the first paragraph failed.");
				}
				// enter the first paragraph ends
				
				// selecting all starts
				try {
					actionObject.sendKeys("\uE03D").sendKeys("a").perform();
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "SelectAllFirstParaFailed");
					debugLogger.info("Select all of first paragraph failed.");
				}
				// selecting all ends
				
				// selecting the font type starts
				
				try {
					Thread.sleep(3000);
					selenium.click(fontTypeDropDown);
					Thread.sleep(3000);
					selenium.click(fontFutura);
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "ChooseFontTypeFailed");
					debugLogger.info("Choosing FUTURA font style failed.");
				}
				// selecting the font type ends
				
				// selecting the font size starts
				try {
						Thread.sleep(3000);
						
						List<WebElement> myFields = driver.findElements(By.xpath(sizeChange));
					    
						for(int i=0;i<=myFields.size();i++){
							if(i==0){
					    	    String optionId = myFields.get(i).getAttribute("id");
					    	    String upId = "//*[@id='"+optionId+"']/div[1]/div[1]";
					    	    Thread.sleep(2000);
					    	   
					    	    selenium.doubleClick(upId);
					    	   
								Thread.sleep(4000);
							}
						}
					} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "SelectFontSizeFailed");
					debugLogger.info("Selecting the font size failed.");
				}
				// selecting the font size ends
				
				// increasing the para spacing before starts
				try {
					Thread.sleep(3000);
					List<WebElement> myFields = driver.findElements(By.xpath(sizeChange));
				    
					for(int i=0;i<=myFields.size();i++){
						if(i==2){
				    	    String optionId = myFields.get(i).getAttribute("id");
				    	    String upId = "//*[@id='"+optionId+"']/div[1]/div[1]";
				    	    Thread.sleep(2000);
				    	   
				    	    //selenium.doubleClick(upId);
				    	    selenium.doubleClick(upId);
				    	   
							Thread.sleep(4000);
						}
					}
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "IncreaseSpaceBeforeParaFailed");
					debugLogger.info("Increasing para space 'before' failed.");
				}
				// increasing the para spacing before ends
				
				// increasing the para spacing after starts
				try {
					Thread.sleep(3000);
					List<WebElement> myFields = driver.findElements(By.xpath(sizeChange));
				    
					for(int i=0;i<=myFields.size();i++){
						if(i==3){
				    	    String optionId = myFields.get(i).getAttribute("id");
				    	    String upId = "//*[@id='"+optionId+"']/div[1]/div[1]";
				    	    Thread.sleep(2000);
				    	   
				    	    selenium.doubleClick(upId);
				    	   
							Thread.sleep(4000);
						}
					}
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "IncreaseSpaceAfterParaFailed");
					debugLogger.info("Increasing para space 'after' failed.");
				}
				// increasing the para spacing after ends
				
				// changing the alignment starts
				try {
					List<WebElement> myFields = driver.findElements(By.xpath(allignmentChange));
					
					for(int i=0; i<myFields.size();i++){
						if(i>3 && i<8){
							String optionId = myFields.get(i).getAttribute("id");
							String allignId = "//*[@id='"+optionId+"']";
							Thread.sleep(3000);
							selenium.click(allignId);
						}
					}
					
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "AlignmentFailed");
					debugLogger.info("Alignment failed.");
				}
				// changing the alignment ends
				
				// changing line spacing starts
				try {
					Thread.sleep(3000);
					List<WebElement> myFields = driver.findElements(By.xpath(sizeChange));
				    
					for(int i=0;i<=myFields.size();i++){
						if(i==1){
				    	    String optionId = myFields.get(i).getAttribute("id");
				    	    String upId = "//*[@id='"+optionId+"']/div[1]/div[1]";
				    	    Thread.sleep(2000);
				    	   
				    	    selenium.doubleClick(upId);
				    	   
							Thread.sleep(4000);
						}
					}
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "LineSpacingFailed");
					debugLogger.info("Line spacing failed");
				}
				// changing line spacing ends
				
				// indentation starts
				try {
					List<WebElement> myFields = driver.findElements(By.xpath(allignmentChange));
					
					for(int i=0; i<myFields.size();i++){
						if(i>10 && i<13){
							String optionId = myFields.get(i).getAttribute("id");
							String allignId = "//*[@id='"+optionId+"']";
							Thread.sleep(3000);
							selenium.click(allignId);
						}
					}
					
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "IndentationFailed");
					debugLogger.info("Indenting failed.");
				}
				// indentation ends
				
				// changing color start
				try {
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[@class='iw-color-label-view']")).click();
					
					Thread.sleep(1000);
					List<WebElement> myFields = driver.findElements(By.xpath("//*[@class='item first-row']"));
					
					for(int i=0; i<myFields.size();i++){
						if(i==1){
							String optionId = myFields.get(i).getAttribute("id");
							String colorId = "//*[@id='"+optionId+"']";
							Thread.sleep(3000);
							selenium.click(colorId);
						}
					}
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "ColorChangeFailed");
					debugLogger.info("Changing color failed.");
				}
				// changing color ends
				
				// deselecting starts
				try {
					Thread.sleep(3000);
					actionObject.sendKeys("\uE03D").sendKeys(Keys.UP).perform();
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "DeselectingFailed");
					debugLogger.info("Deselecting failed.");
				}
				// deselecting ends
				
				// entring the heading starts
				try {
					actionObject.sendKeys("Apple may build less expensive iPhone: report\n\n").perform();
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "EntringHeadingFailed");
					debugLogger.info("Entring a header failed.");
				}
				// entring the heading ends
				
				// going to the end and typing a new para starts
				
				try {
					Thread.sleep(2000);
					actionObject.sendKeys("\uE03D").sendKeys(Keys.DOWN).perform();
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "TraversingToEndFailed");
					debugLogger.info("Traversing to end of the paragraph failed.");
				}
				// going to the end and typing a new para starts
				
				// enter a new para at the end starts
				try {
					actionObject.sendKeys("\uE03D").perform();
					actionObject.sendKeys("\n\nThe cheaper iPhone could come out as early as this year, or the idea could be scrapped, as has "+
					"previously happened. The Journal cites unnamed people briefed on the matter. \nApple, which is based on Cupertino, California, declined "+
							"to comment.\nPrices for the latest iPhones without a wireless contract currently start at $649. " +
					"Apple, though, already sells older models at discounts. \nCopyright 2013 The Associated Press. All rights reserved. This material may " +
							"not be published, broadcast, rewritten or redistributed.").perform();
				} catch (Exception e) {
					captureScreenshot(isEditBlankSucessfull, "EntringSecondParaFailed");
					debugLogger.info("Enting second paragraph failed.");
				}
				// enter a new para at the end ends

				Thread.sleep(4000);
				EditBlankDocAddTextBox();
				Thread.sleep(4000);
				EditDocFormat();
				Thread.sleep(4000);
				viewMenu();
				Thread.sleep(4000);
				find();
				Thread.sleep(4000);
				helpMenu();
				
				Thread.sleep(4000);
				driver.close();
				Thread.sleep(3000);
				driver.switchTo().window(mainParentHandle);
				
				Thread.sleep(10000);
				driver.switchTo().frame("pages");
			}
			isEditBlankSucessfull = true;
		}
		else{
			isEditBlankSucessfull = false;
			debugLogger.debug("Unable to locate the document to open");
			testResultsLogger.info("Unable to locate the document to open");
			captureScreenshot(isEditBlankSucessfull, "LocatingDocToOpenFailed");
		}
		
	}
	catch(Exception e){
		isEditBlankSucessfull = false;
		captureScreenshot(isEditBlankSucessfull, "EditBlank");
		debugLogger.debug("Creating document from gear option was unsuccessful"+e);
		testResultsLogger.info("Creating document from gear option was unsuccessful "+e);
		e.printStackTrace();
	}
	return isEditBlankSucessfull;
}	
	
	public boolean uploadInvalidFormat(String docLocation){
		
		boolean invlidFormatUpload = false;
		try {
			Thread.sleep(4000);
			//boolean tabFrameVisible = isElementPresent(By.xpath(iCloudConstants.tabFrame));
			
			//if (tabFrameVisible){		
				Thread.sleep(2000);
				if(isElementPresent(By.xpath(iCloudConstants.actionMenu)))
				{
					driver.findElement(By.xpath(iCloudConstants.actionMenu)).isSelected();
					driver.findElement(By.xpath(iCloudConstants.actionMenu)).click();
					Thread.sleep(3000);
			
					//select from enabled choices
					List<WebElement> inputs = driver.findElements(By.xpath("//*[@class='atv3 sc-view sc-menu-item']"));
					int index = 0;
					for (WebElement option : inputs) {
						if(index==0 || index==1 ){
							index++;
						}
						
						//upload document option
						if(index==2){
							Thread.sleep(3000);
							option.isSelected();					
							option.click();
							Thread.sleep(3000);
							break;
						}	
					}			
					//testResultsLogger.info("upload menu missing");
						
					Thread.sleep(3000);

					if(iCloudConstants.googleChromeBrowser.equalsIgnoreCase(browserType)&& operatingSystem.equalsIgnoreCase(iCloudConstants.MacOsX))
					{
						try{
						
							try {
								//clicking on the view icon to view as columns
								generalUtility.robotMousePress(BrowserCoordinates.xCoordianteForViewIconInDocumentUploadWindow,
													BrowserCoordinates.yCoordianteForViewIconInDocumentUploadWindow);
								Thread.sleep(1000);
							} catch (Exception e) {
								e.printStackTrace();
								invlidFormatUpload = false;
								captureScreenshot(invlidFormatUpload, "UnableToClickOnViewIcon");
							}
							try {
								//clicking on the search box
								generalUtility.robotMousePress(BrowserCoordinates.xCoordinateOfSearchInDocumentUploadWindow,
													BrowserCoordinates.yCoordinateOfSearchInDocumentUploadWindow);
								Thread.sleep(1000);
							} catch (Exception e) {
								e.printStackTrace();
								invlidFormatUpload = false;
								captureScreenshot(invlidFormatUpload, "UnableToClickOnSearchBox");
							}
							String uploadFileName = generalUtility.getFileNameFromFileLocation(docLocation);
							debugLogger.debug("The file name obtained after processing file location is "+ uploadFileName);
							//typing the file name in search box
							generalUtility.setClipboardData(uploadFileName);
							
							generalUtility.robotSearchProcess(operatingSystem);
							Thread.sleep(2000);
							//clicking for to search the file in the entire mac
							generalUtility.robotMousePress(BrowserCoordinates.xCoordinateOfSearchInEntireMacForDocumentUpload,
											BrowserCoordinates.yCoordinateOfSearchInEntireMacForDocumentUpload);
							Thread.sleep(5000);
							//choosing the first search result document
							generalUtility.robotMousePress(BrowserCoordinates.xCoordinateOfSearchResultInDocumentUploadWindow,
												BrowserCoordinates.yCoordinateOfSearchResultInDocumentUploadWindow);
							//clicking the enter to upload the doc
							generalUtility.robotEnterKeyPress();
							Thread.sleep(4000);						
							//upload ok button
							String uploadFileOKButton = "//label[text()='OK']";
							if(isElementPresent(By.xpath(uploadFileOKButton)))
							{
								driver.findElement(By.xpath(uploadFileOKButton)).isSelected();
								driver.findElement(By.xpath(uploadFileOKButton)).click();
							}
							Thread.sleep(3000);
							//if file to upload already exits, replace it
							String uploadFileReplaceButton = "//label[text()='Replace']";
							if(isElementPresent(By.xpath(uploadFileReplaceButton)))
							{
								driver.findElement(By.xpath(uploadFileReplaceButton)).isSelected();
								driver.findElement(By.xpath(uploadFileReplaceButton)).click();							
							} 
				
				
						} catch (Exception ioe) {
							testResultsLogger.info("File couldn't be uploaded" + ioe);
							invlidFormatUpload = false;
							captureScreenshot(invlidFormatUpload, "FileCantBeUploaded");
							logout();
						}
					Thread.sleep(3000);
					
					}else {
						testResultsLogger.info("Gear menu missing or upload menu is missing...file couldn't be uploaded");
						invlidFormatUpload = false;
						captureScreenshot(invlidFormatUpload, "GearMenuMissing");
					}
					invlidFormatUpload = true;
				}//action menu check
				else
				{
					testResultsLogger.info("Action menu missing or not reachable");
					invlidFormatUpload = false;
					captureScreenshot(invlidFormatUpload, "GearMenuNotReachable");
				}
		} catch (Exception e) {
			e.printStackTrace();
			captureScreenshot(invlidFormatUpload, "InvalidFormatUploadUnsucessfull");
		}
			
		//}//tab visible
	return invlidFormatUpload;
}
	
	private void checkVisibility(String element,String description) throws InterruptedException
	{
		for (int second = 0;; second++) 
		{
			if (second >= 60)
			{
				throw new InterruptedException("Timed out after 120 seconds of waiting for visibility of element "
								+ description);
			}
			debugLogger.debug("Searching for visibility of element : "+ description);
			Boolean blnIsElementVisible = driver.findElement(By.xpath(element)).isDisplayed();
			if (blnIsElementVisible) 
			{
				debugLogger.debug("Visibility of element found : "+ description);
				break;
			} 
			else
			{
				Thread.sleep(1000);
			}
			Thread.sleep(1000);
		}
	}
	
	private void waitForElementPresent(By by,String description) throws InterruptedException
	{
		for (int second = 0;; second++) 
		{
			if (second >= 60)
			{
				throw new InterruptedException("Timed out after 120 seconds of waiting for element "+ description);
			}
			debugLogger.debug("Searching for element : "+ description);
			if (isElementPresent(by))
			{
				debugLogger.debug("Element found : "+ description);
				break;
			}
			else
			{
				Thread.sleep(1000);
			}
			Thread.sleep(1000);
		}
	}
	
	private boolean isElementPresent(By by)
    {
        try 
        {
        	driver.findElement(by);
            return true;
        }
        catch(Exception e)
        {
        	debugLogger.error(e.getMessage());
            return false;
        }
    }
	
	private void waitForSpinningImageDisappear(String spinningImage)throws InterruptedException 
	{
		for (int second = 0;; second++) 
		{
			if (second >= 60)
			{
				throw new InterruptedException("Timed out after 60 seconds for waiting for login");
			}
		
			if (!isElementPresent(By.xpath(iCloudConstants.spinningImage)))
			{
				break;
			}
			Thread.sleep(1000);
		}
	}
	
	@SuppressWarnings("unused")
	
	private void initWebElementsXpathInPagesTab() 
	{
		iCloudConstants.deleteOptionClickInPagesTab = propertyReader.getDeleteOptionClickInPagesTab();
		iCloudConstants.downloadToPagesFormatInPagesTab = propertyReader.getDownloadToPagesFormatInPagesTab();
		iCloudConstants.downloadToPDFFormatInPagesTab = propertyReader.getDownloadToPDFFormatInPagesTab();
		iCloudConstants.downloadToWordFormatInPagesTab = propertyReader.getDownloadToWordFormatInPagesTab();
		iCloudConstants.deleteOptionClickToDeleteDocumentsInDeleteFolderInPagesTab = 
												propertyReader.getDeleteOptionClickToDeleteDocumentsInDeleteFolderInPagesTab();
		iCloudConstants.savingTypingRenameTextInFolderInPagesTab = propertyReader.getSavingTypingRenameTextInFolderInPagesTab();
	}
	
	public boolean login()
	{
		boolean isLoginSuccessful = false;
		
		try{
   
			// Clicking the button if present to enter the Sign In page
			boolean icDevSignInButtonPresent = selenium.isElementPresent(iCloudConstants.icDevSignIn);
			driver.findElement(By.xpath(iCloudConstants.icDevSignIn)).click();
			
		    icDevSignInButtonPresent = selenium.isElementPresent(iCloudConstants.icDevSignIn);
		    if (icDevSignInButtonPresent)
		    {
		    		debugLogger.debug("icDevSignInButton is present");
		    		checkVisibility(iCloudConstants.icDevSignIn,iCloudConstants.icDevSignInDescription);
		    		driver.findElement(By.xpath(iCloudConstants.icDevSignIn)).click();
		    		
		    		// authorizing with the credentials
				    //for(int index=0;index<5;index++)
				    //{
		    		WebElement user = driver.findElement(By.xpath(iCloudConstants.icUserNameTextbox));
		    		WebElement password = driver.findElement(By.xpath(iCloudConstants.icPasswordTextbox));
			    	if(isElementPresent(By.xpath(iCloudConstants.icUserNameTextbox))){					    	
					    	user.sendKeys(propertyReader.getUserId());
					}
					    
					if(isElementPresent(By.xpath(iCloudConstants.icPasswordTextbox))){					    	
					    	password.sendKeys(propertyReader.getPwd());
					}
					Thread.sleep(3000);	
					
					if(isElementPresent(By.xpath(iCloudConstants.icLoginButton))){
						driver.findElement(By.xpath(iCloudConstants.icLoginButton)).click();
					}
					
					Thread.sleep(3000);	
				
					//logic to re-enter username and password
					if ((user.equals("")) && (password.equals(""))){
						
						debugLogger.debug("Please enter correct username or password");
						testResultsLogger.info("Please enter correct username or password");
						Thread.sleep(3000);	
						user.clear();
						user.sendKeys(propertyReader.getUserId());
						Thread.sleep(3000);	
						password.clear();
						password.sendKeys(propertyReader.getPwd());
						Thread.sleep(3000);	
						driver.findElement(By.xpath(iCloudConstants.icLoginButton)).click();
						
						// wait for the spinning image to disappear
						waitForSpinningImageDisappear(iCloudConstants.spinningImage);
					}
						
				    //}
		    }else{
		    		debugLogger.debug("SignInButton is not present");
		    		testResultsLogger.info("SignInButton is not present");
		    		isLoginSuccessful = false;
		    		Thread.sleep(1000);
		    }
		    isLoginSuccessful = true;
		    
		}catch (InterruptedException ie)
		{
			isLoginSuccessful = false;
			debugLogger.debug("Login unsuccessful");
			testResultsLogger.info("Login unsuccessful");
	    	ie.printStackTrace();
	    	driver.close();
		}
		
		return isLoginSuccessful;
	}
	
	public boolean login(String tabname)
	{
		boolean isLoginSuccessful = false;
		
		try{
			// Clicking the button if present to enter the Sign In page
			boolean icDevSignInButtonPresent = selenium.isElementPresent(iCloudConstants.icDevSignIn);
			driver.findElement(By.xpath(iCloudConstants.icDevSignIn)).click();
			
		    icDevSignInButtonPresent = selenium.isElementPresent(iCloudConstants.icDevSignIn);
		    final Set<String> parentWindowHandle = driver.getWindowHandles();
		    if (icDevSignInButtonPresent)
		    {
	    		debugLogger.debug("icDevSignInButton is present");
	    		checkVisibility(iCloudConstants.icDevSignIn,iCloudConstants.icDevSignInDescription);
	    		driver.findElement(By.xpath(iCloudConstants.icDevSignIn)).click();
	    		
	    		// authorizing with the credentials
			    //for(int index=0;index<5;index++)
			    //{
	    		WebElement user = driver.findElement(By.xpath(iCloudConstants.icUserNameTextbox));
	    		WebElement password = driver.findElement(By.xpath(iCloudConstants.icPasswordTextbox));
		    	if(isElementPresent(By.xpath(iCloudConstants.icUserNameTextbox))){					    	
				    	user.sendKeys(propertyReader.getUserId());
				}
				    
				if(isElementPresent(By.xpath(iCloudConstants.icPasswordTextbox))){					    	
				    	password.sendKeys(propertyReader.getPwd());
				}
				Thread.sleep(3000);	
				
				if(isElementPresent(By.xpath(iCloudConstants.icLoginButton))){
					driver.findElement(By.xpath(iCloudConstants.icLoginButton)).click();
				}
				
				Thread.sleep(3000);	
				//logic to re-enter username and password
				if ((user.equals("")) && (password.equals(""))){
					debugLogger.debug("Please enter correct username or password");
					testResultsLogger.info("Please enter correct username or password");
					Thread.sleep(3000);	
					user.clear();
					user.sendKeys(propertyReader.getUserId());
					Thread.sleep(3000);	
					password.clear();
					password.sendKeys(propertyReader.getPwd());
					Thread.sleep(3000);	
					driver.findElement(By.xpath(iCloudConstants.icLoginButton)).click();
					// wait for the spinning image to disappear
					waitForSpinningImageDisappear(iCloudConstants.spinningImage);
				}
				Thread.sleep(3000);
				
				if(isElementPresent(By.xpath("//img[contains(@src,'pages')]")) && tabname=="pages")
				{
					try {
						driver.findElement(By.xpath("//img[contains(@src,'pages')]")).click();
						waitForSpinningImageDisappear(iCloudConstants.spinningImage);
						Thread.sleep(3000);	
						driver.switchTo().frame("pages");
						Thread.sleep(4000);
						//splash screen pop-up shows up, close that one
						if(isElementPresent(By.xpath("//label[text()='Get started with Pages']")))
						{
							driver.findElement(By.xpath("//label[text()='Get started with Pages']")).click();
							Thread.sleep(4000);
						}
						
						isLoginSuccessful = true;
					} catch (Exception e) {
						e.printStackTrace();
						testResultsLogger.info("Pages icon not found");
						isLoginSuccessful = false;
						
						captureScreenshot(isLoginSuccessful, "Login_PagesIcon");
					}
					
					try {
						if(isElementPresent(By.xpath("//*[@class='atv3 sc-view toolbar']")))
						{
							driver.findElement(By.xpath("//label[text()='Cancel']")).click();
						}
						else{}
					} catch (Exception e) {
						e.printStackTrace();
						captureScreenshot(isLoginSuccessful, "TemplateCancelFailed");
					}
					Thread.sleep(4000);
driver.findElement(By.xpath("//*[@class='img help-menu']")).click();

					
				} else if(isElementPresent(By.xpath("//img[contains(@src,'keynote')]")) && tabname=="keynote")
				{
					try {
						driver.findElement(By.xpath("//img[contains(@src,'keynote')]")).click();
						waitForSpinningImageDisappear(iCloudConstants.spinningImage);
						Thread.sleep(3000);	
						driver.switchTo().frame("keynote");
						Thread.sleep(4000);
						//splash screen pop-up shows up, close that one
						if(isElementPresent(By.xpath("//label[text()='Get started with Keynote']")))
						{
							driver.findElement(By.xpath("//label[text()='Get started with Keynote']")).click();
							Thread.sleep(4000);
						}
						
						isLoginSuccessful = true;
					} catch (Exception e) {
						e.printStackTrace();
						testResultsLogger.info("Keynote icon not found");
						isLoginSuccessful = false;
						
						captureScreenshot(isLoginSuccessful, "Login_KeynoteIcon");
					}
				} else if (isElementPresent(By.xpath("//img[contains(@src,'numbers')]")) && tabname=="numbers")
				{
					driver.findElement(By.xpath("//img[contains(@src,'numbers')]")).click();
					waitForSpinningImageDisappear(iCloudConstants.spinningImage);
					Thread.sleep(3000);	
					driver.switchTo().frame("numbers");
				    Thread.sleep(4000);
				    //splash screen pop-up shows up, close that one
					try {
						if(isElementPresent(By.xpath("//label[text()='Get started with Numbers']")))
						{
							driver.findElement(By.xpath("//label[text()='Get started with Numbers']")).click();
							Thread.sleep(4000);
						}
						
						isLoginSuccessful = true;
					} catch (Exception e) {
						e.printStackTrace();
						testResultsLogger.info("Number icon not found");
						isLoginSuccessful = false;
						
						captureScreenshot(isLoginSuccessful, "Login_NumberIcon");
					}
				} else {
					testResultsLogger.info("Pages or Number or Keynote icon not found");
					isLoginSuccessful = false;
					
					captureScreenshot(isLoginSuccessful, "Login_IconNotFound");
				}
				
		    }else{
		    		debugLogger.debug("SignInButton is not present");
		    		testResultsLogger.info("SignInButton is not present");
		    		isLoginSuccessful = false;
		    		Thread.sleep(1000);
		    		captureScreenshot(isLoginSuccessful, "Login_SignInButton");
		    }
		    
		}catch (InterruptedException ie)
		{
			isLoginSuccessful = false;
			captureScreenshot(isLoginSuccessful, "Login_Unsuccessful");
			debugLogger.debug("Login unsuccessful");
			testResultsLogger.info("Login unsuccessful");
	    	ie.printStackTrace();
	    	driver.close();
		}

        return isLoginSuccessful;
	}
	
	/*public boolean tabSwitching()
	{
		boolean isTabSwitchingSuccessful = false;
		//parentWindow = driver.getWindowHandle();
		//driver.switchTo().frame(parentWindow);
		driver.switchTo().frame("bight");
		
		try{
			boolean tabFrameVisible = isElementPresent(By.xpath(iCloudConstants.tabFrame));
			
			if (tabFrameVisible){
				Thread.sleep(2000);
				if(isElementPresent(By.xpath(iCloudConstants.keynoteTab)))
				{
					driver.findElement(By.xpath(iCloudConstants.keynoteTab)).click();
				} else {
					debugLogger.debug("Keynote tab is missing or unable to locate");
					testResultsLogger.info("Keynote tab is missing or unable to locate");
				}
				Thread.sleep(2000);
				if(isElementPresent(By.xpath(iCloudConstants.numbersTab)))
				{
					driver.findElement(By.xpath(iCloudConstants.numbersTab)).click();
				} else {
					debugLogger.debug("Numbers tab is missing or unable to locate");
					testResultsLogger.info("Numbers tab is missing or unable to locate");
				}	
				Thread.sleep(2000);
				if(isElementPresent(By.xpath(iCloudConstants.pagesTab)))
				{
					driver.findElement(By.xpath(iCloudConstants.pagesTab)).click();
				} else {
					debugLogger.debug("Pages tab is missing or unable to locate");
					testResultsLogger.info("Pages tab is missing or unable to locate");
					//logout();
				}
			}else {
				debugLogger.debug("Unable to locate parent frame");
				testResultsLogger.info("Unable to locate parent frame");
			}
			
			isTabSwitchingSuccessful = true;
			
		} catch (InterruptedException e) {
			isTabSwitchingSuccessful = false;
			captureScreenshot(isTabSwitchingSuccessful, "TabSwitching");
			debugLogger.debug("TAB switching was unsuccessful"+e);
			testResultsLogger.info("TAB switching was unsuccessful "+e);
			e.printStackTrace();
		}
		
		return isTabSwitchingSuccessful;
		
	}*/

	public boolean logout()
	{
		boolean isLogooutSuccessful = false;
		//try {Thread.sleep(3000);} catch (InterruptedException e1) {e1.printStackTrace();}
		//driver.switchTo().frame("pages");
		try {Thread.sleep(3000);} catch (InterruptedException e1) {e1.printStackTrace();}
		
		boolean tabFrameVisible = true;
		//boolean tabFrameVisible = isElementPresent(By.xpath(iCloudConstants.tabFrame));
		
		try 
		{
			Thread.sleep(4000);
			if (tabFrameVisible)
			{
				String icloudLink = "//*[@class='img app-switcher']";
				Thread.sleep(3000);
				if(isElementPresent(By.xpath(icloudLink))){
					
					//driver.findElement(By.xpath(icloudLink)).isSelected();
					driver.findElement(By.xpath(icloudLink)).click();	
					
					Thread.sleep(5000);
					boolean mainTabFrameVisible = isElementPresent(By.xpath("//div[@class='atv3 sc-view']"));
					
					String signOutLink = "//div[text()='Sign Out']";
					
					if(mainTabFrameVisible){
						driver.switchTo().defaultContent();
						try {
							if(isElementPresent(By.xpath(signOutLink))){
								driver.findElement(By.xpath(signOutLink)).click();
								//driver.findElement(By.linkText("Sign Out")).click();
								Thread.sleep(3000);
							}
							else {
								driver.close();
							}
						} catch (Exception e) {
							e.printStackTrace();
							testResultsLogger.info("Not able to locate Sign out link...exiting");
							isLogooutSuccessful = false;
							captureScreenshot(isLogooutSuccessful, "SignOutLinkUnreachable");
						}
						
					}//main tab visible

				}else {
					testResultsLogger.info("Not able to locate iCloud link...exiting");
					isLogooutSuccessful = false;
					
					driver.close();
				}			
			}//end tab frame
			isLogooutSuccessful = true;	
		} catch (InterruptedException e) {
			
			isLogooutSuccessful = false;
			captureScreenshot(isLogooutSuccessful, "Logout");
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Gilligan log out: FAILED");

		}
	return isLogooutSuccessful;
		
	}

	public boolean uploadPages(String docLocation)
	{
		boolean uploadSuccessful = false;
		driver.switchTo().frame("pages");
		
		try {
			Thread.sleep(5000);
			if(isElementPresent(By.xpath(com.webtest.icloud.iwork.iCloudConstants.actionMenu)))
			{
				driver.findElement(By.xpath(com.webtest.icloud.iwork.iCloudConstants.actionMenu)).isSelected();
				driver.findElement(By.xpath(com.webtest.icloud.iwork.iCloudConstants.actionMenu)).click();
				Thread.sleep(4000);
		
				//select from enabled choices
				List<WebElement> inputs = driver.findElements(By.xpath("//*[@class='atv3 sc-view sc-menu-item']"));
				int index = 0;
				for (WebElement option : inputs) {
					if(index==0 || index==1 ){
						index++;
					}
					
					//upload document option
					if(index==2){
						Thread.sleep(3000);
						option.isSelected();					
						option.click();
						Thread.sleep(3000);
						break;
					}	
				}			

				Thread.sleep(4000);

				if(com.webtest.icloud.iwork.iCloudConstants.googleChromeBrowser.equalsIgnoreCase(browserType)&& operatingSystem.equalsIgnoreCase(com.webtest.icloud.iwork.iCloudConstants.MacOsX))
				{
					Thread.sleep(2000);
					generalUtility.bringTheFocusToCentreOfScreen();
					
					try{								
							//clicking on the view icon to view as columns
							generalUtility.robotMousePress(BrowserCoordinates.xCoordianteForViewIconInDocumentUploadWindow,
												BrowserCoordinates.yCoordianteForViewIconInDocumentUploadWindow);
							Thread.sleep(2000);
					} catch (Exception ioe) {
						testResultsLogger.info("File upload: unable to locate view icon\n" + ioe);
						uploadSuccessful = false;
						captureScreenshot(uploadSuccessful, "FileUploadFailed");
						generalUtility.robotEscKeyPress();
					}
					try{
							//clicking on the search box
							generalUtility.robotMousePress(BrowserCoordinates.xCoordinateOfSearchInDocumentUploadWindow,
												BrowserCoordinates.yCoordinateOfSearchInDocumentUploadWindow);
							Thread.sleep(2000);
					} catch (Exception ioe) {
						testResultsLogger.info("File upload: unable to search box icon\n" + ioe);
						uploadSuccessful = false;
						captureScreenshot(uploadSuccessful, "FileUploadFailed");
						generalUtility.robotEscKeyPress();
						
					}		
					try{
						String uploadFileName = generalUtility.getFileNameFromFileLocation(docLocation);
							//debugLogger.debug("The file name obtained after processing file location is "+ uploadFileName);
							//typing the file name in search box
							generalUtility.setClipboardData(uploadFileName);
					} catch (Exception ioe) {
						testResultsLogger.info("File upload: unable to type file name\n" + ioe);
						uploadSuccessful = false;
						captureScreenshot(uploadSuccessful, "FileUploadFailed");
						generalUtility.robotEscKeyPress();
					}		
					try{
							generalUtility.robotSearchProcess(operatingSystem);
							Thread.sleep(2000);
							//clicking for to search the file in the entire mac
							generalUtility.robotMousePress(BrowserCoordinates.xCoordinateOfSearchInEntireMacForDocumentUpload,
											BrowserCoordinates.yCoordinateOfSearchInEntireMacForDocumentUpload);
							Thread.sleep(5000);
					} catch (Exception ioe) {
						testResultsLogger.info("File upload: unable to locate file\n" + ioe);
						uploadSuccessful = false;
						captureScreenshot(uploadSuccessful, "FileUploadFailed");
						generalUtility.robotEscKeyPress();
					}
					try{
							//choosing the first search result document
							generalUtility.robotMousePress(BrowserCoordinates.xCoordinateOfSearchResultInDocumentUploadWindow,
												BrowserCoordinates.yCoordinateOfSearchResultInDocumentUploadWindow);
					} catch (Exception ioe) {
						testResultsLogger.info("File upload: unable to click on first found file\n" + ioe);
						uploadSuccessful = false;
						captureScreenshot(uploadSuccessful, "FileUploadFailed");
						generalUtility.robotEscKeyPress();
					}
					try{
							//clicking the enter to upload the doc
							generalUtility.robotEnterKeyPress();
							Thread.sleep(4000);
							uploadSuccessful = true;
					} catch (Exception ioe) {
						testResultsLogger.info("File upload: unable to click enter\n" + ioe);
						uploadSuccessful = false;
						captureScreenshot(uploadSuccessful, "FileUploadFailed");
						generalUtility.robotEscKeyPress();
					}
		
/*							if (generalUtility.fileCorruptedCheck(uploadFileName, "2600")){
								uploadSuccessful = true;
								testResultsLogger.info("File upload check: uploaded successful");
							} else {
								uploadSuccessful = false;
								testResultsLogger.info("File upload check: uploaded unsuccessful");
								//dismiss open window
								generalUtility.robotEscKeyPress();
							}
					
*/					//upload OK button
					String uploadFileOKButton = "//label[text()='OK']";
					if(isElementPresent(By.xpath(uploadFileOKButton)))
					{
						driver.findElement(By.xpath(uploadFileOKButton)).isSelected();
						driver.findElement(By.xpath(uploadFileOKButton)).click();
						uploadSuccessful = true;
					}
					try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
				}//browser check
				//if file to upload already exits, replace it
				String uploadFileReplaceButton = "//label[text()='Replace']";
				if(isElementPresent(By.xpath(uploadFileReplaceButton)))
				{
					driver.findElement(By.xpath(uploadFileReplaceButton)).isSelected();
					driver.findElement(By.xpath(uploadFileReplaceButton)).click();
					uploadSuccessful = true;
				}
				Thread.sleep(2000);				
			}else {
				testResultsLogger.info("Gear menu missing or upload menu is missing...file couldn't be uploaded");
				uploadSuccessful = false;
				captureScreenshot(uploadSuccessful, "GearMenuMissing");
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			//e.printStackTrace();
			uploadSuccessful = false;
			captureScreenshot(uploadSuccessful, "UploadUnsucessfull");
		}
		return uploadSuccessful;
	}

	public boolean createDocGearMenu()
	{
		boolean createDocGearMenuSuccessful = false;
		//Get current windows		
		final Set<String> parentWindowHandle = driver.getWindowHandles();
		String parentWindowHandle1 = driver.getWindowHandle();
		String actionMenu = "//*[@class='img action-menu']";

		try {
			if(isElementPresent(By.xpath(actionMenu))){
				driver.findElement(By.xpath(actionMenu)).isSelected();
				driver.findElement(By.xpath(actionMenu)).click();
				Thread.sleep(3000);
				List<WebElement> inputs = driver.findElements(By.xpath("//*[@class='atv3 sc-view sc-menu-item']/a/span"));
				int index =0;
				for (WebElement option : inputs) {
					if(index==0){
						Thread.sleep(3000);
						option.isSelected();
						option.click();
					}
					index++;
					break;
				}
				
				Thread.sleep(3000);
				if(isElementPresent(By.xpath(iCloudConstants.templateChooseButton)))
				{
					driver.findElement(By.xpath(iCloudConstants.templateChooseButton)).click(); //click action that cause new window to open 
					
				}else{
					debugLogger.info("Template choose window didn't open...going back to canvas manager");
					driver.switchTo().frame("pages");
				}
				//Get current window handles
				Set<String> cHandle = driver.getWindowHandles();
				int windowHandleCount = cHandle.size();
				//remove all before handles from after.  Leaves you with new window handle
				cHandle.removeAll(parentWindowHandle);		
				//Switch to the new window
				String newWindowHandle = cHandle.iterator().next();
				driver.switchTo().window(newWindowHandle);
		
				Thread.sleep(5000);
				//open another browser window and non-supported browser pop-up shows up, close that one
				if(isElementPresent(By.xpath(iCloudConstants.nonSupportedBrowserPopup)))
				{
					driver.findElement(By.xpath(iCloudConstants.nonSupportedBrowserPopup)).click();
				}
				Thread.sleep(3000);
				//driver.switchTo().window(newWindowHandle);
				driver.close();
				Thread.sleep(3000);
				//generalUtility.bringTheFocusToCentreOfScreen();
				driver.switchTo().window(parentWindowHandle1);				
				driver.switchTo().frame("pages");
				createDocGearMenuSuccessful = true;
				Thread.sleep(5000);
			} else {
				debugLogger.info("Create doc: Unable to find gear menu");
				captureScreenshot(createDocGearMenuSuccessful, "GearMenuNotFound");
			}
		} catch (Exception e) {
			debugLogger.info("Creating doc via gear menu failed.");
			captureScreenshot(createDocGearMenuSuccessful, "createDocGearMenuUnSuccessful");
		}	
		return createDocGearMenuSuccessful;
	}
	
	/*private boolean createDocGear(){
		boolean isCreateDocGearSucessfull = false;
		try {
			//Get current windows		
			final Set<String> beforeHandles = driver.getWindowHandles();
			//try {Thread.sleep(3000l);} catch (InterruptedException e) {e.printStackTrace();}
			//driver.switchTo().frame("bight");
			Thread.sleep(3000);
			String actionMenu = "//*[@class='img action-menu']";
			if(isElementPresent(By.xpath(actionMenu))){
				driver.findElement(By.xpath(actionMenu)).isSelected();
				driver.findElement(By.xpath(actionMenu)).click();
				Thread.sleep(2000);
			
				try {
					List<WebElement> inputs = driver.findElements(By.xpath("//*[@class='atv3 sc-view sc-menu-item']/a/span"));
					int index =0;
					for (WebElement option : inputs) {
						if(index==0){
							Thread.sleep(3000);
							option.isSelected();
							option.click();
						}
						index++;
						isCreateDocGearSucessfull = true;
						break;
					}
					//Thread.sleep(2000);
					//driver.findElement(By.xpath("//span[text()='Create Document']"));
				} catch (Exception e) {
					e.printStackTrace();
					captureScreenshot(isCreateDocGearSucessfull, "UnableToClickCreate");
				}

				Thread.sleep(3000);
				//chooseBlankTheme(beforeHandles);
				
			} else {
				testResultsLogger.info("Create doc: Unable to find gear menu");
				captureScreenshot(isCreateDocGearSucessfull, "GearMenuNotFound");
			}
		} catch (Exception e) {
			e.printStackTrace();
			captureScreenshot(isCreateDocGearSucessfull, "UnableToCreateDocGear");
		}
		return isCreateDocGearSucessfull;
	}*/

	public boolean chooseBlankThemeGearMenu()
	{
		boolean chooseBlankThemeGearMenuSuccessful = false;
		//Get current windows		
		final Set<String> parentWindowHandle = driver.getWindowHandles();

		try {Thread.sleep(3000l);} catch (InterruptedException e) {e.printStackTrace();}

		String actionMenu = "//*[@class='img action-menu']";

		if(isElementPresent(By.xpath(actionMenu))){
			driver.findElement(By.xpath(actionMenu)).isSelected();
			driver.findElement(By.xpath(actionMenu)).click();
			try {Thread.sleep(3000l);} catch (InterruptedException e) {e.printStackTrace();}
			List<WebElement> inputs = driver.findElements(By.xpath("//*[@class='atv3 sc-view sc-menu-item']/a/span"));
			int index =0;
			for (WebElement option : inputs) {
				if(index==0){
					try {Thread.sleep(3000l);} catch (InterruptedException e) {e.printStackTrace();}
					option.isSelected();
					option.click();
				}
				index++;
				break;
			}
			try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}
			
	        WebElement blankTheme = driver.findElement(By.xpath("//img[contains(@src,'Blank')]"));           
	        Actions myMouse = new Actions(driver); 
	        myMouse.moveToElement(blankTheme).build().perform();

			//look for Blank Theme 
			String blankThemePath = "//img[contains(@src,'Blank')]";
			try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}
			if(isElementPresent(By.xpath(blankThemePath))){
				
				driver.findElement(By.xpath(blankThemePath)).isSelected();
				driver.findElement(By.xpath(blankThemePath)).click();
			
				//create "Blank" doc
				try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
				if(isElementPresent(By.xpath(iCloudConstants.templateChooseButton)))
				{
					driver.findElement(By.xpath(iCloudConstants.templateChooseButton)).click(); //click action that cause new window to open 
				} else {
					testResultsLogger.info("Template choose button not found/not clickable");
				}
				
				try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}

				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Blank Theme selected");
			}else{
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Blank Theme not found...exiting");
				chooseBlankThemeGearMenuSuccessful = false;
				//Get current window handles
				Set<String> cHandle = driver.getWindowHandles();
				int windowHandleCount = cHandle.size();
				//remove all before handles from after.  Leaves you with new window handle
				cHandle.removeAll(parentWindowHandle);		
				//Switch to the new window
				String newWindowHandle = cHandle.iterator().next();
				driver.switchTo().window(newWindowHandle);
				//save and close
				driver.switchTo().window(newWindowHandle).close();
			}
				//Get current window handles
				Set<String> cHandle = driver.getWindowHandles();
				int windowHandleCount = cHandle.size();
				//remove all before handles from after.  Leaves you with new window handle
				cHandle.removeAll(parentWindowHandle);		
				//Switch to the new window
				String newWindowHandle = cHandle.iterator().next();
				driver.switchTo().window(newWindowHandle);
				
				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
				//try to close the alert window 
				//Alert alert = driver.switchTo().alert();
				//alert.dismiss();
				
				//open another browser window and non-supported browser pop-up shows up, close that one
				if(isElementPresent(By.xpath(iCloudConstants.nonSupportedBrowserPopup)))
				{
					driver.findElement(By.xpath(iCloudConstants.nonSupportedBrowserPopup)).click();
				}
				
				driver.switchTo().window(newWindowHandle);
				driver.close();
				try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
				chooseBlankThemeGearMenuSuccessful = true;
			
		} else {
			testResultsLogger.info("Create doc: Unable to find gear menu");
		}	
		return chooseBlankThemeGearMenuSuccessful;		
	}
	
	/*public boolean chooseBlankThemePlusIcon()
	{
		boolean chooseBlankThemeSuccessful = false;		
		boolean tabFrameVisible = isElementPresent(By.xpath(iCloudConstants.tabFrame));
		
		if (tabFrameVisible){
			//create a new doc via + icon
			int divcount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
			//Get current windows		
			final Set<String> parentWindowHandle = driver.getWindowHandles();
			try {
				driver.findElement(By.xpath(iCloudConstants.docCount+"["+divcount+iCloudConstants.docCount_end)).click();			
			}catch(Exception e){
				testResultsLogger.error(e.getMessage());
			}
			try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}
			
	        WebElement blankTheme = driver.findElement(By.xpath("//img[contains(@src,'Blank')]"));           
	        Actions myMouse = new Actions(driver); 
	        myMouse.moveToElement(blankTheme).build().perform();

			//look for Blank Theme 
			String blankThemePath = "//img[contains(@src,'Blank')]";
			
			if(isElementPresent(By.xpath(blankThemePath))){
				
				driver.findElement(By.xpath(blankThemePath)).isSelected();
				driver.findElement(By.xpath(blankThemePath)).click();
			
				//create "Blank" doc
				try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
				if(isElementPresent(By.xpath(iCloudConstants.templateChooseButton)))
				{
					driver.findElement(By.xpath(iCloudConstants.templateChooseButton)).click(); //click action that cause new window to open 
				} else {
					testResultsLogger.info("Template choose button not found/not clickable");
				}
				
				try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}

				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Blank Theme selected");
			}else
			{
				testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Blank Theme not found...exiting");
				chooseBlankThemeSuccessful = false;
				//Get current window handles
				Set<String> cHandle = driver.getWindowHandles();
				int windowHandleCount = cHandle.size();
				//remove all before handles from after.  Leaves you with new window handle
				cHandle.removeAll(parentWindowHandle);		
				//Switch to the new window
				String newWindowHandle = cHandle.iterator().next();
				driver.switchTo().window(newWindowHandle);
				//save and close
				driver.switchTo().window(newWindowHandle).close();
			}
			
			//Get current window handles
			Set<String> cHandle = driver.getWindowHandles();
			int windowHandleCount = cHandle.size();
			//remove all before handles from after.  Leaves you with new window handle
			cHandle.removeAll(parentWindowHandle);		
			//Switch to the new window
			String newWindowHandle = cHandle.iterator().next();
			driver.switchTo().window(newWindowHandle);
			
			try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
			
			//open another browser window and non-supported browser pop-up shows up, close that one
			if(isElementPresent(By.xpath(iCloudConstants.nonSupportedBrowserPopup)))
			{
				driver.findElement(By.xpath(iCloudConstants.nonSupportedBrowserPopup)).click();
			}
			try {Thread.sleep(6000);} catch (InterruptedException e) {e.printStackTrace();}
			driver.switchTo().window(newWindowHandle).close();

			chooseBlankThemeSuccessful = true;
		} else {
			testResultsLogger.info("main frame tab is not visible");
		}
		try {Thread.sleep(6000);} catch (InterruptedException e) {e.printStackTrace();}

		return chooseBlankThemeSuccessful;
	}//end chooseBlankTheme()
*/	

	public boolean chooseProjectProposalTheme(){
		
		boolean isProjectProposalThemeSelected = false;
		try {
			mainParentHandle = driver.getWindowHandle();
			Thread.sleep(3000);
			driver.switchTo().frame("pages");
			//driver.switchTo().window(mainParentHandle);
			
			//create a new doc via + icon
			int divcount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
			//Get current windows		
			final Set<String> parentWindowHandle = driver.getWindowHandles();
			try {
				driver.findElement(By.xpath(iCloudConstants.docCount+"["+divcount+iCloudConstants.docCount_end)).click();			
			}catch(Exception e){
				testResultsLogger.error(e.getMessage());
			}
			
		    WebElement projectProposalTheme = driver.findElement(By.xpath("//img[contains(@src,'ProjectProposal')]"));          
		    Actions myMouse = new Actions(driver); 
		    myMouse.moveToElement(projectProposalTheme).build().perform();
		    

			//look for Project Proposal theme 
			String projectProposalThemePath = "//img[contains(@src,'ProjectProposal')]";
			//String partyInviteThemePath = "//img[contains(@src,'PartyInvite')]";
			
			if(isElementPresent(By.xpath(projectProposalThemePath)))
			{
				driver.findElement(By.xpath(projectProposalThemePath)).isSelected();
				driver.findElement(By.xpath(projectProposalThemePath)).click();
			
				//create "Project Proposal" doc
				Thread.sleep(3000);
				if(isElementPresent(By.xpath(iCloudConstants.templateChooseButton)))
				{
					driver.findElement(By.xpath(iCloudConstants.templateChooseButton)).click(); //click action that cause new window to open 
				}
				Thread.sleep(4000);

				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Project Proposal Theme selected : PASSED");
			}else
			{
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": PROJECT PROPOSAL THEME NOT SELECTED : *********FAILED********");
				
				//Get current window handles
				Set<String> cHandle = driver.getWindowHandles();
				int windowHandleCount = cHandle.size();
				//remove all before handles from after.  Leaves you with new window handle
				cHandle.removeAll(parentWindowHandle);		
				//Switch to the new window
				String newWindowHandle = cHandle.iterator().next();
				driver.switchTo().window(newWindowHandle);
				//save and close
				driver.switchTo().window(newWindowHandle).close();
			}
			
			//Get current window handles
			Set<String> cHandle = driver.getWindowHandles();
			int windowHandleCount = cHandle.size();
			//remove all before handles from after.  Leaves you with new window handle
			cHandle.removeAll(parentWindowHandle);		
			//Switch to the new window
			String newWindowHandle = cHandle.iterator().next();
			driver.switchTo().window(newWindowHandle);
			
			//open another browser window and non-supported browser pop-up shows up, close that one
			if(isElementPresent(By.xpath(iCloudConstants.nonSupportedBrowserPopup)))
			{
				driver.findElement(By.xpath(iCloudConstants.nonSupportedBrowserPopup)).click();
			}
			driver.switchTo().window(newWindowHandle);

			Thread.sleep(7000);
			//Image manipulation
			imageAddAndEdit();			
			Thread.sleep(7000);
			
			//added for dubugging jan 30th 3:27pm
			Set<String> currentW = driver.getWindowHandles();
			System.out.println(currentW.toString());
			driver.close();
			driver.switchTo().window(mainParentHandle);
			
			Thread.sleep(9000);
			isProjectProposalThemeSelected = true;
			
		} catch (Exception e) {
			captureScreenshot(isProjectProposalThemeSelected, "ProjectProposalThemeNotSelected");
			debugLogger.info("Project Proposal Theme Not Selected.");
		}
		return isProjectProposalThemeSelected;
	}

public boolean saveDocument(Set<String> currentWindowHandle){
		
		boolean isSaveSuccessful = false;
		String childHandle = driver.getWindowHandle();
		/*//Get current window handles
		Set<String> cHandle = driver.getWindowHandles();
		int windowHandleCount = cHandle.size();
		//remove all before handles from after.  Leaves you with new window handle
		cHandle.removeAll(currentWindowHandle);		
		//Switch to the new window
		String newWindowHandle = cHandle.iterator().next();
		driver.switchTo().window(newWindowHandle);*/

		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		
		try {
			/*//driver.switchTo().window(newWindowHandle);
			driver.switchTo().window(newWindowHandle).close();
			
			Thread.sleep(4000);

			driver.switchTo().window(currentWindowHandle.toString());
			//driver.close();
*/			driver.close();
			Thread.sleep(3000);
			driver.switchTo().window(childHandle);
			isSaveSuccessful = true;
		}catch(Exception e){
			
			//testResultsLogger.info("Not able to close the current window");
			captureScreenshot(isSaveSuccessful, "CloseCurrentWindowFailed");
		}

		return isSaveSuccessful;
	}

	private void shapeAddAndEdit(Set<String> pWindowHandle){
		
		try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
		//ChooseNonBlankTheme();
		//HandlingOKAlert();
		// clicking on image icon and add image

		try{
			// clicking on shapes
			//selenium.focus("class=img insert-shape-icon");
			driver.findElement(By.xpath("//div[@class='img insert-shape-icon']"));
			
			try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
			//selenium.click("xpath=//div[text()='Shapes']");
			String shapeIcon = "//div[text()='Shapes']";
			if(isElementPresent(By.xpath(shapeIcon)))
			{
				//selenium.click(shapeIcon);
				driver.findElement(By.xpath(shapeIcon)).click();
			}
		}
		catch(Exception e1){
			System.out.println("Shapes menu is not selectable or pop over menu did not appear to allow to select star ***** "+e1);
			viewMenu();
			//selenium.close();
			driver.close();
		}
		try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
		
		//select a star
		try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
		//selenium.click("xpath=html/body/div[6]/div[3]/div[15]/div[2]");		
		driver.findElement(By.xpath("/html/body/div[6]/div[3]/div[15]/div[2]")).click();
		
		try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
		//selenium.click("xpath=//label[text()='Arrange']");
		driver.findElement(By.xpath("//label[text()='Arrange']")).click();
		
		// moving the shape next to the image
		try{
			try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
			selenium.focus("xpath=html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[3]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input");
			driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[3]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input")).sendKeys("400 px");
			
			try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
			selenium.focus("xpath=html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div[2]/input");
			driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[3]/div[2]/div/div[2]/div[1]/div[2]/div[2]/input")).sendKeys("20 px");
		}
		catch(Exception e2){
			System.out.println("Shape move does not work*****"+e2);
		}
		// resizing the shape
		try{
			try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
			
			selenium.focus("xpath=html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input");
			driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input")).sendKeys("150 px");
			
			try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
			
			selenium.focus("xpath=html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/div[2]/input");
			driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/div[2]/input")).sendKeys("150 px");
			driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/div[2]/input")).sendKeys(Keys.RETURN);
		}
		catch(Exception e3){
			//System.out.println("Shape resize does not work"+e3);
		}
	
	}
	
	private void viewMenu(){
		
		boolean isViewMenuWorking = false;
		try{
			Thread.sleep(4000);
			selenium.click("xpath=//div[text()='View']");
		}
		catch(Exception e1){
			captureScreenshot(isViewMenuWorking, "ViewNotClickable");
			//ImageAddEdit();
			selenium.close();
			debugLogger.info("View Not Clickable.");
		}
		try{
			Thread.sleep(2000);
			selenium.click("xpath=//span[text()='Guides']");
		}catch(Exception e){
			captureScreenshot(isViewMenuWorking, "GuideNotClickable");
			debugLogger.info("Guide not clickable");
		}
			
		try {
			Thread.sleep(2000);
			selenium.click("xpath=//span[text()='Center Guides']");
		} catch (InterruptedException e) {
			captureScreenshot(isViewMenuWorking, "CenterGuidesNotClickable");
			debugLogger.info("Center Guid not clickable");
		}
			//enable guides
			try {Thread.sleep(4000);} catch (InterruptedException e1) {e1.printStackTrace();}
			selenium.click("xpath=//div[text()='View']");
			
			try {Thread.sleep(2000);} catch (InterruptedException e1) {e1.printStackTrace();}
			selenium.click("xpath=//span[text()='Guides']");
			
		try {
			Thread.sleep(2000);
			selenium.click("xpath=//span[text()='Edge Guides']");
		} catch (InterruptedException e) {
			captureScreenshot(isViewMenuWorking, "EdgeGuidesNotClickable");
			debugLogger.info("Edge Guide not clickable.");
		}
			
			try {Thread.sleep(4000);} catch (InterruptedException e1) {e1.printStackTrace();}
			selenium.click("xpath=//div[text()='View']");
			
			try {Thread.sleep(2000);} catch (InterruptedException e1) {e1.printStackTrace();}
			selenium.click("xpath=//span[text()='Guides']");
			
			try {
				Thread.sleep(2000);
				selenium.click("xpath=//span[text()='Spacing Guides']");
			} catch (InterruptedException e) {
				captureScreenshot(isViewMenuWorking, "SpacingGuidesNotClickable");
				debugLogger.info("Spacing Guides not clickable.");
			}
		
			//check zoom option
			try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
			selenium.click("xpath=//div[text()='View']");
			
			try {
				Thread.sleep(2000);
				selenium.click("xpath=//*[@class='menu-item has-icon']/span/span");
			} catch (Exception e1) {
				captureScreenshot(isViewMenuWorking, "UnableToClickZoom");
				debugLogger.info("Unable to click on Zoom");
			}
			
			try {
				Thread.sleep(2000);
				selenium.click("xpath=//span[text()='200%']");
			} catch (Exception e) {
				captureScreenshot(isViewMenuWorking, "UnableToZoom200");
				debugLogger.info("Unable to zoom 200%");
			}	
	}

	public boolean downloadInWordContextMenu(String docPath)
	{
		//driver.switchTo().frame("bight");
		boolean downloadSuccessful = false;
		
		try {
			Actions action= new Actions(driver);
			
			if(isElementPresent(By.xpath(docPath)))
			{
				int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
				if (docCount < 1){
					//no document found, please create a new document 
					downloadInWordContextMenu(docPath);
				}
				else{
					String selectFirstDoc_alt = "//div[@class='img-container']";
					//find the name of the document
					String FirstDocName = "//div[@class='document-name']";			
					String docName = driver.findElement(By.xpath(FirstDocName)).getText();		
					driver.findElement(By.xpath(selectFirstDoc_alt)).click();
					
					Thread.sleep(2000);
					
					//send keys (ctrl + enter)
					action.contextClick().sendKeys(Keys.LEFT_ALT).sendKeys(Keys.RETURN);
					action.build().perform();
					Thread.sleep(3000);
					String downloadContextMenu = "/html/body/div[5]/div[2]/div/div/div[1]/a";
					Thread.sleep(3000);
					action.sendKeys(Keys.LEFT_ALT).build().perform();
					if(isElementPresent(By.xpath(downloadContextMenu)))
					{
						driver.findElement(By.xpath(downloadContextMenu)).isEnabled();
						driver.findElement(By.xpath(downloadContextMenu)).isSelected();
						driver.findElement(By.xpath(downloadContextMenu)).click();
							
						Thread.sleep(3000);
					
						String downloadPages = "//div[contains(@class,'type-choice type-choice-2')]";
						
						if(isElementPresent(By.xpath(downloadPages)))
						{
							driver.findElement(By.xpath(downloadPages)).click();
							Thread.sleep(6000);
							
						} else {
							captureScreenshot(downloadSuccessful, "DownloadWordIcon");
							debugLogger.info("Download Word Icon not clickable");
						}
					} 
					else {
							captureScreenshot(downloadSuccessful, "DownloadWordViaContext");
							debugLogger.info("Download Word Icon not present");
					}
				}
				downloadSuccessful = true;
			}
		} 
		catch (Exception e) {
			captureScreenshot(downloadSuccessful, "DownloadViaContext");
			debugLogger.info("Download Word Via Context Failed");
		}
		return downloadSuccessful;
}
	
	public boolean choosePersonalPhotoLetterTheme(){
		
		boolean isPersonalPhotoLetterSelectable = false;
		mainParentHandle = driver.getWindowHandle();
		try{
			Thread.sleep(3000);
				
			//create a new doc via + icon
			int divcount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
			//Get current windows		
			final Set<String> parentWindowHandle = driver.getWindowHandles();
			String parentWindowHandle3 = driver.getWindowHandle();
			try {
				driver.findElement(By.xpath(iCloudConstants.docCount+"["+divcount+iCloudConstants.docCount_end)).click();
			}catch(Exception e){
				captureScreenshot(isPersonalPhotoLetterSelectable, "UnableToSelectThemeOption");
				debugLogger.info("Unable to Select Perosnal Photo Letter Theme Option.");
			}
			
	        WebElement PersonalPhotoLetterTheme = driver.findElement(By.xpath("//img[contains(@src,'PersonalPhotoLetter.')]"));
	        Actions myMouse = new Actions(driver);
	        myMouse.moveToElement(PersonalPhotoLetterTheme).build().perform();
	        
			//look for Personal Photo Letter theme 
			String PersonalPhotoLetterThemePath = "//img[contains(@src,'PersonalPhotoLetter.')]";
			
			if(isElementPresent(By.xpath(PersonalPhotoLetterThemePath)))
			{
				driver.findElement(By.xpath(PersonalPhotoLetterThemePath)).isSelected();
				driver.findElement(By.xpath(PersonalPhotoLetterThemePath)).click();
				driver.findElement(By.xpath(PersonalPhotoLetterThemePath)).click();
				//selenium.doubleClick(PersonalPhotoLetterThemePath);
			
				//create "Personal Photo Letter" doc
				Thread.sleep(3000);
				if(isElementPresent(By.xpath(iCloudConstants.templateChooseButton)))
				{
					driver.findElement(By.xpath(iCloudConstants.templateChooseButton)).click(); //click action that cause new window to open 
					//selenium.doubleClick(iCloudConstants.templateChooseButton);
				}
				Thread.sleep(12000);
				
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Personal Photo Letter Theme selected : PASSED");
			}
			else
			{
				debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": PERSONAL PHOTO LETTER THEME not found :  ******* FAILED *******");
				captureScreenshot(isPersonalPhotoLetterSelectable, "UnableToSelectPersonalPhotoTheme");
				
				//Get current window handles
				Set<String> cHandle = driver.getWindowHandles();
				int windowHandleCount = cHandle.size();
				//remove all before handles from after.  Leaves you with new window handle
				cHandle.removeAll(parentWindowHandle);		
				//Switch to the new window
				String newWindowHandle = cHandle.iterator().next();
				driver.switchTo().window(newWindowHandle);
				//save and close
				driver.switchTo().window(newWindowHandle).close();
			}
			
			//Get current window handles
			Set<String> cHandle = driver.getWindowHandles();
			int windowHandleCount = cHandle.size();
			//remove all before handles from after.  Leaves you with new window handle
			cHandle.removeAll(parentWindowHandle);		
			//Switch to the new window
			String newWindowHandle = cHandle.iterator().next();
			driver.switchTo().window(newWindowHandle);
			
			//open another browser window and non-supported browser pop-up shows up, close that one
			if(isElementPresent(By.xpath(iCloudConstants.nonSupportedBrowserPopup)))
			{
				driver.findElement(By.xpath(iCloudConstants.nonSupportedBrowserPopup)).click();
			}
			driver.switchTo().window(newWindowHandle);

			Thread.sleep(5000);
			//Edit manipulation of a Personal Photo Letter
			editFontStyle();
			
			Thread.sleep(4000);
			Shapes();
			Thread.sleep(5000);
			generalUtility.bringTheFocusToCentreOfScreen();
			
			Thread.sleep(6000);
			driver.close();
			Thread.sleep(5000);
			driver.switchTo().window(mainParentHandle);
			Thread.sleep(5000);	
			//driver.switchTo().frame("pages");
			isPersonalPhotoLetterSelectable = true;
		}
		catch(Exception e){
			captureScreenshot(isPersonalPhotoLetterSelectable, "ChoosePersonalPhotoLetter");
			debugLogger.info("Choosing Personal Photo Letter Theme Failed.");
		}
		return isPersonalPhotoLetterSelectable;
	}
		
	public void editFontStyle(){
		boolean isEditFontStyle = false;
		try {
			Actions aObject = new Actions(driver);
			
			try {
				Thread.sleep(2000);
				aObject.sendKeys("Test_02").build().perform();
				Thread.sleep(4000);
				//aObject.sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).
				//sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).perform();
				aObject.sendKeys(Keys.DELETE).perform();
				Thread.sleep(4000);
				aObject.sendKeys(Keys.DOWN).sendKeys(Keys.RIGHT).perform();
				Thread.sleep(4000);
				//aObject.sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).
				//sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).perform();
				aObject.sendKeys(Keys.DELETE).perform();
			} catch (Exception e) {
				captureScreenshot(isEditFontStyle, "UnableToAddAuthor");
				debugLogger.info("Unable to Add Author");
			}
			
			/*try {
				Thread.sleep(4000);
				aObject.sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).
				sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).perform();
			} catch (Exception e1) {
				e1.printStackTrace();
				isEditFontStyle = false;
				captureScreenshot(isEditFontStyle, "UnableToDeleteAuthor");
			}
			
			try {
				Thread.sleep(4000);
				aObject.sendKeys(Keys.DOWN).sendKeys(Keys.RIGHT).perform();
			} catch (Exception e1) {
				e1.printStackTrace();
				isEditFontStyle = false;
				captureScreenshot(isEditFontStyle, "UnableToNavigateToRecepient");
			}
			
			try {
				Thread.sleep(4000);
				aObject.sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).
				sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).perform();
			} catch (Exception e1) {
				e1.printStackTrace();
				isEditFontStyle = false;
				captureScreenshot(isEditFontStyle, "UnableToDeleteRecepient");
			}*/
			
			try {
				Thread.sleep(4000);
				aObject.sendKeys("Internal User").perform();
				Thread.sleep(4000);
				//aObject.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.RIGHT).perform();
				aObject.sendKeys(Keys.DOWN).perform();
				//aObject.sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).perform();
				aObject.sendKeys(Keys.RIGHT).perform();
				//aObject.sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).perform();
				aObject.sendKeys(Keys.DELETE).perform();
			} catch (Exception e) {
				captureScreenshot(isEditFontStyle, "UnableToAddNewRecepient");
				debugLogger.info("Unable to add new recepient");
			}
			
			/*try {
				Thread.sleep(4000);
				aObject.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.RIGHT).perform();
				aObject.sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).perform();
				aObject.sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).sendKeys(Keys.DELETE).perform();
			} catch (Exception e1) {
				e1.printStackTrace();
				isEditFontStyle = false;
				captureScreenshot(isEditFontStyle, "UnableToNavigateAfterSalutation");
			}*/
			
			try {
				Thread.sleep(4000);
				aObject.sendKeys("Internal User").perform();
				Thread.sleep(4000);
				//aObject.sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).perform();
				aObject.sendKeys(Keys.RIGHT).perform();
				Thread.sleep(3000);
				//aObject.sendKeys(Keys.SHIFT).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).
				//sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).perform();
				aObject.sendKeys(Keys.SHIFT).sendKeys(Keys.DOWN).perform();
			} catch (Exception e1) {
				captureScreenshot(isEditFontStyle, "UnableToEnterRecepient");
				debugLogger.info("Unable to enter recepeint");
			}
			
			/*try {
				Thread.sleep(4000);
				aObject.sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).perform();
				Thread.sleep(3000);
				aObject.sendKeys(Keys.SHIFT).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).
				sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).perform();
				
			} catch (Exception e1) {
				e1.printStackTrace();
				isEditFontStyle = false;
				captureScreenshot(isEditFontStyle, "UnableToHighlightFirstPara");
			}*/
			
			try {
				Thread.sleep(4000);
				selenium.click("xpath=html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[1]/div[1]/div[3]");
				Thread.sleep(4000);
				selenium.click("xpath=html/body/div[6]/div[2]/div[2]/div/div[20]/a");
				aObject.sendKeys(Keys.SHIFT).perform();
				
			} catch (Exception e) {
				captureScreenshot(isEditFontStyle, "UnableToClickOnFontType");
				debugLogger.info("Unable to click on font type.");
			}
			
			/*try {
				Thread.sleep(4000);
				selenium.click("xpath=html/body/div[6]/div[2]/div[2]/div/div[20]/a");
			} catch (Exception e1) {
				e1.printStackTrace();
				isEditFontStyle = false;
				captureScreenshot(isEditFontStyle, "UnableToSelectCourierFont");
			}
			
			aObject.sendKeys(Keys.SHIFT).perform();*/
			
			try {
				Thread.sleep(4000);
				aObject.sendKeys(Keys.RIGHT).sendKeys(Keys.RIGHT).perform();
				Thread.sleep(2000);
				selenium.click("//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme popup sc-view sc-button-view sc-select-view button sc-regular-size']/label");
				Thread.sleep(4000);
				aObject.sendKeys(Keys.DOWN).build().perform();
				Thread.sleep(3000);
				//aObject.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();
				aObject.sendKeys(Keys.DOWN).build().perform();
				aObject.sendKeys(Keys.RETURN).build().perform();
			} catch (Exception e) {
				captureScreenshot(isEditFontStyle, "UnableToClickListStyle");
				debugLogger.info("Unable to click on List Style");
			}
			
			/*try {
				Thread.sleep(4000);
				aObject.sendKeys(Keys.DOWN).build().perform();
				Thread.sleep(3000);
				aObject.sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).sendKeys(Keys.DOWN).build().perform();
				aObject.sendKeys(Keys.RETURN).build().perform();
			} catch (Exception e1) {
				e1.printStackTrace();
				isEditFontStyle = false;
				captureScreenshot(isEditFontStyle, "UnableToSelectLetteredOption");
			}*/
			isEditFontStyle = true;
		} catch (Exception e) {
			captureScreenshot(isEditFontStyle, "EditFontStyleUnsuccessful");
			debugLogger.info("Edit Font Style Unsuccessful");
		}
	}

	public void captureScreenshot(boolean result, String name) {
		 
	        if (!result) {
	            try {
	            	SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
	                WebDriver returned = new Augmenter().augment(driver);
	                if (returned != null) {
	                	File f = ((TakesScreenshot) returned)
	                            .getScreenshotAs(OutputType.FILE);
	                    try {
	                    	FileUtils.copyFile(f, new File(SCREENSHOT_FOLDER
	                                + name+"_"+formater.format(Calendar.getInstance().getTime()) + SCREENSHOT_FORMAT));
	                        } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            } catch (ScreenshotException se) {
	                se.printStackTrace();
	            }
	        }
	    }
	 
	public void EditBlankDocAddTextBox(){
			
		boolean isEditTextBox = false;
		String fontTypeDropDown = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme popup sc-view sc-button-view sc-select-view iw-font-family-select-view button sc-regular-size icon']/label";
		String fontFutura = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view']/div[24]";
		String sizeChange = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view iw-text-spinner-view']";
		
		 	try {
				Actions aObject = new Actions(driver);
 
				// clicking the text box icon starts
				try {
					Thread.sleep(2000);
					selenium.click("xpath=//div[text()='Text Box']");
				} catch (Exception e) {
					captureScreenshot(isEditTextBox, "TextBoxTabNotPresent");
					debugLogger.info("Unable to click on Text Box icon.");
				}
				
				/*try {
					Thread.sleep(2000);
					driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/div[3]/div/div[2]/div/div[2]/div/div/div")).isSelected();
					
					Thread.sleep(2000);
					driver.findElement(By.xpath("//*[@class=\"layer layer-svg layer-svg-selection_highlight\"]")).click();
				} catch (Exception e) {
					e.printStackTrace();
					isEditTextBox = false;
					captureScreenshot(isEditTextBox, "ClickInsideTextBoxFailed");
				}*/
				// clicking inside the text box ends************
				
				// entering text inside the text box starts
				try {
					//Thread.sleep(2000);
					//driver.findElement(By.xpath("//*[@class=\"layer layer-svg layer-svg-selection_highlight\"]")).click();
					Thread.sleep(2000);
					aObject.sendKeys("Font Example").perform();
				} catch (Exception e) {
					captureScreenshot(isEditTextBox, "TypingInsideTextBoxFailed");
					debugLogger.info("Typing inside the text box failed.");
				}
				// entering text inside the text box ends***********
				
				// selecting all inside the text box starts
				try {
					Thread.sleep(2000);
					aObject.sendKeys("\uE03D").sendKeys("a").perform();
					Thread.sleep(3000);
				} catch (Exception e) {
					captureScreenshot(isEditTextBox, "SelectingAllInsideTextBoxFailed");
					debugLogger.info("Selecting all inside the text box failed.");
				}
				
				// selecting all inside the text box ends
				
				// selecting the font type starts
				
				try {
					Thread.sleep(3000);
					selenium.click(fontTypeDropDown);
					Thread.sleep(3000);
					selenium.click(fontFutura);
				} catch (Exception e1) {
					captureScreenshot(isEditTextBox, "FontTypeSelectFailed");
					debugLogger.info("Selecting FUTURA font style failed.");
				}
				// selecting the font type ends
				
				// selecting the font size starts
				try {
					Thread.sleep(3000);
					
					List<WebElement> myFields = driver.findElements(By.xpath(sizeChange));
				    
					for(int i=0;i<=myFields.size();i++){
						if(i==0){
				    	    String optionId = myFields.get(i).getAttribute("id");
				    	    String upId = "//*[@id='"+optionId+"']/div[1]/div[1]";
				    	    Thread.sleep(2000);
				    	   
				    	    selenium.doubleClick(upId);
				    	   
							Thread.sleep(4000);
						}
						
					}
				} catch (Exception e) {
				captureScreenshot(isEditTextBox, "FontSizeSelectFailed");
				debugLogger.info("Selecting font size failed.");
				}
				isEditTextBox = true;
			} catch (Exception e) {
				captureScreenshot(isEditTextBox, "EditTextBoxUnsuccessful");
				debugLogger.info("Editing text box was unsuccessful");
			}
			
		}
	 
	public void EditDocFormat(){
			
		boolean isEditDocFormatSucessfull = false;
		
		try {
			// before disabling the format options
			try{
				// clicking on shapes
				selenium.focus("class=img format-icon");
				Thread.sleep(2000);
				selenium.click("xpath=//div[text()='Format']");
			}
			catch(Exception e){
				selenium.close();
				captureScreenshot(isEditDocFormatSucessfull, "DisableFormatFailed");
				debugLogger.info("Format menu is not selectable while disabling ***** ");
			}
			
			try{
				// clicking on shapes
				selenium.focus("class=img format-icon");
				Thread.sleep(2000);
				selenium.click("xpath=//div[text()='Format']");
			}
			catch(Exception e){
				selenium.close();
				captureScreenshot(isEditDocFormatSucessfull, "EnableFormatFailed");
				debugLogger.info("Format menu is not selectable while enabling ***** ");
			}
			isEditDocFormatSucessfull = true;
		} catch (Exception e) {;
			captureScreenshot(isEditDocFormatSucessfull, "isEditDocFormatUnSucessfull");
			debugLogger.info("Editing Document Format Unsuccessful");
		}
	}
		
	public void Shapes(){
			
		boolean isShapesTabFunctional = false;
		//String shapeInputBox = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view sc-text-field-view not-empty sc-regular-size']";
		String shapeInputBox = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view iw-text-spinner-view']";
		
		try {
			try{
				// clicking on shapes
				selenium.focus("class=img insert-shape-icon");
				Thread.sleep(2000);
				selenium.click("xpath=//div[text()='Shapes']");
			}
			catch(Exception e){
				selenium.close();
				captureScreenshot(isShapesTabFunctional, "ClickShapesIconFailed");
				debugLogger.info("Shapes menu is not selectable or pop over menu did not appear to allow to select star ***** ");
			}
			
			try {
				Thread.sleep(2000);
				selenium.click("xpath=html/body/div[6]/div[3]/div[15]/div[2]");		// selecting the star
			} catch (Exception e1) {
				captureScreenshot(isShapesTabFunctional, "SelectStarShapeFailed");
				debugLogger.info("Selecting Start Shape Failed.");
			}
			
			
			try {
				Thread.sleep(2000);
				selenium.click("xpath=//label[text()='Arrange']");
			} catch (Exception e1) {
				captureScreenshot(isShapesTabFunctional, "ArrangeTabSelectFailed");
				debugLogger.info("Unable to select Arrange Tab");
			}
			
			// moving the shape next to the image
			try{
				Thread.sleep(3000);
				
				List<WebElement> myFields = driver.findElements(By.xpath(shapeInputBox));
				
				for(int i=0;i<=myFields.size();i++){
					if(i==0){
			    	    String optionId = myFields.get(i).getAttribute("id");
			    	    String inputId = "//*[@id='"+optionId+"']/div[2]/div[2]/input";
			    	    Thread.sleep(2000);
			    	   
			    	    selenium.focus(inputId);
			    	    driver.findElement(By.xpath(inputId)).sendKeys("80 px");
			    	   
						Thread.sleep(3000);
					}
					if(i==1){
			    	    String optionId = myFields.get(i).getAttribute("id");
			    	    String inputId = "//*[@id='"+optionId+"']/div[2]/div[2]/input";
			    	    Thread.sleep(2000);
			    	   
			    	    selenium.focus(inputId);
			    	    driver.findElement(By.xpath(inputId)).sendKeys("50 px");
			    	   
						Thread.sleep(3000);
					}
				}
			}
			catch(Exception e2){
				captureScreenshot(isShapesTabFunctional, "ShapeReLocFailed");
				debugLogger.info("Shape move does not work*****");
			}
			// resizing the shape
			try{
				Thread.sleep(3000);
				
				List<WebElement> myFields = driver.findElements(By.xpath(shapeInputBox));
			    
				for(int i=0;i<=myFields.size();i++){
					if(i==2){
			    	    String optionId = myFields.get(i).getAttribute("id");
			    	    String inputId = "//*[@id='"+optionId+"']/div[2]/div[2]/input";
			    	    Thread.sleep(2000);
			    	   
			    	    selenium.focus(inputId);
			    	    driver.findElement(By.xpath(inputId)).sendKeys("400 px");
			    	   
						Thread.sleep(3000);
					}
					if(i==3){
			    	    String optionId = myFields.get(i).getAttribute("id");
			    	    String inputId = "//*[@id='"+optionId+"']/div[2]/div[2]/input";
			    	    Thread.sleep(2000);
			    	   
			    	    selenium.focus(inputId);
			    	    driver.findElement(By.xpath(inputId)).sendKeys("20 px");
			    	    driver.findElement(By.xpath(inputId)).sendKeys(Keys.RETURN);
			    	   
						Thread.sleep(3000);
					}
				}
				
			}
			catch(Exception e3){
				captureScreenshot(isShapesTabFunctional, "ShapeReSizeFailed");
				debugLogger.info("Shape resize does not work*****");
			}
			isShapesTabFunctional = true;
		} catch (Exception e) {
			captureScreenshot(isShapesTabFunctional, "ShapesTabNotFunctional");
			debugLogger.info("Shapes Tab Function Failed.");
		}
		
		// switching to the document Window
		Set<String> lHandle = driver.getWindowHandles();
		lHandle.remove(mainParentHandle);
		String theWindow = lHandle.toString();
		//removing brackets from the string "theWindow"
		String string_one = theWindow.substring(0, theWindow.length()-1);
		String myDocWindow = string_one.substring(1,string_one.length());
		driver.switchTo().window(myDocWindow);
	}
		
	public void helpMenu(){
		boolean isHelpFunctional = false;
		try {
			final Set<String> beforeHandlesA = driver.getWindowHandles();
			String parentWindowHandle2 = driver.getWindowHandle();
			try{
				Thread.sleep(3000);
				selenium.click("xpath=//div[text()='Tools']");
			}
			catch(Exception e){
				captureScreenshot(isHelpFunctional, "ToolsIconNotClickable");
				debugLogger.info("Tools Icon not Clickable.");
			}
			try{
				Thread.sleep(2000);
				selenium.click("xpath=//span[text()='Help']");
			}catch(Exception e){
				captureScreenshot(isHelpFunctional, "HelpIconNotClickable");
				debugLogger.info("Help Icon not clickable.");
			}
			try{
				Thread.sleep(3000);
				
				Set<String> cHandle1 = driver.getWindowHandles();
				int windowHandleCount1 = cHandle1.size();
				cHandle1.removeAll(beforeHandlesA);
				String newWindowHandle1 = cHandle1.iterator().next();
				driver.switchTo().window(newWindowHandle1);
				Thread.sleep(2000);
				driver.close();
				Thread.sleep(4000);
				
				// switching to the document Window
				Set<String> lHandle = driver.getWindowHandles();
				lHandle.remove(mainParentHandle);
				String theWindow = lHandle.toString();
				//removing brackets from the string "theWindow"
				String string_one = theWindow.substring(0, theWindow.length()-1);
				String myDocWindow = string_one.substring(1,string_one.length());
				driver.switchTo().window(myDocWindow);
				
				//driver.switchTo().defaultContent();
				//driver.switchTo().window(beforeHandlesA.toString());
				//driver.switchTo().window(parentWindowHandle2);
				Thread.sleep(2000);
				isHelpFunctional = true;
				
			}catch(Exception e){
				captureScreenshot(isHelpFunctional, "HelpWindowNotClosing");
				debugLogger.info("Help Window not closing.");
			}
			isHelpFunctional = true;
		} catch (Exception e) {
			captureScreenshot(isHelpFunctional, "HelpFailed");
			debugLogger.info("Help action failed.");
		}
	}
	
	public void find(){
		
		boolean isFindFunctional = false;
		String gearFind = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view sc-menu-item']";
		
		try {
			try{
				Thread.sleep(3000);
				selenium.click("xpath=//div[text()='Tools']");
			}
			catch(Exception e){
				captureScreenshot(isFindFunctional, "ToolsIconNotClickable");
				debugLogger.info("Tools Icon not clickable");
			}
			
			try{
				Thread.sleep(2000);
				selenium.click("xpath=//span[text()='Find']");
			}catch(Exception e){
				captureScreenshot(isFindFunctional, "FindIconNotClickable");
				debugLogger.info("Find Icon not clickable");
			}
			
			try {
				Thread.sleep(2000);
				if(isElementPresent(By.xpath("//*[@aria-label='Find']"))){
					driver.findElement(By.xpath("//*[@aria-label='Find']")).sendKeys("the");
					Thread.sleep(2000);
					driver.findElement(By.xpath("//label[text()='Done']")).click();
				}
			} catch (InterruptedException e) {
				captureScreenshot(isFindFunctional, "KeywordSearchFailed");
				debugLogger.info("Keyword search failed.");
			}
			
			try {
				Thread.sleep(3000);
				selenium.click("xpath=//div[text()='Tools']");
				
				Thread.sleep(2000);
				selenium.click("xpath=//span[text()='Find']");
				
				Thread.sleep(2000);
				if(isElementPresent(By.xpath("//*[@class='img options-button']"))){
					driver.findElement(By.xpath("//*[@class='img options-button']")).click();
					
					Thread.sleep(3000);
					
					List<WebElement> myFields = driver.findElements(By.xpath(gearFind));
					
					for(int i=0;i<=myFields.size();i++){
						if(i==1){
				    	    String optionId = myFields.get(i).getAttribute("id");
				    	    String inputId = "//*[@id='"+optionId+"']/a/span";
				    	    Thread.sleep(2000);
				    	   
				    	    selenium.click(inputId);
							Thread.sleep(3000);
						}
					}
					driver.findElement(By.xpath("//*[@aria-label='Find']")).sendKeys("the");
					Thread.sleep(3000);
					driver.findElement(By.xpath("//*[@aria-label='Replace']")).sendKeys("yes");
					Thread.sleep(3000);
					if(isElementPresent(By.xpath("//label[text()='Replace']"))){
						for(int i=0;i<11;i++){
							selenium.click("//label[text()='Replace']");
						}
					}
					Thread.sleep(2000);
					driver.findElement(By.xpath("//label[text()='Done']")).click();
				}
			} catch (InterruptedException e) {
				captureScreenshot(isFindFunctional, "FindReplaceFailed");
				debugLogger.info("Find and Repalace failed.");
			}
			
			isFindFunctional = true;
			
		} catch (Exception e) {
			captureScreenshot(isFindFunctional, "FindScenarioFailed");
			debugLogger.info("Find Scenario Failed.");
		}
		
		
	}
	//deepak addition for demo.
public boolean saveDocument(){
		
		boolean isSaveSuccessful = false;

		/*//Get current window handles
		Set<String> cHandle = driver.getWindowHandles();
		int windowHandleCount = cHandle.size();
		//remove all before handles from after.  Leaves you with new window handle
		cHandle.removeAll(currentWindowHandle);		
		//Switch to the new window
		String newWindowHandle = cHandle.iterator().next();
		driver.switchTo().window(newWindowHandle);*/

		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		
		try {
			/*//driver.switchTo().window(newWindowHandle);
			driver.switchTo().window(newWindowHandle).close();
			
			Thread.sleep(4000);

			driver.switchTo().window(currentWindowHandle.toString());
			//driver.close();
*/			driver.close();
			isSaveSuccessful = true;
		}catch(Exception e){
			
			//testResultsLogger.info("Not able to close the current window");
			captureScreenshot(isSaveSuccessful, "CloseCurrentWindowFailed");
		}

		return isSaveSuccessful;
	}

public boolean deleteAllDoc(){
	
	
	System.out.println("3 : ");
	boolean isDeleteAll = false;
	Actions action= new Actions(driver);
	String openFirstDoc = "xpath=//*[@class='preview clickable preview-img select-border']";
	
	try {
		tempRobot = new Robot();
		Thread.sleep(3000);
		int docCount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
		System.out.println("4 count = "+docCount);
		//driver.switchTo().frame("pages");
		if(docCount>1){
			
			/*System.out.println("5");
			String selectFirstDoc_alt = "//div[@class='img-container']";
			//String selectFirstDoc_alt = "/html/body/div[2]/div[3]/div[2]/div/div[4]/div/div[2]/div/div";
			
			driver.findElement(By.xpath(selectFirstDoc_alt)).click();
			System.out.println("6");
			Thread.sleep(2000);
			action.sendKeys("\uE03D").sendKeys("a").perform();

			
			//send keys (ctrl + enter)
			WebElement firstDoc = driver.findElement(By.xpath(selectFirstDoc_alt));
			action.contextClick(firstDoc).sendKeys(Keys.LEFT_CONTROL).sendKeys(Keys.RETURN).perform();
			Thread.sleep(3000);
			//action.build().perform();
			action.release().sendKeys(Keys.NULL).perform();
			Thread.sleep(3000);*/
			
			System.out.println("5");
			selenium.click(openFirstDoc);
			Thread.sleep(2000);
			//driver.findElement(By.xpath(openFirstDoc)).sendKeys("\uE03D");
			//driver.findElement(By.xpath(openFirstDoc)).sendKeys(Keys.chord("\uE03D", "a"));
			//selenium.keyPressNative(Keys.chord(Keys.COMMAND, "a"));
			tempRobot.keyPress(KeyEvent.VK_META);
			tempRobot.keyPress(KeyEvent.VK_A);
			Thread.sleep(3000);
			tempRobot.keyRelease(KeyEvent.VK_A);
			tempRobot.keyRelease(KeyEvent.VK_META);
			Thread.sleep(3000);
			
			
			
	/*		tempRobot.keyPress(KeyEvent.VK_CONTROL);
			
			//selenium.click(openFirstDoc);
			selenium.mouseDownRight(openFirstDoc);
			Thread.sleep(3000);
			selenium.mouseUpRight(openFirstDoc);
			tempRobot.keyRelease(KeyEvent.VK_CONTROL);
	*/		
			
							
			String deleteContextMenu = "/html/body/div[5]/div[2]/div/div/div[3]/a";
			
			if(isElementPresent(By.xpath(deleteContextMenu)))
			{
				System.out.println("7");
				driver.findElement(By.xpath(deleteContextMenu)).click();
					
				Thread.sleep(3000);
				
				String deleteButtonConfirmation = "//label[text()='Delete']";
				if(isElementPresent(By.xpath(deleteButtonConfirmation))){
					driver.findElement(By.xpath(deleteButtonConfirmation)).isSelected();
					driver.findElement(By.xpath(deleteButtonConfirmation)).click();
					Thread.sleep(7000);
				}else {
					captureScreenshot(isDeleteAll, "DeleteConfSelectFailed");
					debugLogger.info("Unable to confirm Delete by clicking on the button");
				}
				
			} else {
				captureScreenshot(isDeleteAll, "UnableToSelectDelete");
				debugLogger.info("Unable to select delete option");
			}	
			isDeleteAll = true;
		}
		else{
			debugLogger.info("There are no existing documents to delete.");
		}
	} catch (Exception e) {
		captureScreenshot(isDeleteAll, "DeleteAllExistingFailed");
		debugLogger.info("Delete all existing documents failed.");
	}
	return isDeleteAll;
}

//@Ignore
@Test
public void testRunAllScenario()
{
	testScenario03();
	try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}
	testScenario04();
	try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}
	testScenario05();
	//try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}
	//testScenario06();
	
	try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}
	testScenario07();
}
//upload
private void testScenario07()
{
	try
	{
	    Thread.sleep(5000);
	    uploadPages("test_01.pages");
		testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Upload : PASSED");
		Thread.sleep(13000);
	    		    
	} catch (Exception e) {
		testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Upload : ########## FAILED ##########");
		debugLogger.debug("OS: " +operatingSystem+" Browser: " +browserType+ ": Upload : ########## FAILED ##########");
		debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Upload : ########## FAILED ##########");
		fail("OS: " +operatingSystem+" Browser: " +browserType+ ": Upload : ########## FAILED ##########");
	}
}

private void testScenario06()
{
	try
	{
	    Thread.sleep(5000);
	    downloadInPDFContextMenu(iCloudConstants.docSelection);
		testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (in pdf via gear menu) : PASSED");
		
	    Thread.sleep(13000);
	    		    
	} catch (Exception e) {
		testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (in pdf via context menu) : ########## FAILED ##########");
		debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (in pdf via context menu) : ########## FAILED ##########");
		debugLogger.debug("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (in pdf via context menu) : ########## FAILED ##########");
		fail("OS: " +operatingSystem+" Browser: " +browserType+ ": Download (in pdf via context menu) : ########## FAILED ##########");
	}
}

private void imageAddAndEdit(){


	try {Thread.sleep(9000);} catch (InterruptedException e) {e.printStackTrace();}
	Actions action = new Actions(driver);
	boolean isImageEditPossible = false;
	try{				
			Thread.sleep(2000);
			//String imageOndocLink = "//*[@class='layer layer-svg layer-svg-selection_highlight' and @width='589' and @height='371']";
			String imageOndocLink = "//*[@width='475' and @height='310' and @x='0']";
			
			String imageIcon = "//div[@class='iw-toolbar-button-label' and text()='Image']";		
			String imageTab = "//div[@class='sc-button-label sc-regular-size ellipsis' and text()='Image']";
			String borderCheckbox ="//div[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view sc-button-view sc-checkbox-view sc-checkbox-control sc-static-layout checkbox sc-regular-size']/span[@class='label sc-regular-size' and text()='Border']";				
			String bordertypeDropdown = "//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme popup sc-view sc-button-view sc-select-view button sc-regular-size']";
			String borderPicFrame ="//span[contains(text(),'Picture')]";
			//String borderDropDown = "//div[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme popup sc-view sc-button-view sc-select-view stroke-button button sc-regular-size icon']";
			String borderPicFrameTypeDropdown =".//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme iw-picture-frame sc-view sc-button-view button sc-regular-size icon']";
			String borderPicFrameType ="//div[@class='icon img' and contains(@style,'Hardcover')]";				
			String shadowCheckbox ="//div[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view sc-button-view sc-checkbox-view sc-checkbox-control sc-static-layout checkbox sc-regular-size']/span[@class='label sc-regular-size' and text()='Shadow']";
			String reflectionCheckbox ="//div[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view sc-button-view sc-checkbox-view sc-checkbox-control sc-static-layout checkbox sc-regular-size']/span[@class='label sc-regular-size' and text()='Reflection']";
			String imageEditInputbox ="//*[@class='gilligan-atv3 gilligan-atv3-extra iwork-theme sc-view sc-text-field-view not-empty sc-regular-size']/div[2]/input";
			String chooseImageButton ="//label[text()='Choose Image']";
			String opacityInputbox = "//input";
			String reflectionInputbox = "//input";
			String textImageText = "//*[contains(@style, 'font-size: 40px;')]";
			
			
			Thread.sleep(1000);
			
			if(isElementPresent(By.xpath(imageOndocLink)))
			{			
				WebElement imageOndocElementLink = driver.findElement(By.xpath(imageOndocLink));
				Thread.sleep(1000);
				
				/*double clicking on the image and delete mask
				 Not implementing the double click just yet.
				action.doubleClick(imageOndocElementLink).perform();
				Thread.sleep(1000);
				//action.contextClick().sendKeys(Keys.DELETE).build().perform();
				action.click().sendKeys(Keys.DELETE).perform();
				action.release(imageOndocElementLink).perform();*/
				
				Thread.sleep(2000);
				//select the image
				driver.findElement(By.xpath(imageOndocLink)).click();
				
				//selenium.focus("class=sc-button-label sc-regular-size ellipsis");
				//try {Thread.sleep(2000l);} catch (InterruptedException e) {e.printStackTrace();}
				
				//re-size image
				Thread.sleep(2000);
				//selenium.click("xpath=//label[text()='Arrange']");
				if(isElementPresent(By.xpath("//label[text()='Arrange']")))
				{
					driver.findElement(By.xpath("//label[text()='Arrange']")).click();
				} else {
					debugLogger.info("unable to find arrange tab");
					captureScreenshot(isImageEditPossible, "ArrangeTabNotFound");
				}
				
				// re-size image
				try{
					//change size
					Thread.sleep(2000);
					selenium.focus("xpath=html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input");
					//driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input")).isSelected();
					driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[2]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input")).sendKeys("300 px");
					//change position
					Thread.sleep(3000);
					selenium.focus("xpath=html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[3]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input");
					//driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[3]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input")).isSelected();
					driver.findElement(By.xpath("html/body/div[3]/div[1]/div[3]/div[3]/div[2]/div/div[2]/div[3]/div[2]/div/div[3]/div[2]/div/div[1]/div[1]/div[2]/div[2]/input")).sendKeys("40 px");
					Thread.sleep(3000);
					
				}catch(Exception e){
					debugLogger.info("unable to re-size image" + e);
					captureScreenshot(isImageEditPossible, "UnableToResize");
				}
				
				Thread.sleep(2000);
				//selenium.click("xpath=//label[text()='Image']");
				if(isElementPresent(By.xpath("//label[text()='Image']")))
				{
					driver.findElement(By.xpath("//label[text()='Image']")).click();
				} else {
					debugLogger.info("unable to find image tab");
					captureScreenshot(isImageEditPossible, "ImageTabNotFound");
				}
				
				//click on Border type down on side bar
				if(isElementPresent(By.xpath(bordertypeDropdown)))
				{
					driver.findElement(By.xpath(bordertypeDropdown)).click();
					Thread.sleep(2000);
					//click on border type
					if(isElementPresent(By.xpath(borderPicFrame)))
					{
						driver.findElement(By.xpath(borderPicFrame)).click();
						Thread.sleep(3000);
						if(isElementPresent(By.xpath(borderPicFrameTypeDropdown)))
						{
							driver.findElement(By.xpath(borderPicFrameTypeDropdown)).isEnabled();
							driver.findElement(By.xpath(borderPicFrameTypeDropdown)).isSelected();
							driver.findElement(By.xpath(borderPicFrameTypeDropdown)).click();
							Thread.sleep(3000);
							if(isElementPresent(By.xpath(borderPicFrameType)))
							{
								WebElement borderPicFrameTypeElement = driver.findElement(By.xpath(borderPicFrameType));           
					            Actions myMouse = new Actions(driver); 
					            myMouse.moveToElement(borderPicFrameTypeElement).build().perform();
								driver.findElement(By.xpath(borderPicFrameType)).click();
							}
						}
						
					}
					
				}
				
				Thread.sleep(3000);
				//add shadow
				if(isElementPresent(By.xpath(shadowCheckbox)))
				{
					driver.findElement(By.xpath(shadowCheckbox)).click();
				}else{
					debugLogger.info("Shadow not applied");
					captureScreenshot(isImageEditPossible, "ShadowNotApplied");
				}
				
				Thread.sleep(3000);
				//add reflection
				if(isElementPresent(By.xpath(reflectionCheckbox)))
				{
					//checks the reflection box
					driver.findElement(By.xpath(reflectionCheckbox)).click();
					Thread.sleep(2000);
				
					//adds 100% reflection
					List<WebElement> inputs = driver.findElements(By.xpath(reflectionInputbox));
					if (isElementPresent(By.xpath(reflectionInputbox))){
						int i =0;
						for (WebElement option : inputs) {						
							if(i==0 || i==1 || i==2 || i==3 || i==4 || i==5 || i==6 || i==7 || i==8 || i==9){i++;}
							if (i==10){
								String optionid = option.getAttribute("name");
								Thread.sleep(2000);
								String reflectionInputBoxXpath = "//input[@name='" + optionid + "']"; 
								selenium.focus(reflectionInputBoxXpath);
								Thread.sleep(2000);
								driver.findElement(By.xpath(reflectionInputBoxXpath)).sendKeys("100%");
								driver.findElement(By.xpath(reflectionInputBoxXpath)).sendKeys(Keys.ENTER);
								Thread.sleep(4000);
								break;
							}
						}//end for
					} else {
						debugLogger.info("Reflection not applied to 100%");
						captureScreenshot(isImageEditPossible, "OpacityNotAppliedTo100");
					}
					
				}else{
					debugLogger.info("Reflection not applied");
					captureScreenshot(isImageEditPossible, "ReflectionNotApplied");
				}
				
				//Thread.sleep(6000);
				
				//Opacity input to 50%
				//if(isElementPresent(By.xpath(opacityInputbox)))
				//{
					//driver.findElement(By.xpath(opacityInputbox)).sendKeys("50 px");
				//}
				
				//Opacity set to 50%
				List<WebElement> inputs = driver.findElements(By.xpath(opacityInputbox));
				if (isElementPresent(By.xpath(opacityInputbox))){
					int i =0;
					for (WebElement option : inputs) {						
						if(i==0 || i==1 || i==2 || i==3 || i==4 || i==5 || i==6 || i==7 || i==8 || i==9 || i==10){i++;}
						if (i==11){
							String optionid = option.getAttribute("name");
							Thread.sleep(2000);
							String opacityInputBoxXpath = "//input[@name='" + optionid + "']"; 
							selenium.focus(opacityInputBoxXpath);
							Thread.sleep(2000);
							driver.findElement(By.xpath(opacityInputBoxXpath)).sendKeys("82%");
							driver.findElement(By.xpath(opacityInputBoxXpath)).sendKeys(Keys.ENTER);

							
							Thread.sleep(4000);
						}
					}//end for
				} else {
					debugLogger.info("Opacity not applied");
					captureScreenshot(isImageEditPossible, "OpacityNotApplied");
				}
								
				//add a new image
				//action.release(imageOndocElementLink);
				//Image Icon on top menu
				Thread.sleep(6000);
				selenium.focus("class=iw-toolbar-button-label");
				//click on Image on menu bar, add an image
				if(isElementPresent(By.xpath(imageIcon)))
				{
					driver.findElement(By.xpath(imageIcon)).click();
					if(isElementPresent(By.xpath(chooseImageButton)))
					{
						driver.findElement(By.xpath(chooseImageButton)).isSelected();
						Thread.sleep(1000);
						driver.findElement(By.xpath(chooseImageButton)).click();
					}else{
						debugLogger.info("Choose Image button not clickable");
						captureScreenshot(isImageEditPossible, "ImageButtonNotClickable");
					}
					Thread.sleep(2000);
				}
			}else{
				debugLogger.info("No image found on current page");
				captureScreenshot(isImageEditPossible, "NoImageFound");
			}
			
			//Changing Text Below Image
			try{
				Thread.sleep(3000);
				if(isElementPresent(By.xpath(textImageText))){

					WebElement imageTextElement = driver.findElement(By.xpath(textImageText));
					action.sendKeys(imageTextElement, Keys.DELETE).build().perform();
					action.sendKeys(imageTextElement, "One Year Mileage").build().perform();
					Thread.sleep(2000);
				}
				
			}catch(Exception e){
				debugLogger.info("Unable to change text below image" + e);
				captureScreenshot(isImageEditPossible, "UnableToChangeText");
			}
			isImageEditPossible = true;
			
	}catch(Exception e1){
		debugLogger.info("Image menu is not selectable or pop over menu did not show up");
		captureScreenshot(isImageEditPossible, "ImageNotSelectableORMenuNoShow");
		selenium.close();	
	}
	try {Thread.sleep(6000);} catch (InterruptedException e) {e.printStackTrace();}
}

@Ignore
@Test
public void testError(){
	boolean isTestError = false;
	try {
		if(!downloadInPDFContextMenu(iCloudConstants.docSelection)){
			isTestError = false;
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOADING PDF (VIA CONTEXT MENU) : ########## FAILED ##########");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOADING PDF (VIA CONTEXT MENU) : ########## FAILED ##########");
			captureScreenshot(isTestError, "DownloadPDF_Failed");
		}else{
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Downloading PDF (Via Context Menu) : PASSED");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Downloading PDF (Via Context Menu) : PASSED");
			isTestError = true;
			Thread.sleep(6000);	
		}
		
	} 
	catch (Exception e) {
		testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOADING PDF (VIA CONTEXT MENU) : ########## FAILED ##########");
		debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": DOWNLOADING PDF (VIA CONTEXT MENU) : ########## FAILED ##########");
		captureScreenshot(isTestError, "DownloadPDF_Failed");
	}
	try {
		if(!choosePersonalPhotoLetterTheme_Error()){
			isTestError = false;
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING AND EDITING PERSONAL PHOTO LETTER THEME : ########## FAILED ##########");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING AND EDITING PERSONAL PHOTO LETTER THEME : ########## FAILED ##########");
			captureScreenshot(isTestError, "CreateAndEditPersonalPhotoLetterFailed");
		}else{
			testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING AND EDITING PERSONAL PHOTO LETTER THEME : PASSED");
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING AND EDITING PERSONAL PHOTO LETTER THEME : PASSED");
			isTestError = true;
			Thread.sleep(6000);	
		}
		
	} 
	catch (Exception e) {
		testResultsLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING AND EDITING PERSONAL PHOTO LETTER THEME : ########## FAILED ##########");
		debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": CREATING AND EDITING PERSONAL PHOTO LETTER THEME : ########## FAILED ##########");
		captureScreenshot(isTestError, "CreateAndEditPersonalPhotoLetterFailed");
	}
	
}

public boolean choosePersonalPhotoLetterTheme_Error(){
	
	boolean isPersonalPhotoLetterSelectable = false;
	mainParentHandle = driver.getWindowHandle();
	try{
			
		//create a new doc via + icon
		int divcount = driver.findElements(By.xpath(iCloudConstants.docCount)).size();
		//Get current windows		
		final Set<String> parentWindowHandle = driver.getWindowHandles();
		
		try {
			driver.findElement(By.xpath(iCloudConstants.docCount+"["+divcount+iCloudConstants.docCount_end)).click();
		}catch(Exception e){
			captureScreenshot(isPersonalPhotoLetterSelectable, "UnableToSelectThemeOption");
			debugLogger.info("Unable to Select Perosnal Photo Letter Theme Option.");
		}
		
        WebElement PersonalPhotoLetterTheme = driver.findElement(By.xpath("//img[contains(@src,'PersonalPhotoLetter.')]"));
        Actions myMouse = new Actions(driver);
        myMouse.moveToElement(PersonalPhotoLetterTheme).build().perform();
        
		//look for Personal Photo Letter theme 
		String PersonalPhotoLetterThemePath = "//img[contains(@src,'PersonalPhotoLetterTheme.')]"; //appended Theme for failure.
		
		if(isElementPresent(By.xpath(PersonalPhotoLetterThemePath)))
		{
			driver.findElement(By.xpath(PersonalPhotoLetterThemePath)).isSelected();
			driver.findElement(By.xpath(PersonalPhotoLetterThemePath)).click();
			driver.findElement(By.xpath(PersonalPhotoLetterThemePath)).click();
			//selenium.doubleClick(PersonalPhotoLetterThemePath);
		
			//create "Personal Photo Letter" doc
	
			if(isElementPresent(By.xpath(iCloudConstants.templateChooseButton)))
			{
				driver.findElement(By.xpath(iCloudConstants.templateChooseButton)).click(); //click action that cause new window to open 
				//selenium.doubleClick(iCloudConstants.templateChooseButton);
			}
			
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": Personal Photo Letter Theme selected : PASSED");
		}
		else
		{
			debugLogger.info("OS: " +operatingSystem+" Browser: " +browserType+ ": PERSONAL PHOTO LETTER THEME not found :  ******* FAILED *******");
			captureScreenshot(isPersonalPhotoLetterSelectable, "UnableToSelectPersonalPhotoTheme");
			Thread.sleep(5000);
			driver.findElement(By.xpath("//label[text()='Cancel']")).click();
			//Get current window handles
			Set<String> cHandle = driver.getWindowHandles();
			int windowHandleCount = cHandle.size();
			//remove all before handles from after.  Leaves you with new window handle
			cHandle.removeAll(parentWindowHandle);		
			//Switch to the new window
			String newWindowHandle = cHandle.iterator().next();
			driver.switchTo().window(newWindowHandle);
			//save and close
			driver.switchTo().window(newWindowHandle).close();
		}
		
		//Get current window handles
		Set<String> cHandle = driver.getWindowHandles();
		int windowHandleCount = cHandle.size();
		//remove all before handles from after.  Leaves you with new window handle
		cHandle.removeAll(parentWindowHandle);		
		//Switch to the new window
		String newWindowHandle = cHandle.iterator().next();
		driver.switchTo().window(newWindowHandle);
		
		//open another browser window and non-supported browser pop-up shows up, close that one
		if(isElementPresent(By.xpath(iCloudConstants.nonSupportedBrowserPopup)))
		{
			driver.findElement(By.xpath(iCloudConstants.nonSupportedBrowserPopup)).click();
		}
		driver.switchTo().window(newWindowHandle);

		//Edit manipulation of a Personal Photo Letter
		editFontStyle();
		
		Shapes();
		generalUtility.bringTheFocusToCentreOfScreen();
		driver.close();
		driver.switchTo().window(mainParentHandle);
		//driver.switchTo().frame("pages");
		isPersonalPhotoLetterSelectable = true;
	}
	catch(Exception e){
		captureScreenshot(isPersonalPhotoLetterSelectable, "ChoosePersonalPhotoLetterFailed");
		debugLogger.info("Choosing Personal Photo Letter Theme Failed.");
		driver.switchTo().frame("pages");
	}
	return isPersonalPhotoLetterSelectable;
}
	
}
	