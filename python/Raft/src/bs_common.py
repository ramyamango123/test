import json, uuid, re, datetime, socket, raft.core.hostinfo
		
def cleanupConfiguration():
	msg = "INFO - cleanupConfiguration executed"
	writeToConsole(msg)
	writeToLogFile(msg)

	configurationData = getConfiguration()
	configurationData["runPath"] = ""
	configurationData["UUID"] = ""
	
	with open("config.json", 'w+') as outFile:	
		json.dump(configurationData, outFile)
		outFile.close()	
				
def fileDeleteFromExported(inFileName, inExportedFormat):
	stepName = "delete file "+ inFileName	
	configurationData = getConfiguration()
	
	myFileExportPath = configurationData["fileExportPath"]

	target.dock().dockItems()["Finder"].click()
	keyboard.typeString_withModifiersMask_("g", (kUIAShiftKeyMask|kUIACommandKeyMask))
	keyboard.typeString_(myFileExportPath)	
	target.processes()["Finder"].mainWindow().sheet().buttons()["Go"].click()
	
	for item in target.processes()["Finder"].mainWindow().toolbar().radioButtons():
		if item.value() == 1:
			myFileView = item.name()
			break
	
	target.processes()["Finder"].mainWindow().toolbar().radioButtons()["list view"].click()	
	
# 	(myFileName, myFileExtension) = inFileName.split(".")

	while target.processes()["Finder"].mainWindow().outlines()["list view"].visibleColumns()["Name"].textFields().firstWithPredicate_("value like '"+ inFileName +"*."+ inExportedFormat +"'"):
		target.processes()["Finder"].mainWindow().outlines()["list view"].visibleColumns()["Name"].textFields().firstWithPredicate_("value like '"+ inFileName +"*."+ inExportedFormat +"'").click()	
		time.sleep(1)
		keyboard.typeVirtualKey_withModifiersMask_(kVK_Delete, (kUIACommandKeyMask))
		
	
	target.processes()["Finder"].mainWindow().toolbar().radioButtons()[myFileView].click()
		
def getConfiguration():
	try:
		open("config.json")
		
	except IOError as e:
		sys.stderr.write("issue retrieving config.json. terminating.\n")	
		logFail()
		exit()	
	
	fileContents = open("config.json").read()
	fileObject = json.loads(fileContents)	

	if fileObject["UUID"] == "": 	
		getUUID(fileObject)
	
	if fileObject["runPath"] == "":
		getRunPath(fileObject)
		
	return fileObject	
	
def getDataFile(inFile):
	try:
		open(inFile)
		
	except IOError as e:
		msg = "failed to retrieve file: "+ inFile
		writeToConsole(msg)
		writeToLogFile(msg)
		exit()
	
	fileContents = open(inFile).read()
	fileObject = json.loads(fileContents)
	return fileObject	
			
def getResultsFile():
	configurationData = getConfiguration()
	myResultsFile = configurationData["runPath"] +"/"+ configurationData["UUID"] +"_results.json"
	
	try:
		open(myResultsFile)	
		
	except IOError as e:
		if not os.path.exists(configurationData["runPath"]): os.makedirs(configurationData["runPath"])
		outFile = open(myResultsFile, 'w+')
		outFile.close()		
		
	if os.stat(myResultsFile)[6] == 0:	
		writeResultsHeader(configurationData)
		
	fileContents = open(myResultsFile).read()
	fileObject = json.loads(fileContents)
	return fileObject
	
def getRunPath(configurationData):
	storedRunPath = configurationData["runPath"]
	storedUUID = configurationData["UUID"]
	currentDate = time.strftime("%Y.%m.%d",time.localtime())
	
	if storedRunPath == currentDate +"/"+ storedUUID:
		if not os.path.exists(storedRunPath):		
			os.makedirs(storedRunPath)
			
	else:
		if not os.path.exists(currentDate):
			os.makedirs(currentDate)
				
		if not os.path.exists(currentDate +"/"+ storedUUID):
			os.makedirs(currentDate +"/"+ storedUUID)	
			configurationData["runPath"] = currentDate +"/"+ storedUUID
			
			with open("config.json", 'w+') as outFile:	
				json.dump(configurationData, outFile)
				outFile.close()	
				
def getSafariVersion():
	import plistlib
# 	if not target.frontProcess().withName_("Safari"):
# 		target.launchApplication_("Safari")
# 		
# 	time.sleep(1)
# 	target.processes()["Safari"].menus()["Safari"].menuItems()["About Safari"].choose()
# 	safariBrowserVersion = target.processes()["Safari"].frontWindow().staticTexts().firstWithPredicate_("name contains 'Version'").name()[8:]
# 	target.processes()["Safari"].frontWindow().closeButton().click()
# 	keyboard.typeString_withModifiersMask_("q",(kUIACommandKeyMask))
# 	return safariBrowserVersion
	try:
		open("/Applications/Safari.app/Contents/version.plist")
	
	except IOError as e:
		msg = "failed to find Safari plist"
		writeToConsole(msg)
		writeToLogFile(msg)
		return ""
			
	safariVersionPlistFile = plistlib.readPlist("/Applications/Safari.app/Contents/version.plist")
	safariVersion = safariVersionPlistFile["CFBundleShortVersionString"]
	safariBuild = safariVersionPlistFile["CFBundleVersion"]
	mySafariBuild = safariVersion +" ("+ safariBuild + ")"
	return mySafariBuild
					
def getUUID(configurationData):
	if configurationData["UUID"] == "":
		newUUID = str(uuid.uuid1())
		configurationData["UUID"] = newUUID
		
		with open("config.json", 'w+') as outFile:	
			json.dump(configurationData, outFile)
			outFile.close()	
	
def printResultsData():
	myResults = getResultsFile()
	print "#################################################################"
	print "results: ", myResults["results"]
	print "#################################################################"	
	
def quitSafari():
	if target.frontProcess().withName_("Safari"):
		keyboard.typeString_withModifiersMask_("q",(kUIACommandKeyMask))
		
	if not target.frontProcess().withName_("Safari"):
		msg = "safari process no longer running"
		writeToConsole(msg)
		writeToLogFile(msg)

	else:
		msg = "safari process still running"
		writeToConsole(msg)
		writeToLogFile(msg)
		logFail()	
	
def resetItemStatus(inFile):

	try:
		open(inFile)
		
	except IOError as e:
		msg = "failed to retrieve file: "+ inFile
		writeToConsole(msg)
		writeToLogFile(msg)
		exit()
	
	fileContents = open(inFile).read()
	fileObject = json.loads(fileContents)
	
	try:
		fileObject["appTemplates"]
		
	except KeyError as e:
		msg = "failed to find 'appTemplates' key in file: "+ inFile
		writeToConsole(msg)
		writeToLogFile(msg)
		exit()
	
	for key in fileObject["appTemplates"]:
		fileObject["appTemplates"][key]["completed"] = 0	
		
	with open(inFile, 'w+') as outFile:
		json.dump(fileObject, outFile)
	
def setCurrentIteration(inFile, inIteration):
	try:
		open(inFile)
		
	except IOError as e:
		msg = "failed to retrieve file: "+ inFile
		writeToConsole(msg)
		writeToLogFile(msg)
		exit()
	
	fileContents = open(inFile).read()
	fileObject = json.loads(fileContents)
	
	try:
		fileObject["iteration"]
		
	except KeyError as e:
		msg = "failed to find 'iteration' key in file: "+ inFile
		writeToConsole(msg)
		writeToLogFile(msg)
		exit()
	
	fileObject["iteration"] = inIteration
	
	with open(inFile, 'w+') as outFile:
		json.dump(fileObject, outFile)
		outFile.close()
		
def takeScreenshotOfError():
	configurationData = getConfiguration()
	storedUUID = configurationData["UUID"]
	storedRunPath = configurationData["runPath"]
	os.system("screencapture "+ storedRunPath +"/"+ storedUUID +"_"+ time.strftime("%I_%M_%S_%p",time.localtime()) +".png")

def transactionFailedContinue(msg):
	writeToLogFile(msg)
	writeToConsole(msg)
	takeScreenshotOfError()

def transactionFailedExit(msg):
	writeToLogFile(msg)
	writeToConsole(msg)
	takeScreenshotOfError()
	cleanupConfiguration()
	logFail()
	exit()
	
def writeConfigurationDataToLogFile():
	configurationData = getConfiguration()
	writeToLogFile("#############################################################")
	writeToLogFile("UUID:      "+ configurationData["UUID"])
	writeToLogFile("runPath:   "+ configurationData["runPath"])
	writeToLogFile("iCloudEnv: "+ configurationData["iCloudAccount"]["environment"])
	writeToLogFile("userid:    "+ configurationData["iCloudAccount"]["userid"])
	writeToLogFile("password:  "+ configurationData["iCloudAccount"]["password"])
	writeToLogFile("#############################################################")
	
def writeFunctionalTestResults(testcaseId, stepName, status):
	configurationData = getConfiguration()
	myResultsFile = configurationData["runPath"] +"/"+ configurationData["UUID"] +"_results.json"
	
	try:
		open(myResultsFile)	
		
	except IOError as e:	
		getResultsFile()	

	fileContents = open(myResultsFile).read()	
	fileObject = json.loads(fileContents)	

	doneChecking = False
	strTestCaseID = str(testcaseId)
	newResults = {}
	newResults["step"] = stepName
	newResults["status"] = status	
		
	if not strTestCaseID in fileObject["results"]:
		fileObject["results"][strTestCaseID] = {}
		fileObject["results"][strTestCaseID]["steps"] = []
	
	if not fileObject["results"][strTestCaseID]["steps"]:
			fileObject["results"][strTestCaseID]["steps"].append(newResults)
			doneChecking = True
			
	while not doneChecking:	
		for key in fileObject["results"][strTestCaseID]["steps"]:
			if key["step"] == stepName:	
				doneChecking = True
				break	
		
		if not doneChecking:	
			for key in fileObject["results"]:	
				if key == strTestCaseID:
					fileObject["results"][strTestCaseID]["steps"].append(newResults)
					doneChecking = True
					break
				
	with open(myResultsFile, 'w+') as outFile:
		json.dump(fileObject, outFile)
		outFile.close()				

def writePerformanceTestResults(testcaseId, stepName, status, timingResult):
	configurationData = getConfiguration()
	myResultsFile = configurationData["runPath"] +"/"+ configurationData["UUID"] +"_results.json"
	fileContents = open(myResultsFile).read()	
	fileObject = json.loads(fileContents)	

	doneChecking = False
	strTestCaseID = str(testcaseId)
	newResults = {}
	newResults["step"] = stepName
	newResults["status"] = status	
	newResults["timings"] = []
	newResults["timings"].append(timingResult)
		
	if not strTestCaseID in fileObject["results"]:
		fileObject["results"][strTestCaseID] = {}
		fileObject["results"][strTestCaseID]["steps"] = []
	
	if not fileObject["results"][strTestCaseID]["steps"]:
			fileObject["results"][strTestCaseID]["steps"].append(newResults)
			doneChecking = True
	 		
	while not doneChecking:	
		for key in fileObject["results"][strTestCaseID]["steps"]:
			if key["step"] == stepName:	
				key["timings"].append(timingResult)
				doneChecking = True
				break	
		
		if not doneChecking:	
			for key in fileObject["results"]:	
				if key == strTestCaseID:
					fileObject["results"][strTestCaseID]["steps"].append(newResults)
					doneChecking = True
					break
	
	msg = str(newResults)
	writeToLogFile(msg)
	writeToConsole(msg)
				
	with open(myResultsFile, 'w+') as outFile:
		json.dump(fileObject, outFile)
		outFile.close()		
		
def writeItemStatus(inFile, item, status):
	
	try:
		open(inFile)
		
	except IOError as e:
		msg = "failed to retrieve file: "+ inFile
		writeToConsole(msg)
		writeToLogFile(msg)
		exit()
	
	fileContents = open(inFile).read()
	fileObject = json.loads(fileContents)
	
	try:
		fileObject["appTemplates"]
		
	except KeyError as e:
		msg = "failed to find 'appTemplates' key in file: "+ inFile
		writeToConsole(msg)
		writeToLogFile(msg)
		exit()
	
	fileObject["appTemplates"][item]["completed"] = status
	
	with open(inFile, 'w+') as outFile:
		json.dump(fileObject, outFile)
		outFile.close()
		
def writeOSXSystemProfile():
	import subprocess
	configurationData = getConfiguration()
	mySystemProfile = configurationData["runPath"] +"/"+ configurationData["UUID"] +"_SystemProfileBasic.spx"
	
	try:
		open(mySystemProfile)
	
	except IOError as e:
		if not os.path.exists(configurationData["runPath"]): os.makedirs(configurationData["runPath"])
		outFile = open(mySystemProfile, 'w+')
		outFile.close()
	
	with open(mySystemProfile, "r+") as out:
		subprocess.Popen(["system_profiler", "-xml"], stdout=out)
	
	time.sleep(5)	
	while target.processes()["system_profiler"]:
		time.sleep(1)
		
	msg = "INFO - OSX system profile stored"
	writeToConsole(msg)
	writeToLogFile(msg)
	
def writeToConsole(msg):
	sys.stderr.write("*** "+ msg +"\n")	
		
def writeToLogFile(msg):	
	configurationData = getConfiguration()
	myLogFile = configurationData["runPath"] +"/"+ configurationData["UUID"] +"_logfile.txt"
	
	try:
		open(myLogFile)
		
	except IOError as e:
		if not os.path.exists(configurationData["runPath"]): os.makedirs(configurationData["runPath"])
		outFile = open(myLogFile, 'w+')
		outFile.close()		
		
	outFile = open(myLogFile, 'r+')
	outFile.read()
	outFile.write(time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime()) +":  "+ msg +"\n")
	outFile.close()	
	
def writeResultsHeader(configurationData):
	myResultsFile = configurationData["runPath"] +"/"+ configurationData["UUID"] +"_results.json"	
	with open(myResultsFile, 'w+') as outFile:
		dictionary = {}
		dictionary["version"] = 1
		dictionary["type"] = "Performance"
		dictionary["configurations"] = {}
		dictionary["configurations"]["date"] = str(datetime.date.today())
		dictionary["configurations"]["machineName"] = str(socket.gethostname())
# 		dictionary["configurations"]["machineIP"] = socket.gethostbyname(socket.gethostname())
# 		dictionary["configurations"]["osType"] = "OS X"
# 		dictionary["configurations"]["osVersion"] = raft.core.hostinfo.system_version()[0] +" "+ raft.core.hostinfo.system_version()[1]
		dictionary["configurations"]["browserType"] = "Safari"
		dictionary["configurations"]["browserVersion"] = getSafariVersion()
# 		dictionary["configurations"]["browserVersion"] = ""
		dictionary["configurations"]["environment"] = "iCloud2"
		dictionary["configurations"]["impairment"] = ""
		dictionary["results"] = {}
		json.dump(dictionary, outFile)
		outFile.close()
		
def writeResultsHeaderDataToLogFile():
	myResults = getResultsFile()
	writeToLogFile("#############################################################")
	writeToLogFile("type:             "+ str(myResults["type"]))
	writeToLogFile("version:          "+ str(myResults["version"]))
	writeToLogFile("machine name:     "+ myResults["configurations"]["machineName"])
# 	writeToLogFile("machine ip:       "+ myResults["configurations"]["machineIP"])
# 	writeToLogFile("os type:          "+ myResults["configurations"]["osType"])
# 	writeToLogFile("os version:       "+ myResults["configurations"]["osVersion"])
	writeToLogFile("browser type:     "+ myResults["configurations"]["browserType"])
	writeToLogFile("browser version:  "+ myResults["configurations"]["browserVersion"])
	writeToLogFile("date:             "+ myResults["configurations"]["date"])
	writeToLogFile("environment:      "+ myResults["configurations"]["environment"])
	writeToLogFile("impairment:       "+ myResults["configurations"]["impairment"])
	writeToLogFile("#############################################################")

### OLD FUNCTIONS ###

def renameDirectory(inFile):
	oldDirPath = os.path.dirname(inFile)
	oldDirPathLessOne = os.path.dirname(os.path.dirname(inFile))
	dirToRename = os.path.basename(os.path.normpath(oldDirPath))
	newDirName = time.strftime("%Y.%m.%d",time.localtime()) +" "+ dirToRename
	newDirPath = oldDirPathLessOne+"/"+newDirName
	os.rename(oldDirPath, newDirPath)
	return str(newDirPath)
	
def fileRenameWithUUID(inFile):
	filePath = os.path.dirname(inFile)
	fileName = os.path.basename(inFile)	
	(fileNameNoExtenstion, fileNameExtension) = os.path.splitext(fileName)
	newUUID = getUUID()
	newFileName = fileNameNoExtenstion+"_"+newUUID+fileNameExtension
	fileRenamed = filePath +"/"+ newFileName
	os.rename(inFile, fileRenamed)