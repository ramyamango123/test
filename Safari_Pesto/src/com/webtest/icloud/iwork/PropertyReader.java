package com.webtest.icloud.iwork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	
	private static PropertyReader  propertyReader = null;
	private Properties properties = null;
	private Properties xpathProperties = null;
/*	
	private static PropertyReader  propertyReader = null;
	private static Properties properties = null;
	private static Properties xpathProperties = null;
	
	public static PropertyReader getInstance()
	{
		if (propertyReader == null)
		{
			propertyReader = new PropertyReader();
		}
		return propertyReader;
	}

	private PropertyReader getInstance()
	{
		if (propertyReader == null)
		{
			propertyReader = new PropertyReader();
		}
		return propertyReader;
	}
*/
	public static PropertyReader getInstance()
	{
		if (propertyReader == null)
		{
			propertyReader = new PropertyReader();
		}
		return propertyReader;
	}
	public void loadSeleniumPropertiesFile() throws FileNotFoundException, IOException
	{
		properties = new Properties();
		properties.load(new FileInputStream(System.getProperty("selenium.properties")));
	}
	
	public void loadXpathProperties(String browserType) throws FileNotFoundException, IOException
	{
		xpathProperties = new Properties();
		if (iCloudConstants.internetExplorerBrowser.equalsIgnoreCase(browserType))
		  {
			xpathProperties.load(new FileInputStream(System.getProperty("xpath.IE.properties")));
		  }
		  else
		  {
			  xpathProperties.load(new FileInputStream(System.getProperty("xpath.FirefoxandChrome.properties")));
		  }
	}
	
	String getUserId()
	{
		return properties.getProperty("username");
	}

	String getPwd()
	{
		return properties.getProperty("password");
	}
	
	String getOldNameForFolderInPagesTab()
	{
		return properties.getProperty("oldNameForFolderInPagesTab");
	}
	
	String getOldNameForFolderInKeynoteTab()
	{
		return properties.getProperty("oldNameForFolderInKeynoteTab");
	}
	
	String getOldNameForFolderInNumbersTab()
	{
		return properties.getProperty("oldNameForFolderInNumbersTab");
	}
	
	String getNewNameForFolderInPagesTab()
	{
		return properties.getProperty("newNameForFolderInPagesTab");
	}
	
	String getNewNameForFolderInKeynoteTab()
	{
		return properties.getProperty("newNameForFolderInKeynoteTab");
	}
	
	String getNewNameForFolderInNumbersTab()
	{
		return properties.getProperty("newNameForFolderInNumbersTab");
	}
	
	String getExportDocumentNameInPagesTab()
	{
		return properties.getProperty("exportDocumentNameInPagesTab");
	}
	
	String getExportDocumentNameInKeynoteTab()
	{
		return properties.getProperty("exportDocumentNameInKeynoteTab");
	}
	
	String getExportDocumentNameInNumbersTab()
	{
		return properties.getProperty("exportDocumentNameInNumbersTab");
	}
	
	String getDocumentNamesInDeleteFolderInPagesTab()
	{
		return properties.getProperty("documentNamesInDeleteFolderInPagesTab");
	}
	
	String getDocumentNamesInDeleteFolderInKeynoteTab()
	{
		return properties.getProperty("documentNamesInDeleteFolderInKeynoteTab");
	}
	
	String getDocumentNamesInDeleteFolderInNumbersTab()
	{
		return properties.getProperty("documentNamesInDeleteFolderInNumbersTab");
	}
	
	String getMinFileSizeInKbForPdfInKeynoteTab()
	{
		return properties.getProperty("minFileSizeInKbForPdfInKeynoteTab");
	}
	
	String getMinFileSizeInKbForPowerPointInKeynoteTab()
	{
		return properties.getProperty("minFileSizeInKbForPowerPointInKeynoteTab");
	}
	
	String getMinFileSizeInKbForKeynoteInKeynoteTab()
	{
		return properties.getProperty("minFileSizeInKbForKeynoteInKeynoteTab");
	}
	
	String getMinFileSizeInKbForPagesInPagesTab()
	{
		return properties.getProperty("minFileSizeInKbForPagesInPagesTab");
	}
	
	String getMinFileSizeInKbForWordInPagesTab()
	{
		return properties.getProperty("minFileSizeInKbForWordInPagesTab");
	}
	
	String getMinFileSizeInKbForPdfInPagesTab()
	{
		return properties.getProperty("minFileSizeInKbForPdfInPagesTab");
	}
	
	String getMinFileSizeInKbForNumbersInNumbersTab()
	{
		return properties.getProperty("minFileSizeInKbForNumbersInNumbersTab");
	}
	
	String getMinFileSizeInKbForExcelInNumbersTab()
	{
		return properties.getProperty("minFileSizeInKbForExcelInNumbersTab");
	}
	
	String getMinFileSizeInKbForPdfInNumbersTab()
	{
		return properties.getProperty("minFileSizeInKbForPdfInNumbersTab");
	}
	
	String getUploadFilePath()
	{
		return properties.getProperty("fileLocationToUpload");
	}
	
	String getUploadFileName()
	{
		return properties.getProperty("defaultUploadFile");
	}
	
	public String getUrl() 
	{
		return properties.getProperty("url");
	}

	String getTimeOut() 
	{
		return properties.getProperty("timeout");
	}

	String getDownloadedFileInPagesFormatInPagesTab() 
	{
		return properties.getProperty("downloadedFileInPagesFormatInPagesTab");
	}
	
	String getDownloadedFileInPdfFormatInPagesTab() 
	{
		return properties.getProperty("downloadedFileInPdfFormatInPagesTab");
	}
	
	String getDownloadedFileInWordFormatInPagesTab() 
	{
		return properties.getProperty("downloadedFileInWordFormatInPagesTab");
	}

	String getDownloadedFileInKeynoteFormatInKeynoteTab() 
	{
		return properties.getProperty("downloadedFileInKeynoteFormatInKeynoteTab");
	}
	
	String getDownloadedFileInPdfFormatInKeynoteTab() 
	{
		return properties.getProperty("downloadedFileInPdfFormatInKeynoteTab");
	}
	
	String getDownloadedFileInPowerPointFormatInKeynoteTab() 
	{
		return properties.getProperty("downloadedFileInPowerPointFormatInKeynoteTab");
	}

	String getDownloadedFileInNumbersFormatInNumbersTab() 
	{
		return properties.getProperty("downloadedFileInNumbersFormatInNumbersTab");
	}
	
	String getDownloadedFileInPdfFormatInNumbersTab() 
	{
		return properties.getProperty("downloadedFileInPdfFormatInNumbersTab");
	}
	
	String getDownloadedFileInExcelFormatInNumbersTab() 
	{
		return properties.getProperty("downloadedFileInExcelFormatInNumbersTab");
	}
	
	String getUploadDocumentButton()
	{
		return xpathProperties.getProperty("uploadDocumentButton");
	}
	
	String getDeleteOptionClickInPagesTab()
	{
		return xpathProperties.getProperty("deleteOptionClickInPagesTab");
	}
	
	String getDownloadToPagesFormatInPagesTab()
	{
		return xpathProperties.getProperty("downloadToPagesFormatInPagesTab");
	}
	
	String getDownloadToPDFFormatInPagesTab()
	{
		return xpathProperties.getProperty("downloadToPDFFormatInPagesTab");
	}
	
	String getDownloadToWordFormatInPagesTab()
	{
		return xpathProperties.getProperty("downloadToWordFormatInPagesTab");
	}
	
	String getDeleteOptionClickToDeleteDocumentsInDeleteFolderInPagesTab()
	{
		return xpathProperties.getProperty("deleteOptionClickToDeleteDocumentsInDeleteFolderInPagesTab");
	}
	
	String getSavingTypingRenameTextInFolderInPagesTab()
	{
		return xpathProperties.getProperty("savingTypingRenameTextInFolderInPagesTab");
	}
	
	String getSavingTypingRenameTextInFolderDescription()
	{
		return xpathProperties.getProperty("savingTypingRenameTextInFolderDescription");
	}
	
	String getDeleteOptionClickInKeynoteTab()
	{
		return xpathProperties.getProperty("deleteOptionClickInKeynoteTab");
	}
	
	String getDownloadToKeynoteFormatInKeynoteTab()
	{
		return xpathProperties.getProperty("downloadToKeynoteFormatInKeynoteTab");
	}
	
	String getDownloadToPDFFormatInKeynoteTab()
	{
		return xpathProperties.getProperty("downloadToPDFFormatInKeynoteTab");
	}
	
	String getDownloadToPowerPointFormatInKeynoteTab()
	{
		return xpathProperties.getProperty("downloadToPowerPointFormatInKeynoteTab");
	}
	
	String getSavingTypingRenameTextInFolderInKeynoteTab()
	{
		return xpathProperties.getProperty("savingTypingRenameTextInFolderInKeynoteTab");
	}
	
	String getDeleteOptionClickToDeleteDocumentsInDeleteFolderInKeynoteTab()
	{
		return xpathProperties.getProperty("deleteOptionClickToDeleteDocumentsInDeleteFolderInKeynoteTab");
	}
	
	String getDeleteOptionClickInNumbersTab()
	{
		return xpathProperties.getProperty("deleteOptionClickInNumbersTab");
	}
	
	String getDownloadToNumbersFormatInNumbersTab()
	{
		return xpathProperties.getProperty("downloadToNumbersFormatInNumbersTab");
	}
	
	String getDownloadToPdfFormatInNumbersTab()
	{
		return xpathProperties.getProperty("downloadToPdfFormatInNumbersTab");
	}
	
	String getDownloadToExcelFormatInNumbersTab()
	{
		return xpathProperties.getProperty("downloadToExcelFormatInNumbersTab");
	}
	
	String getSavingTypingRenameTextInFolderInNumbersTab()
	{
		return xpathProperties.getProperty("savingTypingRenameTextInFolderInNumbersTab");
	}
	
	String getdeleteOptionClickToDeleteDocumentsInDeleteFolderInNumbersTab()
	{
		return xpathProperties.getProperty("deleteOptionClickToDeleteDocumentsInDeleteFolderInNumbersTab");
	}
	
}
