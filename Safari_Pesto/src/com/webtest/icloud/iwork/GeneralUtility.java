package com.webtest.icloud.iwork;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;

public class GeneralUtility 
{
	private static Logger testResultsLogger = Logger.getLogger("seleniumTestResultsLogger");
	private static Logger debugLogger = Logger.getLogger("seleniumDebugStatementsLogger");
	private Robot robot = null;
	
	public GeneralUtility()
	{
		try 
		{
			robot = new Robot();
		} 
		catch (AWTException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Dimension getScreenResolution()
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	    int screenWidth = gd.getDisplayMode().getWidth();
	    int screenHeight = gd.getDisplayMode().getHeight();
	    return new Dimension(screenWidth, screenHeight); 
	}
	
	public void deleteAlreadyExistingDownloadedDocument(String downloadedFile) 
	{
		File file = new File(downloadedFile);
		if (file.exists() && file.isFile()) 
		{
			file.delete();
			debugLogger.debug("The old doc to delete in the local folder is " + downloadedFile);
		}
	}
	
	public boolean fileCorruptedCheck(String filename,String minFileSize) 
	{
		File file = new File(filename);
        long filesize = file.length();
        long filesizeInKb = filesize / iCloudConstants.convertFileSizeFromBytesToKb;
        debugLogger.debug("The size of the file " + filename +" in Kb is " +filesizeInKb );
        return (filesizeInKb > Long.parseLong(minFileSize)) ? false : true ;  
	}
	
	public String getFileNameFromFileLocation(String uploadFileLocation) 
	{
		return uploadFileLocation.substring(uploadFileLocation.
				lastIndexOf(File.separator)+1).trim();
	}
	
	public void clearUploadedFile(String FileLocation) 
	{
		File file = new File(FileLocation);
		if (file.exists() && file.isFile()) 
		{
			file.delete();
			debugLogger.debug("The new created document " + FileLocation + " was deleted");
		}	
	}

	public String createFileToUpload(String fileLocation,String fileName) 
	{
		String sorceFile=fileLocation+File.separator+fileName;
		String destFile=fileLocation+File.separator+System.currentTimeMillis()+iCloudConstants.underScore+fileName;
		try 
		{
			FileUtils.copyFile(new File(sorceFile), new File(destFile));
			Thread.sleep(1000);

		} 
		catch (IOException e) 
		{
			testResultsLogger.error(e.getMessage());
		} catch (InterruptedException e) 
		{
			testResultsLogger.error(e.getMessage());
		}
		debugLogger.debug("The  destination file in createFileToUpload() is " + destFile);
		return new File(destFile).getAbsolutePath();
	}
	
	public void setClipboardData(String fileName) 
	{
		StringSelection stringSelection = new StringSelection(fileName);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
	
	public void robotSearchProcess(String operatingSystem) 
	{
		//native key strokes for CTRL, V and ENTER keys
		if(operatingSystem.equals(iCloudConstants.MacOsX))
		{
			// cmd-V on Mac
			robot.keyPress(KeyEvent.VK_META);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_META);
		}
		else
		{
			// Ctrl-V on Win
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public boolean checkDownloadedDocument(String downloadedFile) 
	{
		File file = new File(downloadedFile);
		return (file.exists() && file.isFile()) ? true : false;
	}
	
	public void bringTheFocusToCentreOfScreen() 
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getScreenDevices()[0];
		int screenWidth = gd.getDisplayMode().getWidth();
		int screenHeight = gd.getDisplayMode().getHeight();
		/*
		 * Move the cursor to the middle of the screen and click to bring the
		 * focus to the same message popup
		 */
		robot.mouseMove(screenWidth / 2, screenHeight / 2);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void robotMousePress(int x_coordinate,int y_coordinate)
	{
		debugLogger.debug("Inside robotMousePress() for x co-ordinate"+x_coordinate + " y-coordinate "+y_coordinate);
		robot.mouseMove(x_coordinate,y_coordinate);
		robot.mousePress(InputEvent.BUTTON1_MASK );
		robot.mouseRelease(InputEvent.BUTTON1_MASK );
		//robot.mousePress(InputEvent.BUTTON1_MASK );
		//robot.mouseRelease(InputEvent.BUTTON1_MASK );
	}
	
	public void robotEnterKeyPress()
	{
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		//robot.keyPress(KeyEvent.VK_ENTER);
		//robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void robotControlEnterKeyPress(String operatingSystem) 
	{
		//native key strokes for CTRL and ENTER keys
		if(operatingSystem.equals(iCloudConstants.MacOsX))
		{
			// Ctrl-Enter on Mac
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
		else
		{
			// Ctrl-Enter on Win
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			//robot.keyRelease(KeyEvent.VK_ENTER);
			//robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	public void robotControlEnterKeyRelease(String operatingSystem) 
	{
		//native key strokes for CTRL and ENTER keys
		if(operatingSystem.equals(iCloudConstants.MacOsX))
		{
			// Ctrl-Enter on Mac
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ALT);
		}
		else
		{
			// Ctrl-Enter on Win
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}
	
	public void robotEscKeyPress()
	{
	robot.keyPress(KeyEvent.VK_ESCAPE);
	robot.keyRelease(KeyEvent.VK_ESCAPE);
	}
}
