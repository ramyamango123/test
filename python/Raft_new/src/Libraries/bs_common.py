libraryBuildVersion = "2013.05.15 v1.0.4"
##########################################################################################
import json, uuid, re, datetime, socket, inspect, subprocess, plistlib, shutil, shlex, pprint#, raftlibs
##########################################################################################
def fileDeleteFromOSX(inFileName):
	stepName = "delete file "+ inFileName	
	myConfigData = getConfigurationData()
	myFileExportPath = myConfigData["fileExportPath"]
	if not target.frontProcess().name() == "Finder":
		target.dock().dockItems()["Finder"].click()
	
	if not os.path.basename(os.path.normpath(myFileExportPath)) == target.processes()["Finder"].mainWindow().name():
		keyboard.typeString_withModifiersMask_("g", (kUIAShiftKeyMask|kUIACommandKeyMask))
		keyboard.typeString_(myFileExportPath)	
		time.sleep(2)
		target.processes()["Finder"].mainWindow().sheet().buttons()["Go"].click()
		
	if not target.processes()["Finder"].mainWindow().toolbar().radioButtons()["list view"].value == 1:	
			target.processes()["Finder"].mainWindow().toolbar().radioButtons()["list view"].click()		

	(myFileName, myFileExtension) = inFileName.split(".")

	while target.processes()["Finder"].mainWindow().outlines()["list view"].visibleColumns()["Name"].textFields().firstWithPredicate_("value like '"+ myFileName +"*."+ myFileExtension +"'"):
		target.processes()["Finder"].mainWindow().outlines()["list view"].visibleColumns()["Name"].textFields().firstWithPredicate_("value like '"+ myFileName +"*."+ myFileExtension +"'").click()	
		time.sleep(1)
		keyboard.typeVirtualKey_withModifiersMask_(kVK_Delete, (kUIACommandKeyMask))
		
def fileRename(inAppCodeName, inFileName):
	myFilePath = os.path.dirname(inFileName)
	myFileName = os.path.basename(inFileName)	
	(myFileNameNoExtenstion, myFileNameExtension) = os.path.splitext(myFileName)
	newFileName = inAppCodeName +"TestResults."+ myFileNameExtension 
	fileRenamed = myFilePath +"/"+ newFileName
	
	try:
		os.rename(inFileName, fileRenamed)
	
	except:
		msg = "FAIL - bs_common.fileRename: file rename failed"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
def formatResultsData():
	myConfigData = getConfigurationData()
	myResultsFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_results.json"
	
	try:
		fileContents = open(myResultsFile).read()	
		fileObject = json.loads(fileContents)	
		
	except:
		msg = "FAIL - problem loading results data"
		scriptFailedExit(msg)

	with open(myResultsFile, 'w+') as outFile:
		json.dump(pprint.pprint(fileObject), outFile)
		outFile.close()			
			
def generateResultsEmail(inAppCodeName):
	myConfigData = getConfigurationData()
	myEmailFilePath = "/Users/userAccount/Automation/Email/CSV"
	myCsvFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_csvResults.csv"
	myResultsFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_results.json"

	try:	
		shutil.copy(myCsvFile, myEmailFilePath)
		shutil.copy(myResultsFile, myEmailFilePath)

	except:
		msg = "FAIL - bs_common.generateResultsEmail: file copy failed"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
	myCopiedCsvFile = myEmailFilePath +"/"+ myConfigData["UUID"] +"_csvResults.csv"
	myCopiedResultsFile = myEmailFilePath +"/"+ myConfigData["UUID"] +"_results.json"
	
	filesToRename = [myCopiedCsvFile, myCopiedResultsFile]
	try:
		for item in filesToRename:
			fileRename(inAppCodeName, item)

	except:
		msg = "FAIL - bs_common.generateResultsEmail: file rename failed"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
	
	executeCommands = ['javac -cp ".:opencsv.jar:mail.jar:selenium.jar" -d . Email_Setup.java', 'java -cp ".:opencsv.jar:mail.jar:selenium.jar" Email_Setup']
	for item in executeCommands:
		args = shlex.split(item)
		subprocess.Popen(args, cwd='/Users/userAccount/Automation/Email/')
		time.sleep(10)

def getConfigurationData():
	myConfigFile = "../Preferences/config.json"
	
	try:
		fileContents = open(myConfigFile).read()
		fileObject = json.loads(fileContents)		
		return fileObject
		
	except IOError as e:
		msg = "FAIL - bs_common.getConfiguration: IOError occurred"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
	except:
		msg = "FAIL - bs_common.getConfiguration: uncaught error occurred"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
def getResultsData():
	myConfigData = getConfigurationData()
	myResultsFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_results.json"

	try:
		open(myResultsFile)
		
	except IOError as e:
		createNewFile = open(myResultsFile, 'w+')
		createNewFile.close()
				
	if os.stat(myResultsFile)[6] == 0:	
		setResultsHeaderData()

	try:		
		fileContents = open(myResultsFile).read()
		fileObject = json.loads(fileContents)
		return fileObject
		
	except:
		msg = "FAIL - bs_common.getResultsData: failed to load results data"
		logFail()
		exit()
	
def getSafariVersion():
	try:
		open("/Applications/Safari.app/Contents/version.plist")
	
	except IOError as e:
		msg = "failed to find Safari plist"
		writeItemToLog(msg)
		return ""
			
	safariVersionPlistFile = plistlib.readPlist("/Applications/Safari.app/Contents/version.plist")
	safariVersion = safariVersionPlistFile["CFBundleShortVersionString"]
	safariBuild = safariVersionPlistFile["CFBundleVersion"]
	mySafariBuild = safariVersion +" ("+ safariBuild + ")"
	return mySafariBuild
	
def quitSafari():
	if target.processes()["Safari"]:
		keyboard.typeString_withModifiersMask_("q",(kUIACommandKeyMask))
# 		raftlibs.sui.quitApp("Safari")
	
	time.sleep(5)
				
	if not target.processes()["Safari"]:
		msg = "PASS - safari process no longer running"
		writeItemToLogFile(msg)

	else:
		msg = "FAIL - safari process still running"
		scriptFailedExit(msg)
		
def resetConfigurationData():
	myConfigFile = "../Preferences/config.json"
	myConfigData = getConfigurationData()
	
	myConfigData["UUID"] = ""
	myConfigData["resultsPath"] = ""
	
	try:
		with open(myConfigFile, 'w+') as outFile:
			json.dump(myConfigData, outFile)
			outFile.close()
			
	except IOError as e:
		msg = "FAIL - bs_common.resetConfigurationData: IOError writing data occurred"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
	except:
		msg = "FAIL - bs_common.resetConfigurationData: uncaught error occurred while writing data"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
def resetSafari():
	stepName = "Reset Safari"
	if not target.frontProcess().withName_("Safari"):
		target.launchApplication_("Safari")

	target.processes()["Safari"].menus()["Safari"].click()
	target.processes()["Safari"].menus()["Safari"].menuItems().firstWithPredicate_("name like '*Reset*'").click()
	target.processes()["Safari"].frontWindow().buttons()["Reset"].click()
	writeItemToLogFile("PASS - "+ stepName)
		
def scriptFailedExit(msg):
	writeItemToLogFile(msg)
	takeScreenshotOfError()
	resetConfigurationData()
	logFail()
	exit()
	
def setConfigurationData():
	myConfigFile = "../Preferences/config.json"
	myConfigData = getConfigurationData()
	
	myConfigData["UUID"] = str(uuid.uuid1())
	
	generatedUUID = myConfigData["UUID"]
	currentDate = time.strftime("%Y.%m.%d",time.localtime())
	newResultsPath = "../Results/"+ currentDate +"/"+ generatedUUID
	
	
	if not os.path.exists(newResultsPath):
		os.makedirs(newResultsPath)
			
	myConfigData["resultsPath"] = newResultsPath
				
	try:
		with open(myConfigFile, 'w+') as outFile:
			json.dump(myConfigData, outFile)
			outFile.close()
				
	except IOError as e:
		msg = "FAIL - bs_common.setConfigurationData: IOError writing config data occurred"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
	except:
		msg = "FAIL - bs_common.configFileSetVariables: uncaught error occurred while writing config data"
		sys.stderr.write("*** "+ msg +"\n")
		logFail()
		exit()
		
def setResultsHeaderData():
	myConfigData = getConfigurationData()
	myResultsFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_results.json"	
	
	with open(myResultsFile, 'w+') as outFile:
		dictionary = {}
		dictionary["version"] = myConfigData["version"]
		dictionary["type"] = myConfigData["scriptType"]
		dictionary["configurations"] = {}
		dictionary["configurations"]["date"] = str(datetime.date.today())
		dictionary["configurations"]["machineName"] = str(socket.gethostname())
		dictionary["configurations"]["browserType"] = "Safari"
		dictionary["configurations"]["browserVersion"] = getSafariVersion()
		dictionary["configurations"]["environment"] = myConfigData["iCloudAccount"]["environment"]
		dictionary["configurations"]["impairment"] = ""
		dictionary["results"] = {}
		json.dump(dictionary, outFile)
		outFile.close()
		
def takeScreenshotOfError():
	myConfigData = getConfigurationData()
	storedUUID = myConfigData["UUID"]
	storedResultsPath = myConfigData["resultsPath"]
	os.system("screencapture "+ storedResultsPath +"/"+ storedUUID +"_"+ time.strftime("%I_%M_%S_%p",time.localtime()) +".png")		

def writeConfigurationDataToLogFile():
	myConfigData = getConfigurationData()
	myLogFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_logfile.txt"
	
	try:
		open(myLogFile)
	
	except IOError as e:
		createNewFile = open(myLogFile, 'w+')
		createNewFile.close()
	
	myTime = time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime())
	myLogFile = open(myLogFile, 'r+')
	myLogFile.read()
	
	myLogFile.write(myTime +":  "+ "############################################################" +"\n")
	myLogFile.write(myTime +":  "+ "UUID:             "+ myConfigData["UUID"] +"\n")
	myLogFile.write(myTime +":  "+ "iCloudEnv:        "+ myConfigData["iCloudAccount"]["environment"] +"\n")
	myLogFile.write(myTime +":  "+ "userid:           "+ myConfigData["iCloudAccount"]["userid"] +"\n")
	myLogFile.write(myTime +":  "+ "password:         "+ myConfigData["iCloudAccount"]["password"] +"\n")
	myLogFile.write(myTime +":  "+ "############################################################" +"\n")
	myLogFile.close()	
	
	sys.stderr.write("*** "+ "############################################################" +"\n")
	sys.stderr.write("*** "+ "UUID:             "+ myConfigData["UUID"] +"\n")
	sys.stderr.write("*** "+ "iCloudEnv:        "+ myConfigData["iCloudAccount"]["environment"] +"\n")
	sys.stderr.write("*** "+ "userid:           "+ myConfigData["iCloudAccount"]["userid"] +"\n")
	sys.stderr.write("*** "+ "password:         "+ myConfigData["iCloudAccount"]["password"] +"\n")
	sys.stderr.write("*** "+ "############################################################" +"\n")
	
def writeItemToCsvFile(msg):
	myConfigData = getConfigurationData()
	myCsvFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_csvResults.csv"
	
	try:
		open(myCsvFile)
	
	except IOError as e:
		createNewFile = open(myCsvFile, 'w+')
		createNewFile.close()
	
	myFile = open(myCsvFile, 'r+')
	myFile.read()
	
	myFile.write(msg +"\n")
	myFile.close()	
	
def writeItemToLogFile(msg):
	myConfigData = getConfigurationData()
	myLogFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_logfile.txt"
	
	try:
		open(myLogFile)
	
	except IOError as e:
		createNewFile = open(myLogFile, 'w+')
		createNewFile.close()
	
	myTime = time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime())
	myFile = open(myLogFile, 'r+')
	myFile.read()
	
	myFile.write(myTime +":  "+ msg +"\n")
	sys.stderr.write("*** "+ msg +"\n")
	myFile.close()	
	
def writeOSXProfileToFile():
	myConfigData = getConfigurationData()
	mySystemProfile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_SystemProfileBasic.spx"
	
	try:
		open(mySystemProfile)
	
	except IOError as e:
		createNewFile = open(mySystemProfile, 'w+')
		createNewFile.close()
		
	with open(mySystemProfile, "r+") as out:
		subprocess.Popen(["system_profiler", "-xml"], stdout=out)

	target.processes()["system_profiler"].waitForInvalid()
			
	msg = "INFO - OSX system profile stored"
	sys.stderr.write("*** "+ msg +"\n")

def writeResultsHeaderDataToLogFile():
	myConfigData = getConfigurationData()
	myResults = getResultsData()	
	myLogFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_logfile.txt"
	
	try:
		open(myLogFile)
	
	except IOError as e:
		createNewFile = open(myLogFile, 'w+')
		createNewFile.close()
		
	myTime = time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime())
	myLogFile = open(myLogFile, 'r+')
	myLogFile.read()
	
	myLogFile.write(myTime +":  "+ "############################################################" +"\n")
	myLogFile.write(myTime +":  "+ "scriptType:       "+ str(myResults["type"]) +"\n")
	myLogFile.write(myTime +":  "+ "version:          "+ str(myResults["version"]) +"\n")
	myLogFile.write(myTime +":  "+ "machine name:     "+ myResults["configurations"]["machineName"] +"\n")
	myLogFile.write(myTime +":  "+ "browser type:     "+ myResults["configurations"]["browserType"] +"\n")	
	myLogFile.write(myTime +":  "+ "browser version:  "+ myResults["configurations"]["browserVersion"] +"\n")
	myLogFile.write(myTime +":  "+ "date:             "+ myResults["configurations"]["date"] +"\n")
	myLogFile.write(myTime +":  "+ "environment:      "+ myResults["configurations"]["environment"] +"\n")
	myLogFile.write(myTime +":  "+ "impairment:       "+ myResults["configurations"]["impairment"] +"\n")
	myLogFile.write(myTime +":  "+ "############################################################" +"\n")	
	myLogFile.close()	
	
	sys.stderr.write("*** "+ "############################################################" +"\n")
	sys.stderr.write("*** "+ "scriptType:       "+ str(myResults["type"]) +"\n")
	sys.stderr.write("*** "+ "version:          "+ str(myResults["version"]) +"\n")
	sys.stderr.write("*** "+ "machine name:     "+ myResults["configurations"]["machineName"] +"\n")
	sys.stderr.write("*** "+ "browser type:     "+ myResults["configurations"]["browserType"] +"\n")	
	sys.stderr.write("*** "+ "browser version:  "+ myResults["configurations"]["browserVersion"] +"\n")
	sys.stderr.write("*** "+ "date:             "+ myResults["configurations"]["date"] +"\n")
	sys.stderr.write("*** "+ "environment:      "+ myResults["configurations"]["environment"] +"\n")
	sys.stderr.write("*** "+ "impairment:       "+ myResults["configurations"]["impairment"] +"\n")
	sys.stderr.write("*** "+ "############################################################" +"\n")

def writeResults(testcaseId, stepName, status):
	myConfigData = getConfigurationData()
	myResultsFile = myConfigData["resultsPath"] +"/"+ myConfigData["UUID"] +"_results.json"
	
	try:
		fileContents = open(myResultsFile).read()	
		fileObject = json.loads(fileContents)	
		
	except:
		msg = "FAIL - problem loading results data"
		scriptFailedExit(msg)

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