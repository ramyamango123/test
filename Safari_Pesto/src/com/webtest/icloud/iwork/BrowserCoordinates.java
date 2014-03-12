package com.webtest.icloud.iwork;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BrowserCoordinates
{
	private static Logger debugLogger = Logger.getLogger("seleniumDebugStatementsLogger");
	private static Logger testResultsLogger = Logger.getLogger("seleniumTestResultsLogger");
	
	public static int xCoordianteToClickOnAllowForDownloadingMultipleFiles,yCoordianteToClickOnAllowForDownloadingMultipleFiles;
	public static int xCoordianteForDownloadingInPdfFormat,yCoordianteForDownloadingInPdfFormat;
	public static int xCoordianteForViewIconInDocumentUploadWindow,yCoordianteForViewIconInDocumentUploadWindow;
	public static int xCoordinateOfSearchInDocumentUploadWindow,yCoordinateOfSearchInDocumentUploadWindow;
	public static int xCoordinateOfSearchResultInDocumentUploadWindow,yCoordinateOfSearchResultInDocumentUploadWindow;
	public static int xCoordinateOfSearchInEntireMacForDocumentUpload,yCoordinateOfSearchInEntireMacForDocumentUpload;
	public static int xCoordianteToSaveDocumentInIEBrowser,yCoordianteToSaveDocumentInIEBrowser;
	public static int xCoordinatesClickingTheFileForChromeIE,yCoordinatesClickingTheFileForChromeIE;
	public static int xCoordianteForDownloadingInPdfFormatInWindows,yCoordianteForDownloadingInPdfFormatInWindows;
	public static boolean coordinatesExistsForResolution = false;

	public void parseXmlToSelectCoordinates(String screenResolution,String xmlToParse,String operatingSystem)
	{
		try 
		{
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse (new File(xmlToParse));
            
            // normalize text representation
            doc.getDocumentElement ().normalize ();
            //debugLogger.debug("Root element of the doc is " + doc.getDocumentElement().getNodeName());
            //debugLogger.debug("The screen resolution in parseXmlToSelectCoordinatesForMac() is "+screenResolution);
            //debugLogger.debug("The xml to parse is "+xmlToParse);

            NodeList listOfScreenResolutions = doc.getElementsByTagName("ScreenResolution");
            int totalScreenResolutions = listOfScreenResolutions.getLength();
            debugLogger.debug("No of screen resolutions : " + totalScreenResolutions);

            for(int index=0; index<listOfScreenResolutions.getLength() ; index++)
            {
                Node firstPersonNode = listOfScreenResolutions.item(index);
                if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element firstPersonElement = (Element)firstPersonNode;
                    if (firstPersonElement.getAttribute("dimension").equalsIgnoreCase(screenResolution))
                    {
                    	coordinatesExistsForResolution = true;
                    	
                    	if (operatingSystem.equalsIgnoreCase(iCloudConstants.MacOsX))
                    	{
                    		NodeList xCoordianteForViewIconInDocumentUploadWindowList = firstPersonElement
								.getElementsByTagName("xCoordianteForViewIconInDocumentUploadWindow");
                    		Element xCoordianteForViewIconInDocumentUploadWindowElement = (Element) xCoordianteForViewIconInDocumentUploadWindowList
							.item(0);

                    		NodeList textXCoordianteForViewIconInDocumentUploadWindowList = xCoordianteForViewIconInDocumentUploadWindowElement
								.getChildNodes();
                    		xCoordianteForViewIconInDocumentUploadWindow = Integer
								.parseInt(((Node) textXCoordianteForViewIconInDocumentUploadWindowList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("xCoordianteForViewIconInDocumentUploadWindow chosen is "+ xCoordianteForViewIconInDocumentUploadWindow);

                    		NodeList yCoordianteForViewIconInDocumentUploadWindowList = firstPersonElement
								.getElementsByTagName("yCoordianteForViewIconInDocumentUploadWindow");
                    		Element yCoordianteForViewIconInDocumentUploadWindowElement = (Element) yCoordianteForViewIconInDocumentUploadWindowList
								.item(0);

                    		NodeList textYCoordianteForViewIconInDocumentUploadWindowList = yCoordianteForViewIconInDocumentUploadWindowElement
								.getChildNodes();
                    		yCoordianteForViewIconInDocumentUploadWindow = Integer
								.parseInt(((Node) textYCoordianteForViewIconInDocumentUploadWindowList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("yCoordianteForViewIconInDocumentUploadWindow chosen is "+ yCoordianteForViewIconInDocumentUploadWindow);

                    		NodeList xCoordinateOfSearchInDocumentUploadWindowList = firstPersonElement
								.getElementsByTagName("xCoordinateOfSearchInDocumentUploadWindow");
                    		Element xCoordinateOfSearchInDocumentUploadWindowElement = (Element) xCoordinateOfSearchInDocumentUploadWindowList
								.item(0);

                    		NodeList textXCoordinateOfSearchInDocumentUploadWindowList = xCoordinateOfSearchInDocumentUploadWindowElement
								.getChildNodes();
                    		xCoordinateOfSearchInDocumentUploadWindow = Integer
								.parseInt(((Node) textXCoordinateOfSearchInDocumentUploadWindowList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("xCoordinateOfSearchInDocumentUploadWindow chosen is "+ xCoordinateOfSearchInDocumentUploadWindow);

                    		NodeList yCoordinateOfSearchInDocumentUploadWindowList = firstPersonElement
								.getElementsByTagName("yCoordinateOfSearchInDocumentUploadWindow");
                    		Element yCoordinateOfSearchInDocumentUploadWindowElement = (Element) yCoordinateOfSearchInDocumentUploadWindowList
								.item(0);

                    		NodeList textYCoordinateOfSearchInDocumentUploadWindowList = yCoordinateOfSearchInDocumentUploadWindowElement
								.getChildNodes();
                    		yCoordinateOfSearchInDocumentUploadWindow = Integer
								.parseInt(((Node) textYCoordinateOfSearchInDocumentUploadWindowList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("yCoordinateOfSearchInDocumentUploadWindow chosen is "+ yCoordinateOfSearchInDocumentUploadWindow);

                    		NodeList xCoordinateOfSearchResultInDocumentUploadWindowList = firstPersonElement
								.getElementsByTagName("xCoordinateOfSearchResultInDocumentUploadWindow");
                    		Element xCoordinateOfSearchResultInDocumentUploadWindowElement = (Element) xCoordinateOfSearchResultInDocumentUploadWindowList
								.item(0);

                    		NodeList textXCoordinateOfSearchResultInDocumentUploadWindowList = xCoordinateOfSearchResultInDocumentUploadWindowElement
								.getChildNodes();
                    		xCoordinateOfSearchResultInDocumentUploadWindow = Integer
								.parseInt(((Node) textXCoordinateOfSearchResultInDocumentUploadWindowList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("xCoordinateOfSearchResultInDocumentUploadWindow chosen is "+ xCoordinateOfSearchResultInDocumentUploadWindow);

                    		NodeList yCoordinateOfSearchResultInDocumentUploadWindowList = firstPersonElement
								.getElementsByTagName("yCoordinateOfSearchResultInDocumentUploadWindow");
                    		Element yCoordinateOfSearchResultInDocumentUploadWindowElement = (Element) yCoordinateOfSearchResultInDocumentUploadWindowList
								.item(0);

                    		NodeList textYCoordinateOfSearchResultInDocumentUploadWindowList = yCoordinateOfSearchResultInDocumentUploadWindowElement
								.getChildNodes();
                    		yCoordinateOfSearchResultInDocumentUploadWindow = Integer
								.parseInt(((Node) textYCoordinateOfSearchResultInDocumentUploadWindowList
									.item(0)).getNodeValue().trim());
                    		debugLogger.debug("yCoordinateOfSearchResultInDocumentUploadWindow chosen is "+ yCoordinateOfSearchResultInDocumentUploadWindow);

                    		NodeList xCoordianteToClickOnAllowForDownloadingMultipleFilesList = firstPersonElement
								.getElementsByTagName("xCoordianteToClickOnAllowForDownloadingMultipleFiles");
                    		Element xCoordianteToClickOnAllowForDownloadingMultipleFilesElement = (Element) xCoordianteToClickOnAllowForDownloadingMultipleFilesList
								.item(0);

                    		NodeList textXCoordianteToClickOnAllowForDownloadingMultipleFilesList = xCoordianteToClickOnAllowForDownloadingMultipleFilesElement
								.getChildNodes();
                    		xCoordianteToClickOnAllowForDownloadingMultipleFiles = Integer
								.parseInt(((Node) textXCoordianteToClickOnAllowForDownloadingMultipleFilesList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("xCoordianteToClickOnAllowForDownloadingMultipleFiles chosen is "+ xCoordianteToClickOnAllowForDownloadingMultipleFiles);

                    		NodeList yCoordianteToClickOnAllowForDownloadingMultipleFilesList = firstPersonElement
								.getElementsByTagName("yCoordianteToClickOnAllowForDownloadingMultipleFiles");
                    		Element yCoordianteToClickOnAllowForDownloadingMultipleFilesElement = (Element) yCoordianteToClickOnAllowForDownloadingMultipleFilesList
								.item(0);

                    		NodeList textYCoordianteToClickOnAllowForDownloadingMultipleFilesList = yCoordianteToClickOnAllowForDownloadingMultipleFilesElement
								.getChildNodes();
                    		yCoordianteToClickOnAllowForDownloadingMultipleFiles = Integer
								.parseInt(((Node) textYCoordianteToClickOnAllowForDownloadingMultipleFilesList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("yCoordianteToClickOnAllowForDownloadingMultipleFiles chosen is "+ yCoordianteToClickOnAllowForDownloadingMultipleFiles);

                    		NodeList xCoordianteForDownloadingInPdfFormatList = firstPersonElement
								.getElementsByTagName("xCoordianteForDownloadingInPdfFormat");
                    		Element xCoordianteForDownloadingInPdfFormatElement = (Element) xCoordianteForDownloadingInPdfFormatList
								.item(0);

                    		NodeList textXCoordianteForDownloadingInPdfFormatList = xCoordianteForDownloadingInPdfFormatElement
								.getChildNodes();
                    		xCoordianteForDownloadingInPdfFormat = Integer
								.parseInt(((Node) textXCoordianteForDownloadingInPdfFormatList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("xCoordianteForDownloadingInPdfFormat chosen is "+ xCoordianteForDownloadingInPdfFormat);

                    		NodeList yCoordianteForDownloadingInPdfFormatList = firstPersonElement
								.getElementsByTagName("yCoordianteForDownloadingInPdfFormat");
                    		Element yCoordianteForDownloadingInPdfFormatElement = (Element) yCoordianteForDownloadingInPdfFormatList
								.item(0);

                    		NodeList textYCoordianteForDownloadingInPdfFormatList = yCoordianteForDownloadingInPdfFormatElement
								.getChildNodes();
                    		yCoordianteForDownloadingInPdfFormat = Integer
								.parseInt(((Node) textYCoordianteForDownloadingInPdfFormatList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("yCoordianteForDownloadingInPdfFormat chosen is "+ yCoordianteForDownloadingInPdfFormat);

                    		NodeList xCoordinateOfSearchInEntireMacForDocumentUploadList = firstPersonElement
								.getElementsByTagName("xCoordinateOfSearchInEntireMacForDocumentUpload");
                    		Element xCoordinateOfSearchInEntireMacForDocumentUploadElement = (Element) xCoordinateOfSearchInEntireMacForDocumentUploadList
								.item(0);

                    		NodeList textXCoordinateOfSearchInEntireMacForDocumentUploadList = xCoordinateOfSearchInEntireMacForDocumentUploadElement
								.getChildNodes();
                    		xCoordinateOfSearchInEntireMacForDocumentUpload = Integer
								.parseInt(((Node) textXCoordinateOfSearchInEntireMacForDocumentUploadList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("xCoordinateOfSearchInEntireMacForDocumentUpload chosen is "+ xCoordinateOfSearchInEntireMacForDocumentUpload);

                    		NodeList yCoordinateOfSearchInEntireMacForDocumentUploadList = firstPersonElement
								.getElementsByTagName("yCoordinateOfSearchInEntireMacForDocumentUpload");
                    		Element yCoordinateOfSearchInEntireMacForDocumentUploadElement = (Element) yCoordinateOfSearchInEntireMacForDocumentUploadList
								.item(0);

                    		NodeList textYCoordinateOfSearchInEntireMacForDocumentUploadList = yCoordinateOfSearchInEntireMacForDocumentUploadElement
								.getChildNodes();
                    		yCoordinateOfSearchInEntireMacForDocumentUpload = Integer
								.parseInt(((Node) textYCoordinateOfSearchInEntireMacForDocumentUploadList
									.item(0)).getNodeValue().trim());
                    		//debugLogger.debug("yCoordinateOfSearchInEntireMacForDocumentUpload chosen is "+ yCoordinateOfSearchInEntireMacForDocumentUpload);
                    	}
                    	else
                    	{
                    		NodeList xCoordianteToSaveDocumentListInIEBrowser = firstPersonElement
								.getElementsByTagName("xCoordianteToSaveDocumentInIEBrowser");
                    		Element xCoordianteToSaveDocumentElementInIEBrowser = (Element) xCoordianteToSaveDocumentListInIEBrowser
								.item(0);

                    		NodeList textXCoordianteToSaveDocumentListInIEBrowser = xCoordianteToSaveDocumentElementInIEBrowser
								.getChildNodes();
                    		xCoordianteToSaveDocumentInIEBrowser = Integer
								.parseInt(((Node) textXCoordianteToSaveDocumentListInIEBrowser
								.item(0)).getNodeValue().trim());
                    		debugLogger.debug("xCoordianteToSaveDocumentInIEBrowser chosen is "+ xCoordianteToSaveDocumentInIEBrowser);
                    		
                    		NodeList yCoordianteToSaveDocumentListInIEBrowser = firstPersonElement
							.getElementsByTagName("yCoordianteToSaveDocumentInIEBrowser");
                    		Element yCoordianteToSaveDocumentElementInIEBrowser = (Element) yCoordianteToSaveDocumentListInIEBrowser
							.item(0);

                    		NodeList textYCoordianteToSaveDocumentListInIEBrowser = yCoordianteToSaveDocumentElementInIEBrowser
								.getChildNodes();
                    		yCoordianteToSaveDocumentInIEBrowser = Integer
								.parseInt(((Node) textYCoordianteToSaveDocumentListInIEBrowser
										.item(0)).getNodeValue().trim());
                    		debugLogger.debug("yCoordianteToSaveDocumentInIEBrowser chosen is "+ yCoordianteToSaveDocumentInIEBrowser);
                    		
                    		//added for chrome in IE to click on FileName for upload
                    		NodeList xCoordianteToClickFileIEBrowser = firstPersonElement
							.getElementsByTagName("xCoordinatesClickingTheFileForChromeIE");
                    		Element xCoordianteToClickElementFileIEBrowser = (Element) xCoordianteToClickFileIEBrowser
							.item(0);

	                		NodeList textXCoordianteToClickFileIEBrowser = xCoordianteToClickElementFileIEBrowser
								.getChildNodes();
	                		xCoordinatesClickingTheFileForChromeIE = Integer
								.parseInt(((Node) textXCoordianteToClickFileIEBrowser
								.item(0)).getNodeValue().trim());
	                		debugLogger.debug("xCoordianteToClickFileIEBrowser chosen is "+ xCoordinatesClickingTheFileForChromeIE);
	                		
	                		NodeList yCoordianteToClickFileIEBrowser = firstPersonElement
							.getElementsByTagName("yCoordinatesClickingTheFileForChromeIE");
	                		Element yCoordianteToClickElementFileIEBrowser = (Element) yCoordianteToClickFileIEBrowser
							.item(0);
	
	                		NodeList textYCoordianteToClickFileIEBrowser = yCoordianteToClickElementFileIEBrowser
								.getChildNodes();
	                		yCoordinatesClickingTheFileForChromeIE = Integer
								.parseInt(((Node) textYCoordianteToClickFileIEBrowser
										.item(0)).getNodeValue().trim());
	                		debugLogger.debug("yCoordinatesClickingTheFileForChromeIE chosen is "+ yCoordinatesClickingTheFileForChromeIE);
	                		
	                		//added for saving the pdf in chrome on windows
	                		
	                		NodeList xCoordianteForDownloadingInPdfFormatListInWindows = firstPersonElement
							.getElementsByTagName("xCoordianteForDownloadingInPdfFormatInWindows");
		            		Element xCoordianteForDownloadingInPdfFormatElementInWindows = (Element) xCoordianteForDownloadingInPdfFormatListInWindows
								.item(0);
	
		            		NodeList textXCoordianteForDownloadingInPdfFormatListInWindows = xCoordianteForDownloadingInPdfFormatElementInWindows
								.getChildNodes();
		            		xCoordianteForDownloadingInPdfFormatInWindows = Integer
								.parseInt(((Node) textXCoordianteForDownloadingInPdfFormatListInWindows
									.item(0)).getNodeValue().trim());
		            		debugLogger.debug("xCoordianteForDownloadingInPdfFormatInWindows chosen is "+ xCoordianteForDownloadingInPdfFormatInWindows);
	
		            		NodeList yCoordianteForDownloadingInPdfFormatListInWindows = firstPersonElement
								.getElementsByTagName("yCoordianteForDownloadingInPdfFormatInWindows");
		            		Element yCoordianteForDownloadingInPdfFormatElementInWindows = (Element) yCoordianteForDownloadingInPdfFormatListInWindows
								.item(0);
	
		            		NodeList textYCoordianteForDownloadingInPdfFormatListInWindows = yCoordianteForDownloadingInPdfFormatElementInWindows
								.getChildNodes();
		            		yCoordianteForDownloadingInPdfFormatInWindows = Integer
								.parseInt(((Node) textYCoordianteForDownloadingInPdfFormatListInWindows
									.item(0)).getNodeValue().trim());
		            		debugLogger.debug("yCoordianteForDownloadingInPdfFormatInWindows chosen is "+ yCoordianteForDownloadingInPdfFormatInWindows);
                    	}			
                    }
                }
            }
        }
		catch (Exception e)
		{
			testResultsLogger.error ("** Parsing error" + e.getMessage());
			testResultsLogger.info("Parsing was unsuccessful.Hence quitting the test suite");
			System.exit(0);
		}
	}
}

