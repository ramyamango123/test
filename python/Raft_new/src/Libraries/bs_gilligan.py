libraryBuildVersion = "2013.05.13 v1.0.2"
###################################################################
import re, Foundation, bs_common
###################################################################

def checkFileSyncing(appName):
	stepName = "check file syncing in "+ appName
	time.sleep(1)
	if 	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains 'Updating'"):
		timeoutCount = 1
		timeoutLimit = 120
		while timeoutCount <= timeoutLimit: 
			if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains 'Updating'"):		
# 				print "*** INFO - object still valid"
				time.sleep(1)
				timeoutCount = timeoutCount + 1
			
			else:
				if timeoutCount >= 1:
# 					print "*** INFO - object should be invalid"
					break
						
		if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains 'Updating'"):
# 			print "*** INFO - stop checkFileSyncing"
			return True
			
		else:
			msg = "FAIL - "+ stepName
			bs_common.scriptFailedExit()
			return False
					
def contextMenu_fileDuplicate(testcaseId, appName, inFileName):
	stepName = "contextMenu file duplicate - "+ appName +" / "+ inFileName	
	time.sleep(1)	
	if not target.processes()["Safari"].frontWindow():
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].click()

	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[inFileName]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeItemToLogFile(msg)
	
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[inFileName].bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	time.sleep(1)
	keyboard.pressKey_(kVK_Control)
	mouse.click_((x, yOffset))	
	keyboard.releaseKey_(kVK_Control)
	
	myAppFileType = getFileTypeFromAppName(appName)

	time.sleep(1)	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems()["Duplicate "+ myAppFileType].staticTexts()["Duplicate "+ myAppFileType].click()
	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name like '"+ inFileName +" copy'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
def contextMenu_fileDownload(testcaseId, appName, inFileName, inExportType):
	stepName = "contextMenu file download - "+ appName +" / "+ inFileName +" / "+ inExportType
	
	if not target.processes()["Safari"].frontWindow():
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].click()
		time.sleep(1)
	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains '"+ inFileName +"'").bounds()		
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)
	mouse.click_((x, yOffset))
	time.sleep(1)		

	keyboard.pressKey_(kVK_Control)
	mouse.click_((x, yOffset))	
	keyboard.releaseKey_(kVK_Control)
	time.sleep(1)
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems()["Download "+ myAppFileType +"..."].staticTexts()["Download "+ myAppFileType +"..."].click()
	time.sleep(1)
	
	myExportType, myUploadedType = getFileExportType(appName, inExportType)
	
	fileExportPattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/en-us/source/resources/export/(.+)')
	fileUploadedPattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/bight/core_docs/(.+)/en-us/source/resources/images/(.+)')

	for image in target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images():	
		urlString = image.url()
			
		if urlString is None:
			bs_common.writeResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - match "+ appName +" / "+ inFileName +" / "+ inExportType
			bs_common.scriptFailedExit(msg)
			
		resultExportPattern = fileExportPattern.match(urlString)
		resultUploadPattern = fileUploadedPattern.match(urlString)
		
		if "ubiquityws.icloud.com" in urlString:
# 			This is a document manager object. Skip.			
			continue
			
		if not resultExportPattern is None: 
			urlExportImage = resultExportPattern.groups()[1]
			if urlExportImage == myExportType:
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[inExportType].click()
				break
		
		if not resultUploadPattern is None:
			urlUploadImage = resultUploadPattern.groups()[2]
			if urlUploadImage == myUploadedType:
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[inExportType].click()
				break
	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[u"Creating a file for download..."].waitForInvalid()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[u"Creating a file for download..."]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")	
		msg = "FAIL - "+ stepName +": download dialog still valid"
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	time.sleep(3)
		
def createNewFile(testcaseId, appName, fileName):
	stepName = "create - "+ appName +" / "+ fileName
# 	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	
	myFileType = getFileTypeFromAppName(appName)
	
	if not target.processes()["Safari"].frontWindow():
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].click()
		time.sleep(2)
			
	with patience(30):
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()["Create "+ myFileType].isEnabled()

	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()["Create "+ myFileType].isEnabled():
		myCreateObject = "Create " + myFileType
		myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[myCreateObject].bounds()
		x = Foundation.NSMidX(myBounds)
		y = Foundation.NSMidY(myBounds)
		yOffset = (y - 60)
		mouse.click_((x, yOffset))

	else:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
	if not selectGilliganTemplate(appName, fileName):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
	with patience(30):
		target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
		
	if not target.processes()["Safari"].mainWindow().webViews()[0].elements()[0].buttons().firstWithPredicate_("name contains '%'"):	
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	fileNameToSplit = re.sub(r"([A-Z])", r" \1", fileName).split()
	splitFileName = ' '.join(fileNameToSplit)
	
	if appName == "Keynote":
		splitFileName = "Presentation"

	if not target.processes()["Safari"].windows().firstWithPredicate_("name contains '"+ splitFileName +"'"):
# 	if not target.processes()["Safari"].mainWindow().name()[splitFileName]:
# 	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains '"+ splitFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName +": window name is wrong"
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)

def createNewFromTemplate(testcaseId, templateKey, runDataFile):
	(appName, templateName) = templateKey.split("_")
	stepName = "new from template '"+ templateKey +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	
	fileType = getFileTypeFromAppName(appName)

	time.sleep(1)		
	loadGilliganApp(testcaseId, appName)
	
	time.sleep(1)
	createNewFromFileType = "Create " + fileType
	
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[createNewFromFileType].bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 90)

	mouse.click_((x, yOffset))	
 	
	startTimer = selectGilliganTemplate(appName, templateName)
	
	with patience(60):
		target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].elements()[0].buttons().firstWithPredicate_("name contains '%'"):	
		stopTimer = time.time()
		timingResult = (stopTimer - startTimer)
		formattedResult = '{0:.3g}'.format(timingResult)
		bs_common.writePerformanceTestResults(testcaseId, stepName, "PASS", formattedResult)

		target.processes()["Safari"].mainWindow().click()
		target.processes()["Safari"].mainWindow().closeButton().click()
		
	else:
		bs_common.writePerformanceTestResults(testcaseId, stepName, "FAIL", 0.00)
		msg = "FAIL - "+ appName + " '"+ templateName +"' creation failed"
		bs_common.scriptFailedExit(msg)
		
	checkFileSyncing(appName)
	
	validateObject = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Go to the iCloud home screen"]
	validateResult = bs_common.validateObject(validateObject)
	
	if not validateResult:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_common.validateObject: "+ validateObject
		bs_common.scriptFailedExit(msg)
	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Go to the iCloud home screen"].click()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Cancel"].isEnabled():
		bs_common.writePerformanceTestResults(testcaseId, stepName, "FAIL", 0.00)
		msg = "FAIL - Something is wrong. Should not see the template screen"
		bs_common.scriptFailedExit(msg)
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups().firstWithPredicate_("helpTag contains 'Try again later'"):
		bs_common.writePerformanceTestResults(testcaseId, stepName, "FAIL", 0.00)
		msg = "FAIL - "+ appName + " '"+ templateName +"' Received 'Could not be Created' dialog"
		bs_common.scriptFailedExit(msg)
		
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups().firstWithPredicate_("helpTag contains 'Try again later'").buttons()["OK"].click()
		target.processes()["Safari"].mainWindow().click()
		target.processes()["Safari"].mainWindow().closeButton().click()
	
		validateObject = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Go to the iCloud home screen"]
		validateResult = bs_common.validateObject(validateObject)
		
		if not validateResult:
			bs_common.writeResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - bs_common.validateObject: "+ validateObject
			bs_common.scriptFailedExit(msg)
		
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Go to the iCloud home screen"].click()

	bs_common.writeItemStatus(runDataFile, templateKey, 1)

def deleteGilliganFiles(testcaseId, appName):
	stepName = "delete files in "+ appName		
	if not target.processes()["Safari"].frontWindow():
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].click()
		time.sleep(1)	
	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'"):
		loadGilliganApp(testcaseId, appName)

	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images():
		msg = "INFO - no files on document manager for "+ appName
		bs_common.writeItemToLogFile(msg)
		return
	
	time.sleep(1)
	fileCount = str(target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images().count())
	
	time.sleep(1)
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images()[0].click()

	keyboard.typeString_withModifiersMask_("a", (kUIACommandKeyMask))
	keyboard.typeVirtualKey_(kVK_Delete)

	time.sleep(1)
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups()[0].buttons()["Delete"].click()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images().count() > 0:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "INFO - deleted "+ fileCount +" files in "+ appName + " document manager"
		bs_common.writeItemToLogFile(msg)
		
def exitGilligan(testcaseId, appName):
	stepName = "exit gilligan from - "+ appName
	if not target.processes()["Safari"].frontWindow():
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].click()
		time.sleep(1)

	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'"):
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").click()
		time.sleep(1)
		
	if target.processes()["Safari"].windows()["iCloud"].webViews()[0].buttons()["Sign Out"].isEnabled():
		bs_common.writeResults(testcaseId, appName, "PASS")
# 		msg = "PASS - "+ stepName
# 		bs_common.writeItemToLogFile(msg)
	
	else:
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)

def fileClose(testcaseId, inFileName):
	stepName = "file close - "+ inFileName
	if target.processes()["Safari"].windows().firstWithPredicate_("name contains '"+ inFileName +"'"):
		target.processes()["Safari"].windows().firstWithPredicate_("name contains '"+ inFileName +"'").click()
		time.sleep(1)
		keyboard.typeString_withModifiersMask_("w", (kUIACommandKeyMask))
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
	
	else:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
def fileFindReplace(testcaseId, appName, inFileName, inFindText, inReplaceText):
	stepName = "find '"+ inFindText +"' replace '"+ inReplaceText +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0]
	myResults = bs_common.getResultsData()
	
	if baseObjectPath.staticTexts()["Loading..."]:
		with patience(30):
			baseObjectPath.staticTexts()["Loading..."].waitForInvalid()
	
	if not target.processes()["Safari"].windows().firstWithPredicate_("name like '*"+ inFileName +"*'"):
		bs_common.writeItemToLogFile("INFO - failed to find "+ inFileName +" open")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg) 
		
	keyboard.typeVirtualKey_withModifiersMask_(3, (kUIAControlKeyMask))
	baseObjectPath.buttons()[0].click()
	baseObjectPath.menuItems()["Find & Replace"].click()
	baseObjectPath.textFields()["Find"].click()
	keyboard.typeString_(inFindText)
	baseObjectPath.textFields()["Replace"].click()
	keyboard.typeString_(inReplaceText)
	
	if baseObjectPath.textFields()["Replace"].value() == inReplaceText:
		baseObjectPath.buttons()["Done\n"].click()
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg) 

def fileShowHelp(testcaseId, appName, inFileName):
	stepName = "show help for '"+ inFileName +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0]
	myResults = bs_common.getResultsData()
	
	if not target.processes()["Safari"].windows().firstWithPredicate_("name like '*"+ inFileName +"*'"):
		bs_common.writeItemToLogFile("INFO - failed to find "+ inFileName +" open")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg) 
		
	if baseObjectPath.elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Tools"]:
		baseObjectPath.elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Tools"].click()
	
	else:
		bs_common.writeItemToLogFile("INFO - failed to find toolbar")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	if baseObjectPath.menuItems()["Help"]:
		baseObjectPath.menuItems()["Help"].click()
		
	else:
		bs_common.writeItemToLogFile("INFO - failed to find menuItem")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)	
		
	if target.processes()["Safari"].windows()["iCloud Help"]:
		target.processes()["Safari"].windows()["iCloud Help"].closeButton().click()
		bs_common.writeResults(testcaseId, stepName	, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
	
	else:
		bs_common.writeItemToLogFile("INFO - failed to find help window")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
def fileOpen(testcaseId, appName, inFileName):
	stepName = "file open - "+ appName +" / "+ inFileName	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)
	mouse.doubleClick_((x, yOffset))	
	
	target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	if not target.processes()["Safari"].mainWindow().webViews()[0].elements()[0].buttons().firstWithPredicate_("name contains '%'"):	
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
def fileOpenUploaded(testcaseId, appName, inFileName):
	stepName = "file open - "+ appName +" / "+ inFileName	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName +": file not found"
		bs_common.writeItemToLogFile(msg)
		
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)
	mouse.doubleClick_((x, yOffset))	
	
	if target.processes()["Safari"].windows()[appName +" for iCloud beta"]:	
		timeoutCount = 1
		timeoutLimit = 120
		while timeoutCount <= timeoutLimit: 
			if target.processes()["Safari"].windows()[appName +" for iCloud beta"].webViews()[0].staticTexts()["Loading..."]:		
# 				print "*** INFO - object still valid"
				time.sleep(1)
				timeoutCount = timeoutCount + 1
			
			else:
				if timeoutCount >= 1:
#  					print "*** INFO - object should be invalid"
					break
					
		if 	target.processes()["Safari"].windows()[appName +" for iCloud beta"].webViews()[0].groups()[0]:
			target.processes()["Safari"].windows()[appName +" for iCloud beta"].webViews()[0].groups()[0].buttons()["Open"].click()	
						
		if not target.processes()["Safari"].windows()[appName +" for iCloud beta"].webViews()[0].staticTexts()["Loading..."]:
# 			print "*** INFO - stop checking"
			return True
			
# 		else:
# 			msg = "FAIL - "+ stepName +": failed white screen 'Loading...' validation"
# 			bs_common.scriptFailedExit(msg)
# 			return False
	
	target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName +": failed normal 'Loading...' validation"
		bs_common.scriptFailedExit(msg)
		
	if not target.processes()["Safari"].mainWindow().webViews()[0].elements()[0].buttons().firstWithPredicate_("name contains '%'"):	
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName +": failed to find % on toolbar"
		bs_common.scriptFailedExit(msg)
	
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)		
			
def fileRename(testcaseId, appName, inFileName, inFileRenamedTo):
	stepName = "file rename - "+ appName +" / "+ inFileName +" to "+ inFileRenamedTo
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
	try:
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").click()
		
	except:
		myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
		x = Foundation.NSMidX(myBounds)
		y = Foundation.NSMidY(myBounds)
		mouse.click_((x, y))		
		
	keyboard.typeString_(inFileRenamedTo)
	keyboard.typeVirtualKey_(kVK_Return)
	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains '"+ inFileRenamedTo +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	time.sleep(3)
	
def formatMenuShowHide(testcaseId, appName, inFileName):
	stepName = "format menu show / hide"
	myResults = bs_common.getResultsData()
		
	if target.processes()["Safari"].windows()[inFileName]:	
		if target.processes()["Safari"].mainWindow().webViews()[0].buttons()["Text"]:
			if target.processes()["Safari"].mainWindow().webViews()[0].elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Tools"]:
				target.processes()["Safari"].mainWindow().webViews()[0].elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Tools"].click()
				
				if target.processes()["Safari"].mainWindow().webViews()[0].menuItems()["Hide Format Panel"].staticTexts()["Hide Format Panel"]:
					target.processes()["Safari"].mainWindow().webViews()[0].menuItems()["Hide Format Panel"].staticTexts()["Hide Format Panel"].click()
					
				else:
					bs_common.writeItemToLogFile("INFO - failed to find menuItem 'Hide Format Panel'")
					bs_common.writeResults(testcaseId, stepName, "FAIL")
					msg = "FAIL - "+ stepName
					bs_common.scriptFailedExit(msg)
					
				time.sleep(5)
				
				if target.processes()["Safari"].mainWindow().webViews()[0].elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Tools"]:
					target.processes()["Safari"].mainWindow().webViews()[0].elements().firstWithPredicate_("className like 'UIAToolbar'").buttons()["Tools"].click()
				
					if target.processes()["Safari"].mainWindow().webViews()[0].menuItems()["Show Format Panel"].staticTexts()["Show Format Panel"]:
						target.processes()["Safari"].mainWindow().webViews()[0].menuItems()["Show Format Panel"].staticTexts()["Show Format Panel"].click()
						
					else:
						bs_common.writeItemToLogFile("INFO - failed to find menuItem 'Show Format Panel'")
						bs_common.writeResults(testcaseId, stepName, "FAIL")
						msg = "FAIL - "+ stepName
						bs_common.scriptFailedExit(msg)
										
				if target.processes()["Safari"].mainWindow().webViews()[0].buttons()["Text"]:					
					bs_common.writeResults(testcaseId, stepName, "PASS")
					msg = "PASS - "+ stepName
					bs_common.writeItemToLogFile(msg)
									
				else:
					bs_common.writeResults(testcaseId, stepName, "FAIL")
					msg = "FAIL - "+ stepName
					bs_common.scriptFailedExit(msg)
		
def gearMenu_fileDelete(testcaseId, appName, inFileName):
	stepName = "gearMenu file delete - "+ appName +" / "+ inFileName
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[inFileName]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[inFileName].bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)
	mouse.click_((x, yOffset))

	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems()[4].staticTexts()["Delete "+ myAppFileType].click()
	
	time.sleep(1)
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].groups()[0].buttons()["Delete"].click()
	time.sleep(1)
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[inFileName]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
def gearMenu_fileDownload(testcaseId, appName, inFileName, inExportType):
	stepName = "gearMenu file download - "+ appName +" / "+ inFileName +" / "+ inExportType
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name like '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_gilligan.gearMenu_fileDownload: file not found in document manager: "+ inFileName
		bs_common.scriptFailedExit(msg)
		
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name like '"+ inFileName +"'").bounds()	
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)
	mouse.click_((x, yOffset))	
	
	try:
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
		
	except:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_gilligan.gearMenu_fileDownload: failed to click gearMenu button"
		bs_common.scriptFailedExit(msg)
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	try:
		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems()["Download "+ myAppFileType +"..."].staticTexts()["Download "+ myAppFileType +"..."].click()
		
	except:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_gilligan.gearMenu_fileDownload: failed to click gearMenu menu item"
		bs_common.scriptFailedExit(msg)
		
	myExportType, myUploadedType = getFileExportType(appName, inExportType)
	
	fileExportPattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/en-us/source/resources/export/(.+)')
	fileUploadedPattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/bight/core_docs/(.+)/en-us/source/resources/images/(.+)')

	for image in target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images():	
		urlString = image.url()
	
		if urlString is None:
			bs_common.writeResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - bs_gilligan.gearMenu_fileDownload: match failed for "+ inFileName
			bs_common.scriptFailedExit(msg)
			
		resultExportPattern = fileExportPattern.match(urlString)
		resultUploadPattern = fileUploadedPattern.match(urlString)
		
		if "ubiquityws.icloud.com" in urlString:
# 			This is a document manager object. Skip.			
			continue
			
		if not resultExportPattern is None: 
# 			print "*** matched export image pattern"
			urlExportImage = resultExportPattern.groups()[1]
			if urlExportImage == myExportType:
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images().firstWithPredicate_("url like '"+ urlString +"'").click()
				break
		
		if not resultUploadPattern is None:
# 			print "*** matched upload image pattern"		
			urlUploadImage = resultUploadPattern.groups()[2]
			if urlUploadImage == myUploadedType:
				target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].images().firstWithPredicate_("url like '"+ urlString +"'").click()
				break

	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[u"Creating a file for download..."].waitForInvalid()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[u"Creating a file for download..."]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")	
		msg = "FAIL - "+ stepName +": download dialog still valid"
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	time.sleep(3)
	
def gearMenu_fileDuplicate(testcaseId, appName, inFileName):
	stepName = "gearMenu file duplicate - "+ appName +" / "+ inFileName
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeItemToLogFile(msg)
	
	myBounds = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)
	mouse.click_((x, yOffset))

	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems()["Duplicate "+ myAppFileType].staticTexts()["Duplicate "+ myAppFileType].click()
	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name like '"+ inFileName +" copy'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	time.sleep(3)
			
def gearMenu_fileUpload(testcaseId, appName, inFileName):
	stepName = "gearMenu file upload - "+ appName +" / "+ inFileName
	myConfigData = bs_common.getConfigurationData()
	myFileImportPath = myConfigData["fileImportPath"]
	myFileName, myFileExtenstion = inFileName.split(".")

	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems()["Upload "+ myAppFileType +"..."].staticTexts()["Upload "+ myAppFileType +"..."].click()
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name like 'Choose * upload*'"):
		baseObjectPath.buttons()["Choose Files"].click()
					
	keyboard.typeString_withModifiersMask_("g", (kUIAShiftKeyMask|kUIACommandKeyMask))
	keyboard.typeString_(myFileImportPath +"/"+ inFileName)	
	keyboard.typeVirtualKey_(kVK_Return)
	time.sleep(1)
	target.processes()["Safari"].mainWindow().sheet().buttons()["Choose"].click()
	
	target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Cancel\n"].waitForInvalid()
	
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name like '"+ myFileName +"*'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
						
def getFileExportType(appName, exportType):
	if appName == "Pages" and exportType == "Pages":
		return "export-pages.png", "uploaded_pages.jpg"
		
	elif appName == "Pages" and exportType == "Word":
		return "export-word.png", "uploaded_word.jpg"
		
	elif appName == "Numbers" and exportType == "Numbers":
		return "export-numbers.png", "uploaded_numbers.jpg"
		
	elif appName == "Numbers" and exportType == "Excel":
		return "export-excel.png", "uploaded_excel.jpg"
		
	elif appName == "Keynote" and exportType == "Keynote":
		return "export-keynote.png", "uploaded_keynote.jpg"	
	
	elif appName == "Keynote" and exportType == "PowerPoint":
		return "export-powerpoint.png", "uploaded_powerpoint.jpg"
		
	elif exportType == "PDF":
		return "export-pdf.png", "export_pdf.png"
				
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
	stepName = "load gilligan "+ appName	
	time.sleep(1)
	
	try:
		target.processes()["Safari"].mainWindow().webViews()[0].buttons()[appName].click()
		
	except:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_gilligan.loadGilliganApp: failed to click app icon"
		bs_common.scriptFailedExit(msg)
				
	target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."].waitForInvalid()
	
	if 	target.processes()["Safari"].mainWindow().webViews()[0].staticTexts()["Loading..."]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_gilligan.loadGilliganApp: loading... still valid"
		bs_common.scriptFailedExit(msg)
							
	if not target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[appName +" for iCloud"]:
	
		if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Get started with'").isEnabled() == True:
			target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Get started with'").click()
		
		if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts().firstWithPredicate_("name contains 'Choose a Template'").isEnabled() == True:
			target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Cancel'").click()
		
		if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems().firstWithPredicate_("name contains 'Learn More About'").isEnabled() == True:
			target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons()["Show help"].click()	
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].staticTexts()[appName +" for iCloud"]:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName	
		bs_common.writeItemToLogFile(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_gilligan.loadGilliganApp: failed to find proper app state"
		bs_common.scriptFailedExit(msg)
		
def modifySpreadsheetTabs(testcaseId, inFileName):
	stepName = "modify spreadsheet tabs"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0]
	myResults = bs_common.getResultsData()
	
	if baseObjectPath.staticTexts()["Loading..."]:
		with patience(30):
			baseObjectPath.staticTexts()["Loading..."].waitForInvalid()
	
	if not target.processes()["Safari"].windows().firstWithPredicate_("name like '*"+ inFileName +"*'"):
		bs_common.writeItemToLogFile("INFO - failed to find "+ inFileName +" open")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg) 
		
	baseObjectPath.buttons()[0].click()
	time.sleep(1)
	baseObjectPath.buttons()[0].click()
	time.sleep(1)
	
	if not baseObjectPath.staticTexts()["Sheet 1"] and baseObjectPath.staticTexts()["Sheet 2"]:
		bs_common.writeItemToLogFile("INFO - failed to find new spreadsheet tabs")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg) 
	
	keyboard.pressKey_(kVK_Control)
	baseObjectPath.staticTexts()["Sheet 2"].click()
	keyboard.releaseKey_(kVK_Control)
	baseObjectPath.menuItems()["Delete Sheet"].staticTexts()["Delete Sheet"].click()
	time.sleep(1)
	
	if baseObjectPath.staticTexts()["Sheet 2"]:
		bs_common.writeItemToLogFile("INFO - failed to delete tab 'Sheet 2'")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg) 
		
	baseObjectPath.staticTexts()["Sheet 1"].click()
	baseObjectPath.staticTexts()["Sheet 1"].doubleClick()
	keyboard.typeString_("Bills")
	keyboard.typeVirtualKey_(kVK_Return)
	time.sleep(1)
	
	if not baseObjectPath.staticTexts()["Bills"]:
		bs_common.writeItemToLogFile("INFO - failed to rename tab")
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg) 
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
	
def selectGilliganTemplate(appName, inFileName):	
	stepName = "select template: "+ inFileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]

	filePattern = re.compile('https://www\.icloud\.com/applications/bight/(.+)/bight/templates/(.+)/en-us/source/(.+)/(.+)\.(.+)')
	
	fileFindTries = 0
	fileFound = False
	
	while fileFindTries < 4 and not fileFound:
		for image in baseObjectPath.images():
			urlString = image.url()
			result = filePattern.match(urlString)

			if "ubiquityws.icloud.com" in urlString:
# 				This is a document manager object. Skip.			
				continue
							
			if result is None:
				sys.stderr.write("%s '%s' could not match pattern in url '%s'\n" % (appName, inFileName, urlString))
				return False
				
			urlAppName = result.groups()[2]
			urlAppVersion = result.groups()[0]
			urlAppfile = result.groups()[3]
			
			strippedFileName = "".join(inFileName.split())
			
			if not urlAppfile == strippedFileName:
				continue
				
			else:
				image.click()
				time.sleep(1)
				baseObjectPath.buttons().firstWithPredicate_("name contains 'Choose'").click()
				startTimer = time.time()
				fileFound = True
				return startTimer
				break
				
		keyboard.typeVirtualKey_(kVK_DownArrow)
		time.sleep(1)
		fileFindTries = fileFindTries + 1
	
	if fileFindTries == 2 and not fileFound:
		sys.stderr.write(appName + " '"+ inFileName +"' not found\n")
		return False
		
def validateGearMenu(testcaseId, appName):
	stepName = "validate gearMenu"
	myReturnStatus = True
# 	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName]
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Document and Sort Options'").isValid():
		myAppFileType = getFileTypeFromAppName(appName)
		
		myMenuItem = ["Create", "Upload", "Download", "Duplicate", "Delete", "Share", "Send"]

		target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
		
		itemIndex = 0
		for item in myMenuItem:							
			if target.processes()["Safari"].mainWindow().webViews()[0].webViews()[appName].menuItems()[itemIndex].staticTexts().firstWithPredicate_("value contains '"+ item +"'"):
				msg = "PASS - gearMenu - '"+ item +"'"
				bs_common.writeItemToLogFile(msg)
				itemIndex = itemIndex + 1
			
			else:
				msg = "FAIL - gearMenu - '"+ item +"'"
				bs_common.writeItemToLogFile(msg)
				itemIndex = itemIndex + 1
				myReturnStatus = False
	
	if not myReturnStatus:
		keyboard.typeVirtualKey_(kVK_Escape)
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
	else:
		keyboard.typeVirtualKey_(kVK_Escape)
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
		
		
		
		
		
		