#!/usr/bin/python
# -*- coding: utf-8 -*-
if __name__ == '__main__':
	# The following code allows this test to be invoked outside the harness and should be left unchanged
	import os, sys
	args = [os.path.realpath(os.path.expanduser("/usr/local/bin/raft")), "-f"] + sys.argv
	os.execv(args[0], args)
"""
Contact: Christopher Eichhorn / iWork Tools Team
"""
# This is a Raft test. For more information see http://raft.apple.com
testDescription  = ""                 # Add a brief description of test functionality
testVersion      = "2013.04.11"       # Used to differentiate between results for different versions of the test
testState        = DevelopmentState   # Possible values: DevelopmentState, ProductionState
###################################################################
import bs_common, bs_icloud, bs_gilligan
###################################################################
def runTest(params):
	runDataFile = "gilliganTemplates.json"
	templateData = bs_common.getDataFile(runDataFile)	
	
	bs_common.writeConfigurationDataToLogFile()
	bs_common.writeResultsHeaderDataToLogFile()
	
	bs_icloud.loadiCloudURL("xxxxxxxx")
	bs_icloud.iCloudSignIn("xxxxxxxx")
	
	myIterationCount = templateData["iterationCount"]
	
	for x in range(myIterationCount):
		templateData = bs_common.getDataFile(runDataFile)
		currentIteration = templateData["iteration"] + 1
		
		if currentIteration > myIterationCount:
			msg = "all iterations have been completed"
			bs_common.writeToConsole(msg)
			bs_common.writeToLogFile(msg)
			break
			
		else:
			msg = "this is iteration "+ str(currentIteration) +" of "+ str(myIterationCount)
			bs_common.writeToConsole(msg)
			bs_common.writeToLogFile(msg)
			
		for item in templateData['order']:
			if templateData["appTemplates"][item]["completed"] == 0:
				bs_gilligan.createNewFromTemplate(12345678, item, runDataFile)
				
			else:
				msg = "'"+ item +"' - set as completed. skipping"
				bs_common.writeToConsole(msg)
				bs_common.writeToLogFile(msg)
				continue
						
			bs_common.setCurrentIteration(runDataFile, currentIteration)
			bs_common.resetItemStatus(runDataFile)
	
	bs_gilligan.deleteGilliganFiles("xxxxxxxx", "Keynote")	
	bs_gilligan.deleteGilliganFiles("xxxxxxxx", "Numbers")	
	bs_gilligan.deleteGilliganFiles("xxxxxxxx", "Pages")	
	bs_icloud.iCloudSignOut("xxxxxxxx")	
	bs_common.quitSafari()
	bs_common.cleanupConfiguration()
	bs_common.setCurrentIteration(runDataFile, 0)
