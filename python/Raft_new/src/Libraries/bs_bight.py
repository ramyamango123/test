libraryBuildVersion = "2013.05.13 v1.0.0"
###################################################################
import bs_common
###################################################################
		
def exitBightApp(testcaseId):
	stepName = "exit bight app"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")

	baseObjectPath.click()
	time.sleep(1)

	if baseObjectPath.buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'"):
		baseObjectPath.buttons().firstWithPredicate_("helpTag like 'Go to the iCloud home screen'").click()
		time.sleep(1)
		
	if target.processes()["Safari"].windows()["iCloud"].webViews()[0].buttons()["Sign Out"].isEnabled():
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
	
	else:
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
def fileRename(testcaseId, appName, inFileToRename, inFileToRenameTo):
	stepName = "file rename from '"+ inFileToRename +"' to '"+ inFileToRenameTo +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")

	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ appName +"'").click()	

	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileToRename +"*'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)

	baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileToRename +"*'").doubleClick()	
	keyboard.typeString_(inFileToRenameTo)
	keyboard.typeVirtualKey_(kVK_Return)
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileToRenameTo +"*'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
def fileReplace(testcaseId, appName, inFileName, inNewFileName):
	stepName = "file replace - '"+ inFileName +"' with '"+ inNewFileName +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")

	myConfigData = bs_common.getConfigurationData()
	myFileImportPath = myConfigData["fileImportPath"]
	
	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ appName +"'").click()	

	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
			
	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(myAppName)
	
	baseObjectPath.menuItems()["Upload "+ myAppFileType +"..."].staticTexts()["Upload "+ myAppFileType +"..."].click()	
	keyboard.typeString_withModifiersMask_("g", (kUIAShiftKeyMask|kUIACommandKeyMask))
	keyboard.typeString_(myFileImportPath)
	target.processes()["Safari"].mainWindow().sheet().buttons()["Go"].click()
	
	target.processes()["Safari"].mainWindow().sheet().radioButtons()["list view"].click()	
	target.processes()["Safari"].mainWindow().sheet().outlines()["list view"].visibleColumns()["Name"].textFields()[0].click()
	
	for i in target.processes()["Safari"].mainWindow().sheet().outlines()["list view"].visibleColumns()["Name"].textFields():
		if i.value() == item:
			target.processes()["Safari"].mainWindow().sheet().buttons()["Choose"].click()
			break
		
		else:
			keyboard.typeVirtualKey_(kVK_DownArrow)
		
	if baseObjectPath.buttons()["Replace\n"]:
		baseObjectPath.buttons()["Replace\n"].click()
		
def fileDelete(testcaseId, appName, inFileName):
	stepName = "file delete - "+ appName +" / "+ inFileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")
	
	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ appName +"'").click()	
	
	myFileName, myFileExtension = os.path.splitext(inFileName)	
	
	if not baseObjectPath.staticTexts()[myFileName]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	myBounds = baseObjectPath.staticTexts()[myFileName].bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	mouse.click_((x, yOffset))

	keyboard.typeVirtualKey_(kVK_Delete)
		
	time.sleep(1)
	baseObjectPath.groups()[0].buttons()["Delete"].click()
	time.sleep(1)
	
	if baseObjectPath.staticTexts()[myFileName]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)

def folderRename(testcaseId, appName, inFolderToRename, inFolderRenameTo):
	stepName = "folder rename from '"+ inFolderToRename +"' to '"+ inFolderRenameTo +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")

	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ appName +"'").click()	

	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFolderToRename +"*'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
	
# 	baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFolderToRename +"*'").click()	
	baseObjectPath.staticTexts()[inFolderToRename].click()
	
# 	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFolderToRename +"*'").bounds()
	myBounds = baseObjectPath.staticTexts()[inFolderToRename].bounds()

	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y + 60)

	mouse.click_((x, yOffset))		

	keyboard.typeString_(inFolderRenameTo)
	keyboard.typeVirtualKey_(kVK_Return)
		
# 	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFolderRenameTo +"*'"):
	if not baseObjectPath.staticTexts()[inFolderRenameTo]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		yOffset = (y - 90)
		mouse.click_((x, yOffset))
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
def folderDelete(testcaseId, appName, inFolderName):
	stepName = "folder delete - "+ appName +" / "+ inFolderName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")
	
	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ appName +"'").click()		
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ inFolderName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ inFolderName +"'").click()
	
	while baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ inFolderName +"*'"):			
		if baseObjectPath.staticTexts().firstWithPredicate_("name like 'No Presentations'").isValid():
			bs_common.writeItemToLogFile("INFO - Folder should be deleted now")
				
		else:	
			if baseObjectPath.staticTexts().firstWithPredicate_("name like 'Presentation*'"):
				myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name like 'Presentation*'").bounds()
				x = Foundation.NSMidX(myBounds)
				y = Foundation.NSMidY(myBounds)
				yOffset = (y - 60)
	
				mouse.click_((x, yOffset))		
				keyboard.typeVirtualKey_(kVK_Delete)	
				time.sleep(1)
				baseObjectPath.groups()[0].buttons()["Delete"].click()
		
	if baseObjectPath.staticTexts().firstWithPredicate_("name like '"+ inFolderName +"*'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)

def gearMenu_fileDownload(testcaseId, appName, inFileName, inFileFormat):
	stepName = "gearMenu file download '"+ inFileName +"' as '"+ inFileFormat +"'"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")

	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ appName +"'").click()	
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	mouse.click_((x, yOffset))	
	
	myAppFileType = getFileTypeFromAppName(appName)
	
	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()

	baseObjectPath.menuItems()["Download "+ myAppFileType +"..."].staticTexts()["Download "+ myAppFileType +"..."].click()	
	
	if baseObjectPath.staticTexts()["Choose a download format."]:
		baseObjectPath.staticTexts()[inFileFormat].click()
		
	timeout = 0
	if baseObjectPath.staticTexts().firstWithPredicate_("value like '*Creating a file for download*'"):
		while timeout < 120:
			if bool(baseObjectPath.staticTexts().firstWithPredicate_("value like '*Creating a file for download*'").isValid()):
				time.sleep(1)
				timeout = timeout + 1
				
			else:
				if timeout > 0:
					break	

	bs_common.writeResults(testcaseId, stepName, "PASS")	
	msg = "PASS - "+ stepName
	bs_common.writeItemToLogFile(msg)	
							
def gearMenu_fileDuplicate(testcaseId, appName, inFileName):
	stepName = "gearMenu file duplicate - "+ inFileName
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")
	
	myAppFileType = getFileTypeFromAppName(appName)

	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ appName +"'").click()	

	if not baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	myBounds = baseObjectPath.staticTexts().firstWithPredicate_("name beginswith '"+ inFileName +"'").bounds()
	x = Foundation.NSMidX(myBounds)
	y = Foundation.NSMidY(myBounds)
	yOffset = (y - 60)

	mouse.click_((x, yOffset))	
	
	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()

	baseObjectPath.menuItems()["Duplicate "+ myAppFileType].staticTexts()["Duplicate "+ myAppFileType].click()	
	
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ inFileName +"*copy'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)

def gearMenu_fileUpload(testcaseId, item):
	stepName = "gearMenu file upload - "+ item
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0].webViews().firstWithPredicate_("className like 'UIAWebView'")

	myConfigData = bs_common.getConfigurationData()
	myFileImportPath = myConfigData["fileImportPath"]
	
	myFileName, myAppName = getAppNameAndFileName(item)
	
	baseObjectPath.buttons().firstWithPredicate_("name contains '"+ myAppName +"'").click()	
	baseObjectPath.buttons().firstWithPredicate_("name contains 'Document and Sort Options'").click()
	
	myAppFileType = getFileTypeFromAppName(myAppName)
	
	baseObjectPath.menuItems()["Upload "+ myAppFileType +"..."].staticTexts()["Upload "+ myAppFileType +"..."].click()	
	keyboard.typeString_withModifiersMask_("g", (kUIAShiftKeyMask|kUIACommandKeyMask))
	keyboard.typeString_(myFileImportPath)
	target.processes()["Safari"].mainWindow().sheet().buttons()["Go"].click()
	
	target.processes()["Safari"].mainWindow().sheet().radioButtons()["list view"].click()	
	target.processes()["Safari"].mainWindow().sheet().outlines()["list view"].visibleColumns()["Name"].textFields()[0].click()
	
	for i in target.processes()["Safari"].mainWindow().sheet().outlines()["list view"].visibleColumns()["Name"].textFields():
		if i.value() == item:
			target.processes()["Safari"].mainWindow().sheet().buttons()["Choose"].click()
			break
		
		else:
			keyboard.typeVirtualKey_(kVK_DownArrow)
	
	time.sleep(2)
			
	if baseObjectPath.buttons()["Replace\n"]:
		baseObjectPath.buttons()["Replace\n"].click()
		
	timeout = 0
	if baseObjectPath.staticTexts().firstWithPredicate_("value like '*Uploading*'"):
		while timeout < 120:
			if bool(baseObjectPath.staticTexts().firstWithPredicate_("value like '*Uploading*'").isValid()):
				time.sleep(1)
				timeout = timeout + 1
				
			else:
				if timeout > 0:
					break	
		
	if not baseObjectPath.staticTexts().firstWithPredicate_("name like '*"+ myFileName +"*'"):
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")	
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)

def getAppNameAndFileName(inItem):
	myFileName, myFileExtension = os.path.splitext(inItem)
	
	if myFileExtension == ".key":
		myAppName = "Keynote"
		
	elif myFileExtension == ".ppt":
		myAppName = "Keynote"
		
	elif myFileExtension == ".pages":
		myAppName = "Pages"
		
	elif myFileExtension == ".doc":
		myAppName = "Pages"
		
	elif myFileExtension == ".numbers":
		myAppName = "Numbers"
		
	elif myFileExtension == ".xls":
		myAppName = "Numbers"
		
	return myFileName, myAppName
	
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
		
def loadBightApp(testcaseId):
	stepName = "load bight app"
	baseObjectPath = target.processes()["Safari"].mainWindow().webViews()[0]
	
	if not target.processes()["Safari"].mainWindow().webViews()[0].buttons()["iWork"]:
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)
		
	else:
		if target.processes()["Safari"].mainWindow().toolbar().textFields()[0]:
			target.processes()["Safari"].mainWindow().toolbar().textFields()[0].click()
			time.sleep(1)
			target.processes()["Safari"].mainWindow().toolbar().textFields()[0].click()
			keyboard.typeString_("/#bight")
			keyboard.typeVirtualKey_(kVK_Return)
			
	time.sleep(5)
	
	if target.processes()["Safari"].mainWindow().webViews()[0].webViews()["AppTitle.bight"]:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - "+ stepName
		bs_common.scriptFailedExit(msg)