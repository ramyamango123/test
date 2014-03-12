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

	bs_common.writeItemToCsvFile("Pesto Automation Test Suite started on: "+ time.strftime("%a %d %b %Y %I:%M:%S %p",time.localtime()))
	bs_common.writeItemToCsvFile("OS: OS X Browser: Safari / "+ myResultsData["configurations"]["browserVersion"])

	bs_icloud.loadiCloudURL("xxxxxxxx")
	bs_icloud.iCloudSignIn("xxxxxxxx")
	bs_gilligan.loadGilliganApp("xxxxxxxx", "Pages")
	
	bs_common.writeItemToCsvFile("Gilligan login :, PASSED")	

	bs_gilligan.deleteGilliganFiles("xxxxxxxx", "Pages")
	
	bs_common.writeItemToCsvFile("Deleting All Existing Documents :, PASSED")	
	
	bs_gilligan.validateGearMenu("xxxxxxxx", "Pages")
	
	bs_common.writeItemToCsvFile("Validate Gear Menu Items :, PASSED")	
	
	bs_gilligan.createNewFile("xxxxxxxx", "Pages", "Blank")
	bs_gilligan.fileClose("xxxxxxxx", "Blank")
	bs_gilligan.checkFileSyncing("Pages")	
	
	bs_common.writeItemToCsvFile("Creating Doc (Blank Theme Via Gear Icon) :, PASSED")	
	
	bs_gilligan.fileRename("xxxxxxxx", "Pages", "Blank", "Test_00.pages")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Rename Blank Doc :, PASSED")	
	
	bs_gilligan.fileOpen("xxxxxxxx", "Pages", "Test_00.pages")
	bs_gilligan.formatMenuShowHide("xxxxxxxx", "Pages", "Test_00.pages")
	bs_gilligan.fileClose("xxxxxxxx", "Test_00.pages")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Blank Document Show/Hide format bar only :, PASSED")	
	
	bs_gilligan.createNewFile("xxxxxxxx", "Pages", "Personal Photo Letter")
	bs_gilligan.fileClose("xxxxxxxx", "Personal Photo Letter")
	bs_gilligan.checkFileSyncing("Pages")

	bs_common.writeItemToCsvFile("Personal Photo Letter (create/open only) :, PASSED")	

	bs_gilligan.fileRename("xxxxxxxx", "Pages", "Personal Photo Letter", "Test_02")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Rename Personal Photo Letter Doc :, PASSED")	

	bs_gilligan.gearMenu_fileDuplicate("xxxxxxxx", "Pages", "Test_02")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Duplicate (Via Gear Icon) :, PASSED")	
	
	bs_gilligan.gearMenu_fileDownload("xxxxxxxx", "Pages", "Test_02 copy", "Pages")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Download (In Pages Via Gear Icon) :, PASSED")	
	
	bs_gilligan.createNewFile("xxxxxxxx", "Pages", "Project Proposal")
	bs_gilligan.fileClose("xxxxxxxx", "Project Proposal")
	bs_gilligan.checkFileSyncing("Pages")

	bs_common.writeItemToCsvFile("Project Proposal *** Create only. Image edit not implemented *** :, PASSED")	
	
	bs_gilligan.gearMenu_fileDownload("xxxxxxxx", "Pages", "Test_02 copy", "PDF")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Download (In PDF Via Gear Icon) :, PASSED")	
	
	bs_gilligan.gearMenu_fileDownload("xxxxxxxx", "Pages", "Project Proposal", "Word")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Download (In Word Via Gear Icon) :, PASSED")
	
	bs_gilligan.createNewFile("xxxxxxxx", "Pages", "Poster")

	bs_common.writeItemToCsvFile("Creating Doc (Poster Theme Via + Icon) :, PASSED")
	
	bs_gilligan.fileClose("xxxxxxxx", "Poster")
	
	bs_common.writeItemToCsvFile("Saving Document :, PASSED")
	
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.gearMenu_fileDelete("xxxxxxxx", "Pages", "Project Proposal")
	
	bs_common.writeItemToCsvFile("Deleting Document Via Gear Menu :, PASSED")
	
	bs_gilligan.gearMenu_fileUpload("xxxxxxxx", "Pages", "Test_01.pages")
	
	bs_gilligan.contextMenu_fileDuplicate("xxxxxxxx", "Pages", "Test_01")	
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Upload Pages :, PASSED")
		
	bs_gilligan.fileRename("xxxxxxxx", "Pages", "Test_01 copy", "Test Uploaded")
	
	bs_common.writeItemToCsvFile("Rename Doc :, PASSED")

	bs_gilligan.fileOpen("xxxxxxxx", "Pages", "Test Uploaded")
	
	bs_common.writeItemToCsvFile("Open And Edit Uploaded Pages *** edit not implemented *** :, PASSED")
	
	bs_gilligan.fileClose("xxxxxxxx", "Test Uploaded")	
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_gilligan.contextMenu_fileDuplicate("xxxxxxxx", "Pages", "Test Uploaded")	
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Duplicate (Via Context Menu) :, PASSED")
	
	bs_gilligan.contextMenu_fileDownload("xxxxxxxx", "Pages", "Test Uploaded copy", "PDF")
	
	bs_common.writeItemToCsvFile("Downloading PDF Doc Via Context Menu :, PASSED")
	
	bs_gilligan.contextMenu_fileDuplicate("xxxxxxxx", "Pages", "Test_01")
	bs_gilligan.checkFileSyncing("Pages")	
	
	bs_gilligan.fileRename("xxxxxxxx", "Pages", "Test_01 copy", "Test_03")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Rename Doc :, PASSED")

	bs_gilligan.fileOpen("xxxxxxxx", "Pages", "Test_03")
	bs_gilligan.fileFindReplace("xxxxxxxx", "Pages", "Test_03", "null", "Float")
	bs_gilligan.fileShowHelp("xxxxxxxx", "Pages", "Test_03")
	bs_gilligan.fileClose("xxxxxxxx", "Test_03")
	
	bs_common.writeItemToCsvFile("Open And Edit Existing Doc :, PASSED")

	bs_gilligan.gearMenu_fileUpload("xxxxxxxx", "Pages", "test.txt")
	bs_gilligan.checkFileSyncing("Pages")
	
	bs_common.writeItemToCsvFile("Upload Text File :, PASSED")
	
	bs_gilligan.gearMenu_fileDelete("xxxxxxxx", "Pages", "test")
	
	bs_common.writeItemToCsvFile("Deleting Text File With Delete Key :, PASSED")
	
	bs_gilligan.exitGilligan("xxxxxxxx", "Pages")
	
	bs_common.writeItemToCsvFile("Gilligan logout:, PASSED")
	
	bs_icloud.iCloudSignOut("xxxxxxxx")
	bs_common.quitSafari()
	
	bs_common.fileDeleteFromOSX("Test_02 copy.pages")
	bs_common.fileDeleteFromOSX("Project Proposal.doc")	
	bs_common.fileDeleteFromOSX("Test Uploaded copy.pages")

	bs_common.resetConfigurationData()	
	logPass()