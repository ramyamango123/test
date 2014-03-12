###################################################################
import bs_common
###################################################################
def iCloudSignIn(testcaseId):
	stepName = "icloud sign in"
	if testcaseId is not None:
		configurationData = bs_common.getConfiguration()
		time.sleep(1)
		
		with patience(30):
			target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Apple ID"].isEnabled()
		
		if not target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Apple ID"].isEnabled():
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - "+ stepName
			bs_common.transactionFailedExit(msg)
			
		target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Apple ID"].click()
		keyboard.typeString_(configurationData["iCloudAccount"]["userid"])
		target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Password"].click()
		keyboard.typeString_(configurationData["iCloudAccount"]["password"])
		target.processes()["Safari"].mainWindow().webViews()[0].buttons().firstWithPredicate_("helpTag like 'Sign In'").click()	
		
		with patience(30): target.processes()["Safari"].mainWindow().webViews()[0].images()["iCloud"]
		
		time.sleep(5)
		
		if not target.processes()["Safari"].mainWindow().webViews()[0].images()["iCloud"].isEnabled():
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - "+ stepName
			bs_common.transactionFailedExit(msg)

		else:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
			msg = "PASS - "+ stepName
			bs_common.writeToConsole(msg)
			bs_common.writeToLogFile(msg)
			
def iCloudSignOut(testcaseId):
	stepName = "icloud sign out"
	if testcaseId is not None:
		time.sleep(1)
		if target.processes()["Safari"].windows()["iCloud"].webViews()[0].buttons()["Sign Out"].isEnabled():
			target.processes()["Safari"].windows()["iCloud"].webViews()[0].buttons()["Sign Out"].click()
			
		if target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Apple ID"]:
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
			msg = "PASS - "+ stepName
			bs_common.writeToConsole(msg)
			bs_common.writeToLogFile(msg)
	
		else:
			msg = "FAIL - "+ stepName
			bs_common.transactionFailedExit(msg)
			
def loadiCloudURL(testcaseId):
	stepName = "load icloud url"
	if testcaseId is not None:	
		if not target.frontProcess().withName_("Safari"):
			target.launchApplication_("Safari")
			time.sleep(1)
			
		if target.processes()["Safari"].mainWindow().toolbar().textFields()[0]:
			target.processes()["Safari"].mainWindow().toolbar().textFields()[0].click()
			keyboard.typeString_("www.icloud.com")
			keyboard.typeVirtualKey_(kVK_Return)
			time.sleep(1)
			
		if target.processes()["Safari"].mainWindow().toolbar().textFields()["Address and Search"].value() == "www.icloud.com":
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "PASS")
			msg = "PASS - "+ stepName
			bs_common.writeToConsole(msg)
			bs_common.writeToLogFile(msg)
			
		else:	
			bs_common.writeFunctionalTestResults(testcaseId, stepName, "FAIL")
			msg = "FAIL - "+ stepName
			bs_common.transactionFailedExit(msg)

