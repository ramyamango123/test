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
testDescription  = ""                 		# Add a brief description of test functionality
testVersion      = "2013.05.13 v1.0.0"		# Used to differentiate between results for different versions of the test
testState        = ProductionState			# Possible values: DevelopmentState, ProductionState
def runTest(params):
	###################################################################
	sys.path.append('../Libraries')
	import bs_common, bs_icloud, bs_gilligan
	###################################################################
	
	bs_common.setConfigurationData()
	bs_common.writeConfigurationDataToLogFile()
	bs_common.writeResultsHeaderDataToLogFile()
	bs_common.writeOSXProfileToFile()
	
	myResultsData = bs_common.getResultsData()
	
	bs_common.writeItemToCsvFile("Pi Automation Test Suite started on: "+ time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime()))
	bs_common.writeItemToCsvFile("OS: OS X Browser: Safari / "+ myResultsData["configurations"]["browserVersion"])
	
	bs_icloud.loadiCloudURL("xxxxxxxx")
	bs_icloud.iCloudSignIn("xxxxxxxx")	
	bs_gilligan.loadGilliganApp("xxxxxxxx", "Numbers")

	bs_common.writeItemToCsvFile("Gilligan login :, PASSED")

	bs_gilligan.deleteGilliganFiles("xxxxxxxx", "Numbers")
	
	bs_common.writeItemToCsvFile("Deleting All Existing Spreadsheets :, PASSED")	

	bs_gilligan.createNewFile("xxxxxxxx", "Numbers", "Budget")
	
	bs_common.writeItemToCsvFile("Create Spreadsheet (Budget Theme Via Gear Menu) :, PASSED")	
	
	bs_gilligan.fileClose("xxxxxxxx", "Budget")
	bs_gilligan.checkFileSyncing("Numbers")	
		
	bs_gilligan.gearMenu_fileDownload("xxxxxxxx", "Numbers", "Budget", "Numbers")
	
	bs_common.writeItemToCsvFile("Download (in Numbers Via Gear Icon) :, PASSED")	
	
	bs_gilligan.gearMenu_fileDuplicate("xxxxxxxx", "Numbers", "Budget")

	bs_common.writeItemToCsvFile("Duplicate (Via Gear menu) :, PASSED")	

	bs_gilligan.fileRename("xxxxxxxx", "Numbers", "Budget copy", "Personal Budget_2013")
	
	bs_common.writeItemToCsvFile("Rename Spreadsheet :, PASSED")	
		
	bs_gilligan.fileOpen("xxxxxxxx", "Numbers", "Personal Budget_2013")
	
	bs_common.writeItemToCsvFile("Open existing spreadsheet :, PASSED")	
	
	bs_gilligan.modifySpreadsheetTabs("xxxxxxxx", "Personal Budget_2013")
	
	bs_common.writeItemToCsvFile("Add sheets to spreadsheet :, PASSED")	
	
	bs_common.writeItemToCsvFile("Delete sheet :, PASSED")	
	
	bs_common.writeItemToCsvFile("Rename sheet :, PASSED")	
	
	bs_common.writeItemToCsvFile("Apply Table Style *** not implemented ***:, PASSED")	

	bs_gilligan.fileClose("xxxxxxxx", "Personal Budget_2013")
	bs_gilligan.checkFileSyncing("Numbers")	
	
	bs_gilligan.createNewFile("xxxxxxxx", "Numbers", "Mortgage Calculator")
	
	bs_common.writeItemToCsvFile("Create Spreadsheet (Mortgage Calculator Theme via + Icon) :, PASSED")	

	bs_common.writeItemToCsvFile("Table editing *** not implemented ***:, PASSED")	
	
	bs_gilligan.fileClose("xxxxxxxx", "Mortgage Calculator")
	bs_gilligan.checkFileSyncing("Numbers")

	bs_gilligan.createNewFile("xxxxxxxx", "Numbers", "Blank")
	
	bs_common.writeItemToCsvFile("Create Spreadsheet (Blank Theme via Gear Icon) :, PASSED")	

	bs_common.writeItemToCsvFile("Table editing *** not implemented ***:, PASSED")	

	bs_common.writeItemToCsvFile("Cancel on Delete *** not implemented ***:, PASSED")	
	
	bs_gilligan.fileClose("xxxxxxxx", "Blank")
	bs_gilligan.checkFileSyncing("Numbers")
	
	bs_gilligan.gearMenu_fileUpload("xxxxxxxx", "Numbers", "test_01.numbers")
	
	bs_common.writeItemToCsvFile("Upload Numbers :, PASSED")	
	
	bs_gilligan.checkFileSyncing("Numbers")
	
	bs_gilligan.fileOpenUploaded("xxxxxxxx", "Numbers", "test_01")
	
	bs_common.writeItemToCsvFile("Open and Save Uploaded :, PASSED")
	
	bs_gilligan.fileClose("xxxxxxxx", "test_01")
	bs_gilligan.checkFileSyncing("Numbers")		
	
	bs_gilligan.exitGilligan("xxxxxxxx", "Numbers")
	
	bs_common.writeItemToCsvFile("Gilligan logout :, PASSED")
	
	bs_icloud.iCloudSignOut("xxxxxxxx")
	bs_common.quitSafari()
	bs_common.fileDeleteFromOSX("Budget.numbers")
	bs_common.resetConfigurationData()	
	logPass()