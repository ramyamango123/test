libraryBuildVersion = "2013.05.13 v1.0.1"
##########################################################################################
import bs_common
##########################################################################################

def iCloudSignIn(testcaseId):
	stepName = "icloud sign in"
	myConfigData = bs_common.getConfigurationData()

	if not target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Apple ID"]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")	
		msg = "FAIL - bs_icloud.iCloudSignIn: failed to find Apple ID field"
		bs_common.scriptFailedExit(msg)
		
	try:
		target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Apple ID"].click()
		keyboard.typeString_(myConfigData["iCloudAccount"]["userid"])
		
	except:
		bs_common.writeResults(testcaseId, stepName, "FAIL")	
		msg = "FAIL - bs_icloud.iCloudSignIn: failed to enter userid info"
		bs_common.scriptFailedExit(msg)

	try:
		target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Password"].click()
		keyboard.typeString_(myConfigData["iCloudAccount"]["password"])
		
	except:
		bs_common.writeResults(testcaseId, stepName, "FAIL")	
		msg = "FAIL - bs_icloud.iCloudSignIn: failed to enter password info"
		bs_common.scriptFailedExit(msg)
		
	try:
		target.processes()["Safari"].mainWindow().webViews()[0].buttons().firstWithPredicate_("helpTag like 'Sign In'").click()	
		
	except:
		bs_common.writeResults(testcaseId, stepName, "FAIL")	
		msg = "FAIL - bs_icloud.iCloudSignIn: failed to click sign in button"
		bs_common.scriptFailedExit(msg)
	
	time.sleep(1)
	if not target.processes()["Safari"].mainWindow().webViews()[0].images()["iCloud"]:	
		bs_common.writeResults(testcaseId, stepName, "FAIL")	
		msg = "FAIL - bs_icloud.iCloudSignIn: failed to find iCloud icon after sign in"
		bs_common.scriptFailedExit(msg)
		
	else:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
					
def iCloudSignOut(testcaseId):
	stepName = "icloud sign out"
	time.sleep(1)	
	try:
		target.processes()["Safari"].mainWindow().webViews()[0].buttons()["Sign Out"].click()
		
	except:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_icloud.iCloudSignOut: failed to click sign out button"
		bs_common.scriptFailedExit(msg)		

	time.sleep(1)	
	if target.processes()["Safari"].mainWindow().webViews()[0].textFields()["Apple ID"]:
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)

	else:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_icloud.iCloudSignOut: failed to find proper state after sign out"
		bs_common.scriptFailedExit(msg)		
		
def loadiCloudURL(testcaseId):
	stepName = "load icloud url"
	time.sleep(1)
	if not target.frontProcess().withName_("Safari"):
		target.launchApplication_("Safari")
		
	if not target.frontProcess().withName_("Safari"):
		msg = "FAIL - bs_icloud.loadiCloudURL: failed to find Safari process"
		bs_common.scriptFailedExit(msg)

	time.sleep(1)		
	if target.processes()["Safari"].mainWindow().toolbar().textFields()[0]:
		target.processes()["Safari"].mainWindow().toolbar().textFields()[0].click()
		keyboard.typeString_("www.icloud.com")
		keyboard.typeVirtualKey_(kVK_Return)
		
	if not target.processes()["Safari"].mainWindow().toolbar().textFields()["Address and Search"]:
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_icloud.loadiCloudURL: failed to find Safari address field"
		bs_common.scriptFailedExit(msg)
		
	time.sleep(1)		
	if target.processes()["Safari"].mainWindow().toolbar().textFields()["Address and Search"].value() == "www.icloud.com":
		bs_common.writeResults(testcaseId, stepName, "PASS")
		msg = "PASS - "+ stepName
		bs_common.writeItemToLogFile(msg)
		
	else:	
		bs_common.writeResults(testcaseId, stepName, "FAIL")
		msg = "FAIL - bs_icloud.loadiCloudURL: failed to find www.icloud.com in address field"
		bs_common.scriptFailedExit(msg)