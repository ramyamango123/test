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
testVersion      = "2013.03.27"       # Used to differentiate between results for different versions of the test
testState        = DevelopmentState   # Possible values: DevelopmentState, ProductionState
###################################################################
import bs_common, bs_icloud, bs_gilligan
###################################################################
def runTest(params):
	bs_common.writeConfigurationDataToLogFile()
	bs_common.writeResultsHeaderDataToLogFile()
# 	bs_common.writeOSXSystemProfile()
	
	bs_icloud.loadiCloudURL("xxxxxxxx")
	bs_icloud.iCloudSignIn("xxxxxxxx")
	bs_gilligan.loadGilliganApp("10000001", "Pages")
	
	bs_gilligan.validateGearMenu("10000001", "Pages")

	bs_gilligan.createNewFile("10000002", "Pages", "Blank")
	bs_gilligan.fileClose("10000002", "Blank")
	bs_gilligan.checkFileSyncing("Pages")	

	bs_gilligan.fileRename("10000003", "Pages", "Blank", "Test_00.pages")
	bs_gilligan.checkFileSyncing("Pages")

	bs_gilligan.fileOpen("10000004", "Pages", "Test_00.pages")
	bs_gilligan.formatMenuShowHide("10000004", "Pages", "Test_00.pages")
	bs_gilligan.fileClose("10000004", "Test_00.pages")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.createNewFile("10000005", "Pages", "Personal Photo Letter")
	bs_gilligan.fileClose("10000005", "Personal Photo Letter")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.fileRename("10000006", "Pages", "Personal Photo Letter", "Test_02")
	bs_gilligan.checkFileSyncing("Pages")

	bs_gilligan.fileDuplicate("10000007", "Pages", "Test_02")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.fileExport("10000008", "Pages", "Test_00.pages copy", "Pages")
	bs_common.fileDeleteFromExported("Test_00.pages", "pages")
	
	bs_gilligan.createNewFile("10000009", "Pages", "Project Proposal")
	bs_gilligan.fileClose("10000009", "Project Proposal")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.fileExport("10000011", "Pages", "Project Proposal", "Word")
	bs_common.fileDeleteFromExported("Project Proposal", "doc")
	
	bs_gilligan.createNewFile("10000012", "Pages", "Poster")
	bs_gilligan.fileClose("10000012", "Poster")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.fileDeleteGilligan("10000013", "Pages", "Project Proposal")

	bs_gilligan.gearMenu_fileUpload("10000014", "Pages", "Test_01.pages")
	bs_gilligan.fileDuplicate("10000014", "Pages", "Test_01")
	bs_gilligan.fileRename("10000014", "Pages", "Test_01", "Test Uploaded")
	
	bs_gilligan.fileOpen("10000014", "Pages", "Test Uploaded")
	bs_gilligan.fileClose("10000014", "Test Uploaded")	
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.contextMenu_fileDuplicate("10000015", "Pages", "Test Uploaded")	
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.contextMenu_fileExport("10000016", "Pages", "Test Uploaded copy", "Pages")
	
	bs_gilligan.fileRename("10000017", "Pages", "Test_01 copy", "Test_03")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.fileOpen("10000018", "Pages", "Test_03")
	bs_gilligan.fileFindReplace("10000018", "Pages", "Test_03", "null", "Float")
	
	
	
	
	
# 	bs_gilligan.exitGilligan("xxxxxxxx", "Pages")
# 	bs_gilligan.deleteGilliganFiles("xxxxxxxx", "Pages")
# 	bs_gilligan.exitGilligan("xxxxxxxx", "Pages")
# 	bs_icloud.iCloudSignOut("xxxxxxxx")
	bs_common.cleanupConfiguration()	