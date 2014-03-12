###################################################################
import re, Foundation, bs_common
###################################################################

def checkFileSyncing(appName):
	timeout = 0
	while timeout < 60: 
		if bool(target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains 'Updating'").isValid()):		
			time.sleep(1)
			timeout = timeout + 1
		else:
			if timeout > 0:
				msg = "INFO - waited for file syncing for "+ str(timeout) +" iterations"
				bs_common.writeToConsole(msg)
				bs_common.writeToLogFile(msg)
			break
		
def createNewFile(testcaseId, appName, fileName):
	stepName = "create new file - "+ appName +" / "+ fileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	myFileType = getFileTypeFromAppName(appName)
	
	baseObjectPath.click()
		
	with patience(30): baseObjectPath.staticTexts()["Create "+ myFileType].isEnabled() == True

	if baseObjectPath.staticTexts()["Create "+ myFileType].isEnabled() == True:
		myNewFile = "Create " + myFileType
		hitpointX = baseObjectPath.staticTexts()[myNewFile].hitpoint().x
		hitpointY = baseObjectPath.staticTexts()[myNewFile].hitpoint().y
		hitpointOffsetY = (hitpointY - 90)
		mouse.click_((hitpointX, hitpointOffsetY))	

	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)
	
	if not selectGilliganTemplate(appName, fileName):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)
	
	with patience(30):
		target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
		
	if not target.processes()["Safari"].mainWindow().webViews()[0].elements()[0].buttons().firstWithPredicate_("name contains '%'"):	
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)
		
	fileNameToSplit = re.sub(r"([A-Z])", r" \1", fileName).split()
	splitFileName = ' '.join(fileNameToSplit)

	if not baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ splitFileName +"'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)
		
	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)

def createNewFromTemplate(testcaseId, templateKey, runDataFile):
	(appName, templateName) = templateKey.split("_")
	stepName = "create new "+ templateKey
	
	if testcaseId is not None:
		time.sleep(1)		
		fileType = getFileTypeFromAppName(appName)
		loadGilliganApp(testcaseId, appName)
		
		createNewFromFileType = "Create " + fileType
		hitpointX = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[createNewFromFileType].hitpoint().x
		hitpointY = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[createNewFromFileType].hitpoint().y
		hitpointOffsetY = (hitpointY - 90)
		mouse.click_((hitpointX, hitpointOffsetY))	
		
		startTimer = selectGilliganTemplate(appName, templateName)
		
		with patience(60): target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
		
		if target.processes()["Safari"].mainWindow().webViews()[0].elements()[0].buttons().firstWithPredicate_("name contains '%'"):	
			stopTimer = time.time()
			timingResult = (stopTimer - startTimer)
			formattedResult = '{0:.3g}'.format(timingResult)
			bs_common.writePerformanceTestResults(testcaseId, stepName, "PASS", formattedResult)

			target.processes()["Safari"].mainWindow().click()
			target.processes()["Safari"].mainWindow().closeButton().click()
			
			checkFileSyncing(appName)
			
			with patience(30):
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").isEnabled()
			
			if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").isEnabled():
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").click()

			else:
				bs_common.writePerformanceTestResults(testcaseId, stepName, "FAIL", 0.00)
				msg = appName + " '"+ templateName +"' creation failed"
				bs_common.transactionFailedExit(msg)
		
		if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Cancel"].isEnabled():
			bs_common.writePerformanceTestResults(testcaseId, stepName, "FAIL", 0.00)
			msg = "Something is wrong. Should not see the template screen"
			bs_common.transactionFailedExit(msg)
		
		if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups().firstWithPredicate_("helpTag contains 'Try again later'"):
			bs_common.writePerformanceTestResults(testcaseId, stepName, "FAIL", 0.00)
			msg = appName + " '"+ templateName +"' Received 'Could not be Created' dialog"
			bs_common.transactionFailedExit(msg)
			
			target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups().firstWithPredicate_("helpTag contains 'Try again later'").buttons()["OK"].click()
			target.processes()["Safari"].mainWindow().click()
			target.processes()["Safari"].mainWindow().closeButton().click()
		
			with patience(45):
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").isEnabled() == True	
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").click()
			
		bs_common.writeItemStatus(runDataFile, templateKey, 1)

def deleteGilliganFiles(testcaseId, appName):
	stepName = "delete files in "+ appName	
	resultsData = bs_common.getResultsFile()

	if testcaseId is not None:
		loadGilliganApp(testcaseId, appName)
		
		checkFileSyncing(appName)
	
		fileCount = str(target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images().count())
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images()[0].click()
		keyboard.typeString_withModifiersMask_("a", (kUIACommandKeyMask))
		keyboard.typeVirtualKey_(kVK_Delete)
		time.sleep(1)
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups()[0].buttons()["Delete"].click()
		time.sleep(1)
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").click()	
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
		msg = "INFO - deleted "+ fileCount +" files in "+ appName +" document manager"
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
def exitGilligan(testcaseId, appName):
	stepName = "exit gilligan from - "+ appName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()

	if baseObjectPath.buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'"):
		baseObjectPath.buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").click()
		
	if target.processes()["Safari"].windows()["iCloud"].webViews()[0].buttons()["Sign Out"].isEnabled():
		bs_common.writeFunctionalTestResults(testcaseId, appName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
	
	else:
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)

def fileClose(testcaseId, fileName):
	stepName = "file close - "+ fileName
	if target.processes()["Safari"].windows().firstWithPredicate_("name like '*"+ fileName +"*'"):
		target.processes()["Safari"].windows().firstWithPredicate_("name like '*"+ fileName +"*'").click()
		keyboard.typeString_withModifiersMask_("w", (kUIACommandKeyMask))
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
	
	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)

def fileDeleteGilligan(testcaseId, appName, fileName):
	stepName = "file delete - "+ appName +" / "+ fileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ fileName +"'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
	
	xPoint = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ fileName +"'").hitpoint().x
	yPoint = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ fileName +"'").hitpoint().y
	yOffset = (yPoint - 60)

	mouse.click_((xPoint, yOffset))

	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(appName)
	
# 	baseObjectPath.menuItems()[u"Delete "+ myAppFileType +" "].staticTexts()["Delete "+ myAppFileType].click()
	baseObjectPath.menuItems()[4].staticTexts()["Delete "+ myAppFileType].click()
	
	time.sleep(1)
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups()[0].buttons()["Delete"].click()
	time.sleep(1)
	
	if baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ fileName +"'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
def fileDuplicate(testcaseId, appName, inFileName):
	stepName = "file duplicate - "+ appName +" / "+ inFileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
	
	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	mouse.click_((x, yOffset))

	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	baseObjectPath.menuItems()["Duplicate "+ myAppFileType].staticTexts()["Duplicate "+ myAppFileType].click()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ inFileName +" copy'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
def fileExport(testcaseId, appName, fileName, exportType):
	stepName = "file export - "+ appName +" / "+ fileName +" / "+ exportType
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ fileName +"'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)
	
	xPoint = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ fileName +"'").hitpoint().x
	yPoint = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ fileName +"'").hitpoint().y
	yOffset = (yPoint - 60)

	mouse.click_((xPoint, yOffset))

	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	baseObjectPath.menuItems()["Download "+ myAppFileType +"..."].staticTexts()["Download "+ myAppFileType +"..."].click()
	
	myExportType = getFileExportType(appName, exportType)
	
	filePattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/en-us/source/resources/export/(.+)')

	for image in baseObjectPath.images():	
		urlString = image.url()
	
		if urlString is None:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - match "+ appName +" / "+ fileName +" / "+ exportType
			bs_common.transactionFailedExit(msg)

		result = filePattern.match(urlString)
		
		if "ubiquityws.icloud.com" in urlString:
# 			This is a document manager object. Skip.			
			continue

		urlExportImage = result.groups()[1]				
		if urlExportImage == myExportType:
			baseObjectPath.images().firstWithPredicate_("url like '"+ urlString +"'").click()
			break
			
	with patience(60): baseObjectPath.buttons()["Cancel\n"].waitForInvalid()
	bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
	msg = "PASS - "+ stepName
	bs_common.writeToConsole(msg)
	bs_common.writeToLogFile(msg)
	
def fileFindReplace(testcaseId, appName, inFileName, inFindText, inReplaceText):
	stepName = "find '"+ inFindText +"' replace '"+ inReplaceText +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)
	
	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	mouse.doubleClick_((x, yOffset))
	


def fileOpen(testcaseId, appName, inFileName):
	stepName = "file open - "+ appName +" / "+ inFileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	mouse.doubleClick_((x, yOffset))	

	with patience(30):
		target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
		
	if not target.processes()["Safari"].mainWindow().webViews()[0].elements()[0].buttons().firstWithPredicate_("name contains '%'"):	
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
	
	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
			
def fileRename(testcaseId, appName, fileName, fileRenameTo):
	stepName = "file rename - "+ appName +" / "+ fileName +" to "+ fileRenameTo
	resultsData = bs_common.getResultsFile()
	
	if testcaseId is not None:
		try:
			if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ fileName +"'"):
				bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
				msg = "FAIL - "+ stepName
				bs_common.writeToConsole(msg)
				bs_common.writeToLogFile(msg)
											
			xPoint = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images()[0].hitpoint().x
			yPoint = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images()[0].hitpoint().y
			yOffset = (yPoint + 125)
			
			mouse.click_((xPoint, yOffset))
	
			keyboard.typeString_(fileRenameTo)
			keyboard.typeVirtualKey_(kVK_Return)
			
			if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name like '"+ fileRenameTo +"'"):
				bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
				msg = "FAIL - "+ stepName
				bs_common.writeToConsole(msg)
				bs_common.writeToLogFile(msg)
				
			else:
				bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")	
				msg = "PASS - "+ stepName
				bs_common.writeToConsole(msg)
				bs_common.writeToLogFile(msg)
				
		except:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - "+ stepName
			bs_common.transactionFailedExit(msg)
			
def contextMenu_fileDuplicate(testcaseId, appName, inFileName):
	stepName = "context menu file duplicate - "+ appName +" / "+ inFileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
	
	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	keyboard.pressKey_(kVK_Control)
	mouse.click_((x, yOffset))	
	keyboard.releaseKey_(kVK_Control)
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	baseObjectPath.menuItems()["Duplicate "+ myAppFileType].staticTexts()["Duplicate "+ myAppFileType].click()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ fileName +" copy'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
def contextMenu_fileExport(testcaseId, appName, inFileName, inExportType):
	stepName = "context menu file export - "+ appName +" / "+ inFileName +" / "+ inExportType
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.transactionFailedExit(msg)
	
	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	keyboard.pressKey_(kVK_Control)
	mouse.click_((x, yOffset))	
	keyboard.releaseKey_(kVK_Control)
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	baseObjectPath.menuItems()["Download "+ myAppFileType +"..."].staticTexts()["Download "+ myAppFileType +"..."].click()
	
	myExportType = getFileExportType(appName, inExportType)
	
	filePattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/en-us/source/resources/export/(.+)')

	for image in baseObjectPath.images():	
		urlString = image.url()
	
		if urlString is None:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - match "+ appName +" / "+ inFileName +" / "+ inExportType
			bs_common.transactionFailedExit(msg)

		result = filePattern.match(urlString)
		
		if "ubiquityws.icloud.com" in urlString:
# 			This is a document manager object. Skip.			
			continue

		urlExportImage = result.groups()[1]				
		if urlExportImage == myExportType:
			baseObjectPath.images().firstWithPredicate_("url like '"+ urlString +"'").click()
			break
			
	with patience(60): baseObjectPath.buttons()["Cancel\n"].waitForInvalid()
	bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
	msg = "PASS - "+ stepName
	bs_common.writeToConsole(msg)
	bs_common.writeToLogFile(msg)
			
def gearMenu_fileUpload(testcaseId, appName, inFileName):
	stepName = "file upload - "+ appName +" / "+ inFileName
	
	configurationData = bs_common.getConfiguration()
	myFileImportPath = configurationData["fileImportPath"]

	myFileName, myFileExtenstion = inFileName.split(".")
	
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	resultsData = bs_common.getResultsFile()

	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	baseObjectPath.menuItems()["Upload "+ myAppFileType +"..."].staticTexts()["Upload "+ myAppFileType +"..."].click()
	
	if baseObjectPath.staticTexts().firstWithPredicate_("name like 'Choose * upload*'"):
		print "upload dialog appeared"
		baseObjectPath.buttons()["Choose Files"].click()
				
	keyboard.typeString_withModifiersMask_("g", (kUIAShiftKeyMask|kUIACommandKeyMask))
	keyboard.typeString_(myFileImportPath +"/"+ inFileName)	
	keyboard.typeVirtualKey_(kVK_Return)
	time.sleep(1)
	target.processes()["Safari"].mainWindow().sheet().buttons()["Choose"].click()
	
	with patience(60): baseObjectPath.buttons()["Cancel\n"].waitForInvalid()
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ myFileName +"*'"):
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		
	else:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)

def formatMenuShowHide(testcaseId, appName, fileName):
	stepName = "format menu show / hide"
	resultsData = bs_common.getResultsFile()
	
	if target.processes()["Safari"].windows()[fileName]:
		if target.processes()["Safari"].mainWindow().webViews()[0].buttons()["Text"]:
			if target.processes()["Safari"].mainWindow().webViews()[0].elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Format"]:
				target.processes()["Safari"].mainWindow().webViews()[0].elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Format"].click()
				
				if not target.processes()["Safari"].mainWindow().webViews()[0].buttons()["Text"]:
					target.processes()["Safari"].mainWindow().webViews()[0].elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Format"].click()
					
					if target.processes()["Safari"].mainWindow().webViews()[0].buttons()["Text"]:
						bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
						msg = "PASS - "+ stepName
						bs_common.writeToConsole(msg)
						bs_common.writeToLogFile(msg)
										
					else:
						bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
						msg = "FAIL - "+ stepName
						bs_common.transactionFailedExit(msg)
	
	else:
		msg = "FAIL - "+ stepName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
						
def getFileExportType(appName, exportType):
	if appName == "Pages" and exportType == "Pages":
		return "export-pages.png"
		
	elif appName == "Pages" and exportType == "Word":
		return "export-word.png"
		
	elif exportType == "PDF":
		return "export-pdf.png"
				
def getFileTypeFromAppName(appName):
	if appName == "Keynote":
		fileType = "Presentation"
		return fileType
	elif appName == "Numbers":
		fileType = "Spreadsheet"
		return fileType
	elif appName == "Pages":
		fileType = "Document"
		return fileType
		
def loadGilliganApp(testcaseId, appName):
	stepName = "load gilligan app - "+ appName
	resultsData = bs_common.getResultsFile()
	
	if testcaseId is not None:
		time.sleep(1)	
		if not target.processes()["Safari"].mainWindow().webViews()[0].buttons()[appName].isEnabled() == True:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - "+ stepName
			bs_common.transactionFailedExit(msg)
			
		else:
			target.processes()["Safari"].mainWindow().webViews()[0].buttons()[appName].click()
			
		if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains '"+ appName +" for iCloud'").isEnabled() == True:
		
			if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Get started with'").isEnabled() == True:
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Get started with'").click()
			
			if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains 'Choose a Template'").isEnabled() == True:
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Cancel'").click()
			
			if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems().firstWithPredicate_("name contains 'Learn More About'").isEnabled() == True:
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Show help"].click()	
		
		if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains '"+ appName +" for iCloud'").isEnabled() == True:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
			msg = "PASS - "+ stepName
			bs_common.writeToConsole(msg)
			bs_common.writeToLogFile(msg)
			
		else:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - "+ stepName
			bs_common.transactionFailedExit(msg)
					
def selectGilliganTemplate(appName, fileName):
	import re
	
	try:
		filePattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/bight/templates/(.+)/en-us/source/(.+)/(.+)\.(.+)')
		
		fileFindTries = 0
		fileFound = False
		
		while fileFindTries < 4 and not fileFound:
			for image in target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images():
				
				urlString = image.url()
				result = filePattern.match(urlString)
	
				if "ubiquityws.icloud.com" in urlString:
# 					This is a document manager object. Skip.			
					continue
								
				if result is None:
					sys.stderr.write("%s '%s' could not match pattern in url '%s'\n" % (appName, fileName, urlString))
					return False
					
				urlAppName = result.groups()[2]
				urlAppVersion = result.groups()[0]
				urlAppfile = result.groups()[3]
				
				strippedFileName = "".join(fileName.split())
				
				if not urlAppfile == strippedFileName:
					continue
					
				else:
					image.click()
					time.sleep(1)
					target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Choose'").click()
					startTimer = time.time()
					fileFound = True
					return startTimer
					break
					
			keyboard.typeVirtualKey_(kVK_DownArrow)
			time.sleep(3)
			fileFindTries = fileFindTries + 1
		
		if fileFindTries == 2 and not fileFound:
			sys.stderr.write(appName + " '"+ fileName +"' not found\n")
			return False

	except:
		sys.stderr.write("script error has occurred. terminating.\n")
		bs_common.takeScreenshotOfError()
		return False
		
def validateGearMenu(testcaseId, appName):
	stepName = "validate gear menu"
	myReturnStatus = True
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	if baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").isValid():
		myAppFileType = getFileTypeFromAppName(appName)
		
# 		myMenuItem = ["Create "+ myAppFileType, "Upload "+ myAppFileType +"...", "Download "+ myAppFileType +"...", "Duplicate "+ myAppFileType, "Delete "+ myAppFileType, "Share "+ myAppFileType +" Link", "Send a Copy..."]
		myMenuItem = ["Create "+ myAppFileType, "Upload "+ myAppFileType +"...", "Download "+ myAppFileType +"...", "Duplicate "+ myAppFileType, "Share "+ myAppFileType +" Link"]

		baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
		
		for item in myMenuItem:							
			if baseObjectPath.menuItems()[item].staticTexts()[item].isValid():
				msg = "PASS - gear menu item '"+ item +"'"
				bs_common.writeToConsole(msg)
				bs_common.writeToLogFile(msg)
			
			else:
				msg = "FAIL - gear menu item '"+ item +"'"
				bs_common.writeToConsole(msg)
				bs_common.writeToLogFile(msg)
				myReturnStatus = False
	
	if not myReturnStatus:
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
		keyboard.typeVirtualKey_(kVK_Escape)
		msg = "FAIL - validate gear menu for "+ appName
		bs_common.transactionFailedContinue(msg)
	
	else:
		keyboard.typeVirtualKey_(kVK_Escape)
		msg = "PASS - validate gear menu for "+ appName
		bs_common.writeToConsole(msg)
		bs_common.writeToLogFile(msg)
		bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
		
		
		
		
		
		
		